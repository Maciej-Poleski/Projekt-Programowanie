package manager.editor;

/**
 * Opisuje kwadratową macierz filtru, używaną jako parametr dla interfejsu IFilterMatrix
 * Macierz może być tylko i wyłącznie kwadratowa o boku nieparzystym >= 3
 * Dozwolone są macierze 3x3 5x5 itd 
 * @author Patryk
 *
 */
public class Matrix {
	private int mSize;
	private float[] mData;
	
	/**
	 * Tworzy nową macierz rozmiaru size x size i wypelnia ją zerami poza środkowym polem,
	 * któremu przypisuje 1.0f (identycznościowy filtr)
	 * @param size - rozmiar boku macierzy, dozwolone są tylko liczby nieparzyste
	 * @throws IllegalArgumentException - gdy rozmiar jets nie prawidłowy
	 */
	public Matrix(int size){
		if(size <= 0 || size%2==0){
			throw new IllegalArgumentException();
		}
		mSize = size;
		mData = new float[size*size];
		mData[size*size/2] = 1.0f;
	}
	
    /**
     * Tworzy nową macierz na podstawie dostarczonej tablicy wartości jej pól
     * @param table - tablica z wartościami pól macierzy
     * @throws IllegalArgumentException gdy rozmiar tablicy jest niedozwolony 
     * (nie opisuje mecierzy 3x3 5x5 itd)
     */
    public Matrix(float[] table){
    	if(table == null) {
    		throw new IllegalArgumentException();
    	}
    	setFromTable(table);
    }

    /**
     * @return wartości pól macierzy
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
     * Ustawia pola macierzy na wartości pobrane z tablicy
     * @param table - tablica z wartościami pól macierzy
     * @throws IllegalArgumentException gdy rozmiar macierzy jest niedozwolony
     */
    public final void setFromTable(float[] table){
    	if(table == null) {
    		throw new IllegalArgumentException();
    	}
    	int size = (int)Math.sqrt(table.length);
    	if(size*size != table.length || size < 3 || size%2==0){
    		throw new IllegalArgumentException();
    	}
    	mSize = size;
    	mData = table.clone();
    }
}
