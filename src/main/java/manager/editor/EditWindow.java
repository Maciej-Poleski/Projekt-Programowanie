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
	private int nFilters=17;
	private int nCategories=6;
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
		WindowRange, WindowResize, WindowMatrix, WindowLUT, WindowHistogram, NoWindow, WindowGallery
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
		JButton bClose = new JButton("Zamknij");
		toolBar.add(bClose);
		bClose.setActionCommand("close");
		bClose.addActionListener(this);
		JButton bsClose = new JButton("Zapisz i zamknij");
		toolBar.add(bsClose);
		bsClose.setActionCommand("applyclose");
		bsClose.addActionListener(this);

	}
	private void InitFiltersToGUI(){
		filterNamesGUI =new String[]{
				"Detekcja krawędzi", "Filtr LUT", "Filtr matrycowy", "Histogram", "Obrót w lewo", "Obrót w prawo", "Symetria", "Uwypuklenie", "Wygładzenie", "Wyostrzanie", "Zmiana rozmiaru"
		};
		filterCategoryNamesGUI=new String[]{
				"Edycja", "Filtry podstawowe", "Filtry kolorystyczne","Inne", "Filtry kanałowe", "Własne filtry", 
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
				new FilterGUI("grayScaleLightness", "Skala szarości (jasność)", FWindowType.NoWindow, FilterFactory.grayScaleLightness()),
				new FilterGUI("grayScaleAverage", "Skala szarości (średnia)", FWindowType.NoWindow, FilterFactory.grayScaleAverage()),
				new FilterGUI("grayScaleLuminosity", "Skala szarości (nasycenie)", FWindowType.NoWindow, FilterFactory.grayScaleLuminosity()),
				new FilterGUI("MatrixFilter", "Filtr macierzowy", FWindowType.WindowMatrix, null),
				new FilterGUI("LUTFilter", "Filtr LUT", FWindowType.WindowLUT, null),
				new FilterGUI("Resize", "Zmień rozmiar", FWindowType.WindowResize, null),
		};
		JMenuFilterButtons=new JMenuItem[nFilters];
		JMenuFilterCategories=new JMenu[nCategories];

	}
	private void InitMenu(){
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		for (int i=0; i<nCategories; ++i){
			JMenuFilterCategories[i]=new JMenu(filterCategoryNamesGUI[i]);
			menuBar.add(JMenuFilterCategories[i]);
		}
		for (int i=0; i<nFilters; ++i){
			JMenuFilterButtons[i]=new JMenuItem(filters[i].nameGUI);
			JMenuFilterButtons[i].setActionCommand(filters[i].name);
			JMenuFilterButtons[i].addActionListener(this);
		}
		JMenuFilterCategories[1].add(JMenuFilterButtons[0]);
		JMenuFilterCategories[1].add(JMenuFilterButtons[1]);
		JMenuFilterCategories[1].add(JMenuFilterButtons[2]);
		JMenuFilterCategories[1].add(JMenuFilterButtons[3]);
		JMenuFilterCategories[2].add(JMenuFilterButtons[4]);
		JMenuFilterCategories[2].add(JMenuFilterButtons[5]);
		JMenuFilterCategories[2].add(JMenuFilterButtons[11]);
		JMenuFilterCategories[2].add(JMenuFilterButtons[12]);
		JMenuFilterCategories[2].add(JMenuFilterButtons[13]);
		JMenuFilterCategories[3].add(JMenuFilterButtons[6]);
		JMenuFilterCategories[3].add(JMenuFilterButtons[7]);
		JMenuFilterCategories[4].add(JMenuFilterButtons[8]);
		JMenuFilterCategories[4].add(JMenuFilterButtons[9]);
		JMenuFilterCategories[4].add(JMenuFilterButtons[10]);
		JMenuFilterCategories[5].add(JMenuFilterButtons[14]);
		JMenuFilterCategories[5].add(JMenuFilterButtons[15]);
		JMenuItem mHistogram = new JMenuItem("Histogram");
		mHistogram.setActionCommand("mHistogram");
		mHistogram.addActionListener(this);
		menuBar.add(mHistogram);
		
		JMenuItem mUndo = new JMenuItem("Cofnij");
		mUndo.setActionCommand("undo");
		mUndo.addActionListener(this);
		JMenuFilterCategories[0].add(mUndo);
		JMenuFilterCategories[0].add(JMenuFilterButtons[16]);
		//mEdycja.add(mUndo);
	}

	/**
	 * Konstruktor - wymagany jest obraz do edycji
	 * @param image - referencja do objektu klasy BufferedImage przechowująca obraz do edycji
	 */
	public EditWindow(BufferedImage image) {
		if (image==null){
			return;
		}
		InitFiltersToGUI();
		mainImageViewer=new ImageViewer(image, 400, 400);
		PDImage=new PixelData(image);
		history=new LinkedList<PixelData>();
		InitGui();
		getContentPane().add(mainImageViewer);
		this.setVisible(true);
	}
	/**
	 * Zwraca obraz po edycji. Jeśli użytkownik nie zdecydował się na zastosowanie zmian, obraz jest nullem
	 * @return przetworzony obraz
	 * 
	 */
	public BufferedImage getTransformedImage() {
		this.setVisible(true);
		if (PDImage==null){
			return null;
		}
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
			PDImage=null;
			this.setVisible(false);
			this.dispose();
			return;
		} 
		if (e.getActionCommand().equals("applyclose")) {
			this.setVisible(false);
			this.dispose();
			return;
		} 
		if (e.getActionCommand().equals("mHistogram")) {
			new WindowHistogram(null);
			return;
		} 
		for (int i=0; i<filters.length; ++i){
			if (e.getActionCommand().equals(filters[i].name)) {
				switch(filters[i].window){
				case WindowRange: 
					apply (new WindowRange(PDImage, (IFilterRange)filters[i].filter, filters[i].nameGUI).showDialog()); 
					filters[i].filter.reset();
					break;
				case WindowMatrix: 
					apply (new WindowMatrix(PDImage).showDialog()); 
					break;	
				case WindowLUT: 
					apply (new WindowLUT(PDImage).showDialog()); 
					break;
				case WindowResize: 
					apply (new WindowResize(PDImage).showDialog()); 
					break;	
				case NoWindow:
					PixelData tdata= (PixelData)PDImage.clone();
					filters[i].filter.apply(PDImage, tdata);
					apply (tdata); 
					break;		
				case WindowGallery:
					break;	
				}
				return;
			}
		}	
	} 
}
