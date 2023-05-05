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

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

public class CustomizedJTable extends JTable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CustomizedJTable(AbstractTableModel metaDataTableModel) {
        super(metaDataTableModel);
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return getPreferredSize().width < getParent().getWidth();
    }
}
