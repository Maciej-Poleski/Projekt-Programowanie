package manager.editor;

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
	public static IFilter sepia(){
		return new FilterSepia();
	}
}
