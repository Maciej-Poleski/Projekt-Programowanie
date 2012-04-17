package manager.editor;

/**
 * Klasa służąca do zmiany rozmiary obrazu metodą najbliższego sąsiedztwa - mało dokładna ale szybka
 * @author Patryk
 */
public class FilterImageResizeNearestNeighbour implements IFilter{
	private int mWidth, mHeight;
	FilterImageResizeNearestNeighbour(int width, int height){
		mWidth = width;
		mHeight = height;
	}
	/**
	 * zapisuje obraz original do obrazu image odpowiednio go skalując
	 */
	@Override
	public void apply(PixelData original, PixelData temp){
		int mOrigWidth = original.getWidth(), mOrigHeight = original.getHeight();
		int mTempWidth = temp.getWidth(), mTempHeight = temp.getHeight();
		float ratioX = (float)mOrigWidth/(float)mTempWidth;
		float ratioY = (float)mOrigHeight/(float)mTempHeight;
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		original.toRGB(); temp.toRGB();
		float ip,jp; int indI, indJ;
		for(int j=0;j<mTempHeight;j++){
			for(int i=0;i<mTempWidth;i++){
				ip = (float)i*ratioX;
				jp = (float)j*ratioY;
				if((float)((int)ip+1)-ip < ip-(float)((int)ip)) {indI = (int)ip+1;} else {indI = (int)ip;}
				if((float)((int)jp+1)-jp < jp-(float)((int)jp)) {indJ = (int)jp+1;} else {indJ = (int)jp;}
				indI = Math.max(0, Math.min(indI, mOrigWidth-1));
				indJ = Math.max(0, Math.min(indJ, mOrigHeight-1));
				tempData[PixelData.PIXEL_SIZE*(j*mTempWidth+i)] = origData[PixelData.PIXEL_SIZE*(indJ*mOrigWidth+indI)];
				tempData[PixelData.PIXEL_SIZE*(j*mTempWidth+i)+1] = origData[PixelData.PIXEL_SIZE*(indJ*mOrigWidth+indI)+1];
				tempData[PixelData.PIXEL_SIZE*(j*mTempWidth+i)+2] = origData[PixelData.PIXEL_SIZE*(indJ*mOrigWidth+indI)+2];
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
	
	@Override
	public void reset() {}
}
