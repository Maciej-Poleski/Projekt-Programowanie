package manager.editor;

import manager.editor.FilterGrayScale.FilterGrayScaleTypes;

/**
 * Fabryka Filtr�w - generuje filtry gotowe do aplikowania na obrazie
 * @author Patryk
 */
public class FilterFactory {
	/**
	 * @return Filtr tworz�cy negatyw obrazu
	 */
	public static IFilter negative(){
		return new FilterNegative();
	}
	/**
	 * @return Filtr tworz�cy fotograficzny efekt sepii
	 */
	public static IFilterRange sepia(){
		return new FilterSepia();
	}
	/**
	 * @return Filtr tworz�cy obraz w skali szaro�ci. Obraz generowany na podstawie �redniej jasno�ci
	 * danego pixela.
	 */
	public static IFilter grayScaleLightness(){
		return new FilterGrayScale(FilterGrayScaleTypes.LIGHTNESS);
	}
	/**
	 * @return Filtr tworz�cy obraz w skali szaro�ci. Obraz generowany na podstawie �redniej warto�ci
	 * sk�adowych RGB danego pixela.
	 */
	public static IFilter grayScaleAverage(){
		return new FilterGrayScale(FilterGrayScaleTypes.AVERAGE);
	}
	/**
	 * @return Filtr tworz�cy obraz w skali szaro�ci. Obraz generowany na podstawie nasycenia sk�adowych RGB
	 * danego pixela, wykorzystuje wi�ksze wyczulenie ludzkiego oka na barw� zielon�.
	 */
	public static IFilter grayScaleLuminosity(){
		return new FilterGrayScale(FilterGrayScaleTypes.LUMINOSITY);
	}
	
	/**
	 * @return Filtr odpowiedzialny za korekcj� kana��w RGB
	 */
	public static IFilterRange RGBCorrection(){
		return new FilterRGBCorrection();
	}
}
