package manager.files.backup;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;
import manager.tags.MasterTag;
import manager.tags.Tags;

/**
 * @author Piotr Kolacz
 * 
 */
public class BackupsManager implements Serializable {

	private final Tags tags;

	private Map<MasterTag, BackupManager> backupManagers;

	/**
	 * Default constructor.
	 * 
	 * @param tags Tags object used in application
	 */
	public BackupsManager(Tags tags) {
		this.tags = tags;
		backupManagers = new HashMap<>();
	}

	/**
	 * Returns file associated with given FileId
	 * 
	 * @param fileId
	 * @return file associated with given FileId
	 * @throws OperationInterruptedException
	 * @throws FileNotAvailableException
	 */
	public File getFile(FileID fileId) throws OperationInterruptedException,
			FileNotAvailableException {

		for (BackupManager bm : backupManagers.values()) {
			if (bm.getPrimaryBackup().getListOfAvailableFiles()
					.contains(fileId)) {
				return bm.getPrimaryBackup().getFile(fileId);
			}
		}

		throw new FileNotAvailableException();
	}

	/**
	 * Returns BackupManager associated with provided MasterTag
	 * 
	 * @param tag
	 * @return BackupManager associated with provided MasterTag
	 */
	public BackupManager getBackupManagerAssociatedWithMasterTag(MasterTag tag) {
		MasterTag tmp = tags.getOldestAncestor(tag);
		return backupManagers.get(tmp);
	}

	/**
	 * Register new BackupManager associated with provided MasterTag. MasterTag
	 * should be as general as possible, e.g. Family, Mountains etc.
	 * 
	 * @param tag
	 * @param manager
	 */
	public void registerBackupManager(MasterTag tag, BackupManager manager) {
		backupManagers.put(tag, manager);
	}

	/**
	 * Returns all registered backups.  
	 * 
	 * @return all registered backups.
	 */
	public Map<MasterTag, BackupManager> getAllBackupManagers() {
		return backupManagers;
	}
}
