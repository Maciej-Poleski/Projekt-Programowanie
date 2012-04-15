package manager.editor;

/**
 * Filtr tworzący negatyw obrazu
 * @author Patryk
 */
public class FilterNegative implements IFilter {
	@Override
	public void apply(PixelData original, PixelData temp){
		if(original.getWidth() != temp.getWidth() || original.getHeight() != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		original.toRGB(); temp.toRGB();
		for(int i=0;i<origData.length;i++) {
			tempData[i] = ColorConverter.RGBCMY_BYTE_MAX - origData[i];
		}
	}
	
	@Override
	public PixelData apply(PixelData image) {
		if(image == null) {return null;}
		PixelData ret = (PixelData)image.clone();
		apply(image, image);
		return ret;
	}
}
