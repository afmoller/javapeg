/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program.categories;

import java.awt.Component;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.model.CategoriesModel;
import moller.javapeg.program.model.ModelInstanceLibrary;
import moller.util.gui.CustomJOptionPane;

public class CategoryUtil {

    /**
     * This method checks whether a category with a specified name already
     * exists. The scope for "exists" is in the same sub category or in the top
     * level. In other words the same category name may exist several times but
     * not in the same scope.
     *
     * @param root is the content to search for potential matches of an
     *                 already existing category.
     *
     * @param selectedPath if a sub category is selected, then that is
     *        specified by this parameter, otherwise it is null and that
     *        indicates that the top (root) level shall be examined.
     *
     * @param categoryName is the name of the category to search for.
     *
     * @return a boolean value indicating whether the category name to search
     *         for exists in the specified scope or not. True is returned if
     *         the specified category name in the parameter categoryName is
     *         found in the scope specified by the parameter selectedPath.
     */
    public static boolean alreadyExists(DefaultMutableTreeNode root, TreePath selectedPath, String categoryName) {

        // If the top level categories is to be examined ...
        if (selectedPath == null || selectedPath.getPathCount() == 1) {
            return existsInNode(categoryName, root);
        }
        // ... or the categories of a sub category
        else {
            return existsInNode(categoryName, (DefaultMutableTreeNode)selectedPath.getLastPathComponent());
        }
    }

    private static boolean existsInNode(String categoryName, DefaultMutableTreeNode node) {
        int numberOfChildren = node.getChildCount();

        if (numberOfChildren > 0) {
            for (int i = 0; i < numberOfChildren; i++) {
                DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)node.getChildAt(i);

                if (categoryName.equals(((CategoryUserObject)dmtn.getUserObject()).getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether the name of a category is valid.
     *
     * @param category contains the string with the category name.
     * @return true if the string contained in the parameter category does not
     *         start with a white space and the length of the category name is
     *         longer than zero characters, otherwise false is returned.
     */
    public static boolean isValid(String category) {
        return category.length() > 0 && !category.startsWith(" ");
    }

    public static JTree createCategoriesTree() {
        JTree categoriesTree = new JTree();

        CategoriesModel categoriesModel = ModelInstanceLibrary.getInstance().getCategoriesModel();

        if (categoriesModel.getRoot() == null) {
            categoriesModel.setRoot(Config.getInstance().get().getCategories());
        }

        categoriesTree.setModel(categoriesModel);
        categoriesTree.setShowsRootHandles(true);
        categoriesTree.setRootVisible(false);

        return categoriesTree;
    }

    public static Map<String, ImportedCategoryTreeAndDisplayJavaPegID> createImportedCategoriesTree() {

        Map<String, ImportedCategories> importedCategories = Config.getInstance().get().getImportedCategoriesConfig();

        Map<String, ImportedCategoryTreeAndDisplayJavaPegID> categoriesTreesMap = new HashMap<String, ImportedCategoryTreeAndDisplayJavaPegID>(importedCategories.size());

        for (String javaPegID : importedCategories.keySet()) {
            ImportedCategories importedCategory = importedCategories.get(javaPegID);

            JTree categoriesTree = new JTree();

            categoriesTree.setModel(new CategoriesModel(importedCategory.getRoot()));
            categoriesTree.setShowsRootHandles(true);
            categoriesTree.setRootVisible(false);

            ImportedCategoryTreeAndDisplayJavaPegID importedCategoryTreeAndDisplayJavaPegID = new ImportedCategoryTreeAndDisplayJavaPegID();

            importedCategoryTreeAndDisplayJavaPegID.setCategoriesTree(categoriesTree);
            importedCategoryTreeAndDisplayJavaPegID.setJavaPegId(javaPegID);

            categoriesTreesMap.put(importedCategory.getDisplayName(), importedCategoryTreeAndDisplayJavaPegID);
        }
        return categoriesTreesMap;
    }

       public static boolean displayNameAlreadyInUse(String displayName, Collection<ImportedCategories> importedCategoriesConfig) {
            for (ImportedCategories importedCategoryConfig : importedCategoriesConfig) {
                if (importedCategoryConfig.getDisplayName().equals(displayName)) {
                    return true;
                }
            }
            return false;
        }

        public static String askForANewDisplayName(Component parent, String displayName, Map<String, ImportedCategories> importedCategoriesConfig) {
            String newDisplayName = displayName;

            while (newDisplayName == null /** Cancel button was clicked **/ || newDisplayName.trim().length() == 0 || displayNameAlreadyInUse(newDisplayName, importedCategoriesConfig.values())) {

                Language lang = Language.getInstance();

                if (newDisplayName == null || newDisplayName.trim().length() == 0) {
                    newDisplayName = displayInputDialog(parent, lang.get("categoryimportexport.displayName.invalid.label"), lang.get("categoryimportexport.displayName.invalid.empty"), "");
                } else {
                    newDisplayName = displayInputDialog(parent, lang.get("categoryimportexport.displayName.invalid.label"), lang.get("categoryimportexport.displayName.invalid.alreadyInUse"), "");
                }
            }
            return newDisplayName;
        }

        public static String displayInputDialog(Component parent, String title, String label, String initialValue) {
            return CustomJOptionPane.showInputDialog(parent, label, title, initialValue);
        }
}
