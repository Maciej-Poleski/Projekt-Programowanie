package manager.editor;

/**
 * Filtr aplikujacy przekształcenie macierzowe na obrazie
 * @author Patryk
 */
public class FilterMatrixAdapter implements IFilterMatrix{
	private Matrix mMatrix;
	private final Matrix mDefault;
	
	FilterMatrixAdapter(Matrix mat){
		mMatrix = mat;
		mDefault = new Matrix(mMatrix.getTable());
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
		int matSize = mMatrix.getSize();
		int center = matSize/2, ip, jp;
		float[] matTable = mMatrix.getTable();
		float sum = 0.0f, mR,mG,mB; 
		for(int i=0;i<matTable.length;i++) {
			sum += matTable[i];
		}
		for(int j=0;j<mHeight;j++){
			for(int i=0;i<mWidth;i++){
				mR=0.0f; mG=0.0f; mB = 0.0f;
				for(int mj=0;mj<matSize;mj++){
					jp = j+mj-center;
					for(int mi=0;mi<matSize;mi++){
						ip = i+mi-center;
						if(ip >= 0 && ip < mWidth && jp >= 0 && jp < mHeight){
							mR += origData[PixelData.PIXEL_SIZE*(jp*mWidth+ip)] * matTable[mj*matSize+mi];
							mG += origData[PixelData.PIXEL_SIZE*(jp*mWidth+ip)+1] * matTable[mj*matSize+mi];
							mB += origData[PixelData.PIXEL_SIZE*(jp*mWidth+ip)+2] * matTable[mj*matSize+mi];
						}
					}
				}
				if(sum != 0.0f){
					mR /= sum;
					mG /= sum;
					mB /= sum;
				}
				tempData[PixelData.PIXEL_SIZE*(j*mWidth+i)] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, mR));
				tempData[PixelData.PIXEL_SIZE*(j*mWidth+i)+1] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, mG));
				tempData[PixelData.PIXEL_SIZE*(j*mWidth+i)+2] = Math.max(0.0f, Math.min(ColorConverter.RGBCMY_BYTE_MAX, mB));
			}
		}
	}

	@Override
	public PixelData apply(PixelData image) {
		if(image == null) {return null;}
		PixelData ret = (PixelData)image.clone();
		apply(ret, image);
		return ret;
	}

	@Override
	public Matrix getMatrix() {
		return mMatrix;
	}

	@Override
	public void setMatrix(Matrix matrix) {
		mMatrix = matrix;
	}
	
	@Override
	public void reset() {
		mMatrix = new Matrix(mDefault.getTable());
	}

}
