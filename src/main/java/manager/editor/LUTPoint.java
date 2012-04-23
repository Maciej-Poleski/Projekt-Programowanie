
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
        public LUTPoint(int x, int y){
            this.x=x;
            this.y=y;
            this.rect = new Rectangle(x-5,y-5,10,10);
        }
        public void setLocation(int a, int b){
            this.x=a;
            this.y=a;
            this.rect= new Rectangle(a-5,b-5,10,10);
        }
        public void updateLocation(){
            this.rect.setLocation(x-5,y-5);
        }
    }