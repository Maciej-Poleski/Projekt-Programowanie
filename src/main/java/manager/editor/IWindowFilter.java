package manager.editor;
/**
 * Podstawowy interfejs reprezentujący okienko służace do modyfikacji ustawień danego filtru
 * @author  Marcin Regdos
 */
public interface IWindowFilter {
	/**
     * Zwraca zmodyfikowaną przez filtr kopię obrazu. Jeśli użytkownik nie zastosuje filtru, zwraca nulla.
     */
	public PixelData showDialog();
}
