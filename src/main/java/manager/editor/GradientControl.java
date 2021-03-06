package manager.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import manager.editor.Gradient.ColorPos;

/**
 * Kontrolka służąca do definiowania gradientu przez użytkownika
 * @author Patryk
 */
public class GradientControl extends JPanel{
	private static final long serialVersionUID = 1L;
	private static final int SET_PRECISION_PIXELS = 3;
	private static final int BOX_SIZE = 5;
	private JPanel mSetPanel;
	private Gradient mGradient;
	private JDialog mColorDialog;
	private JColorChooser mColorChooser;
	
	private float mGradientPosMouseDown;
	private float mGradientPrecision;
	
	GradientControl(){
		mColorChooser = new JColorChooser();
		mColorDialog = JColorChooser.createDialog(null, "Wybierz kolor", true, mColorChooser, null, null);
		
		mGradient = new Gradient();
		this.setLayout(new BorderLayout());
		
		mSetPanel = new JPanel(){
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g){
				super.paintComponents(g);
				float[] pos = mGradient.getPositions();
				for(int i=0;i<pos.length;i++){
					g.fillRect(((int)(pos[i]*this.getWidth()))-SET_PRECISION_PIXELS, BOX_SIZE, SET_PRECISION_PIXELS*2, BOX_SIZE);
				}
			}
		};
		mSetPanel.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
			}
			@Override
			public void mouseExited(MouseEvent arg0) {	
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				mGradientPosMouseDown = (float)arg0.getX()/(float)mSetPanel.getWidth();
				mGradientPrecision = (float)SET_PRECISION_PIXELS/(float)mSetPanel.getWidth();
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				float mGradientPosMouseUp = (float)arg0.getX()/(float)mSetPanel.getWidth();
				float[] ret = mGradient.getPositions();
				int mColorPrevIndex = -1;
				int mColorNextIndex = -1;
				for(int i=0;i<ret.length;i++){
					if(Math.abs(ret[i] - mGradientPosMouseDown) < mGradientPrecision){
						mColorPrevIndex = i;
					}
					if(Math.abs(ret[i] - mGradientPosMouseUp) < mGradientPrecision){
						mColorNextIndex = i;
					}
				}
				if(mColorPrevIndex == mColorNextIndex || mColorPrevIndex == -1){
					mColorChooser.setColor(Color.BLACK);
					mColorDialog.setVisible(true);
					Color col = mColorChooser.getColor();
					if(col != null){
						if(mColorPrevIndex == -1) {
							mGradient.add(new ColorPos(new ColorRGB(col.getRed(), col.getGreen(), col.getBlue()), mGradientPosMouseUp));
						}
						else {
							mGradient.add(new ColorPos(new ColorRGB(col.getRed(), col.getGreen(), col.getBlue()), ret[mColorPrevIndex]));
						}
					}
				}
				else {
					ColorRGB color = mGradient.interpolate(mGradientPosMouseDown);
					mGradient.delete(mGradientPosMouseDown, mGradientPrecision);
					mGradient.add(new ColorPos(color, mGradientPosMouseUp));
				}
				repaint();
			}
		});
		this.add(mSetPanel, BorderLayout.NORTH);
		
		JPanel mGradientPanel = new JPanel(){
			private static final long serialVersionUID = 1L;
			private static final int PREVIEW_WIDTH = 800;
			private static final int PREVIEW_HEIGHT = 32;
			private PixelData img = new PixelData(PREVIEW_WIDTH, PREVIEW_HEIGHT);
			@Override
			public void paintComponent(Graphics g){
				super.paintComponents(g);
				mGradient.getPixelData(img);
				g.drawImage(img.toBufferedImage(),0,0,this.getWidth(),this.getHeight(),null);
			}
		};
		this.add(mGradientPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Zwraca gradient który użytkownik zdefiniował
	 * @return gradient
	 */
	public Gradient getGradient(){
		return mGradient;
	}
}
