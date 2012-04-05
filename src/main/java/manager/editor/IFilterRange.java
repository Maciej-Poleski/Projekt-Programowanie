package manager.editor;

public interface IFilterRange extends IFilter {
    Range[] getRangeTable();

    void setRangeTable(Range[] table);
}
