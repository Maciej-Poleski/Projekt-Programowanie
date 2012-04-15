package manager.files.picasa;

import java.util.List;

import org.junit.Test;

public class PicasaPhotoTest {

	@Test
	public void downloadPhotoTest() throws PicasaAuthenticationException,
			PicasaInformationCollectionException, PicasaMediaDownloadException {

		PicasaService ps = new PicasaService("projekt.programowanie");
		ps.authenticate("projekt1234");

		List<PicasaAlbum> albums = ps.getAllAlbumsList();

		PicasaAlbum album = albums.get(1);
		List<PicasaPhoto> photos = album.getAllPhotos();
		
		PicasaPhoto photo = photos.get(0);

		String downloadDirectory = "C:\\Users\\Ania\\Desktop\\workspace_eclipse\\PicasaIntegration\\downloaded";				
		System.out.println(photo.downloadPhoto(downloadDirectory));

	}

}
