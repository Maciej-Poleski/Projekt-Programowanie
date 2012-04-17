package manager.editor;

/**
 * Filtr tworzący fotograficzny efekt sepii.
 * @author Patryk
 */
public class FilterSepia implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(20.0f, 50.0f, 30.0f, "Nasycenie")
	};

	@Override
	public void apply(PixelData original, PixelData temp){
		int mWidth = original.getWidth(), mHeight = original.getHeight();
		if(mWidth != temp.getWidth() || mHeight != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		float det = mRange[0].getValue(), grey;
		original.toRGB(); temp.toRGB();
		for(int i=0;i<mWidth;i++){
			for(int j=0;j<mHeight;j++){
				grey = ColorConverter.RED_LUMINOSITY * origData[PixelData.PIXEL_SIZE*(i*mHeight+j)] + 
						ColorConverter.GREEN_LUMINOSITY * origData[PixelData.PIXEL_SIZE*(i*mHeight+j)+1] + 
						ColorConverter.BLUE_LUMINOSITY * origData[PixelData.PIXEL_SIZE*(i*mHeight+j)+2];
				tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)] = Math.min(ColorConverter.RGBCMY_BYTE_MAX, grey+2*det);
				tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)+1] = Math.min(ColorConverter.RGBCMY_BYTE_MAX, grey+det);
				tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)+2] = Math.min(ColorConverter.RGBCMY_BYTE_MAX, grey);
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
	
	@Override
	public void reset() {
		for(int i=0;i<mRange.length;i++){
			mRange[i].reset();
		}
	}
}
