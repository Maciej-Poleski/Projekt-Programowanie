package manager.editor;

/**
 * Filtr umożliwiający kanałową korektę CMY - filtr liniowy
 * @author Patryk
 */
public class FilterCMYCorrection implements IFilterRange{
	private final Range[] mRange = new Range[]{
			new Range(-ColorConverter.RGBCMY_BYTE_MAX/2.0f, ColorConverter.RGBCMY_BYTE_MAX/2.0f, 0.0f, "Cyjan"),
			new Range(-ColorConverter.RGBCMY_BYTE_MAX/2.0f, ColorConverter.RGBCMY_BYTE_MAX/2.0f, 0.0f, "Purpura"),
			new Range(-ColorConverter.RGBCMY_BYTE_MAX/2.0f, ColorConverter.RGBCMY_BYTE_MAX/2.0f, 0.0f, "Żółty")
		};

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
			float dC = mRange[0].getValue(), dM = mRange[1].getValue(), dY = mRange[2].getValue();
			for(int i=0;i<mWidth;i++){
				for(int j=0;j<mHeight;j++){
					mC = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)];
					mM = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)+1];
					mY = origData[PixelData.PIXEL_SIZE*(i*mHeight+j)+2];
					tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, mC+dC));
					tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)+1] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, mM+dM));
					tempData[PixelData.PIXEL_SIZE*(i*mHeight+j)+2] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, mY+dY));
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
