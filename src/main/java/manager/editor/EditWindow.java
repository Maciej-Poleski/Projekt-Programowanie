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
	private String [] filterNames;
	private String [] filterNamesGUI;
	private String [] filterCategoryNamesGUI;
	private IFilter [] filters;
	private int [] filterType;
	private JMenuItem [] JMenuFilterButtons;
	private JMenuItem [] JMenuFilterCategories;
	private int nFilters=2;
	private int nCategories=1;
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
		JButton bClose = new JButton("Koniec");
		toolBar.add(bClose);
		bClose.setActionCommand("close");
		bClose.addActionListener(this);

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
		for (int i=0; i<nCategories; ++i){
			JMenuFilterCategories[i]=new JMenu(filterCategoryNamesGUI[i]);
			menuBar.add(JMenuFilterCategories[i]);
		}
		for (int i=0; i<nFilters; ++i){
			JMenuFilterButtons[i]=new JMenuItem(filterNamesGUI[i]);
			JMenuFilterButtons[i].setActionCommand(filterNames[i]);
			JMenuFilterButtons[i].addActionListener(this);
		}
		JMenuFilterCategories[0].add(JMenuFilterButtons[0]);
		JMenuFilterCategories[0].add(JMenuFilterButtons[1]);
	}

	/**
	 * Konstruktor - wymagany jest obraz do edycji
	 * @param image - referencja do objektu klasy BufferedImage przechowująca obraz do edycji
	 */
	private void InitFiltersToGUI(){
		filterNames =new String[]{
				"FilterBinaryzation", "FilterSepia"
		};
		filterNamesGUI =new String[]{
				"Binaryzacja", "Sepia"
		};
		filterCategoryNamesGUI=new String[]{
				"Filtry podstawowe"
		};
		filters=new IFilter[]{
				new FilterBinaryzation(), new FilterSepia()		
		};
		//(1,2..)= (WindowRange, WindowResize, WindowMatrix, WindowLUT..)
		filterType=new int[]{
				1, 1		
		};
		JMenuFilterButtons=new JMenuItem[nFilters];
		JMenuFilterCategories=new JMenu[nCategories];
		
	}
	public EditWindow(BufferedImage image) {
		InitFiltersToGUI();
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
		this.setVisible(true);
		return PDImage.toBufferedImage();
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
		if (e.getActionCommand().equals("undo")) {
			undo();
			return;
		} 
		if (e.getActionCommand().equals("close")) {
			this.setVisible(false);
			this.dispose();
			return;
		} 
		for (int i=0; i<filterNames.length; ++i){
			if (e.getActionCommand().equals(filterNames[i])) {
				switch(filterType[i]){
				case 1: apply (new WindowRange(PDImage, (IFilterRange)filters[i], filterNamesGUI[i]).showDialog()); break;
				case 2: apply (new WindowResize(PDImage,  filterNamesGUI[i]).showDialog()); break;
				}
				return;
			}
		} 
	}
}