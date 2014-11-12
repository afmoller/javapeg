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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.categories.CategoryUtil;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.ConfigUtil;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.ToolTips;
import moller.javapeg.program.config.model.UpdatesChecker;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesCategories;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPaths;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPreview;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.javapeg.program.config.model.repository.RepositoryExceptions;
import moller.javapeg.program.config.model.thumbnail.ThumbNail;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCache;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCreation;
import moller.javapeg.program.config.model.thumbnail.ThumbNailGrayFilter;
import moller.javapeg.program.gui.CustomizedJTable;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.gui.frames.configuration.panels.LoggingConfigurationPanel;
import moller.javapeg.program.gui.frames.configuration.panels.MetadataConfigurationPanel;
import moller.javapeg.program.gui.frames.configuration.panels.RenameConfigurationPanel;
import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.javapeg.program.jpeg.JPEGThumbNailCache;
import moller.javapeg.program.language.ISO639;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.model.ImageRepositoriesTableModel;
import moller.javapeg.program.model.ModelInstanceLibrary;
import moller.util.gui.Screen;
import moller.util.image.ImageUtil;
import moller.util.io.Status;
import moller.util.io.StreamUtil;
import moller.util.java.SystemProperties;
import moller.util.jpeg.JPEGScaleAlgorithm;
import moller.util.string.StringUtil;

