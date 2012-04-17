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

	private final PixelData image;
	private PixelData timage;
	private final IFilterRange filter;
	private Range[] ranges;
	private JLabel [] jlabels;
	private JSlider [] fSliders;
	private ImageViewer preview;
	/**
	 * Konstruktor wymaga podania obrazu na którym filtr ma pracować oraz samego filtru
	 * @param image  obraz do edycji
	 * * @param iFilter filtr zakresowy
	 */
	WindowRange(PixelData image, IFilterRange iFilter, String name){
		this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.image=image;
		this.filter=iFilter;
		ranges=filter.getRangeTable();
		jlabels=new  JLabel [ranges.length];
		fSliders=new JSlider [ranges.length];
		timage=(PixelData) image.clone();
		this.setTitle(name);
		InitGui();
		filter.apply(image, timage);
		preview.setImage(timage.toBufferedImage());

	}
	private void InitGui() {
		this.setSize(450, 450);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel DescrAndSlidersPanel = new JPanel();
			JPanel DescrPanel = new JPanel();
			JPanel SlidersPanel = new JPanel();
			contentPanel.add(DescrAndSlidersPanel, BorderLayout.CENTER);
			DescrAndSlidersPanel.setLayout(new BoxLayout(DescrAndSlidersPanel, BoxLayout.X_AXIS));
			DescrAndSlidersPanel.add(DescrPanel);
			DescrAndSlidersPanel.add(SlidersPanel);
			SlidersPanel.setLayout(new BoxLayout(SlidersPanel, BoxLayout.Y_AXIS));
			DescrPanel.setLayout(new BoxLayout(DescrPanel, BoxLayout.Y_AXIS));
			for (int i=0; i<ranges.length;++i){
				{
					jlabels[i] = new JLabel();
					DescrPanel.add(jlabels[i]);
				}
				{
					fSliders[i] = new JSlider(0, 100, new Float((ranges[i].getValue()-ranges[i].getMin())/(ranges[i].getMax()-ranges[i].getMin())*100).intValue());
					fSliders[i].setToolTipText("Zakres od "+ ranges[i].getMin() + " do " + ranges[i].getMax());
					fSliders[i].addChangeListener(this);
					SlidersPanel.add(fSliders[i]);
					jlabels[i].setText(ranges[i].getName());
					}
			}

		}
		preview =new ImageViewer(timage.toBufferedImage(), 300, 300);
		contentPanel.add(preview, BorderLayout.NORTH);
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
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider)e.getSource();
		//if (!source.getValueIsAdjusting()) {
			for (int i=0; i<ranges.length;++i){
				if (source==fSliders[i]){
					ranges[i].interpolate(new Integer (fSliders[i].getValue()).floatValue()/100);
				}
			}
			filter.setRangeTable(ranges);
			filter.apply(image, timage);
			preview.setImage(timage.toBufferedImage());
		//}
	}
	@Override
	public PixelData showDialog(){
		this.setVisible(true);
		return timage;
	}

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
					fSliders[i].setValue( new Float((ranges[i].getValue()-ranges[i].getMin())/(ranges[i].getMax()-ranges[i].getMin())*100).intValue());
			}
			return;
		}
	} 
}
