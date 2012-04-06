package manager.editor;

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
	public static IFilter sepia(){
		return new FilterSepia();
	}
}
