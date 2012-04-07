package manager.editor;

import manager.editor.FilterGrayScale.FilterGrayScaleTypes;

/**
 * Fabryka Filtrów - generuje filtry gotowe do aplikowania na obrazie
 * @author Patryk
 */
public class FilterFactory {
	/**
	 * @return Filtr tworzący negatyw obrazu
	 */
	public static IFilter negative(){
		return new FilterNegative();
	}
	/**
	 * @return Filtr tworzący fotograficzny efekt sepii
	 */
	public static IFilterRange sepia(){
		return new FilterSepia();
	}
	/**
	 * @return Filtr tworzący obraz w skali szarości. Obraz generowany na podstawie średniej jasności
	 * danego pixela.
	 */
	public static IFilter grayScaleLightness(){
		return new FilterGrayScale(FilterGrayScaleTypes.LIGHTNESS);
	}
	/**
	 * @return Filtr tworzący obraz w skali szarości. Obraz generowany na podstawie średniej wartości
	 * składowych RGB danego pixela.
	 */
	public static IFilter grayScaleAverage(){
		return new FilterGrayScale(FilterGrayScaleTypes.AVERAGE);
	}
	/**
	 * @return Filtr tworzący obraz w skali szarości. Obraz generowany na podstawie nasycenia składowych RGB
	 * danego pixela, wykorzystuje większe wyczulenie ludzkiego oka na barwę zielona.
	 */
	public static IFilter grayScaleLuminosity(){
		return new FilterGrayScale(FilterGrayScaleTypes.LUMINOSITY);
	}
	/**
	 * @return Filtr odpowiedzialny za korekcję kanałów RGB
	 */
	public static IFilterRange RGBCorrection(){
		return new FilterRGBCorrection();
	}
	/**
	 * @return Filtr odpowiedzialny za korekcję kanałów CMY
	 */
	public static IFilterRange CMYCorrection(){
		return new FilterCMYCorrection();
	}
	/**
	 * @return Filtr odpowiedzialny za korekcję kanałów HSV
	 */
	public static IFilterRange HSVCorrection(){
		return new FilterHSVCorrection();
	}
	/**
	 * @return Filtr odpowiedzialny za korekcję kontrastu obrazu
	 */
	public static IFilterRange contrast(){
		return new FilterContrast();
	}
	/**
	 * @return Filtr odpowiedzialny za korekcję jasności obrazu
	 */
	public static IFilterRange brightness(){
		return new FilterBrightness();
	}
	/**
	 * @return Filtr odpowiedzialny za korekcję gamma obrazu
	 */
	public static IFilterRange gamma(){
		return new FilterGamma();
	}
	/**
	 * @return Filtr odpowiedzialny za korekcję ekspozucji obrazu (EV)
	 */
	public static IFilterRange exposure(){
		return new FilterExposure();
	}
	
	//EDGE DETECTING
	
