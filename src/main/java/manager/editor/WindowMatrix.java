package manager.editor;

import javax.swing.JDialog;

/**
 * Klasa reprezentująca okienko do własnoręcznego zdefiniowania macierzy 
 * do filtru macierzowego aplikowanego dla obrazu
 * @author Mikołaj Bińkowski
 */
public class WindowMatrix extends JDialog{
	/**
	 * Konstruktor wymaga podania obrazu na którym pracujemy
	 * @param image - obraz
	 */
	WindowMatrix(PixelData image){
		
	}
	
	/**
	 * Zwraca obraz po edycji
	 * @return obraz po edycji
	 */
	public PixelData getTransformedImage(){
		return null;
	}
}
