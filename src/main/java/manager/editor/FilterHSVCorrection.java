package manager.editor;

/**
 * Filtr umożliwiający kanałową korekcję HSV - zmiana liniowa
 * @author Patryk
 */
public class FilterHSVCorrection implements IFilterRange{
	private final Range[] mRange = new Range[]{
			new Range(-ColorConverter.mHueMaxValue/2.0f, ColorConverter.mHueMaxValue/2.0f, 0.0f, "Barwa"),
			new Range(-ColorConverter.mRGBCMYSVFloatMax/2.0f, ColorConverter.mRGBCMYSVFloatMax/2.0f, 0.0f, "Nasycenie"),
			new Range(-ColorConverter.mRGBCMYSVFloatMax/2.0f, ColorConverter.mRGBCMYSVFloatMax/2.0f, 0.0f, "Jasność")
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
			float mH,mS,mV;
			float dH = mRange[0].getValue(), dS = mRange[1].getValue(), dV = mRange[2].getValue();
			for(int i=0;i<mWidth;i++){
				for(int j=0;j<mHeight;j++){
					mH = origData[PixelData.mPixelSize*(i*mHeight+j)];
					mS = origData[PixelData.mPixelSize*(i*mHeight+j)+1];
					mV = origData[PixelData.mPixelSize*(i*mHeight+j)+2];
					mH+=dH; if(mH >= ColorConverter.mHueMaxValue) {mH -= ColorConverter.mHueMaxValue;}
					if(mH < 0.0f) {mH += ColorConverter.mHueMaxValue;}
					tempData[PixelData.mPixelSize*(i*mHeight+j)] = mH;
					tempData[PixelData.mPixelSize*(i*mHeight+j)+1] = Math.max(0.0f, Math.min(ColorConverter.mRGBCMYSVFloatMax, mS+dS));
					tempData[PixelData.mPixelSize*(i*mHeight+j)+2] = Math.max(0.0f, Math.min(ColorConverter.mRGBCMYSVFloatMax, mV+dV));
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
