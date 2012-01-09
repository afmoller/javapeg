package moller.javapeg.program.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

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

	private final Vector<String> tableHeaderVector;

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

		tableHeaderVector = new Vector<String>();
		tableHeaderVector.addElement(lang.get("metadatapanel.tableheader.type"));
		tableHeaderVector.addElement(lang.get("metadatapanel.tableheader.property"));
		tableHeaderVector.addElement(lang.get("metadatapanel.tableheader.value"));

		table = new JTable(metaDataTableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setEnabled(false);

		scrollpane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		this.add(titleLabel, BorderLayout.NORTH);
		this.add(scrollpane, BorderLayout.CENTER);
	}

	public void setMetaData(File jpegFile) {
		try{
			Metadata metadata = JpegMetadataReader.readMetadata(jpegFile);

			metaDataTableModel.setColumnCount(0);
			metaDataTableModel.setRowCount(0);

			for (String column : tableHeaderVector) {
				metaDataTableModel.addColumn(column);
			}

			for (Vector<String> rowData : getImageTagsInfo(metadata)) {
				metaDataTableModel.addRow(rowData);
			}

			Table.packColumns(table, 15);
			titleLabel.setText(titleLabelDefaultText + " " + jpegFile.getName());
		} catch (JpegProcessingException jpex) {
			logger.logERROR("Could not read meta data from file: " + jpegFile.getAbsolutePath() + ". See stacktrace below for details:");
			logger.logERROR(jpex);
		}
	}

	public void clearMetaData() {
		metaDataTableModel.setColumnCount(0);
		metaDataTableModel.setRowCount(0);
		titleLabel.setText(titleLabelDefaultText);
	}

	private Vector<Vector<String>> getImageTagsInfo(Metadata metadata){

		Vector<Vector<String>> tableDataVector = new Vector<Vector<String>>();

		Iterator directories = metadata.getDirectoryIterator();

		while (directories.hasNext()) {
			Directory directory = (Directory)directories.next();
			Iterator tags = directory.getTagIterator();

			while (tags.hasNext()) {
				Vector<String> tempVector = new Vector<String>();
				Tag tag = (Tag)tags.next();
				if(tag.toString().indexOf("Unknown tag") == -1){
					tempVector.addElement(directory.getName());
					tempVector.addElement(tag.getTagName());
					try {
						tempVector.addElement(tag.getDescription());
					}
					catch (Exception ex) {
					}
					tableDataVector.addElement(tempVector);
				}
			}
			if (directory.hasErrors()) {
				Iterator errors = directory.getErrors();

				logger.logERROR("Following Exif errors where found:");
				while (errors.hasNext()) {
		            logger.logERROR("Directory error: " + errors.next());
				}
			}
		}
		return tableDataVector;
	}
}