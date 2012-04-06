package manager.editor;

/**
 * Szczególny rodzaj filtrów, które jako parametr pracy wykorzystuj¹ tablice funkcji LUT
 * @author Patryk
 */
public interface IFilterLUT extends IFilter {
    /**
     * @return tablica funkcji LUT
     */
    LUTTable getConversionTable();

    /**
     * @param table - nowa tablica
     * 
     * Ustawia funkcjê LUT która bêdzie parametrem pracy filtru
     */
    void setConversionTable(LUTTable table);
}