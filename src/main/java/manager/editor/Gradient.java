package manager.editor;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasa opisująca gradient
 * @author Patryk
 */
public class Gradient {
	/**
	 * Klasa opisująca kolor wraz z jego relatywnym położeniem w gradiencie
	 * @author Patryk
	 */
	public static class ColorPos implements Comparable<ColorPos>{
		private ColorRGB mColor = new ColorRGB(0,0,0);
		private float mPos;
		/**
		 * @param rgb - kolor w przestrzeni RGB, jelsi null domyślnie czarny
		 * @param pos - pozycja w gradiencie [0,1]
		 */
		ColorPos(ColorRGB rgb, float pos){
			setColor(rgb);
			setPos(pos);
		}
		/**
		 * @return pozycja koloru w gradiencie
		 */
		public float getPos(){return mPos;}
		/**
		 * @return kolor
		 */
		public ColorRGB getColor(){return mColor;}
		
		/**
		 * Ustawia kolor w gradienice na wskazanej pozycji
		 * gdy pos < 0 przypisane zostanie 0
		 * gdy pos > 1 przypisane zostanie 1
		 * @param pos - pozycja w gradiencie [0,1]
		 */
		public final void setPos(float pos){
			mPos = Math.max(0.0f, Math.min(1.0f, pos));
		}
		/**
		 * Ustawia kolor, jesli argument jest <b>null</b> kolor nie zostanie zmieniony
		 * @param rgb - kolor z przestrzeni barw RGB
		 */
		public final void setColor(ColorRGB rgb){
			if(rgb != null) {
				mColor = rgb;
			}
		}
		@Override
		public int compareTo(ColorPos arg0) {
			return Float.valueOf(mPos).compareTo(arg0.mPos);
		}
		@Override
		public boolean equals(Object obj){
			if(!(obj instanceof ColorPos)) {return false;}
			return Float.valueOf((((ColorPos)obj).mPos)).equals(mPos);
		}
		@Override
		public int hashCode(){
			return Float.valueOf(mPos).hashCode();
		}
	}
	
	private List<ColorPos> lista = new LinkedList<ColorPos>();
	
	/**
	 * Dodaje do gradientu nowy kolor pod wskazane miejsce, 
	 * jeśli w tym miejsu już jakiś jest to go zastępuje
	 * @param col - kolor wraz ze współrzędną
	 */
	public void add(ColorPos col){
		if(lista.contains(col)){
			lista.get(lista.indexOf(col)).setColor(col.getColor());
		}
		else {
			lista.add(col);
			Collections.sort(lista);
		}
	}
	
	/**
	 * Usuwa kolor z gradientu o ile znajduje się w przedziale podanym jako argumenty
	 * Usunięte zostana wszystkie kolory z tego przedziału
	 * @param pos - pozycja w gradiencie [0,1]
	 * @param precision - precyzja szukania
	 */
	public void delete(float pos, float precision){
		if(lista.isEmpty()) return;
		Iterator<ColorPos> iter = lista.iterator();
		while(iter.hasNext()){
			if(Math.abs(iter.next().mPos - pos) < precision){
				iter.remove();
			}
		}
	}
	
	/**
	 * Zwraca tablice pozycji z przedzialu [0,1] kolorów w gradiencie
	 * Metoda współpracuje z kontrolka GradientColor
	 * @return tablica pozycji kolorów w gradiencie
	 */
	public float[] getPositions(){
		float[] ret = new float[lista.size()];
		int i=0; Iterator<ColorPos> iter = lista.iterator();
		while(iter.hasNext()){
			ret[i++] = iter.next().mPos;
		}
		return ret;
	}
	
	/**
	 * Zwraca pod wskazaną referencję interpolowaną wartość gradientu z pozycji pos
	 * podczas interpolacji tworzone jest przejście tonalne pomiędzy najbliższymi pełnymi kolorami
	 * @param pos - pozycja w gradiencie [0,1]
	 * @param ref - referencja do wyniku
	 */
	public void interpolate(float pos, ColorRGB ref){
		if(lista.isEmpty()){
			ref.setR(0);
			ref.setG(0);
			ref.setB(0);
		} else if(lista.size() == 1){
			ColorRGB rgb = lista.get(0).getColor();
			ref.setR(rgb.getR());
			ref.setG(rgb.getG());
			ref.setB(rgb.getB());
		} else {
			ColorPos prev, last;
			Iterator<ColorPos> iter = lista.iterator();
			prev = iter.next(); 
			last = prev;
			while(iter.hasNext() && last.getPos() <= pos){
				prev = last;
				last = iter.next();
			}
			if(pos <= prev.getPos()){
				ColorRGB rgb = prev.getColor();
				ref.setR(rgb.getR());
				ref.setG(rgb.getG());
				ref.setB(rgb.getB());
			} else if(pos >= last.getPos()){
				ColorRGB rgb = last.getColor();
				ref.setR(rgb.getR());
				ref.setG(rgb.getG());
				ref.setB(rgb.getB());
			} else {
				float coef = (last.getPos()-pos)/(last.getPos()-prev.getPos());
				ColorRGB lastC = last.getColor(), prevC = prev.getColor();
				ref.setR(prevC.getR()*coef + lastC.getR()*(1.0f-coef));
				ref.setG(prevC.getG()*coef + lastC.getG()*(1.0f-coef));
				ref.setB(prevC.getB()*coef + lastC.getB()*(1.0f-coef));
			}
		}
	}
	
	/**
	 * Zwraca interpolowaną wartość gradientu z pozycji pos
	 * @param pos - pozycja w gradiencie [0,1]
	 */
	public ColorRGB interpolate(float pos){
		ColorRGB ret = new ColorRGB(0,0,0);
		interpolate(pos, ret);
		return ret;
	}
	
	/**
	 * Tworzy graficzną wizualizacje gradientu i zapisuje pod wskazana referencję
	 * @param data - docelowe miejsce zapisu wyniku pracy
	 * @throws NullPointerException - gdy data = null
	 */
	public void getPixelData(PixelData data){
		float[] mData = data.getData();
		int mHeight = data.getHeight();
		int mWidth = data.getWidth();
		data.toRGB();
		ColorRGB color = new ColorRGB(0,0,0);
		for(int j=0;j<mWidth;j++) {
			this.interpolate((float)j/(float)mWidth, color);
			for(int i=0;i<mHeight;i++) {
				mData[PixelData.PIXEL_SIZE*(i*mWidth+j)] = color.getR() * ColorConverter.RGBCMY_BYTE_MAX;
				mData[PixelData.PIXEL_SIZE*(i*mWidth+j)+1] = color.getG() * ColorConverter.RGBCMY_BYTE_MAX;
				mData[PixelData.PIXEL_SIZE*(i*mWidth+j)+2] = color.getB() * ColorConverter.RGBCMY_BYTE_MAX;
			}
		}
	}
}
