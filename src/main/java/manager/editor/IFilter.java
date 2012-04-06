package manager.editor;

/**
 * Podstawowy interfejs reprezentuj�cy Filtr, kt�ry pracuje na obrazie <b>PixelData</b>
 * Wszytskie filtry po nim dziedzicz� / implementuj� go
 * @author Patryk
 */
public interface IFilter {
    /**
     * @param original - obraz oryginalny
     * @param temp - obraz tymczasowy
     * 
     * Aplikuje filtr na obraz <b>original</b> i wynik zapisuje w <b>temp</b>
     * Wykorzystywana podczas podgl�du efektu pracy filtru na danym parametrze
     */
    void apply(PixelData original, PixelData temp);

    /**
     * @param image - obraz na kt�rym aplikujemy filtr
     * @return kopia obrazu przed zastosowaniem filtru
     * 
     * Aplikuje filtr na obraz i wynik zapisuje w nim samym, ale zwraca kopi� przed wprowadzeniem zmian
     * Wykorzystywana do ostatecznego aplikowania filtru na obraz, przechowywanie kopi umo�liwia cofni�cie zmian
     */
    PixelData apply(PixelData image);
}
