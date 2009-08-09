package moller.javapeg.program.config;
/**
 * This class was created : 2009-08-05 by Fredrik Möller
 * Latest changed         : 2009-08-06 by Fredrik Möller
 *                        : 2009-08-09 by Fredrik Möller
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;

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
import moller.util.io.StreamUtil;

public class ConfigViewerGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JTree tree;

	private JPanel backgroundsPanel;
	
	private JPanel loggingConfigurationPanel;
	private JPanel updatesConfigurationPanel;
	private JPanel languageConfigurationPanel;
	
	private JSplitPane splitPane;
	
	private JButton okButton;
	private JButton applyButton;
	private JButton cancelButton;
	
	
	
	/**
	 * Variables for the logging panel
	 */
	private JCheckBox developerMode;
	private JComboBox logLevels;
	private JCheckBox rotateLog;
	
	private JTextField rotateLogSize;
	private JTextField logName;
	private JTextField logEntryTimeStampFormat;
	
	/**
	 * Variables for the updates panel
	 */
	private JCheckBox updatesEnabled;
	
	/**
	 * Variables for the language panel
	 */
	private JList languageList;
	
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
		manualRadioButton.addActionListener(new ManualRadioButtonListener());
		automaticRadioButton.addActionListener(new AutomaticRadioButtonListener());
		languageList.addListSelectionListener(new LanguageListListener());
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
		JLabel logLevelsLabel = new JLabel("Log Levels");
		logLevels = new JComboBox(Level.values());				
		logLevels.setSelectedIndex(seletedIndex);
		
//		TODO: Remove hard coded string
		JLabel developerModeLabel = new JLabel("Unbuffered Logging");
		developerMode = new JCheckBox();
		developerMode.setSelected(conf.getBooleanProperty("logger.developerMode"));
				
//		TODO: Remove hard coded string
		JLabel rotateLogLabel = new JLabel("Rotate Log");
		rotateLog = new JCheckBox();
		rotateLog.setSelected(conf.getBooleanProperty("logger.log.rotate"));
		
//		TODO: Remove hard coded string
		JLabel rotateLogSizeLabel = new JLabel("Rotate Log Size");
		rotateLogSize = new JTextField();
		rotateLogSize.setText(conf.getStringProperty("logger.log.rotate.size"));

//		TODO: Remove hard coded string
		JLabel logNameLabel = new JLabel("Log Name");
		logName = new JTextField();
		logName.setText(conf.getStringProperty("logger.log.name"));
		
//		TODO: Remove hard coded string
		JLabel logEntryTimeStampFormatLabel = new JLabel("Log Entry Timestamp Format");
		logEntryTimeStampFormat = new JTextField();
		logEntryTimeStampFormat.setText(conf.getStringProperty("logger.log.entry.timestamp.format"));
				
		loggingConfigurationPanel.add(developerModeLabel, posLoggingPanel);
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(developerMode, posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(rotateLogLabel, posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(rotateLog, posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(rotateLogSizeLabel, posLoggingPanel.nextRow());
		loggingConfigurationPanel.add(new Gap(10), posLoggingPanel.nextCol());
		loggingConfigurationPanel.add(rotateLogSize, posLoggingPanel.nextCol());
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
		loggingConfigurationPanel.add(logEntryTimeStampFormat, posLoggingPanel.nextCol());	
	}
	
	private void createUpdateConfigurationPanel() {
		updatesConfigurationPanel = new JPanel(new GridBagLayout());
		
		GBHelper posUpdatesPanel = new GBHelper();
		
		JLabel updatesEnabledLabel = new JLabel("Enable Application Update Check");
		updatesEnabled = new JCheckBox();
		updatesEnabled.setSelected(conf.getBooleanProperty("updatechecker.enabled"));
		
		
		updatesConfigurationPanel.add(updatesEnabledLabel, posUpdatesPanel);
		updatesConfigurationPanel.add(new Gap(10), posUpdatesPanel.nextCol());
		updatesConfigurationPanel.add(updatesEnabled, posUpdatesPanel.nextCol());	
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
	
	private void updateConfiguration() {
		/**
		 * Update Logging Configuration
		 */
		conf.setStringProperty("logger.log.level", logLevels.getSelectedItem().toString());
		conf.setBooleanProperty("logger.developerMode", developerMode.isSelected());
		conf.setBooleanProperty("logger.log.rotate", rotateLog.isSelected());
		conf.setStringProperty("logger.log.rotate.size", rotateLogSize.getText());
		conf.setStringProperty("logger.log.name", logName.getText());
		conf.setStringProperty("logger.log.entry.timestamp.format", logEntryTimeStampFormat.getText());
		
		/**
		 * Update Updates Configuration
		 */
		conf.setBooleanProperty("updatechecker.enabled", updatesEnabled.isSelected());
		
		/**
		 * Update Language Configuration
		 */
		if(!ConfigUtil.resolveCodeToLanguageName(conf.getStringProperty("gUILanguageISO6391")).equals(currentLanguage.getText())) {
//			TODO: Move string to new language file
			JOptionPane.showMessageDialog(this, lang.get("language.option.gui.information.restartMessage"), lang.get("language.option.gui.information.windowlabel"), JOptionPane.INFORMATION_MESSAGE);
		}
		conf.setBooleanProperty("automaticLanguageSelection", automaticRadioButton.isSelected());
		conf.setStringProperty("gUILanguageISO6391", ISO639.getInstance().getCode(currentLanguage.getText()));
	}
	
	private void closeWindow() {
		updateWindowLocationAndSize();
		this.setVisible(false);
		this.dispose();
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
					
				} else if (selRow == 4) {
					backgroundsPanel.add(languageConfigurationPanel);
				}
			}
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
	
	private class OkButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			updateConfiguration();
			closeWindow();
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