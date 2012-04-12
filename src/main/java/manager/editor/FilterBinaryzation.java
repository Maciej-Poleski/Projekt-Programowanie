package manager.editor;

/**
 * Filtr odpowiedzialny za binaryzację obrazu filtracja odbywa sie wg wzoru:
 * - dla pixeli o jasności średniej poniżej progu -> czarny
 * - dla pixeli o jasności średniej powyżej lub równej progowi -> biały
 * @author Patryk
 */
public class FilterBinaryzation implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(0.0f, 255.0f, 127.5f, "Próg")	
	};

	@Override
	public void apply(PixelData original, PixelData temp) {
		int mWidth = original.getWidth(), mHeight = original.getHeight();
		if(mWidth != temp.getWidth() || mHeight != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		float prog = mRange[0].getValue(), gray;
		original.toRGB(); temp.toRGB();
		for(int j=0;j<mHeight;j++){
			for(int i=0;i<mWidth;i++){
				gray = (origData[3*(j*mWidth+i)]+origData[3*(j*mWidth+i)+1]+origData[3*(j*mWidth+i)+2])/3.0f;
				if(gray < prog) {
					tempData[3*(j*mWidth+i)] = tempData[3*(j*mWidth+i)+1] = tempData[3*(j*mWidth+i)+2] = 0.0f;
				} else {
					tempData[3*(j*mWidth+i)] = tempData[3*(j*mWidth+i)+1] = tempData[3*(j*mWidth+i)+2] = 255.0f;
				}
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
