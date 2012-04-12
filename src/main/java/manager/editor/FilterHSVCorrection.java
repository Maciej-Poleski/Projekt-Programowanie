package manager.editor;

/**
 * Filtr umożliwiający kanałową korekcję HSV - zmiana liniowa
 * @author Patryk
 */
public class FilterHSVCorrection implements IFilterRange{
	private final Range[] mRange = new Range[]{
			new Range(-180.0f, 180.0f, 0.0f, "Barwa"),
			new Range(-0.5f, 0.5f, 0.0f, "Nasycenie"),
			new Range(-0.5f, 0.5f, 0.0f, "Jasność")
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
					mH = origData[3*(i*mHeight+j)];
					mS = origData[3*(i*mHeight+j)+1];
					mV = origData[3*(i*mHeight+j)+2];
					mH+=dH; if(mH >= 360.0f) {mH -= 360.0f;}
					if(mH < 0.0f) {mH += 360.0f;}
					tempData[3*(i*mHeight+j)] = mH;
					tempData[3*(i*mHeight+j)+1] = Math.max(0.0f, Math.min(1.0f, mS+dS));
					tempData[3*(i*mHeight+j)+2] = Math.max(0.0f, Math.min(1.0f, mV+dV));
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
