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
package moller.javapeg.program.firstlaunch;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.enumerations.ConfigurationSchema;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.language.ISO639;
import moller.javapeg.program.language.LanguageUtil;
import moller.util.DefaultLookAndFeel;
import moller.util.gui.Screen;
import moller.util.io.DirectoryUtil;
import moller.util.java.SystemProperties;
import moller.util.xml.XMLUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * This class is used to display an initial configuration GUI the first time
 * JavaPEG is started after it has been installed.
 * <p>
 * The user has the possibility to either make an import of an configuration
 * from an previously done installation, older version or just another
 * installation or to just specify which language (from an list of available
 * languages) JavaPEG shall use.
 *
 * @author Fredrik
 *
 */
public class InitialConfigGUI extends JDialog {

    private static final long serialVersionUID = 1L;

    private JLabel availableLanguageSelectionLabel;
    private JLabel availableConfigurationsInUserDirLabel;
    private JLabel importConfigLabel;
    private JLabel availableAlternativeConfigurationsLabel;

    private JList<String> availableLanguagesJList;
    private JList<File> availableConfigurationsInUserDirectoryJList;
    private JList<File> availableAlternativeConfigurationsJList;

    private JButton importConfigFileChooserOpenButton;
    private JButton continueButton;

    private JRadioButton noImportMode;
    private JRadioButton importMode;

    private JProgressBar progressBar;

    private int englishIndex;

    private boolean selectionChange = false;
    private boolean continueButtonClicked = false;

    private final InitialConfigGUILanguage language = InitialConfigGUILanguage.getInstance();

    public InitialConfigGUI() {
        this.createMainFrame();
        this.initialize();
    }

    private void initialize() {
        noImportMode.doClick();

        if (englishIndex > -1) {
            availableLanguagesJList.setSelectedIndex(englishIndex);
        }

        FindJavaPEGConfigurationFilesInUserHomeWorker fjcfiuh = new  FindJavaPEGConfigurationFilesInUserHomeWorker(new File(SystemProperties.getUserHome()));
        fjcfiuh.execute();
    }

    private void populateJList(JList<File> list, List<File> configurations) {
        if (configurations != null && !configurations.isEmpty()) {
            File[] listData = new File[configurations.size()];

            for (int i = 0; i < configurations.size(); i++) {
                listData[i] = configurations.get(i);
            }
            list.setListData(listData);
        }
    }

    // Create Main Window
    public void createMainFrame() {

        int width = 900;
        int height = 720;

        this.setSize(width, height);
        this.setLocation(Screen.getLeftUpperLocationForCenteredPosition(width, height));
        this.setTitle(language.get("window.title"));

        this.add(this.createMainPanel());

        try{
            DefaultLookAndFeel.set();
        }
        catch (Exception ex){
            // Nothing to do.
        }

        this.setModalityType(ModalityType.APPLICATION_MODAL);
    }

    private JPanel createMainPanel() {

        JPanel configurationPanel = new JPanel();
        configurationPanel.setLayout(new GridBagLayout());

        GBHelper posLeftPanel = new GBHelper();

        configurationPanel.add(this.createConfigurationModePanel(), posLeftPanel.expandH().expandW());
        configurationPanel.add(Box.createVerticalStrut(10), posLeftPanel.nextRow());
        configurationPanel.add(this.createConfigurationPanel(), posLeftPanel.nextRow().expandH().expandW());

        progressBar = new JProgressBar();
        progressBar.setVisible(false);

        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(new GridBagLayout());

        GBHelper posRightPanel = new GBHelper();
        helpPanel.add(this.createHelpPanel(), posRightPanel.expandH());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        GBHelper posMainPanel = new GBHelper();

        mainPanel.add(helpPanel, posMainPanel.expandH());
        mainPanel.add(configurationPanel, posMainPanel.nextCol().expandH().expandW());
        mainPanel.add(progressBar, posMainPanel.nextRow().width(2));
        mainPanel.add(createButtonPanel(), posMainPanel.nextRow().width(2));

        return mainPanel;
    }

