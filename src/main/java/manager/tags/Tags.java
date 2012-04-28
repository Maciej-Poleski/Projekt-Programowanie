package manager.tags;

import manager.files.FileID;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Klasa zarządzająca całym zbiorem tagów wykorzystywanym w aplikacji. Pozwala na dostęp do "głów".
 * Pozwala na dostęp do etykiety tagów. Pozwala na dodawanie dowolnych metadanych do tagów.
 * Aby uzyskać pełną funkcjonalność należy podać jej referencję do bazy danych.
 * <p/>
 * Jest to klasa pełniąca rolę zarządcy całej struktury tagów. Obiekty tej klasy mają wyłączne prawo do modyfikowania
 * ich struktury. Udostępniają również wiele funkcji pozwalających na pracę z tagami jako pewną całością, a nie
 * poszczególnymi węzłami. Możliwe jest uzyskanie modelu drzewa reprezentującego strukturę tagów macierzystych
 * i użytkownika. Dla każdego tagu macierzystego można uzyskać jego ścieżkę w systemie plików od korzenia (lokalizacji)
 * danej kolekcji. Możliwe jest tworzenie i usuwanie relacji. Tak samo jak samych tagów. Obiekty tej klasy potrafią
 * również przechowywać dowolne metadane przypisane do dowolnego tagu. Ten mechanizm może być przydatny jeżeli
 * tekstowa etykieta tagu jest niesatysfakcjonująca.
 *
 * @author Maciej Poleski
 */
public class Tags implements Serializable {
    private final List<Tag<?>> tags = new ArrayList<>();
    private TagFilesStore store = new TagFilesStore();
    private final Map<Tag<?>, String> tagNames = new HashMap<>();
    private final Map<Tag<?>, Set<Serializable>> tagMetadata = new HashMap<>();
    private transient List<WeakReference<MasterTagsTreeModel>> masterTagsTreeModelList = new ArrayList<>();
    private transient List<WeakReference<UserTagsTreeModel>> userTagsTreeModelList = new ArrayList<>();
    private static Tags defaultInstance;
    private static final long serialVersionUID = 1;

    /**
     * Wszystkie węzły w modelu drzewa tagów użytkownika implementują ten interfejs. Pozwala on na wyłuskanie
     * tagu mając do dyspozycji jedynie węzeł z drzewa.
     */
    public interface IUserTagNode {
        /**
         * Zwraca tag użytkownika reprezentowany przez dany węzeł.
         *
         * @return Tag użytkownika
         */
        UserTag getTag();
    }

    /**
     * Konstruuje nowy obiekt z pustą rodziną tagów.
     */
    public Tags() {
        if (defaultInstance == null) {
            setDefaultInstance(this);
        }
    }

    /**
     * Tworzy nowy tag macierzysty.
     *
     * @return Nowy tag macierzysty
     */
    public MasterTag newMasterTag() {
        MasterTag tag = new MasterTag(this);
        tags.add(tag);
        notifyMasterTagsTreeModels();
        return tag;
    }

    /**
     * Tworzy nowy tag użytkownika
     *
     * @return Nowy tag użytkownika
     */
    public UserTag newUserTag() {
        UserTag tag = new UserTag(this);
        tags.add(tag);
        notifyUserTagsTreeModels();
        return tag;
    }

    /**
     * Zwraca wszystkie tagi które nie mają przodków.
     *
     * @return Zbiór tagów które nie mają przodków
     */
    public Set<Tag<?>> getHeads() {
        Set<Tag<?>> result = new HashSet<>();
        for (Tag<?> tag : tags) {
            if (tag.getParents().isEmpty()) {
                result.add(tag);
            }
        }
        return result;
    }

    /**
     * Zwraca wszystkie tagi macierzyste które nie mają przodków.
     *
     * @return Zbiór tagów macierzystych które nie mają przodków
     */
    public Set<MasterTag> getMasterTagHeads() {
        Set<MasterTag> result = new HashSet<>();
        for (Tag<?> tag : getHeads()) {
            if (tag instanceof MasterTag) {
                result.add((MasterTag) tag);
            }
        }
        return result;
    }

