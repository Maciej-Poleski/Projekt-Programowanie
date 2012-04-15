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
		float mLUT[] = new float[PixelData.RGBCMY_CHANNEL_PRECISION];
		for(int i=0;i<PixelData.RGBCMY_CHANNEL_PRECISION;i++){
			mLUT[i] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, mRange[0].getValue()*((float)i-PixelData.RGBCMY_CHANNEL_PRECISION/2.0f) + PixelData.RGBCMY_CHANNEL_PRECISION/2.0f));
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
		return mRange.clone();
	}

	@Override
	public void setRangeTable(Range[] table) {
		if(table == null || table.length != mRange.length){
			throw new IllegalArgumentException();
		}
		for(int i=0;i<table.length;i++){
			if(table[i].getMin() != mRange[i].getMin() || table[i].getMax() != mRange[i].getMax()){
				throw new IllegalArgumentException();
			}
		}
		for(int i=0;i<table.length;i++){
			mRange[i].setValue(table[i].getValue());
		}
	}

}
