package manager.files.picasa;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import manager.files.picasa.PicasaAlbum.PicasaAlbumMediaType;
import manager.files.picasa.PicasaService.AlbumCreator;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.Link;
import com.google.gdata.data.TextConstruct;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class PicasaServiceTest {

	private String testUserName = "test.test";
	private PicasaService ps;
	private PicasawebService picasawebService;

	@Ignore
	@Before
	public void initialize() {
		picasawebService = mock(PicasawebService.class);

		ps = new PicasaService(testUserName) {
			PicasawebService createPicasawebService(String userName) {
				return picasawebService;
			}
		};
	}

	@Ignore
	@Test
	public void successfulAuthenticationTest() throws AuthenticationException,
			PicasaAuthenticationException {

		String password = "password";

		ps.authenticate(password);

		verify(picasawebService).setUserCredentials(testUserName, password);
	}

	@Ignore
	@Test(expected = PicasaAuthenticationException.class)
	public void failedAuthenticationTest() throws AuthenticationException,
			PicasaAuthenticationException {

		String password = "password";

		doThrow(new AuthenticationException("")).when(picasawebService)
				.setUserCredentials(testUserName, password);

		ps.authenticate(password);

	}

	@Ignore
	@Test
	public void getAllAlbumsList() throws IOException, ServiceException,
			PicasaInformationCollectionException {
		UserFeed userFeed = mock(UserFeed.class);

		List<AlbumEntry> albums = new LinkedList<>();
		AlbumEntry albumEntry = mock(AlbumEntry.class);
		albums.add(albumEntry);

		when(albumEntry.getTitle()).thenReturn(mock(TextConstruct.class));
		Link link = mock(Link.class);
		when(link.getHref()).thenReturn("http://google.com");
		when(albumEntry.getFeedLink()).thenReturn(link);

		when(userFeed.getAlbumEntries()).thenReturn(albums);
		when(picasawebService.getFeed(any(URL.class), eq(UserFeed.class)))
				.thenReturn(userFeed);

		List<PicasaAlbum> returnedAlbums = ps.getAllAlbumsList();
		assertTrue(returnedAlbums.contains(new PicasaAlbum(albumEntry,
				picasawebService)));
	}

	@Ignore
	@Test
	public void realAuthenticationTest() throws PicasaAuthenticationException {
		PicasaService ps = new PicasaService("projekt.programowanie");
		ps.authenticate("projekt1234");
	}

	@Ignore
	@Test(expected = PicasaAuthenticationException.class)
	public void realFailedAuthenticationTest()
			throws PicasaAuthenticationException {
		PicasaService ps = new PicasaService("projekt.programowanie");
		ps.authenticate("projekt123");
	}

	@Ignore
	@Test
	public void realGetAllAlbumsList() throws PicasaAuthenticationException,
			PicasaInformationCollectionException {
		PicasaService ps = new PicasaService("projekt.programowanie");
		ps.authenticate("projekt1234");

		List<PicasaAlbum> albums = ps.getAllAlbumsList();

		assertFalse(albums.isEmpty());
	}

	@Ignore
	@Test
	public void realAlbumCreation() throws PicasaAuthenticationException,
			PicasaInformationCollectionException,
			PicasaDataModificationException {

		PicasaAlbum album = null;
		try {
			PicasaService ps = new PicasaService("projekt.programowanie");
			ps.authenticate("projekt1234");

			Date now = new Date();
			String description = "Ale Ala ma kota";
			String title = "testowy1234";

			AlbumCreator ac = ps.getAlbumCreator(title);
			ac.setDate(now).setDescription(description).setPublic(true);

			album = ac.execute();

			assertTrue(album.isPublic());
			// Picasa rounds miliseconds
			assertEquals(now.toString(), album.getDate().toString());
			assertEquals(title, album.getTitle());
			assertEquals(description, album.getDescription());

		} finally {
			if (album != null) {
				album.delete();
			}
		}
	}

	@Test
	public void realGetFilesWithTags() throws PicasaAuthenticationException,
			PicasaInformationCollectionException,
			PicasaDataModificationException, PicasaMediaUploadException {

		PicasaAlbum album = null;
		try {
			PicasaService ps = new PicasaService("projekt.programowanie");
			ps.authenticate("projekt1234");

			String title = "testowy1234";
			AlbumCreator ac = ps.getAlbumCreator(title);
			album = ac.execute();

			String[] keywords = { "Ala", "Las" };
			album.getPicasaPhotoUploader(new File("dj2.jpg"),
					PicasaAlbumMediaType.JPEG)
					.setKeywords(Arrays.asList(keywords)).upload();

			String[] keywords2 = { "Ala" };
			album.getPicasaPhotoUploader(new File("dj2.jpg"),
					PicasaAlbumMediaType.JPEG)
					.setKeywords(Arrays.asList(keywords2)).upload();

			List<PicasaPhoto> photos = ps.getAllPhotosWithTags(Arrays
					.asList(keywords));
			assertEquals(1, photos.size());

			photos = ps.getAllPhotosWithTags(Arrays.asList(keywords2));
			assertEquals(2, photos.size());

		} finally {
			if (album != null) {
				album.delete();
			}
		}

	}
}
