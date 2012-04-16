/**
 * 
 */
package manager.files.picasa;

import java.util.Date;

import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.photos.AlbumEntry;

/**
 * @author Piotr Kolacz
 * 
 */
public abstract class AbstractAlbumPropertiesSetter{

	final AlbumEntry albumEntry;

	public AbstractAlbumPropertiesSetter(AlbumEntry albumEntry) {
		this.albumEntry = albumEntry;
	}

	/**
	 * Sets this album description
	 * 
	 * @param description
	 * @return
	 */
	public AbstractAlbumPropertiesSetter setDescription(String description) {
		albumEntry.setDescription(new PlainTextConstruct(description));
		return this;
	}

	/**
	 * Sets this album title
	 * 
	 * @param title
	 * @return
	 */
	public AbstractAlbumPropertiesSetter setTitle(String title) {
		albumEntry.setTitle(new PlainTextConstruct(title));
		return this;
	}

	
	/**
	 * Sets this album creation date
	 * 
	 * @param date
	 * @return
	 */
	public AbstractAlbumPropertiesSetter setDate(Date date) {
	 	albumEntry.setDate(date);
		return this;
	}

	/**
	 * Sets this album visibility
	 * 
	 * @param isPublic
	 * @return
	 */
	public AbstractAlbumPropertiesSetter setPublic(boolean isPublic) {
		String privateValue = "private";
		String publicValue = "public";
		albumEntry.setAccess(isPublic ? publicValue : privateValue);

		return this;
	}

	
	/**
	 * Executes action specific for extending class, e.g. update or create album.
	 * 
	 * @return
	 * @throws PicasaDataModificationException
	 */
	public abstract PicasaAlbum execute() throws PicasaDataModificationException;

}
