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
package moller.util.datatype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class is an container for storing and retrieving One object of type
 * <code>E</code>.
 *
 * @author Fredrik
 *
 * @param <E>
 */
public class OneSizedList<E> {

    private final List<E> value;

    public OneSizedList() {
        value = Collections.synchronizedList(new ArrayList<E>(1));
    }

    /**
     * Set an object of type <code>E</code> to this list. If there already is
     * an object set to this list then that object is overwritten with the new
     * object.
     *
     * @param e
     *            is the object of type <code>E</code> which is to store in this
     *            list.
     */
    public void set(E e) {
        if (value.isEmpty()) {
            value.add(e);
        }
        value.set(0, e);
    }

    /**
     * Retrieves the object of type <code>E</code> which is stored in this list,
     * or null if no object is stored. After retrieving the stored element, the
     * list will be empty.
     *
     * @return the stored object of type <code>E</code> or null if no object is
     *         stored.
     */
    public E get() {
        if (value.size() == 0) {
            return null;
        }
        return value.remove(0);
    }
}
