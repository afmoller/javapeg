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

import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;

import moller.javapeg.program.C;
import moller.javapeg.program.GBHelper;
import moller.util.string.StringUtil;

public class MetaDataValueSelectionDialogLessEqualGreater extends AbstractMetaDataValueSelectionDialog {

    private static final long serialVersionUID = 1L;

    private JRadioButton less;
    private JRadioButton equal;
    private JRadioButton greater;

    public MetaDataValueSelectionDialogLessEqualGreater(String title, Set<Object> values, String oldValue, Point position) {
        super(title, values, oldValue, position);
    }

    @Override
    protected JPanel createOperatorsPanel() {
        GBHelper positionOperatorsPanel = new GBHelper();

        JPanel backgroundPanel = new JPanel();

        JPanel operatorsPanel = new JPanel(new GridBagLayout());

        ButtonGroup group = new ButtonGroup();

        less = new JRadioButton("<");
        less.setName("less");

        equal = new JRadioButton("=");
        equal.setName("equal");

        greater = new JRadioButton(">");
        greater.setName("greater");

        group.add(less);
        group.add(equal);
        group.add(greater);

        operatorsPanel.add(less, positionOperatorsPanel);
        operatorsPanel.add(equal, positionOperatorsPanel.nextRow());
        operatorsPanel.add(greater, positionOperatorsPanel.nextRow());

        backgroundPanel.add(operatorsPanel);

        return backgroundPanel;
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        less.addActionListener(new RadioButtonListener());
        equal.addActionListener(new RadioButtonListener());
        greater.addActionListener(new RadioButtonListener());
    }

    @Override
    protected void setOldValue(String oldValue) {
        if (oldValue.length() > 0) {
            if (oldValue.startsWith("<")) {
                less.doClick();
            } else if (oldValue.startsWith(">")) {
                greater.doClick();
            } else {
                equal.doClick();
            }
            setValuesInJList(oldValue.substring(1));
        } else {
            equal.doClick();
        }
    }

    @Override
    public void collectSelectedValues() {
        List<Object> selectedValues = getValues().getSelectedValuesList();

        int nrOfSelectedValues = selectedValues.size();

        if (nrOfSelectedValues > 0 && isOkButtonClicked()) {

            if (nrOfSelectedValues == 1) {
                if (less.isSelected()) {
                    setValue("<");
                } else if (equal.isSelected()) {
                    setValue("=");
                } else {
                    setValue(">");
                }
                setValue(getValue() + selectedValues.get(0).toString());
            } else {
                setValue("=");

                StringBuilder sb = new StringBuilder();

                for (Object selectedValue : selectedValues) {
                    sb.append(selectedValue.toString());
                    sb.append(C.META_DATA_PARAMETER_VALUES_DELIMITER);
                }
                setValue(getValue() + StringUtil.removeStringFromEnd(sb.toString(), C.META_DATA_PARAMETER_VALUES_DELIMITER));
            }
        } else {
            setValue(getOldValue());
        }
    }

    private class RadioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = ((Component)e.getSource()).getName();

            if (name.equals("equal")) {
                getValues().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            } else if (name.equals("less") || name.equals("greater")) {
                if (getValues().getSelectedIndices().length > 1) {
                    int firstSelectedIndex = getValues().getSelectedIndices()[0];
                    getValues().clearSelection();
                    getValues().setSelectedIndex(firstSelectedIndex);
                }
                getValues().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            }
        }
    }
}
