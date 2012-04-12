package manager.editor;

/**
 * Filtr tworzący fotograficzny efekt sepii.
 * @author Patryk
 */
public class FilterSepia implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(20.0f, 50.0f, 30.0f, "Nasycenie")
	};

	@Override
	public void apply(PixelData original, PixelData temp){
		int mWidth = original.getWidth(), mHeight = original.getHeight();
		if(mWidth != temp.getWidth() || mHeight != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		float det = mRange[0].getValue(), grey;
		original.toRGB(); temp.toRGB();
		for(int i=0;i<mWidth;i++){
			for(int j=0;j<mHeight;j++){
				grey = 0.21f*origData[3*(i*mHeight+j)] + 0.71f*origData[3*(i*mHeight+j)+1] + 0.07f*origData[3*(i*mHeight+j)+2];
				tempData[3*(i*mHeight+j)] = Math.min(255.0f, grey+2*det);
				tempData[3*(i*mHeight+j)+1] = Math.min(255.0f, grey+det);
				tempData[3*(i*mHeight+j)+2] = Math.min(255.0f, grey);
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
