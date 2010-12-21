package moller.javapeg.program;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.applicationstart.ValidateFileSetup;
import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.categories.CategoryUserObject;
import moller.javapeg.program.categories.CategoryUtil;
import moller.javapeg.program.categories.ImageMetaDataDataBaseHandler;
import moller.javapeg.program.categories.ImageMetaDataDataBaseItem;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.ConfigViewerGUI;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.contexts.ImageMetaDataDataBaseItemsToUpdateContext;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContext;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContextSearchParameters;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContextUtil;
import moller.javapeg.program.enumerations.Action;
import moller.javapeg.program.enumerations.Context;
import moller.javapeg.program.enumerations.MainTabbedPaneComponent;
import moller.javapeg.program.enumerations.MetaDataValueFieldName;
import moller.javapeg.program.gui.ImageSearchResultViewer;
import moller.javapeg.program.gui.ImageViewer;
import moller.javapeg.program.gui.MetaDataValue;
import moller.javapeg.program.gui.MetaDataPanel;
import moller.javapeg.program.gui.MetaDataValueSelectorComplex;
import moller.javapeg.program.gui.MetaDataValueSelectionDialog;
import moller.javapeg.program.gui.StatusPanel;
import moller.javapeg.program.gui.VariablesPanel;
import moller.javapeg.program.gui.checktree.CheckTreeManager;
import moller.javapeg.program.helpviewer.HelpViewerGUI;
import moller.javapeg.program.imagelistformat.ImageList;
import moller.javapeg.program.imagerepository.ImageRepositoryHandler;
import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.javapeg.program.jpeg.JPEGThumbNail;
import moller.javapeg.program.jpeg.JPEGThumbNailCache;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.metadata.MetaDataRetriever;
import moller.javapeg.program.metadata.MetaDataUtil;
import moller.javapeg.program.model.FileModel;
import moller.javapeg.program.model.MetaDataTableModel;
import moller.javapeg.program.model.ModelInstanceLibrary;
import moller.javapeg.program.model.PreviewTableModel;
import moller.javapeg.program.model.SortedListModel;
import moller.javapeg.program.progress.RenameProcess;
import moller.javapeg.program.progress.ThumbNailLoading;
import moller.javapeg.program.rename.RenameProcessContext;
import moller.javapeg.program.rename.ValidatorStatus;
import moller.javapeg.program.rename.process.FileProcessor;
import moller.javapeg.program.rename.process.PostFileProcessor;
import moller.javapeg.program.rename.process.PreFileProcessor;
import moller.javapeg.program.rename.validator.FileAndSubDirectoryTemplate;
import moller.javapeg.program.rename.validator.JPEGTotalPathLength;
import moller.javapeg.program.thumbnailoverview.ThumbNailOverViewCreator;
import moller.javapeg.program.updates.NewVersionChecker;
import moller.javapeg.program.updates.NewVersionGUI;
import moller.util.gui.CustomJOptionPane;
import moller.util.gui.Screen;
import moller.util.gui.Table;
import moller.util.gui.TreeUtil;
import moller.util.gui.Update;
import moller.util.image.ImageUtil;
import moller.util.io.DirectoryUtil;
import moller.util.io.FileUtil;
import moller.util.io.Status;
import moller.util.io.StreamUtil;
import moller.util.jpeg.JPEGScaleAlgorithm;
import moller.util.jpeg.JPEGUtil;
import moller.util.mnemonic.MnemonicConverter;
import moller.util.string.StringUtil;
import moller.util.version.containers.VersionInformation;

public class MainGUI extends JFrame {
	
	private static final long serialVersionUID = 4478711914847747931L;
	
	private static Config config;
	private static Logger logger;
	private static Language lang;

	private JButton destinationPathButton;
	private JButton startProcessButton;
	private JButton removeSelectedImagesButton;
	private JButton removeAllImagesButton;
	private JButton openImageListButton;
	private JButton saveImageListButton;
	private JButton exportImageListButton;
	private JButton moveUpButton;
	private JButton moveDownButton;
	private JButton openImageViewerButton;
	private JButton moveToTopButton;
	private JButton moveToBottomButton;
	private JButton copyImageListdButton;
	private JButton searchImagesButton;
	
	private JLabel destinationPathLabel;
	private JLabel subFolderLabel;
	private JLabel subFolderPreviewTextFieldLabel;
	private JLabel fileNameTemplateLabel;
	private JLabel infoPanelLabel;
	private JLabel amountOfImagesInImageListLabel;
	private JLabel imagePreviewLabel;
	private JLabel imageTagPreviewLabel;
	
	private JTextField destinationPathTextField;
	private JTextField subFolderTextField;
	private JTextField fileNameTemplateTextField;
	private JTextField subFolderNamePreviewTextField;

	private JMenu fileMenu;
	private JMenu helpMenu;
	private JMenu configMenu;

	private JMenuItem configGUIJMenuItem;
	private JMenuItem shutDownProgramJMenuItem;
	private JMenuItem openDestinationFileChooserJMenuItem;
	private JMenuItem startProcessJMenuItem;
	private JMenuItem helpJMenuItem;
	private JMenuItem aboutJMenuItem;
	private JMenuItem popupMenuCopyImageToClipBoardRename;
	private JMenuItem popupMenuCopyImageToClipBoardView;
	private JMenuItem popupMenuCopyImageToClipBoardTag;
	private JMenuItem popupMenuAddImageToViewList;
	private JMenuItem popupMenuAddAllImagesToViewList;
	private JMenuItem popupMenuAddCategory;
	private JMenuItem popupMenuRenameCategory;
	private JMenuItem popupMenuRemoveCategory;
	private JMenuItem popupMenuExpandCategoriesTreeStructure;
	private JMenuItem popupMenuCollapseCategoriesTreeStructure;
		
	private JPopupMenu rightClickMenuCategories;
	private JPopupMenu rightClickMenuRename;
	private JPopupMenu rightClickMenuView;
	private JPopupMenu rightClickMenuTag;

	private JPanel thumbNailsPanel;
	private JPanel imageTagPreviewPanel;
	private JPanel infoPanel;
	private JPanel thumbNailsBackgroundsPanel;
		
	private JSplitPane thumbNailMetaPanelSplitPane;
	private JSplitPane verticalSplitPane;
	private JSplitPane mainSplitPane;
	private JSplitPane previewAndCommentSplitPane;
	private JSplitPane previewCommentCategoriesRatingSplitpane;
	
//	private MetaDataValueSelector cameraModel;
//	private MetaDataValueSelector imageSize;
//	private MetaDataValueSelector iso;
//	private MetaDataValueSelector shutterSpeed;
	
	MetaDataValue imagesSizeMetaDataValue;
	MetaDataValue isoMetaDataValue;
	MetaDataValue shutterSpeedMetaDataValue;
	MetaDataValue apertureValueMetaDataValue;
	MetaDataValue cameraModelMetaDataValue;
	
	private JCheckBox[] ratingCheckBoxes;
	private JTextArea commentTextArea;
	
	private JScrollPane imageTagPreviewScrollPane;
	
	private JMenuBar menuBar;

	private JCheckBox createThumbNailsCheckBox;

	private JTabbedPane tabbedPane;
	private JTabbedPane mainTabbedPane;

	private JTable metaDataTable;
	private JTable previewTable;
		
	private JTree tree;
	
	private Mouselistener mouseListener;
	private MouseButtonListener mouseRightClickButtonListener;
	private CategoriesMouseButtonListener categoriesMouseButtonListener;
	
	private int iconWidth = 160;
	private int columnMargin = 0;
	
	private GridLayout thumbNailGridLayout;
	
	private ThumbNailLoading pb;
	
	private MetaDataTableModel metaDataTableModel;
	private PreviewTableModel previewTableModel;
	
	private StatusPanel statusBar;
	private MetaDataPanel imageMetaDataPanel;
	
	private ThumbNailListener thumbNailListener;
	
	private DefaultListModel imagesToViewListModel = ModelInstanceLibrary.getInstance().getImagesToViewModel();
	private SortedListModel categoriesRepositoryListModel = ModelInstanceLibrary.getInstance().getSortedListModel();
	
	private JList imagesToViewList;
	
	private JTextArea imageCommentTextArea;
	
	private JRadioButton [] ratingRadioButtons;
	private JRadioButton andRadioButton;
	private JRadioButton orRadioButton;
	
	private CheckTreeManager checkTreeManagerForAssignCategroiesCategoryTree;
	private CheckTreeManager checkTreeManagerForFindImagesCategoryTree;
	
	private Thread loadFilesThread;
		
	public MainGUI(){
		
		if(!FileUtil.testWriteAccess(new File(C.USER_HOME))) {
			JOptionPane.showMessageDialog(null, "Can not create files in direcotry: " + C.USER_HOME);
		} 		
		ValidateFileSetup.check();
				
		config =  Config.getInstance();
		logger =  Logger.getInstance();
		lang   =  Language.getInstance();
		
		logger.logDEBUG("JavaPEG is starting");		
		logger.logDEBUG("Language File Loading Started");
		this.readLanguageFile();
		logger.logDEBUG("Language File Loading Finished");
		this.overrideSwingUIProperties();
		if(config.getBooleanProperty("updatechecker.enabled")) {
			logger.logDEBUG("Application Update Check Started");
			this.checkApplicationUpdates();
			logger.logDEBUG("Application Update Check Finished");
		}
		logger.logDEBUG("Image Meta Data Context initialization Started");
		this.initiateImageMetaDataContext();
		logger.logDEBUG("Image Meta Data Context initialization Finished");
		logger.logDEBUG("Creation of Main Frame Started");
		this.createMainFrame();
		logger.logDEBUG("Creation of Main Frame Finished");
		logger.logDEBUG("Creation of Menu Bar Started");
		this.createMenuBar();
		logger.logDEBUG("Creation of Menu Bar Finished");
		this.createToolBar();
		this.createRightClickMenuCategories();
		this.createRightClickMenuRename();
		this.createRightClickMenuView();
		this.createRightClickMenuTag();
		logger.logDEBUG("Adding of Event Listeners Started");
		this.addListeners();
		logger.logDEBUG("Adding of Event Listeners Finished");
		logger.logDEBUG("Application initialization Started");
		this.initiateProgram();
		logger.logDEBUG("Application initialization Finished");
		logger.logDEBUG("Check Available Memory Started");
		this.checkAvailableMemory();
		logger.logDEBUG("Check Available Memory Finished");
		logger.logDEBUG("Application Context initialization Started");
		this.initiateApplicationContext();
		logger.logDEBUG("Application Context initialization Finished");
	}
		
	private void checkApplicationUpdates() {
			
		logger.logDEBUG("Search for Application Updates Started");
		Thread updateCheck = new Thread(){

			public void run(){
				NewVersionChecker nvc = NewVersionChecker.getInstance();
				
				long latestVersion = nvc.newVersionExists(C.VERSION_TIMESTAMP);
				if(latestVersion > C.VERSION_TIMESTAMP) {
					Map<Long, VersionInformation> vim = nvc.getVersionInformation(C.VERSION_TIMESTAMP);
					
					if(vim != null) {
						VersionInformation vi = vim.get(latestVersion);
						
						String changeLog     = nvc.getChangeLog(vim, C.VERSION_TIMESTAMP);
						String downloadURL   = vi.getDownnloadURL();
						String fileName      = vi.getFileName();
						String versionNumber = vi.getVersionNumber();
						int fileSize = vi.getFileSize();
						
						NewVersionGUI nvg = new NewVersionGUI(changeLog, downloadURL, fileName, versionNumber, fileSize);
						nvg.init();
						nvg.setVisible(true);
					}
				}			
				logger.logDEBUG("Search for Application Updates Finished");
			}
		};
		updateCheck.start();
	}
	
	private void checkAvailableMemory() {
		long maxHeapSize = Runtime.getRuntime().maxMemory(); 
				
		if (maxHeapSize < 399572992) {
			logger.logERROR("Maximum Size of Java Heap is to small. Current size is: " + maxHeapSize + " bytes and it must be atleast 399572992 bytes");
			JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.notEnoughMemory"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			closeApplication(1);
		}
	}
	
	// Inläsning av språkfil
	private void readLanguageFile(){
		lang.loadLanguageFile();
	}

	private void overrideSwingUIProperties() {
//		TODO: fix hard coded strings
		UIManager.put("OptionPane.okButtonText", "Ok");
//		TODO: fix hard coded strings
		UIManager.put("OptionPane.cancelButtonText", "Cancel");

	}
	
