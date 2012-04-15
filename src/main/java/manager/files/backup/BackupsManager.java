package manager.files.backup;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Class responsible for serializing all informations about backups in the
 * system.
 * 
 * @author Piotr Kolacz
 * 
 */
public class BackupsManager implements Serializable {

	private static final long serialVersionUID = 1L;

	private final PrimaryBackup primaryBackup;
	private final Set<SecondaryBackup> backups;

	public BackupsManager(PrimaryBackup primaryBackup) {
		this.primaryBackup = primaryBackup;
		this.backups = new HashSet<>();
	}

	public void registerBackup(SecondaryBackup backup) {
		backups.add(backup);
	}

	public PrimaryBackup getPrimaryBackup() {
		return primaryBackup;
	}
}
