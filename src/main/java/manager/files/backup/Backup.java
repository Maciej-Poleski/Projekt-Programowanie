package manager.files.backup;

import java.io.File;
import java.nio.file.Path;
import java.util.Date;

import manager.files.FileID;
import manager.files.OperationInterruptedException;
import manager.tags.MasterTag;

public interface Backup {

	/**
	 * Tworzy nową lokalizację dla backupu
	 * @param backupLocation
	 */
	void createNewBackup(MasterTag masaterTag, File backupLocation);

	/**
	 * aktualizuje kopie zapasowa z wszystkimi zmianami dokonanymi od czasu
	 * ostatniej synchronizacji.
	 * @param Pobiera sciezka do katalogu, Mastertag
	 */
	void synchronizeBackup(File backupLocation) throws OperationInterruptedException;

	/**
	 * Pozwala odzyskać plik przypadkiem usunięty
	 * @param Pobiera ID pliku
	 */
	File retrieveFile(FileID file) throws OperationInterruptedException;

	/**
	 * Zwraca date danego backupu tego pliku
	 * @return Zwraca Data @param Pobiera Plik
	 */
	Date getLastBackupDate(File backupLocation) throws OperationInterruptedException;
}
