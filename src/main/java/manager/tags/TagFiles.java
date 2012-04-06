package manager.tags;

import manager.files.FileID;

import java.io.Serializable;
import java.util.Collection;

/**
 * Struktura przechowująca zbiór plików posiadających dany tag.
 *
 * @param <T> Typ tagu obsługiwanego przez instancję tej klasy
 * @author Maciej Poleski
 * @deprecated Ta klasa jest już nie potrzebna.
 */
class TagFiles<T extends Tag<T>> implements Serializable {
    private Tag<T> tag;
    private Collection<FileID> files;

    public TagFiles() {
    }

    /**
     * Zwraca tag który posiadają pliki zarządzane przez tą strukturę.
     *
     * @return Tag który posiadają pliki zarządzane przez tą strukturę.
     */
    public Tag<T> getTag() {
        return null;
    }

    /**
     * Ustawia tag który posiadają pliki zarządzane przez tą strukturę.
     *
     * @param tag Tag który posiadają pliki zarządzane przez tą strukturę
     */
    public void setTag(Tag<T> tag) {
    }

    /**
     * Dodaje plik do zbioru plików posiadających ten tag.
     *
     * @param file Plik który zostanie oznaczony tym tagiem
     */
    public void addFile(FileID file) {
    }

    /**
     * Usuwa plik ze zbioru plików posiadających ten tag.
     *
     * @param file Plik który zostanie usunięty
     */
    public void removeFile(FileID file) {
    }

    /**
     * Zwraca kolekcję wszystkich plików posiadających ten tag
     *
     * @return Kolekcja wszystkich plików posiadających ten tag
     */
    public Collection<FileID> getFiles() {
        return null;
    }
}
