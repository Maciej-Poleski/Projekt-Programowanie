package manager.editor;

import java.awt.image.BufferedImage;

/**
 * Reprezentuje tablice pixeli obrazu. Mo¿e przechowywaæ dane w 3 przestrzeniach kolorów
 * RGB, CMY, HSV, daje mo¿liwoœæ exportu i importu ze struktur¹ <b>BufferedImage</b> 
 * oraz wykonania swojej kopii zapasowej.
 * Jest to podstawowa struktura na której bêd¹ pracowaæ filtry.
 * @author Patryk
 *
 */
public class PixelData implements Cloneable {
	private enum DataType{
		RGB, CMY, HSV;
	}
	private DataType mDataType = DataType.RGB;
	int mWidth, mHeight;
	float[] mData;
	
	private PixelData(){}
	
    /**
     * @param image - referencja do obrazu z którego pobieramy dane o pixelach
     */
    public PixelData(BufferedImage image) {
    	mWidth = image.getWidth();
    	mHeight = image.getHeight();
    	mData = image.getRaster().getPixels(0, 0, mWidth, mHeight, (float[])null);
    }

    /**
     * Transformuje obraz do przestrzeni barw RGB
     */
    public void toRGB() {
    	if(mDataType == DataType.CMY){
    		for(int i=0;i<mData.length;i++) mData[i] = 255.0f - mData[i];
    	} 
    	if(mDataType == DataType.HSV){
    		float H,S,V,R=0.0f,G=0.0f,B=0.0f;
    		for(int i=0;i<mWidth;i++)
    			for(int j=0;j<mHeight;j++){
    				H = mData[3*(i*mHeight+j)];
    				S = mData[3*(i*mHeight+j)+1];
    				V = mData[3*(i*mHeight+j)+2];
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
    				mData[3*(i*mHeight+j)] = Math.max(0.0f, Math.min(255.0f, 255.0f * R));
    				mData[3*(i*mHeight+j)+1] = Math.max(0.0f, Math.min(255.0f, 255.0f * G));
    				mData[3*(i*mHeight+j)+2] = Math.max(0.0f, Math.min(255.0f, 255.0f * B));
    			}
    	}
    	mDataType = DataType.RGB;
    }

    /**
     * Transformuje obraz do przestrzeni barw HSV
     */
    public void toHSV() {
    	if(mDataType == DataType.CMY) toRGB();
    	if(mDataType == DataType.RGB){
    		float H=0.0f,S=0.0f,V=0.0f,R,G,B,min,max;
    		for(int i=0;i<mWidth;i++)
    			for(int j=0;j<mHeight;j++){
    				R = mData[3*(i*mHeight+j)] / 255.0f;
    				G = mData[3*(i*mHeight+j)+1] / 255.0f;
    				B = mData[3*(i*mHeight+j)+2] / 255.0f;
    				max = Math.max(Math.max(R, G), B);
    	            min = Math.min(Math.min(R, G), B);
    	            
    	            if(max == 0.0f) H = 0;
    	            else if(max == R) H = 60.0f * ((G - B) / (max - min));
    	            else if(max == G) H = 60.0f * (2.0f + (B - R) / (max - min));
    	            else if(max == B) H = 60.0f * (4.0f + (R - G) / (max - min));

    	            if (max == 0.0f) S = 0.0f;
    	            else S = 1.0f - (min / max);
    	            V = max / 255.0f;
    	            
    	            mData[3*(i*mHeight+j)] = H;
    	            mData[3*(i*mHeight+j)+1] = S;
    	            mData[3*(i*mHeight+j)+2] = V;
    			}
    	}
    	mDataType = DataType.HSV;
    }

    /**
     * Transformuje obraz do przestrzeni barw CMY
     */
    public void toCMY() {
    	if(mDataType == DataType.HSV) toRGB();
    	if(mDataType == DataType.RGB) for(int i=0;i<mData.length;i++) mData[i] = 255.0f - mData[i];
    	mDataType = DataType.CMY;
    }

    /**
     * @return Dok³adna kopia danych <b>PixelData</b>
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
    	PixelData ret = new PixelData();
    	ret.mWidth = mWidth;
    	ret.mHeight = mHeight;
    	ret.mData = mData.clone();
    	ret.mDataType = mDataType;
        return ret;
    }
    
    /**
     * @return Obraz typu <b>BufferedImage</b> z ustawionymi pixelami
     */
    public BufferedImage toBufferedImage(){
    	toRGB();
    	BufferedImage image = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);
    	image.getRaster().setPixels(0, 0, mWidth, mHeight, mData);
    	return image;
    }
    
    /**
     * @param image - obraz do którego próbujemy przenieœæ dane
     * @throws IllegalArgumentException - gdy rozmiar <b>image</b>  nie zgadza siê z rozmiarem danych PixelData
     * nale¿y wtedy u¿yc metody <b>toBufferedData()</b>
     */
    public void toBufferedImage(BufferedImage image) throws IllegalArgumentException{
    	if(image.getWidth() != mWidth || image.getHeight() != mHeight) throw new IllegalArgumentException();
    	toRGB();
    	image.getRaster().setPixels(0, 0, mWidth, mHeight, mData);
    }

    /**
     * @param k - numer kana³u z którego chcemy ekstrachowac <b>Histogram</b> kana³ = k modulo 3
     * @return <b>Histogram</b> zwieraj¹cy informacje o danym kanale.
     */
    public Histogram getHistogram(int k) {
        return null;
    }
}
