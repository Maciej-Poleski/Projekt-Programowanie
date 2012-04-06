package manager.editor;

/**
 * Szczeg�lny rodzaj filtr�w, kt�re jako parametr pracy wykorzystuj� tablice funkcji LUT
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
     * Ustawia funkcj� LUT kt�ra b�dzie parametrem pracy filtru
     */
    void setConversionTable(LUTTable table);
}