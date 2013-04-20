package moller.javapeg.program.gui.metadata.impl;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import moller.javapeg.program.C;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.Gap;
import moller.javapeg.program.gui.metadata.MetaDataValueSelectionDialog;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.model.SortedListModel;
import moller.util.string.StringUtil;

public class MetaDataValueSelectionDialogEqual extends JDialog implements MetaDataValueSelectionDialog {

    private static final long serialVersionUID = 1L;

    private JList<Object> values;

    private JButton okButton;
    private JButton cancelButton;

    private boolean okButtonClicked;

    private String value;
    private final String oldValue;

    private JRadioButton equal;

    private final Language lang = Language.getInstance();

    public MetaDataValueSelectionDialogEqual(String title, Set<Object> values, String oldValue, Point position) {

        this.value = "";
        this.oldValue = oldValue;

        okButtonClicked = false;

        this.setTitle(title);
        this.setModal(true);

        JPanel background = new JPanel(new GridBagLayout());

        GBHelper pos = new GBHelper();
        pos.insets = new Insets(1, 1, 1, 1);

        background.add(this.cretaMainPane(title), pos.expandW());
        background.add(this.createButtonPanel(), pos.nextRow().expandW());

        this.getContentPane().add(background);
        this.setValues(values);
        this.setOldValue(oldValue);
        this.setOkButtonState();
        this.addListeners();
        this.pack();
        this.setLocation(position);
        this.setVisible(true);
    }

    private JPanel createButtonPanel() {
        okButton = new JButton(lang.get("common.button.ok.label"));
        cancelButton = new JButton(lang.get("common.button.cancel.label"));

        JPanel background = new JPanel();

        background.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));
        background.add(okButton);
        background.add(cancelButton);

        return background;
    }

    private JPanel cretaMainPane(String title) {
        GBHelper positionMainPanel = new GBHelper();

        JPanel mainPanel = new JPanel(new GridBagLayout());

        JLabel selectionModeLabel = new JLabel(lang.get("category.metadatavalue.selection.mode"));
        selectionModeLabel.setForeground(Color.GRAY);

        JLabel valuesLabel = new JLabel(lang.get("category.metadatavalue.selection.values"));
        valuesLabel.setForeground(Color.GRAY);

        mainPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        mainPanel.add(selectionModeLabel, positionMainPanel);
        mainPanel.add(this.createOperatorsPanel(), positionMainPanel.nextRow().expandW().align(GridBagConstraints.WEST));
        mainPanel.add(new Gap(4), positionMainPanel.nextRow());
        mainPanel.add(valuesLabel, positionMainPanel.nextRow());
        mainPanel.add(this.createValuesJList(), positionMainPanel.nextRow().expandW());

        return mainPanel;
    }

    private JPanel createOperatorsPanel() {
        GBHelper positionOperatorsPanel = new GBHelper();

        JPanel backgroundPanel = new JPanel();

        JPanel operatorsPanel = new JPanel(new GridBagLayout());

        equal = new JRadioButton("=");
        equal.setName("equal");
        equal.doClick();

        operatorsPanel.add(equal, positionOperatorsPanel.nextRow());

        backgroundPanel.add(operatorsPanel);

        return backgroundPanel;
    }

    private JPanel createValuesJList() {
        GBHelper positionJListPanel = new GBHelper();
        JPanel jListPanel = new JPanel(new GridBagLayout());

        values = new JList<Object>();
        values.setVisibleRowCount(5);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(values);

        jListPanel.add(scrollPane, positionJListPanel.expandW());

        return jListPanel;
    }

    private void addListeners() {
        this.addWindowListener(new WindowDestroyer());
        okButton.addActionListener(new OkButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());
        values.addListSelectionListener(new ValuesListListener());
    }

    private void setOldValue(String oldValue) {
        if (oldValue.length() > 0) {
             if (oldValue.startsWith("=")) {
                equal.setSelected(true);
            }
            this.setValuesInJList(oldValue.substring(1));
        } else {
            equal.setSelected(true);
        }
    }

    private void setValuesInJList(String valuesStringWithoutPrefix) {
        String[] valueStrings = valuesStringWithoutPrefix.split(C.META_DATA_PARAMETER_VALUES_DELIMITER_REGEXP);

        List<Integer> indices = new ArrayList<Integer>();

        for (String valueString : valueStrings) {
            int index = this.getIndexForValue(valueString);

            if (index != -1) {
                indices.add(index);
            }
        }

        int[] indicesArray = new int[indices.size()];

        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }
        values.setSelectedIndices(indicesArray);
    }

    private int getIndexForValue(String value) {
        ListModel<Object> model = values.getModel();

        for (int index = 0; index < model.getSize(); index++) {
            if (model.getElementAt(index).toString().equals(value)) {
                return index;
            }
        }
        return -1;
    }

    public String getResult() {
        return value;
    }

    private void setOkButtonState() {
        if (oldValue.length() >  0) {
            okButton.setEnabled(true);
        } else {
            okButton.setEnabled(false);
        }
    }

    public void collectSelectedValues() {
        List<Object> selectedValues = values.getSelectedValuesList();

        int nrOfSelectedValues = selectedValues.size();

        if (nrOfSelectedValues > 0 && okButtonClicked) {
            value = "=";

            StringBuilder sb = new StringBuilder();

            for (Object selectedValue : selectedValues) {
                sb.append(selectedValue.toString());
                sb.append(C.META_DATA_PARAMETER_VALUES_DELIMITER);
            }
            value += StringUtil.removeStringFromEnd(sb.toString(), C.META_DATA_PARAMETER_VALUES_DELIMITER);
        } else {
            value = oldValue;
        }
    }

    private void setValues(Set<Object> values) {
        this.values.setModel(new SortedListModel(values));
    }

    private class WindowDestroyer extends WindowAdapter{
        @Override
        public void windowClosing (WindowEvent e){
            e.getWindow().dispose();
        }
    }

    private class OkButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            okButtonClicked = true;
            dispose();
        }
    }

    private class CancelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            value = oldValue;
            dispose();
        }
    }

    private class ValuesListListener implements ListSelectionListener {
        @SuppressWarnings("unchecked")
        public void valueChanged(ListSelectionEvent e) {
            if(!((JList<Object>)e.getSource()).getSelectionModel().isSelectionEmpty()) {
                okButton.setEnabled(true);
            }
        }
    }
}
