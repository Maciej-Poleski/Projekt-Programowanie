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

	public PicasaPhoto(PhotoEntry photoEntry) {
		this.photoEntry = photoEntry;

	}

	public String getTitle() {
		return photoEntry.getTitle().getPlainText();
	}

	public String getDescription() {
		return photoEntry.getDescription().getPlainText();
	}

	public String getAlbumId() {
		return photoEntry.getAlbumId();
	}

	public File downloadPhoto(String downloadDirectory)
			throws PicasaMediaDownloadException {

		try {
			String photoUrl = photoEntry.getMediaContents().get(0).getUrl();
			int index = photoUrl.lastIndexOf("/");
			photoUrl = photoUrl.substring(0, index + 1) + "s2000"
					+ photoUrl.substring(index);

			System.out.println(photoUrl);

			URL url = new URL(photoUrl);

			try (InputStream is = url.openStream()) {

				File file = new File(downloadDirectory + "/" + getTitle());

				try (OutputStream out = new BufferedOutputStream(
						new FileOutputStream(file))) {

					for (int b; (b = is.read()) != -1;) {
						out.write(b);
					}

				}

				return file;

			}
		} catch (IOException e) {

			throw new PicasaMediaDownloadException();
		}
	}

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
