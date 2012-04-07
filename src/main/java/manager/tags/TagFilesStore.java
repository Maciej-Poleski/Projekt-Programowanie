package manager.tags;

import manager.files.FileID;

import java.io.Serializable;
import java.util.*;

/**
 * Główny magazyn otagowanych plików. Pozwala na zarządzanie zbiorem danych. Dodawanie i usuwanie tagów i plików.
 * Uzyskiwanie informacji o plikach posiadających dane Tagi oraz o Tagach przypisanych do danego zbioru plików.
 *
 * @author Maciej Poleski
 */
public class TagFilesStore implements Serializable {
    private final Map<Tag<?>, Set<FileID>> filesByTags = new HashMap<Tag<?>, Set<FileID>>();
    private final Map<FileID, Set<Tag<?>>> tagsByFiles = new HashMap<FileID, Set<Tag<?>>>();

    /**
     * Konstruuje nowy magazyn plików otagowanych.
     */
    public TagFilesStore() {
    }

    /**
     * Dodaje plik do bazy danych. Tag macierzysty jest obowiązkowy. Tagi użytkownika mogą być pominięte.
     *
     * @param fileId    Uchwyt do reprezentanta pliku
     * @param masterTag Tag macierzysty związany z plikiem
     * @param userTags  Opcjonalne tagi użytkownika
     */
    public void addFile(FileID fileId, MasterTag masterTag, Set<UserTag> userTags) {
        if (userTags == null)
            userTags = new HashSet<UserTag>();
        addTagInformation(fileId, masterTag);
        for (UserTag tag : userTags)
            addTagInformation(fileId, tag);
    }

    /**
     * Usuwa plik z bazy danych (nie dokonuje fizycznego usunięcia pliku z nośnika).
     *
     * @param fileId Uchwyt do reprezentanta pliku
     */
    public void removeFile(FileID fileId) {
        tagsByFiles.remove(fileId);
        for (Set<FileID> set : filesByTags.values()) {
            set.remove(fileId);
        }
        cleanupTagsByFiles();
    }

    /**
     * Zwraca kolekcję plików posiadających jakikolwiek z wymienionych tagów.
     *
     * @param tags Poszukiwane tagi
     * @return Kolekcja plików takich że każdy z nich posiada przynajmniej jeden z wymienionych tagów.
     */
    public Set<FileID> getFilesWithOneOf(Set<Tag<?>> tags) {
        Set<FileID> result = new HashSet<FileID>();
        if (tags == null)
            return result;
        for (Tag<?> tag : tags) {
            if (filesByTags.containsKey(tag))
                result.addAll(filesByTags.get(tag));
        }
        return result;
    }

    /**
     * Zwraca kolekcję plików posiadających wszystkie wymienione tagi.
     *
     * @param tags Poszukiwane tagi
     * @return Kolekcja plików takich że każdy z nich posiada wszystkie wymienione tagi.
     */
    public Set<FileID> getFilesWithAllOf(Set<Tag<?>> tags) {
        Set<FileID> result = new HashSet<FileID>();
        for (Set<FileID> set : filesByTags.values())
            result.addAll(set);
        if (tags == null)
            return result;
        for (Tag<?> tag : tags) {
            List<FileID> filesToRemoveFromResult = new ArrayList<FileID>();
            for (FileID file : result) {
                if (!tagsByFiles.containsKey(file) || !tagsByFiles.get(file).contains(tag))
                    filesToRemoveFromResult.add(file);
            }
            result.removeAll(filesToRemoveFromResult);
        }
        return result;
    }

    /**
     * Zwraca wszystkie pliki o wskazanym tagu macierzystym.
     * Ta funkcja jest szczególnie przydatna dla GUI.
     *
     * @param masterTag Tag macierzysty
     * @return Kolekcja plików posiadających podany tag macierzysty
     */
    public Set<FileID> getFilesFrom(MasterTag masterTag) {
        Set<FileID> result = new HashSet<FileID>();
        if (masterTag == null || !filesByTags.containsKey(masterTag))
            return result;
        result.addAll(filesByTags.get(masterTag));
        return result;
    }

    /**
     * Dodaje zbiór plików do wskazanej kolekcji. Innymi słowy przypisuje tag macierzysty do zbioru plików.
     *
     * @param files     Zbiór importowanych plików.
     * @param masterTag Tag macierzysty który otrzymają pliki.
     * @throws NullPointerException Jeżeli masterTag==null
     */
    public void addFilesTo(Set<FileID> files, MasterTag masterTag) {
        if (files == null)
            return;
        if (masterTag == null)
            throw new NullPointerException();
        for (FileID file : files)
            addTagInformation(file, masterTag);
    }

    /**
     * Dodaje zbiór plików do wskazanej kolekcji i przypisuje im wskazane tagi.
     * Innymi słowy przypisuje tag macierzysty do zbioru plików. I taguje je.
     *
     * @param files     Zbiór importowanych plików
     * @param masterTag Tag macierzysty który otrzymają pliki
     * @param userTags  Tagi użytkownika które otrzymają pliki
     * @throws NullPointerException Jeżeli masterTag==null
     */
    public void addFiles(Set<FileID> files, MasterTag masterTag, Set<UserTag> userTags) {
        if (files == null)
            return;
        if (masterTag == null)
            throw new NullPointerException();
        if (userTags == null)
            userTags = new HashSet<UserTag>();
        for (FileID file : files) {
            addTagInformation(file, masterTag);
            for (UserTag tag : userTags)
                addTagInformation(file, tag);
        }
    }

    /**
     * Wyciąga wszystkie tagi z podanego zbioru plików. Operacja symetryczna do getFilesWithOneOf.
     *
     * @param files Kolekcja plików
     * @return Zbiór tagów które pojawiły się przynajmniej przy jednym z plików
     * @see #getFilesWithOneOf(java.util.Set)
     */
    public Set<Tag<?>> getTagsFrom(Set<FileID> files) {
        Set<Tag<?>> result = new HashSet<Tag<?>>();
        if (files == null)
            return result;
        for (FileID file : files)
            if (tagsByFiles.containsKey(file))
                result.addAll(tagsByFiles.get(file));
        return result;
    }

    private void addTagInformation(FileID file, Tag<?> tag) {
        if (!filesByTags.containsKey(tag))
            filesByTags.put(tag, new HashSet<FileID>());
        filesByTags.get(tag).add(file);

        if (!tagsByFiles.containsKey(file))
            tagsByFiles.put(file, new HashSet<Tag<?>>());
        tagsByFiles.get(file).add(tag);
    }

    private void cleanupTagsByFiles() {
        for (Iterator<Set<Tag<?>>> i = tagsByFiles.values().iterator(); i.hasNext(); ) {
            if (i.next().isEmpty())
                i.remove();
        }
    }
}
