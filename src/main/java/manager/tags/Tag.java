package manager.tags;

import java.io.Serializable;
import java.util.ArrayList;
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
    List<T> childrenList = new ArrayList<>();
    List<T> descendantsList = new ArrayList<>();
    Tags creator;
    private static final long serialVersionUID = 1;

    protected Tag(Tags creator) {
        this.creator = creator;
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
        List<T> childrenListCopy = new ArrayList<>(childrenList);
        return childrenListCopy;
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
        List<T> kolejkaDoWczytania = new ArrayList<>();
        for (T child : childrenList) {
            kolejkaDoWczytania.add(child);
        }
        while (!kolejkaDoWczytania.isEmpty()) {
            descendantsList.add(kolejkaDoWczytania.get(0));
            for (T child : kolejkaDoWczytania.get(0).childrenList) {
                kolejkaDoWczytania.add(child);
            }
            kolejkaDoWczytania.remove(0);
        }
        List<T> descendantsListCopy = new ArrayList<>(descendantsList);
        descendantsList.clear();
        return descendantsListCopy;
    }

    /**
     * Ustawia wskazany tag jako dziecko tego tagu w hierarchii.
     *
     * @param child Tag który zostanie dzieckiem tego tagu
     */
    abstract void addChild(T child);

    /**
     * Usuwa wskazany tag z listy dzieci tego tagu.
     *
     * @param child Tag do usunięcia
     */
    abstract void removeChild(T child);

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

    Tags getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return getCreator().getNameOfTag(this);
    }
}
