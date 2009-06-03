package moller.javapeg.program.model;
/**
* This class was created : 2009-05-27 by Fredrik Möller
* Latest changed         : 2009-05-30 by Fredrik Möller
*                        : 2009-06-01 by Fredrik Möller
*/

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import moller.javapeg.program.language.Language;

public class MetaDataTableModel extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private Language lang = Language.getInstance();

	/**
	 * The static singleton instance of this class.
	 */
	private static MetaDataTableModel instance;
	
	/**
	 * Private constructor.
	 */
	private MetaDataTableModel() {		
	}
		
	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static MetaDataTableModel getInstance() {
		if (instance != null)
			return instance;
		synchronized (MetaDataTableModel.class) {
			if (instance == null) {
				instance = new MetaDataTableModel();
			}
			return instance;
		}
	}
	
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
}