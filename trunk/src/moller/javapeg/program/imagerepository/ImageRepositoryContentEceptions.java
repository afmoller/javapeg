package moller.javapeg.program.imagerepository;

import java.util.Set;

public class ImageRepositoryContentEceptions {

    private Set<Object> allwaysAdd;
    private Set<Object> neverAdd;

    public Set<Object> getAllwaysAdd() {
        return allwaysAdd;
    }
    public Set<Object> getNeverAdd() {
        return neverAdd;
    }
    public void setAllwaysAdd(Set<Object> allwaysAdd) {
        this.allwaysAdd = allwaysAdd;
    }
    public void setNeverAdd(Set<Object> neverAdd) {
        this.neverAdd = neverAdd;
    }
}
