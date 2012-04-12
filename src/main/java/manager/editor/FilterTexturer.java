package manager.editor;

/**
 * Filtr aplikujący do obrazu teksture wygenerowaną przy pomocy <b>ITextureGenerator</b>
 * @author Patryk
 */
public class FilterTexturer implements IFilter{
	public enum TexturingMode{
		ADD, SUBSTRACT, MULTIPLY, MASK
	}
	private TexturingMode mMode = TexturingMode.ADD;
	private final ITextureGenerator mGenerator;
	
	/**
	 * Tworzy nowy obiekt teksturujący
	 * @param generator - Generator tekstur, z którego filtr korzysta
	 * @throws NullPointerException - gdy argument jest nullem - klasa musi mieć jakikolwiek generator,
	 * aby pracować
	 */
	FilterTexturer(ITextureGenerator generator){
		if(generator == null) throw new NullPointerException();
		mGenerator = generator;
	}
	/**
	 * Tworzy nowy obiekt teksturujący
	 * @param generator - Generator tekstur, z którego filtr korzytsa
	 * @param mode - Tryb pracy filtru (domyślnie <b>ADD</b>)
	 */
	FilterTexturer(ITextureGenerator generator, TexturingMode mode){
		this(generator);
		mMode = mode;
	}
	
	/**
	 * @return aktualny tryb pracy teksturatora
	 */
	public TexturingMode getMode(){
		return mMode;
	}
	/**
	 * @return generator pracujacy z teksturatorem
	 */
	public ITextureGenerator getGenerator(){
		return mGenerator;
	}
	/**
	 * Ustawia nowy tryb pracy teksturatora
	 * @param mode - nowy tryb pracy
	 */
	public void setMode(TexturingMode mode){
		mMode = mode;
	}
	
	@Override
	public void apply(PixelData original, PixelData temp) {
		int mWidth = original.getWidth(), mHeight = original.getHeight();
		if(mWidth != temp.getWidth() || mHeight != temp.getHeight()){
			throw new IllegalArgumentException();
		}
		float[] origData = original.getData();
		float[] tempData = temp.getData();
		float gray; ColorRGB mCol = new ColorRGB(0,0,0);
		original.toRGB(); temp.toRGB();
		for(int i=0;i<mWidth;i++){
			for(int j=0;j<mHeight;j++){
				mGenerator.getValue((float)i/(float)mWidth, (float)j/(float)mHeight, mCol);
				switch(mMode){
				case ADD:
					tempData[3*(j*mWidth+i)] = Math.min(255.0f, origData[3*(j*mWidth+i)] + mCol.getR()*255.0f);
					tempData[3*(j*mWidth+i)+1] = Math.min(255.0f, origData[3*(j*mWidth+i)+1] + mCol.getG()*255.0f);
					tempData[3*(j*mWidth+i)+2] = Math.min(255.0f, origData[3*(j*mWidth+i)+2] + mCol.getB()*255.0f);
					break;
				case SUBSTRACT:
					tempData[3*(j*mWidth+i)] = Math.max(0.0f, origData[3*(j*mWidth+i)] - mCol.getR()*255.0f);
					tempData[3*(j*mWidth+i)+1] = Math.max(0.0f, origData[3*(j*mWidth+i)+1] - mCol.getG()*255.0f);
					tempData[3*(j*mWidth+i)+2] = Math.max(0.0f, origData[3*(j*mWidth+i)+2] - mCol.getB()*255.0f);
					break;
				case MULTIPLY:
					tempData[3*(j*mWidth+i)] = origData[3*(j*mWidth+i)] * mCol.getR();
					tempData[3*(j*mWidth+i)+1] = origData[3*(j*mWidth+i)+1] * mCol.getG();
					tempData[3*(j*mWidth+i)+2] = origData[3*(j*mWidth+i)+2] * mCol.getB();
					break;
				case MASK:
					gray = 0.21f*origData[3*(j*mWidth+i)] + 0.71f*origData[3*(j*mWidth+i)+1] + 0.07f*origData[3*(j*mWidth+i)+2];
					tempData[3*(j*mWidth+i)] = gray * mCol.getR();
					tempData[3*(j*mWidth+i)+1] = gray * mCol.getG();
					tempData[3*(j*mWidth+i)+2] = gray * mCol.getB();
					break;
				}
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
