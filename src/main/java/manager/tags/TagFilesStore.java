package manager.tags;

import manager.files.FileID;

import java.io.Serializable;
import java.util.*;

/**
 * Główny magazyn otagowanych plików. Pozwala na zarządzanie zbiorem danych. Dodawanie i usuwanie tagów i plików.
 * Uzyskiwanie informacji o plikach posiadających dane Tagi oraz o Tagach przypisanych do danego zbioru plików.
 * <p/>
 * Obiekty tej klasy pozwalają na uzyskiwanie informacji o tym jakie tagi posiadają wskazane pliki oraz jakie pliki
 * są otagowane wskazanymi tagami. Obiekty tej klasy zarządzają tą informacją pozwalając na dodawanie i usuwanie
 * tak pojedynczych tagów jak i całych plików. Należy pamiętać, że każdy plik posiada dokładnie jeden tag macierzysty.
 * Usunięcie tagu macierzystego z danego pliku efektywnie spowoduje usunięcie danego pliku z bazy. <B>Usunięcie tagu
 * macierzystego jako takiego spowoduje usunięcie wszystkich plików otagowanych nim oraz jego pochodnymi</B> (usunięcie
 * katalogu "zdjęcia" powoduje usunięcie wszystkich zdjęć wewnątrz tego katalogu, a nie tylko samego katalogu
 * "zdjęcia"). Możliwe jest uzyskanie informacji o plikach otagowanych wskazanym zbiorem tagów. Możliwe jest przy tym
 * uzyskanie wyniku będącego "sumą" wyników dla pojedynczego tagu jak i "przecięciem". Możliwe jest również uzyskanie
 * informacji o tym które pliki są "na prawdę" otagowane wskazanym tagiem (a nie dowolną z jego pochodnych).
 *
 * @author Maciej Poleski
 */
public class TagFilesStore implements Serializable {
    private final Map<Tag<?>, Set<FileID>> filesByTags = new HashMap<>();
    private final Map<FileID, Set<Tag<?>>> tagsByFiles = new HashMap<>();
    private static final long serialVersionUID = 1;

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
     * @throws IllegalStateException    Jeżeli podany plik jest już w bazie
     * @throws IllegalArgumentException Jeżeli fileId lub masterTag jest nullem
     */
    public void addFile(FileID fileId, MasterTag masterTag, Set<UserTag> userTags) {
        if (fileId == null || masterTag == null) {
            throw new IllegalArgumentException("Tworzenie powiązania między null-ami nie ma sensu");
        }
        if (userTags == null) {
            userTags = new HashSet<>();
        }
        if (tagsByFiles.containsKey(fileId)) {
            throw new IllegalStateException("Plik " + fileId + " jest już w bazie");
        }
        addTagInformation(fileId, masterTag);
        for (UserTag tag : userTags) {
            addTagInformation(fileId, tag);
        }
    }

    /**
     * Dodaje plik do bazy danych. Tag macierzysty jest obowiązkowy.
     *
     * @param fileId    Uchwyt do reprezentanta pliku
     * @param masterTag Tag macierzysty związany z plikiem
     * @throws IllegalStateException    Jeżeli podany plik jest już w bazie
     * @throws IllegalArgumentException Jeżeli fileId lub masterTag jest nullem
     */
    public void addFile(FileID fileId, MasterTag masterTag) {
        addFile(fileId, masterTag, null);
    }

    /**
     * Usuwa plik z bazy danych (nie dokonuje fizycznego usunięcia pliku z nośnika).
     *
     * @param fileId Uchwyt do reprezentanta pliku
     * @throws IllegalArgumentException Jeżeli fileId==null
     */
    public void removeFile(FileID fileId) {
        if (fileId == null) {
            throw new IllegalArgumentException("Usuwanie null-a nie ma sensu");
        }
        tagsByFiles.remove(fileId);
        for (Set<FileID> set : filesByTags.values()) {
            set.remove(fileId);
        }
        cleanupTagsByFiles();
    }

    /**
     * Zwraca zbiór plików posiadających jakikolwiek z wymienionych tagów.
     *
     * @param tags Poszukiwane tagi
     * @return Zbiór plików takich że każdy z nich posiada przynajmniej jeden z wymienionych tagów.
     * @throws IllegalArgumentException Jeżeli któryś z tagów jest null-em
     */
    public Set<FileID> getFilesWithOneOf(Set<Tag<?>> tags) {
        Set<FileID> result = new HashSet<>();
        if (tags == null) {
            return result;
        }
        Set<Tag<?>> computedTags = new HashSet<>();
        for (Tag<?> tag : tags) {
            if (tag == null) {
                throw new IllegalArgumentException("Żaden plik na pewno nie jest otagowany null-em");
            }
            computedTags.addAll(tag.getDescendants());
            computedTags.add(tag);
        }
        for (Tag<?> tag : computedTags) {
            if (filesByTags.containsKey(tag)) {
                result.addAll(filesByTags.get(tag));
            }
        }
        return result;
    }

