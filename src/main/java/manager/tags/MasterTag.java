package manager.tags;

import java.util.Collection;
import java.util.List;

/**
 * Tag macierzysty. Tagi tego typu tworzą strukturę drzewiastą.
 * 
 * @author Zygmunt Łenyk
 */
public class MasterTag extends Tag<MasterTag> {

	private static final long serialVersionUID = 1L;

	protected MasterTag() {
	}

	@Override
	public List<MasterTag> getParents() {
		return null;
	}

	@Override
	public Collection<MasterTag> getPredecessors() {
		return null;
	}

	@Override
	void addParent(MasterTag parent) {
	}

	@Override
	void removeParent(MasterTag parent) {
	}

	/**
	 * Zwraca rodzica tego tagu (jeżeli istnieje) lub null
	 * 
	 * @return Rodzic tego tagu lub null
	 */
	public MasterTag getParent() {
		return null;
	}

	/**
	 * Ustawia rodzica tego tagu. Poprzedni rodzic jest usuwany.
	 * 
	 * @param masterTag
	 *            Tag który zostanie rodzicem tego tagu.
	 */
	public void setParent(MasterTag masterTag) {
	}
}
