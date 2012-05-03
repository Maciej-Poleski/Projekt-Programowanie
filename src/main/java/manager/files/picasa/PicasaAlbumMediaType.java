package manager.files.picasa;

/**
 * Holds MIME file formats supported by Picasaweb.
 * 
 * @author Piotr Kolacz
 * 
 */
public enum PicasaAlbumMediaType {

	/**
	 * Represents MIME type for JPEG.
	 * 
	 */
	JPEG {
		public String toString() {
			return "image/jpeg";
		}
	},
	/**
	 * Represents MIME type for TIFF.
	 * 
	 */
	TIFF {
		public String toString() {
			return "image/tiff";
		}
	},
	/**
	 * Represents MIME type for PNG
	 * 
	 */
	PNG {
		public String toString() {
			return "image/png";
		}
	},
	/**
	 * Represents MIME type for GIF
	 * 
	 */
	GIF {
		public String toString() {
			return "image/gif";
		}
	},
	/**
	 * Represents MIME type for BMP
	 * 
	 */
	BMP {
		public String toString() {
			return "image/bmp";
		}
	};
}