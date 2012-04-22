package manager.editor;

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
public class WindowGalery extends JDialog implements IWindowFilter{
    private PixelData originalPixelData;
    private PixelData copyPixelData = null;
    private ImageViewer preview;
    private IFilter[] filtersEdgeDetection, filtersEdgeDetectionLaPlace, filtersLowPass, filtersHighPass, filtersEmbossing;
    WindowGalery(PixelData image){
        this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        originalPixelData = image;
        copyPixelData = image;
        preview.setImage(originalPixelData.toBufferedImage());
        initComponents();
        initFilter();
    }
    
    @Override
    public PixelData showDialog(){
        this.setVisible(true);
        return copyPixelData;
    }
    
    void initFilter(){
        //EDGE DETECTING
        filtersEdgeDetection = new IFilter[]{
            FilterFactory.edgeDetectionVertical(),
            FilterFactory.edgeDetectionHorizontal(),
            FilterFactory.edgeDetectionDiagonal1(),
            FilterFactory.edgeDetectionDiagonal2(), 
            FilterFactory.edgeDetectionSobelHorizontal(),
            FilterFactory.edgeDetectionSobelVertical(),
            FilterFactory.edgeDetectionPrewittHorizontal(), 
            FilterFactory.edgeDetectionPrewittVertical()
        };
	
	//EDGE DETECTING LAPLACE
	filtersEdgeDetectionLaPlace = new IFilter[]{
            FilterFactory.edgeDetectionLaplace1(), 
            FilterFactory.edgeDetectionLaplace2(), 
            FilterFactory.edgeDetectionLaplace3(), 
            FilterFactory.edgeDetectionLaplaceDiagonal(), 
            FilterFactory.edgeDetectionLaplaceHorizontal(), 
            FilterFactory.edgeDetectionLaplaceVertical(), 
            FilterFactory.edgeDetectionGradientDirectionalEast(), 
            FilterFactory.edgeDetectionGradientDirectionalSouthEast(), 
            FilterFactory.edgeDetectionGradientDirectionalSouth(), 
            FilterFactory.edgeDetectionGradientDirectionalSouthWest(), 
            FilterFactory.edgeDetectionGradientDirectionalWest(), 
            FilterFactory.edgeDetectionGradientDirectionalNorthWest(), 
            FilterFactory.edgeDetectionGradientDirectionalNorth(), 
            FilterFactory.edgeDetectionGradientDirectionalNorthEast()
        };
	
	//LOW PASS FILTERS
	filtersLowPass = new IFilter[]{
            FilterFactory.lowPassAverage3x3(), 
            FilterFactory.lowPassAverage5x5(), 
            FilterFactory.lowPassAverageCircle(), 
            FilterFactory.lowPassLP1(), 
            FilterFactory.lowPassLP2(), 
            FilterFactory.lowPassLP3(), 
            FilterFactory.lowPassPyramid(), 
            FilterFactory.lowPassConical(), 
            FilterFactory.lowPassGauss1(), 
            FilterFactory.lowPassGauss2(), 
            FilterFactory.lowPassGauss3(), 
            FilterFactory.lowPassGauss4(),
            FilterFactory.lowPassGauss5()
        };
	
	//HIGH PASS FILTERS
	filtersHighPass = new IFilter[]{
            FilterFactory.highPassMeanRemoval(), 
            FilterFactory.highPassHP1(), 
            FilterFactory.highPassHP2(), 
            FilterFactory.highPassHP3()
        };
	
	//EMBOSSING FILTERS
	filtersEmbossing = new IFilter[]{
            FilterFactory.embossEast(),
            FilterFactory.embossSouthEast(), 
            FilterFactory.embossSouth(), 
            FilterFactory.embossSouthWest(), 
            FilterFactory.embossWest(), 
            FilterFactory.embossNorthWest(), 
            FilterFactory.embossNorth(), 
            FilterFactory.embossNorthEast()
        };        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listEdgeDetection = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        viewPanel = new javax.swing.JPanel();
        makeEdgeDetection = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        listEdgeDetectionLaPlace = new javax.swing.JList();
        makeEdgeDetectionLaPlace = new javax.swing.JButton();
        makeLowPassFilter = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        listLowPassFilter = new javax.swing.JList();
        makeHighPassFilter = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        listHighPassFilter = new javax.swing.JList();
        makeEmbossingFilter = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        listEmbossingFilter = new javax.swing.JList();

        setTitle("Editor");

        listEdgeDetection.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Edge Detection Vertical", "Edge Detection Horizontal", "Edge Detection Diagonal", "Edge Detection Diagonal2", "Edge Detection Sobel Horizonta", "Edge Detection Sobel Vertica", "Edge Detection Prewitt Horizontal", "Edge Detection Prewitt Vertica" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listEdgeDetection.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listEdgeDetection.setValueIsAdjusting(true);
        jScrollPane1.setViewportView(listEdgeDetection);
        listEdgeDetection.getAccessibleContext().setAccessibleParent(listEdgeDetection);

        jLabel1.setText("Keep changes?");

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(okButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addGap(0, 13, Short.MAX_VALUE))
        );

        viewPanel.add(preview);

