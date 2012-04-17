package manager.editor;

/**
 * Klasa reprezentuje kolor w przestrzeni barw RGB
 * @author Patryk
 */
public class ColorRGB {
	private float mRed, mGreen, mBlue;
	
	/**
	 * @param mR - kanał czerwony [0,255]
	 * @param mG - kanał zielony [0,255]
	 * @param mB - kanał niebieski [0,255]
	 */
	public ColorRGB(int mR, int mG, int mB){
		setR(mR);
		setG(mG);
		setB(mB);
	}
	/**
	 * @param mR - kanał czerwony [0,1]
	 * @param mG - kanał zielony [0,1]
	 * @param mB - kanał niebieski [0,1]
	 */
	public ColorRGB(float mR, float mG, float mB){
		setR(mR); 
		setG(mG); 
		setB(mB);
	}
	
	/**
	 * @return kanał czerwony [0,1]
	 */
	public final float getR(){return mRed;}
	/**
	 * @return kanał zielony [0,1]
	 */
	public final float getG(){return mGreen;}
	/**
	 * @return kanał niebieski [0,1]
	 */
	public final float getB(){return mBlue;}
	
	/**
	 * Ustawia wartość kanału czerwonego
	 * @param mR - kanał czerwony [0,1]
	 */
	public final void setR(float mR){
		mRed = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_FLOAT_MAX, mR));
	}
	/**
	 * Ustawia wartość kanału zielonego
	 * @param mG - kanał zielony [0,1]
	 */
	public final void setG(float mG){
		mGreen = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_FLOAT_MAX, mG));
	}
	/**
	 * Ustawia wartość kanału niebieskiego
	 * @param mB - kanał niebieski [0,1]
	 */
	public final void setB(float mB){
		mBlue = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_FLOAT_MAX, mB));
	}
	/**
	 * Ustawia wartość kanału czerwonego
	 * @param mR - kanał czerwony [0,255]
	 */
	public final void setR(int mR){
		mRed = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, (float)mR/ColorConverter.RGBCMY_BYTE_MAX));
	}
	/**
	 * Ustawia wartość kanału zielonego
	 * @param mG - kanał zielony [0,255]
	 */
	public final void setG(int mG){
		mGreen = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, (float)mG/ColorConverter.RGBCMY_BYTE_MAX));
	}
	/**
	 * Ustawia wartość kanału niebieskiego
	 * @param mB - kanał niebieski [0,255]
	 */
	public final void setB(int mB){
		mBlue = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, (float)mB/ColorConverter.RGBCMY_BYTE_MAX));
	}
}
