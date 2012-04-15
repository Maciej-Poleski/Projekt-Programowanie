package manager.tags;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Reprezentuje szeroko pojęty Tag. Obiekty będące tagami można pytać o rodziców oraz dzieci.
 * Można odszukać wszystkich przodków oraz potomków.
 *
 * @param <T> Klasa konkretna rozszerzająca tą klasę.
 * @author Zygmunt Łenyk
 */
public abstract class Tag<T extends Tag<T>> implements Serializable {
    
	private static final long serialVersionUID = 1L;
	
	private List<T> childrenList;

    protected Tag() {
    }

    /**
     * Zwraca listę rodziców
     *
     * @return Lista rodziców (może być pusta)
     */
    public abstract List<T> getParents();

    /**
     * Zwraca listę dzieci
     *
     * @return Lista dzieci (może być pusta)
     */
    public List<T> getChildren() {
        return null;
    }

    /**
     * Zwraca kolekcję przodków
     *
     * @return Kolekcja przodków (może być pusta)
     */
    public abstract Collection<T> getPredecessors();

    /**
     * Zwraca kolekcję potomków
     *
     * @return Kolekcja potomków (może być pusta)
     */
    public Collection<T> getDescendants() {
        return null;
    }

    /**
     * Ustawia wskazany tag jako dziecko tego tagu w hierarchii.
     *
     * @param child Tag który zostanie dzieckiem tego tagu
     */
    void addChild(T child) {
    }

    /**
     * Usuwa wskazany tag z listy dzieci tego tagu.
     *
     * @param child Tag do usunięcia
     */
    void removeChild(T child) {
    }

    /**
     * Ustawia ten tag jako dziecko wskazanego tagu.
     *
     * @param parent Tag który stanie się rodzicem tego tagu
     */
    abstract void addParent(T parent);

    /**
     * Usuwa wskazanego rodzica
     *
     * @param parent Tag który przestaje być rodzicem tego tagu
     */
    abstract void removeParent(T parent);
}
