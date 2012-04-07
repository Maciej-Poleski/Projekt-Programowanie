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
	
	/**
	 * @return Filtr wykrywający krawędzie metodą Sobel'a typ poziomy
	 */
	public static IFilterMatrix edgeDetectionSobelHorizontal(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,2,1,0,0,0,-1,-2,-1}));
	}
	/**
	 * @return Filtr wykrywający krawędzie metodą Sobel'a typ pionowy
	 */
	public static IFilterMatrix edgeDetectionSobelVertical(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,0,-1,2,0,-2,1,0,-1}));
	}
	/**
	 * @return Filtr wykrywający krawędzie metodą Prewitt'a typ poziomy
	 */
	public static IFilterMatrix edgeDetectionPrewittHorizontal(){
		return new FilterMatrixAdapter(new Matrix(new float[]{-1,-1,-1,0,0,0,1,1,1}));
	}
	/**
	 * @return Filtr wykrywający krawędzie metodą Prewitt'a typ pionowy
	 */
	public static IFilterMatrix edgeDetectionPrewittVertical(){
		return new FilterMatrixAdapter(new Matrix(new float[]{1,0,-1,1,0,-1,1,0,-1}));
	}
}
