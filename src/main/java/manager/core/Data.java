package manager.core;

import manager.files.backup.BackupsManager;
import manager.tags.TagFilesStore;
import manager.tags.Tags;

import java.io.*;
import java.util.logging.Logger;

/**
 * Klasa przechowująca wszelkie informacje potrzebne do pracy aplikacji.
 *
 * @author Maciej Poleski
 */
public final class Data {
    private final Tags tags;
    private final TagFilesStore tagFilesStore;
    private final BackupsManager backupsManager;

    private static final File DATABASE_FILE = new File("database");
    private static boolean loaded = false;

    private Data(Tags tags, TagFilesStore tagFilesStore, BackupsManager backupsManager) {
        this.tags = tags;
        this.tagFilesStore = tagFilesStore;
        this.backupsManager = backupsManager;
    }

    /**
     * Ładuje bazę danych aplikacji i zwraca ją. Tą funkcje wolno wywołać tylko jeden raz.
     *
     * @return Baza danych gotowa do użycia
     * @throws IOException           Jeżeli wystąpi błąd IO
     * @throws IllegalStateException Jeżeli to nie jest pierwsze wywołanie
     */
    public static Data load() throws IOException {
        if (loaded) {
            throw new IllegalStateException("Tą funkcje wolno uruchomić tylko raz");
        }
        loaded = true;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(DATABASE_FILE));
            TagFilesStore tagFilesStore1 = (TagFilesStore) objectInputStream.readObject();
            Tags tags = (Tags) objectInputStream.readObject();
            BackupsManager backupsManager = (BackupsManager) objectInputStream.readObject();
            objectInputStream.close();
            return new Data(tags, tagFilesStore1, backupsManager);
        } catch (FileNotFoundException e) {
            Tags tags = new Tags();
            BackupsManager backupsManager = new BackupsManager(tags);
            return new Data(tags, tags.getStore(), backupsManager);
        } catch (ClassNotFoundException e) {
            Logger.getLogger("Data").throwing("core.Data", "load", e);
            return null;
        }
    }

    /**
     * Zapisuje bazę danych.
     *
     * @throws IOException Jeżeli zapis się nie powiedzie z powodu błędu IO
     */
    public void save() throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(DATABASE_FILE));
        objectOutputStream.writeObject(getTagFilesStore());
        objectOutputStream.writeObject(getTags());
        objectOutputStream.writeObject(getBackupsManager());
        objectOutputStream.close();
    }

    static boolean isLoaded() {
        return loaded;
    }

    static void setLoaded(boolean loaded) {
        Data.loaded = loaded;
    }

    /**
     * Zwraca obiekt służący do zarządzania strukturą tagów.
     *
     * @return Obiekt służący do zarządzania strukturą tagów.
     */
    public Tags getTags() {
        return tags;
    }

    /**
     * Zwraca obiekt służący do zarządzania otagowanymi plikami.
     *
     * @return Obiekt służący do zarządzania otagowanymi plikami.
     */
    public TagFilesStore getTagFilesStore() {
        return tagFilesStore;
    }

    /**
     * Zwraca obiekt służący do zarządzania kopiami zapasowymi
     *
     * @return Obiekt służący do zarządzania kopiami zapasowymi
     */
    public BackupsManager getBackupsManager() {
        return backupsManager;
    }
}
