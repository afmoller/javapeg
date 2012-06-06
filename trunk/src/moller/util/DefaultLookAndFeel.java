package moller.util;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import moller.util.gui.Update;

public class DefaultLookAndFeel {

    /**
     * This method will set the default look and feel if it is not already set.
     *
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws UnsupportedLookAndFeelException
     */
    public static void set() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {

        LookAndFeel lookAndFeel = UIManager.getLookAndFeel();

        if (lookAndFeel == null) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } else if (!lookAndFeel.getClass().getCanonicalName().equals(UIManager.getSystemLookAndFeelClassName())) {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        Update.updateAllUIs();
    }
}
