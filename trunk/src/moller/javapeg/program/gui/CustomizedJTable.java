package moller.javapeg.program.gui;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CustomizedJTable extends JTable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public CustomizedJTable(DefaultTableModel metaDataTableModel) {
        super(metaDataTableModel);
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return getPreferredSize().width < getParent().getWidth();
    }
}
