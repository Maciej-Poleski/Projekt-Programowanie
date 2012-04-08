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
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null) throw new NullPointerException();
		if(original.mWidth != temp.mWidth || original.mHeight != temp.mHeight)
			throw new IllegalArgumentException();
		original.toRGB(); temp.toRGB();
		for(int i=0;i<original.mWidth;i++)
			for(int j=0;j<original.mHeight;j++){
				temp.mData[3*((original.mHeight-1-j)*original.mWidth+i)] = original.mData[3*(j*original.mWidth+i)];
				temp.mData[3*((original.mHeight-1-j)*original.mWidth+i)+1] = original.mData[3*(j*original.mWidth+i)+1];
				temp.mData[3*((original.mHeight-1-j)*original.mWidth+i)+2] = original.mData[3*(j*original.mWidth+i)+2];
			}
	}
	/**
	 * Odwrotnie niż w przypadku zwykłych filtrów zwraca obraz zmodyfikowany, 
	 * zaś image zostawia w spokoju
	 * @return Obraz po odbiciu wzdłuż osi poziomej
	 */
	@Override
	public PixelData apply(PixelData image) {
		if(image == null) return null;
		PixelData ret = new PixelData(image.mWidth, image.mHeight);
		apply(image, ret);
		return ret;
	}
}