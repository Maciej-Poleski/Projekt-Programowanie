package manager.editor;

/**
 * Szczególny rodzaj filtrów używających jako parametr macierzy
 * @author Patryk
 */
public interface IFilterMatrix extends IFilter {
    /**
     * @return macierz przekształcenia
     */
    public Matrix getMatrix();

    /**
     * Ustawia nową macierz przekształcenia
     * @param matrix - nowa macierz przekształcenia
     */
    public void setMatrix(Matrix matrix);
}
