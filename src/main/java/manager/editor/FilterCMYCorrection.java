package manager.editor;

public class FilterCMYCorrection implements IFilterRange{
	private final Range[] mRange = new Range[]{
			new Range(-128.0f, 128.0f, 0.0f),
			new Range(-128.0f, 128.0f, 0.0f),
			new Range(-128.0f, 128.0f, 0.0f)
		};

		@Override
		public void apply(PixelData original, PixelData temp)
				throws IllegalArgumentException {
			if(original == null || temp == null) throw new NullPointerException();
			if(original.mWidth != temp.mWidth || original.mHeight != temp.mHeight) 
				throw new IllegalArgumentException();
			original.toCMY(); temp.toCMY();
			float C,M,Y;
			float Cd = mRange[0].getValue(), Md = mRange[1].getValue(), Yd = mRange[2].getValue();
			for(int i=0;i<original.mWidth;i++)
				for(int j=0;j<original.mHeight;j++){
					C = original.mData[3*(i*original.mHeight+j)];
					M = original.mData[3*(i*original.mHeight+j)+1];
					Y = original.mData[3*(i*original.mHeight+j)+2];
					temp.mData[3*(i*original.mHeight+j)] = Math.max(0.0f, Math.min(255.0f, C+Cd));
					temp.mData[3*(i*original.mHeight+j)+1] = Math.max(0.0f, Math.min(255.0f, M+Md));
					temp.mData[3*(i*original.mHeight+j)+2] = Math.max(0.0f, Math.min(255.0f, Y+Yd));
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
