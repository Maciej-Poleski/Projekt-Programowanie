package manager.files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Collection;

import javax.imageio.ImageIO;

import manager.files.old.FileInfo;
import manager.tags.Tag;

/**
 * Klasa udostepnia podstawowe operacje na plikach związane z grafiką (jej
 * wyświetlanie i edycja zdjęć).
 * 
 * @author Jakub Cieśla
 */
public class ImageProviderForGUI {

	public static final class ImageHolder {

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

	/**
	 * Zapisuje plik po edycji.
	 * 
	 * @throws FileSaveException
	 *             Nieudany zapis pliku.
	 * @param editedImage
	 *            Obraz po edycji.
	 */
	void saveEditedImage(ImageHolder editedImage) throws FileSaveException {
		File file = FileInfo.getFile(editedImage.getImageID()); // (Marcin)
																// potrzebna
		// metoda zwracająca
		// ORGINALNA ścieżkę
		try {
			// Otwiera kanał na pliku, który ma być kopiowany
			FileChannel srcChannel = new FileInputStream(file).getChannel(); // problem
																				// z
																				// przerobieniem
																				// BufferImage
																				// na
																				// File,
																				// do
																				// konsultacji
																				// z
																				// Patrykiem

			// Otwiera kanał dla pliku docelowego
			FileChannel dstChannel = new FileOutputStream(file).getChannel();

			// Kopiuje zawartość z jednego do drugiego
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

			// Zamknięcie kanałów.
			srcChannel.close();
			dstChannel.close();
		} catch (Exception e) {
			throw new FileSaveException();
		}
	}

	/**
	 * Pobiera ID i zwraca obraz do edycji.
	 * 
	 * @throws FileNotAvailableException
	 *             Jeżeli niemożliwy dostęp do pliku.
	 * @return Obraz gotowy do edycji.
	 * @param ID
	 *            pliku do edycji.
	 */
	ImageHolder getImageForEdit(FileID fileID)
			throws FileNotAvailableException {
		try {
			File file = FileInfo.getFile(fileID);
			return new ImageHolder((BufferedImage) ImageIO.read(file), fileID);
		} catch (Exception e) {
			throw new FileNotAvailableException();
		}
	}
	
	Collection<ImageHolder> getImagesWithTags(Collection<Tag<?>> tags, boolean intersection){
		return null;
	}
	
}