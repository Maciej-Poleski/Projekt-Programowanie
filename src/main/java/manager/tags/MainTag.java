package manager.tags;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Tag macierzysty. Tagi tego typu tworzą strukturę drzewiastą.
 *
 * @author Zygmunt Łenyk
 */
public class MainTag extends Tag<MainTag> {
    protected MainTag() {
    }

    @Override
    public List<MainTag> getParents() {
        return null;
    }

    @Override
    public Collection<MainTag> getPredecessors() {
        return null;
    }

    @Override
    void addParent(MainTag parent) {
    }

    @Override
    void removeParent(MainTag parent) {
    }

    /**
     * Zwraca rodzica tego tagu (jeżeli istnieje) lub null
     *
     * @return Rodzic tego tagu lub null
     */
    public MainTag getParent() {
        return null;
    }

    /**
     * Ustawia rodzica tego tagu. Poprzedni rodzic jest usuwany.
     *
     * @param mainTag Tag który zostanie rodzicem tego tagu.
     */
    public void setParent(MainTag mainTag) {
    }
}
