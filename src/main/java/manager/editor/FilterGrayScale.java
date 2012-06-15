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
	public void apply(PixelData original, PixelData temp){
		int mWidth = original.getWidth(), mHeight = original.getHeight();
		if(mWidth != temp.getWidth() || mHeight != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		original.toRGB(); temp.toRGB();
		float gray=0.0f,mR,mG,mB;
		for(int i=0;i<mWidth;i++){
			for(int j=0;j<mHeight;j++){
				mR = origData[PixelData.mPixelSize*(i*mHeight+j)];
				mG = origData[PixelData.mPixelSize*(i*mHeight+j)+1];
				mB = origData[PixelData.mPixelSize*(i*mHeight+j)+2];
				switch(mType){
				case LIGHTNESS:
					gray = (Math.max(mR, Math.max(mG, mB)) + Math.min(mR, Math.min(mG, mB)))/2.0f;
					break;
				case AVERAGE:
					gray = (mR+mG+mB)/3.0f;
					break;
				case LUMINOSITY:
					gray = 0.21f*mR + 0.71f*mG + 0.07f*mB;
					break;
				default:
					gray = 0.0f;
					break;
				}
				tempData[PixelData.mPixelSize*(i*mHeight+j)] = gray;
				tempData[PixelData.mPixelSize*(i*mHeight+j)+1] = gray;
				tempData[PixelData.mPixelSize*(i*mHeight+j)+2] = gray;
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

}
