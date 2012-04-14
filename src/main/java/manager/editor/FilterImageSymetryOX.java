package manager.editor;

/**
 * Filtr odbija obraz wzdłuż osi pionowej
 * @author Patryk
 */
public class FilterImageSymetryOX implements IFilter{
	/**
	 * zapisuje obraz original do obrazu image odbijając go wzdłuż osi pionowej
	 */
	@Override
	public void apply(PixelData original, PixelData temp){
		int mWidth = original.getWidth(), mHeight = original.getHeight();
		if(mWidth != temp.getWidth() || mHeight != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		original.toRGB(); temp.toRGB();
		for(int i=0;i<mWidth;i++){
			for(int j=0;j<mHeight;j++){
				tempData[PixelData.mPixelSize*(j*mWidth+mWidth-i-1)] = origData[PixelData.mPixelSize*(j*mWidth+i)];
				tempData[PixelData.mPixelSize*(j*mWidth+mWidth-i-1)+1] = origData[PixelData.mPixelSize*(j*mWidth+i)+1];
				tempData[PixelData.mPixelSize*(j*mWidth+mWidth-i-1)+2] = origData[PixelData.mPixelSize*(j*mWidth+i)+2];
			}
		}
	}
	/**
	 * Odwrotnie niż w przypadku zwykłych filtrów zwraca obraz zmodyfikowany, 
	 * zaś image zostawia w spokoju
	 * @return Obraz po odbiciu wzdłuż osi pionowej
	 */
	@Override
	public PixelData apply(PixelData image) {
		if(image == null) {return null;}
		PixelData ret = new PixelData(image.getWidth(), image.getHeight());
		apply(image, ret);
		return ret;
	}
}
