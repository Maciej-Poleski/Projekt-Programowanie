package manager.files.backup;

import java.io.File;
import java.util.Date;
import java.util.Set;

import manager.files.FileID;

/**
 * @author Karol
 * 
 */
public final class FileSystemBackupImplementation extends SecondaryBackup {

	private static final long serialVersionUID = 1L;

	public FileSystemBackupImplementation(PrimaryBackup originalBackup) {
		super(originalBackup);
		// TODO Auto-generated constructor stub
	}

	@Override
	public File getFile(FileID fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<FileID> getListOfAvailableFiles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getLastBackupUpdateDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBackup() {
		// TODO Auto-generated method stub

	}

}
