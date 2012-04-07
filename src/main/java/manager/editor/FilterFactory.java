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
}
