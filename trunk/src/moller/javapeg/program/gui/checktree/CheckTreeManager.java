/**
 * MySwing: Advanced Swing Utilites
 * Copyright (C) 2005  Santhosh Kumar T
 * <p/>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */

package moller.javapeg.program.gui.checktree;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JCheckBox;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * @author Santhosh Kumar T
 * @email  santhosh@in.fiorano.com
 */
public class CheckTreeManager extends MouseAdapter implements TreeSelectionListener{
    private final CheckTreeSelectionModel selectionModel;
    private final TreePathSelectable selectable;
    private final boolean smartSelection;
    protected JTree tree = new JTree();
    int hotspot = new JCheckBox().getPreferredSize().width;
    private boolean selectionEnabled;

    public CheckTreeManager(JTree tree, boolean dig, TreePathSelectable selectable, boolean smartSelection){
        this.tree = tree;
        selectionModel = new CheckTreeSelectionModel(tree.getModel(), dig);
        this.selectable = selectable;
        this.smartSelection = smartSelection;

        // note: if largemodel is not set
        // then treenodes are getting truncated.
        // need to debug further to find the problem
        if(selectable!=null)
            tree.setLargeModel(true);

        tree.setCellRenderer(new CheckTreeCellRenderer(tree.getCellRenderer(), selectionModel, selectable));
        tree.addMouseListener(this);
        selectionModel.addTreeSelectionListener(this);
    }

    public TreePathSelectable getSelectable(TreePathSelectable selectable){
        return selectable;
    }

    private void removeSelectionPaths(DefaultMutableTreeNode node) {
        selectionModel.removeSelectionPath(new TreePath(node.getPath()));

        if (smartSelection) {
            int nrOfChildren = node.getChildCount();

            if (nrOfChildren > 0) {
                for (int i = 0; i < nrOfChildren; i++) {
                    removeSelectionPaths((DefaultMutableTreeNode)node.getChildAt(i));
                }
            }
        }
    }

    private void addSelectionPaths(DefaultMutableTreeNode node) {
        TreePath nodeTreePath = new TreePath(node.getPath());

        selectionModel.addSelectionPath(nodeTreePath);

        if (smartSelection) {
            TreePath parentPath = nodeTreePath.getParentPath();

            while (parentPath != null) {
                selectionModel.addSelectionPath(parentPath);
                parentPath = parentPath.getParentPath();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me){
        if (isSelectionEnabled()) {
            TreePath path = tree.getPathForLocation(me.getX(), me.getY());
            if(path==null) {
                return;
            }
            if(me.getX()>tree.getPathBounds(path).x+hotspot) {
                return;
            }

            if(selectable!=null && !selectable.isSelectable(path)) {
                return;
            }

            boolean selected = selectionModel.isPathSelected(path, selectionModel.isDigged());
            selectionModel.removeTreeSelectionListener(this);

            try{
                DefaultMutableTreeNode clickedPath = (DefaultMutableTreeNode)path.getLastPathComponent();

                if(selected) {
                    removeSelectionPaths(clickedPath);
                } else {
                    addSelectionPaths(clickedPath);
                }
            } finally{
                selectionModel.addTreeSelectionListener(this);
                tree.treeDidChange();
            }
        }
    }

    public CheckTreeSelectionModel getSelectionModel(){
        return selectionModel;
    }

    @Override
    public void valueChanged(TreeSelectionEvent e){
        tree.treeDidChange();
    }

    public JTree getCheckedJtree() {
        return tree;
    }

    public TreeModel getTreeModel() {
        return tree.getModel();
    }

    public boolean isSelectionEnabled() {
        return selectionEnabled;
    }

    public void setSelectionEnabled(boolean selectionEnabled) {
        this.selectionEnabled = selectionEnabled;
    }
}