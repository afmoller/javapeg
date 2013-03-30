package moller.javapeg.program.config.model.categories;

import javax.swing.tree.DefaultMutableTreeNode;

public class ImportedCategories {

    private String javaPegId;
    private String displayName;
    private DefaultMutableTreeNode root;
    private Integer highestUsedId;

    public String getJavaPegId() {
        return javaPegId;
    }
    public Integer getHighestUsedId() {
        return highestUsedId;
    }
    public void setJavaPegId(String javaPegId) {
        this.javaPegId = javaPegId;
    }
    public void setHighestUsedId(Integer highestUsedId) {
        this.highestUsedId = highestUsedId;
    }
    public String getDisplayName() {
        return displayName;
    }
    public DefaultMutableTreeNode getRoot() {
        return root;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public void setRoot(DefaultMutableTreeNode root) {
        this.root = root;
    }
    @Override
    public String toString() {
        return displayName;
    }
}