	private void createMenuBar(){

		// Skapa menyrader i arkiv-menyn
		openDestinationFileChooserJMenuItem = new JMenuItem(lang.get("menu.item.openDestinationFileChooser"));
		openDestinationFileChooserJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.openDestinationFileChooser.accelerator").charAt(0)), ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		
		startProcessJMenuItem = new JMenuItem(lang.get("menu.item.startProcess"));
		startProcessJMenuItem.setToolTipText(lang.get("tooltip.selectSourceDirectoryWithImagesAndDestinationDirectory"));
		startProcessJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.startProcess.accelerator").charAt(0)), ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		startProcessJMenuItem.setEnabled(false);
		
		shutDownProgramJMenuItem = new JMenuItem(lang.get("menu.item.exit"));
		shutDownProgramJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.exit.accelerator").charAt(0)), ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		
		fileMenu = new JMenu(lang.get("menu.file"));
		fileMenu.setMnemonic(lang.get("menu.mnemonic.file").charAt(0));

		fileMenu.add(openDestinationFileChooserJMenuItem);
		fileMenu.add(startProcessJMenuItem);
		fileMenu.add(shutDownProgramJMenuItem);
								
		// Create rows in the Configuration menu
		configGUIJMenuItem = new JMenuItem(lang.get("menu.item.configuration"));
		configGUIJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent('c'), ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));

		configMenu = new JMenu(lang.get("menu.configuration"));
		configMenu.setMnemonic(lang.get("menu.mnemonic.configuration").charAt(0));
		
		configMenu.add(configGUIJMenuItem);
		
		// Skapa menyrader i hjälp-menyn
		helpJMenuItem = new JMenuItem(lang.get("menu.item.programHelp"));
		helpJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, KeyEvent.CTRL_MASK + KeyEvent.ALT_MASK));
		
		aboutJMenuItem = new JMenuItem(lang.get("menu.item.about"));
		aboutJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.item.about.accelerator").charAt(0)), ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
		
		helpMenu = new JMenu(lang.get("menu.help"));
		helpMenu.setMnemonic(lang.get("menu.mnemonic.help").charAt(0));

		helpMenu.add(helpJMenuItem);
		helpMenu.add(aboutJMenuItem);

		menuBar = new JMenuBar();

		menuBar.add(fileMenu);
		menuBar.add(configMenu);
		menuBar.add(helpMenu);

		this.setJMenuBar(menuBar);
	}

	private void createToolBar(){
	}

	private JScrollPane createThumbNailsBackgroundPanel(){
	
		thumbNailGridLayout = new GridLayout(0, 6);
		thumbNailsPanel = new JPanel(thumbNailGridLayout);

		JScrollBar hSB = new JScrollBar(JScrollBar.HORIZONTAL);
		JScrollBar vSB = new JScrollBar(JScrollBar.VERTICAL);

		hSB.setUnitIncrement(40);
		vSB.setUnitIncrement(40);

		JScrollPane scrollpane = new JScrollPane(thumbNailsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setHorizontalScrollBar(hSB);
		scrollpane.setVerticalScrollBar(vSB);
		
		return scrollpane;
	}

	private JPanel createInfoPanel() {

		// Skapa övergripande panel som håller innehåller för övrigt innehåll.
		infoPanel = new JPanel(new BorderLayout());
		infoPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 1, 2)));
		
		infoPanelLabel = new JLabel(lang.get("information.panel.informationLabel"));
		infoPanelLabel.setForeground(Color.GRAY);

		// Skapa en tabbed pane som innehåller tre paneler
		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);

		// Skapa tabellmodeller
    	previewTableModel = ModelInstanceLibrary.getInstance().getPreviewTableModel();
    	metaDataTableModel = ModelInstanceLibrary.getInstance().getMetaDataTableModel();
    	
    	// Skapa tabellen för metadata-informatonen och sätt attribut till den
    	metaDataTable = new JTable(metaDataTableModel);
		metaDataTable.setEnabled(false);
		metaDataTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		metaDataTable.getTableHeader().setReorderingAllowed(false);

		// Skapa tabellen för namnförhandsgranskningen och sätt attribut till den
		previewTable = new JTable(previewTableModel);
		previewTable.setEnabled(false);
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

		subFolderPreviewTextFieldLabel = new JLabel(lang.get("information.panel.subFolderNameLabel"));

		subFolderNamePreviewTextField = new JTextField();
		subFolderNamePreviewTextField.setEditable(false);
		subFolderNamePreviewTextField.setBackground(Color.WHITE);

		JScrollPane scrollpanePreview = new JScrollPane(previewTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpanePreview.setHorizontalScrollBar(phSB);
		scrollpanePreview.setVerticalScrollBar(pvSB);
		
		JPanel previewSubDirectoryPanel = new JPanel(new GridLayout(2, 1));
		previewSubDirectoryPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 1));
		previewSubDirectoryPanel.add(subFolderPreviewTextFieldLabel);
		previewSubDirectoryPanel.add(subFolderNamePreviewTextField);
				
		JPanel previewTablePanel = new JPanel(new BorderLayout());
		previewTablePanel.add(previewSubDirectoryPanel, BorderLayout.NORTH);
		previewTablePanel.add(scrollpanePreview, BorderLayout.CENTER);
		
		tabbedPane.addTab(lang.get("information.panel.metaDataLabel"), scrollpaneMeta);
		tabbedPane.addTab(lang.get("information.panel.previewLabel"), previewTablePanel);
				
		infoPanel.add(infoPanelLabel, BorderLayout.NORTH);
		infoPanel.add(tabbedPane, BorderLayout.CENTER);
		
		return infoPanel;
	}

	// Denna metod anropas när innehållet i textfälten för undermappsnamn
	// eller filnamnmall ändrats.
	private void updatePreviewTable() {
		
		ApplicationContext ac = ApplicationContext.getInstance();
		
		if(ac.getMetaDataObjects().size() > 0 && ac.getDestinationPath().length() > 0) {
	
			ValidatorStatus vs = JPEGTotalPathLength.getInstance().test();
						
			if (vs.isValid()) {
				tabbedPane.setSelectedIndex(1);
				previewTableModel.setRowCount(0);
				previewTableModel.setColumns();
								
				RenameProcessContext rpc = RenameProcessContext.getInstance();
						
				Map<File, File> allJPEGFileNameMappings = rpc.getAllJPEGFileNameMappings();
				
				Set<File> sortedSet = new TreeSet<File>(allJPEGFileNameMappings.keySet());
										
				for (File file : sortedSet) {
					Object[] row = {file.getName(), allJPEGFileNameMappings.get(file).getName()};
					previewTableModel.addRow(row);
				}
	
				Table.packColumns(previewTable, 6);
	
				subFolderNamePreviewTextField.setText(rpc.getSubDirectoryName());
			} else {
				JOptionPane.showMessageDialog(null, vs.getStatusMessage(), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			}
		} else {
			subFolderNamePreviewTextField.setText("");
			previewTableModel.setColumnCount(0);
		}
	}

	private void createMainFrame(){

		this.setTitle("JavaPEG " + C.JAVAPEG_VERSION);

		InputStream imageStream = null;
		ImageIcon titleImageIcon = new ImageIcon();
		try {
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/javapeg.gif");
			titleImageIcon.setImage(ImageIO.read(imageStream));
			this.setIconImage(titleImageIcon.getImage());
		} catch (Exception e) {
			logger.logERROR("Could not open the image javapeg.gif");
		} finally {
			try {
				StreamUtil.closeStream(imageStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.setSize(new Dimension(config.getIntProperty("mainGUI.window.width"), config.getIntProperty("mainGUI.window.height")));

		Point xyFromConfig = new Point(config.getIntProperty("mainGUI.window.location.x"),config.getIntProperty("mainGUI.window.location.y"));
				
		if(Screen.isOnScreen(xyFromConfig)) {
			this.setLocation(xyFromConfig);
		} else {
			this.setLocation(0,0);
			JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			logger.logERROR("Could not set location of Main GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");
		}
		
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
		
		thumbNailListener = new ThumbNailListener();
		mouseRightClickButtonListener = new MouseButtonListener();
		categoriesMouseButtonListener = new CategoriesMouseButtonListener();

		mainSplitPane = new JSplitPane();
		mainSplitPane.setDividerLocation(config.getIntProperty("mainSplitPane.location"));
		mainSplitPane.setOneTouchExpandable(true);
					
		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		verticalSplitPane.setDividerLocation(config.getIntProperty("verticalSplitPane.location"));
		verticalSplitPane.setOneTouchExpandable(true);
				
		thumbNailMetaPanelSplitPane = new JSplitPane();
		thumbNailMetaPanelSplitPane.setDividerLocation(config.getIntProperty("thumbNailMetaDataPanelSplitPane.location"));
		thumbNailMetaPanelSplitPane.setOneTouchExpandable(true);
		thumbNailMetaPanelSplitPane.setDividerSize(10);

		JLabel thumbNailsTitleLable = new JLabel(lang.get("picture.panel.pictureLabel"));
		thumbNailsTitleLable.setForeground(Color.GRAY);
		
		thumbNailsBackgroundsPanel = new JPanel(new BorderLayout());
		thumbNailsBackgroundsPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));
		thumbNailsBackgroundsPanel.add(thumbNailsTitleLable, BorderLayout.NORTH);
		thumbNailsBackgroundsPanel.add(this.createThumbNailsBackgroundPanel(), BorderLayout.CENTER);
		
		mainTabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		mainTabbedPane.addTab(lang.get("tabbedpane.imageRename"), this.createRenamePanel());
//		TODO: Fix hard coded string
		mainTabbedPane.addTab("TAG IMAGES", this.createCategorizePanel());
//		TODO: Fix hard coded string
		mainTabbedPane.addTab("SEARCH / " + lang.get("tabbedpane.imageView")  , this.createViewPanel());
		
		imageMetaDataPanel = new MetaDataPanel();
		thumbNailMetaPanelSplitPane.setLeftComponent(thumbNailsBackgroundsPanel);			
		thumbNailMetaPanelSplitPane.setRightComponent(imageMetaDataPanel);
		
		verticalSplitPane.setTopComponent(mainTabbedPane);
		verticalSplitPane.setBottomComponent(thumbNailMetaPanelSplitPane);
					
		mainSplitPane.setLeftComponent(createTreePanel());
		mainSplitPane.setRightComponent(verticalSplitPane);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.add(mainSplitPane, BorderLayout.CENTER);
			
		boolean [] timerStatus = {false,false,false,false};
		statusBar = new StatusPanel(timerStatus);
		
		this.add(statusBar, BorderLayout.SOUTH);
	}
	
	private JPanel createTreePanel() {
			
		JPanel treePanelBackground = new JPanel(new BorderLayout());
		treePanelBackground.setBorder(BorderFactory.createCompoundBorder((new EtchedBorder(EtchedBorder.LOWERED)), new EmptyBorder(2, 2, 2, 2)));
		
		JLabel inputLabel = new JLabel(lang.get("labels.sourcePath"));
		inputLabel.setForeground(Color.GRAY);
		
		treePanelBackground.add(inputLabel, BorderLayout.NORTH);
		treePanelBackground.add(this.initiateJTree(), BorderLayout.CENTER);
		
		JPanel borderPanel = new JPanel(new BorderLayout());
		borderPanel.setBorder(BorderFactory.createCompoundBorder((new BevelBorder(BevelBorder.LOWERED)), new EmptyBorder(2, 2, 0, 2)));
		borderPanel.add(treePanelBackground, BorderLayout.CENTER);
		
		return borderPanel;
	}
	
	private JPanel createRenamePanel() {
		
		GBHelper posBackgroundPanel = new GBHelper();
		
		JPanel backgroundJPanel = new JPanel(new GridBagLayout());
		backgroundJPanel.setName(MainTabbedPaneComponent.RENAME.toString());
		backgroundJPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		
		backgroundJPanel.add(this.createRenameInputPanel(), posBackgroundPanel.nextCol());
		backgroundJPanel.add(new Gap(2), posBackgroundPanel.nextCol());
		backgroundJPanel.add(this.createInfoPanel(), posBackgroundPanel.nextCol().expandW());
				
		return backgroundJPanel;
	}
	
	private JPanel createViewPanel() {
		
		GBHelper posBackgroundPanel = new GBHelper();
				
		JPanel backgroundJPanel = new JPanel(new GridBagLayout());
		backgroundJPanel.setName(MainTabbedPaneComponent.VIEW.toString());
		backgroundJPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		backgroundJPanel.add(this.createFindImageSection(), posBackgroundPanel.expandH().expandW());
		backgroundJPanel.add(new Gap(2), posBackgroundPanel.nextCol());
		backgroundJPanel.add(this.createViewPanelListSection(), posBackgroundPanel.expandH());
						
		return backgroundJPanel;
	}
	
	private JPanel createCategorizePanel() {
		
		previewCommentCategoriesRatingSplitpane = new JSplitPane();
		
		previewCommentCategoriesRatingSplitpane.setLeftComponent(this.createPreviweAndCommentPanel());
		previewCommentCategoriesRatingSplitpane.setRightComponent(this.createCategoryAndRatingPanel());
		previewCommentCategoriesRatingSplitpane.setDividerLocation(config.getIntProperty("previewCommentCategoriesRatingSplitpane.location"));
		
		GBHelper posBackgroundPanel = new GBHelper();

		JPanel backgroundJPanel = new JPanel(new GridBagLayout());
		backgroundJPanel.setName(MainTabbedPaneComponent.CATEGORIZE.toString());
		backgroundJPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		backgroundJPanel.add(previewCommentCategoriesRatingSplitpane, posBackgroundPanel.expandH().expandW());

		return backgroundJPanel;
	}
	
	private JPanel createViewPanelListSection () {
		
		removeSelectedImagesButton     = new JButton();
		removeAllImagesButton          = new JButton();
		openImageListButton            = new JButton();
		saveImageListButton            = new JButton();
		exportImageListButton          = new JButton();
		moveUpButton                   = new JButton();
		moveDownButton                 = new JButton();
		openImageViewerButton          = new JButton();
		moveToTopButton                = new JButton();
		moveToBottomButton             = new JButton();
		copyImageListdButton = new JButton();
		
		InputStream imageStream = null;
		
		ImageIcon removePictureImageIcon = new ImageIcon();
		ImageIcon removeAllPictureImageIcon = new ImageIcon();
		ImageIcon openImageListImageIcon = new ImageIcon();
		ImageIcon saveImageListImageIcon = new ImageIcon();
		ImageIcon exportImageListImageIcon = new ImageIcon();
		ImageIcon moveUpImageIcon = new ImageIcon();
		ImageIcon moveDownImageIcon = new ImageIcon();
		ImageIcon viewImagesImageIcon = new ImageIcon();
		ImageIcon moveToTopImageIcon = new ImageIcon();
		ImageIcon moveToBottomImageIcon = new ImageIcon();
		ImageIcon copyImageListToClipBoardImageIcon = new ImageIcon();
		
		try {		
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/remove.gif");
			removePictureImageIcon.setImage(ImageIO.read(imageStream));
			removeSelectedImagesButton.setIcon(removePictureImageIcon);
			removeSelectedImagesButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.removeSelectedImages"));
			
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/removeall.gif");
			removeAllPictureImageIcon.setImage(ImageIO.read(imageStream));
			removeAllImagesButton.setIcon(removeAllPictureImageIcon);	
			removeAllImagesButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.removeAllImages"));
			
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/open.gif");
			openImageListImageIcon.setImage(ImageIO.read(imageStream));
			openImageListButton.setIcon(openImageListImageIcon);	
			openImageListButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.openImageList"));
			
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/save.gif");
			saveImageListImageIcon.setImage(ImageIO.read(imageStream));
			saveImageListButton.setIcon(saveImageListImageIcon);	
			saveImageListButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.saveImageList"));
			
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/export.gif");
			exportImageListImageIcon.setImage(ImageIO.read(imageStream));
			exportImageListButton.setIcon(exportImageListImageIcon);	
			exportImageListButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.exportImageList"));
						
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/up.gif");
			moveUpImageIcon.setImage(ImageIO.read(imageStream));
			moveUpButton.setIcon(moveUpImageIcon);	
			moveUpButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.moveUp"));
			
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/down.gif");
			moveDownImageIcon.setImage(ImageIO.read(imageStream));
			moveDownButton.setIcon(moveDownImageIcon);	
			moveDownButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.moveDown"));
			
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/view.gif");
			viewImagesImageIcon.setImage(ImageIO.read(imageStream));
			openImageViewerButton.setIcon(viewImagesImageIcon);	
			openImageViewerButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.viewImages"));

			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/top.gif");
			moveToTopImageIcon.setImage(ImageIO.read(imageStream));
			moveToTopButton.setIcon(moveToTopImageIcon);
			moveToTopButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.moveToTop"));

			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/bottom.gif");
			moveToBottomImageIcon.setImage(ImageIO.read(imageStream));
			moveToBottomButton.setIcon(moveToBottomImageIcon);
			moveToBottomButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.moveToBottom"));
			
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/copy.gif");
			copyImageListToClipBoardImageIcon.setImage(ImageIO.read(imageStream));
			copyImageListdButton.setIcon(copyImageListToClipBoardImageIcon);
			copyImageListdButton.setToolTipText("Copy Images in List to System Clipboard");
		
		} catch (Exception e) {
			logger.logERROR("Could not open the image add.gif");
		} finally {
			try {
				StreamUtil.closeStream(imageStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		imagesToViewList = new JList(imagesToViewListModel);
		imagesToViewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JPanel backgroundPanel = new JPanel(new GridBagLayout());
		backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));
				
		GBHelper posVerticalButtonPanel = new GBHelper();
		
		JPanel verticalButtonPanel = new JPanel(new GridBagLayout());
		verticalButtonPanel.add(removeSelectedImagesButton, posVerticalButtonPanel);
		verticalButtonPanel.add(removeAllImagesButton, posVerticalButtonPanel.nextRow());
		verticalButtonPanel.add(moveToTopButton, posVerticalButtonPanel.nextRow());
		verticalButtonPanel.add(moveUpButton, posVerticalButtonPanel.nextRow());
		verticalButtonPanel.add(moveDownButton, posVerticalButtonPanel.nextRow());
		verticalButtonPanel.add(moveToBottomButton, posVerticalButtonPanel.nextRow());
		
		
		GBHelper posHorisontalButtonPanel = new GBHelper();
		
		JPanel horisontalButtonPanel = new JPanel(new GridBagLayout());
		
		horisontalButtonPanel.add(openImageListButton, posHorisontalButtonPanel);
		horisontalButtonPanel.add(saveImageListButton, posHorisontalButtonPanel.nextCol());
		horisontalButtonPanel.add(exportImageListButton, posHorisontalButtonPanel.nextCol());
		horisontalButtonPanel.add(openImageViewerButton, posHorisontalButtonPanel.nextCol());
		horisontalButtonPanel.add(copyImageListdButton, posHorisontalButtonPanel.nextCol());
		
		GBHelper posBackgroundPanel = new GBHelper();

		JScrollPane spImageList = new JScrollPane(imagesToViewList);

		JLabel imageListLabel = new JLabel(lang.get("maingui.tabbedpane.imagelist.label.list"));
		imageListLabel.setForeground(Color.GRAY);

		amountOfImagesInImageListLabel = new JLabel();
		this.setNrOfImagesLabels();

		JLabel previewLabel = new JLabel(lang.get("maingui.tabbedpane.imagelist.label.preview"));
		previewLabel.setForeground(Color.GRAY);
		
		JPanel previewBackgroundPanel = new JPanel(new GridBagLayout());
		
		GBHelper posPreviewPanel = new GBHelper();
				
		imagePreviewLabel = new JLabel();
				
		JPanel previewPanel = new JPanel();
		previewPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
							
		previewPanel.add(imagePreviewLabel);

		previewBackgroundPanel.add(previewPanel, posPreviewPanel);
		
		JPanel imageListPanel = new JPanel();
		imageListPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));
				
		backgroundPanel.add(imageListLabel, posBackgroundPanel);
		backgroundPanel.add(previewLabel, posBackgroundPanel.nextCol().nextCol().nextCol().nextCol());
		backgroundPanel.add(spImageList, posBackgroundPanel.nextRow().expandH());
		
		backgroundPanel.add(new Gap(3), posBackgroundPanel.nextCol());
		backgroundPanel.add(verticalButtonPanel, posBackgroundPanel.nextCol().align(GridBagConstraints.NORTH));
		backgroundPanel.add(new Gap(3), posBackgroundPanel.nextCol());
		backgroundPanel.add(previewBackgroundPanel, posBackgroundPanel.nextCol().align(GridBagConstraints.NORTH));
		backgroundPanel.add(new Gap(3), posBackgroundPanel.nextRow());
		backgroundPanel.add(horisontalButtonPanel, posBackgroundPanel.nextRow().align(GridBagConstraints.WEST));
		backgroundPanel.add(amountOfImagesInImageListLabel, posBackgroundPanel.nextCol().nextCol().nextCol().nextCol());
		
		return backgroundPanel;
	}
	
	private JPanel createFindImageSection() {
		JPanel backgroundPanel = new JPanel(new GridBagLayout());
		backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));
		
		GBHelper posBackgroundPanel = new GBHelper();

