package manager.editor;

/**
 * Filtr umożliwiający kanałową korekcję HSV - zmiana liniowa
 * @author Patryk
 */
public class FilterHSVCorrection implements IFilterRange{
	private final Range[] mRange = new Range[]{
			new Range(-ColorConverter.HUE_MAX/2.0f, ColorConverter.HUE_MAX/2.0f, 0.0f, "Barwa"),
			new Range(-ColorConverter.RGBCMY_FLOAT_MAX/2.0f, ColorConverter.RGBCMY_FLOAT_MAX/2.0f, 0.0f, "Nasycenie"),
			new Range(-ColorConverter.RGBCMY_FLOAT_MAX/2.0f, ColorConverter.RGBCMY_FLOAT_MAX/2.0f, 0.0f, "Jasność")
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
					mH = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)];
					mS = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)+1];
					mV = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)+2];
					mH+=dH; if(mH >= ColorConverter.HUE_MAX) {mH -= ColorConverter.HUE_MAX;}
					if(mH < 0.0f) {mH += ColorConverter.HUE_MAX;}
					tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)] = mH;
					tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)+1] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_FLOAT_MAX, mS+dS));
					tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)+2] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_FLOAT_MAX, mV+dV));
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
