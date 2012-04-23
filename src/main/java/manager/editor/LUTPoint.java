package manager.editor;

import java.awt.*;

/**
 * Klasa reprezentująca punkt na wykresie edytowalnym poprzez LUTPanel
 * @author mikolaj
 */

public class LUTPoint{
    int x;
    int y;
    Rectangle rect;

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
}