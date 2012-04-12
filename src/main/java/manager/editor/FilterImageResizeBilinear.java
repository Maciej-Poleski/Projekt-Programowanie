package manager.editor;

/**
 * Klasa służąca do zmiany rozmiary obrazu metodą dwukrotnego próbkowania liniowego - daje dość dobre
 * rezultaty, dokładniejsza niż najbliższe sąsiedztwo, bardziej kosztowna obliczeniowo
 * @author Patryk
 */
public class FilterImageResizeBilinear implements IFilter{
	private int mWidth, mHeight;
	/**
	 * Tworzy nowy filtr do zmiany wielkości obrazu na podany w parametrach
	 * @param width - długość obrazu wynikowego
	 * @param height - wysokość obrazu wynikowego
	 */
	FilterImageResizeBilinear(int width, int height){
		mWidth = width;
		mHeight = height;
	}
	/**
	 * zapisuje obraz original do obrazu image odpowiednio go skalując
	 */
	@Override
	public void apply(PixelData original, PixelData temp) {
		int mOrigWidth = original.getWidth(), mOrigHeight = original.getHeight();
		int mTempWidth = temp.getWidth(), mTempHeight = temp.getHeight();
		float ratioX = (float)mOrigWidth/(float)mTempWidth;
		float ratioY = (float)mOrigHeight/(float)mTempHeight;
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		original.toRGB(); temp.toRGB();
		float ip,jp,mRbeg,mRend,mGbeg,mGend,mBbeg,mBend,coef; 
		int indIbeg, indIend, indJbeg, indJend;
		for(int j=0;j<mTempHeight;j++){
			for(int i=0;i<mTempWidth;i++){
				ip = (float)i*ratioX;
				jp = (float)j*ratioY;
				//wspolrzene kraty próbkowania
				if((int)ip >= mOrigWidth) {indIbeg = mOrigWidth-1;} else {indIbeg = (int)ip;}
				if((int)jp >= mOrigHeight) {indJbeg = mOrigHeight-1;} else {indJbeg = (int)jp;}
				if(indIbeg + 1 >= mOrigWidth) {indIend = mOrigWidth-1;} else {indIend = indIbeg+1;}
				if(indJbeg + 1 >= mOrigHeight) {indJend = mOrigHeight-1;} else {indJend = indJbeg+1;}
				jp = Math.min(mOrigHeight, jp); ip = Math.min(mOrigWidth, ip);
				//probkowanie poziome
				coef = ip-(float)indIbeg;
				mRbeg = origData[3*(indJbeg*mOrigWidth+indIbeg)] * coef + origData[3*(indJbeg*mOrigWidth+indIend)] * (1.0f-coef);
				mGbeg = origData[3*(indJbeg*mOrigWidth+indIbeg)+1] * coef + origData[3*(indJbeg*mOrigWidth+indIend)+1] * (1.0f-coef);
				mBbeg = origData[3*(indJbeg*mOrigWidth+indIbeg)+2] * coef + origData[3*(indJbeg*mOrigWidth+indIend)+2] * (1.0f-coef);
				mRend = origData[3*(indJend*mOrigWidth+indIbeg)] * coef + origData[3*(indJend*mOrigWidth+indIend)] * (1.0f-coef);
				mGend = origData[3*(indJend*mOrigWidth+indIbeg)+1] * coef + origData[3*(indJend*mOrigWidth+indIend)+1] * (1.0f-coef);
				mBend = origData[3*(indJend*mOrigWidth+indIbeg)+2] * coef + origData[3*(indJend*mOrigWidth+indIend)+2] * (1.0f-coef);
				//probkowanie pionowe
				coef = jp-(float)indJbeg;
				tempData[3*(j*mTempWidth+i)] = mRbeg * coef + mRend * (1.0f-coef);
				tempData[3*(j*mTempWidth+i)+1] = mGbeg * coef + mGend * (1.0f-coef);
				tempData[3*(j*mTempWidth+i)+2] = mBbeg * coef + mBend * (1.0f-coef);
			}
		}
	}
	/**
	 * Odwrotnie niż w przypadku zwykłych filtrów zwraca obraz zmodyfikowany, 
	 * zaś image zostawia w spokoju
	 * @return Obraz po zmianie rozmiaru
	 */
	@Override
	public PixelData apply(PixelData image) {
		if(image == null) {return null;}
		PixelData ret = new PixelData(mWidth, mHeight);
		apply(image, ret);
		return ret;
	}
}