//		TODO: fix hard coded string
		JLabel findInCategoriesLabel = new JLabel("CATEGORIES");
		findInCategoriesLabel.setForeground(Color.GRAY);
		
//		TODO: fix hard coded string
		JLabel findInMetaDataLabel = new JLabel("IMAGE META DATA");
		findInMetaDataLabel.setForeground(Color.GRAY);
		
//		TODO: fix hard coded string
		JLabel findInRatingLabel = new JLabel("RATING");
		findInRatingLabel.setForeground(Color.GRAY);
		
		backgroundPanel.add(findInCategoriesLabel, posBackgroundPanel);
		backgroundPanel.add(new Gap(2), posBackgroundPanel.nextCol());
		backgroundPanel.add(findInMetaDataLabel, posBackgroundPanel.nextCol());
		backgroundPanel.add(new Gap(2), posBackgroundPanel.nextCol());
		backgroundPanel.add(findInRatingLabel, posBackgroundPanel.nextCol());
		backgroundPanel.add(this.createCategoriesPanel(), posBackgroundPanel.nextRow().expandW());
		backgroundPanel.add(new Gap(2), posBackgroundPanel.nextCol());
		backgroundPanel.add(this.createImageMeteDataPanel(), posBackgroundPanel.nextCol());
		backgroundPanel.add(new Gap(2), posBackgroundPanel.nextCol());
		backgroundPanel.add(this.createRatingCommentAndButtonPanel(), posBackgroundPanel.nextCol());
		
		return backgroundPanel;
	}
	
	private JPanel createCategoriesPanel() {
		
//		TODO: Fix hard coded string
		andRadioButton = new JRadioButton("AND");
//		TODO: Fix hard coded string
		andRadioButton.setToolTipText("<html>An image must have all the selected categories<br/>assigned to be added to the search result.</html>");
//		TODO: Fix hard coded string
		orRadioButton = new JRadioButton("OR");
//		TODO: Fix hard coded string
		orRadioButton.setToolTipText("<html>An image may have any combination of the selected<br/>categories assigned to be added to the search result.</html>");
		
		ButtonGroup group = new ButtonGroup();
		
		group.add(andRadioButton);
		group.add(orRadioButton);
		
//		TODO: Make configurable
		orRadioButton.setSelected(true);
		
		JPanel selectionModePanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		
		selectionModePanel.add(andRadioButton);
		selectionModePanel.add(orRadioButton);
		
		JTree categoriesTree = CategoryUtil.createCategoriesTree();
		
		checkTreeManagerForFindImagesCategoryTree = new CheckTreeManager(categoriesTree, false, null, false); 
			
		JScrollPane categoriesScrollPane = new JScrollPane();
		categoriesScrollPane.getViewport().add(categoriesTree);
		
		GBHelper posBackground = new GBHelper();
		JPanel backgroundPanel = new JPanel(new GridBagLayout());
		backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));
		
		posBackground.fill = GridBagConstraints.BOTH;
		
		backgroundPanel.add(categoriesScrollPane, posBackground.expandH().expandW());
		backgroundPanel.add(selectionModePanel, posBackground.nextRow());
		
		return backgroundPanel;
	}
	
	
	private JPanel createImageMeteDataPanel() {
		JPanel backgroundPanel = new JPanel(new GridBagLayout());
		
		backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));
		
		GBHelper posBackgroundPanel = new GBHelper();
		
		ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();
		
//		TODO: fix hard coded string
		MetaDataValueSelectorComplex date = new MetaDataValueSelectorComplex("YEAR", "MONTH", "DAY", true, "");
		date.setFirstValues(imdc.getYears());
		date.setSecondValues(date.initiateContinuousSet(1, 12));
		date.setThirdValues(date.initiateContinuousSet(1, 31));
		
		
//		TODO: fix hard coded string
		MetaDataValueSelectorComplex time = new MetaDataValueSelectorComplex("HOUR", "MINUTE", "SECOND", true, "");
		time.setFirstValues(time.initiateContinuousSet(0, 23));
		time.setSecondValues(time.initiateContinuousSet(0, 59));
		time.setThirdValues(time.initiateContinuousSet(0, 59));
		
//		TODO: fix hard coded string
		JLabel imageSizeLabel = new JLabel("IMAGE SIZE");
//		TODO: fix hard coded string
		JLabel isoLabel = new JLabel("ISO");
//		TODO: fix hard coded string
		JLabel cameraModelLabel = new JLabel("CAMERA MODEL");
//		TODO: fix hard coded string
		JLabel shutterSpeedLabel = new JLabel("SHUTTER SPEED");
//		TODO: fix hard coded string
		JLabel apertureValueLabel = new JLabel("APERTURE VALUE");
		
		MetaDataTextfieldListener mdtl = new MetaDataTextfieldListener();
		
		imagesSizeMetaDataValue = new MetaDataValue(mdtl, MetaDataValueFieldName.IMAGE_SIZE.toString());
		isoMetaDataValue = new MetaDataValue(mdtl, MetaDataValueFieldName.ISO.toString());
		shutterSpeedMetaDataValue = new MetaDataValue(mdtl, MetaDataValueFieldName.SHUTTER_SPEED.toString());
		apertureValueMetaDataValue = new MetaDataValue(mdtl, MetaDataValueFieldName.APERTURE_VALUE.toString());
		cameraModelMetaDataValue = new MetaDataValue(mdtl, MetaDataValueFieldName.CAMERA_MODEL.toString());
		
		backgroundPanel.add(date.getMainPanel(), posBackgroundPanel.nextRow());
		if (date.hasOperators()) {
			backgroundPanel.add(date.getOperatorsPanel(), posBackgroundPanel.nextCol());	
		}
		
		backgroundPanel.add(time.getMainPanel(), posBackgroundPanel.nextRow());
		if (time.hasOperators()) {
			backgroundPanel.add(time.getOperatorsPanel(), posBackgroundPanel.nextCol());	
		}
		
		backgroundPanel.add(imageSizeLabel, posBackgroundPanel.nextRow());
		backgroundPanel.add(imagesSizeMetaDataValue, posBackgroundPanel.nextRow());
		backgroundPanel.add(isoLabel, posBackgroundPanel.nextRow());
		backgroundPanel.add(isoMetaDataValue, posBackgroundPanel.nextRow());
		backgroundPanel.add(shutterSpeedLabel, posBackgroundPanel.nextRow());
		backgroundPanel.add(shutterSpeedMetaDataValue, posBackgroundPanel.nextRow());
		backgroundPanel.add(apertureValueLabel, posBackgroundPanel.nextRow());
		backgroundPanel.add(apertureValueMetaDataValue, posBackgroundPanel.nextRow());
		backgroundPanel.add(cameraModelLabel, posBackgroundPanel.nextRow());
		backgroundPanel.add(cameraModelMetaDataValue, posBackgroundPanel.nextRow());
		
		return backgroundPanel;
	}
	
	private class MetaDataTextfieldListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			MetaDataValueSelectionDialog mdvsd = null;
			
			ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();
			String value = ((JTextField)e.getSource()).getText();
	
			MetaDataValueFieldName mdtf = MetaDataValueFieldName.valueOf(((Component)e.getSource()).getName());
			
			switch (mdtf) {
			case APERTURE_VALUE:
				mdvsd = new MetaDataValueSelectionDialog(mdtf.toString(), new HashSet<Object>(imdc.getApertureValues()), value, e.getLocationOnScreen());
				break;
			case CAMERA_MODEL:
				mdvsd = new MetaDataValueSelectionDialog(mdtf.toString(), new HashSet<Object>(imdc.getCameraModels()), value, e.getLocationOnScreen());
				break;
			case IMAGE_SIZE:
				mdvsd = new MetaDataValueSelectionDialog(mdtf.toString(), new HashSet<Object>(imdc.getImageSizeValues()), value, e.getLocationOnScreen());
				break;
			case ISO:
				mdvsd = new MetaDataValueSelectionDialog(mdtf.toString(), new HashSet<Object>(imdc.getIsoValues()), value, e.getLocationOnScreen());
				break;
			case SHUTTER_SPEED:
				mdvsd = new MetaDataValueSelectionDialog(mdtf.toString(), new HashSet<Object>(imdc.getShutterSpeedValues()), value, e.getLocationOnScreen());
				break;
			}
			mdvsd.collectSelectedValues();
			
			((JTextField)e.getSource()).setText(mdvsd.getResult());
		}
	}
	
	private JPanel createRatingCommentAndButtonPanel() {
		
		GBHelper posRatingPanel = new GBHelper();
		JPanel ratingPanel = new JPanel(new GridBagLayout());
		ratingPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));
		
		ratingCheckBoxes = new JCheckBox[6];
		
		for (int i = 0; i < ratingCheckBoxes.length; i++) {
			if (i == 0) {
				ratingPanel.add(ratingCheckBoxes[i] = new JCheckBox("UNRATED"), posRatingPanel);
			} else {
				ratingPanel.add(ratingCheckBoxes[i] = new JCheckBox(Integer.toString(i)), posRatingPanel.nextCol());
			}
		}

