package manager.editor;

/**
 * Klasa implementująca gradientowy generatur tekstur działający w trybie liniowym.
 * Generator generuje tekstury w dowolnym kierunku i skali (jest bardzo uniwersalny)
 * @author Patryk
 */
public class TextureGeneratorGradientLinear implements ITextureGenerator{
	private Gradient mGrad;
	private Point mBegin, mEnd;
	/**
	 * Ustawia wektor kierunkowy dla gradientu wzdłuż którego zostaje wyznaczany
	 * gradient liniowy. Jeśli punkty wyznaczają wektor zerowy lub gradient jest nullem
	 * zostaje rzucony wyjątek
	 * @param beg - początek wektora
	 * @param end - koniec wektora
	 * @throws IllegalArgumentException - gdy wektor podany jako argument jest wektorem zerowym
	 * @throws NullPointerException - gdy gradient jest nullem
	 */
	TextureGeneratorGradientLinear(Gradient grad, Point beg, Point end){
		setVector(beg, end);
		setGradient(grad);
	}
	/**
	 * Ustawia wektor kierunkowy dla gradientu wzdłuż którego zostaje wyznaczany
	 * gradient liniowy. Jeśli punkty wyznaczają wektor zerowy zostaje rzucony wyjątek
	 * @param beg - początek wektora
	 * @param end - koniec wektora
	 * @throws IllegalArgumentException - gdy wektor podany jako argument jest wektorem zerowym
	 */
	public final void setVector(Point beg, Point end){
		if(Math.abs(beg.x - end.x) < ColorConverter.FLOAT_PRECISION && 
				Math.abs(beg.y - end.y) < ColorConverter.FLOAT_PRECISION){
			throw new IllegalArgumentException();
		}
		mBegin = beg;
		mEnd = end;
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
	public final void setGradient(Gradient grad){
		if(grad == null) {throw new NullPointerException();}
		mGrad = grad;
	}
	
	@Override
	public void getValue(float fx, float fy, ColorRGB temp) {
		float x = fx - mBegin.x, y = fy - mBegin.y;
		float a = mEnd.x - mBegin.x, b = mEnd.y - mBegin.y;
		float len = a*a+b*b;
		mGrad.interpolate(Math.max(0.0f, Math.min(1.0f, (a*x+b*y)/len)), temp);
	}
}
