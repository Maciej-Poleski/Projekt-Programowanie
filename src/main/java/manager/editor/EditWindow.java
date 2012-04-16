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
	private String [] filterNamesGUI;
	private String [] filterCategoryNamesGUI;
	//private IFilter [] filters;
	private int [] filterType;
	private JMenuItem [] JMenuFilterButtons;
	private JMenu [] JMenuFilterCategories;
	private FilterGUI [] filters;
	private int nFilters=11;
	private int nCategories=4;
	private static class FilterGUI{
		String name, nameGUI;
		FWindowType window;
		IFilter filter;
		FilterGUI(String name, String nameGUI, FWindowType window, IFilter Filter){
			this.name=name;
			this.nameGUI=nameGUI;
			this.window=window;
			this.filter=Filter;
		}
	}
	private enum FWindowType{
		WindowRange		
	}
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
	private void InitFiltersToGUI(){
		filterNamesGUI =new String[]{
				"Detekcja krawędzi", "Filtr LUT", "Filtr matrycowy", "Histogram", "Obrót w lewo", "Obrót w prawo", "Symetria", "Uwypuklenie", "Wygładzenie", "Wyostrzanie", "Zmiana rozmiaru"
		};
		filterCategoryNamesGUI=new String[]{
				"Filtry podstawowe", "Filtry kolorystyczne","Inne", "Filtry kanałowe",  
		};
		filters=new FilterGUI[]{
				new FilterGUI("FilterBrightness","Jasność", FWindowType.WindowRange, new FilterBrightness() ),
				new FilterGUI("FilterContrast","Kontrast", FWindowType.WindowRange, new FilterContrast() ),
				new FilterGUI("FilterGamma","Korekta gamma", FWindowType.WindowRange, new FilterGamma() ),
				new FilterGUI("FilterExposure","Korekta ekspozycji", FWindowType.WindowRange, new FilterExposure() ),
				new FilterGUI("FilterSepia","Sepia", FWindowType.WindowRange, new FilterSepia() ),
				new FilterGUI("FilterColorAccent","Akcent kolorystyczny", FWindowType.WindowRange, new FilterColorAccent() ),
				new FilterGUI("FilterBinaryzation","Binaryzacja", FWindowType.WindowRange, new FilterBinaryzation() ),
				new FilterGUI("FilterSolarize","Solaryzacja", FWindowType.WindowRange, new FilterSolarize() ),
				new FilterGUI("FilterRGBCorrection","Korekta RGB", FWindowType.WindowRange, new FilterRGBCorrection() ),
				new FilterGUI("FilterCMYCorrection","Korekta CMY", FWindowType.WindowRange, new FilterCMYCorrection() ),
				new FilterGUI("FilterHSBCorrection", "Korekta HSV", FWindowType.WindowRange, new FilterHSVCorrection() ),
		};
		JMenuFilterButtons=new JMenuItem[nFilters];
		JMenuFilterCategories=new JMenu[nCategories];

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
			JMenuFilterButtons[i]=new JMenuItem(filters[i].nameGUI);
			JMenuFilterButtons[i].setActionCommand(filters[i].name);
			JMenuFilterButtons[i].addActionListener(this);
		}
		JMenuFilterCategories[0].add(JMenuFilterButtons[0]);
		JMenuFilterCategories[0].add(JMenuFilterButtons[1]);
		JMenuFilterCategories[0].add(JMenuFilterButtons[2]);
		JMenuFilterCategories[0].add(JMenuFilterButtons[3]);
		JMenuFilterCategories[1].add(JMenuFilterButtons[4]);
		JMenuFilterCategories[1].add(JMenuFilterButtons[5]);
		JMenuFilterCategories[2].add(JMenuFilterButtons[6]);
		JMenuFilterCategories[2].add(JMenuFilterButtons[7]);
		JMenuFilterCategories[3].add(JMenuFilterButtons[8]);
		JMenuFilterCategories[3].add(JMenuFilterButtons[9]);
		JMenuFilterCategories[3].add(JMenuFilterButtons[10]);
	}

	/**
	 * Konstruktor - wymagany jest obraz do edycji
	 * @param image - referencja do objektu klasy BufferedImage przechowująca obraz do edycji
	 */
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
		for (int i=0; i<filters.length; ++i){
			if (e.getActionCommand().equals(filters[i].name)) {
				switch(filters[i].window){
				case WindowRange: apply (new WindowRange(PDImage, (IFilterRange)filters[i].filter, filters[i].nameGUI).showDialog()); break;
				//case 2: apply (new WindowResize(PDImage,  filters[i].nameGUI).showDialog()); break;
				}
				return;
			}
		} 
	}
}