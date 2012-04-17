package manager.editor;

/**
 * Klasa udostępnia generatory tekstur
 * Próba utworzenia obiektu tej klasy nawet przy użyciu refleksji 
 * skończy się wyjątkiem <b>UnsupportedOperationException</B>
 * @author Patryk
 */
public final class TexturerFactory {
	private TexturerFactory(){
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Zwraca monochromatyczna maskę
	 * @param color - kolor maski
	 * @return monochromatyczy generator tekstur
	 */
	public static FilterTexturer monochromatic(ColorRGB color){
		return new FilterTexturer(new TextureGeneratorMonochromatic(color));
	}
	/**
	 * Zwraca monochromatyczna maskę
	 * @param color - kolor maski
	 * @return monochromatyczy generator tekstur
	 */
	public static FilterTexturer monochromatic(ColorCMY color){
		return new FilterTexturer(new TextureGeneratorMonochromatic(color));
	}
	/**
	 * Zwraca monochromatyczna maskę
	 * @param color - kolor maski
	 * @return monochromatyczy generator tekstur
	 */
	public static FilterTexturer monochromatic(ColorHSV color){
		return new FilterTexturer(new TextureGeneratorMonochromatic(color));
	}
	
	/**
	 * Zwraca pionowy generator gradientowy o interpolacji liniowej
	 * @param grad - gradient na którym pracuje generator
	 * @return generator tekstur będących pionowymi gradientami liniowymi
	 */
	public static FilterTexturer gradientLinearVertical(Gradient grad){
		return new FilterTexturer(new TextureGeneratorGradientLinear(grad, new Point(0,0), new Point(0,1)));
	}
	/**
	 * Zwraca poziomy generator gradientowy o interpolacji liniowej
	 * @param grad - gradient na którym pracuje generator
	 * @return generator tekstur będących poziomymi gradientami liniowymi
	 */
	public static FilterTexturer gradientLinearHorizontal(Gradient grad){
		return new FilterTexturer(new TextureGeneratorGradientLinear(grad, new Point(0,0), new Point(1,0)));
	}
	/**
	 * Zwraca skośny generator gradientowy o interpolacji liniowej pracujący na przekątnej
	 * lewy górny do prawy dolny
	 * @param grad - gradient na którym pracuje generator
	 * @return generator tekstur będących skośnymi gradientami liniowymi
	 */
	public static FilterTexturer gradientLinearDiagonal1(Gradient grad){
		return new FilterTexturer(new TextureGeneratorGradientLinear(grad, new Point(0,0), new Point(1,1)));
	}
	/**
	 * Zwraca skośny generator gradientowy o interpolacji liniowej pracujący na przekątnej
	 * prawy górny do lewy dolny
	 * @param grad - gradient na którym pracuje generator
	 * @return generator tekstur będących skosnymi gradientami liniowymi
	 */
	public static FilterTexturer gradientLinearDiagonal2(Gradient grad){
		return new FilterTexturer(new TextureGeneratorGradientLinear(grad, new Point(1,0), new Point(0,1)));
	}
	
	
	/**
	 * Zwraca kołowy generator gradientowy
	 * @param grad - gradient na którym pracuje generator
	 * @return generator tekstur będących kołowymi gradientami
	 */
	public static FilterTexturer gradientCircle(Gradient grad){
		return new FilterTexturer(new TextureGeneratorGradientCircle(grad, new Point(0.5f,0.5f), new Point(0,0)));
	}
}
