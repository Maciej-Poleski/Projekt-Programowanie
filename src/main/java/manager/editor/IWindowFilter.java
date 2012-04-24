package manager.editor;
/**
 * Podstawowy interfejs reprezentujący okienko służace do modyfikacji ustawień danego filtru
 * @author  Marcin Regdos
 */
interface IWindowFilter {
	/**
     * Zwraca zmodyfikowaną przez filtr kopię obrazu. Jeśli użytkownik nie zastosuje filtru, zwraca nulla.
     */
	PixelData showDialog();
}
