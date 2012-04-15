package manager.editor;

/**
 * Filtr odpowiedzialny za binaryzację obrazu filtracja odbywa sie wg wzoru:
 * - dla pixeli o jasności średniej poniżej progu -> czarny
 * - dla pixeli o jasności średniej powyżej lub równej progowi -> biały
 * @author Patryk
 */
public class FilterBinaryzation implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(0.0f, ColorConverter.mRGBCMYByteMax, ColorConverter.mRGBCMYByteMax / 2.0f, "Próg")	
	};

	@Override
	public void apply(PixelData original, PixelData temp) {
		int mWidth = original.getWidth(), mHeight = original.getHeight();
		if(mWidth != temp.getWidth() || mHeight != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		float prog = mRange[0].getValue(), gray;
		original.toRGB(); temp.toRGB();
		for(int j=0;j<mHeight;j++){
			for(int i=0;i<mWidth;i++){
				gray = (origData[PixelData.mPixelSize*(j*mWidth+i)]+
						origData[PixelData.mPixelSize*(j*mWidth+i)+1]+
						origData[PixelData.mPixelSize*(j*mWidth+i)+2]) / ((float)PixelData.mPixelSize);
				if(gray < prog) {
					tempData[PixelData.mPixelSize*(j*mWidth+i)] = 0.0f;
					tempData[PixelData.mPixelSize*(j*mWidth+i)+1] = 0.0f;
					tempData[PixelData.mPixelSize*(j*mWidth+i)+2] = 0.0f;
				} else {
					tempData[PixelData.mPixelSize*(j*mWidth+i)] = ColorConverter.mRGBCMYByteMax;
					tempData[PixelData.mPixelSize*(j*mWidth+i)+1] = ColorConverter.mRGBCMYByteMax;
					tempData[PixelData.mPixelSize*(j*mWidth+i)+2] = ColorConverter.mRGBCMYByteMax;
				}
			}
		}
	}

	@Override
	public PixelData apply(PixelData image) {
		if(image == null) {return null;}
		PixelData ret = (PixelData)image.clone();
		apply(image, image);
		return ret;
	}

	@Override
	public Range[] getRangeTable() {
		return mRange;
	}

}
