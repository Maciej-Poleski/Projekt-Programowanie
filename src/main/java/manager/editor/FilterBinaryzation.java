package manager.editor;

/**
 * Filtr odpowiedzialny za binaryzację obrazu filtracja odbywa sie wg wzoru:
 * - dla pixeli o jasności średniej poniżej progu -> czarny
 * - dla pixeli o jasności średniej powyżej lub równej progowi -> biały
 * @author Patryk
 */
public class FilterBinaryzation implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(0.0f, 255.0f, 127.5f)	
	};

	@Override
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null) throw new NullPointerException();
		if(original.mWidth != temp.mWidth || original.mHeight != temp.mHeight) 
			throw new IllegalArgumentException();
		float prog = mRange[0].getValue(), gray;
		original.toRGB(); temp.toRGB();
		for(int j=0;j<original.mHeight;j++)
			for(int i=0;i<original.mWidth;i++){
				gray = (original.mData[3*(j*original.mWidth+i)]+original.mData[3*(j*original.mWidth+i)+1]+original.mData[3*(j*original.mWidth+i)+2])/3.0f;
				if(gray < prog) temp.mData[3*(j*original.mWidth+i)] = temp.mData[3*(j*original.mWidth+i)+1] = temp.mData[3*(j*original.mWidth+i)+2] = 0.0f;
				else temp.mData[3*(j*original.mWidth+i)] = temp.mData[3*(j*original.mWidth+i)+1] = temp.mData[3*(j*original.mWidth+i)+2] = 255.0f;
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
