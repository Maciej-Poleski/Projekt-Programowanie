package manager.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * Kontrolka do wyświetlania obrazu
 * Nie korzystac bezposrednio, nalezy uzyc ImageViewer
 * @author Marcin Regdos
 */
public class ImagePanel extends JPanel  {
	private Image image; //? BufferedImage
	private int zoom;
	public ImagePanel (Image image){
		this.image=image;
		zoom=100;
	}
	public void changeImage (Image image){
		this.image=image;
	}
	public void changeZoom (int zoom){
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
