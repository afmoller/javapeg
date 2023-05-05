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
package moller.javapeg.program.model;

import moller.javapeg.program.language.Language;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class PreviewTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    private final Language lang = Language.getInstance();

    public void setColumns() {
        this.setColumnCount(0);
        this.addColumn(lang.get("information.panel.fileNameCurrent"));
        this.addColumn(lang.get("information.panel.fileNamePreview"));
    }

    public void setTableContent(Vector<Vector<String>> dataVector) {
        this.setRowCount(0);

        for (Vector<String> rowData : dataVector) {
            this.addRow(rowData);
        }
    }
}
