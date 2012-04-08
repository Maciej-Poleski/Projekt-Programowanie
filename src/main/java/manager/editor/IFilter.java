package manager.editor;

/**
 * Podstawowy interfejs reprezentujący Filtr, który pracuje na obrazie <b>PixelData</b>
 * Wszytskie filtry po nim dziedziczą / implementują go
 * @author Patryk
 */
public interface IFilter {
    /**
     * Aplikuje filtr na obraz <b>original</b> i wynik zapisuje w <b>temp</b>
     * Wykorzystywana podczas podglądu efektu pracy filtru na danym parametrze
     * 
     * @param original - obraz oryginalny
     * @param temp - obraz tymczasowy
     * @throws IllegalArgumentException - gdy rozmiar temp jest niezgodny z rozmiarem original
     */
    public void apply(PixelData original, PixelData temp) throws IllegalArgumentException;

    /**
     * Aplikuje filtr na obraz i wynik zapisuje w nim samym, ale zwraca kopię przed wprowadzeniem zmian
     * Wykorzystywana do ostatecznego aplikowania filtru na obraz, przechowywanie kopi umożliwia cofnięcie zmian
     * 
     * @param image - obraz na którym aplikujemy filtr
     * @return kopia obrazu przed zastosowaniem filtru lub null gdy <b>image</b> jest null
     */
    public PixelData apply(PixelData image);
}
