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

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPane;
import moller.javapeg.program.enumerations.SplitPaneDividerSize;
import moller.javapeg.program.enumerations.TabPosition;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.javapeg.program.gui.frames.configuration.panels.base.BaseConfigurationPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Fredrik on 2015-04-25.
 */
public class GUIConfigurationPanel extends BaseConfigurationPanel {

    private JButton tabTextColorChooserButton;
    private JPanel tabTextColorPreviewPanel;

    private SplitPaneDividerThicknessComboBox treeToCenterComboBox;
    private SplitPaneDividerThicknessComboBox thumbNailsToTabsComboBox;
    private SplitPaneDividerThicknessComboBox thumbNailsToMetaDataComboBox;
    private SplitPaneDividerThicknessComboBox centerToImageListComboBox;

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
        GBHelper posBackgroundPanel = new GBHelper();

        this.setLayout(new GridBagLayout());
        this.add(createMainGUIConfigurationPanel(), posBackgroundPanel.expandW().expandH());
    }

    private JPanel createMainGUIConfigurationPanel() {
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        //TODO: Fix hard coded string
        backgroundPanel.setBorder(BorderFactory.createTitledBorder("Main Window"));

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(createTabsConfigurationPanel(), posBackgroundPanel.expandW());
        backgroundPanel.add(createSplitPanesConfigurationPanel(), posBackgroundPanel.nextRow().expandW());
        backgroundPanel.add(Box.createVerticalGlue(), posBackgroundPanel.nextRow().expandH());

        return backgroundPanel;
    }

    private JPanel createSplitPanesConfigurationPanel() {
        GUI gui = getConfiguration().getgUI();
        GUIWindow main = gui.getMain();

        JLabel treeToCenterLabel = new JLabel(getLang().get("configviewer.userinterface.main.dividersize.text"));
        treeToCenterComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        GUIWindowSplitPane mainGuiWindowSplitPane = main.getGUIWindowSplitPane(ConfigElement.MAIN.getElementValue());
        treeToCenterComboBox.setSelectedThickness(mainGuiWindowSplitPane.getDividerSize());

        JLabel thumbNailsToTabsLabel = new JLabel(getLang().get("configviewer.userinterface.thumbNailsToTabs.dividersize.text"));
        thumbNailsToTabsComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        GUIWindowSplitPane verticalGuiWindowSplitPane = main.getGUIWindowSplitPane(ConfigElement.VERTICAL.getElementValue());
        thumbNailsToTabsComboBox.setSelectedThickness(verticalGuiWindowSplitPane.getDividerSize());

        JLabel thumbNailsToMetaDataLabel = new JLabel(getLang().get("configviewer.userinterface.thumbNailsToMetaData.dividersize.text"));
        thumbNailsToMetaDataComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        GUIWindowSplitPane thumbNailsToMetaDataGuiWindowSplitPane = main.getGUIWindowSplitPane(ConfigElement.THUMB_NAIL_META_DATA_PANEL.getElementValue());
        thumbNailsToMetaDataComboBox.setSelectedThickness(thumbNailsToMetaDataGuiWindowSplitPane.getDividerSize());

        JLabel centerToImageListLabel = new JLabel(getLang().get("configviewer.userinterface.centerToImageList.dividersize.text"));
        centerToImageListComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        GUIWindowSplitPane centerToImageListGuiWindowSplitPane = main.getGUIWindowSplitPane(ConfigElement.MAIN_TO_IMAGELIST.getElementValue());
        centerToImageListComboBox.setSelectedThickness(centerToImageListGuiWindowSplitPane.getDividerSize());

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        //TODO: Fix hard coded string
        backgroundPanel.setBorder(BorderFactory.createTitledBorder("Split panes"));

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(treeToCenterLabel, posBackgroundPanel);
        backgroundPanel.add(treeToCenterComboBox, posBackgroundPanel.nextCol());
        backgroundPanel.add(thumbNailsToTabsLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(thumbNailsToTabsComboBox, posBackgroundPanel.nextCol());
        backgroundPanel.add(thumbNailsToMetaDataLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(thumbNailsToMetaDataComboBox, posBackgroundPanel.nextCol());
        backgroundPanel.add(centerToImageListLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(centerToImageListComboBox, posBackgroundPanel.nextCol());

        return backgroundPanel;
    }

    private JPanel createTabsConfigurationPanel() {

        //TODO: Fix hard coded string
        JLabel tabPositionLabel = new JLabel("Position Tabs:");

        JComboBox<TabPosition> tabPositionComboBox = new JComboBox<>();
        tabPositionComboBox.addItem(TabPosition.TOP);
        tabPositionComboBox.addItem(TabPosition.BOTTOM);

        //TODO: Fix hard coded string
        JLabel tabTextColorLabel = new JLabel("Tab text color:");

        tabTextColorPreviewPanel = new JPanel();

        //TODO: Fix hard coded string
        tabTextColorChooserButton = new JButton("Choose color");


        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        //TODO: Fix hard coded string
        backgroundPanel.setBorder(BorderFactory.createTitledBorder("Tabs"));

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(tabPositionLabel, posBackgroundPanel);
        backgroundPanel.add(tabPositionComboBox, posBackgroundPanel.nextCol().width(2));
        backgroundPanel.add(tabTextColorLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(tabTextColorPreviewPanel, posBackgroundPanel.nextCol());
        backgroundPanel.add(tabTextColorChooserButton, posBackgroundPanel.nextCol());

        return backgroundPanel;
    }

    @Override
    public String getChangedConfigurationMessage() {
        StringBuilder displayMessage = new StringBuilder();

        GUIWindow main = getConfiguration().getgUI().getMain();

        SplitPaneDividerSize mainConfigurationDividerSize = main.getGUIWindowSplitPane(ConfigElement.MAIN.getElementValue()).getDividerSize();
        SplitPaneDividerSize mainConfigurationGuiDividerSize = treeToCenterComboBox.getItemAt(treeToCenterComboBox.getSelectedIndex()).getSplitPaneDividerSize();
        if (mainConfigurationDividerSize != mainConfigurationGuiDividerSize) {
            displayMessage.append(getLang().get("configviewer.userinterface.main.dividersize.text") + ": " + getLang().get(mainConfigurationGuiDividerSize.getLocalizationKey()) + " (" + getLang().get(mainConfigurationDividerSize.getLocalizationKey()) + ")\n");
        }

        SplitPaneDividerSize verticalConfigurationDividerSize = main.getGUIWindowSplitPane(ConfigElement.VERTICAL.getElementValue()).getDividerSize();
        SplitPaneDividerSize verticalConfigurationGuiDividerSize = thumbNailsToTabsComboBox.getItemAt(thumbNailsToTabsComboBox.getSelectedIndex()).getSplitPaneDividerSize();
        if (verticalConfigurationDividerSize != verticalConfigurationGuiDividerSize) {
            displayMessage.append(getLang().get("configviewer.userinterface.thumbNailsToTabs.dividersize.text") + ": " + getLang().get(verticalConfigurationGuiDividerSize.getLocalizationKey()) + " (" + getLang().get(verticalConfigurationDividerSize.getLocalizationKey()) + ")\n");
        }

        SplitPaneDividerSize thumbNailsToMetaDataConfigurationDividerSize = main.getGUIWindowSplitPane(ConfigElement.THUMB_NAIL_META_DATA_PANEL.getElementValue()).getDividerSize();
        SplitPaneDividerSize thumbNailsToMetaDataConfigurationGuiDividerSize = thumbNailsToMetaDataComboBox.getItemAt(thumbNailsToMetaDataComboBox.getSelectedIndex()).getSplitPaneDividerSize();
        if (thumbNailsToMetaDataConfigurationDividerSize != thumbNailsToMetaDataConfigurationGuiDividerSize) {
            displayMessage.append(getLang().get("configviewer.userinterface.thumbNailsToMetaData.dividersize.text") + ": " + getLang().get(thumbNailsToMetaDataConfigurationGuiDividerSize.getLocalizationKey()) + " (" + getLang().get(thumbNailsToMetaDataConfigurationDividerSize.getLocalizationKey()) + ")\n");
        }

        SplitPaneDividerSize centerToImageListConfigurationDividerSize = main.getGUIWindowSplitPane(ConfigElement.MAIN_TO_IMAGELIST.getElementValue()).getDividerSize();
        SplitPaneDividerSize centerToImageListConfigurationGuiDividerSize = centerToImageListComboBox.getItemAt(centerToImageListComboBox.getSelectedIndex()).getSplitPaneDividerSize();
        if (centerToImageListConfigurationDividerSize != centerToImageListConfigurationGuiDividerSize) {
            displayMessage.append(getLang().get("configviewer.userinterface.centerToImageList.dividersize.text") + ": " + getLang().get(centerToImageListConfigurationGuiDividerSize.getLocalizationKey()) + " (" + getLang().get(centerToImageListConfigurationDividerSize.getLocalizationKey()) + ")\n");
        }

        return displayMessage.toString();
    }

    @Override
    public void updateConfiguration() {
        GUI gui = getConfiguration().getgUI();

        GUIWindow main = gui.getMain();
        main.getGUIWindowSplitPane(ConfigElement.MAIN.getElementValue()).setDividerSize(treeToCenterComboBox.getItemAt(treeToCenterComboBox.getSelectedIndex()).getSplitPaneDividerSize());
        main.getGUIWindowSplitPane(ConfigElement.VERTICAL.getElementValue()).setDividerSize(thumbNailsToTabsComboBox.getItemAt(thumbNailsToTabsComboBox.getSelectedIndex()).getSplitPaneDividerSize());
        main.getGUIWindowSplitPane(ConfigElement.THUMB_NAIL_META_DATA_PANEL.getElementValue()).setDividerSize(thumbNailsToMetaDataComboBox.getItemAt(thumbNailsToMetaDataComboBox.getSelectedIndex()).getSplitPaneDividerSize());
        main.getGUIWindowSplitPane(ConfigElement.MAIN_TO_IMAGELIST.getElementValue()).setDividerSize(centerToImageListComboBox.getItemAt(centerToImageListComboBox.getSelectedIndex()).getSplitPaneDividerSize());
    }

    private class TabTextColorChooserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO: Fix hard coded string
            Color selectedColor = JColorChooser.showDialog(null, "Choose a Color", null);
            if (selectedColor != null) {
                tabTextColorPreviewPanel.setBackground(selectedColor);
            }
        }
    }
}