        javax.swing.GroupLayout viewPanelLayout = new javax.swing.GroupLayout(viewPanel);
        viewPanel.setLayout(viewPanelLayout);
        viewPanelLayout.setHorizontalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        viewPanelLayout.setVerticalGroup(
            viewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        makeEdgeDetection.setText("Make Edge Detection");
        makeEdgeDetection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeEdgeDetectionActionPerformed(evt);
            }
        });

        listEdgeDetectionLaPlace.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "edgeDetectionLaplace1", "edgeDetectionLaplace2", "edgeDetectionLaplace3", "edgeDetectionLaplaceDiagonal", "edgeDetectionLaplaceHorizontal", "edgeDetectionLaplaceVertical", "edgeDetectionGradientDirectionalEast", "edgeDetectionGradientDirectionalSouthEast", "edgeDetectionGradientDirectionalSouth", "edgeDetectionGradientDirectionalSouthWest", "edgeDetectionGradientDirectionalWest", "edgeDetectionGradientDirectionalNorthWest", "edgeDetectionGradientDirectionalNorth", "edgeDetectionGradientDirectionalNorthEast", " " };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listEdgeDetectionLaPlace.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(listEdgeDetectionLaPlace);

        makeEdgeDetectionLaPlace.setText("Make Edge Detection LaPlace");
        makeEdgeDetectionLaPlace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeEdgeDetectionLaPlaceActionPerformed(evt);
            }
        });

        makeLowPassFilter.setText("Make Low Pass Filter");
        makeLowPassFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeLowPassFilterActionPerformed(evt);
            }
        });

        listLowPassFilter.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "lowPassAverage3x3", "lowPassAverage5x5", "lowPassAverageCircle", "lowPassLP1", "lowPassLP2", "lowPassLP3", "lowPassPyramid", " lowPassConical", "lowPassGauss1", "lowPassGauss2", "lowPassGauss3", "lowPassGauss4", "lowPassGauss5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listLowPassFilter.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(listLowPassFilter);

        makeHighPassFilter.setText("Make High Pass Filter");
        makeHighPassFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeHighPassFilterActionPerformed(evt);
            }
        });

        listHighPassFilter.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "highPassMeanRemoval", "highPassHP1", "highPassHP2", "highPassHP3" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listHighPassFilter.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(listHighPassFilter);

        makeEmbossingFilter.setText("Make Embossing FIlter");
        makeEmbossingFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeEmbossingFilterActionPerformed(evt);
            }
        });

        listEmbossingFilter.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "embossEast", "embossSouthEast", "embossSouth", "embossSouthWest", "embossWest", "embossNorthWest", "embossNorth", "embossNorthEast" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        listEmbossingFilter.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(listEmbossingFilter);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(makeEdgeDetection, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(makeEdgeDetectionLaPlace, javax.swing.GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
                    .addComponent(makeLowPassFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)
                    .addComponent(makeHighPassFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addComponent(makeEmbossingFilter, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 400, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(404, 404, 404))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(makeEdgeDetection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(makeEdgeDetectionLaPlace)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(makeLowPassFilter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(makeHighPassFilter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(makeEmbossingFilter)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(viewPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        copyPixelData = null;
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void makeEdgeDetectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeEdgeDetectionActionPerformed
        copyPixelData = (PixelData) originalPixelData.clone();
        filtersEdgeDetection[listEdgeDetection.getSelectedIndex()].apply(originalPixelData, copyPixelData);
        preview.setImage(originalPixelData.toBufferedImage());
    }//GEN-LAST:event_makeEdgeDetectionActionPerformed

    private void makeEdgeDetectionLaPlaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeEdgeDetectionLaPlaceActionPerformed
        copyPixelData = (PixelData) originalPixelData.clone();
        filtersEdgeDetectionLaPlace[listEdgeDetectionLaPlace.getSelectedIndex()].apply(originalPixelData, copyPixelData);
        preview.setImage(originalPixelData.toBufferedImage());
    }//GEN-LAST:event_makeEdgeDetectionLaPlaceActionPerformed

    private void makeLowPassFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeLowPassFilterActionPerformed
        copyPixelData = (PixelData) originalPixelData.clone();
        filtersLowPass[listLowPassFilter.getSelectedIndex()].apply(originalPixelData, copyPixelData);
        preview.setImage(originalPixelData.toBufferedImage());
    }//GEN-LAST:event_makeLowPassFilterActionPerformed

    private void makeHighPassFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeHighPassFilterActionPerformed
        copyPixelData = (PixelData) originalPixelData.clone();
        filtersHighPass[listHighPassFilter.getSelectedIndex()].apply(originalPixelData, copyPixelData);
        preview.setImage(originalPixelData.toBufferedImage());
    }//GEN-LAST:event_makeHighPassFilterActionPerformed

    private void makeEmbossingFilterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeEmbossingFilterActionPerformed
        copyPixelData = (PixelData) originalPixelData.clone();
        filtersEmbossing[listEmbossingFilter.getSelectedIndex()].apply(originalPixelData, copyPixelData);
        preview.setImage(originalPixelData.toBufferedImage());
    }//GEN-LAST:event_makeEmbossingFilterActionPerformed

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
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JList listEdgeDetection;
    private javax.swing.JList listEdgeDetectionLaPlace;
    private javax.swing.JList listEmbossingFilter;
    private javax.swing.JList listHighPassFilter;
    private javax.swing.JList listLowPassFilter;
    private javax.swing.JButton makeEdgeDetection;
    private javax.swing.JButton makeEdgeDetectionLaPlace;
    private javax.swing.JButton makeEmbossingFilter;
    private javax.swing.JButton makeHighPassFilter;
    private javax.swing.JButton makeLowPassFilter;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel viewPanel;
    // End of variables declaration//GEN-END:variables

}
