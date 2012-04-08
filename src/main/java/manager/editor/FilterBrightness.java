package manager.editor;

/**
 * Filtr umożliwiający zmianę jasności obrazu - filtr liniowy
 * @author Patryk
 */
public class FilterBrightness implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(-128.0f, 128.0f, 0.0f)	
	};
	@Override
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null) throw new NullPointerException();
		if(original.mWidth != temp.mWidth || original.mHeight != temp.mHeight) 
			throw new IllegalArgumentException();
		original.toRGB(); temp.toRGB();
		for(int i=0;i<original.mData.length;i++)
			temp.mData[i] = Math.max(0.0f, Math.min(255.0f, original.mData[i] + mRange[0].getValue()));
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
