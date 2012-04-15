/**
 * 
 */
package manager.files.backup;

import java.io.File;
import java.util.Date;
import java.util.List;

import manager.files.FileID;

/**
 * @author Piotr Kolacz
 * 
 */
public final class PicasaBackupImplementation extends SecondaryBackup {

	public PicasaBackupImplementation(PrimaryBackup originalBackup) {
		super(originalBackup);
		// TODO Auto-generated constructor stub
	}

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
	public Date getLastBackupUpdateDate() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see manager.files.backup.SecondaryBackup#updateBackup()
	 */
	@Override
	public void updateBackup() {
		// TODO Auto-generated method stub

	}

}
