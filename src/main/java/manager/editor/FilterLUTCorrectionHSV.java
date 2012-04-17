package manager.editor;

/**
 * Filtr umożliwiający kanałową korekte HSV przy pomocy tablicy LUT
 * @author Patryk
 */
public class FilterLUTCorrectionHSV implements IFilterLUT{
	private LUTTable[] mTable = new LUTTable[3];
	
	public FilterLUTCorrectionHSV(){
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
		original.toHSV(); temp.toHSV();
		float mH,mS,mV;
		for(int i=0;i<mWidth;i++){
			for(int j=0;j<mHeight;j++){
				mH = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)] / ColorConverter.HUE_MAX;
				mS = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)+1];
				mV = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)+2];
				tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)] = mTable[0].getValue(mH) * ColorConverter.HUE_MAX;
				tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)+1] = mTable[1].getValue(mS);
				tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)+2] = mTable[2].getValue(mV);
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
