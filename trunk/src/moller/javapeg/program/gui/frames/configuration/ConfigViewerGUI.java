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
package moller.javapeg.program.gui.frames.configuration;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.gui.frames.configuration.panels.LanguageConfigurationPanel;
import moller.javapeg.program.gui.frames.configuration.panels.LoggingConfigurationPanel;
import moller.javapeg.program.gui.frames.configuration.panels.MetadataConfigurationPanel;
import moller.javapeg.program.gui.frames.configuration.panels.RenameConfigurationPanel;
import moller.javapeg.program.gui.frames.configuration.panels.TagConfigurationPanel;
import moller.javapeg.program.gui.frames.configuration.panels.ThumbnailConfigurationPanel;
import moller.javapeg.program.gui.frames.configuration.panels.UpdatesConfigurationPanel;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.gui.Screen;

public class ConfigViewerGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTree tree;

    private JPanel backgroundsPanel;

    private final UpdatesConfigurationPanel updatesConfigurationPanel;
    private final LanguageConfigurationPanel languageConfigurationPanel;
    private final ThumbnailConfigurationPanel thumbnailConfigurationPanel;
    private final TagConfigurationPanel tagConfigurationPanel;
    private final MetadataConfigurationPanel metadataConfigurationPanel;
    private final RenameConfigurationPanel renameConfigurationPanel;
    private final LoggingConfigurationPanel loggingConfigurationPanel;

    private JSplitPane splitPane;

    private JButton okButton;
    private JButton applyButton;
    private JButton cancelButton;

    private final Configuration configuration;
    private final Logger   logger;
    private final Language lang;

    public ConfigViewerGUI() {
        configuration = Config.getInstance().get();
        logger = Logger.getInstance();
        lang   = Language.getInstance();

        this.initiateWindow();

        loggingConfigurationPanel = new LoggingConfigurationPanel();
        updatesConfigurationPanel = new UpdatesConfigurationPanel();
        languageConfigurationPanel = new LanguageConfigurationPanel();
        renameConfigurationPanel = new RenameConfigurationPanel();
        thumbnailConfigurationPanel = new ThumbnailConfigurationPanel();
        tagConfigurationPanel = new TagConfigurationPanel();
        metadataConfigurationPanel = new MetadataConfigurationPanel();

        this.addListeners();
    }

    private void initiateWindow() {
        Rectangle sizeAndLocation = configuration.getgUI().getConfigViewer().getSizeAndLocation();

        this.setSize(sizeAndLocation.getSize());

        Point xyFromConfig = sizeAndLocation.getLocation();

        if (Screen.isVisibleOnScreen(sizeAndLocation)) {
            this.setLocation(xyFromConfig);
            this.setSize(sizeAndLocation.getSize());
        } else {
            logger.logERROR("Could not set location of Config Viewer GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");
            JOptionPane.showMessageDialog(null, lang.get("configviewer.window.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);

            this.setLocation(0,0);
            this.setSize(GUIDefaults.CONFIG_VIEWER_WIDTH, GUIDefaults.CONFIG_VIEWER_HEIGHT);
        }

        this.setLayout(new BorderLayout());

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
            logger.logERROR("Could not set desired Look And Feel for Config Viewer GUI");
            logger.logERROR("Below is the generated StackTrace");

            for(StackTraceElement element : e.getStackTrace()) {
                logger.logERROR(element.toString());
            }
        }
        this.getContentPane().add(this.initiateSplitPane(), BorderLayout.CENTER);
        this.getContentPane().add(this.createButtonPanel(), BorderLayout.SOUTH);

        this.setIconImage(IconLoader.getIcon(Icons.CONFIGURATION).getImage());
        this.setTitle(lang.get("configviewer.window.title"));
    }

    private JSplitPane initiateSplitPane() {
        backgroundsPanel = new JPanel(new BorderLayout());

        splitPane = new JSplitPane();
        splitPane.setOneTouchExpandable(true);
        splitPane.setDividerLocation(200);
        splitPane.add(this.initiateJTree(), JSplitPane.LEFT);
        splitPane.add(backgroundsPanel, JSplitPane.RIGHT);

        return splitPane;
    }

    private void addListeners(){
        this.addWindowListener(new WindowEventHandler());
        okButton.addActionListener(new OkButtonListener());
        applyButton.addActionListener(new ApplyButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        okButton     = new JButton(lang.get("common.button.ok.label"));
        applyButton  = new JButton(lang.get("common.button.apply.label"));
        cancelButton = new JButton(lang.get("common.button.cancel.label"));

        buttonPanel.add(okButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void updateWindowLocationAndSize() {
        GUIWindow configViewer = configuration.getgUI().getConfigViewer();

        Rectangle sizeAndLocation = new Rectangle();

        sizeAndLocation.setLocation(this.getLocation().x, this.getLocation().y);
        sizeAndLocation.setSize(this.getSize().width, this.getSize().height);

        configViewer.setSizeAndLocation(sizeAndLocation);
    }

    private JScrollPane initiateJTree() {
        tree = new JTree(ConfigViewerGUIUtil.createNodes());
        tree.setShowsRootHandles (true);
        tree.addMouseListener(new Mouselistener());

        return new JScrollPane(tree);
    }

    private boolean updateConfiguration() {

        if (!loggingConfigurationPanel.isValidConfiguration()) {
            return false;
        }

        if (!thumbnailConfigurationPanel.isValidConfiguration()) {
            return false;
        }

        if (!renameConfigurationPanel.isValidConfiguration()) {
            return false;
        }

        /**
         * Show configuration changes.
         */
        this.displayChangedConfigurationMessage();

        /**
         * Update Logging Configuration
         */
        loggingConfigurationPanel.updateConfiguration();

        /**
         * Update Updates Configuration
         */
        updatesConfigurationPanel.updateConfiguration();

        /**
         * Update Language Configuration
         */
        languageConfigurationPanel.updateConfiguration();

        /**
         * Update Rename Configuration
         */
        renameConfigurationPanel.updateConfiguration();

        /**
         * Update Thumbnail and ToolTips Configuration
         */
        thumbnailConfigurationPanel.updateConfiguration();

        /**
         * Update Tag Configuration
         */
        tagConfigurationPanel.updateConfiguration();

        /**
         * Update Metadata Configuration
         */
        metadataConfigurationPanel.updateConfiguration();

        return true;
    }

    private void displayChangedConfigurationMessage() {

        String preMessage = lang.get("configviewer.changed.configuration.start");
        String postMessage = lang.get("configviewer.changed.configuration.end");
        StringBuilder displayMessage = new StringBuilder();

        displayMessage.append(updatesConfigurationPanel.getChangedConfigurationMessage());
        displayMessage.append(thumbnailConfigurationPanel.getChangedConfigurationMessage());
        displayMessage.append(tagConfigurationPanel.getChangedConfigurationMessage());
        displayMessage.append(languageConfigurationPanel.getChangedConfigurationMessage());
        displayMessage.append(loggingConfigurationPanel.getChangedConfigurationMessage());
        displayMessage.append(renameConfigurationPanel.getChangedConfigurationMessage());

        if(displayMessage.length() > 0) {
            JOptionPane.showMessageDialog(this, preMessage + "\n\n" + displayMessage +  "\n" + postMessage, lang.get("errormessage.maingui.informationMessageLabel"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Performs the needed actions to be performed when the window is closing
     */
    private void closeWindow() {
        updateWindowLocationAndSize();
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Mouse listener
     */
    private class Mouselistener extends MouseAdapter{
        @Override
        public void mousePressed(MouseEvent e){

            int selRow = tree.getRowForLocation(e.getX(), e.getY());
            if(selRow > -1) {

                backgroundsPanel.removeAll();
                backgroundsPanel.updateUI();

                if (selRow == 1) {
                    backgroundsPanel.add(loggingConfigurationPanel);
                } else if (selRow == 2) {
                    backgroundsPanel.add(updatesConfigurationPanel);
                } else if (selRow == 3) {
                    backgroundsPanel.add(renameConfigurationPanel);
                } else if (selRow == 4) {
                    backgroundsPanel.add(languageConfigurationPanel);
                } else if (selRow == 5) {
                    backgroundsPanel.add(thumbnailConfigurationPanel);
                } else if (selRow == 6) {
                    backgroundsPanel.add(tagConfigurationPanel);
                } else if (selRow == 7) {
                    backgroundsPanel.add(metadataConfigurationPanel);
                }
            }
        }
    }

    private class OkButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (updateConfiguration()) {
                closeWindow();
            }
        }
    }

    private class ApplyButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateConfiguration();
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            closeWindow();
        }
    }

    private class WindowEventHandler extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent e) {
            updateWindowLocationAndSize();
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
    }
}