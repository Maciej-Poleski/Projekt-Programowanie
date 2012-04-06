package manager.editor;

/**
 * Opisuje kwadratow� macierz filtru, u�ywana jako parametr dla interfejsu IFilterMatrix
 * Macierz mo�e by� tylko i wy��cznie kwadratowa o boku nieparzystym >= 3
 * Dozwolone s� macierze 3x3 5x5 itd 
 * @author Patryk
 *
 */
public class Matrix {
	private int mSize;
	private float[] mData;
	
	/**
	 * Tworzy now� macierz rozmiaru size x size i wypelnia j� zerami poza �rodkowym polem,
	 * kt�remu przypisuje 1.0f (identyczno�ciowy filtr)
	 * @param size - rozmiar boku macierzy, dozwolone s� tylko liczby nieparzyste
	 * @throws IllegalArgumentException - gdy rozmiar jets nie prawid�owy
	 */
	public Matrix(int size) throws IllegalArgumentException{
		if(size <= 0 || size%2==0) throw new IllegalArgumentException();
		mSize = size;
		mData = new float[size*size];
		mData[size*size/2] = 1.0f;
	}
	
    /**
     * Tworzy now� macierz na podstawie dostarczonej tablicy warto�ci jej p�l
     * @param table - tablica z warto�ciami p�l macierzy
     * @throws IllegalArgumentException gdy rozmiar tablicy jest niedozwolony 
     * (nie opisuje mecierzy 3x3 5x5 itd)
     */
    public Matrix(float[] table) throws IllegalArgumentException {
    	setFromTable(table);
    }

    /**
     * @return warto�ci p�l macierzy
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
     * Ustawia pola macierzy na warto�ci pobrane z tablicy
     * @param table - tablica z warto�ciami p�l macierzy
     * @throws IllegalArgumentException gdy rozmiar macierzy jest niedozwolony
     */
    public void setFromTable(float[] table) throws IllegalArgumentException {
    	int size = (int)Math.sqrt(table.length);
    	if(size*size != table.length || size < 3 || size%2==0) throw new IllegalArgumentException();
    	mSize = size;
    	mData = table.clone();
    }
}
