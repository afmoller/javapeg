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

import javax.swing.tree.DefaultTreeSelectionModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.util.*;

/**
 * @author Santhosh Kumar T
 * @email  santhosh@in.fiorano.com
 */
public class CheckTreeSelectionModel extends DefaultTreeSelectionModel{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final TreeModel model;
    private boolean dig = true;

    public CheckTreeSelectionModel(TreeModel model, boolean dig){
        this.model = model;
        this.dig = dig;
        setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
    }

    public boolean isDigged(){
        return dig;
    }

    // tests whether there is any unselected node in the subtree of given path
    public boolean isPartiallySelected(TreePath path){
        if(isPathSelected(path, true))
            return false;
        TreePath[] selectionPaths = getSelectionPaths();
        if(selectionPaths==null)
            return false;
        for(int j = 0; j<selectionPaths.length; j++){
            if(isDescendant(selectionPaths[j], path))
                return true;
        }
        return false;
    }

    // tells whether given path is selected.
    // if dig is true, then a path is assumed to be selected, if
    // one of its ancestor is selected.
    public boolean isPathSelected(TreePath path, boolean dig){
        if(!dig)
            return super.isPathSelected(path);
        while(path!=null && !super.isPathSelected(path))
            path = path.getParentPath();
        return path!=null;
    }

    // is path1 descendant of path2
    private boolean isDescendant(TreePath path1, TreePath path2){
        Object obj1[] = path1.getPath();
        Object obj2[] = path2.getPath();
        for(int i = 0; i<obj2.length; i++){
            if(obj1[i]!=obj2[i])
                return false;
        }
        return true;
    }

    @Override
    public void setSelectionPaths(TreePath[] paths){
        if(dig)
            throw new UnsupportedOperationException("not implemented yet!!!");
        else
            super.setSelectionPaths(paths);
    }

    @Override
    public void addSelectionPaths(TreePath[] paths){
        if(!dig){
            super.addSelectionPaths(paths);
            return;
        }

        // unselect all descendants of paths[]
        for(int i = 0; i<paths.length; i++){
            TreePath path = paths[i];
            TreePath[] selectionPaths = getSelectionPaths();
            if(selectionPaths==null)
                break;
            ArrayList<TreePath> toBeRemoved = new ArrayList<TreePath>();
            for(int j = 0; j<selectionPaths.length; j++){
                if(isDescendant(selectionPaths[j], path))
                    toBeRemoved.add(selectionPaths[j]);
            }
            super.removeSelectionPaths(toBeRemoved.toArray(new TreePath[0]));
        }

        // if all siblings are selected then unselect them and select parent recursively
        // otherwize just select that path.
        for(int i = 0; i<paths.length; i++){
            TreePath path = paths[i];
            TreePath temp = null;
            while(areSiblingsSelected(path)){
                temp = path;
                if(path.getParentPath()==null)
                    break;
                path = path.getParentPath();
            }
            if(temp!=null){
                if(temp.getParentPath()!=null)
                    addSelectionPath(temp.getParentPath());
                else{
                    if(!isSelectionEmpty())
                        removeSelectionPaths(getSelectionPaths());
                    super.addSelectionPaths(new TreePath[]{temp});
                }
            }else
                super.addSelectionPaths(new TreePath[]{ path});
        }
    }

    // tells whether all siblings of given path are selected.
    private boolean areSiblingsSelected(TreePath path){
        TreePath parent = path.getParentPath();
        if(parent==null)
            return true;
        Object node = path.getLastPathComponent();
        Object parentNode = parent.getLastPathComponent();

        int childCount = model.getChildCount(parentNode);
        for(int i = 0; i<childCount; i++){
            Object childNode = model.getChild(parentNode, i);
            if(childNode==node)
                continue;
            if(!isPathSelected(parent.pathByAddingChild(childNode)))
                return false;
        }
        return true;
    }

    @Override
    public void removeSelectionPaths(TreePath[] paths){
        if(!dig){
            super.removeSelectionPaths(paths);
            return;
        }

        for(int i = 0; i<paths.length; i++){
            TreePath path = paths[i];
            if(path.getPathCount()==1)
                super.removeSelectionPaths(new TreePath[]{ path});
            else
                toggleRemoveSelection(path);
        }
    }

    // if any ancestor node of given path is selected then unselect it
    //  and selection all its descendants except given path and descendants.
    // otherwise just unselect the given path
    private void toggleRemoveSelection(TreePath path){
        Stack<TreePath> stack = new Stack<TreePath>();
        TreePath parent = path.getParentPath();
        while(parent!=null && !isPathSelected(parent)){
            stack.push(parent);
            parent = parent.getParentPath();
        }
        if(parent!=null)
            stack.push(parent);
        else{
            super.removeSelectionPaths(new TreePath[]{path});
            return;
        }

        while(!stack.isEmpty()){
            TreePath temp = stack.pop();
            TreePath peekPath = stack.isEmpty() ? path : (TreePath)stack.peek();
            Object node = temp.getLastPathComponent();
            Object peekNode = peekPath.getLastPathComponent();
            int childCount = model.getChildCount(node);
            for(int i = 0; i<childCount; i++){
                Object childNode = model.getChild(node, i);
                if(childNode!=peekNode)
                    super.addSelectionPaths(new TreePath[]{temp.pathByAddingChild(childNode)});
            }
        }
        super.removeSelectionPaths(new TreePath[]{parent});
    }

    @SuppressWarnings("unchecked")
    public Enumeration<TreePath> getAllSelectedPaths(){
        TreePath[] treePaths = getSelectionPaths();
        if(treePaths==null)
            return Collections.enumeration(Collections.EMPTY_LIST);
        Enumeration<TreePath> enumer = Collections.enumeration(Arrays.asList(treePaths));
        if(dig)
            enumer = new PreorderEnumeration(enumer, model);
        return enumer;
    }
}