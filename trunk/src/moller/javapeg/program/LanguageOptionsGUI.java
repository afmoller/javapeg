package moller.javapeg.program;
/**
 * This class was created : 2009-01-12 by Fredrik Möller
 * Latest changed         : 2009-02-21 by Fredrik Möller
 *                        : 2009-03-01 by Fredrik Möller
 *                        : 2009-03-03 by Fredrik Möller
 *                        : 2009-03-05 by Fredrik Möller
 *                        : 2009-03-23 by Fredrik Möller
 *                        : 2009-04-14 by Fredrik Möller
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import moller.javapeg.program.language.ISO639;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;

public class LanguageOptionsGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6460865254657599037L;

	private JPanel mainPanel;
	
	private JLabel selectionModeJLabel;
	private JLabel currentLanguageLabel;
	private JLabel currentLanguage;
	private JLabel availableLanguages;
	
	private JRadioButton manualRadioButton;
	private JRadioButton automaticRadioButton;
	
	private ButtonGroup languageSelectionMode;
	
	private JList languageList;

	private String currentLanguageString;
	
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
		
		Dimension	dScreen		= Toolkit.getDefaultToolkit().getScreenSize();
		Dimension	dContent 	= new Dimension(280,200);

		this.setLocation((dScreen.width-dContent.width)/2,(dScreen.height-dContent.height)/2);
		this.setSize(dContent);
		this.setResizable(false);
		
		mainPanel = new JPanel(null);
		mainPanel.setBounds(3,3,269,163);
		mainPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)); 
	
		selectionModeJLabel = new JLabel(lang.get("language.option.gui.selectionModeJLabel"));
		selectionModeJLabel.setForeground(Color.GRAY);
		selectionModeJLabel.setBounds(7,8,120,14);
		
		manualRadioButton = new JRadioButton(lang.get("language.option.gui.manualRadioButton"));
		manualRadioButton.setActionCommand("manualRadioButton");
		manualRadioButton.setBounds(7,25,120,16);
				
		automaticRadioButton = new JRadioButton(lang.get("language.option.gui.automaticRadioButton"));
		automaticRadioButton.setActionCommand("automaticRadioButton");
		automaticRadioButton.setBounds(7,43,120,16);
		
		languageSelectionMode = new ButtonGroup();
		languageSelectionMode.add(manualRadioButton);
		languageSelectionMode.add(automaticRadioButton);
		
		currentLanguageLabel = new JLabel(lang.get("language.option.gui.currentLanguageLabel"));
		currentLanguageLabel.setForeground(Color.GRAY);
		currentLanguageLabel.setBounds(7,65,120,14);
				
		currentLanguageString = resolveCodeToLanguageName(conf.getStringProperty("gUILanguageISO6391"));
		
		if(conf.getBooleanProperty("automaticLanguageSelection")) {
			if(!conf.getStringProperty("gUILanguageISO6391").equals(System.getProperty("user.language"))) {
				conf.setStringProperty("gUILanguageISO6391", System.getProperty("user.language"));
			}	
		}
				
		currentLanguage = new JLabel(resolveCodeToLanguageName(conf.getStringProperty("gUILanguageISO6391")));
		currentLanguage.setBounds(7,85,120,14);
				
		availableLanguages = new JLabel(lang.get("language.option.gui.availableLanguages"));
		availableLanguages.setForeground(Color.GRAY);
		availableLanguages.setBounds(130,8,130,14);
		
		languageList = new JList(listLanguagesFiles());
		
		if(conf.getBooleanProperty("automaticLanguageSelection")) {
			languageList.setEnabled(false);
		}
		
		JScrollPane sp = new JScrollPane(languageList);
		sp.setBounds(130,25,120,130);

		mainPanel.add(selectionModeJLabel);
		mainPanel.add(manualRadioButton);
		mainPanel.add(automaticRadioButton);
		mainPanel.add(currentLanguageLabel);
		mainPanel.add(currentLanguage);
		mainPanel.add(availableLanguages);
		mainPanel.add(sp);
				
		this.getContentPane().setLayout(null);
		this.getContentPane().add(mainPanel);
		
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
	}
	
	private void addListeners(){
		manualRadioButton.addActionListener(new RadioButtonListener());
		automaticRadioButton.addActionListener(new RadioButtonListener());
		languageList.addListSelectionListener(new ListListener());
		addWindowListener(new WindowDestroyer());
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
				conf.setBooleanProperty("automaticLanguageSelection", false);
			}
			
			else if(actionCommand.equals("automaticRadioButton")) {
				languageList.setEnabled(false);
				
				String userLanguage = System.getProperty("user.language");
				
				if(!conf.getStringProperty("gUILanguageISO6391").equals(userLanguage)) {
					conf.setStringProperty("gUILanguageISO6391", userLanguage);
					currentLanguage.setText(resolveCodeToLanguageName(userLanguage));
				}
				conf.setBooleanProperty("automaticLanguageSelection", true);
			}
		}
	}
	
	private class ListListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent lse) {
			
			if(languageList.getSelectedIndex() > -1) {
				currentLanguage.setText((String)languageList.getSelectedValue());
				conf.setStringProperty("gUILanguageISO6391", ISO639.getInstance().getCode((String)languageList.getSelectedValue()));
			}
		}
	}
	
	private class WindowDestroyer extends WindowAdapter	{
		
		Language lang = Language.getInstance();
		
		public void windowClosing(WindowEvent e) {
			if(!resolveCodeToLanguageName(conf.getStringProperty("gUILanguageISO6391")).equals(currentLanguageString)) {
				JOptionPane.showMessageDialog(null, lang.get("language.option.gui.information.restartMessage"), lang.get("language.option.gui.information.windowlabel"), JOptionPane.INFORMATION_MESSAGE);
			}
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
	}
}