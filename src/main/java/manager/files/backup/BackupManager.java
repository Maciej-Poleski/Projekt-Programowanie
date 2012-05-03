package manager.files.backup;

import java.io.File;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import manager.files.OperationInterruptedException;
import manager.files.picasa.PicasaAuthenticationException;
import manager.files.picasa.PicasaService;

/**
 * Class responsible for serializing all informations about backups in the
 * system.
 * 
 * @author Piotr Kolacz
 * 
 */
public class BackupManager implements Serializable {

	private static final long serialVersionUID = 1L;

	private final PrimaryBackup primaryBackup;
	private final Set<SecondaryBackup> backups;

	/**
	 * Default constructor
	 * 
	 * @param primaryBackup
	 */
	public BackupManager(PrimaryBackup primaryBackup) {
		this.primaryBackup = primaryBackup;
		this.backups = new HashSet<>();
	}

	/**
	 * Allow registration of additional backup. You can provide any backup
	 * implementation extending SecondaryBackup abstract class.
	 * 
	 * @param backup
	 */
	public void registerBackup(SecondaryBackup backup) {
		backups.add(backup);
	}

	/**
	 * Registers PicasaBackup. PicasaBackup implementation uses Google Picasa
	 * Web Services. You can you this backup to store only photos and movies.
	 * 
	 * @param picasaLogin
	 *            user's Google Account login (with or without \\@google.com)
	 * @param picasaPassword
	 *            user's Google Account password user for authentication
	 *            purposes
	 * @param downloadLocation
	 *            location used to store files retrieved from backup, if not
	 *            specified uses default system temp location
	 */
	public SecondaryBackup registerPicasaBackup(String picasaLogin,
			String picasaPassword, File downloadLocation)
			throws OperationInterruptedException {

		try {
			PicasaService ps = new PicasaService(picasaLogin);
			ps.authenticate(picasaPassword);
			
		} catch (PicasaAuthenticationException e) {
			throw new OperationInterruptedException("Incorrect login or password", e);
		}
		
		for (SecondaryBackup b : backups) {
			if (b instanceof PicasaBackupImpl) {
				PicasaBackupImpl tmp = (PicasaBackupImpl) b;
				if (tmp.getUserName().equals(picasaLogin)) {
					throw new OperationInterruptedException(
							"In that location already exists backup");
				}
			}
		}
		
		SecondaryBackup sb = new PicasaBackupImpl(primaryBackup, picasaLogin,
				picasaPassword, downloadLocation);

		registerBackup(sb);

		return sb;
	}

	/**
	 * Registers FileSystemBackup. FileSystemBackup implementation uses
	 * specified location to create safety copy of files stored in this backup
	 * manager.
	 * 
	 * @param backupLocation
	 *            location where backup should be stored. If it does not exist
	 *            is created. It must be directory location not ordinary file.
	 * @throws OperationInterruptedException
	 *             if backupLocation exists and is not a directory
	 */
	public SecondaryBackup registerFileSystemBackup(File backupLocation)
			throws OperationInterruptedException {

		for (SecondaryBackup b : backups) {
			if (b instanceof FileSystemBackupImpl) {
				FileSystemBackupImpl tmp = (FileSystemBackupImpl) b;
				if (tmp.getLocation().equals(backupLocation)) {
					throw new OperationInterruptedException(
							"In that location already exists backup");
				}
			}
		}

		SecondaryBackup sb = new FileSystemBackupImpl(primaryBackup,
				backupLocation);

		registerBackup(sb);

		return sb;
	}

	/**
	 * Returns primary backup associated with that BackupsManager
	 * 
	 */
	public PrimaryBackup getPrimaryBackup() {
		return primaryBackup;
	}

	/**
	 * Returns immutable view of available safetyBackups. It can be used to
	 * retrieve lost file.
	 */
	public Set<SecondaryBackup> getSecondaryBackups() {
		return Collections.unmodifiableSet(backups);
	}
}
