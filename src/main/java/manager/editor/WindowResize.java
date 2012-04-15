package manager.editor;

import javax.swing.JDialog;

/**
 * Klasa opisuje okno dialogowe służące zmianie rozmiaru obrazka
 * @author Wojtek Jędras
 */
public class WindowResize extends JDialog {
	
	private static final long serialVersionUID = 1L;

	/**
     * Konstruktor - wymagany jest obraz do edycji
     * @param image - referencja do objektu klasy PixelData przechowująca obraz do edycji
     */
    public WindowResize(PixelData image) {
    }

    /**
     * Zwraca obraz po edycji
     * @return przetworzony obraz
     */
    public PixelData getTransformedImage() {
        return null;
    }
}
