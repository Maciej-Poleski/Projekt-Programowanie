package manager.files;

import java.awt.image.BufferedImage;

public final class ImageHolder {

	protected final BufferedImage editableImage;

	protected final FileID imageID;

	/**
	 * 
	 * @ return @ param @ author
	 */
	ImageHolder(BufferedImage editableImage, FileID imageID) {
		this.editableImage = editableImage;
		this.imageID = imageID;
	}

	/**
	 * 
	 * @ return @ param @ author
	 */
	public BufferedImage getBufferedImage() {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((editableImage == null) ? 0 : editableImage.hashCode());
		result = prime * result + ((imageID == null) ? 0 : imageID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImageHolder other = (ImageHolder) obj;
		if (editableImage == null) {
			if (other.editableImage != null)
				return false;
		} else if (!editableImage.equals(other.editableImage))
			return false;
		if (imageID == null) {
			if (other.imageID != null)
				return false;
		} else if (!imageID.equals(other.imageID))
			return false;
		return true;
	}

}