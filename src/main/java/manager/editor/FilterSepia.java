package manager.editor;

public class FilterSepia implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(20.0f, 50.0f, 30.0f)
	};

	@Override
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null ||original.mWidth != temp.mWidth || original.mHeight != temp.mHeight) 
			throw new IllegalArgumentException();
		float det=mRange[0].getValue(), grey;
		original.toRGB();
		for(int i=0;i<original.mWidth;i++)
			for(int j=0;j<original.mHeight;j++){
				grey = 0.21f*temp.mData[3*(i*original.mHeight+j)] + 0.71f*temp.mData[3*(i*original.mHeight+j)+1] + 0.07f*temp.mData[3*(i*original.mHeight+j)+2];
				temp.mData[3*(i*original.mHeight+j)] = Math.min(255.0f, grey+2*det);
				temp.mData[3*(i*original.mHeight+j)+1] = Math.min(255.0f, grey+det);
				temp.mData[3*(i*original.mHeight+j)+2] = Math.min(255.0f, grey);
			}
	}

	@Override
	public PixelData apply(PixelData image) {
		if(image == null) return null;
		PixelData ret = null;
		try {ret = (PixelData)image.clone();}catch(CloneNotSupportedException e){e.printStackTrace();}
		image.toRGB();
		apply(image, image);
		return ret;
	}

	@Override
	public Range[] getRangeTable() {
		return mRange;
	}
}
