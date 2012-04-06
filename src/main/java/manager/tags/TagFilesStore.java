package manager.tags;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import manager.files.FileID;

/**
 * Główny magazyn otagowanych plików. Pozwala na zarządzanie zbiorem danych.
 * Dodawanie i usuwanie tagów i plików. Uzyskiwanie informacji o plikach
 * posiadających dane Tagi oraz o Tagach przypisanych do danego zbioru plików.
 * 
 * @author Maciej Poleski
 */
public class TagFilesStore implements Serializable {
	private Map<Tag<?>, TagFiles<?>> mainBijection;

	public TagFilesStore() {
	}

	/**
	 * Dodaje plik do bazy danych. Tag macierzysty jest obowiązkowy. Tagi
	 * użytkownika mogą być pominięte.
	 * 
	 * @param id
	 *            Uchwyt do reprezentanta pliku
	 * @param mainTag
	 *            Tag macierzysty związany z plikiem
	 * @param userTags
	 *            Opcjonalne tagi użytkownika
	 */
	public void addFile(FileID id, MainTag mainTag, Collection<UserTag> userTags) {
	}

	/**
	 * Usuwa plik z bazy danych (nie dokonuje fizycznego usunięcia pliku z
	 * nośnika).
	 * 
	 * @param id
	 *            Uchwyt do reprezentanta pliku
	 */
	public void removeFile(FileID id) {
	}

	/**
	 * Zwraca kolekcję plików posiadających jakikolwiek z wymienionych tagów.
	 * 
	 * @param tags
	 *            Poszukiwane tagi
	 * @return Kolekcja plików takich że każdy z nich posiada przynajmniej jeden
	 *         z wymienionych tagów.
	 */
	public Collection<FileID> getFilesWithOneOf(Collection<Tag<?>> tags) {
		return null;
	}

	/**
	 * Zwraca kolekcję plików posiadających wszystkie wymienione tagi.
	 * 
	 * @param tags
	 *            Poszukiwane tagi
	 * @return Kolekcja plików takich że każdy z nich posiada wszystkie
	 *         wymienione tagi.
	 */
	public Collection<FileID> getFilesWithAllOf(Collection<Tag<?>> tags) {
		return null;
	}

	/**
	 * Zwraca wszystkie pliki o wskazanym tagu macierzystym. Ta funkcja jest
	 * szczególnie przydatna dla GUI.
	 * 
	 * @param mainTag
	 *            Tag macierzysty
	 * @return Kolekcja plików posiadających podany tag macierzysty
	 */
	public Collection<FileID> getFilesFrom(MainTag mainTag) {
		return null;
	}

	/**
	 * Dodaje zbiór plików do wskazanej kolekcji. Innymi słowy przypisuje tag
	 * macierzysty do zbioru plików.
	 * 
	 * @param files
	 *            Zbiór importowanych plików.
	 * @param mainTag
	 *            Tag macierzysty który otrzymają pliki.
	 */
	public void addFilesTo(Collection<FileID> files, MainTag mainTag) {
	}

	/**
	 * Dodaje zbiór plików do wskazanej kolekcji i przypisuje im wskazane tagi.
	 * Innymi słowy przypisuje tag macierzysty do zbioru plików. I taguje je.
	 * 
	 * @param files
	 *            Zbiór importowanych plików
	 * @param mainTag
	 *            Tag macierzysty który otrzymają pliki
	 * @param userTags
	 *            Tagi użytkownika które otrzymają pliki
	 */
	public void addFiles(Collection<FileID> files, MainTag mainTag,
			Collection<UserTag> userTags) {
	}

	/**
	 * Wyciąga wszystkie tagi z podanej kolekcji plików. Operacja symetryczna do
	 * getFilesWithOneOf.
	 * 
	 * @param files
	 *            Kolekcja plików
	 * @return Zbiór tagów które pojawiły się przynajmniej przy jednym z plików
	 * @see #getFilesWithOneOf(java.util.Collection)
	 */
	public Collection<Tag<?>> getTagsFrom(Collection<FileID> files) {
		return null;
	}
}
