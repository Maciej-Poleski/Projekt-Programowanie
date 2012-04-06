package manager.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;

import manager.tags.MasterTag;
import manager.tags.Tag;

public interface FileSystem {

	/**
	 * Zapisuje plik po edycji.
	 * 
	 * @ param Obraz po edycji. @ Author
	 */
	void saveEditedImage(ImageHolder editedImage) throws FileSaveException;

	/**
	 * Pobiera ID i zwraca obraz do edycji.
	 * 
	 * @ return Obraz gotowy do edycji. @ param ID pliku do edycji. @ author
	 */
	ImageHolder getImageForEdit(FileID fileID)
			throws FileNotAccessibleException;

	/**
	 * Pobiera ID obrazu o maksymalnej szerokosci / wysokosci.
	 * 
	 * @ return Obraz gotowy do podgladu. @ param ID pliku do podgladu,
	 * maksymalna szerokosc / wysokosc. @ Author
	 */
	ImageHolder getImageForPreview(FileID fileID, int maxDimension)
			throws FileNotAccessibleException;

	/**
	 * Pobiera kolekcje tagów i zwraca miniaturki także w kolekcji.
	 * 
	 * @ return Kolekcja ImageHolderow. @ param Kolekcja tagów do pobrania,
	 * maksymalna szerokosc / wysokosc, czy zwrocic przeciecie czy sume. @
	 * author
	 */
	Collection<ImageHolder> getThumbnailsWithTags(Collection<Tag<?>> tags,
			int maxDimension, boolean intersection)
			throws FileNotAccessibleException;

	/**
	 * Wgrywa pliki do systemu katalogów zarządzanego przez aplikacje.
	 * 
	 * @ param tag macierzysty @ param kolekcje plików. @ author
	 */
	void importFiles(MasterTag tag, Collection<File> filesToAdd)
			throws FileNotFoundException;

	/**
	 * Usuwa plik o danym ID.
	 * 
	 * @ param ID pliku. @ author
	 */
	void delete(FileID fileID) throws FileNotAccessibleException;

	/**
	 * Usuwa wszystkie pliki zdefiniowane przez kolekcje.
	 * 
	 * @ param Kolekcja plikow definiowanych przez ID. @ author
	 */
	void deleteAll(Collection<FileID> fileID) throws FileNotAccessibleException;

	/**
	 * Sprawdza, czy pliki podane przez kolekcje znajduja sie na dysku.
	 * 
	 * @ Param Kolekcja plikow definiowanych przez ID @ author
	 */
	Collection<FileID> getUnaccessibleFiles(Collection<FileID> filesToCheck);
}