package manager.editor;

/**
 * Klasa implementująca gradientowy generatur tekstur działający w trybie kołowym.
 * Generator generuje tekstury będące gradientami kołowymi o danym środku i promieniu 
 * wyznaczonym przez dwa punkty w przestrzeni RxR
 * @author Patryk
 */
public class TextureGeneratorGradientCircle implements ITextureGenerator{
	private Gradient mGrad;
	private Point mBegin, mEnd;
	private float mRadius;
	/**
	 * Ustawia wektor kierunkowy dla gradientu, wyznacza on środek oraz promień gradientu. 
	 * Jeśli punkty wyznaczają wektor zerowy lub gradient jest nullem
	 * zostaje rzucony wyjątek
	 * @param beg - początek wektora
	 * @param end - koniec wektora
	 * @throws IllegalArgumentException - gdy wektor podany jako argument jest wektorem zerowym
	 * @throws NullPointerException - gdy gradient jest nullem
	 */
	TextureGeneratorGradientCircle(Gradient grad, Point beg, Point end){
		setVector(beg, end);
		setGradient(grad);
	}
	/**
	 * Ustawia wektor kierunkowy dla gradientu, wyznacza on środek oraz promień gradientu. 
	 * Jeśli punkty wyznaczają wektor zerowy lub gradient jest nullem
	 * zostaje rzucony wyjątek
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
		float dX = mEnd.x-mBegin.x;
		float dY = mEnd.y-mBegin.y;
		mRadius = (float)Math.sqrt(dX*dX+dY*dY);
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
		float distance = (float)Math.sqrt((mBegin.x-fx)*(mBegin.x-fx) + (mBegin.y-fy)*(mBegin.y-fy));
		mGrad.interpolate(Math.max(0.0f, Math.min(1.0f, distance/mRadius)), temp);
	}
}
