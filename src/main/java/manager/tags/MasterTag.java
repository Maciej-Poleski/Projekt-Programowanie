package manager.tags;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Tag macierzysty. Tagi tego typu tworzą strukturę drzewiastą.
 * <p/>
 * Tag macierzysty jest ściśle powiązany z lokalizacją otagowanych nim plików. Oznacza to, że wszystkie pliki otagowane
 * danym tagiem macierzystym znajdują się w tym samym katalogu. Co więcej, nazwa tego katalogu odpowiada etykiecie
 * tagu macierzystego. Tagi macierzyste tworzą strukturę lasu drzew. Oznacza to, że możliwe jest uzyskanie jednoznacznej
 * odpowiedzi na pytanie o rodzica danego tagu macierzystego. Dla dowolnego pliku otagowanego danym tagiem macierzystym
 * możliwe jest uzyskanie jego ścieżki względem korzenia (czyli lokalizacji plików na dysku, kolekcji, kopii zapasowej).
 *
 * @author Zygmunt Łenyk
 */
public class MasterTag extends Tag<MasterTag> {
    private MasterTag parent = null;
    private final List<MasterTag> predecessorsList = new ArrayList<>();
    private static final long serialVersionUID = 1;

    MasterTag(Tags creator) {
        super(creator);
    }

    @Override
    public List<MasterTag> getParents() {
        List<MasterTag> parentsList = new ArrayList<>();
        if (parent != null) {
            parentsList.add(parent);
        }
        return parentsList;
    }

    @Override
    public Collection<MasterTag> getPredecessors() {
        MasterTag tempParent = parent;
        while (tempParent != null) {
            predecessorsList.add(tempParent);
            tempParent = tempParent.parent;
        }
        List<MasterTag> predecessorsListCopy = new ArrayList<>(predecessorsList);
        predecessorsList.clear();
        return predecessorsListCopy;
    }

    @Override
    void addParent(MasterTag parent2) {
        if (this.parent != null) throw new IllegalStateException("juz istnieje rodzic");
        else {
            this.parent = parent2;
            parent2.childrenList.add(this);
        }
    }

    @Override
    void removeParent(MasterTag parent2) {
        if (parent2 != this.parent) throw new IllegalStateException("usuwanie nieistniejącego elementu");
        else {
            this.parent = null;
            parent2.childrenList.remove(this);
        }
    }

    /**
     * Zwraca rodzica tego tagu (jeżeli istnieje) lub null
     *
     * @return Rodzic tego tagu lub null
     */
    public MasterTag getParent() {
        return parent;
    }

    /**
     * Ustawia rodzica tego tagu. Poprzedni rodzic jest usuwany.
     *
     * @param masterTag Tag który zostanie rodzicem tego tagu.
     */
    void setParent(MasterTag masterTag) {
        if (parent == null && masterTag != null) {
            addParent(masterTag);
        } else if (parent != null) {
            removeParent(parent);
            if (masterTag != null) {
                addParent(masterTag);
            }
        }
    }

    @Override
    void addChild(MasterTag child) {
        if (childrenList.indexOf(child) != -1) throw new IllegalStateException("Istnieje już taki tag");
        else child.addParent(this);
    }

    @Override
    void removeChild(MasterTag child) {
        if (childrenList.indexOf(child) == -1) throw new IllegalStateException("Nie ma takiego tagu");
        else child.removeParent(this);
    }
}
