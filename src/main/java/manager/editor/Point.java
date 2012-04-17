package manager.editor;

/**
 * Klasa opisująca puntk w przestrzeni pracy gradientu tj bitmapie mapowanej 
 * układem współrzędnych o granicach 0 i 1  (można wylatywać poza przedział pracy, bo
 * generatory gradientowe obsługują takie sytuacje jako rozciągnięcie gradientu w przestrzeni
 * o większym rozmiarze i nałożenie wyszczególniony fragment przestrzeni tj własnie 0 do 1)
 * @author Patryk
 */
public class Point {
	public float x;
	public float y;
	
	public Point(float mX, float mY){
		x = mX;
		y = mY;
	}
}
