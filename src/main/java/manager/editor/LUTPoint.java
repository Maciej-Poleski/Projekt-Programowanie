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
     * Zmienia bądź ustanawia współrzędną X punktu. Po zmianie uaktualnia
     * położenie pola odpowiadającego za detekcję kliknięcia w ten punkt
     * @param x - nowa współrzędna X punktu
     */
    public void setX(int x){
        this.x=x;
        updateLocation();
    }
    
    /**
     * Zmienia bądź ustanawia współrzędną Y punktu. Po zmianie uaktualnia
     * położenie pola odpowiadającego za detekcję kliknięcia w ten punkt
     * @param y - nowa współrzędna Y punktu
     */
    public void setY(int y){
        this.y=y;
        updateLocation();
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
}