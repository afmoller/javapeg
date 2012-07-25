package moller.javapeg.program.config.model.repository;

import java.io.File;
import java.util.List;

public class RepositoryExceptions {

    private List<File> allwaysAdd;
    private List<File> neverAdd;

    public List<File> getAllwaysAdd() {
        return allwaysAdd;
    }
    public List<File> getNeverAdd() {
        return neverAdd;
    }
    public void setAllwaysAdd(List<File> allwaysAdd) {
        this.allwaysAdd = allwaysAdd;
    }
    public void setNeverAdd(List<File> neverAdd) {
        this.neverAdd = neverAdd;
    }
}
