package manager.files.backup;

import java.io.File;
import java.util.Set;

import manager.files.FileID;
import manager.tags.MasterTag;

/**
 * @author Kuba
 * 
 */
public final class PrimaryBackupImplementation implements PrimaryBackup {

	private static final long serialVersionUID = 1L;

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
	public void addFile(MasterTag tag, File file) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFile(FileID fileId) {
		// TODO Auto-generated method stub

	}

}
