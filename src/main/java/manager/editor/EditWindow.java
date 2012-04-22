package manager.editor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import manager.files.backup.ImageHolder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Klasa reprezentuje okno dialogowe w którym dokonywana jest edycja obrazu
 * @author Marcin Regdos
 */
public class EditWindow extends JFrame implements ActionListener, ComponentListener  {	
	transient private PixelData pdImage;
	transient private LinkedList<PixelData> history;
	private ImageViewer mainImageViewer;
	private String [] filterCategoryNamesGUI;
	private JMenuItem [] jMenuFilterButtons;
	private JMenu [] jMenuFilterCategories;
	transient private FilterGUI [] filters;
	transient private ImageHolder iHolder;
	private static final int dWidth=800, dHeight=600, dLocation=100, dBorderSize=5, dBottomMargin=150, dSideMargins=75;
	private int mainImageViewerHeight=420, mainImageViewerWidth=560;
	private static class FilterGUI{
		String name, nameGUI;
		FWindowType window;
		IFilter filter;
		FilterGUI(String name, String nameGUI, FWindowType window, IFilter filter){
			this.name=name;
			this.nameGUI=nameGUI;
			this.window=window;
			this.filter=filter;
		}
	}
	private enum FWindowType{
		WindowRange, WindowResize, WindowMatrix, WindowLUT, WindowHistogram, NoWindow, WindowGallery, WindowGradient, WidnowHistogram
	}
	private void initGui(){
		setTitle("Edytor plików graficznych");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(dLocation, dLocation, dWidth, dHeight);
		setMinimumSize(new Dimension(dWidth, dHeight));
		initMenu();
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(dBorderSize, dBorderSize, dBorderSize, dBorderSize));
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
	private void initFiltersToGUI(){
		filterCategoryNamesGUI=new String[]{
				"Edycja", "Filtry podstawowe", "Filtry kolorystyczne","Inne", "Filtry kanałowe", "Własne filtry", 
		};
		filters=new FilterGUI[]{
				new FilterGUI("FilterBrightness","Jasność", FWindowType.WindowRange, new FilterBrightness() ),
				new FilterGUI("FilterContrast","Kontrast", FWindowType.WindowRange, new FilterContrast() ),
				new FilterGUI("FilterExposure","Korekta ekspozycji", FWindowType.WindowRange, new FilterExposure() ),
				new FilterGUI("FilterGamma","Korekta gamma", FWindowType.WindowRange, new FilterGamma() ),
				new FilterGUI("FilterColorAccent","Akcent kolorystyczny", FWindowType.WindowRange, new FilterColorAccent() ),
				new FilterGUI("FilterSepia","Sepia", FWindowType.WindowRange, new FilterSepia() ),
				new FilterGUI("FilterBinaryzation","Binaryzacja", FWindowType.WindowRange, new FilterBinaryzation() ),
				new FilterGUI("FilterSolarize","Solaryzacja", FWindowType.WindowRange, new FilterSolarize() ),
				new FilterGUI("FilterRGBCorrection","Korekta RGB", FWindowType.WindowRange, new FilterRGBCorrection() ),
				new FilterGUI("FilterCMYCorrection","Korekta CMY", FWindowType.WindowRange, new FilterCMYCorrection() ),
				new FilterGUI("FilterHSBCorrection", "Korekta HSV", FWindowType.WindowRange, new FilterHSVCorrection() ),
				new FilterGUI("grayScaleLightness", "Skala szarości (jasność)", FWindowType.NoWindow, FilterFactory.grayScaleLightness()),
				new FilterGUI("grayScaleLuminosity", "Skala szarości (nasycenie)", FWindowType.NoWindow, FilterFactory.grayScaleLuminosity()),
				new FilterGUI("grayScaleAverage", "Skala szarości (średnia)", FWindowType.NoWindow, FilterFactory.grayScaleAverage()),
				new FilterGUI("MatrixFilter", "Filtr macierzowy", FWindowType.WindowMatrix, null),
				new FilterGUI("LUTFilter", "Filtr LUT", FWindowType.WindowLUT, null),
				new FilterGUI("Gradient", "Gradient", FWindowType.WindowGradient, null),
				new FilterGUI("Resize", "Zmień rozmiar", FWindowType.WindowResize, null),
				new FilterGUI("FGallery", "Galeria filtrów", FWindowType.WindowGallery, null),
		};
		jMenuFilterButtons=new JMenuItem[filters.length];
		jMenuFilterCategories=new JMenu[filterCategoryNamesGUI.length];

	}
	private void initMenu(){
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		for (int i=0; i<jMenuFilterCategories.length; ++i){
			jMenuFilterCategories[i]=new JMenu(filterCategoryNamesGUI[i]);
			menuBar.add(jMenuFilterCategories[i]);
		}
		for (int i=0; i<jMenuFilterButtons.length; ++i){
			jMenuFilterButtons[i]=new JMenuItem(filters[i].nameGUI);
			jMenuFilterButtons[i].setActionCommand(filters[i].name);
			jMenuFilterButtons[i].addActionListener(this);
		}
		jMenuFilterCategories[1].add(jMenuFilterButtons[0]);
		jMenuFilterCategories[1].add(jMenuFilterButtons[1]);
		jMenuFilterCategories[1].add(jMenuFilterButtons[2]);
		jMenuFilterCategories[1].add(jMenuFilterButtons[3]);
		jMenuFilterCategories[2].add(jMenuFilterButtons[4]);
		jMenuFilterCategories[2].add(jMenuFilterButtons[5]);
		jMenuFilterCategories[2].add(jMenuFilterButtons[11]);
		jMenuFilterCategories[2].add(jMenuFilterButtons[12]);
		jMenuFilterCategories[2].add(jMenuFilterButtons[13]);
		jMenuFilterCategories[3].add(jMenuFilterButtons[6]);
		jMenuFilterCategories[3].add(jMenuFilterButtons[7]);
		jMenuFilterCategories[4].add(jMenuFilterButtons[8]);
		jMenuFilterCategories[4].add(jMenuFilterButtons[9]);
		jMenuFilterCategories[4].add(jMenuFilterButtons[10]);
		jMenuFilterCategories[5].add(jMenuFilterButtons[14]);
		jMenuFilterCategories[5].add(jMenuFilterButtons[15]);
		jMenuFilterCategories[5].add(jMenuFilterButtons[16]);
		JMenuItem mHistogram = new JMenuItem("Histogram");
		mHistogram.setActionCommand("mHistogram");
		mHistogram.addActionListener(this);
		mHistogram.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
		menuBar.add(mHistogram);
		
		JMenuItem mUndo = new JMenuItem("Cofnij");
		mUndo.setActionCommand("undo");
		mUndo.addActionListener(this);
		mUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		jMenuFilterCategories[0].add(mUndo);
		jMenuFilterCategories[0].add(jMenuFilterButtons[17]);
		menuBar.add(jMenuFilterButtons[18]);
		jMenuFilterButtons[18].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, InputEvent.CTRL_DOWN_MASK));
		jMenuFilterButtons[17].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
		jMenuFilterButtons[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_J, InputEvent.CTRL_DOWN_MASK));
		jMenuFilterButtons[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, InputEvent.CTRL_DOWN_MASK));
		//mEdycja.add(mUndo);
	}

	/**
	 * Konstruktor - wymagany jest obraz do edycji
	 * @param input - referencja do obiektu klasy ImageHolder, przechowującej obraz do edycji
	 */
	public EditWindow(ImageHolder input) {
		if (input==null){
			return;
		}
		iHolder=input;
		initFiltersToGUI();
		
		BufferedImage image=iHolder.getBufferedImage();
		mainImageViewer=new ImageViewer(image, mainImageViewerWidth, mainImageViewerHeight);
		pdImage=new PixelData(image);
		history=new LinkedList<PixelData>();
		initGui();
		
		getContentPane().add(mainImageViewer);
		this.addComponentListener(this);
		this.setVisible(true);
	}
	private void undo (){
		if (!history.isEmpty()){
			pdImage=history.pollLast();
			mainImageViewer.setImage(pdImage.toBufferedImage());
		}else{
			JOptionPane.showMessageDialog(this,"Brak wcześniejszej wersji","error", JOptionPane.ERROR_MESSAGE);
		}
	}
	private void apply (PixelData newPixelData){
		if (newPixelData==null){return;}
		history.add(pdImage);
		pdImage=newPixelData;	
		mainImageViewer.setImage(pdImage.toBufferedImage());
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
		if (e.getActionCommand().equals("applyclose")) {
			ImageHolder out=new ImageHolder (pdImage.toBufferedImage(), iHolder.getFileId(), iHolder.getType());
			this.setVisible(false);
			this.dispose();
			return;
		} 
		if (e.getActionCommand().equals("mHistogram")) {
			new WindowHistogram(pdImage).showDialog(); 
			return;
		} 
		for (int i=0; i<filters.length; ++i){
			if (e.getActionCommand().equals(filters[i].name)) {
				switch(filters[i].window){
				case WindowRange: 
					apply (new WindowRange(pdImage, (IFilterRange)filters[i].filter, filters[i].nameGUI).showDialog()); 
					filters[i].filter.reset();
					break;
				case WindowMatrix: 
					apply (new WindowMatrix(pdImage).showDialog()); 
					break;	
				case WindowLUT: 
					apply (new WindowLUT(pdImage).showDialog()); 
					break;
				case WindowGradient: 
					apply (new WindowGradient(pdImage).showDialog()); 
					break;	
				case WindowResize: 
					apply (new WindowResize(pdImage).showDialog()); 
					break;	
				case NoWindow:
					PixelData tdata= (PixelData)pdImage.clone();
					filters[i].filter.apply(pdImage, tdata);
					apply (tdata); 
					break;		
				case WindowGallery:
					apply (new WindowGalery(pdImage).showDialog()); 
					break;	
				case WindowHistogram:
					//new WindowHistogram(pdImage); 
					break;	
				}
				return;
			}
		}	
	}
	@Override
	public void componentHidden(ComponentEvent e) {}
	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentResized(ComponentEvent e) {
		mainImageViewer.changeSize(this.getWidth()-dSideMargins, this.getHeight()-dBottomMargin);
		
	}
	@Override
	public void componentShown(ComponentEvent e) {} 
}
