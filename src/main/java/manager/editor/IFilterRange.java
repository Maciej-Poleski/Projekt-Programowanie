package manager.editor;

/**
 * Szczeg�lny rodzaj filtr�w zakresowych, jako parametr pracy u�ywa tablicy dozwolonych zakres�w
 * pracy na warto�ciach
 * @author Patryk
 */
public interface IFilterRange extends IFilter {
    /**
     * @return tablica dozwolonych zakres�w
     */
    public Range[] getRangeTable();
}
