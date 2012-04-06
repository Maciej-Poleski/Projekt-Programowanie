package manager.editor;

/**
 * Szczególny rodzaj filtrów, które jako parametr pracy wykorzystuj¹ tablice funkcji LUT
 * @author Patryk
 */
public interface IFilterLUT extends IFilter {
    /**
     * @return tablica funkcji LUT
     */
    public LUTTable getConversionTable();

    /**
     * Ustawia funkcjê LUT która bêdzie parametrem pracy filtru
     * @param table - nowa tablica
     */
    public void setConversionTable(LUTTable table);
}