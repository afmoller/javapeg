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
package moller.javapeg.program.gui.components;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HeadingPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JLabel label;
    private final JButton icon;

    public HeadingPanel(String title, Color titleColor, Icons iconPath) {
        this.setLayout(new GridBagLayout());

        label = new JLabel(title);
        label.setForeground(titleColor);

        icon = new JButton();
        icon.setContentAreaFilled(false);
        icon.setBorderPainted(false);
        icon.setMargin(new Insets(0, 0, 0, 0));

        if (null != iconPath) {
            this.setIcon(iconPath, null);
        }

        GBHelper position = new GBHelper();

        this.add(label, position);
        this.add(icon, position.nextCol().align(GridBagConstraints.EAST).expandW());
    }

    public void setIcon(Icons iconToSet, String iconToolTip) {
        icon.setIcon(IconLoader.getIcon(iconToSet));
        if (null != iconToolTip) {
            icon.setToolTipText(iconToolTip);
        }
    }

    public void removeIcon() {
        icon.setIcon(null);
    }

    public void setListener(ActionListener actionListener) {
        icon.addActionListener(actionListener);
    }

    /**
     * Removes any listener which are associated with the icon {@link JButton}
     * member of this class.
     */
    public void removeListeners() {
        ActionListener[] actionListeners = icon.getActionListeners();

        for (ActionListener actionListener : actionListeners) {
            icon.removeActionListener(actionListener);
        }
    }
}
