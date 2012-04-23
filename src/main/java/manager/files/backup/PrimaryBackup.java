package manager.files.backup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;
import manager.tags.MasterTag;

/**
 * Primary backup of application. It is backup application is directly working
 * on. This backup can have copies done with use of SecondaryBackup
 * implementations.
 * 
 * @author Piotr Kolacz
 * 
 */
public interface PrimaryBackup extends Backup {

	/**
	 * Adds file to backup resources. Depending on implementation it can just
	 * copy file to suitable directory or upload it to server.
	 * 
	 * File specified by <code>file</code> parameter is not deleted for safety
	 * reasons.
	 * 
	 * @param tag
	 *            specifies connection of file with tag
	 * @param file
	 *            file to backup
	 * @param fresh
	 *            value true means that files have to be added to recently created root MasterTag
	 * @throws IOException
	 */
	void addFile(MasterTag tag, File file, boolean fresh)
			throws OperationInterruptedException, FileNotFoundException;

	/**
	 * Removes file specified by <code>fileId</code> from backup. This operation
	 * cannot be undone.
	 * 
	 * @param fileId
	 *            id of file to be deleted
	 * @throws IOException
	 */
	void removeFile(FileID fileId) throws FileNotAvailableException,
			OperationInterruptedException;

}
