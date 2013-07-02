package moller.javapeg.program.firstlaunch;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.Gap;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.ISO639;
import moller.javapeg.program.language.LanguageUtil;
import moller.util.DefaultLookAndFeel;
import moller.util.java.SystemProperties;

public class InitialConfigGUI extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JLabel languageSelectionLabel;
    private JList<String> availableLanguagesJList;

    private JLabel importConfigLabel;
    private JButton importConfigFileChooserOpenButton;

    private JRadioButton noImportMode;

    private final Configuration configuration;

    private int englishIndex;

    public InitialConfigGUI() {

        configuration = Config.getInstance().get();

        this.createMainFrame();
        this.initialize();
    }

    private void initialize() {
        noImportMode.doClick();

        if (englishIndex > -1) {
            availableLanguagesJList.setSelectedIndex(englishIndex);
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

        JRadioButton importMode = new JRadioButton("Import");
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

        languageSelectionLabel = new JLabel("Please select application language:");

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

        GBHelper posPanel = new GBHelper();

        panel.add(new Gap(5), posPanel);
        panel.add(languageSelectionLabel, posPanel.nextRow());
        panel.add(new Gap(2), posPanel.nextRow());
        panel.add(availableLanguagesJList, posPanel.nextRow());
        panel.add(new Gap(15), posPanel.nextRow());
        panel.add(importConfigLabel, posPanel.nextRow());
        panel.add(new Gap(2), posPanel.nextRow());
        panel.add(importConfigFileChooserOpenButton, posPanel.nextRow());

        return panel;
    }

    private void searchForOldInstallations(File selectedFile) {

    }

    private class ImportConfigFileChooserOpenButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser(SystemProperties.getUserHome());
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Import JavaPEG Configuration");

            if(chooser.showOpenDialog(InitialConfigGUI.this) == JFileChooser.APPROVE_OPTION) {
                searchForOldInstallations(chooser.getSelectedFile());
            }
        }
    }

    private class ImportModeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            languageSelectionLabel.setEnabled(false);
            availableLanguagesJList.setEnabled(false);
            importConfigLabel.setEnabled(true);
            importConfigFileChooserOpenButton.setEnabled(true);
        }
    }

    private class NoImportModeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            languageSelectionLabel.setEnabled(true);
            availableLanguagesJList.setEnabled(true);
            importConfigLabel.setEnabled(false);
            importConfigFileChooserOpenButton.setEnabled(false);
        }
    }
}
