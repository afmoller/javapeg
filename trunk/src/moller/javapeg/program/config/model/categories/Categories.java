package moller.javapeg.program.config.model.categories;

import java.util.List;

public class Categories {

    private Integer highestUsedId;
    private List<Categories> categories;

    public Integer getHighestUsedId() {
        return highestUsedId;
    }
    public List<Categories> getCategories() {
        return categories;
    }
    public void setHighestUsedId(Integer highestUsedId) {
        this.highestUsedId = highestUsedId;
    }
    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }
}
