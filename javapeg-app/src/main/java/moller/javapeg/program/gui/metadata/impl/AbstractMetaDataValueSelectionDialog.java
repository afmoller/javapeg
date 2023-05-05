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
package moller.javapeg.program.gui.metadata.impl;

import moller.javapeg.program.C;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.gui.metadata.MetaDataValueSelectionDialog;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.model.SortedListModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class AbstractMetaDataValueSelectionDialog extends JDialog implements MetaDataValueSelectionDialog {

    private static final long serialVersionUID = 1L;

    private JList<Object> values;

    private JButton okButton;
    private JButton cancelButton;

    private boolean okButtonClicked;

    private String value;
    private final String oldValue;

    private final Language lang = Language.getInstance();

    public AbstractMetaDataValueSelectionDialog(String title, Set<Object> values, String oldValue, Point position) {

        this.setValue("");
        this.oldValue = oldValue;

        setOkButtonClicked(false);

        this.setTitle(title);
        this.setModal(true);

        JPanel background = new JPanel(new GridBagLayout());

        GBHelper pos = new GBHelper();
        pos.insets = new Insets(1, 1, 1, 1);

        background.add(this.cretaMainPane(title), pos.expandW().expandH());
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

    @Override
    public abstract void collectSelectedValues();

    protected abstract void setOldValue(String oldValue);

    protected abstract JPanel createOperatorsPanel();

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
        mainPanel.add(Box.createVerticalStrut(4), positionMainPanel.nextRow());
        mainPanel.add(valuesLabel, positionMainPanel.nextRow());
        mainPanel.add(this.createValuesJList(), positionMainPanel.nextRow().expandW().expandH());

        return mainPanel;
    }

    private JPanel createValuesJList() {
        GBHelper positionJListPanel = new GBHelper();
        JPanel jListPanel = new JPanel(new GridBagLayout());

        values = new JList<Object>();
        values.setVisibleRowCount(5);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.getViewport().add(values);

        jListPanel.add(scrollPane, positionJListPanel.expandW().expandH());

        return jListPanel;
    }

    protected void addListeners() {
        this.addWindowListener(new WindowDestroyer());
        okButton.addActionListener(new OkButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());
        values.addListSelectionListener(new ValuesListListener());
        values.addMouseListener(new ValuesListDoubleClickListener());
    }

    protected void setValuesInJList(String valuesStringWithoutPrefix) {
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

    @Override
    public String getResult() {
        return getValue();
    }

    private void setOkButtonState() {
        okButton.setEnabled(getOldValue().length() >  0);
    }

    private void setValues(Set<Object> values) {
        this.values.setModel(new SortedListModel<Object>(values));
    }

    public boolean isOkButtonClicked() {
        return okButtonClicked;
    }

    public void setOkButtonClicked(boolean okButtonClicked) {
        this.okButtonClicked = okButtonClicked;
    }

    public JList<Object> getValues() {
        return values;
    }

    public void setValues(JList<Object> values) {
        this.values = values;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOldValue() {
        return oldValue;
    }

    private class WindowDestroyer extends WindowAdapter{
        @Override
        public void windowClosing (WindowEvent e){
            e.getWindow().dispose();
        }
    }

    private class OkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setOkButtonClicked(true);
            dispose();
        }
    }

    private class ValuesListDoubleClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                setOkButtonClicked(true);
                dispose();
            }
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setValue(getOldValue());
            dispose();
        }
    }

    private class ValuesListListener implements ListSelectionListener {
        @Override
        @SuppressWarnings("unchecked")
        public void valueChanged(ListSelectionEvent e) {
            if(!((JList<Object>)e.getSource()).getSelectionModel().isSelectionEmpty()) {
                okButton.setEnabled(true);
            }
        }
    }
}
