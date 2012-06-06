package moller.javapeg.program.config.model.categories;

import java.util.List;

public class CategoriesCategory {

    private CategoriesCategory parentCategory;
    private List<CategoriesCategory> childCategories;

    private String name;
    private Integer id;

    public CategoriesCategory getParentCategory() {
        return parentCategory;
    }
    public List<CategoriesCategory> getChildCategories() {
        return childCategories;
    }
    public String getName() {
        return name;
    }
    public Integer getId() {
        return id;
    }
    public void setParentCategory(CategoriesCategory parentCategory) {
        this.parentCategory = parentCategory;
    }
    public void setChildCategories(List<CategoriesCategory> childCategories) {
        this.childCategories = childCategories;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
