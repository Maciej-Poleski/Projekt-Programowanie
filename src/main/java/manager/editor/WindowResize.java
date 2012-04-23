package manager.editor;




import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
/**
 * Klasa opisuje okno dialogowe s�u��ce zmianie rozmiaru obrazka
 * @author Wojtek J�dras
 */

public class WindowResize extends JDialog implements ActionListener,PropertyChangeListener,IWindowFilter
{
	private static final long serialVersionUID = 1L;
	private PixelData iMage;
    private ButtonGroup buttonGroup1;
    private ButtonGroup buttonGroup2;
    private JButton jButton1;
    private JButton jButton2;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JRadioButton jRadioButton1;
    private JRadioButton jRadioButton2;
    private JRadioButton jRadioButton3;
    private JRadioButton jRadioButton4;
    private JFormattedTextField jTextField1;
    private JFormattedTextField jTextField2;
    private int m, n;
    private int x, y;
    /**
     * Konstruktor - wymagany jest obraz do edycji
     * @param image - referencja do objektu klasy PixelData przechowuj�ca obraz do edycji
     */
    public WindowResize(PixelData image)
	{
            iMage = image;
            initComponents();
        }

        
        private void initComponents()
        {
            this.setModal(true);
            
            buttonGroup1 = new ButtonGroup();
            buttonGroup2 = new ButtonGroup();
            jLabel1 = new JLabel();
            jRadioButton1 = new JRadioButton();
            jRadioButton2 = new JRadioButton();
            jLabel2 = new JLabel();
            jRadioButton3 = new JRadioButton();
            jRadioButton4 = new JRadioButton();
            jLabel3 = new JLabel();
            jLabel4 = new JLabel();
            jTextField1 =  new JFormattedTextField(NumberFormat.getIntegerInstance());
            jTextField2 =  new JFormattedTextField(NumberFormat.getIntegerInstance());
            jButton1 = new JButton();
            jButton2 = new JButton();
            
            m = iMage.getWidth();
            n = iMage.getHeight();
            x = m;
            y = n;
            setResizable(false);
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            
            jTextField1.setValue(new Integer(m));
            jTextField2.setValue(new Integer(n));
            
            jLabel1.setText("Algorytm kowersji:");
            
            buttonGroup1.add(jRadioButton1);
            jRadioButton1.setSelected(true);
            jRadioButton1.setText("Dwuliniowy");
            
             buttonGroup1.add(jRadioButton2);
            jRadioButton2.setText("Najbliższy sąsiad");
            
            jLabel2.setText("Zmiana rozmiaru:");
            
            jButton1.setText("OK");
            jButton2.setText("Anuluj");
            jLabel3.setText("W poziomie:");
            jLabel4.setText("W pionie:");

            buttonGroup2.add(jRadioButton3);
            jRadioButton3.setSelected(true);
            jRadioButton3.setText("Piksele");
            
            buttonGroup2.add(jRadioButton4);
            jRadioButton4.setText("Wartość procentowa");
            
            jRadioButton3.addActionListener(this);
            jRadioButton4.addActionListener(this);
            
            jButton1.addActionListener(this);
            jButton2.addActionListener(this);
            
            jTextField1.addPropertyChangeListener("value", this);
            jTextField2.addPropertyChangeListener("value", this);
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel4)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(jRadioButton1)
                                        .addGap(8, 8, 8))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jRadioButton3)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 4, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jRadioButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jRadioButton4, javax.swing.GroupLayout.Alignment.TRAILING))))))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(50, 50, 50)            
                            .addComponent(jButton2)
                          
                                
                            
                        
                        .addGap(35, 35, 35))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }
        
        /**
         * Zwraca obraz po edycji
         * @return przetworzony obraz
         */
    @Override
    public PixelData showDialog()
	{
                this.setVisible(true);
                return iMage;
	}

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if ("OK".equals(e.getActionCommand())) 
        {
		int a1 = ((Number)jTextField1.getValue()).intValue();
		int a2 = ((Number)jTextField2.getValue()).intValue();
                if (!jRadioButton3.isSelected())
                {
                    a1=(iMage.getWidth() * a1)/100;
                    a2=(iMage.getHeight() * a2)/100;
                }
                if (jRadioButton1.isSelected())
                {
                    FilterImageResizeBilinear a = new FilterImageResizeBilinear(a1, a2);
                    iMage = a.apply(iMage);
                }
                else
                {
                   FilterImageResizeNearestNeighbour a = new FilterImageResizeNearestNeighbour(a1, a2);
                   iMage = a.apply(iMage);
                }
                this.setVisible(false);
                this.dispose();
         } 
         else if("Anuluj".equals(e.getActionCommand()))
         {
            iMage=null;
            this.setVisible(false);
            this.dispose();
         }
         else if("Piksele".equals(e.getActionCommand()))
         {
             jTextField1.setValue(new Integer(m));
             jTextField2.setValue(new Integer(n));
         }
         else if("Wartość procentowa".equals(e.getActionCommand()))
         {
             jTextField1.setValue(new Integer(100));
             jTextField2.setValue(new Integer(100));
         }
         
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object source = evt.getSource();
        if (source == jTextField1){
            if (((Number)jTextField1.getValue()).intValue()<0)
            {
                jTextField1.setValue(new Integer(x));
            }
            else
            {
                x = ((Number)jTextField1.getValue()).intValue(); 
            }
        }
        else
        {
            if (((Number)jTextField2.getValue()).intValue()<0)
            {
                jTextField2.setValue(new Integer(y));
            }
            else
            {
                y = ((Number)jTextField2.getValue()).intValue(); 
            }
        }
    }

}