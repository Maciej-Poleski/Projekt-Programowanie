package manager.editor;

import javax.swing.JDialog;

/**
 * Klasa reprezentująca okno dla filtrów do edycji zakresowej
 * @author Marcin Regdos
 */
public class WindowRange extends JDialog {
	/**
	 * Konstruktor wymaga podania obrazu na którym filtr ma pracować oraz samego filtru
	 * @param image - obraz do edycji
	 */
	WindowRange(PixelData image, IFilterRange iFilter){
		
	}
	
	/**
	 * Zwraca obraz po edycji
	 * @return obraz po edycji
	 */
	public PixelData getTransformedImage(){
		return null;
	}
}
