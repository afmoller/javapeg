package moller.javapeg.program.gui;
/**
* This class was created : 2004-03-13 av Fredrik Möller
* Latest changed         : 2004-03-15 av Fredrik Möller
*                        : 2004-04-23 av Fredrik Möller
*                        : 2009-06-04 av Fredrik Möller
*                        : 2009-06-06 av Fredrik Möller
*                        : 2009-07-07 av Fredrik Möller
*/

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
import moller.util.gui.Table;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class MetaDataPanel extends JPanel {
		
	private static final long serialVersionUID = 1L;
	
	private JScrollPane scrollpane;
	private JTable table;
	
	private Vector<String> tableHeaderVector;
		
	private DefaultTableModel metaDataTableModel;
	
	private static Language lang;
	
	private JLabel titleLabel;
	private String titleLabelDefaultText;
	
	public MetaDataPanel() {
		
		lang = Language.getInstance();
		
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
		} catch (JpegProcessingException jpe) {
			System.err.println("error 1a");
			jpe.printStackTrace();
		}
	}
	
	public void clearMetaData() {
		metaDataTableModel.setColumnCount(0);
		metaDataTableModel.setRowCount(0);
		titleLabel.setText(titleLabelDefaultText);
	}

	@SuppressWarnings("unchecked")
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

				while (errors.hasNext()) {
					System.out.println("ERROR: " + errors.next());
				}
			}
		}
		return tableDataVector;
	}
}