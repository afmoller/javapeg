package moller.javapeg.program.config.model.repository;

import java.net.URI;
import java.util.List;

public class RepositoryExceptions {

    private List<URI> allwaysAdd;
    private List<URI> neverAdd;

    public List<URI> getAllwaysAdd() {
        return allwaysAdd;
    }
    public List<URI> getNeverAdd() {
        return neverAdd;
    }
    public void setAllwaysAdd(List<URI> allwaysAdd) {
        this.allwaysAdd = allwaysAdd;
    }
    public void setNeverAdd(List<URI> neverAdd) {
        this.neverAdd = neverAdd;
    }
}
