package moller.javapeg.program.gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.util.ArrayList;
import java.util.List;

public class CategoriesTransferHandler extends TransferHandler {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    DataFlavor nodesFlavor;
    DataFlavor[] flavors = new DataFlavor[1];
    DefaultMutableTreeNode[] nodesToRemove;

    public CategoriesTransferHandler() {
        try {
            String mimeType = DataFlavor.javaJVMLocalObjectMimeType
                    + ";class=\""
                    + DefaultMutableTreeNode[].class.getName()
                    + "\"";
            nodesFlavor = new DataFlavor(mimeType);
            flavors[0] = nodesFlavor;
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound: " + e.getMessage());
        }
    }

    @Override
    public boolean canImport(TransferSupport support) {
        if (!support.isDrop()) {
            return false;
        }
        support.setShowDropLocation(true);
        if (!support.isDataFlavorSupported(nodesFlavor)) {
            return false;
        }

        // Do not allow a drop on the drag source selections.
        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        JTree tree = (JTree) support.getComponent();
        int dropRow = tree.getRowForPath(dl.getPath());
        int[] selRows = tree.getSelectionRows();
        for (int i = 0; i < selRows.length; i++) {
            if (selRows[i] == dropRow) {
                return false;
            }
        }
        // Do not allow MOVE-action drops if a non-leaf node is
        // selected unless all of its children are also selected.
        int action = support.getDropAction();
        if (action == MOVE) {
            return haveCompleteNode(tree);
        }
        // Do not allow a non-leaf node to be copied to a level
        // which is less than its source level.
        TreePath dest = dl.getPath();
        DefaultMutableTreeNode target = (DefaultMutableTreeNode) dest.getLastPathComponent();
        TreePath path = tree.getPathForRow(selRows[0]);
        DefaultMutableTreeNode firstNode = (DefaultMutableTreeNode) path.getLastPathComponent();

        if (firstNode.getChildCount() > 0 && target.getLevel() < firstNode.getLevel()) {
            return false;
        }
        return true;
    }

    private boolean haveCompleteNode(JTree tree) {
        TreePath[] selectionPaths = tree.getSelectionModel().getSelectionPaths();

        TreePath path = selectionPaths[0];
        DefaultMutableTreeNode first = (DefaultMutableTreeNode) selectionPaths[0].getLastPathComponent();
        int childCount = first.getChildCount();

        // first has children and no children are selected.
        if (childCount > 0 && selectionPaths.length == 1) {
            return false;
        }

        // first may have children.
        for (int i = 1; i < selectionPaths.length; i++) {
            path = selectionPaths[i];
            DefaultMutableTreeNode next = (DefaultMutableTreeNode) path.getLastPathComponent();
            if (first.isNodeChild(next)) {
                // Found a child of first.
                if (childCount > selectionPaths.length - 1) {
                    // Not all children of first are selected.
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JTree tree = (JTree) c;
        TreePath[] paths = tree.getSelectionPaths();
        if (paths != null) {
            List<DefaultMutableTreeNode> toRemove = new ArrayList<DefaultMutableTreeNode>();
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) paths[0].getLastPathComponent();

            DefaultMutableTreeNode subTreeRoot = new DefaultMutableTreeNode(node.getUserObject());

            copySubTree(subTreeRoot, node);

            toRemove.add(node);
            for (int i = 1; i < paths.length; i++) {
                DefaultMutableTreeNode next = (DefaultMutableTreeNode) paths[i].getLastPathComponent();
                toRemove.add(next);
            }

            DefaultMutableTreeNode[] nodes = new DefaultMutableTreeNode[1];
            nodes[0] = subTreeRoot;
            nodesToRemove = toRemove.toArray(new DefaultMutableTreeNode[toRemove.size()]);
            return new NodesTransferable(nodes);
        }
        return null;
    }

    public void copySubTree(DefaultMutableTreeNode subRoot, DefaultMutableTreeNode sourceTree) {
        for (int i = 0; i < sourceTree.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode)sourceTree.getChildAt(i);
            DefaultMutableTreeNode clone = copyNode(child);
            subRoot.add(clone);
            copySubTree(clone, child);
        }
    }

    /** Defensive copy used in createTransferable. */
    private DefaultMutableTreeNode copyNode(TreeNode node) {
        return new DefaultMutableTreeNode(((DefaultMutableTreeNode)node).getUserObject());
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        if ((action & MOVE) == MOVE) {
            JTree tree = (JTree) source;
            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            // Remove nodes saved in nodesToRemove in createTransferable.
            for (int i = 0; i < nodesToRemove.length; i++) {
                model.removeNodeFromParent(nodesToRemove[i]);
            }
        }
    }

    @Override
    public int getSourceActions(JComponent c) {
        return COPY_OR_MOVE;
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) {
            return false;
        }
        // Extract transfer data.
        DefaultMutableTreeNode[] nodes = null;
        try {
            Transferable t = support.getTransferable();
            nodes = (DefaultMutableTreeNode[]) t.getTransferData(nodesFlavor);
        } catch (UnsupportedFlavorException ufe) {
            System.out.println("UnsupportedFlavor: " + ufe.getMessage());
        } catch (java.io.IOException ioe) {
            System.out.println("I/O error: " + ioe.getMessage());
        }
        // Get drop location info.
        JTree.DropLocation dl = (JTree.DropLocation) support.getDropLocation();
        int childIndex = dl.getChildIndex();
        TreePath dest = dl.getPath();
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) dest.getLastPathComponent();
        JTree tree = (JTree) support.getComponent();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        // Configure for drop mode.
        int index = childIndex; // DropMode.INSERT
        if (childIndex == -1) { // DropMode.ON
            index = parent.getChildCount();
        }
        // Add data to model.
        for (int i = 0; i < nodes.length; i++) {
            model.insertNodeInto(nodes[i], parent, index++);
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getName();
    }

    public class NodesTransferable implements Transferable {
        DefaultMutableTreeNode[] nodes;

        public NodesTransferable(DefaultMutableTreeNode[] nodes) {
            this.nodes = nodes;
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) {
                throw new UnsupportedFlavorException(flavor);
            }
            return nodes;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return flavors;
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return nodesFlavor.equals(flavor);
        }
    }
}
