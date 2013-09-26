package moller.javapeg.program.gui;

import javax.swing.JCheckBox;

public class JCheckBoxWithUserObject<T extends Object> extends JCheckBox {

    private static final long serialVersionUID = 1L;

    private T userObject;

    public JCheckBoxWithUserObject(T userObject) {
        this.userObject = userObject;
    }

    public T getUserObject() {
        return userObject;
    }

    public void setUserObject(T userObject) {
        this.userObject = userObject;
    }
}
