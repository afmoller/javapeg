package moller.javapeg.program.config.model.categories;

import javax.swing.tree.DefaultMutableTreeNode;

public class ImportedCategories {

    private String displayName;
    private DefaultMutableTreeNode root;

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
}
