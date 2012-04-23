package manager.editor;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JToggleButton;

/**
 * Klasa reprezentująca okienko do nieliniowej edycji kanałowej (LUTTable)
 * @author Mikołaj Bińkowski
 */

public class WindowLUT extends JDialog implements IWindowFilter{
    private int mode;
    PixelData inputData;
    PixelData tempData;
    PixelData returnData;
 //   private float[][] matrix;
 //   private JTextField fields[][];
    private JButton applyButton;
    private JButton previewButton;
    private JButton abortButton;
    private JToggleButton modeButton[];
    private ImageViewer imagePanel;
    private LUTPanel[] channels;
    private FilterLUTCorrectionCMY cmyFilter;
    private FilterLUTCorrectionHSV hsvFilter;
    private FilterLUTCorrectionRGB rgbFilter;


    /**
     * Konstruktor wymaga podania obrazu na którym pracujemy
     * @param image - obraz
     */

    WindowLUT(PixelData image){
        this.setTitle("LUT Użytkownika");
        this.setModal(true);
        this.setResizable(false);
        this.setLocation(100,0);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        inputData=image;
        tempData=null;
        returnData=null;
        imagePanel=new ImageViewer(image.toBufferedImage(), 600, 600);
        initComponents();
        mode=-1;
  //      enableTextSpaces();
    }

