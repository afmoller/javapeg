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
