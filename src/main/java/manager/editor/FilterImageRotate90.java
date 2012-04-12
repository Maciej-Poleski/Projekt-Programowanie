package manager.editor;

/**
 * Filtr tworzący obraz obrócony o 90 stopni zgodnie z kierunkiem wskazówek zegara
 * @author Patryk
 */
public class FilterImageRotate90 implements IFilter{
	/**
	 * zapisuje obraz original do obrazu image obracając go
	 */
	@Override
	public void apply(PixelData original, PixelData temp){
		int mOrigWidth = original.getWidth(), mOrigHeight = original.getHeight();
		int mTempWidth = temp.getWidth(), mTempHeight = temp.getHeight();
		if(mOrigWidth != mTempHeight || mOrigHeight != mTempWidth){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		original.toRGB(); temp.toRGB();
		for(int i=0;i<mOrigWidth;i++){
			for(int j=0;j<mOrigHeight;j++){
				tempData[3*(i*mTempWidth+mTempWidth-1-j)] = origData[3*(j*mOrigWidth+i)];
				tempData[3*(i*mTempWidth+mTempWidth-1-j)+1] = origData[3*(j*mOrigWidth+i)+1];
				tempData[3*(i*mTempWidth+mTempWidth-1-j)+2] = origData[3*(j*mOrigWidth+i)+2];
			}
		}
	}
	/**
	 * Odwrotnie niż w przypadku zwykłych filtrów zwraca obraz zmodyfikowany, 
	 * zaś image zostawia w spokoju
	 * @return Obraz po obrocie
	 */
	@Override
	public PixelData apply(PixelData image) {
		if(image == null) {return null;}
		PixelData ret = new PixelData(image.getHeight(), image.getWidth());
		apply(image, ret);
		return ret;
	}

}
