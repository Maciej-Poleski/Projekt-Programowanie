package manager.files.backup;

import java.io.Serializable;
import java.util.Collections;
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
