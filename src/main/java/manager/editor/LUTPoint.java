package manager.editor;

import java.awt.*;

/**
 * Klasa reprezentująca punkt na wykresie edytowalnym poprzez LUTPanel
 * @author mikolaj
 */

public class LUTPoint{
    private int x;
    private int y;
    private Rectangle rect;

    /**
     * Kontruktor wymaga podania współrzędnych
     * @param x - współrzędna pozioma
     * @param y - współrzędna pionowa
     */
    public LUTPoint(int x, int y){
        this.x=x;
        this.y=y;
        this.rect = new Rectangle(x-5,y-5,10,10);
    }

    /**
     * Zmienia położenie punktu
     * @param a - nowa współrzędna pozioma
     * @param b - nowa współrzędna pionowa
     */
    public void setLocation(int a, int b){
        this.x=a;
        this.y=a;
        this.rect= new Rectangle(a-5,b-5,10,10);
    }

    /**
     * Uaktualnia położenie pola odpowiadającego za detekcję kliknięcia w ten punkt
     */
    public void updateLocation(){
        this.rect.setLocation(x-5,y-5);
    }
    
    /**
     * Zmienia bądź ustanawia współrzędną X punktu. 
     * @param x - nowa współrzędna X punktu
     */
    public void setX(int x){
        this.x=x;
    }
    
    /**
     * Zmienia bądź ustanawia współrzędną Y punktu. 
     * @param y - nowa współrzędna Y punktu
     */
    public void setY(int y){
        this.y=y;
    }

    /**
     * Zwraca wartość współrzędnej X punktu.
     * @return - współrzędna X punktu
     */
    public int getX(){
        return this.x;
    }

    /**
     * Zwraca wartość współrzędnej Y punktu.
     * @return - współrzędna Y punktu
     */
    public int getY(){
        return this.y;
    }

    /**
     * Sprawdza czy punkt o podanych współrzędnych znajduje się w pobliżu
     * (wewnąrz prostokąta detekcyjnego) tego punktu.
     * @param x - współrzędna X sprawdzanego punktu
     * @param y - współrzędna Y sprawdzanego punktu
     * @return - true, jeśli sprawdzany punkt znajduje się w pobliżu tego punktu,
     *           false w przeciwnym wypadku
     */
    public boolean contains(int x, int y){
        return rect.contains(x, y);
    }
}