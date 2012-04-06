package manager.tags;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Klasa zarządzająca całym zbiorem tagów wykorzystywanym w aplikacji. Pozwala na dostęp do "głów".
 * Pozwala na dostęp do etykiety tagów. Pozwala na dodawanie dowolnych metadanych do tagów.
 * Aby uzyskać pełną funkcjonalność należy podać jej referencję do bazy danych.
 *
 * @author Maciej Poleski
 */
public class Tags implements Serializable {
    private List<? extends Tag<?>> tags;
    private TagFilesStore store;
    private Map<? extends Tag<?>, String> tagNames;
    private Map<? extends Tag<?>, ? extends Collection<?>> tagMatadata;

    Tags() {
    }

    /**
     * Tworzy nowy tag macierzysty.
     *
     * @return Nowy tag macierzysty
     */
    public MasterTag newMainTag() {
        return null;
    }

    /**
     * Tworzy nowy tag użytkownika
     *
     * @return Nowy tag użytkownika
     */
    public UserTag newUserTag() {
        return null;
    }

    /**
     * Zwraca wszystkie tagi które nie mają przodków.
     *
     * @return Kolekcja tagów które nie mają przodków
     */
    public Collection<Tag<?>> getHeads() {
        return null;
    }

    /**
     * Zwraca wszystkie tagi macierzyste które nie mają przodków.
     *
     * @return Kolekcja tagów macierzystych które nie mają przodków
     */
    public Collection<MasterTag> getMainTagHeads() {
        return null;
    }

    /**
     * Zwraca wszystkie tagi użytkownika które nie mają przodków.
     *
     * @return Kolekcja tagów użytkownika które nie mają przodków
     */
    public Collection<UserTag> getUserTagHeads() {
        return null;
    }

    /**
     * Usuwa wskazany tag. Wszystkie pliki oznaczone nim tracą to oznaczenie. Tag jest również usuwany z struktury co
     * może oznaczać rozspójnienie i powstanie nowych "głów". Korzystaj mądrze.
     *
     * @param tag Tag który ma zostać usunięty.
     */
    public void removeTag(Tag tag) {
    }

    /**
     * Ustawia główny magazyn tagów. Niektóre metody wymagają dostępu do głównego magazynu danych.
     * Ta funkcja pozwala na jego ustawienie.
     *
     * @param store Główny magazyn danych
     */
    public void setStore(TagFilesStore store) {
    }

    /**
     * Zwraca referencję do głównego magazynu danych. Uzasadnione jest korzystanie z tej funkcji z każdego modułu.
     *
     * @return Główny magazyn danych
     */
    public TagFilesStore getStore() {
        return null;
    }

    /**
     * Tworzy nowy tag macierzysty jako dziecko wskazanego tagu macierzystego.
     *
     * @param parent Tag macierzysty
     * @return Nowy tag macierzysty będący dzieckiem wskazanego tagu macierzystego
     */
    public MasterTag newMainTag(MasterTag parent) {
        return null;
    }

    /**
     * Tworzy nowy tag macierzysty jako dziecko wskazanego tagu macierzystego. I przypisuje mu etykietę.
     *
     * @param parent Tag macierzysty
     * @param name   Etykieta nowego tagu macierzystego
     * @return Nowy tag macierzysty o wskazanej etykiecie będący dzieckiem wskazanego tagu macierzystego
     */
    public MasterTag newMainTag(MasterTag parent, String name) {
        return null;
    }

    /**
     * Tworzy nowy tag użytkownika jako dziecko wskazanych tagów użytkownika i rodzic wskazanych tagów użytkownika.
     *
     * @param parents  Tagi użytkownika - rodzice
     * @param children Tagi użytkownika - dzieci
     * @return Nowy tag użytkownika będący dzieckiem wskazanych tagów użytkownika i rodzicem wskazanych tagów użytkownika.
     * @throws CycleException Jeżeli dodanie wskazanych relacji spowoduje powstanie cyklu
     */
    public UserTag newUserTag(Collection<UserTag> parents, Collection<UserTag> children) throws CycleException {
        return null;
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
    public UserTag newUserTag(Collection<UserTag> parents, Collection<UserTag> children, String name) throws CycleException {
        return null;
    }

    /**
     * Zwraca etykietę tagu (napis)
     *
     * @param tag Tag którego etykieta zostanie zwrócona.
     * @return Etykieta wskazanego tagu
     */
    public String getNameOfTag(Tag<?> tag) {
        return null;
    }

    /**
     * Ustawia etykietę podanego tagu. Poprzednia etykieta jest zapominana.
     *
     * @param tag     Tag którego etykieta zostanie ustawiona
     * @param newName Etykieta
     */
    public void setNameOfTag(Tag<?> tag, String newName) {
    }

    /**
     * Zwraca wszystkie metadane związane z wskazanym tagiem.
     *
     * @param tag Tag którego metadane zostaną zwrócone
     * @return Wszystkie metadane związane z danym tagiem
     */
    public Collection<?> getAllMetadataOfTag(Tag<?> tag) {
        return null;
    }

    /**
     * Dodaje metadane do tagu. Ta operacja jest opcjonalna co oznacza że metadane zostaną dodane pod warunkiem że nie
     * zostały dodane wcześniej.
     *
     * @param tag      Tag do którego dodajemy metadane
     * @param metadata Metadane które zostaną powiązane z wskazanym tagiem.
     */
    public void addTagMetadata(Tag<?> tag, Object metadata) {
    }

    /**
     * Usuwa metadane z tagu. Ta operacja usuwa powiązanie wskazanego tagu z wskazanymi metadanymi.
     *
     * @param tag      Tag z którego będą usunięte metadane
     * @param metadata Metadane które zostaną usunięte z tagu.
     */
    public void removeTagMetadata(Tag<?> tag, Object metadata) {
    }
}
