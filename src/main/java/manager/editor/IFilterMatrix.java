package manager.editor;

/**
 * Szczególny rodzaj filtrów używających jako parametr macierzy
 * @author Patryk
 */
public interface IFilterMatrix extends IFilter {
    /**
     * @return macierz przekształcenia
     */
    Matrix getMatrix();

    /**
     * Ustawia nową macierz przekształcenia
     * @param matrix - nowa macierz przekształcenia
     */
    void setMatrix(Matrix matrix);
}
