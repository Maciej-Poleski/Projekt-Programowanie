package manager.files.backup;

import java.util.Date;
import java.util.Map;

import manager.files.FileID;
import manager.files.OperationInterruptedException;

/**
 * Application can use implementations of this abstract class to maintain safety
 * backups.
 * 
 * @author Piotr Kolacz
 * 
 */
public abstract class SecondaryBackup implements Backup {

	private static final long serialVersionUID = 1L;

	/**
	 * Reference to backup which state we are mirroring
	 */
	protected final PrimaryBackup originalBackup;

	/**
	 * @param originalBackup
	 *            reference to backup which state we are mirroring
	 */
	public SecondaryBackup(PrimaryBackup originalBackup) {
		this.originalBackup = originalBackup;
	}

	/**
	 * Returns date of last backup synchronization. By backup synchronization we
	 * mean time when last time state of backup was exactly mirror of specified
	 * primary backup state.
	 * 
	 * @param primaryBackup
	 * @return date of last backup update
	 */
	public abstract Date getLastBackupUpdateDate();

	/**
	 * Updates backup state. After that operation succeeds state of this backup
	 * is exactly an mirror of state of PrimaryBackup we are synchronizing with.
	 */
	public abstract void updateBackup() throws OperationInterruptedException;

	/**
	 * Returns list of files contained in this safety backup and not available
	 * in application primary file System (files potentially deleted by user).
	 * 
	 * @return map of descriptions connected with FileID's
	 */
	public abstract Map<String, FileID> getListOfAdditionalFiles();

}
