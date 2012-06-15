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
    protected UserTag() {
    }

    @Override
    public List<UserTag> getParents() {
        return parentsList;
    }

    @Override
    public List<UserTag> getPredecessors() {
    	List<UserTag> kolejkaDoWczytania = new ArrayList<>();
    	for(UserTag parent : parentsList)
    		kolejkaDoWczytania.add(parent);
    	while(!kolejkaDoWczytania.isEmpty()){
    		predecessorsList.add(kolejkaDoWczytania.get(0));
    		for(UserTag parent : kolejkaDoWczytania.get(0).parentsList)
    			kolejkaDoWczytania.add(parent);
    		kolejkaDoWczytania.remove(0);
    	}
        return predecessorsList;
    }

    @Override
    void addParent(UserTag parent) {
    	parentsList.add(parent);
    	parent.childrenList.add(this);
    }

    @Override
    void removeParent(UserTag parent) {
    	parentsList.remove(parent);
    	parent.childrenList.remove(this);
    }

	@Override
	void addChild(UserTag child) {
		childrenList.add(child);
		child.parentsList.add(this);
	}

	@Override
	void removeChild(UserTag child) {
		childrenList.remove(child);
		child.parentsList.remove(this);
	}
}
