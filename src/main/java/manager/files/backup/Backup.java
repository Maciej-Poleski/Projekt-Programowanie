package manager.files.backup;

import java.io.File;
import java.nio.file.Path;
import java.util.Date;

import manager.files.FileID;
import manager.files.OperationInterruptedException;
import manager.tags.MasterTag;

public interface Backup {

	/**
	 * Tworzy nowy Backup.
	 * @param MasterTag master Tag którego backup chcemy zrobić.
	 * @param File ścieżka gdzie chcemy go zrobić.
	 */
	void createNewBackup(MasterTag masaterTag, File backupLocation);

	/**
	 * Synchronizuje backup.
	 * @param MasterTag master Tag którego backup chcemy zrobić.
	 * @param File ścieżka gdzie chcemy go zrobić.
	 */
	public void synchronizeBackup(MasterTag masterTag, File backupLocation) throws OperationInterruptedException;

	/**
	 * Zwraca odzyskany plik.
	 * @param FileID id pliku.
	 * @return Pilk.
	 */
	public File retrieveFile(FileID file) throws OperationInterruptedException;

	/**
	 * Zwraca date dla danego backup'u.
	 * @param File lokacja pliku.
	 * @return Date data.
	 */
	public Date getLastBackupDate(File backupLocation) throws OperationInterruptedException;
}
