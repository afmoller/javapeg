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
package moller.javapeg.program.gui.frames.configuration.panels.userinterface;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.GUI.splitpane.GUIWindowSplitPane;
import moller.javapeg.program.config.model.GUI.tab.GUITab;
import moller.javapeg.program.config.model.GUI.tab.GUITabs;
import moller.javapeg.program.config.model.GUI.tab.GUITabsUtil;
import moller.javapeg.program.enumerations.SplitPaneDividerSize;
import moller.javapeg.program.enumerations.TabPosition;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.javapeg.program.gui.frames.configuration.panels.base.BaseConfigurationPanel;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.util.color.ColorUtil;

/**
 * Created by Fredrik on 2015-04-25.
 */
public class GUIConfigurationPanel extends BaseConfigurationPanel {

    private static final long serialVersionUID = 1L;

    private JButton tabTextColorChooserButton;

    // Main Window
    private SplitPaneDividerThicknessComboBox treeToCenterComboBox;
    private SplitPaneDividerThicknessComboBox thumbNailsToTabsComboBox;
    private SplitPaneDividerThicknessComboBox thumbNailsToMetaDataComboBox;
    private SplitPaneDividerThicknessComboBox centerToImageListComboBox;

    // Image Viewer
    private SplitPaneDividerThicknessComboBox imageToMetaDataComboBox;

    private TabPositionComboBox tabPositionComboBox;

    @Override
    public boolean isValidConfiguration() {
        return true;
    }

    @Override
    protected void addListeners() {
        tabTextColorChooserButton.addActionListener(new TabTextColorChooserButtonListener());
    }

    @Override
    protected void createPanel() {

        JTabbedPane tabs = new JTabbedPane();
      //TODO: Fix hard coded string
        tabs.add("Main Window", createMainGUIConfigurationPanel());
      //TODO: Fix hard coded string
        tabs.add("Image Viewer", createImageViewerConfigurationPanel());

        GBHelper posBackgroundPanel = new GBHelper();

        this.setLayout(new GridBagLayout());
        this.add(tabs, posBackgroundPanel.expandW().expandH());
    }

    private JPanel createMainGUIConfigurationPanel() {
        JPanel backgroundPanel = new JPanel(new GridBagLayout());

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(createTabsConfigurationPanel(), posBackgroundPanel.expandW());
        backgroundPanel.add(createMainGUISplitPanesConfigurationPanel(), posBackgroundPanel.nextRow().expandW());
        backgroundPanel.add(Box.createVerticalGlue(), posBackgroundPanel.nextRow().expandH());

        return backgroundPanel;
    }

    private JPanel createImageViewerConfigurationPanel() {
        JPanel backgroundPanel = new JPanel(new GridBagLayout());

        GBHelper posBackgroundPanel = new GBHelper();
        backgroundPanel.add(createImageViewerSplitPanesConfigurationPanel(), posBackgroundPanel.expandW());
        backgroundPanel.add(Box.createVerticalGlue(), posBackgroundPanel.nextRow().expandH());
        return backgroundPanel;
    }

