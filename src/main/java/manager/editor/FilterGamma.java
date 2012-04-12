package manager.editor;

/**
 * Filtr umożliwiający dokonanie korekcji Gamma na obrazie
 * @author Patryk
 */
public class FilterGamma implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(0.2f, 5.0f, 1.0f, "Gamma")	
	};
	
	@Override
	public void apply(PixelData original, PixelData temp) {
		if(original.getWidth() != temp.getWidth() || original.getHeight() != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		float mLUT[] = new float[256];
		float gamma = 1 / mRange[0].getValue();
		for(int i=0;i<256;i++) {
			mLUT[i] = 255.0f * (float)Math.pow((float)i/255.0f, gamma);
		}
		original.toRGB(); temp.toRGB();
		for(int i=0;i<origData.length;i++){
			tempData[i] = mLUT[(int)origData[i]];
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
