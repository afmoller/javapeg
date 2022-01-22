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
package moller.javapeg.program.gui.frames;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import moller.javapeg.program.C;
import moller.javapeg.program.FileRetriever;
import moller.javapeg.program.FileSelection;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.applicationstart.ValidateFileSetup;
import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.categories.CategoryUserObject;
import moller.javapeg.program.categories.CategoryUtil;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.ConfigUtil;
import moller.javapeg.program.config.controller.section.CategoriesConfig;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.ToolTips;
import moller.javapeg.program.config.model.UpdatesChecker;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.GUI.splitpane.GUIWindowSplitPane;
import moller.javapeg.program.config.model.GUI.splitpane.GUIWindowSplitPaneUtil;
import moller.javapeg.program.config.model.GUI.tab.GUITab;
import moller.javapeg.program.config.model.GUI.tab.GUITabsUtil;
import moller.javapeg.program.config.model.GUI.tab.SelectedMainGUITab;
import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesCategories;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPreview;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.javapeg.program.config.model.repository.RepositoryExceptions;
import moller.javapeg.program.config.model.repository.RepositoryPaths;
import moller.javapeg.program.config.model.thumbnail.ThumbNailGrayFilter;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.contexts.ImageMetaDataDataBaseItemsToUpdateContext;
import moller.javapeg.program.enumerations.CategoryMenuType;
import moller.javapeg.program.enumerations.FileLoadingAction;
import moller.javapeg.program.enumerations.ImageMetaDataContextAction;
import moller.javapeg.program.enumerations.MainTabbedPaneComponent;
import moller.javapeg.program.enumerations.TabPosition;
import moller.javapeg.program.enumerations.xml.ConfigElement;
import moller.javapeg.program.gui.CategoriesTransferHandler;
import moller.javapeg.program.gui.CustomizedJTable;
import moller.javapeg.program.gui.FileTreeCellRenderer;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.gui.LoadedThumbnails;
import moller.javapeg.program.gui.checktree.CategoryCheckTreeUtil;
import moller.javapeg.program.gui.checktree.CheckTreeManager;
import moller.javapeg.program.gui.components.HeadingPanel;
import moller.javapeg.program.gui.components.MetaDataPanel;
import moller.javapeg.program.gui.components.StatusPanel;
import moller.javapeg.program.gui.components.ThumbNailsPanel;
import moller.javapeg.program.gui.components.VariablesPanel;
import moller.javapeg.program.gui.dialog.CategoryImportExportPopup;
import moller.javapeg.program.gui.frames.configuration.ConfigViewerGUI;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.gui.panel.ImageSearchTab;
import moller.javapeg.program.gui.panel.ViewPanelListSection;
import moller.javapeg.program.gui.tab.ImageMergeTab;
import moller.javapeg.program.gui.workers.AbstractImageMetaDataContextLoader;
import moller.javapeg.program.gui.workers.SelectedImageIconGenerator;
import moller.javapeg.program.helpviewer.HelpViewerGUI;
import moller.javapeg.program.imagemetadata.ImageMetaDataDataBase;
import moller.javapeg.program.imagemetadata.ImageMetaDataItem;
import moller.javapeg.program.imagemetadata.handler.ImageMetaDataDataBaseHandler;
import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.javapeg.program.jpeg.JPEGThumbNail;
import moller.javapeg.program.jpeg.JPEGThumbNailCache;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.metadata.MetaDataRetriever;
import moller.javapeg.program.metadata.MetaDataUtil;
import moller.javapeg.program.model.ImageRepositoriesTableModel;
import moller.javapeg.program.model.MetaDataTableModel;
import moller.javapeg.program.model.ModelInstanceLibrary;
import moller.javapeg.program.model.PreviewTableModel;
import moller.javapeg.program.progress.RenameProcess;
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
import moller.util.DefaultLookAndFeel;
import moller.util.datatype.OneSizedList;
import moller.util.gui.Screen;
import moller.util.gui.Table;
import moller.util.gui.TreeUtil;
import moller.util.gui.Update;
import moller.util.image.ImageUtil;
import moller.util.io.DirectoryUtil;
import moller.util.io.FileUtil;
import moller.util.io.PathUtil;
import moller.util.io.Status;
import moller.util.io.StreamUtil;
import moller.util.java.SystemProperties;
import moller.util.jpeg.JPEGUtil;
import moller.util.mnemonic.MnemonicConverter;
import moller.util.os.OsUtil;
import moller.util.result.ResultObject;
import moller.util.string.ParseVMArguments;
import moller.util.string.StringUtil;
import moller.util.version.containers.VersionInformation;

import org.xml.sax.SAXException;

/**
 * @author Fredrik
 *
 */
public class MainGUI extends JFrame {

    private static final long serialVersionUID = 4478711914847747931L;

    private static Configuration configuration;
    private static Logger logger;
    private static Language lang;

    private JButton destinationPathButton;
    private JButton startProcessButton;

    private JButton saveFileNameTemplateButton;
    private JButton saveSubFolderTemplateButton;
    private JButton removeFileNameTemplateButton;
    private JButton removeSubFolderTemplateButton;

    private JLabel imageTagPreviewLabel;

    private JTextField destinationPathTextField;
    private JTextField subFolderTextField;
    private JTextField fileNameTemplateTextField;
    private JTextField subFolderNamePreviewTextField;

    private JComboBox<String> fileNameTemplateComboBox;
    private JComboBox<String> subFolderTemplateComboBox;

    private JMenuItem configGUIJMenuItem;
    private JMenuItem shutDownProgramJMenuItem;
    private JMenuItem openDestinationFileChooserJMenuItem;
    private JMenuItem startProcessJMenuItem;
    private JMenuItem exportCategoryTreeStructureJMenuItem;
    private JMenuItem importCategoryTreeStructureJMenuItem;
    private JMenuItem helpJMenuItem;
    private JMenuItem aboutJMenuItem;
    private JMenuItem popupMenuCopyImageToClipBoard;
    private JMenuItem popupMenuCopySelectedImagesToClipBoard;
    private JMenuItem popupMenuCopyAllImagesToClipBoard;
    private JMenuItem popupMenuAddImagePathToImageRepository;
    private JMenuItem popupMenuRemoveImagePathFromImageRepository;
    private JMenuItem popupMenuSelectAllImages;
    private JMenuItem popupMenuDeSelectAllImages;
    private JMenuItem popupMenuAddImageToViewList;
    private JMenuItem popupMenuAddSelectedImagesToViewList;
    private JMenuItem popupMenuAddAllImagesToViewList;
    private JMenuItem popupMenuAddCategory;
    private JMenuItem popupMenuRenameCategory;
    private JMenuItem popupMenuRemoveCategory;
    private JMenuItem popupMenuSaveSelectedCategoriesToSelectedImages;
    private JMenuItem popupMenuSaveSelectedCategoriesToAllImages;
    private JMenuItem popupMenuExpandCategoriesTreeStructure;
    private JMenuItem popupMenuCollapseCategoriesTreeStructure;
    private JMenuItem popupMenuAddDirectoryToAlwaysAutomaticallyAddToImageRepositoryList;
    private JMenuItem popupMenuAddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList;

    private JPopupMenu rightClickMenuCategories;
    private JPopupMenu rightClickMenu;
    private JPopupMenu rightClickMenuDirectoryTree;

    private ThumbNailsPanel thumbNailsPanel;
    private JPanel thumbNailsBackgroundsPanel;

    private JSplitPane thumbNailMetaPanelSplitPane;
    private JSplitPane verticalSplitPane;
    private JSplitPane mainSplitPane;
    private JSplitPane previewAndCommentSplitPane;
    private JSplitPane previewCommentCategoriesRatingSplitPane;
    private JSplitPane mainSplitPaneImageViewListSplitPane;

    private JScrollPane imageTagPreviewScrollPane;

    private JCheckBox createThumbNailsCheckBox;

    private JTabbedPane tabbedPane;
    private JTabbedPane mainTabbedPane;

    private CustomizedJTable metaDataTable;
    private CustomizedJTable previewTable;

    private JTree tree;
    private JTree categoriesTree;

    // Listeners
    private FileSystemDirectoryTreeMouseListener mouseListener;
    private MouseButtonListener mouseRightClickButtonListener;
    private CategoriesMouseButtonListener categoriesMouseButtonListener;
    private ActionListener addSelectedPathToImageRepository;
    private ThumbNailListener thumbNailListener;

    private int iconWidth = 160;
    private int columnMargin = 0;

    private GridLayout thumbNailGridLayout;

    private MetaDataTableModel metaDataTableModel;
    private PreviewTableModel previewTableModel;

    private StatusPanel statusBar;
    private MetaDataPanel imageMetaDataPanel;

    private final ImageRepositoriesTableModel imageRepositoriesTableModel;

    private JTextArea imageCommentTextArea;

    private JRadioButton [] ratingRadioButtons;

    private CheckTreeManager checkTreeManagerForAssignCategoriesCategoryTree;

    private Thread loadFilesThread;

    private JProgressBar thumbnailLoadingProgressBar;

    private HeadingPanel thumbNailsPanelHeading;
    private ViewPanelListSection viewPanelListSection;

    private ImageMergeTab imageMergeTab;
    private ImageSearchTab imageSearchTab;

    /**
     * This object keeps track on which thumbnails that are loaded and selected
     * in the thumbnail overview part of this GUI.
     */
    private final LoadedThumbnails loadedThumbnails = new LoadedThumbnails();

    private final Map<File, ImageIcon> imageFileToSelectedImageMapping = Collections.synchronizedMap(new HashMap<>());
    private SelectedImageIconGenerator selectedImageIconGenerator;

    /** Provides nice icons and names for files. */
    private final FileSystemView fileSystemView;

    public MainGUI(){

        // Make pre configured logging, logging...
        long startTestWriteAccess = System.currentTimeMillis();
        if(!FileUtil.testWriteAccess(new File(C.USER_HOME))) {
            JOptionPane.showMessageDialog(null, "Can not create files in directory: " + C.USER_HOME);
        }
        long finishedTestWriteAccess = System.currentTimeMillis();

        long startValidateFileSetup = System.currentTimeMillis();
        ValidateFileSetup.check();
        long finishedValidateFileSetup = System.currentTimeMillis();

        long startGetConfiguration = System.currentTimeMillis();
        configuration = Config.getInstance().get();
        long finishedGetConfiguration = System.currentTimeMillis();

        logger = Logger.getInstance();
        logger.logDEBUG("JavaPEG is starting");

        logger.logDEBUG("testWriteAccess took: " + (finishedTestWriteAccess - startTestWriteAccess) + " milliseconds");
        logger.logDEBUG("validateFileSetup took: " + (finishedValidateFileSetup - startValidateFileSetup) + " milliseconds");
        logger.logDEBUG("getConfiguration took: " + (finishedGetConfiguration - startGetConfiguration) + " milliseconds");

        lang = Language.getInstance();

        fileSystemView = FileSystemView.getFileSystemView();

        imageRepositoriesTableModel = ModelInstanceLibrary.getInstance().getImageRepositoriesTableModel();

        this.printSystemPropertiesToLogFile();
        this.overrideSwingUIProperties();

        logger.logDEBUG("Check Available Memory Started");
        this.checkAvailableMemory();
        logger.logDEBUG("Check Available Memory Finished");

        // Check if JavaPEG client id is set, otherwise generate one and set it
        // to the configuration.
        if (!ConfigUtil.isClientIdSet(configuration.getJavapegClientId())) {
            configuration.setJavapegClientId(ConfigUtil.generateClientId());
        }

        UpdatesChecker updatesChecker = configuration.getUpdatesChecker();

        if(updatesChecker.isEnabled()) {
            logger.logDEBUG("Application Update Check Started");
            this.checkApplicationUpdates();
            logger.logDEBUG("Application Update Check Finished");
        }

        logger.logDEBUG("Creation of Main Frame Started");
        this.createMainFrame();
        logger.logDEBUG("Creation of Main Frame Finished");
        logger.logDEBUG("Creation of Menu Bar Started");
        this.createMenuBar();
        logger.logDEBUG("Creation of Menu Bar Finished");
        logger.logDEBUG("Creation of Tool Bar Started");
        this.createToolBar();
        logger.logDEBUG("Creation of Tool Bar Finished");
        this.createRightClickMenuCategories();
        this.createRightClickMenu();
        this.createRightClickMenuDirectoryTree();
        logger.logDEBUG("Adding of Event Listeners Started");
        this.addListeners();
        logger.logDEBUG("Adding of Event Listeners Finished");
        logger.logDEBUG("Application initialization Started");
        this.initiateProgram();
        logger.logDEBUG("Application initialization Finished");
        logger.logDEBUG("Application Context initialization Started");
        this.initiateApplicationContext();
        logger.logDEBUG("Application Context initialization Finished");

        logger.logDEBUG("Image Meta Data Context initialization Started");
        ImageMetaDataContextLoader imdcl = new  ImageMetaDataContextLoader();
        imdcl.addPropertyChangeListener(new ImageMetaDataContextLoaderPropertyListener());
        imdcl.execute();
    }

    private void printSystemPropertiesToLogFile() {
        logger.logDEBUG("##### System Properties Start #####");
        logger.logDEBUG("File Encoding......: " + SystemProperties.getFileEncoding());
        logger.logDEBUG("File Encoding PKG..: " + SystemProperties.getFileEncodingPkg());
        logger.logDEBUG("File Separator.....: " + SystemProperties.getFileSeparator());
        logger.logDEBUG("Java Class Path....: " + SystemProperties.getJavaClassPath());
        logger.logDEBUG("Java Class Version.: " + SystemProperties.getJavaClassVersion());
        logger.logDEBUG("Java Compiler......: " + SystemProperties.getJavaCompiler());
        logger.logDEBUG("Java Home..........: " + SystemProperties.getJavaHome());
        logger.logDEBUG("Java IO Tmpdir.....: " + SystemProperties.getJavaIoTmpdir());
        logger.logDEBUG("Java Vendor........: " + SystemProperties.getJavaVendor());
        logger.logDEBUG("Java Vendor Url....: " + SystemProperties.getJavaVendorUrl());
        logger.logDEBUG("Java Version.......: " + SystemProperties.getJavaVersion());
        logger.logDEBUG("Line Separator.....: " + SystemProperties.getLineSeparator());
        logger.logDEBUG("Os Arch............: " + SystemProperties.getOsArch());
        logger.logDEBUG("Os Name............: " + SystemProperties.getOsName());
        logger.logDEBUG("Os Version.........: " + SystemProperties.getOsVersion());
        logger.logDEBUG("Path Separator.....: " + SystemProperties.getPathSeparator());
        logger.logDEBUG("User Dir...........: " + SystemProperties.getUserDir());
        logger.logDEBUG("User Home..........: " + SystemProperties.getUserHome());
        logger.logDEBUG("User Language......: " + SystemProperties.getUserLanguage());
        logger.logDEBUG("User Name..........: " + SystemProperties.getUserName());
        logger.logDEBUG("User Region........: " + SystemProperties.getUserRegion());
        logger.logDEBUG("User Timezone......: " + SystemProperties.getUserTimezone());
        logger.logDEBUG("##### System Properties Stop #####");
    }

