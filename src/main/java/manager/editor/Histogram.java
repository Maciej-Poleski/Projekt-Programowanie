package manager.editor;

/**
 * Przechowuje informacje o rozkladzie danych w kanale obrazu w formie tablicy czestosci wystapien
 * @author Patryk
 */
public class Histogram {
	public static enum HistogramChannel{
		RED, GREEN, BLUE,
		CYAN, MAGENTA, YELLOW,
		HUE, SATURATION, VALUE
	}
	private final int[] mData;
	private final HistogramChannel channel;
	/**
	 * @param table - tablica wystapien zarejestrowanych
	 * @param channel - kanal dla kt�rego ten histogram trzyma informacje
	 */
	Histogram(int[] table, HistogramChannel channel){
		mData = table.clone();
		this.channel = channel;
	}
    /**
     * @return czestotliwosc wystapien danego argumentu
     */
    public int getValue(int argument) {
    	if(argument < 0 || argument >= mData.length) return 0;
        return mData[argument];
    }
    /**
     * @return dlugosc tablicy z wartosciami wyst�pie�
     */
    public int getValueTableLenght(){
    	return mData.length;
    }
    /**
     * @return typ kana�u dla kt�rego s� przechowywane informacje
     */
    public HistogramChannel getChannel(){
    	return channel;
    }
}
