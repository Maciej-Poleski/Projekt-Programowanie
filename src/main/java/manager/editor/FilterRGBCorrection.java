package manager.editor;

/**
 * Filtr umożliwiający kanałową korekcję kanałów RGB - zmiana liniowa
 * @author Patryk
 */
public class FilterRGBCorrection implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(-128.0f, 128.0f, 0.0f, "Czerwony"),
		new Range(-128.0f, 128.0f, 0.0f, "Zielony"),
		new Range(-128.0f, 128.0f, 0.0f, "Niebieski")
	};

	@Override
	public void apply(PixelData original, PixelData temp) {
		int mWidth = original.getWidth(), mHeight = original.getHeight();
		if(mWidth != temp.getWidth() || mHeight != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		original.toRGB(); temp.toRGB();
		float mR,mG,mB;
		float dR = mRange[0].getValue(), dG = mRange[1].getValue(), dB = mRange[2].getValue();
		for(int i=0;i<mWidth;i++){
			for(int j=0;j<mHeight;j++){
				mR = origData[3*(i*mHeight+j)];
				mG = origData[3*(i*mHeight+j)+1];
				mB = origData[3*(i*mHeight+j)+2];
				tempData[3*(i*mHeight+j)] = Math.max(0.0f, Math.min(255.0f, mR+dR));
				tempData[3*(i*mHeight+j)+1] = Math.max(0.0f, Math.min(255.0f, mG+dG));
				tempData[3*(i*mHeight+j)+2] = Math.max(0.0f, Math.min(255.0f, mB+dB));
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
