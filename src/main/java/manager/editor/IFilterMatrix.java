package manager.editor;

/**
 * Szczeg�lny rodzaj filtr�w u�ywaj�cych jako parametr macierzy
 * @author Patryk
 */
public interface IFilterMatrix extends IFilter {
    /**
     * @return macierz przekszta�cenia
     */
    Matrix getMatrix();

    /**
     * Ustawia now� macierz przekszta�cenia
     * @param matrix - nowa macierz przekszta�cenia
     */
    void setMatrix(Matrix matrix);
}
