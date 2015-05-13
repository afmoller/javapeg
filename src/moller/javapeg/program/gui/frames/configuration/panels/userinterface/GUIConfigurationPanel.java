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
import moller.javapeg.program.enumerations.TabPosition;
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
        backgroundPanel.setBorder(BorderFactory.createTitledBorder("Main Window"));

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(createTabsConfigurationPanel(), posBackgroundPanel.expandW());
        backgroundPanel.add(createSplitPanesConfigurationPanel(), posBackgroundPanel.nextRow().expandW());
        backgroundPanel.add(Box.createVerticalGlue(), posBackgroundPanel.nextRow().expandH());

        return backgroundPanel;
    }

    private JPanel createSplitPanesConfigurationPanel() {
        JLabel treeToCenter = new JLabel("Tree to Center:");
        SplitPaneDividerThicknessComboBox treeToCenterComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        JLabel thumbNailsToTabs = new JLabel("Thumbnails to Tabs:");
        SplitPaneDividerThicknessComboBox thumbNailsToTabsComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        JLabel thumbNailsToMetaData = new JLabel("Thumbnails to Meta data:");
        SplitPaneDividerThicknessComboBox thumbNailsToMetaDataComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        JLabel centerToImageList = new JLabel("Center to image list:");
        SplitPaneDividerThicknessComboBox centerToImageListComboBox = new SplitPaneDividerThicknessComboBox(getLang());

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createTitledBorder("Split panes"));

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(treeToCenter, posBackgroundPanel);
        backgroundPanel.add(treeToCenterComboBox, posBackgroundPanel.nextCol());
        backgroundPanel.add(thumbNailsToTabs, posBackgroundPanel.nextRow());
        backgroundPanel.add(thumbNailsToTabsComboBox, posBackgroundPanel.nextCol());
        backgroundPanel.add(thumbNailsToMetaData, posBackgroundPanel.nextRow());
        backgroundPanel.add(thumbNailsToMetaDataComboBox, posBackgroundPanel.nextCol());
        backgroundPanel.add(centerToImageList, posBackgroundPanel.nextRow());
        backgroundPanel.add(centerToImageListComboBox, posBackgroundPanel.nextCol());

        return backgroundPanel;
    }

    private JPanel createTabsConfigurationPanel() {

        JLabel tabPositionLabel = new JLabel("Position Tabs:");

        JComboBox<TabPosition> tabPositionComboBox = new JComboBox<>();
        tabPositionComboBox.addItem(TabPosition.TOP);
        tabPositionComboBox.addItem(TabPosition.BOTTOM);

        JLabel tabTextColorLabel = new JLabel("Tab text color:");

        tabTextColorPreviewPanel = new JPanel();

        tabTextColorChooserButton = new JButton("Choose color");


        JPanel backgroundPanel = new JPanel(new GridBagLayout());
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
        return null;
    }

    @Override
    public void updateConfiguration() {

    }

    private class TabTextColorChooserButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Color selectedColor = JColorChooser.showDialog(null, "Choose a Color", null);
            if (selectedColor != null) {
                tabTextColorPreviewPanel.setBackground(selectedColor);
            }
        }
    }
}