    private JPanel createButtonPanel() {
        continueButton  = new JButton(language.get("button.continue"));
        continueButton.addActionListener(new ContinueButtonListener());
        continueButton.setEnabled(false);

        JButton cancelButton = new JButton(language.get("button.cancel"));
        cancelButton.addActionListener(new CancelButtonListener());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(new TitledBorder(""));

        buttonPanel.add(continueButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private JPanel createHelpPanel() {

        JTextArea textarea = new JTextArea();
        textarea.setText(language.get("help.text"));
        textarea.setEditable(false);
        textarea.setBorder(new LineBorder(Color.BLACK));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new TitledBorder(language.get("help.title")));

        GBHelper posMainPanel = new GBHelper();

        mainPanel.add(textarea, posMainPanel.expandH());

        return mainPanel;
    }

    private JPanel createConfigurationModePanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        panel.setBorder(new TitledBorder(language.get("configuration.mode.title")));

        noImportMode = new JRadioButton(language.get("configuration.mode.noimport"));
        noImportMode.addActionListener(new NoImportModeListener());

        importMode = new JRadioButton(language.get("configuration.mode.import"));
        importMode.addActionListener(new ImportModeListener());

        ButtonGroup group = new ButtonGroup();

        group.add(noImportMode);
        group.add(importMode);

        GBHelper posPanel = new GBHelper();

        panel.add(noImportMode, posPanel.expandW());
        panel.add(importMode, posPanel.nextRow().expandW());

        return panel;
    }

    private JPanel createConfigurationPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        panel.setBorder(new TitledBorder(language.get("configuration.section.title")));

        availableLanguageSelectionLabel = new JLabel(language.get("configuration.section.available.languages"));

        LanguageUtil.listEmbeddedLanguages(null);

        Set<String> availableLanguagesSet = ApplicationContext.getInstance().getJarFileEmbeddedLanguageFiles();

        String[] availableLanguagesArray = new String[availableLanguagesSet.size()];

        int index = 0;
        englishIndex = -1;
        for (String embeddeLanguageFile : availableLanguagesSet) {
            availableLanguagesArray[index] = ISO639.getInstance().getLanguage(embeddeLanguageFile.substring(embeddeLanguageFile.lastIndexOf(".") + 1));
            if ("english".equalsIgnoreCase(availableLanguagesArray[index])) {
                englishIndex = index;
            }
            index++;
        }

        availableLanguagesJList = new JList<>(availableLanguagesArray);
        availableLanguagesJList.setBorder(new LineBorder(Color.BLACK));

        JScrollPane availableLanguagesJListScrollPane = new JScrollPane(availableLanguagesJList);

        availableConfigurationsInUserDirLabel = new JLabel(language.get("configuration.section.available.configurations.in.user.home"));

        availableConfigurationsInUserDirectoryJList = new JList<File>();
        availableConfigurationsInUserDirectoryJList.setBorder(new LineBorder(Color.BLACK));
        availableConfigurationsInUserDirectoryJList.addListSelectionListener(new AvailableConfigurationsInUserDirectoryJListListener());

        JScrollPane availableConfigurationsInUserDirectoryJListScrollPane = new JScrollPane(availableConfigurationsInUserDirectoryJList);

        importConfigLabel = new JLabel(language.get("configuration.section.other.import.location.title"));

        importConfigFileChooserOpenButton = new JButton();

        JPanel importConfigFileChooserOpenButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
        importConfigFileChooserOpenButtonPanel.add(importConfigFileChooserOpenButton);

        importConfigFileChooserOpenButton.setIcon(IconLoader.getIcon(Icons.OPEN));
        importConfigFileChooserOpenButton.addActionListener(new ImportConfigFileChooserOpenButtonListener());

        availableAlternativeConfigurationsLabel = new JLabel(language.get("configuration.section.other.import.location.found.configurations"));

        availableAlternativeConfigurationsJList = new JList<File>();
        availableAlternativeConfigurationsJList.setBorder(new LineBorder(Color.BLACK));
        availableAlternativeConfigurationsJList.addListSelectionListener(new AvailableAlternativeConfigurationsJListListener());

        JScrollPane availableAlternativeConfigurationsJListScrollPane = new JScrollPane(availableAlternativeConfigurationsJList);

