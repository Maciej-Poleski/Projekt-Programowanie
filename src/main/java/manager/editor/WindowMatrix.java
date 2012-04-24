package manager.editor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

/**
 * Klasa reprezentująca okienko do własnoręcznego zdefiniowania macierzy
 * do filtru macierzowego aplikowanego dla obrazu
 * @author Mikołaj Bińkowski
 */

public class WindowMatrix extends JDialog implements IWindowFilter{
    private static final long serialVersionUID = 1L;
    private final int defaultMode = 3;
    private int mode;

    private PixelData inputData;
    private PixelData tempData;
    private PixelData returnData;
    
    private JTextField fields[][];
    private JButton applyButton;
    private JButton previewButton;
    private JButton abortButton;
    private JToggleButton modeButton[];
    private ImageViewer imagePanel;

    /**
     * Konstruktor wymaga podania obrazu na którym pracujemy
     * @param image - obraz
     */
    WindowMatrix(PixelData image){
        this.setTitle("Macierz Użytkownika");
        this.setModal(true);
        this.setResizable(false);
        this.setLocation(100,100);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        inputData=image;
        tempData=null;
        returnData=null;
        
        initComponents();
        initLayout();

        mode = defaultMode;
        enableTextSpaces();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        imagePanel=new ImageViewer(inputData.toBufferedImage(), 320, 240);
        applyButton = new JButton();
        previewButton = new JButton();
        abortButton = new JButton();
        modeButton = new JToggleButton[3];

        fields = new JTextField[7][7];
        for(int i=0;i<3;i++){
            modeButton[i] = new JToggleButton();
        }
        for(int i=0;i<49;i++){
            fields[i/7][i%7]=new JTextField();
            fields[i/7][i%7].setText("0");
        }
        fields[3][3].setText("1");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        applyButton.setText("Wykonaj");
        previewButton.setText("Podglad");
        abortButton.setText("Anuluj");
        modeButton[0].setText("3x3");
        modeButton[1].setText("5x5");
        modeButton[2].setText("7x7");

        applyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt) {
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

        GroupLayout.ParallelGroup x = layout.createParallelGroup(GroupLayout.Alignment.TRAILING);
        GroupLayout.SequentialGroup y = layout.createSequentialGroup();

        GroupLayout.SequentialGroup xSeq[]=new GroupLayout.SequentialGroup[7];
        GroupLayout.ParallelGroup yPar[]=new GroupLayout.ParallelGroup[7];

        for(int i=0;i<7;i++){
            xSeq[i]= layout.createSequentialGroup();
            yPar[i]= layout.createParallelGroup();
            for(int j=0;j<7;j++){
                xSeq[i].addComponent(fields[i][j], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE);
                yPar[i].addComponent(fields[i][j], GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE);
            }
            x.addGroup(xSeq[i]);
            y.addGroup(yPar[i]);
        }
        y.addGap(75,75,75);

        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup()
            .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(20,20,20)
                .addComponent(imagePanel)
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(x)
                .addGap(20,20,20)
                )
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(applyButton)
                .addGap(10, 10, 10)
                .addComponent(previewButton)
                .addGap(10, 10, 10)
                .addComponent(abortButton)
                .addGap(50, 50, 50)
                .addComponent(modeButton[0])
                .addGap(10, 10, 10)
                .addComponent(modeButton[1])
                .addGap(10, 10, 10)
                .addComponent(modeButton[2])
                .addGap(20, 20, 20)
                )
            );
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(20,20,20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(y)
                    .addComponent(imagePanel)
                    )
                .addGap(20,20,20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(applyButton)
                    .addComponent(previewButton)
                    .addComponent(abortButton)
                    .addComponent(modeButton[0])
                    .addComponent(modeButton[1])
                    .addComponent(modeButton[2])
                    )
                .addGap(20,20,20)
            );
        pack();
    }

    private void enableTextSpaces(){
        int v = (7-mode)/2;
        int i, j;
        for(i=0;i<49;i++){
            fields[i/7][i%7].setEnabled(false);
        }
        for(j=v;j<7-v;j++){
            for(i=v;i<7-v;i++){
                fields[i][j].setEnabled(true);
            }
        }
    }

    private Matrix createMatrix(){
        if(mode==-1){
            throw new IllegalArgumentException();
        }
        int v=(7-mode)/2;
        int i, j;
        float tab[]= new float[mode*mode];
        for(i=v;i<7-v;i++){
            for(j=v;j<7-v;j++){
                try{
                    tab[i-v+mode*(j-v)]=Float.parseFloat(fields[i][j].getText());
                } catch(NumberFormatException e){
                    tab[i-v+mode*(j-v)]=0;
                }
            }
        }
        return new Matrix(tab);
    }

    private void applyMousePressed() {
        try{
            IFilter filter = new FilterMatrixAdapter(createMatrix());
            filter.apply(inputData);
            returnData = inputData;
            imagePanel.setImage(inputData.toBufferedImage());
            imagePanel.revalidate();

        } catch(IllegalArgumentException e){
        }
        this.setVisible(false);
        this.dispose();
    }

    private void previewMousePressed() {
        try{
            IFilter filter = new FilterMatrixAdapter(createMatrix());
            tempData=inputData;
            inputData = filter.apply(tempData);
            imagePanel.setImage(tempData.toBufferedImage());
            imagePanel.revalidate();
        } catch(IllegalArgumentException e){
        }
    }

    private void modeMousePressed(int i){
        if(modeButton[i].isSelected()){
            mode=-1;
            enableTextSpaces();
            return;
        }
        mode = 3+2*i;
        enableTextSpaces();
        modeButton[0].setSelected(false);
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
}