package manager.editor;

/**
 * Klasa konwertująca pomiędzy przestrzeniami barw
 * @author Patryk
 */
public class ColorConverter {
	private ColorConverter(){throw new RuntimeException();};
	/**
	 * @param color - kolor z przestrzeni CMY
	 * @return odpowiadający kolor z przestrzeni RGB
	 */
	public static ColorRGB cmyTOrgb(ColorCMY color){
		return new ColorRGB(1.0f-color.getC(), 1.0f-color.getM(), 1.0f-color.getY());
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
		if(Math.abs(mV) < .00001f) {mR=0.0f; mG=0.0f; mB=0.0f;}
		else{
		 mH /= 60.0f;
		 mI = (int)Math.floor(mH);
		 f = mH-mI;
		 p = mV*(1.0f-mS);
		 q = mV*(1.0f-(mS*f));
		 t = mV*(1.0f-(mS*(1.0f-f)));
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
		return new ColorCMY(1.0f-color.getR(), 1.0f-color.getG(), 1.0f-color.getB());
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
		if (Math.abs(x - mV) < .0001f) {mH=0.0f; mS=0.0f;}
		else {
			if(Math.abs(mR - x) < .0001f) {f = mG-mB;}
			else if(Math.abs(mG - x) < .0001f) {f = mB-mR;}
			else {f = mR-mG;}
			
			if(Math.abs(mR - x) < .0001f) {mI=3.0f;}
			else if(Math.abs(mG - x) < .0001f) {mI=5.0f;}
			else {mI=1.0f;}
			mH = (float)( (int)((mI-f/(mV-x))*60.0f) )%360;
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
