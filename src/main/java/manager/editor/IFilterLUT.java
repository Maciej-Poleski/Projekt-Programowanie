package manager.editor;

/**
 * Szczególny rodzaj filtrów, które jako parametr pracy wykorzystują tablice funkcji LUT
 * @author Patryk
 */
public interface IFilterLUT extends IFilter {
    /**
     * @return tablica funkcji LUT
     */
    public LUTTable getConversionTable();

    /**
     * Ustawia funkcję LUT która będzie parametrem pracy filtru
     * @param table - nowa tablica
     */
    public void setConversionTable(LUTTable table);
}