package manager.files.picasa;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gdata.client.Query;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.UserFeed;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

public class PicasaService {

	private final String feedLink;
	private final String userName;
	private PicasawebService picasawebService;
	private static long counter = 0;

	/**
	 * Default constructor
	 * @param userName
	 */
	public PicasaService(String userName) {
		picasawebService = createPicasawebService(userName);
		feedLink = "https://picasaweb.google.com/data/feed/api/user/"
				+ userName;
		this.userName = userName;
	}

	// method which enables testing without use of outer resources
	PicasawebService createPicasawebService(String userName) {
		return new PicasawebService("KOZiK_application_service_" + ++counter
				+ "_" + userName);
	}

	/**
	 * Authenticate user - only authenticated user can use picasaweb resources
	 * 
	 * @param password
	 * @throws PicasaAuthenticationException
	 *             when authentication fails
	 */
	public void authenticate(String password)
			throws PicasaAuthenticationException {

		try {
			picasawebService.setUserCredentials(userName, password);

		} catch (AuthenticationException e) {
			throw new PicasaAuthenticationException(e);
		}
	}

	/**
	 * @param tags
	 * @return all photos associated with given collection of tags
	 * @throws PicasaInformationCollectionException
	 */
	public List<PicasaPhoto> getAllPhotosWithTags(Collection<String> tags)
			throws PicasaInformationCollectionException {

		AlbumFeed searchResultsFeed;
		try {
			Query myQuery = new Query(new URL(feedLink));

			myQuery.setStringCustomParameter("kind", "photo");

			StringBuilder sb = new StringBuilder();
			for (String tag : tags) {
				sb.append(tag).append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			myQuery.setStringCustomParameter("tag", sb.toString());

			searchResultsFeed = picasawebService
					.query(myQuery, AlbumFeed.class);

			List<PicasaPhoto> photos = new ArrayList<>();

			for (PhotoEntry photo : searchResultsFeed.getPhotoEntries()) {
				photos.add(new PicasaPhoto(photo));
			}

			return photos;

		} catch (IOException | ServiceException e) {
			throw new PicasaInformationCollectionException(e);
		}

	}

	/**
	 * Returns photos in this album
	 * 
	 * @return
	 * @throws PicasaInformationCollectionException
	 */
	public List<PicasaAlbum> getAllAlbumsList()
			throws PicasaInformationCollectionException {

		try {
			URL feedUrl = new URL(feedLink + "?kind=album");
			UserFeed myUserFeed = picasawebService.getFeed(feedUrl,
					UserFeed.class);

			List<PicasaAlbum> albums = new ArrayList<>();
			for (AlbumEntry myAlbum : myUserFeed.getAlbumEntries()) {

				albums.add(new PicasaAlbum(myAlbum, picasawebService));
			}

			return albums;

		} catch (IOException | ServiceException e) {
			throw new PicasaInformationCollectionException(e);
		}
	}

	/**
	 * Enables creation of PicasaWeb album
	 * 
	 * @author Piotr Kolacz
	 * 
	 */
	public static final class AlbumCreator extends
			AbstractAlbumPropertiesSetter {

		private final PicasawebService picasawebService;
		private final URL feedUrl;

		AlbumCreator(String title, PicasawebService picasawebService,
				URL feedUrl, AlbumEntry albumEntry) {
			super(albumEntry);
			setTitle(title);

			this.picasawebService = picasawebService;
			this.feedUrl = feedUrl;
		}

		@Override
		public PicasaAlbum execute() throws PicasaDataModificationException {
			try {

				AlbumEntry insertedEntry = picasawebService.insert(feedUrl,
						albumEntry);

				return new PicasaAlbum(insertedEntry, picasawebService);

			} catch (IOException | ServiceException e) {
				throw new PicasaDataModificationException(e);
			}
		}

	}

	/**
	 * @param title
	 * @return album creator - builder pattern
	 * @throws PicasaInformationCollectionException
	 */
	public AlbumCreator getAlbumCreator(String title)
			throws PicasaInformationCollectionException {
		try {
			return new AlbumCreator(title, picasawebService, new URL(feedLink),
					new AlbumEntry());
		} catch (MalformedURLException ex) {
			throw new PicasaInformationCollectionException(
					"Cannot create link needed for album creation", ex);
		}
	}

}