    private JPanel createMainGUISplitPanesConfigurationPanel() {
        GUI gui = getConfiguration().getgUI();
        GUIWindow main = gui.getMain();

        JLabel treeToCenterLabel = new JLabel(getLang().get("configviewer.userinterface.main.dividersize.text"));
        treeToCenterLabel.setForeground(new Color(34, 177, 76));
        treeToCenterComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        GUIWindowSplitPane mainGuiWindowSplitPane = main.getGUIWindowSplitPane(ConfigElement.MAIN.getElementValue());
        treeToCenterComboBox.setSelectedThickness(mainGuiWindowSplitPane.getDividerSize());

        JLabel thumbNailsToTabsLabel = new JLabel(getLang().get("configviewer.userinterface.thumbNailsToTabs.dividersize.text"));
        thumbNailsToTabsLabel.setForeground(new Color(63, 72, 204));
        thumbNailsToTabsComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        GUIWindowSplitPane verticalGuiWindowSplitPane = main.getGUIWindowSplitPane(ConfigElement.VERTICAL.getElementValue());
        thumbNailsToTabsComboBox.setSelectedThickness(verticalGuiWindowSplitPane.getDividerSize());

        JLabel thumbNailsToMetaDataLabel = new JLabel(getLang().get("configviewer.userinterface.thumbNailsToMetaData.dividersize.text"));
        thumbNailsToMetaDataLabel.setForeground(new Color(163, 73, 164));
        thumbNailsToMetaDataComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        GUIWindowSplitPane thumbNailsToMetaDataGuiWindowSplitPane = main.getGUIWindowSplitPane(ConfigElement.THUMB_NAIL_META_DATA_PANEL.getElementValue());
        thumbNailsToMetaDataComboBox.setSelectedThickness(thumbNailsToMetaDataGuiWindowSplitPane.getDividerSize());

        JLabel centerToImageListLabel = new JLabel(getLang().get("configviewer.userinterface.centerToImageList.dividersize.text"));
        centerToImageListLabel.setForeground(new Color(0, 162, 232));
        centerToImageListComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        GUIWindowSplitPane centerToImageListGuiWindowSplitPane = main.getGUIWindowSplitPane(ConfigElement.MAIN_TO_IMAGELIST.getElementValue());
        centerToImageListComboBox.setSelectedThickness(centerToImageListGuiWindowSplitPane.getDividerSize());

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        //TODO: Fix hard coded string
        backgroundPanel.setBorder(BorderFactory.createTitledBorder("Split panes"));

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(treeToCenterLabel, posBackgroundPanel.expandW());
        backgroundPanel.add(Box.createHorizontalStrut(10), posBackgroundPanel.nextCol());
        backgroundPanel.add(treeToCenterComboBox, posBackgroundPanel.nextCol());
        backgroundPanel.add(Box.createHorizontalStrut(10), posBackgroundPanel.nextCol());
        backgroundPanel.add(new JLabel(IconLoader.getIcon(Icons.CONFIG_GUI_MAIN_GUI_SPLITPANES)), posBackgroundPanel.nextCol().height(7));
        backgroundPanel.add(Box.createVerticalStrut(5), posBackgroundPanel.nextRow());
        backgroundPanel.add(thumbNailsToTabsLabel, posBackgroundPanel.nextRow().expandW());
        backgroundPanel.add(Box.createHorizontalStrut(10), posBackgroundPanel.nextCol());
        backgroundPanel.add(thumbNailsToTabsComboBox, posBackgroundPanel.nextCol());
        backgroundPanel.add(Box.createVerticalStrut(5), posBackgroundPanel.nextRow());
        backgroundPanel.add(thumbNailsToMetaDataLabel, posBackgroundPanel.nextRow().expandW());
        backgroundPanel.add(Box.createHorizontalStrut(10), posBackgroundPanel.nextCol());
        backgroundPanel.add(thumbNailsToMetaDataComboBox, posBackgroundPanel.nextCol());
        backgroundPanel.add(Box.createVerticalStrut(5), posBackgroundPanel.nextRow());
        backgroundPanel.add(centerToImageListLabel, posBackgroundPanel.nextRow().expandW());
        backgroundPanel.add(Box.createHorizontalStrut(10), posBackgroundPanel.nextCol());
        backgroundPanel.add(centerToImageListComboBox, posBackgroundPanel.nextCol());

        return backgroundPanel;
    }

    private JPanel createImageViewerSplitPanesConfigurationPanel() {
        GUI gui = getConfiguration().getgUI();
        GUIWindow imageViewer = gui.getImageViewer();

        JLabel imageToMetaDataLabel = new JLabel(getLang().get("configviewer.userinterface.imageviewer.image.to.metadata.dividersize.text"));
        imageToMetaDataLabel.setForeground(new Color(34, 177, 76));
        imageToMetaDataComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        GUIWindowSplitPane imageViewerWindowSplitPane = imageViewer.getGUIWindowSplitPane(ConfigElement.IMAGE_META_DATA.getElementValue());
        imageToMetaDataComboBox.setSelectedThickness(imageViewerWindowSplitPane.getDividerSize());

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        //TODO: Fix hard coded string
        backgroundPanel.setBorder(BorderFactory.createTitledBorder("Split panes"));

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(imageToMetaDataLabel, posBackgroundPanel.expandW());
        backgroundPanel.add(Box.createHorizontalStrut(10), posBackgroundPanel.nextCol());
        backgroundPanel.add(imageToMetaDataComboBox, posBackgroundPanel.nextCol());
        backgroundPanel.add(Box.createHorizontalStrut(10), posBackgroundPanel.nextCol());
        backgroundPanel.add(new JLabel(IconLoader.getIcon(Icons.CONFIG_GUI_MAIN_GUI_SPLITPANES)), posBackgroundPanel.nextCol().height(7));

        return backgroundPanel;
    }

