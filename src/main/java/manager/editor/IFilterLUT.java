package manager.editor;

/**
 * Szczególny rodzaj filtrów, które jako parametr pracy wykorzystują tablice funkcji LUT
 * @author Patryk
 */
public interface IFilterLUT extends IFilter {
    /**
     * Zwraca tablice funckji LUT (filtr może pracować wielokanałowo)
     * @return tablica funkcji LUT
     */
    LUTTable[] getConversionTable();

    /**
     * Ustawia tablicę funkcji LUT które będą parametrami pracy filtru.
     * Gdy rozmiar tablicy podanej jako argument się nie zgadza lub któryś jej element jest nullem
     * wyrzuca wyjątek
     * @param table - nowa tablica
     * @throws IllegalArgumentException - gdy tablica jest nieodpowiednia
     */
    void setConversionTable(LUTTable[] table);
}