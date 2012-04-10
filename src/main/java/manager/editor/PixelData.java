package manager.editor;

import java.awt.image.BufferedImage;

/**
 * Reprezentuje tablice pixeli obrazu. Może przechowywać dane w 3 przestrzeniach kolorów
 * RGB, CMY, HSV, daje możliwość exportu i importu ze struktury <b>BufferedImage</b> 
 * oraz wykonania swojej kopii zapasowej.
 * Jest to podstawowa struktura na której będą pracować filtry.
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
	
	/**
	 * Tworzy nowy obraz o podanych wymiarach
	 * @param width - długość obrazu
	 * @param height - wysokość obrazu
	 * @throws IllegalArgumentException - gdy width lub height sa < 1
	 */
	public PixelData(int width, int height) throws IllegalArgumentException{
		if(width <= 0 || height <= 0) throw new IllegalArgumentException();
		mWidth = width;
		mHeight = height;
		mData = new float[width*height*3];
	}
	
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
    		float f,p,q,t; int I;
    		for(int i=0;i<mWidth;i++)
    			for(int j=0;j<mHeight;j++){
    				H = mData[3*(i*mHeight+j)];
    				S = mData[3*(i*mHeight+j)+1];
    				V = mData[3*(i*mHeight+j)+2];
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
    		float H=0.0f,S=0.0f,V=0.0f,R,G,B,x,f,I;
    		for(int i=0;i<mWidth;i++)
    			for(int j=0;j<mHeight;j++){
    				R = mData[3*(i*mHeight+j)] / 255.0f;
    				G = mData[3*(i*mHeight+j)+1] / 255.0f;
    				B = mData[3*(i*mHeight+j)+2] / 255.0f;
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
     * @return Dokładna kopia danych <b>PixelData</b>
     */
    @Override
    public Object clone() {
        PixelData ret = null; 
        try{ret = (PixelData) super.clone(); 
        } catch (CloneNotSupportedException e) {} 
        ret.mData = mData.clone(); 
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
     * @param image - obraz do którego próbujemy przenieść dane
     * @throws IllegalArgumentException - gdy rozmiar <b>image</b>  nie zgadza się z rozmiarem danych PixelData
     * Należy wtedy użyc metody <b>toBufferedData()</b>
     */
    public void toBufferedImage(BufferedImage image) throws IllegalArgumentException{
    	if(image == null) throw new NullPointerException();
    	if(image.getWidth() != mWidth || image.getHeight() != mHeight) throw new IllegalArgumentException();
    	toRGB();
    	image.getRaster().setPixels(0, 0, mWidth, mHeight, mData);
    }

    /**
     * @param channel - kanał z którego chcemy ekstrachowac <b>Histogram</b>
     * @return <b>Histogram</b> zwierający informacje o danym kanale.
     */
    public Histogram getHistogram(Histogram.HistogramChannel channel) {
    	int table[] = null;
    	if(channel == Histogram.HistogramChannel.RED){
    		table = new int[256]; toRGB();
    		for(int i=0;i<mWidth;i++) for(int j=0;j<mHeight;j++) table[(int)mData[3*(i*mHeight+j)]]++;
    	}
    	if(channel == Histogram.HistogramChannel.GREEN){
    		table = new int[256]; toRGB();
    		for(int i=0;i<mWidth;i++) for(int j=0;j<mHeight;j++) table[(int)mData[3*(i*mHeight+j)+1]]++;
    	}
    	if(channel == Histogram.HistogramChannel.BLUE){
    		table = new int[256]; toRGB();
    		for(int i=0;i<mWidth;i++) for(int j=0;j<mHeight;j++) table[(int)mData[3*(i*mHeight+j)+2]]++;
    	}
    	if(channel == Histogram.HistogramChannel.CYAN){
    		table = new int[256]; toCMY();
    		for(int i=0;i<mWidth;i++) for(int j=0;j<mHeight;j++) table[(int)mData[3*(i*mHeight+j)]]++;
    	}
    	if(channel == Histogram.HistogramChannel.MAGENTA){
    		table = new int[256]; toCMY();
    		for(int i=0;i<mWidth;i++) for(int j=0;j<mHeight;j++) table[(int)mData[3*(i*mHeight+j)+1]]++;
    	}
    	if(channel == Histogram.HistogramChannel.YELLOW){
    		table = new int[256]; toCMY();
    		for(int i=0;i<mWidth;i++) for(int j=0;j<mHeight;j++) table[(int)mData[3*(i*mHeight+j)+2]]++;
    	}
    	if(channel == Histogram.HistogramChannel.HUE){
    		table = new int[360]; toHSV();
    		for(int i=0;i<mWidth;i++) for(int j=0;j<mHeight;j++) table[((int)mData[3*(i*mHeight+j)])%360]++;
    	}
    	if(channel == Histogram.HistogramChannel.SATURATION){
    		table = new int[100]; toHSV();
    		for(int i=0;i<mWidth;i++) for(int j=0;j<mHeight;j++) table[(int)(mData[3*(i*mHeight+j)+1]*100.0f)]++;
    	}
    	if(channel == Histogram.HistogramChannel.VALUE){
    		table = new int[100]; toHSV();
    		for(int i=0;i<mWidth;i++) for(int j=0;j<mHeight;j++) table[(int)(mData[3*(i*mHeight+j)+2]*100.0f)]++;
    	}
        return new Histogram(table, channel);
    }
}