    private JPanel createTabsConfigurationPanel() {

        GUITab mainGUIApplicationModeTabs = GUITabsUtil.getGUITab(getConfiguration().getgUITabs().getGuiTabs(), ConfigElement.MAIN_GUI_APPLICATION_MODE_TABS.getElementValue());

        JLabel tabPositionLabel = new JLabel(getLang().get("configviewer.userinterface.tabPosition.label.text"));
        tabPositionComboBox = new TabPositionComboBox(getLang());
        tabPositionComboBox.setSelectedPosition(mainGUIApplicationModeTabs.getPosition());

        JLabel tabTextColorLabel = new JLabel(getLang().get("configviewer.userinterface.tabTextColor.label.text"));

        //TODO: Fix hard coded string
        tabTextColorChooserButton = new JButton("Choose color");
        tabTextColorChooserButton.setForeground(ColorUtil.getColorFromRGBString(mainGUIApplicationModeTabs.getTextColor()));

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        //TODO: Fix hard coded string
        backgroundPanel.setBorder(BorderFactory.createTitledBorder("Tabs"));

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(tabPositionLabel, posBackgroundPanel.expandW());
        backgroundPanel.add(tabPositionComboBox, posBackgroundPanel.nextCol().width(2));
        backgroundPanel.add(tabTextColorLabel, posBackgroundPanel.nextRow().expandW());
        backgroundPanel.add(tabTextColorChooserButton, posBackgroundPanel.nextCol());

        return backgroundPanel;
    }

    @Override
    public String getChangedConfigurationMessage() {
        StringBuilder displayMessage = new StringBuilder();

        GUI gUI = getConfiguration().getgUI();

        GUIWindow main = gUI.getMain();
        appendConfigurationDisplayMessage(displayMessage, main, ConfigElement.MAIN, treeToCenterComboBox, "configviewer.userinterface.main.dividersize.text");
        appendConfigurationDisplayMessage(displayMessage, main, ConfigElement.VERTICAL, thumbNailsToTabsComboBox, "configviewer.userinterface.thumbNailsToTabs.dividersize.text");
        appendConfigurationDisplayMessage(displayMessage, main, ConfigElement.THUMB_NAIL_META_DATA_PANEL, thumbNailsToMetaDataComboBox, "configviewer.userinterface.thumbNailsToMetaData.dividersize.text");
        appendConfigurationDisplayMessage(displayMessage, main, ConfigElement.MAIN_TO_IMAGELIST, centerToImageListComboBox, "configviewer.userinterface.centerToImageList.dividersize.text");

        GUIWindow imageViewer = gUI.getImageViewer();
        appendConfigurationDisplayMessage(displayMessage, imageViewer, ConfigElement.IMAGE_META_DATA, imageToMetaDataComboBox, "configviewer.userinterface.imageviewer.image.to.metadata.dividersize.text");

        TabPosition configurationGuiTabPosition = tabPositionComboBox.getItemAt(tabPositionComboBox.getSelectedIndex()).getTabPosition();

        GUITab mainGUIApplicationModeTabs = GUITabsUtil.getGUITab(getConfiguration().getgUITabs().getGuiTabs(), ConfigElement.MAIN_GUI_APPLICATION_MODE_TABS.getElementValue());

        TabPosition configurationTabPosition = mainGUIApplicationModeTabs.getPosition();

        if (configurationGuiTabPosition != configurationTabPosition) {
            displayMessage.append(getLang().get("configviewer.userinterface.tabPosition.label.text") + ": " + getLang().get(configurationGuiTabPosition.getLocalizationKey()) + " (" + getLang().get(configurationTabPosition.getLocalizationKey()) + ")\n");
        }

        String mainGUIApplicationModeTabstextColorFromConfiguration = mainGUIApplicationModeTabs.getTextColor();
        String mainGUIApplicationModeTabstextColorFromConfigurationGUI = ColorUtil.getColorAsRGBString(tabTextColorChooserButton.getForeground());

        if (!mainGUIApplicationModeTabstextColorFromConfigurationGUI.equals(mainGUIApplicationModeTabstextColorFromConfiguration)) {
            displayMessage.append(getLang().get("configviewer.userinterface.tabTextColor.label.text") + ": " + "new: " + mainGUIApplicationModeTabstextColorFromConfigurationGUI + " (" + "old" + mainGUIApplicationModeTabstextColorFromConfiguration + ")\n");
        }

        return displayMessage.toString();
    }

