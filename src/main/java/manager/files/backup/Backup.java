package manager.files.backup;

import java.io.File;
import java.util.List;

import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;

/**
 * Basic class for backup implementations.
 * 
 * @author Piotr Kolacz
 * 
 */
public interface Backup {

	/**
	 * Retrieves a file with specified <code>fileId</code> from backup.
	 * 
	 * @param fileId
	 *            id of file to be returned
	 * @return File handler for <b>copy</b> of that file for safety reasons.
	 */
	File getFile(FileID fileId) throws FileNotAvailableException, OperationInterruptedException;

	/**
	 * Returns list of FileID's currently managed by this backup. This list can
	 * by used to check backup state.
	 * 
	 * @return list of FileID's currently managed by this backup
	 */
	List<FileID> getListOfAvailableFiles() throws OperationInterruptedException;

}
