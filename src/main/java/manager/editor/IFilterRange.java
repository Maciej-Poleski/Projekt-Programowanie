package manager.editor;

/**
 * Szczególny rodzaj filtrów zakresowych, jako parametr pracy u¿ywa tablicy dozwolonych zakresów
 * pracy na wartoœciach
 * @author Patryk
 */
public interface IFilterRange extends IFilter {
    /**
     * @return tablica dozwolonych zakresów
     */
    public Range[] getRangeTable();
}
