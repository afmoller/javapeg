package moller.javapeg.program.firstlaunch;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

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

    private final Configuration configuration;

    public InitialConfigGUI() {

        configuration = Config.getInstance().get();

        this.createMainFrame();

        this.addListeners();

    }

    private void addListeners() {

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

        GBHelper posMainPanel = new GBHelper();

        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new GridBagLayout());

        JLabel label = new JLabel("Please select application language");

        mainPanel.add(label, posMainPanel);
        mainPanel.add(new Gap(10), posMainPanel.nextRow());

        LanguageUtil.listEmbeddedLanguages(null);

        Set<String> availableLanguagesSet = ApplicationContext.getInstance().getJarFileEmbeddedLanguageFiles();

        String[] availableLanguagesArray = new String[availableLanguagesSet.size()];

        int index = 0;
        for (String embeddeLanguageFile : availableLanguagesSet) {
            availableLanguagesArray[index] = ISO639.getInstance().getLanguage(embeddeLanguageFile.substring(embeddeLanguageFile.lastIndexOf(".") + 1));
            index++;
        }

        JList<String> availableLanguagesJList = new JList<>(availableLanguagesArray);

        mainPanel.add(availableLanguagesJList, posMainPanel.nextRow());

        JLabel importConfig = new JLabel("Import configuration from other installation");

        mainPanel.add(new Gap(10), posMainPanel.nextRow());
        mainPanel.add(importConfig, posMainPanel.nextRow());

        JButton importConfigFileChooserOpenButton = new JButton("Open");
        importConfigFileChooserOpenButton.addActionListener(new ImportConfigFileChooserOpenButtonListener());




        mainPanel.add(importConfigFileChooserOpenButton, posMainPanel.nextRow());

        return mainPanel;
    }

    private void searchForOldInstallations() {

    }

    private class ImportConfigFileChooserOpenButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser(SystemProperties.getUserHome());
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Import JavaPEG Configuration");

            if(chooser.showOpenDialog(InitialConfigGUI.this) == JFileChooser.APPROVE_OPTION) {
                chooser.getSelectedFile().getAbsolutePath();
            }

        }

    }

}
