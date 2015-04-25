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
package moller.javapeg.program.gui.panel;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.categories.CategoryUtil;
import moller.javapeg.program.categories.ImportedCategoryTreeAndDisplayJavaPegID;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPane;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPaneUtil;
import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesCategories;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContext;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContextSearchParameters;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContextUtil;
import moller.javapeg.program.datatype.FileAndTimeStampPair;
import moller.javapeg.program.enumerations.MetaDataValueFieldName;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.javapeg.program.gui.checktree.CategoryCheckTreeUtil;
import moller.javapeg.program.gui.checktree.CheckTreeManager;
import moller.javapeg.program.gui.frames.ImageRepositoryStatisticsViewer;
import moller.javapeg.program.gui.frames.ImageSearchResultViewer;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.gui.metadata.MetaDataValueSelectionDialog;
import moller.javapeg.program.gui.metadata.impl.MetaDataValue;
import moller.javapeg.program.gui.metadata.impl.MetaDataValueSelectionDialogEqual;
import moller.javapeg.program.gui.metadata.impl.MetaDataValueSelectionDialogLessEqualGreater;
import moller.javapeg.program.language.Language;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.*;
import java.util.List;

/**
 * This class constructs the search images panel in which it is possible to
 * search for images which are part of the {@link ImageMetaDataContext}.
 *
 * Created by Fredrik on 2015-04-24.
 */
public class ImageSearchTab extends JPanel {

    private JButton searchImagesButton;
    private JButton displayImageRepositoryStatisticsViewerButton;
    private JButton clearCategoriesSelectionButton;
    private JButton clearAllMetaDataParameters;

    private MetaDataValue yearMetaDataValue;
    private MetaDataValue monthMetaDataValue;
    private MetaDataValue dayMetaDataValue;
    private MetaDataValue hourMetaDataValue;
    private MetaDataValue minuteMetaDataValue;
    private MetaDataValue secondMetaDataValue;
    private MetaDataValue imagesSizeMetaDataValue;
    private MetaDataValue isoMetaDataValue;
    private MetaDataValue shutterSpeedMetaDataValue;
    private MetaDataValue apertureValueMetaDataValue;
    private MetaDataValue cameraModelMetaDataValue;

    private JCheckBox[] ratingCheckBoxes;
    private JTextArea commentTextArea;

    private JRadioButton andRadioButton;
    private JRadioButton orRadioButton;

    private Map<String, CheckTreeManager> javaPegIdToCheckTreeManager;

    private JProgressBar imageMetaDataContextLoadingProgressBar;

    private List<ButtonGroup> importedButtonGroups;

    private JSplitPane imageExifMetaDataToRatingCommentAndButtonPanelSplitPane;
    private JSplitPane categoriesToImageExifMetaDataAndRatingCommentAndButtonPanelSplitPane;

    private static Configuration configuration;
    private static Language lang;

    public ImageSearchTab() {
        configuration = Config.getInstance().get();
        lang = Language.getInstance();

        this.createPanel();
        this.addListeners();
    }

