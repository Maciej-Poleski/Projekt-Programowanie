package manager.editor;

/**
 * Klasa udostępnia filtry tworzące fizycznie nowe obrazy (o innych parametrach rozmiaru np).
 * - zmiana rozmiaru
 * - obroty obrazy
 * - symetria obrazu
 * Klasa ta jest wyróżniona, gdyż z uwagi na fakt, że większość jej filtrów tworzy obraz fizycznie innych 
 * rozmiarów, posiadają one odwróconą metodę <b>PixelData apply(PixelData)</b> (zwracany jest obraz PO
 * obróbce a nie jak w wszystkich innych filtrach PRZED, zaś parametr jest nie zmieniany)
 * @author Patryk
 */
public class ImageMaker {
	/**
	 * @param width - długość obrazu wynikowego
	 * @param height - wysokość obrazu wynikowego
	 * @return Filtr zmieniający wielkość obrazu metoda najbliższego sąsiada
	 */
	public static IFilter resizeNearestNeighbour(int width, int height){
		return new FilterImageResizeNearestNeighbour(width, height);
	}
	/**
	 * @param width - długość obrazu wynikowego
	 * @param height - wysokość obrazu wynikowego
	 * @return Filtr zmieniający wielkość obrazu metoda próbkowania dwuliniowego
	 */
	public static IFilter resizeBilinear(int width, int height){
		return new FilterImageResizeBilinear(width, height);
	}
	/**
	 * @return Filtr tworzący obraz obrócony o 90 stopni zgodnie z ruchem wskazówek zegara
	 */
	public static IFilter rotate90(){
		return new FilterImageRotate90();
	}
	/**
	 * @return Filtr tworzący obraz obrócony o 90 stopni przeciwnie do ruchu wskazówek zegara
	 */
	public static IFilter rotate270(){
		return new FilterImageRotate270();
	}
	/**
	 * @return Filtr tworzący obraz odbity względem osi pionowej
	 */
	public static IFilter symetryOX(){
		return new FilterImageSymetryOX();
	}
	/**
	 * @return Filtr tworzący obraz odbity względem osi poziomej
	 */
	public static IFilter symetryOY(){
		return new FilterImageSymetryOY();
	}
}
