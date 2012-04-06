package manager.editor;

/**
 * Opisuje kwadratow¹ macierz filtru, u¿ywana jako parametr dla interfejsu IFilterMatrix
 * Macierz mo¿e byæ tylko i wy³¹cznie kwadratowa o boku nieparzystym >= 3
 * Dozwolone s¹ macierze 3x3 5x5 itd 
 * @author Patryk
 *
 */
public class Matrix {
	private int mSize;
	private float[] mData;
	
	/**
	 * Tworzy now¹ macierz rozmiaru size x size i wypelnia j¹ zerami poza œrodkowym polem,
	 * któremu przypisuje 1.0f (identycznoœciowy filtr)
	 * @param size - rozmiar boku macierzy, dozwolone s¹ tylko liczby nieparzyste
	 * @throws IllegalArgumentException - gdy rozmiar jets nie prawid³owy
	 */
	public Matrix(int size) throws IllegalArgumentException{
		if(size <= 0 || size%2==0) throw new IllegalArgumentException();
		mSize = size;
		mData = new float[size*size];
		mData[size*size/2] = 1.0f;
	}
	
    /**
     * Tworzy now¹ macierz na podstawie dostarczonej tablicy wartoœci jej pól
     * @param table - tablica z wartoœciami pól macierzy
     * @throws IllegalArgumentException gdy rozmiar tablicy jest niedozwolony 
     * (nie opisuje mecierzy 3x3 5x5 itd)
     */
    public Matrix(float[] table) throws IllegalArgumentException {
    	setFromTable(table);
    }

    /**
     * @return wartoœci pól macierzy
     */
    public float[] getTable() {
        return mData;
    }

    /**
     * @return rozmiar macierzy
     */
    public int getSize() {
        return mSize;
    }

    /**
     * Ustawia pola macierzy na wartoœci pobrane z tablicy
     * @param table - tablica z wartoœciami pól macierzy
     * @throws IllegalArgumentException gdy rozmiar macierzy jest niedozwolony
     */
    public void setFromTable(float[] table) throws IllegalArgumentException {
    	int size = (int)Math.sqrt(table.length);
    	if(size*size != table.length || size < 3 || size%2==0) throw new IllegalArgumentException();
    	mSize = size;
    	mData = table.clone();
    }
}
