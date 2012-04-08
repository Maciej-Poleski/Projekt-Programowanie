package manager.tags;

import manager.files.FileID;

import java.io.Serializable;
import java.util.*;

/**
 * Klasa zarządzająca całym zbiorem tagów wykorzystywanym w aplikacji. Pozwala na dostęp do "głów".
 * Pozwala na dostęp do etykiety tagów. Pozwala na dodawanie dowolnych metadanych do tagów.
 * Aby uzyskać pełną funkcjonalność należy podać jej referencję do bazy danych.
 *
 * @author Maciej Poleski
 */
public class Tags implements Serializable {
    private final List<Tag<?>> tags = new ArrayList<>();
    private TagFilesStore store = null;
    private final Map<Tag<?>, String> tagNames = new HashMap<>();
    private final Map<Tag<?>, Set<Object>> tagMetadata = new HashMap<>();
    private static final long serialVersionUID = 1;

    /**
     * Konstruuje nowy obiekt z pustą rodziną tagów.
     */
    public Tags() {
    }

    /**
     * Tworzy nowy tag macierzysty.
     *
     * @return Nowy tag macierzysty
     */
    public MasterTag newMasterTag() {
        MasterTag tag = new MasterTag();
        tags.add(tag);
        return tag;
    }

    /**
     * Tworzy nowy tag użytkownika
     *
     * @return Nowy tag użytkownika
     */
    public UserTag newUserTag() {
        UserTag tag = new UserTag();
        tags.add(tag);
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
    public Set<MasterTag> getMainTagHeads() {
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
     * Usuwa wskazany tag macierzysty usuwając jednocześnie z bazy danych wszystkie pliki otagowane nim, lub jego
     * pochodną. Tag jest również usuwany ze struktury co może oznaczać rozspójnienie i powstanie nowych "głów".
     * Nie nadużywaj tej funkcji. Przed użyciem tej metody należy ustawić bazę plików.
     *
     * @param tag Tag który ma zostać usunięty.
     * @throws StoreNotAvailableException Jeżeli nie ustawiono bazy plików
     * @see #setStore(TagFilesStore)
     */
    public void removeTag(MasterTag tag) {
        checkStore();
        store.removeFamily(tag);
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
        return tag;
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
        UserTag result = new UserTag();
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
        return result;
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
    }

    /**
     * Zwraca wszystkie metadane związane z wskazanym tagiem.
     *
     * @param tag Tag którego metadane zostaną zwrócone
     * @return Wszystkie metadane związane z danym tagiem
     */
    public Set<?> getAllMetadataOfTag(Tag<?> tag) {
        return tagMetadata.get(tag);
    }

    /**
     * Dodaje metadane do tagu. Ta operacja jest opcjonalna co oznacza że metadane zostaną dodane pod warunkiem że nie
     * zostały dodane wcześniej.
     *
     * @param tag      Tag do którego dodajemy metadane
     * @param metadata Metadane które zostaną powiązane z wskazanym tagiem.
     */
    public void addTagMetadata(Tag<?> tag, Object metadata) {
        if (!tagMetadata.containsKey(tag)) {
            tagMetadata.put(tag, new HashSet<>());
        }
        tagMetadata.get(tag).add(metadata);
    }

    /**
     * Usuwa metadane z tagu. Ta operacja usuwa powiązanie wskazanego tagu z wskazanymi metadanymi.
     *
     * @param tag      Tag z którego będą usunięte metadane
     * @param metadata Metadane które zostaną usunięte z tagu.
     */
    public void removeTagMetadata(Tag<?> tag, Object metadata) {
        if (!tagMetadata.containsKey(tag)) {
            return;
        }
        tagMetadata.get(tag).remove(metadata);
        if (tagMetadata.get(tag).isEmpty()) {
            tagMetadata.remove(tag);
        }
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
    }

    private void removeUserTagFromStructure(UserTag tag) {
        for (UserTag parent : tag.getParents()) {
            tag.removeParent(parent);
        }
        for (UserTag child : tag.getChildren()) {
            tag.removeChild(child);
        }
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
}
