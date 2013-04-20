package moller.javapeg.program.model;

import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import moller.javapeg.program.language.Language;

public class PreviewTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    private final Language lang = Language.getInstance();

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
