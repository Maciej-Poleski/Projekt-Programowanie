package manager.editor;

public class FilterGamma implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(0.2f, 5.0f, 1.0f)	
	};
	
	@Override
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null) throw new NullPointerException();
		if(original.mWidth != temp.mWidth || original.mHeight != temp.mHeight) 
			throw new IllegalArgumentException();
		float mLUT[] = new float[256];
		float gamma = 1 / mRange[0].getValue();
		for(int i=0;i<256;i++) mLUT[i] = 255.0f * (float)Math.pow((float)i/255.0f, gamma);
		original.toRGB(); temp.toRGB();
		for(int i=0;i<original.mData.length;i++)
			temp.mData[i] = mLUT[(int)original.mData[i]];
	}

	@Override
	public PixelData apply(PixelData image) {
		if(image == null) return null;
		PixelData ret = (PixelData)image.clone();
		apply(image, image);
		return ret;
	}

	@Override
	public Range[] getRangeTable() {
		return mRange;
	}

}