    /**
     * Zwraca wszystkie tagi użytkownika które nie mają przodków.
     *
     * @return Zbiór tagów użytkownika które nie mają przodków
     */
    public Set<UserTag> getUserTagHeads() {
        Set<UserTag> result = new HashSet<>();
        for (Tag<?> tag : getHeads()) {
            if (tag instanceof UserTag) {
                result.add((UserTag) tag);
            }
        }
        return result;
    }

    /**
     * Usuwa wskazany tag macierzysty ze struktury co może oznaczać rozspójnienie i powstanie nowych "głów".
     * Nie nadużywaj tej funkcji. Przed użyciem tej metody należy ustawić bazę plików.
     *
     * @param tag Tag który ma zostać usunięty.
     * @throws StoreNotAvailableException Jeżeli nie ustawiono bazy plików
     * @throws IllegalArgumentException   Jeżeli tag==null
     * @throws IllegalStateException      Jeżeli istnieje jakikolwiek plik otagowany wskazanym tagiem lub jego pochodną
     * @see #setStore(TagFilesStore)
     */
    public void removeTag(MasterTag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tagowanie null-ami nie ma sensu");
        }
        checkStore();
        if (!store.pretendRemoveFamily(tag).isEmpty()) {
            throw new IllegalStateException("Istnieją pliki otagowane tagiem " + tag + ", więc nie można go usunąć");
        }
        removeMasterTagFromStructure(tag);
    }

    /**
     * Usuwa wskazany tag macierzysty wraz z jego wszystkimi pochodnymi ze struktury.
     * Nie nadużywaj tej funkcji. Przed użyciem tej metody należy ustawić bazę plików.
     *
     * @param tag Tag którego rodzina ma zostać usunięta.
     * @throws StoreNotAvailableException Jeżeli nie ustawiono bazy plików
     * @throws IllegalArgumentException   Jeżeli tag==null
     * @throws IllegalStateException      Jeżeli istnieje jakikolwiek plik otagowany wskazanym tagiem lub jego pochodną
     * @see #setStore(TagFilesStore)
     */
    public void removeMasterTagTree(MasterTag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tagowanie null-ami nie ma sensu");
        }
        checkStore();
        if (!store.pretendRemoveFamily(tag).isEmpty()) {
            throw new IllegalStateException("Istnieją pliki otagowane tagiem " + tag + ", więc nie można go usunąć");
        }
        Collection<MasterTag> tagsToRemove = new ArrayList<>(tag.getDescendants());
        for (MasterTag t : tagsToRemove) {
            removeMasterTagFromStructure(t);
        }
        removeMasterTagFromStructure(tag);
    }

    /**
     * Usuwa wskazany tag użytkownika odznaczając jednocześnie wszystkie oznaczone nim pliki w bazie. Tag jest
     * również usuwany ze struktury co może oznaczać rozspójnienie i powstanie nowych "głów". Przed użyciem tej
     * metody należy ustawić bazę plików.
     *
     * @param tag Tag który ma zostać usunięty.
     * @throws StoreNotAvailableException Jeżeli nie ustawiono bazy plików
     * @see #setStore(TagFilesStore)
     */
    public void removeTag(UserTag tag) {
        Set<FileID> filesToRemoveTagFrom = store.getFilesWithRealTag(tag);
        for (FileID file : filesToRemoveTagFrom) {
            store.removeFileTag(file, tag);
        }
        removeUserTagFromStructure(tag);
    }

    /**
     * Ustawia główny magazyn tagów. Niektóre metody wymagają dostępu do głównego magazynu danych.
     * Ta funkcja pozwala na jego ustawienie.
     *
     * @param store Główny magazyn danych
     */
    public void setStore(TagFilesStore store) {
        this.store = store;
    }

    /**
     * Zwraca referencję do głównego magazynu danych. Uzasadnione jest korzystanie z tej funkcji z każdego modułu.
     *
     * @return Główny magazyn danych
     */
    public TagFilesStore getStore() {
        return store;
    }

    /**
     * Tworzy nowy tag macierzysty jako dziecko wskazanego tagu macierzystego.
     *
     * @param parent Tag macierzysty
     * @return Nowy tag macierzysty będący dzieckiem wskazanego tagu macierzystego
     */
    public MasterTag newMasterTag(MasterTag parent) {
        MasterTag tag = newMasterTag();
        tag.setParent(parent);
        notifyMasterTagsTreeModels();
        return tag;
    }

    /**
     * Tworzy nowy tag macierzysty jako dziecko wskazanego tagu macierzystego. I przypisuje mu etykietę.
     *
     * @param parent Tag macierzysty
     * @param name   Etykieta nowego tagu macierzystego
     * @return Nowy tag macierzysty o wskazanej etykiecie będący dzieckiem wskazanego tagu macierzystego
     */
    public MasterTag newMasterTag(MasterTag parent, String name) {
        MasterTag tag = newMasterTag(parent);
        setNameOfTag(tag, name);
        notifyMasterTagsTreeModels();
        return tag;
    }

    /**
     * Tworzy nowy tag macierzysty i przypisuje mu etykietę.
     *
     * @param name Etykieta nowego tagu macierzystego
     * @return Nowy tag macierzysty o wskazanej etykiecie
     */
    public MasterTag newMasterTag(String name) {
        return newMasterTag(null, name);
    }

    /**
     * Tworzy nowy tag użytkownika jako dziecko wskazanych tagów użytkownika i rodzic wskazanych tagów użytkownika.
     *
     * @param parents  Tagi użytkownika - rodzice
     * @param children Tagi użytkownika - dzieci
     * @return Nowy tag użytkownika będący dzieckiem wskazanych tagów użytkownika i rodzicem wskazanych tagów użytkownika.
     * @throws CycleException Jeżeli dodanie wskazanych relacji spowoduje powstanie cyklu
     */
    public UserTag newUserTag(Set<UserTag> parents, Set<UserTag> children) throws CycleException {
        UserTag result = newUserTag();
        if (parents == null) {
            parents = new HashSet<>();
        }
        if (children == null) {
            children = new HashSet<>();
        }
        for (UserTag parent : parents) {
            result.addParent(parent);
        }
        for (UserTag child : children) {
            result.addChild(child);
        }
        try {
            checkCycle();
            notifyUserTagsTreeModels();
        } catch (CycleException e) {
            for (UserTag parent : parents) {
                result.removeParent(parent);
            }
            for (UserTag child : children) {
                result.removeChild(child);
            }
            throw new CycleException(e);
        }
        return result;
    }

    /**
     * Tworzy nowy tag użytkownika jako dziecko wskazanych tagów użytkownika i rodzic wskazanych tagów użytkownika.
     * Przypisuje mu etykietę.
     *
     * @param parents  Tagi użytkownika - rodzice
     * @param children Tagi użytkownika - dzieci
     * @param name     Etykieta nowego tagu użytkownika
     * @return Nowy tag użytkownika będący dzieckiem wskazanych tagów użytkownika i rodzicem wskazanych tagów
     *         użytkownika o wskazanej etykiecie.
     * @throws CycleException Jeżeli dodanie wskazanych relacji spowoduje powstanie cyklu
     */
    public UserTag newUserTag(Set<UserTag> parents, Set<UserTag> children, String name) throws CycleException {
        UserTag result = newUserTag(parents, children);
        setNameOfTag(result, name);
        notifyUserTagsTreeModels();
        return result;
    }

    /**
     * Tworzy nowy tag użytkownika i przypisuje mu etykietę.
     *
     * @param name Etykieta nowego tagu użytkownika
     * @return Nowy tag użytkownika o wskazanej etykiecie.
     */
    public UserTag newUserTag(String name) {
        try {
            return newUserTag(null, null, name);
        } catch (CycleException e) {
            assert false;
            return null;
        }
    }

    /**
     * Zwraca etykietę tagu (napis)
     *
     * @param tag Tag którego etykieta zostanie zwrócona.
     * @return Etykieta wskazanego tagu
     */
    public String getNameOfTag(Tag<?> tag) {
        return tagNames.get(tag);
    }

    /**
     * Ustawia etykietę podanego tagu. Poprzednia etykieta jest zapominana.
     *
     * @param tag     Tag którego etykieta zostanie ustawiona
     * @param newName Etykieta
     */
    public void setNameOfTag(Tag<?> tag, String newName) {
        tagNames.put(tag, newName);
        if (tag instanceof MasterTag) {
            notifyMasterTagsTreeModels();
        } else if (tag instanceof UserTag) {
            notifyUserTagsTreeModels();
        }
    }

    /**
     * Zwraca wszystkie metadane związane z wskazanym tagiem.
     *
     * @param tag Tag którego metadane zostaną zwrócone
     * @return Wszystkie metadane związane z danym tagiem
     */
    public Set<Serializable> getAllMetadataOfTag(Tag<?> tag) {
        return tagMetadata.get(tag) != null ? tagMetadata.get(tag) : new HashSet<Serializable>();
    }

    /**
     * Dodaje metadane do tagu. Ta operacja jest opcjonalna co oznacza że metadane zostaną dodane pod warunkiem że nie
     * zostały dodane wcześniej.
     *
     * @param tag      Tag do którego dodajemy metadane
     * @param metadata Metadane które zostaną powiązane z wskazanym tagiem.
     */
    public void addTagMetadata(Tag<?> tag, Serializable metadata) {
        if (!tagMetadata.containsKey(tag)) {
            tagMetadata.put(tag, new HashSet<Serializable>());
        }
        tagMetadata.get(tag).add(metadata);
    }

    /**
     * Usuwa metadane z tagu. Ta operacja usuwa powiązanie wskazanego tagu z wskazanymi metadanymi.
     *
     * @param tag      Tag z którego będą usunięte metadane
     * @param metadata Metadane które zostaną usunięte z tagu.
     * @throws IllegalStateException    Jeżeli wskazany tag nie posiada wskazanych metadanych
     * @throws IllegalArgumentException Jeżeli tag==null lub metadata=null
     */
    public void removeTagMetadata(Tag<?> tag, Serializable metadata) {
        if (tag == null || metadata == null) {
            throw new IllegalArgumentException("Musisz wskazać obiekt (nie null)");
        }
        if (!tagMetadata.containsKey(tag) || !tagMetadata.get(tag).contains(metadata)) {
            throw new IllegalStateException("Tag " + tag + " nie posiada metadanych " + metadata);
        }
        tagMetadata.get(tag).remove(metadata);
        if (tagMetadata.get(tag).isEmpty()) {
            tagMetadata.remove(tag);
        }
    }

    /**
     * Dodaje dziecko do tagu (ustawia relację między tagami). Jeżeli zostanie rzucony wyjątek, operacja nie spowoduje
     * modyfikacji struktury.
     *
     * @param child Tag który zostanie dzieckiem
     * @param tag   Tag który zostanie rodzicem
     * @param <T>   Typ tagów
     * @throws IllegalArgumentException Jeżeli child==null lub tag==null
     * @throws IllegalStateException    Jeżeli wskazana relacja już istnieje
     * @throws CycleException           Jeżeli wykonanie tej operacji spowodowałoby powstanie cyklu
     * @see #removeChildFromTag(Tag, Tag)
     */
    public <T extends Tag<T>> void addChildToTag(T child, T tag) throws CycleException {
        if (child == null || tag == null) {
            throw new IllegalArgumentException("Tworzenie relacji między null-ami nie ma sensu");
        }
        if (tag.getChildren().contains(child)) {
            throw new IllegalStateException("Tag " + tag + " ma już dziecko " + child);
        }
        try {
            tag.addChild(child);
            checkCycle();
            if (tag instanceof MasterTag) {
                notifyMasterTagsTreeModels();
            } else if (tag instanceof UserTag) {
                notifyUserTagsTreeModels();
            }
        } catch (CycleException e) {
            tag.removeChild(child);
            throw new CycleException(e);
        } catch (Exception e) {
            throw new IllegalStateException("Wystąpił nieoczekiwany wyjątek: " + e);
        }
    }

    /**
     * Usuwa dziecko z tagu (usuwa relację między tagami). Jeżeli zostanie rzucony wyjątek, operacja nie spowoduje
     * modyfikacji struktury.
     *
     * @param child Tag który zostanie dzieckiem
     * @param tag   Tag który zostanie rodzicem
     * @param <T>   Typ tagów
     * @throws IllegalArgumentException Jeżeli child==null lub tag==null
     * @throws IllegalStateException    Jeżeli wskazana relacja nie istnieje
     * @see #addChildToTag(Tag, Tag)
     */
    public <T extends Tag<T>> void removeChildFromTag(T child, T tag) {
        if (child == null || tag == null) {
            throw new IllegalArgumentException("Tworzenie relacji między null-ami nie ma sensu");
        }
        if (!tag.getChildren().contains(child)) {
            throw new IllegalStateException("Tag " + tag + " nie ma dziecka " + child);
        }
        tag.removeChild(child);
        if (tag instanceof MasterTag) {
            notifyMasterTagsTreeModels();
        } else if (tag instanceof UserTag) {
            notifyUserTagsTreeModels();
        }
    }

    /**
     * Ustawia rodzica wskazanego tagu macierzystego (tworzy relacje). Jeżeli zostanie rzucony wyjątek, operacja nie
     * spowoduje modyfikacji struktury.
     *
     * @param parent Tag który zostanie (jedynym) rodzicem.
     * @param tag    Tag który zostanie dzieckiem.
     * @throws IllegalArgumentException Jeżeli parent==null lub tag==null
     * @throws IllegalStateException    Jeżeli wskazana relacja już istnieje
     * @throws CycleException           Jeżeli dodanie tej relacji spowoduje powstanie cyklu
     * @see #removeParentOfTag(Tag, Tag)
     */
    public void setParentOfTag(MasterTag parent, MasterTag tag) throws CycleException {
        if (parent == null || tag == null) {
            throw new IllegalArgumentException("Tworzenie relacji między null-ami nie ma sensu");
        }
        if (tag.getParent() == parent) {
            throw new IllegalStateException("Tag " + tag + " ma już rodzica " + parent);
        }
        MasterTag oldParent = null;
        try {
            oldParent = tag.getParent();
            tag.setParent(parent);
            checkCycle();
            notifyMasterTagsTreeModels();
        } catch (CycleException e) {
            tag.setParent(oldParent);
            throw new CycleException(e);
        } catch (Exception e) {
            throw new IllegalStateException("Wystąpił nieoczekiwany wyjątek: " + e);
        }
    }

    /**
     * Usuwa wskazanego rodzica wskazanego tagu (usuwa relacje). Jeżeli zostanie rzucony wyjątek,
     * operacja nie spowoduje modyfikacji struktury.
     *
     * @param parent Tag który obecnie jest rodzicem
     * @param tag    Tag który obecnie jest dzieckiem
     * @param <T>    Type tagu
     * @throws IllegalArgumentException Jeżeli parent==null lub tag==null
     * @throws IllegalStateException    Jeżeli wskazana relacja nie istnieje
     * @see #setParentOfTag(MasterTag, MasterTag)
     */
    public <T extends Tag<T>> void removeParentOfTag(T parent, T tag) {
        if (parent == null || tag == null) {
            throw new IllegalArgumentException("Tworzenie relacji między null-ami nie ma sensu");
        }
        if (!tag.getParents().contains(parent)) {
            throw new IllegalStateException("Tag " + tag + " nie ma rodzica " + parent);
        }
        tag.removeParent(parent);
        if (parent instanceof MasterTag) {
            notifyMasterTagsTreeModels();
        } else if (parent instanceof UserTag) {
            notifyUserTagsTreeModels();
        }
    }

    /**
     * Dodaje wskazanego rodzica wskazanego tagu użytkownika (tworzy relacje). Jeżeli zostanie rzucony wyjątek,
     * operacja nie spowoduje modyfikacji struktury.
     *
     * @param parent Tag który zostanie rodzicem.
     * @param tag    Tag który zostanie dzieckiem.
     * @throws IllegalArgumentException Jeżeli parent==null lub tag==null
     * @throws IllegalStateException    Jeżeli wskazana relacja już istnieje
     * @throws CycleException           Jeżeli dodanie tej relacji spowoduje powstanie cyklu
     * @see #removeParentOfTag(Tag, Tag)
     */
    public void addParentOfTag(UserTag parent, UserTag tag) throws CycleException {
        if (parent == null || tag == null) {
            throw new IllegalArgumentException("Tworzenie relacji między null-ami nie ma sensu");
        }
        if (tag.getParents().contains(parent)) {
            throw new IllegalStateException("Tag " + tag + " ma już rodzica " + parent);
        }
        try {
            tag.addParent(parent);
            checkCycle();
            notifyUserTagsTreeModels();
        } catch (CycleException e) {
            tag.removeParent(parent);
            throw new CycleException(e);
        } catch (Exception e) {
            throw new IllegalStateException("Wystąpił nieoczekiwany wyjątek: " + e);
        }
    }

    /**
     * Zwraca fragment ścieżki reprezentowany przez wskazany tag macierzysty.
     *
     * @param tag Wybrany tag
     * @return Fragment ścieżki odpowiadający wskazanemu tagowi
     * @throws IllegalArgumentException Jeżeli tag==null
     * @throws IllegalStateException    Jeżeli któryś z rodziców wskazanego tagu nie posiada etykiety
     */
    public Path getPathFromMasterTag(MasterTag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("null nie ma ścieżki");
        }
        List<String> path = new ArrayList<>();
        for (MasterTag i = tag; i != null; i = i.getParent()) {
            if (getNameOfTag(i) == null || getNameOfTag(i).isEmpty()) {
                throw new IllegalStateException("Nie można zbudować ścieżki w systemie plików z obecnego stanu tagów");
            } else {
                path.add(getNameOfTag(i));
            }
        }
        String[] preparedPath = new String[path.size() - 1];
        for (int i = path.size() - 2; i >= 0; --i) {
            preparedPath[path.size() - 2 - i] = path.get(i);
        }
        if (preparedPath.length != 0) {
            return Paths.get(path.get(path.size() - 1), preparedPath);
        } else {
            return Paths.get(path.get(path.size() - 1));
        }
    }

    /**
     * Zwraca model drzewa na potrzeby widoku drzewa tagów macierzystych. Tylko do odczytu. Zmiana struktury tagów
     * macierzystych spowoduje uszkodzenie wszystkich obiektów tej klasy.
     *
     * @return Model drzewa tagów macierzystych
     */
    public TreeModel getModelOfMasterTags() {
        MasterTagsTreeModel masterTagsTreeModel = new MasterTagsTreeModel();
        masterTagsTreeModelList.add(new WeakReference<>(masterTagsTreeModel));
        return masterTagsTreeModel;
    }

    /**
     * Zwraca model drzewa na potrzeby widoku DAG-u tagów użytkownika. Tylko do odczytu. Zmiana struktury tagów
     * użytkownika spowoduje uszkodzenie wszystkich obiektów tej klasy.
     *
     * @return Model drzewa tagów użytkownika
     */
    public TreeModel getModelOfUserTags() {
        UserTagsTreeModel userTagsTreeModel = new UserTagsTreeModel();
        userTagsTreeModelList.add(new WeakReference<>(userTagsTreeModel));
        return userTagsTreeModel;
    }

    /**
     * Zwraca domyślną instancję klasy Tags
     *
     * @return Obiekt klasy Tags
     */
    public static synchronized Tags getDefaultInstance() {
        if (defaultInstance != null) {
            return defaultInstance;
        } else {
            Tags result = new Tags();
            defaultInstance = result;
            return result;
        }
    }

    /**
     * Ustawia domyślną instancję klasy Tags w ramach aplikacji.
     *
     * @param defaultInstance Instancja klasy Tags
     * @throws IllegalArgumentException Jeżeli defaultInstance==null
     */
    public static void setDefaultInstance(Tags defaultInstance) {
        if (defaultInstance == null) {
            throw new IllegalArgumentException("Null jest niedozwolony");
        }
        Tags.defaultInstance = defaultInstance;
    }

    /**
     * Zwraca najstarszego przodka wskazanego tagu macierzystego.
     *
     * @param tag Tag macierzysty
     * @return Najstarszy przodek wskazanego tagu macierzystego
     * @throws IllegalArgumentException Jeżeli tag==null
     */
    public MasterTag getOldestAncestor(MasterTag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Pytanie o przodka null-a nie ma sensu");
        }
        MasterTag result = tag;
        while (result.getParent() != null) {
            result = result.getParent();
        }
        return result;
    }

    private void checkStore() {
        if (store == null) {
            throw new StoreNotAvailableException();
        }
    }

    private void checkCycle() throws CycleException {
        lookForCycleParent();
        lookForCycleChildren();
    }

    private void lookForCycleParent() throws CycleException {
        new CycleParentFinder(tags);
    }

    private void lookForCycleChildren() throws CycleException {
        new CycleChildrenFinder(tags);
    }

    private void removeMasterTagFromStructure(MasterTag tag) {
        if (tag.getParent() != null) {
            tag.removeParent(tag.getParent());
        }
        for (MasterTag child : tag.getChildren()) {
            tag.removeChild(child);
        }
        tags.remove(tag);
        notifyMasterTagsTreeModels();
    }

    private void removeUserTagFromStructure(UserTag tag) {
        for (UserTag parent : tag.getParents()) {
            tag.removeParent(parent);
        }
        for (UserTag child : tag.getChildren()) {
            tag.removeChild(child);
        }
        tags.remove(tag);
        notifyUserTagsTreeModels();
    }

    private void notifyMasterTagsTreeModels() {
        List<WeakReference<MasterTagsTreeModel>> newList = new ArrayList<>();
        for (WeakReference<MasterTagsTreeModel> model : masterTagsTreeModelList) {
            MasterTagsTreeModel realModel = model.get();
            if (realModel != null) {
                newList.add(new WeakReference<>(realModel));
                realModel.changed();
            }
        }
        masterTagsTreeModelList = newList;
    }

    private void notifyUserTagsTreeModels() {
        List<WeakReference<UserTagsTreeModel>> newList = new ArrayList<>();
        for (WeakReference<UserTagsTreeModel> model : userTagsTreeModelList) {
            UserTagsTreeModel realModel = model.get();
            if (realModel != null) {
                newList.add(new WeakReference<>(realModel));
                realModel.changed();
            }
        }
        userTagsTreeModelList = newList;
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        masterTagsTreeModelList = new ArrayList<>();
        userTagsTreeModelList = new ArrayList<>();
    }

    private static class CycleParentFinder {
        private final Map<Tag<?>, Boolean> state = new HashMap<>();

        CycleParentFinder(List<Tag<?>> tags) throws CycleException {
            for (Tag<?> tag : tags) {
                tryTravel(tag);
            }
        }

        private void tryTravel(Tag<?> tag) throws CycleException {
            if (state.containsKey(tag)) {
                if (!state.get(tag)) {
                    throw new CycleException();
                } else {
                    return;
                }
            }
            state.put(tag, false);
            for (Tag<?> parent : tag.getParents()) {
                tryTravel(parent);
            }
            state.put(tag, true);
        }
    }

    private static class CycleChildrenFinder {
        private final Map<Tag<?>, Boolean> state = new HashMap<>();

        CycleChildrenFinder(List<Tag<?>> tags) throws CycleException {
            for (Tag<?> tag : tags) {
                tryTravel(tag);
            }
        }

        private void tryTravel(Tag<?> tag) throws CycleException {
            if (state.containsKey(tag)) {
                if (!state.get(tag)) {
                    throw new CycleException();
                } else {
                    return;
                }
            }
            state.put(tag, false);
            for (Tag<?> parent : tag.getChildren()) {
                tryTravel(parent);
            }
            state.put(tag, true);
        }
    }

    private class UserTagsTreeModel implements TreeModel {
        private List<UserTag> heads = new ArrayList<>(getUserTagHeads());
        private final List<TreeModelListener> listenerList = new ArrayList<>();

        private class Node implements IUserTagNode {
            private final UserTag tag;

            Node(UserTag tag) {
                this.tag = tag;
            }

            Node() {
                tag = null;
            }

            @Override
            public UserTag getTag() {
                return tag;
            }

            Node getChild(int index) {
                if (tag == null) {
                    if (index == 0) {
                        return new Children();
                    } else {
                        return null;
                    }
                } else {
                    if (index == 0) {
                        return new Parents(tag);
                    } else if (index == 1) {
                        return new Children(tag);
                    } else {
                        return null;
                    }
                }
            }

            int getChildCount() {
                if (tag == null) {
                    return 1;
                } else {
                    return 2;
                }
            }

            int getIndexOfChild(Object child) {
                if (tag == null) {
                    if (child instanceof Children) {
                        return 0;
                    } else {
                        return -1;
                    }
                } else {
                    if (child instanceof Parents) {
                        return 0;
                    } else {
                        assert child instanceof Children;
                        return 1;
                    }
                }
            }

            final List<UserTag> getHeads() {
                return heads;
            }

            @Override
            public String toString() {
                return getNameOfTag(tag);
            }
        }

        private class Children extends Node {
            Children(UserTag tag) {
                super(tag);
            }

            Children() {
            }

            @Override
            public String toString() {
                return "dzieci";
            }

            @Override
            Node getChild(int index) {
                if (getTag() == null) {
                    return new Node(heads.get(index));
                } else {
                    return new Node(getTag().getChildren().get(index));
                }
            }

            @Override
            int getIndexOfChild(Object child) {
                if (getTag() != null) {
                    return getTag().getChildren().indexOf(((Node) child).getTag());
                } else {
                    return getHeads().indexOf(((Node) child).getTag());
                }
            }

            @Override
            int getChildCount() {
                if (getTag() != null) {
                    return getTag().getChildren().size();
                } else {
                    return getHeads().size();
                }
            }
        }

        private class Parents extends Node {
            Parents(UserTag tag) {
                super(tag);
                assert tag != null;
            }

            @Override
            public String toString() {
                return "rodzice";
            }

            @Override
            Node getChild(int index) {
                return new Node(getTag().getParents().get(index));
            }

            @Override
            int getIndexOfChild(Object child) {
                if (getTag() != null) {
                    return getTag().getParents().indexOf(((Node) child).getTag());
                } else {
                    return getHeads().indexOf(((Node) child).getTag());
                }
            }

            @Override
            int getChildCount() {
                if (getTag() != null) {
                    return getTag().getParents().size();
                } else {
                    return getHeads().size();
                }
            }
        }

        @Override
        public Object getRoot() {
            return new Children();
        }

        @Override
        public Object getChild(Object parent, int index) {
            return ((Node) parent).getChild(index);
        }

        @Override
        public int getChildCount(Object parent) {
            return ((Node) parent).getChildCount();
        }

        @Override
        public boolean isLeaf(Object node) {
            return false;  // We mean it
        }

        @Override
        public void valueForPathChanged(TreePath path, Object newValue) {
            // UNSUPPORTED
        }

        @Override
        public int getIndexOfChild(Object parent, Object child) {
            return ((Node) parent).getIndexOfChild(child);
        }

        @Override
        public void addTreeModelListener(TreeModelListener l) {
            listenerList.add(l);
        }

        @Override
        public void removeTreeModelListener(TreeModelListener l) {
            listenerList.remove(l);
        }

        void changed() {
            for (TreeModelListener listener : listenerList) {
                heads = new ArrayList<>(getUserTagHeads());
                listener.treeStructureChanged(new TreeModelEvent(this, new Object[]{getRoot()}));
            }
        }
    }

    private class MasterTagsTreeModel implements TreeModel {
        private final Object root = new Object();
        private List<MasterTag> heads = new ArrayList<>(getMasterTagHeads());
        private final List<TreeModelListener> listenerList = new ArrayList<>();

        @Override
        public Object getRoot() {
            return root;
        }

        @Override
        public Object getChild(Object parent, int index) {
            if (parent.equals(root)) {
                return heads.get(index);
            } else {
                return ((MasterTag) parent).getChildren().get(index);
            }
        }

        @Override
        public int getChildCount(Object parent) {
            if (parent.equals(root)) {
                return heads.size();
            } else {
                return ((MasterTag) parent).getChildren().size();
            }
        }

        @Override
        public boolean isLeaf(Object node) {
            return false; // We mean it
        }

        @Override
        public void valueForPathChanged(TreePath path, Object newValue) {
            // DO NOTHING - UNSUPPORTED
        }

        @Override
        public int getIndexOfChild(Object parent, Object child) {
            if (parent == null || child == null) {
                return -1;
            } else {
                if (parent.equals(root)) {
                    //noinspection SuspiciousMethodCalls
                    return heads.indexOf(child);
                } else {
                    //noinspection SuspiciousMethodCalls
                    return ((MasterTag) parent).getChildren().indexOf(child);
                }
            }
        }

        @Override
        public void addTreeModelListener(TreeModelListener l) {
            listenerList.add(l);
        }

        @Override
        public void removeTreeModelListener(TreeModelListener l) {
            listenerList.remove(l);
        }

        void changed() {
            for (TreeModelListener listener : listenerList) {
                heads = new ArrayList<>(getMasterTagHeads());
                listener.treeStructureChanged(new TreeModelEvent(this, new Object[]{getRoot()}));
            }
        }
    }
}
