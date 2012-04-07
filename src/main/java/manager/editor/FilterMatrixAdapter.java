package manager.editor;

/**
 * Filtr aplikujacy przekształcenie macierzowe na obrazie
 * @author Patryk
 */
public class FilterMatrixAdapter implements IFilterMatrix{
	private Matrix mMatrix;
	
	FilterMatrixAdapter(Matrix mat){
		mMatrix = mat;
	}

	@Override
	public void apply(PixelData original, PixelData temp)
			throws IllegalArgumentException {
		if(original == null || temp == null) throw new NullPointerException();
		if(original.mWidth != temp.mWidth || original.mHeight != temp.mHeight) 
			throw new IllegalArgumentException();
		original.toRGB(); temp.toRGB();
		int matSize = mMatrix.getSize();
		int center = matSize/2, ip, jp;
		float[] matTable = mMatrix.getTable();
		float sum = 0.0f, R,G,B; 
		for(int i=0;i<matTable.length;i++) sum += matTable[i];
		for(int j=0;j<original.mHeight;j++)
			for(int i=0;i<original.mWidth;i++){
				R = G = B = 0.0f;
				for(int mj=0;mj<matSize;mj++){
					jp = j+mj-center;
					for(int mi=0;mi<matSize;mi++){
						ip = i+mi-center;
						if(ip >= 0 && ip < original.mWidth && jp >= 0 && jp < original.mHeight){
							R += original.mData[3*(jp*original.mWidth+ip)] * matTable[mj*matSize+mi];
							G += original.mData[3*(jp*original.mWidth+ip)+1] * matTable[mj*matSize+mi];
							B += original.mData[3*(jp*original.mWidth+ip)+2] * matTable[mj*matSize+mi];
						}
					}
				}
				if(sum != 0.0f){
					R /= sum;
					G /= sum;
					B /= sum;
				}
				temp.mData[3*(j*original.mWidth+i)] = Math.max(0.0f, Math.min(255.0f, R));
				temp.mData[3*(j*original.mWidth+i)+1] = Math.max(0.0f, Math.min(255.0f, G));
				temp.mData[3*(j*original.mWidth+i)+2] = Math.max(0.0f, Math.min(255.0f, B));
			}
	}

	@Override
	public PixelData apply(PixelData image) {
		if(image == null) return null;
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

}
