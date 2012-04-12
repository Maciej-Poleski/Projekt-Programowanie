package manager.editor;

/**
 * Filtr umożliwiający zmianę jasności obrazu - filtr liniowy
 * @author Patryk
 */
public class FilterBrightness implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(-128.0f, 128.0f, 0.0f, "Jasność")	
	};
	@Override
	public void apply(PixelData original, PixelData temp) {
		if(original.getWidth() != temp.getWidth() || original.getHeight() != temp.getHeight()){ 
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		float delta = mRange[0].getValue();
		original.toRGB(); temp.toRGB();
		for(int i=0;i<origData.length;i++){
			tempData[i] = Math.max(0.0f, Math.min(255.0f, origData[i] + delta));
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
