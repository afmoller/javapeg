package moller.javapeg.program.gui;

import javax.swing.JCheckBox;

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
