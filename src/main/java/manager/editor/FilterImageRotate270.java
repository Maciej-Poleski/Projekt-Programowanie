package manager.editor;

/**
 * Filtr tworzący obraz obrócony o 90 stopni przeciwnie do kierunku wskazówek zegara
 * @author Patryk
 */
public class FilterImageRotate270 implements IFilter{
	/**
	 * zapisuje obraz original do obrazu image obracając go
	 */
	@Override
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null) throw new NullPointerException();
		if(original.mWidth != temp.mHeight || original.mHeight != temp.mWidth)
			throw new IllegalArgumentException();
		original.toRGB(); temp.toRGB();
		for(int i=0;i<original.mWidth;i++)
			for(int j=0;j<original.mHeight;j++){
				temp.mData[3*((temp.mHeight-1-i)*temp.mWidth+j)] = original.mData[3*(j*original.mWidth+i)];
				temp.mData[3*((temp.mHeight-1-i)*temp.mWidth+j)+1] = original.mData[3*(j*original.mWidth+i)+1];
				temp.mData[3*((temp.mHeight-1-i)*temp.mWidth+j)+2] = original.mData[3*(j*original.mWidth+i)+2];
			}
	}
	/**
	 * Odwrotnie niż w przypadku zwykłych filtrów zwraca obraz zmodyfikowany, 
	 * zaś image zostawia w spokoju
	 * @return Obraz po obrocie
	 */
	@Override
	public PixelData apply(PixelData image) {
		if(image == null) return null;
		PixelData ret = new PixelData(image.mHeight, image.mWidth);
		apply(image, ret);
		return ret;
	}

}
