package manager.files.picasa;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.media.MediaFileSource;
import com.google.gdata.data.media.mediarss.MediaKeywords;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;

public class PicasaAlbum {

	private final PicasawebService picasawebService;
	private final URL feedUrl;
	private AlbumEntry albumEntry;

	/**
	 * @param albumEntry
	 * @param picasawebService
	 * @throws MalformedURLException
	 *             when created
	 */
	PicasaAlbum(AlbumEntry albumEntry, PicasawebService picasawebService)
			throws MalformedURLException {

		this.picasawebService = picasawebService;
		this.albumEntry = albumEntry;
		this.feedUrl = new URL(albumEntry.getFeedLink().getHref());
	}

	/**
	 * Returns id of this album.
	 * 
	 * @return id of this album.
	 */
	String getId() {
		String id = albumEntry.getId();
		id = id.substring(id.lastIndexOf("/") + 1);
		return id;
	}

	/**
	 * Returns photos located in this album
	 * 
	 * @return
	 * @throws PicasaInformationCollectionException
	 */
	public List<PicasaPhoto> getAllPhotos()
			throws PicasaInformationCollectionException {

		try {
			List<PicasaPhoto> photos = new ArrayList<>();
			AlbumFeed feed = picasawebService.getFeed(feedUrl, AlbumFeed.class);

			for (PhotoEntry photo : feed.getPhotoEntries()) {
				photos.add(new PicasaPhoto(photo));
			}

			return photos;

		} catch (IOException | ServiceException e) {
			throw new PicasaInformationCollectionException(e);
		}
	}

	/**
	 * Deletes this album
	 * 
	 * @throws PicasaDataModificationException
	 */
	public void delete() throws PicasaDataModificationException {
		try {

			albumEntry = albumEntry.getSelf();
			albumEntry.delete();

		} catch (IOException | ServiceException e) {
			throw new PicasaDataModificationException(e);
		}
	}

	/**
	 * @return true if this album is public, false otherwise
	 */
	public boolean isPublic() {
		return "public".equals(albumEntry.getAccess());
	}

	/**
	 * @return title of this album
	 */
	public String getTitle() {
		return albumEntry.getTitle().getPlainText();
	}

	/**
	 * @return description of this album
	 */
	public String getDescription() {
		return albumEntry.getDescription().getPlainText();
	}

	/**
	 * @return date when this album was created
	 */
	public Date getDate() {
		return albumEntry.getDate();
	}

	/**
	 * Returns HTML link to this album. Link contains authentication code, so
	 * can be seen by anyone knowing that link.
	 * 
	 * @return
	 */
	public String getHtmlLink() {
		return albumEntry.getHtmlLink().getHref();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((albumEntry == null) ? 0 : albumEntry.hashCode());
		result = prime
				* result
				+ ((picasawebService == null) ? 0 : picasawebService.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PicasaAlbum))
			return false;
		PicasaAlbum other = (PicasaAlbum) obj;
		if (albumEntry == null) {
			if (other.albumEntry != null)
				return false;
		} else if (!albumEntry.equals(other.albumEntry))
			return false;
		if (feedUrl == null) {
			if (other.feedUrl != null)
				return false;
		} else if (!feedUrl.toString().equals(other.feedUrl.toString()))
			return false;
		if (picasawebService == null) {
			if (other.picasawebService != null)
				return false;
		} else if (!picasawebService.equals(other.picasawebService))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return "[name = " + albumEntry.getTitle().getPlainText() + "]";
	}

	// for test purposes only
	AlbumEntry getAlbumEntry() {
		return albumEntry;
	}

	/**
	 * Returns files uploader.
	 * 
	 * @param file
	 * @param type
	 * @return
	 */
	public PicasaPhotoUploader getPicasaPhotoUploader(File file,
			PicasaAlbumMediaType type) {
		return new PicasaPhotoUploader(file, type, picasawebService, feedUrl);
	}

	/**
	 * Enables files upload to picasaWeb
	 * 
	 * @author Piotr Kolacz
	 * 
	 */
	public static class PicasaPhotoUploader {
		private final File fileToUpload;
		private final PicasaAlbumMediaType fileType;
		private final PicasawebService picasaService;
		private final URL feedUrl;
		private final PhotoEntry photoEntry;

		private PicasaPhotoUploader(File file, PicasaAlbumMediaType type,
				PicasawebService picasaService, URL feedUrl) {
			this.fileToUpload = file;
			this.fileType = type;
			this.picasaService = picasaService;
			this.feedUrl = feedUrl;
			this.photoEntry = new PhotoEntry();
		}

		/**
		 * Uploads given photo into Picasa web service.
		 * 
		 * @return handler for uploaded Photo
		 * @throws PicasaMediaUploadException
		 *             if there are problems with upload
		 */
		public PicasaPhoto upload() throws PicasaMediaUploadException {

			try {

				MediaFileSource myMedia = new MediaFileSource(fileToUpload,
						fileType.toString());
				photoEntry.setMediaSource(myMedia);

				PhotoEntry returnedPhoto = picasaService.insert(feedUrl,
						photoEntry);

				return new PicasaPhoto(returnedPhoto);

			} catch (IOException | ServiceException e) {

				throw new PicasaMediaUploadException(e);
			}
		}

		/**
		 * Sets title of this album.
		 * 
		 * @param title
		 * @return this PicasaPhotoUploader object - builder pattern
		 */
		public PicasaPhotoUploader setTitle(String title) {
			photoEntry.setTitle(new PlainTextConstruct(title));

			return this;
		}

		/**
		 * Sets description of this album.
		 * 
		 * @param description
		 * @return this PicasaPhotoUploader object - builder pattern
		 */
		public PicasaPhotoUploader setDescription(String description) {
			photoEntry.setDescription(new PlainTextConstruct(description));

			return this;
		}

		/**
		 * Sets keywords associated with this photo.
		 * 
		 * @param keywords
		 * @return this PicasaPhotoUploader object - builder pattern
		 */
		public PicasaPhotoUploader setKeywords(Collection<String> keywords) {

			MediaKeywords myTags = new MediaKeywords();

			StringBuilder sb = new StringBuilder();

			for (String k : keywords) {
				sb.append(k).append(",");
			}

			sb.replace(sb.length() - 1, sb.length() - 1, "");

			myTags.addKeyword(sb.toString());
			photoEntry.setKeywords(myTags);

			return this;
		}

	}

	/**
	 * Returns builder which enables changing of this album properties.
	 * 
	 * @return this AlbumPropertiesSetter
	 */
	public AlbumPropertiesSetter getAlbumPropertiesSetter() {
		return new AlbumPropertiesSetter(this);
	}

	/**
	 * Enables changing this album properties.
	 * 
	 * @author Piotr Kolacz
	 */
	public static class AlbumPropertiesSetter extends
			AbstractAlbumPropertiesSetter {

		private final PicasaAlbum picasaAlbum;

		AlbumPropertiesSetter(PicasaAlbum picasaAlbum) {
			super(picasaAlbum.getAlbumEntry());
			this.picasaAlbum = picasaAlbum;
		}

		@Override
		public PicasaAlbum execute() throws PicasaDataModificationException {
			try {
				albumEntry.update();

				return picasaAlbum;

			} catch (IOException | ServiceException e) {
				throw new PicasaDataModificationException(e);
			}
		}
	}

}
