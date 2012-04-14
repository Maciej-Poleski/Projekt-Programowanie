package manager.editor;

/**
 * Filtr umożliwiający zmianę kontrastu obrazu - filtr liniowy
 * @author Patryk
 */
public class FilterContrast implements IFilterRange {
	private final Range[] mRange = new Range[]{
			new Range(0.1f, 10.0f, 1.0f, "Kontrast")
	};

	@Override
	public void apply(PixelData original, PixelData temp){
		if(original.getWidth() != temp.getWidth() || original.getHeight() != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		float mLUT[] = new float[PixelData.mRGBCMYChannelPrecision];
		for(int i=0;i<PixelData.mRGBCMYChannelPrecision;i++){
			mLUT[i] = Math.max(0.0f, Math.min(ColorConverter.mRGBCMYByteMax, mRange[0].getValue()*((float)i-PixelData.mRGBCMYChannelPrecision/2.0f) + PixelData.mRGBCMYChannelPrecision/2.0f));
		}
		original.toRGB(); temp.toRGB();
		for(int i=0;i<origData.length;i++){
			tempData[i] = mLUT[(int)origData[i]];
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
