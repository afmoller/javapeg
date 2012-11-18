package moller.javapeg.program.categories;

import javax.swing.JTree;

public class ImportedCategoryTreeAndDisplayJavaPegID {

    private String javaPegId;
    private JTree categoriesTree;

    public String getJavaPegId() {
        return javaPegId;
    }
    public JTree getCategoriesTree() {
        return categoriesTree;
    }
    public void setJavaPegId(String javaPegId) {
        this.javaPegId = javaPegId;
    }
    public void setCategoriesTree(JTree categoriesTree) {
        this.categoriesTree = categoriesTree;
    }
}
