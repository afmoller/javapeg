package moller.javapeg.program.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class FileModel implements TreeModel {
	private Node root;

	public FileModel ()	{
		this (File.listRoots ());
	}

	public FileModel (Comparator<Object> comparator) {
		this (File.listRoots (), comparator);
	}

	public FileModel (File[] roots)	{
		this (roots, new Comparator<Object>() {
			public int compare (Object fileA, Object fileB) {
				return 0;
			}
		});
	}

	public FileModel (File[] roots, Comparator<Object> comparator) {
		this.root = new Node (roots, comparator);
	}

	public Object getChild (Object parent, int index) {
		return ((Node) parent).getChildren ()[index];
	}

	public int getChildCount (Object parent) {
		return ((Node) parent).getChildren ().length;
	}

	public int getIndexOfChild (Object parent, Object child) {
		Node[] children = ((Node) parent).getChildren ();
		int i = 0;
		while ((i < children.length) && children[i] != child) {
			i ++;
		}
		return (i == children.length) ? -1 : i;
	}

	public Object getRoot () {
		return root;
	}

	public boolean isLeaf (Object node) {
		return ! ((Node) node).hasChildren ();
	}

	public void addTreeModelListener (TreeModelListener listener) {
	}

	public void removeTreeModelListener (TreeModelListener listener) {
	}

	public void valueForPathChanged (TreePath path, Object newValue) {
	}

	private class Node {
		private File file;
		private Node[] children;
		private Comparator<Object> comparator;

		public Node (File[] children, Comparator<Object> comparator) {
			file = null;
			this.comparator = comparator;
			setChildren (children);
		}

		private Node (File file, Comparator<Object> comparator)	{
			this.file = file;
			this.comparator = comparator;
			children = null;
		}

		public File getFile () {
			return file;
		}

		public boolean hasChildren () {
			return ((file == null) || file.isDirectory ()) && ((children == null) || (children.length > 0));
		}

		public Node[] getChildren () {
			if (children == null) {
				setChildren (file.listFiles ());
			}
			return children;
		}

		private void setChildren (File[] files)	{
			List<File> excluded = new ArrayList<File> (files.length);
			for (int i = 0; i < files.length; i ++) {
				if (fileMustBeExcluded (files[i])) {
					excluded.add (files[i]);
				}
			}
			children = new Node[files.length - excluded.size ()];
			for (int i = 0, j = 0; i < files.length; i ++) {
				File file = files[i];
				if (! excluded.contains (file))	{
					children[j ++] = new Node (file, comparator);
				}
			}
			Arrays.sort (children, new Comparator () {
				public int compare (Object nodeA, Object nodeB)	{
					return comparator.compare (((Node) nodeA).getFile (), ((Node) nodeB).getFile ());
				}
			});
		}

		private boolean fileMustBeExcluded (File file) {	
			return ! file.exists () || ! file.canRead () || ! file.isDirectory() || (file.isDirectory() && file.getParent() != null && file.isHidden());
		}

		public String toString () {
			String name = (file == null) ? "" : file.getName ();
			return ((file == null) || ! name.equals ("")) ? name : file.getPath ();
		}
	}
}