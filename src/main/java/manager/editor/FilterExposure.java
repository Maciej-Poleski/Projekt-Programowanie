package manager.editor;

/**
 * Filtr umożliwiający dokonanie korekty ekspozycji
 * przyjmuje parametr w jednostkach EV (Exposure Value)
 * jest to współczynnik kierunkowy prostej konwersji (a = 2^EV)
 * @author Patryk
 */
public class FilterExposure implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(-3.0f, 3.0f, 0.0f, "EV")
	};

	@Override
	public void apply(PixelData original, PixelData temp) {
		if(original.getWidth() != temp.getWidth() || original.getHeight() != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		float coef = (float)Math.pow(2.0f, mRange[0].getValue());
		original.toRGB(); temp.toRGB();
		for(int i=0;i<origData.length;i++){
			tempData[i] = Math.max(0.0f, Math.min(ColorConverter.mRGBCMYByteMax, coef*origData[i]));
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