    private void createPanel() {

        GUI gUI = configuration.getgUI();

        GUIWindow mainGUI = gUI.getMain();
        List<GUIWindowSplitPane> guiWindowSplitPanes = mainGUI.getGuiWindowSplitPane();

        imageExifMetaDataToRatingCommentAndButtonPanelSplitPane = new JSplitPane();
        imageExifMetaDataToRatingCommentAndButtonPanelSplitPane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.IMAGE_SEARCH_IMAGE_EXIF_META_DATA_TO_RATING_COMMENT_AND_BUTTON));
        imageExifMetaDataToRatingCommentAndButtonPanelSplitPane.setLeftComponent(this.createImageExifMeteDataPanel());
        imageExifMetaDataToRatingCommentAndButtonPanelSplitPane.setRightComponent(this.createRatingCommentAndButtonPanel());

        categoriesToImageExifMetaDataAndRatingCommentAndButtonPanelSplitPane = new JSplitPane();
        categoriesToImageExifMetaDataAndRatingCommentAndButtonPanelSplitPane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.IMAGE_SEARCH_CATEGORIES_TO_IMAGE_EXIF_META_DATA_AND_RATING_COMMENT_AND_BUTTON));
        categoriesToImageExifMetaDataAndRatingCommentAndButtonPanelSplitPane.setLeftComponent(this.createCategoriesPanel());
        categoriesToImageExifMetaDataAndRatingCommentAndButtonPanelSplitPane.setRightComponent(imageExifMetaDataToRatingCommentAndButtonPanelSplitPane);

        GBHelper posBackgroundPanel = new GBHelper();

        this.setLayout(new GridBagLayout());
        this.add(categoriesToImageExifMetaDataAndRatingCommentAndButtonPanelSplitPane, posBackgroundPanel.expandH().expandW());
    }

    public int getImageExifMetaDataToRatingCommentAndButtonPanelSplitPaneDividerLocation() {
        return imageExifMetaDataToRatingCommentAndButtonPanelSplitPane.getDividerLocation();
    }

    public int getCategoriesToImageExifMetaDataAndRatingCommentAndButtonPanelSplitPaneDividerLocation() {
        return categoriesToImageExifMetaDataAndRatingCommentAndButtonPanelSplitPane.getDividerLocation();
    }

    public void addListeners() {
        searchImagesButton.addActionListener(new SearchImagesListener());
        displayImageRepositoryStatisticsViewerButton.addActionListener(new DisplayImageRepositoryStatisticsViewerListener());
        clearCategoriesSelectionButton.addActionListener(new ClearCategoriesSelectionListener());
        clearAllMetaDataParameters.addActionListener(new ClearAllMetaDataParametersListener());
    }

    public boolean isOrRadioButtonSelected() {
        return orRadioButton.isSelected();
    }

    private JPanel createCategoriesPanel() {

        andRadioButton = new JRadioButton(lang.get("findimage.categories.andRadioButton.label"));
        andRadioButton.setToolTipText(lang.get("findimage.categories.andRadioButton.tooltip"));
        orRadioButton = new JRadioButton(lang.get("findimage.categories.orRadioButton.label"));
        orRadioButton.setToolTipText(lang.get("findimage.categories.orRadioButton.tooltip"));

        ButtonGroup group = new ButtonGroup();

        group.add(andRadioButton);
        group.add(orRadioButton);

        TagImages tagImages = configuration.getTagImages();

        TagImagesCategories tagImagesCategories = tagImages.getCategories();

        if (tagImagesCategories.getOrRadioButtonIsSelected()) {
            orRadioButton.setSelected(true);
        } else {
            andRadioButton.setSelected(true);
        }

        clearCategoriesSelectionButton = new JButton();
        clearCategoriesSelectionButton.setToolTipText(lang.get("findimage.categories.clearCategoriesSelectionButton.label"));
        clearCategoriesSelectionButton.setIcon(IconLoader.getIcon(Icons.REMOVE));

        JPanel selectionModePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));

        selectionModePanel.add(andRadioButton);
        selectionModePanel.add(orRadioButton);
        selectionModePanel.add(clearCategoriesSelectionButton);

        JTree categoriesTree = CategoryUtil.createCategoriesTree();
        ((DefaultTreeCellRenderer)categoriesTree.getCellRenderer()).setLeafIcon(null);

        Map<String, ImportedCategoryTreeAndDisplayJavaPegID> importedCategoriesTrees = CategoryUtil.createImportedCategoriesTree();

        CheckTreeManager checkTreeManagerForFindImagesCategoryTree = new CheckTreeManager(categoriesTree, false, null, false);
        checkTreeManagerForFindImagesCategoryTree.setSelectionEnabled(true);

        javaPegIdToCheckTreeManager = new HashMap<>(importedCategoriesTrees.size() + 1);
        javaPegIdToCheckTreeManager.put(configuration.getJavapegClientId(), checkTreeManagerForFindImagesCategoryTree);

        JScrollPane categoriesScrollPane = new JScrollPane();
        categoriesScrollPane.getViewport().add(categoriesTree);

        GBHelper posCategoryTreeAndSelectionMode = new GBHelper();
        JPanel categoryTreeAndSelectionModePanel = new JPanel(new GridBagLayout());

        categoryTreeAndSelectionModePanel.add(categoriesScrollPane, posCategoryTreeAndSelectionMode.expandH().expandW());
        categoryTreeAndSelectionModePanel.add(Box.createVerticalStrut(2), posCategoryTreeAndSelectionMode.nextRow());
        categoryTreeAndSelectionModePanel.add(selectionModePanel, posCategoryTreeAndSelectionMode.nextRow());

        JLabel findInCategoriesLabel = new JLabel(lang.get("findimage.categories.label"));
        findInCategoriesLabel.setForeground(Color.GRAY);

        GBHelper posBackground = new GBHelper();
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));
        backgroundPanel.add(findInCategoriesLabel, posBackground);

        posBackground.fill = GridBagConstraints.BOTH;

        if (importedCategoriesTrees.size() > 0) {
            JTabbedPane categoriesTabbedPane = new JTabbedPane();
            categoriesTabbedPane.add(lang.get("category.mineCategoriesTab"), categoryTreeAndSelectionModePanel);

            Set<String> displayNames = new TreeSet<>(importedCategoriesTrees.keySet());

            importedButtonGroups = new ArrayList<>(importedCategoriesTrees.size());

            for (String displayName : displayNames) {

                ImportedCategoryTreeAndDisplayJavaPegID importedCategoryTree = importedCategoriesTrees.get(displayName);

                ((DefaultTreeCellRenderer)importedCategoryTree.getCategoriesTree().getCellRenderer()).setLeafIcon(null);

                CheckTreeManager checkTreeManager = new CheckTreeManager(importedCategoryTree.getCategoriesTree(), false, null, false);
                checkTreeManager.setSelectionEnabled(true);

                String importedJavaPegId = importedCategoryTree.getJavaPegId();

                javaPegIdToCheckTreeManager.put(importedJavaPegId, checkTreeManager);

                JScrollPane scrollPane = new JScrollPane();
                scrollPane.getViewport().add(importedCategoriesTrees.get(displayName).getCategoriesTree());

                JRadioButton andRadioButton = new JRadioButton(lang.get("findimage.categories.andRadioButton.label"));
                andRadioButton.setToolTipText(lang.get("findimage.categories.andRadioButton.tooltip"));
                andRadioButton.setActionCommand("AND");
                andRadioButton.setName(importedJavaPegId);

                JRadioButton orRadioButton = new JRadioButton(lang.get("findimage.categories.orRadioButton.label"));
                orRadioButton.setToolTipText(lang.get("findimage.categories.orRadioButton.tooltip"));
                orRadioButton.setActionCommand("OR");
                orRadioButton.setName(importedJavaPegId);
                orRadioButton.setSelected(true);

                ButtonGroup importedGroup = new ButtonGroup();

                importedGroup.add(andRadioButton);
                importedGroup.add(orRadioButton);

                importedButtonGroups.add(importedGroup);

                JButton importedClearCategoriesSelectionButton = new JButton(IconLoader.getIcon(Icons.REMOVE));
                importedClearCategoriesSelectionButton.setToolTipText(lang.get("findimage.categories.clearCategoriesSelectionButton.label"));
                importedClearCategoriesSelectionButton.setActionCommand(importedJavaPegId);
                importedClearCategoriesSelectionButton.addActionListener(new ImportedClearCategoriesSelectionListener());

                JPanel importedSelectionModePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));

                importedSelectionModePanel.add(andRadioButton);
                importedSelectionModePanel.add(orRadioButton);
                importedSelectionModePanel.add(importedClearCategoriesSelectionButton);


                GBHelper posImportedCategoryTreeAndSelectionMode = new GBHelper();
                JPanel importedCategoryTreeAndSelectionModePanel = new JPanel(new GridBagLayout());

                importedCategoryTreeAndSelectionModePanel.add(scrollPane, posImportedCategoryTreeAndSelectionMode.expandH().expandW());
                importedCategoryTreeAndSelectionModePanel.add(Box.createVerticalStrut(2), posImportedCategoryTreeAndSelectionMode.nextRow());
                importedCategoryTreeAndSelectionModePanel.add(importedSelectionModePanel, posImportedCategoryTreeAndSelectionMode.nextRow());

                categoriesTabbedPane.add(displayName, importedCategoryTreeAndSelectionModePanel);
            }
            backgroundPanel.add(categoriesTabbedPane, posBackground.nextRow().expandH().expandW());
        } else {
            backgroundPanel.add(categoryTreeAndSelectionModePanel, posBackground.nextRow().expandH().expandW());
        }

        return backgroundPanel;
    }

    private JPanel createImageExifMeteDataPanel() {
        JPanel backgroundPanel = new JPanel(new GridBagLayout());

        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        GBHelper posBackgroundPanel = new GBHelper();

        JLabel yearLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.YEAR.toString()));
        JLabel monthLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.MONTH.toString()));
        JLabel dayLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.DAY.toString()));
        JLabel hourLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.HOUR.toString()));
        JLabel minuteLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.MINUTE.toString()));
        JLabel secondLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.SECOND.toString()));
        JLabel imageSizeLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.IMAGE_SIZE.toString()));
        JLabel isoLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.ISO.toString()));
        JLabel cameraModelLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.CAMERA_MODEL.toString()));
        JLabel shutterSpeedLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.EXPOSURE_TIME.toString()));
        JLabel apertureValueLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.APERTURE_VALUE.toString()));

        yearMetaDataValue = new MetaDataValue(MetaDataValueFieldName.YEAR.toString());
        yearMetaDataValue.setEnabled(false);
        monthMetaDataValue = new MetaDataValue(MetaDataValueFieldName.MONTH.toString());
        monthMetaDataValue.setEnabled(false);
        dayMetaDataValue = new MetaDataValue(MetaDataValueFieldName.DAY.toString());
        dayMetaDataValue.setEnabled(false);
        hourMetaDataValue = new MetaDataValue(MetaDataValueFieldName.HOUR.toString());
        hourMetaDataValue.setEnabled(false);
        minuteMetaDataValue = new MetaDataValue(MetaDataValueFieldName.MINUTE.toString());
        minuteMetaDataValue.setEnabled(false);
        secondMetaDataValue = new MetaDataValue(MetaDataValueFieldName.SECOND.toString());
        secondMetaDataValue.setEnabled(false);
        imagesSizeMetaDataValue = new MetaDataValue(MetaDataValueFieldName.IMAGE_SIZE.toString());
        imagesSizeMetaDataValue.setEnabled(false);
        isoMetaDataValue = new MetaDataValue(MetaDataValueFieldName.ISO.toString());
        isoMetaDataValue.setEnabled(false);
        shutterSpeedMetaDataValue = new MetaDataValue(MetaDataValueFieldName.EXPOSURE_TIME.toString());
        shutterSpeedMetaDataValue.setEnabled(false);
        apertureValueMetaDataValue = new MetaDataValue(MetaDataValueFieldName.APERTURE_VALUE.toString());
        apertureValueMetaDataValue.setEnabled(false);
        cameraModelMetaDataValue = new MetaDataValue(MetaDataValueFieldName.CAMERA_MODEL.toString());
        cameraModelMetaDataValue.setEnabled(false);

        final int size = 5;

        JLabel findInMetaDataExifLabel = new JLabel(lang.get("findimage.imagemetadata.label"));
        findInMetaDataExifLabel.setForeground(Color.GRAY);

        backgroundPanel.add(findInMetaDataExifLabel, posBackgroundPanel);
        backgroundPanel.add(yearLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(imageSizeLabel, posBackgroundPanel.nextCol());
        backgroundPanel.add(yearMetaDataValue, posBackgroundPanel.nextRow().expandH().expandW());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(imagesSizeMetaDataValue, posBackgroundPanel.nextCol().expandW());
        backgroundPanel.add(monthLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(isoLabel, posBackgroundPanel.nextCol());
        backgroundPanel.add(monthMetaDataValue, posBackgroundPanel.nextRow().expandH().expandW());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(isoMetaDataValue, posBackgroundPanel.nextCol().expandW());
        backgroundPanel.add(dayLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(shutterSpeedLabel, posBackgroundPanel.nextCol());
        backgroundPanel.add(dayMetaDataValue, posBackgroundPanel.nextRow().expandH().expandW());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(shutterSpeedMetaDataValue, posBackgroundPanel.nextCol().expandW());
        backgroundPanel.add(hourLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(apertureValueLabel, posBackgroundPanel.nextCol());
        backgroundPanel.add(hourMetaDataValue, posBackgroundPanel.nextRow().expandH().expandW());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(apertureValueMetaDataValue, posBackgroundPanel.nextCol().expandW());
        backgroundPanel.add(minuteLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(cameraModelLabel, posBackgroundPanel.nextCol());
        backgroundPanel.add(minuteMetaDataValue, posBackgroundPanel.nextRow().expandH().expandW());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(cameraModelMetaDataValue, posBackgroundPanel.nextCol().expandW());
        backgroundPanel.add(secondLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(secondMetaDataValue, posBackgroundPanel.nextRow().expandH().expandW());

        return backgroundPanel;
    }

    private JPanel createRatingCommentAndButtonPanel() {

        JLabel findInRatingLabel = new JLabel(lang.get("findimage.rating.label"));
        findInRatingLabel.setForeground(Color.GRAY);

        GBHelper posRatingPanel = new GBHelper();
        JPanel ratingPanel = new JPanel(new GridBagLayout());
        ratingPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));
        ratingPanel.add(findInRatingLabel, posRatingPanel);

        ratingCheckBoxes = new JCheckBox[6];

        for (int i = 0; i < ratingCheckBoxes.length; i++) {
            if (i == 0) {
                ratingPanel.add(ratingCheckBoxes[i] = new JCheckBox(lang.get("findimage.rating.label.unrated")), posRatingPanel.nextRow());
            } else {
                ratingPanel.add(ratingCheckBoxes[i] = new JCheckBox(Integer.toString(i)), posRatingPanel.nextCol());
            }
        }

        ratingPanel.add(new JPanel(), posRatingPanel.nextCol().expandW());

        JLabel commentLabel = new JLabel(lang.get("findimage.comment.label"));
        commentLabel.setForeground(Color.GRAY);

        GBHelper posCommentPanel = new GBHelper();
        JPanel commentPanel = new JPanel(new GridBagLayout());
        commentPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));
        commentPanel.add(commentLabel, posCommentPanel);

        commentTextArea = new JTextArea();
        commentTextArea.setLineWrap(true);
        commentTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(commentTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        commentPanel.add(scrollPane, posCommentPanel.nextRow().expandH().expandW());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setBorder(BorderFactory.createTitledBorder(""));

        displayImageRepositoryStatisticsViewerButton = new JButton(IconLoader.getIcon(Icons.STATISTICS));
        displayImageRepositoryStatisticsViewerButton.setToolTipText(lang.get("findimage.searchImages.initializing.imagecontext.tooltip"));
        displayImageRepositoryStatisticsViewerButton.setEnabled(false);

        clearAllMetaDataParameters = new JButton(IconLoader.getIcon(Icons.REMOVE));
        clearAllMetaDataParameters.setToolTipText(lang.get("findimage.clearAllMetaDataParameters.tooltip"));

        searchImagesButton = new JButton(IconLoader.getIcon(Icons.FIND));
        searchImagesButton.setToolTipText(lang.get("findimage.searchImages.initializing.imagecontext.tooltip"));
        searchImagesButton.setEnabled(false);

        buttonPanel.add(searchImagesButton);
        buttonPanel.add(clearAllMetaDataParameters);
        buttonPanel.add(displayImageRepositoryStatisticsViewerButton);

        imageMetaDataContextLoadingProgressBar = new JProgressBar();
        imageMetaDataContextLoadingProgressBar.setStringPainted(true);
        imageMetaDataContextLoadingProgressBar.setVisible(true);

        GBHelper posBackground = new GBHelper();
        JPanel backgroundPanel = new JPanel(new GridBagLayout());

        backgroundPanel.add(ratingPanel, posBackground.expandW());
        backgroundPanel.add(commentPanel, posBackground.nextRow().expandH().expandW());
        backgroundPanel.add(buttonPanel, posBackground.nextRow().expandW());
        backgroundPanel.add(imageMetaDataContextLoadingProgressBar, posBackground.nextRow().expandW());

        return backgroundPanel;
    }

    public void setImageMetaDataContextLoadingProgressBarValue(int imageMetaDataContextLoadingProgressBarValue) {
        imageMetaDataContextLoadingProgressBar.setValue(imageMetaDataContextLoadingProgressBarValue);
    }

    private class ClearCategoriesSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            javaPegIdToCheckTreeManager.get(configuration.getJavapegClientId()).getSelectionModel().clearSelection();
        }
    }

    private class DisplayImageRepositoryStatisticsViewerListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ImageRepositoryStatisticsViewer imageRepositoryStatisticsViewer = new ImageRepositoryStatisticsViewer();
            imageRepositoryStatisticsViewer.setVisible(true);
        }
    }

    private class ImportedClearCategoriesSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CheckTreeManager checkTreeManager = javaPegIdToCheckTreeManager.get(e.getActionCommand());

            if (checkTreeManager != null) {
                checkTreeManager.getSelectionModel().clearSelection();
            }
        }
    }

    private class ClearAllMetaDataParametersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (javaPegIdToCheckTreeManager != null) {
                for (CheckTreeManager checkTreeManager : javaPegIdToCheckTreeManager.values()) {
                    checkTreeManager.getSelectionModel().clearSelection();
                }
            }

            yearMetaDataValue.clearValue();
            monthMetaDataValue.clearValue();
            dayMetaDataValue.clearValue();
            hourMetaDataValue.clearValue();
            minuteMetaDataValue.clearValue();
            secondMetaDataValue.clearValue();
            imagesSizeMetaDataValue.clearValue();
            isoMetaDataValue.clearValue();
            shutterSpeedMetaDataValue.clearValue();
            apertureValueMetaDataValue.clearValue();
            cameraModelMetaDataValue.clearValue();

            for (JCheckBox ratingCheckBox : ratingCheckBoxes) {
                ratingCheckBox.setSelected(false);
            }

            commentTextArea.setText("");
        }
    }

    public void activateFieldsAfterMetaDataContextLoadingFinished() {
        // hide the progress bar...
        imageMetaDataContextLoadingProgressBar.setVisible(false);

        searchImagesButton.setEnabled(true);
        searchImagesButton.setToolTipText(lang.get("findimage.searchImages.tooltip"));

        displayImageRepositoryStatisticsViewerButton.setEnabled(true);
        displayImageRepositoryStatisticsViewerButton.setToolTipText(lang.get("findimage.displayImageRepositoryStatisticsViewer.tooltip"));

        MetaDataTextFieldListener mdtl = new MetaDataTextFieldListener();

        yearMetaDataValue.setEnabled(true);
        yearMetaDataValue.setMouseListener(mdtl);
        monthMetaDataValue.setEnabled(true);
        monthMetaDataValue.setMouseListener(mdtl);
        dayMetaDataValue.setEnabled(true);
        dayMetaDataValue.setMouseListener(mdtl);
        hourMetaDataValue.setEnabled(true);
        hourMetaDataValue.setMouseListener(mdtl);
        minuteMetaDataValue.setEnabled(true);
        minuteMetaDataValue.setMouseListener(mdtl);
        secondMetaDataValue.setEnabled(true);
        secondMetaDataValue.setMouseListener(mdtl);
        imagesSizeMetaDataValue.setEnabled(true);
        imagesSizeMetaDataValue.setMouseListener(mdtl);
        isoMetaDataValue.setEnabled(true);
        isoMetaDataValue.setMouseListener(mdtl);
        shutterSpeedMetaDataValue.setEnabled(true);
        shutterSpeedMetaDataValue.setMouseListener(mdtl);
        apertureValueMetaDataValue.setEnabled(true);
        apertureValueMetaDataValue.setMouseListener(mdtl);
        cameraModelMetaDataValue.setEnabled(true);
        cameraModelMetaDataValue.setMouseListener(mdtl);
    }

    private class SearchImagesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Prepare and perform the image search...
            Set<File> foundImages = ImageMetaDataContextUtil.performImageSearch(collectSearchParameters());

            if (foundImages.size() > 0) {
                // ... and create file and timestamp pairs...
                ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

                List<FileAndTimeStampPair> fileAndTimeStampPairs = new ArrayList<>(foundImages.size());
                for (File foundImage : foundImages) {
                    fileAndTimeStampPairs.add(new FileAndTimeStampPair(foundImage, imdc.getDateTime(foundImage)));
                }

                // ... and sort the file and timestamp pairs...
                Collections.sort(fileAndTimeStampPairs);

                // ... and create a new list with the file object from the
                // sorted file and timestamp pairs.
                List<File> foundImagesAsSortedList = new ArrayList<>(foundImages.size());
                for (FileAndTimeStampPair fileAndTimeStampPair : fileAndTimeStampPairs) {
                    foundImagesAsSortedList.add(fileAndTimeStampPair.getFile());
                }

                ImageSearchResultViewer imagesearchResultViewer = new ImageSearchResultViewer(foundImagesAsSortedList);
                imagesearchResultViewer.setVisible(true);
            } else {
                displayInformationMessage(lang.get("findimage.searchImages.result"));
            }
        }
    }

    private ImageMetaDataContextSearchParameters collectSearchParameters() {

        ImageMetaDataContextSearchParameters imdcsp = new ImageMetaDataContextSearchParameters();
        imdcsp.setJavaPegIdToCategoriesMap(getSelectedJavaPegIdToCategoriesMapFromTreeModels(javaPegIdToCheckTreeManager));
        imdcsp.setJavaPegToAndCategoriesSearch(getImportedAndCategoriesSearch(importedButtonGroups));

        imdcsp.setYear(yearMetaDataValue.getValue());
        imdcsp.setMonth(monthMetaDataValue.getValue());
        imdcsp.setDay(dayMetaDataValue.getValue());
        imdcsp.setHour(hourMetaDataValue.getValue());
        imdcsp.setMinute(minuteMetaDataValue.getValue());
        imdcsp.setSecond(secondMetaDataValue.getValue());
        imdcsp.setFNumber(apertureValueMetaDataValue.getValue());
        imdcsp.setCameraModel(cameraModelMetaDataValue.getValue());
        imdcsp.setComment(commentTextArea.getText());
        imdcsp.setImageSize(imagesSizeMetaDataValue.getValue());
        imdcsp.setIso(isoMetaDataValue.getValue());
        imdcsp.setRating(getSelectedRatings());
        imdcsp.setShutterSpeed(shutterSpeedMetaDataValue.getValue());

        return imdcsp;
    }

    private Map<String, Boolean> getImportedAndCategoriesSearch(List<ButtonGroup> importedButtonGroups) {
        Map<String, Boolean> javaPegIdToImportedAndCategories;

        // Set the state for this client "AND" button...
        javaPegIdToImportedAndCategories = new HashMap<>();
        javaPegIdToImportedAndCategories.put(configuration.getJavapegClientId(), andRadioButton.isSelected());

        // And set the state of the "AND" button for any imported categories.
        if (importedButtonGroups != null && importedButtonGroups.size() > 0) {

            for (ButtonGroup buttonGroup : importedButtonGroups) {

                Enumeration<AbstractButton> abstractButtons = buttonGroup.getElements();

                while (abstractButtons.hasMoreElements()) {
                    AbstractButton abstractButton = abstractButtons.nextElement();

                    if (abstractButton.isSelected()) {
                        javaPegIdToImportedAndCategories.put(abstractButton.getName(), abstractButton.getActionCommand().equals("AND"));
                    }
                }
            }
        }
        return javaPegIdToImportedAndCategories;
    }

    private boolean[] getSelectedRatings() {
        boolean allDeSelected = true;

        for (JCheckBox ratingCheckBox : ratingCheckBoxes) {
            if (ratingCheckBox.isSelected()) {
                allDeSelected = false;
            }
        }

        if (allDeSelected) {
            return null;
        } else {
            boolean[] selectedRatings = new boolean[6];

            for (int i = 0; i < ratingCheckBoxes.length; i++) {
                selectedRatings[i] = ratingCheckBoxes[i].isSelected();
            }
            return selectedRatings;
        }
    }

    private Map<String, Categories> getSelectedJavaPegIdToCategoriesMapFromTreeModels(Map<String, CheckTreeManager> javaPegIdToCheckTreeManager) {
        Map<String, Categories> javaPegIdToCategories = null;

        if (javaPegIdToCheckTreeManager != null && !javaPegIdToCheckTreeManager.isEmpty()) {
            javaPegIdToCategories = new HashMap<>();

            for (String javaPegId : javaPegIdToCheckTreeManager.keySet()) {
                javaPegIdToCategories.put(javaPegId, CategoryCheckTreeUtil.getSelectedCategoriesFromTreeModel(javaPegIdToCheckTreeManager.get(javaPegId)));
            }
        }
        return javaPegIdToCategories;
    }

    private void displayInformationMessage(String informationMessage) {
        JOptionPane.showMessageDialog(this, informationMessage, lang.get("common.information"), JOptionPane.INFORMATION_MESSAGE);
    }

    private class MetaDataTextFieldListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            MetaDataValueSelectionDialog mdvsd = null;

            ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();
            String value = ((JTextField)e.getSource()).getText();

            MetaDataValueFieldName mdtf = MetaDataValueFieldName.valueOf(((Component)e.getSource()).getName());

            String prefix = "metadata.field.name.";

            switch (mdtf) {
                case YEAR:
                    mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getYears()), value, e.getLocationOnScreen());
                    break;
                case MONTH:
                    mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getMonths()), value, e.getLocationOnScreen());
                    break;
                case DAY:
                    mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getDates()), value, e.getLocationOnScreen());
                    break;
                case HOUR:
                    mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getHours()), value, e.getLocationOnScreen());
                    break;
                case MINUTE:
                    mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getMinutes()), value, e.getLocationOnScreen());
                    break;
                case SECOND:
                    mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getSeconds()), value, e.getLocationOnScreen());
                    break;
                case APERTURE_VALUE:
                    mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getFNumberValues()), value, e.getLocationOnScreen());
                    break;
                case CAMERA_MODEL:
                    mdvsd = new MetaDataValueSelectionDialogEqual(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getCameraModels()), value, e.getLocationOnScreen());
                    break;
                case IMAGE_SIZE:
                    mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getImageSizeValues()), value, e.getLocationOnScreen());
                    break;
                case ISO:
                    mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getIsoValues()), value, e.getLocationOnScreen());
                    break;
                case EXPOSURE_TIME:
                    mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getExposureTimeValues()), value, e.getLocationOnScreen());
                    break;
            }
            mdvsd.collectSelectedValues();

            JTextField textField = (JTextField)e.getSource();

            textField.setText(mdvsd.getResult());
            textField.setToolTipText(mdvsd.getResult());
        }
    }
}
