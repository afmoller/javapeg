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
package moller.util;

import moller.util.gui.Update;

import javax.swing.*;

/**
 * Utility class which makes it possible to set the Default Look and Feel for an
 * application.
 *
 * @author Fredrik
 *
 */
public class DefaultLookAndFeel {

    /**
     * This method will set the default look and feel if it is not already set
     * and updates all UI:s associated with the running application.
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
