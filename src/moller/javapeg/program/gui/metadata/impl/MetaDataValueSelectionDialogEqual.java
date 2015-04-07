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
import moller.util.string.StringUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;

public class MetaDataValueSelectionDialogEqual extends AbstractMetaDataValueSelectionDialog {

    private static final long serialVersionUID = 1L;

    private JRadioButton equal;

    public MetaDataValueSelectionDialogEqual(String title, Set<Object> values, String oldValue, Point position) {
        super(title, values, oldValue, position);
    }

    @Override
    protected JPanel createOperatorsPanel() {
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

    @Override
    protected void setOldValue(String oldValue) {
        if (oldValue.length() > 0) {
             if (oldValue.startsWith("=")) {
                equal.setSelected(true);
            }
            this.setValuesInJList(oldValue.substring(1));
        } else {
            equal.setSelected(true);
        }
    }

    @Override
    public void collectSelectedValues() {
        List<Object> selectedValues = getValues().getSelectedValuesList();

        int nrOfSelectedValues = selectedValues.size();

        if (nrOfSelectedValues > 0 && isOkButtonClicked()) {
            setValue("=");

            StringBuilder sb = new StringBuilder();

            for (Object selectedValue : selectedValues) {
                sb.append(selectedValue.toString());
                sb.append(C.META_DATA_PARAMETER_VALUES_DELIMITER);
            }
            setValue(getValue() + StringUtil.removeStringFromEnd(sb.toString(), C.META_DATA_PARAMETER_VALUES_DELIMITER));
        } else {
            setValue(getOldValue());
        }
    }
}