    private void appendConfigurationDisplayMessage(StringBuilder displayMessage, GUIWindow guiWindow, ConfigElement configElement, SplitPaneDividerThicknessComboBox splitPaneDividerThicknessComboBox, String languageKey) {
        SplitPaneDividerSize configurationDividerSize = getSplitPaneDividerSizeFromConfiguration(guiWindow, configElement);
        SplitPaneDividerSize configurationGuiDividerSize = getSplitPaneDividerSizeFromConfigurationGui(splitPaneDividerThicknessComboBox);
        if (configurationDividerSize != configurationGuiDividerSize) {
            displayMessage.append(getLang().get(languageKey) + ": " + getLang().get(configurationGuiDividerSize.getLocalizationKey()) + " (" + getLang().get(configurationDividerSize.getLocalizationKey()) + ")\n");
        }
    }

    private SplitPaneDividerSize getSplitPaneDividerSizeFromConfiguration(GUIWindow guiWindow, ConfigElement configElement) {
        return guiWindow.getGUIWindowSplitPane(configElement.getElementValue()).getDividerSize();
    }

    private SplitPaneDividerSize getSplitPaneDividerSizeFromConfigurationGui(SplitPaneDividerThicknessComboBox splitPaneDividerThicknessComboBox) {
        return splitPaneDividerThicknessComboBox.getItemAt(splitPaneDividerThicknessComboBox.getSelectedIndex()).getSplitPaneDividerSize();
    }

    @Override
    public void updateConfiguration() {
        GUI gui = getConfiguration().getgUI();

        GUIWindow main = gui.getMain();
        main.getGUIWindowSplitPane(ConfigElement.MAIN.getElementValue()).setDividerSize(treeToCenterComboBox.getItemAt(treeToCenterComboBox.getSelectedIndex()).getSplitPaneDividerSize());
        main.getGUIWindowSplitPane(ConfigElement.VERTICAL.getElementValue()).setDividerSize(thumbNailsToTabsComboBox.getItemAt(thumbNailsToTabsComboBox.getSelectedIndex()).getSplitPaneDividerSize());
        main.getGUIWindowSplitPane(ConfigElement.THUMB_NAIL_META_DATA_PANEL.getElementValue()).setDividerSize(thumbNailsToMetaDataComboBox.getItemAt(thumbNailsToMetaDataComboBox.getSelectedIndex()).getSplitPaneDividerSize());
        main.getGUIWindowSplitPane(ConfigElement.MAIN_TO_IMAGELIST.getElementValue()).setDividerSize(centerToImageListComboBox.getItemAt(centerToImageListComboBox.getSelectedIndex()).getSplitPaneDividerSize());

        GUIWindow imageViewer = gui.getImageViewer();
        imageViewer.getGUIWindowSplitPane(ConfigElement.IMAGE_META_DATA.getElementValue()).setDividerSize(imageToMetaDataComboBox.getItemAt(imageToMetaDataComboBox.getSelectedIndex()).getSplitPaneDividerSize());

        GUITabs guiTabs = getConfiguration().getgUITabs();
        GUITab mainGuiApplicationModeGuiTab = GUITabsUtil.getGUITab(guiTabs.getGuiTabs(), ConfigElement.MAIN_GUI_APPLICATION_MODE_TABS.getElementValue());
        mainGuiApplicationModeGuiTab.setPosition(tabPositionComboBox.getItemAt(tabPositionComboBox.getSelectedIndex()).getTabPosition());
        mainGuiApplicationModeGuiTab.setTextColor(ColorUtil.getColorAsRGBString(tabTextColorChooserButton.getForeground()));
    }

    private class TabTextColorChooserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JColorChooser.setDefaultLocale(Locale.getDefault());

            // TODO: Fix hard coded string
            Color selectedColor = JColorChooser.showDialog(null, "Choose a Color", null);
            if (selectedColor != null) {
                tabTextColorChooserButton.setForeground(selectedColor);
            }
        }
    }
}
