package manager.editor;

/**
 * Przechowuje informacje o rozkładzie danych w kanale obrazu w formie tablicy częstości wystąpień
 * @author Patryk
 */
public class Histogram {
	static enum HistogramChannel{
		RED, GREEN, BLUE,
		CYAN, MAGENTA, YELLOW,
		HUE, SATURATION, VALUE
	}
	private final int[] mData;
	private final HistogramChannel channel;
	/**
	 * @param table - tablica wystapien zarejestrowanych
	 * @param channel - kanal dla którego ten histogram trzyma informacje
	 */
	Histogram(int[] table, HistogramChannel channel){
		mData = table.clone();
		this.channel = channel;
	}
    /**
     * @return częstotliwość wystąpień danego argumentu
     */
    public int getValue(int argument) {
    	if(argument < 0 || argument >= mData.length) {return 0;}
        return mData[argument];
    }
    /**
     * @return długość tablicy z wartościami wystąpień
     */
    public int getValueTableLenght(){
    	return mData.length;
    }
    /**
     * @return typ kanału dla którego są przechowywane informacje
     */
    public HistogramChannel getChannel(){
    	return channel;
    }
}
