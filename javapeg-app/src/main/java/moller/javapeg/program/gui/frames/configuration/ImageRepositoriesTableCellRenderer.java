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
package moller.javapeg.program.gui.frames.configuration;

import moller.javapeg.program.language.Language;
import moller.util.io.Status;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ImageRepositoriesTableCellRenderer extends JLabel implements TableCellRenderer {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final Language lang = Language.getInstance();

    public ImageRepositoriesTableCellRenderer() {
        this.setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Status directoryStatus = (Status)table.getModel().getValueAt(table.convertRowIndexToModel(row), 1);

        if (table.convertColumnIndexToModel(column) == 1) {
            this.setText(lang.get(directoryStatus.getTextKey()));
        } else {
            this.setText(value.toString());
        }

        this.setBackground(directoryStatus.getBakgroundColor());
        this.setToolTipText(lang.get(directoryStatus.getToolTipTextKey()));

        if(isSelected) {
            this.setBackground(new Color(127,127,127));
        }
        return this;
    }
}
