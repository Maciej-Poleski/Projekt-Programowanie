package manager.editor;

/**
 * Filtr odbija obraz wzdłuż osi poziomej
 * @author Patryk
 */
public class FilterImageSymetryOY implements IFilter{
	/**
	 * zapisuje obraz original do obrazu image odbijając go wzdłuż osi poziomej
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
				tempData[PixelData.PIXEL_SIZE*((mHeight-1-j)*mWidth+i)] = origData[PixelData.PIXEL_SIZE*(j*mWidth+i)];
				tempData[PixelData.PIXEL_SIZE*((mHeight-1-j)*mWidth+i)+1] = origData[PixelData.PIXEL_SIZE*(j*mWidth+i)+1];
				tempData[PixelData.PIXEL_SIZE*((mHeight-1-j)*mWidth+i)+2] = origData[PixelData.PIXEL_SIZE*(j*mWidth+i)+2];
			}
		}
	}
	/**
	 * Odwrotnie niż w przypadku zwykłych filtrów zwraca obraz zmodyfikowany, 
	 * zaś image zostawia w spokoju
	 * @return Obraz po odbiciu wzdłuż osi poziomej
	 */
	@Override
	public PixelData apply(PixelData image) {
		if(image == null) {return null;}
		PixelData ret = new PixelData(image.getWidth(), image.getHeight());
		apply(image, ret);
		return ret;
	}
}