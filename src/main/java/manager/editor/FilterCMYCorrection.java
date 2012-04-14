package manager.editor;

/**
 * Filtr umożliwiający kanałową korektę CMY - filtr liniowy
 * @author Patryk
 */
public class FilterCMYCorrection implements IFilterRange{
	private final Range[] mRange = new Range[]{
			new Range(-ColorConverter.mRGBCMYByteMax / 2.0f, ColorConverter.mRGBCMYByteMax / 2.0f, 0.0f, "Cyjan"),
			new Range(-ColorConverter.mRGBCMYByteMax / 2.0f, ColorConverter.mRGBCMYByteMax / 2.0f, 0.0f, "Purpura"),
			new Range(-ColorConverter.mRGBCMYByteMax / 2.0f, ColorConverter.mRGBCMYByteMax / 2.0f, 0.0f, "Żółty")
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
					mC = origData[PixelData.mPixelSize*(i*mHeight+j)];
					mM = origData[PixelData.mPixelSize*(i*mHeight+j)+1];
					mY = origData[PixelData.mPixelSize*(i*mHeight+j)+2];
					tempData[PixelData.mPixelSize*(i*mHeight+j)] = Math.max(0.0f, Math.min(ColorConverter.mRGBCMYByteMax, mC+dC));
					tempData[PixelData.mPixelSize*(i*mHeight+j)+1] = Math.max(0.0f, Math.min(ColorConverter.mRGBCMYByteMax, mM+dM));
					tempData[PixelData.mPixelSize*(i*mHeight+j)+2] = Math.max(0.0f, Math.min(ColorConverter.mRGBCMYByteMax, mY+dY));
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
