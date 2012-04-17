package manager.editor;

/**
 * Klasa reprezentuje kolor w przestrzeni barw CMY
 * @author Patryk
 */
public class ColorCMY {
	private float mCyan, mMagenta, mYellow;
	
	/**
	 * @param mC - kanał cyjan [0,255]
	 * @param mM - kanał purpura [0,255]
	 * @param mY - kanał żółty [0,255]
	 */
	public ColorCMY(int mC, int mM, int mY){
		setC(mC);
		setM(mM);
		setY(mY);
	}
	/**
	 * @param mC - kanał cyjan [0,1]
	 * @param mM - kanał purpura [0,1]
	 * @param mY - kanał żółty [0,1]
	 */
	public ColorCMY(float mC, float mM, float mY){
		setC(mC); 
		setM(mM); 
		setY(mY);
	}
	
	/**
	 * @return kanał cyjan [0,1]
	 */
	public final float getC(){return mCyan;}
	/**
	 * @return kanał purpura [0,1]
	 */
	public final float getM(){return mMagenta;}
	/**
	 * @return kanał żółty [0,1]
	 */
	public final float getY(){return mYellow;}
	
	/**
	 * Ustawia wartość kanału cyjanu
	 * @param mC - kanał cyjan [0,1]
	 */
	public final void setC(float mC){
		mCyan = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_FLOAT_MAX, mC));
	}
	/**
	 * Ustawia wartość kanału purpury
	 * @param mM - kanał purpury [0,1]
	 */
	public final void setM(float mM){
		mMagenta = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_FLOAT_MAX, mM));
	}
	/**
	 * Ustawia wartość kanału żółtego
	 * @param mY - kanał żółty [0,1]
	 */
	public final void setY(float mY){
		mYellow = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_FLOAT_MAX, mY));
	}
	/**
	 * Ustawia wartość kanału cyjanu
	 * @param mC - kanał cyjan [0,255]
	 */
	public final void setC(int mC){
		mCyan = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, (float)mC/ColorConverter.RGBCMY_BYTE_MAX));
	}
	/**
	 * Ustawia wartość kanału purpury
	 * @param mM - kanał purpury [0,255]
	 */
	public final void setM(int mM){
		mMagenta = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, (float)mM/ColorConverter.RGBCMY_BYTE_MAX));
	}
	/**
	 * Ustawia wartość kanału żółtego
	 * @param mY - kanał żółty [0,255]
	 */
	public final void setY(int mY){
		mYellow = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, (float)mY/ColorConverter.RGBCMY_BYTE_MAX));
	}
}