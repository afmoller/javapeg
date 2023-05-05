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
package moller.javapeg.program.model.iso;

import moller.javapeg.program.enumerations.filter.IFilterMask;
import moller.javapeg.program.language.Language;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilteringTableModel<F extends IFilterMask> extends AbstractTableModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static final int CAMERA_MODEL = 0;
    private static final int ISO_FILTER_PATTERN = 1;

    private final String[] columnNames;
    private final List<CameraAndFilterPair<F>> rows;

    private final Language lang = Language.getInstance();

    public FilteringTableModel() {
        columnNames = new String[2];
        columnNames[CAMERA_MODEL] = lang.get("configviewer.metadata.filtertable.header.cameramodel");
        columnNames[ISO_FILTER_PATTERN] = lang.get("configviewer.metadata.filtertable.header.filter");

        rows = Collections.synchronizedList(new ArrayList<CameraAndFilterPair<F>>());
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex - 1  > rows.size()) {
            throw new IllegalArgumentException("rowIndex: " + rowIndex + " is outside of the range 0 - " + (rows.size() - 1));
        }

        if (columnIndex > 1) {
            throw new IllegalArgumentException("columnIndex: " + columnIndex + " is outside of the range 0 - 1");
        }

        CameraAndFilterPair<F> cameraAndISOFilterPair = rows.get(rowIndex);

        return columnIndex == 0 ? cameraAndISOFilterPair.getCameraModel() : cameraAndISOFilterPair.getFilterMask();
    }

    public synchronized void addRow(CameraAndFilterPair<F> cameraAndISOFilterPair) {
        if (rows.indexOf(cameraAndISOFilterPair) > -1) {
            rows.get(rows.indexOf(cameraAndISOFilterPair)).setFilterMask(cameraAndISOFilterPair.getFilterMask());
        } else {
            rows.add(cameraAndISOFilterPair);
        }
        fireTableDataChanged();
    }

    public synchronized void removeRow(CameraAndFilterPair<F> cameraAndISOFilterPair) {
        rows.remove(rows.indexOf(cameraAndISOFilterPair));
        fireTableDataChanged();
    }

    public CameraAndFilterPair<F> getRow(int rowIndex) {
        if (rowIndex - 1  > rows.size()) {
            throw new IllegalArgumentException("rowIndex: " + rowIndex + " is outside of the range 0 - " + (rows.size() - 1));
        }

        return rows.get(rowIndex);
    }
}
