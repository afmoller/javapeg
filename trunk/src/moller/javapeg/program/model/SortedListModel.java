package moller.javapeg.program.model;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

public class SortedListModel extends AbstractListModel {

	private static final long serialVersionUID = 1L;

	private SortedSet<Object> model;

	public SortedListModel() {
		model = new TreeSet<Object>();
	}
	
	public SortedListModel(Set<Object> values) {
		model = new TreeSet<Object>();
		model.addAll(values);
		fireContentsChanged(this, 0, getSize());
	}

	public int getSize() {
		return model.size();
	}

	public Object getElementAt(int index) {
		return model.toArray()[index];
	}

	public void add(Object element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}

	public void addAll(Set<Object> elements) {
		model.addAll(elements);
		fireContentsChanged(this, 0, getSize());
	}

	public void clear() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
	}

	public boolean contains(Object element) {
		return model.contains(element);
	}

	public Object firstElement() {
		return model.first();
	}

	public Iterator<Object> iterator() {
		return model.iterator();
	}

	public Object lastElement() {
		return model.last();
	}

	public boolean removeElement(Object element) {
		boolean removed = model.remove(element);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		return removed;   
	}
}