    private void checkApplicationUpdates() {

        logger.logDEBUG("Search for Application Updates Started");
        Thread updateCheck = new Thread(){

            @Override
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

        RuntimeMXBean RuntimemxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = RuntimemxBean.getInputArguments();

        long xmxValue = -1;
        String xmxString = "";

        for (String argument : arguments) {
            if (argument.toUpperCase().contains("XMX")) {
                xmxString = argument;
                xmxValue = ParseVMArguments.parseXmxToLong(argument);
                break;
            }
        }

        if (xmxValue < ParseVMArguments.parseXmxToLong("-Xmx768m")) {
            logger.logERROR("Maximum Size of Java Heap is to small. Current size is: \"" + xmxString + "\" bytes and it must be atleast Xmx768m");
            JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.notEnoughMemory"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            closeApplication(1);
        }
    }

    private void overrideSwingUIProperties() {
        UIManager.put("OptionPane.okButtonText", lang.get("common.button.ok.label"));
        UIManager.put("OptionPane.cancelButtonText", lang.get("common.button.cancel.label"));
        UIManager.put("OptionPane.yesButtonText", lang.get("common.button.yes.label"));
        UIManager.put("OptionPane.noButtonText", lang.get("common.button.no.label"));
    }

    private void createMenuBar(){

        // Skapa menyrader i arkiv-menyn
        openDestinationFileChooserJMenuItem = new JMenuItem(lang.get("menu.item.openDestinationFileChooser"));
        openDestinationFileChooserJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.openDestinationFileChooser.accelerator").charAt(0)), InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));

        startProcessJMenuItem = new JMenuItem(lang.get("menu.item.startProcess"));
        startProcessJMenuItem.setToolTipText(lang.get("tooltip.selectSourceDirectoryWithImagesAndDestinationDirectory"));
        startProcessJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.startProcess.accelerator").charAt(0)), InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));
        startProcessJMenuItem.setEnabled(false);

        shutDownProgramJMenuItem = new JMenuItem(lang.get("menu.item.exit"));
        shutDownProgramJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.exit.accelerator").charAt(0)), InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));

        exportCategoryTreeStructureJMenuItem = new JMenuItem(lang.get("categoryimportexport.export.long.title"));
        exportCategoryTreeStructureJMenuItem.setToolTipText(lang.get("categoryimportexport.export.long.title.tooltip"));
        exportCategoryTreeStructureJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent('E'), InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));

