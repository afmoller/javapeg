package moller.javapeg.program;
/**
 * This class was created : 2009-01-12 by Fredrik Möller
 * Latest changed         : 2009-02-21 by Fredrik Möller
 *                        : 2009-03-01 by Fredrik Möller
 *                        : 2009-03-03 by Fredrik Möller
 *                        : 2009-03-05 by Fredrik Möller
 *                        : 2009-03-23 by Fredrik Möller
 *                        : 2009-04-14 by Fredrik Möller
 *                        : 2009-05-02 by Fredrik Möller
 *                        : 2009-05-03 by Fredrik Möller
 *                        : 2009-05-04 by Fredrik Möller
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import moller.javapeg.program.language.ISO639;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.gui.Screen;

public class LanguageOptionsGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6460865254657599037L;

	private JPanel mainPanel;
	private JPanel buttonPanel;
	
	private JLabel selectionModeJLabel;
	private JLabel currentLanguageLabel;
	private JLabel currentLanguage;
	private JLabel availableLanguages;
	
	private JRadioButton manualRadioButton;
	private JRadioButton automaticRadioButton;
	
	private ButtonGroup languageSelectionMode;
	
	private JButton okButton;
	private JButton cancelButton;
	
	private JList languageList;
	
	private Config conf;
	
	private Logger logger;
	
	private Language lang; 
	
	/**
	 * The system dependent file separator char
	 */
	private final static String FS = File.separator;
	
	
	public LanguageOptionsGUI() {
		
		conf = Config.getInstance();
		
		logger = Logger.getInstance();
		
		lang = Language.getInstance();
		
		this.setTitle(lang.get("language.option.gui.windowTitle"));
				
		Point xyFromConfig = new Point(conf.getIntProperty("languageOption.window.location.x"),conf.getIntProperty("languageOption.window.location.y"));
		
		if(Screen.isOnScreen(xyFromConfig)) {
			this.setLocation(xyFromConfig);
		} else {
			this.setLocation(0,0);
			JOptionPane.showMessageDialog(null, lang.get("language.option.gui.window.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			logger.logERROR("Could not set location of Language Option GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");
		}
		
		this.setResizable(false);
		
		mainPanel = new JPanel(new GridBagLayout());
		mainPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		JPanel leftPanel = new JPanel(new GridBagLayout());
		JPanel rightPanel = new JPanel(new GridBagLayout());
		
		selectionModeJLabel = new JLabel(lang.get("language.option.gui.selectionModeJLabel"));
		selectionModeJLabel.setForeground(Color.GRAY);
		
		manualRadioButton = new JRadioButton(lang.get("language.option.gui.manualRadioButton"));
		manualRadioButton.setActionCommand("manualRadioButton");
				
		automaticRadioButton = new JRadioButton(lang.get("language.option.gui.automaticRadioButton"));
		automaticRadioButton.setActionCommand("automaticRadioButton");
		
		languageSelectionMode = new ButtonGroup();
		languageSelectionMode.add(manualRadioButton);
		languageSelectionMode.add(automaticRadioButton);
		
		currentLanguageLabel = new JLabel(lang.get("language.option.gui.currentLanguageLabel"));
		currentLanguageLabel.setForeground(Color.GRAY);
				
		if(conf.getBooleanProperty("automaticLanguageSelection")) {
			if(!conf.getStringProperty("gUILanguageISO6391").equals(System.getProperty("user.language"))) {
				conf.setStringProperty("gUILanguageISO6391", System.getProperty("user.language"));
			}	
		}
				
		currentLanguage = new JLabel(resolveCodeToLanguageName(conf.getStringProperty("gUILanguageISO6391")));
		
		GBHelper posLeft = new GBHelper();
		
		leftPanel.add(selectionModeJLabel, posLeft);
		leftPanel.add(new Gap(2), posLeft.nextRow());
		leftPanel.add(manualRadioButton, posLeft.nextRow());
		leftPanel.add(automaticRadioButton, posLeft.nextRow());
		leftPanel.add(new Gap(15), posLeft.nextRow());
		leftPanel.add(currentLanguageLabel, posLeft.nextRow());
		leftPanel.add(new Gap(5), posLeft.nextRow());
		leftPanel.add(currentLanguage, posLeft.nextRow());
		
		availableLanguages = new JLabel(lang.get("language.option.gui.availableLanguages"));
		availableLanguages.setForeground(Color.GRAY);
		
		languageList = new JList(listLanguagesFiles());
		
		if(conf.getBooleanProperty("automaticLanguageSelection")) {
			languageList.setEnabled(false);
		}
		
		JScrollPane sp = new JScrollPane(languageList);

		GBHelper posRight = new GBHelper();

		rightPanel.add(availableLanguages, posRight);
		rightPanel.add(new Gap(2), posRight.nextRow());
		rightPanel.add(sp, posRight.nextRow());
			
		GBHelper pos = new GBHelper();
		
		mainPanel.add(new Gap(5), pos);
		mainPanel.add(new Gap(5), pos.nextRow());
		mainPanel.add(leftPanel, pos.nextCol().align(GridBagConstraints.NORTH));
		mainPanel.add(new Gap(5), pos.nextCol());
		mainPanel.add(rightPanel, pos.nextCol());
		mainPanel.add(new Gap(5), pos.nextCol());
		mainPanel.add(new Gap(5), pos.nextRow());
			
		buttonPanel = new JPanel();
		
		okButton = new JButton(lang.get("language.option.gui.okButton"));
		cancelButton = new JButton(lang.get("language.option.gui.cancelButton"));
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
				
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){
			logger.logERROR("Could not set desired Look And Feel for Language Options GUI");
			logger.logFATAL("Below is the generated StackTrace");
			
			for(StackTraceElement element : e.getStackTrace()) {
				logger.logERROR(element.toString());
			}
		}
		
		addListeners();
		
		if(conf.getBooleanProperty("automaticLanguageSelection")) {
			automaticRadioButton.setSelected(true);
		} else {
			manualRadioButton.setSelected(true);
		}
		this.pack();
	}
	
	private void addListeners(){
		manualRadioButton.addActionListener(new RadioButtonListener());
		automaticRadioButton.addActionListener(new RadioButtonListener());
		languageList.addListSelectionListener(new ListListener());
		addWindowListener(new WindowDestroyer());
		okButton.addActionListener(new OkButtonListener());
		cancelButton.addActionListener(new CancelButtonListener());
	}
	
	private String [] listLanguagesFiles() {
		Set<String> languageFiles = ApplicationContext.getInstance().getJarFileEmbeddedLanguageFiles();
				
		File languageFolder = new File(System.getProperty("user.dir") + FS + "resources" + FS + "lang");
		File [] files = languageFolder.listFiles();
				
		for (File file : files) {
			if (file.isFile()) {
				languageFiles.add(file.getName());
			}
		}
				
		List <String> languageNames = new ArrayList<String>(languageFiles.size());
		
		ISO639 iso639 = ISO639.getInstance();
		String code = "";
		String languageName = "";
		
		for (String fileName : languageFiles) {
			code = fileName.substring(fileName.lastIndexOf(".") + 1);
			languageName = iso639.getLanguage(code);
			if (languageName != null) {
				languageNames.add(iso639.getLanguage(code));	
			}
		}
		Collections.sort(languageNames);
				
		return (String [])languageNames.toArray(new String[0]);
	}
	
	private String resolveCodeToLanguageName(String code) {
				
		String language = ISO639.getInstance().getLanguage(code);
		
		if (language == null) {
			return lang.get("language.option.gui.languageNameNotFound");
		}
		return language;
	}
	
	private class RadioButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String actionCommand = e.getActionCommand();
						
			if(actionCommand.equals("manualRadioButton")) {
				languageList.setEnabled(true);
			} else if(actionCommand.equals("automaticRadioButton")) {
				languageList.setEnabled(false);
				
				String userLanguage = System.getProperty("user.language");
				
				if(!conf.getStringProperty("gUILanguageISO6391").equals(userLanguage)) {
					currentLanguage.setText(resolveCodeToLanguageName(userLanguage));
				}
			}
		}
	}
	
	private class ListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent lse) {
			
			if(languageList.getSelectedIndex() > -1) {
				currentLanguage.setText((String)languageList.getSelectedValue());
			}
		}
	}
	
	private class OkButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			updateWindowLocationAndSize();
			closeWindow();
		}	
	}
	
	private class CancelButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			updateWindowLocationAndSize();
			dispose();
		}	
	}
	
	private class WindowDestroyer extends WindowAdapter	{
		public void windowClosing(WindowEvent e) {
			updateWindowLocationAndSize();
			dispose();
		}
	}
			
	private void closeWindow () {
		if(!resolveCodeToLanguageName(conf.getStringProperty("gUILanguageISO6391")).equals(currentLanguage.getText())) {
			JOptionPane.showMessageDialog(null, lang.get("language.option.gui.information.restartMessage"), lang.get("language.option.gui.information.windowlabel"), JOptionPane.INFORMATION_MESSAGE);
		}
		updateConfig();
		dispose();
	}
	
	private void updateConfig() {
		if (automaticRadioButton.isSelected()) {
			conf.setBooleanProperty("automaticLanguageSelection", true);
		} else {
			conf.setBooleanProperty("automaticLanguageSelection", false);
		}		
		conf.setStringProperty("gUILanguageISO6391", ISO639.getInstance().getCode(currentLanguage.getText()));
	}
	
	private void updateWindowLocationAndSize() {
		conf.setIntProperty("languageOption.window.location.x", this.getLocation().x);
		conf.setIntProperty("languageOption.window.location.y", this.getLocation().y);
	}
}