package manager.tags;

import java.util.List;

/**
 * Tag użytkownika. Tagi tego typu tworzą strukturę DAG-u.
 *
 * @author Zygmunt Łenyk
 */
public class UserTag extends Tag<UserTag> {
    protected UserTag() {
    }

    @Override
    public List<UserTag> getParents() {
        return null;
    }

    @Override
    public List<UserTag> getPredecessors() {
        return null;
    }

    @Override
    void addParent(UserTag parent) {
    }

    @Override
    void removeParent(UserTag parent) {
    }
}