        @SuppressWarnings("unchecked")
    private void initComponents() {

        channels = new LUTPanel[3];
        for(int i=0;i<3;i++){
            channels[i]=new LUTPanel();
            channels[i].setMinimumSize(new Dimension(208,208));
            channels[i].setMaximumSize(new Dimension(208,208));
        }
        applyButton = new JButton();
        previewButton = new JButton();
        abortButton = new JButton();
        modeButton = new JToggleButton[3];
        for(int i=0;i<3;i++){
            modeButton[i] = new JToggleButton();
        }

        cmyFilter = new FilterLUTCorrectionCMY();
        hsvFilter = new FilterLUTCorrectionHSV();
        rgbFilter = new FilterLUTCorrectionRGB();

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        applyButton.setText("Wykonaj");
        previewButton.setText("Podglad");
        abortButton.setText("Anuluj");
        modeButton[0].setText("CMY");
        modeButton[1].setText("HSV");
        modeButton[2].setText("RGB");

        applyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                applyMousePressed(evt);
            }
        });
        previewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                previewMousePressed(evt);
            }
        });
        abortButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                abortMousePressed(evt);
            }
        });
        modeButton[0].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    modeMousePressed(evt,0);
                }
            });
        modeButton[1].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    modeMousePressed(evt,1);
                }
            });
        modeButton[2].addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    modeMousePressed(evt,2);
                }
            });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(//GroupLayout.Alignment.LEADING,
            layout.createSequentialGroup()
            .addGap(20,20,20)
            .addGroup( layout.createParallelGroup()
/*                .addGroup(GroupLayout.Alignment.LEADING,
                  layout.createSequentialGroup()
                  //  .addGap(20, 20, 20)
                    .addComponent(modeButton[0])
                    .addGap(10, 10, 10)
                    .addComponent(modeButton[1])
                    .addGap(10, 10, 10)
                    .addComponent(modeButton[2])
                    .addGap(20, 20, 20)
                    )*/
                .addComponent(imagePanel)
                .addGroup(GroupLayout.Alignment.LEADING,
                layout.createSequentialGroup()
                  //  .addGap(20, 20, 20)
                    .addComponent(applyButton)
                    .addGap(10, 10, 10)
                    .addComponent(previewButton)
                    .addGap(10, 10, 10)
                    .addComponent(abortButton)
                    .addGap(20, 20, 20)
                    .addContainerGap(20, Short.MAX_VALUE)
                    .addComponent(modeButton[0])
                    .addGap(10, 10, 10)
                    .addComponent(modeButton[1])
                    .addGap(10, 10, 10)
                    .addComponent(modeButton[2])
                    )
                )
            .addGap(20,20,20)
            .addGroup(layout.createParallelGroup()
                .addComponent(channels[0])
                .addComponent(channels[1])
                .addComponent(channels[2])
                )
            .addGap(20,20,20)
            );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(//(GroupLayout.Alignment.LEADING),
                layout.createSequentialGroup()
                    .addGap(20,20,20)
/*                    .addGroup(layout.createParallelGroup()
                        .addComponent(modeButton[0])
                        .addComponent(modeButton[1])
                        .addComponent(modeButton[2])
                        )
                    .addGap(20,20,20)*/
                    .addComponent(imagePanel)
                    .addContainerGap(20, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup()
                        .addComponent(applyButton)
                        .addComponent(previewButton)
                        .addComponent(abortButton)
                        .addComponent(modeButton[0])
                        .addComponent(modeButton[1])
                        .addComponent(modeButton[2])
                        )
                    .addGap(20,20,20)
                    )
                .addGroup(
                layout.createSequentialGroup()
                    .addGap(20,20,20)
                    .addComponent(channels[0])
                    .addGap(20,20,20)
                    .addComponent(channels[1])
                    .addGap(20,20,20)
                    .addComponent(channels[2])
                    .addGap(20,20,20)
                    )
                );
        pack();
    }

    private void applyMousePressed(MouseEvent evt) {
        LUTTable[] luts = new LUTTable[3];
        for(int i=0;i<3;i++){
            luts[i] = channels[i].getLUTTable();
        }
        switch(mode){
            case -1:
                this.setVisible(false);
                this.dispose();
                returnData=null;
            case 0:
                cmyFilter.setConversionTable(luts);
                cmyFilter.apply(inputData);
            case 1:
                hsvFilter.setConversionTable(luts);
                hsvFilter.apply(inputData);
            case 2:
                rgbFilter.setConversionTable(luts);
                rgbFilter.apply(inputData);
        }
        returnData = inputData;
        imagePanel.setImage(inputData.toBufferedImage());
        imagePanel.revalidate();
        this.setVisible(false);
        this.dispose();
    }

    private void previewMousePressed(MouseEvent evt) {
        try{
            tempData=inputData;
            LUTTable[] luts = new LUTTable[3];
            for(int i=0;i<3;i++){
                luts[i] = channels[i].getLUTTable();
            }
            switch(mode){
                case -1:
                    return;
                case 0:
                    cmyFilter.setConversionTable(luts);
                    inputData = cmyFilter.apply(tempData);
                case 1:
                    hsvFilter.setConversionTable(luts);
                    inputData = hsvFilter.apply(tempData);
                case 2:
                    rgbFilter.setConversionTable(luts);
                    inputData = rgbFilter.apply(tempData);
            }
            imagePanel.setImage(tempData.toBufferedImage());
            imagePanel.revalidate();
        } catch(IllegalArgumentException e){
         //   throw e;
        }
    }

    private void modeMousePressed(MouseEvent evt, int i){
        if(modeButton[i].isSelected()){
            mode=-1;
            return;
        }
        mode = i;
//        modeButton[0].setSelected(false);
        for(int j=0;j<3;j++){
            if((i!=j)&&(modeButton[j].isSelected())){
                modeButton[j].setSelected(false);
            }
        }
    }

    private void abortMousePressed(MouseEvent evt) {
        this.setVisible(false);
        this.dispose();
        returnData=null;
    }
	/**
	 * Zwraca obraz po edycji
	 * @return obraz po edycji
	 */
    public PixelData getTransformedImage(){
        return returnData;
    }

    public PixelData showDialog() {
        this.setVisible(true);
        return returnData;
    }
        public static void main(String... args) throws java.io.IOException {
        java.awt.image.BufferedImage img = null;
        try {
            String path =System.getProperty("user.dir")+java.io.File.separatorChar+"Documents"+java.io.File.separatorChar+"aneciak.jpg";
 //          String path= ="file://"+System.getProperty("user.dir")+File.separatorChar+"images"+File.separatorChar+"first.jpg";
            System.out.println(path);
            img = javax.imageio.ImageIO.read(new java.io.File(path));
            final PixelData data = new PixelData(img);
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new WindowLUT(data).setVisible(true);
                }
            });
        } catch (java.io.IOException e) {
            throw e;
        }
    }

}