        GBHelper posPanel = new GBHelper();

        panel.add(Box.createVerticalStrut(5), posPanel);
        panel.add(availableLanguageSelectionLabel, posPanel.nextRow());
        panel.add(Box.createVerticalStrut(2), posPanel.nextRow());
        panel.add(availableLanguagesJListScrollPane, posPanel.nextRow().expandH().expandW());
        panel.add(Box.createVerticalStrut(15), posPanel.nextRow());
        panel.add(availableConfigurationsInUserDirLabel, posPanel.nextRow());
        panel.add(Box.createVerticalStrut(2), posPanel.nextRow());
        panel.add(availableConfigurationsInUserDirectoryJListScrollPane, posPanel.nextRow().expandH().expandW());
        panel.add(Box.createVerticalStrut(15), posPanel.nextRow());
        panel.add(importConfigLabel, posPanel.nextRow());
        panel.add(Box.createVerticalStrut(2), posPanel.nextRow());
        panel.add(importConfigFileChooserOpenButtonPanel, posPanel.nextRow());
        panel.add(Box.createVerticalStrut(5), posPanel.nextRow());
        panel.add(availableAlternativeConfigurationsLabel, posPanel.nextRow());
        panel.add(availableAlternativeConfigurationsJListScrollPane, posPanel.nextRow().expandH().expandW());

