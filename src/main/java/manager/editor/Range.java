package manager.editor;

/**
 * Klasa opisuj¹ca przedzia³ dozwolonych wartoœci oraz wybran¹ wartoœæ z tego przedzia³u
 * Uzywana jako parametr w filtrach typu IFilterRange
 * @author Patryk
 * 
 */
public class Range {
	private float mMin, mMax, mValue;
	
    /**
     * Zostanie utworzony obiekt  reprezentuj¹cy przedzia³ [min(a,b), max(a,b)]
     * @param a - jeden z krañców przedzia³u
     * @param b - jeden z krañców przedzia³u
     */
    public Range(float a, float b) {
    	mMin = Math.min(a, b);
    	mMax = Math.max(a, b);
    }
    
    /**
     * Zostanie utworzony obiekt  reprezentuj¹cy przedzia³ [min(a,b), max(a,b)] 
     * oraz nadana wartoœæ wyró¿niona z tego przedzia³u
     * @param a - jeden z krañców przedzia³u
     * @param b - jeden z krañców przedzia³u
     * @param val - wartoœæ wyró¿niona
     */
    public Range(float a, float b, float val){
    	this(a,b);
    	setValue(val);
    }

    /**
     * @return wyró¿niona wartoœæ wewn¹trz przedzia³u
     */
    public float getValue() {
        return mValue;
    }

    /**
     * @return pocz¹tek przedzia³u
     */
    public float getMin() {
        return mMin;
    }

    /**
     * @return koniec przedzia³u
     */
    public float getMax() {
        return mMax;
    }
    
    /**
     * Ustawia wartoœæ wyró¿nion¹
     * @param value - Wartoœæ z przedzia³u [0,1] reprezentuj¹ca wyskalowane po³o¿enie 
     * wartoœci wybranej wewn¹trz przedzia³u. Gdy <b>value</b> < 0 zostaje domyœlnie przypisane <b>value = 0</b>
     * gdy <b>value</b> > 1 zostaje domyœlnie przypisane <b>value = 1</b>
     */
    public void interpolate(float value){
    	if(value > 1.0f) value = 1.0f;
    	if(value < 0.0f) value = 0.0f;
    	mValue = mMin + (mMax-mMin)*value;
    }

    /**
     * Ustawia wartoœæ wyró¿nion¹
     * @param value - wartoœæ wyró¿niona z przedzia³u, gdy value < minimum to domyœlnie value = minimum
     * gdy value > maximu  to domyœlnie value = maximum
     */
    public void setValue(float value) {
    	if(value < mMin) mValue = mMin;
    	else if(value > mMax) mValue = mMax;
    	else mValue = value;
    }

    @Override
    public String toString() {
        return mMin + ";" + mMax;
    }
}
