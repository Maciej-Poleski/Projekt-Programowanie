package manager.files.picasa;

/**
 * Holds file formats supported by Picasaweb
 * 
 * @author Piotr Kolacz
 * 
 */
public enum PicasaAlbumMediaType {

	JPEG {
		public String toString() {
			return "image/jpeg";
		}
	},
	TIFF {
		public String toString() {
			return "image/tiff";
		}
	},
	PNG {
		public String toString() {
			return "image/png";
		}
	},
	GIF {
		public String toString() {
			return "image/gif";
		}
	},
	BMP {
		public String toString() {
			return "image/bmp";
		}
	};
}