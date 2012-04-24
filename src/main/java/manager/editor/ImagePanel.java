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
	private Image image; 
	private int zoom, width, height;
	private static final int Defaultzoom=100, DefaultScroll=10;
	/**
	 * Nowy Image Panel
	 * @param image - wyświetlany w kontrolce obraz
	 */
	ImagePanel (Image image){
		this.image=image;
		zoom=Defaultzoom;
		this.addMouseMotionListener(this);
	}
	/**
	 * Nowy Image Panel
	 * @param image - wyświetlany w kontrolce obraz
	 * @param w - wysokość obaszru wyświetlania
	 * @param h - szerokość obszaru wyświetlania
	 */
	ImagePanel (Image image, int w, int h){
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
	/**
	 * Zmiana maksymalnego rozmiaru kontrolki
	 * @param width 
	 *		nowa szerokość (w pikselach)
	 *@param height
	 *		nowa wysokość
	 */
	void changeMaxSize (int w, int h){
		width=w;
		height=h;
	}
	void changeImageSize (int maxWidth, int maxHeight){
		int nzoom= maxWidth*Defaultzoom/ image.getWidth(null);
		int nzoom2= maxHeight*Defaultzoom/ image.getHeight(null);
		if (nzoom>nzoom2){
			nzoom=nzoom2;
		}
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
			g.drawImage(image, 0, 0, image.getWidth(null)*zoom/Defaultzoom,  image.getHeight(null)*zoom/Defaultzoom, null);
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
			return new java.awt.Dimension(image.getWidth(null)*zoom/Defaultzoom, image.getHeight(null)*zoom/Defaultzoom);
		}
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		final int t=5;
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
			return visibleRect.width - DefaultScroll;
		} else {
			return visibleRect.height - DefaultScroll;
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
			return visibleRect.width - DefaultScroll;
		} else {
			return visibleRect.height - DefaultScroll;
		}
	}
}
