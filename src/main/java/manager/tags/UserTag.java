package manager.tags;

import java.util.ArrayList;
import java.util.List;

/**
 * Tag użytkownika. Tagi tego typu tworzą strukturę DAG-u.
 * <p/>
 * Tagi użytkownika mogą być swobodnie przypisywane dowolnym plikom. Każdy plik może mieć przypisane dowolnie wiele
 * takich tagów. Tagi tego typu tworzą strukturę DAG-u. Oznacza to, że możliwe jest wyrażenie sytuacji w której dany
 * tag zawęża znaczenie kilku innych tagów. Możliwe jest np określenie że pewien gatunek muzyki jest rodzajem kilku
 * innych gatunków.
 *
 * @author Zygmunt Łenyk
 */
public class UserTag extends Tag<UserTag> {
    private final List<UserTag> parentsList = new ArrayList<>();
    private final List<UserTag> predecessorsList = new ArrayList<>();
    private static final long serialVersionUID = 1;

    UserTag(Tags creator) {
        super(creator);
    }

    @Override
    public List<UserTag> getParents() {
        return new ArrayList<>(parentsList);
    }

    @Override
    public List<UserTag> getPredecessors() {
        List<UserTag> kolejkaDoWczytania = new ArrayList<>();
        for (UserTag parent : parentsList)
            kolejkaDoWczytania.add(parent);
        while (!kolejkaDoWczytania.isEmpty()) {
            predecessorsList.add(kolejkaDoWczytania.get(0));
            for (UserTag parent : kolejkaDoWczytania.get(0).parentsList)
                kolejkaDoWczytania.add(parent);
            kolejkaDoWczytania.remove(0);
        }
        List<UserTag> predecessorsListCopy = new ArrayList<>(predecessorsList);
        predecessorsList.clear();
        return predecessorsListCopy;
    }

    @Override
    void addParent(UserTag parent) {
        if (parentsList.indexOf(parent) != -1) throw new IllegalStateException("juz istnieje taki tag");
        else {
            parentsList.add(parent);
            parent.childrenList.add(this);
        }
    }

    @Override
    void removeParent(UserTag parent) {
        if (parentsList.indexOf(parent) == -1) throw new IllegalStateException("usuwanie nieistniejącego tagu");
        else {
            parentsList.remove(parent);
            parent.childrenList.remove(this);
        }
    }

    @Override
    void addChild(UserTag child) {
        if (childrenList.indexOf(child) == -1) {
            child.addParent(this);
        } else throw new IllegalStateException("juz istnieje taki tag");
    }

    @Override
    void removeChild(UserTag child) {
        if (childrenList.indexOf(child) != -1) {
            child.removeParent(this);
        } else throw new IllegalStateException("usuwanie nieistniejącego elementu");
    }

}
