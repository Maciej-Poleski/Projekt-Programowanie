package manager.files.picasa;

import java.io.File;

import org.junit.Test;

public class PicasaPhotoTest {

	@Test
	public void downloadPhotoTest() throws PicasaAuthenticationException,
			PicasaInformationCollectionException, PicasaMediaDownloadException,
			PicasaMediaUploadException, PicasaDataModificationException {

		PicasaService ps = new PicasaService("projekt.programowanie");
		ps.authenticate("projekt1234");

		PicasaAlbum album = ps.getAlbumCreator("testAlbum").execute();
		String path = "src" + File.separator + "test" + File.separator
				+ "resources" + File.separator + "GoogleMusic.jpg";
		PicasaPhoto photo = album.getPicasaPhotoUploader(new File(path),
				PicasaAlbumMediaType.JPEG).upload();

		path = "src" + File.separator + "test" + File.separator + "resources"
				+ File.separator + "downloaded";
		File downloadDirectory = new File(path);

		System.out.println(photo.downloadPhoto(downloadDirectory));

	}

}
