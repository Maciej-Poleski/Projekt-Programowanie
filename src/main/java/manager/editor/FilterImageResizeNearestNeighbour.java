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
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null) throw new NullPointerException();
		float ratioX = (float)original.mWidth/(float)temp.mWidth;
		float ratioY = (float)original.mHeight/(float)temp.mHeight;
		original.toRGB(); temp.toRGB();
		float ip,jp; int indI, indJ;
		for(int j=0;j<temp.mHeight;j++)
			for(int i=0;i<temp.mWidth;i++){
				ip = (float)i*ratioX;
				jp = (float)j*ratioY;
				if((float)((int)ip+1)-ip < ip-(float)((int)ip)) indI = (int)ip+1; else indI = (int)ip;
				if((float)((int)jp+1)-jp < jp-(float)((int)jp)) indJ = (int)jp+1; else indJ = (int)jp;
				indI = Math.max(0, Math.min(indI, original.mWidth-1));
				indJ = Math.max(0, Math.min(indJ, original.mHeight-1));
				temp.mData[3*(j*temp.mWidth+i)] = original.mData[3*(indJ*original.mWidth+indI)];
				temp.mData[3*(j*temp.mWidth+i)+1] = original.mData[3*(indJ*original.mWidth+indI)+1];
				temp.mData[3*(j*temp.mWidth+i)+2] = original.mData[3*(indJ*original.mWidth+indI)+2];
			}
	}
	/**
	 * Odwrotnie niż w przypadku zwykłych filtrów zwraca obraz zmodyfikowany, 
	 * zaś image zostawia w spokoju
	 * @return Obraz po zmianie rozmiaru
	 */
	@Override
	public PixelData apply(PixelData image) {
		if(image == null) return null;
		PixelData ret = new PixelData(mWidth, mHeight);
		apply(image, ret);
		return ret;
	}
}
