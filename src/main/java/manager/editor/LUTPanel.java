package manager.editor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.LinkedList;
import javax.swing.*;

/**
 * Panel wyświetlający edytowalny wykres funkcji [0,1]->[0,1]
 *
 * @author mikolaj
 */

public class LUTPanel extends JPanel implements MouseListener, MouseMotionListener{
        LinkedList<LUTPoint> points = new LinkedList<LUTPoint>();
        BufferedImage plotArea;
        Graphics2D plot;
     
        Rectangle area = new Rectangle(0, 0, 208, 208);
        BufferedImage bi;
        
        Graphics2D big;

        // Holds the coordinates of the user's last mousePressed event.
        int last_x, last_y;
        boolean firstTime = true;
        TexturePaint fillPolka, strokePolka;

        // True if the user pressed, dragged or released the mouse outside of
        // the rectangle; false otherwise.
        boolean pressOut = false;

    public LUTPanel(){
                this.setSize(208,208);
                points.add(new LUTPoint(4,204));
                points.add(new LUTPoint(204,4));
                
                paintPlot();

                plot.dispose();

                this.addMouseMotionListener(this);
                this.addMouseListener(this);
    }

    private void paintPlot(){
        plotArea = new BufferedImage(208,208, BufferedImage.TYPE_INT_RGB);
        plot = plotArea.createGraphics();
        plot.setColor(Color.white);
        plot.fillRect(0, 0, 208, 208);
        plot.setBackground(Color.white);
        plot.setColor(Color.blue);
        plot.drawPolyline(this.getXs(), this.getYs(), points.size());
        for(int i=0;i<points.size();i++){
            plot.fillOval(points.get(i).x-4, points.get(i).y-4, 8, 8);
        }
        plot.drawImage(plotArea, 0, 0, null);
  //      plot.dispose();
    }

        @Override
	protected void paintComponent(Graphics g){
		super.paintComponents(g);
		g.setColor(Color.white);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (plotArea!=null){
			g.drawImage(plotArea, 0, 0, plotArea.getWidth(), plotArea.getHeight(), null);
		}
	}

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e){

            last_x = e.getX();
            last_y = e.getY();

            // Checks whether or not the cursor is inside of the rectangle
            // while the user is pressing the mouse.
            if ( area.contains(e.getX(), e.getY()) ) {
                updateLocation(e);
//         TemporaryLUT.label.setText("contains");

            } else {
                pressOut = true;
            }
    }

    public void mouseReleased(MouseEvent e){
     //       if ( area.contains(e.getX(), e.getY()) ) {
         updateReleaseLocation(e);
  //       this.revalidate();
      //      } else {
      //   pressOut = false;
        //    }
    }

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseDragged(MouseEvent e){
  //          last_x = e.getX();
    //        last_y = e.getY();
            if ( !pressOut ) {
         updateLocation(e);
            } else {
         //       TemporaryLUT.label.setText("First position the cursor on the rectangle and then drag.");
        }
    }

    public void mouseMoved(MouseEvent e) {}

    /**
     * Dodaje nowy punkt do listy punktów wykresu, w odpowiedniej kolejności
     * @param x
     * @param y
     */
    public void addPoint(int x, int y){
        for(int i=0;i<points.size();i++){
            if(points.get(i).x>x){
                points.add(i,new LUTPoint(x,y));
                break;
            }
        }
    }
    /**
     * @return - tablica współrzędnej x argumentów
     */
    public int[] getXs(){
        int[] ret = new int[points.size()];
        for(int i=0;i<points.size();i++){
            ret[i]=points.get(i).x;
        }
        return ret;
    }
    /**
     *  - tablica współrzędnej y argumentów
     * @return
     */
    public int[] getYs(){
        int[] ret = new int[points.size()];
        for(int i=0;i<points.size();i++){
            ret[i]=points.get(i).y;
        }
        return ret;
    }
    // Handles the event of the user pressing down the mouse button.
    private float valueAt(int x){
        if(x<4){ return 0.0f; }
        if(x>204){ return 0.0f; }
        int i;
        for(i=0;i<points.size();i++){
            if(points.get(i).x>=x){
                break;
            }
        }
        float u=((float)x-(float)points.get(i-1).x)/((float)points.get(i).x-(float)points.get(i-1).x);
        float v=((float)points.get(i).x-(float)x)/((float)points.get(i).x-(float)points.get(i-1).x);
        float value=(v*(float)points.get(i-1).y+u*(float)points.get(i).y);
        return value;
    }
    
    /**
     * @return - LUTTable odpowiadająca aktualnemu wykresowi
     */
    public LUTTable getLUTTable(){
        float[] values = new float[201];
        for(int i=0;i<201;i++){
            values[i]=(204-valueAt(i+4))/200;
        }
        return new LUTTable(values);
    }

    private boolean isContainedInPlot(int x, int y){
        int i;
        for(i=0;i<points.size();i++){
            if(points.get(i).x>x){
                break;
            }
        }
        if(i==points.size()){ 
            return false;
        }
        float u=((float)x-(float)points.get(i-1).x)/((float)points.get(i).x-(float)points.get(i-1).x);
        float v=((float)points.get(i).x-(float)x)/((float)points.get(i).x-(float)points.get(i-1).x);
        float yOnPlot=(v*(float)points.get(i-1).y+u*(float)points.get(i).y);
        if(yOnPlot-y>10){ return false; }
        if(y-yOnPlot>10){ return false; }
        return true;
    }
    private int whereContained(int x, int y){
        for(int i=0;i<points.size();i++){
            if(points.get(i).rect.contains(x, y)){ return i; }
        }
        if(isContainedInPlot(x,y)){ return -1; }
        return -2;
    }

    private int possibleMovementX(int i, int x){
         if(i<=0 || i >=points.size()-1){
             return points.get(i).x;
         }
         int left=points.get(i-1).x;
         int right=points.get(i+1).x;
         if(x<=left){ return left+1; }
         if(x>=right){ return right-1; }
         return x;
     }
     private int possibleMovementY(int i, int y){
         if(y<=4){ return 4; }
         if(y>=204){ return 204; }
         return y;
     }
     private void updateReleaseLocation(MouseEvent e){
         int i=whereContained(last_x,last_y);
         if(i>=0){
         //    points.get(i).setLocation(possibleMovementX(i,e.getX()), possibleMovementY(i, e.getY()));
          //   points.get(i).y=possibleMovementY(i, e.getY());
            // points.get(i).x=possibleMovementX(i, e.getX());
             points.get(i).updateLocation();
         }
        paintPlot();
        this.revalidate();
        this.repaint();

    }
     private void updateLocation(MouseEvent e){
         int i=whereContained(last_x,last_y);
         if(i>=0){
           //  points.get(i).setLocation(possibleMovementX(i,e.getX()), possibleMovementY(i, e.getY()));
             points.get(i).y=possibleMovementY(i, e.getY());
             points.get(i).x=possibleMovementX(i, e.getX());
      //       points.get(i).updateLocation();
         
        }
        else if(i==-1){
            addPoint(last_x,last_y);
        }

        paintPlot();
        this.revalidate();
        this.repaint();
     }
}