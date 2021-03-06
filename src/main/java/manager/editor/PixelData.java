package manager.editor;

import java.awt.image.BufferedImage;
import manager.editor.Histogram.HistogramChannel;

/**
 * Reprezentuje tablice pixeli obrazu. Może przechowywać dane w 3 przestrzeniach kolorów
 * RGB, CMY, HSV, daje możliwość exportu i importu ze struktury <b>BufferedImage</b> 
 * oraz wykonania swojej kopii zapasowej.
 * Jest to podstawowa struktura na której będą pracować filtry.
 * @author Patryk
 *
 */
public class PixelData implements Cloneable {
	/**
	 * Rozmiar danych zajmowanych w tablicy przez każdego pixela w bajtach
	 */
	public static final int PIXEL_SIZE = 3;
	/**
	 * Tablicowa precyzja zapisu kanałów Saturation i Value (modelu HSV)
	 */
	public static final int SV_CHANNEL_PRECISON = 100;
	/**
	 * Tablicowa precyzja zapisu kanałów Hue (modelu HSV)
	 */
	public static final int HUE_CHANNEL_PRECISION = 360;
	/**
	 * Tablicowa precyzja zapisu kanałów RGB i CMY
	 */
	public static final int RGBCMY_CHANNEL_PRECISION = 256;
	
	/**
	 * Maska wartośći kanału w standardzie sRGB
	 */
	public static final int RGB_CHANNEL_MASK = 0xff;
	
	/**
	 * Przesunięcie kanału czerwonego w standardzie sRGB
	 */
	public static final int RGB_RED_SHIFT = 16;
	/**
	 * Przesunięcie kanału zielonego w standardzie sRGB
	 */
	public static final int RGB_GREEN_SHIFT = 8;
	/**
	 * Przesunięcie kanału niebieskiego w standardzie sRGB
	 */
	public static final int RGB_BLUE_SHIFT = 0;
	
	private enum DataType{
		RGB, CMY, HSV;
	}
	private DataType mDataType = DataType.RGB;
	private int mWidth, mHeight;
	private float[] mData;
	
	/**
	 * Tworzy nowy obraz o podanych wymiarach
	 * @param width - długość obrazu
	 * @param height - wysokość obrazu
	 * @throws IllegalArgumentException - gdy width lub height są < 1
	 */
	public PixelData(int width, int height) {
		if(width <= 0 || height <= 0) {throw new IllegalArgumentException();}
		mWidth = width;
		mHeight = height;
		mData = new float[width*height*PIXEL_SIZE];
	}
	
    /**
     * @param image - referencja do obrazu z którego pobieramy dane o pixelach
     */
    public PixelData(BufferedImage image) {
    	mWidth = image.getWidth();
    	mHeight = image.getHeight();
    	mData = new float[PIXEL_SIZE*mWidth*mHeight];
    	int mRGB;
		for(int j=0;j<mHeight;j++){
			for(int i=0;i<mWidth;i++){
				mRGB = image.getRGB(i, j);
				mData[PIXEL_SIZE*(j*mWidth+i)] = (float)((mRGB >> RGB_RED_SHIFT) & RGB_CHANNEL_MASK);
				mData[PIXEL_SIZE*(j*mWidth+i)+1] = (float)((mRGB >> RGB_GREEN_SHIFT) & RGB_CHANNEL_MASK);
				mData[PIXEL_SIZE*(j*mWidth+i)+2] = (float)((mRGB >> RGB_BLUE_SHIFT) & RGB_CHANNEL_MASK);
			}
    	}
    }
    
    /**
     * Zwraca szerokość obrazu
     * @return Szerokość obrazu
     */
    public int getWidth(){
    	return mWidth;
    }
    
    /**
     * Zwraca wysokość obrazu
     * @return Wysokość obrazu
     */
    public int getHeight(){
    	return mHeight;
    }
    
    /**
     * Zwraca tablicę danych o pixelach
     * @return tablica danych pixeli
     */
    final float[] getData(){
    	return mData;
    }

