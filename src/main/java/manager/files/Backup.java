package manager.files;

import java.io.File;

public interface Backup {

	/**
	 * aktualizuje kopie zapasowa z wszystkimi zmianami dokonanymi od czasu
	 * ostatniej synchronizacji
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
	void getListOfChanges(File backupLocation)
			throws OperationInterruptedException;

	/**
	 * 
	 * @ return @ param @ author
	 */
	void getLastBackupDate(File backupLocation)
			throws OperationInterruptedException;
}
