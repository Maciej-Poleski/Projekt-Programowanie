package manager.editor;

/**
 * Klasa reprezentuje kolor w przestrzeni barw RGB
 * @author Patryk
 */
public class ColorRGB {
	private float mRed, mGreen, mBlue;
	
	/**
	 * @param R - kanał czerwony [0,255]
	 * @param G - kanał zielony [0,255]
	 * @param B - kanał niebieski [0,255]
	 */
	public ColorRGB(int R, int G, int B){
		setR(R);
		setG(G);
		setB(B);
	}
	/**
	 * @param R - kanał czerwony [0,1]
	 * @param G - kanał zielony [0,1]
	 * @param B - kanał niebieski [0,1]
	 */
	public ColorRGB(float R, float G, float B){
		setR(R); 
		setG(G); 
		setB(B);
	}
	
	/**
	 * @return kanał czerwony [0,1]
	 */
	public float getR(){return mRed;}
	/**
	 * @return kanał zielony [0,1]
	 */
	public float getG(){return mGreen;}
	/**
	 * @return kanał niebieski [0,1]
	 */
	public float getB(){return mBlue;}
	
	/**
	 * Ustawia wartość kanału czerwonego
	 * @param R - kanał czerwony [0,1]
	 */
	public void setR(float R){
		mRed = Math.max(0.0f, Math.min(1.0f, R));
	}
	/**
	 * Ustawia wartość kanału zielonego
	 * @param G - kanał zielony [0,1]
	 */
	public void setG(float G){
		mGreen = Math.max(0.0f, Math.min(1.0f, G));
	}
	/**
	 * Ustawia wartość kanału niebieskiego
	 * @param B - kanał niebieski [0,1]
	 */
	public void setB(float B){
		mBlue = Math.max(0.0f, Math.min(1.0f, B));
	}
	/**
	 * Ustawia wartość kanału czerwonego
	 * @param R - kanał czerwony [0,255]
	 */
	public void setR(int R){
		mRed = Math.max(0.0f, Math.min(255.0f, (float)R/255.0f));
	}
	/**
	 * Ustawia wartość kanału zielonego
	 * @param G - kanał zielony [0,255]
	 */
	public void setG(int G){
		mGreen = Math.max(0.0f, Math.min(255.0f, (float)G/255.0f));
	}
	/**
	 * Ustawia wartość kanału niebieskiego
	 * @param B - kanał niebieski [0,255]
	 */
	public void setB(int B){
		mBlue = Math.max(0.0f, Math.min(255.0f, (float)B/255.0f));
	}
}
