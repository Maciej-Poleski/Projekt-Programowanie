package manager.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

/**
 * Kontrolka do wyswietlania obrazu
 * Nie korzystac bezposrednio, nalezy uzyc ImageViewer
 * @author Marcin Regdos
 */
class ImagePanel extends JPanel implements Scrollable, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private Image image; //? BufferedImage
	private int zoom, width, height;
	private static final int defaultzoom=100, defaultScroll=10;
	public ImagePanel (Image image){
		this.image=image;
		zoom=defaultzoom;
		this.addMouseMotionListener(this);
	}
	public ImagePanel (Image image, int w, int h){
		this(image);
		width=w;
		height=h;
	}
	/**
     * Zmiana obrazu
     * @param image - nowy obraz
     */
	void changeImage (Image image){
		this.image=image;
		updateImagePanel();
	}
	/**
     * Zmiana powiększenia obrazu
     * @param zoom - nowe powiększenie (w procentach, 100 - rozmiar oryginalny)
     */
	void changeZoom (int zoom){
		this.zoom=zoom;
		updateImagePanel();
	}
	void changeMaxSize (int w, int h){
		width=w;
		height=h;
	}
	void changeImageSize (int maxWidth, int maxHeight){
		int nzoom= maxWidth*defaultzoom/ image.getWidth(null);
		int nzoom2= maxHeight*defaultzoom/ image.getHeight(null);
		if (nzoom>nzoom2)nzoom=nzoom2;
		changeZoom(nzoom);
	}
	private void updateImagePanel(){
		this.setPreferredSize(getPreferredSize());
		this.revalidate();
		this.repaint();		
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponents(g);
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (image!=null){
			g.drawImage(image, 0, 0, image.getWidth(null)*zoom/defaultzoom,  image.getHeight(null)*zoom/defaultzoom, null);
		}
	}
	/**
	 * Na potrzeby WindowViewer
	 * @return - wymiary kontrolki
	 */
	public Dimension getPreferredSize() {
        if (image==null) {
            return new Dimension(1, 1);
        } else {
            return new java.awt.Dimension(image.getWidth(null)*zoom/defaultzoom, image.getHeight(null)*zoom/defaultzoom);
        }
    }
	@Override
	public void mouseDragged(MouseEvent e) {
		int t=5;
        scrollRectToVisible(new Rectangle(e.getX(), e.getY(), width/t, height/t));	
        scrollRectToVisible(new Rectangle(e.getX()-width/t, e.getY()-height/t, width/t, height/t));	
	}
	@Override
	public void mouseMoved(MouseEvent e) {}
	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return this.getPreferredSize();
	}
	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		 if (orientation == SwingConstants.HORIZONTAL) {
	            return visibleRect.width - defaultScroll;
	        } else {
	            return visibleRect.height - defaultScroll;
	        }
	}
	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}
	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		if (orientation == SwingConstants.HORIZONTAL) {
            return visibleRect.width - defaultScroll;
        } else {
            return visibleRect.height - defaultScroll;
        }
	}
}
