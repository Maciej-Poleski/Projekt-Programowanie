/**
 * 
 */
package manager.files.backup;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import manager.files.FileID;
import manager.files.FileNotAvailableException;
import manager.files.OperationInterruptedException;
import manager.files.picasa.PicasaAlbum;
import manager.files.picasa.PicasaAlbum.PicasaAlbumMediaType;
import manager.files.picasa.PicasaAuthenticationException;
import manager.files.picasa.PicasaDataModificationException;
import manager.files.picasa.PicasaInformationCollectionException;
import manager.files.picasa.PicasaMediaDownloadException;
import manager.files.picasa.PicasaMediaUploadException;
import manager.files.picasa.PicasaPhoto;
import manager.files.picasa.PicasaService;

/**
 * @author Piotr Kolacz
 * 
 */
public final class PicasaBackupImplementation extends SecondaryBackup {

	private static final long serialVersionUID = 1L;
	private Date lastBackup;
	private String userName;
	private String password;
	private String downloadLocation;
	private final Map<FileID, PicasaPhoto> backupedFiles = new HashMap<>();

	public PicasaBackupImplementation(PrimaryBackup originalBackup,
			String picasaLogin, String picasaPassword, String downloadLocation) {
		super(originalBackup);

		userName = picasaLogin;
		password = picasaPassword;
		this.downloadLocation = downloadLocation;
	}

	@Override
	public File getFile(FileID fileId) throws FileNotAvailableException,
			OperationInterruptedException {

		PicasaPhoto photo = backupedFiles.get(fileId);
		if (photo == null) {
			throw new FileNotAvailableException();
		}

		try {

			File downlaoded = PicasaPhoto
					.downloadPhoto(downloadLocation, photo);
			return downlaoded;
			
		} catch (PicasaMediaDownloadException e) {
			throw new OperationInterruptedException(e);
		}

	}

	@Override
	public Set<FileID> getListOfAvailableFiles() {
		return backupedFiles.keySet();
	}

	@Override
	public Date getLastBackupUpdateDate() {
		return lastBackup;
	}

	@Override
	public void updateBackup() throws OperationInterruptedException {
		try {
			PicasaService ps = new PicasaService(userName);
			ps.authenticate(password);

			List<PicasaAlbum> albums = ps.getAllAlbumsList();
			for (PicasaAlbum a : albums) {
				a.delete();
			}

			PicasaAlbum album = ps.getAlbumCreator("Backup")
					.setDate(new Date()).execute();

			Set<FileID> files = originalBackup.getListOfAvailableFiles();

			for (FileID f : files) {
				File file = originalBackup.getFile(f);

				// FIXME other types
				PicasaPhoto ph = album.getPicasaPhotoUploader(file,
						PicasaAlbumMediaType.JPEG).upload();
				backupedFiles.put(f, ph);
			}

			lastBackup = new Date();

		} catch (PicasaAuthenticationException
				| PicasaInformationCollectionException
				| PicasaDataModificationException | FileNotAvailableException
				| PicasaMediaUploadException ex) {
			throw new OperationInterruptedException(ex);
		}

	}

}