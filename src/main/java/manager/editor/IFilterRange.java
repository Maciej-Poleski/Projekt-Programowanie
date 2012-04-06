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
    Range[] getRangeTable();

    /**
     * Ustawia tablic� dozwolonych zakres�w pracy filtru
     * @param table - nowa tablica zakres�w
     */
    void setRangeTable(Range[] table);
}
