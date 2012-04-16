package manager.editor;

/**
 * Filtr umożliwiający kanałową korektę RGB przy pomocy tablicy LUT
 * @author Patryk
 */
public class FilterLUTCorrectionRGB implements IFilterLUT{
	private LUTTable[] mTable = new LUTTable[3];
	
	public FilterLUTCorrectionRGB(){
		for(int i=0;i<mTable.length;i++){
			mTable[i] = new LUTTable(null);
		}
	}

	@Override
	public void apply(PixelData original, PixelData temp) {
		int mWidth = original.getWidth(), mHeight = original.getHeight();
		if(mWidth != temp.getWidth() || mHeight != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		original.toRGB(); temp.toRGB();
		float mR,mG,mB;
		for(int i=0;i<mWidth;i++){
			for(int j=0;j<mHeight;j++){
				mR = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)] / ColorConverter.RGBCMY_BYTE_MAX;
				mG = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)+1] / ColorConverter.RGBCMY_BYTE_MAX;
				mB = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)+2] / ColorConverter.RGBCMY_BYTE_MAX;
				tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)] = mTable[0].getValue(mR) * ColorConverter.RGBCMY_BYTE_MAX;
				tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)+1] = mTable[1].getValue(mG) * ColorConverter.RGBCMY_BYTE_MAX;
				tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)+2] = mTable[2].getValue(mB) * ColorConverter.RGBCMY_BYTE_MAX;
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
	public LUTTable[] getConversionTable() {
		return mTable.clone();
	}

	@Override
	public void setConversionTable(LUTTable[] table) {
		if(table == null || table.length != mTable.length){
			throw new IllegalArgumentException();
		}
		for(int i=0;i<table.length;i++){
			if(table[i] == null){
				throw new IllegalArgumentException();
			}
		}
		for(int i=0;i<table.length;i++){
			mTable[i] = table[i];
		}
	}
	
	@Override
	public void reset() {
		for(int i=0;i<mTable.length;i++){
			mTable[i].reset();
		}
	}

}