//		TODO: Fix hard coded string
		JLabel commentLabel = new JLabel("COMMENT");
		commentLabel.setForeground(Color.GRAY);
		
		GBHelper posCommentPanel = new GBHelper();
		JPanel commentPanel = new JPanel(new GridBagLayout());
		commentPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));
		
		commentTextArea = new JTextArea();
		commentTextArea.setLineWrap(true);
		commentTextArea.setWrapStyleWord(true);
				
		JScrollPane scrollPane = new JScrollPane(commentTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		commentPanel.add(scrollPane, posCommentPanel.nextRow().expandH().expandW());
		
		JPanel buttonPanel = new JPanel(new BorderLayout());
		
//		TODO: Fix hard coded string
		searchImagesButton = new JButton("SEARCH IMAGES");
		
		buttonPanel.add(searchImagesButton, BorderLayout.EAST);
		
		GBHelper posBackground = new GBHelper();
		JPanel backgroundPanel = new JPanel(new GridBagLayout());
				
		backgroundPanel.add(ratingPanel, posBackground);
		backgroundPanel.add(commentLabel, posBackground.nextRow());
		backgroundPanel.add(commentPanel, posBackground.nextRow().expandH());
		backgroundPanel.add(buttonPanel, posBackground.nextRow());
		
		return backgroundPanel;
	}
	
	private boolean setStartProcessButtonState() {
		
		ApplicationContext ac = ApplicationContext.getInstance();
		
		if (ac.getMetaDataObjects().size() > 0 && ac.getDestinationPath().length() > 0) {
			startProcessButton.setToolTipText(lang.get("tooltip.beginNameChangeProcessButton"));
			startProcessJMenuItem.setToolTipText("");
			return true;
		} else if (ac.getMetaDataObjects().size() > 0) {
			startProcessButton.setToolTipText(lang.get("tooltip.selectDestinationDirectory"));
			startProcessJMenuItem.setToolTipText(lang.get("tooltip.selectDestinationDirectory"));
			return false;
		} else if (ac.getDestinationPath().length() > 0) {
			startProcessButton.setToolTipText(lang.get("tooltip.selectSourceDirectoryWithImages"));
			startProcessJMenuItem.setToolTipText(lang.get("tooltip.selectSourceDirectoryWithImages"));
			return false;
		} else {
			startProcessButton.setToolTipText(lang.get("tooltip.selectSourceDirectoryWithImagesAndDestinationDirectory"));
			startProcessJMenuItem.setToolTipText(lang.get("tooltip.selectSourceDirectoryWithImagesAndDestinationDirectory"));
			return false;
		}
	}
		
	private JPanel createRenameInputPanel() {
			
		createThumbNailsCheckBox = new JCheckBox(lang.get("checkbox.createThumbNails"));
		createThumbNailsCheckBox.setToolTipText(lang.get("tooltip.createThumbNails"));
		createThumbNailsCheckBox.setActionCommand("createThumbNailsCheckBox");
		
		InputStream imageStream = null;
		ImageIcon playPictureImageIcon = new ImageIcon();
		try {
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/play.gif");
			playPictureImageIcon.setImage(ImageIO.read(imageStream));
		} catch (Exception e) {
			logger.logERROR("Could not open the image play.gif");
		} finally {
			try {
				StreamUtil.closeStream(imageStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		startProcessButton = new JButton(playPictureImageIcon);
		startProcessButton.setActionCommand("startProcessButton");
		startProcessButton.setToolTipText(lang.get("tooltip.selectSourceDirectoryWithImagesAndDestinationDirectory"));
		startProcessButton.setPreferredSize(new Dimension(30, 20));
		startProcessButton.setMinimumSize(new Dimension(30, 20));
		startProcessButton.setEnabled(false);
		
		imageStream = null;
		ImageIcon openPictureImageIcon = new ImageIcon();
		try {
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/open.gif");
			openPictureImageIcon.setImage(ImageIO.read(imageStream));
		} catch (Exception e) {
			logger.logERROR("Could not open the image open.gif");
		} finally {
			try {
				StreamUtil.closeStream(imageStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		destinationPathLabel = new JLabel(lang.get("labels.destinatonPath"));
		destinationPathLabel.setForeground(Color.GRAY);

		destinationPathTextField = new JTextField();
		destinationPathTextField.setEditable(false);
		destinationPathTextField.setBackground(Color.WHITE);
		
		destinationPathButton = new JButton(openPictureImageIcon);
		destinationPathButton.setActionCommand("destinationPathButton");
		destinationPathButton.setToolTipText(lang.get("tooltip.destinationPathButton"));
		destinationPathButton.setPreferredSize(new Dimension(30, 20));
		destinationPathButton.setMinimumSize(new Dimension(30, 20));
						
		subFolderLabel = new JLabel(lang.get("labels.subFolderName"));
		subFolderLabel.setForeground(Color.GRAY);

		subFolderTextField = new JTextField();
		subFolderTextField.setEnabled(false);
		subFolderTextField.setToolTipText(lang.get("tooltip.enableTemplateFields"));
				
		fileNameTemplateLabel = new JLabel(lang.get("labels.fileNameTemplate"));
		fileNameTemplateLabel.setForeground(Color.GRAY);
		
		fileNameTemplateTextField = new JTextField();
		fileNameTemplateTextField.setEnabled(false);
		fileNameTemplateTextField.setToolTipText(lang.get("tooltip.enableTemplateFields"));
				
		GBHelper inputPos = new GBHelper();
		JPanel inputPanel = new JPanel(new GridBagLayout());
		inputPanel.add(destinationPathLabel, inputPos);
		inputPanel.add(destinationPathTextField, inputPos.nextRow());
		inputPanel.add(destinationPathButton, inputPos.nextCol());
		inputPanel.add(new Gap(4), inputPos.nextRow());
		inputPanel.add(subFolderLabel, inputPos.nextRow());
		inputPanel.add(subFolderTextField, inputPos.nextRow());
		inputPanel.add(new Gap(4), inputPos.nextRow());
		inputPanel.add(fileNameTemplateLabel, inputPos.nextRow());
		inputPanel.add(fileNameTemplateTextField, inputPos.nextRow());
				
		GBHelper posBackground = new GBHelper();
		JPanel backgroundPanel = new JPanel(new GridBagLayout());
		backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));
		backgroundPanel.add(inputPanel, posBackground.align(GridBagConstraints.NORTHWEST));
		backgroundPanel.add(new Gap(10), posBackground.nextCol());
		backgroundPanel.add(new VariablesPanel(), posBackground.nextCol());
		backgroundPanel.add(new Gap(5), posBackground.nextRow());
		backgroundPanel.add(createThumbNailsCheckBox, inputPos.nextRow().nextCol().nextCol());
		backgroundPanel.add(startProcessButton, inputPos.nextRow().nextCol().nextCol().align(GridBagConstraints.EAST));
						
		return backgroundPanel; 
	}

	private JPanel createCategoryAndRatingPanel() {
		
//		TODO: Fix hard coded string
		JLabel categorizeHeading = new JLabel("CATEGORIES");
		categorizeHeading.setForeground(Color.GRAY);
		
		JTree categoriesTree = CategoryUtil.createCategoriesTree();
		categoriesTree.addMouseListener(categoriesMouseButtonListener);
		
		// makes your tree as CheckTree
		checkTreeManagerForAssignCategroiesCategoryTree = new CheckTreeManager(categoriesTree, false, null, true); 
			
		JScrollPane categoriesScrollPane = new JScrollPane();
		categoriesScrollPane.getViewport().add(categoriesTree);

//		TODO: Fix hard coded string
		JLabel ratingLabel = new JLabel("RATING");
		ratingLabel.setForeground(Color.GRAY);
		
		GBHelper posBackground = new GBHelper();
		JPanel backgroundPanel = new JPanel(new GridBagLayout());
		backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));
		
		posBackground.fill = GridBagConstraints.BOTH;
		
		backgroundPanel.add(categorizeHeading, posBackground);
		backgroundPanel.add(new Gap(2), posBackground.nextRow());
		backgroundPanel.add(categoriesScrollPane, posBackground.nextRow().expandH().expandW());
		backgroundPanel.add(new Gap(2), posBackground.nextRow());
		backgroundPanel.add(ratingLabel,posBackground.nextRow().nextRow());
		
		ratingRadioButtons = new JRadioButton[6];
		ButtonGroup ratingButtonGroup = new ButtonGroup();
		
		JPanel ratingButtonPanel = new JPanel(new GridLayout(1,6));
		
		for (int i = 0; i < ratingRadioButtons.length; i++) {
			JRadioButton jrb = new JRadioButton();
			ratingButtonGroup.add(jrb);
			if (i > 0) {
				jrb.setHorizontalTextPosition(SwingConstants.LEFT);
				jrb.setText(Integer.toString(i));
				
				switch (i) {
				case 1:
//					TODO: Fix hard coded string
					jrb.setToolTipText("Bad");					
					break;
				case 5:
//					TODO: Fix hard coded string
					jrb.setToolTipText("Good");
					break;
				}
				ratingButtonPanel.add(jrb);
			} else {
				// set the "hidden" button to selected as default.
				jrb.setSelected(true);
			}
			ratingRadioButtons[i] = jrb;
		}
		
		backgroundPanel.add(ratingButtonPanel, posBackground.nextRow());
		
		return backgroundPanel;
	}
	
	private JPanel createPreviweAndCommentPanel() {
		
//		TODO: Fix hard coded string
		JLabel previewHeading = new JLabel("PREVIEW");
		previewHeading.setForeground(Color.GRAY);
		
		GBHelper posBackground = new GBHelper();
		
		imageTagPreviewLabel = new JLabel();
				
		imageTagPreviewPanel = new JPanel(new BorderLayout());
		imageTagPreviewPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));			
		imageTagPreviewPanel.add(imageTagPreviewLabel, BorderLayout.CENTER);
		
		imageTagPreviewScrollPane = new JScrollPane(imageTagPreviewPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		JPanel backgroundPanel = new JPanel(new GridBagLayout());
		backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));
		
