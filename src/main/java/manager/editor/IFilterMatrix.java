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
     * @param matrix - nowa macierz przekszta�cenia
     * 
     * Ustawia now� macierz przekszta�cenia
     */
    void setMatrix(Matrix matrix);
}
