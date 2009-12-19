package moller.javapeg.program.model;
/**
* This class was created : 2009-05-27 by Fredrik Möller
* Latest changed         : 2009-05-30 by Fredrik Möller
*                        : 2009-06-01 by Fredrik Möller
*                        : 2009-09-13 by Fredrik Möller
*                        : 2009-12-17 by Fredrik Möller
*/

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import moller.javapeg.program.language.Language;
import moller.javapeg.program.metadata.MetaData;

public class MetaDataTableModel extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private Language lang = Language.getInstance();
	
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
		
		Object [] meta = new Object[9];
		
		meta[0] = metaData.getFileName();
		meta[1] = metaData.getExifDate();
		meta[2] = metaData.getExifTime();
		meta[3] = metaData.getExifCameraModel();
		meta[4] = metaData.getExifShutterSpeed();
		meta[5] = metaData.getExifISOValue();
		meta[6] = metaData.getExifPictureWidth();
		meta[7] = metaData.getExifPictureHeight();
		meta[8] = metaData.getExifApertureValue();
				
		this.addRow(meta);
	}
}