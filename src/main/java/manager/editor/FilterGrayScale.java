package manager.editor;

/**
 * Filtr tworzący zdjęcia w skali szarości w 3 wariantach:
 * - średniej absolutnej
 * - średniej jasności
 * - luminestencji barwy
 * @author Patryk
 */
public class FilterGrayScale implements IFilter{
	public enum FilterGrayScaleTypes{
		LIGHTNESS, AVERAGE, LUMINOSITY
	}
	private FilterGrayScaleTypes mType;
	FilterGrayScale(FilterGrayScaleTypes type){
		mType = type;
	}
	@Override
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null) throw new NullPointerException();
		if(original.mWidth != temp.mWidth || original.mHeight != temp.mHeight) 
			throw new IllegalArgumentException();
		original.toRGB(); temp.toRGB();
		float gray=0.0f,R,G,B;
		for(int i=0;i<original.mWidth;i++)
			for(int j=0;j<original.mHeight;j++){
				R = original.mData[3*(i*original.mHeight+j)];
				G = original.mData[3*(i*original.mHeight+j)+1];
				B = original.mData[3*(i*original.mHeight+j)+2];
				switch(mType){
				case LIGHTNESS:
					gray = (Math.max(R, Math.max(G, B)) + Math.min(R, Math.min(G, B)))/2.0f;
					break;
				case AVERAGE:
					gray = (R+G+B)/3.0f;
					break;
				case LUMINOSITY:
					gray = 0.21f*R + 0.71f*G + 0.07f*B;
					break;
				}
				temp.mData[3*(i*original.mHeight+j)] = gray;
				temp.mData[3*(i*original.mHeight+j)+1] = gray;
				temp.mData[3*(i*original.mHeight+j)+2] = gray;
			}
	}

	@Override
	public PixelData apply(PixelData image) {
		if(image == null) return null;
		PixelData ret = (PixelData)image.clone();
		apply(image, image);
		return ret;
	}

}
