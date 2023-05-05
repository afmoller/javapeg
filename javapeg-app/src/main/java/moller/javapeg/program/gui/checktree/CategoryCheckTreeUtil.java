package moller.javapeg.program.gui.checktree;

import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.categories.CategoryUserObject;
import moller.util.string.StringUtil;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * Created by Fredrik on 2015-04-24.
 */
public class CategoryCheckTreeUtil {

    /**
     * @return
     */
    public static Categories getSelectedCategoriesFromTreeModel(CheckTreeManager checkTreeManager) {
        Categories selectedId = null;

        // to get the paths that were checked
        TreePath checkedPaths[] = checkTreeManager.getSelectionModel().getSelectionPaths();

        if (checkedPaths != null  && checkedPaths.length > 0 ) {
            selectedId = new Categories();

            for (TreePath checkedPath : checkedPaths) {

                Object[] defaultMutableTreeNodes = checkedPath.getPath();
                Object leafNode = defaultMutableTreeNodes[defaultMutableTreeNodes.length-1];

                if (leafNode instanceof DefaultMutableTreeNode) {
                    String id = ((CategoryUserObject)((DefaultMutableTreeNode)leafNode).getUserObject()).getIdentity();
                    if (StringUtil.isInt(id) && Integer.parseInt(id) > -1) {
                        selectedId.addCategory(id);
                    }
                }
            }
        }
        return selectedId;
    }
}
