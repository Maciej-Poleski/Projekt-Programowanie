package manager.editor;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Klasa opisuje okno dialogowe s³u¿¹ce zmianie rozmiaru obrazka
 * @author Wojtek Jêdras
 */

public class WindowResize extends JDialog implements ActionListener
{
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
    private JTextField jTextField1;
    private JTextField jTextField2;
   
    /**
     * Konstruktor - wymagany jest obraz do edycji
     * @param image - referencja do objektu klasy PixelData przechowuj¹ca obraz do edycji
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
            jTextField1 = new JTextField();
            jTextField2 = new JTextField();
            jButton1 = new JButton();
            jButton2 = new JButton();
            
            setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
            
            jLabel1.setText("Algorytm kowersji:");
            
            buttonGroup1.add(jRadioButton1);
            jRadioButton1.setSelected(true);
            jRadioButton1.setText("Dwuliniowy");
            
             buttonGroup1.add(jRadioButton2);
            jRadioButton2.setText("Najbli¿szy s¹siad");
            
            jLabel2.setText("Zmiana rozmiaru:");
            
            jButton1.setText("OK");
            jButton2.setText("Anuluj");
            jLabel3.setText("W poziomie:");
            jLabel4.setText("W pionie:");

            buttonGroup2.add(jRadioButton3);
            jRadioButton3.setSelected(true);
            jRadioButton3.setText("Piksele");
            
            buttonGroup2.add(jRadioButton4);
            jRadioButton4.setText("Wartoœæ procentowa");
            
		
            jButton1.addActionListener(this);
            jButton2.addActionListener(this);
        
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                String s1 = jTextField1.getText();
		String s2 = jTextField2.getText();
		int a1 = Integer.parseInt(s1);
		int a2 = Integer.parseInt(s2);
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
                
         } 
         else 
         {
            iMage=null;
         }
         this.setVisible(false);
         this.dispose();
    }

    
}
