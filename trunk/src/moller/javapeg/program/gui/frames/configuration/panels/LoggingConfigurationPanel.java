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

import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.model.Logging;
import moller.javapeg.program.enumerations.Level;
import moller.javapeg.program.gui.frames.configuration.panels.base.BaseConfigurationPanel;
import moller.util.io.PathUtil;

public class LoggingConfigurationPanel extends BaseConfigurationPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JComboBox<Level> logLevels;
    private JCheckBox developerMode;
    private JCheckBox rotateLog;
    private JCheckBox zipLog;
    private JTextField rotateLogSize;
    private JComboBox<String> rotateLogSizeFactor;
    private JTextField logName;
    private JComboBox<String> logEntryTimeStampFormats;
    private JTextField logEntryTimeStampPreview;

    @Override
    public boolean isValidConfiguration() {
        if (!validateLogName(logName.getText())) {
            return false;
        }

        if (!validateLogRotateSize()) {
            return false;
        }
        return true;
    }

    @Override
    protected void addListeners() {
        rotateLogSize.getDocument().addDocumentListener(new RotateLogSizeJTextFieldListener());
        rotateLogSizeFactor.addItemListener(new RotateLogSizeFactorJComboBoxListener());
        logName.getDocument().addDocumentListener(new LogNameJTextFieldListener());
        logEntryTimeStampFormats.addItemListener(new LogEntryTimestampFormatsJComboBoxListener());
        rotateLog.addChangeListener(new RotateLogCheckBoxListener());
    }

    @Override
    protected void createPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), BorderFactory.createTitledBorder(getLang().get("configviewer.tree.node.logging"))));


        Logging logging = getConfiguration().getLogging();

        JLabel logLevelsLabel = new JLabel(getLang().get("configviewer.logging.label.logLevel.text"));
        logLevels = new JComboBox<Level>(Level.values());
        logLevels.setSelectedItem(logging.getLevel());

        developerMode = new JCheckBox(getLang().get("configviewer.logging.label.developerMode.text"));
        developerMode.setSelected(logging.getDeveloperMode());

        rotateLog = new JCheckBox(getLang().get("configviewer.logging.label.rotateLog.text"));
        rotateLog.setSelected(logging.getRotate());

        zipLog = new JCheckBox(getLang().get("configviewer.logging.label.zipLog.text"));
        zipLog.setSelected(logging.getRotateZip());

        JLabel rotateLogSizeLabel = new JLabel(getLang().get("configviewer.logging.label.rotateLogSize.text"));

        JPanel logSizePanel = new JPanel(new GridBagLayout());
        GBHelper posLogSizePanel = new GBHelper();

        rotateLogSize = new JTextField();
        rotateLogSize.setEnabled(logging.getRotate());

        long logSize = logging.getRotateSize();

        String [] factors = {"KiB", "MiB"};

        rotateLogSizeFactor = new JComboBox<String>(factors);

        /**
         * Set values to the rotate log size JTextField and rotate log size
         * factor JComboBox
         */
        longToHuman(logSize);

        logSizePanel.add(rotateLogSize, posLogSizePanel.expandW());
        logSizePanel.add(Box.createHorizontalStrut(10), posLogSizePanel.nextCol());
        logSizePanel.add(rotateLogSizeFactor, posLogSizePanel.nextCol());

        JLabel logNameLabel = new JLabel(getLang().get("configviewer.logging.label.logName.text"));
        logName = new JTextField();
        logName.setText(logging.getFileName());

        JLabel logEntryTimeStampFormatLabel = new JLabel(getLang().get("configviewer.logging.label.logEntryTimeStampFormat.text"));

        Set<String> formats = new LinkedHashSet<String>();

        formats.add(logging.getTimeStampFormat().toPattern());
        formats.add("yyyy-MM-dd'T'HH:mm:ss:SSSZ");
        formats.add("yyyyMMdd'T'HHmmssSSSZ");
        formats.add("yyyy-D'T'HH:mm:ss:SSSZ");
        formats.add("yyyyD'T'HHmmssSSSZ");
        formats.add("MM/dd/yyyy:HH:mm:ss:SSS");
        formats.add("dd/MM/yyyy:HH:mm:ss:SSS");

        logEntryTimeStampFormats = new JComboBox<String>(formats.toArray(new String[]{""}));

        JLabel logEntryTimeStampPreviewLabel = new JLabel(getLang().get("configviewer.logging.label.logEntryTimeStampPreview.text"));
        logEntryTimeStampPreview = new JTextField();
        logEntryTimeStampPreview.setEditable(false);
        updatePreviewTimestamp();

        GBHelper posPanel = new GBHelper();
        add(developerMode, posPanel.expandW());
        add(rotateLog, posPanel.nextRow().expandW());
        add(zipLog, posPanel.nextRow().expandW());
        add(Box.createVerticalStrut(5), posPanel.nextRow());
        add(rotateLogSizeLabel, posPanel.nextRow());
        add(logSizePanel, posPanel.nextCol().expandW());
        add(Box.createVerticalStrut(5), posPanel.nextRow());
        add(logLevelsLabel, posPanel.nextRow());
        add(logLevels, posPanel.nextCol().expandW());
        add(Box.createVerticalStrut(5), posPanel.nextRow());
        add(logNameLabel, posPanel.nextRow());
        add(logName, posPanel.nextCol().expandW());
        add(Box.createVerticalStrut(5), posPanel.nextRow());
        add(logEntryTimeStampFormatLabel, posPanel.nextRow());
        add(logEntryTimeStampFormats, posPanel.nextCol().expandW());
        add(Box.createVerticalStrut(5), posPanel.nextRow());
        add(logEntryTimeStampPreviewLabel, posPanel.nextRow());
        add(logEntryTimeStampPreview, posPanel.nextCol().expandW());
        add(Box.createVerticalGlue(), posPanel.nextRow().expandH());
    }

    @Override
    public String getChangedConfigurationMessage() {
        StringBuilder displayMessage = new StringBuilder();

        Logging logging = getConfiguration().getLogging();

        if(logging.getDeveloperMode() != developerMode.isSelected()){
            displayMessage.append(getLang().get("configviewer.logging.label.developerMode.text") + ": " + developerMode.isSelected() + " (" + logging.getDeveloperMode() + ")\n");
        }

        if(logging.getRotate() != rotateLog.isSelected()){
            displayMessage.append(getLang().get("configviewer.logging.label.rotateLog.text") + ": " + rotateLog.isSelected() + " (" + logging.getRotate() + ")\n");
        }

        if(logging.getRotateZip() != zipLog.isSelected()){
            displayMessage.append(getLang().get("configviewer.logging.label.zipLog.text") + ": " + zipLog.isSelected() + " (" + logging.getRotateZip() + ")\n");
        }

        if(!logging.getRotateSize().equals(calculateRotateLogSize(rotateLogSize.getText(), rotateLogSizeFactor.getSelectedItem().toString()))){
            displayMessage.append(getLang().get("configviewer.logging.label.rotateLogSize.text") + ": " + rotateLogSize.getText() + " " + rotateLogSizeFactor.getSelectedItem() + " (" + parseRotateLongSize(logging.getRotateSize(), rotateLogSizeFactor.getSelectedItem().toString()) + " " + rotateLogSizeFactor.getSelectedItem()+ ")\n");
        }

        if(logging.getLevel() != (Level)logLevels.getSelectedItem()) {
            displayMessage.append(getLang().get("configviewer.logging.label.logLevel.text") + ": " + logLevels.getSelectedItem() + " (" + logging.getLevel() + ")\n");
        }

        if(!logging.getFileName().equals(logName.getText())){
            displayMessage.append(getLang().get("configviewer.logging.label.logName.text") + ": " + logName.getText() + " (" + logging.getFileName() + ")\n");
        }

        if(!logging.getTimeStampFormat().toPattern().equals(logEntryTimeStampFormats.getSelectedItem())) {
            displayMessage.append(getLang().get("configviewer.logging.label.logEntryTimeStampFormat.text") + ": " + logEntryTimeStampFormats.getSelectedItem() + " (" + logging.getTimeStampFormat().toPattern() + ")\n");
        }

        return displayMessage.toString();
    }

    private void longToHuman (Long logSize) {
        if (logSize / (1024 * 1024) > 1) {
            rotateLogSize.setText(Long.toString(logSize / (1024 * 1024)));
            rotateLogSizeFactor.setSelectedIndex(1);
        } else {
            rotateLogSize.setText(Long.toString(logSize / (1024)));
            rotateLogSizeFactor.setSelectedIndex(0);
        }
    }

    private void updatePreviewTimestamp() {
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat(logEntryTimeStampFormats.getSelectedItem().toString());
        logEntryTimeStampPreview.setText(sdf.format(date));
    }

    private boolean validateLogName(String logName) {
        boolean isValid = true;

        int result = PathUtil.validateString(logName, false);

        if (result > -1) {
            isValid = false;
            JOptionPane.showMessageDialog(this, getLang().get("common.message.error.invalidFileName") + " " + (char)result, getLang().get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
        }
        return isValid;
    }

    private Long calculateRotateLogSize(String size, String factor) {
        return calculateRotateLogSize(Long.parseLong(size), factor);
    }

    private String parseRotateLongSize(Long size, String factor) {
        if (factor.equals("KiB")) {
            size /= 1024;
        } else {
            size /= 1024 * 1024;
        }
        return Long.toString(size);
    }

    private long calculateRotateLogSize(Long size, String factor) {
        if (factor.equals("KiB")) {
            size *= 1024;
        } else {
            size *= 1024 * 1024;
        }
        return size;
    }

    private boolean validateLogRotateSize() {
        boolean isValid = true;

        try {
            Long size = Long.parseLong(rotateLogSize.getText());

            String factor = rotateLogSizeFactor.getSelectedItem().toString();

            size = calculateRotateLogSize(size, factor);

            if(size > 100 * 1024 * 1024) {
                isValid = false;

                if(factor.equals("KiB")) {
                    JOptionPane.showMessageDialog(this, getLang().get("configviewer.errormessage.rotateLogSizeToLargeKiB"), getLang().get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, getLang().get("configviewer.errormessage.rotateLogSizeToLargeMiB"), getLang().get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                }
            }

            if(size < 10 * 1024) {
                JOptionPane.showMessageDialog(this, getLang().get("configviewer.errormessage.rotateLogSizeToSmall"), getLang().get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException nfex) {
            isValid = false;
            JOptionPane.showMessageDialog(this, getLang().get("configviewer.errormessage.rotateLogSizeNotAnInteger"), getLang().get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
        }
        return isValid;
    }

    private class LogEntryTimestampFormatsJComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updatePreviewTimestamp();
            }
        }
    }

    private class LogNameJTextFieldListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            validateLogName(logName.getText());
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }

    private class RotateLogSizeFactorJComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                validateLogRotateSize();
            }
        }
    }

    private class RotateLogCheckBoxListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            rotateLogSize.setEnabled(rotateLog.isSelected());
        }
    }

    private class RotateLogSizeJTextFieldListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            validateLogRotateSize();
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
        }
    }

    @Override
    public void updateConfiguration() {
        Logging logging = getConfiguration().getLogging();

        logging.setDeveloperMode(developerMode.isSelected());
        logging.setFileName(logName.getText().trim());
        logging.setLevel((Level)logLevels.getSelectedItem());
        logging.setRotate(rotateLog.isSelected());
        logging.setRotateSize(calculateRotateLogSize(Long.parseLong(rotateLogSize.getText()), rotateLogSizeFactor.getSelectedItem().toString()));
        logging.setRotateZip(zipLog.isSelected());
        logging.setTimeStampFormat(new SimpleDateFormat((String)logEntryTimeStampFormats.getSelectedItem()));
    }
}