    /**
     * Zwraca zbiór plików posiadających wszystkie wymienione tagi.
     *
     * @param tags Poszukiwane tagi
     * @return Zbiór plików takich że każdy z nich posiada wszystkie wymienione tagi.
     * @throws IllegalArgumentException Jeżeli któryś z tagów jest null-em
     */
    public Set<FileID> getFilesWithAllOf(Set<Tag<?>> tags) {
        Set<FileID> result = new HashSet<>();
        for (Set<FileID> set : filesByTags.values()) {
            result.addAll(set);
        }
        if (tags == null) {
            return result;
        }
        for (Tag<?> tag : tags) {
            if (tag == null) {
                throw new IllegalArgumentException("Żaden plik na pewno nie jest otagowany null-em");
            }
            List<FileID> filesToRemoveFromResult = new ArrayList<>();
            for (FileID file : result) {
                if (!tagsByFiles.containsKey(file)) {
                    filesToRemoveFromResult.add(file);
                }
                Set<Tag<?>> computedTagSet = new HashSet<>();
                computedTagSet.addAll(tag.getDescendants());
                computedTagSet.add(tag);
                computedTagSet.retainAll(tagsByFiles.get(file));
                if (computedTagSet.isEmpty()) {
                    filesToRemoveFromResult.add(file);
                }
            }
            result.removeAll(filesToRemoveFromResult);
        }
        return result;
    }

