package manager.core;

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

    private static final File DATABASE_FILE = new File("database");
    private static boolean loaded = false;

    private Data(Tags tags, TagFilesStore tagFilesStore) {
        this.tags = tags;
        this.tagFilesStore = tagFilesStore;
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
            Tags.setDefaultInstance(tags);
            return new Data(tags, tagFilesStore1);
        } catch (FileNotFoundException e) {
            TagFilesStore tagFilesStore1 = new TagFilesStore();
            Tags tags = new Tags();
            tags.setStore(tagFilesStore1);
            Tags.setDefaultInstance(tags);
            return new Data(tags, tagFilesStore1);
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
    }

    /**
     * Obiekt służący do zarządzania strukturą tagów.
     */
    public Tags getTags() {
        return tags;
    }

    /**
     * Obiekt służący do zarządzania otagowanymi plikami.
     */
    public TagFilesStore getTagFilesStore() {
        return tagFilesStore;
    }

    static boolean isLoaded() {
        return loaded;
    }

    static void setLoaded(boolean loaded) {
        Data.loaded = loaded;
    }
}
