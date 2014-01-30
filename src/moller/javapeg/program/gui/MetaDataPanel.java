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

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.gui.Table;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class MetaDataPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JScrollPane scrollpane;
    private final JTable table;

    private final DefaultTableModel metaDataTableModel;

    private static Language lang;

    private static Logger logger;

    private final JLabel titleLabel;
    private final String titleLabelDefaultText;

    public MetaDataPanel() {

        lang = Language.getInstance();
        logger = Logger.getInstance();

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));

        titleLabelDefaultText = lang.get("metadatapanel.titleDefaultText");

        titleLabel = new JLabel(titleLabelDefaultText);
        titleLabel.setForeground(Color.GRAY);

        metaDataTableModel = new DefaultTableModel();
        metaDataTableModel.addColumn(lang.get("metadatapanel.tableheader.type"));
        metaDataTableModel.addColumn(lang.get("metadatapanel.tableheader.property"));
        metaDataTableModel.addColumn(lang.get("metadatapanel.tableheader.value"));

        TableRowSorter<TableModel> metaDataTableModelSorter = new TableRowSorter<TableModel>(metaDataTableModel);

        table = new JTable(metaDataTableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setRowSorter(metaDataTableModelSorter);
        table.setEnabled(false);

        scrollpane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.add(titleLabel, BorderLayout.NORTH);
        this.add(scrollpane, BorderLayout.CENTER);
    }

    public void setMetaData(File jpegFile) {
        try{
            Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);

            metaDataTableModel.setRowCount(0);

            for (Vector<String> rowData : getImageTagsInfo(metadata)) {
                metaDataTableModel.addRow(rowData);
            }

            Table.packColumns(table, 15);
            titleLabel.setText(titleLabelDefaultText + " " + jpegFile.getName());
        } catch (JpegProcessingException jpex) {
            logger.logERROR("Could not read meta data from file: " + jpegFile.getAbsolutePath() + ". See stacktrace below for details:");
            logger.logERROR(jpex);
        } catch (IOException iox) {
            logger.logERROR("Could not read meta data from file: " + jpegFile.getAbsolutePath() + ". See stacktrace below for details:");
            logger.logERROR(iox);        }
    }

    public void clearMetaData() {
        metaDataTableModel.setRowCount(0);
        Table.packColumns(table, 15);
        titleLabel.setText(titleLabelDefaultText);
    }

    private Vector<Vector<String>> getImageTagsInfo(Metadata metadata){

        Vector<Vector<String>> tableDataVector = new Vector<Vector<String>>();

        for (Directory directory : metadata.getDirectories()) {

            Collection<Tag> tags = directory.getTags();

            for (Tag tag : tags) {

                Vector<String> tempVector = new Vector<String>();

                if(tag.toString().indexOf("Unknown tag") == -1){
                    tempVector.addElement(directory.getName());
                    tempVector.addElement(tag.getTagName());
                    try {
                        tempVector.addElement(tag.getDescription());
                    }
                    catch (Exception ex) {
                        logger.logERROR("Could not get tag description: ");
                        logger.logERROR(ex);
                    }
                    tableDataVector.addElement(tempVector);
                }
            }
            if (directory.hasErrors()) {

                logger.logERROR("Following Exif errors where found:");

                for (String error : directory.getErrors()) {
                    logger.logERROR("Directory error: " + error);
                }
            }
        }
        return tableDataVector;
    }
}
