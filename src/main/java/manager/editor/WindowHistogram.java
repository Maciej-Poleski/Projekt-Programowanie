package manager.editor;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Wojtek
 */
public class WindowHistogram extends JDialog implements ActionListener, ItemListener, IWindowFilter
{
	private static final long serialVersionUID = 1L;
	private Drawing jPanel1;
    private transient Histogram cos[];
    private boolean tF[] = {false};
    private JCheckBox jCheckBox1;
    private JCheckBox jCheckBox2;
    private JCheckBox jCheckBox3;
    private JComboBox jComboBox1;
    private int check = 0;
    public WindowHistogram(PixelData data)        
    {
        cos = new Histogram[9];
        cos[0] = data.getHistogram(Histogram.HistogramChannel.RED);
        cos[1] = data.getHistogram(Histogram.HistogramChannel.GREEN);
        cos[2] = data.getHistogram(Histogram.HistogramChannel.BLUE);
        cos[3] = data.getHistogram(Histogram.HistogramChannel.CYAN);
        cos[4] = data.getHistogram(Histogram.HistogramChannel.MAGENTA);
        cos[5] = data.getHistogram(Histogram.HistogramChannel.YELLOW);
        cos[6] = data.getHistogram(Histogram.HistogramChannel.HUE);
        cos[7] = data.getHistogram(Histogram.HistogramChannel.SATURATION);
        cos[8] = data.getHistogram(Histogram.HistogramChannel.VALUE);
        initComponents();
    }

    private void initComponents() 
    {
        this.setModal(true);
        this.setTitle("Histogram");
        tF = new boolean[9];
        tF[0]=true;
        jPanel1 = new Drawing();
        jComboBox1 = new JComboBox();
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RGB", "CMY", "HSV"}));
        jCheckBox1 = new JCheckBox();
        jCheckBox2 = new JCheckBox();
        jCheckBox3 = new JCheckBox();
        JButton jButton1 = new JButton();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        jCheckBox1.setText("Czerwony");
        jCheckBox1.setSelected(true);
        jCheckBox2.setText("Zielony");
        jCheckBox3.setText("Niebieski");
        
        jButton1.setText("Zamknij");
        
        jComboBox1.addActionListener(this);
        jCheckBox1.addItemListener(this);
        jCheckBox2.addItemListener(this);
        jCheckBox3.addItemListener(this);
        jButton1.addActionListener(this);
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 515, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 259, Short.MAX_VALUE)
        );
        
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3)
                    .addComponent(jButton1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>
    
    @Override
    public PixelData showDialog()
    {
        this.setVisible(true);
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if ("Zamknij".equals(e.getActionCommand()))
        {
            this.setVisible(false);
            this.dispose();
        }
        else
        {
        for (int au=0; au<9; au++)
        {
            tF[au]=false;
        }
        if (jComboBox1.getSelectedItem().equals("RGB"))
        {
            check = 0;
            jCheckBox1.setText("Czerwony");
            jCheckBox2.setText("Zielony");
            jCheckBox3.setText("Niebieski");
        }
        if (jComboBox1.getSelectedItem().equals("CMY"))
        {
            check = 3;
            jCheckBox1.setText("Cyjan");
            jCheckBox2.setText("Magenta");
            jCheckBox3.setText("Żółty");
        }
        if (jComboBox1.getSelectedItem().equals("HSV"))
        {
            check = 6;
            jCheckBox1.setText("Barwa");
            jCheckBox2.setText("Saturacja");
            jCheckBox3.setText("Wartość");
        }
        if (jCheckBox1.isSelected())
        {
            tF[0+check]=true;
        }
        else
        {
            tF[0+check]=false;
        }
        if (jCheckBox2.isSelected())
        {
            tF[1+check]=true;
        }
        else
        {
            tF[1+check]=false;
        }
        if (jCheckBox3.isSelected())
        {
            tF[2+check]=true;
        }
        else
        {
            tF[2+check]=false;
        }
        jPanel1.repaint();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) 
    {
        if (jCheckBox1.isSelected())
        {
            tF[0+check]=true;
        }
        else
        {
            tF[0+check]=false;
        }
        if (jCheckBox2.isSelected())
        {
            tF[1+check]=true;
        }
        else
        {
            tF[1+check]=false;
        }
        if (jCheckBox3.isSelected())
        {
            tF[2+check]=true;
        }
        else
        {
            tF[2+check]=false;
        }
        jPanel1.repaint();
    }

    
    class Drawing extends JPanel
    {
        @Override
        public void paintComponent(Graphics g)
        {
            float max = 0;
            super.paintComponent(g);
            Color colortable[] = new Color[9];
            colortable[0] = Color.red;
            colortable[1] = Color.green;
            colortable[2] = Color.blue;
            colortable[3] = Color.cyan;
            colortable[4] = Color.magenta;
            colortable[5] = new Color(255, 205, 0);
            colortable[6] = new Color(153, 153, 153);
            colortable[7] = new Color(102, 102, 102);
            colortable[8] = new Color(51, 51, 51);
            
            g.drawLine(0,258,514,258);
            g.drawLine(0,0,0,257);
            g.drawLine(0,0,514,0);
            g.drawLine(514,0,514,258);
            
            for (int a1 = 0; a1<tF.length; a1++)
            {
                if (tF[a1])
                {
                    g.setColor(colortable[a1]);
                    for (int a=0; a<cos[a1].getValueTableLenght(); a++)
                    {
                        if (cos[a1].getValue(a)>max)
                        {
                            max = cos[a1].getValue(a);
                        }  
                    }
                   int x1, x2, y1, y2;
                   for(int a = 0; a<cos[a1].getValueTableLenght(); a++)
                   {
                        y1 = (int) (256-(256/max*cos[a1].getValue(a)));
                        y2 = (int) (256-(256/max*cos[a1].getValue(a+1)));
                        x1 = 512/cos[a1].getValueTableLenght()*(a);
                        x2 = 512/cos[a1].getValueTableLenght()*(a+1);
                        g.drawLine(x1+1,y1+1,x2+1,y2+1);
                   }
               }
            }
        }
    }
}


