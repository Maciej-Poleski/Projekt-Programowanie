package manager.core;

import manager.files.backup.BackupsManager;
import manager.tags.TagFilesStore;
import manager.tags.Tags;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.logging.Logger;

/**
 * Klasa przechowująca wszelkie informacje potrzebne do pracy aplikacji. Umożliwia transakcyjne podejście do zapisu
 * stanu aplikacji. Podczas wczytywania bazy zakładana jest blokada, która jest zwalniana podczas zapisu tak aby
 * tylko jedna instancja aplikacji mogła modyfikować bazę. Możliwe jest też usunięcie blokady w sytuacji gdy
 * poprzednie uruchomienie aplikacji zakończyło się niepowodzeniem.
 *
 * @author Maciej Poleski
 */
public final class Data {
    private final Tags tags;
    private final TagFilesStore tagFilesStore;
    private final BackupsManager backupsManager;

    private static final File DATABASE_FILE = new File("database");
    private static final File DATABASE_LOCK = new File(".database_lock");
    private boolean destroyed = false;

    private Data(Tags tags, TagFilesStore tagFilesStore, BackupsManager backupsManager) {
        this.tags = tags;
        this.tagFilesStore = tagFilesStore;
        this.backupsManager = backupsManager;
    }

    /**
     * Ładuje bazę danych aplikacji i zwraca ją. Tą funkcje wolno wywołać tylko jeden raz.
     *
     * @return Baza danych gotowa do użycia
     * @throws IOException Jeżeli wystąpi błąd IO
     */
    private static Data load() throws IOException {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(DATABASE_FILE));
            TagFilesStore tagFilesStore1 = (TagFilesStore) objectInputStream.readObject();
            Tags tags = (Tags) objectInputStream.readObject();
            BackupsManager backupsManager = (BackupsManager) objectInputStream.readObject();
            objectInputStream.close();
            return new Data(tags, tagFilesStore1, backupsManager);
        } catch (FileNotFoundException e) {
            return reset();
        } catch (ClassNotFoundException e) {
            Logger.getLogger("Data").throwing("core.Data", "load", e);
            return null;
        }
    }

    /**
     * Ładuje bazę danych i zwraca ją. Ta funkcja w pierwszej kolejności zakłada blokadę tak aby wyeliminować
     * możliwość równoczesnej pracy na bazie dwóch instancji aplikacji.
     *
     * @return Baza danych gotowa do użycia
     * @throws IOException           Jeżeli wystąpi błąd IO
     * @throws IllegalStateException Jeżeli baza danych jest zablokowana
     */
    public static Data lockAndLoad() throws IOException {
        if (!DATABASE_LOCK.createNewFile()) {
            throw new IllegalStateException("Baza danych jest zablokowana");
        }
        return load();
    }

    /**
     * Tworzy nową czystą bazę danych aplikacji i zwraca ją. Wywołanie tej funkcji uniemożliwia dalsze wywołania do
     * Data.load(). Tworzy blokadę.
     *
     * @return Baza danych gotowa do użycia.
     * @throws IOException Jeżeli wystąpi błąd IO
     */
    public static Data reset() throws IOException {
        DATABASE_LOCK.createNewFile();
        Tags tags = new Tags();
        BackupsManager backupsManager = new BackupsManager(tags);
        return new Data(tags, tags.getStore(), backupsManager);
    }

    /**
     * Usuwa blokadę z bazy danych. Może być przydatne jeżeli aplikacja nie została poprawnie zamknięta.
     * Uruchamiać tylko na wyraźne życzenie użytkownika.
     *
     * @throws IOException Jeżeli wystąpi błąd IO
     */
    public static void breakLock() throws IOException {
        try {
            Files.delete(DATABASE_LOCK.toPath());
        } catch (NoSuchFileException ignored) {
        }
    }

    /**
     * Zapisuje bazę danych.
     *
     * @throws IOException           Jeżeli zapis się nie powiedzie z powodu błędu IO
     * @throws IllegalStateException Jeżeli baza danych została zniszczona
     */
    public void save() throws IOException {
        checkDestroyed();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(DATABASE_FILE));
        objectOutputStream.writeObject(getTagFilesStore());
        objectOutputStream.writeObject(getTags());
        objectOutputStream.writeObject(getBackupsManager());
        objectOutputStream.close();
    }

    /**
     * Zapisuje bazę danych i zwalnia blokadę. Po wykonaniu tej metody nie można korzystać z tego obiektu.
     *
     * @throws IOException Jeżeli wystąpi błąd IO
     */
    public void saveAndRelease() throws IOException {
        save();
        breakLock();
        destroyed = true;
    }

    /**
     * Sprawdza czy baza danych jest zablokowana.
     *
     * @return True jeżeli jest zablokowana, false jeżeli nie
     * @throws IOException Jeżeli wystąpi błąd IO
     */
    static boolean isLocked() throws IOException {
        return DATABASE_LOCK.exists();
    }

    /**
     * Zwraca obiekt służący do zarządzania strukturą tagów.
     *
     * @return Obiekt służący do zarządzania strukturą tagów.
     * @throws IllegalStateException Jeżeli baza danych została zniszczona
     */
    public Tags getTags() {
        checkDestroyed();
        return tags;
    }

    /**
     * Zwraca obiekt służący do zarządzania otagowanymi plikami.
     *
     * @return Obiekt służący do zarządzania otagowanymi plikami.
     * @throws IllegalStateException Jeżeli baza danych została zniszczona
     */
    public TagFilesStore getTagFilesStore() {
        checkDestroyed();
        return tagFilesStore;
    }

    /**
     * Zwraca obiekt służący do zarządzania kopiami zapasowymi
     *
     * @return Obiekt służący do zarządzania kopiami zapasowymi
     * @throws IllegalStateException Jeżeli baza danych została zniszczona
     */
    public BackupsManager getBackupsManager() {
        checkDestroyed();
        return backupsManager;
    }

    private void checkDestroyed() {
        if (destroyed) {
            throw new IllegalStateException("Ta baza została zniszczona na życzenie metodą z rodziny \"release\"");
        }
    }
}
