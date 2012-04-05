package manager.editor;

public interface IFilter {
    void apply(PixelData original, PixelData temp);

    PixelData apply(PixelData image);
}
