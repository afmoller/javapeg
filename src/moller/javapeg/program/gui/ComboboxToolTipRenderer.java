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

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;

/**
 * This Renderer renders a tooltip when an item is hovered in a
 * {@link JComboBox}
 *
 * @author Fredrik
 *
 */
public class ComboboxToolTipRenderer extends DefaultListCellRenderer {

    /**
     *
     */
    private static final long serialVersionUID = -8351701724030728105L;

    ArrayList<String> tooltips;

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        JComponent comp = (JComponent) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (index > -1 && value != null && tooltips != null && index <= (tooltips.size() - 1)) {
            list.setToolTipText(tooltips.get(index));
        }
        return comp;
    }

    public void setTooltips(ArrayList<String> tooltips) {
        this.tooltips = tooltips;
    }
}