//		TODO: Fix hard coded string
		JLabel commentHeading = new JLabel("COMMENT");
		commentHeading.setForeground(Color.GRAY);
				
		imageCommentTextArea = new JTextArea();
		imageCommentTextArea.setLineWrap(true);
		imageCommentTextArea.setWrapStyleWord(true);
				
		JScrollPane scrollPane = new JScrollPane(imageCommentTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JPanel topPanel = new JPanel(new GridBagLayout());
		topPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		GBHelper posTop = new GBHelper();
		
		topPanel.add(previewHeading, posTop);
		topPanel.add(new Gap(2), posTop.nextRow());
		topPanel.add(imageTagPreviewScrollPane, posTop.nextRow().expandH().expandW());
		topPanel.add(new Gap(2), posTop.nextRow());
		
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		GBHelper posBottom = new GBHelper();
		
		bottomPanel.add(commentHeading, posBottom);
		bottomPanel.add(new Gap(2), posBottom.nextRow());
		bottomPanel.add(scrollPane, posBottom.nextRow().expandH().expandW());
		
		previewAndCommentSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		previewAndCommentSplitPane.setDividerLocation(config.getIntProperty("previewAndCommentSplitPane.location"));
		previewAndCommentSplitPane.setTopComponent(topPanel);
		previewAndCommentSplitPane.setBottomComponent(bottomPanel);
		
		backgroundPanel.add(previewAndCommentSplitPane, posBackground.expandH().expandW());

		return backgroundPanel;
	}
	
	private JScrollPane initiateJTree() {
		
		tree = new JTree (new FileModel (new Comparator<Object> (){
			public int compare (Object fileAsObjectA, Object fileAsObjectB){
				File fileA = (File) fileAsObjectA;
				File fileB = (File) fileAsObjectB;
				return (fileA.isDirectory () && ! fileB.isDirectory ()) ? -1 : (! fileA.isDirectory () && fileB.isDirectory ()) ? 1 : fileA.getAbsolutePath ().compareToIgnoreCase (fileB.getAbsolutePath ());
			}
		}));
	
		DefaultTreeCellRenderer renderer = 	new DefaultTreeCellRenderer();
    	renderer.setLeafIcon(renderer.getDefaultClosedIcon());
		
		tree.setCellRenderer(renderer);
		tree.setRootVisible (false);
		tree.setShowsRootHandles (true);
		tree.addMouseListener(mouseListener = new Mouselistener());
		
		return new JScrollPane(tree);
	}

	public void addListeners(){
		this.addWindowListener(new WindowDestroyer());
		shutDownProgramJMenuItem.addActionListener(new MenuListener());
		openDestinationFileChooserJMenuItem.addActionListener(new MenuListener());
		startProcessJMenuItem.addActionListener(new MenuListener());
		destinationPathButton.addActionListener(new ButtonListener());
		startProcessButton.addActionListener(new ButtonListener());
		helpJMenuItem.addActionListener(new MenuListener());
		aboutJMenuItem.addActionListener(new MenuListener());
		configGUIJMenuItem.addActionListener(new MenuListener());
		
		subFolderTextField.getDocument().addDocumentListener(new JTextFieldListener());
		fileNameTemplateTextField.getDocument().addDocumentListener(new JTextFieldListener());
		tabbedPane.addChangeListener(new JTabbedPaneListener());
		createThumbNailsCheckBox.addActionListener(new CheckBoxListener());
		thumbNailsBackgroundsPanel.addComponentListener(new ComponentListener());
		
		removeSelectedImagesButton.addActionListener(new RemoveSelectedImagesListener());
		removeAllImagesButton.addActionListener(new RemoveAllImagesListener());
		openImageListButton.addActionListener(new OpenImageListListener());
		saveImageListButton.addActionListener(new SaveImageListListener());
		exportImageListButton.addActionListener(new ExportImageListListener());
		moveUpButton.addActionListener(new MoveImageUpInListListener());
		moveDownButton.addActionListener(new MoveImageDownInListListener());
		moveToTopButton.addActionListener(new MoveImageToTopInListListener());
		moveToBottomButton.addActionListener(new MoveImageToBottomInListListener());
		openImageViewerButton.addActionListener(new OpenImageViewerListener());
		copyImageListdButton.addActionListener(new CopyImageListListener());
		searchImagesButton.addActionListener(new SearchImagesListener());
		
		popupMenuCopyImageToClipBoardRename.addActionListener(new CopyImageToSystemClipBoard());
		popupMenuCopyImageToClipBoardView.addActionListener(new CopyImageToSystemClipBoard());
		popupMenuCopyImageToClipBoardTag.addActionListener(new CopyImageToSystemClipBoard());
		popupMenuAddImageToViewList.addActionListener(new AddImageToViewList());
		popupMenuAddAllImagesToViewList.addActionListener(new AddAllImagesToViewList());
		popupMenuAddCategory.addActionListener(new AddCategory());
		popupMenuRenameCategory.addActionListener(new RenameCategory());
		popupMenuRemoveCategory.addActionListener(new RemoveCategory());
		popupMenuCollapseCategoriesTreeStructure.addActionListener(new CollapseCategoryTreeStructure());
		popupMenuExpandCategoriesTreeStructure.addActionListener(new ExpandCategoryTreeStructure());
				
		imagesToViewList.addListSelectionListener(new ImagesToViewListListener());
//		mainTabbedPane.addChangeListener(new MainTabbedPaneListener());
	}
	
	public void createRightClickMenuCategories() {
		rightClickMenuCategories = new JPopupMenu();
		rightClickMenuCategories.add(popupMenuCollapseCategoriesTreeStructure = new JMenuItem());
		rightClickMenuCategories.add(popupMenuExpandCategoriesTreeStructure = new JMenuItem());
		rightClickMenuCategories.addSeparator();
		rightClickMenuCategories.add(popupMenuAddCategory = new JMenuItem());
		rightClickMenuCategories.add(popupMenuRenameCategory = new JMenuItem());
		rightClickMenuCategories.add(popupMenuRemoveCategory = new JMenuItem());
	}
	
	public void createRightClickMenuRename() {

//		TODO: Fix hard coded string
		popupMenuCopyImageToClipBoardRename = new JMenuItem("Copy to System Clipboard");
		
		rightClickMenuRename = new JPopupMenu();
		rightClickMenuRename.add(popupMenuCopyImageToClipBoardRename);
	}
		
	public void createRightClickMenuView(){
		
		rightClickMenuView = new JPopupMenu();

//		TODO: Fix hard coded string
		popupMenuCopyImageToClipBoardView = new JMenuItem("Copy to System Clipboard");
		popupMenuAddImageToViewList = new JMenuItem(lang.get("maingui.popupmenu.addImageToList"));
		popupMenuAddAllImagesToViewList = new JMenuItem(lang.get("maingui.popupmenu.addAllImagesToList"));
		
		rightClickMenuView.add(popupMenuCopyImageToClipBoardView);
		rightClickMenuView.addSeparator();
		rightClickMenuView.add(popupMenuAddImageToViewList);
		rightClickMenuView.add(popupMenuAddAllImagesToViewList);
	}
	
	public void createRightClickMenuTag() {
		rightClickMenuTag = new JPopupMenu();
		
//		TODO: Fix hard coded string
		popupMenuCopyImageToClipBoardTag = new JMenuItem("Copy to System Clipboard");
		
		rightClickMenuTag.add(popupMenuCopyImageToClipBoardTag);
	}

	public void initiateProgram(){
		
	 	subFolderTextField.setText(config.getStringProperty("subFolderName"));
		fileNameTemplateTextField.setText(config.getStringProperty("fileNameTemplate"));

		if(config.getBooleanProperty("createThumbNailsCheckBox")) {
			createThumbNailsCheckBox.setSelected(true);
		}
		Update.updateAllUIs();		
	}
	
	public void initiateImageMetaDataContext() {
		Object [] repositoryPaths = ImageRepositoryHandler.getInstance().load();
		
		if(repositoryPaths != null) {
			for (int i=0; i<repositoryPaths.length; i++) {
				ImageRepositoryItem iri = new ImageRepositoryItem();
				String directory = (String)repositoryPaths[i];
				
				iri.setPathStatus(DirectoryUtil.getStatus(directory));
				iri.setPath(directory);
				
				switch (iri.getPathStatus()) {
				case EXISTS:
					File imageMetaDataDataBaseFile = new File(directory, C.JAVAPEG_IMAGE_META_NAME);
					if (imageMetaDataDataBaseFile.exists()) {
						ImageMetaDataDataBaseHandler.deserializeImageMetaDataDataBaseFile(imageMetaDataDataBaseFile, Context.IMAGE_META_DATA_CONTEXT);	
					}
					break;
				case NOT_AVAILABLE:
					
					break;

				case DOES_NOT_EXIST:
//					TODO: If configured, remove the directory path automatically
					break;
				}
				
				categoriesRepositoryListModel.add(iri);
			}
		}
	}
	
	public void initiateApplicationContext() {
		ApplicationContext ac = ApplicationContext.getInstance();
		// Disabled to avoid NPE:s with current model of load previous path at application start (Not loading)
//		ac.setSourcePath(config.getStringProperty("sourcePath"));
		ac.setTemplateFileName(config.getStringProperty("fileNameTemplate"));
		ac.setTemplateSubFolderName(config.getStringProperty("subFolderName"));
		ac.setCreateThumbNailsCheckBoxSelected(config.getBooleanProperty("createThumbNailsCheckBox"));
	}

	private void saveSettings(){
		
		config.setStringProperty("sourcePath", ApplicationContext.getInstance().getSourcePath());

		if(!destinationPathTextField.getText().equals(""))
			config.setStringProperty("destinationPath", destinationPathTextField.getText());

		config.setStringProperty("subFolderName", subFolderTextField.getText());
		config.setStringProperty("fileNameTemplate", fileNameTemplateTextField.getText());
		config.setBooleanProperty("createThumbNailsCheckBox", createThumbNailsCheckBox.isSelected());
		config.setIntProperty("mainGUI.window.location.x", this.getLocationOnScreen().x);
		config.setIntProperty("mainGUI.window.location.y", this.getLocationOnScreen().y);
		config.setIntProperty("mainGUI.window.width", this.getSize().width);
		config.setIntProperty("mainGUI.window.height", this.getSize().height);
		config.setIntProperty("mainSplitPane.location", mainSplitPane.getDividerLocation());
		config.setIntProperty("verticalSplitPane.location", verticalSplitPane.getDividerLocation());
		config.setIntProperty("thumbNailMetaDataPanelSplitPane.location", thumbNailMetaPanelSplitPane.getDividerLocation());
		config.setIntProperty("previewAndCommentSplitPane.location", previewAndCommentSplitPane.getDividerLocation());
		config.setIntProperty("previewCommentCategoriesRatingSplitpane.location", previewCommentCategoriesRatingSplitpane.getDividerLocation());
				
		try {
			config.saveSettings();
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
	
	private void saveRepositoryPaths() {
		ImageRepositoryHandler.getInstance().store(categoriesRepositoryListModel.iterator());
	}
		
	private void addThumbnail(JButton thumbNail) {
		thumbNailsPanel.add(thumbNail);
	}
	
	private void updateGUI() {
		thumbNailsPanel.updateUI();
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
	
	private void setInputsEnabled(boolean state) {
		destinationPathButton.setEnabled(state);
		openDestinationFileChooserJMenuItem.setEnabled(state);
		fileNameTemplateTextField.setEnabled(state);
		subFolderTextField.setEnabled(state);
		createThumbNailsCheckBox.setEnabled(state);
		startProcessButton.setEnabled(state);
		startProcessJMenuItem.setEnabled(state);
		tree.setEnabled(state);
	}
	
	private void closeApplication(int exitValue) {
		if(exitValue == 0) {
			saveSettings();
			saveRepositoryPaths();
			storeCurrentlySelectedImageData();
			flushImageMetaDataBaseToDisk();
		}
		logger.logDEBUG("JavePEG was shut down");
		logger.flush();
		System.exit(exitValue);
	}
	
	private int displayConfirmDialog(String message, String label, int type) {
		return JOptionPane.showConfirmDialog(this, message, label, type);
	}
	
	private void displayInformationMessage(String informationMessage) {
//		TODO: fix hard coded string
		JOptionPane.showMessageDialog(this, informationMessage, "Information", JOptionPane.INFORMATION_MESSAGE);	
	}
	
	private void displayErrorMessage(String errorMessage) {
		JOptionPane.showMessageDialog(this, errorMessage, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);	
	}
	
	public String displayInputDialog(String title, String label, String initialValue) {
		return CustomJOptionPane.showInputDialog(this, label, title, initialValue);
	}
			
	// WindowDestroyer
	private class WindowDestroyer extends WindowAdapter{
		public void windowClosing (WindowEvent e){
			closeApplication(0);
		}
	}

	// Menylyssnarklass
	private class MenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String actionCommand = e.getActionCommand();

			if(actionCommand.equals(lang.get("menu.item.exit"))){
				closeApplication(0);
			} else if(actionCommand.equals(lang.get("menu.item.openDestinationFileChooser"))) {
				destinationPathButton.doClick();
			} else if(actionCommand.equals(lang.get("menu.item.startProcess"))) {
				startProcessButton.doClick();
			} else if (actionCommand.equals(lang.get("menu.item.about")))	{
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
	    	} else if (actionCommand.equals(lang.get("menu.item.programHelp"))) {
				new HelpViewerGUI().setVisible(true);				
			} else if (actionCommand.equals(lang.get("menu.item.configuration"))) {
				new ConfigViewerGUI().setVisible(true);				
			}
		}
	}

	// Knapplyssnarklass
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String actionCommand = e.getActionCommand();
			
			if(actionCommand.equals("destinationPathButton")) {
				destinationPathTextField.setEditable(true);

				/**
			     * Kontrollera så att sparad sökväg fortfarande existerar
			     * och i annat fall hoppa upp ett steg i trädstrukturen och
			     * kontrollera ifall den sökvägen existerar
			     **/
				File tempFile = new File(config.getStringProperty("destinationPath"));

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
				int returnVal = chooser.showOpenDialog(MainGUI.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					String temp = chooser.getSelectedFile().getAbsolutePath();
					
					char [] tempArray = temp.toCharArray();

					for(int i = 0; i < tempArray.length; i++) {
						if((int)tempArray[i] == 92) {
							tempArray[i] = '/';
						}
					}
					
					if (!ApplicationContext.getInstance().getSourcePath().equals(String.valueOf(tempArray))) {
						
						ApplicationContext.getInstance().setDestinationPath(String.valueOf(tempArray));
						destinationPathTextField.setText(String.valueOf(tempArray));
						config.setStringProperty("destinationPath", destinationPathTextField.getText());
						
						subFolderTextField.setEnabled(true);
						subFolderTextField.setToolTipText(lang.get("tooltip.subFolderName"));
						
						fileNameTemplateTextField.setEnabled(true);
						fileNameTemplateTextField.setToolTipText(lang.get("tooltip.fileNameTemplate"));
							
						destinationPathTextField.setEnabled(true);
						destinationPathTextField.setEditable(false);
						
						startProcessButton.setEnabled(setStartProcessButtonState());
						startProcessJMenuItem.setEnabled(setStartProcessButtonState());
					} else {
						JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.sameSourceAndDestination"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
					}	
				}
			}
			
			if(actionCommand.equals("startProcessButton")){
				if(ApplicationContext.getInstance().isImageViewerDisplayed()) {
					JOptionPane.showMessageDialog(null, lang.get("errormessage.jpgrename.imageViewerMustBeClosed"), lang.get("errormessage.maingui.informationMessageLabel"), JOptionPane.INFORMATION_MESSAGE);
				} else {
					removeMouseListener();
					setInputsEnabled(false);		

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

							RenameProcess rp = new RenameProcess();
							rp.init();
							rp.setVisible(true);

							logger.logDEBUG("Pre File Processing Started");
							rp.setLogMessage(lang.get("rename.PreFileProcessor.starting"));
							ValidatorStatus vs = PreFileProcessor.getInstance().startTest(rp);
							logger.logDEBUG("Pre File Processing Finished");
							rp.setLogMessage(lang.get("rename.PreFileProcessor.finished"));

							if(!vs.isValid()) {
								rp.setAlwaysOnTop(false);
								JOptionPane.showMessageDialog(null, vs.getStatusMessage(), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
								logger.logERROR("Pre File Processing found following errors:\n" + vs.getStatusMessage());
								rp.setLogMessage(lang.get("rename.PreFileProcessor.error")+ "\n" + vs.getStatusMessage());
								rp.setAlwaysOnTop(true);
								setInputsEnabled(true);
								addMouseListener();
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

								setInputsEnabled(true);
								addMouseListener();
							}
							rp.renameProcessFinished();
						}
					};
					renameThread.start();	
				}
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
	
	private void prepareLoadThumbnailsProcess() {
		this.removeMouseListener();
		this.setInputsEnabled(false);
		
		ApplicationContext ac = ApplicationContext.getInstance();
		ac.clearMetaDataObjects();
		
		thumbNailsPanel.removeAll();
		thumbNailsPanel.updateUI();
		
		// Rensa en eventuellt ifylld filnamnsförhandsgranskningstabell.
		// Detta kan ske då det redan öppnats bilder tidigare och dessa
		// fått förhandsgranskning på sina filnamn
		previewTableModel.setRowCount(0);
						
		// Clear the Panel with meta data from potentially already 
		// shown meta data
		imageMetaDataPanel.clearMetaData();
		
		metaDataTableModel.setColumnCount(0);
		metaDataTableModel.setRowCount(0);
	}
	
	private void executeLoadThumbnailsProcess() {
		Thread thumbNailsFetcher = new Thread() {
			
			public void run(){
				boolean bufferContainsImages = true;
				while (loadFilesThread.isAlive() || bufferContainsImages) {
					
					final File jpegFile = ApplicationContext.getInstance().handleJpegFileLoadBuffer(null, Action.RETRIEVE);
					
					if(jpegFile != null) {
									
						JPEGThumbNail tn =	JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(jpegFile);
		
						JButton thumbContainer = new JButton();
						thumbContainer.setIcon(new ImageIcon(tn.getThumbNailData()));
						thumbContainer.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
						if (!config.getStringProperty("thumbnails.tooltip.state").equals("0")) {
							thumbContainer.setToolTipText(MetaDataUtil.getToolTipText(jpegFile));	
						}
						thumbContainer.setActionCommand(jpegFile.getAbsolutePath());
						thumbContainer.addActionListener(thumbNailListener);
						thumbContainer.addMouseListener(mouseRightClickButtonListener);
		
						columnMargin = thumbContainer.getBorder().getBorderInsets(thumbContainer).left;
						columnMargin += thumbContainer.getBorder().getBorderInsets(thumbContainer).right;
		
						int width = thumbContainer.getIcon().getIconWidth();
		
						if (width > iconWidth) {
							iconWidth = width;
						}
		
						addThumbnail(thumbContainer);
						
						MetaData metaData = MetaDataRetriever.getMetaData(jpegFile);

						metaDataTableModel.addTableRow(metaData);
						ApplicationContext.getInstance().addMetaDataObject(metaData);
						setStatusMessages();
						updateGUI();
						pb.updateProgressBar();							
					} else if (!loadFilesThread.isAlive()){
						bufferContainsImages = false;
					}
										
					try {
						if (loadFilesThread.isAlive()) {
							Thread.sleep(10);
						}
					} catch (InterruptedException e1) {
					}
				}
				pb.dispose();
				addMouseListener();
				setInputsEnabled(true);
				startProcessButton.setEnabled(setStartProcessButtonState());
				startProcessJMenuItem.setEnabled(setStartProcessButtonState());

				Table.packColumns(metaDataTable, 6);	
			}
		};
		thumbNailsFetcher.start();
		setStatusMessages();
	}
	
	private void loadThumbNails(final File sourcePath) {
		this.prepareLoadThumbnailsProcess();
		
		String sourcePathString = sourcePath.getAbsolutePath();
		
		ApplicationContext.getInstance().setSourcePath(sourcePathString);
		
		config.setStringProperty("sourcePath", sourcePathString);
		statusBar.setStatusMessage(lang.get("statusbar.message.selectedPath") + " " + sourcePathString, lang.get("statusbar.message.selectedPath"), 0);
				
		loadFilesThread = new Thread() {
			public void run() {
				if(sourcePath.isDirectory()) {
					try {
						FileRetriever.getInstance().loadFilesFromDisk(Arrays.asList(sourcePath.listFiles()));
					} catch (Throwable sex) {
						logger.logERROR("Can not list files in directory: " + sourcePath.getAbsolutePath());
						logger.logERROR(sex);
//						TODO: Hard coded string
						JOptionPane.showMessageDialog(null, "Can not list files in directory: " + sourcePath.getAbsolutePath(), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
					}	
				}
			}
		};
		loadFilesThread.start();
		
		try {
			int max = 0;
			if (sourcePath.listFiles() != null) {
				max = sourcePath.listFiles().length;
			}
			pb = new ThumbNailLoading(0, max, this);
			pb.setVisible(max > 0);
		} catch (Throwable th) {			
		}
		
		metaDataTableModel.setColumns();
		
		this.executeLoadThumbnailsProcess();
				
		// Byta till metadata-tabben ifall tabben skulle stå i annat läge.
		tabbedPane.setSelectedIndex(0);	
	}
		
	/**
	 * Mouse listener
	 */
	private class Mouselistener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			int selRow = tree.getRowForLocation(e.getX(), e.getY());
						
			if(selRow != -1) {
				
				Object [] path = tree.getPathForLocation(e.getX(), e.getY()).getPath();
				String totalPath = "";
				for(int i=0; i<path.length; i++){
					if(i==0 || i==1 || i==path.length-1){
						totalPath = totalPath + path[i].toString();
					} else{
						totalPath = totalPath + path[i].toString() + "\\";
					}
				}
				
				ApplicationContext ac = ApplicationContext.getInstance();
				if(!ac.getSourcePath().equals(totalPath)) {
					ImageMetaDataDataBaseItemsToUpdateContext imddbituc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();
					if(imddbituc.getLoadedRepositoryPath() != null) {
						storeCurrentlySelectedImageData();
						flushImageMetaDataBaseToDisk();
						clearTagTab();
					}
					
					File repositoryPath = new File(totalPath);
					
					int nrOfJPEGFilesInRepositoryPath = -1;
					
					try {
						nrOfJPEGFilesInRepositoryPath = JPEGUtil.getJPEGFiles(repositoryPath).size();
					} catch (FileNotFoundException fnfex) {
						nrOfJPEGFilesInRepositoryPath = 0;
						logger.logDEBUG("Problem with determining nr of JPEG files in directory: " + totalPath);
						logger.logDEBUG(fnfex);
					} catch (IOException ioex) {
						nrOfJPEGFilesInRepositoryPath = 0;
						logger.logDEBUG("Problem with determining nr of JPEG files in directory: " + totalPath);
						logger.logDEBUG(ioex);
					}
					
					if (nrOfJPEGFilesInRepositoryPath > 0) {
						ImageMetaDataDataBaseHandler.initiateDataBase(repositoryPath);
						imddbituc.setRepositoryPath(repositoryPath);
						
						// Load thumb nails for all JPEG images that exists in the 
						// selected path.
						loadThumbNails(new File(totalPath));
												
						// Populate the image repository model with any 
						// unpopulated paths.
						ImageRepositoryItem iri = new ImageRepositoryItem(totalPath, Status.EXISTS);
						
						if(!categoriesRepositoryListModel.contains(iri)) {
							categoriesRepositoryListModel.add(iri);
						}
					}
				}
			}	
		}
	}

	private void removeMouseListener(){
		tree.removeMouseListener(mouseListener);
	}
	
	private void addMouseListener(){
		tree.addMouseListener(mouseListener);
	}
	
	private void setNrOfImagesLabels () {
		amountOfImagesInImageListLabel.setText(lang.get("maingui.tabbedpane.imagelist.label.numberOfImagesInList") + " " + Integer.toString(imagesToViewListModel.size()));
	}
	
	private void setStatusMessages() {
		int nrOfImages =  FileRetriever.getInstance().handleNrOfJpegImages(Action.RETRIEVE);
		
		if (nrOfImages > 0) {
			int nrOfColumns = thumbNailGridLayout.getColumns();
			
			statusBar.setStatusMessage(Integer.toString(nrOfColumns), lang.get("statusbar.message.amountOfColumns"), 1);
			
			int extraRow = nrOfImages % nrOfColumns == 0 ? 0 : 1;
			int rowsInGridLayout = (nrOfImages / nrOfColumns) + extraRow; 

			statusBar.setStatusMessage(Integer.toString(rowsInGridLayout), lang.get("statusbar.message.amountOfRows"), 2);
			statusBar.setStatusMessage(Integer.toString(nrOfImages), lang.get("statusbar.message.amountOfImagesInDirectory"), 3);
		} else {
			statusBar.setStatusMessage("0", lang.get("statusbar.message.amountOfColumns"), 1);
			statusBar.setStatusMessage("0", lang.get("statusbar.message.amountOfRows"), 2);
			statusBar.setStatusMessage("0", lang.get("statusbar.message.amountOfImagesInDirectory"), 3);
		}
	}
		
	private class ComponentListener extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			if (((thumbNailsPanel.getVisibleRect().width - (columnMargin * thumbNailGridLayout.getColumns())) / iconWidth) != thumbNailGridLayout.getColumns()) {
				
				int columns = (thumbNailsPanel.getVisibleRect().width - ((thumbNailGridLayout.getHgap() * thumbNailGridLayout.getColumns()) + columnMargin * thumbNailGridLayout.getColumns())) / iconWidth;
				
				thumbNailGridLayout.setColumns(columns > 0 ? columns : 1);
				thumbNailsPanel.invalidate();
				thumbNailsPanel.repaint();
				thumbNailsPanel.updateUI();
				setStatusMessages();
			}				
		}
	}
	
	private class ThumbNailListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			File jpegImage = new File(e.getActionCommand());
			
			imageMetaDataPanel.setMetaData(jpegImage);
			
			MainTabbedPaneComponent selectedMainTabbedPaneComponent = MainTabbedPaneComponent.valueOf(((JPanel)mainTabbedPane.getSelectedComponent()).getName());
			
			if(selectedMainTabbedPaneComponent == MainTabbedPaneComponent.CATEGORIZE) {
//				TODO: Fix -10 workaround to something generic
				int width = imageTagPreviewScrollPane.getViewportBorderBounds().width - 10;
				int height = imageTagPreviewScrollPane.getViewportBorderBounds().height - 10;
								
				try {
					Image scaledImage = null;
					JPEGThumbNail thumbnail = null;
					
					if(config.getBooleanProperty("tab.tagImage.previewImage.useEmbeddedThumbnail")) {
						JPEGThumbNailCache jtc = JPEGThumbNailCache.getInstance();
						thumbnail = jtc.get(jpegImage);
					} 
					
					if (thumbnail != null) {
						Icon thumbNailIcon = new ImageIcon(thumbnail.getThumbNailData());
						
						if (thumbNailIcon.getIconHeight() > height || thumbNailIcon.getIconWidth() > width) {
							thumbNailIcon = null;
							
							scaledImage = ImageUtil.createThumbNailAdaptedToAvailableSpace(thumbnail.getThumbNailData(), width, height, JPEGScaleAlgorithm.SMOOTH);
							imageTagPreviewLabel.setIcon(new ImageIcon(scaledImage));
						} else {
							imageTagPreviewLabel.setIcon(new ImageIcon(thumbnail.getThumbNailData()));
						}
							
					} else {
						scaledImage = ImageUtil.createThumbNailAdaptedToAvailableSpace(jpegImage, width, height, JPEGScaleAlgorithm.SMOOTH);
						imageTagPreviewLabel.setIcon(new ImageIcon(scaledImage));
					}
					
					storeCurrentlySelectedImageData();
					
					ImageMetaDataDataBaseItemsToUpdateContext irc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();
					
					// Load the selected image
					irc.setCurrentlySelectedImage(jpegImage);
					ImageMetaDataDataBaseItem imageMetaDataDataBaseItem = irc.getImageMetaDataBaseItem(jpegImage);
					
					imageCommentTextArea.setText(imageMetaDataDataBaseItem.getComment());
					setRatingValue(imageMetaDataDataBaseItem.getRating());
					setCategories(imageMetaDataDataBaseItem.getCategories());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * This method set the correct JRadioButton to selected according to the
	 * integer ratingValue parameter. If the ratingValue parameter has an 
	 * invalid value then no JRadioButton will be selected. This can happen if
	 * no rating has been set or if the current set ratingValue is incorrect.
	 *  
	 * @param ratingValue points out which JRadioButton in the Array of 
	 *        JRadioButtons to select.
	 */
	private void setRatingValue(int ratingValue) {
		// if value of ratingValue is valid, then select the correct JRadioButton
		if (ratingValue < ratingRadioButtons.length) {
			ratingRadioButtons[ratingValue].setSelected(true);
		} else {
			ratingRadioButtons[0].setSelected(true);
		}
	}
	
	/**
	 * Get the currently set ratingValue or 0 if no rating value has been set.
	 * 
	 * @return the currently set rating value or 0.
	 */
	private int getRatingValue() {
		for (int i = 0; i < ratingRadioButtons.length; i++) {
			if (ratingRadioButtons[i].isSelected()) {
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * @param categories
	 */
	@SuppressWarnings("unchecked")
	private void setCategories(Categories categories) {
		
		checkTreeManagerForAssignCategroiesCategoryTree.getSelectionModel().clearSelection();
		
		if (categories.size() > 0) {
			DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategroiesCategoryTree.getTreeModel();
			Enumeration<DefaultMutableTreeNode> elements = ((DefaultMutableTreeNode)model.getRoot()).preorderEnumeration();
			
			List<TreePath> treePaths = new ArrayList<TreePath>();
			
			while (elements.hasMoreElements()) {
				DefaultMutableTreeNode element = elements.nextElement();
				
				CategoryUserObject cuo = ((CategoryUserObject)element.getUserObject()); 
				String id = cuo.getIdentity();
				
				if (categories.contains(id)) {
					treePaths.add(new TreePath(model.getPathToRoot(element)));
				}
			}
			if (treePaths.size() > 0) {
				checkTreeManagerForAssignCategroiesCategoryTree.getSelectionModel().setSelectionPaths(treePaths.toArray(new TreePath[treePaths.size()]));	
			}
		}
	}
	
	/**
	 * @return
	 */
	private Categories getSelectedCategoriesFromTreeModel(CheckTreeManager checkTreeManager) {
		Categories selectedId = new Categories();;
		
		// to get the paths that were checked
		TreePath checkedPaths[] = checkTreeManager.getSelectionModel().getSelectionPaths();
		
		if (checkedPaths != null  && checkedPaths.length > 0 ) {
			for (TreePath checkedPath : checkedPaths) {

				Object[] defaultMutableTreeNodes = checkedPath.getPath();
				Object leafNode = defaultMutableTreeNodes[defaultMutableTreeNodes.length-1];
				
				if (leafNode instanceof DefaultMutableTreeNode) {
					String id = ((CategoryUserObject)((DefaultMutableTreeNode)leafNode).getUserObject()).getIdentity();
					if (StringUtil.isInt(id) && Integer.parseInt(id) > -1) {
						selectedId.addCategory(id);
					}
				}
			}	
		}
		return selectedId;
	}
	
	private void storeCurrentlySelectedImageData() {
		ImageMetaDataDataBaseItemsToUpdateContext irc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();
		File currentlySelectedImage = irc.getCurrentlySelectedImage();
		
		// Store changes to the currently loaded image.
		if(currentlySelectedImage != null) {
			ImageMetaDataDataBaseItem imageMetaDataDataBaseItem = null;
			
			imageMetaDataDataBaseItem = irc.getImageMetaDataBaseItem(currentlySelectedImage);
			imageMetaDataDataBaseItem.setComment(imageCommentTextArea.getText());
			imageMetaDataDataBaseItem.setRating(getRatingValue());
			imageMetaDataDataBaseItem.setCategories(getSelectedCategoriesFromTreeModel(checkTreeManagerForAssignCategroiesCategoryTree));
			
			irc.setImageMetaDatadataBaseItem(currentlySelectedImage, imageMetaDataDataBaseItem);
		}
	}
	
	private class RemoveSelectedImagesListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			if (!imagesToViewList.isSelectionEmpty()) {
				
				int [] selectedIndices = imagesToViewList.getSelectedIndices();
								
				// Remove all the selected indices
				while(selectedIndices.length > 0) {
					imagesToViewListModel.remove(selectedIndices[0]);
					selectedIndices = imagesToViewList.getSelectedIndices();
				}
				imagePreviewLabel.setIcon(null);
				setNrOfImagesLabels();
			}
		}
	}
	
	private class RemoveAllImagesListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (imagesToViewListModel.size() > 0) {
				imagesToViewListModel.clear();
				imagePreviewLabel.setIcon(null);
				setNrOfImagesLabels();
			}
		}
	}
	
	private class OpenImageListListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			FileNameExtensionFilter fileFilterPolyView = new FileNameExtensionFilter("JavaPEG Image List", "jil");
			
			JFileChooser chooser = new JFileChooser();
							
			chooser.setAcceptAllFileFilterUsed(false);
			chooser.setDialogTitle(lang.get("maingui.tabbedpane.imagelist.filechooser.openImageList.title"));
			chooser.addChoosableFileFilter(fileFilterPolyView);
							
			File source = null;	
			
			int returnVal = chooser.showOpenDialog(null);
		    if(returnVal == JFileChooser.APPROVE_OPTION) {
		    	
		    	if(!imagesToViewListModel.isEmpty()) {
		    		int returnValue = JOptionPane.showConfirmDialog(null, lang.get("maingui.tabbedpane.imagelist.filechooser.openImageList.nonSavedImageListMessage"));
					
					/**
					 * 0 indicates a yes answer, and then the current list 
					 * shall be overwritten, otherwise just return.
					 */ 
					if(returnValue != 0) {
						return;
					}	
		    	}
		    			    	
		    	source = chooser.getSelectedFile();
		    	
		    	try {
					List<String> fileContent = FileUtil.readFromFile(source);
					
					imagesToViewListModel.clear();
					
					List<String> notExistingFiles = new ArrayList<String>();
									
					for(String filePath : fileContent) {
						File file = new File(filePath);
						
						if(file.exists()) {
							imagesToViewListModel.addElement(file);
						} else {
							notExistingFiles.add(filePath);
						}
					}
					
					if(!notExistingFiles.isEmpty()) {		
						StringBuilder missingFilesErrorMessage = new StringBuilder();
						
						String lS = System.getProperty("line.separator");

						missingFilesErrorMessage.append(lang.get("maingui.tabbedpane.imagelist.filechooser.openImageList.missingFilesErrorMessage"));
						missingFilesErrorMessage.append(lS);
						missingFilesErrorMessage.append(lS);
													
						for(String path : notExistingFiles) {
							missingFilesErrorMessage.append(path);
							missingFilesErrorMessage.append(lS);
						}
													
						JOptionPane.showMessageDialog(null, missingFilesErrorMessage, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
					}
					setNrOfImagesLabels();
				} catch (IOException ioe) {
					JOptionPane.showMessageDialog(null, lang.get("maingui.tabbedpane.imagelist.filechooser.openImageList.couldNotReadFile") + "\n(" + source.getAbsolutePath() + ")", lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
					logger.logERROR("Could not read from file:");
					logger.logERROR(ioe);
				}
		    }
		}
	}
	
	private class SaveImageListListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			if (imagesToViewListModel.size() > 0) {
				
				FileNameExtensionFilter fileFilterPolyView = new FileNameExtensionFilter("JavaPEG Image List", "jil");
				
				JFileChooser chooser = new JFileChooser();
								
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setDialogTitle(lang.get("maingui.tabbedpane.imagelist.filechooser.saveImageList.title"));
				chooser.addChoosableFileFilter(fileFilterPolyView);
								
				File destination = null;	
				
				int returnVal = chooser.showSaveDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	destination = chooser.getSelectedFile();			    				    	
			    	ImageList.getInstance().createList(imagesToViewListModel, destination, "jil", "JavaPEG Image List");
			    }
			}
		}
	}
	
	private class MoveImageUpInListListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int selecteIndex = imagesToViewList.getSelectedIndex();
			
			if(selecteIndex > -1) {
				if(selecteIndex > 0) {
					Object obj = imagesToViewListModel.remove(selecteIndex);
					imagesToViewListModel.add(selecteIndex - 1, obj);
					imagesToViewList.setSelectedIndex(selecteIndex - 1);
				}
			}
		}
	}
	
	private class MoveImageDownInListListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int selecteIndex = imagesToViewList.getSelectedIndex();
			
			if(selecteIndex > -1) {
				if(selecteIndex < (imagesToViewListModel.size() - 1)) {
					Object obj = imagesToViewListModel.remove(selecteIndex);
					imagesToViewListModel.add(selecteIndex + 1, obj);
					imagesToViewList.setSelectedIndex(selecteIndex + 1);
				}
			}			
		}
	}
	
	private class MoveImageToTopInListListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int selecteIndex = imagesToViewList.getSelectedIndex();
			
			if (selecteIndex > 0) {
				Object obj = imagesToViewListModel.remove(selecteIndex);
				imagesToViewListModel.add(0, obj);
				imagesToViewList.setSelectedIndex(0);
			}	
		}
	}
	
	private class MoveImageToBottomInListListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int selecteIndex = imagesToViewList.getSelectedIndex();
			
			if (selecteIndex > -1) {
				Object obj = imagesToViewListModel.remove(selecteIndex);
				imagesToViewListModel.addElement(obj);
				imagesToViewList.setSelectedIndex(imagesToViewListModel.size() - 1);
			}	
		}
	}
	
	private class OpenImageViewerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!imagesToViewListModel.isEmpty()) {
				
				ApplicationContext ac = ApplicationContext.getInstance();
				if (ac.isImageViewerDisplayed()) {
					JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.onlyOneImageViewer"), lang.get("errormessage.maingui.informationMessageLabel"), JOptionPane.INFORMATION_MESSAGE);
				} else {
					List<File> imagesToView = new ArrayList<File>();

					for (int i = 0; i < imagesToViewListModel.size(); i++) {
						imagesToView.add((File)imagesToViewListModel.get(i));
					}

					ImageViewer imageViewer = new ImageViewer(imagesToView);
					imageViewer.setVisible(true);
					ac.setImageViewerDisplayed(true);
				}
			}
		}
	}
	
	private class CopyImageListListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(!imagesToViewListModel.isEmpty()) {
				List<File> imagesToSetToSystemClipBoard = new ArrayList<File>();

				for (int i = 0; i < imagesToViewListModel.size(); i++) {
					imagesToSetToSystemClipBoard.add((File)imagesToViewListModel.get(i));
				}
				FileSelection fileSelection = new FileSelection(imagesToSetToSystemClipBoard);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
			}
		}
	}
	
	private class SearchImagesListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ImageMetaDataContextSearchParameters imageMetaDataContextSearchParameters = collectSearchParameters();
			
			Set<File> foundImages = ImageMetaDataContextUtil.performImageSearch(imageMetaDataContextSearchParameters);
			
			if (foundImages.size() > 0) {
				ImageSearchResultViewer imagesearchResultViewer = new ImageSearchResultViewer(foundImages);
				imagesearchResultViewer.setVisible(true);
			} else {
//				TODO: fix hard coded string
				displayInformationMessage("No images found");
			}
		}
	}
			
	private class ExportImageListListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			if (imagesToViewListModel.size() > 0) {
				
				FileNameExtensionFilter fileFilterIrfanView = new FileNameExtensionFilter("IrfanView", "txt");
				FileNameExtensionFilter fileFilterPolyView  = new FileNameExtensionFilter("PolyView" , "pvs");
				FileNameExtensionFilter fileFilterXnView    = new FileNameExtensionFilter("XnView"   , "sld");
								
				JFileChooser chooser = new JFileChooser();
								
				chooser.setAcceptAllFileFilterUsed(false);
				chooser.setDialogTitle(lang.get("maingui.tabbedpane.imagelist.filechooser.exportImageList.title"));
				chooser.addChoosableFileFilter(fileFilterIrfanView);
				chooser.addChoosableFileFilter(fileFilterPolyView);
				chooser.addChoosableFileFilter(fileFilterXnView);
												
				File destination = null;	
				
				int returnVal = chooser.showSaveDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	destination = chooser.getSelectedFile();
			    				    
			    	String listFormat      = ((FileNameExtensionFilter)chooser.getFileFilter()).getExtensions()[0];
			    	String listDescription = ((FileNameExtensionFilter)chooser.getFileFilter()).getDescription();
										
			    	ImageList.getInstance().createList(imagesToViewListModel, destination, listFormat, listDescription);
			    }
			}
		}
	}
	
	private class AddImageToViewList implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			imagesToViewListModel.addElement(new File(e.getActionCommand()));
			setNrOfImagesLabels();
		}
	}
	
	private class CopyImageToSystemClipBoard implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<File> selectedFiles = new ArrayList<File>();
			selectedFiles.add(new File(e.getActionCommand()));

			FileSelection fileSelection = new FileSelection(selectedFiles);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
		}
	}
	
	private class AddAllImagesToViewList implements ActionListener {
		public void actionPerformed(ActionEvent e) {
				
			File imageFilePath = new File(ApplicationContext.getInstance().getSourcePath());
			
			for (File file : imageFilePath.listFiles()) {
				try {
					if (JPEGUtil.isJPEG(file)) {
						imagesToViewListModel.addElement(file);
					}
				} catch (FileNotFoundException fnfex) {
					JOptionPane.showMessageDialog(null, lang.get("fileretriever.canNotFindFile") + "\n(" + file.getAbsolutePath() + ")", lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
					logger.logERROR("Could not find file:");
					logger.logERROR(fnfex);
				} catch (IOException iox) {
					JOptionPane.showMessageDialog(null, lang.get("fileretriever.canNotReadFromFile") + "\n(" + file.getAbsolutePath() + ")", lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
					logger.logERROR("Can not read from file:");
					logger.logERROR(iox);
				}
			}
			setNrOfImagesLabels();
		}		
	}

	private class AddCategory implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			TreePath selectedPath = ApplicationContext.getInstance().getSelectedCategoryPath();
			String categoryName = null;
			boolean isTopLevelCategory = false;
			DefaultMutableTreeNode selectedNode = null;
			DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategroiesCategoryTree.getTreeModel();
			
			// Should the category be added at the top level...
			if (selectedPath == null) {
//				TODO: fix hard coded string
				categoryName = displayInputDialog("Add new root category", "Please enter a name for the new category", "");
				isTopLevelCategory = true;
            // ... or as a sub category of an existing category
			} else {
				selectedNode = ((DefaultMutableTreeNode)selectedPath.getLastPathComponent());
				String value = ((CategoryUserObject)selectedNode.getUserObject()).getName();
				
//				TODO: fix hard coded string
				categoryName = displayInputDialog("Add new sub category to category: " + value, "Please enter a name for the sub category", "");
			}
			
			if (categoryName != null) {
				if (!CategoryUtil.isValid(categoryName)) {
//					TODO: fix hard coded string
					displayErrorMessage("The category name can not start with a white space: " + "\"" + categoryName + "\"");	
				} else if(CategoryUtil.alreadyExists((DefaultMutableTreeNode)model.getRoot(), selectedPath, categoryName)) {
//					TODO: fix hard coded string
					displayErrorMessage("\"" + categoryName + "\"" + " already exists as a category in the selected scope, please choose another name");
				} else {
					CategoryUserObject cuo = new CategoryUserObject(categoryName, Integer.toString(ApplicationContext.getInstance().getNextIDToUse()));
					
					DefaultMutableTreeNode newCategory = new DefaultMutableTreeNode(cuo);
										
					if (isTopLevelCategory) {
						TreeUtil.insertNodeInAlphabeticalOrder(newCategory, (DefaultMutableTreeNode)model.getRoot(), model);
					} else {
						TreeUtil.insertNodeInAlphabeticalOrder(newCategory, selectedNode, model);
					}
					File categoriesFile = new File(C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION + C.FS + "config" + C.FS +  "categories.xml");
					CategoryUtil.store(categoriesFile, (DefaultMutableTreeNode)model.getRoot());
				}
			}
		}
	}
	
	private class RenameCategory implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			TreePath selectedPath = ApplicationContext.getInstance().getSelectedCategoryPath();
			
			DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategroiesCategoryTree.getTreeModel();
			
			DefaultMutableTreeNode nodeToRename = ((DefaultMutableTreeNode)selectedPath.getLastPathComponent());
			String value = ((CategoryUserObject)nodeToRename.getUserObject()).getName();
			
			String newName = displayInputDialog("Rename category", "Please enter a new name for category: " + value, value);
			
			if (newName != null && !newName.equals(value)) {
				if (!CategoryUtil.isValid(newName)) {
					displayErrorMessage("The category name can not start with a white space: " + "\"" + newName + "\"");	
				} else if(CategoryUtil.alreadyExists((DefaultMutableTreeNode)model.getRoot(), selectedPath.getParentPath(), newName)) {
					displayErrorMessage("\"" + newName + "\"" + " already exists as a category in the selected scope, please choose another name");
				} else {
					((CategoryUserObject)nodeToRename.getUserObject()).setName(newName);
					model.nodeChanged(nodeToRename);

					DefaultMutableTreeNode parent = (DefaultMutableTreeNode)nodeToRename.getParent();

					TreeUtil.sortNodesAlphabetically(parent, model);

					File categoriesFile = new File(C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION + C.FS + "config" + C.FS +  "categories.xml");
					CategoryUtil.store(categoriesFile, (DefaultMutableTreeNode)model.getRoot());
				}
			}
		}
	}

	private class RemoveCategory implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TreePath selectedPath = ApplicationContext.getInstance().getSelectedCategoryPath();
			
			DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategroiesCategoryTree.getTreeModel();
			
			DefaultMutableTreeNode nodeToRemove = (DefaultMutableTreeNode)selectedPath.getLastPathComponent();
			
			int result = 0;
			
			if (nodeToRemove.getChildCount() > 0) {
//				TODO: Fix hard coded string
				String message ="Remove category: " + ((CategoryUserObject)nodeToRemove.getUserObject()).getName() + " and all sub categories?";
				result = displayConfirmDialog(message, lang.get("errormessage.maingui.warningMessageLabel"), JOptionPane.OK_CANCEL_OPTION);
			}
			if (result == 0) {
				model.removeNodeFromParent((DefaultMutableTreeNode)selectedPath.getLastPathComponent());
				
				File categoriesFile = new File(C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION + C.FS + "config" + C.FS +  "categories.xml");
				CategoryUtil.store(categoriesFile, (DefaultMutableTreeNode)model.getRoot());
			}
		}
	}
	
	private class CollapseCategoryTreeStructure implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TreePath selectedPath = ApplicationContext.getInstance().getSelectedCategoryPath();
			
			JTree tree  = checkTreeManagerForAssignCategroiesCategoryTree.getCheckedJtree();
			
			if (selectedPath == null) {
				DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategroiesCategoryTree.getTreeModel();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
				TreeUtil.collapseEntireTree(tree, root, false);
			} else {
				TreeUtil.collapseEntireTree(tree, (DefaultMutableTreeNode)selectedPath.getLastPathComponent(), true);
			}
		}
	}
	
	private class ExpandCategoryTreeStructure implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			TreePath selectedPath = ApplicationContext.getInstance().getSelectedCategoryPath();
			
			JTree tree  = checkTreeManagerForAssignCategroiesCategoryTree.getCheckedJtree();
			
			if (selectedPath == null) {
				DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategroiesCategoryTree.getTreeModel();
				DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
				TreeUtil.expandEntireTree(tree, root, false);
			} else {
				TreeUtil.expandEntireTree(tree, (DefaultMutableTreeNode)selectedPath.getLastPathComponent(), true);
			}
		}
	}
		
	private class ImagesToViewListListener implements ListSelectionListener {
		
		public void valueChanged(ListSelectionEvent e) {
			int selectedIndex = imagesToViewList.getSelectedIndex();
						
			if (selectedIndex > -1) {
				JPEGThumbNail thumbNail = JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom((File)imagesToViewListModel.get(selectedIndex));
				imagePreviewLabel.setIcon(new ImageIcon(thumbNail.getThumbNailData()));
			}
		}
	}
	
	/**
	 * This method flushes out all changes to the currently loaded set of
	 * {@link ImageMetaDataDataBaseItem} objects to disk.
	 */
	private void flushImageMetaDataBaseToDisk() {
		ImageMetaDataDataBaseItemsToUpdateContext imddbituc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();
		ImageMetaDataDataBaseHandler.updateDataBaseFile(imddbituc.getImageMetaDataBaseItems(), imddbituc.getLoadedRepositoryPath());
		imddbituc.reInit();
	}
	
	private ImageMetaDataContextSearchParameters collectSearchParameters() {
		
		ImageMetaDataContextSearchParameters imdcsp = new ImageMetaDataContextSearchParameters();
		imdcsp.setCategories(getSelectedCategoriesFromTreeModel(checkTreeManagerForFindImagesCategoryTree));
		imdcsp.setAndCategoriesSearch(andRadioButton.isSelected());
		
//		imdcsp.setCameraModel(cameraModel.getSelectedStringValue());
//		imdcsp.setComment(commentTextArea.getText());
//		if (!imageSize.getSelectedImageSizeValue().equals("")) {
//			imdcsp.setImageSize(new ImageSize(imageSize.getSelectedImageSizeValue()));
//		} else {
//			imdcsp.setImageSize(null);
//		}
//		imdcsp.setIso(iso.getSelectedIntegerValue());
//		try {
//			imdcsp.setShutterSpeed(new ShutterSpeed(shutterSpeed.getSelectedShutterSpeedValue()));
//		} catch (ShutterSpeedException e) {
//			imdcsp.setShutterSpeed(null);
//		}
		
//		MetaDataValue imagesSizeMetaDataValue;
//		MetaDataValue isoMetaDataValue;
//		MetaDataValue shutterSpeedMetaDataValue;
//		MetaDataValue apertureValueMetaDataValue;
//		MetaDataValue cameraModelMetaDataValue;
		
		imdcsp.setImageSize(imagesSizeMetaDataValue.getValue());
		imdcsp.setIso(isoMetaDataValue.getValue());
		
		imdcsp.setRating(getSelectedRatings());
		imdcsp.setShutterSpeed(shutterSpeedMetaDataValue.getValue());
		
		return imdcsp;
	}
	
	private boolean[] getSelectedRatings() {
		boolean[] selectedRatings = null;
		boolean allDeSelected = true;
		
		for (JCheckBox ratingCheckBox : ratingCheckBoxes) {
			if (ratingCheckBox.isSelected()) {
				allDeSelected = false;	
			}
		}
		
		if (allDeSelected) {
			return selectedRatings;	
		} else {
			selectedRatings = new boolean[6];
			
			for (int i = 0; i < ratingCheckBoxes.length; i++) {
				selectedRatings[i] = ratingCheckBoxes[i].isSelected();	
			}
			return selectedRatings;
		}
	}
	
	/**
	 * This method resets the state of the Image Tag tab to the initial state;
	 * with no preview image and no comment.
	 */
	private void clearTagTab() {
		imageCommentTextArea.setText("");
		imageTagPreviewLabel.setIcon(null);
		setRatingValue(0);
		checkTreeManagerForAssignCategroiesCategoryTree.getSelectionModel().clearSelection();
	}
	
	private class MouseButtonListener extends MouseAdapter{
		public void mouseReleased(MouseEvent e){
			if(e.isPopupTrigger() && (mainTabbedPane.getSelectedIndex() == 0)) {
				rightClickMenuRename.show(e.getComponent(),e.getX(), e.getY());
				popupMenuCopyImageToClipBoardRename.setActionCommand(((JButton)e.getComponent()).getActionCommand());
			} else if(e.isPopupTrigger() && (mainTabbedPane.getSelectedIndex() == 2)) {
				rightClickMenuView.show(e.getComponent(),e.getX(), e.getY());
				popupMenuAddImageToViewList.setActionCommand(((JButton)e.getComponent()).getActionCommand());
				popupMenuCopyImageToClipBoardView.setActionCommand(((JButton)e.getComponent()).getActionCommand());
			} else if(e.isPopupTrigger() && (mainTabbedPane.getSelectedIndex() == 1)) {
				rightClickMenuTag.show(e.getComponent(), e.getX(), e.getY());
				popupMenuCopyImageToClipBoardTag.setActionCommand(((JButton)e.getComponent()).getActionCommand());
			}
		}
	}
	
