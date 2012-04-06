package manager.editor;

/**
 * Klasa opisuj�ca przedzia� dozwolonych warto�ci oraz wybran� warto�� z tego przedzia�u
 * Uzywana jako parametr w filtrach typu IFilterRange
 * @author Patryk
 * 
 */
public class Range {
	private float mMin, mMax, mValue;
	
    /**
     * Zostanie utworzony obiekt  reprezentuj�cy przedzia� [min(a,b), max(a,b)]
     * @param a - jeden z kra�c�w przedzia�u
     * @param b - jeden z kra�c�w przedzia�u
     */
    public Range(float a, float b) {
    	mMin = Math.min(a, b);
    	mMax = Math.max(a, b);
    }
    
    /**
     * Zostanie utworzony obiekt  reprezentuj�cy przedzia� [min(a,b), max(a,b)] 
     * oraz nadana warto�� wyr�niona z tego przedzia�u
     * @param a - jeden z kra�c�w przedzia�u
     * @param b - jeden z kra�c�w przedzia�u
     * @param val - warto�� wyr�niona
     */
    public Range(float a, float b, float val){
    	this(a,b);
    	setValue(val);
    }

    /**
     * @return wyr�niona warto�� wewn�trz przedzia�u
     */
    public float getValue() {
        return mValue;
    }

    /**
     * @return pocz�tek przedzia�u
     */
    public float getMin() {
        return mMin;
    }

    /**
     * @return koniec przedzia�u
     */
    public float getMax() {
        return mMax;
    }
    
    /**
     * Ustawia warto�� wyr�nion�
     * @param value - Warto�� z przedzia�u [0,1] reprezentuj�ca wyskalowane po�o�enie 
     * warto�ci wybranej wewn�trz przedzia�u. Gdy <b>value</b> < 0 zostaje domy�lnie przypisane <b>value = 0</b>
     * gdy <b>value</b> > 1 zostaje domy�lnie przypisane <b>value = 1</b>
     */
    public void interpolate(float value){
    	if(value > 1.0f) value = 1.0f;
    	if(value < 0.0f) value = 0.0f;
    	mValue = mMin + (mMax-mMin)*value;
    }

    /**
     * Ustawia warto�� wyr�nion�
     * @param value - warto�� wyr�niona z przedzia�u, gdy value < minimum to domy�lnie value = minimum
     * gdy value > maximu  to domy�lnie value = maximum
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
