package manager.files.backup;

import java.io.File;
import java.util.Date;
import java.util.List;

import manager.files.FileID;
import manager.files.OperationInterruptedException;

public interface Backup {

	/**
	 * Tworzy nową lokalizację dla backupu
	 * @param backupLocation
	 */
	void createNewBackup(File backupLocation);

	/**
	 * aktualizuje kopie zapasowa z wszystkimi zmianami dokonanymi od czasu
	 * ostatniej synchronizacji.
	 * 
	 * @ param sciezka do katalogu @ author
	 */
	void synchronizeBackup(File backupLocation)
			throws OperationInterruptedException;

	/**
	 * Pozwala odzyskać plik przypadkiem usunięty
	 * 
	 * @ param ID pliku @ author
	 */
	void retrieveFile(FileID file) throws OperationInterruptedException;

	/**
	 * 
	 * @ return @ param @ author
	 */
	List<Change> getListOfChanges(File backupLocation)
			throws OperationInterruptedException;

	/**
	 * 
	 * @ return @ param @ author
	 */
	Date getLastBackupDate(File backupLocation)
			throws OperationInterruptedException;
}
