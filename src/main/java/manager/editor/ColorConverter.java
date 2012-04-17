package manager.editor;

/**
 * Klasa konwertująca pomiędzy przestrzeniami barw
 * Próba stworzenia obiektu tej klasy nawet przy pomocy refleksji
 * skończy się wyrzuceniem wyjątku <b>UnsupportedOperationException</b>
 * @author Patryk
 */
public final class ColorConverter {
	/**
	 * Stała opisuje precyzje porównywania floatów
	 */
	public static final float FLOAT_PRECISION = 0.00001f;
	/**
	 * Kąt pełny palety barw (Hue) służy do zapętlenia wartości (modulo)
	 */
	public static final int HUE_CHANNEL_PRECISION = 360;
	/**
	 * Kąt wyznaczający podział koła barw na podzbiory do konwersji RGB -> HSV i odwrotnie
	 */
	public static final float HUE_CIRCLE_SPLITTER = 60.0f;
	/**
	 * Maksymalna dopuszczalna wartość kanału barwy (Hue) zarówno w 
	 * modelu barw jak i w bitmapie
	 */
	public static final float HUE_MAX = 359.999f;
	/**
	 * Maksymalna wartość kanałów RGB, CMY oraz SV (z modelu HSV) w modelach barw
	 */
	public static final float RGBCMY_FLOAT_MAX = 1.0f;
	/**
	 * Maksymalna wartość kanałów RGB i CMY w bitmapie
	 */
	public static final float RGBCMY_BYTE_MAX = 255.0f;
	/**
	 * Stosunek udziału koloru czerwonego w barwie wykrywanej przez ludzkie oko
	 */
	public static final float RED_LUMINOSITY = 0.21f;
	/**
	 * Stosunek udziału koloru zielonego w barwie wykrywanej przez ludzkie oko
	 */
	public static final float GREEN_LUMINOSITY =  0.71f;
	/**
	 * Stosunek udziału koloru niebieskiego w barwie wykrywanej przez ludzkie oko
	 */
	public static final float BLUE_LUMINOSITY = 0.07f;
	
	private ColorConverter(){throw new UnsupportedOperationException();};
	/**
	 * @param color - kolor z przestrzeni CMY
	 * @return odpowiadający kolor z przestrzeni RGB
	 */
	public static ColorRGB cmyTOrgb(ColorCMY color){
		return new ColorRGB(RGBCMY_FLOAT_MAX-color.getC(), RGBCMY_FLOAT_MAX-color.getM(), RGBCMY_FLOAT_MAX-color.getY());
	}
	/**
	 * @param color - kolor z przestrzeni HSV
	 * @return odpowiadający kolor z przestrzeni RGB
	 */
	public static ColorRGB hsvTOrgb(ColorHSV color){
		float mR=0,mG=0,mB=0,mI,f,p,q,t;
		float mH = color.getH();
		float mS = color.getS();
		float mV = color.getV();
		if(Math.abs(mV) < FLOAT_PRECISION) {mR=0.0f; mG=0.0f; mB=0.0f;}
		else{
		 mH /= HUE_CIRCLE_SPLITTER;
		 mI = (int)Math.floor(mH);
		 f = mH-mI;
		 p = mV*(RGBCMY_FLOAT_MAX-mS);
		 q = mV*(RGBCMY_FLOAT_MAX-(mS*f));
		 t = mV*(RGBCMY_FLOAT_MAX-(mS*(RGBCMY_FLOAT_MAX-f)));
		 if (mI==0) {mR=mV; mG=t; mB=p;}
		 else if (mI==1) {mR=q; mG=mV; mB=p;}
		 else if (mI==2) {mR=p; mG=mV; mB=t;}
		 else if (mI==3) {mR=p; mG=q; mB=mV;}
		 else if (mI==4) {mR=t; mG=p; mB=mV;}
		 else if (mI==5) {mR=mV; mG=p; mB=q;}
		}
		return new ColorRGB(mR,mG,mB);
	}
	
	/**
	 * @param color - kolor z przestrzeni RGB
	 * @return odpowiadający kolor z przestrzeni CMY
	 */
	public static ColorCMY rgbTOcmy(ColorRGB color){
		return new ColorCMY(RGBCMY_FLOAT_MAX-color.getR(), RGBCMY_FLOAT_MAX-color.getG(), RGBCMY_FLOAT_MAX-color.getB());
	}
	/**
	 * @param color - kolor z przestrzeni HSV
	 * @return odpowiadający kolor z przestrzeni CMY
	 */
	public static ColorCMY hsvTOcmy(ColorHSV color){
		return rgbTOcmy(hsvTOrgb(color));
	}
	
	/**
	 * @param color - kolor z przestrzeni RGB
	 * @return odpowiadający kolor z przestrzeni HSV
	 */
	public static ColorHSV rgbTOhsv(ColorRGB color){
		float mH=0.0f,mS=0.0f,mV=0.0f,mR,mG,mB,x,mI,f;
		mR = color.getR();
		mG = color.getG();
		mB = color.getB();
		x = Math.min(Math.min(mR, mG), mB);
		mV = Math.max(Math.max(mR, mG), mB);
		if (Math.abs(x - mV) < FLOAT_PRECISION) {mH=0.0f; mS=0.0f;}
		else {
			if(Math.abs(mR - x) < FLOAT_PRECISION) {f = mG-mB;}
			else if(Math.abs(mG - x) < FLOAT_PRECISION) {f = mB-mR;}
			else {f = mR-mG;}
			
			if(Math.abs(mR - x) < FLOAT_PRECISION) {mI=3.0f;}
			else if(Math.abs(mG - x) < FLOAT_PRECISION) {mI=5.0f;}
			else {mI=1.0f;}
			mH = (float)( (int)((mI-f/(mV-x))*HUE_CIRCLE_SPLITTER) ) % HUE_CHANNEL_PRECISION;
			mS = ((mV-x)/mV);
		}		
		return new ColorHSV(mH,mS,mV);
	}
	/**
	 * @param color - kolor z przestrzeni CMY
	 * @return odpowiadający kolor z przestrzeni HSV
	 */
	public static ColorHSV cmyTOhsv(ColorCMY color){
		return rgbTOhsv(cmyTOrgb(color));
	}
}
