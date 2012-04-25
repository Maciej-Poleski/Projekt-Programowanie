package manager.files.picasa;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gdata.client.Query;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.data.photos.TagEntry;
import com.google.gdata.util.ServiceException;

public final class PicasaPhoto {

	private PhotoEntry photoEntry;

	/**
	 * Default constructor
	 * 
	 * @param photoEntry
	 */
	public PicasaPhoto(PhotoEntry photoEntry) {
		this.photoEntry = photoEntry;
	}

	/**
	 * @return title of this photo
	 */
	public String getTitle() {
		return photoEntry.getTitle().getPlainText();
	}

	/**
	 * @return description of this photo
	 */
	public String getDescription() {
		return photoEntry.getDescription().getPlainText();
	}

	/**
	 * @return id of album this photo is associated with
	 */
	public String getAlbumId() {
		return photoEntry.getAlbumId();
	}

	/**
	 * Downloads this photo from picasaWeb service.
	 * 
	 * @param downloadDirectory
	 *            if not specified default system temp directory is used
	 * @return handler to downloaded File
	 * @throws PicasaMediaDownloadException
	 */
	public File downloadPhoto(File downloadDirectory)
			throws PicasaMediaDownloadException {

		return downloadPhoto(downloadDirectory, this);
	}

	/**
	 * Downloads photo provided by photo parameter
	 * 
	 * @param downloadDirectory
	 * @param photo
	 *            to be downloaded
	 * @return handler to downloaded File
	 * @throws PicasaMediaDownloadException
	 */
	public static File downloadPhoto(File downloadDirectory, PicasaPhoto photo)
			throws PicasaMediaDownloadException {
		try {
			String photoUrl = photo.photoEntry.getMediaContents().get(0)
					.getUrl();
			int index = photoUrl.lastIndexOf('/');
			photoUrl = photoUrl.substring(0, index + 1) + "s2000"
					+ photoUrl.substring(index);

			URL url = new URL(photoUrl);

			try (InputStream is = url.openStream()) {

				File file = File.createTempFile("picasaDownload_",
						photo.getTitle(), downloadDirectory);

				try (OutputStream out = new BufferedOutputStream(
						new FileOutputStream(file))) {

					int readed;
					for (byte buffer[] = new byte[1 << 10]; (readed = is
							.read(buffer)) != -1;) {
						out.write(buffer, 0, readed);
					}

				}

				return file;

			}
		} catch (IOException e) {

			throw new PicasaMediaDownloadException(e);
		}

	}

	/**
	 * @return tags associated with this photo
	 * @throws PicasaInformationCollectionException
	 */
	public List<String> getListOfTags()
			throws PicasaInformationCollectionException {

		try {
			URL feedUrl = new URL(photoEntry.getFeedLink().getHref()
					.replaceFirst("\\?.*", "?kind=tag"));
			Query tagsQuery = new Query(feedUrl);

			AlbumFeed searchResultsFeed = photoEntry.getService().query(
					tagsQuery, AlbumFeed.class);

			List<String> tags = new ArrayList<>();

			for (TagEntry tag : searchResultsFeed.getTagEntries()) {
				tags.add(tag.getTitle().getPlainText());
			}

			return tags;

		} catch (IOException | ServiceException e) {
			throw new PicasaInformationCollectionException(e);
		}

	}

	/**
	 * Deletes this photo
	 * 
	 * @throws PicasaDataModificationException
	 */
	public void delete() throws PicasaDataModificationException {
		try {
			photoEntry.delete();

		} catch (IOException | ServiceException e) {
			throw new PicasaDataModificationException(e);
		}
	}

	@Override
	public String toString() {
		return "Photo [name=" + photoEntry.getTitle().getPlainText() + "]";
	}

}
