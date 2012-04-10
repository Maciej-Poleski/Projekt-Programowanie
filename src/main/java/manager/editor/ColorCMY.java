package manager.editor;

/**
 * Klasa reprezentuje kolor w przestrzeni barw CMY
 * @author Patryk
 */
public class ColorCMY {
	private float mCyan, mMagenta, mYellow;
	
	/**
	 * @param C - kanał cyjan [0,255]
	 * @param M - kanał purpura [0,255]
	 * @param Y - kanał żółty [0,255]
	 */
	public ColorCMY(int C, int M, int Y){
		setC(C);
		setM(M);
		setY(Y);
	}
	/**
	 * @param C - kanał cyjan [0,1]
	 * @param M - kanał purpura [0,1]
	 * @param Y - kanał żółty [0,1]
	 */
	public ColorCMY(float C, float M, float Y){
		setC(C); 
		setM(M); 
		setY(Y);
	}
	
	/**
	 * @return kanał cyjan [0,1]
	 */
	public float getC(){return mCyan;}
	/**
	 * @return kanał purpura [0,1]
	 */
	public float getM(){return mMagenta;}
	/**
	 * @return kanał żółty [0,1]
	 */
	public float getY(){return mYellow;}
	
	/**
	 * Ustawia wartość kanału cyjanu
	 * @param C - kanał cyjan [0,1]
	 */
	public void setC(float C){
		mCyan = Math.max(0.0f, Math.min(1.0f, C));
	}
	/**
	 * Ustawia wartość kanału purpury
	 * @param M - kanał purpury [0,1]
	 */
	public void setM(float M){
		mMagenta = Math.max(0.0f, Math.min(1.0f, M));
	}
	/**
	 * Ustawia wartość kanału żółtego
	 * @param B - kanał żółty [0,1]
	 */
	public void setY(float Y){
		mYellow = Math.max(0.0f, Math.min(1.0f, Y));
	}
	/**
	 * Ustawia wartość kanału cyjanu
	 * @param C - kanał cyjan [0,255]
	 */
	public void setC(int C){
		mCyan = Math.max(0.0f, Math.min(255.0f, (float)C/255.0f));
	}
	/**
	 * Ustawia wartość kanału purpury
	 * @param M - kanał purpury [0,255]
	 */
	public void setM(int M){
		mMagenta = Math.max(0.0f, Math.min(255.0f, (float)M/255.0f));
	}
	/**
	 * Ustawia wartość kanału żółtego
	 * @param Y - kanał żółty [0,255]
	 */
	public void setY(int Y){
		mYellow = Math.max(0.0f, Math.min(255.0f, (float)Y/255.0f));
	}
}