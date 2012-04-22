package manager.files.backup;

import java.awt.image.BufferedImage;
import manager.files.FileID;

/**
 * Klasa reprezentująca uchwyt do pliku. Służy ona do współpracy z edytorem
 * grafiki (ułatwia mu pracę przez podanie gotowego do użycia BufferedImage.
 * 
 * @author Jakub Cieśla
 * 
 */
public class ImageHolder {
	private final BufferedImage editableImage;
	private final FileID imageID;
	private final String type;

	public ImageHolder(BufferedImage editableImage, FileID imageID, String type) {
		this.editableImage = editableImage;
		this.imageID = imageID;
		this.type = type;
	}

	/**
	 * Fukcja zwraca BufferedImage z uchwytu.
	 * 
	 * @return BufferedImage Stworzony z danego pliku.
	 */
	public BufferedImage getBufferedImage() {
		return this.editableImage;
	}

	/**
	 * Fukcja zwraca FileID pliku z uchwytu.
	 * 
	 * @return FileID pliku, do którego "przyczepiony" jest dany uchwyt.
	 */
	public FileID getFileId() {
		return this.imageID;
	}

	/**
	 * Funkcja zwraca typ pliku z uchwytu.
	 * 
	 * @return Typ pliku, do którego "przyczepiony" jest dany uchwyt.
	 */
	public String getType() {
		return this.type;
	}
}