        return panel;
    }

    /**
     * @param selectedFile
     * @return
     */
    private List<File> findJavaPEGConfigurationFiles(File selectedFile) {
        List<File> javaPegConfigurationFiles = new ArrayList<File>();

        List<File> potentialJavaPEGConfigurationDirectories = DirectoryUtil.findDirectories(selectedFile, "javapeg");

        if (potentialJavaPEGConfigurationDirectories != null) {
            for (File potentialJavaPEGConfigurationDirectory : potentialJavaPEGConfigurationDirectories) {
                List<File> potentialJavaPEGConfigurationsFiles = DirectoryUtil.findFiles(potentialJavaPEGConfigurationDirectory, "xml");

                for (File potentialJavaPEGConfigurationsFile : potentialJavaPEGConfigurationsFiles) {

                    for (ConfigurationSchema schema : ConfigurationSchema.values()) {
                        String configSchemaLocation = C.PATH_SCHEMAS + schema.getSchemaName();
                        StreamSource configSchema = new StreamSource(StartJavaPEG.class.getResourceAsStream(configSchemaLocation));

                        if (XMLUtil.validate(potentialJavaPEGConfigurationsFile, configSchema).getResult()) {
                            javaPegConfigurationFiles.add(potentialJavaPEGConfigurationsFile);
                            break;
                        }
                    }
                }
            }
        }

        return javaPegConfigurationFiles;
    }

    private void activateImport(boolean activate) {
        availableLanguageSelectionLabel.setEnabled(!activate);
        availableLanguagesJList.setEnabled(!activate);
        availableAlternativeConfigurationsLabel.setEnabled(activate);
        availableAlternativeConfigurationsJList.setEnabled(activate);
        availableConfigurationsInUserDirLabel.setEnabled(activate);
        availableConfigurationsInUserDirectoryJList.setEnabled(activate);
        importConfigLabel.setEnabled(activate);
        importConfigFileChooserOpenButton.setEnabled(activate);
    }

    public boolean isImport() {
        return importMode.isSelected();
    }

    public File getImportPath() {
        if (availableAlternativeConfigurationsJList.isSelectionEmpty()) {
            return availableConfigurationsInUserDirectoryJList.getSelectedValue();
        } else {
            return availableAlternativeConfigurationsJList.getSelectedValue();
        }
    }

    public String getLanguage() {
        return availableLanguagesJList.getSelectedValue();
    }

    public boolean isContinueButtonClicked() {
        return continueButtonClicked;
    }

    public void setContinueButtonClicked(boolean continueButtonClicked) {
        this.continueButtonClicked = continueButtonClicked;
    }

    private class ImportConfigFileChooserOpenButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser(SystemProperties.getUserHome());
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle(language.get("configuration.search.filechooser.title"));

            if(chooser.showOpenDialog(InitialConfigGUI.this) == JFileChooser.APPROVE_OPTION) {
                FindJavaPEGConfigurationFilesInUserHomeWorker fjcfiuh = new  FindJavaPEGConfigurationFilesInUserHomeWorker(chooser.getSelectedFile());
                fjcfiuh.execute();
            }
        }
    }

    private class ImportModeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            activateImport(true);
        }
    }

    private class NoImportModeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            activateImport(false);
        }
    }

    /**
     * Listener class that listens for clicks on the Continue button and if that
     * happens, the flag "continuebuttonisclicked" is set to true and this
     * {@link JDialog} is disposed.
     *
     * @author Fredrik
     *
     */
    private class ContinueButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setContinueButtonClicked(true);
            dispose();
        }
    }

    /**
     * Listener class that listens for clicks on the Cancel button and if that
     * happens, the flag "continuebuttonisclicked" is set to false and this
     * {@link JDialog} is disposed.
     *
     * @author Fredrik
     *
     */
    private class CancelButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setContinueButtonClicked(false);
            dispose();
        }
    }

    private class AvailableAlternativeConfigurationsJListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!selectionChange) {
                selectionChange = true;
                availableConfigurationsInUserDirectoryJList.clearSelection();
                selectionChange = false;
            }
        }
    }

    private class AvailableConfigurationsInUserDirectoryJListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!selectionChange) {
                selectionChange = true;
                availableAlternativeConfigurationsJList.clearSelection();
                selectionChange = false;
            }
        }
    }

    /**
     * This class extends the {@link SwingWorker} and performs the long running
     * task of finding all JavaPEG configuration files in a specified path.
     *
     * @author Fredrik
     *
     */
    private class FindJavaPEGConfigurationFilesInUserHomeWorker extends SwingWorker<List<File>, String> {

        File directoryToSearchIn;

        public FindJavaPEGConfigurationFilesInUserHomeWorker(File directoryToSearchIn) {
            this.directoryToSearchIn = directoryToSearchIn;
        }

        @Override
        protected List<File> doInBackground() throws Exception {
            // Avoid making it possible to trigger two concurrent searches.
            if (importMode.isSelected()) {
                importConfigFileChooserOpenButton.setEnabled(false);
            }

            progressBar.setVisible(true);
            progressBar.setIndeterminate(true);
            progressBar.setToolTipText(language.get("configuration.search.in.directory") + " " + directoryToSearchIn.getAbsolutePath());
            return findJavaPEGConfigurationFiles(directoryToSearchIn);
        }

        @Override
        protected void done() {

            try {
                List<File> foundConfigurationFilesInUserHome = get();
                // Do not display the configuration file for the current
                // installation.
                File configFile = new File(C.PATH_TO_CONFIGURATION_FILE);
                foundConfigurationFilesInUserHome.remove(configFile);

                populateJList(availableConfigurationsInUserDirectoryJList, foundConfigurationFilesInUserHome);

                progressBar.setVisible(false);
                continueButton.setEnabled(true);

                if (importMode.isSelected()) {
                    importConfigFileChooserOpenButton.setEnabled(true);
                }

                if (foundConfigurationFilesInUserHome != null && !foundConfigurationFilesInUserHome.isEmpty()) {
                    importMode.doClick();
                    JOptionPane.showMessageDialog(InitialConfigGUI.this, language.get("configuration.search.found.configurationfiles") + " " + directoryToSearchIn.getAbsolutePath());
                } else {
                    if (!importMode.isSelected()) {
                        noImportMode.doClick();
                    }
                    JOptionPane.showMessageDialog(InitialConfigGUI.this, language.get("configuration.search.no.found.configurationfiles") + " " + directoryToSearchIn.getAbsolutePath());
                }
            } catch (InterruptedException iex) {
                JOptionPane.showMessageDialog(InitialConfigGUI.this, language.get("configuration.search.aborted") + " " + iex, language.get("lable.information"), JOptionPane.INFORMATION_MESSAGE);
            } catch (ExecutionException eex) {
                JOptionPane.showMessageDialog(InitialConfigGUI.this, language.get("configuration.search.aborted") + " " + eex.getCause(), language.get("lable.information"), JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
