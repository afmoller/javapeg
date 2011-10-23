package moller.javapeg.program.imagerepository;

import java.util.Set;

public class ImageRepository {

    private Set<Object> allwaysAdd;
    private Set<Object> neverAdd;
    private Set<Object> paths;

    public Set<Object> getAllwaysAdd() {
        return allwaysAdd;
    }
    public Set<Object> getNeverAdd() {
        return neverAdd;
    }
    public Set<Object> getPaths() {
        return paths;
    }
    public void setAllwaysAdd(Set<Object> allwaysAdd) {
        this.allwaysAdd = allwaysAdd;
    }
    public void setNeverAdd(Set<Object> neverAdd) {
        this.neverAdd = neverAdd;
    }
    public void setPaths(Set<Object> paths) {
        this.paths = paths;
    }
}
