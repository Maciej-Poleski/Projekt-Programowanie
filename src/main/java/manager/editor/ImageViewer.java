package manager.editor;

import java.awt.Color;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Kontrolka do wyswietlania obrazu (scroll)
 * @author Marcin Regdos
 */
public class ImageViewer extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 1L;
	private ImagePanel iPanel;
	private JSpinner zoomSpinner;
	private JScrollPane scrollPane;
	private int height, width;
	private static final int maxZoom=1000, defZoom=100, minZoom=10;
	/**
     * Nowy Image Viewer
     * @param image  wyswietlany obraz
     * @param width szerokosc kontrolki
     * @param height wysokosc kontrolki
     */
	public ImageViewer (Image image, int width, int height){
		this.height=height;
		this.width=width;
		iPanel=new ImagePanel(image, width, height);
		zoomSpinner =new JSpinner (new SpinnerNumberModel(defZoom, minZoom,maxZoom,minZoom)); 
		zoomSpinner.addChangeListener(this);
		JPanel topPanel=new JPanel();
		topPanel.add(new JLabel ("Zoom"));
		topPanel.add(zoomSpinner);
		scrollPane = new JScrollPane(iPanel);
		scrollPane.setViewportBorder(BorderFactory.createLineBorder(Color.BLACK));
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.setColumnHeaderView(topPanel);
		scrollPane.setPreferredSize(new  java.awt.Dimension(this.width, this.height));
		this.setPreferredSize(scrollPane.getPreferredSize());
		this.add(scrollPane);
		this.setSize(scrollPane.getPreferredSize());
		this.repaint();
	}
	/**
     * Zmiana wyswietlanego obrazu
     * @param image - nowy obraz
     */
	void setImage (Image image){
		iPanel.changeImage(image);
	}
	/**
     * Zmiana rozmiaru kontrolki
     * @param width nowa szerokość kontrolki
     * @param height nowa wysokość kontrolki
     */
	void changeSize (int width, int height){
		this.height=height;
		this.width=width;
		iPanel.changeMaxSize(width, height);
		scrollPane.setPreferredSize(new  java.awt.Dimension(this.width, this.height));
		this.setPreferredSize(scrollPane.getPreferredSize());
		//this.setSize(scrollPane.getPreferredSize());
		this.repaint();
		
	}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		iPanel.changeZoom((Integer)zoomSpinner.getValue());	
	}
}

