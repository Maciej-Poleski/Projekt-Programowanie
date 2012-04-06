package manager.editor;

/**
 * Szczególny rodzaj filtrów u¿ywaj¹cych jako parametr macierzy
 * @author Patryk
 */
public interface IFilterMatrix extends IFilter {
    /**
     * @return macierz przekszta³cenia
     */
    public Matrix getMatrix();

    /**
     * Ustawia now¹ macierz przekszta³cenia
     * @param matrix - nowa macierz przekszta³cenia
     */
    public void setMatrix(Matrix matrix);
}
