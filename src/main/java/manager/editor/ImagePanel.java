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
	private static final int defaultzoom=100;
	public ImagePanel (Image image){
		this.image=image;
		zoom=defaultzoom;
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
}
