package manager.editor;

import javax.swing.*;
import java.awt.image.BufferedImage;

/**
 * Klasa reprezentuje okno dialogowe w którym dokonywana jest edycja obrazu
 * @author Marcin Regdos
 */
public class EditWindow extends JDialog {
    
	private static final long serialVersionUID = 1L;

	/**
     * Konstruktor - wymagany jest obraz do edycji
     * @param image - referencja do objektu klasy BufferedImage przechowująca obraz do edycji
     */
    public EditWindow(BufferedImage image) {
    }

    /**
     * Zwraca obraz po edycji
     * @return przetworzony obraz
     */
    public BufferedImage getTransformedImage() {
        return null;
    }
}
