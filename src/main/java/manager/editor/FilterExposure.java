package manager.editor;

/**
 * Filtr umożliwiający dokonanie korekty ekspozycji
 * przyjmuje parametr w jednostkach EV (Exposure Value)
 * jest to współczynnik kierunkowy prostej konwersji (a = 2^EV)
 * @author Patryk
 */
public class FilterExposure implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(-3.0f, 3.0f, 0.0f)
	};

	@Override
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null) throw new NullPointerException();
		if(original.mWidth != temp.mWidth || original.mHeight != temp.mHeight) 
			throw new IllegalArgumentException();
		float coef = (float)Math.pow(2.0f, mRange[0].getValue());
		original.toRGB(); temp.toRGB();
		for(int i=0;i<original.mData.length;i++)
			temp.mData[i] = Math.max(0.0f, Math.min(255.0f, coef*original.mData[i]));
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
