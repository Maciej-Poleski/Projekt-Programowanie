package manager.editor;

/**
 * Filtr umożliwiający kanałową korekte CMY przy pomocy tablicy LUT
 * @author Patryk
 */
public class FilterLUTCorrectionCMY implements IFilterLUT{
	private LUTTable[] mTable = new LUTTable[3];
	
	public FilterLUTCorrectionCMY(){
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
		original.toCMY(); temp.toCMY();
		float mC,mM,mY;
		for(int i=0;i<mWidth;i++){
			for(int j=0;j<mHeight;j++){
				mC = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)] / ColorConverter.RGBCMY_BYTE_MAX;
				mM = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)+1] / ColorConverter.RGBCMY_BYTE_MAX;
				mY = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)+2] / ColorConverter.RGBCMY_BYTE_MAX;
				tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)] = mTable[0].getValue(mC) * ColorConverter.RGBCMY_BYTE_MAX;
				tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)+1] = mTable[1].getValue(mM) * ColorConverter.RGBCMY_BYTE_MAX;
				tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)+2] = mTable[2].getValue(mY) * ColorConverter.RGBCMY_BYTE_MAX;
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
