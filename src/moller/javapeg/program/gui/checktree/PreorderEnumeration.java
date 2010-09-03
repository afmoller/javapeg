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

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Stack;

/**
 * @author Santhosh Kumar T
 * @email  santhosh@in.fiorano.com
 */
public class PreorderEnumeration implements Enumeration<TreePath> {
    private TreeModel model;
    protected Stack<Enumeration<TreePath>> stack = new Stack<Enumeration<TreePath>>();

    public PreorderEnumeration(TreePath path, TreeModel model){
        this(Collections.enumeration(Collections.singletonList(path)), model);
    }

    public PreorderEnumeration(Enumeration<TreePath> enumer, TreeModel model){
        this.model = model;
        stack.push(enumer);
    }

    public boolean hasMoreElements() {
        return (!stack.empty() &&
            ((Enumeration<TreePath>)stack.peek()).hasMoreElements());
    }

    public TreePath nextElement() {
        Enumeration<TreePath> enumer = (Enumeration<TreePath>)stack.peek();
        TreePath path = (TreePath)enumer.nextElement();

        if(!enumer.hasMoreElements())
            stack.pop();

        if(model.getChildCount(path.getLastPathComponent())>0)
            stack.push(new ChildrenEnumeration(path, model));
        return path;
    }
}
