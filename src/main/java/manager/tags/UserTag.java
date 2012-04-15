package manager.tags;

import java.util.ArrayList;
import java.util.List;

/**
 * Tag użytkownika. Tagi tego typu tworzą strukturę DAG-u.
 *
 * @author Zygmunt Łenyk
 */
public class UserTag extends Tag<UserTag> {
    List<UserTag> parentsList = new ArrayList<>();
    List<UserTag> predecessorsList = new ArrayList<>();
    private static final long serialVersionUID = 1;

    protected UserTag(Tags creator) {
        super(creator);
    }

    @Override
    public List<UserTag> getParents() {
        List<UserTag> parentsListCopy = new ArrayList<>(parentsList);
        return parentsListCopy;
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
        if (parentsList.indexOf(parent) == -1) throw new IllegalStateException("usuwanie nieistniejacego tagu");
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
