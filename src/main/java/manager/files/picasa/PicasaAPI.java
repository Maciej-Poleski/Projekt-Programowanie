package manager.files.picasa;

import java.util.Collection;

import manager.files.FileID;
import manager.files.OperationInterruptedException;
import manager.tags.Tag;

public interface PicasaAPI {
	/**
	 * Wysyla plik o okreslonym ID na konto w webalbumie Picasa
	 * 
	 * @ param ID pliku @ author
	 */
	void uploadFile(FileID fileID) throws AuthorizationException,
			OperationInterruptedException;

	/**
	 * Wysyla kolekcje plikow na konto w webalbumie Picasa
	 * 
	 * @ param Kolekcja plikow parametryzowana przez FileID @ author
	 */
	void uploadAllFiles(Collection<FileID> files)
			throws AuthorizationException, OperationInterruptedException;

	/**
	 * Usuwa z webalbumu okreslony plik
	 * 
	 * @ param ID pliku @ author
	 */
	void deleteFile(FileID fileID) throws AuthorizationException,
			OperationInterruptedException;

	/**
	 * Usuwa z webalbumu kolekcje plikow
	 * 
	 * @ param Kolekcja plikow parametryzowana przez FileID @ author
	 */
	void deleteAllFiles(Collection<FileID> files)
			throws AuthorizationException, OperationInterruptedException;

	/**
	 * 
	 * @ return @ param @ author
	 */
	void addTags(FileID fileID, Collection<Tag<?>> tags)
			throws AuthorizationException, OperationInterruptedException;

	/**
	 * 
	 * @ return @ param @ author
	 */
	void deleteTag(FileID fileID, Tag<?> tag) throws AuthorizationException,
			OperationInterruptedException;

}