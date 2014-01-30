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

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import moller.javapeg.program.language.Language;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.metadata.MetaDataUtil;

public class MetaDataTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    private final Language lang = Language.getInstance();

    public void setColumns() {
        this.setColumnCount(0);
        this.addColumn(lang.get("information.panel.columnNameFileName"));
        this.addColumn(lang.get("variable.pictureDate"));
        this.addColumn(lang.get("variable.pictureTime"));
        this.addColumn(lang.get("variable.cameraModel"));
        this.addColumn(lang.get("variable.shutterSpeed"));
        this.addColumn(lang.get("variable.isoValue"));
        this.addColumn(lang.get("variable.pictureWidth"));
        this.addColumn(lang.get("variable.pictureHeight"));
        this.addColumn(lang.get("variable.apertureValue"));
    }

    public void setTableContent(Vector<Vector<String>> dataVector) {
        this.setRowCount(0);

        for (Vector<String> rowData : dataVector) {
            this.addRow(rowData);
        }
    }

    public void addTableRow(MetaData metaData) {

        String noValue = lang.get("common.missing.value");

        Object[] meta = new Object[9];

        meta[0] = MetaDataUtil.hasValue(metaData.getFileName()) ? metaData.getFileName() : noValue;
        meta[1] = MetaDataUtil.hasValue(metaData.getExifDateAsString()) ? metaData.getExifDateAsString() : noValue;
        meta[2] = MetaDataUtil.hasValue(metaData.getExifTimeAsString()) ? metaData.getExifTimeAsString() : noValue;
        meta[3] = MetaDataUtil.hasValue(metaData.getExifCameraModel()) ? metaData.getExifCameraModel() : noValue;
        meta[4] = MetaDataUtil.hasValue(metaData.getExifExposureTime()) ? metaData.getExifExposureTime() : noValue;
        meta[5] = MetaDataUtil.hasValue(metaData.getExifISOValue()) ? metaData.getExifISOValue() : noValue;
        meta[6] = MetaDataUtil.hasValue(metaData.getExifPictureWidth()) ? metaData.getExifPictureWidth() : noValue;
        meta[7] = MetaDataUtil.hasValue(metaData.getExifPictureHeight()) ? metaData.getExifPictureHeight() : noValue;
        meta[8] = MetaDataUtil.hasValue(metaData.getExifFNumber()) ? metaData.getExifFNumber() : noValue;

        this.addRow(meta);
    }
}
