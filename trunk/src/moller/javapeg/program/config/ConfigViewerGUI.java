package moller.javapeg.program.config;
/**
 * This class was created : 2009-08-05 by Fredrik Möller
 * Latest changed         : 2009-08-06 by Fredrik Möller
 *                        : 2009-08-09 by Fredrik Möller
 *                        : 2009-08-10 by Fredrik Möller
 *                        : 2009-08-12 by Fredrik Möller
 *                        : 2009-08-13 by Fredrik Möller
 *                        : 2009-08-21 by Fredrik Möller
 *                        : 2009-08-23 by Fredrik Möller
 *                        : 2009-09-04 by Fredrik Möller
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
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
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
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
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.Gap;
import moller.javapeg.program.language.ISO639;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Level;
import moller.javapeg.program.logger.Logger;
import moller.util.gui.Screen;
import moller.util.io.PathUtil;
import moller.util.io.StreamUtil;

public class ConfigViewerGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JTree tree;

	private JPanel backgroundsPanel;
	
	private JPanel loggingConfigurationPanel;
	private JPanel updatesConfigurationPanel;
	private JPanel languageConfigurationPanel;
	private JPanel renameConfigurationPanel;
	
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
	
	private  JLabel currentLanguage;
			
	private JRadioButton manualRadioButton;
	private JRadioButton automaticRadioButton;
	
	private Config   conf;
	private Logger   logger;
	private Language lang;
			
	public ConfigViewerGUI() {
		conf   = Config.getInstance();
		logger = Logger.getInstance();
		lang   = Language.getInstance();
				
		this.initiateWindow();
		this.createLoggingConfigurationPanel();
		this.createUpdateConfigurationPanel();
		this.createLanguageConfigurationPanel();
		this.createRenameConfigurationPanel();
		this.addListeners();	
	}
	
	private void initiateWindow() {
								
		this.setSize(new Dimension(conf.getIntProperty("configViewerGUI.window.width"),conf.getIntProperty("configViewerGUI.window.height")));
				
		Point xyFromConfig = new Point(conf.getIntProperty("configViewerGUI.window.location.x"),conf.getIntProperty("configViewerGUI.window.location.y"));
				
		if(Screen.isOnScreen(xyFromConfig)) {
			this.setLocation(xyFromConfig);
		} else {
			this.setLocation(0,0);
//			TODO: Change errror message
			JOptionPane.showMessageDialog(null, lang.get("helpViewerGUI.window.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
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
			StreamUtil.closeStream(imageStream);
		}
		
//		TODO: Fix hard coded string
		this.setTitle("Configuration");
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
		logName.getDocument().addDocumentListener(new JavaPEGLogNameJTextFieldListener());
		logEntryTimeStampFormats.addItemListener(new LogEntryTimestampFormatsJComboBoxListener());
		manualRadioButton.addActionListener(new ManualRadioButtonListener());
		automaticRadioButton.addActionListener(new AutomaticRadioButtonListener());
		languageList.addListSelectionListener(new LanguageListListener());
		updatesEnabled.addChangeListener(new UpdatesEnabledCheckBoxListener());
		rotateLog.addChangeListener(new RotateLogCheckBoxListener());
		okButton.addActionListener(new OkButtonListener());
		applyButton.addActionListener(new ApplyButtonListener());
		cancelButton.addActionListener(new CancelButtonListener());
	}
	
	private JPanel createButtonPanel() {
		JPanel buttonPanel = new JPanel();

//		TODO: Remove hard coded strings
		okButton = new JButton("Ok");
		applyButton = new JButton("Apply");
		cancelButton = new JButton("Cancel");
		
		buttonPanel.add(okButton);
		buttonPanel.add(applyButton);
		buttonPanel.add(cancelButton);
		
		return buttonPanel;
	}
	
	private void createLoggingConfigurationPanel() {
		loggingConfigurationPanel = new JPanel(new GridBagLayout());
		loggingConfigurationPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		GBHelper posLoggingPanel = new GBHelper();
						
		String logLevel = conf.getStringProperty("logger.log.level");
		int seletedIndex = 0; 
		
		for (Level level : Level.values()) {
			if(level.toString().equals(logLevel)) {
				seletedIndex = level.ordinal();
				break;
			}	
		}
//		TODO: Remove hard coded string
		JLabel logLevelsLabel = new JLabel("Log Level");
		logLevels = new JComboBox(Level.values());				
		logLevels.setSelectedIndex(seletedIndex);
		
//		TODO: Remove hard coded string
		JLabel developerModeLabel = new JLabel("Unbuffered Logging");
		developerMode = new JCheckBox();
		developerMode.setSelected(conf.getBooleanProperty("logger.developerMode"));
				
//		TODO: Remove hard coded string
		JLabel rotateLogLabel = new JLabel("Rotate Log Automatically");
		rotateLog = new JCheckBox();
		rotateLog.setSelected(conf.getBooleanProperty("logger.log.rotate"));

//		TODO: Remove hard coded string
		JLabel zipLogLabel = new JLabel("Zip Rotated Log");
		zipLog = new JCheckBox();
		zipLog.setSelected(conf.getBooleanProperty("logger.log.rotate.zip"));
		
		
//		TODO: Remove hard coded string
		JLabel rotateLogSizeLabel = new JLabel("Rotate Log Size");
	
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
		
		
//		TODO: Remove hard coded string
		JLabel logNameLabel = new JLabel("Log Name");
		logName = new JTextField();
		logName.setText(conf.getStringProperty("logger.log.name"));
		
//		TODO: Remove hard coded string
		JLabel logEntryTimeStampFormatLabel = new JLabel("Log Entry Timestamp Format");
	
		Set<String> formats = new LinkedHashSet<String>();
		
		formats.add(conf.getStringProperty("logger.log.entry.timestamp.format"));
		formats.add("yyyy-MM-dd'T'HH:mm:ss:SSSZ");
		formats.add("yyyyMMdd'T'HHmmssSSSZ");
		formats.add("yyyy-D'T'HH:mm:ss:SSSZ");
		formats.add("yyyyD'T'HHmmssSSSZ");
		formats.add("MM/dd/yyyy:HH:mm:ss:SSS");
		formats.add("dd/MM/yyyy:HH:mm:ss:SSS");
				
		logEntryTimeStampFormats = new JComboBox(formats.toArray());
			
//		TODO: Remove hard coded string
		JLabel logEntryTimeStampPreviewLabel = new JLabel("Log Entry Timestamp Preview");
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
		updatesConfigurationPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		GBHelper posUpdatesPanel = new GBHelper();
		
//		TODO: Remove hard coded string
		JLabel updatesEnabledLabel = new JLabel("Enable Application Update Check");
		updatesEnabled = new JCheckBox();
		updatesEnabled.setSelected(conf.getBooleanProperty("updatechecker.enabled"));
		
//		TODO: Remove hard coded string
		JLabel attachVersionInformationLabel = new JLabel("Add Version Information To Update Check");
		sendVersionInformationEnabled = new JCheckBox();
		sendVersionInformationEnabled.setSelected(conf.getBooleanProperty("updatechecker.attachVersionInformation"));
		sendVersionInformationEnabled.setEnabled(updatesEnabled.isSelected());
		
		updatesConfigurationPanel.add(updatesEnabledLabel, posUpdatesPanel);
		updatesConfigurationPanel.add(new Gap(10), posUpdatesPanel.nextCol());
		updatesConfigurationPanel.add(updatesEnabled, posUpdatesPanel.nextCol());
		updatesConfigurationPanel.add(attachVersionInformationLabel, posUpdatesPanel.nextRow());
		updatesConfigurationPanel.add(new Gap(10), posUpdatesPanel.nextCol());
		updatesConfigurationPanel.add(sendVersionInformationEnabled, posUpdatesPanel.nextCol());
	}
	
	private void createRenameConfigurationPanel() {
		renameConfigurationPanel = new JPanel(new GridBagLayout());
		renameConfigurationPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		GBHelper posRenamePanel = new GBHelper();
		
//		TODO: Remove hard coded string
		JLabel useLastModifiedDateLabel = new JLabel("Use Last Modified Date if Exif date is missing");
		useLastModifiedDate = new JCheckBox();
		useLastModifiedDate.setSelected(conf.getBooleanProperty("rename.use.lastmodified.date"));

//		TODO: Remove hard coded string
		JLabel useLastModifiedTimeLabel = new JLabel("Use Last Modified Time if Exif time is missing");
		useLastModifiedTime = new JCheckBox();
		useLastModifiedTime.setSelected(conf.getBooleanProperty("rename.use.lastmodified.time"));
		
		renameConfigurationPanel.add(useLastModifiedDateLabel, posRenamePanel);
		renameConfigurationPanel.add(new Gap(10), posRenamePanel.nextCol());
		renameConfigurationPanel.add(useLastModifiedDate, posRenamePanel.nextCol());
		renameConfigurationPanel.add(useLastModifiedTimeLabel, posRenamePanel.nextRow());
		renameConfigurationPanel.add(new Gap(10), posRenamePanel.nextCol());
		renameConfigurationPanel.add(useLastModifiedTime, posRenamePanel.nextCol());
	}
	
	private void createLanguageConfigurationPanel() {
		
		languageConfigurationPanel = new JPanel(new GridBagLayout());
		languageConfigurationPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
						
		JPanel leftPanel = new JPanel(new GridBagLayout());
		JPanel rightPanel = new JPanel(new GridBagLayout());
		
//		TODO: Move string to new language file
		JLabel selectionModeJLabel = new JLabel(lang.get("language.option.gui.selectionModeJLabel"));
		selectionModeJLabel.setForeground(Color.GRAY);
		
//		TODO: Move string to new language file
		manualRadioButton = new JRadioButton(lang.get("language.option.gui.manualRadioButton"));
		automaticRadioButton = new JRadioButton(lang.get("language.option.gui.automaticRadioButton"));
				
		ButtonGroup languageSelectionMode = new ButtonGroup();
		languageSelectionMode.add(manualRadioButton);
		languageSelectionMode.add(automaticRadioButton);
		
//		TODO: Move string to new language file
		JLabel currentLanguageLabel = new JLabel(lang.get("language.option.gui.currentLanguageLabel"));
		currentLanguageLabel.setForeground(Color.GRAY);
				
		if(conf.getBooleanProperty("automaticLanguageSelection")) {
			if(!conf.getStringProperty("gUILanguageISO6391").equals(System.getProperty("user.language"))) {
				conf.setStringProperty("gUILanguageISO6391", System.getProperty("user.language"));
			}	
		}
				
		currentLanguage = new JLabel(ConfigUtil.resolveCodeToLanguageName(conf.getStringProperty("gUILanguageISO6391")));
		
		GBHelper posLeft = new GBHelper();
		
		leftPanel.add(selectionModeJLabel, posLeft);
		leftPanel.add(new Gap(2), posLeft.nextRow());
		leftPanel.add(manualRadioButton, posLeft.nextRow());
		leftPanel.add(automaticRadioButton, posLeft.nextRow());
		leftPanel.add(new Gap(15), posLeft.nextRow());
		leftPanel.add(currentLanguageLabel, posLeft.nextRow());
		leftPanel.add(new Gap(5), posLeft.nextRow());
		leftPanel.add(currentLanguage, posLeft.nextRow());
		
//		TODO: Move string to new language file
		JLabel availableLanguages = new JLabel(lang.get("language.option.gui.availableLanguages"));
		availableLanguages.setForeground(Color.GRAY);
		
		languageList = new JList(ConfigUtil.listLanguagesFiles());
		
		if(conf.getBooleanProperty("automaticLanguageSelection")) {
			languageList.setEnabled(false);
		}
		
		JScrollPane sp = new JScrollPane(languageList);

		GBHelper posRight = new GBHelper();

		rightPanel.add(availableLanguages, posRight);
		rightPanel.add(new Gap(2), posRight.nextRow());
		rightPanel.add(sp, posRight.nextRow());
			
		GBHelper posLanguagePanel = new GBHelper();
		
		languageConfigurationPanel.add(new Gap(5), posLanguagePanel);
		languageConfigurationPanel.add(new Gap(5), posLanguagePanel.nextRow());
		languageConfigurationPanel.add(leftPanel, posLanguagePanel.nextCol().align(GridBagConstraints.NORTH));
		languageConfigurationPanel.add(new Gap(5), posLanguagePanel.nextCol());
		languageConfigurationPanel.add(rightPanel, posLanguagePanel.nextCol());
		languageConfigurationPanel.add(new Gap(5), posLanguagePanel.nextCol());
		languageConfigurationPanel.add(new Gap(5), posLanguagePanel.nextRow());
		
		if(conf.getBooleanProperty("automaticLanguageSelection")) {
			automaticRadioButton.setSelected(true);
		} else {
			manualRadioButton.setSelected(true);
		}
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
		if(!ConfigUtil.resolveCodeToLanguageName(conf.getStringProperty("gUILanguageISO6391")).equals(currentLanguage.getText())) {
//			TODO: Move string to new language file
			JOptionPane.showMessageDialog(this, lang.get("language.option.gui.information.restartMessage"), lang.get("language.option.gui.information.windowlabel"), JOptionPane.INFORMATION_MESSAGE);
		}
		conf.setBooleanProperty("automaticLanguageSelection", automaticRadioButton.isSelected());
		conf.setStringProperty("gUILanguageISO6391", ISO639.getInstance().getCode(currentLanguage.getText()));
		
		/**
		 * Update Rename Configuration
		 */
		conf.setBooleanProperty("rename.use.lastmodified.date", useLastModifiedDate.isSelected());
		conf.setBooleanProperty("rename.use.lastmodified.time", useLastModifiedTime.isSelected());
		
		return true;
	}
		
	private boolean validateLogName(String logName) {
    	boolean isValid = true;
    	
		int result = PathUtil.validateString(logName, false);
    	
    	if (result > -1) {
    		isValid = false;
//    		TODO: remove hard coded string
    		JOptionPane.showMessageDialog(this, "The file name can not contain the character: " + (char)result, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
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
//					TODO: Remove hard coded string
					JOptionPane.showMessageDialog(this, "The rotate log size is to large: Maximum allowed is 100000 KiB", lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);	
				} else {
//					TODO: Remove hard coded string
					JOptionPane.showMessageDialog(this, "The rotate log size is to large: Maximum allowed is 100 MiB", lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
				}
			}
			
			if(size < 10 * 1024) {
//				TODO: Remove hard coded string
				JOptionPane.showMessageDialog(this, "The rotate log size is to small: Minimum allowed is 10 KiB", lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);					
			}
		} catch (NumberFormatException nfex) {
			isValid = false;
//			TODO: Remove hard coded string
			JOptionPane.showMessageDialog(this, "The rotate log size must be an integer", lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
		}
		return isValid;
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
		
	private void closeWindow() {
		updateWindowLocationAndSize();
		this.setVisible(false);
		this.dispose();
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
				
	/**
	 * Mouse listener
	 */
	private class Mouselistener extends MouseAdapter{
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
			
	private class JavaPEGLogNameJTextFieldListener implements DocumentListener {

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
		public void windowClosing(WindowEvent e) {
			updateWindowLocationAndSize();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
	}
}