public class ConfigViewerGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTree tree;

    private JPanel backgroundsPanel;

    private JPanel updatesConfigurationPanel;
    private JPanel languageConfigurationPanel;
    private JPanel thumbnailConfigurationPanel;
    private JPanel tagConfigurationPanel;
    private final MetadataConfigurationPanel metadataConfigurationPanel;
    private final RenameConfigurationPanel renameConfigurationPanel;
    private final LoggingConfigurationPanel loggingConfigurationPanel;

    private JSplitPane splitPane;

    private JButton okButton;
    private JButton applyButton;
    private JButton cancelButton;

    /**
     * Variables for the updates panel
     */
    private JCheckBox updatesEnabled;
    private JCheckBox sendVersionInformationEnabled;

    /**
     * Variables for the language panel
     */
    private JList<String> languageList;

    private JLabel currentLanguage;

    private JRadioButton manualRadioButton;
    private JRadioButton automaticRadioButton;

    /**
     * Variables for the thumbnail panel
     */
    private JCheckBox createThumbnailIfMissingOrCorrupt;
    private JTextField thumbnailWidth;
    private JTextField thumbnailHeight;
    private JComboBox<JPEGScaleAlgorithm> thumbnailCreationAlgorithm;
    private JTextField maxCacheSize;
    private JButton clearCacheJButton;
    private JLabel cacheSizeLabel;
    private JCheckBox enableThumbnailCache;
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

    /**
     * Variables for the tag panel
     */
    private JRadioButton addAutomaticallyRadioButton;
    private JRadioButton askToAddRadioButton;
    private JRadioButton doNotAddRadioButton;

    private JRadioButton useEmbeddedThumbnail;
    private JRadioButton useScaledThumbnail;

    private JCheckBox warnWhenRemoveCategory;
    private JCheckBox warnWhenRemoveCategoryWithSubCategories;

    private JButton removeSelectedImagePathsButton;

    private JList<ImportedCategories> importedCategoriesList;
    private JList<Object> imageRepositoriesAllwaysAddList;
    private JList<Object> imageRepositoriesNeverAddList;

    private JLabel imageRepositoriesMetaDataLabel;
    private CustomizedJTable imageRepositoriesTable;

    private JPopupMenu importedCategoriesPopupMenu;
    private ImportedCategories theImportedCategoriesToRenameOrDelete;

    private final Configuration configuration;
    private final Logger   logger;
    private final Language lang;

    // Configuration values read from configuration.

    private String GUI_LANGUAGE_ISO6391;
    private Integer THUMBNAIL_WIDTH;
    private Integer THUMBNAIL_HEIGHT;
    private JPEGScaleAlgorithm CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT_ALGORITHM;
    private Integer THUMBNAIL_MAX_CACHE_SIZE;
    private String OVERVIEW_THUMBNAIL_TOOLTIP_STATE;
    private String IMAGE_SEARCH_RESULT_THUMBNAIL_TOOLTIP_STATE;
    private String OVERVIEW_IMAGE_VIEWER_THUMBNAIL_TOOLTIP_STATE;
    private Integer ADD_TO_IMAGEREPOSITOY_POLICY;
    private Integer PERCENTAGE_SLIDER;
    private Boolean BRIGHTENED_CHECKBOX;
    private boolean UPDATE_CHECK_ENABLED;
    private boolean UPDATE_CHECK_ATTACH_VERSION;
    private boolean AUTOMATIC_LANGUAGE_SELECTION;
    private boolean CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT;
    private boolean ENABLE_THUMBNAIL_CACHE;
    private boolean USE_EMBEDDED_THUMBNAIL;
    private boolean WARN_WHEN_REMOVE_CATEGORY;
    private boolean WARN_WHEN_REMOVE_CATEGORY_WITH_SUB_CATEGORIES;

    public ConfigViewerGUI() {
        configuration = Config.getInstance().get();
        logger = Logger.getInstance();
        lang   = Language.getInstance();

        this.setStartupConfig();
        this.initiateWindow();

        loggingConfigurationPanel = new LoggingConfigurationPanel();

        this.createUpdateConfigurationPanel();
        this.createLanguageConfigurationPanel();

        renameConfigurationPanel = new RenameConfigurationPanel();

        this.createThumbnailConfigurationPanel();
        this.createTagConfigurationPanel();

        metadataConfigurationPanel = new MetadataConfigurationPanel();

        this.addListeners();
    }

    private void setStartupConfig() {
        UPDATE_CHECK_ENABLED = configuration.getUpdatesChecker().isEnabled();
        UPDATE_CHECK_ATTACH_VERSION = configuration.getUpdatesChecker().getAttachVersionInformation();
        AUTOMATIC_LANGUAGE_SELECTION = configuration.getLanguage().getAutomaticSelection();
        GUI_LANGUAGE_ISO6391 = configuration.getLanguage().getgUILanguageISO6391();
        CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT = configuration.getThumbNail().getCreation().getIfMissingOrCorrupt();
        THUMBNAIL_WIDTH = configuration.getThumbNail().getCreation().getWidth();
        THUMBNAIL_HEIGHT = configuration.getThumbNail().getCreation().getHeight();
        CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT_ALGORITHM = configuration.getThumbNail().getCreation().getAlgorithm();
        THUMBNAIL_MAX_CACHE_SIZE = configuration.getThumbNail().getCache().getMaxSize();
        ENABLE_THUMBNAIL_CACHE = configuration.getThumbNail().getCache().getEnabled();
        USE_EMBEDDED_THUMBNAIL = configuration.getTagImages().getPreview().getUseEmbeddedThumbnail();
        OVERVIEW_THUMBNAIL_TOOLTIP_STATE = configuration.getToolTips().getOverviewState();
        IMAGE_SEARCH_RESULT_THUMBNAIL_TOOLTIP_STATE = configuration.getToolTips().getImageSearchResultState();
        OVERVIEW_IMAGE_VIEWER_THUMBNAIL_TOOLTIP_STATE = configuration.getToolTips().getOverviewImageViewerState();
        WARN_WHEN_REMOVE_CATEGORY = configuration.getTagImages().getCategories().getWarnWhenRemove();
        WARN_WHEN_REMOVE_CATEGORY_WITH_SUB_CATEGORIES  = configuration.getTagImages().getCategories().getWarnWhenRemoveWithSubCategories();
        ADD_TO_IMAGEREPOSITOY_POLICY = configuration.getTagImages().getImagesPaths().getAddToRepositoryPolicy();
        PERCENTAGE_SLIDER = configuration.getThumbNail().getGrayFilter().getPercentage();
        BRIGHTENED_CHECKBOX = configuration.getThumbNail().getGrayFilter().isPixelsBrightened();
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

        InputStream imageStream = null;
        try {
            imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/configuration.gif");
            this.setIconImage(ImageIO.read(imageStream));
        } catch (Exception e) {
            Logger.getInstance().logERROR("Could not open the image Help16.gif");
        } finally {
            StreamUtil.close(imageStream, true);
        }
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
        manualRadioButton.addActionListener(new ManualRadioButtonListener());
        automaticRadioButton.addActionListener(new AutomaticRadioButtonListener());
        languageList.addListSelectionListener(new LanguageListListener());
        updatesEnabled.addChangeListener(new UpdatesEnabledCheckBoxListener());
        okButton.addActionListener(new OkButtonListener());
        applyButton.addActionListener(new ApplyButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());
        thumbnailWidth.getDocument().addDocumentListener(new ThumbnailWidthJTextFieldListener());
        thumbnailHeight.getDocument().addDocumentListener(new ThumbnailHeightJTextFieldListener());
        createThumbnailIfMissingOrCorrupt.addChangeListener(new CreateThumbnailCheckBoxListener());
        maxCacheSize.getDocument().addDocumentListener(new ThumbnailMaxCacheSizeJTextFieldListener());
        clearCacheJButton.addActionListener(new ClearCacheButtonListener());
        enableThumbnailCache.addChangeListener(new EnableThumbnailCacheCheckBoxListener());

        removeSelectedImagePathsButton.addActionListener(new RemoveSelectedImagePathsButtonListener());

        ModelInstanceLibrary.getInstance().getImageRepositoriesTableModel().addTableModelListener(new ImageRepositoriesTableModelListener());

        // Force an invocation of the registered listener if all the image
        // repositories already have been added to the model (which occurs when
        // the application is started).
        ModelInstanceLibrary.getInstance().getImageRepositoriesTableModel().fireTableDataChanged();
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

    private void createUpdateConfigurationPanel() {
        updatesConfigurationPanel = new JPanel(new GridBagLayout());
        updatesConfigurationPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), BorderFactory.createTitledBorder(lang.get("configviewer.tree.node.updates"))));

        GBHelper posUpdatesPanel = new GBHelper();

        UpdatesChecker updatesChecker = configuration.getUpdatesChecker();

        updatesEnabled = new JCheckBox(lang.get("configviewer.update.label.updateEnabled.text"));
        updatesEnabled.setSelected(updatesChecker.isEnabled());

        sendVersionInformationEnabled = new JCheckBox(lang.get("configviewer.update.label.attachVersionInformation.text"));
        sendVersionInformationEnabled.setSelected(updatesChecker.getAttachVersionInformation());
        sendVersionInformationEnabled.setEnabled(updatesEnabled.isSelected());

        updatesConfigurationPanel.add(updatesEnabled, posUpdatesPanel.expandW());
        updatesConfigurationPanel.add(sendVersionInformationEnabled, posUpdatesPanel.nextRow().expandW());
        updatesConfigurationPanel.add(Box.createVerticalGlue(), posUpdatesPanel.nextRow().expandH().expandW());
    }

    private void createLanguageConfigurationPanel() {
        languageConfigurationPanel = new JPanel(new GridBagLayout());

        manualRadioButton = new JRadioButton(lang.get("configviewer.language.radiobutton.manual"));
        automaticRadioButton = new JRadioButton(lang.get("configviewer.language.radiobutton.automatic"));

        ButtonGroup languageSelectionMode = new ButtonGroup();
        languageSelectionMode.add(manualRadioButton);
        languageSelectionMode.add(automaticRadioButton);

        moller.javapeg.program.config.model.Language language = configuration.getLanguage();

        if (language.getAutomaticSelection()) {
            if (!language.getgUILanguageISO6391().equals(SystemProperties.getUserLanguage())) {
                language.setgUILanguageISO6391(SystemProperties.getUserLanguage());
            }
        }

        JPanel selectionModePanel = new JPanel(new GridBagLayout());
        selectionModePanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.language.label.selectionMode")));

        GBHelper posSelectionMode = new GBHelper();

        selectionModePanel.add(manualRadioButton, posSelectionMode.expandW());
        selectionModePanel.add(automaticRadioButton, posSelectionMode.nextRow().expandW());
        selectionModePanel.add(Box.createVerticalGlue(), posSelectionMode.nextRow().expandH());

        languageList = new JList<String>(ConfigUtil.listLanguagesFiles());

        if (language.getAutomaticSelection()) {
            languageList.setEnabled(false);
        }

        JScrollPane languageListScrollPane = new JScrollPane(languageList);

        JPanel availableLanguagesPanel = new JPanel(new GridBagLayout());
        availableLanguagesPanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.language.label.availableLanguages")));

        GBHelper posAvailableLanguages = new GBHelper();

        availableLanguagesPanel.add(languageListScrollPane, posAvailableLanguages.expandW().expandH());

        currentLanguage = new JLabel(ConfigUtil.resolveCodeToLanguageName(language.getgUILanguageISO6391()));

        JPanel currentLanguagePanel = new JPanel(new GridBagLayout());
        currentLanguagePanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.language.label.currentLanguage")));

        GBHelper posCurrentLanguage = new GBHelper();

        currentLanguagePanel.add(currentLanguage, posCurrentLanguage.expandW());
        currentLanguagePanel.add(Box.createVerticalGlue(), posCurrentLanguage.nextRow().expandH());

        GBHelper posLanguagePanel = new GBHelper();

        languageConfigurationPanel.add(currentLanguagePanel, posLanguagePanel.expandW().expandH());
        languageConfigurationPanel.add(selectionModePanel, posLanguagePanel.nextRow().expandW().expandH());
        languageConfigurationPanel.add(availableLanguagesPanel, posLanguagePanel.nextRow().expandW().expandH());

        if (language.getAutomaticSelection()) {
            automaticRadioButton.setSelected(true);
        } else {
            manualRadioButton.setSelected(true);
        }
    }

    private void createThumbnailConfigurationPanel() {

        ThumbNail thumbNail = configuration.getThumbNail();

        /**
         * Start of Thumbnail Creation Area
         */
        createThumbnailIfMissingOrCorrupt = new JCheckBox(lang.get("configviewer.thumbnail.creation.label.missingOrCorrupt"));
        createThumbnailIfMissingOrCorrupt.setSelected(thumbNail.getCreation().getIfMissingOrCorrupt());

        JLabel thumbnailWidthLabel = new JLabel(lang.get("configviewer.thumbnail.creation.label.thumbnail.width"));
        thumbnailWidth = new JTextField(Integer.toString(thumbNail.getCreation().getWidth()));
        thumbnailWidth.setColumns(5);
        thumbnailWidth.setEnabled(thumbNail.getCreation().getIfMissingOrCorrupt());

        JLabel thumbnailHeightLabel = new JLabel(lang.get("configviewer.thumbnail.creation.label.thumbnail.height"));
        thumbnailHeight = new JTextField(Integer.toString(thumbNail.getCreation().getHeight()));
        thumbnailHeight.setColumns(5);
        thumbnailHeight.setEnabled(thumbNail.getCreation().getIfMissingOrCorrupt());

        JLabel thumbnailCreationMode = new JLabel(lang.get("configviewer.thumbnail.creation.label.algorithm"));

        thumbnailCreationAlgorithm = new JComboBox<JPEGScaleAlgorithm>(JPEGScaleAlgorithm.values());
        thumbnailCreationAlgorithm.setSelectedItem(thumbNail.getCreation().getAlgorithm());
        thumbnailCreationAlgorithm.invalidate();
        thumbnailCreationAlgorithm.setEnabled(thumbNail.getCreation().getIfMissingOrCorrupt());

        JPanel thumbnailCreationPanel = new JPanel(new GridBagLayout());
        thumbnailCreationPanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.thumbnail.creation.label")));

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
        enableThumbnailCache = new JCheckBox(lang.get("configviewer.thumbnail.cache.label.enable"));
        enableThumbnailCache.setSelected(thumbNail.getCache().getEnabled());

        JPEGThumbNailCache jptc = JPEGThumbNailCache.getInstance();
        JLabel cacheSizeLabelHeading = new JLabel(lang.get("configviewer.thumbnail.cache.label.size") + ": ");

        cacheSizeLabel = new JLabel(Integer.toString(jptc.getCurrentSize()));
        cacheSizeLabel.setEnabled(thumbNail.getCache().getEnabled());

        JLabel cacheMaxSizeLabel = new JLabel(lang.get("configviewer.thumbnail.cache.label.size.max") + ": ");

        maxCacheSize = new JTextField(6);
        maxCacheSize.setText(Integer.toString(thumbNail.getCache().getMaxSize()));
        maxCacheSize.setEnabled(thumbNail.getCache().getEnabled());

        JLabel clearCachLabel = new JLabel(lang.get("configviewer.thumbnail.cache.label.clear"));

        try {
            Icon cleanThumbNailCacheImageIcon = ImageUtil.getIcon(StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/remove.gif"), true);
            clearCacheJButton = new JButton(cleanThumbNailCacheImageIcon);
        } catch (IOException iox) {
            clearCacheJButton = new JButton("X");
            logger.logERROR("Could not set image resources/images/viewtab/remove.gif to clean thumbnail cache button." );
            logger.logERROR(iox);
        }

        clearCacheJButton.setEnabled(thumbNail.getCache().getEnabled() && (jptc.getCurrentSize() > 0));

        JPanel thumbnailCachePanel = new JPanel(new GridBagLayout());
        thumbnailCachePanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.thumbnail.cache.label")));

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
        JLabel percentSliderLabel = new JLabel(lang.get("configviewer.thumbnail.grayfilter.transparency.label"));

        percentageSlider = new JSlider(0, 100);
        percentageSlider.setLabelTable(percentageSlider.createStandardLabels(10));
        percentageSlider.setPaintLabels(true);
        percentageSlider.setValue(thumbNail.getGrayFilter().getPercentage());

        brightenedCheckBox = new JCheckBox(lang.get("configviewer.thumbnail.grayfilter.increase.contrast.label"));
        brightenedCheckBox.setSelected(thumbNail.getGrayFilter().isPixelsBrightened());

        JPanel thumbnailGrayFilterPanel = new JPanel(new GridBagLayout());
        thumbnailGrayFilterPanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.thumbnail.grayfilter.selected.thumbnail.heading.label")));

        GBHelper posThumbnailGrayFilterPanel = new GBHelper();

        thumbnailGrayFilterPanel.add(percentSliderLabel, posThumbnailGrayFilterPanel);
        thumbnailGrayFilterPanel.add(percentageSlider, posThumbnailGrayFilterPanel.nextCol().expandW());
        thumbnailGrayFilterPanel.add(brightenedCheckBox, posThumbnailGrayFilterPanel.nextRow());
        thumbnailGrayFilterPanel.add(Box.createVerticalGlue(), posThumbnailGrayFilterPanel.nextRow().expandH());

        /**
         * Start of Thumbnail ToolTip Area
         */
        String disabled = lang.get("configviewer.thumbnail.tooltip.label.disabled");
        String enabled = lang.get("configviewer.thumbnail.tooltip.label.enabled");
        String extended = lang.get("configviewer.thumbnail.tooltip.label.extended");

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

        ToolTips toolTips = configuration.getToolTips();

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
        thumbnailOverview.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.thumbnail.mainWindow.label")));
        thumbnailOverview.setPreferredSize(new Dimension(180, 120));

        thumbnailOverview.add(overviewToolTipDisabled, posThumbnailOverviewPanel);
        thumbnailOverview.add(overviewToolTipEnabled, posThumbnailOverviewPanel.nextRow().expandW());
        thumbnailOverview.add(overviewToolTipExtended, posThumbnailOverviewPanel.nextRow().expandW());
        thumbnailOverview.add(Box.createVerticalGlue(), posThumbnailOverviewPanel.nextRow().expandH());

        GBHelper posThumbnailImageSearchResultPanel = new GBHelper();
        JPanel thumbnailImageSerachResult = new JPanel(new GridBagLayout());
        thumbnailImageSerachResult.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.thumbnail.imageSearchResultWindow.label")));
        thumbnailImageSerachResult.setPreferredSize(new Dimension(180, 120));

        thumbnailImageSerachResult.add(imageSearchResultToolTipDisabled, posThumbnailImageSearchResultPanel.expandW());
        thumbnailImageSerachResult.add(imageSearchResultToolTipEnabled, posThumbnailImageSearchResultPanel.nextRow().expandW());
        thumbnailImageSerachResult.add(imageSearchResultToolTipExtended, posThumbnailImageSearchResultPanel.nextRow().expandW());
        thumbnailImageSerachResult.add(Box.createVerticalGlue(), posThumbnailImageSearchResultPanel.nextRow().expandH());

        GBHelper posThumbnailOverviewImageViewer = new GBHelper();
        JPanel thumbnailOverviewImageViewer = new JPanel(new GridBagLayout());
        thumbnailOverviewImageViewer.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.thumbnail.imageViewerWindow.label")));
        thumbnailOverviewImageViewer.setPreferredSize(new Dimension(180, 120));

        thumbnailOverviewImageViewer.add(overviewImageViewerToolTipDisabled, posThumbnailOverviewImageViewer.expandW());
        thumbnailOverviewImageViewer.add(overviewImageViewerToolTipEnabled, posThumbnailOverviewImageViewer.nextRow().expandW());
        thumbnailOverviewImageViewer.add(overviewImageViewerToolTipExtended, posThumbnailOverviewImageViewer.nextRow().expandW());
        thumbnailOverviewImageViewer.add(Box.createVerticalGlue(), posThumbnailOverviewImageViewer.nextRow().expandH());

        JPanel thumbnailToolTipPanel = new JPanel(new GridBagLayout());
        thumbnailToolTipPanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.thumbnail.tooltip.label")));

        GBHelper posThumbnailToolTipPanel = new GBHelper();

        thumbnailToolTipPanel.add(thumbnailOverview, posThumbnailToolTipPanel.expandH());
        thumbnailToolTipPanel.add(thumbnailImageSerachResult, posThumbnailToolTipPanel.nextCol().expandH());
        thumbnailToolTipPanel.add(thumbnailOverviewImageViewer, posThumbnailToolTipPanel.nextCol().expandH().expandW());

        thumbnailConfigurationPanel = new JPanel(new GridBagLayout());
        thumbnailConfigurationPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        GBHelper posThumbnailPanel = new GBHelper();

        thumbnailConfigurationPanel.add(thumbnailCreationPanel, posThumbnailPanel.expandW().expandH());
        thumbnailConfigurationPanel.add(thumbnailCachePanel, posThumbnailPanel.nextRow().expandW().expandH());
        thumbnailConfigurationPanel.add(thumbnailGrayFilterPanel, posThumbnailPanel.nextRow().expandW().expandH());
        thumbnailConfigurationPanel.add(thumbnailToolTipPanel, posThumbnailPanel.nextRow().expandW().expandH());
    }

    private void createTagConfigurationPanel() {

        TagImagesPreview tagImagesPreview = configuration.getTagImages().getPreview();

        /**
         * Start of Preview Image Area
         */
        useEmbeddedThumbnail = new JRadioButton(lang.get("configviewer.tag.previewimage.label.embeddedthumbnail"));
        useEmbeddedThumbnail.setSelected(tagImagesPreview.getUseEmbeddedThumbnail());

        useScaledThumbnail = new JRadioButton(lang.get("configviewer.tag.previewimage.label.scaledthumbnail"));
        useScaledThumbnail.setSelected(!tagImagesPreview.getUseEmbeddedThumbnail());

        ButtonGroup group = new ButtonGroup();
        group.add(useEmbeddedThumbnail);
        group.add(useScaledThumbnail);

        JPanel previewImagePanel = new JPanel(new GridBagLayout());
        previewImagePanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.tag.previewimage.label")));

        GBHelper posPreviewImagePanel = new GBHelper();

        previewImagePanel.add(useEmbeddedThumbnail, posPreviewImagePanel.expandW());
        previewImagePanel.add(useScaledThumbnail, posPreviewImagePanel.nextRow().expandW());
        previewImagePanel.add(Box.createVerticalGlue(), posPreviewImagePanel.nextRow().expandH());

        TagImagesCategories tagImagesCategories = configuration.getTagImages().getCategories();

        /**
         * Start of Categories Area
         */
        warnWhenRemoveCategory = new JCheckBox(lang.get("configviewer.tag.categories.warnWhenRemove"));
        warnWhenRemoveCategory.setSelected(tagImagesCategories.getWarnWhenRemove());
        warnWhenRemoveCategoryWithSubCategories = new JCheckBox(lang.get("configviewer.tag.categories.warnWhenRemoveCategoryWithSubCategories"));
        warnWhenRemoveCategoryWithSubCategories.setSelected(tagImagesCategories.getWarnWhenRemoveWithSubCategories());

        JPanel categoriesPanel = new JPanel(new GridBagLayout());
        categoriesPanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.tag.categories.label")));

        GBHelper posCategoriesPanel = new GBHelper();

        categoriesPanel.add(warnWhenRemoveCategory, posCategoriesPanel.expandW());
        categoriesPanel.add(warnWhenRemoveCategoryWithSubCategories, posCategoriesPanel.nextRow().expandW());
        categoriesPanel.add(Box.createVerticalGlue(), posCategoriesPanel.nextRow().expandH());

        /**
         * Start of Imported Categories Area
         */
        Map<String, ImportedCategories> importedCategories = configuration.getImportedCategoriesConfig();

        DefaultListModel<ImportedCategories> importedCategoriesListModel = new DefaultListModel<ImportedCategories>();
        for (ImportedCategories importedCategory : importedCategories.values()) {
            importedCategoriesListModel.addElement(importedCategory);
        }

        importedCategoriesList = new JList<ImportedCategories>(importedCategoriesListModel);
        importedCategoriesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        importedCategoriesList.addMouseListener(new RightClickMouseListener());

        JMenuItem renameImportedCategoriesJMenuItem = new JMenuItem(lang.get("configviewer.tag.importedCategories.menuitem.rename"));
        renameImportedCategoriesJMenuItem.setActionCommand("Rename");
        renameImportedCategoriesJMenuItem.addActionListener(new ImportedCategoiresPopupListener());

        JMenuItem deleteImportedCategoriesJMenuItem = new JMenuItem(lang.get("configviewer.tag.importedCategories.menuitem.delete"));
        deleteImportedCategoriesJMenuItem.setActionCommand("Delete");
        deleteImportedCategoriesJMenuItem.addActionListener(new ImportedCategoiresPopupListener());

        importedCategoriesPopupMenu = new JPopupMenu();
        importedCategoriesPopupMenu.add(renameImportedCategoriesJMenuItem);
        importedCategoriesPopupMenu.add(deleteImportedCategoriesJMenuItem);

        JScrollPane importedCategoriesScrollPane = new JScrollPane(importedCategoriesList);
        importedCategoriesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JButton removeSelectedImportedCategoriesButton = new JButton();
        removeSelectedImportedCategoriesButton.setToolTipText(lang.get("configviewer.tag.importedCategories.removeSelectedImportedCategoriesButton.tooltip"));
        removeSelectedImportedCategoriesButton.addActionListener(new RemoveImportedCategoriesListener());

        JPanel importedCategoriesPanel = new JPanel(new GridBagLayout());
        importedCategoriesPanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.tag.importedCategories.panel.border.label")));

        GBHelper posImportedCategoriesPanel = new GBHelper();

        importedCategoriesPanel.add(importedCategoriesScrollPane , posImportedCategoriesPanel.expandW().expandH());
        importedCategoriesPanel.add(removeSelectedImportedCategoriesButton, posImportedCategoriesPanel.nextRow().align(GridBagConstraints.WEST));

        /**
         * Start of Image Repositories Area
         */
        addAutomaticallyRadioButton = new JRadioButton(lang.get("configviewer.tag.imageRepositories.label.addAutomatically"));
        addAutomaticallyRadioButton.setName("0");
        askToAddRadioButton = new JRadioButton(lang.get("configviewer.tag.imageRepositories.label.askToAdd"));
        askToAddRadioButton.setName("1");
        doNotAddRadioButton = new JRadioButton(lang.get("configviewer.tag.imageRepositories.label.doNotAskToAdd"));
        doNotAddRadioButton.setName("2");

        ButtonGroup buttonGroup = new ButtonGroup();

        buttonGroup.add(addAutomaticallyRadioButton);
        buttonGroup.add(askToAddRadioButton);
        buttonGroup.add(doNotAddRadioButton);

        TagImagesPaths tagImagesPaths = configuration.getTagImages().getImagesPaths();

        String addToImageRepositoryPolicy = Integer.toString(tagImagesPaths.getAddToRepositoryPolicy());

        if (addToImageRepositoryPolicy.equalsIgnoreCase(addAutomaticallyRadioButton.getName())) {
            addAutomaticallyRadioButton.setSelected(true);
        } else if (addToImageRepositoryPolicy.equalsIgnoreCase(askToAddRadioButton.getName())) {
            askToAddRadioButton.setSelected(true);
        } else {
            doNotAddRadioButton.setSelected(true);
        }

        RepositoryExceptions repositoryExceptions = configuration.getRepository().getExceptions();

        imageRepositoriesAllwaysAddList = new JList<Object>(repositoryExceptions.getAllwaysAdd().toArray());
        imageRepositoriesAllwaysAddList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane imageRepositoriesAllwaysAddScrollPane = new JScrollPane(imageRepositoriesAllwaysAddList);
        imageRepositoriesAllwaysAddScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        imageRepositoriesNeverAddList = new JList<Object>(repositoryExceptions.getNeverAdd().toArray());
        imageRepositoriesNeverAddList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        JScrollPane imageRepositoriesNeverAddScrollPane = new JScrollPane(imageRepositoriesNeverAddList);
        imageRepositoriesNeverAddScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JButton removeSelectedAllwaysAddImagePaths = new JButton();
        removeSelectedAllwaysAddImagePaths.setToolTipText(lang.get("configviewer.tag.imageRepositories.label.removeSelectedPaths"));
        removeSelectedAllwaysAddImagePaths.setName("AllwaysAddImagePaths");
        removeSelectedAllwaysAddImagePaths.addActionListener(new RemoveExceptionPathsListener());

        JButton removeSelectedDoNotAllwaysAddImagePaths = new JButton();
        removeSelectedDoNotAllwaysAddImagePaths.setToolTipText(lang.get("configviewer.tag.imageRepositories.label.removeSelectedPaths"));
        removeSelectedDoNotAllwaysAddImagePaths.setName("DoNotAllwaysAddImagePaths");
        removeSelectedDoNotAllwaysAddImagePaths.addActionListener(new RemoveExceptionPathsListener());

        try {
            Icon removeIcon = ImageUtil.getIcon(StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/remove.gif"), true);

            removeSelectedAllwaysAddImagePaths.setIcon(removeIcon);
            removeSelectedDoNotAllwaysAddImagePaths.setIcon(removeIcon);
            removeSelectedImportedCategoriesButton.setIcon(removeIcon);
        } catch (IOException iox) {
            removeSelectedAllwaysAddImagePaths.setText("X");
            removeSelectedDoNotAllwaysAddImagePaths.setText("X");
            removeSelectedImportedCategoriesButton.setText("X");
            logger.logERROR("Could not set image: resources/images/viewtab/remove.gif as icon for the remove selected image paths button. See stacktrace below for details");
            logger.logERROR(iox);
        }

        ImageRepositoriesTableModel imageRepositoriesTableModel = ModelInstanceLibrary.getInstance().getImageRepositoriesTableModel();

        TableRowSorter<TableModel> imageRepositoriesTableModelSorter = new TableRowSorter<TableModel>(imageRepositoriesTableModel);
        imageRepositoriesTable = new CustomizedJTable(imageRepositoriesTableModel);
        imageRepositoriesTable.setRowSorter(imageRepositoriesTableModelSorter);
        imageRepositoriesTable.getRowSorter().toggleSortOrder(0);
        imageRepositoriesTable.setDefaultRenderer(Object.class, new ImageRepositoriesTableCellRenderer());

        JScrollPane imageRepositoriesScrollPane = new JScrollPane(imageRepositoriesTable);
        imageRepositoriesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel imageRepositoriesAdditionModePanel = new JPanel(new GridBagLayout());
        imageRepositoriesAdditionModePanel.setBorder(BorderFactory.createTitledBorder(""));
        imageRepositoriesAdditionModePanel.setName(lang.get("configviewer.tag.imageRepositoriesAdditionMode.label"));

        GBHelper posImageRepositories = new GBHelper();

        imageRepositoriesAdditionModePanel.add(addAutomaticallyRadioButton, posImageRepositories.expandW());
        imageRepositoriesAdditionModePanel.add(askToAddRadioButton, posImageRepositories.nextRow().expandW());
        imageRepositoriesAdditionModePanel.add(doNotAddRadioButton, posImageRepositories.nextRow().expandW());

        imageRepositoriesAdditionModePanel.add(Box.createVerticalStrut(10), posImageRepositories.nextRow());
        imageRepositoriesAdditionModePanel.add(new JLabel(lang.get("configviewer.tag.imageRepositoriesAdditionMode.allwaysAdd.label")), posImageRepositories.nextRow());
        imageRepositoriesAdditionModePanel.add(imageRepositoriesAllwaysAddScrollPane, posImageRepositories.nextRow().expandW().expandH());
        imageRepositoriesAdditionModePanel.add(removeSelectedAllwaysAddImagePaths, posImageRepositories.nextRow().align(GridBagConstraints.WEST));
        imageRepositoriesAdditionModePanel.add(Box.createVerticalStrut(15), posImageRepositories.nextRow());
        imageRepositoriesAdditionModePanel.add(new JLabel(lang.get("configviewer.tag.imageRepositoriesAdditionMode.neverAdd.label")), posImageRepositories.nextRow().nextRow());
        imageRepositoriesAdditionModePanel.add(imageRepositoriesNeverAddScrollPane, posImageRepositories.nextRow().expandW().expandH());
        imageRepositoriesAdditionModePanel.add(removeSelectedDoNotAllwaysAddImagePaths, posImageRepositories.nextRow().align(GridBagConstraints.WEST));

        JPanel imageRepositoriesContentPanel = new JPanel(new GridBagLayout());
        imageRepositoriesContentPanel.setBorder(BorderFactory.createTitledBorder(""));
        imageRepositoriesContentPanel.setName(lang.get("configviewer.tag.imageRepositoriesContent.label"));

        JPanel buttonPanel = new JPanel(new GridBagLayout());

        removeSelectedImagePathsButton = new JButton();

        InputStream imageStream = null;
        try {
            imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/remove.gif");
            removeSelectedImagePathsButton.setIcon(new ImageIcon(ImageIO.read(imageStream)));
        } catch (IOException iox) {
            removeSelectedImagePathsButton.setText("X");
            logger.logERROR("Could not set image: resources/images/viewtab/remove.gif as icon for the remove selected image paths button. See stacktrace below for details");
            logger.logERROR(iox);
        } finally {
            StreamUtil.close(imageStream, true);
        }

        removeSelectedImagePathsButton.setToolTipText("Remove selected path(s) from the image repository");

        GBHelper posButtonPanel = new GBHelper();

        buttonPanel.add(removeSelectedImagePathsButton, posButtonPanel.align(GridBagConstraints.WEST));

        GBHelper posImageRepositoriesContent = new GBHelper();

        imageRepositoriesMetaDataLabel = new JLabel();
        imageRepositoriesContentPanel.add(imageRepositoriesMetaDataLabel, posImageRepositoriesContent);
        imageRepositoriesContentPanel.add(imageRepositoriesScrollPane, posImageRepositoriesContent.nextRow().expandW().expandH());
        imageRepositoriesContentPanel.add(Box.createVerticalStrut(2), posImageRepositoriesContent.nextRow());
        imageRepositoriesContentPanel.add(buttonPanel, posImageRepositoriesContent.nextRow().align(GridBagConstraints.WEST));

        JTabbedPane imageRepositoryTabbedPane = new JTabbedPane();
        imageRepositoryTabbedPane.add(imageRepositoriesAdditionModePanel);
        imageRepositoryTabbedPane.add(imageRepositoriesContentPanel);

        tagConfigurationPanel = new JPanel(new GridBagLayout());
        tagConfigurationPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        GBHelper posTagPanel = new GBHelper();

        tagConfigurationPanel.add(previewImagePanel, posTagPanel.expandW());
        tagConfigurationPanel.add(categoriesPanel, posTagPanel.nextRow().expandW());
        tagConfigurationPanel.add(importedCategoriesPanel, posTagPanel.nextRow().expandW().expandH());
        tagConfigurationPanel.add(imageRepositoryTabbedPane, posTagPanel.nextRow().expandW().expandH());
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

        if(!validateThumbnailSize("width")) {
            return false;
        }

        if(!validateThumbnailSize("height")) {
            return false;
        }

        if(!validateThumbnailCacheMaxSize()) {
            return false;
        }

        if (!renameConfigurationPanel.isValidConfiguration()) {
            return false;
        }

        /**
         * Update Logging Configuration
         */
        loggingConfigurationPanel.updateConfiguration();

        /**
         * Update Updates Configuration
         */
        UpdatesChecker updatesChecker = configuration.getUpdatesChecker();

        updatesChecker.setEnabled(updatesEnabled.isSelected());
        updatesChecker.setAttachVersionInformation(sendVersionInformationEnabled.isSelected());

        /**
         * Update Language Configuration
         */
        moller.javapeg.program.config.model.Language language = configuration.getLanguage();

        language.setAutomaticSelection(automaticRadioButton.isSelected());
        language.setgUILanguageISO6391(ISO639.getInstance().getCode(currentLanguage.getText()));

        /**
         * Update Rename Configuration
         */
        renameConfigurationPanel.updateConfiguration();

        /**
         * Update Thumbnail Configuration
         */
        ThumbNail thumbNail = configuration.getThumbNail();

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

        /***
         * Update ToolTips configuration
         */
        ToolTips toolTips = configuration.getToolTips();
        toolTips.setImageSearchResultState(getImageSearchResultTooltipState());
        toolTips.setOverviewImageViewerState(getOverviewImageViewerTooltipState());
        toolTips.setOverviewState(getOverviewToolTipState());

        /**
         * Update Tag Configuration
         */
        TagImages tagImages = configuration.getTagImages();

        TagImagesCategories tagImagesCategories = tagImages.getCategories();

        tagImagesCategories.setWarnWhenRemove(warnWhenRemoveCategory.isSelected());
        tagImagesCategories.setWarnWhenRemoveWithSubCategories(warnWhenRemoveCategoryWithSubCategories.isSelected());

        TagImagesPaths tagImagesPaths = tagImages.getImagesPaths();

        tagImagesPaths.setAddToRepositoryPolicy(Integer.parseInt(getAddToRepositoryPolicy()));

        TagImagesPreview tagImagesPreview = tagImages.getPreview();

        tagImagesPreview.setUseEmbeddedThumbnail(useEmbeddedThumbnail.isSelected());

        /**
         * Show configuration changes.
         */
        this.displayChangedConfigurationMessage();

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

    private String getAddToRepositoryPolicy() {
        if (addAutomaticallyRadioButton.isSelected()) {
            return addAutomaticallyRadioButton.getName();
        } else if (askToAddRadioButton.isSelected()) {
            return askToAddRadioButton.getName();
        } else {
            return doNotAddRadioButton.getName();
        }
    }

    private void displayChangedConfigurationMessage() {

        String preMessage = lang.get("configviewer.changed.configuration.start");
        String postMessage = lang.get("configviewer.changed.configuration.end");
        StringBuilder displayMessage = new StringBuilder();

        if(UPDATE_CHECK_ENABLED != updatesEnabled.isSelected()){
            displayMessage.append(lang.get("configviewer.update.label.updateEnabled.text") + ": " + updatesEnabled.isSelected() + " (" + UPDATE_CHECK_ENABLED + ")\n");
        }

        if(UPDATE_CHECK_ATTACH_VERSION != sendVersionInformationEnabled.isSelected()){
            displayMessage.append(lang.get("configviewer.update.label.attachVersionInformation.text") + ": " + sendVersionInformationEnabled.isSelected() + " (" + UPDATE_CHECK_ATTACH_VERSION + ")\n");
        }

        if(AUTOMATIC_LANGUAGE_SELECTION != automaticRadioButton.isSelected()){
            displayMessage.append(lang.get("configviewer.language.radiobutton.automatic") + ": " + automaticRadioButton.isSelected() + " (" + AUTOMATIC_LANGUAGE_SELECTION + ")\n");
        }

        if(!GUI_LANGUAGE_ISO6391.equals(ISO639.getInstance().getCode(currentLanguage.getText()))){
            displayMessage.append(lang.get("configviewer.language.label.currentLanguage") + ": " + currentLanguage.getText() + " (" + ISO639.getInstance().getLanguage(GUI_LANGUAGE_ISO6391) + ")\n");
        }

        if(CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT != createThumbnailIfMissingOrCorrupt.isSelected()) {
            displayMessage.append(lang.get("configviewer.thumbnail.creation.label.missingOrCorrupt") + ": " + createThumbnailIfMissingOrCorrupt.isSelected() + " (" + CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT + ")\n");
        }

        if(!THUMBNAIL_WIDTH.equals(Integer.parseInt(thumbnailWidth.getText()))) {
            displayMessage.append(lang.get("configviewer.thumbnail.creation.label.thumbnail.width") + ": " + thumbnailWidth.getText() + " (" + THUMBNAIL_WIDTH + ")\n");
        }

        if(!THUMBNAIL_HEIGHT.equals(Integer.parseInt(thumbnailHeight.getText()))) {
            displayMessage.append(lang.get("configviewer.thumbnail.creation.label.thumbnail.height") + ": " + thumbnailHeight.getText() + " (" + THUMBNAIL_HEIGHT + ")\n");
        }

        if(CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT_ALGORITHM != (JPEGScaleAlgorithm)thumbnailCreationAlgorithm.getSelectedItem()) {
            displayMessage.append(lang.get("configviewer.thumbnail.creation.label.algorithm") + ": " + thumbnailCreationAlgorithm.getSelectedItem().toString() + " (" + CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT_ALGORITHM + ")\n");
        }

        if(!THUMBNAIL_MAX_CACHE_SIZE.equals(Integer.parseInt(maxCacheSize.getText()))) {
            displayMessage.append(lang.get("configviewer.thumbnail.cache.label.size.max") + ": " + maxCacheSize.getText() + " (" + THUMBNAIL_MAX_CACHE_SIZE + ")\n");
        }

        if(ENABLE_THUMBNAIL_CACHE != enableThumbnailCache.isSelected()) {
            displayMessage.append(lang.get("configviewer.thumbnail.cache.label.enable") + ": " + enableThumbnailCache.isSelected() + " (" + ENABLE_THUMBNAIL_CACHE + ")\n");
        }

        displayMessage.append(loggingConfigurationPanel.getChangedConfigurationMessage());
        displayMessage.append(renameConfigurationPanel.getChangedConfigurationMessage());

        if(USE_EMBEDDED_THUMBNAIL != useEmbeddedThumbnail.isSelected()) {
            if (USE_EMBEDDED_THUMBNAIL) {
                displayMessage.append(lang.get("configviewer.tag.previewimage.label.scaledthumbnail")+ " (" + lang.get("configviewer.tag.previewimage.label.embeddedthumbnail") + ")\n");
            } else {
                displayMessage.append(lang.get("configviewer.tag.previewimage.label.embeddedthumbnail")+ " (" + lang.get("configviewer.tag.previewimage.label.scaledthumbnail") + ")\n");
            }
        }

        if(!OVERVIEW_THUMBNAIL_TOOLTIP_STATE.equals(getOverviewToolTipState())) {
            int previousThumbNailToolTipState = -1;

            try {
                previousThumbNailToolTipState = Integer.parseInt(OVERVIEW_THUMBNAIL_TOOLTIP_STATE);
            } catch (NumberFormatException nfex) {
                previousThumbNailToolTipState = 1;
            }

            int currentThumbNailToolTipState = Integer.parseInt(getOverviewToolTipState());

            String previous = getTooltipStateAsLocalizedString(previousThumbNailToolTipState);
            String current = getTooltipStateAsLocalizedString(currentThumbNailToolTipState);

            displayMessage.append(lang.get("configviewer.thumbnail.tooltip.label") + ": " + current + " (" + previous + ")\n");
        }

        if(!IMAGE_SEARCH_RESULT_THUMBNAIL_TOOLTIP_STATE.equals(getImageSearchResultTooltipState())) {
            int previousThumbNailToolTipState = -1;

            try {
                previousThumbNailToolTipState = Integer.parseInt(IMAGE_SEARCH_RESULT_THUMBNAIL_TOOLTIP_STATE);
            } catch (NumberFormatException nfex) {
                previousThumbNailToolTipState = 1;
            }

            int currentThumbNailToolTipState = Integer.parseInt(getImageSearchResultTooltipState());

            String previous = getTooltipStateAsLocalizedString(previousThumbNailToolTipState);
            String current = getTooltipStateAsLocalizedString(currentThumbNailToolTipState);

            displayMessage.append(lang.get("configviewer.thumbnail.tooltip.label") + ": " + current + " (" + previous + ")\n");
        }

        if(!OVERVIEW_IMAGE_VIEWER_THUMBNAIL_TOOLTIP_STATE.equals(getOverviewImageViewerTooltipState())) {
            int previousThumbNailToolTipState = -1;

            try {
                previousThumbNailToolTipState = Integer.parseInt(OVERVIEW_IMAGE_VIEWER_THUMBNAIL_TOOLTIP_STATE);
            } catch (NumberFormatException nfex) {
                previousThumbNailToolTipState = 1;
            }

            int currentThumbNailToolTipState = Integer.parseInt(getOverviewImageViewerTooltipState());

            String previous = getTooltipStateAsLocalizedString(previousThumbNailToolTipState);
            String current = getTooltipStateAsLocalizedString(currentThumbNailToolTipState);

            displayMessage.append(lang.get("configviewer.thumbnail.tooltip.label") + ": " + current + " (" + previous + ")\n");
        }

        if(!ADD_TO_IMAGEREPOSITOY_POLICY.equals(Integer.parseInt(getAddToRepositoryPolicy()))) {
            String previous = "";

            switch (ADD_TO_IMAGEREPOSITOY_POLICY) {
            case 0:
                previous = lang.get("configviewer.tag.imageRepositories.label.addAutomatically");
                break;
            case 1:
                previous = lang.get("configviewer.tag.imageRepositories.label.askToAdd");
                break;
            case 2:
                previous = lang.get("configviewer.tag.imageRepositories.label.doNotAskToAdd");
                break;
            }

            int currentAddToImageRepositoryPolicy = Integer.parseInt(getAddToRepositoryPolicy());
            String current = "";

            switch (currentAddToImageRepositoryPolicy) {
            case 0:
                current = lang.get("configviewer.tag.imageRepositories.label.addAutomatically");
                break;
            case 1:
                current = lang.get("configviewer.tag.imageRepositories.label.askToAdd");
                break;
            case 2:
                current = lang.get("configviewer.tag.imageRepositories.label.doNotAskToAdd");
                break;
            }

            displayMessage.append(lang.get("configviewer.tag.imageRepositoriesAdditionMode.label") + ": " + current + " (" + previous + ")\n");
        }

        if(WARN_WHEN_REMOVE_CATEGORY != warnWhenRemoveCategory.isSelected()) {
            displayMessage.append(lang.get("configviewer.tag.categories.warnWhenRemove") + ": " + warnWhenRemoveCategory.isSelected() + " (" + WARN_WHEN_REMOVE_CATEGORY + ")\n");
        }

        if(WARN_WHEN_REMOVE_CATEGORY_WITH_SUB_CATEGORIES != warnWhenRemoveCategoryWithSubCategories.isSelected()) {
            displayMessage.append(lang.get("configviewer.tag.categories.warnWhenRemoveCategoryWithSubCategories") + ": " + warnWhenRemoveCategoryWithSubCategories.isSelected() + " (" + WARN_WHEN_REMOVE_CATEGORY_WITH_SUB_CATEGORIES + ")\n");
        }

        if(!PERCENTAGE_SLIDER.equals(percentageSlider.getValue())) {
            displayMessage.append(lang.get("configviewer.thumbnail.grayfilter.transparency.label") + ": " + percentageSlider.getValue() + " (" + PERCENTAGE_SLIDER + ")\n");
        }

        if(BRIGHTENED_CHECKBOX != brightenedCheckBox.isSelected()) {
            displayMessage.append(lang.get("configviewer.thumbnail.grayfilter.increase.contrast.label") + ": " + brightenedCheckBox.isSelected() + " (" + BRIGHTENED_CHECKBOX + ")\n");
        }

        if(displayMessage.length() > 0) {
            JOptionPane.showMessageDialog(this, preMessage + "\n\n" + displayMessage +  "\n" + postMessage, lang.get("errormessage.maingui.informationMessageLabel"), JOptionPane.INFORMATION_MESSAGE);
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
        String disabled = lang.get("configviewer.thumbnail.tooltip.label.disabled");
        String enabled = lang.get("configviewer.thumbnail.tooltip.label.enabled");
        String extended = lang.get("configviewer.thumbnail.tooltip.label.extended");

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

    private boolean validateThumbnailSize(String validatorFor) {

        String errorMessage = "";

        if (validatorFor.equals("width")) {
            String thumbnailWidthString = thumbnailWidth.getText();
            if(!StringUtil.isInt(thumbnailWidthString)) {
                errorMessage = lang.get("configviewer.thumbnail.creation.validation.width.integer");
            }
            else if(!StringUtil.isInt(thumbnailWidthString, true)) {
                errorMessage = lang.get("configviewer.thumbnail.creation.validation.width.integerNonNegative");
            }
        } else {
            String thumbnailHeightString = thumbnailHeight.getText();
            if(!StringUtil.isInt(thumbnailHeightString)) {
                errorMessage = lang.get("configviewer.thumbnail.creation.validation.height.integer");
            }
            else if(!StringUtil.isInt(thumbnailHeightString, true)) {
                errorMessage = lang.get("configviewer.thumbnail.creation.validation.height.integerNonNegative");
            }
        }
        if(errorMessage.length() > 0) {
            JOptionPane.showMessageDialog(this, errorMessage, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean validateThumbnailCacheMaxSize() {
        if(!StringUtil.isInt(maxCacheSize.getText(), true)) {
            JOptionPane.showMessageDialog(this, lang.get("configviewer.thumbnail.cache.validation.size.max"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * Performs the needed actions to be performed when the window is closing
     */
    private void closeWindow() {
        updateWindowLocationAndSize();
        this.setVisible(false);
        this.dispose();
    }

    private int displayConfirmDialog(String message, String label, int type) {
        return JOptionPane.showConfirmDialog(this, message, label, type);
    }

    private void removeImportedCategories() {
        Map<String, ImportedCategories> importedCategoriesMap = Config.getInstance().get().getImportedCategoriesConfig();

        Set<String> keysToRemove = new HashSet<String>();

        // Search for imported categories to remove...
        for (Object selectedValue : importedCategoriesList.getSelectedValuesList()) {

            for (String key : importedCategoriesMap.keySet()) {
                if (importedCategoriesMap.get(key).equals(selectedValue)) {
                    keysToRemove.add(key);
                    ((DefaultListModel<ImportedCategories>)importedCategoriesList.getModel()).removeElement(selectedValue);
                }
            }
        }

        // ... and remove the found matches.
        for (String key : keysToRemove) {
            importedCategoriesMap.remove(key);
        }

        importedCategoriesList.clearSelection();
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

    private class ManualRadioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            languageList.setEnabled(true);
        }
    }

    private class AutomaticRadioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            languageList.setEnabled(false);

            String userLanguage = System.getProperty("user.language");

            if(!configuration.getLanguage().getgUILanguageISO6391().equals(userLanguage)) {
                currentLanguage.setText(ConfigUtil.resolveCodeToLanguageName(userLanguage));
            }
        }
    }

    private class LanguageListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            if(languageList.getSelectedIndex() > -1) {
                currentLanguage.setText(languageList.getSelectedValue());
            }
        }
    }

    private class UpdatesEnabledCheckBoxListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            sendVersionInformationEnabled.setEnabled(updatesEnabled.isSelected());
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

            int result = displayConfirmDialog(lang.get("configviewer.thumbnail.cache.label.clear.question") + " (" + jptc.getCurrentSize() + ")", lang.get("common.confirmation"), JOptionPane.OK_CANCEL_OPTION);

            if (result == 0) {
                jptc.clear();
                cacheSizeLabel.setText(Integer.toString(jptc.getCurrentSize()));
                clearCacheJButton.setEnabled(false);
            }
        }
    }

    /**
     * This class listens for changes to the image repositories TableModel and
     * prints information about how many entries and of which type there are in
     * the model onto a {@link JLabel}.
     *
     * @author Fredrik
     *
     */
    private class ImageRepositoriesTableModelListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {
            ImageRepositoriesTableModel imageRepositoriesTableModel = (ImageRepositoriesTableModel)e.getSource();

            int total = imageRepositoriesTableModel.getRowCount();
            Map<Status, AtomicInteger> numberOfRowsPerStatus = imageRepositoriesTableModel.getNumberOfRowsPerStatus();

            String labelText = createLabelText(total, numberOfRowsPerStatus);
            String labelTooltipText = createLabelTooltipText(total, numberOfRowsPerStatus);

            imageRepositoriesMetaDataLabel.setText(labelText);
            imageRepositoriesMetaDataLabel.setToolTipText(labelTooltipText);
        }

        private String createLabelTooltipText(int total, Map<Status, AtomicInteger> numberOfRowsPerStatus) {
            String stringToFormat = createText(total, numberOfRowsPerStatus);

            String totalAmount = lang.get("configviewer.tag.imageRepositories.label.totalAmount");
            String exists = lang.get("configviewer.tag.imageRepositories.label.exists");
            String notAvailable = lang.get("configviewer.tag.imageRepositories.label.notAvailable");
            String doesNotExist = lang.get("configviewer.tag.imageRepositories.label.doesNotExist");
            String inconsistent = lang.get("configviewer.tag.imageRepositories.label.inconsistent");
            String corrupt = lang.get("configviewer.tag.imageRepositories.label.corrupt");

            stringToFormat = "<html>" + stringToFormat + "</html>";

            return String.format(stringToFormat,totalAmount, "<br/>" + exists, "<br/>" + doesNotExist, "<br/>" + notAvailable, "<br/>" + inconsistent, "<br/>" + corrupt);
        }

        private String createLabelText(int total, Map<Status, AtomicInteger> numberOfRowsPerStatus) {
            String stringToFormat = createText(total, numberOfRowsPerStatus);

            String totalAmountMnemonic = lang.get("configviewer.tag.imageRepositories.label.totalAmountMnemonic");
            String existsMnemonic = lang.get("configviewer.tag.imageRepositories.label.existsMnemonic");
            String notAvailableMnemonic = lang.get("configviewer.tag.imageRepositories.label.notAvailableMnemonic");
            String doesNotExistMnemonic = lang.get("configviewer.tag.imageRepositories.label.doesNotExistMnemonic");
            String inconsistentMnemonic = lang.get("configviewer.tag.imageRepositories.label.inconsistentMnemonic");
            String corruptMnemonic = lang.get("configviewer.tag.imageRepositories.label.corruptMnemonic");

            return String.format(stringToFormat, totalAmountMnemonic, existsMnemonic, doesNotExistMnemonic, notAvailableMnemonic, inconsistentMnemonic, corruptMnemonic);
        }

        private String createText(int total, Map<Status, AtomicInteger> numberOfRowsPerStatus) {
            StringBuilder builder = new StringBuilder();
            builder.append("%s: ");
            builder.append(total);
            builder.append(" %s: ");
            builder.append(getNumberOfForStatus(numberOfRowsPerStatus, Status.EXISTS));
            builder.append(" %s: ");
            builder.append(getNumberOfForStatus(numberOfRowsPerStatus, Status.DOES_NOT_EXIST));
            builder.append(" %s: ");
            builder.append(getNumberOfForStatus(numberOfRowsPerStatus, Status.NOT_AVAILABLE));
            builder.append(" %s: ");
            builder.append(getNumberOfForStatus(numberOfRowsPerStatus, Status.INCONSISTENT));
            builder.append(" %s: ");
            builder.append(getNumberOfForStatus(numberOfRowsPerStatus, Status.CORRUPT));

            return builder.toString();
        }

        private Number getNumberOfForStatus(Map<Status, AtomicInteger> numberOfRowsPerStatus, Status status) {
            return numberOfRowsPerStatus.get(status) == null ? 0 : numberOfRowsPerStatus.get(status);
        }
    }

    private class RemoveSelectedImagePathsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ImageRepositoriesTableModel imageRepositoriesTableModel = ModelInstanceLibrary.getInstance().getImageRepositoriesTableModel();

            List<ImageRepositoryItem> selectedValues = getSelectedValues(imageRepositoriesTable);

            StringBuilder paths = new StringBuilder();

            for (Object selectedValue : selectedValues) {
                ImageRepositoryItem iri = (ImageRepositoryItem)selectedValue;

                String status = lang.get(iri.getPathStatus().getTextKey());

                paths.append(iri.getPath() + " (" + status + ")");
                paths.append(C.LS);
            }

            int result = displayConfirmDialog(lang.get("configviewer.tag.imageRepositories.label.pathsWillBeRemoved") + C.LS + C.LS + paths.toString(), lang.get("common.confirmation"), JOptionPane.OK_CANCEL_OPTION);

            if (result == 0) {
                for (ImageRepositoryItem selectedValue : selectedValues) {
                    imageRepositoriesTableModel.removeRow(selectedValue);
                }
            }
        }

        private List<ImageRepositoryItem> getSelectedValues(CustomizedJTable imageRepositoriesTable) {

            int[] selectedRows = imageRepositoriesTable.getSelectedRows();

            List<ImageRepositoryItem> selectedValues = new ArrayList<ImageRepositoryItem>();

            for (int selectedRow : selectedRows) {
                File path = (File) imageRepositoriesTable.getValueAt(selectedRow, 0);
                Status status = (Status) imageRepositoriesTable.getValueAt(selectedRow, 1);

                ImageRepositoryItem iri = new ImageRepositoryItem(path, status);

                selectedValues.add(iri);
            }
            return selectedValues;
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
            setStartupConfig();
        }
    }

    private class CancelButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            closeWindow();
        }
    }

    private class RemoveExceptionPathsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RepositoryExceptions repositoryExceptions = Config.getInstance().get().getRepository().getExceptions();

            if (((JButton)e.getSource()).getName().equals("AllwaysAddImagePaths")) {
                if (!imageRepositoriesAllwaysAddList.isSelectionEmpty()) {
                    for (Object selectedValue : imageRepositoriesAllwaysAddList.getSelectedValuesList()) {
                        repositoryExceptions.getAllwaysAdd().remove(selectedValue);
                    }
                    imageRepositoriesAllwaysAddList.clearSelection();
                }
            } else {
                if (!imageRepositoriesNeverAddList.isSelectionEmpty()) {
                    for (Object selectedValue : imageRepositoriesNeverAddList.getSelectedValuesList()) {
                        repositoryExceptions.getNeverAdd().remove(selectedValue);
                    }
                    imageRepositoriesNeverAddList.clearSelection();
                }
            }
        }
    }

    private class RemoveImportedCategoriesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!importedCategoriesList.isSelectionEmpty()) {
                removeImportedCategories();
            }
        }
    }

    private class RightClickMouseListener extends MouseAdapter  {

        @SuppressWarnings("unchecked")
        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                ((JList<Object>)e.getSource()).setSelectedIndex(((JList<Object>)e.getSource()).locationToIndex(e.getPoint()) );

                int selecteIndex = ((JList<Object>)e.getSource()).locationToIndex(e.getPoint());

                if (selecteIndex > -1) {
                    theImportedCategoriesToRenameOrDelete = (ImportedCategories)((JList<Object>)e.getSource()).getModel().getElementAt(selecteIndex);
                }
                importedCategoriesPopupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }

    private class ImportedCategoiresPopupListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Rename")) {
                if (theImportedCategoriesToRenameOrDelete != null) {
                    String newName = CategoryUtil.displayInputDialog(ConfigViewerGUI.this, lang.get("configviewer.tag.importedCategories.rename.dialog.title"), lang.get("configviewer.tag.importedCategories.rename.dialog.text") + " " + theImportedCategoriesToRenameOrDelete.getDisplayName(), "");

                    // If newName is null then the cancel button has been
                    // clicked, then do nothing.
                    if (newName != null) {
                        if (CategoryUtil.displayNameAlreadyInUse(newName, configuration.getImportedCategoriesConfig().values()) || newName.trim().length() == 0) {
                            theImportedCategoriesToRenameOrDelete.setDisplayName(CategoryUtil.askForANewDisplayName(ConfigViewerGUI.this, newName, configuration.getImportedCategoriesConfig()));
                        } else {
                            theImportedCategoriesToRenameOrDelete.setDisplayName(newName);
                        }
                    }
                }
            } else if (e.getActionCommand().equals("Delete")) {
                if (theImportedCategoriesToRenameOrDelete != null) {
                    removeImportedCategories();
                    theImportedCategoriesToRenameOrDelete = null;
                }
            }
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