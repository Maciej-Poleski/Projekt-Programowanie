package manager.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Kontrolka do wyswietlania obrazu (scroll)
 * @author Marcin Regdos
 */
public class ImageViewer extends JPanel implements ChangeListener{
	private ImagePanel iPanel;
	private JPanel topPanel;
	private JScrollPane scrollPane;
	private JSpinner zoomSpinner;
	private int height, width;
	/**
     * Zostanie utworzona nowa kontrolka
     * @param image - wyswietlany obraz
     * @param width szerokosc kontrolki
     * @param height wysokosc kontrolki
     */
	public ImageViewer (Image image, int width, int height){
		this.height=height;
		this.width=width;
		iPanel=new ImagePanel(image);
		zoomSpinner =new JSpinner (new SpinnerNumberModel(100, 10,1000,10)); 
		zoomSpinner.addChangeListener(this);
		
		topPanel=new JPanel();
		topPanel.add(new JLabel ("Zoom"));
		topPanel.add(zoomSpinner);
		
		scrollPane = new JScrollPane(iPanel);
		scrollPane.setViewportBorder(BorderFactory.createLineBorder(Color.BLACK));
		scrollPane.setWheelScrollingEnabled(true);
		scrollPane.setColumnHeaderView(topPanel);
		scrollPane.setPreferredSize(new  java.awt.Dimension(this.width, this.height));
		scrollPane.setSize(scrollPane.getPreferredSize());
		this.setPreferredSize(scrollPane.getPreferredSize());
		this.add(scrollPane);
		this.repaint();
	}
	/**
     * Zmiana wyswietlanego obrazu
     * @param image - nowy obraz
     */
	public void setImage (Image image){
		iPanel.changeImage(image);
	}
	@Override
	public void stateChanged(ChangeEvent arg0) {
		iPanel.changeZoom((Integer)zoomSpinner.getValue());	
		this.revalidate();
		this.repaint();
	}
}
