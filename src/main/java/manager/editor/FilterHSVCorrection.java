package manager.editor;

/**
 * Filtr umożliwiający kanałową korekcję HSV - zmiana liniowa
 * @author Patryk
 */
public class FilterHSVCorrection implements IFilterRange{
	private final Range[] mRange = new Range[]{
			new Range(-180.0f, 180.0f, 0.0f),
			new Range(-0.5f, 0.5f, 0.0f),
			new Range(-0.5f, 0.5f, 0.0f)
		};

		@Override
		public void apply(PixelData original, PixelData temp)
				throws IllegalArgumentException {
			if(original == null || temp == null) throw new NullPointerException();
			if(original.mWidth != temp.mWidth || original.mHeight != temp.mHeight) 
				throw new IllegalArgumentException();
			original.toHSV(); temp.toHSV();
			float H,S,V;
			float Hd = mRange[0].getValue(), Sd = mRange[1].getValue(), Vd = mRange[2].getValue();
			for(int i=0;i<original.mWidth;i++)
				for(int j=0;j<original.mHeight;j++){
					H = original.mData[3*(i*original.mHeight+j)];
					S = original.mData[3*(i*original.mHeight+j)+1];
					V = original.mData[3*(i*original.mHeight+j)+2];
					H+=Hd; if(H >= 360.0f) H -= 360.0f;
					if(H < 0.0f) H += 360.0f;
					temp.mData[3*(i*original.mHeight+j)] = H;
					temp.mData[3*(i*original.mHeight+j)+1] = Math.max(0.0f, Math.min(1.0f, S+Sd));
					temp.mData[3*(i*original.mHeight+j)+2] = Math.max(0.0f, Math.min(1.0f, V+Vd));
				}	
		}

		@Override
		public PixelData apply(PixelData image) {
			if(image == null) return null;
			PixelData ret = (PixelData)image.clone();
			apply(image, image);
			return ret;
		}

		@Override
		public Range[] getRangeTable() {
			return mRange;
		}
}
