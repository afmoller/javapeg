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
package moller.util.gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.util.*;

public class TreeUtil {

	public static void expandEntireTree(JTree tree, DefaultMutableTreeNode root, boolean expandRoot) {

		if (expandRoot) {
			tree.expandPath(new TreePath(root.getPath()));
		}

		Enumeration<TreeNode> children = root.children();

		while (children.hasMoreElements()) {
			TreeNode child = children.nextElement();

			tree.expandPath(new TreePath(((DefaultMutableTreeNode)child).getPath()));

			if(child.children().hasMoreElements()) {
				expandEntireTree(tree, (DefaultMutableTreeNode)child, expandRoot);
			}
		}
	}

	public static void collapseEntireTree(JTree tree, DefaultMutableTreeNode root, boolean collapseRoot) {

		Enumeration<TreeNode> children = root.children();

		while (children.hasMoreElements()) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode)children.nextElement();

			if(child.children().hasMoreElements()) {
				collapseEntireTree(tree, child, collapseRoot);
			}
			tree.collapsePath(new TreePath(child.getPath()));
		}

		if (collapseRoot) {
			tree.collapsePath(new TreePath(root.getPath()));
		}
	}

	public static void insertNodeInAlphabeticalOrder(DefaultMutableTreeNode childNode, DefaultMutableTreeNode parentNode, DefaultTreeModel model) {
		Enumeration<TreeNode> children = parentNode.children();

		String nodeName = childNode.toString();
		int index = 0;

		if (children.hasMoreElements()) {
			while (children.hasMoreElements()) {
				String displayString = children.nextElement().toString();
				if (nodeName.compareToIgnoreCase(displayString) < 1) {
					break;
				}
				index++;
			}
			model.insertNodeInto(childNode, parentNode, index);
		} else {
			model.insertNodeInto(childNode, parentNode, 0);
		}
	}

	public static void sortNodesAlphabetically(DefaultMutableTreeNode nodeToSort, DefaultTreeModel model) {
		int nrOfChildren = nodeToSort.getChildCount();

		if (nrOfChildren > 1) {
			Map<String, DefaultMutableTreeNode> nameNodeMap = new HashMap<>(nrOfChildren);

			for (int i = 0; i < nrOfChildren; i++) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)nodeToSort.getChildAt(i);
				nameNodeMap.put(child.toString(), child);
			}

			List<String> names = new ArrayList<>(nameNodeMap.keySet());

			names.sort(new IgnoreCaseComparator());

			for (DefaultMutableTreeNode node : nameNodeMap.values()) {
				model.removeNodeFromParent(node);
			}

			for (int i = 0; i < names.size(); i++) {
				model.insertNodeInto(nameNodeMap.get(names.get(i)), nodeToSort, i);
			}
			model.reload();
		}
	}

	private static class IgnoreCaseComparator implements Comparator<String> {
		@Override
        public int compare(String o1, String o2) {
			return o1.compareToIgnoreCase(o2);
		}
	}
}
