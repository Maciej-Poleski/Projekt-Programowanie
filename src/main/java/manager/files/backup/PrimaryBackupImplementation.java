package manager.files.backup;

import java.io.File;
import java.util.List;

import manager.files.FileID;
import manager.tags.MasterTag;

/**
 * @author Kuba
 *
 */
public final class PrimaryBackupImplementation implements PrimaryBackup {

	@Override
	public File getFile(FileID fileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FileID> getListOfAvailableFiles() {
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
