package manager.editor;

/**
 * Filtr tworz¹cy negatyw obrazu
 * @author Patryk
 */
public class FilterNegative implements IFilter {
	@Override
	public void apply(PixelData original, PixelData temp) throws IllegalArgumentException {
		if(original == null || temp == null ||original.mWidth != temp.mWidth || original.mHeight != temp.mHeight) 
			throw new IllegalArgumentException();
		original.toRGB();
		for(int i=0;i<original.mData.length;i++) temp.mData[i] = 255.0f - original.mData[i];
	}
	
	@Override
	public PixelData apply(PixelData image) {
		if(image == null) return null;
		PixelData ret = null;
		try {ret = (PixelData)image.clone();}catch(CloneNotSupportedException e) {e.printStackTrace();}
		image.toRGB();
		apply(image, image);
		return ret;
	}
}
