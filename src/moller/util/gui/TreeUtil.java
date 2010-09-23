package moller.util.gui;

import java.util.Enumeration;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
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
}
