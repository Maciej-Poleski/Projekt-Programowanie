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
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Klasa reprezentuje okno dialogowe w którym dokonywana jest edycja obrazu
 * @author Marcin Regdos
 */
public class EditWindow extends JFrame implements ActionListener, ComponentListener, WindowStateListener  {	
	private static final long serialVersionUID = 1L;
	private transient PixelData pdImage;
	private transient LinkedList<PixelData> history;
	private ImageViewer mainImageViewer;
	private String [] filterCategoryNamesGUI;
	private JMenuItem [] jMenuFilterButtons;
	private JMenu [] jMenuFilterCategories;
	private transient FilterGUI [] filters;
	private transient ImageHolder iHolder;
	private transient ImageHolder returnHolder;
	private transient ActionListener parentWindow;
	private static final int DWIDTH=800, DHEIGHT=600, DLocation=100, DBORDER_SIZE=5, DBOTTOM_MARGIN=100, DSIDE_MARGINS=75;
	private int mainImageViewerHeight=420, mainImageViewerWidth=560;
	private static class FilterGUI{
		private final String name, nameGUI;
		private final FWindowType window;
		private final IFilter filter;
		FilterGUI(String name, String nameGUI, FWindowType window, IFilter filter){
			this.name=name;
			this.nameGUI=nameGUI;
			this.window=window;
			this.filter=filter;
		}
		String getName(){return name;}
		String getNameGUI(){return nameGUI;}
		IFilter getFilter(){return filter;}
		FWindowType getWindow(){return window;}
	}
	private enum FWindowType{
		WindowRange, WindowResize, WindowMatrix, WindowLUT, WindowHistogram, NoWindow, WindowGallery, WindowGradient, WidnowHistogram
	}
	private void initGui(){
		setTitle("Edytor plików graficznych");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(DLocation, DLocation, DWIDTH, DHEIGHT);
		setMinimumSize(new Dimension(DWIDTH, DHEIGHT));
		initMenu();
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(DBORDER_SIZE, DBORDER_SIZE, DBORDER_SIZE, DBORDER_SIZE));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JToolBar toolBar = new JToolBar();
		//contentPane.add(toolBar, BorderLayout.NORTH);

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
				new FilterGUI("LUTFilter", "LUT kanałowe", FWindowType.WindowLUT, null),
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
			jMenuFilterButtons[i]=new JMenuItem(filters[i].getNameGUI());
			jMenuFilterButtons[i].setActionCommand(filters[i].getName());
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
		//jMenuFilterCategories[5].add(jMenuFilterButtons[16]);
		JMenuItem mHistogram = new JMenuItem("Histogram");
		mHistogram.setActionCommand("mHistogram");
		mHistogram.addActionListener(this);
		mHistogram.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, InputEvent.CTRL_DOWN_MASK));
		menuBar.add(mHistogram);
		
		JMenuItem mUndo = new JMenuItem("Cofnij");
		mUndo.setActionCommand("undo");
		mUndo.addActionListener(this);
		mUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
		JMenuItem mSave = new JMenuItem("Zapisz");
		mSave.setActionCommand("save");
		mSave.addActionListener(this);
		mSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		JMenuItem mClose = new JMenuItem("Zakończ");
		mClose.setActionCommand("close");
		mClose.addActionListener(this);
		mClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
		
		jMenuFilterCategories[0].add(mUndo);
		jMenuFilterCategories[0].add(jMenuFilterButtons[17]);
		jMenuFilterCategories[0].add(mSave);
		jMenuFilterCategories[0].add(mClose);
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
	public EditWindow(ImageHolder input, ActionListener pw) {
		if (input==null){
			return;
		}
		returnHolder=iHolder=input;
		initFiltersToGUI();
		parentWindow=pw;
		BufferedImage image=iHolder.getBufferedImage();
		mainImageViewer=new ImageViewer(image, mainImageViewerWidth, mainImageViewerHeight);
		pdImage=new PixelData(image);
		history=new LinkedList<PixelData>();
		initGui();
		
		getContentPane().add(mainImageViewer);
		this.addComponentListener(this);
		this.addWindowStateListener(this);
		this.setVisible(true);
	}
	/**
	 * Zwraca aktualnie edytowany obraz (stan z momentu ostatniego zapisu)
	 * @return ImageHolder 
	 */
	public ImageHolder getImage() {
		return returnHolder;
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
		if (e.getActionCommand().equals("save")) {
			returnHolder=new ImageHolder (pdImage.toBufferedImage(), iHolder.getFileId(), iHolder.getType());
			JButton tb=new JButton();
			tb.addActionListener(parentWindow);
			tb.doClick();
		} 
		if (e.getActionCommand().equals("mHistogram")) {
			new WindowHistogram(pdImage).showDialog(); 
			return;
		} 
		for (int i=0; i<filters.length; ++i){
			if (e.getActionCommand().equals(filters[i].getName())) {
				switch(filters[i].getWindow()){
				case WindowRange: 
					apply (new WindowRange((PixelData)pdImage.clone(), (IFilterRange)filters[i].getFilter(), filters[i].getNameGUI()).showDialog()); 
					filters[i].getFilter().reset();
					break;
				case WindowMatrix: 
					apply (new WindowMatrix((PixelData)pdImage.clone()).showDialog()); 
					break;	
				case WindowLUT: 
					apply (new WindowLUT((PixelData)pdImage.clone()).showDialog()); 
					break;
				case WindowGradient: 
					apply (new WindowGradient((PixelData)pdImage.clone()).showDialog()); 
					break;	
				case WindowResize: 
					apply (new WindowResize((PixelData)pdImage.clone()).showDialog()); 
					break;	
				case NoWindow:
					PixelData tdata= (PixelData)pdImage.clone();
					filters[i].filter.apply(pdImage, tdata);
					apply (tdata); 
					break;		
				case WindowGallery:
					apply (new WindowGalery((PixelData)pdImage.clone()).showDialog()); 
					break;	
				default: break;	
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
		mainImageViewer.changeSize(this.getWidth()-DSIDE_MARGINS, this.getHeight()-DBOTTOM_MARGIN);
		
	}
	@Override
	public void componentShown(ComponentEvent e) {}
	@Override
	public void windowStateChanged(WindowEvent arg0) {
		mainImageViewer.changeSize(this.getWidth()-DSIDE_MARGINS, this.getHeight()-DBOTTOM_MARGIN);
		
	} 
}
