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
 * TODO obsluga myszy
 */
public class ImageViewer extends JPanel implements ChangeListener{
	private Image image;
	private ImagePanel iPanel;
	private JPanel topPanel;
	private JScrollPane scrollPane;
	private JSpinner zoomSpinner;
	private int zoom, height, width;
	/**
     * Zostanie utworzona nowa kontrolka
     * @param image - wyswietlany obraz
     * @param width @param height - rozmiary kontrolki
     */
	public ImageViewer (Image image, int width, int height){
		this.image=image;
		this.height=height;
		this.width=width;
		zoom=100;
		iPanel=new ImagePanel(this.image);
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
	@Override
	public void stateChanged(ChangeEvent arg0) {
		zoom=(int) zoomSpinner.getValue();
		iPanel.changeZoom(zoom);	
		this.revalidate();
		this.repaint();
	}
}

