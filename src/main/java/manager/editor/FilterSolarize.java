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
			new Range(0.0f, 255.0f, 127.5f)
	};
	
	@Override
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null) throw new NullPointerException();
		if(original.mWidth != temp.mWidth || original.mHeight != temp.mHeight) 
			throw new IllegalArgumentException();
		float mLUT[] = new float[256];
		float prog = mRange[0].getValue();
		for(int i=0;i<256;i++) 
			if(i < prog) mLUT[i] = (float)i;
			else mLUT[i] = (float)(255-i);
		original.toRGB(); temp.toRGB();
		for(int i=0;i<original.mData.length;i++)
			temp.mData[i] = mLUT[(int)original.mData[i]];
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
