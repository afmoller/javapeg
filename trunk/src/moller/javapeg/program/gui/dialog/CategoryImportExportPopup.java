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
package moller.javapeg.program.gui.dialog;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.DefaultLookAndFeel;
import moller.util.io.FileUtil;
import moller.util.java.SystemProperties;

public class CategoryImportExportPopup extends JDialog {

    private static final long serialVersionUID = 1L;

    private File categoryFileToImportExport;

    private JButton actionButton;
    private JButton cancelButton;
    private JButton pathSelectionButton;

    private boolean actionButtonClicked;
    private boolean filePathSelected;
    private boolean isImport;

    private JTextField nameTextField;
    private JTextField categoryImportExportTextField;

    private final Logger logger;
    private final Language lang;

    public CategoryImportExportPopup(boolean isImport, String title, Rectangle sizeAndLocation, File imageMetaDataDataBase) {

        logger = Logger.getInstance();
        lang = Language.getInstance();

        actionButtonClicked = false;

        this.setTitle(title);
        this.setImport(isImport);
        this.setModal(true);

        JPanel background = new JPanel(new GridBagLayout());

        GBHelper pos = new GBHelper();
        pos.insets = new Insets(1, 1, 1, 1);

        background.add(this.cretaMainPane(imageMetaDataDataBase), pos.expandW().expandH());
        background.add(this.createButtonPanel(), pos.nextRow().expandW());

        this.getContentPane().add(background);
        this.setOkButtonDisabled();
        this.addListeners();
        this.setLookAndFeel();
        this.pack();
        this.setLocation(sizeAndLocation.getLocation());
        this.setSize(sizeAndLocation.getSize());
        this.setVisible(true);
    }

    private JPanel createButtonPanel() {

        actionButton = new JButton();

        if (isImport()) {
            actionButton.setText(lang.get("categoryimportexport.import"));
        } else {
            actionButton.setText(lang.get("categoryimportexport.export"));
        }

        cancelButton = new JButton(lang.get("common.button.cancel.label"));

        JPanel background = new JPanel();

        background.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));
        background.add(actionButton);
        background.add(cancelButton);

        return background;
    }

    private void setLookAndFeel() {
        try{
            DefaultLookAndFeel.set();
        } catch (Exception ex){
            logger.logERROR("Could not set default Look And Feel for Category Imort Popup");
            logger.logERROR(ex);
        }
    }

    private JPanel cretaMainPane(File imageMetaDataDataBase) {
        GBHelper positionMainPanel = new GBHelper();

        JPanel mainPanel = new JPanel(new GridBagLayout());
        JLabel importFileLabel = null;

        if (isImport && imageMetaDataDataBase != null) {
            importFileLabel = new JLabel(lang.get("categoryimportexport.importFileLabel") + " " + imageMetaDataDataBase.getAbsolutePath());
            importFileLabel.setForeground(Color.GRAY);
        }

        JLabel importNameLabel = new JLabel(lang.get("categoryimportexport.importNameLabel"));
        importNameLabel.setForeground(Color.GRAY);

        nameTextField = new JTextField();

        if (!isImport) {
            nameTextField.setText(SystemProperties.getUserName());
        }

        JLabel categoryImportExportLabel = new JLabel();

        if (isImport()) {
            categoryImportExportLabel.setText(lang.get("categoryimportexport.categoryImportExportImportLabel"));
        } else {
            categoryImportExportLabel.setText(lang.get("categoryimportexport.categoryImportExportExportLabel"));
        }
        categoryImportExportLabel.setForeground(Color.GRAY);

        categoryImportExportTextField = new JTextField();
        categoryImportExportTextField.setEditable(false);

        pathSelectionButton = new JButton();
        pathSelectionButton.setIcon(IconLoader.getIcon(Icons.OPEN));

        if (isImport()) {
            pathSelectionButton.setToolTipText(lang.get("categoryimportexport.selectCategoryFileToImport"));
        } else {
            pathSelectionButton.setToolTipText(lang.get("categoryimportexport.selectDestinationForCategoryExport"));
        }

        mainPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        if (isImport && imageMetaDataDataBase != null) {
            mainPanel.add(importFileLabel, positionMainPanel);
            mainPanel.add(new JLabel(" "), positionMainPanel.nextRow());
        }
        mainPanel.add(categoryImportExportLabel, positionMainPanel.nextRow().expandW());
        mainPanel.add(categoryImportExportTextField, positionMainPanel.nextRow().expandW());
        mainPanel.add(pathSelectionButton, positionMainPanel.nextCol());
        mainPanel.add(Box.createVerticalStrut(4), positionMainPanel.nextRow());
        mainPanel.add(importNameLabel, positionMainPanel.nextRow().expandW());
        mainPanel.add(nameTextField, positionMainPanel.nextRow().expandW());

        return mainPanel;
    }

    public boolean isActionButtonClicked() {
        return actionButtonClicked;
    }

    public String getFileName() {
        return nameTextField.getText();
    }

    private void setOkButtonDisabled() {
        actionButton.setEnabled(false);
    }

    private boolean isImport() {
        return this.isImport;
    }

    private void setImport(boolean isImport) {
        this.isImport = isImport;
    }

    private void addListeners() {
        this.addWindowListener(new WindowDestroyer());
        actionButton.addActionListener(new ActionButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());
        pathSelectionButton.addActionListener(new CategoryImportButtonListener());
        nameTextField.getDocument().addDocumentListener(new NameTextFieldListener());
    }

    public File getCategoryFileToImportExport() {
        return categoryFileToImportExport;
    }

    private class WindowDestroyer extends WindowAdapter{
        @Override
        public void windowClosing (WindowEvent windowsEvent){
            windowsEvent.getWindow().dispose();
        }
    }

    private class CategoryImportButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser chooser = new JFileChooser();

            if (isImport()) {
                FileFilter filter = new FileNameExtensionFilter("Category export files - cml", "cml");
                chooser.setFileFilter(filter);
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.setDialogTitle(lang.get("categoryimportexport.selectCategoryFileToImport"));
            } else {
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle(lang.get("categoryimportexport.selectDestinationForCategoryExport"));
            }

            if(chooser.showOpenDialog(CategoryImportExportPopup.this) == JFileChooser.APPROVE_OPTION) {
                categoryFileToImportExport = chooser.getSelectedFile();
                categoryImportExportTextField.setText(categoryFileToImportExport.getAbsolutePath());
                filePathSelected = true;

                if (!nameTextField.getText().isEmpty()) {
                    actionButton.setEnabled(true);
                }
            }
        }
    }

    private class ActionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            actionButtonClicked = true;
            dispose();
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private class NameTextFieldListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            validateInputInRealtime();
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
            validateInputInRealtime();
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }

    public void validateInputInRealtime() {
        actionButton.setEnabled(FileUtil.validFileName(nameTextField.getText()) &&
                                                      !nameTextField.getText().isEmpty() &&
                                                       filePathSelected);

    }
}
