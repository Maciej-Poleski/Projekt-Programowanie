package manager.editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Klasa reprezentująca okno do modyfikacji filtrów zakresowych
 * @author Marcin Regdos
 */
public class WindowRange extends JDialog implements ChangeListener, ActionListener, IWindowFilter {

	private final JPanel contentPanel = new JPanel();

	transient private final PixelData image;
	transient private PixelData timage;
	transient private final IFilterRange filter;
	private Range[] ranges;
	private JLabel [] jlabels;
	private JSlider [] fSliders;
	private ImageViewer preview;
	private final int dWidth=450, dHeight=450, maxSliderValue=100, dBorderSize=5, dImageSize=300;
	
	/**
	 * Konstruktor wymaga podania obrazu na którym filtr ma pracować oraz samego filtru
	 * @param image  obraz do edycji
	 * * @param iFilter filtr zakresowy
	 */
	WindowRange(PixelData image, IFilterRange iFilter, String name){
		this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.image=image;
		this.setResizable(false);
		this.filter=iFilter;
		ranges=filter.getRangeTable();
		jlabels=new  JLabel [ranges.length];
		fSliders=new JSlider [ranges.length];
		timage=(PixelData) image.clone();
		this.setTitle(name);
		initGui();
		filter.apply(image, timage);
		preview.setImage(timage.toBufferedImage());

	}
	private void initGui() {
		this.setSize(dWidth, dHeight);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(dBorderSize, dBorderSize, dBorderSize, dBorderSize));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(dBorderSize, dBorderSize));
		{
			JPanel descrAndslidersPanel = new JPanel();
			JPanel descrPanel = new JPanel();
			JPanel slidersPanel = new JPanel();
			contentPanel.add(descrAndslidersPanel, BorderLayout.SOUTH);
			descrAndslidersPanel.setLayout(new BoxLayout(descrAndslidersPanel, BoxLayout.X_AXIS));
			descrAndslidersPanel.add(descrPanel);
			descrAndslidersPanel.add(slidersPanel);
			slidersPanel.setLayout(new BoxLayout(slidersPanel, BoxLayout.Y_AXIS));
			descrPanel.setLayout(new BoxLayout(descrPanel, BoxLayout.Y_AXIS));
			for (int i=0; i<ranges.length;++i){
				{
					jlabels[i] = new JLabel();
					descrPanel.add(jlabels[i]);
				}
				{
					fSliders[i] = new JSlider(0, maxSliderValue, (int) ((ranges[i].getValue()-ranges[i].getMin())/(ranges[i].getMax()-ranges[i].getMin())*100));
					fSliders[i].setToolTipText("Zakres od "+ ranges[i].getMin() + " do " + ranges[i].getMax());
					fSliders[i].addChangeListener(this);
					slidersPanel.add(fSliders[i]);
					jlabels[i].setText(ranges[i].getName());
					}
			}

		}
		preview =new ImageViewer(timage.toBufferedImage(), dImageSize, dImageSize);
		contentPanel.add(preview, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			JButton revertButton = new JButton("Resetuj filtr");
			revertButton.setActionCommand("reset");
			buttonPane.add(revertButton);
			revertButton.addActionListener(this);
			{
				JButton okButton = new JButton("Zastosuj");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(this);
			}
			{
				JButton cancelButton = new JButton("Anuluj");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(this);
			}
			
		}


	}
	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		if (!source.getValueIsAdjusting()) {
			for (int i=0; i<ranges.length;++i){
				if (source==fSliders[i]){
					ranges[i].interpolate( Integer.valueOf(fSliders[i].getValue()).floatValue()/100);
				}
			}
			filter.setRangeTable(ranges);
			filter.apply(image, timage);
			preview.setImage(timage.toBufferedImage());
		}
	}
	@Override
	public PixelData showDialog(){
		this.setVisible(true);
		return timage;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("OK".equals(e.getActionCommand())) {
			this.setVisible(false);
			this.dispose();
			return;
		} 
		if ("Cancel".equals(e.getActionCommand())) {
			timage=null;
			this.setVisible(false);
			this.dispose();
			return;
		}
		if (e.getActionCommand().equals("reset")){
			filter.reset();
			for (int i=0; i<ranges.length;++i){
					fSliders[i].setValue((int) ((ranges[i].getValue()-ranges[i].getMin())/(ranges[i].getMax()-ranges[i].getMin())*100));
			}
			return;
		}
	} 
}
