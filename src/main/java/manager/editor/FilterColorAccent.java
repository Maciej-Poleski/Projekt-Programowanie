package manager.editor;

import java.awt.Color;

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
		new Range(0.0f, ColorConverter.mHueMaxValue, 0.0f, "Barwa"),
		new Range(0.0f, ColorConverter.mHueMaxValue / 2.0f, 10.0f, "Tolerancja")
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
				mH = origData[PixelData.mPixelSize*(j*mWidth+i)];
				mS = origData[PixelData.mPixelSize*(j*mWidth+i)+1];
				mV = origData[PixelData.mPixelSize*(j*mWidth+i)+2];
				if((mHmin <= mH && mH <= mHmax) || 
						(mHmin <= mH - ColorConverter.mHueMaxValue && mH - ColorConverter.mHueMaxValue <= mHmax) || 
						(mHmin <= mH + ColorConverter.mHueMaxValue && mH + ColorConverter.mHueMaxValue <= mHmax)){
					tempData[PixelData.mPixelSize*(j*mWidth+i)+1] = mS;
				} else {
					tempData[PixelData.mPixelSize*(j*mWidth+i)+1] = 0.0f;
				}
				tempData[PixelData.mPixelSize*(j*mWidth+i)] = mH;
				tempData[PixelData.mPixelSize*(j*mWidth+i)+2] = mV;
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
		return mRange;
	}

}
