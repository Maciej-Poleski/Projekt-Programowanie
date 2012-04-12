package manager.editor;

/**
 * Klasa implementująca gradientowy generatur tekstur działający w kierunku pionowym
 * @author Patryk
 */
public class TextureGeneratorGradientVertical implements ITextureGenerator{
	private Gradient mGrad;
	
	/**
	 * @param grad - referencja na Gradient który posłuży do generacji tekstury
	 */
	public TextureGeneratorGradientVertical(Gradient grad){
		setGradient(grad);
	}
	
	/**
	 * Zwraca gradient generujący
	 * @return aktulany gradient generujący
	 */
	public Gradient getGradient(){
		return mGrad;
	}
	
	/**
	 * Ustawia gradient generujący teksture, gdy <b>null</b> rzuca wyjątek, 
	 * bo generator musi na czymś pracować
	 * @param grad - Gradient który posłuży do generacji tekstury
	 */
	public void setGradient(Gradient grad){
		if(grad == null) throw new NullPointerException();
		mGrad = grad;
	}
	
	@Override
	public void getValue(float fx, float fy, ColorRGB temp) {
		mGrad.interpolate(Math.max(0.0f, Math.min(1.0f, fy)), temp);
	}
}