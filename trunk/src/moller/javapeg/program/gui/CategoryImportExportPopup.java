package moller.javapeg.program.gui;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
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

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.Gap;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.DefaultLookAndFeel;
import moller.util.image.ImageUtil;
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

        //TODO: Remove hard coded string
        if (isImport()) {
            actionButton.setText("Import");
        } else {
            actionButton.setText("Export");
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
            //      TODO: Remove hard coded string
            importFileLabel = new JLabel("Kategoriimport för fil: " + imageMetaDataDataBase.getAbsolutePath());
            importFileLabel.setForeground(Color.GRAY);
        }

        //        TODO: Remove hard coded string
        JLabel importNameLabel = new JLabel("Namn");
        importNameLabel.setForeground(Color.GRAY);

        nameTextField = new JTextField();

        if (!isImport) {
            nameTextField.setText(SystemProperties.getUserName());
        }

//      TODO: Remove hard coded string
        JLabel categoryImportExportLabel = new JLabel();

        if (isImport()) {
            categoryImportExportLabel.setText("Kategorifil att importera");
        } else {
            categoryImportExportLabel.setText("Exportera kategorifil till");
        }
        categoryImportExportLabel.setForeground(Color.GRAY);

        categoryImportExportTextField = new JTextField();
        categoryImportExportTextField.setEditable(false);

        pathSelectionButton = new JButton();

//      TODO: Remove hard coded string
        if (isImport()) {
            pathSelectionButton.setToolTipText("Välj kategorifil att importera");
        } else {
            pathSelectionButton.setToolTipText("Välj destination för kategoriexport");
        }

        try {
            pathSelectionButton.setIcon(ImageUtil.getIcon(StartJavaPEG.class.getResourceAsStream("resources/images/open.gif"), true));
        } catch (IOException iox) {
            pathSelectionButton.setText("Open");
            logger.logERROR("Could not set image: resources/images/open.gif as icon for the import categories button. See stacktrace below for details");
            logger.logERROR(iox);
        }

        mainPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        if (isImport && imageMetaDataDataBase != null) {
            mainPanel.add(importFileLabel, positionMainPanel);
            mainPanel.add(new JLabel(" "), positionMainPanel.nextRow());
        }
        mainPanel.add(categoryImportExportLabel, positionMainPanel.nextRow().expandW());
        mainPanel.add(categoryImportExportTextField, positionMainPanel.nextRow().expandW());
        mainPanel.add(pathSelectionButton, positionMainPanel.nextCol());
        mainPanel.add(new Gap(4), positionMainPanel.nextRow());
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
        public void actionPerformed(ActionEvent e) {

            JFileChooser chooser = new JFileChooser();

            if (isImport()) {
                FileFilter filter = new FileNameExtensionFilter("Category export files - cml", "cml");
                chooser.setFileFilter(filter);
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);
//              TODO: Remove hard coded string
                chooser.setDialogTitle("Välj kategorifil att importera");
            } else {
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//              TODO: Remove hard coded string
                chooser.setDialogTitle("Välj katalog att exportera till");
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
        public void actionPerformed(ActionEvent e) {
            actionButtonClicked = true;
            dispose();
        }
    }

    private class CancelButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
        }
    }

    private class NameTextFieldListener implements DocumentListener {

        public void insertUpdate(DocumentEvent e) {
            validateInputInRealtime();
        }
        public void removeUpdate(DocumentEvent e) {
            validateInputInRealtime();
        }
        public void changedUpdate(DocumentEvent e) {
        }
    }

    public void validateInputInRealtime() {
        actionButton.setEnabled(FileUtil.validFileName(nameTextField.getText()) &&
                                                      !nameTextField.getText().isEmpty() &&
                                                       filePathSelected);

    }
}
