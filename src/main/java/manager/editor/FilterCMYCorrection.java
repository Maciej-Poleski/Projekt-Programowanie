package manager.editor;

/**
 * Filtr umożliwiający kanałową korektę CMY - filtr liniowy
 * @author Patryk
 */
public class FilterCMYCorrection implements IFilterRange{
	private final Range[] mRange = new Range[]{
			new Range(-128.0f, 128.0f, 0.0f, "Cyjan"),
			new Range(-128.0f, 128.0f, 0.0f, "Purpura"),
			new Range(-128.0f, 128.0f, 0.0f, "Żółty")
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
					mC = origData[3*(i*mHeight+j)];
					mM = origData[3*(i*mHeight+j)+1];
					mY = origData[3*(i*mHeight+j)+2];
					tempData[3*(i*mHeight+j)] = Math.max(0.0f, Math.min(255.0f, mC+dC));
					tempData[3*(i*mHeight+j)+1] = Math.max(0.0f, Math.min(255.0f, mM+dM));
					tempData[3*(i*mHeight+j)+2] = Math.max(0.0f, Math.min(255.0f, mY+dY));
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
