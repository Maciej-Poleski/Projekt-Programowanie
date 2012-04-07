package manager.editor;

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
					temp.mData[3*(i*original.mHeight+j)] = Math.max(0.0f, Math.min(359.9f, H+Hd));
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
