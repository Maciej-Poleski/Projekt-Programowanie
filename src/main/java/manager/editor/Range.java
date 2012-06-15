package manager.editor;

/**
 * Klasa opisująca przedział dozwolonych wartości oraz wybraną wartość z tego przedziału
 * Używana jako parametr w filtrach typu IFilterRange
 * @author Patryk
 * 
 */
public class Range {
	private float mMin, mMax, mValue;
	private final String mName;
	
    /**
     * Zostanie utworzony obiekt  reprezentujący przedział [min(a,b), max(a,b)]
     * @param a - jeden z krańców przedziału
     * @param b - jeden z krańców przedziału
     * @param name - nazwa przedziału
     */
    public Range(float a, float b, String name) {
    	mMin = Math.min(a, b);
    	mMax = Math.max(a, b);
    	mValue = (a+b)/2.0f;
    	mName = name;
    }
    
    /**
     * Zostanie utworzony obiekt  reprezentujący przedział [min(a,b), max(a,b)] 
     * oraz nadana wartość wyróniona z tego przedziału
     * @param a - jeden z krańców przedziału
     * @param b - jeden z krańców przedziału
     * @param val - wartość wyróżniona
     * @param name - nazwa przedziału
     */
    public Range(float a, float b, float val, String name){
    	this(a,b,name);
    	setValue(val);
    }

    /**
     * @return wyróżniona wartość wewnątrz przedziału
     */
    public float getValue() {
        return mValue;
    }
    
    /**
     * @return interpolowana pozycja wartości wyróżnionej w przedziale -> należy do [0,1]
     */
    public float getInterpolatedValue(){
    	if(mMin == mMax) {return 0.0f;}
    	return (mValue - mMin) / (mMax - mMin);
    }

    /**
     * @return początek przedziału
     */
    public float getMin() {
        return mMin;
    }

    /**
     * @return koniec przedziału
     */
    public float getMax() {
        return mMax;
    }
    
    /**
     * @return nazwa przedziału
     */
    public String getName(){
    	return mName;
    }
    
    /**
     * Ustawia wartość wyróżnioną
     * @param value - Wartość z przedziału [0,1] reprezentująca wyskalowane położenie 
     * wartości wybranej wewnątrz przedziału. Gdy <b>value</b> < 0 zostaje domyślnie przypisane <b>value = 0</b>
     * gdy <b>value</b> > 1 zostaje domyślnie przypisane <b>value = 1</b>
     */
    public final void interpolate(float value){
    	if(value > 1.0f) {value = 1.0f;}
    	if(value < 0.0f) {value = 0.0f;}
    	mValue = mMin + (mMax-mMin)*value;
    }

    /**
     * Ustawia wartość wyróżnioną
     * @param value - wartość wyróżniona z przedziału, gdy value < minimum to domyślnie value = minimum
     * gdy value > maximum  to domyślnie value = maximum
     */
    public final void setValue(float value) {
    	if(value < mMin) {mValue = mMin;}
    	else if(value > mMax) {mValue = mMax;}
    	else {mValue = value;}
    }

    @Override
    public String toString() {
        return mMin + ";" + mMax;
    }
}