    /**
     * Transformuje obraz do przestrzeni barw RGB
     */
    public void toRGB() {
    	if(mDataType == DataType.CMY){
    		for(int i=0;i<mData.length;i++) {mData[i] = ColorConverter.RGBCMY_BYTE_MAX - mData[i];}
    	} 
    	if(mDataType == DataType.HSV){
    		float mH,mS,mV,mR=0.0f,mG=0.0f,mB=0.0f;
    		float f,p,q,t; int mI;
    		for(int i=0;i<mWidth;i++){
    			for(int j=0;j<mHeight;j++){
    				mH = mData[PIXEL_SIZE*(i*mHeight+j)];
    				mS = mData[PIXEL_SIZE*(i*mHeight+j)+1];
    				mV = mData[PIXEL_SIZE*(i*mHeight+j)+2];
    				if(Math.abs(mV) < ColorConverter.FLOAT_PRECISION) {mR=0.0f; mG=0.0f; mB=0.0f;}
    				else{
    				 mH /= ColorConverter.HUE_CIRCLE_SPLITTER;
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
    				mData[PIXEL_SIZE*(i*mHeight+j)] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, ColorConverter.RGBCMY_BYTE_MAX * mR));
    				mData[PIXEL_SIZE*(i*mHeight+j)+1] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, ColorConverter.RGBCMY_BYTE_MAX * mG));
    				mData[PIXEL_SIZE*(i*mHeight+j)+2] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, ColorConverter.RGBCMY_BYTE_MAX * mB));
    			}
    		}
    	}
    	mDataType = DataType.RGB;
    }

    /**
     * Transformuje obraz do przestrzeni barw HSV
     */
    public void toHSV() {
    	if(mDataType == DataType.CMY) {toRGB();}
    	if(mDataType == DataType.RGB){
    		float mH=0.0f,mS=0.0f,mV=0.0f,mR,mG,mB,x,f,mI;
    		for(int i=0;i<mWidth;i++){
    			for(int j=0;j<mHeight;j++){
    				mR = mData[PIXEL_SIZE*(i*mHeight+j)] / ColorConverter.RGBCMY_BYTE_MAX;
    				mG = mData[PIXEL_SIZE*(i*mHeight+j)+1] / ColorConverter.RGBCMY_BYTE_MAX;
    				mB = mData[PIXEL_SIZE*(i*mHeight+j)+2] / ColorConverter.RGBCMY_BYTE_MAX;
    				x = Math.min(Math.min(mR, mG), mB);
    				mV = Math.max(Math.max(mR, mG), mB);
    				if (Math.abs(x - mV) < ColorConverter.FLOAT_PRECISION) {mH=0.0f; mS=0.0f;}
    				else {
    					if(Math.abs(mR - x) < ColorConverter.FLOAT_PRECISION) {f = mG-mB;}
    					else if(Math.abs(mG - x) < ColorConverter.FLOAT_PRECISION) {f = mB-mR;}
    					else {f = mR-mG;}
    					
    					if(Math.abs(mR - x) < ColorConverter.FLOAT_PRECISION) {mI=3.0f;}
    					else if(Math.abs(mG - x) < ColorConverter.FLOAT_PRECISION) {mI=5.0f;}
    					else {mI=1.0f;}
    					mH = (float)( (int)((mI-f/(mV-x))*ColorConverter.HUE_CIRCLE_SPLITTER) )%HUE_CHANNEL_PRECISION;
    					mS = ((mV-x)/mV);
    				}
    				
    	            mData[PIXEL_SIZE*(i*mHeight+j)] = mH;
    	            mData[PIXEL_SIZE*(i*mHeight+j)+1] = mS;
    	            mData[PIXEL_SIZE*(i*mHeight+j)+2] = mV;
    			}
    		}
    	}
    	mDataType = DataType.HSV;
    }

    /**
     * Transformuje obraz do przestrzeni barw CMY
     */
    public void toCMY() {
    	if(mDataType == DataType.HSV) {toRGB();}
    	if(mDataType == DataType.RGB) {
    		for(int i=0;i<mData.length;i++) {mData[i] = ColorConverter.RGBCMY_BYTE_MAX - mData[i];} 
    	}
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
    public void toBufferedImage(BufferedImage image) {
    	if(image.getWidth() != mWidth || image.getHeight() != mHeight) {throw new IllegalArgumentException();}
    	toRGB(); int mRGB,mR,mG,mB;
    	for(int j=0;j<mHeight;j++){
			for(int i=0;i<mWidth;i++){
				mR = (int)mData[PIXEL_SIZE*(j*mWidth+i)];
				mG = (int)mData[PIXEL_SIZE*(j*mWidth+i)+1];
				mB = (int)mData[PIXEL_SIZE*(j*mWidth+i)+2];
				mRGB = (mR << RGB_RED_SHIFT) & (mG << RGB_GREEN_SHIFT) & (mB << RGB_BLUE_SHIFT);
				image.setRGB(i, j, mRGB);
			}
		}
    }

    /**
     * @param channel - kanał z którego chcemy ekstrachowac <b>Histogram</b>
     * @return <b>Histogram</b> zwierający informacje o danym kanale.
     */
    public Histogram getHistogram(Histogram.HistogramChannel channel) {
    	if(channel == Histogram.HistogramChannel.RED) {return getHistogramRed();}
    	else if(channel == Histogram.HistogramChannel.GREEN) {return getHistogramGreen();}
    	else if(channel == Histogram.HistogramChannel.BLUE) {return getHistogramBlue();}
    	else if(channel == Histogram.HistogramChannel.CYAN) {return getHistogramCyan();}
    	else if(channel == Histogram.HistogramChannel.MAGENTA) {return getHistogramMagenta();}
    	else if(channel == Histogram.HistogramChannel.YELLOW) {return getHistogramYellow();}
    	else if(channel == Histogram.HistogramChannel.HUE) {return getHistogramHue();}
    	else if(channel == Histogram.HistogramChannel.SATURATION) {return getHistogramSaturation();}
    	return getHistogramValue();
    }
    
    private Histogram getHistogramRed(){
    	toRGB();
    	int[] table = new int[RGBCMY_CHANNEL_PRECISION];
		for(int i=0;i<mWidth;i++) {
			for(int j=0;j<mHeight;j++) {
				table[(int)mData[PIXEL_SIZE*(i*mHeight+j)]]++;
			}
		}
    	return new Histogram(table, HistogramChannel.RED);
    }
    private Histogram getHistogramGreen(){
    	toRGB();
    	int[] table = new int[RGBCMY_CHANNEL_PRECISION];
		for(int i=0;i<mWidth;i++) {
			for(int j=0;j<mHeight;j++) {
				table[(int)mData[PIXEL_SIZE*(i*mHeight+j)+1]]++;
			}
		}
    	return new Histogram(table, HistogramChannel.GREEN);
    }
    private Histogram getHistogramBlue(){
    	toRGB();
    	int[] table = new int[RGBCMY_CHANNEL_PRECISION];
		for(int i=0;i<mWidth;i++) {
			for(int j=0;j<mHeight;j++) {
				table[(int)mData[PIXEL_SIZE*(i*mHeight+j)+2]]++;
			}
		}
    	return new Histogram(table, HistogramChannel.BLUE);
    }
    
    private Histogram getHistogramCyan(){
    	toCMY();
    	int[] table = new int[RGBCMY_CHANNEL_PRECISION];
		for(int i=0;i<mWidth;i++) {
			for(int j=0;j<mHeight;j++) {
				table[(int)mData[PIXEL_SIZE*(i*mHeight+j)]]++;
			}
		}
    	return new Histogram(table, HistogramChannel.CYAN);
    }
    private Histogram getHistogramMagenta(){
    	toCMY();
    	int[] table = new int[RGBCMY_CHANNEL_PRECISION];
		for(int i=0;i<mWidth;i++) {
			for(int j=0;j<mHeight;j++) {
				table[(int)mData[PIXEL_SIZE*(i*mHeight+j)+1]]++;
			}
		}
    	return new Histogram(table, HistogramChannel.MAGENTA);
    }
    private Histogram getHistogramYellow(){
    	toCMY();
    	int[] table = new int[RGBCMY_CHANNEL_PRECISION];
		for(int i=0;i<mWidth;i++) {
			for(int j=0;j<mHeight;j++) {
				table[(int)mData[PIXEL_SIZE*(i*mHeight+j)+2]]++;
			}
		}
    	return new Histogram(table, HistogramChannel.YELLOW);
    }
    
    private Histogram getHistogramHue(){
    	int[] table = new int[HUE_CHANNEL_PRECISION]; toHSV();
		for(int i=0;i<mWidth;i++) {
			for(int j=0;j<mHeight;j++) {
				table[((int)mData[PIXEL_SIZE*(i*mHeight+j)])%HUE_CHANNEL_PRECISION]++;
			}
		}
		return new Histogram(table, HistogramChannel.HUE);
    }
    private Histogram getHistogramSaturation(){
    	int[] table = new int[SV_CHANNEL_PRECISON+1]; toHSV();
		for(int i=0;i<mWidth;i++) {
			for(int j=0;j<mHeight;j++) {
				table[(int)(mData[PIXEL_SIZE*(i*mHeight+j)+1]*(float)SV_CHANNEL_PRECISON)]++;
			}
		}
		return new Histogram(table, HistogramChannel.SATURATION);
    }
    private Histogram getHistogramValue(){
    	int[] table = new int[SV_CHANNEL_PRECISON+1]; toHSV();
		for(int i=0;i<mWidth;i++) {
			for(int j=0;j<mHeight;j++) {
				table[(int)(mData[PIXEL_SIZE*(i*mHeight+j)+2]*(float)SV_CHANNEL_PRECISON)]++;
			}
		}
		return new Histogram(table, HistogramChannel.VALUE);
    }
}
