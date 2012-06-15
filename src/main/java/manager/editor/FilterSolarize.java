package manager.editor;

/**
 * Filtr odpowiedzialny za solaryzację obrazu filtracja odbywa się wg wzoru
 * - dla wartości pixeli poniżej progu bez zmian
 * - dal wartości pixeli powyżej progu negatyw
 * Szczególne przypadki:
 * - próg = 0 -> negatyw
 * - próg = 255 -> obraz bez zmian
 * - przy wartośći progu różnej od 0,127.5,255 funkcja transformująca jest nieciągła (duże skoki na progu)
 * @author Patryk
 */
public class FilterSolarize implements IFilterRange{
	private final Range[] mRange = new Range[]{
			new Range(0.0f, ColorConverter.mRGBCMYByteMax, ColorConverter.mRGBCMYByteMax/2.0f, "Próg")
	};
	
	@Override
	public void apply(PixelData original, PixelData temp) {
		if(original.getWidth() != temp.getWidth() || original.getHeight() != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		float mLUT[] = new float[PixelData.mRGBCMYChannelPrecision];
		float prog = mRange[0].getValue();
		for(int i=0;i<PixelData.mRGBCMYChannelPrecision;i++) {
			if(i < prog) {mLUT[i] = (float)i;}
			else {mLUT[i] = ColorConverter.mRGBCMYByteMax-(float)i;}
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
