package manager.editor;

/**
 * Klasa opisująca puntk w przestrzeni pracy gradientu tj bitmapie mapowanej 
 * układem współrzędnych o granicach 0 i 1  (można wylatywać poza przedział pracy, bo
 * generatory gradientowe obsługują takie sytuacje jako rozciągnięcie gradientu w przestrzeni
 * o większym rozmiarze i nałożenie wyszczególniony fragment przestrzeni tj własnie 0 do 1)
 * @author Patryk
 */
public class Point {
	private float x;
	private float y;
	
	public Point(float mX, float mY){
		x = mX;
		y = mY;
	}
	
	/**
	 * Zwraca współrzędną poziomą
	 * @return wspołrzędna na osi OX
	 */
	public float getX(){
		return x;
	}
	
	/**
	 * Zwraca współrzędną pionową
	 * @return wspołrzędna na osi OY
	 */
	public float getY(){
		return y;
	}
	
	/**
	 * Ustawia współrzędną poziomą
	 * @param mX wspołrzędna na osi OX
	 */
	public void setX(float mX){
		x = mX;
	}
	
	/**
	 * Ustawia współrzędną pionową
	 * @param mY wspołrzędna na osi OY
	 */
	public void setY(float mY){
		y = mY;
	}
}
