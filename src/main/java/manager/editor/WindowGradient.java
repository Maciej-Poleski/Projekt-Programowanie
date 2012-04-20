package manager.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

import manager.editor.FilterTexturer.TexturingMode;

/**
 * Okienko do filtrów gradientowych
 * @author Patryk
 */
public class WindowGradient extends JDialog implements IWindowFilter, ActionListener {
	private GradientControl mGradientSetter;
	private PixelData mOriginal;
	private PixelData mAfterFilter;
	private FilterTexturer mFilter;
	private JButton mButtonOK;
	
	WindowGradient(PixelData image){
		mOriginal = image;
		mAfterFilter = (PixelData)mOriginal.clone();
		this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(800, 600);
		this.setLayout(new BorderLayout());
		
		mGradientSetter = new GradientControl();
		this.add(mGradientSetter, BorderLayout.CENTER);
		
		mButtonOK = new JButton("Zastosuj");
		mButtonOK.setActionCommand("OK");
		mButtonOK.addActionListener(this);
		this.add(mButtonOK, BorderLayout.SOUTH);
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
			return;
		}
	}
}
