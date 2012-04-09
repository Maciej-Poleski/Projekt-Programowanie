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
		float R=0,G=0,B=0;
		float H = color.getH();
		float S = color.getS();
		float V = color.getV();
		if (H >= 0.0f && H < 60.0f){
			R = V;
			G = V * (1.0f - S * (1.0f - H / 60.0f));
			B = V * (1.0f - S);
		}
        else if(H >= 60.0f && H < 120.0f){
        	R = V * (1.0f - S * (H / 60.0f - 1.0f));
        	G = V;
        	B = V * (1.0f - S);
        }
        else if(H >= 120.0f && H < 180.0f){
        	R = V * (1.0f - S);
        	G = V;
        	B = V * (1.0f - S * (3.0f - H / 60.0f));
        }
        else if(H >= 180.0f && H < 240.0f){
        	R = V * (1 - S);
        	G = V * (1 - S * (H / 60.0f - 3.0f));
        	B = V;
        }
        else if(H >= 240.0f && H < 300.0f){
        	R = V * (1.0f - S * (5.0f - H / 60.0f));
        	G = V * (1.0f - S);
        	B = V;
        }
        else if(H >= 300.0f && H < 360.0f){
        	R = V;
        	G = V * (1.0f - S);
        	B = V * (1.0f - S * (H / 60.0f - 5.0f));
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
		float H=0.0f,S=0.0f,V=0.0f,R,G,B,min,max;
		R = color.getR();
		G = color.getG();
		B = color.getB();
		max = Math.max(Math.max(R, G), B);
		min = Math.min(Math.min(R, G), B);
		
		if(max == 0.0f) H = 0;
		else if(max == R) H = 60.0f * ((G - B) / (max - min));
		else if(max == G) H = 60.0f * (2.0f + (B - R) / (max - min));
		else if(max == B) H = 60.0f * (4.0f + (R - G) / (max - min));
		
		if (max == 0.0f) S = 0.0f;
		else S = 1.0f - (min / max);
		V = max / 255.0f;
		
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
