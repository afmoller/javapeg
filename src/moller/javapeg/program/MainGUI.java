package moller.javapeg.program;
/**
 * This class was created : 2009-02-25 by Fredrik Möller
 * Latest changed         : 2009-02-26 by Fredrik Möller
 *                        : 2009-03-01 by Fredrik Möller
 *                        : 2009-03-02 by Fredrik Möller
 *                        : 2009-03-03 by Fredrik Möller
 *                        : 2009-03-04 by Fredrik Möller
 *                        : 2009-03-05 by Fredrik Möller
 *                        : 2009-03-09 by Fredrik Möller
 *                        : 2009-03-10 by Fredrik Möller
 *                        : 2009-03-11 by Fredrik Möller
 *                        : 2009-03-18 by Fredrik Möller
 *                        : 2009-03-25 by Fredrik Möller
 *                        : 2009-03-26 by Fredrik Möller
 *                        : 2009-04-04 by Fredrik Möller
 *                        : 2009-04-05 by Fredrik Möller
 *                        : 2009-04-06 by Fredrik Möller
 *                        : 2009-04-12 by Fredrik Möller
 *                        : 2009-04-13 by Fredrik Möller
 *                        : 2009-04-15 by Fredrik Möller
 *                        : 2009-04-23 by Fredrik Möller
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.filechooser.FileChooser;
import moller.javapeg.program.helpviewer.HelpViewerGUI;
import moller.javapeg.program.jpeg.JPEGThumbNail;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaDataUtil;
import moller.javapeg.program.progress.RenameProcess;
import moller.javapeg.program.rename.RenameProcessContext;
import moller.javapeg.program.rename.ValidatorStatus;
import moller.javapeg.program.rename.process.FileProcessor;
import moller.javapeg.program.rename.process.PostFileProcessor;
import moller.javapeg.program.rename.process.PreFileProcessor;
import moller.javapeg.program.rename.validator.FileAndSubDirectoryTemplate;
import moller.javapeg.program.rename.validator.JPEGTotalPathLength;
import moller.javapeg.program.thumbnailoverview.ThumbNailOverViewCreator;
import moller.util.gui.Screen;
import moller.util.gui.Table;
import moller.util.gui.Update;
import moller.util.io.StreamUtil;
import moller.util.mnemonic.MnemonicConverter;

public class MainGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4478711914847747931L;
	private JButton pathSelectButton;
	private JButton destinationPathButton;
	private JButton startProcessButton;

	private JLabel pathLabel;
	private JLabel destinationPathLabel;
	private JLabel subFolderLabel;
	private JLabel fileNameTemplateLabel;
	private JLabel variablesLabel;
	private JLabel dateLabelA;
	private JLabel dateLabelB;
	private JLabel timeLabelA;
	private JLabel timeLabelB;
	private	JLabel modelLabelA;
	private	JLabel modelLabelB;
	private	JLabel shutterSpeedLabelA;
	private	JLabel shutterSpeedLabelB;
	private	JLabel isoLabelA;
	private	JLabel isoLabelB;
	private	JLabel widthLabelA;
	private	JLabel widthLabelB;
	private	JLabel heigthLabelA;
	private	JLabel heigthLabelB;
	private	JLabel apertureLabelA;
	private	JLabel apertureLabelB;
	private	JLabel todaysDateLabelA;
	private	JLabel todaysDateLabelB;
	private JLabel picturesLabel;
	private JLabel infoPanelLabel;

	private JTextField pathTextField;
	private JTextField destinationPathTextField;
	private JTextField subFolderTextField;
	private JTextField fileNameTemplateTextField;
	private JTextField subFolderPreviewTextFieldLabel;
	private JTextField subFolderNamePreviewTextField;

	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenu languageMenu;

	private JMenuItem languageOptionsJMenuItem;
	private JMenuItem shutDownProgramJMenuItem;
	private JMenuItem openSourceFileChooserJMenuItem;
	private JMenuItem openDestinationFileChooserJMenuItem;
	private JMenuItem startProcessJMenuItem;
	private JMenuItem helpJMenuItem;
	private JMenuItem aboutJMenuItem;

	private JPanel mainPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel variablesPanel;
	private JPanel thumbNailsBackgroundPanel;
	private JPanel thumbNailsPanel;
	private JPanel infoPanel;
	private JPanel metaDataPanel;
	private JPanel namePreviewPanel;

	private JMenuBar menuBar;

	private JCheckBox createThumbNailsCheckBox;

	private Thread pictureLoader;

	private JTabbedPane tabbedPane;

	private DefaultTableModel modelMetaData;
	private DefaultTableModel modelPreview;

	private JTable metaDataTable;
	private JTable previewTable;
	
	private Timer fileChooserScanner;
	
	private Language lang;

	public MainGUI(){
		
		FileSetup.check();
		Logger logger = Logger.getInstance();
		logger.logDEBUG("JavaPEG is starting");
		logger.logDEBUG("Language File Loading Started");
		readLanguageFile();
		logger.logDEBUG("Language File Loading Finished");
		logger.logDEBUG("Creation of Left Panel Started");
		createLeftPanel();
		logger.logDEBUG("Creation of Left Panel Finished");
		logger.logDEBUG("Creation of Right Panel Started");
		createRightPanel();
		logger.logDEBUG("Creation of Right Panel Finished");
		logger.logDEBUG("Creation of Thumb Nails Background Panel Started");
		createThumbNailsBackgroundPanel();
		logger.logDEBUG("Creation of Thumb Nails Background Panel Finished");
		logger.logDEBUG("Creation of Info Panel Started");
		createInfoPanel();
		logger.logDEBUG("Creation of Info Panel Finished");
		logger.logDEBUG("Creation of Main Frame Started");
		createMainFrame();
		logger.logDEBUG("Creation of Main Frame Finished");
		logger.logDEBUG("Creation of Menu Bar Started");
		createMenuBar();
		logger.logDEBUG("Creation of Menu Bar Finished");
		createToolBar();
		logger.logDEBUG("Adding of Event Listeners Started");
		addListeners();
		logger.logDEBUG("Adding of Event Listeners Finished");
		logger.logDEBUG("Application initialization Started");
		initiateProgram();
		logger.logDEBUG("Application initialization Finished");
		logger.logDEBUG("Application Context initialization Started");
		initiateApplicationContext();
		logger.logDEBUG("Application Context initialization Finished");
	}
	
	// Inläsning av språkfil
	private void readLanguageFile(){
		lang = Language.getInstance();
		lang.loadLanguageFile();
	}

	private void createMenuBar(){

		// Skapa menyrader i arkiv-menyn
		openSourceFileChooserJMenuItem = new JMenuItem(lang.get("menu.item.openSourceFileChooser"));
		openSourceFileChooserJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.openSourceFileChooser.accelerator").charAt(0)), ActionEvent.CTRL_MASK));

		openDestinationFileChooserJMenuItem = new JMenuItem(lang.get("menu.item.openDestinationFileChooser"));
		openDestinationFileChooserJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.openDestinationFileChooser.accelerator").charAt(0)), ActionEvent.CTRL_MASK));
		openDestinationFileChooserJMenuItem.setEnabled(false);

		startProcessJMenuItem = new JMenuItem(lang.get("menu.item.startProcess"));
		startProcessJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.startProcess.accelerator").charAt(0)), ActionEvent.CTRL_MASK));
		startProcessJMenuItem.setEnabled(false);
		
		shutDownProgramJMenuItem = new JMenuItem(lang.get("menu.item.exit"));
		shutDownProgramJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.exit.accelerator").charAt(0)), ActionEvent.CTRL_MASK));
		
		fileMenu = new JMenu(lang.get("menu.file"));
		fileMenu.setMnemonic(lang.get("menu.mnemonic.file").charAt(0));
		
		fileMenu.add(openSourceFileChooserJMenuItem);
		fileMenu.add(openDestinationFileChooserJMenuItem);
		fileMenu.add(startProcessJMenuItem);
		fileMenu.add(shutDownProgramJMenuItem);
		
		// Skapa menyrader i språk-menyn
		languageOptionsJMenuItem = new JMenuItem(lang.get("menu.item.languageChoice"));
		languageOptionsJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.item.languageChoice.accelerator").charAt(0)), ActionEvent.CTRL_MASK));
		
		languageMenu = new JMenu(lang.get("menu.language"));
		languageMenu.setMnemonic(lang.get("menu.mnemonic.language").charAt(0));

		languageMenu.add(languageOptionsJMenuItem);

		// Skapa menyrader i hjälp-menyn
		helpJMenuItem = new JMenuItem(lang.get("menu.item.programHelp"));
		helpJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.item.programHelp.accelerator").charAt(0)), ActionEvent.CTRL_MASK));
		
		aboutJMenuItem = new JMenuItem(lang.get("menu.item.about"));
		aboutJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.item.about.accelerator").charAt(0)), ActionEvent.CTRL_MASK));
		
		helpMenu = new JMenu(lang.get("menu.help"));
		helpMenu.setMnemonic(lang.get("menu.mnemonic.help").charAt(0));

		helpMenu.add(helpJMenuItem);
		helpMenu.add(aboutJMenuItem);

		menuBar = new JMenuBar();

		menuBar.add(fileMenu);
		menuBar.add(languageMenu);
		menuBar.add(helpMenu);

		this.setJMenuBar(menuBar);
	}

	private void createToolBar(){
	}

	private void createLeftPanel(){
		
		leftPanel = new JPanel(null);
		leftPanel.setBounds(3,3,215,299);

		pathLabel = new JLabel(lang.get("labels.sourcePath"));
		pathLabel.setForeground(Color.GRAY);
		pathLabel.setBounds(5,5,200,12);

		pathTextField = new JTextField();
		pathTextField.setBounds(5,19,175,20);
		pathTextField.setEditable(false);
		pathTextField.setBackground(Color.WHITE);

		InputStream imageStream = null;
		ImageIcon openPictureImageIcon = new ImageIcon();
		try {
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/open.gif");
			openPictureImageIcon.setImage(ImageIO.read(imageStream));
		} catch (Exception e) {
			Logger.getInstance().logERROR("Could not open the image open.gif");
		} finally {
			StreamUtil.closeStream(imageStream);
		}

		pathSelectButton = new JButton(openPictureImageIcon);
		pathSelectButton.setActionCommand("selectPathButton");
		pathSelectButton.setToolTipText(lang.get("tooltip.sourcePathButton"));
		pathSelectButton.setBounds(182,19,25,20);
				
		destinationPathLabel = new JLabel(lang.get("labels.destinatonPath"));
		destinationPathLabel.setForeground(Color.GRAY);
		destinationPathLabel.setBounds(5,45,200,12);
		destinationPathLabel.setEnabled(false);

		destinationPathTextField = new JTextField();
		destinationPathTextField.setBounds(5,58,175,20);
		destinationPathTextField.setEditable(false);
		destinationPathTextField.setBackground(Color.WHITE);
		
		destinationPathButton = new JButton(openPictureImageIcon);
		destinationPathButton.setActionCommand("destinationPathButton");
		destinationPathButton.setToolTipText(lang.get("tooltip.destinationPathButton"));
		destinationPathButton.setBounds(182,58,25,20);
		
		subFolderLabel = new JLabel(lang.get("labels.subFolderName"));
		subFolderLabel.setForeground(Color.GRAY);
		subFolderLabel.setBounds(5,84,200,12);
		subFolderLabel.setEnabled(false);

		subFolderTextField = new JTextField();
		subFolderTextField.setBounds(5,98,175,20);
		subFolderTextField.setToolTipText(lang.get("tooltip.subFolderName"));
		subFolderTextField.setEnabled(false);

		fileNameTemplateLabel = new JLabel(lang.get("labels.fileNameTemplate"));
		fileNameTemplateLabel.setForeground(Color.GRAY);
		fileNameTemplateLabel.setBounds(5,125,200,12);

		fileNameTemplateTextField = new JTextField();
		fileNameTemplateTextField.setToolTipText(lang.get("tooltip.fileNameTemplate"));
		fileNameTemplateTextField.setBounds(5,139,175,20);

		leftPanel.add(pathLabel);
		leftPanel.add(pathTextField);
		leftPanel.add(pathSelectButton);
		leftPanel.add(destinationPathLabel);
		leftPanel.add(destinationPathTextField);
		leftPanel.add(destinationPathButton);
		leftPanel.add(subFolderLabel);
		leftPanel.add(subFolderTextField);
		leftPanel.add(fileNameTemplateLabel);
		leftPanel.add(fileNameTemplateTextField);
	}

	private void createRightPanel(){

		rightPanel = new JPanel(null);
		rightPanel.setBounds(219,3,215,299);

		variablesLabel = new JLabel(lang.get("labels.variables"));
		variablesLabel.setForeground(Color.GRAY);
		variablesLabel.setBounds(5,5,200,12);

		variablesPanel = new JPanel(null);
		variablesPanel.setBounds(5,19,205,190);
		variablesPanel.setBackground(Color.WHITE);
		variablesPanel.setBorder(BorderFactory.createLineBorder(new Color(127,157,185)));

		dateLabelA = new JLabel("%" + lang.get("variable.pictureDateVariable") + "%");
		dateLabelA.setBounds(6,5,35,12);
		dateLabelA.setName("%" + lang.get("variable.pictureDateVariable") + "%");

		dateLabelB = new JLabel("=  " + lang.get("variable.pictureDate") + " (*)");
		dateLabelB.setBounds(45,5,150,12);
		dateLabelB.setName("%" + lang.get("variable.pictureDateVariable") + "%");

		timeLabelA = new JLabel("%" + lang.get("variable.pictureTimeVariable") + "%");
		timeLabelA.setBounds(6,20,35,12);
		timeLabelA.setName("%" + lang.get("variable.pictureTimeVariable") + "%");

		timeLabelB = new JLabel("=  " + lang.get("variable.pictureTime"));
		timeLabelB.setBounds(45,20,150,12);
		timeLabelB.setName("%" + lang.get("variable.pictureTimeVariable") + "%");

		modelLabelA = new JLabel("%" + lang.get("variable.cameraModelVariable") + "%");
		modelLabelA.setBounds(6,35,35,12);
		modelLabelA.setName("%" + lang.get("variable.cameraModelVariable") + "%");

		modelLabelB = new JLabel("=  " + lang.get("variable.cameraModel") + " (*)");
		modelLabelB.setBounds(45,35,150,12);
		modelLabelB.setName("%" + lang.get("variable.cameraModelVariable") + "%");

		shutterSpeedLabelA = new JLabel("%" + lang.get("variable.shutterSpeedVariable") + "%");
		shutterSpeedLabelA.setBounds(6,50,35,12);
		shutterSpeedLabelA.setName("%" + lang.get("variable.shutterSpeedVariable") + "%");

		shutterSpeedLabelB = new JLabel("=  " + lang.get("variable.shutterSpeed"));
		shutterSpeedLabelB.setBounds(45,50,150,12);
		shutterSpeedLabelB.setName("%" + lang.get("variable.shutterSpeedVariable") + "%");

		isoLabelA = new JLabel("%" + lang.get("variable.isoValueVariable") + "%");
		isoLabelA.setBounds(6,65,35,12);
		isoLabelA.setName("%" + lang.get("variable.isoValueVariable") + "%");

		isoLabelB = new JLabel("=  " + lang.get("variable.isoValue"));
		isoLabelB.setBounds(45,65,150,12);
		isoLabelB.setName("%" + lang.get("variable.isoValueVariable") + "%");

		widthLabelA = new JLabel("%" + lang.get("variable.pictureWidthVariable") + "%");
		widthLabelA.setBounds(6,80,35,12);
		widthLabelA.setName("%" + lang.get("variable.pictureWidthVariable") + "%");

		widthLabelB = new JLabel("=  " + lang.get("variable.pictureWidth"));
		widthLabelB.setBounds(45,80,150,12);
		widthLabelB.setName("%" + lang.get("variable.pictureWidthVariable") + "%");

		heigthLabelA = new JLabel("%" + lang.get("variable.pictureHeightVariable") + "%");
		heigthLabelA.setBounds(6,95,35,12);
		heigthLabelA.setName("%" + lang.get("variable.pictureHeightVariable") + "%");

		heigthLabelB = new JLabel("=  " + lang.get("variable.pictureHeight"));
		heigthLabelB.setBounds(45,95,150,12);
		heigthLabelB.setName("%" + lang.get("variable.pictureHeightVariable") + "%");

		apertureLabelA = new JLabel("%" + lang.get("variable.apertureValueVariable") + "%");
		apertureLabelA.setBounds(6,110,35,12);
		apertureLabelA.setName("%" + lang.get("variable.apertureValueVariable") + "%");

		apertureLabelB = new JLabel("=  " + lang.get("variable.apertureValue"));
		apertureLabelB.setBounds(45,110,150,12);
		apertureLabelB.setName("%" + lang.get("variable.apertureValueVariable") + "%");

		todaysDateLabelA = new JLabel("%" + lang.get("variable.dateOftodayVariable") + "%");
		todaysDateLabelA.setBounds(6,125,35,12);
		todaysDateLabelA.setName("%" + lang.get("variable.dateOftodayVariable") + "%");

		todaysDateLabelB = new JLabel("=  " + lang.get("variable.dateOftoday") + " (*)");
		todaysDateLabelB.setBounds(45,125,150,12);
		todaysDateLabelB.setName("%" + lang.get("variable.dateOftodayVariable") + "%");

		JLabel infoLabelA = new JLabel(lang.get("variable.comment.infoLabelA"));
		infoLabelA.setBounds(6,145,190,12);

		JLabel infoLabelB = new JLabel(lang.get("variable.comment.infoLabelB"));
		infoLabelB.setBounds(26,160,150,12);

		variablesPanel.add(dateLabelA);
		variablesPanel.add(dateLabelB);
		variablesPanel.add(timeLabelA);
		variablesPanel.add(timeLabelB);
		variablesPanel.add(modelLabelA);
		variablesPanel.add(modelLabelB);
		variablesPanel.add(shutterSpeedLabelA);
		variablesPanel.add(shutterSpeedLabelB);
		variablesPanel.add(isoLabelA);
		variablesPanel.add(isoLabelB);
		variablesPanel.add(widthLabelA);
		variablesPanel.add(widthLabelB);
		variablesPanel.add(heigthLabelA);
		variablesPanel.add(heigthLabelB);
		variablesPanel.add(apertureLabelA);
		variablesPanel.add(apertureLabelB);
		variablesPanel.add(todaysDateLabelA);
		variablesPanel.add(todaysDateLabelB);
		variablesPanel.add(infoLabelA);
		variablesPanel.add(infoLabelB);

		createThumbNailsCheckBox = new JCheckBox(lang.get("checkbox.createThumbNails"));
		createThumbNailsCheckBox.setBounds(5,233,200,16);
		createThumbNailsCheckBox.setToolTipText(lang.get("tooltip.createThumbNails"));
		createThumbNailsCheckBox.setActionCommand("createThumbNailsCheckBox");
		createThumbNailsCheckBox.setEnabled(false);
		
		InputStream imageStream = null;
		ImageIcon playPictureImageIcon = new ImageIcon();
		try {
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/play.gif");
			playPictureImageIcon.setImage(ImageIO.read(imageStream));
		} catch (Exception e) {
			Logger.getInstance().logERROR("Could not open the image play.gif");
		} finally {
			StreamUtil.closeStream(imageStream);
		}
				
		startProcessButton = new JButton(playPictureImageIcon);
		startProcessButton.setBounds(182,274,25,20);
		startProcessButton.setActionCommand("startProcessButton");
		startProcessButton.setToolTipText(lang.get("tooltip.beginNameChangeProcessButton"));
		startProcessButton.setEnabled(false);
				
		rightPanel.add(variablesPanel);
		rightPanel.add(variablesLabel);
		rightPanel.add(createThumbNailsCheckBox);
		rightPanel.add(startProcessButton);
	}

	private void createThumbNailsBackgroundPanel(){

		thumbNailsBackgroundPanel = new JPanel(null);
		thumbNailsBackgroundPanel.setBounds(3,310,1013,404);
		thumbNailsBackgroundPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		picturesLabel = new JLabel(lang.get("picture.panel.pictureLabel"));
		picturesLabel.setForeground(Color.GRAY);
		picturesLabel.setBounds(8,7,200,12);

		thumbNailsBackgroundPanel.add(picturesLabel);

		thumbNailsPanel = new JPanel(new GridLayout(0,6));

		JScrollBar hSB = new JScrollBar(JScrollBar.HORIZONTAL);
		JScrollBar vSB = new JScrollBar(JScrollBar.VERTICAL);

		hSB.setUnitIncrement(40);
		vSB.setUnitIncrement(40);

		JScrollPane scrollpane = new JScrollPane(thumbNailsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setHorizontalScrollBar(hSB);
		scrollpane.setVerticalScrollBar(vSB);
		scrollpane.setBounds(8,21,997,375);

		thumbNailsBackgroundPanel.add(scrollpane);
	}

	private void createInfoPanel() {

		// Skapa övergripande panel som håller innehåller för övrigt innehåll.
		infoPanel = new JPanel(null);
		infoPanel.setBounds(443,3,573,304);
		infoPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		infoPanelLabel = new JLabel(lang.get("information.panel.informationLabel"));
		infoPanelLabel.setForeground(Color.GRAY);
		infoPanelLabel.setBounds(8,7,200,12);

		// Skapa en tabbed pane som innehåller tre paneler
		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(8,21,559,278);

		// Skapa de tre panelerna som skall läggas till tabbed pane
		metaDataPanel = new JPanel(null);
		namePreviewPanel = new JPanel(null);

		// Skapa tabellmodeller
		modelMetaData = new DefaultTableModel();
    	modelPreview = new DefaultTableModel();
    	
    	// Add columns
    	modelPreview.addColumn(lang.get("information.panel.fileNameCurrent"));
    	modelPreview.addColumn(lang.get("information.panel.fileNamePreview"));
    	
    	// Skapa tabellen för metadata-informatonen och sätt attribut till den
		metaDataTable = new JTable(modelMetaData);
		metaDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		metaDataTable.getTableHeader().setReorderingAllowed(false);

		// Skapa tabellen för namnförhandsgranskningen och sätt attribut till den
		previewTable = new JTable(modelPreview);
		previewTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		previewTable.getTableHeader().setReorderingAllowed(false);

		// Skapa scrollbars...
		JScrollBar mhSB = new JScrollBar(JScrollBar.HORIZONTAL);
		JScrollBar mvSB = new JScrollBar(JScrollBar.VERTICAL);

		JScrollBar phSB = new JScrollBar(JScrollBar.HORIZONTAL);
		JScrollBar pvSB = new JScrollBar(JScrollBar.VERTICAL);

		// ... och sätt dess egenskaper
		mhSB.setUnitIncrement(40);
		mvSB.setUnitIncrement(40);

		phSB.setUnitIncrement(40);
		pvSB.setUnitIncrement(40);

		JScrollPane scrollpaneMeta = new JScrollPane(metaDataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpaneMeta.setHorizontalScrollBar(mhSB);
		scrollpaneMeta.setVerticalScrollBar(mvSB);
		scrollpaneMeta.setBounds(0,0,553,249);

		subFolderPreviewTextFieldLabel = new JTextField();
		subFolderPreviewTextFieldLabel.setBounds(0,2,100,17);
		subFolderPreviewTextFieldLabel.setText(lang.get("information.panel.subFolderNameLabel"));
		subFolderPreviewTextFieldLabel.setEditable(false);

		subFolderNamePreviewTextField = new JTextField();
		subFolderNamePreviewTextField.setBounds(101,2,452,17);
		subFolderNamePreviewTextField.setEditable(false);
		subFolderNamePreviewTextField.setBackground(Color.WHITE);

		JScrollPane scrollpanePreview = new JScrollPane(previewTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpanePreview.setHorizontalScrollBar(phSB);
		scrollpanePreview.setVerticalScrollBar(pvSB);
		scrollpanePreview.setBounds(0,20,553,229);

		tabbedPane.addTab(lang.get("information.panel.metaDataLabel"), metaDataPanel);
		tabbedPane.addTab(lang.get("information.panel.previewLabel"), namePreviewPanel);

		metaDataPanel.add(scrollpaneMeta);
		namePreviewPanel.add(subFolderPreviewTextFieldLabel);
		namePreviewPanel.add(subFolderNamePreviewTextField);
		namePreviewPanel.add(scrollpanePreview);
		infoPanel.add(infoPanelLabel);
		infoPanel.add(tabbedPane);
	}

	// Denna metod anropas när innehållet i textfälten för undermappsnamn
	// eller filnamnmall ändrats.
	private void updatePreviewTable() {
		
		if(pathTextField.getText().length() > 0) {
	
			ValidatorStatus vs = JPEGTotalPathLength.getInstance().test();
						
			if (vs.isValid()) {
				
				tabbedPane.setSelectedIndex(1);
				modelPreview.setRowCount(0);
								
				RenameProcessContext rpc = RenameProcessContext.getInstance();
						
				Map<File, File> allJPEGFileNameMappings = rpc.getAllJPEGFileNameMappings();
				
				Set<File> sortedSet = new TreeSet<File>(allJPEGFileNameMappings.keySet());
										
				for (File file : sortedSet) {
					Object[] row = {file.getName(), allJPEGFileNameMappings.get(file).getName()};
					modelPreview.addRow(row);
				}
	
				Table.packColumns(previewTable, 6);
	
				int theColumnWidth = 0;
	
				theColumnWidth = previewTable.getColumnModel().getColumn(0).getPreferredWidth();
	
				subFolderPreviewTextFieldLabel.setBounds(0,2,theColumnWidth,17);
				subFolderNamePreviewTextField.setBounds(theColumnWidth + 1,2,552 - theColumnWidth,17);
				subFolderNamePreviewTextField.setText(rpc.getSubDirectoryName());
			} else {
				JOptionPane.showMessageDialog(null, vs.getStatusMessage(), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void createMainFrame(){
		
		Config conf = Config.getInstance();
		Logger logger = Logger.getInstance();

		this.setTitle("JavaPEG 2.0");

		InputStream imageStream = null;
		ImageIcon titleImageIcon = new ImageIcon();
		try {
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/javapeg.gif");
			titleImageIcon.setImage(ImageIO.read(imageStream));
			this.setIconImage(titleImageIcon.getImage());
		} catch (Exception e) {
			logger.logERROR("Could not open the image javapeg.gif");
		} finally {
			StreamUtil.closeStream(imageStream);
		}
		
		this.setSize(new Dimension(1024,768));

		Point xyFromConfig = new Point(conf.getIntProperty("window.location.x"),conf.getIntProperty("window.location.y"));
				
		if(Screen.isOnScreen(xyFromConfig)) {
			this.setLocation(xyFromConfig);
		} else {
			this.setLocation(0,0);
			JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			logger.logERROR("Could not set location of Main GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");
		}
		
		this.setResizable(false);

		mainPanel = new JPanel(null);
		mainPanel.setBounds(3,3,438,304);
		mainPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);

		this.getContentPane().setLayout(null);
		this.getContentPane().add(mainPanel);
		this.getContentPane().add(thumbNailsBackgroundPanel);
		this.getContentPane().add(infoPanel);

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e){
			logger.logERROR("Could not set desired Look And Feel for Main GUI");
			
			StringBuilder sb = new StringBuilder(4096);
			
			for(StackTraceElement element : e.getStackTrace()) {
				sb.append(element.toString());
				sb.append(System.getProperty("line.separator"));
			}
			logger.logERROR(sb.toString());
		}
	}

	public void addListeners(){
		this.addWindowListener(new WindowDestroyer());
		shutDownProgramJMenuItem.addActionListener(new MenuListener());
		openSourceFileChooserJMenuItem.addActionListener(new MenuListener());
		openDestinationFileChooserJMenuItem.addActionListener(new MenuListener());
		startProcessJMenuItem.addActionListener(new MenuListener());
		pathSelectButton.addActionListener(new ButtonListener());
		destinationPathButton.addActionListener(new ButtonListener());
		startProcessButton.addActionListener(new ButtonListener());
		helpJMenuItem.addActionListener(new MenuListener());
		aboutJMenuItem.addActionListener(new MenuListener());
		languageOptionsJMenuItem.addActionListener(new MenuListener());
		
		subFolderTextField.getDocument().addDocumentListener(new JTextFieldListener());
		fileNameTemplateTextField.getDocument().addDocumentListener(new JTextFieldListener());
		tabbedPane.addChangeListener(new JTabbedPaneListener());
		createThumbNailsCheckBox.addActionListener(new CheckBoxListener());
	}

	public void initiateProgram(){
		
		Config conf = Config.getInstance();
		
	 	subFolderTextField.setText(conf.getStringProperty("subFolderName"));
		fileNameTemplateTextField.setText(conf.getStringProperty("fileNameTemplate"));

		if(conf.getBooleanProperty("createThumbNailsCheckBox"))
			createThumbNailsCheckBox.setSelected(true);

		destinationPathLabel.setEnabled(false);
		destinationPathTextField.setEnabled(false);
		destinationPathButton.setEnabled(false);

		subFolderTextField.setEnabled(false);
		subFolderLabel.setEnabled(false);

		fileNameTemplateLabel.setEnabled(false);
		fileNameTemplateTextField.setEnabled(false);
		
		tabbedPaneEnabled(false, false);
			
		Update.updateAllUIs();
		
		ActionListener taskPerformer = new ActionListener() {
			ApplicationContext ac = ApplicationContext.getInstance();
			
			public void actionPerformed(ActionEvent evt) {
								
				if(ac.isFileChooserCancelButtonClicked()) {
					fileChooserScanner.stop();
				} else if (ac.isFileChooserOkButtonClicked()) {
					fileChooserScanner.stop();
					
					String temp = ac.getSourcePath();

					char [] tempArray = temp.toCharArray();

					for(int i = 0; i < tempArray.length; i++) {
						if((int)tempArray[i] == 92) {
							tempArray[i] = '/';
						}
					}
					
					Config conf = Config.getInstance();
					
					pathTextField.setText(String.valueOf(tempArray));
					conf.setStringProperty("sourcePath", pathTextField.getText());
					destinationPathButton.setEnabled(true);
					destinationPathLabel.setEnabled(true);
					openDestinationFileChooserJMenuItem.setEnabled(true);

					MetaDataUtil.setMetaDataObjects(conf.getStringProperty("sourcePath"));
					Vector<Vector<String>> rowData = MetaDataUtil.getMetaData();
					Vector<String> columnNames = new Vector<String>();
					
					columnNames.addElement(lang.get("information.panel.columnNameFileName"));
					columnNames.addElement(lang.get("variable.pictureDate"));
					columnNames.addElement(lang.get("variable.pictureTime"));
					columnNames.addElement(lang.get("variable.cameraModel"));
					columnNames.addElement(lang.get("variable.shutterSpeed"));
					columnNames.addElement(lang.get("variable.isoValue"));
					columnNames.addElement(lang.get("variable.pictureWidth"));
					columnNames.addElement(lang.get("variable.pictureHeight"));
					columnNames.addElement(lang.get("variable.apertureValue"));

					modelMetaData.setDataVector(rowData, columnNames);

					// Rensa en eventuellt ifylld filnamnsförhandsgranskningstabell.
					// Detta kan ske då det redan öppnats bilder tidigare och dessa
					// fått förhandsgranskning på sina filnamn
					if(modelPreview.getRowCount() > 0) {
						modelPreview.setRowCount(0);
					}

					// Byta till metadata-tabben ifall tabben skulle stå i annat läge.
					tabbedPane.setSelectedIndex(0);

					Table.packColumns(metaDataTable, 6);

					populateThumbNailsPanel(conf.getStringProperty("sourcePath"));
					
					tabbedPaneEnabled(true, false);
				}
			}
		};
  		fileChooserScanner = new Timer(100, taskPerformer);
	}
	
	public void initiateApplicationContext() {
					
		Config conf = Config.getInstance();
		
		ApplicationContext ac = ApplicationContext.getInstance();
		ac.setSourcePath(conf.getStringProperty("sourcePath"));
		ac.setTemplateFileName(conf.getStringProperty("fileNameTemplate"));
		ac.setTemplateSubFolderName(conf.getStringProperty("subFolderName"));
		ac.setCreateThumbNailsCheckBoxSelected(conf.getBooleanProperty("createThumbNailsCheckBox"));
	}

	private void saveSettings(){
		
		Config conf = Config.getInstance();
		Logger logger = Logger.getInstance();

		if(!pathTextField.getText().equals(""))
			conf.setStringProperty("sourcePath", pathTextField.getText());

		if(!destinationPathTextField.getText().equals(""))
			conf.setStringProperty("destinationPath", destinationPathTextField.getText());

		conf.setStringProperty("subFolderName", subFolderTextField.getText());
		conf.setStringProperty("fileNameTemplate", fileNameTemplateTextField.getText());
		conf.setBooleanProperty("createThumbNailsCheckBox", createThumbNailsCheckBox.isSelected());
		conf.setIntProperty("window.location.x", this.getLocationOnScreen().x);
		conf.setIntProperty("window.location.y", this.getLocationOnScreen().y);

		try {
			conf.saveSettings();
		} catch (FileNotFoundException e) {
			logger.logFATAL("Could not save configuration to file: ");
			for(StackTraceElement element : e.getStackTrace()) {
				logger.logFATAL(element.toString());	
			}
			
		} catch (IOException e) {
			logger.logFATAL("Could not save configuration to file: ");
			for(StackTraceElement element : e.getStackTrace()) {
				logger.logFATAL(element.toString());	
			}
		}
	}

	/***
	 * Denna metod anropas när källkatalog valts. D.v.s när användaren klickat
	 * på Ok i filväljardialogen. Därefter tar denna metod vara på sökvägen till
	 * källmappen och fyller på panelen med tumnaglar genom att till sin hjälp
	 * använda ett par hjälpklasser.
	 **/
	private void populateThumbNailsPanel(final String sourcePath) {

		// Börja med att rensa panelen från eventuella gamla bilder...
		thumbNailsPanel.removeAll();

		// Skapa en ny tråd för tumnagelskapandet för att undvika att resten
		// av programmet fryser under tiden.
		if(pictureLoader != null && pictureLoader.isAlive()) {
		} else {

			pictureLoader = new Thread(){

				public void run(){

					// Hämta alla jpgfiler ifrån utpekad källkatalog
					FileRetriever fr = FileRetriever.getInstance();
								
					// Hämta en lista med jpeg filer och iterera igenom alla filer 
					// och leta upp och ta ut tumnageln ur varje bild.
					for (File jpegFile : fr.getJPEGFiles()) {
					
						// Hämta ur tumnageln ur angiven fil
						JPEGThumbNail tn = JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(jpegFile);

						JLabel thumbContainer = new JLabel();

						thumbContainer.setIcon(new ImageIcon(tn.getThumbNailData()));
						thumbContainer.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
						thumbContainer.setToolTipText(MetaDataUtil.getToolTipText(jpegFile));
						thumbContainer.setHorizontalAlignment(JLabel.CENTER);

						thumbNailsPanel.add(thumbContainer);
						thumbNailsPanel.updateUI();
					}
				}
			};
		}

		if(pictureLoader != null && !pictureLoader.isAlive()) {
			pictureLoader.start();
		}
	}
	
    private void validateInputInRealtime() {
	    	
		if (subFolderTextField.isFocusOwner() || fileNameTemplateTextField.isFocusOwner()) {
	
			ApplicationContext ac = ApplicationContext.getInstance();
			ac.setTemplateFileName(fileNameTemplateTextField.getText());
			ac.setTemplateSubFolderName(subFolderTextField.getText());
			
			ValidatorStatus vs = FileAndSubDirectoryTemplate.getInstance().test();
			
			if(vs.isValid()) {
				updatePreviewTable();
			} else {
				JOptionPane.showMessageDialog(null,vs.getStatusMessage(), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void tabbedPaneEnabled(boolean fistEnabled, boolean secondEnabled) {
    	tabbedPane.setEnabledAt(0, fistEnabled);
    	tabbedPane.setEnabledAt(1, secondEnabled);
    }
	
	private void setInputsEnabled(boolean state) {
		pathSelectButton.setEnabled(state);
		destinationPathButton.setEnabled(state);
		fileNameTemplateTextField.setEnabled(state);
		subFolderTextField.setEnabled(state);
		createThumbNailsCheckBox.setEnabled(state);
		startProcessButton.setEnabled(state);	
	}
		
	// WindowDestroyer
	private class WindowDestroyer extends WindowAdapter{
		public void windowClosing (WindowEvent e){
			saveSettings();
			Logger logger = Logger.getInstance();
			logger.logDEBUG("JavePEG was shut down");
			logger.flush();
			System.exit(0);
		}
	}

	// Menylyssnarklass
	private class MenuListener implements ActionListener{
		
		Config conf = Config.getInstance();
		
		public void actionPerformed(ActionEvent e){
			String actionCommand = e.getActionCommand();

			if(actionCommand.equals(lang.get("menu.item.exit"))){
					saveSettings();
					System.exit(0);
			}

			else if(actionCommand.equals(lang.get("menu.item.openSourceFileChooser"))) {
				pathSelectButton.doClick();
			}

			else if(actionCommand.equals(lang.get("menu.item.openDestinationFileChooser"))) {
				destinationPathButton.doClick();
			}

			else if(actionCommand.equals(lang.get("menu.item.startProcess"))) {
				startProcessButton.doClick();
			}

			else if (actionCommand.equals(lang.get("menu.item.about")))	{

				JOptionPane.showMessageDialog(null, lang.get("aboutDialog.TextRowA") +
                                             "\n" + lang.get("aboutDialog.TextRowB") +
                                             "\n" +
                                             "\n" + lang.get("aboutDialog.TextRowC") +
                                             "\n" + lang.get("aboutDialog.TextRowD") +
                                             "\n" +
                                             "\n" + lang.get("aboutDialog.TextRowE") +
                                             "\n" +
                                             "\n" + lang.get("aboutDialog.TextRowF") +
                                             "\n" + lang.get("aboutDialog.TextRowG") +
                                             "\n" + lang.get("aboutDialog.TextRowH"), lang.get("aboutDialog.Label"), JOptionPane.INFORMATION_MESSAGE);
	    	}

	    	else if (actionCommand.equals(lang.get("menu.item.programHelp"))) {
				new HelpViewerGUI().setVisible(true);				
			}
			
			else if (actionCommand.equals(lang.get("menu.item.languageChoice"))) {
				LanguageOptionsGUI loGUI = new LanguageOptionsGUI();
				loGUI.setVisible(true);
			}
		}
	}

	// Knapplyssnarklass
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String actionCommand = e.getActionCommand();

			if(actionCommand.equals("selectPathButton")) {
				FileChooser fc = new FileChooser();
				fc.setVisible(true);
				fileChooserScanner.start();
			}
			
			if(actionCommand.equals("destinationPathButton")) {
				destinationPathTextField.setEditable(true);
				
				Config conf = Config.getInstance();

				/**
			     * Kontrollera så att sparad sökväg fortfarande existerar
			     * och i annat fall hoppa upp ett steg i trädstrukturen och
			     * kontrollera ifall den sökvägen existerar
			     **/
				File tempFile = new File(conf.getStringProperty("destinationPath"));

				boolean exists = false;
				while(!exists) {
					try {
						if(!tempFile.exists()) {
							tempFile = tempFile.getParentFile();
						} else {
							exists = true;
						}
					} catch (NullPointerException npe) {
						System.out.println("catch");
						FileSystemView fsv = FileSystemView.getFileSystemView(); 
						tempFile = fsv.getDefaultDirectory();
						exists = true;
					}
				}
				
				JFileChooser chooser = new JFileChooser(tempFile.getAbsolutePath());
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setDialogTitle(lang.get("fileSelectionDialog.destinationPathFileChooser"));
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION)
				{
					String temp = chooser.getSelectedFile().getAbsolutePath();
					
					char [] tempArray = temp.toCharArray();

					for(int i = 0; i < tempArray.length; i++) {
						if((int)tempArray[i] == 92) {
							tempArray[i] = '/';
						}
					}
					
					if (!pathTextField.getText().equals(String.valueOf(tempArray))) {
						
						ApplicationContext.getInstance().setDestinationPath(String.valueOf(tempArray));
						destinationPathTextField.setText(String.valueOf(tempArray));
						conf.setStringProperty("destinationPath", destinationPathTextField.getText());
						subFolderTextField.setEnabled(true);
						fileNameTemplateTextField.setEnabled(true);	
						destinationPathTextField.setEnabled(true);
						destinationPathTextField.setEditable(false);
						tabbedPaneEnabled(true, true);
						createThumbNailsCheckBox.setEnabled(true);
						startProcessButton.setEnabled(true);
						startProcessJMenuItem.setEnabled(true);
					} else {
						JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.sameSourceAndDestination"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
					}	
				}
			}
			
			if(actionCommand.equals("startProcessButton")){
				String subFolderName = "";
				String fileNameTemplate = ""; 

				// Ta bort eventuella mellanslag först och sist i undermappsnamnsmallen
				subFolderName = subFolderTextField.getText();
				subFolderName = subFolderName.trim();
				subFolderTextField.setText(subFolderName);

				// Ta bort eventuella mellanslag först och sist i filnamnsmallen
				fileNameTemplate = fileNameTemplateTextField.getText();
				fileNameTemplate = fileNameTemplate.trim();
				fileNameTemplateTextField.setText(fileNameTemplate);
								
				Thread renameThread = new Thread() {
					
					public void run(){
						
						setInputsEnabled(false);
						pathSelectButton.setEnabled(false);
						destinationPathButton.setEnabled(false);
						fileNameTemplateTextField.setEnabled(false);
						subFolderTextField.setEnabled(false);
								
						RenameProcess rp = new RenameProcess();
						rp.init();
						rp.setVisible(true);

						Logger logger = Logger.getInstance();
						logger.logDEBUG("Pre File Processing Started");
						rp.setLogMessage(lang.get("rename.PreFileProcessor.starting"));
						ValidatorStatus vs = PreFileProcessor.getInstance().startTest(rp);
						logger.logDEBUG("Pre File Processing Finished");
						rp.setLogMessage(lang.get("rename.PreFileProcessor.finished") + "\n");
						
						if(!vs.isValid()) {
							rp.setAlwaysOnTop(false);
							JOptionPane.showMessageDialog(null, vs.getStatusMessage(), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
							logger.logERROR("Pre File Processing found following errors:\n" + vs.getStatusMessage());
							rp.setLogMessage(lang.get("rename.PreFileProcessor.error")+ "\n" + vs.getStatusMessage());
							rp.setAlwaysOnTop(true);
							setInputsEnabled(true);
						} else {
							logger.logDEBUG("File Processing Started");
							rp.setLogMessage(lang.get("rename.FileProcessor.starting"));
							FileProcessor.getInstance().process(rp);
							logger.logDEBUG("File Processing Finished");
							rp.setLogMessage(lang.get("rename.FileProcessor.finished"));
							
							
							if(createThumbNailsCheckBox.isSelected()) {
								logger.logDEBUG("Thumb Nail Overview Creation Started");
								rp.setLogMessage(lang.get("thumbnailoverview.ThumbNailOverViewCreator.starting"));
								ThumbNailOverViewCreator.getInstance().create();
								logger.logDEBUG("Thumb Nail Overview Creation Finished");
								rp.setLogMessage(lang.get("thumbnailoverview.ThumbNailOverViewCreator.finished"));
								rp.incProcessProgress();
							}
							
							
							logger.logDEBUG("File Integrity Check Started");
							rp.setLogMessage(lang.get("rename.PostFileProcessor.integrityCheck.starting"));
							if(!PostFileProcessor .getInstance().process(rp)) {
								rp.setAlwaysOnTop(false);
								JOptionPane.showMessageDialog(null, lang.get("rename.PostFileProcessor.integrityCheck.error"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
								rp.setAlwaysOnTop(true);
								rp.setLogMessage(lang.get("rename.PostFileProcessor.integrityCheck.error"));
							}
							logger.logDEBUG("File Integrity Check Finished");
							rp.setLogMessage(lang.get("rename.PostFileProcessor.integrityCheck.finished"));
							rp.incProcessProgress();
							
							rp.setAlwaysOnTop(false);
							JOptionPane.showMessageDialog(null, lang.get("rename.FileProcessor.finished"), "", JOptionPane.INFORMATION_MESSAGE);
							rp.setAlwaysOnTop(true);
							
							pathTextField.setText("");
							destinationPathTextField.setText("");
							
							tabbedPane.setSelectedIndex(0);
							tabbedPaneEnabled(false, false);
														
							pathSelectButton.setEnabled(true);
							openSourceFileChooserJMenuItem.setEnabled(true);
							destinationPathButton.setEnabled(false);
							openDestinationFileChooserJMenuItem.setEnabled(false);
							subFolderTextField.setEnabled(false);
							fileNameTemplateTextField.setEnabled(false);
							createThumbNailsCheckBox.setEnabled(false);
							startProcessButton.setEnabled(false);
							startProcessJMenuItem.setEnabled(false);
							
							modelPreview.setRowCount(0);
							
							modelMetaData.setRowCount(0);
							modelMetaData.setColumnCount(0);
							
							thumbNailsPanel.removeAll();
						}
						rp.renameProcessFinished();
					}
				};
				renameThread.start();	
			}
		}
	}

	private class JTextFieldListener implements DocumentListener {

	    public void insertUpdate(DocumentEvent e) {
			validateInputInRealtime();
	    }
	    public void removeUpdate(DocumentEvent e) {
			validateInputInRealtime();
	    }
	    public void changedUpdate(DocumentEvent e) {
	    }
	}
		
	private class JTabbedPaneListener implements ChangeListener {

		public void stateChanged(ChangeEvent evt) {
			if(tabbedPane.getSelectedIndex() == 1) {
				updatePreviewTable();
			}
		}
	}
	
	private class CheckBoxListener implements ActionListener {
		public void actionPerformed(ActionEvent e){
			String actionCommand = e.getActionCommand();

			if(actionCommand.equals("createThumbNailsCheckBox")) {
				ApplicationContext.getInstance().setCreateThumbNailsCheckBoxSelected(createThumbNailsCheckBox.isSelected());
			}
		}
	}
}