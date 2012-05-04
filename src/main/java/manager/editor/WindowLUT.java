package manager.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
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
    private final int defaultMode = 2;
    private Color[][] colorSet;

    private PixelData inputData;
    private PixelData tempData;
    private PixelData returnData;

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
        mode = defaultMode;
        this.setTitle("LUT Użytkownika");
        this.setModal(true);
        this.setResizable(false);
        this.setLocation(100,0);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cmyFilter = new FilterLUTCorrectionCMY();
        hsvFilter = new FilterLUTCorrectionHSV();
        rgbFilter = new FilterLUTCorrectionRGB();

        channels = new LUTPanel[3];
        for(int i=0;i<3;i++){
            channels[i]=new LUTPanel();
            channels[i].setMinimumSize(new Dimension(208,208));
            channels[i].setMaximumSize(new Dimension(208,208));
        }

        inputData=image;
        tempData=new PixelData(inputData.getWidth(), inputData.getHeight());
        returnData=null;
        imagePanel=new ImageViewer(image.toBufferedImage(), 600, 600);

        colorSet = new Color[][]{
            new Color[]{Color.CYAN, Color.MAGENTA, Color.YELLOW},
            new Color[]{Color.BLACK, Color.BLACK, Color.BLACK},
            new Color[]{Color.RED, Color.GREEN, Color.BLUE},
            new Color[]{Color.GRAY, Color.GRAY, Color.GRAY}
        };
        setColors(2);

        initComponents();
        initLayout();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        applyButton = new JButton();
        previewButton = new JButton();
        abortButton = new JButton();
        modeButton = new JToggleButton[3];
        for(int i=0;i<3;i++){
            modeButton[i] = new JToggleButton();
        }
        modeButton[2].setSelected(true);

        applyButton.setText("Wykonaj");
        previewButton.setText("Podglad");
        abortButton.setText("Anuluj");
        modeButton[0].setText("CMY");
        modeButton[1].setText("HSV");
        modeButton[2].setText("RGB");

        applyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                applyMousePressed();
            }
        });

        previewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                previewMousePressed();
            }
        });

        abortButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                abortMousePressed();
            }
        });

        modeButton[0].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                modeMousePressed(0);
            }
        });

        modeButton[1].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                modeMousePressed(1);
            }
        });

        modeButton[2].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                modeMousePressed(2);
            }
        });
    }

    private void initLayout(){
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
            .addGap(20,20,20)
            .addGroup( layout.createParallelGroup()
                .addComponent(imagePanel)
                .addGroup(GroupLayout.Alignment.LEADING,
                layout.createSequentialGroup()
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
        layout.setVerticalGroup(layout.createParallelGroup()
            .addGroup(layout.createSequentialGroup()
                .addGap(20,20,20)
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
            .addGroup(layout.createSequentialGroup()
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

    private void setColors(int i){
        for(int j=0;j<3;j++){
            channels[j].setColor(colorSet[i][j]);
        }
    }

    private void applyMousePressed() {
        LUTTable[] luts = new LUTTable[3];
        for(int i=0;i<3;i++){
            luts[i] = channels[i].getLUTTable();
        }
        switch(mode){
            case -1:{
                this.setVisible(false);
                this.dispose();
                returnData=null;
                break;
            }
            case 0:{
                cmyFilter.setConversionTable(luts);
                cmyFilter.apply(inputData);
                break;
            }
            case 1:{
                hsvFilter.setConversionTable(luts);
                hsvFilter.apply(inputData);
                break;
            }
            case 2:{
                rgbFilter.setConversionTable(luts);
                rgbFilter.apply(inputData);
            }
        }
        returnData = inputData;
        imagePanel.setImage(inputData.toBufferedImage());
        imagePanel.revalidate();
        this.setVisible(false);
        this.dispose();
    }

    private void previewMousePressed() {
        try{
            LUTTable[] luts = new LUTTable[3];
            for(int i=0;i<3;i++){
                luts[i] = channels[i].getLUTTable();
            }
            switch(mode){
                case (-1):{
                    return;
                }
                case 0:{
                    cmyFilter.setConversionTable(luts);
                    cmyFilter.apply(inputData, tempData);
                    break;
                }
                case 1:{
                    hsvFilter.setConversionTable(luts);
                    hsvFilter.apply(inputData, tempData);
                    break;
                }
                case 2:{
                    rgbFilter.setConversionTable(luts);
                    rgbFilter.apply(inputData, tempData);
                }
            }
            imagePanel.setImage(tempData.toBufferedImage());
            imagePanel.revalidate();
            imagePanel.repaint();
        } catch(IllegalArgumentException e){
        }
    }

    private void modeMousePressed(int i){
        if(modeButton[i].isSelected()){
            mode=-1;
            setColors(3);
            return;
        }
        mode = i;
        setColors(i);
        for(int j=0;j<3;j++){
            if((i!=j)&&(modeButton[j].isSelected())){
                modeButton[j].setSelected(false);
            }
        }
    }

    private void abortMousePressed() {
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