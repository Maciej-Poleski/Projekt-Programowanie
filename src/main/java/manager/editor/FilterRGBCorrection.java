package manager.editor;

public class FilterRGBCorrection implements IFilterRange{
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
		original.toRGB(); temp.toRGB();
		float R,G,B;
		float Rd = mRange[0].getValue(), Gd = mRange[1].getValue(), Bd = mRange[2].getValue();
		for(int i=0;i<original.mWidth;i++)
			for(int j=0;j<original.mHeight;j++){
				R = original.mData[3*(i*original.mHeight+j)];
				G = original.mData[3*(i*original.mHeight+j)+1];
				B = original.mData[3*(i*original.mHeight+j)+2];
				temp.mData[3*(i*original.mHeight+j)] = Math.max(0.0f, Math.min(255.0f, R+Rd));
				temp.mData[3*(i*original.mHeight+j)+1] = Math.max(0.0f, Math.min(255.0f, G+Gd));
				temp.mData[3*(i*original.mHeight+j)+2] = Math.max(0.0f, Math.min(255.0f, B+Bd));
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
