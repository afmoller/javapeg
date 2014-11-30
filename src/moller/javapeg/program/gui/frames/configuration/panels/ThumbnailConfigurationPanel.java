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
package moller.javapeg.program.gui.frames.configuration.panels;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.model.ToolTips;
import moller.javapeg.program.config.model.thumbnail.ThumbNail;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCache;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCreation;
import moller.javapeg.program.config.model.thumbnail.ThumbNailGrayFilter;
import moller.javapeg.program.gui.frames.configuration.panels.base.BaseConfigurationPanel;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.jpeg.JPEGThumbNailCache;
import moller.util.jpeg.JPEGScaleAlgorithm;
import moller.util.string.StringUtil;

public class ThumbnailConfigurationPanel extends BaseConfigurationPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JCheckBox createThumbnailIfMissingOrCorrupt;
    private JTextField thumbnailWidth;
    private JTextField thumbnailHeight;
    private JComboBox<JPEGScaleAlgorithm> thumbnailCreationAlgorithm;
    private JCheckBox enableThumbnailCache;
    private JLabel cacheSizeLabel;
    private JTextField maxCacheSize;
    private JButton clearCacheJButton;
    private JSlider percentageSlider;
    private JCheckBox brightenedCheckBox;
    private JRadioButton overviewToolTipDisabled;
    private JRadioButton overviewToolTipEnabled;
    private JRadioButton overviewToolTipExtended;
    private JRadioButton imageSearchResultToolTipDisabled;
    private JRadioButton imageSearchResultToolTipEnabled;
    private JRadioButton imageSearchResultToolTipExtended;
    private JRadioButton overviewImageViewerToolTipDisabled;
    private JRadioButton overviewImageViewerToolTipEnabled;
    private JRadioButton overviewImageViewerToolTipExtended;

    @Override
    public boolean isValidConfiguration() {
        if(!validateThumbnailSize("width")) {
            return false;
        }

        if(!validateThumbnailSize("height")) {
            return false;
        }

        if(!validateThumbnailCacheMaxSize()) {
            return false;
        }
        return true;
    }

    @Override
    protected void addListeners() {
        thumbnailWidth.getDocument().addDocumentListener(new ThumbnailWidthJTextFieldListener());
        thumbnailHeight.getDocument().addDocumentListener(new ThumbnailHeightJTextFieldListener());
        createThumbnailIfMissingOrCorrupt.addChangeListener(new CreateThumbnailCheckBoxListener());
        maxCacheSize.getDocument().addDocumentListener(new ThumbnailMaxCacheSizeJTextFieldListener());
        clearCacheJButton.addActionListener(new ClearCacheButtonListener());
        enableThumbnailCache.addChangeListener(new EnableThumbnailCacheCheckBoxListener());
    }

    @Override
    protected void createPanel() {
        ThumbNail thumbNail = getConfiguration().getThumbNail();

        /**
         * Start of Thumbnail Creation Area
         */
        createThumbnailIfMissingOrCorrupt = new JCheckBox(getLang().get("configviewer.thumbnail.creation.label.missingOrCorrupt"));
        createThumbnailIfMissingOrCorrupt.setSelected(thumbNail.getCreation().getIfMissingOrCorrupt());

        JLabel thumbnailWidthLabel = new JLabel(getLang().get("configviewer.thumbnail.creation.label.thumbnail.width"));
        thumbnailWidth = new JTextField(Integer.toString(thumbNail.getCreation().getWidth()));
        thumbnailWidth.setColumns(5);
        thumbnailWidth.setEnabled(thumbNail.getCreation().getIfMissingOrCorrupt());

        JLabel thumbnailHeightLabel = new JLabel(getLang().get("configviewer.thumbnail.creation.label.thumbnail.height"));
        thumbnailHeight = new JTextField(Integer.toString(thumbNail.getCreation().getHeight()));
        thumbnailHeight.setColumns(5);
        thumbnailHeight.setEnabled(thumbNail.getCreation().getIfMissingOrCorrupt());

        JLabel thumbnailCreationMode = new JLabel(getLang().get("configviewer.thumbnail.creation.label.algorithm"));

        thumbnailCreationAlgorithm = new JComboBox<JPEGScaleAlgorithm>(JPEGScaleAlgorithm.values());
        thumbnailCreationAlgorithm.setSelectedItem(thumbNail.getCreation().getAlgorithm());
        thumbnailCreationAlgorithm.invalidate();
        thumbnailCreationAlgorithm.setEnabled(thumbNail.getCreation().getIfMissingOrCorrupt());

        JPanel thumbnailCreationPanel = new JPanel(new GridBagLayout());
        thumbnailCreationPanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.thumbnail.creation.label")));

        GBHelper posThumbnailCreationPanel = new GBHelper();

        thumbnailCreationPanel.add(createThumbnailIfMissingOrCorrupt, posThumbnailCreationPanel.expandW());
        thumbnailCreationPanel.add(Box.createVerticalStrut(5), posThumbnailCreationPanel.nextRow());
        thumbnailCreationPanel.add(thumbnailWidthLabel, posThumbnailCreationPanel.nextRow());
        thumbnailCreationPanel.add(thumbnailWidth, posThumbnailCreationPanel.nextCol());
        thumbnailCreationPanel.add(Box.createVerticalStrut(5), posThumbnailCreationPanel.nextRow());
        thumbnailCreationPanel.add(thumbnailHeightLabel, posThumbnailCreationPanel.nextRow());
        thumbnailCreationPanel.add(thumbnailHeight, posThumbnailCreationPanel.nextCol());
        thumbnailCreationPanel.add(Box.createVerticalStrut(5), posThumbnailCreationPanel.nextRow());
        thumbnailCreationPanel.add(thumbnailCreationMode, posThumbnailCreationPanel.nextRow());
        thumbnailCreationPanel.add(thumbnailCreationAlgorithm, posThumbnailCreationPanel.nextCol());
        thumbnailCreationPanel.add(Box.createVerticalGlue(), posThumbnailCreationPanel.nextRow().expandH());

        /**
         * Start of Thumbnail Cache Area
         */
        enableThumbnailCache = new JCheckBox(getLang().get("configviewer.thumbnail.cache.label.enable"));
        enableThumbnailCache.setSelected(thumbNail.getCache().getEnabled());

        JPEGThumbNailCache jptc = JPEGThumbNailCache.getInstance();
        JLabel cacheSizeLabelHeading = new JLabel(getLang().get("configviewer.thumbnail.cache.label.size") + ": ");

        cacheSizeLabel = new JLabel(Integer.toString(jptc.getCurrentSize()));
        cacheSizeLabel.setEnabled(thumbNail.getCache().getEnabled());

        JLabel cacheMaxSizeLabel = new JLabel(getLang().get("configviewer.thumbnail.cache.label.size.max") + ": ");

        maxCacheSize = new JTextField(6);
        maxCacheSize.setText(Integer.toString(thumbNail.getCache().getMaxSize()));
        maxCacheSize.setEnabled(thumbNail.getCache().getEnabled());

        JLabel clearCachLabel = new JLabel(getLang().get("configviewer.thumbnail.cache.label.clear"));

        clearCacheJButton = new JButton(IconLoader.getIcon(Icons.REMOVE));
        clearCacheJButton.setEnabled(thumbNail.getCache().getEnabled() && (jptc.getCurrentSize() > 0));

        JPanel thumbnailCachePanel = new JPanel(new GridBagLayout());
        thumbnailCachePanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.thumbnail.cache.label")));

        GBHelper posThumbnailCachePanel = new GBHelper();

        thumbnailCachePanel.add(enableThumbnailCache, posThumbnailCachePanel.expandW());
        thumbnailCachePanel.add(Box.createVerticalStrut(10), posThumbnailCachePanel.nextRow());

        thumbnailCachePanel.add(cacheSizeLabelHeading, posThumbnailCachePanel.nextRow());
        thumbnailCachePanel.add(cacheSizeLabel, posThumbnailCachePanel.nextCol());
        thumbnailCachePanel.add(Box.createVerticalStrut(5), posThumbnailCachePanel.nextRow());
        thumbnailCachePanel.add(cacheMaxSizeLabel, posThumbnailCachePanel.nextRow());
        thumbnailCachePanel.add(maxCacheSize, posThumbnailCachePanel.nextCol());
        thumbnailCachePanel.add(Box.createVerticalStrut(5), posThumbnailCachePanel.nextRow());
        thumbnailCachePanel.add(clearCachLabel, posThumbnailCachePanel.nextRow());
        thumbnailCachePanel.add(clearCacheJButton, posThumbnailCachePanel.nextCol());
        thumbnailCachePanel.add(Box.createVerticalGlue(), posThumbnailCachePanel.nextRow().expandH());

        /**
         * Start of Thumbnail Grayfilter Area
         */
        JLabel percentSliderLabel = new JLabel(getLang().get("configviewer.thumbnail.grayfilter.transparency.label"));

        percentageSlider = new JSlider(0, 100);
        percentageSlider.setLabelTable(percentageSlider.createStandardLabels(10));
        percentageSlider.setPaintLabels(true);
        percentageSlider.setValue(thumbNail.getGrayFilter().getPercentage());

        brightenedCheckBox = new JCheckBox(getLang().get("configviewer.thumbnail.grayfilter.increase.contrast.label"));
        brightenedCheckBox.setSelected(thumbNail.getGrayFilter().isPixelsBrightened());

        JPanel thumbnailGrayFilterPanel = new JPanel(new GridBagLayout());
        thumbnailGrayFilterPanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.thumbnail.grayfilter.selected.thumbnail.heading.label")));

        GBHelper posThumbnailGrayFilterPanel = new GBHelper();

        thumbnailGrayFilterPanel.add(percentSliderLabel, posThumbnailGrayFilterPanel);
        thumbnailGrayFilterPanel.add(percentageSlider, posThumbnailGrayFilterPanel.nextCol().expandW());
        thumbnailGrayFilterPanel.add(brightenedCheckBox, posThumbnailGrayFilterPanel.nextRow());
        thumbnailGrayFilterPanel.add(Box.createVerticalGlue(), posThumbnailGrayFilterPanel.nextRow().expandH());

        /**
         * Start of Thumbnail ToolTip Area
         */
        String disabled = getLang().get("configviewer.thumbnail.tooltip.label.disabled");
        String enabled = getLang().get("configviewer.thumbnail.tooltip.label.enabled");
        String extended = getLang().get("configviewer.thumbnail.tooltip.label.extended");

        overviewToolTipDisabled = new JRadioButton(disabled);
        overviewToolTipDisabled.setName("0");
        overviewToolTipEnabled = new JRadioButton(enabled);
        overviewToolTipEnabled.setName("1");
        overviewToolTipExtended = new JRadioButton(extended);
        overviewToolTipExtended.setName("2");

        ButtonGroup overviewGroup = new ButtonGroup();

        overviewGroup.add(overviewToolTipDisabled);
        overviewGroup.add(overviewToolTipEnabled);
        overviewGroup.add(overviewToolTipExtended);

        imageSearchResultToolTipDisabled = new JRadioButton(disabled);
        imageSearchResultToolTipDisabled.setName("0");
        imageSearchResultToolTipEnabled = new JRadioButton(enabled);
        imageSearchResultToolTipEnabled.setName("1");
        imageSearchResultToolTipExtended = new JRadioButton(extended);
        imageSearchResultToolTipExtended.setName("2");

        ButtonGroup imageSearchResultGroup = new ButtonGroup();

        imageSearchResultGroup.add(imageSearchResultToolTipDisabled);
        imageSearchResultGroup.add(imageSearchResultToolTipEnabled);
        imageSearchResultGroup.add(imageSearchResultToolTipExtended);

        overviewImageViewerToolTipDisabled = new JRadioButton(disabled);
        overviewImageViewerToolTipDisabled.setName("0");
        overviewImageViewerToolTipEnabled = new JRadioButton(enabled);
        overviewImageViewerToolTipEnabled.setName("1");
        overviewImageViewerToolTipExtended = new JRadioButton(extended);
        overviewImageViewerToolTipExtended.setName("2");

        ButtonGroup overviewImageViewerGroup = new ButtonGroup();

        overviewImageViewerGroup.add(overviewImageViewerToolTipDisabled);
        overviewImageViewerGroup.add(overviewImageViewerToolTipEnabled);
        overviewImageViewerGroup.add(overviewImageViewerToolTipExtended);

        ToolTips toolTips = getConfiguration().getToolTips();

        String imageSearcResultToolTipState = toolTips.getImageSearchResultState();
        String overviewImageViewerToolTipState = toolTips.getOverviewImageViewerState();
        String overviewToolTipState = toolTips.getOverviewState();

        if (imageSearcResultToolTipState.equalsIgnoreCase(imageSearchResultToolTipDisabled.getName())) {
            imageSearchResultToolTipDisabled.setSelected(true);
        } else if (imageSearcResultToolTipState.equalsIgnoreCase(imageSearchResultToolTipExtended.getName())) {
            imageSearchResultToolTipExtended.setSelected(true);
        } else {
            imageSearchResultToolTipEnabled.setSelected(true);
        }

        if (overviewImageViewerToolTipState.equalsIgnoreCase(overviewImageViewerToolTipDisabled.getName())) {
            overviewImageViewerToolTipDisabled.setSelected(true);
        } else if (overviewImageViewerToolTipState.equalsIgnoreCase(overviewImageViewerToolTipExtended.getName())) {
            overviewImageViewerToolTipExtended.setSelected(true);
        } else {
            overviewImageViewerToolTipEnabled.setSelected(true);
        }

        if (overviewToolTipState.equalsIgnoreCase(overviewToolTipDisabled.getName())) {
            overviewToolTipDisabled.setSelected(true);
        } else if (overviewToolTipState.equalsIgnoreCase(overviewToolTipExtended.getName())) {
            overviewToolTipExtended.setSelected(true);
        } else {
            overviewToolTipEnabled.setSelected(true);
        }

        GBHelper posThumbnailOverviewPanel = new GBHelper();
        JPanel thumbnailOverview = new JPanel(new GridBagLayout());
        thumbnailOverview.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.thumbnail.mainWindow.label")));
        thumbnailOverview.setPreferredSize(new Dimension(180, 120));

        thumbnailOverview.add(overviewToolTipDisabled, posThumbnailOverviewPanel);
        thumbnailOverview.add(overviewToolTipEnabled, posThumbnailOverviewPanel.nextRow().expandW());
        thumbnailOverview.add(overviewToolTipExtended, posThumbnailOverviewPanel.nextRow().expandW());
        thumbnailOverview.add(Box.createVerticalGlue(), posThumbnailOverviewPanel.nextRow().expandH());

        GBHelper posThumbnailImageSearchResultPanel = new GBHelper();
        JPanel thumbnailImageSerachResult = new JPanel(new GridBagLayout());
        thumbnailImageSerachResult.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.thumbnail.imageSearchResultWindow.label")));
        thumbnailImageSerachResult.setPreferredSize(new Dimension(180, 120));

        thumbnailImageSerachResult.add(imageSearchResultToolTipDisabled, posThumbnailImageSearchResultPanel.expandW());
        thumbnailImageSerachResult.add(imageSearchResultToolTipEnabled, posThumbnailImageSearchResultPanel.nextRow().expandW());
        thumbnailImageSerachResult.add(imageSearchResultToolTipExtended, posThumbnailImageSearchResultPanel.nextRow().expandW());
        thumbnailImageSerachResult.add(Box.createVerticalGlue(), posThumbnailImageSearchResultPanel.nextRow().expandH());

        GBHelper posThumbnailOverviewImageViewer = new GBHelper();
        JPanel thumbnailOverviewImageViewer = new JPanel(new GridBagLayout());
        thumbnailOverviewImageViewer.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.thumbnail.imageViewerWindow.label")));
        thumbnailOverviewImageViewer.setPreferredSize(new Dimension(180, 120));

        thumbnailOverviewImageViewer.add(overviewImageViewerToolTipDisabled, posThumbnailOverviewImageViewer.expandW());
        thumbnailOverviewImageViewer.add(overviewImageViewerToolTipEnabled, posThumbnailOverviewImageViewer.nextRow().expandW());
        thumbnailOverviewImageViewer.add(overviewImageViewerToolTipExtended, posThumbnailOverviewImageViewer.nextRow().expandW());
        thumbnailOverviewImageViewer.add(Box.createVerticalGlue(), posThumbnailOverviewImageViewer.nextRow().expandH());

        JPanel thumbnailToolTipPanel = new JPanel(new GridBagLayout());
        thumbnailToolTipPanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.thumbnail.tooltip.label")));

        GBHelper posThumbnailToolTipPanel = new GBHelper();

        thumbnailToolTipPanel.add(thumbnailOverview, posThumbnailToolTipPanel.expandH());
        thumbnailToolTipPanel.add(thumbnailImageSerachResult, posThumbnailToolTipPanel.nextCol().expandH());
        thumbnailToolTipPanel.add(thumbnailOverviewImageViewer, posThumbnailToolTipPanel.nextCol().expandH().expandW());

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        GBHelper posPanel = new GBHelper();

        add(thumbnailCreationPanel, posPanel.expandW().expandH());
        add(thumbnailCachePanel, posPanel.nextRow().expandW().expandH());
        add(thumbnailGrayFilterPanel, posPanel.nextRow().expandW().expandH());
        add(thumbnailToolTipPanel, posPanel.nextRow().expandW().expandH());
    }

    @Override
    public String getChangedConfigurationMessage() {
        StringBuilder displayMessage = new StringBuilder();

        ThumbNail thumbNail = getConfiguration().getThumbNail();

        ThumbNailCreation creation = thumbNail.getCreation();

        if(creation.getIfMissingOrCorrupt() != createThumbnailIfMissingOrCorrupt.isSelected()) {
            displayMessage .append(getLang().get("configviewer.thumbnail.creation.label.missingOrCorrupt") + ": " + createThumbnailIfMissingOrCorrupt.isSelected() + " (" + creation.getIfMissingOrCorrupt() + ")\n");
        }

        if(!creation.getWidth().equals(Integer.parseInt(thumbnailWidth.getText()))) {
            displayMessage.append(getLang().get("configviewer.thumbnail.creation.label.thumbnail.width") + ": " + thumbnailWidth.getText() + " (" + creation.getWidth() + ")\n");
        }

        if(!creation.getHeight().equals(Integer.parseInt(thumbnailHeight.getText()))) {
            displayMessage.append(getLang().get("configviewer.thumbnail.creation.label.thumbnail.height") + ": " + thumbnailHeight.getText() + " (" + creation.getHeight() + ")\n");
        }

        if(creation.getAlgorithm() != (JPEGScaleAlgorithm)thumbnailCreationAlgorithm.getSelectedItem()) {
            displayMessage.append(getLang().get("configviewer.thumbnail.creation.label.algorithm") + ": " + thumbnailCreationAlgorithm.getSelectedItem().toString() + " (" + creation.getAlgorithm() + ")\n");
        }

        ThumbNailCache cache = thumbNail.getCache();

        if(!cache.getMaxSize().equals(Integer.parseInt(maxCacheSize.getText()))) {
            displayMessage.append(getLang().get("configviewer.thumbnail.cache.label.size.max") + ": " + maxCacheSize.getText() + " (" + cache.getMaxSize() + ")\n");
        }

        if(cache.getEnabled() != enableThumbnailCache.isSelected()) {
            displayMessage.append(getLang().get("configviewer.thumbnail.cache.label.enable") + ": " + enableThumbnailCache.isSelected() + " (" + cache.getEnabled() + ")\n");
        }

        ToolTips toolTips = getConfiguration().getToolTips();

        if(!toolTips.getOverviewState().equals(getOverviewToolTipState())) {
            int previousThumbNailToolTipState = -1;

            try {
                previousThumbNailToolTipState = Integer.parseInt(toolTips.getOverviewState());
            } catch (NumberFormatException nfex) {
                previousThumbNailToolTipState = 1;
            }

            int currentThumbNailToolTipState = Integer.parseInt(getOverviewToolTipState());

            String previous = getTooltipStateAsLocalizedString(previousThumbNailToolTipState);
            String current = getTooltipStateAsLocalizedString(currentThumbNailToolTipState);

            displayMessage.append(getLang().get("configviewer.thumbnail.tooltip.label") + ": " + current + " (" + previous + ")\n");
        }

        if(!toolTips.getImageSearchResultState().equals(getImageSearchResultTooltipState())) {
            int previousThumbNailToolTipState = -1;

            try {
                previousThumbNailToolTipState = Integer.parseInt(toolTips.getImageSearchResultState());
            } catch (NumberFormatException nfex) {
                previousThumbNailToolTipState = 1;
            }

            int currentThumbNailToolTipState = Integer.parseInt(getImageSearchResultTooltipState());

            String previous = getTooltipStateAsLocalizedString(previousThumbNailToolTipState);
            String current = getTooltipStateAsLocalizedString(currentThumbNailToolTipState);

            displayMessage.append(getLang().get("configviewer.thumbnail.tooltip.label") + ": " + current + " (" + previous + ")\n");
        }

        if(!toolTips.getOverviewImageViewerState().equals(getOverviewImageViewerTooltipState())) {
            int previousThumbNailToolTipState = -1;

            try {
                previousThumbNailToolTipState = Integer.parseInt(toolTips.getOverviewImageViewerState());
            } catch (NumberFormatException nfex) {
                previousThumbNailToolTipState = 1;
            }

            int currentThumbNailToolTipState = Integer.parseInt(getOverviewImageViewerTooltipState());

            String previous = getTooltipStateAsLocalizedString(previousThumbNailToolTipState);
            String current = getTooltipStateAsLocalizedString(currentThumbNailToolTipState);

            displayMessage.append(getLang().get("configviewer.thumbnail.tooltip.label") + ": " + current + " (" + previous + ")\n");
        }

        ThumbNailGrayFilter grayFilter = thumbNail.getGrayFilter();

        if(!Integer.valueOf(grayFilter.getPercentage()).equals(percentageSlider.getValue())) {
            displayMessage.append(getLang().get("configviewer.thumbnail.grayfilter.transparency.label") + ": " + percentageSlider.getValue() + " (" + Integer.valueOf(grayFilter.getPercentage()) + ")\n");
        }

        if(grayFilter.isPixelsBrightened() != brightenedCheckBox.isSelected()) {
            displayMessage.append(getLang().get("configviewer.thumbnail.grayfilter.increase.contrast.label") + ": " + brightenedCheckBox.isSelected() + " (" + grayFilter.isPixelsBrightened() + ")\n");
        }

        return displayMessage.toString();
    }

    @Override
    public void updateConfiguration() {
        ThumbNail thumbNail = getConfiguration().getThumbNail();

        ThumbNailCache thumbNailCache = thumbNail.getCache();

        thumbNailCache.setEnabled(enableThumbnailCache.isSelected());
        thumbNailCache.setMaxSize(Integer.parseInt(maxCacheSize.getText()));

        ThumbNailCreation thumbNailCreation = thumbNail.getCreation();

        thumbNailCreation.setAlgorithm((JPEGScaleAlgorithm)thumbnailCreationAlgorithm.getSelectedItem());
        thumbNailCreation.setHeight(Integer.parseInt(thumbnailHeight.getText()));
        thumbNailCreation.setIfMissingOrCorrupt(createThumbnailIfMissingOrCorrupt.isSelected());
        thumbNailCreation.setWidth(Integer.parseInt(thumbnailWidth.getText()));

        ThumbNailGrayFilter thumbNailGrayFilter = thumbNail.getGrayFilter();

        thumbNailGrayFilter.setPercentage(percentageSlider.getValue());
        thumbNailGrayFilter.setPixelsBrightened(brightenedCheckBox.isSelected());

        ToolTips toolTips = getConfiguration().getToolTips();
        toolTips.setImageSearchResultState(getImageSearchResultTooltipState());
        toolTips.setOverviewImageViewerState(getOverviewImageViewerTooltipState());
        toolTips.setOverviewState(getOverviewToolTipState());
    }

    private class  ThumbnailWidthJTextFieldListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            validateThumbnailSize("width");
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
        }
    }

    private class  ThumbnailHeightJTextFieldListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            validateThumbnailSize("height");
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
        }
    }

    private class  ThumbnailMaxCacheSizeJTextFieldListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            validateThumbnailCacheMaxSize();
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
        }
    }

    private class CreateThumbnailCheckBoxListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            thumbnailWidth.setEnabled(createThumbnailIfMissingOrCorrupt.isSelected());
            thumbnailHeight.setEnabled(createThumbnailIfMissingOrCorrupt.isSelected());
            thumbnailCreationAlgorithm.setEnabled(createThumbnailIfMissingOrCorrupt.isSelected());
        }
    }

    private class EnableThumbnailCacheCheckBoxListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            maxCacheSize.setEnabled(enableThumbnailCache.isSelected());
            clearCacheJButton.setEnabled(enableThumbnailCache.isSelected());
            cacheSizeLabel.setEnabled(enableThumbnailCache.isSelected());
        }
    }

    private class ClearCacheButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JPEGThumbNailCache jptc = JPEGThumbNailCache.getInstance();

            int result = displayConfirmDialog(getLang().get("configviewer.thumbnail.cache.label.clear.question") + " (" + jptc.getCurrentSize() + ")", getLang().get("common.confirmation"), JOptionPane.OK_CANCEL_OPTION);

            if (result == 0) {
                jptc.clear();
                cacheSizeLabel.setText(Integer.toString(jptc.getCurrentSize()));
                clearCacheJButton.setEnabled(false);
            }
        }
    }

    private boolean validateThumbnailSize(String validatorFor) {

        String errorMessage = "";

        if (validatorFor.equals("width")) {
            String thumbnailWidthString = thumbnailWidth.getText();
            if(!StringUtil.isInt(thumbnailWidthString)) {
                errorMessage = getLang().get("configviewer.thumbnail.creation.validation.width.integer");
            }
            else if(!StringUtil.isInt(thumbnailWidthString, true)) {
                errorMessage = getLang().get("configviewer.thumbnail.creation.validation.width.integerNonNegative");
            }
        } else {
            String thumbnailHeightString = thumbnailHeight.getText();
            if(!StringUtil.isInt(thumbnailHeightString)) {
                errorMessage = getLang().get("configviewer.thumbnail.creation.validation.height.integer");
            }
            else if(!StringUtil.isInt(thumbnailHeightString, true)) {
                errorMessage = getLang().get("configviewer.thumbnail.creation.validation.height.integerNonNegative");
            }
        }
        if(errorMessage.length() > 0) {
            JOptionPane.showMessageDialog(this, errorMessage, getLang().get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateThumbnailCacheMaxSize() {
        if(!StringUtil.isInt(maxCacheSize.getText(), true)) {
            JOptionPane.showMessageDialog(this, getLang().get("configviewer.thumbnail.cache.validation.size.max"), getLang().get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private String getOverviewToolTipState() {
        if (overviewToolTipDisabled.isSelected()) {
            return overviewToolTipDisabled.getName();
        } else if (overviewToolTipEnabled.isSelected()) {
            return overviewToolTipEnabled.getName();
        } else {
            return overviewToolTipExtended.getName();
        }
    }

    private String getImageSearchResultTooltipState() {
        if (imageSearchResultToolTipDisabled.isSelected()) {
            return imageSearchResultToolTipDisabled.getName();
        } else if (imageSearchResultToolTipEnabled.isSelected()) {
            return imageSearchResultToolTipEnabled.getName();
        } else {
            return imageSearchResultToolTipExtended.getName();
        }
    }

    private String getOverviewImageViewerTooltipState() {
        if (overviewImageViewerToolTipDisabled.isSelected()) {
            return overviewImageViewerToolTipDisabled.getName();
        } else if (overviewImageViewerToolTipEnabled.isSelected()) {
            return overviewImageViewerToolTipEnabled.getName();
        } else {
            return overviewImageViewerToolTipExtended.getName();
        }
    }

    /**
     * This method returns a localized textual representation of an selected
     * thumbnail tooltip state.
     *
     * @param thumbNailToolTipState
     *            is the state to return as a localized string.
     * @return a localized textual representation of an int tooltip state.
     */
    private String getTooltipStateAsLocalizedString(int thumbNailToolTipState) {
        String disabled = getLang().get("configviewer.thumbnail.tooltip.label.disabled");
        String enabled = getLang().get("configviewer.thumbnail.tooltip.label.enabled");
        String extended = getLang().get("configviewer.thumbnail.tooltip.label.extended");

        switch (thumbNailToolTipState) {
        case 0:
            return disabled;
        case 1:
            return enabled;
        case 2:
            return extended;
        }
        return enabled;
    }
}
