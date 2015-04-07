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
package moller.javapeg.program.gui.frames.configuration.panels;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.javapeg.program.gui.frames.configuration.panels.base.BaseConfigurationPanel;
import moller.util.string.StringUtil;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class RenameConfigurationPanel extends BaseConfigurationPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JCheckBox useLastModifiedDate;
    private JCheckBox useLastModifiedTime;
    private JTextField maximumLengthOfCameraModelValueTextField;

    @Override
    protected void addListeners() {
        maximumLengthOfCameraModelValueTextField.getDocument().addDocumentListener(new MaximumLengtOfCameraModelJTextFieldListener());
    }

    @Override
    protected void createPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), BorderFactory.createTitledBorder(getLang().get("configviewer.tree.node.rename"))));

        GBHelper posPanel = new GBHelper();

        RenameImages renameImages = getConfiguration().getRenameImages();

        useLastModifiedDate = new JCheckBox(getLang().get("configviewer.rename.label.useLastModifiedDate.text"));
        useLastModifiedDate.setSelected(renameImages.getUseLastModifiedDate());

        useLastModifiedTime = new JCheckBox(getLang().get("configviewer.rename.label.useLastModifiedTime.text"));
        useLastModifiedTime.setSelected(renameImages.getUseLastModifiedTime());

        JLabel cameraModelValueLengthLabel = new JLabel(getLang().get("configviewer.rename.label.maximumCameraModelValueLength"));
        maximumLengthOfCameraModelValueTextField = new JTextField(5);
        maximumLengthOfCameraModelValueTextField.setText(Integer.toString(renameImages.getCameraModelNameMaximumLength()));

        add(cameraModelValueLengthLabel, posPanel);
        add(maximumLengthOfCameraModelValueTextField, posPanel.nextCol().expandW());
        add(useLastModifiedDate, posPanel.nextRow().expandW());
        add(useLastModifiedTime, posPanel.nextRow().expandW());
        add(Box.createVerticalGlue(), posPanel.nextRow().expandW().expandH());
    }

    @Override
    public void updateConfiguration() {
        RenameImages renameImages = getConfiguration().getRenameImages();

        renameImages.setCameraModelNameMaximumLength(Integer.parseInt(maximumLengthOfCameraModelValueTextField.getText()));
        renameImages.setUseLastModifiedDate(useLastModifiedDate.isSelected());
        renameImages.setUseLastModifiedTime(useLastModifiedTime.isSelected());
    }

    private class  MaximumLengtOfCameraModelJTextFieldListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
            validateMaximumLengtOfCameraModel();
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            validateMaximumLengtOfCameraModel();
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
            validateMaximumLengtOfCameraModel();
        }
    }

    @Override
    public boolean isValidConfiguration() {
        return validateMaximumLengtOfCameraModel();
    }

    boolean validateMaximumLengtOfCameraModel() {
        if(!StringUtil.isInt(maximumLengthOfCameraModelValueTextField.getText(), true)) {
            JOptionPane.showMessageDialog(this, getLang().get("configviewer.rename.label.maximumCameraModelValueLengthNotNegative"), getLang().get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public String getChangedConfigurationMessage() {
        StringBuilder displayMessage = new StringBuilder();

        RenameImages renameImages = getConfiguration().getRenameImages();

        if(renameImages.getUseLastModifiedDate() != useLastModifiedDate.isSelected()){
            displayMessage.append(getLang().get("configviewer.rename.label.useLastModifiedDate.text") + ": " + useLastModifiedDate.isSelected() + " (" + renameImages.getUseLastModifiedDate() + ")\n");
        }

        if(renameImages.getUseLastModifiedTime() != useLastModifiedTime.isSelected()){
            displayMessage.append(getLang().get("configviewer.rename.label.useLastModifiedTime.text") + ": " + useLastModifiedTime.isSelected() + " (" + renameImages.getUseLastModifiedTime() + ")\n");
        }

        if(!renameImages.getCameraModelNameMaximumLength().equals(Integer.parseInt(maximumLengthOfCameraModelValueTextField.getText()))) {
            displayMessage.append(getLang().get("configviewer.rename.label.maximumCameraModelValueLength") + ": " + maximumLengthOfCameraModelValueTextField.getText() + " (" + renameImages.getCameraModelNameMaximumLength() + ")\n");
        }
        return displayMessage.toString();
    }
}
