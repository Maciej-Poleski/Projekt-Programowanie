package manager.editor;

/**
 * Tablica opisująca funkcję konwersji obrazu
 * funkcja [0,1] -> [0,1] przez co jest możliwość elastycznego skalowania dziedziny i przeciwdziedziny
 * @author Patryk
 */
public class LUTTable {
	private float[] mData;
	
	/**
	 * Tworzy nowa tablice fukncji na podstawie tablicy jej wartosci, gdy tablica wejsciowa ma mniej
	 * niż 2 elementy lub jest <b>null</b> zostanie utworzona funkcja identycznosciowa
	 * 
	 * @param vals - tablica wartości funkcji
	 * @throws IllegalArgumentException - gdy w podanej tablicy znajdzą sie wartosci z poza [0,1]
	 */
	LUTTable(float[] vals){
		setConversionTable(vals);
	}
	
	/**
	 * @param arg - argumnet z dziedziny [0,1]
	 * @return interpolowana wartosc funkcji LUT na argumencie <b>arg</b>
	 */
	public final float getValue(float arg){
		if(arg <= 0.0f) {return mData[0];}
		if(arg >= 1.0f) {return mData[mData.length-1];}
		float delta = 1.0f/(mData.length-1);
		int beg = (int)(arg/delta);
		if(Math.abs(delta*beg - arg) < .00001f) return mData[beg];
		float hdet = (mData[beg+1] - mData[beg])/delta;
		return mData[beg] + hdet * (arg-beg*delta);
	}

    /**
     * Ustala nową tablicę konwersji obrazu, gdy tablica wejsciowa ma mniej
	 * niz 2 elementy lub jest <b>null</b> zostanie utworzona funkcja identycznosciowa
     * 
     * @param table - nowa tablica konwersji
     * @throws IllegalArgumentException - gdy w podanej tablicy znajdą sie wartosci z poza [0,1]
     */
    public final void setConversionTable(float[] table){
    	if(table != null) {
    		for(int i=0;i<table.length;i++){
    			if(table[i] < 0.0f || table[i] > 1.0f) {
    				throw new IllegalArgumentException();
    			}
    		}
    	}
    	if(table == null || table.length < 2){
			mData = new float[2];
			mData[0] = 0.0f;
			mData[1] = 1.0f;
		} else {mData = table.clone();}
    }
}
