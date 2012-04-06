package manager.editor;

/**
 * Szczególny rodzaj filtrów u¿ywaj¹cych jako parametr macierzy
 * @author Patryk
 */
public interface IFilterMatrix extends IFilter {
    /**
     * @return macierz przekszta³cenia
     */
    Matrix getMatrix();

    /**
     * @param matrix - nowa macierz przekszta³cenia
     * 
     * Ustawia now¹ macierz przekszta³cenia
     */
    void setMatrix(Matrix matrix);
}
