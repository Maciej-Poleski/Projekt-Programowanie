package manager.editor;

/**
 * Szczególny rodzaj filtrów zakresowych, jako parametr pracy używa tablicy dozwolonych zakresów
 * pracy na wartościach
 * @author Patryk
 */
public interface IFilterRange extends IFilter {
    /**
     * Zwraca tablicę dozwolonych zakresów
     * @return tablica dozwolonych zakresów
     */
    Range[] getRangeTable();
    
    /**
     * Ustawia tablice zakresów, jeśli zakresy są niedozwolone (np różnią się krańcami lub
     * jest ich niedozwolona dla danego filtru ilość zostanie rzucony wyjątek)
     * @param table - nowa tablica zakresów z ustawionymi wartościami
     * @throws IllegalArgumentException
     */
    void setRangeTable(Range[] table);
}
