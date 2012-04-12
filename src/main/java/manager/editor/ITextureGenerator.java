package manager.editor;

/**
 * Interfejs, który jest implemetowany przez klasy będące generatorami tekstur (np gradientowych masek)
 * @author Patryk
 */
public interface ITextureGenerator {
	/**
	 * Zwraca wynik pracy generatora na podanych współrzędnych pod referencje <b>temp</b>
	 * @param fx - współrzędna pozioma w teksturze z przedziału [0,1]
	 * @param fy - współrzędna pionowa w teksturze z przedziału [0,1]
	 * @return Kolor pixela tekstury w przestrzeni barw RGB
	 */
	void getValue(float fx, float fy, ColorRGB temp);
}