    /**
     * Zwraca zbiór plików posiadających wskazany tag.
     *
     * @param tag Poszukiwany tag
     * @return Zbiór plików otagowanych wskazanym tagiem
     * @throws IllegalArgumentException Jeżeli tag==null
     */
    public Set<FileID> getFilesWith(Tag<?> tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Nie można tagować null-em, więc w bazie nie może być takich tagów");
        }
        return getFilesWithOneOf(new HashSet<Tag<?>>(Arrays.asList(tag)));
    }

    /**
     * Zwraca wszystkie pliki o wskazanym tagu macierzystym.
     * Ta funkcja jest szczególnie przydatna dla GUI.
     *
     * @param masterTag Tag macierzysty
     * @return Kolekcja plików posiadających podany tag macierzysty
     * @throws IllegalArgumentException Jeżeli masterTag==null
     */
    public Set<FileID> getFilesFrom(MasterTag masterTag) {
        if (masterTag == null) {
            throw new IllegalArgumentException("Nie można tagować null-em, więc w bazie nie może być takich tagów");
        }
        return getFilesWith(masterTag);
    }

    /**
     * Dodaje zbiór plików do wskazanej kolekcji. Innymi słowy przypisuje tag macierzysty do zbioru plików.
     *
     * @param files     Zbiór importowanych plików.
     * @param masterTag Tag macierzysty który otrzymają pliki.
     * @throws IllegalArgumentException Jeżeli masterTag==null lub któryś z plików jest już w bazie
     */
    public void addFiles(Set<FileID> files, MasterTag masterTag) {
        if (files == null) {
            return;
        }
        if (masterTag == null) {
            throw new IllegalArgumentException("Nie można tagować null-em");
        }
        for (FileID file : files) {
            if (isInDatabase(file)) {
                throw new IllegalArgumentException("Plik " + file + " jest już w bazie danych");
            }
            addTagInformation(file, masterTag);
        }
    }

    /**
     * Dodaje zbiór plików do wskazanej kolekcji i przypisuje im wskazane tagi.
     * Innymi słowy przypisuje tag macierzysty do zbioru plików. I taguje je. Jeżeli któryś z plików jest już w bazie
     * danych rzucany jest wyjątek IllegalArgumentException (baza danych nie jest modyfikowana).
     *
     * @param files     Zbiór importowanych plików
     * @param masterTag Tag macierzysty który otrzymają pliki
     * @param userTags  Tagi użytkownika które otrzymają pliki
     * @throws IllegalArgumentException Jeżeli masterTag==null lub któryś z plików jest już w bazie
     */
    public void addFiles(Set<FileID> files, MasterTag masterTag, Set<UserTag> userTags) {
        if (files == null) {
            return;
        }
        if (masterTag == null) {
            throw new IllegalArgumentException("Każdy plik w bazie musi posiadać tag macierzysty (nie null)");
        }
        if (userTags == null) {
            userTags = new HashSet<>();
        }
        for (FileID file : files) {
            if (isInDatabase(file)) {
                throw new IllegalArgumentException("Plik " + file + " jest już w bazie danych");
            }
        }
        for (FileID file : files) {
            addTagInformation(file, masterTag);
            for (UserTag tag : userTags) {
                addTagInformation(file, tag);
            }
        }
    }

    /**
     * Otagowuje wskazany plik wskazanym tagiem użytkownika.
     *
     * @param userTag Tag którym zostanie otagowany plik
     * @param fileID  Plik który zostanie otagowany
     * @throws IllegalArgumentException Jeżeli userTag==null lub fileIF==null
     * @throws IllegalStateException    Jeżeli wskazanego pliku nie ma w bazie
     */
    public void addUserTagToFile(UserTag userTag, FileID fileID) {
        if (userTag == null || fileID == null) {
            throw new IllegalArgumentException("Otagowywanie null-i oraz tagowanie null-ami jest bez sensu");
        }
        getMasterTagFrom(fileID); // Sprawdzam stan
        addTagInformation(fileID, userTag);
    }

    /**
     * Wyciąga wszystkie tagi z podanego zbioru plików.
     *
     * @param files Kolekcja plików
     * @return Zbiór tagów które pojawiły się przynajmniej przy jednym z plików
     * @throws IllegalArgumentException Jeżeli któryś z plików to null
     * @see #getFilesWithOneOf(java.util.Set)
     */
    public Set<Tag<?>> getTagsFrom(Set<FileID> files) {
        Set<Tag<?>> result = new HashSet<>();
        if (files == null) {
            return result;
        }
        for (FileID file : files) {
            if (file == null) {
                throw new IllegalArgumentException("Sprawdzanie jak jest otagowany null nie ma sensu");
            }
            if (tagsByFiles.containsKey(file)) {
                result.addAll(tagsByFiles.get(file));
            }
        }
        return result;
    }

    /**
     * Wyciąga wszystkie tagi z podanego pliku.
     *
     * @param file Plik z którego zostaną wyciągnięte tagi.
     * @return Zbiór tagów przypisanych do danego pliku
     * @throws IllegalArgumentException Jeżeli file==null
     */
    public Set<Tag<?>> getTagsFrom(FileID file) {
        return getTagsFrom(new HashSet<>(Arrays.asList(file)));
    }

    /**
     * Wyciąga tag macierzysty z podanego pliku.
     *
     * @param file Plik z którego zostanie wyciągnięty tag macierzysty.
     * @return Tag macierzysty
     * @throws IllegalArgumentException Jeżeli file==null
     * @throws IllegalStateException    Jeżeli plik nie posiada przypisanego tagu macierzystego
     */
    public MasterTag getMasterTagFrom(FileID file) {
        if (file == null) {
            throw new IllegalArgumentException("null nie jest otagowany");
        }
        Set<Tag<?>> partialResult = getTagsFrom(file);
        for (Tag<?> tag : partialResult) {
            if (tag instanceof MasterTag) {
                return (MasterTag) tag;
            }
        }
        throw new IllegalStateException("Plik " + file + " nie posiada żadnego tagu macierzystego");
    }

    /**
     * Usuwa wskazany tag ze wskazanego pliku.
     *
     * @param file Plik z którego zostanie usunięty tag
     * @param tag  Tag który zostanie usunięty z pliku
     * @throws IllegalArgumentException Jeżeli file==null lub tag==null
     * @throws IllegalStateException    Jeżeli w bazie nie ma pliku file lub nie jest on otagowany tagiem tag
     */
    public void removeFileTag(FileID file, UserTag tag) {
        if (file == null) {
            throw new IllegalArgumentException("W bazie na pewno nie ma null-a, usuwanie jest bez sensu");
        }
        if (tag == null) {
            throw new IllegalArgumentException("Tagowanie null-ami nie ma sensu");
        }
        if (isNotInDatabase(file, tag)) {
            throw new IllegalStateException("Plik " + file + " nie posiada tagu " + tag);
        }
        removeTagInformation(file, tag);
    }

    /**
     * Usuwa wskazany zbiór tagów ze wskazanego pliku.
     *
     * @param file Plik z którego zostaną usunięte tagi.
     * @param tags Tagi które będą usunięte ze wskazanego pliku
     * @throws IllegalArgumentException Jeżeli file==null
     * @throws IllegalStateException    Jeżeli w bazie nie ma pliku file lub nie jest on otagowany wskazanymi tagami
     */
    public void removeFileTags(FileID file, Set<UserTag> tags) {
        if (file == null) {
            throw new IllegalArgumentException("W bazie na pewno nie ma null-a, usuwanie jest bez sensu");
        }
        if (tags == null) {
            return;
        }
        for (UserTag tag : tags) {
            if (isNotInDatabase(file, tag)) {
                throw new IllegalStateException("Plik " + file + " nie posiada tagu " + tag);
            }
        }
        try {
            for (UserTag tag : tags) {
                removeTagInformation(file, tag);
            }
        } catch (Exception t) {
            throw new IllegalStateException("Nastąpił nieoczekiwany wyjątek", t);
        }
    }

    /**
     * Usuwa kolekcję plików wskazaną tagiem macierzystym. TA FUNKCJA JEST BARDZO NIEBEZPIECZNA. Upewnij się że jej
     * użycie jest dokładnie tym co chcesz zrobić, ponieważ jej uruchomienie spowoduje usunięcie z bazy ogromnej
     * ilości plików.
     *
     * @param tag Wszystkie pliki oznaczone tym tagiem macierzystym (lub pochodnym) zostaną usunięte z bazy
     * @return Zbiór plików usuniętych z bazy
     * @throws IllegalArgumentException Jeżeli tag==null
     */
    public Set<FileID> removeFamily(MasterTag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tagowanie null-ami nie ma sensu");
        }
        Set<FileID> filesToRemove = pretendRemoveFamily(tag);
        for (FileID file : filesToRemove) {
            removeFile(file);
        }
        return filesToRemove;
    }

    /**
     * Symuluje operację usunięcia plików oznaczonych wskazanym tagiem macierzystym z bazy
     *
     * @param tag Wybrany tag macierzysty
     * @return Zbiór plików które zostałyby usunięte z bazy, gdyby został usunięty wskazany tag
     */
    public Set<FileID> pretendRemoveFamily(MasterTag tag) {
        return getFilesFrom(tag);
    }

    /**
     * Zwraca zbiór plików otagowanych wskazanym tagiem. Plik uznajemy za otagowany danym tagiem jeżeli użytkownik
     * oznaczył ten plik danym tagiem osobiście (nie działają reguły dziedziczenia tagów).
     *
     * @param tag Wskazany tag
     * @return Zbiór plików posiadających przypisany wskazany tag bezpośrednio (nie dziedzicząc)
     * @throws IllegalArgumentException Jeżeli tag==null
     */
    public Set<FileID> getFilesWithRealTag(Tag<?> tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tagowanie null-ami nie ma sensu");
        }
        Set<FileID> result = new HashSet<>();
        if (!filesByTags.containsKey(tag)) {
            return result;
        }
        result.addAll(filesByTags.get(tag));
        return result;
    }

    private void addTagInformation(FileID file, Tag<?> tag) {
        if (!filesByTags.containsKey(tag)) {
            filesByTags.put(tag, new HashSet<FileID>());
        }
        filesByTags.get(tag).add(file);

        if (!tagsByFiles.containsKey(file)) {
            tagsByFiles.put(file, new HashSet<Tag<?>>());
        }
        tagsByFiles.get(file).add(tag);
    }

    private void removeTagInformation(FileID file, UserTag tag) {
        if (file == null || tag == null) {
            throw new IllegalArgumentException();
        }
        if (!tagsByFiles.containsKey(file) || !tagsByFiles.get(file).contains(tag)) {
            return;
        }
        tagsByFiles.get(file).remove(tag);
        if (tagsByFiles.get(file).isEmpty()) {
            tagsByFiles.remove(file);
        }
        filesByTags.get(tag).remove(file);
        if (filesByTags.get(tag).isEmpty()) {
            filesByTags.remove(tag);
        }
    }

    private void cleanupTagsByFiles() {
        for (Iterator<Set<Tag<?>>> i = tagsByFiles.values().iterator(); i.hasNext(); ) {
            if (i.next().isEmpty()) {
                i.remove();
            }
        }
    }

    private boolean isInDatabase(FileID file) {
        return tagsByFiles.containsKey(file);
    }

    private boolean isNotInDatabase(FileID file, Tag<?> tag) {
        if (file == null || tag == null) {
            throw new IllegalArgumentException();
        }
        return !tagsByFiles.containsKey(file) || !tagsByFiles.get(file).contains(tag);
    }
}