//	private class MainTabbedPaneListener implements ChangeListener {
//		public void stateChanged(ChangeEvent e) {
//			JPanel selectedComponent = (JPanel)((JTabbedPane)e.getSource()).getSelectedComponent();
//			
//			MainTabbedPaneComponent mainTabbedPaneComponent = MainTabbedPaneComponent.valueOf(selectedComponent.getName());
//			
//			ApplicationContext ac = ApplicationContext.getInstance();
//			String sourcePath = ac.getSourcePath(); 
//			
//			switch (mainTabbedPaneComponent) {
//			case RENAME:
//			case CATEGORIZE:
////				if (!sourcePath.equals("")) {
////					loadThumbNails(new File(sourcePath));
////				} else {
//					thumbNailsPanel.removeAll();
//					thumbNailsPanel.updateUI();
//					
//					statusBar.clear();
////				}
//				break;
//			case VIEW:
////				if (ac.imageSearchResultExists()) {
////					loadThumbNailsFromImageSearchResult(ac.getImageSearchResult());
////				} else {
////					if (!sourcePath.equals("")) {
////						loadThumbNails(new File(sourcePath));
////					} else {
//						thumbNailsPanel.removeAll();
//						thumbNailsPanel.updateUI();
//						
//						statusBar.clear();
////					}
////				}
//				break;
//			}
//		}
//	}
	
	private class CategoriesMouseButtonListener extends MouseAdapter{
		
		public void mouseReleased(MouseEvent e){
			TreePath selectedPath = checkTreeManagerForAssignCategroiesCategoryTree.getCheckedJtree().getPathForLocation(e.getX(), e.getY());
			
			if(e.isPopupTrigger()) {
				String collapseCategory = "";
				String expandCategory = "";
				String addCategory = "";
				String renameCategory = "";
				String removeCategory = "";
				
				DefaultMutableTreeNode treeNode = null;
				
				// Should the category be added at the top level...
				if (selectedPath == null) {
//					TODO: Fix hard coded string
					addCategory = "Add new Top Level Category";
//					TODO: Fix hard coded string					
					collapseCategory = "Collapse Top Level Categories";
//					TODO: Fix hard coded string					
					expandCategory = "Expand Top Level Categories";
		        // ... or as a sub category of an existing category
				} else {
					treeNode = ((DefaultMutableTreeNode)selectedPath.getLastPathComponent());
					String value = ((CategoryUserObject)treeNode.getUserObject()).getName();
//					TODO: Fix hard coded string					
					collapseCategory = "Collapse Category: " + value;
//					TODO: Fix hard coded string					
					expandCategory = "Expand  Category: " + value;
//					TODO: Fix hard coded string
					addCategory = "Add new sub category to Category: " + value;	
//					TODO: Fix hard coded string
					renameCategory = "Rename selected Category: " + value;
//					TODO: Fix hard coded string
					removeCategory = "Remove selected Category: " + value;
				}					
				
				popupMenuAddCategory.setText(addCategory);
				popupMenuRenameCategory.setText(renameCategory);
				popupMenuRemoveCategory.setText(removeCategory);
				
				popupMenuCollapseCategoriesTreeStructure.setText(collapseCategory);
				popupMenuExpandCategoriesTreeStructure.setText(expandCategory);
				
				JTree categoryTree = checkTreeManagerForAssignCategroiesCategoryTree.getCheckedJtree();
				
				/**
				 * If no category has been selected.
				 */
				if (selectedPath == null) {
					DefaultMutableTreeNode root = (DefaultMutableTreeNode)checkTreeManagerForAssignCategroiesCategoryTree.getTreeModel().getRoot();
					
					int nrOfChildren = root.getChildCount();
					boolean someChildIsExpanded = false;
					boolean someChildCanBeExpanded = false;
					
					if (nrOfChildren > 0) {
						for (int i = 0; i < nrOfChildren; i++) {
							DefaultMutableTreeNode child = (DefaultMutableTreeNode)root.getChildAt(i);
							
							boolean currentChildIsExpanded = categoryTree.isExpanded(new TreePath(child.getPath()));
							
							if (!someChildIsExpanded) {
								someChildIsExpanded = currentChildIsExpanded;
							}
							if (!someChildCanBeExpanded && child.getChildCount() > 0 && !currentChildIsExpanded) {
								someChildCanBeExpanded = true;
							}
						}
						
						if (someChildCanBeExpanded && someChildIsExpanded ) {
							this.createMenu(CategoryMenyType.NO_RENAME_REMOVE);
						} else if (someChildCanBeExpanded && !someChildIsExpanded) {
							this.createMenu(CategoryMenyType.NO_RENAME_REMOVE_COLLAPSE);
						} else if (!someChildCanBeExpanded && !someChildIsExpanded) {
							this.createMenu(CategoryMenyType.NO_RENAME_REMOVE_EXPAND_COLLAPSE);
						} else if (!someChildCanBeExpanded && someChildIsExpanded) {
							this.createMenu(CategoryMenyType.NO_RENAME_REMOVE_EXPAND);
						}
					} else {
						this.createMenu(CategoryMenyType.NO_RENAME_REMOVE_EXPAND_COLLAPSE);
					}
				} 
				/**
				 * If a category has been selected
				 */
				else {
					if (treeNode.getChildCount() > 0) {
						if (categoryTree.isExpanded(selectedPath)) {
							this.createMenu(CategoryMenyType.NO_EXPAND);
						} else {
							this.createMenu(CategoryMenyType.NO_COLLAPSE);
						}
					} else {
						this.createMenu(CategoryMenyType.NO_EXPAND_COLLAPSE);
					}
				}

				ApplicationContext.getInstance().setSelectedCategoryPath(selectedPath);
				
				rightClickMenuCategories.show(e.getComponent(),e.getX(), e.getY());
			}
		}
		
		private void createMenu(CategoryMenyType categoryMenyType) {
			
			rightClickMenuCategories.removeAll();	
			
			switch (categoryMenyType) {
			case ALL:
				rightClickMenuCategories.add(popupMenuCollapseCategoriesTreeStructure);
				rightClickMenuCategories.add(popupMenuExpandCategoriesTreeStructure);
				rightClickMenuCategories.addSeparator();
				rightClickMenuCategories.add(popupMenuAddCategory);
				rightClickMenuCategories.add(popupMenuRenameCategory);
				rightClickMenuCategories.add(popupMenuRemoveCategory);
				break;
				
			case NO_COLLAPSE:
				rightClickMenuCategories.add(popupMenuExpandCategoriesTreeStructure);
				rightClickMenuCategories.addSeparator();
				rightClickMenuCategories.add(popupMenuAddCategory);
				rightClickMenuCategories.add(popupMenuRenameCategory);
				rightClickMenuCategories.add(popupMenuRemoveCategory);
				break;
				
			case NO_EXPAND:
				rightClickMenuCategories.add(popupMenuCollapseCategoriesTreeStructure);
				rightClickMenuCategories.addSeparator();
				rightClickMenuCategories.add(popupMenuAddCategory);
				rightClickMenuCategories.add(popupMenuRenameCategory);
				rightClickMenuCategories.add(popupMenuRemoveCategory);
				break;
				
			case NO_EXPAND_COLLAPSE:
				rightClickMenuCategories.add(popupMenuAddCategory);
				rightClickMenuCategories.add(popupMenuRenameCategory);
				rightClickMenuCategories.add(popupMenuRemoveCategory);
				break;
				
			case NO_RENAME_REMOVE:
				rightClickMenuCategories.add(popupMenuCollapseCategoriesTreeStructure);
				rightClickMenuCategories.add(popupMenuExpandCategoriesTreeStructure);
				rightClickMenuCategories.addSeparator();
				rightClickMenuCategories.add(popupMenuAddCategory);
				break;
				
			case NO_RENAME_REMOVE_COLLAPSE:
				rightClickMenuCategories.add(popupMenuExpandCategoriesTreeStructure);
				rightClickMenuCategories.addSeparator();
				rightClickMenuCategories.add(popupMenuAddCategory);
				break;
				
			case NO_RENAME_REMOVE_EXPAND:
				rightClickMenuCategories.add(popupMenuCollapseCategoriesTreeStructure);
				rightClickMenuCategories.addSeparator();
				rightClickMenuCategories.add(popupMenuAddCategory);
				break;
				
			case NO_RENAME_REMOVE_EXPAND_COLLAPSE:
				rightClickMenuCategories.add(popupMenuAddCategory);
				break;
			}
		}
	}
	
	private enum CategoryMenyType {
		ALL,
		NO_COLLAPSE,
		NO_EXPAND,
		NO_EXPAND_COLLAPSE,
		NO_RENAME_REMOVE,
		NO_RENAME_REMOVE_COLLAPSE,
		NO_RENAME_REMOVE_EXPAND_COLLAPSE,
		NO_RENAME_REMOVE_EXPAND;
	}
}
