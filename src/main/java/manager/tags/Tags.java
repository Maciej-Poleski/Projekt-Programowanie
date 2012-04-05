package manager.tags;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Klasa zarządzająca całym zbiorem tagów wykorzystywanym w aplikacji. Pozwala na dostęp do "głów".
 * Aby uzyskać pełną funkcjonalność należy podać jej referencję do bazy danych.
 *
 * @author Maciej Poleski
 */
public class Tags implements Serializable {
    private List<Tag> tags;
    private TagFilesStore store;

    Tags() {
    }

    /**
     * Tworzy nowy tag macierzysty.
     *
     * @return Nowy tag macierzysty
     */
    public MainTag newMainTag() {
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
    public Collection<MainTag> getMainTagHeads() {
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
}
