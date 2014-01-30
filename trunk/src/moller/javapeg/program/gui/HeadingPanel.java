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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.logger.Logger;
import moller.util.image.ImageUtil;

public class HeadingPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JLabel label;
    private final JButton icon;

    public HeadingPanel(String title, Color titleColor, String iconPath) {
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

    public void setIcon(String iconPath, String iconToolTip) {
        try {
            icon.setIcon(ImageUtil.getIcon(StartJavaPEG.class.getResourceAsStream(iconPath), true));
            if (null != iconToolTip) {
                icon.setToolTipText(iconToolTip);
            }
        } catch (IOException iox) {
            Logger logger = Logger.getInstance();
            logger.logERROR("Could not set image: " + iconPath + " as icon. See stacktrace below for details");
            logger.logERROR(iox);
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
