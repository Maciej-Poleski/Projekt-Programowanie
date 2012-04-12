package manager.editor;

/**
 * Szczególny rodzaj filtrów zakresowych, jako parametr pracy używa tablicy dozwolonych zakresów
 * pracy na wartościach
 * @author Patryk
 */
public interface IFilterRange extends IFilter {
    /**
     * @return tablica dozwolonych zakresów
     */
    Range[] getRangeTable();
}
