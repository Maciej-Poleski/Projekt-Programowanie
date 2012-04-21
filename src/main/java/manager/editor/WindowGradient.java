package manager.editor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import manager.editor.FilterTexturer.TexturingMode;

/**
 * Okienko do filtrów gradientowych
 * @author Patryk
 */
public class WindowGradient extends JDialog implements IWindowFilter, ActionListener {
	private static final long serialVersionUID = 1L;
	private GradientControl mGradientSetter;
	private PixelData mOriginal;
	private PixelData mAfterFilter;
	private FilterTexturer mFilter;
	private JComboBox texturingMode = new JComboBox();
	private JComboBox texturingType = new JComboBox();
	private JButton mButtonOK;
	private JButton mButtonCancel;
	WindowGradient(PixelData image){
		mOriginal = image;
		mAfterFilter = (PixelData)mOriginal.clone();
		this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800, 600);
		this.getContentPane().setLayout(new BorderLayout());
		
		mButtonOK = new JButton("Zastosuj");
		mButtonCancel= new JButton("Anuluj");
		JPanel contentPanel=new JPanel();
		JPanel buttonPanel=new JPanel();
		JPanel lPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel rPanel=new JPanel(new BorderLayout());
		
		ImagePanel preview =new ImagePanel(mAfterFilter.toBufferedImage());
		preview.changeImageSize(300, 300);
		JPanel imagePanel=new JPanel();
		imagePanel.add(preview);
		lPanel.add(imagePanel);
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		
		JPanel texturing = new JPanel();
		JPanel descrPanel = new JPanel();
		JPanel comboPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		texturing.setLayout(new BoxLayout(texturing, BoxLayout.X_AXIS));
		
		comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.Y_AXIS));
		descrPanel.setLayout(new BoxLayout(descrPanel, BoxLayout.Y_AXIS));
		JLabel label1=new JLabel ("OPIS1 ");
		JLabel label2=new JLabel ("OPIS2 ");
		descrPanel.add(label1);
		descrPanel.add(label2);
		comboPanel.add(texturingMode);
		comboPanel.add(texturingType);
		texturing.add(descrPanel);
		texturing.add(comboPanel);
		buttonPanel.add(mButtonOK);
		buttonPanel.add(mButtonCancel);
		rPanel.add(texturing, BorderLayout.NORTH);
		rPanel.add(buttonPanel, BorderLayout.SOUTH);
		lPanel.add(rPanel);
		contentPanel.add(lPanel);
		mGradientSetter = new GradientControl();
		mGradientSetter.setSize(this.getWidth(), 200);
		contentPanel.add(mGradientSetter);
		//this.add(mGradientSetter, BorderLayout.SOUTH);

		
		mButtonOK.setActionCommand("OK");
		mButtonOK.addActionListener(this);
		//this.add(mButtonOK, BorderLayout.SOUTH);
	}

	@Override
	public PixelData showDialog() {
		this.setVisible(true);
		mFilter = TexturerFactory.gradientLinearHorizontal(mGradientSetter.getGradient());
		mFilter.setMode(TexturingMode.MULTIPLY);
		mFilter.apply(mOriginal, mAfterFilter);
		return mAfterFilter;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("OK".compareTo(e.getActionCommand()) == 0){
			this.setVisible(false);
			this.dispose();
		}
	}
}
