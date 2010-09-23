package moller.util.gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class TreeUtil {

	@SuppressWarnings("unchecked")
	public static void expandEntireTree(JTree tree, DefaultMutableTreeNode root, boolean expandRoot) {
		
		if (expandRoot) {
			tree.expandPath(new TreePath(root.getPath()));
		}
		
		Enumeration<DefaultMutableTreeNode> children = root.children();
		
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode child = children.nextElement();
			
			tree.expandPath(new TreePath(child.getPath()));
			
			if(child.children().hasMoreElements()) {
				expandEntireTree(tree, child, expandRoot);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void collapseEntireTree(JTree tree, DefaultMutableTreeNode root, boolean collapseRoot) {
		
		Enumeration<DefaultMutableTreeNode> children = root.children();
		
		while (children.hasMoreElements()) {
			DefaultMutableTreeNode child = children.nextElement();
			
			if(child.children().hasMoreElements()) {
				collapseEntireTree(tree, child, collapseRoot);
			}
			tree.collapsePath(new TreePath(child.getPath()));
		}
		
		if (collapseRoot) {
			tree.collapsePath(new TreePath(root.getPath()));
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void insertNodeInAlphabeticalOrder(DefaultMutableTreeNode childNode, DefaultMutableTreeNode parentNode, DefaultTreeModel model) {
		Enumeration<DefaultMutableTreeNode> children = parentNode.children();
		
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
			Map<String, DefaultMutableTreeNode> nameNodeMap = new HashMap<String, DefaultMutableTreeNode>(nrOfChildren);
			
			for (int i = 0; i < nrOfChildren; i++) {
				DefaultMutableTreeNode child = (DefaultMutableTreeNode)nodeToSort.getChildAt(i);
				nameNodeMap.put(child.toString(), child);
			}

			List<String> names = new ArrayList<String>(nameNodeMap.keySet());
			
			Collections.sort(names, new IgnoreCaseComparator());
			
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
		public int compare(String o1, String o2) {
			return o1.compareToIgnoreCase(o2);
		}
	}
}
