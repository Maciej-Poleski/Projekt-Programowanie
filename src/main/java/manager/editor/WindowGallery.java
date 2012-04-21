package manager.editor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 * Klasa reprezentująca okienko galerii filtrów
 * @author ?
 */

public class WindowGallery extends JDialog implements IWindowFilter{
    PixelData inputData;
    PixelData returnData;

    /**
     * Konstruktor wymaga podania obrazu na którym pracujemy
     * @param image - obraz
     */
    WindowGallery(PixelData image){
        this.setTitle("Galeria filtrów");
        this.setModal(true);
        this.setResizable(false);
        this.setLocation(100,100);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        returnData=image;
    }

	/**
	 * Zwraca obraz po edycji
	 * @return obraz po edycji
	 */

    public PixelData showDialog() {
        this.setVisible(true);
        return returnData;
    }
}