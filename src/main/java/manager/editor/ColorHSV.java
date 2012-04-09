package manager.editor;

/**
 * Klasa reprezentuje kolor w przestrzeni barw HSV
 * @author Patryk
 */
public class ColorHSV {
	private float mHue, mSaturation, mValue;
	
	/**
	 * @param H - Barwa [0,359]
	 * @param S - Nasycenie [0,1]
	 * @param V - Jasność [0,1]
	 */
	public ColorHSV(float H, float S, float V){
		setH(H);
		setS(S);
		setV(V);
	}
	
	/**
	 * @return wartość kanału barwy [0,359]
	 */
	public float getH(){return mHue;}
	/**
	 * @return wartość kanału nasycenia [0,1]
	 */
	public float getS(){return mSaturation;}
	/**
	 * @return wartość kanału jasności [0,1]
	 */
	public float getV(){return mValue;}
	
	/**
	 * Ustawia wartość kanału barwy dla koloru
	 * @param H - kanał barwy
	 */
	public void setH(float H){
		mHue = Math.max(0.0f, Math.min(359.9f, H));
	}
	/**
	 * Ustawia wartość kanału nasycenia dla koloru
	 * @param S - kanał nasycenia
	 */
	public void setS(float S){
		mSaturation = Math.max(0.0f, Math.min(1.0f, S));
	}
	/**
	 * Ustawia wartość kanału jasności dla koloru
	 * @param V - kanał jasności
	 */
	public void setV(float V){
		mValue = Math.max(0.0f, Math.min(1.0f, V));
	}
}
