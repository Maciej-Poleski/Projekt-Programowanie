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
	 * Zwraca pod wskazaną referencję interpolowaną wartość gradientu z pozycji pos
	 * podczas interpolacji tworzone jest przejście tonalne pomiędzy najbliższymi pełnymi kolorami
	 * @param pos - pozycja w gradiencie [0,1]
	 * @param ref - referencja do wyniku
	 */
	public void interpolate(float pos, ColorRGB ref){
		if(lista.size() == 0){
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
}
