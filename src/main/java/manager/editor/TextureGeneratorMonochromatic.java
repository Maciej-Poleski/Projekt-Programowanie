package manager.editor;

/**
 * Generator tekstury będącej monochromatyczną maską koloru
 * @author Patryk
 */
public class TextureGeneratorMonochromatic implements ITextureGenerator{
	private ColorRGB mRGB;
	
	/**
	 * Tworzy nowy generator monochromatyczny
	 * @param hsv - kolor w przesztrzeni barw HSV
	 */
	public TextureGeneratorMonochromatic(ColorHSV hsv){
		mRGB = ColorConverter.hsvTOrgb(hsv);
	}
	/**
	 * Tworzy nowy generator monochromatyczny
	 * @param rgb - kolor w przesztrzeni barw RGB
	 */
	public TextureGeneratorMonochromatic(ColorRGB rgb){
		mRGB = rgb;
	}
	/**
	 * Tworzy nowy generator monochromatyczny
	 * @param cmy - kolor w przesztrzeni barw CMY
	 */
	public TextureGeneratorMonochromatic(ColorCMY cmy){
		mRGB = ColorConverter.cmyTOrgb(cmy);
	}
	/**
	 * Zwraca kolor maski monochromatycznej
	 * @return kolor w przestrzeni RGB używany jako maska
	 */
	public ColorRGB getColor(){return mRGB;}
	/**
	 * Ustawia nowy kolor maski monochromatycznej
	 * @param color - nowy kolor maski z przestrzeni RGB
	 */
	public void setColor(ColorRGB color){mRGB = color;}
	
	@Override
	public void getValue(float fx, float fy, ColorRGB temp){
		temp.setR(mRGB.getR());
		temp.setG(mRGB.getG());
		temp.setB(mRGB.getB());
	}
}
