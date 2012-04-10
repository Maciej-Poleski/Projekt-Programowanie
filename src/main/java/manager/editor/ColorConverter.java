package manager.editor;

/**
 * Klasa konwertująca pomiędzy przestrzeniami barw
 * @author Patryk
 */
public class ColorConverter {
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
		float R=0,G=0,B=0,I,f,p,q,t;
		float H = color.getH();
		float S = color.getS();
		float V = color.getV();
		if(V == 0.0f) R=G=B=0.0f;
		else{
		 H /= 60.0f;
		 I = (int)Math.floor(H);
		 f = H-I;
		 p = V*(1.0f-S);
		 q = V*(1.0f-(S*f));
		 t = V*(1.0f-(S*(1.0f-f)));
		 if (I==0) {R=V; G=t; B=p;}
		 else if (I==1) {R=q; G=V; B=p;}
		 else if (I==2) {R=p; G=V; B=t;}
		 else if (I==3) {R=p; G=q; B=V;}
		 else if (I==4) {R=t; G=p; B=V;}
		 else if (I==5) {R=V; G=p; B=q;}
		}
		return new ColorRGB(R,G,B);
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
		float H=0.0f,S=0.0f,V=0.0f,R,G,B,x,I,f;
		R = color.getR();
		G = color.getG();
		B = color.getB();
		x = Math.min(Math.min(R, G), B);
		V = Math.max(Math.max(R, G), B);
		if (x == V) H=S=0.0f;
		else {
			if(R == x) f = G-B;
			else if(G == x) f = B-R;
			else f = R-G;
			
			if(R == x) I=3.0f;
			else if(G == x) I=5.0f;
			else I=1.0f;
			H = (float)( (int)((I-f/(V-x))*60.0f) )%360;
			S = ((V-x)/V);
		}		
		return new ColorHSV(H,S,V);
	}
	/**
	 * @param color - kolor z przestrzeni CMY
	 * @return odpowiadający kolor z przestrzeni HSV
	 */
	public static ColorHSV cmyTOhsv(ColorCMY color){
		return rgbTOhsv(cmyTOrgb(color));
	}
}
