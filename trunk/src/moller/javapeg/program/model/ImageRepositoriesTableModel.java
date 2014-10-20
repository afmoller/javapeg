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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.table.AbstractTableModel;

import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.javapeg.program.language.Language;
import moller.util.io.Status;

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
        fireTableDataChanged();
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
