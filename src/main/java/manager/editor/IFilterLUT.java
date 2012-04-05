package manager.editor;

public interface IFilterLUT extends IFilter {
    LUTTable getConversionTable();

    void setConversionTable(LUTTable table);
}
