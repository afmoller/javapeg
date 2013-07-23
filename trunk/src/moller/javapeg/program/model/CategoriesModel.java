package moller.javapeg.program.model;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class CategoriesModel extends DefaultTreeModel {

    private static final long serialVersionUID = 1L;

    public CategoriesModel(TreeNode root) {
        super(root);
    }

    @Override
    public boolean isLeaf(Object node) {
        if (isRoot(node)) {
            return false;
        }
        return super.isLeaf(node);
    }

    private boolean isRoot(Object node) {
        return node != null && node == getRoot();
    }
}
