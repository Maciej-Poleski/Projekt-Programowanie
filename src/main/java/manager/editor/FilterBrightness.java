package manager.editor;

/**
 * Filtr umożliwiający zmianę jasności obrazu - filtr liniowy
 * @author Patryk
 */
public class FilterBrightness implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(-ColorConverter.RGBCMY_BYTE_MAX / 2.0f, ColorConverter.RGBCMY_BYTE_MAX / 2.0f, 0.0f, "Jasność")	
	};
	@Override
	public void apply(PixelData original, PixelData temp) {
		if(original.getWidth() != temp.getWidth() || original.getHeight() != temp.getHeight()){ 
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		float delta = mRange[0].getValue();
		original.toRGB(); temp.toRGB();
		for(int i=0;i<origData.length;i++){
			tempData[i] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, origData[i] + delta));
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
