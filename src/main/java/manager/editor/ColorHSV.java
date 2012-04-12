package manager.editor;

/**
 * Klasa reprezentuje kolor w przestrzeni barw HSV
 * @author Patryk
 */
public class ColorHSV {
	private float mHue, mSaturation, mValue;
	
	/**
	 * @param mH - Barwa [0,359]
	 * @param mS - Nasycenie [0,1]
	 * @param mV - Jasność [0,1]
	 */
	public ColorHSV(float mH, float mS, float mV){
		setH(mH);
		setS(mS);
		setV(mV);
	}
	
	/**
	 * @return wartość kanału barwy [0,359]
	 */
	public final float getH(){return mHue;}
	/**
	 * @return wartość kanału nasycenia [0,1]
	 */
	public final float getS(){return mSaturation;}
	/**
	 * @return wartość kanału jasności [0,1]
	 */
	public final float getV(){return mValue;}
	
	/**
	 * Ustawia wartość kanału barwy dla koloru
	 * @param mH - kanał barwy
	 */
	public final void setH(float mH){
		mHue = Math.max(0.0f, Math.min(359.9f, mH));
	}
	/**
	 * Ustawia wartość kanału nasycenia dla koloru
	 * @param mS - kanał nasycenia
	 */
	public final void setS(float mS){
		mSaturation = Math.max(0.0f, Math.min(1.0f, mS));
	}
	/**
	 * Ustawia wartość kanału jasności dla koloru
	 * @param mV - kanał jasności
	 */
	public final void setV(float mV){
		mValue = Math.max(0.0f, Math.min(1.0f, mV));
	}
}
