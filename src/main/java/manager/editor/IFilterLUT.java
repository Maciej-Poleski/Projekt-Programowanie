package manager.editor;

/**
 * Szczeg�lny rodzaj filtr�w, kt�re jako parametr pracy wykorzystuj� tablice funkcji LUT
 * @author Patryk
 */
public interface IFilterLUT extends IFilter {
    /**
     * @return tablica funkcji LUT
     */
    public LUTTable getConversionTable();

    /**
     * Ustawia funkcj� LUT kt�ra b�dzie parametrem pracy filtru
     * @param table - nowa tablica
     */
    public void setConversionTable(LUTTable table);
}