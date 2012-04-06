package manager.editor;

import manager.editor.FilterGrayScale.FilterGrayScaleTypes;

/**
 * Fabryka Filtrów - generuje filtry gotowe do aplikowania na obrazie
 * @author Patryk
 */
public class FilterFactory {
	/**
	 * @return Filtr tworz¹cy negatyw obrazu
	 */
	public static IFilter negative(){
		return new FilterNegative();
	}
	/**
	 * @return Filtr tworz¹cy fotograficzny efekt sepii
	 */
	public static IFilterRange sepia(){
		return new FilterSepia();
	}
	/**
	 * @return Filtr tworz¹cy obraz w skali szaroœci. Obraz generowany na podstawie œredniej jasnoœci
	 * danego pixela.
	 */
	public static IFilter grayScaleLightness(){
		return new FilterGrayScale(FilterGrayScaleTypes.LIGHTNESS);
	}
	/**
	 * @return Filtr tworz¹cy obraz w skali szaroœci. Obraz generowany na podstawie œredniej wartoœci
	 * sk³adowych RGB danego pixela.
	 */
	public static IFilter grayScaleAverage(){
		return new FilterGrayScale(FilterGrayScaleTypes.AVERAGE);
	}
	/**
	 * @return Filtr tworz¹cy obraz w skali szaroœci. Obraz generowany na podstawie nasycenia sk³adowych RGB
	 * danego pixela, wykorzystuje wiêksze wyczulenie ludzkiego oka na barwê zielon¹.
	 */
	public static IFilter grayScaleLuminosity(){
		return new FilterGrayScale(FilterGrayScaleTypes.LUMINOSITY);
	}
	
	/**
	 * @return Filtr odpowiedzialny za korekcjê kana³ów RGB
	 */
	public static IFilterRange RGBCorrection(){
		return new FilterRGBCorrection();
	}
}
