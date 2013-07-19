package moller.javapeg.program.firstlaunch;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.Gap;
import moller.javapeg.program.config.ConfigUtil;
import moller.javapeg.program.config.schema.SchemaUtil;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.ISO639;
import moller.javapeg.program.language.LanguageUtil;
import moller.util.DefaultLookAndFeel;
import moller.util.io.DirectoryUtil;
import moller.util.java.SystemProperties;

public class InitialConfigGUI extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel availableLanguageSelectionLabel;
    private JLabel availableConfigurationsInUserDirLabel;
    private JLabel importConfigLabel;
    private JLabel availableAlternativeConfigurationsLabel;

    private JList<String> availableLanguagesJList;
    private JList<String> availableConfigurationsInUserDirectoryJList;
    private JList<String> availableAlternativeConfigurationsJList;

    private JButton importConfigFileChooserOpenButton;

    private JRadioButton noImportMode;
    private JRadioButton importMode;

    private int englishIndex;

    private boolean selectionChange = false;

    public InitialConfigGUI() {
        this.createMainFrame();
        this.initialize();
    }

    private void initialize() {
        noImportMode.doClick();

        if (englishIndex > -1) {
            availableLanguagesJList.setSelectedIndex(englishIndex);
        }

        List<File> foundConfigurationFilesInUserHome = findJavaPEGConfigurationFiles(new File(SystemProperties.getUserHome()));

        populateJList(availableConfigurationsInUserDirectoryJList, foundConfigurationFilesInUserHome);

        if (foundConfigurationFilesInUserHome != null && !foundConfigurationFilesInUserHome.isEmpty()) {
            importMode.doClick();
        } else {
            noImportMode.doClick();
        }
    }

    public void populateJList(JList<String> list, List<File> configurations) {
        if (configurations != null && !configurations.isEmpty()) {
            String[] listData = new String[configurations.size()];

            for (int i = 0; i < configurations.size(); i++) {
                listData[i] = configurations.get(i).getAbsolutePath();
            }
            list.setListData(listData);
        } else {
            list.setListData(new String[]{"No older installations found"});
        }
    }

    // Create Main Window
    public void createMainFrame() {

        this.add(this.createMainPanel());

        try{
            DefaultLookAndFeel.set();
        }
        catch (Exception ex){
            // Nothing to do.
        }
    }

    public JPanel createMainPanel() {

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridBagLayout());

        GBHelper posMainPanel = new GBHelper();

        mainPanel.add(this.createConfigurationModePanel(), posMainPanel);
        mainPanel.add(new Gap(10), posMainPanel.nextRow());
        mainPanel.add(this.createConfigurationPanel(), posMainPanel.nextRow());

        return mainPanel;
    }

    private JPanel createConfigurationModePanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        panel.setBorder(new TitledBorder("1: Configuration Mode"));

        noImportMode = new JRadioButton("No Import");
        noImportMode.addActionListener(new NoImportModeListener());

        importMode = new JRadioButton("Import");
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
        panel.setBorder(new TitledBorder("2: Configuration"));

        availableLanguageSelectionLabel = new JLabel("Please select application language:");

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
        availableLanguagesJListScrollPane.setMinimumSize(new Dimension(300, 30));

        availableConfigurationsInUserDirLabel = new JLabel("Found configurations in user home directory");

        availableConfigurationsInUserDirectoryJList = new JList<String>();
        availableConfigurationsInUserDirectoryJList.setBorder(new LineBorder(Color.BLACK));
        availableConfigurationsInUserDirectoryJList.addListSelectionListener(new AvailableConfigurationsInUserDirectoryJListListener());

        JScrollPane availableConfigurationsInUserDirectoryJListScrollPane = new JScrollPane(availableConfigurationsInUserDirectoryJList);
        availableConfigurationsInUserDirectoryJListScrollPane.setMinimumSize(new Dimension(300, 30));

        importConfigLabel = new JLabel("Import configuration from other installation:");

        importConfigFileChooserOpenButton = new JButton();

        try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/open.gif")) {
            ImageIcon openImageIcon = new ImageIcon();
            openImageIcon.setImage(ImageIO.read(imageStream));
            importConfigFileChooserOpenButton.setIcon(openImageIcon);
        } catch (IOException e) {
            importConfigFileChooserOpenButton.setText("Open");
        }

        importConfigFileChooserOpenButton.addActionListener(new ImportConfigFileChooserOpenButtonListener());

        availableAlternativeConfigurationsLabel = new JLabel("Found Configurations");

        availableAlternativeConfigurationsJList = new JList<String>();
        availableAlternativeConfigurationsJList.setBorder(new LineBorder(Color.BLACK));
        availableAlternativeConfigurationsJList.addListSelectionListener(new AvailableAlternativeConfigurationsJListListener());

        JScrollPane availableAlternativeConfigurationsJListScrollPane = new JScrollPane(availableAlternativeConfigurationsJList);
        availableAlternativeConfigurationsJListScrollPane.setMinimumSize(new Dimension(300, 30));

        GBHelper posPanel = new GBHelper();

        panel.add(new Gap(5), posPanel);
        panel.add(availableLanguageSelectionLabel, posPanel.nextRow());
        panel.add(new Gap(2), posPanel.nextRow());
        panel.add(availableLanguagesJListScrollPane, posPanel.nextRow());
        panel.add(new Gap(15), posPanel.nextRow());
        panel.add(availableConfigurationsInUserDirLabel, posPanel.nextRow());
        panel.add(new Gap(2), posPanel.nextRow());
        panel.add(availableConfigurationsInUserDirectoryJListScrollPane, posPanel.nextRow());
        panel.add(new Gap(15), posPanel.nextRow());
        panel.add(importConfigLabel, posPanel.nextRow());
        panel.add(new Gap(2), posPanel.nextRow());
        panel.add(importConfigFileChooserOpenButton, posPanel.nextRow());
        panel.add(new Gap(5), posPanel.nextRow());
        panel.add(availableAlternativeConfigurationsLabel, posPanel.nextRow());
        panel.add(availableAlternativeConfigurationsJListScrollPane, posPanel.nextRow());

        return panel;
    }

    private List<File> findJavaPEGConfigurationFiles(File selectedFile) {
        List<File> javaPegConfigurationFiles = new ArrayList<File>();

        List<File> potentialJavaPEGConfigurationDirectories = DirectoryUtil.findDirectories(selectedFile, "javapeg");

        if (potentialJavaPEGConfigurationDirectories != null) {
            for (File potentialJavaPEGConfigurationDirectory : potentialJavaPEGConfigurationDirectories) {
                List<File> potentialJavaPEGConfigurationsFiles = DirectoryUtil.findFiles(potentialJavaPEGConfigurationDirectory, "xml");

                for (File potentialJavaPEGConfigurationsFile : potentialJavaPEGConfigurationsFiles) {

                    for (String schema : SchemaUtil.getSchemas()) {
                        String configSchemaLocation = C.PATH_SCHEMAS + schema;

                        if (ConfigUtil.isConfigValid(potentialJavaPEGConfigurationsFile, configSchemaLocation).getResult()) {
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

    public String getImportPath() {
        if (availableAlternativeConfigurationsJList.isSelectionEmpty()) {
            return availableConfigurationsInUserDirectoryJList.getSelectedValue();
        } else {
            return availableAlternativeConfigurationsJList.getSelectedValue();
        }
    }

    public String getLanguage() {
        return availableLanguagesJList.getSelectedValue();
    }

    private class ImportConfigFileChooserOpenButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser(SystemProperties.getUserHome());
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Import JavaPEG Configuration");

            if(chooser.showOpenDialog(InitialConfigGUI.this) == JFileChooser.APPROVE_OPTION) {
                populateJList(availableAlternativeConfigurationsJList, findJavaPEGConfigurationFiles(chooser.getSelectedFile()));
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
}
