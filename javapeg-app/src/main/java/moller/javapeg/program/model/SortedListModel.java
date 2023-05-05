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

import javax.swing.*;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedListModel<T extends Object> extends AbstractListModel<T> {

    private static final long serialVersionUID = 1L;

    private final SortedSet<T> model;

    public SortedListModel() {
        model = new TreeSet<T>();
    }

    public SortedListModel(Set<T> values) {
        model = new TreeSet<T>();
        if (model.addAll(values)) {
            fireIntervalAdded(this, 0, getSize());
        }
    }

    @Override
    public int getSize() {
        return model.size();
    }

    @Override
    public T getElementAt(int index) {
        Iterator<T> iterator = model.iterator();

        int currentIndex = 0;
        while (iterator.hasNext()) {
            T element = iterator.next();
            if (currentIndex == index) {
                return element;
            }
            currentIndex++;
        }
        return null;
    }

    public SortedSet<T> getModel() {
        return model;
    }

    /**
     * Add an element of type T to the model, if it does not already exists in
     * the model.
     *
     * @param element
     *            is the object to add to the model.
     */
    public void add(T element) {
        if (model.add(element)) {
            fireIntervalAdded(this, 0, getSize());
        }
    }

    public void addAll(Set<T> elements) {
        if (model.addAll(elements)) {
            fireIntervalAdded(this, 0, getSize());
        }
    }

    public void clear() {
        model.clear();
        fireIntervalRemoved(this, 0, getSize());
    }

    public boolean contains(T element) {
        return model.contains(element);
    }

    public T firstElement() {
        return model.first();
    }

    public Iterator<T> iterator() {
        return model.iterator();
    }

    public T lastElement() {
        return model.last();
    }

    public boolean removeElement(T element) {
        boolean removed = model.remove(element);
        if (removed) {
            fireIntervalRemoved(this, 0, getSize());
        }
        return removed;
    }
}