	/**
	 * @return Filtr wykrywający krawędzie ukierunkowany na pionowe krawędzie
	 */
	public static IFilter edgeDetectionVertical(){
		return new FilterMatrixAdapter(new Matrix(new float[]{0,0,0, -1,1,0, 0,0,0}));
	}
	/**
	 * @return Filtr wykrywający krawędzie ukierunkowany na poziome krawędzie
	 */
	public static IFilter edgeDetectionHorizontal(){
		return new FilterMatrixAdapter(new Matrix(new float[]{0,-1,0, 0,1,0, 0,0,0}));
	}
	/**
	 * @return Filtr wykrywający krawędzie ukierunkowany na skośne krawędzie
	 */
	public static IFilter edgeDetectionDiagonal1(){
		return new FilterMatrixAdapter(new Matrix(new float[]{-1,0,0, 0,1,0, 0,0,0}));
	}
	/**
	 * @return Filtr wykrywający krawędzie ukierunkowany na skośne krawędzie
	 */
	public static IFilter edgeDetectionDiagonal2(){
		return new FilterMatrixAdapter(new Matrix(new float[]{0,0,-1, 0,1,0, 0,0,0}));
	}
	/**
	 * @return Filtr wykrywający krawędzie metodą Sobel'a typ poziomy
	 */
	public static IFilter edgeDetectionSobelHorizontal(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,2,1,0,0,0,-1,-2,-1}));
	}
	/**
	 * @return Filtr wykrywający krawędzie metodą Sobel'a typ pionowy
	 */
	public static IFilter edgeDetectionSobelVertical(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,0,-1,2,0,-2,1,0,-1}));
	}
	/**
	 * @return Filtr wykrywający krawędzie metodą Prewitt'a typ poziomy
	 */
	public static IFilter edgeDetectionPrewittHorizontal(){
		return new FilterMatrixAdapter(new Matrix(new float[]{-1,-1,-1,0,0,0,1,1,1}));
	}
	/**
	 * @return Filtr wykrywający krawędzie metodą Prewitt'a typ pionowy
	 */
	public static IFilter edgeDetectionPrewittVertical(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,0,-1,1,0,-1,1,0,-1}));
	}
	
	//EDGE DETECTING LAPLACE
	
	/**
	 * @return Filtr Laplace'a do wielokierunkowego wykrywania krawędzi - od pozostałych filtrów odróżnia
	 * go zdolność do tworzenia ostrzejszych krawędzi
	 */
	public static IFilter edgeDetectionLaplace1(){
		return new FilterMatrixAdapter(new Matrix(new float[]{0,-1,0, -1,4,-1, 0,-1,0}));
	}
	/**
	 * @return Filtr Laplace'a do wielokierunkowego wykrywania krawędzi - od pozostałych filtrów odróżnia
	 * go zdolność do tworzenia ostrzejszych krawędzi
	 */
	public static IFilter edgeDetectionLaplace2(){
		return new FilterMatrixAdapter(new Matrix(new float[]{-1,-1,-1, -1,8,-1, -1,-1,-1}));
	}
	/**
	 * @return Filtr Laplace'a do wielokierunkowego wykrywania krawędzi - od pozostałych filtrów odróżnia
	 * go zdolność do tworzenia ostrzejszych krawędzi
	 */
	public static IFilter edgeDetectionLaplace3(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,-2,1, -2,4,-2, 1,-2,1}));
	}
	/**
	 * @return Filtr Laplace'a do przekątniowego wykrywania krawędzi
	 */
	public static IFilter edgeDetectionLaplaceDiagonal(){
		return new FilterMatrixAdapter(new Matrix(new float[]{-1,0,-1, 0,4,0, -1,0,-1}));
	}
	/**
	 * @return Filtr Laplace'a do wykrywania krawędzi poziomych
	 */
	public static IFilter edgeDetectionLaplaceHorizontal(){
		return new FilterMatrixAdapter(new Matrix(new float[]{0,-1,0, 0,2,0, 0,-1,0}));
	}
	/**
	 * @return Filtr Laplace'a do wykrywania krawędzi pionowych
	 */
	public static IFilter edgeDetectionLaplaceVertical(){
		return new FilterMatrixAdapter(new Matrix(new float[]{0,0,0, -1,2,-1, 0,0,0}));
	}
	
	//GRADIENT DIRECTIONAL EDGE DETECTING
	
	/**
	 * @return Filtr gradientowy wykrywający krawędzie w kierunku wschodnim
	 */
	public static IFilter edgeDetectionGradientDirectionalEast(){
		return new FilterMatrixAdapter(new Matrix(new float[]{-1,1,1, -1,-2,1, -1,1,1}));
	}
	/**
	 * @return Filtr gradientowy wykrywający krawędzie w kierunku południowo-wschodnim
	 */
	public static IFilter edgeDetectionGradientDirectionalSouthEast(){
		return new FilterMatrixAdapter(new Matrix(new float[]{-1,-1,1, -1,-2,1, 1,1,1}));
	}
	/**
	 * @return Filtr gradientowy wykrywający krawędzie w kierunku południowym
	 */
	public static IFilter edgeDetectionGradientDirectionalSouth(){
		return new FilterMatrixAdapter(new Matrix(new float[]{-1,-1,-1, 1,-2,1, 1,1,1}));
	}
	/**
	 * @return Filtr gradientowy wykrywający krawędzie w kierunku południowo-zachodnim
	 */
	public static IFilter edgeDetectionGradientDirectionalSouthWest(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,-1,-1, 1,-2,-1, 1,1,1}));
	}
	/**
	 * @return Filtr gradientowy wykrywający krawędzie w kierunku zachodnim
	 */
	public static IFilter edgeDetectionGradientDirectionalWest(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,1,-1, 1,-2,-1, 1,1,-1}));
	}
	/**
	 * @return Filtr gradientowy wykrywający krawędzie w kierunku północno-zachodnim
	 */
	public static IFilter edgeDetectionGradientDirectionalNorthWest(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,1,1, 1,-2,-1, 1,-1,-1}));
	}
	/**
	 * @return Filtr gradientowy wykrywający krawędzie w kierunku północnym
	 */
	public static IFilter edgeDetectionGradientDirectionalNorth(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,1,1, 1,-2,1, -1,-1,-1}));
	}
	/**
	 * @return Filtr gradientowy wykrywający krawędzie w kierunku północn0-wschodnim
	 */
	public static IFilter edgeDetectionGradientDirectionalNorthEast(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,1,1, -1,-2,1, -1,-1,1}));
	}
	
	//LOW PASS FILTERS
	
	/**
	 * @return Filtr dolnoprzepustowy uśredniający - podstawowy filtr uśredniający, uśrednia wartość
	 * aktualnego pixela wraz ze swoimi 8 sąsiadami
	 */
	public static IFilter lowPassAverage3x3(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,1,1, 1,1,1, 1,1,1}));
	}
	/**
	 * @return Filtr dolnoprzepustowy uśredniający - podstawowy filtr uśredniający, uśrednia wartość
	 * aktualnego pixela wraz ze swoimi 24 sąsiadami
	 */
	public static IFilter lowPassAverage5x5(){
		return new FilterMatrixAdapter(new Matrix(
				new float[]{1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1}));
	}
	/**
	 * @return Filtr dolnoprzepustowy uśredniający - podstawowy filtr uśredniający, uśrednia wartość
	 * aktualnego pixela wraz ze swoimi 20 sąsiadami (leżacymi najbliżej na kole)
	 */
	public static IFilter lowPassAverageCircle(){
		return new FilterMatrixAdapter(new Matrix(
				new float[]{0,1,1,1,0, 1,1,1,1,1, 1,1,1,1,1, 1,1,1,1,1, 0,1,1,1,0}));
	}
	/**
	 * @return Filtr dolnoprzepustowy uśredniający - uśrednia wartość
	 * aktualnego pixela wraz ze swoimi 8 sąsiadami, zwiększenie wagi powoduje mniejsze rozmycie
	 * niż w przypadku filtru klasycznego
	 */
	public static IFilter lowPassLP1(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,1,1, 1,2,1, 1,1,1}));
	}
	/**
	 * @return Filtr dolnoprzepustowy uśredniający - uśrednia wartość
	 * aktualnego pixela wraz ze swoimi 8 sąsiadami, jeszcze mniejsze rozmycie niż w przypadku LP1
	 */
	public static IFilter lowPassLP2(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,1,1, 1,4,1, 1,1,1}));
	}
	/**
	 * @return Filtr dolnoprzepustowy uśredniający - uśrednia wartość
	 * aktualnego pixela wraz ze swoimi 8 sąsiadami, jeszcze mniejsze rozmycie niż w przypadku LP2 i LP1
	 */
	public static IFilter lowPassLP3(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,1,1, 1,12,1, 1,1,1}));
	}
	/**
	 * @return Filtr dolnoprzepustowy piramidalny - nazwa bierze się od rozkładu czynników w macierzy,
	 * które układają się wysokościowo w piramidę - waga pixela maleje wraz z oddalaniem od źródła
	 */
	public static IFilter lowPassPyramid(){
		return new FilterMatrixAdapter(new Matrix(
				new float[]{1,2,3,2,1, 2,4,6,4,2, 3,6,9,6,3, 2,4,6,4,2, 1,2,3,2,1}));
	}
	/**
	 * @return Filtr dolnoprzepustowy stożkowy - nazwa bierze się od rozkładu czynników w macierzy,
	 * które układają się wysokościowo w stożek - waga pixela maleje wraz z oddalaniem od źródła
	 */
	public static IFilter lowPassConical(){
		return new FilterMatrixAdapter(new Matrix(
				new float[]{0,0,1,0,0, 0,2,2,2,0, 1,2,5,2,1, 0,2,2,2,0, 0,0,1,0,0}));
	}
	/**
	 * @return Filtr dolnoprzepustowy Gaussa - wartość wynikowa jest wynikiem funkcji rozkładu
	 * normalnego - krzywej Gaussa - znaczenie punktu maleje wraz z oddalaniem od źródła
	 */
	public static IFilter lowPassGauss1(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,2,1, 2,4,2, 1,2,1}));
	}
	/**
	 * @return Filtr dolnoprzepustowy Gaussa - wartość wynikowa jest wynikiem funkcji rozkładu
	 * normalnego - krzywej Gaussa - znaczenie punktu maleje wraz z oddalaniem od źródła, 
	 * dokładniejszy od Gauss1 - analizuje większą liczbę pixeli
	 */
	public static IFilter lowPassGauss2(){
		return new FilterMatrixAdapter(new Matrix(
				new float[]{1,1,2,1,1, 1,2,4,2,1, 2,4,8,4,2, 1,2,4,2,1, 1,1,2,1,1}));
	}
	/**
	 * @return Filtr dolnoprzepustowy Gaussa - wartość wynikowa jest wynikiem funkcji rozkładu
	 * normalnego - krzywej Gaussa - znaczenie punktu maleje wraz z oddalaniem od źródła, 
	 * zmienione czynniki w stosunku do Gauss2
	 */
	public static IFilter lowPassGauss3(){
		return new FilterMatrixAdapter(new Matrix(
				new float[]{0,1,2,1,0, 1,4,8,4,1, 2,8,16,8,2, 1,4,8,4,1, 0,1,2,1,0}));
	}
	/**
	 * @return Filtr dolnoprzepustowy Gaussa - wartość wynikowa jest wynikiem funkcji rozkładu
	 * normalnego - krzywej Gaussa - znaczenie punktu maleje wraz z oddalaniem od źródła, 
	 * zmienione czynniki w stosunku do Gauss2 i Gauss3
	 */
	public static IFilter lowPassGauss4(){
		return new FilterMatrixAdapter(new Matrix(
				new float[]{1,4,7,4,1, 4,16,26,16,4, 7,26,41,26,7, 4,16,26,16,4, 1,4,7,4,1}));
	}
	/**
	 * @return Filtr dolnoprzepustowy Gaussa - wersja 7x7 - obejmuje 49 pixeli
	 */
	public static IFilter lowPassGauss5(){
		return new FilterMatrixAdapter(new Matrix(
				new float[]{1,1,2,2,2,1,1, 
						1,2,2,4,2,2,1, 
						2,2,4,8,4,2,2,
						2,4,8,16,8,4,2,
						2,2,4,8,4,2,2,
						1,2,2,4,2,2,1, 
						1,1,2,2,2,1,1}));
	}
	
	//HIGH PASS FILTERS
	
	/**
	 * @return Filtr górnoprzepustowy - usuwa średnią, od wartości pixela oderwane są pixele sąsiadujące
	 * jest to podstawowy filtr wyostrzający szumy i krawędzie
	 */
	public static IFilter highPassMeanRemoval(){
		return new FilterMatrixAdapter(new Matrix(new float[]{-1,-1,-1, -1,9,-1, -1,-1,-1}));
	}
	/**
	 * @return Filtr górnoprzepustowy - wyostrzający charakteryzuje się mniejszym wyostrzeniem szumów niż
	 * MeanRemoval
	 */
	public static IFilter highPassHP1(){
		return new FilterMatrixAdapter(new Matrix(new float[]{0,-1,0, -1,5,-1, 0,-1,0}));
	}
	/**
	 * @return Filtr górnoprzepustowy - wyostrzający w stosunku do HP1 zmienione współczynniki
	 */
	public static IFilter highPassHP2(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,-2,1, -2,5,-2, 1,-2,1}));
	}
	/**
	 * @return Filtr górnoprzepustowy - wyostrzający większy nacisk na dany pixel niż w HP1
	 */
	public static IFilter highPassHP3(){
		return new FilterMatrixAdapter(new Matrix(new float[]{0,-1,0, -1,20,-1, 0,-1,0}));
	}
	
	//EMBOSSING FILTERS
	
	/**
	 * @return Filtr uwypuklający - wprowadza złudzenie wypukłości tworząc efekt płaskorzeźby,
	 * działa w kierunku wschodnim
	 */
	public static IFilter embossEast(){
		return new FilterMatrixAdapter(new Matrix(new float[]{-1,0,1, -1,1,1, -1,0,1}));
	}
	/**
	 * @return Filtr uwypuklający - wprowadza złudzenie wypukłości tworząc efekt płaskorzeźby,
	 * działa w kierunku południowo-wschodnim
	 */
	public static IFilter embossSouthEast(){
		return new FilterMatrixAdapter(new Matrix(new float[]{-1,-1,0, -1,1,1, 0,1,1}));
	}
	/**
	 * @return Filtr uwypuklający - wprowadza złudzenie wypukłości tworząc efekt płaskorzeźby,
	 * działa w kierunku południowym
	 */
	public static IFilter embossSouth(){
		return new FilterMatrixAdapter(new Matrix(new float[]{-1,-1,-1, 0,1,0, 1,1,1}));
	}
	/**
	 * @return Filtr uwypuklający - wprowadza złudzenie wypukłości tworząc efekt płaskorzeźby,
	 * działa w kierunku południowo-zachodnim
	 */
	public static IFilter embossSouthWest(){
		return new FilterMatrixAdapter(new Matrix(new float[]{0,-1,-1, 1,1,-1, 1,1,0}));
	}
	/**
	 * @return Filtr uwypuklający - wprowadza złudzenie wypukłości tworząc efekt płaskorzeźby,
	 * działa w kierunku zachodnim
	 */
	public static IFilter embossWest(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,0,-1, 1,1,-1, 1,0,-1}));
	}
	/**
	 * @return Filtr uwypuklający - wprowadza złudzenie wypukłości tworząc efekt płaskorzeźby,
	 * działa w kierunku północno-zachodnim
	 */
	public static IFilter embossNorthWest(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,1,0, 1,1,-1, 0,-1,-1}));
	}
	/**
	 * @return Filtr uwypuklający - wprowadza złudzenie wypukłości tworząc efekt płaskorzeźby,
	 * działa w kierunku północnym
	 */
	public static IFilter embossNorth(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,1,1, 0,1,0, -1,0,-1}));
	}
	/**
	 * @return Filtr uwypuklający - wprowadza złudzenie wypukłości tworząc efekt płaskorzeźby,
	 * działa w kierunku północno-wschodnim
	 */
	public static IFilter embossNorthEast(){
		return new FilterMatrixAdapter(new Matrix(new float[]{0,1,1, -1,1,1, -1,-1,0}));
	}
}
