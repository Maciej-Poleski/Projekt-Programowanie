package manager.editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Klasa reprezentuje okno dialogowe w którym dokonywana jest edycja obrazu
 * @author Marcin Regdos
 */
public class EditWindow extends JFrame implements ActionListener  {
	private JPanel contentPane;
	private PixelData PDImage;
	private LinkedList<PixelData> history;
	private ImageViewer mainImageViewer;
	private void InitGui(){
		setTitle("Edytor plików graficznych");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 600);
		InitMenu();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);

		JButton buttonUndo = new JButton("Cofnij");
		toolBar.add(buttonUndo);
		buttonUndo.setActionCommand("undo");
		buttonUndo.addActionListener(this);

	}
	private void InitMenu(){
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mEdycja = new JMenu("Edycja");
		menuBar.add(mEdycja);
		
		JMenuItem mUndo = new JMenuItem("Cofnij");
		mUndo.setActionCommand("undo");
		mUndo.addActionListener(this);
		mEdycja.add(mUndo);
		
		JMenu mFiltryPodstawowe = new JMenu("Filtry podstawowe");
		menuBar.add(mFiltryPodstawowe);
		
		JMenuItem mFilterBinaryzation = new JMenuItem("Binaryzacja");
		mFilterBinaryzation.setActionCommand("FilterBinaryzation");
		mFilterBinaryzation.addActionListener(this);
		mFiltryPodstawowe.add(mFilterBinaryzation);
		
	}

	/**
	 * Konstruktor - wymagany jest obraz do edycji
	 * @param image - referencja do objektu klasy BufferedImage przechowująca obraz do edycji
	 */

	public EditWindow(BufferedImage image) {
		mainImageViewer=new ImageViewer(image, 400, 400);
		PDImage=new PixelData(image);
		history=new LinkedList<PixelData>();
		InitGui();
		getContentPane().add(mainImageViewer);
		this.setVisible(true);
	}
	/**
	 * Zwraca obraz po edycji
	 * @return przetworzony obraz
	 */
	public BufferedImage getTransformedImage() {
		return null;
	}
	private void undo (){
		if (history.isEmpty()==false){
			PDImage=history.pollLast();
			mainImageViewer.setImage(PDImage.toBufferedImage());
		}else{
			JOptionPane.showMessageDialog(this,"Brak wcześniejszej wersji","error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void apply (PixelData newPixelData){
		if (newPixelData==null)return;
		history.add(PDImage);
		PDImage=newPixelData;	
		mainImageViewer.setImage(PDImage.toBufferedImage());
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("undo".equals(e.getActionCommand())) {
			undo();
			return;
		} 
		if ("FilterBinaryzation".equals(e.getActionCommand())) {
			WindowRange dialog = new WindowRange(PDImage, new FilterBinaryzation(), "Binaryzacja");
			apply (dialog.showDialog());
			return;
		} 
	}
}