        importCategoryTreeStructureJMenuItem = new JMenuItem(lang.get("categoryimportexport.import.long.title"));
        importCategoryTreeStructureJMenuItem.setToolTipText(lang.get("categoryimportexport.import.long.title.tooltip"));
        importCategoryTreeStructureJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent('I'), InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));

        JMenu fileMenu = new JMenu(lang.get("menu.file"));
        fileMenu.setMnemonic(lang.get("menu.mnemonic.file").charAt(0));

        fileMenu.add(openDestinationFileChooserJMenuItem);
        fileMenu.add(startProcessJMenuItem);
        fileMenu.add(shutDownProgramJMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exportCategoryTreeStructureJMenuItem);
        fileMenu.add(importCategoryTreeStructureJMenuItem);

        // Create rows in the Configuration menu
        configGUIJMenuItem = new JMenuItem(lang.get("menu.item.configuration"));
        configGUIJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent('c'), InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));

        JMenu configMenu = new JMenu(lang.get("menu.configuration"));
        configMenu.setMnemonic(lang.get("menu.mnemonic.configuration").charAt(0));

        configMenu.add(configGUIJMenuItem);

        // Create menu items in the help menu
        helpJMenuItem = new JMenuItem(lang.get("menu.item.programHelp"));
        helpJMenuItem.setAccelerator(KeyStroke.getKeyStroke("F1"));

        aboutJMenuItem = new JMenuItem(lang.get("menu.item.about"));
        aboutJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.item.about.accelerator").charAt(0)), InputEvent.CTRL_DOWN_MASK + InputEvent.ALT_DOWN_MASK));

        JMenu helpMenu = new JMenu(lang.get("menu.help"));
        helpMenu.setMnemonic(lang.get("menu.mnemonic.help").charAt(0));

        helpMenu.add(helpJMenuItem);
        helpMenu.add(aboutJMenuItem);

        JMenuBar menuBar = new JMenuBar();

        menuBar.add(fileMenu);
        menuBar.add(configMenu);
        menuBar.add(helpMenu);

        this.setJMenuBar(menuBar);
    }

    private void createToolBar(){
    }

    private JScrollPane createThumbNailsBackgroundPanel(){

        thumbNailGridLayout = new GridLayout(0, 6);
        thumbNailsPanel = new ThumbNailsPanel(thumbNailGridLayout);

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
        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 1, 2)));

        JLabel infoPanelLabel = new JLabel(lang.get("information.panel.informationLabel"));
        infoPanelLabel.setForeground(Color.GRAY);

        // Skapa en tabbed pane som innehåller tre paneler
        tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);

        // Skapa tabellmodeller
        previewTableModel = ModelInstanceLibrary.getInstance().getPreviewTableModel();
        metaDataTableModel = ModelInstanceLibrary.getInstance().getMetaDataTableModel();

        // Skapa tabellen för metadata-informatonen och sätt attribut till den
        TableRowSorter<TableModel> metaDataTableModelSorter = new TableRowSorter<TableModel>(metaDataTableModel);
        metaDataTable = new CustomizedJTable(metaDataTableModel);
        metaDataTable.setEnabled(false);
        metaDataTable.setRowSorter(metaDataTableModelSorter);

        // Skapa tabellen för namnförhandsgranskningen och sätt attribut till den
        TableRowSorter<TableModel> previewTableModelSorter = new TableRowSorter<TableModel>(previewTableModel);
        previewTable = new CustomizedJTable(previewTableModel);
        previewTable.setEnabled(false);
        previewTable.setRowSorter(previewTableModelSorter);

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

        JLabel subFolderPreviewTextFieldLabel = new JLabel(lang.get("information.panel.subFolderNameLabel"));

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

                Set<File> sortedSet = new TreeSet<>(allJPEGFileNameMappings.keySet());

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
        this.setIconImage(IconLoader.getIcon(Icons.JAVAPEG).getImage());

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        KeyEventDispatcher customKeyEventDispatcher = new CustomKeyEventDispatcher();
        manager.addKeyEventDispatcher(customKeyEventDispatcher);

        GUI gUI = configuration.getgUI();

        GUIWindow mainGUI = gUI.getMain();

        Point xyFromConfig = mainGUI.getSizeAndLocation().getLocation();

        if (Screen.isVisibleOnScreen(mainGUI.getSizeAndLocation())) {
            this.setLocation(xyFromConfig);
            this.setSize(mainGUI.getSizeAndLocation().getSize());

        } else {
            JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            logger.logERROR("Could not set location of Main GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");

            this.setLocation(0,0);
            this.setSize(GUIDefaults.MAINGUI_WIDTH, GUIDefaults.MAINGUI_HEIGHT);
        }

        try{
            DefaultLookAndFeel.set();
        }
        catch (Exception ex){
            logger.logERROR("Could not set default Look And Feel for Main GUI");
            logger.logERROR(ex);
        }

        thumbNailListener = new ThumbNailListener();
        mouseRightClickButtonListener = new MouseButtonListener();
        categoriesMouseButtonListener = new CategoriesMouseButtonListener();

        List<GUIWindowSplitPane> guiWindowSplitPanes = mainGUI.getGuiWindowSplitPane();

        thumbNailsPanelHeading = new HeadingPanel(lang.get("picture.panel.pictureLabel"), Color.GRAY, null);

        thumbnailLoadingProgressBar = new JProgressBar();
        thumbnailLoadingProgressBar.setStringPainted(true);
        thumbnailLoadingProgressBar.setVisible(false);

        thumbNailsBackgroundsPanel = new JPanel(new BorderLayout());
        thumbNailsBackgroundsPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));
        thumbNailsBackgroundsPanel.add(thumbNailsPanelHeading, BorderLayout.NORTH);
        thumbNailsBackgroundsPanel.add(this.createThumbNailsBackgroundPanel(), BorderLayout.CENTER);
        thumbNailsBackgroundsPanel.add(thumbnailLoadingProgressBar, BorderLayout.SOUTH);

        imageMergeTab = new ImageMergeTab();

        GUITab mainGuiApplicationModeTabs = GUITabsUtil.getGUITab(configuration.getgUITabs().getGuiTabs(), ConfigElement.MAIN_GUI_APPLICATION_MODE_TABS.getElementValue());

        mainTabbedPane = new JTabbedPane(getJTabbedPaneConstant(mainGuiApplicationModeTabs.getPosition()));
        mainTabbedPane.addTab(lang.get("tabbedpane.imageMerge"), imageMergeTab);
        mainTabbedPane.addTab(lang.get("tabbedpane.imageRename"), this.createRenamePanel());
        mainTabbedPane.addTab(lang.get("tabbedpane.imageTag"), this.createCategorizePanel());
        mainTabbedPane.addTab(lang.get("tabbedpane.imageView"), this.createViewPanel());
        mainTabbedPane.setForeground(mainGuiApplicationModeTabs.getTextColor());

        imageMetaDataPanel = new MetaDataPanel();

        thumbNailMetaPanelSplitPane = new JSplitPane();
        thumbNailMetaPanelSplitPane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.THUMB_NAIL_META_DATA_PANEL));
        thumbNailMetaPanelSplitPane.setDividerSize(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerSize(guiWindowSplitPanes, ConfigElement.THUMB_NAIL_META_DATA_PANEL).getDividerSizeInPixels());
        thumbNailMetaPanelSplitPane.setOneTouchExpandable(true);

        thumbNailMetaPanelSplitPane.setLeftComponent(thumbNailsBackgroundsPanel);
        thumbNailMetaPanelSplitPane.setRightComponent(imageMetaDataPanel);

        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        verticalSplitPane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.VERTICAL));
        verticalSplitPane.setDividerSize(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerSize(guiWindowSplitPanes, ConfigElement.VERTICAL).getDividerSizeInPixels());
        verticalSplitPane.setOneTouchExpandable(true);
        verticalSplitPane.setTopComponent(mainTabbedPane);
        verticalSplitPane.setBottomComponent(thumbNailMetaPanelSplitPane);

        mainSplitPane = new JSplitPane();
        mainSplitPane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.MAIN));
        mainSplitPane.setDividerSize(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerSize(guiWindowSplitPanes, ConfigElement.MAIN).getDividerSizeInPixels());
        mainSplitPane.setOneTouchExpandable(true);
        mainSplitPane.setLeftComponent(createTreePanel());
        mainSplitPane.setRightComponent(verticalSplitPane);

        viewPanelListSection = new ViewPanelListSection();

        mainSplitPaneImageViewListSplitPane = new JSplitPane();
        mainSplitPaneImageViewListSplitPane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.MAIN_TO_IMAGELIST));
        mainSplitPaneImageViewListSplitPane.setDividerSize(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerSize(guiWindowSplitPanes, ConfigElement.MAIN_TO_IMAGELIST).getDividerSizeInPixels());
        mainSplitPaneImageViewListSplitPane.setOneTouchExpandable(true);
        mainSplitPaneImageViewListSplitPane.setLeftComponent(mainSplitPane);
        mainSplitPaneImageViewListSplitPane.setRightComponent(viewPanelListSection);

        this.getContentPane().setLayout(new BorderLayout());
        this.add(mainSplitPaneImageViewListSplitPane, BorderLayout.CENTER);

        boolean [] timerStatus = {false,false,false,false};
        statusBar = new StatusPanel(timerStatus);

        this.add(statusBar, BorderLayout.SOUTH);
    }

    private int getJTabbedPaneConstant(TabPosition tabPosition) {
        switch (tabPosition) {

            case TOP:
                return JTabbedPane.TOP;
            case BOTTOM:
                return  JTabbedPane.BOTTOM;
            default:
                return JTabbedPane.TOP;
        }
    }

    private JPanel createTreePanel() {

        JPanel treePanelBackground = new JPanel(new BorderLayout());
        treePanelBackground.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        JLabel inputLabel = new JLabel(lang.get("labels.sourcePath"));
        inputLabel.setForeground(Color.GRAY);

        treePanelBackground.add(inputLabel, BorderLayout.NORTH);
        treePanelBackground.add(this.initiateJTree(), BorderLayout.CENTER);

        JPanel borderPanel = new JPanel(new BorderLayout());
        borderPanel.setBorder(BorderFactory.createEmptyBorder(5, 2, 0, 2));
        borderPanel.add(treePanelBackground, BorderLayout.CENTER);

        return borderPanel;
    }

    private JPanel createRenamePanel() {

        GBHelper posBackgroundPanel = new GBHelper();

        JPanel backgroundJPanel = new JPanel(new GridBagLayout());
        backgroundJPanel.setName(MainTabbedPaneComponent.RENAME.toString());
        backgroundJPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        backgroundJPanel.add(this.createRenameInputPanel(), posBackgroundPanel.nextCol().expandH());
        backgroundJPanel.add(Box.createHorizontalStrut(2), posBackgroundPanel.nextCol());
        backgroundJPanel.add(this.createInfoPanel(), posBackgroundPanel.nextCol().expandW().expandH());

        return backgroundJPanel;
    }

    private JPanel createViewPanel() {

        imageSearchTab = new ImageSearchTab();

        GBHelper posBackgroundPanel = new GBHelper();

        JPanel backgroundJPanel = new JPanel(new GridBagLayout());
        backgroundJPanel.setName(MainTabbedPaneComponent.VIEW.toString());
        backgroundJPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        backgroundJPanel.add(imageSearchTab, posBackgroundPanel.expandH().expandW());

        return backgroundJPanel;
    }

    private JPanel createCategorizePanel() {

        GUI gUI = configuration.getgUI();

        previewCommentCategoriesRatingSplitPane = new JSplitPane();

        previewCommentCategoriesRatingSplitPane.setLeftComponent(this.createPreviewAndCommentPanel());
        previewCommentCategoriesRatingSplitPane.setRightComponent(this.createCategoryAndRatingPanel());
        previewCommentCategoriesRatingSplitPane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(gUI.getMain().getGuiWindowSplitPane(), ConfigElement.PREVIEW_COMMENT_CATEGORIES_RATING));

        GBHelper posBackgroundPanel = new GBHelper();

        JPanel backgroundJPanel = new JPanel(new GridBagLayout());
        backgroundJPanel.setName(MainTabbedPaneComponent.CATEGORIZE.toString());
        backgroundJPanel.add(previewCommentCategoriesRatingSplitPane, posBackgroundPanel.expandH().expandW());

        this.setRatingCommentAndCategoryEnabled(false);

        return backgroundJPanel;
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
        RenameImages renameImages = configuration.getRenameImages();

        createThumbNailsCheckBox = new JCheckBox(lang.get("checkbox.createThumbNails"));
        createThumbNailsCheckBox.setToolTipText(lang.get("tooltip.createThumbNails"));
        createThumbNailsCheckBox.setActionCommand("createThumbNailsCheckBox");

        if(renameImages.getCreateThumbNails()) {
            createThumbNailsCheckBox.setSelected(true);
        }

        startProcessButton = new JButton(IconLoader.getIcon(Icons.PLAY));
        startProcessButton.setActionCommand("startProcessButton");
        startProcessButton.setToolTipText(lang.get("tooltip.selectSourceDirectoryWithImagesAndDestinationDirectory"));
        startProcessButton.setPreferredSize(new Dimension(30, 20));
        startProcessButton.setMinimumSize(new Dimension(30, 20));
        startProcessButton.setEnabled(false);

        JLabel destinationPathLabel = new JLabel(lang.get("labels.destinationPath"));
        destinationPathLabel.setForeground(Color.GRAY);

        destinationPathTextField = new JTextField();
        destinationPathTextField.setEditable(false);
        destinationPathTextField.setBackground(Color.WHITE);

        destinationPathButton = new JButton(IconLoader.getIcon(Icons.OPEN));
        destinationPathButton.setActionCommand("destinationPathButton");
        destinationPathButton.setToolTipText(lang.get("tooltip.destinationPathButton"));
        destinationPathButton.setPreferredSize(new Dimension(30, 20));
        destinationPathButton.setMinimumSize(new Dimension(30, 20));
        destinationPathButton.setEnabled(false);

        JLabel subFolderLabel = new JLabel(lang.get("labels.subFolderName"));
        subFolderLabel.setForeground(Color.GRAY);

        subFolderTextField = new JTextField();
        subFolderTextField.setEnabled(false);
        subFolderTextField.setToolTipText(lang.get("tooltip.enableTemplateFields"));
        subFolderTextField.setText(renameImages.getTemplateSubDirectoryName());

        JLabel fileNameTemplateLabel = new JLabel(lang.get("labels.fileNameTemplate"));
        fileNameTemplateLabel.setForeground(Color.GRAY);

        fileNameTemplateTextField = new JTextField();
        fileNameTemplateTextField.setEnabled(false);
        fileNameTemplateTextField.setToolTipText(lang.get("tooltip.enableTemplateFields"));
        fileNameTemplateTextField.setText(renameImages.getTemplateFileName());

        DefaultComboBoxModel<String> subFolderTemplateComboBoxModel = new DefaultComboBoxModel<>();
        for (String template : renameImages.getTemplateSubDirectoryNames()) {
            subFolderTemplateComboBoxModel.addElement(template);
        }

        subFolderTemplateComboBox = new JComboBox<>();
        subFolderTemplateComboBox.setModel(subFolderTemplateComboBoxModel);
        subFolderTemplateComboBox.setEnabled(false);

        DefaultComboBoxModel<String> fileNameTemplateComboBoxModel = new DefaultComboBoxModel<>();
        for (String template : renameImages.getTemplateFileNameNames()) {
            fileNameTemplateComboBoxModel.addElement(template);
        }

        fileNameTemplateComboBox = new JComboBox<>();
        fileNameTemplateComboBox.setModel(fileNameTemplateComboBoxModel);
        fileNameTemplateComboBox.setEnabled(false);

        saveFileNameTemplateButton = new JButton(IconLoader.getIcon(Icons.SAVE));
        saveFileNameTemplateButton.setToolTipText(lang.get("tooltip.saveTemplateToTemplatesList"));
        saveFileNameTemplateButton.setEnabled(false);

        saveSubFolderTemplateButton = new JButton(IconLoader.getIcon(Icons.SAVE));
        saveSubFolderTemplateButton.setToolTipText(lang.get("tooltip.saveTemplateToTemplatesList"));
        saveSubFolderTemplateButton.setEnabled(false);

        removeFileNameTemplateButton = new JButton(IconLoader.getIcon(Icons.REMOVE));
        removeFileNameTemplateButton.setToolTipText(lang.get("tooltip.deleteTemplateFromTemplateList"));
        removeFileNameTemplateButton.setEnabled(false);

        removeSubFolderTemplateButton = new JButton(IconLoader.getIcon(Icons.REMOVE));
        removeSubFolderTemplateButton.setToolTipText(lang.get("tooltip.deleteTemplateFromTemplateList"));
        removeSubFolderTemplateButton.setEnabled(false);

        GBHelper inputPos = new GBHelper();
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.add(destinationPathLabel, inputPos);
        inputPanel.add(destinationPathTextField, inputPos.nextRow());
        inputPanel.add(destinationPathButton, inputPos.nextCol());
        inputPanel.add(Box.createVerticalStrut(4), inputPos.nextRow());
        inputPanel.add(subFolderLabel, inputPos.nextRow());
        inputPanel.add(subFolderTextField, inputPos.nextRow());
        inputPanel.add(saveSubFolderTemplateButton, inputPos.nextCol());
        inputPanel.add(Box.createVerticalStrut(4), inputPos.nextRow());
        inputPanel.add(subFolderTemplateComboBox, inputPos.nextRow());
        inputPanel.add(removeSubFolderTemplateButton, inputPos.nextCol());
        inputPanel.add(Box.createVerticalStrut(4), inputPos.nextRow());
        inputPanel.add(fileNameTemplateLabel, inputPos.nextRow());
        inputPanel.add(fileNameTemplateTextField, inputPos.nextRow());
        inputPanel.add(saveFileNameTemplateButton, inputPos.nextCol());
        inputPanel.add(Box.createVerticalStrut(4), inputPos.nextRow());
        inputPanel.add(fileNameTemplateComboBox, inputPos.nextRow());
        inputPanel.add(removeFileNameTemplateButton, inputPos.nextCol());

        GBHelper posBackground = new GBHelper();
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""),new EmptyBorder(2, 2, 2, 2)));
        backgroundPanel.add(inputPanel, posBackground.align(GridBagConstraints.NORTHWEST));
        backgroundPanel.add(Box.createHorizontalStrut(10), posBackground.nextCol());
        backgroundPanel.add(new VariablesPanel(), posBackground.nextCol());
        backgroundPanel.add(Box.createVerticalStrut(5), posBackground.nextRow());
        backgroundPanel.add(createThumbNailsCheckBox, inputPos.nextRow().nextCol().nextCol());
        backgroundPanel.add(startProcessButton, inputPos.nextRow().nextCol().nextCol().align(GridBagConstraints.EAST));
        backgroundPanel.add(Box.createVerticalGlue(), inputPos.nextRow().expandH());

        return backgroundPanel;
    }

    private JPanel createCategoryAndRatingPanel() {

        JLabel categorizeHeading = new JLabel(lang.get("findimage.categories.label"));
        categorizeHeading.setForeground(Color.GRAY);

        categoriesTree = CategoryUtil.createCategoriesTree();
        categoriesTree.addMouseListener(categoriesMouseButtonListener);
        categoriesTree.setDragEnabled(true);
        categoriesTree.setTransferHandler(new CategoriesTransferHandler());
        categoriesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        categoriesTree.setDropMode(DropMode.ON_OR_INSERT);
        categoriesTree.setExpandsSelectedPaths(false);
        ((DefaultTreeCellRenderer)categoriesTree.getCellRenderer()).setLeafIcon(null);

        // makes your tree as CheckTree
        checkTreeManagerForAssignCategoriesCategoryTree = new CheckTreeManager(categoriesTree, false, null, true);

        JScrollPane categoriesScrollPane = new JScrollPane();
        categoriesScrollPane.getViewport().add(categoriesTree);

        JLabel ratingLabel = new JLabel(lang.get("findimage.rating.label"));
        ratingLabel.setForeground(Color.GRAY);

        GBHelper posBackground = new GBHelper();
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""),new EmptyBorder(2, 2, 2, 2)));

        posBackground.fill = GridBagConstraints.BOTH;

        backgroundPanel.add(categorizeHeading, posBackground);
        backgroundPanel.add(Box.createVerticalStrut(2), posBackground.nextRow());
        backgroundPanel.add(categoriesScrollPane, posBackground.nextRow().expandH().expandW());
        backgroundPanel.add(Box.createVerticalStrut(2), posBackground.nextRow());
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
                    jrb.setToolTipText(lang.get("findimage.rating.tooltip.bad"));
                    break;
                case 5:
                    jrb.setToolTipText(lang.get("findimage.rating.tooltip.good"));
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

        GBHelper posBorderPanel = new GBHelper();

        JPanel borderPanel = new JPanel(new GridBagLayout());
        borderPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        borderPanel.add(backgroundPanel, posBorderPanel.expandH().expandW());

        return borderPanel;
    }

    private JPanel createPreviewAndCommentPanel() {

        JLabel previewHeading = new JLabel(lang.get("findimage.preview.label"));
        previewHeading.setForeground(Color.GRAY);

        imageTagPreviewLabel = new JLabel();

        GBHelper posImageTagPreviewPanel = new GBHelper();
        JPanel imageTagPreviewPanel = new JPanel(new GridBagLayout());

        imageTagPreviewPanel.add(imageTagPreviewLabel, posImageTagPreviewPanel.expandH().expandW());

        imageTagPreviewScrollPane = new JScrollPane(imageTagPreviewPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JLabel commentHeading = new JLabel(lang.get("findimage.comment.label"));
        commentHeading.setForeground(Color.GRAY);

        imageCommentTextArea = new JTextArea();
        imageCommentTextArea.setLineWrap(true);
        imageCommentTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(imageCommentTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        GBHelper posTop = new GBHelper();

        topPanel.add(previewHeading, posTop);
        topPanel.add(Box.createVerticalStrut(2), posTop.nextRow());
        topPanel.add(imageTagPreviewScrollPane, posTop.nextRow().expandH().expandW());
        topPanel.add(Box.createVerticalStrut(2), posTop.nextRow());

        GBHelper posBackgroundTop = new GBHelper();

        JPanel backgroundTopPanel = new JPanel(new GridBagLayout());
        backgroundTopPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        backgroundTopPanel.add(topPanel, posBackgroundTop.expandH().expandW());

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        GBHelper posBottom = new GBHelper();

        bottomPanel.add(commentHeading, posBottom);
        bottomPanel.add(Box.createVerticalStrut(2), posBottom.nextRow());
        bottomPanel.add(scrollPane, posBottom.nextRow().expandH().expandW());

        GBHelper posBackgroundBottom = new GBHelper();

        JPanel backgroundBottomPanel = new JPanel(new GridBagLayout());
        backgroundBottomPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        backgroundBottomPanel.add(bottomPanel, posBackgroundBottom.expandH().expandW());

        GUI gUI = configuration.getgUI();

        previewAndCommentSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        previewAndCommentSplitPane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(gUI.getMain().getGuiWindowSplitPane(), ConfigElement.PREVIEW_AND_COMMENT));
        previewAndCommentSplitPane.setTopComponent(backgroundTopPanel);
        previewAndCommentSplitPane.setBottomComponent(backgroundBottomPanel);

        GBHelper posBackground = new GBHelper();
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.add(previewAndCommentSplitPane, posBackground.expandH().expandW());

        return backgroundPanel;
    }

    private JScrollPane initiateJTree() {

        // the File tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode();
        DefaultTreeModel treeModel = new DefaultTreeModel(root);

        TreeSelectionListener treeSelectionListener = new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent tse){
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)tse.getPath().getLastPathComponent();
                showChildren(node);
            }
        };

        // show the file system roots.
        File[] roots = fileSystemView.getRoots();
        for (File fileSystemRoot : roots) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(fileSystemRoot);
            root.add( node );
            File[] files = fileSystemView.getFiles(fileSystemRoot, true);
            for (File file : files) {
                if (file.isDirectory()) {
                    node.add(new DefaultMutableTreeNode(file));
                }
            }
        }

        tree = new JTree(treeModel);
        tree.setRootVisible(false);
        tree.addTreeSelectionListener(treeSelectionListener);
        tree.setCellRenderer(new FileTreeCellRenderer());
        tree.addMouseListener(mouseListener = new FileSystemDirectoryTreeMouseListener());
        tree.setShowsRootHandles (true);
        tree.expandRow(0);

        showRootFile();
        return new JScrollPane(tree);
    }

    private void showChildren(final DefaultMutableTreeNode node) {
        tree.setEnabled(false);

        File file = (File) node.getUserObject();
        if (file.isDirectory()) {
            File[] files = fileSystemView.getFiles(file, true);

            Arrays.sort(files);

            if (node.isLeaf()) {
                for (File child : files) {
                    if (child.isDirectory()) {
                        node.add(new DefaultMutableTreeNode(child));
                    }
                }
            }
        }
        tree.setEnabled(true);
    }

    public void showRootFile() {
        // ensure the main files are displayed
        tree.setSelectionInterval(0, 0);
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
        exportCategoryTreeStructureJMenuItem.addActionListener(new MenuListener());
        importCategoryTreeStructureJMenuItem.addActionListener(new MenuListener());

        subFolderTextField.getDocument().addDocumentListener(new JTextFieldListener());
        fileNameTemplateTextField.getDocument().addDocumentListener(new JTextFieldListener());
        tabbedPane.addChangeListener(new JTabbedPaneListener());
        createThumbNailsCheckBox.addActionListener(new CheckBoxListener());
        thumbNailsBackgroundsPanel.addComponentListener(new ComponentListener());

        popupMenuCopyImageToClipBoard.addActionListener(new CopyImageToSystemClipBoard());

        popupMenuCopyAllImagesToClipBoard.addActionListener(new CopyAllImagesToSystemClipBoard());

        SelectAllImagesAction selectAllImagesAction = new SelectAllImagesAction();
        selectAllImagesAction.putValue(Action.NAME, lang.get("maingui.popupmenu.selectAllImages"));
        popupMenuSelectAllImages.setAction(selectAllImagesAction);

        DeSelectAllImagesAction deSelectAllImagesAction = new DeSelectAllImagesAction();
        deSelectAllImagesAction.putValue(Action.NAME, lang.get("maingui.popupmenu.deSelectAllImages"));
        popupMenuDeSelectAllImages.setAction(deSelectAllImagesAction);

        CopySelectedImagesAction copySelectedImagesAction = new CopySelectedImagesAction();
        copySelectedImagesAction.putValue(Action.NAME, lang.get("maingui.popupmenu.copySelectedToSystemClipboard"));
        popupMenuCopySelectedImagesToClipBoard.setAction(copySelectedImagesAction);

        popupMenuAddImageToViewList.addActionListener(new AddImageToViewList());
        popupMenuAddSelectedImagesToViewList.addActionListener(new AddSelectedImagesToViewList());
        popupMenuAddAllImagesToViewList.addActionListener(new AddAllImagesToViewList());
        popupMenuAddCategory.addActionListener(new AddCategory());
        popupMenuRenameCategory.addActionListener(new RenameCategory());
        popupMenuRemoveCategory.addActionListener(new RemoveCategory());
        popupMenuSaveSelectedCategoriesToSelectedImages.addActionListener(new SaveSelectedCategoriesToSelectedImages());
        popupMenuSaveSelectedCategoriesToAllImages.addActionListener(new SaveSelectedCategoriesToAllImages());
        popupMenuCollapseCategoriesTreeStructure.addActionListener(new CollapseCategoryTreeStructure());
        popupMenuExpandCategoriesTreeStructure.addActionListener(new ExpandCategoryTreeStructure());
        popupMenuAddImagePathToImageRepository.addActionListener(addSelectedPathToImageRepository = new AddSelectedPathToImageRepository());
        popupMenuRemoveImagePathFromImageRepository.addActionListener(new RemoveSelectedPathFromImageRepository());
        popupMenuAddDirectoryToAlwaysAutomaticallyAddToImageRepositoryList.addActionListener(new AddDirectoryToAlwaysAutomaticallyAddToImageRepositoryList());
        popupMenuAddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList.addActionListener(new AddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList());

        mainTabbedPane.addChangeListener(new MainTabbedPaneListener());

        saveFileNameTemplateButton.addActionListener(new SaveFileNameTemplateButtonListener());
        saveSubFolderTemplateButton.addActionListener(new SaveSubFolderTemplateButtonListener());
        removeFileNameTemplateButton.addActionListener(new RemoveFileNameTemplateButtonListener());
        removeSubFolderTemplateButton.addActionListener(new RemoveSubFolderTemplateButtonListener());

        fileNameTemplateComboBox.addActionListener(new FileNameTemplateComboBoxListener());
        subFolderTemplateComboBox.addActionListener(new SubFolderTemplateComboBoxListener());
        imageTagPreviewScrollPane.addComponentListener(new ImageTagPreviewScrollPaneListener());
    }

    private class SelectAllImagesAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            selectAllImages();
        }
    }

    private class DeSelectAllImagesAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            deSelectAllImages(e, null);
        }
    }

    private void deSelectAllImages(ActionEvent e, JToggleButton toggleButton) {
        // if the Ctrl key is NOT pressed, then clear all selection.
        if (!((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK)) {
            loadedThumbnails.clearSelections();
        }

        if (toggleButton != null) {
            loadedThumbnails.removeSelection(toggleButton);
        }

        storeCurrentlySelectedImageData();
        ImageMetaDataDataBaseItemsToUpdateContext.getInstance().setCurrentlySelectedImage(null);
        clearTagTab();
        setRatingCommentAndCategoryEnabled(false);
        imageMetaDataPanel.clearMetaData();
    }

    private void selectAllImages() {
        if (loadedThumbnails  != null && !loadedThumbnails.isEmpty()) {
            while (!selectedImageIconGenerator.isDone()) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e1) {
                }
            }

            JToggleButton firstJToggleButton = null;
            JToggleButton jToggleButtonToSelect = null;

            for (JToggleButton jToggleButton : thumbNailsPanel.getJToggleButtons()) {
                if (firstJToggleButton == null) {
                    firstJToggleButton = jToggleButton;
                }

                if (jToggleButton.isSelected()) {
                    jToggleButtonToSelect = jToggleButton;
                    break;
                }
            }

            if (jToggleButtonToSelect == null) {
                jToggleButtonToSelect = firstJToggleButton;
            }

            jToggleButtonToSelect.setSelected(true);
            ActionEvent actionEvent = new  ActionEvent(jToggleButtonToSelect, ActionEvent.ACTION_PERFORMED, jToggleButtonToSelect.getActionCommand());
            for (ActionListener listener : jToggleButtonToSelect.getActionListeners()) {
                listener.actionPerformed(actionEvent);
            }

            for (JToggleButton jToggleButton : thumbNailsPanel.getJToggleButtons()) {
                if (!jToggleButton.isSelected()) {
                    jToggleButton.setSelected(true);
                    File imageFile = new File(jToggleButton.getActionCommand());
                    jToggleButton.setIcon(imageFileToSelectedImageMapping.get(imageFile));
                }
            }
        }
    }

    private class CopySelectedImagesAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (loadedThumbnails.size() > 0) {
                FileSelection fileSelection = new FileSelection(loadedThumbnails.getSelectedAsFileObjects());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
            }
        }
    }

    public void createRightClickMenuCategories() {
        rightClickMenuCategories = new JPopupMenu();
        rightClickMenuCategories.add(popupMenuCollapseCategoriesTreeStructure = new JMenuItem());
        rightClickMenuCategories.add(popupMenuExpandCategoriesTreeStructure = new JMenuItem());
        rightClickMenuCategories.addSeparator();
        rightClickMenuCategories.add(popupMenuAddCategory = new JMenuItem());
        rightClickMenuCategories.add(popupMenuRenameCategory = new JMenuItem());
        rightClickMenuCategories.add(popupMenuRemoveCategory = new JMenuItem());
        rightClickMenuCategories.add(popupMenuSaveSelectedCategoriesToSelectedImages = new JMenuItem());
        rightClickMenuCategories.add(popupMenuSaveSelectedCategoriesToAllImages = new JMenuItem());
    }

    public void createRightClickMenu(){
        popupMenuCopyImageToClipBoard = new JMenuItem(lang.get("maingui.popupmenu.copyToSystemClipboard"));
        popupMenuCopySelectedImagesToClipBoard = new JMenuItem();
        popupMenuCopyAllImagesToClipBoard = new JMenuItem(lang.get("maingui.popupmenu.copyAllToSystemClipboard"));
        popupMenuAddImagePathToImageRepository = new JMenuItem(lang.get("maingui.popupmenu.addImagePathToImageRepository"));
        popupMenuRemoveImagePathFromImageRepository = new JMenuItem(lang.get("maingui.popupmenu.removeImagePathFromImageRepository"));
        popupMenuAddImageToViewList = new JMenuItem(lang.get("maingui.popupmenu.addImageToList"));
        popupMenuAddSelectedImagesToViewList = new JMenuItem(lang.get("maingui.popupmenu.addSelectedImagesToList"));
        popupMenuAddAllImagesToViewList = new JMenuItem(lang.get("maingui.popupmenu.addAllImagesToList"));
        popupMenuSelectAllImages = new JMenuItem(lang.get("maingui.popupmenu.selectAllImages"));
        popupMenuDeSelectAllImages = new JMenuItem(lang.get("maingui.popupmenu.deSelectAllImages"));

        rightClickMenu = new JPopupMenu();
        rightClickMenu.add(popupMenuAddImagePathToImageRepository);
        rightClickMenu.add(popupMenuRemoveImagePathFromImageRepository);
        rightClickMenu.addSeparator();
        rightClickMenu.add(popupMenuCopyImageToClipBoard);
        rightClickMenu.add(popupMenuCopySelectedImagesToClipBoard);
        rightClickMenu.add(popupMenuCopyAllImagesToClipBoard);
        rightClickMenu.addSeparator();
        rightClickMenu.add(popupMenuAddImageToViewList);
        rightClickMenu.add(popupMenuAddSelectedImagesToViewList);
        rightClickMenu.add(popupMenuAddAllImagesToViewList);
        rightClickMenu.addSeparator();
        rightClickMenu.add(popupMenuSelectAllImages);
        rightClickMenu.add(popupMenuDeSelectAllImages);
    }

    public void createRightClickMenuDirectoryTree() {
        popupMenuAddDirectoryToAlwaysAutomaticallyAddToImageRepositoryList = new JMenuItem(lang.get("imagerepository.addDirectoryToAllwaysAddAutomaticallyList.label"));
        popupMenuAddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList = new JMenuItem(lang.get("imagerepository.addDirectoryToNeverAddAutomaticallyList.label"));

        rightClickMenuDirectoryTree = new JPopupMenu();
        rightClickMenuDirectoryTree.add(popupMenuAddDirectoryToAlwaysAutomaticallyAddToImageRepositoryList);
        rightClickMenuDirectoryTree.add(popupMenuAddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList);
    }

    public void initiateProgram(){

        selectLastSelectedTab();
        Update.updateAllUIs();
    }

    /**
     * Selects the tab which was selected the last time JavaPEG was running.
     */
    private void selectLastSelectedTab() {
        GUI gUI = configuration.getgUI();

        // Force a state changed of the tabs, which in turn makes the title of
        // the selected tab bold, if the last selected tab is the first tab,
        // which by construction also is the tab to be automatically selected,
        // and therefore is no state changed, when that tab is programmatically
        // selected in this method.
        if (gUI.getSelectedMainGUITab().getGuiOrder() == 0) {
            mainTabbedPane.setSelectedIndex(mainTabbedPane.getTabCount() - 1);
        }

        mainTabbedPane.setSelectedIndex(gUI.getSelectedMainGUITab().getGuiOrder());
    }

    public void initiateApplicationContext() {
        ApplicationContext ac = ApplicationContext.getInstance();
        // Disabled to avoid NPE:s with current model of load previous path at application start (Not loading)
//        ac.setSourcePath(config.getStringProperty("sourcePath"));

        RenameImages renameImages = configuration.getRenameImages();

        ac.setTemplateFileName(renameImages.getTemplateFileName());
        ac.setTemplateSubFolderName(renameImages.getTemplateSubDirectoryName());
        ac.setCreateThumbNailsCheckBoxSelected(renameImages.getCreateThumbNails());
        ac.setMainTabbedPaneComponent(MainTabbedPaneComponent.valueOf(mainTabbedPane.getSelectedComponent().getName()));
    }

    private void saveSettings(){
        RenameImages renameImages = configuration.getRenameImages();

        renameImages.setPathSource(ApplicationContext.getInstance().getSourcePath());

        if(!destinationPathTextField.getText().equals("")) {
            renameImages.setPathDestination(new File(destinationPathTextField.getText()));
        }

        renameImages.setTemplateSubDirectoryName(subFolderTextField.getText());
        renameImages.setTemplateFileName(fileNameTemplateTextField.getText());

        TreeSet<String> fileNameTemplates = new TreeSet<>();
        for (int index = 0; index < fileNameTemplateComboBox.getModel().getSize(); index++) {
            fileNameTemplates.add(fileNameTemplateComboBox.getModel().getElementAt(index));
        }
        renameImages.setTemplateFileNameNames(fileNameTemplates);

        TreeSet<String> subDirectoryTemplates = new TreeSet<>();
        for (int index = 0; index < subFolderTemplateComboBox.getModel().getSize(); index++) {
            subDirectoryTemplates.add(subFolderTemplateComboBox.getModel().getElementAt(index));
        }
        renameImages.setTemplateSubDirectoryNames(subDirectoryTemplates);
        renameImages.setCreateThumbNails(createThumbNailsCheckBox.isSelected());

        TagImages tagImages = configuration.getTagImages();
        TagImagesCategories tagImagesCategories = tagImages.getCategories();
        tagImagesCategories.setOrRadioButtonIsSelected(imageSearchTab.isOrRadioButtonSelected());

        GUI gUI = configuration.getgUI();

        if (this.isVisible()) {
            Rectangle sizeAndLocation = gUI.getMain().getSizeAndLocation();

            sizeAndLocation.setSize(this.getSize().width, this.getSize().height);
            sizeAndLocation.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);
        }

        gUI.setSelectedMainGUITab(SelectedMainGUITab.getEnumeration(mainTabbedPane.getSelectedIndex()));

        List<GUIWindowSplitPane> guiWindowSplitPanes = gUI.getMain().getGuiWindowSplitPane();

        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.MAIN, mainSplitPane.getDividerLocation());
        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.MAIN_TO_IMAGELIST, mainSplitPaneImageViewListSplitPane.getDividerLocation());
        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.VERTICAL, verticalSplitPane.getDividerLocation());
        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.THUMB_NAIL_META_DATA_PANEL, thumbNailMetaPanelSplitPane.getDividerLocation());
        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.PREVIEW_AND_COMMENT, previewAndCommentSplitPane.getDividerLocation());
        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.PREVIEW_COMMENT_CATEGORIES_RATING, previewCommentCategoriesRatingSplitPane.getDividerLocation());
        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.IMAGE_MERGE_DIRECTORIES_TO_PROCESS_LOG, imageMergeTab.getImageMergeDirectoriesToProcessLogSplitPaneDividerLocation());
        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.IMAGE_SEARCH_IMAGE_EXIF_META_DATA_TO_RATING_COMMENT_AND_BUTTON, imageSearchTab.getImageExifMetaDataToRatingCommentAndButtonPanelSplitPaneDividerLocation());
        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.IMAGE_SEARCH_CATEGORIES_TO_IMAGE_EXIF_META_DATA_AND_RATING_COMMENT_AND_BUTTON, imageSearchTab.getCategoriesToImageExifMetaDataAndRatingCommentAndButtonPanelSplitPaneDividerLocation());

        List<File> paths = imageRepositoriesTableModel.getPaths();

        RepositoryPaths repositoryPaths = configuration.getRepository().getPaths();
        repositoryPaths.setPaths(paths);

        Config.getInstance().save();
    }

    private void addThumbnail(JToggleButton thumbNail) {
        thumbNailsPanel.add(thumbNail);
    }

    private void updateGUI() {
        thumbNailsPanel.updateUI();
    }

    private void validateInputInRealtime() {
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

    private void setInputsEnabled(boolean state) {
        destinationPathButton.setEnabled(state);
        openDestinationFileChooserJMenuItem.setEnabled(state);
        fileNameTemplateTextField.setEnabled(state);
        fileNameTemplateComboBox.setEnabled(state);
        subFolderTextField.setEnabled(state);
        subFolderTemplateComboBox.setEnabled(state);
        createThumbNailsCheckBox.setEnabled(state);
        startProcessButton.setEnabled(state);
        startProcessJMenuItem.setEnabled(state);
        tree.setEnabled(state);
        saveFileNameTemplateButton.setEnabled(state);
        saveSubFolderTemplateButton.setEnabled(state);
        removeFileNameTemplateButton.setEnabled(state);
        removeSubFolderTemplateButton.setEnabled(state);
    }

    private void closeApplication(int exitValue) {
        if(exitValue == 0) {
            saveSettings();
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
        JOptionPane.showMessageDialog(this, informationMessage, lang.get("common.information"), JOptionPane.INFORMATION_MESSAGE);
    }

    private void displayErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(this, errorMessage, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
    }

    public String displayInputDialog(String title, String label, String initialValue) {
        return CategoryUtil.displayInputDialog(this, title, label, initialValue);
    }

    private void importCategories() {

        CategoryImportExportPopup ciep = new CategoryImportExportPopup(true, lang.get("categoryimportexport.import.long.title"), new Rectangle(100, 100, 500,200), null);
        if (ciep.isActionButtonClicked()) {

            ImportedCategories importedCategoriesFromFile = CategoriesConfig.importCategoriesConfig(ciep.getCategoryFileToImportExport());

            String importedCategoriesJavePegId = importedCategoriesFromFile.getJavaPegId();

            ImportedCategories importedCategories = new ImportedCategories();
            importedCategories.setDisplayName(ciep.getFileName());
            importedCategories.setHighestUsedId(importedCategoriesFromFile.getHighestUsedId());
            importedCategories.setJavaPegId(importedCategoriesJavePegId);
            importedCategories.setRoot(importedCategoriesFromFile.getRoot());

            Map<String, ImportedCategories> importedCategoriesConfig = configuration.getImportedCategoriesConfig();

            ApplicationContext ac = ApplicationContext.getInstance();

            if (importedCategoriesConfig.containsKey(importedCategoriesJavePegId)) {
                if (importedCategoriesFromFile.getHighestUsedId() >= importedCategoriesConfig.get(importedCategoriesJavePegId).getHighestUsedId()) {

                    // Check if there already is an imported categories from
                    // currently imported JavaPEG client but with a different
                    // name than entered in the import dialog.
                    String currentDispayName = importedCategoriesConfig.get(importedCategoriesJavePegId).getDisplayName();

                    boolean skipNameTest = false;

                    if (!ciep.getFileName().equals(currentDispayName)) {
                        switch (displayConfirmDialog(String.format(lang.get("categoryimportexport.alreadyImportedWithAnotherName"), currentDispayName, ciep.getFileName()), lang.get("errormessage.maingui.warningMessageLabel"), JOptionPane.YES_NO_OPTION)) {
                        case JOptionPane.YES_OPTION:
                            importedCategories.setDisplayName(currentDispayName);
                            skipNameTest = true;
                            break;
                        default:
                            // Do nothing. The correct name is already set.
                            break;
                        }
                    }

                    if (!skipNameTest) {
                        if (CategoryUtil.displayNameAlreadyInUse(importedCategories.getDisplayName(), importedCategoriesConfig.values()) || importedCategories.getDisplayName().trim().length() == 0) {
                            importedCategories.setDisplayName(CategoryUtil.askForANewDisplayName(this, importedCategories.getDisplayName(), importedCategoriesConfig));
                        }
                    }

                    importedCategoriesConfig.put(importedCategoriesFromFile.getJavaPegId(), importedCategories);
                    ac.setRestartNeeded();
                } else {
                    displayInformationMessage(lang.get("categoryimportexport.newerVersionAlreadyImported"));
                }
            } else {
                if (CategoryUtil.displayNameAlreadyInUse(importedCategories.getDisplayName(), importedCategoriesConfig.values())) {
                    importedCategories.setDisplayName(CategoryUtil.askForANewDisplayName(this, importedCategories.getDisplayName(), importedCategoriesConfig));
                }

                importedCategoriesConfig.put(importedCategoriesFromFile.getJavaPegId(), importedCategories);
                ac.setRestartNeeded();
            }

            if (ac.isRestartNeeded()) {
              displayInformationMessage(lang.get("common.application.restart.needed"));
            }
        }
    }

    private void exportCategories() {

        CategoryImportExportPopup ciep = new CategoryImportExportPopup(false, lang.get("categoryimportexport.export.long.title"), new Rectangle(100, 100, 300, 200), null);

        if (ciep.isActionButtonClicked()) {
            File directoryToExportCategoriesTo = ciep.getCategoryFileToImportExport();

            if (FileUtil.testWriteAccess(directoryToExportCategoriesTo)) {
                OutputStream os = null;
                File categoryExportFile = null;

                try {
                    categoryExportFile = new File(directoryToExportCategoriesTo, ciep.getFileName() + ".cml");
                    os = new FileOutputStream(categoryExportFile);

                    XMLOutputFactory factory = XMLOutputFactory.newInstance();
                    XMLStreamWriter xmlsw = factory.createXMLStreamWriter(os, C.UTF8);

                    CategoriesConfig.exportCategoriesConfig(configuration.getCategories(), configuration.getJavapegClientId(), ApplicationContext.getInstance().getHighestUsedCategoryID(), xmlsw);

                    displayInformationMessage(lang.get("categoryimportexport.categoryImportExportExport.exported") + " " + categoryExportFile.getAbsolutePath());
                } catch (FileNotFoundException | XMLStreamException ex) {
                 categoryExportError(categoryExportFile, ex);
                } finally {
                    StreamUtil.close(os, true);
                }
            } else {
                displayErrorMessage(lang.get("categoryimportexport.export.noWriteAccess") + "(" + directoryToExportCategoriesTo.getAbsolutePath() + ")");
                logger.logWARN("No write access to directory: " + directoryToExportCategoriesTo.getAbsolutePath());
            }
        }
    }

    private void categoryExportError(File categoryExportFile, Exception ex) {
        displayErrorMessage(lang.get("categoryimportexport.export.error") + " " + categoryExportFile.getAbsolutePath());
        logger.logERROR("Could not export categories to: " + categoryExportFile.getAbsolutePath());
        logger.logERROR(ex);
    }

    // WindowDestroyer
    private class WindowDestroyer extends WindowAdapter{
        @Override
        public void windowClosing (WindowEvent e){
            closeApplication(0);
        }
    }

    // Menylyssnarklass
    private class MenuListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String actionCommand = e.getActionCommand();

            if(actionCommand.equals(lang.get("menu.item.exit"))){
                closeApplication(0);
            } else if(actionCommand.equals(lang.get("menu.item.openDestinationFileChooser"))) {
                destinationPathButton.doClick();
            } else if(actionCommand.equals(lang.get("menu.item.startProcess"))) {
                startProcessButton.doClick();
            } else if (actionCommand.equals(lang.get("menu.item.about")))    {
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
            } else if (actionCommand.equals(lang.get("categoryimportexport.export.long.title"))) {
                exportCategories();
            } else if (actionCommand.equals(lang.get("categoryimportexport.import.long.title"))) {
                importCategories();
            }
        }
    }

    // Knapplyssnarklass
    private class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            String actionCommand = e.getActionCommand();

            if(actionCommand.equals("destinationPathButton")) {
                destinationPathTextField.setEditable(true);

                RenameImages renameImages = configuration.getRenameImages();

                // Kontrollera s� att sparad s�kv�g fortfarande existerar
                // och i annat fall hoppa upp ett steg i tr�dstrukturen och
                // kontrollera ifall den s�kv�gen existerar
                File pathDestination = renameImages.getPathDestination();

                boolean exists = false;
                while(!exists) {
                    try {
                        if(!pathDestination.exists()) {
                            pathDestination = pathDestination.getParentFile();
                        } else {
                            exists = true;
                        }
                    } catch (NullPointerException npe) {
                        FileSystemView fsv = FileSystemView.getFileSystemView();
                        pathDestination = fsv.getDefaultDirectory();
                        exists = true;
                    }
                }

                JFileChooser chooser = new JFileChooser(pathDestination.getAbsolutePath());
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setDialogTitle(lang.get("fileSelectionDialog.destinationPathFileChooser"));

                if(chooser.showOpenDialog(MainGUI.this) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = chooser.getSelectedFile();

                    if (!ApplicationContext.getInstance().getSourcePath().equals(selectedFile)) {

                        ApplicationContext.getInstance().setDestinationPath(selectedFile.getAbsolutePath());
                        destinationPathTextField.setText(selectedFile.getAbsolutePath());
                        renameImages.setPathDestination(new File(destinationPathTextField.getText()));

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

                    // Remove potential white spaces at the start and end of the
                    // sub directory template
                    String subFolderName = subFolderTextField.getText();
                    subFolderName = subFolderName.trim();
                    subFolderTextField.setText(subFolderName);

                    // Remove potential white spaces at the start and end of the
                    // file name template
                    String fileNameTemplate = fileNameTemplateTextField.getText();
                    fileNameTemplate = fileNameTemplate.trim();
                    fileNameTemplateTextField.setText(fileNameTemplate);

                    Thread renameThread = new Thread() {

                        @Override
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

        @Override
        public void insertUpdate(DocumentEvent e) {
            validateInputInRealtime();
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
           if (fileNameTemplateTextField.hasFocus() || subFolderTextField.hasFocus()) {
               validateInputInRealtime();
           }
        }
        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }

    private class JTabbedPaneListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent evt) {
            if(tabbedPane.getSelectedIndex() == 1) {
                updatePreviewTable();
            }
        }
    }

    private class CheckBoxListener implements ActionListener {
        @Override
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

        // Rensa en eventuellt ifylld filnamnsf�rhandsgranskningstabell.
        // Detta kan ske d� det redan �ppnats bilder tidigare och dessa
        // f�tt f�rhandsgranskning p� sina filnamn
        previewTableModel.setRowCount(0);

        // Clear the Panel with meta data from potentially already
        // shown meta data
        imageMetaDataPanel.clearMetaData();

        metaDataTableModel.setColumnCount(0);
        metaDataTableModel.setRowCount(0);

        // Remove all references of any old selected thumbnail buttons and
        // finally clear the entire list of the selected buttons.
        loadedThumbnails.clearSelections();
        loadedThumbnails.clear();
    }

    private void executeLoadThumbnailsProcess() {

        Thread thumbNailsFetcher = new Thread() {

            @Override
            public void run(){
                try {
                    getThis().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                    boolean bufferContainsImages = true;
                    while (loadFilesThread.isAlive() || bufferContainsImages) {

                        ApplicationContext ac = ApplicationContext.getInstance();

                        final File jpegFile = ac.handleJpegFileLoadBuffer(null, FileLoadingAction.RETRIEVE);

                        if(jpegFile != null) {

                            JPEGThumbNail tn = JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(jpegFile);

                            ImageIcon imageIcon = new ImageIcon(tn.getThumbNailData());
                            imageIcon = ImageUtil.rotateIfNeeded(imageIcon, tn.getMetaData());

                            JToggleButton thumbContainer = new JToggleButton();

                            thumbContainer.setIcon(imageIcon);
                            thumbContainer.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

                            ToolTips toolTips = configuration.getToolTips();

                            if (!toolTips.getOverviewState().equals("0")) {
                                thumbContainer.setToolTipText(MetaDataUtil.getToolTipText(jpegFile, toolTips.getOverviewState()));
                            }
                            thumbContainer.setActionCommand(jpegFile.getAbsolutePath());

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
                            thumbnailLoadingProgressBar.setMaximum(ac.getNrOfFilesInSourcePath() - FileRetriever.getInstance().getNonJPEGFiles().size());
                            thumbnailLoadingProgressBar.setValue(thumbnailLoadingProgressBar.getValue() + 1);
                            loadedThumbnails.add(thumbContainer);
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
                    thumbnailLoadingProgressBar.setValue(0);
                    thumbnailLoadingProgressBar.setVisible(false);
                    addMouseListener();
                    setInputsEnabled(true);
                    loadedThumbnails.addActionListener(thumbNailListener);
                    loadedThumbnails.addMouseListener(mouseRightClickButtonListener);
                    startProcessButton.setEnabled(setStartProcessButtonState());
                    startProcessJMenuItem.setEnabled(setStartProcessButtonState());

                    Table.packColumns(metaDataTable, 6);
                } finally {
                    getThis().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

                    // Start the background task of "creating selected" images
                    //for the JToggleButtons.
                    selectedImageIconGenerator = new SelectedImageIconGenerator(loadedThumbnails, imageFileToSelectedImageMapping);
                    selectedImageIconGenerator.execute();
                }
            }
        };
        thumbNailsFetcher.start();
        setStatusMessages();
    }

    private void loadThumbNails(final File sourcePath) {
        this.prepareLoadThumbnailsProcess();

        ApplicationContext ac = ApplicationContext.getInstance();

        ac.setSourcePath(sourcePath);

        final File[] filesInSourcePath = DirectoryUtil.listFilesInAscendingDateOrder(sourcePath);

        ac.setNrOfFilesInSourcePath(filesInSourcePath.length);

        RenameImages renameImages = configuration.getRenameImages();

        renameImages.setPathSource(sourcePath);

        statusBar.setStatusMessage(lang.get("statusbar.message.selectedPath") + " " + sourcePath.getAbsolutePath(), lang.get("statusbar.message.selectedPath"), 0);

        loadFilesThread = new Thread() {
            @Override
            public void run() {
                if(sourcePath.isDirectory()) {
                    try {
                        FileRetriever.getInstance().loadFilesFromDisk(Arrays.asList(filesInSourcePath));
                    } catch (Throwable sex) {
                        logger.logERROR("Can not list files in directory: " + sourcePath.getAbsolutePath());
                        logger.logERROR(sex);
                        JOptionPane.showMessageDialog(null, lang.get("common.message.error.canNotListFilesInDirectory")+ " " + sourcePath.getAbsolutePath(), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        };
        loadFilesThread.start();

        thumbnailLoadingProgressBar.setMinimum(0);
        thumbnailLoadingProgressBar.setMaximum(ac.getNrOfFilesInSourcePath());
        thumbnailLoadingProgressBar.setValue(0);
        thumbnailLoadingProgressBar.setVisible(true);

        metaDataTableModel.setColumns();

        this.executeLoadThumbnailsProcess();

        // Byta till metadata-tabben ifall tabben skulle st� i annat l�ge.
        tabbedPane.setSelectedIndex(0);
    }

    /**
     * Performs a check to see if the current executing is poart of the
     * supported operating systems, if not, an {@link RuntimeException} is
     * thrown.
     */
    private void checkIfOperatingSystemIsSupported() {
        if (!OsUtil.getOsName().toLowerCase().contains("windows")) {
            throw new RuntimeException("Unsupported Operating system: " + OsUtil.getOsName());
        }
    }

    /**
     * This listener class listens for mouse clicks made on the
     * FilesSystemDirectory three and performs the appropriate actions depending
     * on if it was a "right click" or "left click" with the mouse.
     */
    private class FileSystemDirectoryTreeMouseListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent event){
            // If no row in the tree structure is clicked, then do nothing...
            if(tree.getRowForLocation(event.getX(), event.getY()) == -1) {
                return;
            }

            // Abort if the current operating system is unsupported.
            checkIfOperatingSystemIsSupported();

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
            File selectedFile = (File)selectedNode.getUserObject();
            String totalPath;
            try {
                totalPath = selectedFile.getCanonicalPath();
            } catch (IOException iox) {
                logger.logDEBUG("Exception was thrown when getCanonicaPath of File object: " + selectedFile.getAbsolutePath() + " was called. See stacktrace for more details");
                logger.logDEBUG(iox);
                // If an exception is thrown then it means that a "special"
                // folder such as "Computer" or "Network" has been
                // selected. Thoose folders are no real directories and
                // will therefore cause an exception to be thrown when the
                // cannonical path is asked. And this is the thereby the
                // mechanism to decide wether an folder shall be
                // investigated for images or not.
                return;
            }

            // Right click with the mouse...
            if (event.isPopupTrigger()) {
                handleRightClickOnFileTreeItem(totalPath, event);
            }
            // ... or a left click.
            else {
                HandleLeftClickOnFileSystemFileTree handleLeftClickOnFileSystemFileTree = new HandleLeftClickOnFileSystemFileTree(totalPath);
                handleLeftClickOnFileSystemFileTree.execute();
            }
        }
    }

    /**
     * When an item, row, in the file selection tree is clicked with the "right
     * click" mouse button, then this method is called, and as result one of the
     * actions take place:
     * <p/>
     * 1: a context menu is displayed, in which the possibility to add the
     * selected row (a file path) to the list of directories in which all
     * directories which contains JPEG images shall automatically be added to
     * the meta data base repository when such kind of directory is selected in
     * the file tree or the file path shall be added to a list whith directories
     * in which any directory containing JPEG images will not be automatically
     * added to the repository of image meta data files.
     * <p/>
     * 2: An popup, saying that the selected path is already part of one of the
     * lists described in point 1.
     *
     * @param totalPath
     *            is the selected path in the file tree
     * @param event
     *            is the {@link MouseEvent} that was fired when the tree was
     *            clicked.
     */
    private void handleRightClickOnFileTreeItem(String totalPath, MouseEvent event) {
        File selectedPath = new File(totalPath);

        if (!imageRepositoriesTableModel.contains(new ImageRepositoryItem(selectedPath, Status.EXISTS))) {

            RepositoryExceptions repositoryExceptions = Config.getInstance().get().getRepository().getExceptions();

            boolean isParent = false;
            boolean alwaysAdd = false;

            for (File addAutomatically : repositoryExceptions.getAllwaysAdd()) {
                if (selectedPath.getAbsolutePath().equals((addAutomatically).getAbsolutePath())) {
                    alwaysAdd = true;
                    break;
                } else {
                    if (PathUtil.isChild(selectedPath, addAutomatically)) {
                        alwaysAdd = true;
                        break;
                    } else if (PathUtil.isParent(selectedPath, addAutomatically)) {
                        isParent = true;
                        break;
                    }
                }
            }

            boolean neverAdd = false;

            for (File doNotAddAutomatically : repositoryExceptions.getNeverAdd()) {
                if (selectedPath.getAbsolutePath().equals((doNotAddAutomatically).getAbsolutePath())) {
                    neverAdd = true;
                    break;
                } else {
                    if (PathUtil.isChild(selectedPath, doNotAddAutomatically)) {
                        neverAdd = true;
                        break;
                    } else if (PathUtil.isParent(selectedPath, doNotAddAutomatically)) {
                        isParent = true;
                        break;
                    }
                }
            }

            if (!alwaysAdd && !neverAdd && !isParent) {
                popupMenuAddDirectoryToAlwaysAutomaticallyAddToImageRepositoryList.setActionCommand(totalPath);
                popupMenuAddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList.setActionCommand(totalPath);
                rightClickMenuDirectoryTree.show(event.getComponent(), event.getX(), event.getY());
            } else {
                if (alwaysAdd) {
                    displayInformationMessage(totalPath + " " + lang.get("imagerepository.directory.already.added.to.allways.add"));
                } else if (neverAdd) {
                    displayInformationMessage(totalPath + " " + lang.get("imagerepository.directory.already.added.to.never.add"));
                } else {
                    displayInformationMessage(lang.get("imagerepository.directory.is.parent.to.already.added.directory"));
                }
            }
        }
    }

    private void setRatingCommentAndCategoryEnabled(boolean enabled) {
        imageCommentTextArea.setEnabled(enabled);
        for (JRadioButton ratingRadioButton : ratingRadioButtons) {
            ratingRadioButton.setEnabled(enabled);
        }
        checkTreeManagerForAssignCategoriesCategoryTree.getCheckedJtree().setEnabled(enabled);
        checkTreeManagerForAssignCategoriesCategoryTree.setSelectionEnabled(enabled);

        if (enabled) {
            categoriesTree.removeMouseListener(categoriesMouseButtonListener);
            categoriesTree.addMouseListener(categoriesMouseButtonListener);
        } else {
            categoriesTree.removeMouseListener(categoriesMouseButtonListener);
        }
    }

    private void removeMouseListener(){
        tree.removeMouseListener(mouseListener);
    }

    private void addMouseListener(){
        tree.addMouseListener(mouseListener);
    }

    private void setStatusMessages() {
        int nrOfImages =  FileRetriever.getInstance().handleNrOfJpegImages(FileLoadingAction.RETRIEVE);

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

    private class ImageTagPreviewScrollPaneListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            ImageMetaDataDataBaseItemsToUpdateContext irc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();
            File currentlySelectedImage = irc.getCurrentlySelectedImage();

            if (currentlySelectedImage != null) {
                try {
                    loadScaleIfNeededAndDisplayPreviewThumbnail(currentlySelectedImage);
                } catch (IOException iox) {
                    logger.logERROR("Could not create a resized preview image of image: " + currentlySelectedImage.getAbsolutePath());
                    logger.logERROR(iox);
                }
            }
        }
    }

    /**
     * This method loads an preview image either from the thumbnail cache, if
     * that is specified by the application settings, or from the orginal image
     * file. If the preview area is smaller than the loaded thumbnail, or the
     * original image, then will the image be scaled to fit the available space
     * in the preview area.
     *
     * @param jpegImage
     *            is the image to load an preview for.
     * @throws IOException
     *             is thrown if it is not possible to create the thumbnail.
     */
    private void loadScaleIfNeededAndDisplayPreviewThumbnail(File jpegImage) throws IOException {
        try {
            getThis().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            TagImages tagImages = configuration.getTagImages();

            TagImagesPreview tagImagesPreview = tagImages.getPreview();

            JPEGThumbNail thumbnail = null;

            if(tagImagesPreview.getUseEmbeddedThumbnail()) {
                JPEGThumbNailCache jtc = JPEGThumbNailCache.getInstance();
                thumbnail = jtc.get(jpegImage);
            }

            int width = imageTagPreviewScrollPane.getViewport().getSize().width;
            int height = imageTagPreviewScrollPane.getViewport().getSize().height;

            Image scaledImage;
            if (thumbnail != null) {
                ImageIcon thumbNailImageIcon = new ImageIcon(thumbnail.getThumbNailData());
                thumbNailImageIcon = ImageUtil.rotateIfNeeded(thumbNailImageIcon, thumbnail.getMetaData());

                if (thumbNailImageIcon.getIconHeight() > height || thumbNailImageIcon.getIconWidth() > width) {
                    scaledImage = ImageUtil.createThumbNailAdaptedToAvailableSpace(thumbnail.getThumbNailData(), width, height);
                    imageTagPreviewLabel.setIcon(new ImageIcon(scaledImage));
                } else {
                    imageTagPreviewLabel.setIcon(thumbNailImageIcon);
                }
            } else {
                scaledImage = ImageUtil.createThumbNailAdaptedToAvailableSpace(jpegImage, width, height);
                imageTagPreviewLabel.setIcon(new ImageIcon(scaledImage));
            }
        } finally {
            getThis().setCursor(Cursor.getDefaultCursor());
        }
    }

    private class ThumbNailListener implements ActionListener {

        OneSizedList<File> queue = new OneSizedList<>();

        Thread loadScaleIfNeededAndDisplayPreviewThumbnailThread = null;

        @Override
        public void actionPerformed(ActionEvent e) {

            Object source = e.getSource();

            if (source instanceof JToggleButton) {
                JToggleButton selectedThumbnail = (JToggleButton)source;

                // An image is selected
                if (selectedThumbnail.isSelected()) {
                    ApplicationContext ac = ApplicationContext.getInstance();

                    if (ac.isImageMetaDataDataBaseFileLoaded() &&
                            ac.isImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance() &&
                            ac.isImageMetaDataDataBaseFileWritable()) {
                        setRatingCommentAndCategoryEnabled(true);
                    }

                    ThumbNailGrayFilter grayFilter = configuration.getThumbNail().getGrayFilter();
                    boolean pixelsBrightened = grayFilter.isPixelsBrightened();
                    int percentage = grayFilter.getPercentage();

                    if ((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK) {
                        loadedThumbnails.addSelection(selectedThumbnail, pixelsBrightened, percentage);
                    }

                    // Set all thumbnails from the previous selected to to the
                    // currently selected to selected or all previous thumbnails
                    // if none of the previous are selected.
                    else if ((e.getModifiers() & ActionEvent.SHIFT_MASK) == ActionEvent.SHIFT_MASK) {

                        List<JToggleButton> deSelectedButtonsBetweenPreviousSelectedAndCurrentlySelected = new ArrayList<>();

                        for (JToggleButton thumbNail : thumbNailsPanel.getJToggleButtons()) {
                            deSelectedButtonsBetweenPreviousSelectedAndCurrentlySelected.add(thumbNail);

                            if(thumbNail.isSelected() && thumbNail != selectedThumbnail) {
                                deSelectedButtonsBetweenPreviousSelectedAndCurrentlySelected.clear();
                            }

                            if (thumbNail == selectedThumbnail) {
                                break;
                            }
                        }

                        for (JToggleButton currentlyDeSelectedThumbnail : deSelectedButtonsBetweenPreviousSelectedAndCurrentlySelected) {
                            loadedThumbnails.addSelection(currentlyDeSelectedThumbnail, pixelsBrightened, percentage);
                        }

                    } else {
                        loadedThumbnails.set(selectedThumbnail, pixelsBrightened, percentage);
                    }

                    File jpegImage = new File(e.getActionCommand());

                    imageMetaDataPanel.setMetaData(jpegImage);

                    MainTabbedPaneComponent selectedMainTabbedPaneComponent = MainTabbedPaneComponent.valueOf((mainTabbedPane.getSelectedComponent()).getName());

                    queue.set(jpegImage);

                    if(selectedMainTabbedPaneComponent == MainTabbedPaneComponent.CATEGORIZE && ac.isImageMetaDataDataBaseFileLoaded()) {
                        if (loadScaleIfNeededAndDisplayPreviewThumbnailThread == null || !loadScaleIfNeededAndDisplayPreviewThumbnailThread.isAlive()) {
                            loadScaleIfNeededAndDisplayPreviewThumbnailThread = new Thread() {
                                @Override
                                public void run() {
                                    File jpegImage;
                                    while ((jpegImage = queue.get()) != null) {
                                        try {
                                            loadScaleIfNeededAndDisplayPreviewThumbnail(jpegImage);
                                        } catch (IOException iox) {
                                            logger.logERROR("Could not create thumbnail adapted to available space for image: " + jpegImage.getAbsolutePath());
                                            logger.logERROR(iox);
                                        }
                                    }
                                }
                            };

                            loadScaleIfNeededAndDisplayPreviewThumbnailThread.start();
                        }

                        storeCurrentlySelectedImageData();

                        ImageMetaDataDataBaseItemsToUpdateContext irc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();

                        // Load the selected image
                        irc.setCurrentlySelectedImage(jpegImage);
                        ImageMetaDataItem imageMetaDataDataBaseItem = irc.getImageMetaDataBaseItem(jpegImage);

                        imageCommentTextArea.setText(imageMetaDataDataBaseItem.getComment());
                        setRatingValue(imageMetaDataDataBaseItem.getRating());
                        setCategories(imageMetaDataDataBaseItem.getCategories());
                    }
                }
                // An image is deselected
                else {
                    deSelectAllImages(e, selectedThumbnail);
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
    private void setCategories(Categories categories) {

        checkTreeManagerForAssignCategoriesCategoryTree.getSelectionModel().clearSelection();

        if (categories != null && categories.size() > 0) {
            DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategoriesCategoryTree.getTreeModel();
            Enumeration<TreeNode> elements = ((DefaultMutableTreeNode)model.getRoot()).preorderEnumeration();

            List<TreePath> treePaths = new ArrayList<>();

            while (elements.hasMoreElements()) {
                DefaultMutableTreeNode element = (DefaultMutableTreeNode)elements.nextElement();

                CategoryUserObject cuo = ((CategoryUserObject)element.getUserObject());
                String id = cuo.getIdentity();

                if (categories.contains(id)) {
                    treePaths.add(new TreePath(model.getPathToRoot(element)));
                }
            }
            if (treePaths.size() > 0) {
                checkTreeManagerForAssignCategoriesCategoryTree.getSelectionModel().setSelectionPaths(treePaths.toArray(new TreePath[treePaths.size()]));
            }
        }
    }

    private void storeCurrentlySelectedImageData() {
        ImageMetaDataDataBaseItemsToUpdateContext irc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();
        File currentlySelectedImage = irc.getCurrentlySelectedImage();

        // Store changes to the currently loaded image.
        if(currentlySelectedImage != null) {
            ImageMetaDataItem imageMetaDataDataBaseItem = null;

            Categories categories = CategoryCheckTreeUtil.getSelectedCategoriesFromTreeModel(checkTreeManagerForAssignCategoriesCategoryTree);
            String comment = imageCommentTextArea.getText();
            int rating = getRatingValue();

            imageMetaDataDataBaseItem = irc.getImageMetaDataBaseItem(currentlySelectedImage);

            if (imageMetaDataDataBaseItem.hasChanged(categories, comment, rating)) {
                imageMetaDataDataBaseItem.setComment(comment);
                imageMetaDataDataBaseItem.setRating(rating);
                imageMetaDataDataBaseItem.setCategories(categories);
                imageMetaDataDataBaseItem.setNeedsToBeSyncedWithImageMetaDataContext(true);

                irc.setImageMetaDatadataBaseItem(currentlySelectedImage, imageMetaDataDataBaseItem);
                irc.setFlushNeeded(true);
            }
        }
    }

    private class AddImageToViewList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File image = new File(e.getActionCommand());
            viewPanelListSection.handleAddImageToImageList(image, true);
        }
    }

    /**
     * This listener class adds all currently selected images in the thumbnail
     * overview to the list of images to display in the {@link ImageViewer} GUI.
     *
     * @author Fredrik
     *ctrl
     */
    private class AddSelectedImagesToViewList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<File> selectedAsFileObjects = loadedThumbnails.getSelectedAsFileObjects();
            int size = selectedAsFileObjects.size();

            for (int index = 0; index < size; index++) {
                viewPanelListSection.handleAddImageToImageList(selectedAsFileObjects.get(index), index == size - 1);
            }
        }
    }

    private class CopyImageToSystemClipBoard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<File> selectedFiles = new ArrayList<>();
            selectedFiles.add(new File(e.getActionCommand()));

            FileSelection fileSelection = new FileSelection(selectedFiles);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
        }
    }

    private class CopyAllImagesToSystemClipBoard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           Collection<File> jPEGFiles = FileRetriever.getInstance().getJPEGFiles();

           if (jPEGFiles.size() > 0) {
               FileSelection fileSelection = new FileSelection(new ArrayList<>(jPEGFiles));
               Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
           }
        }
    }

    /**
     * This listener class adds all images that are shown in the main GUI to the
     * "image viewer" list and to the image viewer, if that one is displayed.
     * The images are added in the same order as they are displayed in the main
     * GUI beginning at the top left corner of the table and then working right
     * and down.
     *
     * @author Fredrik
     *
     */
    private class AddAllImagesToViewList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            List<File> allDisplayedThumbnailsAsFileObjects = loadedThumbnails.getAllAsFileObjects();
            int size = allDisplayedThumbnailsAsFileObjects.size();

            for (int index = 0; index < size; index++) {
                viewPanelListSection.handleAddImageToImageList(allDisplayedThumbnailsAsFileObjects.get(index), index == size -1);
            }
        }
    }

    private class AddCategory implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            TreePath selectedPath = ApplicationContext.getInstance().getSelectedCategoryPath();
            String categoryName;
            boolean isTopLevelCategory = false;
            DefaultMutableTreeNode selectedNode = null;
            DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategoriesCategoryTree.getTreeModel();

            // Should the category be added at the top level...
            if (selectedPath == null) {
                categoryName = displayInputDialog(lang.get("findimage.categories.addNewTopLevelCategory"), lang.get("category.enterNameForNewCategory"), "");
                isTopLevelCategory = true;
            // ... or as a sub category of an existing category
            } else {
                selectedNode = ((DefaultMutableTreeNode)selectedPath.getLastPathComponent());
                String value = ((CategoryUserObject)selectedNode.getUserObject()).getName();

                categoryName = displayInputDialog(lang.get("findimage.categories.addNewSubCategoryToCategory") + " " + value, lang.get("category.enterNameForNewSubCategory"), "");
            }

            if (categoryName != null) {
                if (!CategoryUtil.isValid(categoryName)) {
                    displayErrorMessage(lang.get("category.errormessage.categoryNameCanNotStartWithSpace") + " \"" + categoryName + "\"");
                } else if(CategoryUtil.alreadyExists((DefaultMutableTreeNode)model.getRoot(), selectedPath, categoryName)) {
                    displayErrorMessage("\"" + categoryName + "\" " + lang.get("category.errormessage.categoryNameAlreadyExistsInScope"));
                } else {
                    CategoryUserObject cuo = new CategoryUserObject(categoryName, Integer.toString(ApplicationContext.getInstance().getNextIDToUse()));

                    DefaultMutableTreeNode newCategory = new DefaultMutableTreeNode(cuo);

                    if (isTopLevelCategory) {
                        TreeUtil.insertNodeInAlphabeticalOrder(newCategory, (DefaultMutableTreeNode)model.getRoot(), model);
                    } else {
                        TreeUtil.insertNodeInAlphabeticalOrder(newCategory, selectedNode, model);
                    }
                }
            }
            Config.getInstance().save();
        }
    }

    private class RenameCategory implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            TreePath selectedPath = ApplicationContext.getInstance().getSelectedCategoryPath();

            DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategoriesCategoryTree.getTreeModel();

            DefaultMutableTreeNode nodeToRename = ((DefaultMutableTreeNode)selectedPath.getLastPathComponent());
            String value = ((CategoryUserObject)nodeToRename.getUserObject()).getName();

            String newName = displayInputDialog(lang.get("category.rename"), lang.get("category.enterNewNameForCategory") + " " + value, value);

            if (newName != null && !newName.equals(value)) {
                if (!CategoryUtil.isValid(newName)) {
                    displayErrorMessage(lang.get("category.errormessage.categoryNameCanNotStartWithSpace") + " \"" + newName + "\"");
                } else if(CategoryUtil.alreadyExists((DefaultMutableTreeNode)model.getRoot(), selectedPath.getParentPath(), newName)) {
                    displayErrorMessage("\"" + newName + "\" " + lang.get("category.errormessage.categoryNameAlreadyExistsInScope"));
                } else {
                    ((CategoryUserObject)nodeToRename.getUserObject()).setName(newName);
                    model.nodeChanged(nodeToRename);

                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode)nodeToRename.getParent();

                    TreeUtil.sortNodesAlphabetically(parent, model);
                }
            }
            Config.getInstance().save();
        }
    }

    private class RemoveCategory implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TreePath selectedPath = ApplicationContext.getInstance().getSelectedCategoryPath();

            DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategoriesCategoryTree.getTreeModel();

            DefaultMutableTreeNode nodeToRemove = (DefaultMutableTreeNode)selectedPath.getLastPathComponent();

            int result = 0;

            TagImages tagImages = configuration.getTagImages();

            TagImagesCategories tagImagesCategories = tagImages.getCategories();

            if (nodeToRemove.getChildCount() > 0) {
                if (tagImagesCategories.getWarnWhenRemoveWithSubCategories()) {
                    String message = lang.get("findimage.categories.removeAllCategoriesAndSubCategories1") + " " + ((CategoryUserObject)nodeToRemove.getUserObject()).getName() + " " + lang.get("findimage.categories.removeAllCategoriesAndSubCategories2");
                    result = displayConfirmDialog(message, lang.get("errormessage.maingui.warningMessageLabel"), JOptionPane.OK_CANCEL_OPTION);
                }
            } else {
                if (tagImagesCategories.getWarnWhenRemove()) {
                    String message = lang.get("findimage.categories.removeAllCategoriesAndSubCategories1") + " " + ((CategoryUserObject)nodeToRemove.getUserObject()).getName() + " " + lang.get("findimage.categories.removeAllCategoriesAndSubCategories3");
                    result = displayConfirmDialog(message, lang.get("errormessage.maingui.warningMessageLabel"), JOptionPane.OK_CANCEL_OPTION);
                }
            }
            if (result == 0) {
                model.removeNodeFromParent((DefaultMutableTreeNode)selectedPath.getLastPathComponent());
            }
            Config.getInstance().save();
        }
    }

    /**
     * This listener class adds the currently selected categories to every
     * selected image in the thumbnail overview.
     *
     * @author Fredrik
     *
     */
    private class SaveSelectedCategoriesToSelectedImages implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<File> selectedThmbnailButtonsAsFiles = loadedThumbnails.getSelectedAsFileObjects();
            setSelectedCategoriesToImages(selectedThmbnailButtonsAsFiles);
        }
    }

    /**
     * This listener class adds the currently selected categories to all images
     * in the thumbnail overview.
     *
     * @author Fredrik
     *
     */
    private class SaveSelectedCategoriesToAllImages implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            List<File> allThumbnailButtonsAsFiles = loadedThumbnails.getAllAsFileObjects();
            setSelectedCategoriesToImages(allThumbnailButtonsAsFiles);
        }
    }

    private void setSelectedCategoriesToImages(List<File> imageFiles) {
        // Only do this if any image is selected...
        if (imageFiles != null && !imageFiles.isEmpty()) {

            Categories selectedCategoriesFromTreeModel = CategoryCheckTreeUtil.getSelectedCategoriesFromTreeModel(checkTreeManagerForAssignCategoriesCategoryTree);

            // ... and there are any categories selected.
            if (selectedCategoriesFromTreeModel.size() > 0) {
                ImageMetaDataDataBaseItemsToUpdateContext imddbituc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();

                for (File imageFile : imageFiles) {
                    ImageMetaDataItem imageMetaDataBaseItem = imddbituc.getImageMetaDataBaseItem(imageFile);

                    if (imageMetaDataBaseItem != null) {
                        Categories currentlyStoredCategories = imageMetaDataBaseItem.getCategories();

                        if (currentlyStoredCategories.mergeCategories(selectedCategoriesFromTreeModel.getCategories())) {
                            imddbituc.setFlushNeeded(true);
                            imageMetaDataBaseItem.setNeedsToBeSyncedWithImageMetaDataContext(true);
                        }
                    }
                }
            }
        }
    }

    private class CollapseCategoryTreeStructure implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TreePath selectedPath = ApplicationContext.getInstance().getSelectedCategoryPath();

            JTree tree  = checkTreeManagerForAssignCategoriesCategoryTree.getCheckedJtree();

            if (selectedPath == null) {
                DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategoriesCategoryTree.getTreeModel();
                DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
                TreeUtil.collapseEntireTree(tree, root, false);
            } else {
                TreeUtil.collapseEntireTree(tree, (DefaultMutableTreeNode) selectedPath.getLastPathComponent(), true);
            }
        }
    }

    private class ExpandCategoryTreeStructure implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TreePath selectedPath = ApplicationContext.getInstance().getSelectedCategoryPath();

            JTree tree  = checkTreeManagerForAssignCategoriesCategoryTree.getCheckedJtree();

            if (selectedPath == null) {
                DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategoriesCategoryTree.getTreeModel();
                DefaultMutableTreeNode root = (DefaultMutableTreeNode)model.getRoot();
                TreeUtil.expandEntireTree(tree, root, false);
            } else {
                TreeUtil.expandEntireTree(tree, (DefaultMutableTreeNode) selectedPath.getLastPathComponent(), true);
            }
        }
    }

    /**
     * This method flushes out all changes to the currently loaded set of
     * {@link ImageMetaDataItem} objects to disk.
     */
    private void flushImageMetaDataBaseToDisk() {
        ImageMetaDataDataBaseItemsToUpdateContext imddbituc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();
        if (imddbituc.getLoadedRepositoryPath() != null) {
            ImageMetaDataDataBaseHandler.updateDataBaseFile(imddbituc.getImageMetaDataBaseItems(), imddbituc.getLoadedRepositoryPath(), ImageMetaDataContextAction.UPDATE);
            imddbituc.reInit();
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
        checkTreeManagerForAssignCategoriesCategoryTree.getSelectionModel().clearSelection();
    }

    private class MouseButtonListener extends MouseAdapter{
        @Override
        public void mouseReleased(MouseEvent e){
            if(e.isPopupTrigger()) {
                if (imageRepositoriesTableModel.contains(new ImageRepositoryItem(ApplicationContext.getInstance().getSourcePath(), Status.EXISTS))) {
                    popupMenuAddImagePathToImageRepository.setVisible(false);
                    popupMenuRemoveImagePathFromImageRepository.setVisible(true);
                } else {
                    popupMenuAddImagePathToImageRepository.setVisible(true);
                    popupMenuRemoveImagePathFromImageRepository.setVisible(false);
                }
                popupMenuAddImageToViewList.setActionCommand(((JToggleButton)e.getComponent()).getActionCommand());
                popupMenuCopyImageToClipBoard.setActionCommand(((JToggleButton) e.getComponent()).getActionCommand());
                rightClickMenu.show(e.getComponent(), e.getX(), e.getY());
            } else if ((!e.isPopupTrigger()) && (e.getClickCount() ==  2)) {
                File image = new File(((JToggleButton)e.getComponent()).getActionCommand());
                viewPanelListSection.handleAddImageToImageList(image, true);
            }
        }
    }

    private class MainTabbedPaneListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {

            makeTextOfSelectedTabBold((JTabbedPane)e.getSource());

            JPanel selectedComponent = (JPanel)((JTabbedPane)e.getSource()).getSelectedComponent();
            MainTabbedPaneComponent mainTabbedPaneComponent = MainTabbedPaneComponent.valueOf(selectedComponent.getName());

            ApplicationContext ac = ApplicationContext.getInstance();

            switch (mainTabbedPaneComponent) {
            case MERGE:
            case RENAME:
            case VIEW:
                if (ac.getMainTabbedPaneComponent() == MainTabbedPaneComponent.CATEGORIZE) {
                    storeCurrentlySelectedImageData();

                    ImageMetaDataDataBaseItemsToUpdateContext imddbituc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();

                    Map<File, ImageMetaDataItem> imageMetaDataBaseItems = imddbituc.getImageMetaDataBaseItems();

                    for (File image : imageMetaDataBaseItems.keySet()) {
                        ImageMetaDataItem imddbi = imageMetaDataBaseItems.get(image);
                        if (imddbi.isNeedsToBeSyncedWithImageMetaDataContext()) {
                            ImageMetaDataDataBaseHandler.updateImageMetaDataContext(configuration.getJavapegClientId(), image, imddbi.getComment(), imddbi.getRating(), imddbi.getCategories());
                            imddbi.setNeedsToBeSyncedWithImageMetaDataContext(false);
                        }
                    }
                }
                ac.setMainTabbedPaneComponent(mainTabbedPaneComponent);
                break;
            case CATEGORIZE:
                // Since the only thing to do is to update the
                // ImageMetaDataContext there is nothing to do here, except
                // from updating the ApplicationContext with the currently
                // selected tab since the selected tab is the only one that can
                // have unsaved meta data.
                ac.setMainTabbedPaneComponent(MainTabbedPaneComponent.CATEGORIZE);
                break;
            }
        }

        private void makeTextOfSelectedTabBold(JTabbedPane tabbedPane) {
            for (int index = 0; index < tabbedPane.getTabCount(); index++) {
                String titleAtIndex = tabbedPane.getTitleAt(index);
                tabbedPane.setTitleAt(index, removeHtmlBold(titleAtIndex));
            }

            String titleAt = tabbedPane.getTitleAt(tabbedPane.getSelectedIndex());

            tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), "<html><b>" + titleAt + "</b></html>");
        }

        private String removeHtmlBold(String titleAtIndex) {
            titleAtIndex = titleAtIndex.replace("<html><b>", "");
            titleAtIndex = titleAtIndex.replace("</b></html>", "");
            return titleAtIndex;
        }
    }

    private class AddSelectedPathToImageRepository implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ApplicationContext ac = ApplicationContext.getInstance();

            File repositoryPath = ac.getSourcePath();

            File imageMetaDataDataBaseFile = new File(repositoryPath, C.JAVAPEG_IMAGE_META_NAME);

            try {
                ImageMetaDataDataBase imageMetaDataDataBase;

                // If an image meta data base file already exists in the
                // selected directory, then deserialize that file and make some
                // validity testing.
                if (imageMetaDataDataBaseFile.exists()) {
                    if (!ImageMetaDataDataBaseHandler.isMetaDataBaseValid(imageMetaDataDataBaseFile).getResult()) {
                        displayErrorMessage(lang.get("imagerepository.repository.corrupt"));
                        return;
                    }
                    imageMetaDataDataBase = ImageMetaDataDataBaseHandler.deserializeImageMetaDataDataBaseFile(imageMetaDataDataBaseFile);

                    if (!ImageMetaDataDataBaseHandler.isConsistent(imageMetaDataDataBase, repositoryPath)) {
                        displayErrorMessage(lang.get("imagerepository.repository.inconsistent"));
                        return;
                    }

                    ImageMetaDataDataBaseHandler.showCategoryImportDialogIfNeeded(imageMetaDataDataBaseFile, imageMetaDataDataBase.getJavaPEGId());
                }
                // Create the image meta data base file if not already
                // existing.
                else {
                    if (!ImageMetaDataDataBaseHandler.createImageMetaDataDataBaseFileIn(repositoryPath)) {
                        return;
                    }
                    ac.setImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance(true);
                    imageMetaDataDataBase = ImageMetaDataDataBaseHandler.deserializeImageMetaDataDataBaseFile(imageMetaDataDataBaseFile);
                }

                // Populate the image meta data context with content form the
                // deserialized XML file.
                ImageMetaDataDataBaseHandler.populateImageMetaDataContext(imageMetaDataDataBase.getJavaPEGId(), imageMetaDataDataBase.getImageMetaDataItems());

                // If the meta data base file is created by this JavaPEG
                // instance then shall it be possible to make changes to it, if
                // the file is writable for the current user.
                ac.setImageMetaDataDataBaseFileWritable(imageMetaDataDataBaseFile.canWrite());

                if (ac.isImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance() && ac.isImageMetaDataDataBaseFileWritable()) {
                    ImageMetaDataDataBaseItemsToUpdateContext imddbituc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();

                    imddbituc.setRepositoryPath(repositoryPath);
                    imddbituc.setImageMetaDataItems(imageMetaDataDataBase.getImageMetaDataItems());

                    ac.setImageMetaDataDataBaseFileLoaded(true);
                }

                // Populate the image repository model with the currently
                // selected path.
                ImageRepositoryItem iri = new ImageRepositoryItem(repositoryPath, Status.EXISTS);
                imageRepositoriesTableModel.addRow(iri);

                // Add the path to the configuration, so the entry will be
                // persisted upon application exit.
                configuration.getRepository().getPaths().getPaths().add(repositoryPath);

                if (ac.isImageMetaDataDataBaseFileWritable()) {
                    thumbNailsPanelHeading.setIcon(Icons.DB, lang.get("imagerepository.directory.added"));
                }
                else {
                    thumbNailsPanelHeading.setIcon(Icons.LOCK, lang.get("imagerepository.directory.added.writeprotected"));
                }

                thumbNailsPanelHeading.removeListeners();

                if (ac.isRestartNeeded()) {
                    displayInformationMessage(lang.get("common.application.restart.needed"));
                }

                logger.logDEBUG("Image Meta Data Base File: " + imageMetaDataDataBaseFile.getAbsolutePath() + " was successfully de serialized");
            } catch (ParserConfigurationException pcex) {
                logger.logERROR("Could not create a DocumentBuilder");
                logger.logERROR(pcex);
            } catch (SAXException sex) {
                logger.logERROR("Could not parse file: " + imageMetaDataDataBaseFile.getAbsolutePath());
                logger.logERROR(sex);
            } catch (IOException iox) {
                logger.logERROR("IO exception occurred when parsing file: " + imageMetaDataDataBaseFile.getAbsolutePath());
                logger.logERROR(iox);
            }
        }
    }

    /**
     * @return the instance of this Class.
     */
    private Component getThis() {
        return this;
    }

    private class RemoveSelectedPathFromImageRepository implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ImageRepositoryItem iri = new ImageRepositoryItem(ApplicationContext.getInstance().getSourcePath(), Status.EXISTS);
            imageRepositoriesTableModel.removeRow(iri);
        }
    }

    private class AddDirectoryToAlwaysAutomaticallyAddToImageRepositoryList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            Config.getInstance().get().getRepository().getExceptions().getAllwaysAdd().add(new File(ae.getActionCommand()));
        }
    }

    private class AddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            Config.getInstance().get().getRepository().getExceptions().getNeverAdd().add(new File(ae.getActionCommand()));
        }
    }

    /**
     * Listener class for the "save a new file name template" button.
     *
     * @author Fredrik
     *
     */
    private class SaveFileNameTemplateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            addTemplateToModel(fileNameTemplateComboBox, fileNameTemplateTextField);
        }
    }

    /**
     * Listener class for the "save a new sub directory template" button.
     *
     * @author Fredrik
     *
     */
    private class SaveSubFolderTemplateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            addTemplateToModel(subFolderTemplateComboBox, subFolderTextField);
        }
    }

    /**
     * This method will add a template, a String defining a template used by the
     * rename functionality, to the {@link JComboBox} that is referenced by the
     * parameter comboBox. A template will be added to the list of already
     * existing templates, or be the first, if the entered text is not an empty
     * string or already existing in the list of templates.
     *
     * @param comboBox
     *            is the {@link JComboBox} that will have new template added.
     * @param textField
     *            contains the new template.
     */
    private void addTemplateToModel(JComboBox<String> comboBox, JTextField textField) {
        String templateString = textField.getText();
        templateString = templateString.trim();

        if (StringUtil.isNotBlank(templateString)) {
            DefaultComboBoxModel<String> model = (DefaultComboBoxModel<String>) comboBox
                    .getModel();

            for (int index = 0; index < model.getSize(); index++) {
                String item = model.getElementAt(index);

                if (item.equals(templateString)) {
                    return;
                }
            }
            model.addElement(templateString);
        }
    }

    /**
     * This method removes an already stored template from one of the template
     * {@link JComboBox} lists: File name or Subdirectory. It will only remove
     * an item if there is one selected. And after successfull removal, then is
     * the selection cleared, meaning, the {@link JComboBox} has no selected
     * value.
     *
     * @param comboBox
     *            specifies which {@link JComboBox} to remove an template from.
     */
    private void removeTemplateFromModel(JComboBox<String> comboBox) {
        Object selectedItem = comboBox.getModel().getSelectedItem();

        if (selectedItem != null) {
            comboBox.removeItem(selectedItem);
            comboBox.getEditor().setItem(null);
        }
    }

    /**
     * Listener class for the "remove an existing file name template" button.
     *
     * @author Fredrik
     *
     */
    private class RemoveFileNameTemplateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            removeTemplateFromModel(fileNameTemplateComboBox);
        }
    }

    /**
     * Listener class for the "remove an existing sub directory template"
     * button.
     *
     * @author Fredrik
     *
     */
    private class RemoveSubFolderTemplateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            removeTemplateFromModel(subFolderTemplateComboBox);
        }
    }

    /**
     * Listener class for the file name template {@link JComboBox}. When an
     * entry in the list is selected then the value is set to the file name
     * template {@link JTextField}
     *
     * @author Fredrik
     *
     */
    private class FileNameTemplateComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setSelectedValueToJTextField(e, fileNameTemplateTextField);
        }
    }

    /**
     * Listener class for the sub folder template {@link JComboBox}. When an
     * entry in the list is selected then the value is set to the sub folder
     * template {@link JTextField}
     *
     * @author Fredrik
     *
     */
    private class SubFolderTemplateComboBoxListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            setSelectedValueToJTextField(e, subFolderTextField);
        }
    }

    /**
     * Helper method to get the text from an selected entry in a
     * {@link JComboBox} and put the text onto a {@link JTextField}
     *
     * @param e
     *            is an {@link ActionEvent} fired from an {@link JComboBox} when
     *            an selection is changed.
     * @param destinationTextField
     *            is to which {@link JTextField} to set the text that is
     *            selected by the {@link JComboBox} that fired the
     *            {@link ActionEvent} contained by the parameter e.
     */
    private void setSelectedValueToJTextField(ActionEvent e, JTextField destinationTextField) {
        JComboBox<?> source = null;

        if (e.getSource() instanceof JComboBox<?>) {
            source = (JComboBox<?>)e.getSource();
        }

        if (source != null) {
            int selectedIndex = source.getSelectedIndex();
            if (selectedIndex > -1) {
                Object selectedElement = source.getModel().getElementAt(selectedIndex);
                if (selectedElement instanceof String) {
                    destinationTextField.setText((String)selectedElement);
                }
            }
        }
    }

    private class CategoriesMouseButtonListener extends MouseAdapter{

        @Override
        public void mouseReleased(MouseEvent e){
            if (ApplicationContext.getInstance().isImageMetaDataDataBaseFileLoaded()) {
                TreePath selectedPath = checkTreeManagerForAssignCategoriesCategoryTree.getCheckedJtree().getPathForLocation(e.getX(), e.getY());

                if(e.isPopupTrigger()) {
                    String collapseCategory;
                    String expandCategory;
                    String addCategory;
                    String renameCategory = "";
                    String removeCategory = "";

                    DefaultMutableTreeNode treeNode = null;

                    if (selectedPath == null) {
                        // Should the category be added at the top level...
                        addCategory = lang.get("findimage.categories.addNewTopLevelCategory");
                        collapseCategory = lang.get("findimage.categories.collapseTopLevelCategories");
                        expandCategory = lang.get("findimage.categories.expandTopLevelCategories");
                    } else {
                        // ... or as a sub category of an existing category
                        treeNode = ((DefaultMutableTreeNode)selectedPath.getLastPathComponent());
                        String value = ((CategoryUserObject)treeNode.getUserObject()).getName();
                        collapseCategory = lang.get("findimage.categories.collapseCategory") + " " + value;
                        expandCategory = lang.get("findimage.categories.expandCategory") + " " + value;
                        addCategory = lang.get("findimage.categories.addNewSubCategoryToCategory") + " " + value;
                        renameCategory = lang.get("findimage.categories.renameSelectedCategory") + " " + value;
                        removeCategory = lang.get("findimage.categories.removeSelectedCategory") + " " + value;
                    }

                    popupMenuAddCategory.setText(addCategory);
                    popupMenuRenameCategory.setText(renameCategory);
                    popupMenuRemoveCategory.setText(removeCategory);

                    popupMenuCollapseCategoriesTreeStructure.setText(collapseCategory);
                    popupMenuExpandCategoriesTreeStructure.setText(expandCategory);

                    popupMenuSaveSelectedCategoriesToSelectedImages.setText(lang.get("findimage.categories.saveSelectedCategoriesToSelectedImages"));
                    popupMenuSaveSelectedCategoriesToAllImages.setText(lang.get("findimage.categories.saveSelectedCategoriesToAllImages"));

                    JTree categoryTree = checkTreeManagerForAssignCategoriesCategoryTree.getCheckedJtree();

                    // If no category has been selected.
                    if (selectedPath == null) {
                        DefaultMutableTreeNode root = (DefaultMutableTreeNode)checkTreeManagerForAssignCategoriesCategoryTree.getTreeModel().getRoot();

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
                                this.createMenu(CategoryMenuType.NO_RENAME_REMOVE);
                            } else if (someChildCanBeExpanded && !someChildIsExpanded) {
                                this.createMenu(CategoryMenuType.NO_RENAME_REMOVE_COLLAPSE);
                            } else if (!someChildCanBeExpanded && !someChildIsExpanded) {
                                this.createMenu(CategoryMenuType.NO_RENAME_REMOVE_EXPAND_COLLAPSE);
                            } else if (!someChildCanBeExpanded && someChildIsExpanded) {
                                this.createMenu(CategoryMenuType.NO_RENAME_REMOVE_EXPAND);
                            }
                        } else {
                            this.createMenu(CategoryMenuType.NO_RENAME_REMOVE_EXPAND_COLLAPSE);
                        }
                    }

                    // If a category has been selected
                    else {
                        if (treeNode.getChildCount() > 0) {
                            if (categoryTree.isExpanded(selectedPath)) {
                                this.createMenu(CategoryMenuType.NO_EXPAND);
                            } else {
                                this.createMenu(CategoryMenuType.NO_COLLAPSE);
                            }
                        } else {
                            this.createMenu(CategoryMenuType.NO_EXPAND_COLLAPSE);
                        }
                    }

                    ApplicationContext.getInstance().setSelectedCategoryPath(selectedPath);

                    rightClickMenuCategories.show(e.getComponent(),e.getX(), e.getY());
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            TreePath selectionPath = categoriesTree.getSelectionModel().getSelectionPath();

            if (selectionPath != null) {
                DefaultMutableTreeNode lastPathComponent = (DefaultMutableTreeNode)selectionPath.getLastPathComponent();

                Enumeration<TreeNode> preOrderEnumeration = lastPathComponent.preorderEnumeration();
                while(preOrderEnumeration.hasMoreElements()){
                    categoriesTree.getSelectionModel().addSelectionPath(getPath(preOrderEnumeration.nextElement()));
                }
            }
        }

        public TreePath getPath(TreeNode treeNode) {
            List<Object> nodes = new ArrayList<>();
            if (treeNode != null) {
                nodes.add(treeNode);
                treeNode = treeNode.getParent();
                while (treeNode != null) {
                    nodes.add(0, treeNode);
                    treeNode = treeNode.getParent();
                }
            }
            return nodes.isEmpty() ? null : new TreePath(nodes.toArray());
        }

        private void createMenu(CategoryMenuType categoryMenuType) {

            rightClickMenuCategories.removeAll();

            switch (categoryMenuType) {
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

            rightClickMenuCategories.addSeparator();

            Categories selectedCategoriesFromTreeModel = CategoryCheckTreeUtil.getSelectedCategoriesFromTreeModel(checkTreeManagerForAssignCategoriesCategoryTree);
            popupMenuSaveSelectedCategoriesToAllImages.setEnabled(selectedCategoriesFromTreeModel != null && selectedCategoriesFromTreeModel.size() > 0 && loadedThumbnails.size() > 0);
            rightClickMenuCategories.add(popupMenuSaveSelectedCategoriesToAllImages);

            // Enable this menu item if there is more than one selected image and there is at least one selected category.
            popupMenuSaveSelectedCategoriesToSelectedImages.setEnabled(selectedCategoriesFromTreeModel != null && selectedCategoriesFromTreeModel.size() > 0 && loadedThumbnails.hasSelectedThumbnails());
            rightClickMenuCategories.add(popupMenuSaveSelectedCategoriesToSelectedImages);
        }
    }

    /**
     * This class extends the AbstractImageMetaDataContextLoader which extends
     * the {@link SwingWorker} and performs the long running task of loading
     * (parsing, validating and put the data into the Image meta data context)
     * all the configured image meta data repository files in a separate thread.
     *
     * When the loading is done, then the "search image", "view statistics" and
     * "delete repository paths (int the configuration GUI)" buttons are set to
     * enabled, and thereby it is possible to perform searches in the image meta
     * data repository or perform changes in the image repository.
     *
     * @author Fredrik
     *
     */
    private class ImageMetaDataContextLoader extends AbstractImageMetaDataContextLoader {

        /**
         * Load all the repository paths into the table model, with the
         * {@link Status#UNKNOWN}. This is to minimize the risk that the
         * configuration GUI is opened and closed before all repoitory paths
         * have been validated and added to the table model.
         */
        public ImageMetaDataContextLoader() {
            List<File> paths = configuration.getRepository().getPaths().getPaths();

            Set<ImageRepositoryItem> imageRepositoryItems = new HashSet<>();

            for (File path : paths) {
                ImageRepositoryItem imageRepositoryItem = new ImageRepositoryItem(path, Status.UNKNOWN);
                imageRepositoryItems.add(imageRepositoryItem);
            }

            imageRepositoriesTableModel.addAll(imageRepositoryItems);
        }

        @Override
        protected void process(List<ImageRepositoryItem> chunks) {
            for (ImageRepositoryItem imageRepositoryItem : chunks) {
                imageRepositoriesTableModel.setRowStatus(imageRepositoryItem.getPath(), imageRepositoryItem.getPathStatus());
            }
        }

        @Override
        protected void done() {
            imageSearchTab.activateFieldsAfterMetaDataContextLoadingFinished();
            imageSearchTab.addListenersToFilterFields();

            try {
                ResultObject<String[]> result = get();

                String[] errorMessages = result.getObject();

                if (errorMessages[0] != null || errorMessages[1] != null) {
                    StringBuilder errorMessage = new StringBuilder();

                    if (errorMessages[0] != null) {
                        errorMessage.append(lang.get("imagerepository.repositories.inconsistent"));
                        errorMessage.append(C.LS).append(C.LS);
                        errorMessage.append(errorMessages[0]);
                        errorMessage.append(C.LS);
                        errorMessage.append(lang.get("imagerepository.repositories.inconsistent.repair"));
                        errorMessage.append(C.LS).append(C.LS);
                    }

                    if (errorMessages[1] != null) {
                        errorMessage.append(lang.get("imagerepository.repositories.corrupt"));
                        errorMessage.append(C.LS).append(C.LS);
                        errorMessage.append(errorMessages[1]);
                        errorMessage.append(C.LS);
                        errorMessage.append(lang.get("imagerepository.repositories.corrupt.repair"));
                    }
                    displayErrorMessage(errorMessage.toString());
                }

                if (ApplicationContext.getInstance().isRestartNeeded()) {
                    displayInformationMessage(lang.get("common.application.restart.needed"));
                }
            } catch (InterruptedException | ExecutionException e) {
                logger.logERROR(e);
            }
            logger.logDEBUG("Image Meta Data Context initialization Finished");
        }
    }

    /**
     * This class handles the task of either loading and de-serializing a meta
     * data file or first create one, if one should be automatically created or
     * the answer from the user is to create one and then de-serialize the meta
     * data base file.
     *
     * @author Fredrik
     *
     */
    private class HandleLeftClickOnFileSystemFileTree extends SwingWorker<Void, String> {

        private final String totalPath;

        public HandleLeftClickOnFileSystemFileTree(String totalPath) {
            this.totalPath = totalPath;
        }

        @Override
        protected Void doInBackground() throws Exception {
            ApplicationContext ac = ApplicationContext.getInstance();
            if(!new File(totalPath).equals(ac.getSourcePath())) {
                thumbNailsPanelHeading.removeIcon();
                thumbNailsPanelHeading.removeListeners();

                ImageMetaDataDataBaseItemsToUpdateContext imddbituc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();
                if(imddbituc.getLoadedRepositoryPath() != null) {
                    storeCurrentlySelectedImageData();
                    flushImageMetaDataBaseToDisk();
                }
                clearTagTab();

                File repositoryPath = new File(totalPath);

                boolean repositoryPathContainsJPEGFiles;

                try {
                    repositoryPathContainsJPEGFiles = JPEGUtil.containsJPEGFiles(repositoryPath);
                } catch (IOException iox) {
                    repositoryPathContainsJPEGFiles = false;
                    logger.logDEBUG("Problem with determining nr of JPEG files in directory: " + totalPath);
                    logger.logDEBUG(iox);
                }

                setRatingCommentAndCategoryEnabled(false);

                if (repositoryPathContainsJPEGFiles) {
                    // Reset the flag indicating that an image meta data
                    // base file has been loaded to not loaded, since we are
                    // in the scope of potentially loading a new one.
                    ac.setImageMetaDataDataBaseFileLoaded(false);

                    // Load thumbnails for all JPEG images that exists in the
                    // selected path.
                    loadThumbNails(repositoryPath);

                    File imageMetaDataDataBaseFile = new File(repositoryPath, C.JAVAPEG_IMAGE_META_NAME);

                    ImageRepositoryItem iri = new ImageRepositoryItem(repositoryPath, Status.EXISTS);

                    ImageMetaDataDataBase imageMetaDataDataBase = null;

                    if (!imageRepositoriesTableModel.contains(iri)) {
                        // If the selected path shall not be added to the
                        // image meta data repository, according to policy
                        // and answer then just do nothing
                        if (!ImageMetaDataDataBaseHandler.addPathToRepositoryAccordingToPolicy(getThis(), repositoryPath)) {
                            thumbNailsPanelHeading.setIcon(Icons.DB_ADD, lang.get("imagerepository.directory.not.added"));
                            thumbNailsPanelHeading.setListener(addSelectedPathToImageRepository);
                            return null;
                        }

                        try {

                            // If an image meta data base file already exists in the
                            // selected directory, then deserialize that file and make some
                            // validity testing.
                            if (imageMetaDataDataBaseFile.exists()) {
                                if (!ImageMetaDataDataBaseHandler.isMetaDataBaseValid(imageMetaDataDataBaseFile).getResult()) {
                                    displayErrorMessage(lang.get("imagerepository.repository.corrupt"));
                                    return null;
                                }

                                imageMetaDataDataBase = ImageMetaDataDataBaseHandler.deserializeImageMetaDataDataBaseFile(imageMetaDataDataBaseFile);

                                if (!ImageMetaDataDataBaseHandler.isConsistent(imageMetaDataDataBase, repositoryPath)) {
                                    displayErrorMessage(lang.get("imagerepository.repository.inconsistent"));
                                    return null;
                                }

                                ImageMetaDataDataBaseHandler.showCategoryImportDialogIfNeeded(imageMetaDataDataBaseFile, imageMetaDataDataBase.getJavaPEGId());
                            }

                            // Create the image meta data base file if it is
                            // not already existing.
                            else {
                                if (!ImageMetaDataDataBaseHandler.createImageMetaDataDataBaseFileIn(repositoryPath)) {
                                    return null;
                                }
                                imageMetaDataDataBase = ImageMetaDataDataBaseHandler.deserializeImageMetaDataDataBaseFile(imageMetaDataDataBaseFile);
                            }

                            // Populate the image meta data context with content form the
                            // deserialized XML file.
                            ImageMetaDataDataBaseHandler.populateImageMetaDataContext(imageMetaDataDataBase.getJavaPEGId(), imageMetaDataDataBase.getImageMetaDataItems());

                            // * Add the path to the configuration, so the entry will be
                            // * persisted upon application exit.
                            configuration.getRepository().getPaths().getPaths().add(repositoryPath);
                            Config.getInstance().save();

                            /**
                             * Update the label with the amount of images matching
                             * the currently selected filter in the search tab
                             */
                            imageSearchTab.updateMatchingImagesLabel();
                        } catch (ParserConfigurationException pcex) {
                            logger.logERROR("Could not create a DocumentBuilder");
                            logger.logERROR(pcex);
                        } catch (SAXException sex) {
                            logger.logERROR("Could not parse file: " + imageMetaDataDataBaseFile.getAbsolutePath());
                            logger.logERROR(sex);
                        } catch (IOException iox) {
                            logger.logERROR("IO exception occurred when parsing file: " + imageMetaDataDataBaseFile.getAbsolutePath());
                            logger.logERROR(iox);
                        }
                    } else {

                        // If an image meta data base file already exists in the
                        // selected directory, then deserialize that file and make some
                        // validity testing.
                        if (imageMetaDataDataBaseFile.exists()) {
                            if (!ImageMetaDataDataBaseHandler.isMetaDataBaseValid(imageMetaDataDataBaseFile).getResult()) {
                                displayErrorMessage(lang.get("imagerepository.repository.corrupt"));
                                return null;
                            }
                            try {
                                imageMetaDataDataBase = ImageMetaDataDataBaseHandler.deserializeImageMetaDataDataBaseFile(imageMetaDataDataBaseFile);
                                if (!ImageMetaDataDataBaseHandler.isConsistent(imageMetaDataDataBase, repositoryPath)) {
                                    displayErrorMessage(lang.get("imagerepository.repository.inconsistent"));
                                    return null;
                                }

                                ImageMetaDataDataBaseHandler.showCategoryImportDialogIfNeeded(imageMetaDataDataBaseFile, imageMetaDataDataBase.getJavaPEGId());
                            } catch (ParserConfigurationException pcex) {
                                logger.logERROR("Could not create a DocumentBuilder");
                                logger.logERROR(pcex);
                            } catch (SAXException sex) {
                                logger.logERROR("Could not parse file: " + imageMetaDataDataBaseFile.getAbsolutePath());
                                logger.logERROR(sex);
                            } catch (IOException iox) {
                                logger.logERROR("IO exception occurred when parsing file: " + imageMetaDataDataBaseFile.getAbsolutePath());
                                logger.logERROR(iox);
                            }
                        } else {
                            displayErrorMessage("The metadata base file: " + imageMetaDataDataBaseFile.getAbsolutePath() + " is missing");
                            logger.logERROR(String.format(lang.get("imagerepository.missing"), imageMetaDataDataBaseFile.getAbsolutePath()));
                            return null;
                        }
                    }

                    // If the meta data base file is created by this JavaPEG
                    // instance then it shall be possible to make changes to it, if
                    // the file is writable for the current user.
                    boolean canWrite = imageMetaDataDataBaseFile.canWrite();

                    if (ac.isImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance() && canWrite) {
                        imddbituc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();
                        imddbituc.setRepositoryPath(repositoryPath);
                        imddbituc.setImageMetaDataItems(imageMetaDataDataBase.getImageMetaDataItems());

                        ac.setImageMetaDataDataBaseFileLoaded(true);
                    }

                    // Populate the image repository model with any
                    // unpopulated paths.
                    if(!imageRepositoriesTableModel.contains(iri)) {
                        imageRepositoriesTableModel.addRow(iri);
                    }

                    ac.setImageMetaDataDataBaseFileWritable(canWrite);

                    if (canWrite) {
                        thumbNailsPanelHeading.setIcon(Icons.DB, lang.get("imagerepository.directory.added"));
                    }
                    else {
                        thumbNailsPanelHeading.setIcon(Icons.LOCK, lang.get("imagerepository.directory.added.writeprotected"));
                    }

                    thumbNailsPanelHeading.removeListeners();

                    if (ac.isRestartNeeded()) {
                        displayInformationMessage(lang.get("common.application.restart.needed"));
                    }
                }
            }
            return null;
        }
    }

    private class CustomKeyEventDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {

            if (MainGUI.this.isFocused()) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK) {
                    if (KeyEvent.VK_A == e.getKeyCode()) {
                        selectAllImages();
                        return true;
                    }
                }

                if (e.getID() == KeyEvent.KEY_PRESSED  && inputForRatingButtonsAccepted(e.getKeyChar())) {
                    JRadioButton ratingRadioButton = ratingRadioButtons[e.getKeyChar() - 48];
                    if (ratingRadioButton.isEnabled()) {
                        if (ratingRadioButton.isSelected()) {
                            ratingRadioButtons[0].setSelected(true);
                        } else {
                            ratingRadioButton.setSelected(true);
                        }
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean inputForRatingButtonsAccepted(int keyCode) {
            // numeric keyboard key number: 1 to 5
            return keyCode >= 49 && keyCode <= 53;
        }
    }

    private class ImageMetaDataContextLoaderPropertyListener implements PropertyChangeListener {
        /**
         * Invoked when task's progress property changes.
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("progress".equals(evt.getPropertyName())) {
                int progress = (Integer) evt.getNewValue();
                imageSearchTab.setImageMetaDataContextLoadingProgressBarValue(progress);
            }
        }
    }
}
