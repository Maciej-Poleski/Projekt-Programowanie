package manager.editor;

import java.awt.image.BufferedImage;

public class PixelData implements Cloneable {
    public PixelData(BufferedImage image) {
    }

    public void toRGB() {
    }

    public void toHSV() {
    }

    public void toCMY() {
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Histogram getHistogram(int k) {
        return null;
    }
}
