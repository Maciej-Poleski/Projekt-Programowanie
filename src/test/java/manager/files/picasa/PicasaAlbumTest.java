package manager.files.picasa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import manager.files.picasa.PicasaAlbum.PicasaPhotoUploader;
import manager.files.picasa.PicasaService.AlbumCreator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class PicasaAlbumTest {

	PicasaService ps;

	@Before
	public void setUp() throws Exception {
		ps = new PicasaService("projekt.programowanie");
		ps.authenticate("projekt1234");
	}

	@Test
	public void realDeleteAlbumTest() throws PicasaAuthenticationException,
			PicasaInformationCollectionException,
			PicasaDataModificationException, PicasaMediaUploadException {

		PicasaAlbum album = null;
		try {

			String title = "testowy1234";
			AlbumCreator ac = ps.getAlbumCreator(title);
			album = ac.execute();

		} finally {
			if (album != null) {
				album.delete();
			}
		}
	}

	@Test
	public void deleteAllAlbums() throws PicasaAuthenticationException,
			PicasaInformationCollectionException,
			PicasaDataModificationException {

		List<PicasaAlbum> albums = ps.getAllAlbumsList();

		for (PicasaAlbum a : albums) {
			if ("testowy1234".equals(a.getTitle())) {
				a.delete();
			}
		}

	}

	@Ignore
	@Test
	public void realAddPhotoToAlbumTest() throws PicasaAuthenticationException,
			PicasaInformationCollectionException,
			PicasaDataModificationException, PicasaMediaUploadException {

		// test nie działa ze względu na specyfikę webService'u picasy - to nie
		// błąd tej aplikacji

		PicasaAlbum album = null;
		try {
			File file = new File("dj2.jpg");

			String title = "testowy1234";
			String description = "description for test";

			AlbumCreator ac = ps.getAlbumCreator(title);
			album = ac.execute();

			PicasaPhotoUploader po = album.getPicasaPhotoUploader(file,
					PicasaAlbumMediaType.JPEG);

			String[] keywords = { "Ala", "Kot", "Tomek" };
			po.setTitle(title).setDescription(description)
					.setKeywords(Arrays.asList(keywords));

			PicasaPhoto photo = po.upload();

			assertEquals(album.getId(), photo.getAlbumId());
			assertEquals(title, photo.getTitle());
			assertEquals(description, photo.getDescription());
			assertTrue(Arrays.equals(keywords,
					photo.getListOfTags().toArray(new String[0])));

		} finally {

			if (album != null) {

				album.delete();
			}
		}
	}

}
