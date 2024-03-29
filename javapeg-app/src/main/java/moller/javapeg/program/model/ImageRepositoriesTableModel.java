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

import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.javapeg.program.language.Language;
import moller.util.io.Status;

import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageRepositoriesTableModel extends AbstractTableModel {

    private static final int PATH_INDEX = 0;
    private static final int PATH_STATUS_INDEX = 1;

    /**
     *
     */
    private static final long serialVersionUID = -6495206717465789009L;

    private final Language lang = Language.getInstance();

    private final String[] columnNames;
    private final List<Object[]> rows;

    public ImageRepositoriesTableModel() {
        columnNames = new String[2];
        columnNames[PATH_INDEX] = lang.get("configviewer.tag.imageRepositoriesContent.table.column.path");
        columnNames[PATH_STATUS_INDEX] = lang.get("configviewer.tag.imageRepositoriesContent.table.column.status");

        rows = Collections.synchronizedList(new ArrayList<Object[]>());
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        return rows.get(row)[col];
    }

    public void addRow(ImageRepositoryItem imageRepositoryItem) {
        rows.add(new Object[]{imageRepositoryItem.getPath(), imageRepositoryItem.getPathStatus()});
        fireTableRowsInserted(getRowCount() - 1, getRowCount() - 1);
    }

    /**
     * Sets the {@link Status} column of an existing row.
     *
     * @param path
     *            specifies which row that shall be updated.
     * @param newStatus
     *            is the {@link Status} to set.
     */
    public void setRowStatus(File path, Status newStatus) {

        int size = getRowCount();

        for (int index = 0; index < size; index++) {
            Object[] objects = rows.get(index);
            Object pathColumn = objects[PATH_INDEX];

            if (pathColumn.equals(path)) {
                objects[PATH_STATUS_INDEX] = newStatus;
                fireTableCellUpdated(index, PATH_STATUS_INDEX);
                break;
            }
        }
    }

    public synchronized void removeRow(ImageRepositoryItem iri) {
        int index;
        for (index = 0; index < rows.size(); index++) {
            if (((File)rows.get(index)[PATH_INDEX]).equals(iri.getPath())) {
                break;
            }
        }
        rows.remove(index);
        fireTableDataChanged();
    }

    public boolean contains(ImageRepositoryItem imageRepositoryItem) {
        for (Object[] row : rows) {
            if (row[PATH_INDEX].equals(imageRepositoryItem.getPath())) {
                return true;
            }
        }
        return false;
    }

    public List<File> getPaths() {
        List<File> paths = new ArrayList<File>();

        for (Object[] row : rows) {
            paths.add((File)row[PATH_INDEX]);
        }

        return paths;
    }

    public void addAll(Set<ImageRepositoryItem> imageRepositoryItems) {
        for (ImageRepositoryItem imageRepositoryItem : imageRepositoryItems) {
            rows.add(new Object[]{imageRepositoryItem.getPath(), imageRepositoryItem.getPathStatus()});
        }
        fireTableDataChanged();
    }

    public Map<Status, AtomicInteger> getNumberOfRowsPerStatus() {
        Map<Status, AtomicInteger> statusToAmountMapping = new HashMap<Status, AtomicInteger>();

        synchronized (rows) {
            Iterator<Object[]> iterator = rows.iterator();
            while (iterator.hasNext()) {
                Object[] row = iterator.next();
                Status status = (Status)row[PATH_STATUS_INDEX];

                addToMap(status, statusToAmountMapping);
            }
        }
        return statusToAmountMapping;
    }

    private void addToMap(Status status, Map<Status, AtomicInteger> statusToAmountMapping) {
        if (statusToAmountMapping.containsKey(status)) {
            statusToAmountMapping.get(status).incrementAndGet();
        } else {
            statusToAmountMapping.put(status, new AtomicInteger(1));
        }
    }
}
