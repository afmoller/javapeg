package moller.javapeg.program.config.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import javax.swing.JSplitPane;
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

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.categories.CategoryUtil;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.ConfigUtil;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.Logging;
import moller.javapeg.program.config.model.ToolTips;
import moller.javapeg.program.config.model.UpdatesChecker;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesCategories;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPaths;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPreview;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.javapeg.program.config.model.repository.RepositoryExceptions;
import moller.javapeg.program.config.model.thumbnail.ThumbNail;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCache;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCreation;
import moller.javapeg.program.enumerations.Level;
import moller.javapeg.program.gui.CustomCellRenderer;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.javapeg.program.jpeg.JPEGThumbNailCache;
import moller.javapeg.program.language.ISO639;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.model.ModelInstanceLibrary;
import moller.javapeg.program.model.SortedListModel;
import moller.util.gui.Screen;
import moller.util.image.ImageUtil;
import moller.util.io.PathUtil;
import moller.util.io.StreamUtil;
import moller.util.java.SystemProperties;
import moller.util.jpeg.JPEGScaleAlgorithm;
import moller.util.string.StringUtil;

public class ConfigViewerGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTree tree;

    private JPanel backgroundsPanel;

    private JPanel loggingConfigurationPanel;
    private JPanel updatesConfigurationPanel;
    private JPanel languageConfigurationPanel;
    private JPanel renameConfigurationPanel;
    private JPanel thumbnailConfigurationPanel;
    private JPanel tagConfigurationPanel;

    private JSplitPane splitPane;

    private JButton okButton;
    private JButton applyButton;
    private JButton cancelButton;

    /**
     * Variables for the logging panel
     */
    private JCheckBox developerMode;
    private JCheckBox rotateLog;
    private JCheckBox zipLog;
    private JComboBox<String> rotateLogSizeFactor;
    private JComboBox<Level> logLevels;
    private JComboBox<String> logEntryTimeStampFormats;

    private JTextField rotateLogSize;
    private JTextField logName;
    private JTextField logEntryTimeStampPreview;

    /**
     * Variables for the updates panel
     */
    private JCheckBox updatesEnabled;
    private JCheckBox sendVersionInformationEnabled;

    /**
     * Variables for the language panel
     */
    private JList<String> languageList;

    /**
     * Variables for the rename panel
     */
    private JCheckBox useLastModifiedDate;
    private JCheckBox useLastModifiedTime;
    private JTextField maximumLengthOfCameraModelValueTextField;

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

    private JRadioButton toolTipDisabled;
    private JRadioButton toolTipEnabled;
    private JRadioButton toolTipExtended;

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

    private JCheckBox automaticallyRemoveNonExistingImagePathsCheckBox;
    private JButton removeSelectedImagePathsButton;

    private JList<Object> importedCategoriesList;
    private JList<Object> imageRepositoriesAllwaysAddList;
    private JList<Object> imageRepositoriesNeverAddList;
    private JList<ImageRepositoryItem> imageRepositoriesList;

    private JPopupMenu importedCategoriesPopupMenu;
    private ImportedCategories theImportedCategoriesToRenameOrDelete;

    private final Configuration configuration;
    private final Logger   logger;
    private final Language lang;

    // Configuration values read from configuration.
    private Level LOG_LEVEL;
    private String LOG_NAME;
    private Long LOG_ROTATE_SIZE;
    private SimpleDateFormat LOG_ENTRY_TIMESTAMP_FORMAT;
    private String GUI_LANGUAGE_ISO6391;
    private Integer THUMBNAIL_WIDTH;
    private Integer THUMBNAIL_HEIGHT;
    private JPEGScaleAlgorithm CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT_ALGORITHM;
    private Integer THUMBNAIL_MAX_CACHE_SIZE;
    private Integer MAXIMUM_LENGTH_OF_CAMERA_MODEL;
    private String THUMBNAIL_TOOLTIP_STATE;
    private Integer ADD_TO_IMAGEREPOSITOY_POLICY;

    private boolean DEVELOPER_MODE;
    private boolean LOG_ROTATE;
    private boolean LOG_ROTATE_ZIP;
    private boolean UPDATE_CHECK_ENABLED;
    private boolean UPDATE_CHECK_ATTACH_VERSION;
    private boolean USE_LAST_MODIFIED_DATE;
    private boolean USE_LAST_MODIFIED_TIME;
    private boolean AUTOMATIC_LANGUAGE_SELECTION;
    private boolean CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT;
    private boolean ENABLE_THUMBNAIL_CACHE;
    private boolean USE_EMBEDDED_THUMBNAIL;
    private boolean WARN_WHEN_REMOVE_CATEGORY;
    private boolean WARN_WHEN_REMOVE_CATEGORY_WITH_SUB_CATEGORIES;
    private boolean AUTOMATICALLY_REMOVE_NON_EXISTING_IMAGE_PATHS_CHECKBOX;

    public ConfigViewerGUI() {
        configuration = Config.getInstance().get();
        logger = Logger.getInstance();
        lang   = Language.getInstance();

        this.setStartupConfig();
        this.initiateWindow();
        this.createLoggingConfigurationPanel();
        this.createUpdateConfigurationPanel();
        this.createLanguageConfigurationPanel();
        this.createRenameConfigurationPanel();
        this.createThumbnailConfigurationPanel();
        this.createTagConfigurationPanel();
        this.addListeners();
    }

    private void setStartupConfig() {

        LOG_LEVEL = configuration.getLogging().getLevel();
        DEVELOPER_MODE = configuration.getLogging().getDeveloperMode();
        LOG_ROTATE = configuration.getLogging().getRotate();
        LOG_ROTATE_ZIP = configuration.getLogging().getRotateZip();
        LOG_ROTATE_SIZE = configuration.getLogging().getRotateSize();
        LOG_NAME = configuration.getLogging().getFileName();
        LOG_ENTRY_TIMESTAMP_FORMAT = configuration.getLogging().getTimeStampFormat();
        UPDATE_CHECK_ENABLED = configuration.getUpdatesChecker().getEnabled();
        UPDATE_CHECK_ATTACH_VERSION = configuration.getUpdatesChecker().getAttachVersionInformation();
        USE_LAST_MODIFIED_DATE = configuration.getRenameImages().getUseLastModifiedDate();
        USE_LAST_MODIFIED_TIME = configuration.getRenameImages().getUseLastModifiedTime();
        MAXIMUM_LENGTH_OF_CAMERA_MODEL = configuration.getRenameImages().getCameraModelNameMaximumLength();
        AUTOMATIC_LANGUAGE_SELECTION = configuration.getLanguage().getAutomaticSelection();
        GUI_LANGUAGE_ISO6391 = configuration.getLanguage().getgUILanguageISO6391();
        CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT = configuration.getThumbNail().getCreation().getIfMissingOrCorrupt();
        THUMBNAIL_WIDTH = configuration.getThumbNail().getCreation().getWidth();
        THUMBNAIL_HEIGHT = configuration.getThumbNail().getCreation().getHeight();
        CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT_ALGORITHM = configuration.getThumbNail().getCreation().getAlgorithm();
        THUMBNAIL_MAX_CACHE_SIZE = configuration.getThumbNail().getCache().getMaxSize();
        ENABLE_THUMBNAIL_CACHE = configuration.getThumbNail().getCache().getEnabled();
        USE_EMBEDDED_THUMBNAIL = configuration.getTagImages().getPreview().getUseEmbeddedThumbnail();
        THUMBNAIL_TOOLTIP_STATE = configuration.getToolTips().getState();
        WARN_WHEN_REMOVE_CATEGORY = configuration.getTagImages().getCategories().getWarnWhenRemove();
        WARN_WHEN_REMOVE_CATEGORY_WITH_SUB_CATEGORIES  = configuration.getTagImages().getCategories().getWarnWhenRemoveWithSubCategories();
        AUTOMATICALLY_REMOVE_NON_EXISTING_IMAGE_PATHS_CHECKBOX = configuration.getTagImages().getImagesPaths().getAutomaticallyRemoveNonExistingImagePath();
        ADD_TO_IMAGEREPOSITOY_POLICY = configuration.getTagImages().getImagesPaths().getAddToRepositoryPolicy();
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
        rotateLogSize.getDocument().addDocumentListener(new RotateLogSizeJTextFieldListener());
        rotateLogSizeFactor.addItemListener(new RotateLogSizeFactorJComboBoxListener());
        logName.getDocument().addDocumentListener(new LogNameJTextFieldListener());
        logEntryTimeStampFormats.addItemListener(new LogEntryTimestampFormatsJComboBoxListener());
        manualRadioButton.addActionListener(new ManualRadioButtonListener());
        automaticRadioButton.addActionListener(new AutomaticRadioButtonListener());
        languageList.addListSelectionListener(new LanguageListListener());
        updatesEnabled.addChangeListener(new UpdatesEnabledCheckBoxListener());
        rotateLog.addChangeListener(new RotateLogCheckBoxListener());
        okButton.addActionListener(new OkButtonListener());
        applyButton.addActionListener(new ApplyButtonListener());
        cancelButton.addActionListener(new CancelButtonListener());
        thumbnailWidth.getDocument().addDocumentListener(new ThumbnailWidthJTextFieldListener());
        thumbnailHeight.getDocument().addDocumentListener(new ThumbnailHeightJTextFieldListener());
        createThumbnailIfMissingOrCorrupt.addChangeListener(new CreateThumbnailCheckBoxListener());
        maxCacheSize.getDocument().addDocumentListener(new ThumbnailMaxCacheSizeJTextFieldListener());
        clearCacheJButton.addActionListener(new ClearCacheButtonListener());
        enableThumbnailCache.addChangeListener(new EnableThumbnailCacheCheckBoxListener());
        maximumLengthOfCameraModelValueTextField.getDocument().addDocumentListener(new MaximumLengtOfCameraModelJTextFieldListener());
        removeSelectedImagePathsButton.addActionListener(new RemoveSelectedImagePathsButtonListener());
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        okButton     = new JButton(lang.get("common.button.ok.label"));
        applyButton  = new JButton(lang.get("common.button.apply.label"));
        cancelButton = new JButton(lang.get("common.button.cancel.label"));

        buttonPanel.add(okButton);
        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void createLoggingConfigurationPanel() {
        loggingConfigurationPanel = new JPanel(new GridBagLayout());
        loggingConfigurationPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), BorderFactory.createTitledBorder(lang.get("configviewer.tree.node.logging"))));

        GBHelper posLoggingPanel = new GBHelper();

        Logging logging = configuration.getLogging();

        JLabel logLevelsLabel = new JLabel(lang.get("configviewer.logging.label.logLevel.text"));
        logLevels = new JComboBox<Level>(Level.values());
        logLevels.setSelectedItem(logging.getLevel());

        developerMode = new JCheckBox(lang.get("configviewer.logging.label.developerMode.text"));
        developerMode.setSelected(logging.getDeveloperMode());

        rotateLog = new JCheckBox(lang.get("configviewer.logging.label.rotateLog.text"));
        rotateLog.setSelected(logging.getRotate());

        zipLog = new JCheckBox(lang.get("configviewer.logging.label.zipLog.text"));
        zipLog.setSelected(logging.getRotateZip());

        JLabel rotateLogSizeLabel = new JLabel(lang.get("configviewer.logging.label.rotateLogSize.text"));

        JPanel logSizePanel = new JPanel(new GridBagLayout());
        GBHelper posLogSizePanel = new GBHelper();

        rotateLogSize = new JTextField();
        rotateLogSize.setEnabled(logging.getRotate());

        long logSize = logging.getRotateSize();

        String [] factors = {"KiB", "MiB"};

        rotateLogSizeFactor = new JComboBox<String>(factors);

        /**
         * Set values to the rotate log size JTextField and rotate log size
         * factor JComboBox
         */
        longToHuman(logSize);

        logSizePanel.add(rotateLogSize, posLogSizePanel.expandW());
        logSizePanel.add(Box.createHorizontalStrut(10), posLogSizePanel.nextCol());
        logSizePanel.add(rotateLogSizeFactor, posLogSizePanel.nextCol());

        JLabel logNameLabel = new JLabel(lang.get("configviewer.logging.label.logName.text"));
        logName = new JTextField();
        logName.setText(logging.getFileName());

        JLabel logEntryTimeStampFormatLabel = new JLabel(lang.get("configviewer.logging.label.logEntryTimeStampFormat.text"));

        Set<String> formats = new LinkedHashSet<String>();

        formats.add(logging.getTimeStampFormat().toPattern());
        formats.add("yyyy-MM-dd'T'HH:mm:ss:SSSZ");
        formats.add("yyyyMMdd'T'HHmmssSSSZ");
        formats.add("yyyy-D'T'HH:mm:ss:SSSZ");
        formats.add("yyyyD'T'HHmmssSSSZ");
        formats.add("MM/dd/yyyy:HH:mm:ss:SSS");
        formats.add("dd/MM/yyyy:HH:mm:ss:SSS");

        logEntryTimeStampFormats = new JComboBox<String>(formats.toArray(new String[]{""}));

        JLabel logEntryTimeStampPreviewLabel = new JLabel(lang.get("configviewer.logging.label.logEntryTimeStampPreview.text"));
        logEntryTimeStampPreview = new JTextField();
        logEntryTimeStampPreview.setEditable(false);
        this.updatePreviewTimestamp();

        loggingConfigurationPanel.add(developerMode, posLoggingPanel.expandW());
        loggingConfigurationPanel.add(rotateLog, posLoggingPanel.nextRow().expandW());
        loggingConfigurationPanel.add(zipLog, posLoggingPanel.nextRow().expandW());
        loggingConfigurationPanel.add(Box.createVerticalStrut(5), posLoggingPanel.nextRow());
        loggingConfigurationPanel.add(rotateLogSizeLabel, posLoggingPanel.nextRow());
        loggingConfigurationPanel.add(logSizePanel, posLoggingPanel.nextCol().expandW());
        loggingConfigurationPanel.add(Box.createVerticalStrut(5), posLoggingPanel.nextRow());
        loggingConfigurationPanel.add(logLevelsLabel, posLoggingPanel.nextRow());
        loggingConfigurationPanel.add(logLevels, posLoggingPanel.nextCol().expandW());
        loggingConfigurationPanel.add(Box.createVerticalStrut(5), posLoggingPanel.nextRow());
        loggingConfigurationPanel.add(logNameLabel, posLoggingPanel.nextRow());
        loggingConfigurationPanel.add(logName, posLoggingPanel.nextCol().expandW());
        loggingConfigurationPanel.add(Box.createVerticalStrut(5), posLoggingPanel.nextRow());
        loggingConfigurationPanel.add(logEntryTimeStampFormatLabel, posLoggingPanel.nextRow());
        loggingConfigurationPanel.add(logEntryTimeStampFormats, posLoggingPanel.nextCol().expandW());
        loggingConfigurationPanel.add(Box.createVerticalStrut(5), posLoggingPanel.nextRow());
        loggingConfigurationPanel.add(logEntryTimeStampPreviewLabel, posLoggingPanel.nextRow());
        loggingConfigurationPanel.add(logEntryTimeStampPreview, posLoggingPanel.nextCol().expandW());
        loggingConfigurationPanel.add(Box.createVerticalGlue(), posLoggingPanel.nextRow().expandH());
    }

    private void createUpdateConfigurationPanel() {
        updatesConfigurationPanel = new JPanel(new GridBagLayout());
        updatesConfigurationPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), BorderFactory.createTitledBorder(lang.get("configviewer.tree.node.updates"))));

        GBHelper posUpdatesPanel = new GBHelper();

        UpdatesChecker updatesChecker = configuration.getUpdatesChecker();

        updatesEnabled = new JCheckBox(lang.get("configviewer.update.label.updateEnabled.text"));
        updatesEnabled.setSelected(updatesChecker.getEnabled());

        sendVersionInformationEnabled = new JCheckBox(lang.get("configviewer.update.label.attachVersionInformation.text"));
        sendVersionInformationEnabled.setSelected(updatesChecker.getAttachVersionInformation());
        sendVersionInformationEnabled.setEnabled(updatesEnabled.isSelected());

        updatesConfigurationPanel.add(updatesEnabled, posUpdatesPanel.expandW());
        updatesConfigurationPanel.add(sendVersionInformationEnabled, posUpdatesPanel.nextRow().expandW());
        updatesConfigurationPanel.add(Box.createVerticalGlue(), posUpdatesPanel.nextRow().expandH().expandW());
    }

    private void createRenameConfigurationPanel() {
        renameConfigurationPanel = new JPanel(new GridBagLayout());
        renameConfigurationPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), BorderFactory.createTitledBorder(lang.get("configviewer.tree.node.rename"))));

        GBHelper posRenamePanel = new GBHelper();

        RenameImages renameImages = configuration.getRenameImages();

        useLastModifiedDate = new JCheckBox(lang.get("configviewer.rename.label.useLastModifiedDate.text"));
        useLastModifiedDate.setSelected(renameImages.getUseLastModifiedDate());

        useLastModifiedTime = new JCheckBox(lang.get("configviewer.rename.label.useLastModifiedTime.text"));
        useLastModifiedTime.setSelected(renameImages.getUseLastModifiedTime());

        JLabel cameraModelValueLengthLabel = new JLabel(lang.get("configviewer.rename.label.maximumCameraModelValueLength"));
        maximumLengthOfCameraModelValueTextField = new JTextField(5);
        maximumLengthOfCameraModelValueTextField.setText(Integer.toString(renameImages.getCameraModelNameMaximumLength()));

        renameConfigurationPanel.add(cameraModelValueLengthLabel, posRenamePanel);
        renameConfigurationPanel.add(maximumLengthOfCameraModelValueTextField, posRenamePanel.nextCol().expandW());
        renameConfigurationPanel.add(useLastModifiedDate, posRenamePanel.nextRow().expandW());
        renameConfigurationPanel.add(useLastModifiedTime, posRenamePanel.nextRow().expandW());
        renameConfigurationPanel.add(Box.createVerticalGlue(), posRenamePanel.nextRow().expandW().expandH());
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
         * Start of Thumbnail ToolTip Area
         */

        toolTipDisabled = new JRadioButton(lang.get("configviewer.thumbnail.tooltip.label.disabled"));
        toolTipDisabled.setName("0");
        toolTipEnabled = new JRadioButton(lang.get("configviewer.thumbnail.tooltip.label.enabled"));
        toolTipEnabled.setName("1");
        toolTipExtended = new JRadioButton(lang.get("configviewer.thumbnail.tooltip.label.extended"));
        toolTipExtended.setName("2");

        ButtonGroup group = new ButtonGroup();

        group.add(toolTipDisabled);
        group.add(toolTipEnabled);
        group.add(toolTipExtended);

        ToolTips toolTips = configuration.getToolTips();

        String toolTipState = toolTips.getState();

        if (toolTipState.equalsIgnoreCase(toolTipDisabled.getName())) {
            toolTipDisabled.setSelected(true);
        } else if (toolTipState.equalsIgnoreCase(toolTipExtended.getName())) {
            toolTipExtended.setSelected(true);
        } else {
            toolTipEnabled.setSelected(true);
        }

        JPanel thumbnailToolTipPanel = new JPanel(new GridBagLayout());
        thumbnailToolTipPanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.thumbnail.tooltip.label")));

        GBHelper posThumbnailToolTipPanel = new GBHelper();

        thumbnailToolTipPanel.add(toolTipDisabled, posThumbnailToolTipPanel.expandW());
        thumbnailToolTipPanel.add(toolTipEnabled, posThumbnailToolTipPanel.nextRow().expandW());
        thumbnailToolTipPanel.add(toolTipExtended, posThumbnailToolTipPanel.nextRow().expandW());
        thumbnailToolTipPanel.add(Box.createVerticalGlue(), posThumbnailToolTipPanel.nextRow().expandH());

        thumbnailConfigurationPanel = new JPanel(new GridBagLayout());
        thumbnailConfigurationPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        GBHelper posThumbnailPanel = new GBHelper();

        thumbnailConfigurationPanel.add(thumbnailCreationPanel, posThumbnailPanel.expandW().expandH());
        thumbnailConfigurationPanel.add(thumbnailCachePanel, posThumbnailPanel.nextRow().expandW().expandH());
        thumbnailConfigurationPanel.add(thumbnailToolTipPanel, posThumbnailPanel.nextRow().expandW().expandH());
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
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

        importedCategoriesList = new JList(importedCategoriesListModel);
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

        imageRepositoriesList = new JList<ImageRepositoryItem>(ModelInstanceLibrary.getInstance().getImageRepositoryListModel());
        imageRepositoriesList.setCellRenderer(new CustomCellRenderer());
        imageRepositoriesList.setVisibleRowCount(5);

        JScrollPane imageRepositoriesScrollPane = new JScrollPane(imageRepositoriesList);
        imageRepositoriesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel imageRepositoriesAdditionModePanel = new JPanel(new GridBagLayout());
        imageRepositoriesAdditionModePanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.tag.imageRepositoriesAdditionMode.label")));

        automaticallyRemoveNonExistingImagePathsCheckBox = new JCheckBox(lang.get("configviewer.tag.imageRepositories.label.removeNonExistingPaths"));
        automaticallyRemoveNonExistingImagePathsCheckBox.setSelected(tagImagesPaths.getAutomaticallyRemoveNonExistingImagePath());
        automaticallyRemoveNonExistingImagePathsCheckBox.setToolTipText(lang.get("configviewer.tag.imageRepositories.label.removeNonExistingPaths.tooltip"));

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
        imageRepositoriesContentPanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.tag.imageRepositoriesContent.label")));

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
        buttonPanel.add(automaticallyRemoveNonExistingImagePathsCheckBox, posButtonPanel.nextRow());

        GBHelper posImageRepositoriesContent = new GBHelper();

        imageRepositoriesContentPanel.add(imageRepositoriesScrollPane, posImageRepositoriesContent.expandW().expandH());
        imageRepositoriesContentPanel.add(Box.createVerticalStrut(2), posImageRepositoriesContent.nextRow());
        imageRepositoriesContentPanel.add(buttonPanel, posImageRepositoriesContent.nextRow().align(GridBagConstraints.WEST));

        tagConfigurationPanel = new JPanel(new GridBagLayout());
        tagConfigurationPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

        GBHelper posTagPanel = new GBHelper();

        tagConfigurationPanel.add(previewImagePanel, posTagPanel.expandW().expandH());
        tagConfigurationPanel.add(categoriesPanel, posTagPanel.nextRow().expandW().expandH());
        tagConfigurationPanel.add(importedCategoriesPanel, posTagPanel.nextRow().expandW().expandH());
        tagConfigurationPanel.add(imageRepositoriesAdditionModePanel, posTagPanel.nextRow().expandW().expandH());
        tagConfigurationPanel.add(imageRepositoriesContentPanel, posTagPanel.nextRow().expandW().expandH());
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

    private void updatePreviewTimestamp() {
        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat(logEntryTimeStampFormats.getSelectedItem().toString());
        logEntryTimeStampPreview.setText(sdf.format(date));
    }

    private boolean updateConfiguration() {

        if(!validateLogName(logName.getText())) {
            return  false;
        }
        if(!validateLogRotateSize()) {
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

        if(!validateMaximumLengtOfCameraModel()) {
            return false;
        }

        /**
         * Update Logging Configuration
         */
        Logging logging = configuration.getLogging();

        logging.setDeveloperMode(developerMode.isSelected());
        logging.setFileName(logName.getText().trim());
        logging.setLevel((Level)logLevels.getSelectedItem());
        logging.setRotate(rotateLog.isSelected());
        logging.setRotateSize(calculateRotateLogSize(Long.parseLong(rotateLogSize.getText()), rotateLogSizeFactor.getSelectedItem().toString()));
        logging.setRotateZip(zipLog.isSelected());
        logging.setTimeStampFormat(new SimpleDateFormat((String)logEntryTimeStampFormats.getSelectedItem()));

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
        RenameImages renameImages = configuration.getRenameImages();

        renameImages.setCameraModelNameMaximumLength(Integer.parseInt(maximumLengthOfCameraModelValueTextField.getText()));
        renameImages.setUseLastModifiedDate(useLastModifiedDate.isSelected());
        renameImages.setUseLastModifiedTime(useLastModifiedTime.isSelected());

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

        /***
         * Update ToolTips configuration
         */
        configuration.getToolTips().setState(getToolTipState());

        /**
         * Update Tag Configuration
         */
        TagImages tagImages = configuration.getTagImages();

        TagImagesCategories tagImagesCategories = tagImages.getCategories();

        tagImagesCategories.setWarnWhenRemove(warnWhenRemoveCategory.isSelected());
        tagImagesCategories.setWarnWhenRemoveWithSubCategories(warnWhenRemoveCategoryWithSubCategories.isSelected());

        TagImagesPaths tagImagesPaths = tagImages.getImagesPaths();

        tagImagesPaths.setAddToRepositoryPolicy(Integer.parseInt(getAddToRepositoryPolicy()));
        tagImagesPaths.setAutomaticallyRemoveNonExistingImagePath(automaticallyRemoveNonExistingImagePathsCheckBox.isSelected());

        TagImagesPreview tagImagesPreview = tagImages.getPreview();

        tagImagesPreview.setUseEmbeddedThumbnail(useEmbeddedThumbnail.isSelected());

        /**
         * Show configuration changes.
         */
        this.displayChangedConfigurationMessage();

        return true;
    }

    private String getToolTipState() {
        if (toolTipDisabled.isSelected()) {
            return toolTipDisabled.getName();
        } else if (toolTipEnabled.isSelected()) {
            return toolTipEnabled.getName();
        } else {
            return toolTipExtended.getName();
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

        if(DEVELOPER_MODE != developerMode.isSelected()){
            displayMessage.append(lang.get("configviewer.logging.label.developerMode.text") + ": " + developerMode.isSelected() + " (" + DEVELOPER_MODE + ")\n");
        }

        if(LOG_ROTATE != rotateLog.isSelected()){
            displayMessage.append(lang.get("configviewer.logging.label.rotateLog.text") + ": " + rotateLog.isSelected() + " (" + LOG_ROTATE + ")\n");
        }

        if(LOG_ROTATE_ZIP != zipLog.isSelected()){
            displayMessage.append(lang.get("configviewer.logging.label.zipLog.text") + ": " + zipLog.isSelected() + " (" + LOG_ROTATE_ZIP + ")\n");
        }

        if(!LOG_ROTATE_SIZE.equals(calculateRotateLogSize(rotateLogSize.getText(), rotateLogSizeFactor.getSelectedItem().toString()))){
            displayMessage.append(lang.get("configviewer.logging.label.rotateLogSize.text") + ": " + rotateLogSize.getText() + " " + rotateLogSizeFactor.getSelectedItem() + " (" + parseRotateLongSize(LOG_ROTATE_SIZE, rotateLogSizeFactor.getSelectedItem().toString()) + " " + rotateLogSizeFactor.getSelectedItem()+ ")\n");
        }

        if(LOG_LEVEL != (Level)logLevels.getSelectedItem()) {
            displayMessage.append(lang.get("configviewer.logging.label.logLevel.text") + ": " + logLevels.getSelectedItem() + " (" + LOG_LEVEL + ")\n");
        }

        if(!LOG_NAME.equals(logName.getText())){
            displayMessage.append(lang.get("configviewer.logging.label.logName.text") + ": " + logName.getText() + " (" + LOG_NAME + ")\n");
        }

        if(!LOG_ENTRY_TIMESTAMP_FORMAT.toPattern().equals(logEntryTimeStampFormats.getSelectedItem())) {
            displayMessage.append(lang.get("configviewer.logging.label.logEntryTimeStampFormat.text") + ": " + logEntryTimeStampFormats.getSelectedItem() + " (" + LOG_ENTRY_TIMESTAMP_FORMAT.toPattern() + ")\n");
        }

        if(UPDATE_CHECK_ENABLED != updatesEnabled.isSelected()){
            displayMessage.append(lang.get("configviewer.update.label.updateEnabled.text") + ": " + updatesEnabled.isSelected() + " (" + UPDATE_CHECK_ENABLED + ")\n");
        }

        if(UPDATE_CHECK_ATTACH_VERSION != sendVersionInformationEnabled.isSelected()){
            displayMessage.append(lang.get("configviewer.update.label.attachVersionInformation.text") + ": " + sendVersionInformationEnabled.isSelected() + " (" + UPDATE_CHECK_ATTACH_VERSION + ")\n");
        }

        if(USE_LAST_MODIFIED_DATE != useLastModifiedDate.isSelected()){
            displayMessage.append(lang.get("configviewer.rename.label.useLastModifiedDate.text") + ": " + useLastModifiedDate.isSelected() + " (" + USE_LAST_MODIFIED_DATE + ")\n");
        }

        if(USE_LAST_MODIFIED_TIME != useLastModifiedTime.isSelected()){
            displayMessage.append(lang.get("configviewer.rename.label.useLastModifiedTime.text") + ": " + useLastModifiedTime.isSelected() + " (" + USE_LAST_MODIFIED_TIME + ")\n");
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

        if(!MAXIMUM_LENGTH_OF_CAMERA_MODEL.equals(Integer.parseInt(maximumLengthOfCameraModelValueTextField.getText()))) {
            displayMessage.append(lang.get("configviewer.rename.label.maximumCameraModelValueLength") + ": " + maximumLengthOfCameraModelValueTextField.getText() + " (" + MAXIMUM_LENGTH_OF_CAMERA_MODEL + ")\n");
        }

        if(USE_EMBEDDED_THUMBNAIL != useEmbeddedThumbnail.isSelected()) {
            if (USE_EMBEDDED_THUMBNAIL) {
                displayMessage.append(lang.get("configviewer.tag.previewimage.label.scaledthumbnail")+ " (" + lang.get("configviewer.tag.previewimage.label.embeddedthumbnail") + ")\n");
            } else {
                displayMessage.append(lang.get("configviewer.tag.previewimage.label.embeddedthumbnail")+ " (" + lang.get("configviewer.tag.previewimage.label.scaledthumbnail") + ")\n");
            }
        }

        if(!THUMBNAIL_TOOLTIP_STATE.equals(getToolTipState())) {
            int previousThumbNailToolTipState = -1;
            String previous = "";

            try {
                previousThumbNailToolTipState = Integer.parseInt(THUMBNAIL_TOOLTIP_STATE);
            } catch (NumberFormatException nfex) {
                previousThumbNailToolTipState = 1;
            }

            switch (previousThumbNailToolTipState) {
            case 0:
                previous = lang.get("configviewer.thumbnail.tooltip.label.disabled");
                break;
            case 1:
                previous = lang.get("configviewer.thumbnail.tooltip.label.enabled");
                break;
            case 2:
                previous = lang.get("configviewer.thumbnail.tooltip.label.extended");
                break;
            }

            int currentThumbNailToolTipState = Integer.parseInt(getToolTipState());
            String current = "";

            switch (currentThumbNailToolTipState) {
            case 0:
                current = lang.get("configviewer.thumbnail.tooltip.label.disabled");
                break;
            case 1:
                current = lang.get("configviewer.thumbnail.tooltip.label.enabled");
                break;
            case 2:
                current = lang.get("configviewer.thumbnail.tooltip.label.extended");
                break;
            }

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

        if(AUTOMATICALLY_REMOVE_NON_EXISTING_IMAGE_PATHS_CHECKBOX != automaticallyRemoveNonExistingImagePathsCheckBox.isSelected()) {
            displayMessage.append(lang.get("configviewer.tag.imageRepositories.label.removeNonExistingPaths") + ": " + automaticallyRemoveNonExistingImagePathsCheckBox.isSelected() + " (" + AUTOMATICALLY_REMOVE_NON_EXISTING_IMAGE_PATHS_CHECKBOX + ")\n");
        }

        if(displayMessage.length() > 0) {
            JOptionPane.showMessageDialog(this, preMessage + "\n\n" + displayMessage +  "\n" + postMessage, lang.get("errormessage.maingui.informationMessageLabel"), JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean validateLogName(String logName) {
        boolean isValid = true;

        int result = PathUtil.validateString(logName, false);

        if (result > -1) {
            isValid = false;
            JOptionPane.showMessageDialog(this, lang.get("common.message.error.invalidFileName") + " " + (char)result, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
        }
        return isValid;
    }

    private boolean validateLogRotateSize() {
        boolean isValid = true;

        try {
            Long size = Long.parseLong(rotateLogSize.getText());

            String factor = rotateLogSizeFactor.getSelectedItem().toString();

            size = calculateRotateLogSize(size, factor);

            if(size > 100 * 1024 * 1024) {
                isValid = false;

                if(factor.equals("KiB")) {
                    JOptionPane.showMessageDialog(this, lang.get("configviewer.errormessage.rotateLogSizeToLargeKiB"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, lang.get("configviewer.errormessage.rotateLogSizeToLargeMiB"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                }
            }

            if(size < 10 * 1024) {
                JOptionPane.showMessageDialog(this, lang.get("configviewer.errormessage.rotateLogSizeToSmall"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException nfex) {
            isValid = false;
            JOptionPane.showMessageDialog(this, lang.get("configviewer.errormessage.rotateLogSizeNotAnInteger"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
        }
        return isValid;
    }

    private Long calculateRotateLogSize(String size, String factor) {
        return calculateRotateLogSize(Long.parseLong(size), factor);
    }

    private String parseRotateLongSize(Long size, String factor) {
        if (factor.equals("KiB")) {
            size /= 1024;
        } else {
            size /= 1024 * 1024;
        }
        return Long.toString(size);
    }

    private long calculateRotateLogSize(Long size, String factor) {
        if (factor.equals("KiB")) {
            size *= 1024;
        } else {
            size *= 1024 * 1024;
        }
        return size;
    }

    private void longToHuman (Long logSize) {
        if (logSize / (1024 * 1024) > 1) {
            rotateLogSize.setText(Long.toString(logSize / (1024 * 1024)));
            rotateLogSizeFactor.setSelectedIndex(1);
        } else {
            rotateLogSize.setText(Long.toString(logSize / (1024)));
            rotateLogSizeFactor.setSelectedIndex(0);
        }
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

    private boolean validateMaximumLengtOfCameraModel() {
        if(!StringUtil.isInt(maximumLengthOfCameraModelValueTextField.getText(), true)) {
            JOptionPane.showMessageDialog(this, lang.get("configviewer.rename.label.maximumCameraModelValueLengthNotNegative"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

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
                    ((DefaultListModel<Object>)importedCategoriesList.getModel()).removeElement(selectedValue);
                }
            }
        }

        // ... and remove the found matches.
        for (String key : keysToRemove) {
            importedCategoriesMap.remove(key);
        }

        importedCategoriesList.clearSelection();
    }

    private class RotateLogSizeJTextFieldListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            validateLogRotateSize();
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
        }
    }

    private class RotateLogSizeFactorJComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                validateLogRotateSize();
            }
        }
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

    private class  MaximumLengtOfCameraModelJTextFieldListener implements DocumentListener {

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            validateMaximumLengtOfCameraModel();
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
                }
            }
        }
    }

    private class LogEntryTimestampFormatsJComboBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                updatePreviewTimestamp();
            }
        }
    }

    private class LogNameJTextFieldListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            validateLogName(logName.getText());
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
        }
        @Override
        public void changedUpdate(DocumentEvent e) {
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

    private class RotateLogCheckBoxListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            rotateLogSize.setEnabled(rotateLog.isSelected());
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

    private class RemoveSelectedImagePathsButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            SortedListModel<ImageRepositoryItem> imageRepositroyListModel = ModelInstanceLibrary.getInstance().getImageRepositoryListModel();

            List<ImageRepositoryItem> selectedValues = imageRepositoriesList.getSelectedValuesList();

            StringBuilder paths = new StringBuilder();

            for (Object selectedValue : selectedValues) {
                ImageRepositoryItem iri = (ImageRepositoryItem)selectedValue;

                String status = "";

                switch (iri.getPathStatus()) {
                case EXISTS:
                    status = lang.get("configviewer.tag.imageRepositories.label.exists");
                    break;
                case NOT_AVAILABLE:
                    status = lang.get("configviewer.tag.imageRepositories.label.notAvailable");
                    break;
                case DOES_NOT_EXIST:
                    status = lang.get("configviewer.tag.imageRepositories.label.doesNotExist");
                    break;
                }
                paths.append(iri.getPath() + " (" + status + ")");
                paths.append(C.LS);
            }

            int result = displayConfirmDialog(lang.get("configviewer.tag.imageRepositories.label.pathsWillBeRemoved") + C.LS + C.LS + paths.toString(), lang.get("common.confirmation"), JOptionPane.OK_CANCEL_OPTION);

            if (result == 0) {
                for (ImageRepositoryItem selectedValue : selectedValues) {
                    imageRepositroyListModel.removeElement(selectedValue);
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