package manager.files;

import java.awt.image.BufferedImage;

public final class ImageHolder {

	private final BufferedImage editableImage;
	private final FileID imageID;

	ImageHolder(BufferedImage editableImage, FileID imageID) {
		this.editableImage = editableImage;
		this.imageID = imageID;
	}

	public BufferedImage getBufferedImage() {
		return editableImage;
	}

	public FileID getImageID() {
		return imageID;
	}

}