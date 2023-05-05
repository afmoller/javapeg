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
package moller.javapeg.program.gui;

import javax.swing.*;

/**
 * This class is an extension of the {@link JCheckBox} class. The extension
 * consists of the possibility to store any object that extends {@link Object}
 * as a user object.
 *
 * @author Fredrik
 *
 * @param <T>
 *            specifies the type of the object that this extended version of a
 *            {@link JCheckBox} can store.
 */
public class JCheckBoxWithUserObject<T extends Object> extends JCheckBox {

    private static final long serialVersionUID = 1L;

    private T userObject;

    /**
     * Constructor.
     *
     * @param userObject
     *            is the object that shall be stored as user object.
     */
    public JCheckBoxWithUserObject(T userObject) {
        this.userObject = userObject;
    }

    /**
     * Return the user object
     *
     * @return the object that has been stored as user object.
     */
    public T getUserObject() {
        return userObject;
    }

    /**
     * Set a user object.
     *
     * @param userObject
     *            is the object to set as user object.
     */
    public void setUserObject(T userObject) {
        this.userObject = userObject;
    }
}
