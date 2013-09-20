package moller.javapeg.program.model;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

public class SortedListModel<T extends Object> extends AbstractListModel<T> {

    private static final long serialVersionUID = 1L;

    private final SortedSet<T> model;

    public SortedListModel() {
        model = new TreeSet<T>();
    }

    public SortedListModel(Set<T> values) {
        model = new TreeSet<T>();
        if (model.addAll(values)) {
            fireContentsChanged(this, 0, getSize());
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
            fireContentsChanged(this, 0, getSize());
        }
    }

    public void addAll(Set<T> elements) {
        if (model.addAll(elements)) {
            fireContentsChanged(this, 0, getSize());
        }
    }

    public void clear() {
        model.clear();
        fireContentsChanged(this, 0, getSize());
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
            fireContentsChanged(this, 0, getSize());
        }
        return removed;
    }
}