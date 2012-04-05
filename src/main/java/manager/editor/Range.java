package manager.editor;

public class Range {
	private float mMin, mMax, mValue;
    public Range(float a, float b) {
    	mMin = Math.min(a, b);
    	mMax = Math.max(a, b);
    }

    public float getValue() {
        return mValue;
    }

    public float getMin() {
        return mMin;
    }

    public float getMax() {
        return mMax;
    }
    
    public void interpolate(float value){
    	if(value > 1.0f) value = 1.0f;
    	if(value < 0.0f) value = 0.0f;
    	mValue = mMin + (mMax-mMin)*value;
    }

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
