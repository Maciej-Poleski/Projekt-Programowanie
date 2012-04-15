package manager.editor;

/**
 * Filtr odpowiedzialny za tworzenie akcentu kolorystycznego
 * filtr dwu-argumentowy:
 * arg1 - barwa wyróżniona
 * arg2 - tolerancja
 * Filtr pozostawia kolory mieszczące sie w przedziale tolerancji bez zmian, zaś pozostałe zmienia
 * w odcienie szarości metodą luminestencji
 * @author Patryk
 */
public class FilterColorAccent implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(0.0f, 360.0f, 0.0f, "Barwa"),
		new Range(0.0f, 180.0f, 10.0f, "Tolerancja")
	};
	
	@Override
	public void apply(PixelData original, PixelData temp){
		int mWidth = original.getWidth(), mHeight = original.getHeight();
		if(mWidth != temp.getWidth() || mHeight != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		original.toHSV(); temp.toHSV();
		float mHue = mRange[0].getValue(), mTol = mRange[1].getValue();
		float mHmax = mHue+mTol, mHmin = mHue-mTol;
		float mH=0,mS=0,mV=0;
		for(int i=0;i<mWidth;i++){
			for(int j=0;j<mHeight;j++){
				mH = origData[PixelData.PIXEL_SIZE*(j*mWidth+i)];
				mS = origData[PixelData.PIXEL_SIZE*(j*mWidth+i)+1];
				mV = origData[PixelData.PIXEL_SIZE*(j*mWidth+i)+2];
				if((mHmin <= mH && mH <= mHmax) || 
						(mHmin <= mH - ColorConverter.HUE_MAX && mH - ColorConverter.HUE_MAX <= mHmax) || 
						(mHmin <= mH + ColorConverter.HUE_MAX && mH + ColorConverter.HUE_MAX <= mHmax)){
					tempData[PixelData.PIXEL_SIZE*(j*mWidth+i)+1] = mS;
				} else {
					tempData[PixelData.PIXEL_SIZE*(j*mWidth+i)+1] = 0.0f;
				}
				tempData[PixelData.PIXEL_SIZE*(j*mWidth+i)] = mH;
				tempData[PixelData.PIXEL_SIZE*(j*mWidth+i)+2] = mV;
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

}
