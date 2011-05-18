package moller.javapeg.program.config;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Point;
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
import java.util.LinkedHashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
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
import moller.javapeg.program.Gap;
import moller.javapeg.program.enumerations.Level;
import moller.javapeg.program.gui.CustomCellRenderer;
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
	private JComboBox rotateLogSizeFactor;
	private JComboBox logLevels;
	private JComboBox logEntryTimeStampFormats;

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
	private JList languageList;

	/**
	 * Variables for the rename panel
	 */
	private JCheckBox useLastModifiedDate;
	private JCheckBox useLastModifiedTime;
	private JTextField maximumLengthOfCameraModelValueTextField;

	private  JLabel currentLanguage;

	private JRadioButton manualRadioButton;
	private JRadioButton automaticRadioButton;

	/**
	 * Variables for the thumbnail panel
	 */
	private JCheckBox createThumbnailIfMissingOrCorrupt;
	private JTextField thumbnailWidth;
	private JTextField thumbnailHeight;
	private JComboBox thumbnailCreationAlgorithm;
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

	private JList imageRepositoriesList;

	private final Config   conf;
	private final Logger   logger;
	private final Language lang;

	// Configuration values read from configuration.
	private String LOG_LEVEL;
	private String LOG_NAME;
	private String LOG_ROTATE_SIZE;
	private String LOG_ENTRY_TIMESTAMP_FORMAT;
	private String GUI_LANGUAGE_ISO6391;
	private String THUMBNAIL_WIDTH;
	private String THUMBNAIL_HEIGHT;
	private String CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT_ALGORITHM;
	private String THUMBNAIL_MAX_CACHE_SIZE;
	private String MAXIMUM_LENGTH_OF_CAMERA_MODEL;
	private String THUMBNAIL_TOOLTIP_STATE;
	private String ADD_TO_IMAGEREPOSITOY_POLICY;

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
		conf   = Config.getInstance();
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

		LOG_LEVEL = conf.getStringProperty("logger.log.level");
		DEVELOPER_MODE = conf.getBooleanProperty("logger.developerMode");
		LOG_ROTATE = conf.getBooleanProperty("logger.log.rotate");
		LOG_ROTATE_ZIP = conf.getBooleanProperty("logger.log.rotate.zip");
		LOG_ROTATE_SIZE = conf.getStringProperty("logger.log.rotate.size");
		LOG_NAME = conf.getStringProperty("logger.log.name");
		LOG_ENTRY_TIMESTAMP_FORMAT = conf.getStringProperty("logger.log.entry.timestamp.format");
		UPDATE_CHECK_ENABLED = conf.getBooleanProperty("updatechecker.enabled");
		UPDATE_CHECK_ATTACH_VERSION = conf.getBooleanProperty("updatechecker.attachVersionInformation");
		USE_LAST_MODIFIED_DATE = conf.getBooleanProperty("rename.use.lastmodified.date");
		USE_LAST_MODIFIED_TIME = conf.getBooleanProperty("rename.use.lastmodified.time");
		AUTOMATIC_LANGUAGE_SELECTION = conf.getBooleanProperty("automaticLanguageSelection");
		GUI_LANGUAGE_ISO6391 = conf.getStringProperty("gUILanguageISO6391");
		CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT = conf.getBooleanProperty("thumbnails.view.create-if-missing-or-corrupt");
		THUMBNAIL_WIDTH = conf.getStringProperty("thumbnails.view.width");
		THUMBNAIL_HEIGHT = conf.getStringProperty("thumbnails.view.height");
		CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT_ALGORITHM = conf.getStringProperty("thumbnails.view.create.algorithm");
		THUMBNAIL_MAX_CACHE_SIZE = conf.getStringProperty("thumbnails.cache.max-size");
		ENABLE_THUMBNAIL_CACHE = conf.getBooleanProperty("thumbnails.cache.enabled");
		MAXIMUM_LENGTH_OF_CAMERA_MODEL = conf.getStringProperty("rename.maximum.length.camera-model");
		USE_EMBEDDED_THUMBNAIL = conf.getBooleanProperty("tab.tagImage.previewImage.useEmbeddedThumbnail");
		THUMBNAIL_TOOLTIP_STATE = conf.getStringProperty("thumbnails.tooltip.state");
		WARN_WHEN_REMOVE_CATEGORY = conf.getBooleanProperty("categories.warnWhenRemoveCategory");
		WARN_WHEN_REMOVE_CATEGORY_WITH_SUB_CATEGORIES  = conf.getBooleanProperty("categories.warnWhenRemoveCategoryWithSubCategories");
		AUTOMATICALLY_REMOVE_NON_EXISTING_IMAGE_PATHS_CHECKBOX = conf.getBooleanProperty("imageRepository.automaticallyRemoveNonExistingImagePaths");
		ADD_TO_IMAGEREPOSITOY_POLICY = conf.getStringProperty("imageRepository.addToRepositoryPolicy");
	}

	private void initiateWindow() {

		this.setSize(new Dimension(conf.getIntProperty("configViewerGUI.window.width"),conf.getIntProperty("configViewerGUI.window.height")));

		Point xyFromConfig = new Point(conf.getIntProperty("configViewerGUI.window.location.x"),conf.getIntProperty("configViewerGUI.window.location.y"));

		if(Screen.isOnScreen(xyFromConfig)) {
			this.setLocation(xyFromConfig);
		} else {
			this.setLocation(0,0);
			JOptionPane.showMessageDialog(null, lang.get("configviewer.window.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			logger.logERROR("Could not set location of Config Viewer GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");
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

		String logLevel = conf.getStringProperty("logger.log.level");
		int seletedIndex = 0;

		for (Level level : Level.values()) {
			if(level.toString().equals(logLevel)) {
				seletedIndex = level.ordinal();
				break;
			}
		}
		JLabel logLevelsLabel = new JLabel(lang.get("configviewer.logging.label.logLevel.text"));
		logLevels = new JComboBox(Level.values());
		logLevels.setSelectedIndex(seletedIndex);

		JLabel developerModeLabel = new JLabel(lang.get("configviewer.logging.label.developerMode.text"));
		developerMode = new JCheckBox();
		developerMode.setSelected(conf.getBooleanProperty("logger.developerMode"));

		JLabel rotateLogLabel = new JLabel(lang.get("configviewer.logging.label.rotateLog.text"));
		rotateLog = new JCheckBox();
		rotateLog.setSelected(conf.getBooleanProperty("logger.log.rotate"));

		JLabel zipLogLabel = new JLabel(lang.get("configviewer.logging.label.zipLog.text"));
		zipLog = new JCheckBox();
		zipLog.setSelected(conf.getBooleanProperty("logger.log.rotate.zip"));

		JLabel rotateLogSizeLabel = new JLabel(lang.get("configviewer.logging.label.rotateLogSize.text"));

		JPanel logSizePanel = new JPanel(new GridBagLayout());
		GBHelper posLogSizePanel = new GBHelper();

		rotateLogSize = new JTextField();
		rotateLogSize.setEnabled(conf.getBooleanProperty("logger.log.rotate"));

		long logSize = 0;

		try {
			logSize = Long.parseLong(conf.getStringProperty("logger.log.rotate.size"));
		} catch (NumberFormatException nfex) {
			logSize = 1024000;
		}

		String [] factors = {"KiB", "MiB"};

		rotateLogSizeFactor = new JComboBox(factors);

		/**
		 * Set values to the rotate log size JTextField and rotate log size
		 * factor JComboBox
		 */
		longToHuman(logSize);

		logSizePanel.add(rotateLogSize, posLogSizePanel.expandW());
		logSizePanel.add(new Gap(10), posLogSizePanel.nextCol());
		logSizePanel.add(rotateLogSizeFactor, posLogSizePanel.nextCol());

		JLabel logNameLabel = new JLabel(lang.get("configviewer.logging.label.logName.text"));
		logName = new JTextField();
		logName.setText(conf.getStringProperty("logger.log.name"));

		JLabel logEntryTimeStampFormatLabel = new JLabel(lang.get("configviewer.logging.label.logEntryTimeStampFormat.text"));

		Set<String> formats = new LinkedHashSet<String>();

		formats.add(conf.getStringProperty("logger.log.entry.timestamp.format"));
		formats.add("yyyy-MM-dd'T'HH:mm:ss:SSSZ");
		formats.add("yyyyMMdd'T'HHmmssSSSZ");
		formats.add("yyyy-D'T'HH:mm:ss:SSSZ");
		formats.add("yyyyD'T'HHmmssSSSZ");
		formats.add("MM/dd/yyyy:HH:mm:ss:SSS");
		formats.add("dd/MM/yyyy:HH:mm:ss:SSS");

		logEntryTimeStampFormats = new JComboBox(formats.toArray());

		JLabel logEntryTimeStampPreviewLabel = new JLabel(lang.get("configviewer.logging.label.logEntryTimeStampPreview.text"));
		logEntryTimeStampPreview = new JTextField();
		logEntryTimeStampPreview.setEditable(false);
		this.updatePreviewTimestamp();

		loggingConfigurationPanel.add(developerModeLabel, posLoggingPanel);
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(developerMode, posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(rotateLogLabel, posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(rotateLog, posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(zipLogLabel, posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(zipLog, posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(rotateLogSizeLabel, posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(logSizePanel, posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(logLevelsLabel, posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(logLevels, posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(logNameLabel, posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(logName, posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(logEntryTimeStampFormatLabel, posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(logEntryTimeStampFormats, posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(logEntryTimeStampPreviewLabel, posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(logEntryTimeStampPreview, posLoggingPanel.nextCol());
	}

	private void createUpdateConfigurationPanel() {
		updatesConfigurationPanel = new JPanel(new GridBagLayout());
		updatesConfigurationPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), BorderFactory.createTitledBorder(lang.get("configviewer.tree.node.updates"))));

		GBHelper posUpdatesPanel = new GBHelper();

		updatesEnabled = new JCheckBox(lang.get("configviewer.update.label.updateEnabled.text"));
		updatesEnabled.setSelected(conf.getBooleanProperty("updatechecker.enabled"));

		sendVersionInformationEnabled = new JCheckBox(lang.get("configviewer.update.label.attachVersionInformation.text"));
		sendVersionInformationEnabled.setSelected(conf.getBooleanProperty("updatechecker.attachVersionInformation"));
		sendVersionInformationEnabled.setEnabled(updatesEnabled.isSelected());

		updatesConfigurationPanel.add(updatesEnabled, posUpdatesPanel.expandW());
		updatesConfigurationPanel.add(sendVersionInformationEnabled, posUpdatesPanel.nextRow().expandW());
	}

	private void createRenameConfigurationPanel() {
		renameConfigurationPanel = new JPanel(new GridBagLayout());
		renameConfigurationPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), BorderFactory.createTitledBorder(lang.get("configviewer.tree.node.rename"))));

		GBHelper posRenamePanel = new GBHelper();

		useLastModifiedDate = new JCheckBox(lang.get("configviewer.rename.label.useLastModifiedDate.text"));
		useLastModifiedDate.setSelected(conf.getBooleanProperty("rename.use.lastmodified.date"));

		useLastModifiedTime = new JCheckBox(lang.get("configviewer.rename.label.useLastModifiedTime.text"));
		useLastModifiedTime.setSelected(conf.getBooleanProperty("rename.use.lastmodified.time"));

		JLabel cameraModelValueLengthLabel = new JLabel(lang.get("configviewer.rename.label.maximumCameraModelValueLength"));
		maximumLengthOfCameraModelValueTextField = new JTextField(5);
		maximumLengthOfCameraModelValueTextField.setText(conf.getStringProperty("rename.maximum.length.camera-model"));

		renameConfigurationPanel.add(cameraModelValueLengthLabel, posRenamePanel);
		renameConfigurationPanel.add(new Gap(10), posRenamePanel.nextCol());
		renameConfigurationPanel.add(maximumLengthOfCameraModelValueTextField, posRenamePanel.nextCol().expandW());
		renameConfigurationPanel.add(useLastModifiedDate, posRenamePanel.nextRow().expandW());
		renameConfigurationPanel.add(useLastModifiedTime, posRenamePanel.nextRow().expandW());
	}

	private void createLanguageConfigurationPanel() {
		languageConfigurationPanel = new JPanel(new GridBagLayout());

		manualRadioButton = new JRadioButton(lang.get("configviewer.language.radiobutton.manual"));
		automaticRadioButton = new JRadioButton(lang.get("configviewer.language.radiobutton.automatic"));

		ButtonGroup languageSelectionMode = new ButtonGroup();
		languageSelectionMode.add(manualRadioButton);
		languageSelectionMode.add(automaticRadioButton);

		if(conf.getBooleanProperty("automaticLanguageSelection")) {
			if(!conf.getStringProperty("gUILanguageISO6391").equals(System.getProperty("user.language"))) {
				conf.setStringProperty("gUILanguageISO6391", System.getProperty("user.language"));
			}
		}

		JPanel selectionModePanel = new JPanel(new GridBagLayout());
		selectionModePanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.language.label.selectionMode")));

		GBHelper posSelectionMode = new GBHelper();

		selectionModePanel.add(manualRadioButton, posSelectionMode.expandW());
		selectionModePanel.add(automaticRadioButton, posSelectionMode.nextRow().expandW());

		languageList = new JList(ConfigUtil.listLanguagesFiles());

		if(conf.getBooleanProperty("automaticLanguageSelection")) {
			languageList.setEnabled(false);
		}

		JScrollPane languageListScrollPane = new JScrollPane(languageList);

		JPanel availableLanguagesPanel = new JPanel(new GridBagLayout());
		availableLanguagesPanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.language.label.availableLanguages")));

		GBHelper posAvailableLanguages = new GBHelper();

		availableLanguagesPanel.add(languageListScrollPane, posAvailableLanguages.expandW().expandH());

		currentLanguage = new JLabel(ConfigUtil.resolveCodeToLanguageName(conf.getStringProperty("gUILanguageISO6391")));

		JPanel currentLanguagePanel = new JPanel(new GridBagLayout());
		currentLanguagePanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.language.label.currentLanguage")));

		GBHelper posCurrentLanguage = new GBHelper();

		currentLanguagePanel.add(currentLanguage, posCurrentLanguage.expandW());

		GBHelper posLanguagePanel = new GBHelper();

		languageConfigurationPanel.add(currentLanguagePanel, posLanguagePanel.expandW().expandH());
		languageConfigurationPanel.add(selectionModePanel, posLanguagePanel.nextRow().expandW().expandH());
		languageConfigurationPanel.add(availableLanguagesPanel, posLanguagePanel.nextRow().expandW().expandH());

		if(conf.getBooleanProperty("automaticLanguageSelection")) {
			automaticRadioButton.setSelected(true);
		} else {
			manualRadioButton.setSelected(true);
		}
	}

	private void createThumbnailConfigurationPanel() {

		/**
		 * Start of Thumbnail Creation Area
		 */
		createThumbnailIfMissingOrCorrupt = new JCheckBox(lang.get("configviewer.thumbnail.creation.label.missingOrCorrupt"));
		createThumbnailIfMissingOrCorrupt.setSelected(conf.getBooleanProperty("thumbnails.view.create-if-missing-or-corrupt"));

		JLabel thumbnailWidthLabel = new JLabel(lang.get("configviewer.thumbnail.creation.label.thumbnail.width"));
		thumbnailWidth = new JTextField(conf.getStringProperty("thumbnails.view.width"));
		thumbnailWidth.setColumns(5);
		thumbnailWidth.setEnabled(conf.getBooleanProperty("thumbnails.view.create-if-missing-or-corrupt"));

		JLabel thumbnailHeightLabel = new JLabel(lang.get("configviewer.thumbnail.creation.label.thumbnail.height"));
		thumbnailHeight = new JTextField(conf.getStringProperty("thumbnails.view.height"));
		thumbnailHeight.setColumns(5);
		thumbnailHeight.setEnabled(conf.getBooleanProperty("thumbnails.view.create-if-missing-or-corrupt"));

		JLabel thumbnailCreationMode = new JLabel(lang.get("configviewer.thumbnail.creation.label.algorithm"));

		String scaleAlgorithm = conf.getStringProperty("thumbnails.view.create.algorithm");
		int seletedIndex = 0;

		for (JPEGScaleAlgorithm algorithm : JPEGScaleAlgorithm.values()) {
			if(algorithm.toString().equals(scaleAlgorithm)) {
				seletedIndex = algorithm.ordinal();
				break;
			}
		}

		thumbnailCreationAlgorithm = new JComboBox(JPEGScaleAlgorithm.values());
		thumbnailCreationAlgorithm.setSelectedIndex(seletedIndex);
		thumbnailCreationAlgorithm.invalidate();
		thumbnailCreationAlgorithm.setEnabled(conf.getBooleanProperty("thumbnails.view.create-if-missing-or-corrupt"));

		JPanel thumbnailCreationPanel = new JPanel(new GridBagLayout());
		thumbnailCreationPanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.thumbnail.creation.label")));

		GBHelper posThumbnailCreationPanel = new GBHelper();

		thumbnailCreationPanel.add(createThumbnailIfMissingOrCorrupt, posThumbnailCreationPanel.expandW());
		thumbnailCreationPanel.add(new Gap(5), posThumbnailCreationPanel.nextRow());
		thumbnailCreationPanel.add(thumbnailWidthLabel, posThumbnailCreationPanel.nextRow());
		thumbnailCreationPanel.add(new Gap(10), posThumbnailCreationPanel.nextCol());
		thumbnailCreationPanel.add(thumbnailWidth, posThumbnailCreationPanel.nextCol());
		thumbnailCreationPanel.add(new Gap(5), posThumbnailCreationPanel.nextRow());
		thumbnailCreationPanel.add(thumbnailHeightLabel, posThumbnailCreationPanel.nextRow());
		thumbnailCreationPanel.add(new Gap(10), posThumbnailCreationPanel.nextCol());
		thumbnailCreationPanel.add(thumbnailHeight, posThumbnailCreationPanel.nextCol());
		thumbnailCreationPanel.add(new Gap(10), posThumbnailCreationPanel.nextRow());
		thumbnailCreationPanel.add(thumbnailCreationMode, posThumbnailCreationPanel.nextRow());
		thumbnailCreationPanel.add(new Gap(10), posThumbnailCreationPanel.nextCol());
		thumbnailCreationPanel.add(thumbnailCreationAlgorithm, posThumbnailCreationPanel.nextCol());

		/**
		 * Start of Thumbnail Cache Area
		 */
		enableThumbnailCache = new JCheckBox(lang.get("configviewer.thumbnail.cache.label.enable"));
		enableThumbnailCache.setSelected(conf.getBooleanProperty("thumbnails.cache.enabled"));


		JPEGThumbNailCache jptc = JPEGThumbNailCache.getInstance();
		JLabel cacheSizeLabelHeading = new JLabel(lang.get("configviewer.thumbnail.cache.label.size") + ": ");

		cacheSizeLabel = new JLabel(Integer.toString(jptc.getCurrentSize()));
		cacheSizeLabel.setEnabled(conf.getBooleanProperty("thumbnails.cache.enabled"));

		JLabel cacheMaxSizeLabel = new JLabel(lang.get("configviewer.thumbnail.cache.label.size.max") + ": ");

		maxCacheSize = new JTextField(6);
		maxCacheSize.setText(Integer.toString(conf.getIntProperty("thumbnails.cache.max-size")));
		maxCacheSize.setEnabled(conf.getBooleanProperty("thumbnails.cache.enabled"));

		JLabel clearCachLabel = new JLabel(lang.get("configviewer.thumbnail.cache.label.clear"));

		try {
			Icon cleanThumbNailCacheImageIcon = ImageUtil.getIcon(StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/remove.gif"), true);
			clearCacheJButton = new JButton(cleanThumbNailCacheImageIcon);
		} catch (IOException iox) {
			clearCacheJButton = new JButton("X");
			logger.logERROR("Could not set image resources/images/viewtab/remove.gif to clean thumbnail cache button." );
			logger.logERROR(iox);
		}

		clearCacheJButton.setEnabled(conf.getBooleanProperty("thumbnails.cache.enabled") && (jptc.getCurrentSize() > 0));

		JPanel thumbnailCachePanel = new JPanel(new GridBagLayout());
		thumbnailCachePanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.thumbnail.cache.label")));

		GBHelper posThumbnailCachePanel = new GBHelper();

		thumbnailCachePanel.add(enableThumbnailCache, posThumbnailCachePanel.expandW());
		thumbnailCachePanel.add(new Gap(10), posThumbnailCachePanel.nextRow());

		thumbnailCachePanel.add(cacheSizeLabelHeading, posThumbnailCachePanel.nextRow());
		thumbnailCachePanel.add(new Gap(10), posThumbnailCachePanel.nextCol());
		thumbnailCachePanel.add(cacheSizeLabel, posThumbnailCachePanel.nextCol());
		thumbnailCachePanel.add(new Gap(10), posThumbnailCachePanel.nextRow());
		thumbnailCachePanel.add(cacheMaxSizeLabel, posThumbnailCachePanel.nextRow());
		thumbnailCachePanel.add(new Gap(10), posThumbnailCachePanel.nextCol());
		thumbnailCachePanel.add(maxCacheSize, posThumbnailCachePanel.nextCol());
		thumbnailCachePanel.add(new Gap(10), posThumbnailCachePanel.nextRow());
		thumbnailCachePanel.add(clearCachLabel, posThumbnailCachePanel.nextRow());
		thumbnailCachePanel.add(new Gap(10), posThumbnailCachePanel.nextCol());
		thumbnailCachePanel.add(clearCacheJButton, posThumbnailCachePanel.nextCol());

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

		String toolTipState = conf.getStringProperty("thumbnails.tooltip.state");

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

		thumbnailConfigurationPanel = new JPanel(new GridBagLayout());
		thumbnailConfigurationPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		GBHelper posThumbnailPanel = new GBHelper();

		thumbnailConfigurationPanel.add(thumbnailCreationPanel, posThumbnailPanel.expandW().expandH());
		thumbnailConfigurationPanel.add(thumbnailCachePanel, posThumbnailPanel.nextRow().expandW().expandH());
		thumbnailConfigurationPanel.add(thumbnailToolTipPanel, posThumbnailPanel.nextRow().expandW().expandH());
	}

	private void createTagConfigurationPanel() {

		/**
		 * Start of Preview Image Area
		 */
		useEmbeddedThumbnail = new JRadioButton(lang.get("configviewer.tag.previewimage.label.embeddedthumbnail"));
		useEmbeddedThumbnail.setSelected(conf.getBooleanProperty("tab.tagImage.previewImage.useEmbeddedThumbnail"));

		useScaledThumbnail = new JRadioButton(lang.get("configviewer.tag.previewimage.label.scaledthumbnail"));
		useScaledThumbnail.setSelected(!conf.getBooleanProperty("tab.tagImage.previewImage.useEmbeddedThumbnail"));

		ButtonGroup group = new ButtonGroup();
		group.add(useEmbeddedThumbnail);
		group.add(useScaledThumbnail);

		JPanel previewImagePanel = new JPanel(new GridBagLayout());
		previewImagePanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.tag.previewimage.label")));

		GBHelper posPreviewImagePanel = new GBHelper();

		previewImagePanel.add(useEmbeddedThumbnail, posPreviewImagePanel.expandW());
		previewImagePanel.add(useScaledThumbnail, posPreviewImagePanel.nextRow().expandW());

		/**
		 * Start of Categories Area
		 */
		warnWhenRemoveCategory = new JCheckBox(lang.get("configviewer.tag.categories.warnWhenRemove"));
		warnWhenRemoveCategory.setSelected(conf.getBooleanProperty("categories.warnWhenRemoveCategory"));
		warnWhenRemoveCategoryWithSubCategories = new JCheckBox(lang.get("configviewer.tag.categories.warnWhenRemoveCategoryWithSubCategories"));
		warnWhenRemoveCategoryWithSubCategories.setSelected(conf.getBooleanProperty("categories.warnWhenRemoveCategoryWithSubCategories"));

		JPanel categoriesPanel = new JPanel(new GridBagLayout());
		categoriesPanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.tag.categories.label")));

		GBHelper posCategoriesPanel = new GBHelper();

		categoriesPanel.add(warnWhenRemoveCategory, posCategoriesPanel.expandW());
		categoriesPanel.add(warnWhenRemoveCategoryWithSubCategories, posCategoriesPanel.nextRow().expandW());

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

		String addToImageRepositoryPolicy = conf.getStringProperty("imageRepository.addToRepositoryPolicy");

		if (addToImageRepositoryPolicy.equalsIgnoreCase(addAutomaticallyRadioButton.getName())) {
			addAutomaticallyRadioButton.setSelected(true);
		} else if (addToImageRepositoryPolicy.equalsIgnoreCase(askToAddRadioButton.getName())) {
			askToAddRadioButton.setSelected(true);
		} else {
			doNotAddRadioButton.setSelected(true);
		}

		imageRepositoriesList = new JList(ModelInstanceLibrary.getInstance().getImageRepositoryListModel());
		imageRepositoriesList.setCellRenderer(new CustomCellRenderer());

		JScrollPane imageRepositoriesScrollPane = new JScrollPane(imageRepositoriesList);
		imageRepositoriesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		JPanel imageRepositoriesAdditionModePanel = new JPanel(new GridBagLayout());
		imageRepositoriesAdditionModePanel.setBorder(BorderFactory.createTitledBorder(lang.get("configviewer.tag.imageRepositoriesAdditionMode.label")));

		automaticallyRemoveNonExistingImagePathsCheckBox = new JCheckBox(lang.get("configviewer.tag.imageRepositories.label.removeNonExistingPaths"));
		automaticallyRemoveNonExistingImagePathsCheckBox.setSelected(conf.getBooleanProperty("imageRepository.automaticallyRemoveNonExistingImagePaths"));
		automaticallyRemoveNonExistingImagePathsCheckBox.setToolTipText(lang.get("configviewer.tag.imageRepositories.label.removeNonExistingPaths.tooltip"));


		GBHelper posImageRepositories = new GBHelper();

		imageRepositoriesAdditionModePanel.add(addAutomaticallyRadioButton, posImageRepositories.expandW());
		imageRepositoriesAdditionModePanel.add(askToAddRadioButton, posImageRepositories.nextRow().expandW());
		imageRepositoriesAdditionModePanel.add(doNotAddRadioButton, posImageRepositories.nextRow().expandW());

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

		buttonPanel.add(automaticallyRemoveNonExistingImagePathsCheckBox, posButtonPanel);
		buttonPanel.add(removeSelectedImagePathsButton, posButtonPanel.nextCol());

		GBHelper posImageRepositoriesContent = new GBHelper();

		imageRepositoriesContentPanel.add(imageRepositoriesScrollPane, posImageRepositoriesContent.expandW().expandH());
		imageRepositoriesContentPanel.add(new Gap(2), posImageRepositoriesContent.nextRow());
		imageRepositoriesContentPanel.add(buttonPanel, posImageRepositoriesContent.nextRow().expandW());

		tagConfigurationPanel = new JPanel(new GridBagLayout());
		tagConfigurationPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		GBHelper posTagPanel = new GBHelper();

		tagConfigurationPanel.add(previewImagePanel, posTagPanel.expandW().expandH());
		tagConfigurationPanel.add(categoriesPanel, posTagPanel.nextRow().expandW().expandH());
		tagConfigurationPanel.add(imageRepositoriesAdditionModePanel, posTagPanel.nextRow().expandW().expandH());
		tagConfigurationPanel.add(imageRepositoriesContentPanel, posTagPanel.nextRow().expandW().expandH());
	}

	private void updateWindowLocationAndSize() {
		conf.setIntProperty("configViewerGUI.window.location.x", this.getLocation().x);
		conf.setIntProperty("configViewerGUI.window.location.y", this.getLocation().y);
		conf.setIntProperty("configViewerGUI.window.width", this.getSize().width);
		conf.setIntProperty("configViewerGUI.window.height", this.getSize().height);
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
		conf.setStringProperty("logger.log.level", logLevels.getSelectedItem().toString());
		conf.setBooleanProperty("logger.developerMode", developerMode.isSelected());
		conf.setBooleanProperty("logger.log.rotate", rotateLog.isSelected());
		conf.setBooleanProperty("logger.log.rotate.zip", zipLog.isSelected());
		conf.setStringProperty("logger.log.rotate.size", Long.toString(calculateRotateLogSize(Long.parseLong(rotateLogSize.getText()), rotateLogSizeFactor.getSelectedItem().toString())));
		conf.setStringProperty("logger.log.name", logName.getText().trim());
		conf.setStringProperty("logger.log.entry.timestamp.format", logEntryTimeStampFormats.getSelectedItem().toString());

		/**
		 * Update Updates Configuration
		 */
		conf.setBooleanProperty("updatechecker.enabled", updatesEnabled.isSelected());
		conf.setBooleanProperty("updatechecker.attachVersionInformation", sendVersionInformationEnabled.isSelected());

		/**
		 * Update Language Configuration
		 */
		conf.setBooleanProperty("automaticLanguageSelection", automaticRadioButton.isSelected());
		conf.setStringProperty("gUILanguageISO6391", ISO639.getInstance().getCode(currentLanguage.getText()));

		/**
		 * Update Rename Configuration
		 */
		conf.setBooleanProperty("rename.use.lastmodified.date", useLastModifiedDate.isSelected());
		conf.setBooleanProperty("rename.use.lastmodified.time", useLastModifiedTime.isSelected());
		conf.setStringProperty("rename.maximum.length.camera-model", maximumLengthOfCameraModelValueTextField.getText());

		/**
		 * Update Thumbnail Configuration
		 */
		conf.setBooleanProperty("thumbnails.view.create-if-missing-or-corrupt", createThumbnailIfMissingOrCorrupt.isSelected());
		conf.setStringProperty("thumbnails.view.width", thumbnailWidth.getText());
		conf.setStringProperty("thumbnails.view.height", thumbnailHeight.getText());
		conf.setStringProperty("thumbnails.view.create.algorithm", thumbnailCreationAlgorithm.getSelectedItem().toString());
		conf.setStringProperty("thumbnails.cache.max-size", maxCacheSize.getText());
		conf.setBooleanProperty("thumbnails.cache.enabled", enableThumbnailCache.isSelected());
		conf.setStringProperty("thumbnails.tooltip.state", getToolTipState());

		/**
		 * Update Tag Configuration
		 */
		conf.setBooleanProperty("tab.tagImage.previewImage.useEmbeddedThumbnail", useEmbeddedThumbnail.isSelected());
		conf.setBooleanProperty("categories.warnWhenRemoveCategory", warnWhenRemoveCategory.isSelected());
		conf.setBooleanProperty("categories.warnWhenRemoveCategoryWithSubCategories", warnWhenRemoveCategoryWithSubCategories.isSelected());
		conf.setBooleanProperty("imageRepository.automaticallyRemoveNonExistingImagePaths", automaticallyRemoveNonExistingImagePathsCheckBox.isSelected());
		conf.setStringProperty("imageRepository.addToRepositoryPolicy", getAddToRepositoryPolicy());

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

		if(!LOG_LEVEL.equals(logLevels.getSelectedItem().toString())) {
			displayMessage.append(lang.get("configviewer.logging.label.logLevel.text") + ": " + logLevels.getSelectedItem() + " (" + LOG_LEVEL + ")\n");
		}

		if(!LOG_NAME.equals(logName.getText())){
			displayMessage.append(lang.get("configviewer.logging.label.logName.text") + ": " + logName.getText() + " (" + LOG_NAME + ")\n");
		}

		if(!LOG_ENTRY_TIMESTAMP_FORMAT.equals(logEntryTimeStampFormats.getSelectedItem().toString())) {
			displayMessage.append(lang.get("configviewer.logging.label.logEntryTimeStampFormat.text") + ": " + logEntryTimeStampFormats.getSelectedItem() + " (" + LOG_ENTRY_TIMESTAMP_FORMAT + ")\n");
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

		if(!THUMBNAIL_WIDTH.equals(thumbnailWidth.getText())) {
			displayMessage.append(lang.get("configviewer.thumbnail.creation.label.thumbnail.width") + ": " + thumbnailWidth.getText() + " (" + THUMBNAIL_WIDTH + ")\n");
		}

		if(!THUMBNAIL_HEIGHT.equals(thumbnailHeight.getText())) {
			displayMessage.append(lang.get("configviewer.thumbnail.creation.label.thumbnail.height") + ": " + thumbnailHeight.getText() + " (" + THUMBNAIL_HEIGHT + ")\n");
		}

		if(!CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT_ALGORITHM.equals(thumbnailCreationAlgorithm.getSelectedItem().toString())) {
			displayMessage.append(lang.get("configviewer.thumbnail.creation.label.algorithm") + ": " + thumbnailCreationAlgorithm.getSelectedItem().toString() + " (" + CREATE_THUMBNAIL_IF_MISSING_OR_CORRUPT_ALGORITHM + ")\n");
		}

		if(!THUMBNAIL_MAX_CACHE_SIZE.equals(maxCacheSize.getText())) {
			displayMessage.append(lang.get("configviewer.thumbnail.cache.label.size.max") + ": " + maxCacheSize.getText() + " (" + THUMBNAIL_MAX_CACHE_SIZE + ")\n");
		}

		if(ENABLE_THUMBNAIL_CACHE != enableThumbnailCache.isSelected()) {
			displayMessage.append(lang.get("configviewer.thumbnail.cache.label.enable") + ": " + enableThumbnailCache.isSelected() + " (" + ENABLE_THUMBNAIL_CACHE + ")\n");
		}

		if(!MAXIMUM_LENGTH_OF_CAMERA_MODEL.equals(maximumLengthOfCameraModelValueTextField.getText())) {
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

		if(!ADD_TO_IMAGEREPOSITOY_POLICY.equals(getAddToRepositoryPolicy())) {
			int previousAddToImageRepositoryPolicy = -1;
			String previous = "";

			try {
				previousAddToImageRepositoryPolicy = Integer.parseInt(ADD_TO_IMAGEREPOSITOY_POLICY);
			} catch (NumberFormatException nfex) {
				previousAddToImageRepositoryPolicy = 1;
			}

			switch (previousAddToImageRepositoryPolicy) {
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

	private String calculateRotateLogSize(String size, String factor) {
		return Long.toString(calculateRotateLogSize(Long.parseLong(size), factor));
	}

	private String parseRotateLongSize(String size, String factor) {

		long longSize = Long.parseLong(size);

		if (factor.equals("KiB")) {
			longSize /= 1024;
		} else {
			longSize /= 1024 * 1024;
		}
		return Long.toString(longSize);

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

	private class RotateLogSizeJTextFieldListener implements DocumentListener {

		public void changedUpdate(DocumentEvent e) {
		}
		public void insertUpdate(DocumentEvent e) {
			validateLogRotateSize();
		}
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

		public void changedUpdate(DocumentEvent e) {
		}
		public void insertUpdate(DocumentEvent e) {
			validateThumbnailSize("width");
		}
		public void removeUpdate(DocumentEvent e) {
		}
	}

	private class  ThumbnailHeightJTextFieldListener implements DocumentListener {

		public void changedUpdate(DocumentEvent e) {
		}
		public void insertUpdate(DocumentEvent e) {
			validateThumbnailSize("height");
		}
		public void removeUpdate(DocumentEvent e) {
		}
	}

	private class  ThumbnailMaxCacheSizeJTextFieldListener implements DocumentListener {

		public void changedUpdate(DocumentEvent e) {
		}
		public void insertUpdate(DocumentEvent e) {
			validateThumbnailCacheMaxSize();
		}
		public void removeUpdate(DocumentEvent e) {
		}
	}

	private class  MaximumLengtOfCameraModelJTextFieldListener implements DocumentListener {

		public void changedUpdate(DocumentEvent e) {
		}
		public void insertUpdate(DocumentEvent e) {
			validateMaximumLengtOfCameraModel();
		}
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

	    public void insertUpdate(DocumentEvent e) {
	    	validateLogName(logName.getText());
	    }
	    public void removeUpdate(DocumentEvent e) {
	    }
	    public void changedUpdate(DocumentEvent e) {
	    }
	}

	private class ManualRadioButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			languageList.setEnabled(true);
		}
	}

	private class AutomaticRadioButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			languageList.setEnabled(false);

			String userLanguage = System.getProperty("user.language");

			if(!conf.getStringProperty("gUILanguageISO6391").equals(userLanguage)) {
				currentLanguage.setText(ConfigUtil.resolveCodeToLanguageName(userLanguage));
			}
		}
	}

	private class LanguageListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent lse) {
			if(languageList.getSelectedIndex() > -1) {
				currentLanguage.setText((String)languageList.getSelectedValue());
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
			SortedListModel imageRepositroyListModel = ModelInstanceLibrary.getInstance().getImageRepositoryListModel();

			Object[] selectedValues = imageRepositoriesList.getSelectedValues();

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
				for (Object selectedValue : selectedValues) {
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

	private class WindowEventHandler extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent e) {
			updateWindowLocationAndSize();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
	}
}