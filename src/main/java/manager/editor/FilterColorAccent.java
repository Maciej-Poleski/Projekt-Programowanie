package manager.editor;

/**
 * Filtr odpowiedzialny za tworzenie akcentu kolorystycznego
 * filtr dwu-argumentowy:
 * arg1 - barwa wyróżniona
 * arg2 - tolerancja
 * Filtr pozostawia kolory mieszczące sie w przedziale tolerancji bez zmian, zaś pozostałe zmienia
 * w odcienie szarości metodą luminestencji
 * @author Patryk
 */
public class FilterColorAccent implements IFilterRange{
	private final Range[] mRange = new Range[]{
		new Range(0.0f, 359.9f, 0.0f),
		new Range(0.0f, 180.0f, 10.0f)
	};
	
	@Override
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null) throw new NullPointerException();
		if(original.mWidth != temp.mWidth || original.mHeight != temp.mHeight) 
			throw new IllegalArgumentException();
		original.toHSV(); temp.toHSV();
		float Hue = mRange[0].getValue(), Tol = mRange[1].getValue();
		float Hmax = Hue+Tol, Hmin = Hue-Tol;
		float H=0,S=0,V=0;
		for(int i=0;i<original.mWidth;i++)
			for(int j=0;j<original.mHeight;j++){
				H = original.mData[3*(j*original.mWidth+i)];
				S = original.mData[3*(j*original.mWidth+i)+1];
				V = original.mData[3*(j*original.mWidth+i)+2];
				if((Hmin <= H && H <= Hmax) || (Hmin <= H-360.0f && H-360.0f <= Hmax) || (Hmin <= H+360.0f && H+360.0f <= Hmax))
					temp.mData[3*(j*original.mWidth+i)+1] = S;
				else temp.mData[3*(j*original.mWidth+i)+1] = 0.0f;
				temp.mData[3*(j*original.mWidth+i)] = H;
				temp.mData[3*(j*original.mWidth+i)+2] = V;
			}
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
