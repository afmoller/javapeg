package moller.javapeg.program.model;
/**
* This class was created : 2009-06-01 by Fredrik Möller
* Latest changed         : 
*/

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import moller.javapeg.program.language.Language;

public class PreviewTableModel extends DefaultTableModel {
	
	private static final long serialVersionUID = 1L;
	
	private Language lang = Language.getInstance();

	/**
	 * The static singleton instance of this class.
	 */
	private static PreviewTableModel instance;
	
	/**
	 * Private constructor.
	 */
	private PreviewTableModel() {	
	}
		
	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static PreviewTableModel getInstance() {
		if (instance != null)
			return instance;
		synchronized (PreviewTableModel.class) {
			if (instance == null) {
				instance = new PreviewTableModel();
			}
			return instance;
		}
	}
	
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