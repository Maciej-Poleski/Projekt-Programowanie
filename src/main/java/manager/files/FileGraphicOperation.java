package manager.files;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

/**
 * Klasa udostepnia podstawowe operacje na plikach związane z grafiką (jej
 * wyświetlanie i edycja zdjęć).
 * 
 * @author Jakub Cieśla
 */
public class FileGraphicOperation {

	/**
	 * Zapisuje plik po edycji.
	 * 
	 * @throws FileSaveException Nieudany zapis pliku.
	 * @param editedImage Obraz po edycji.
	 */
	void saveEditedImage(ImageHolder editedImage) throws FileSaveException {
		File file = FileInfo.getFile(editedImage.getImageID()); // (Marcin) potrzebna
															// metoda zwracająca
															// ORGINALNA ścieżkę
		try {
			ImageIO.write(editedImage.getBufferedImage(), "jpg", file); //potrzebne dodanie obsługi większej ilości typów plików
		} catch (Exception e) {
			throw new FileSaveException();
		}
	}

	/**
	 * Pobiera ID i zwraca obraz do edycji.
	 * 
	 * @throws FileNotAccessibleException Jeżeli niemożliwy dostęp do pliku.
	 * @return Obraz gotowy do edycji.
	 * @param fileID ID pliku do edycji.
	 */
	ImageHolder getImageForEdit(FileID fileID)
			throws FileNotAccessibleException {
		try {
			File file = FileInfo.getFile(fileID);
			return new ImageHolder((BufferedImage) ImageIO.read(file), fileID);
		} catch (Exception e) {
			throw new FileNotAccessibleException();
		}
	}
}