package manager.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * Kontrolka do wyswietlania obrazu
 * Nie korzystac bezposrednio, nalezy uzyc ImageViewer
 * @author Marcin Regdos
 */
class ImagePanel extends JPanel  {
	private static final long serialVersionUID = 1L;
	private Image image; //? BufferedImage
	private int zoom;
	public ImagePanel (Image image){
		this.image=image;
		zoom=100;
	}
	/**
     * Zmiana obrazu
     * @param image - nowy obraz
     */
	void changeImage (Image image){
		this.image=image;
		this.repaint();
	}
	void changeZoom (int zoom){
		this.zoom=zoom;
		this.repaint();
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponents(g);
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (image!=null){
			g.drawImage(image, 0, 0, image.getWidth(null)*zoom/100,  image.getHeight(null)*zoom/100, null);
		}
	}
	public Dimension getPreferredSize() {
        if (image==null) {
            return new Dimension(1, 1);
        } else {
            return new java.awt.Dimension(image.getWidth(null)*zoom/100, image.getHeight(null)*zoom/100);
        }
    }
}
