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
package moller.util.gui;

import javax.swing.*;
import java.awt.*;

/**
 * This Class extends the existing JOptionPane and fills a gap that exists in
 * that class. There is no possibility to show an input dialog with custom
 * title and a default value entered in the input area in the JOptionPane class
 * so this class adds that functionality.
 *
 * @author Fredrik
 *
 */
public class CustomJOptionPane extends JOptionPane {

	private static final long serialVersionUID = 1L;

    public static String showInputDialog(Component parentComponent, Object message, String title, Object initialSelectionValue) {
        return (String)showInputDialog(parentComponent, message, title, QUESTION_MESSAGE, null, null, initialSelectionValue);
    }
}
