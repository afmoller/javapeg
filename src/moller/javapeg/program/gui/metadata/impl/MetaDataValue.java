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
package moller.javapeg.program.gui.metadata.impl;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class MetaDataValue extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextField textField;

    private final JButton clearTextFieldButton;

    public MetaDataValue(String name) {
        super();

        textField = new JTextField();
        textField.setEditable(false);
        textField.setName(name);
        textField.setMinimumSize(new Dimension(50, textField.getSize().height));
        textField.setPreferredSize(new Dimension(200, textField.getSize().height));

        Color c = UIManager.getDefaults().getColor("TextField.background");
        if (null != c) {
            Color color = new Color(c.getRed(), c.getGreen(), c.getBlue());
            textField.setBackground(color);
        }

        clearTextFieldButton = new JButton(IconLoader.getIcon(Icons.REMOVE));
        clearTextFieldButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                clearValue();
            }
        });

        this.setLayout(new GridBagLayout());

        GBHelper posBackground = new GBHelper();

        this.add(textField, posBackground.expandW());
        this.add(clearTextFieldButton, posBackground.nextCol());
    }

    public String getValue() {
        return textField.getText();
    }

    public void clearValue() {
        textField.setText("");
        textField.setToolTipText("");
    }

    public void setMouseListener(MouseListener mouseListener) {
        textField.addMouseListener(mouseListener);
    }

    @Override
    public void setEnabled(boolean enabled) {
        textField.setEnabled(enabled);

    }
}
