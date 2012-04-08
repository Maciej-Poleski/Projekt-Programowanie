package manager.editor;

/**
 * Klasa służąca do zmiany rozmiary obrazu metodą dwukrotnego próbkowania liniowego - daje dość dobre
 * rezultaty, dokładniejsza niż najbliższe sąsiedztwo, bardziej kosztowna obliczeniowo
 * @author Patryk
 */
public class FilterImageResizeBilinear implements IFilter{
	private int mWidth, mHeight;
	/**
	 * Tworzy nowy filtr do zmiany wielkości obrazu na podany w parametrach
	 * @param width - długość obrazu wynikowego
	 * @param height - wysokość obrazu wynikowego
	 */
	FilterImageResizeBilinear(int width, int height){
		mWidth = width;
		mHeight = height;
	}
	/**
	 * zapisuje obraz original do obrazu image odpowiednio go skalując
	 */
	@Override
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null) throw new NullPointerException();
		float ratioX = (float)original.mWidth/(float)temp.mWidth;
		float ratioY = (float)original.mHeight/(float)temp.mHeight;
		original.toRGB(); temp.toRGB();
		float ip,jp,Rbeg,Rend,Gbeg,Gend,Bbeg,Bend,coef; 
		int indIbeg, indIend, indJbeg, indJend;
		for(int j=0;j<temp.mHeight;j++)
			for(int i=0;i<temp.mWidth;i++){
				ip = (float)i*ratioX;
				jp = (float)j*ratioY;
				//wspolrzene kraty próbkowania
				if((int)ip >= original.mWidth) indIbeg = original.mWidth-1; else indIbeg = (int)ip;
				if((int)jp >= original.mHeight) indJbeg = original.mHeight-1; else indJbeg = (int)jp;
				if(indIbeg + 1 >= original.mWidth) indIend = original.mWidth-1; else indIend = indIbeg+1;
				if(indJbeg + 1 >= original.mHeight) indJend = original.mHeight-1; else indJend = indJbeg+1;
				jp = Math.min(original.mHeight, jp); ip = Math.min(original.mWidth, ip);
				//probkowanie poziome
				coef = ip-(float)indIbeg;
				Rbeg = original.mData[3*(indJbeg*original.mWidth+indIbeg)] * coef + original.mData[3*(indJbeg*original.mWidth+indIend)] * (1.0f-coef);
				Gbeg = original.mData[3*(indJbeg*original.mWidth+indIbeg)+1] * coef + original.mData[3*(indJbeg*original.mWidth+indIend)+1] * (1.0f-coef);
				Bbeg = original.mData[3*(indJbeg*original.mWidth+indIbeg)+2] * coef + original.mData[3*(indJbeg*original.mWidth+indIend)+2] * (1.0f-coef);
				Rend = original.mData[3*(indJend*original.mWidth+indIbeg)] * coef + original.mData[3*(indJend*original.mWidth+indIend)] * (1.0f-coef);
				Gend = original.mData[3*(indJend*original.mWidth+indIbeg)+1] * coef + original.mData[3*(indJend*original.mWidth+indIend)+1] * (1.0f-coef);
				Bend = original.mData[3*(indJend*original.mWidth+indIbeg)+2] * coef + original.mData[3*(indJend*original.mWidth+indIend)+2] * (1.0f-coef);
				//probkowanie pionowe
				coef = jp-(float)indJbeg;
				temp.mData[3*(j*temp.mWidth+i)] = Rbeg * coef + Rend * (1.0f-coef);
				temp.mData[3*(j*temp.mWidth+i)+1] = Gbeg * coef + Gend * (1.0f-coef);
				temp.mData[3*(j*temp.mWidth+i)+2] = Bbeg * coef + Bend * (1.0f-coef);
			}
	}
	/**
	 * Odwrotnie niż w przypadku zwykłych filtrów zwraca obraz zmodyfikowany, 
	 * zaś image zostawia w spokoju
	 * @return Obraz po zmianie rozmiaru
	 */
	@Override
	public PixelData apply(PixelData image) {
		if(image == null) return null;
		PixelData ret = new PixelData(mWidth, mHeight);
		apply(image, ret);
		return ret;
	}
}
