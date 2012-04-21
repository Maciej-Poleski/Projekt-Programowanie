package manager.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JDialog;

/**
 *
 * @author Karol Banyś
 */
public class WindowGalery extends JDialog implements IWindowFilter, ActionListener{
    private PixelData originalPixelData;
    private PixelData copyPixelData = null;
    /*private IFilter = new Ifilter[]{
        new IFilter() {};
    };*/
    
    WindowGalery(PixelData image){
        this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        originalPixelData = image;
        copyPixelData = image;
        initComponents();
    }
    
    @Override
    public PixelData showDialog(){
        this.setVisible(true);
        return copyPixelData;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Edge Detection Vertical", "Edge Detection Horizontal", "Edge Detection Diagonal", "Edge Detection Diagonal2", "Edge Detection Sobel Horizonta", "Edge Detection Sobel Vertica", "Edge Detection Prewitt Horizontal", "Edge Detection Prewitt Vertica" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jScrollPane1.setViewportView(jList1);
        jList1.getAccessibleContext().setAccessibleParent(jList1);

        jButton1.setText("OK");
        jButton1.setActionCommand("OK");

        jButton2.setText("CANCEL");
        jButton2.setActionCommand("CANCEL");

        jLabel1.setText("Zachować zmainy?");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 419, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(247, 247, 247)
                        .addComponent(jButton1)
                        .addGap(88, 88, 88)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(297, 297, 297)
                        .addComponent(jLabel1)))
                .addContainerGap(272, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(0, 47, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        //Test
        try {
            BufferedImage im = ImageIO.read(new File("C:\\Users\\Karol\\Desktop\\rycerstwo.jpg"));
            PixelData data = new PixelData(im);
            WindowGalery window = new WindowGalery(data);
            ImageIO.write(window.showDialog().toBufferedImage(), "jpg", new File("d:\\dark-new.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(WindowGalery.class.getName()).log(Level.SEVERE, null, ex);
        }
        //test
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("CANCEL")){
            copyPixelData = null;
            System.out.println("t");
            this.setVisible(false);
            this.dispose();
            return;
        }
        if(e.getActionCommand().equals("OK")){
            this.setVisible(false);
            System.out.println("w");
            this.dispose();
            return;
        }
    }
}
