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
package moller.javapeg.program.gui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
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

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.FileRetriever;
import moller.javapeg.program.FileSelection;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.applicationstart.ValidateFileSetup;
import moller.javapeg.program.categories.Categories;
import moller.javapeg.program.categories.CategoryUserObject;
import moller.javapeg.program.categories.CategoryUtil;
import moller.javapeg.program.categories.ImportedCategoryTreeAndDisplayJavaPegID;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.ConfigUtil;
import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.controller.section.CategoriesConfig;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.ToolTips;
import moller.javapeg.program.config.model.UpdatesChecker;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPane;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPaneUtil;
import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesCategories;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPreview;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.javapeg.program.config.model.repository.RepositoryExceptions;
import moller.javapeg.program.config.model.repository.RepositoryPaths;
import moller.javapeg.program.config.model.thumbnail.ThumbNailGrayFilter;
import moller.javapeg.program.config.view.ConfigViewerGUI;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.contexts.ImageMetaDataDataBaseItemsToUpdateContext;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContext;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContextSearchParameters;
import moller.javapeg.program.contexts.imagemetadata.ImageMetaDataContextUtil;
import moller.javapeg.program.datatype.FileAndTimeStampPair;
import moller.javapeg.program.enumerations.CategoryMenueType;
import moller.javapeg.program.enumerations.FileLoadingAction;
import moller.javapeg.program.enumerations.ImageMetaDataContextAction;
import moller.javapeg.program.enumerations.MainTabbedPaneComponent;
import moller.javapeg.program.enumerations.MetaDataValueFieldName;
import moller.javapeg.program.gui.CategoriesTransferHandler;
import moller.javapeg.program.gui.CategoryImportExportPopup;
import moller.javapeg.program.gui.CustomizedJTable;
import moller.javapeg.program.gui.FileTreeCellRenderer;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.gui.HeadingPanel;
import moller.javapeg.program.gui.ImageResizer;
import moller.javapeg.program.gui.ImageSearchResultViewer;
import moller.javapeg.program.gui.MetaDataPanel;
import moller.javapeg.program.gui.StatusPanel;
import moller.javapeg.program.gui.VariablesPanel;
import moller.javapeg.program.gui.checktree.CheckTreeManager;
import moller.javapeg.program.gui.metadata.MetaDataValueSelectionDialog;
import moller.javapeg.program.gui.metadata.impl.MetaDataValue;
import moller.javapeg.program.gui.metadata.impl.MetaDataValueSelectionDialogEqual;
import moller.javapeg.program.gui.metadata.impl.MetaDataValueSelectionDialogLessEqualGreater;
import moller.javapeg.program.gui.tab.ImageMergeTab;
import moller.javapeg.program.helpviewer.HelpViewerGUI;
import moller.javapeg.program.imagelistformat.ImageList;
import moller.javapeg.program.imagemetadata.ImageMetaDataDataBase;
import moller.javapeg.program.imagemetadata.ImageMetaDataDataBaseHandler;
import moller.javapeg.program.imagemetadata.ImageMetaDataItem;
import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.javapeg.program.imageviewer.ImageViewer;
import moller.javapeg.program.jpeg.JPEGThumbNail;
import moller.javapeg.program.jpeg.JPEGThumbNailCache;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.metadata.MetaDataRetriever;
import moller.javapeg.program.metadata.MetaDataUtil;
import moller.javapeg.program.model.MetaDataTableModel;
import moller.javapeg.program.model.ModelInstanceLibrary;
import moller.javapeg.program.model.PreviewTableModel;
import moller.javapeg.program.model.SortedListModel;
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
    private JButton openImageResizerButton;
    private JButton searchImagesButton;
    private JButton clearCategoriesSelectionButton;
    private JButton clearAllMetaDataParameters;
    private JButton saveFileNameTemplateButton;
    private JButton saveSubFolderTemplateButton;
    private JButton removeFileNameTemplateButton;
    private JButton removeSubFolderTemplateButton;

    private JLabel amountOfImagesInImageListLabel;
    private JLabel imagePreviewLabel;
    private JLabel imageTagPreviewLabel;

    private JTextField destinationPathTextField;
    private JTextField subFolderTextField;
    private JTextField fileNameTemplateTextField;
    private JTextField subFolderNamePreviewTextField;

    private JComboBox<String> fileNameTemplateComboBox;
    private JComboBox<String> subFolderTemplateComboBox;

    private JMenu fileMenu;
    private JMenu helpMenu;
    private JMenu configMenu;

    private JMenuItem configGUIJMenuItem;
    private JMenuItem shutDownProgramJMenuItem;
    private JMenuItem openDestinationFileChooserJMenuItem;
    private JMenuItem startProcessJMenuItem;
    private JMenuItem exportCategoryTreeStructureJMenuItem;
    private JMenuItem importCategoryTreeStructureJMenuItem;
    private JMenuItem helpJMenuItem;
    private JMenuItem aboutJMenuItem;
    private JMenuItem popupMenuCopyImageToClipBoardRename;
    private JMenuItem popupMenuCopyImageToClipBoardMerge;
    private JMenuItem popupMenuCopyImageToClipBoardView;
    private JMenuItem popupMenuCopyImageToClipBoardTag;
    private JMenuItem popupMenuCopySelectedImagesToClipBoardRename;
    private JMenuItem popupMenuCopySelectedImagesToClipBoardMerge;
    private JMenuItem popupMenuCopySelectedImagesToClipBoardView;
    private JMenuItem popupMenuCopySelectedImagesToClipBoardTag;
    private JMenuItem popupMenuCopyAllImagesToClipBoardRename;
    private JMenuItem popupMenuCopyAllImagesToClipBoardMerge;
    private JMenuItem popupMenuCopyAllImagesToClipBoardView;
    private JMenuItem popupMenuCopyAllImagesToClipBoardTag;
    private JMenuItem popupMenuAddImagePathToImageRepositoryRename;
    private JMenuItem popupMenuAddImagePathToImageRepositoryMerge;
    private JMenuItem popupMenuAddImagePathToImageRepositoryView;
    private JMenuItem popupMenuAddImagePathToImageRepositoryTag;
    private JMenuItem popupMenuRemoveImagePathFromImageRepositoryRename;
    private JMenuItem popupMenuRemoveImagePathFromImageRepositoryMerge;
    private JMenuItem popupMenuRemoveImagePathFromImageRepositoryView;
    private JMenuItem popupMenuRemoveImagePathFromImageRepositoryTag;
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
    private JMenuItem popupMenuAddDirectoryToAllwaysAutomaticallyAddToImageRepositoryList;
    private JMenuItem popupMenuAddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList;

    private JPopupMenu rightClickMenuCategories;
    private JPopupMenu rightClickMenuRename;
    private JPopupMenu rightClickMenuView;
    private JPopupMenu rightClickMenuTag;
    private JPopupMenu rightClickMenuDirectoryTree;
    private JPopupMenu rightClickMenuMerge;

    private JPanel thumbNailsPanel;
    private JPanel thumbNailsBackgroundsPanel;

    private JSplitPane thumbNailMetaPanelSplitPane;
    private JSplitPane verticalSplitPane;
    private JSplitPane mainSplitPane;
    private JSplitPane previewAndCommentSplitPane;
    private JSplitPane previewCommentCategoriesRatingSplitpane;

    private MetaDataValue yearMetaDataValue;
    private MetaDataValue monthMetaDataValue;
    private MetaDataValue dayMetaDataValue;
    private MetaDataValue hourMetaDataValue;
    private MetaDataValue minuteMetaDataValue;
    private MetaDataValue secondMetaDataValue;
    private MetaDataValue imagesSizeMetaDataValue;
    private MetaDataValue isoMetaDataValue;
    private MetaDataValue shutterSpeedMetaDataValue;
    private MetaDataValue apertureValueMetaDataValue;
    private MetaDataValue cameraModelMetaDataValue;

    private JCheckBox[] ratingCheckBoxes;
    private JTextArea commentTextArea;

    private JScrollPane imageTagPreviewScrollPane;

    private JCheckBox createThumbNailsCheckBox;

    private JTabbedPane tabbedPane;
    private JTabbedPane mainTabbedPane;

    private CustomizedJTable metaDataTable;
    private CustomizedJTable previewTable;

    private JTree tree;
    private JTree categoriesTree;

    private FileSystemDirectoryTreeMouselistener mouseListener;
    private MouseButtonListener mouseRightClickButtonListener;
    private CategoriesMouseButtonListener categoriesMouseButtonListener;
    private ActionListener addSelecetedPathToImageRepository;

    private int iconWidth = 160;
    private int columnMargin = 0;

    private GridLayout thumbNailGridLayout;

    private MetaDataTableModel metaDataTableModel;
    private PreviewTableModel previewTableModel;

    private StatusPanel statusBar;
    private MetaDataPanel imageMetaDataPanel;

    private ThumbNailListener thumbNailListener;

    private final DefaultListModel<File> imagesToViewListModel;
    private final SortedListModel<ImageRepositoryItem> imageRepositoryListModel;

    private JList<File> imagesToViewList;

    private JTextArea imageCommentTextArea;

    private JRadioButton [] ratingRadioButtons;
    private JRadioButton andRadioButton;
    private JRadioButton orRadioButton;

    private CheckTreeManager checkTreeManagerForAssignCategoriesCategoryTree;

    private Map<String, CheckTreeManager> javaPegIdToCheckTreeManager;

    private Thread loadFilesThread;

    private ImageViewer imageViewer;

    private JProgressBar thumbnailLoadingProgressBar;

    private HeadingPanel thumbNailsPanelHeading;

    private List<ButtonGroup> importedButtonGroups;

    /**
     * This object keeps track on which thumbnails that are loaded and selected
     * in the thumbnailoverview part of this GUI.
     */
    private final LoadedThumbnails loadedThumbnailButtons = new LoadedThumbnails();

    /** Provides nice icons and names for files. */
    private final FileSystemView fileSystemView;

    public MainGUI(){

        // Make pre configured logging, logging...
        long startTestWriteAccess = System.currentTimeMillis();
        if(!FileUtil.testWriteAccess(new File(C.USER_HOME))) {
            JOptionPane.showMessageDialog(null, "Can not create files in direcotry: " + C.USER_HOME);
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

        imagesToViewListModel = ModelInstanceLibrary.getInstance().getImagesToViewModel();
        imageRepositoryListModel = ModelInstanceLibrary.getInstance().getImageRepositoryListModel();

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
        this.createRightClickMenuRename();
        this.createRightClickMenuView();
        this.createRightClickMenuTag();
        this.createRightClickMenuDirectoryTree();
        this.createRightClickMenuMerge();
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
        openDestinationFileChooserJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.openDestinationFileChooser.accelerator").charAt(0)), ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));

        startProcessJMenuItem = new JMenuItem(lang.get("menu.item.startProcess"));
        startProcessJMenuItem.setToolTipText(lang.get("tooltip.selectSourceDirectoryWithImagesAndDestinationDirectory"));
        startProcessJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.startProcess.accelerator").charAt(0)), ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));
        startProcessJMenuItem.setEnabled(false);

        shutDownProgramJMenuItem = new JMenuItem(lang.get("menu.item.exit"));
        shutDownProgramJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.iten.exit.accelerator").charAt(0)), ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));

        exportCategoryTreeStructureJMenuItem = new JMenuItem(lang.get("categoryimportexport.export.long.title"));
        exportCategoryTreeStructureJMenuItem.setToolTipText(lang.get("categoryimportexport.export.long.title.tooltip"));
        exportCategoryTreeStructureJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent('E'), ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));

        importCategoryTreeStructureJMenuItem = new JMenuItem(lang.get("categoryimportexport.import.long.title"));
        importCategoryTreeStructureJMenuItem.setToolTipText(lang.get("categoryimportexport.import.long.title.tooltip"));
        importCategoryTreeStructureJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent('I'), ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));

        fileMenu = new JMenu(lang.get("menu.file"));
        fileMenu.setMnemonic(lang.get("menu.mnemonic.file").charAt(0));

        fileMenu.add(openDestinationFileChooserJMenuItem);
        fileMenu.add(startProcessJMenuItem);
        fileMenu.add(shutDownProgramJMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exportCategoryTreeStructureJMenuItem);
        fileMenu.add(importCategoryTreeStructureJMenuItem);

        // Create rows in the Configuration menu
        configGUIJMenuItem = new JMenuItem(lang.get("menu.item.configuration"));
        configGUIJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent('c'), ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));

        configMenu = new JMenu(lang.get("menu.configuration"));
        configMenu.setMnemonic(lang.get("menu.mnemonic.configuration").charAt(0));

        configMenu.add(configGUIJMenuItem);

        // Create menu items in the help menu
        helpJMenuItem = new JMenuItem(lang.get("menu.item.programHelp"));
        helpJMenuItem.setAccelerator(KeyStroke.getKeyStroke("F1"));

        aboutJMenuItem = new JMenuItem(lang.get("menu.item.about"));
        aboutJMenuItem.setAccelerator(KeyStroke.getKeyStroke(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("menu.item.about.accelerator").charAt(0)), ActionEvent.CTRL_MASK + ActionEvent.ALT_MASK));

        helpMenu = new JMenu(lang.get("menu.help"));
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
            } catch (IOException iox) {
                logger.logERROR("Could not close InputStream for image: \"resources/images/javapeg.gif\"");
                logger.logERROR(iox);
            }
        }

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

        mainSplitPane = new JSplitPane();
        mainSplitPane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.MAIN));
        mainSplitPane.setOneTouchExpandable(true);

        verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        verticalSplitPane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.VERTICAL));
        verticalSplitPane.setOneTouchExpandable(true);

        thumbNailMetaPanelSplitPane = new JSplitPane();
        thumbNailMetaPanelSplitPane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.THUMB_NAIL_META_DATA_PANEL));
        thumbNailMetaPanelSplitPane.setOneTouchExpandable(true);
        thumbNailMetaPanelSplitPane.setDividerSize(10);

        thumbNailsPanelHeading = new HeadingPanel(lang.get("picture.panel.pictureLabel"), Color.GRAY, null);

        thumbnailLoadingProgressBar = new JProgressBar();
        thumbnailLoadingProgressBar.setStringPainted(true);
        thumbnailLoadingProgressBar.setVisible(false);

        thumbNailsBackgroundsPanel = new JPanel(new BorderLayout());
        thumbNailsBackgroundsPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));
        thumbNailsBackgroundsPanel.add(thumbNailsPanelHeading, BorderLayout.NORTH);
        thumbNailsBackgroundsPanel.add(this.createThumbNailsBackgroundPanel(), BorderLayout.CENTER);
        thumbNailsBackgroundsPanel.add(thumbnailLoadingProgressBar, BorderLayout.SOUTH);

        mainTabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        mainTabbedPane.addTab(lang.get("tabbedpane.imageMerge"), new ImageMergeTab());
        mainTabbedPane.addTab(lang.get("tabbedpane.imageRename"), this.createRenamePanel());
        mainTabbedPane.addTab(lang.get("tabbedpane.imageTag"), this.createCategorizePanel());
        mainTabbedPane.addTab(lang.get("tabbedpane.imageView"), this.createViewPanel());

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

        GBHelper posBackgroundPanel = new GBHelper();

        JPanel backgroundJPanel = new JPanel(new GridBagLayout());
        backgroundJPanel.setName(MainTabbedPaneComponent.VIEW.toString());
        backgroundJPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        backgroundJPanel.add(this.createFindImageSection(), posBackgroundPanel.expandH().expandW());
        backgroundJPanel.add(Box.createHorizontalStrut(2), posBackgroundPanel.nextCol());
        backgroundJPanel.add(this.createViewPanelListSection(), posBackgroundPanel.nextCol().expandH());

        return backgroundJPanel;
    }

    private JPanel createCategorizePanel() {

        GUI gUI = configuration.getgUI();

        previewCommentCategoriesRatingSplitpane = new JSplitPane();

        previewCommentCategoriesRatingSplitpane.setLeftComponent(this.createPreviweAndCommentPanel());
        previewCommentCategoriesRatingSplitpane.setRightComponent(this.createCategoryAndRatingPanel());
        previewCommentCategoriesRatingSplitpane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(gUI.getMain().getGuiWindowSplitPane(), ConfigElement.PREVIEW_COMMENT_CATEGORIES_RATING));

        GBHelper posBackgroundPanel = new GBHelper();

        JPanel backgroundJPanel = new JPanel(new GridBagLayout());
        backgroundJPanel.setName(MainTabbedPaneComponent.CATEGORIZE.toString());
        backgroundJPanel.add(previewCommentCategoriesRatingSplitpane, posBackgroundPanel.expandH().expandW());

        this.setRatingCommentAndCategoryEnabled(false);

        return backgroundJPanel;
    }

    private JPanel createViewPanelListSection () {

        removeSelectedImagesButton = new JButton();
        removeAllImagesButton      = new JButton();
        openImageListButton        = new JButton();
        saveImageListButton        = new JButton();
        exportImageListButton      = new JButton();
        moveUpButton               = new JButton();
        moveDownButton             = new JButton();
        openImageViewerButton      = new JButton();
        moveToTopButton            = new JButton();
        moveToBottomButton         = new JButton();
        copyImageListdButton       = new JButton();
        openImageResizerButton     = new JButton();

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
        ImageIcon openImageResizerImageIcon = new ImageIcon();

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
            copyImageListdButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.copyImageListToClipboard"));

            imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/ImageResizer16.gif");
            openImageResizerImageIcon.setImage(ImageIO.read(imageStream));
            openImageResizerButton.setIcon(openImageResizerImageIcon);
            openImageResizerButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.openImageResizer"));


        } catch (IOException iox) {
            logger.logERROR("Could not open the image");
            logger.logERROR(iox);
        } finally {
            try {
                StreamUtil.closeStream(imageStream);
            } catch (IOException iox) {
                logger.logERROR("Could not close InputStream for image");
                logger.logERROR(iox);
            }
        }

        imagesToViewList = new JList<File>(imagesToViewListModel);
        imagesToViewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        GBHelper posVerticalButtonPanel = new GBHelper();

        JPanel verticalButtonPanel = new JPanel(new GridBagLayout());
        verticalButtonPanel.add(removeSelectedImagesButton, posVerticalButtonPanel);
        verticalButtonPanel.add(removeAllImagesButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(moveToTopButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(moveUpButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(moveDownButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(moveToBottomButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(openImageResizerButton, posVerticalButtonPanel.nextRow());

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
        previewPanel.setBorder(BorderFactory.createTitledBorder(""));

        previewPanel.add(imagePreviewLabel);

        previewBackgroundPanel.add(previewPanel, posPreviewPanel);

        backgroundPanel.add(imageListLabel, posBackgroundPanel);
        backgroundPanel.add(previewLabel, posBackgroundPanel.nextCol().nextCol().nextCol().nextCol());
        backgroundPanel.add(spImageList, posBackgroundPanel.nextRow().expandH());

        backgroundPanel.add(Box.createHorizontalStrut(3), posBackgroundPanel.nextCol());
        backgroundPanel.add(verticalButtonPanel, posBackgroundPanel.nextCol().align(GridBagConstraints.NORTH));
        backgroundPanel.add(Box.createHorizontalStrut(3), posBackgroundPanel.nextCol());
        backgroundPanel.add(previewBackgroundPanel, posBackgroundPanel.nextCol().align(GridBagConstraints.NORTH));
        backgroundPanel.add(Box.createVerticalStrut(3), posBackgroundPanel.nextRow());
        backgroundPanel.add(horisontalButtonPanel, posBackgroundPanel.nextRow().align(GridBagConstraints.WEST));
        backgroundPanel.add(amountOfImagesInImageListLabel, posBackgroundPanel.nextCol().nextCol().nextCol().nextCol());

        return backgroundPanel;
    }

    private JPanel createFindImageSection() {
        JPanel backgroundPanel = new JPanel(new GridBagLayout());

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(this.createCategoriesPanel(), posBackgroundPanel.nextRow().expandW().expandH());
        backgroundPanel.add(Box.createHorizontalStrut(2), posBackgroundPanel.nextCol());
        backgroundPanel.add(this.createImageExifMeteDataPanel(), posBackgroundPanel.nextCol().expandH());
        backgroundPanel.add(Box.createHorizontalStrut(2), posBackgroundPanel.nextCol());
        backgroundPanel.add(this.createRatingCommentAndButtonPanel(), posBackgroundPanel.nextCol().expandH());

        return backgroundPanel;
    }

    private JPanel createCategoriesPanel() {

        andRadioButton = new JRadioButton(lang.get("findimage.categories.andRadioButton.label"));
        andRadioButton.setToolTipText(lang.get("findimage.categories.andRadioButton.tooltip"));
        orRadioButton = new JRadioButton(lang.get("findimage.categories.orRadioButton.label"));
        orRadioButton.setToolTipText(lang.get("findimage.categories.orRadioButton.tooltip"));

        ButtonGroup group = new ButtonGroup();

        group.add(andRadioButton);
        group.add(orRadioButton);

        TagImages tagImages = configuration.getTagImages();

        TagImagesCategories tagImagesCategories = tagImages.getCategories();

        if (tagImagesCategories.getOrRadioButtonIsSelected()) {
            orRadioButton.setSelected(true);
        } else {
            andRadioButton.setSelected(true);
        }

        clearCategoriesSelectionButton = new JButton();
        clearCategoriesSelectionButton.setToolTipText(lang.get("findimage.categories.clearCategoriesSelectionButton.label"));

        try {
            clearCategoriesSelectionButton.setIcon(ImageUtil.getIcon(StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/remove.gif"), true));
        } catch (IOException iox) {
            clearCategoriesSelectionButton.setText("x");
            Logger logger = Logger.getInstance();
            logger.logERROR("Could not set image: resources/images/viewtab/remove.gif as icon for the clear categories button. See stacktrace below for details");
            logger.logERROR(iox);
        }

        JPanel selectionModePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));

        selectionModePanel.add(andRadioButton);
        selectionModePanel.add(orRadioButton);
        selectionModePanel.add(clearCategoriesSelectionButton);

        JTree categoriesTree = CategoryUtil.createCategoriesTree();
        ((DefaultTreeCellRenderer)categoriesTree.getCellRenderer()).setLeafIcon(null);

        Map<String, ImportedCategoryTreeAndDisplayJavaPegID> importedCategoriesTrees = CategoryUtil.createImportedCategoriesTree();

        CheckTreeManager checkTreeManagerForFindImagesCategoryTree = new CheckTreeManager(categoriesTree, false, null, false);
        checkTreeManagerForFindImagesCategoryTree.setSelectionEnabled(true);

        javaPegIdToCheckTreeManager = new HashMap<String, CheckTreeManager>(importedCategoriesTrees.size() + 1);
        javaPegIdToCheckTreeManager.put(configuration.getJavapegClientId(), checkTreeManagerForFindImagesCategoryTree);

        JScrollPane categoriesScrollPane = new JScrollPane();
        categoriesScrollPane.getViewport().add(categoriesTree);

        GBHelper posCategoryTreeAndSelectionMode = new GBHelper();
        JPanel categoryTreeAndSelectionModePanel = new JPanel(new GridBagLayout());

        categoryTreeAndSelectionModePanel.add(categoriesScrollPane, posCategoryTreeAndSelectionMode.expandH().expandW());
        categoryTreeAndSelectionModePanel.add(Box.createVerticalStrut(2), posCategoryTreeAndSelectionMode.nextRow());
        categoryTreeAndSelectionModePanel.add(selectionModePanel, posCategoryTreeAndSelectionMode.nextRow());

        JLabel findInCategoriesLabel = new JLabel(lang.get("findimage.categories.label"));
        findInCategoriesLabel.setForeground(Color.GRAY);

        GBHelper posBackground = new GBHelper();
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));
        backgroundPanel.add(findInCategoriesLabel, posBackground);

        posBackground.fill = GridBagConstraints.BOTH;

        JTabbedPane categoriesTabbedPane = null;

        if (importedCategoriesTrees.size() > 0) {

            categoriesTabbedPane = new JTabbedPane();
            categoriesTabbedPane.add(lang.get("category.mineCategoriesTab"), categoryTreeAndSelectionModePanel);

            Set<String> displayNames = new TreeSet<String>(importedCategoriesTrees.keySet());

            importedButtonGroups = new ArrayList<ButtonGroup>(importedCategoriesTrees.size());

            for (String displayName : displayNames) {

                ImportedCategoryTreeAndDisplayJavaPegID importedCategoryTree = importedCategoriesTrees.get(displayName);

                ((DefaultTreeCellRenderer)importedCategoryTree.getCategoriesTree().getCellRenderer()).setLeafIcon(null);

                CheckTreeManager checkTreeManager = new CheckTreeManager(importedCategoryTree.getCategoriesTree(), false, null, false);
                checkTreeManager.setSelectionEnabled(true);

                String importedJavePegId = importedCategoryTree.getJavaPegId();

                javaPegIdToCheckTreeManager.put(importedJavePegId, checkTreeManager);

                JScrollPane scrollPane = new JScrollPane();
                scrollPane.getViewport().add(importedCategoriesTrees.get(displayName).getCategoriesTree());

                JRadioButton andRadioButton = new JRadioButton(lang.get("findimage.categories.andRadioButton.label"));
                andRadioButton.setToolTipText(lang.get("findimage.categories.andRadioButton.tooltip"));
                andRadioButton.setActionCommand("AND");
                andRadioButton.setName(importedJavePegId);

                JRadioButton orRadioButton = new JRadioButton(lang.get("findimage.categories.orRadioButton.label"));
                orRadioButton.setToolTipText(lang.get("findimage.categories.orRadioButton.tooltip"));
                orRadioButton.setActionCommand("OR");
                orRadioButton.setName(importedJavePegId);
                orRadioButton.setSelected(true);

                ButtonGroup importedGroup = new ButtonGroup();

                importedGroup.add(andRadioButton);
                importedGroup.add(orRadioButton);

                importedButtonGroups.add(importedGroup);

                JButton importedClearCategoriesSelectionButton = new JButton();
                importedClearCategoriesSelectionButton.setToolTipText(lang.get("findimage.categories.clearCategoriesSelectionButton.label"));
                importedClearCategoriesSelectionButton.setActionCommand(importedJavePegId);
                importedClearCategoriesSelectionButton.addActionListener(new ImportedClearCategoriesSelectionListener());

                try {
                    importedClearCategoriesSelectionButton.setIcon(ImageUtil.getIcon(StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/remove.gif"), true));
                } catch (IOException iox) {
                    importedClearCategoriesSelectionButton.setText("x");
                    Logger logger = Logger.getInstance();
                    logger.logERROR("Could not set image: resources/images/viewtab/remove.gif as icon for the clear categories button. See stacktrace below for details");
                    logger.logERROR(iox);
                }

                JPanel importedSelectionModePanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));

                importedSelectionModePanel.add(andRadioButton);
                importedSelectionModePanel.add(orRadioButton);
                importedSelectionModePanel.add(importedClearCategoriesSelectionButton);


                GBHelper posImportedCategoryTreeAndSelectionMode = new GBHelper();
                JPanel importedCategoryTreeAndSelectionModePanel = new JPanel(new GridBagLayout());

                importedCategoryTreeAndSelectionModePanel.add(scrollPane, posImportedCategoryTreeAndSelectionMode.expandH().expandW());
                importedCategoryTreeAndSelectionModePanel.add(Box.createVerticalStrut(2), posImportedCategoryTreeAndSelectionMode.nextRow());
                importedCategoryTreeAndSelectionModePanel.add(importedSelectionModePanel, posImportedCategoryTreeAndSelectionMode.nextRow());

                categoriesTabbedPane.add(displayName, importedCategoryTreeAndSelectionModePanel);
            }
            backgroundPanel.add(categoriesTabbedPane, posBackground.nextRow().expandH().expandW());
        } else {
            backgroundPanel.add(categoryTreeAndSelectionModePanel, posBackground.nextRow().expandH().expandW());
        }

        return backgroundPanel;
    }


    private JPanel createImageExifMeteDataPanel() {
        JPanel backgroundPanel = new JPanel(new GridBagLayout());

        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        GBHelper posBackgroundPanel = new GBHelper();

        JLabel yearLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.YEAR.toString()));
        JLabel monthLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.MONTH.toString()));
        JLabel dayLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.DAY.toString()));
        JLabel hourLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.HOUR.toString()));
        JLabel minuteLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.MINUTE.toString()));
        JLabel secondLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.SECOND.toString()));
        JLabel imageSizeLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.IMAGE_SIZE.toString()));
        JLabel isoLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.ISO.toString()));
        JLabel cameraModelLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.CAMERA_MODEL.toString()));
        JLabel shutterSpeedLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.EXPOSURE_TIME.toString()));
        JLabel apertureValueLabel = new JLabel(lang.get("metadata.field.name." + MetaDataValueFieldName.APERTURE_VALUE.toString()));

        yearMetaDataValue = new MetaDataValue(MetaDataValueFieldName.YEAR.toString());
        yearMetaDataValue.setEnabled(false);
        monthMetaDataValue = new MetaDataValue(MetaDataValueFieldName.MONTH.toString());
        monthMetaDataValue.setEnabled(false);
        dayMetaDataValue = new MetaDataValue(MetaDataValueFieldName.DAY.toString());
        dayMetaDataValue.setEnabled(false);
        hourMetaDataValue = new MetaDataValue(MetaDataValueFieldName.HOUR.toString());
        hourMetaDataValue.setEnabled(false);
        minuteMetaDataValue = new MetaDataValue(MetaDataValueFieldName.MINUTE.toString());
        minuteMetaDataValue.setEnabled(false);
        secondMetaDataValue = new MetaDataValue(MetaDataValueFieldName.SECOND.toString());
        secondMetaDataValue.setEnabled(false);
        imagesSizeMetaDataValue = new MetaDataValue(MetaDataValueFieldName.IMAGE_SIZE.toString());
        imagesSizeMetaDataValue.setEnabled(false);
        isoMetaDataValue = new MetaDataValue(MetaDataValueFieldName.ISO.toString());
        isoMetaDataValue.setEnabled(false);
        shutterSpeedMetaDataValue = new MetaDataValue(MetaDataValueFieldName.EXPOSURE_TIME.toString());
        shutterSpeedMetaDataValue.setEnabled(false);
        apertureValueMetaDataValue = new MetaDataValue(MetaDataValueFieldName.APERTURE_VALUE.toString());
        apertureValueMetaDataValue.setEnabled(false);
        cameraModelMetaDataValue = new MetaDataValue(MetaDataValueFieldName.CAMERA_MODEL.toString());
        cameraModelMetaDataValue.setEnabled(false);

        final int size = 5;

        JLabel findInMetaDataExifLabel = new JLabel(lang.get("findimage.imagemetadata.label"));
        findInMetaDataExifLabel.setForeground(Color.GRAY);

        backgroundPanel.add(findInMetaDataExifLabel, posBackgroundPanel);
        backgroundPanel.add(yearLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(imageSizeLabel, posBackgroundPanel.nextCol());
        backgroundPanel.add(yearMetaDataValue, posBackgroundPanel.nextRow().expandH());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(imagesSizeMetaDataValue, posBackgroundPanel.nextCol());
        backgroundPanel.add(monthLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(isoLabel, posBackgroundPanel.nextCol());
        backgroundPanel.add(monthMetaDataValue, posBackgroundPanel.nextRow().expandH());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(isoMetaDataValue, posBackgroundPanel.nextCol());
        backgroundPanel.add(dayLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(shutterSpeedLabel, posBackgroundPanel.nextCol());
        backgroundPanel.add(dayMetaDataValue, posBackgroundPanel.nextRow().expandH());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(shutterSpeedMetaDataValue, posBackgroundPanel.nextCol());
        backgroundPanel.add(hourLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(apertureValueLabel, posBackgroundPanel.nextCol());
        backgroundPanel.add(hourMetaDataValue, posBackgroundPanel.nextRow().expandH());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(apertureValueMetaDataValue, posBackgroundPanel.nextCol());
        backgroundPanel.add(minuteLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(cameraModelLabel, posBackgroundPanel.nextCol());
        backgroundPanel.add(minuteMetaDataValue, posBackgroundPanel.nextRow().expandH());
        backgroundPanel.add(Box.createHorizontalStrut(size), posBackgroundPanel.nextCol());
        backgroundPanel.add(cameraModelMetaDataValue, posBackgroundPanel.nextCol());
        backgroundPanel.add(secondLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(secondMetaDataValue, posBackgroundPanel.nextRow().expandH());

        return backgroundPanel;
    }

    private class MetaDataTextfieldListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            MetaDataValueSelectionDialog mdvsd = null;

            ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();
            String value = ((JTextField)e.getSource()).getText();

            MetaDataValueFieldName mdtf = MetaDataValueFieldName.valueOf(((Component)e.getSource()).getName());

            String prefix = "metadata.field.name.";

            switch (mdtf) {
            case YEAR:
                mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getYears()), value, e.getLocationOnScreen());
                break;
            case MONTH:
                mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getMonths()), value, e.getLocationOnScreen());
                break;
            case DAY:
                mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getDates()), value, e.getLocationOnScreen());
                break;
            case HOUR:
                mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getHours()), value, e.getLocationOnScreen());
                break;
            case MINUTE:
                mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getMinutes()), value, e.getLocationOnScreen());
                break;
            case SECOND:
                mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getSeconds()), value, e.getLocationOnScreen());
                break;
            case APERTURE_VALUE:
                mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getFNumberValues()), value, e.getLocationOnScreen());
                break;
            case CAMERA_MODEL:
                mdvsd = new MetaDataValueSelectionDialogEqual(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getCameraModels()), value, e.getLocationOnScreen());
                break;
            case IMAGE_SIZE:
                mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getImageSizeValues()), value, e.getLocationOnScreen());
                break;
            case ISO:
                mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getIsoValues()), value, e.getLocationOnScreen());
                break;
            case EXPOSURE_TIME:
                mdvsd = new MetaDataValueSelectionDialogLessEqualGreater(lang.get(prefix + mdtf.toString()), new HashSet<Object>(imdc.getExposureTimeValues()), value, e.getLocationOnScreen());
                break;
            }
            mdvsd.collectSelectedValues();

            JTextField textField = (JTextField)e.getSource();

            textField.setText(mdvsd.getResult());
            textField.setToolTipText(mdvsd.getResult());
        }
    }

    private JPanel createRatingCommentAndButtonPanel() {

        JLabel findInRatingLabel = new JLabel(lang.get("findimage.rating.label"));
        findInRatingLabel.setForeground(Color.GRAY);

        GBHelper posRatingPanel = new GBHelper();
        JPanel ratingPanel = new JPanel(new GridBagLayout());
        ratingPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));
        ratingPanel.add(findInRatingLabel, posRatingPanel);

        ratingCheckBoxes = new JCheckBox[6];

        for (int i = 0; i < ratingCheckBoxes.length; i++) {
            if (i == 0) {
                ratingPanel.add(ratingCheckBoxes[i] = new JCheckBox(lang.get("findimage.rating.label.unrated")), posRatingPanel.nextRow());
            } else {
                ratingPanel.add(ratingCheckBoxes[i] = new JCheckBox(Integer.toString(i)), posRatingPanel.nextCol());
            }
        }

        JLabel commentLabel = new JLabel(lang.get("findimage.comment.label"));
        commentLabel.setForeground(Color.GRAY);

        GBHelper posCommentPanel = new GBHelper();
        JPanel commentPanel = new JPanel(new GridBagLayout());
        commentPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));
        commentPanel.add(commentLabel, posCommentPanel);

        commentTextArea = new JTextArea();
        commentTextArea.setLineWrap(true);
        commentTextArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(commentTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        commentPanel.add(scrollPane, posCommentPanel.nextRow().expandH().expandW());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setBorder(BorderFactory.createTitledBorder(""));


        clearAllMetaDataParameters = new JButton();
        clearAllMetaDataParameters.setToolTipText(lang.get("findimage.clearAllMetaDataParameters.tooltip"));

        try {
            clearAllMetaDataParameters.setIcon(ImageUtil.getIcon(StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/remove.gif"), true));
        } catch (IOException iox) {
            clearAllMetaDataParameters.setText("x");
            Logger logger = Logger.getInstance();
            logger.logERROR("Could not set image: resources/images/viewtab/remove.gif as icon for the clear meta data button. See stacktrace below for details");
            logger.logERROR(iox);
        }

        searchImagesButton = new JButton();
        searchImagesButton.setToolTipText(lang.get("findimage.searchImages.initializing.imagecontext.tooltip"));
        searchImagesButton.setEnabled(false);

        try {
            searchImagesButton.setIcon(ImageUtil.getIcon(StartJavaPEG.class.getResourceAsStream("resources/images/Find16.gif"), true));
        } catch (IOException iox) {
            searchImagesButton.setText("SEARCH IMAGES");
            Logger logger = Logger.getInstance();
            logger.logERROR("Could not set image: resources/images/Find16.gif as icon for the search images button. See stacktrace below for details");
            logger.logERROR(iox);
        }

        buttonPanel.add(searchImagesButton);
        buttonPanel.add(clearAllMetaDataParameters);

        GBHelper posBackground = new GBHelper();
        JPanel backgroundPanel = new JPanel(new GridBagLayout());

        backgroundPanel.add(ratingPanel, posBackground.nextRow());
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
        RenameImages renameImages = configuration.getRenameImages();

        createThumbNailsCheckBox = new JCheckBox(lang.get("checkbox.createThumbNails"));
        createThumbNailsCheckBox.setToolTipText(lang.get("tooltip.createThumbNails"));
        createThumbNailsCheckBox.setActionCommand("createThumbNailsCheckBox");

        if(renameImages.getCreateThumbNails()) {
            createThumbNailsCheckBox.setSelected(true);
        }

        ImageIcon playPictureImageIcon = new ImageIcon();
        try(InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/play.gif")) {

            playPictureImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException iox) {
            logger.logERROR("Could not open the image play.gif");
            logger.logERROR(iox);
        }

        startProcessButton = new JButton(playPictureImageIcon);
        startProcessButton.setActionCommand("startProcessButton");
        startProcessButton.setToolTipText(lang.get("tooltip.selectSourceDirectoryWithImagesAndDestinationDirectory"));
        startProcessButton.setPreferredSize(new Dimension(30, 20));
        startProcessButton.setMinimumSize(new Dimension(30, 20));
        startProcessButton.setEnabled(false);

        ImageIcon openPictureImageIcon = new ImageIcon();
        try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/open.gif")){
            openPictureImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException iox) {
            logger.logERROR("Could not open the image open.gif");
            logger.logERROR(iox);
        }

        JLabel destinationPathLabel = new JLabel(lang.get("labels.destinationPath"));
        destinationPathLabel.setForeground(Color.GRAY);

        destinationPathTextField = new JTextField();
        destinationPathTextField.setEditable(false);
        destinationPathTextField.setBackground(Color.WHITE);

        destinationPathButton = new JButton(openPictureImageIcon);
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

        DefaultComboBoxModel<String> subFolderTemplateComboBoxModel = new DefaultComboBoxModel<String>();
        for (String template : renameImages.getTemplateSubDirectoryNames()) {
            subFolderTemplateComboBoxModel.addElement(template);
        }

        subFolderTemplateComboBox = new JComboBox<String>();
        subFolderTemplateComboBox.setModel(subFolderTemplateComboBoxModel);
        subFolderTemplateComboBox.setEnabled(false);

        DefaultComboBoxModel<String> fileNameTemplateComboBoxModel = new DefaultComboBoxModel<String>();
        for (String template : renameImages.getTemplateFileNameNames()) {
            fileNameTemplateComboBoxModel.addElement(template);
        }

        fileNameTemplateComboBox = new JComboBox<String>();
        fileNameTemplateComboBox.setModel(fileNameTemplateComboBoxModel);
        fileNameTemplateComboBox.setEnabled(false);

        ImageIcon saveTemplatePictureImageIcon = new ImageIcon();
        try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/save.gif")){
            saveTemplatePictureImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException iox) {
            logger.logERROR("Could not open the image save.gif");
            logger.logERROR(iox);
        }

        ImageIcon removeTemplatePictureImageIcon = new ImageIcon();
        try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/remove.gif")){
            removeTemplatePictureImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException iox) {
            logger.logERROR("Could not open the image remove.gif");
            logger.logERROR(iox);
        }

        saveFileNameTemplateButton = new JButton(saveTemplatePictureImageIcon);
        saveFileNameTemplateButton.setToolTipText(lang.get("tooltip.saveTemplateToTemplatesList"));
        saveFileNameTemplateButton.setEnabled(false);

        saveSubFolderTemplateButton = new JButton(saveTemplatePictureImageIcon);
        saveSubFolderTemplateButton.setToolTipText(lang.get("tooltip.saveTemplateToTemplatesList"));
        saveSubFolderTemplateButton.setEnabled(false);

        removeFileNameTemplateButton = new JButton(removeTemplatePictureImageIcon);
        removeFileNameTemplateButton.setToolTipText(lang.get("tooltip.deleteTemplateFromTemplateList"));
        removeFileNameTemplateButton.setEnabled(false);

        removeSubFolderTemplateButton = new JButton(removeTemplatePictureImageIcon);
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

    private JPanel createPreviweAndCommentPanel() {

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
        tree.addMouseListener(mouseListener = new FileSystemDirectoryTreeMouselistener());
        tree.setShowsRootHandles (true);
        tree.expandRow(0);

        showRootFile();
        return new JScrollPane(tree);
    }

    private void showChildren(final DefaultMutableTreeNode node) {
        tree.setEnabled(false);

        File file = (File) node.getUserObject();
        if (file.isDirectory()) {
            File[] files = fileSystemView.getFiles(file, true); //!!

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
        tree.setSelectionInterval(0,0);
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
        openImageResizerButton.addActionListener(new OpenImageResizerListener());
        copyImageListdButton.addActionListener(new CopyImageListListener());
        searchImagesButton.addActionListener(new SearchImagesListener());
        clearCategoriesSelectionButton.addActionListener(new ClearCategoriesSelectionListener());
        clearAllMetaDataParameters.addActionListener(new ClearAllMetaDataParametersListener());

        popupMenuCopyImageToClipBoardRename.addActionListener(new CopyImageToSystemClipBoard());
        popupMenuCopyImageToClipBoardMerge.addActionListener(new CopyImageToSystemClipBoard());
        popupMenuCopyImageToClipBoardView.addActionListener(new CopyImageToSystemClipBoard());
        popupMenuCopyImageToClipBoardTag.addActionListener(new CopyImageToSystemClipBoard());
        popupMenuCopyAllImagesToClipBoardRename.addActionListener(new CopyAllImagesToSystemClipBoard());
        popupMenuCopyAllImagesToClipBoardMerge.addActionListener(new CopyAllImagesToSystemClipBoard());
        popupMenuCopyAllImagesToClipBoardView.addActionListener(new CopyAllImagesToSystemClipBoard());
        popupMenuCopyAllImagesToClipBoardTag.addActionListener(new CopyAllImagesToSystemClipBoard());

        CopySelectedImagesAction copySelectedImagesAction = new CopySelectedImagesAction();
        copySelectedImagesAction.putValue(Action.NAME, lang.get("maingui.popupmenu.copySelectedToSystemClipboard"));
        popupMenuCopySelectedImagesToClipBoardRename.setAction(copySelectedImagesAction);
        popupMenuCopySelectedImagesToClipBoardMerge.setAction(copySelectedImagesAction);
        popupMenuCopySelectedImagesToClipBoardView.setAction(copySelectedImagesAction);
        popupMenuCopySelectedImagesToClipBoardTag.setAction(copySelectedImagesAction);

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
        popupMenuAddImagePathToImageRepositoryRename.addActionListener(addSelecetedPathToImageRepository = new AddSelecetedPathToImageRepository());
        popupMenuAddImagePathToImageRepositoryMerge.addActionListener(addSelecetedPathToImageRepository);
        popupMenuAddImagePathToImageRepositoryTag.addActionListener(addSelecetedPathToImageRepository);
        popupMenuAddImagePathToImageRepositoryView.addActionListener(addSelecetedPathToImageRepository);
        popupMenuRemoveImagePathFromImageRepositoryRename.addActionListener(new RemoveSelecetedPathFromImageRepository());
        popupMenuRemoveImagePathFromImageRepositoryMerge.addActionListener(new RemoveSelecetedPathFromImageRepository());
        popupMenuRemoveImagePathFromImageRepositoryTag.addActionListener(new RemoveSelecetedPathFromImageRepository());
        popupMenuRemoveImagePathFromImageRepositoryView.addActionListener(new RemoveSelecetedPathFromImageRepository());
        popupMenuAddDirectoryToAllwaysAutomaticallyAddToImageRepositoryList.addActionListener(new AddDirectoryToAllwaysAutomaticallyAddToImageRepositoryList());
        popupMenuAddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList.addActionListener(new AddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList());

        imagesToViewList.addListSelectionListener(new ImagesToViewListListener());
        mainTabbedPane.addChangeListener(new MainTabbedPaneListener());

        saveFileNameTemplateButton.addActionListener(new SaveFileNameTemplateButtonListener());
        saveSubFolderTemplateButton.addActionListener(new SaveSubFolderTemplateButtonListener());
        removeFileNameTemplateButton.addActionListener(new RemoveFileNameTemplateButtonListener());
        removeSubFolderTemplateButton.addActionListener(new RemoveSubFolderTemplateButtonListener());

        fileNameTemplateComboBox.addActionListener(new FileNameTemplateComboBoxListener());
        subFolderTemplateComboBox.addActionListener(new SubFolderTemplateComboBoxListener());
        imageTagPreviewScrollPane.addComponentListener(new ImageTagPreviewScrollPaneListener());
    }

    private class CopySelectedImagesAction extends AbstractAction {

        private static final long serialVersionUID = 1L;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (loadedThumbnailButtons.size() > 0) {
                FileSelection fileSelection = new FileSelection(loadedThumbnailButtons.getSelectedAsFileObjects());
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
            }
        }
    }

    public void createRightClickMenuMerge() {

        popupMenuAddImagePathToImageRepositoryMerge = new JMenuItem(lang.get("maingui.popupmenu.addImagePathToImageRepository"));
        popupMenuRemoveImagePathFromImageRepositoryMerge = new JMenuItem(lang.get("maingui.popupmenu.removeImagePathFromImageRepository"));
        popupMenuCopyImageToClipBoardMerge = new JMenuItem(lang.get("maingui.popupmenu.copyToSystemClipboard"));
        popupMenuCopySelectedImagesToClipBoardMerge = new JMenuItem();
        popupMenuCopyAllImagesToClipBoardMerge = new JMenuItem(lang.get("maingui.popupmenu.copyAllToSystemClipboard"));

        rightClickMenuMerge = new JPopupMenu();
        rightClickMenuMerge.add(popupMenuAddImagePathToImageRepositoryMerge);
        rightClickMenuMerge.add(popupMenuRemoveImagePathFromImageRepositoryMerge);
        rightClickMenuMerge.addSeparator();
        rightClickMenuMerge.add(popupMenuCopyImageToClipBoardMerge);
        rightClickMenuMerge.add(popupMenuCopySelectedImagesToClipBoardMerge);
        rightClickMenuMerge.add(popupMenuCopyAllImagesToClipBoardMerge);
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

    public void createRightClickMenuRename() {
        popupMenuAddImagePathToImageRepositoryRename = new JMenuItem(lang.get("maingui.popupmenu.addImagePathToImageRepository"));
        popupMenuRemoveImagePathFromImageRepositoryRename = new JMenuItem(lang.get("maingui.popupmenu.removeImagePathFromImageRepository"));
        popupMenuCopyImageToClipBoardRename = new JMenuItem(lang.get("maingui.popupmenu.copyToSystemClipboard"));
        popupMenuCopySelectedImagesToClipBoardRename = new JMenuItem();
        popupMenuCopyAllImagesToClipBoardRename = new JMenuItem(lang.get("maingui.popupmenu.copyAllToSystemClipboard"));

        rightClickMenuRename = new JPopupMenu();
        rightClickMenuRename.add(popupMenuAddImagePathToImageRepositoryRename);
        rightClickMenuRename.add(popupMenuRemoveImagePathFromImageRepositoryRename);
        rightClickMenuRename.addSeparator();
        rightClickMenuRename.add(popupMenuCopyImageToClipBoardRename);
        rightClickMenuRename.add(popupMenuCopySelectedImagesToClipBoardRename);
        rightClickMenuRename.add(popupMenuCopyAllImagesToClipBoardRename);
    }

    public void createRightClickMenuView(){
        popupMenuCopyImageToClipBoardView = new JMenuItem(lang.get("maingui.popupmenu.copyToSystemClipboard"));
        popupMenuCopySelectedImagesToClipBoardView = new JMenuItem();
        popupMenuCopyAllImagesToClipBoardView = new JMenuItem(lang.get("maingui.popupmenu.copyAllToSystemClipboard"));
        popupMenuAddImagePathToImageRepositoryView = new JMenuItem(lang.get("maingui.popupmenu.addImagePathToImageRepository"));
        popupMenuRemoveImagePathFromImageRepositoryView = new JMenuItem(lang.get("maingui.popupmenu.removeImagePathFromImageRepository"));
        popupMenuAddImageToViewList = new JMenuItem(lang.get("maingui.popupmenu.addImageToList"));
        popupMenuAddSelectedImagesToViewList = new JMenuItem(lang.get("maingui.popupmenu.addSelectedImagesToList"));
        popupMenuAddAllImagesToViewList = new JMenuItem(lang.get("maingui.popupmenu.addAllImagesToList"));

        rightClickMenuView = new JPopupMenu();
        rightClickMenuView.add(popupMenuAddImagePathToImageRepositoryView);
        rightClickMenuView.add(popupMenuRemoveImagePathFromImageRepositoryView);
        rightClickMenuView.addSeparator();
        rightClickMenuView.add(popupMenuCopyImageToClipBoardView);
        rightClickMenuView.add(popupMenuCopySelectedImagesToClipBoardView);
        rightClickMenuView.add(popupMenuCopyAllImagesToClipBoardView);
        rightClickMenuView.addSeparator();
        rightClickMenuView.add(popupMenuAddImageToViewList);
        rightClickMenuView.add(popupMenuAddSelectedImagesToViewList);
        rightClickMenuView.add(popupMenuAddAllImagesToViewList);
    }

    public void createRightClickMenuTag() {
        popupMenuAddImagePathToImageRepositoryTag = new JMenuItem(lang.get("maingui.popupmenu.addImagePathToImageRepository"));
        popupMenuRemoveImagePathFromImageRepositoryTag = new JMenuItem(lang.get("maingui.popupmenu.removeImagePathFromImageRepository"));
        popupMenuCopyImageToClipBoardTag = new JMenuItem(lang.get("maingui.popupmenu.copyToSystemClipboard"));
        popupMenuCopySelectedImagesToClipBoardTag = new JMenuItem();
        popupMenuCopyAllImagesToClipBoardTag = new JMenuItem(lang.get("maingui.popupmenu.copyAllToSystemClipboard"));

        rightClickMenuTag = new JPopupMenu();
        rightClickMenuTag.add(popupMenuAddImagePathToImageRepositoryTag);
        rightClickMenuTag.add(popupMenuRemoveImagePathFromImageRepositoryTag);
        rightClickMenuTag.addSeparator();
        rightClickMenuTag.add(popupMenuCopyImageToClipBoardTag);
        rightClickMenuTag.add(popupMenuCopySelectedImagesToClipBoardTag);
        rightClickMenuTag.add(popupMenuCopyAllImagesToClipBoardTag);
    }

    public void createRightClickMenuDirectoryTree() {
        popupMenuAddDirectoryToAllwaysAutomaticallyAddToImageRepositoryList = new JMenuItem(lang.get("imagerepository.addDirectoryToAllwaysAddAutomaticallyList.label"));
        popupMenuAddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList = new JMenuItem(lang.get("imagerepository.addDirectoryToNeverAddAutomaticallyList.label"));

        rightClickMenuDirectoryTree = new JPopupMenu();
        rightClickMenuDirectoryTree.add(popupMenuAddDirectoryToAllwaysAutomaticallyAddToImageRepositoryList);
        rightClickMenuDirectoryTree.add(popupMenuAddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList);
    }

    public void initiateProgram(){
        Update.updateAllUIs();
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

        TreeSet<String> fileNameTemplates = new TreeSet<String>();
        for (int index = 0; index < fileNameTemplateComboBox.getModel().getSize(); index++) {
            fileNameTemplates.add(fileNameTemplateComboBox.getModel().getElementAt(index));
        }
        renameImages.setTemplateFileNameNames(fileNameTemplates);

        TreeSet<String> subDirectoryTemplates = new TreeSet<String>();
        for (int index = 0; index < subFolderTemplateComboBox.getModel().getSize(); index++) {
            subDirectoryTemplates.add(subFolderTemplateComboBox.getModel().getElementAt(index));
        }
        renameImages.setTemplateSubDirectoryNames(subDirectoryTemplates);
        renameImages.setCreateThumbNails(createThumbNailsCheckBox.isSelected());

        TagImages tagImages = configuration.getTagImages();
        TagImagesCategories tagImagesCategories = tagImages.getCategories();
        tagImagesCategories.setOrRadioButtonIsSelected(orRadioButton.isSelected());

        GUI gUI = configuration.getgUI();

        if (this.isVisible()) {
            Rectangle sizeAndLocation = gUI.getMain().getSizeAndLocation();

            sizeAndLocation.setSize(this.getSize().width, this.getSize().height);
            sizeAndLocation.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);
        }

        List<GUIWindowSplitPane> guiWindowSplitPanes = gUI.getMain().getGuiWindowSplitPane();

        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.MAIN, mainSplitPane.getDividerLocation());

        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.VERTICAL, verticalSplitPane.getDividerLocation());
        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.THUMB_NAIL_META_DATA_PANEL, thumbNailMetaPanelSplitPane.getDividerLocation());
        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.PREVIEW_AND_COMMENT, previewAndCommentSplitPane.getDividerLocation());
        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.PREVIEW_COMMENT_CATEGORIES_RATING, previewCommentCategoriesRatingSplitpane.getDividerLocation());

        SortedSet<ImageRepositoryItem> imageRepositoryItems = imageRepositoryListModel.getModel();

        List<File> paths = new ArrayList<File>();

        for (ImageRepositoryItem imageRepositoryItem : imageRepositoryItems) {
            paths.add(imageRepositoryItem.getPath());
        }

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
                } catch (FileNotFoundException fnfex) {
                 categoryExportError(categoryExportFile, fnfex);
                } catch (XMLStreamException xsex) {
                 categoryExportError(categoryExportFile, xsex);
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

                /**
                 * Kontrollera s� att sparad s�kv�g fortfarande existerar
                 * och i annat fall hoppa upp ett steg i tr�dstrukturen och
                 * kontrollera ifall den s�kv�gen existerar
                 **/
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

                    String subFolderName = "";
                    String fileNameTemplate = "";

                    // Remove potential white spaces at the start and end of the
                    // sub directory template
                    subFolderName = subFolderTextField.getText();
                    subFolderName = subFolderName.trim();
                    subFolderTextField.setText(subFolderName);

                    // Remove potential white spaces at the start and end of the
                    // file name template
                    fileNameTemplate = fileNameTemplateTextField.getText();
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
        loadedThumbnailButtons.clearSelections();
        loadedThumbnailButtons.clear();
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

                            JToggleButton thumbContainer = new JToggleButton();
                            thumbContainer.setIcon(new ImageIcon(tn.getThumbNailData()));
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
                            loadedThumbnailButtons.add(thumbContainer);
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
                    loadedThumbnailButtons.addActionListener(thumbNailListener);
                    loadedThumbnailButtons.addMouseListener(mouseRightClickButtonListener);
                    startProcessButton.setEnabled(setStartProcessButtonState());
                    startProcessJMenuItem.setEnabled(setStartProcessButtonState());

                    Table.packColumns(metaDataTable, 6);
                } finally {
                    getThis().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
     * FilesSystemDirectory three and performs the appropiate actions depending
     * on if it was a "right click" or "left click" with the mouse.
     */
    private class FileSystemDirectoryTreeMouselistener extends MouseAdapter {

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

                    boolean repositoryPathContainsJPEGFiles = false;

                    try {
                        repositoryPathContainsJPEGFiles = JPEGUtil.containsJPEGFiles(repositoryPath);
                    } catch (FileNotFoundException fnfex) {
                        repositoryPathContainsJPEGFiles = false;
                        logger.logDEBUG("Problem with determining nr of JPEG files in directory: " + totalPath);
                        logger.logDEBUG(fnfex);
                    } catch (IOException ioex) {
                        repositoryPathContainsJPEGFiles = false;
                        logger.logDEBUG("Problem with determining nr of JPEG files in directory: " + totalPath);
                        logger.logDEBUG(ioex);
                    }

                    setRatingCommentAndCategoryEnabled(false);

                    if (repositoryPathContainsJPEGFiles) {
                        // Reset the flag indicating that an image meta data
                        // base file has been loaded to not loaded, since we are
                        // in the scope of potentially loading a new one.
                        ac.setImageMetaDataDataBaseFileLoaded(false);

                        // Load thumb nails for all JPEG images that exists in the
                        // selected path.
                        loadThumbNails(repositoryPath);

                        File imageMetaDataDataBaseFile = new File(repositoryPath, C.JAVAPEG_IMAGE_META_NAME);

                        ImageRepositoryItem iri = new ImageRepositoryItem(repositoryPath, Status.EXISTS);

                        ImageMetaDataDataBase imageMetaDataDataBase = null;

                        if (!imageRepositoryListModel.contains(iri)) {
                            // If the selected path shall not be added to the
                            // image meta data repository, according to policy
                            // and answer then just do nothing
                            if (!ImageMetaDataDataBaseHandler.addPathToRepositoryAccordingToPolicy(getThis(), repositoryPath)) {
                                thumbNailsPanelHeading.setIcon("resources/images/db_add.png", lang.get("imagerepository.directory.not.added"));
                                thumbNailsPanelHeading.setListener(addSelecetedPathToImageRepository);
                                return;
                            }

                            try {
                                /**
                                 * If an image meta data base file already exists in the
                                 * selected directory, then deserialize that file and make some
                                 * validity testing.
                                 */
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
                                /**
                                 * Create the image meta data base file if it is
                                 * not already existing.
                                 */
                                else {
                                    if (!ImageMetaDataDataBaseHandler.createImageMetaDataDataBaseFileIn(repositoryPath)) {
                                        return;
                                    }
                                    imageMetaDataDataBase = ImageMetaDataDataBaseHandler.deserializeImageMetaDataDataBaseFile(imageMetaDataDataBaseFile);
                                }

                                /**
                                 * Populate the image meta data context with content form the
                                 * deserialized XML file.
                                 */
                                ImageMetaDataDataBaseHandler.populateImageMetaDataContext(imageMetaDataDataBase.getJavaPEGId(), imageMetaDataDataBase.getImageMetaDataItems());

                                /**
                                 * Add the path to the configuration, so the entry will be
                                 * persisted upon application exit.
                                 */
                                configuration.getRepository().getPaths().getPaths().add(repositoryPath);
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

                            /**
                             * If an image meta data base file already exists in the
                             * selected directory, then deserialize that file and make some
                             * validity testing.
                             */
                            if (imageMetaDataDataBaseFile.exists()) {
                                if (!ImageMetaDataDataBaseHandler.isMetaDataBaseValid(imageMetaDataDataBaseFile).getResult()) {
                                    displayErrorMessage(lang.get("imagerepository.repository.corrupt"));
                                    return;
                                }
                                try {
                                    imageMetaDataDataBase = ImageMetaDataDataBaseHandler.deserializeImageMetaDataDataBaseFile(imageMetaDataDataBaseFile);
                                    if (!ImageMetaDataDataBaseHandler.isConsistent(imageMetaDataDataBase, repositoryPath)) {
                                        displayErrorMessage(lang.get("imagerepository.repository.inconsistent"));
                                        return;
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
                                return;
                            }
                        }

                        /**
                         * If the meta data base file is created by this JavaPEG
                         * instance then it shall be possible to make changes to it, if
                         * the file is writable for the current user.
                         */
                        boolean canWrite = imageMetaDataDataBaseFile.canWrite();

                        if (ac.isImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance() && canWrite) {
                            imddbituc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();
                            imddbituc.setRepositoryPath(repositoryPath);
                            imddbituc.setImageMetaDataItems(imageMetaDataDataBase.getImageMetaDataItems());

                            ac.setImageMetaDataDataBaseFileLoaded(true);
                        }

                        /**
                         * Populate the image repository model with any
                         * unpopulated paths.
                         */
                        if(!imageRepositoryListModel.contains(iri)) {
                            imageRepositoryListModel.add(iri);
                        }

                        ac.setImageMetaDataDataBaseFileWritable(canWrite);

                        if (canWrite) {
                            thumbNailsPanelHeading.setIcon("resources/images/db.png", lang.get("imagerepository.directory.added"));
                        }
                        else {
                            thumbNailsPanelHeading.setIcon("resources/images/lock.png", lang.get("imagerepository.directory.added.writeprotected"));
                        }

                        thumbNailsPanelHeading.removeListeners();

                        if (ac.isRestartNeeded()) {
                            displayInformationMessage(lang.get("common.application.restart.needed"));
                        }
                    }
                }
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

        if (!imageRepositoryListModel.contains(new ImageRepositoryItem(selectedPath, Status.EXISTS))) {

            RepositoryExceptions repositoryExceptions = Config.getInstance().get().getRepository().getExceptions();

            boolean isParent = false;
            boolean allwaysAdd = false;

            for (File addAutomatically : repositoryExceptions.getAllwaysAdd()) {
                if (selectedPath.getAbsolutePath().equals((addAutomatically).getAbsolutePath())) {
                    allwaysAdd = true;
                    break;
                } else {
                    if (PathUtil.isChild(selectedPath, addAutomatically)) {
                        allwaysAdd = true;
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

            if (!allwaysAdd && !neverAdd && !isParent) {
                popupMenuAddDirectoryToAllwaysAutomaticallyAddToImageRepositoryList.setActionCommand(totalPath);
                popupMenuAddDirectoryToDoNotAutomaticallyAddDirectoryToImageRepositoryList.setActionCommand(totalPath);
                rightClickMenuDirectoryTree.show(event.getComponent(), event.getX(), event.getY());
            } else {
                if (allwaysAdd) {
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

    private void setNrOfImagesLabels () {
        amountOfImagesInImageListLabel.setText(lang.get("maingui.tabbedpane.imagelist.label.numberOfImagesInList") + " " + Integer.toString(imagesToViewListModel.size()));
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

            Image scaledImage = null;
            if (thumbnail != null) {
                Icon thumbNailIcon = new ImageIcon(thumbnail.getThumbNailData());

                if (thumbNailIcon.getIconHeight() > height || thumbNailIcon.getIconWidth() > width) {
                    thumbNailIcon = null;

                    scaledImage = ImageUtil.createThumbNailAdaptedToAvailableSpace(thumbnail.getThumbNailData(), width, height);
                    imageTagPreviewLabel.setIcon(new ImageIcon(scaledImage));
                } else {
                    imageTagPreviewLabel.setIcon(thumbNailIcon);
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

        OneSizedList<File> queue = new OneSizedList<File>();

        Thread loadScaleIfNeededAndDisplayPreviewThumbnailThread = null;

        @Override
        public void actionPerformed(ActionEvent e) {

            Object source = e.getSource();

            if (source instanceof JToggleButton) {
                JToggleButton toggleButton = (JToggleButton)source;

                // An image is selected
                if (toggleButton.isSelected()) {
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
                        loadedThumbnailButtons.addSelection(toggleButton, pixelsBrightened, percentage);
                    } else {
                        loadedThumbnailButtons.set(toggleButton, pixelsBrightened, percentage);
                    }

                    File jpegImage = new File(e.getActionCommand());

                    imageMetaDataPanel.setMetaData(jpegImage);

                    MainTabbedPaneComponent selectedMainTabbedPaneComponent = MainTabbedPaneComponent.valueOf(((JPanel)mainTabbedPane.getSelectedComponent()).getName());

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
                    // if the Ctrl key is NOT pressed, the clear all selection.
                    if (!((e.getModifiers() & ActionEvent.CTRL_MASK) == ActionEvent.CTRL_MASK)) {
                        loadedThumbnailButtons.clearSelections();
                    }

                    loadedThumbnailButtons.removeSelection(toggleButton);

                    storeCurrentlySelectedImageData();
                    ImageMetaDataDataBaseItemsToUpdateContext.getInstance().setCurrentlySelectedImage(null);
                    clearTagTab();
                    setRatingCommentAndCategoryEnabled(false);
                    imageMetaDataPanel.clearMetaData();
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

        checkTreeManagerForAssignCategoriesCategoryTree.getSelectionModel().clearSelection();

        if (categories != null && categories.size() > 0) {
            DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategoriesCategoryTree.getTreeModel();
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
                checkTreeManagerForAssignCategoriesCategoryTree.getSelectionModel().setSelectionPaths(treePaths.toArray(new TreePath[treePaths.size()]));
            }
        }
    }

    /**
     * @return
     */
    private Categories getSelectedCategoriesFromTreeModel(CheckTreeManager checkTreeManager) {
        Categories selectedId = null;

        // to get the paths that were checked
        TreePath checkedPaths[] = checkTreeManager.getSelectionModel().getSelectionPaths();

        if (checkedPaths != null  && checkedPaths.length > 0 ) {
            selectedId = new Categories();

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

    private Map<String, Categories> getSelectedJavaPegIdToCategoriesMapFromTreeModels(Map<String, CheckTreeManager> javaPegIdToCheckTreeManager) {
        Map<String, Categories> javaPegIdToCategories = null;

        if (javaPegIdToCheckTreeManager != null && !javaPegIdToCheckTreeManager.isEmpty()) {
            javaPegIdToCategories = new HashMap<String, Categories>();

            for (String javaPegId : javaPegIdToCheckTreeManager.keySet()) {
                javaPegIdToCategories.put(javaPegId, getSelectedCategoriesFromTreeModel(javaPegIdToCheckTreeManager.get(javaPegId)));
            }
        }
        return javaPegIdToCategories;
    }

    private void storeCurrentlySelectedImageData() {
        ImageMetaDataDataBaseItemsToUpdateContext irc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();
        File currentlySelectedImage = irc.getCurrentlySelectedImage();

        // Store changes to the currently loaded image.
        if(currentlySelectedImage != null) {
            ImageMetaDataItem imageMetaDataDataBaseItem = null;

            Categories categories = getSelectedCategoriesFromTreeModel(checkTreeManagerForAssignCategoriesCategoryTree);
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

    /**
     * This class implementents the actions that is taking place when the
     * "Remove image button from image view list" is clicked.
     *
     * The selected images are removed and the image after in the list is set
     * as the selected image and the preview image is set to this newly selected
     * image.
     *
     * If there is only one image left after the removal then this image is set
     * to the selected image independent of it was before or after the removed
     * image in image view list.
     *
     * If the last image is removed then the preview image is cleared.
     *
     * @author Fredrik
     *
     */
    private class RemoveSelectedImagesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!imagesToViewList.isSelectionEmpty()) {

                int firstSelectedIndex = imagesToViewList.getSelectedIndex();
                int [] selectedIndices = imagesToViewList.getSelectedIndices();

                // Remove all the selected indices
                while(selectedIndices.length > 0) {
                    imagesToViewListModel.remove(selectedIndices[0]);
                    selectedIndices = imagesToViewList.getSelectedIndices();
                }

                if (imagesToViewListModel.isEmpty()) {
                    // All images have been removed, clear the preview image
                    imagePreviewLabel.setIcon(null);
                } else {
                    if (firstSelectedIndex == 0) {
                        // If the first image was removed
                        imagesToViewList.setSelectedIndex(firstSelectedIndex);
                    } else if (firstSelectedIndex >= imagesToViewListModel.getSize()) {
                        // The last image was removed
                        imagesToViewList.setSelectedIndex(imagesToViewListModel.getSize() - 1);
                    } else  {
                        // An image inbetween the first and last was removed
                        imagesToViewList.setSelectedIndex(firstSelectedIndex);
                    }
                }
                setNrOfImagesLabels();
            }
        }
    }

    private class RemoveAllImagesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imagesToViewListModel.size() > 0) {
                imagesToViewListModel.clear();
                imagePreviewLabel.setIcon(null);
                setNrOfImagesLabels();
            }
        }
    }

    private class OpenImageListListener implements ActionListener {
        @Override
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

                        missingFilesErrorMessage.append(lang.get("maingui.tabbedpane.imagelist.filechooser.openImageList.missingFilesErrorMessage"));
                        missingFilesErrorMessage.append(C.LS);
                        missingFilesErrorMessage.append(C.LS);

                        for(String path : notExistingFiles) {
                            missingFilesErrorMessage.append(path);
                            missingFilesErrorMessage.append(C.LS);
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
        @Override
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
        @Override
        public void actionPerformed(ActionEvent e) {
            int selecteIndex = imagesToViewList.getSelectedIndex();

            if(selecteIndex > -1) {
                if(selecteIndex > 0) {
                    File image = imagesToViewListModel.remove(selecteIndex);
                    imagesToViewListModel.add(selecteIndex - 1, image);
                    imagesToViewList.setSelectedIndex(selecteIndex - 1);
                }
            }
        }
    }

    private class MoveImageDownInListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selecteIndex = imagesToViewList.getSelectedIndex();

            if(selecteIndex > -1) {
                if(selecteIndex < (imagesToViewListModel.size() - 1)) {
                    File image = imagesToViewListModel.remove(selecteIndex);
                    imagesToViewListModel.add(selecteIndex + 1, image);
                    imagesToViewList.setSelectedIndex(selecteIndex + 1);
                }
            }
        }
    }

    private class MoveImageToTopInListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selecteIndex = imagesToViewList.getSelectedIndex();

            if (selecteIndex > 0) {
                File image = imagesToViewListModel.remove(selecteIndex);
                imagesToViewListModel.add(0, image);
                imagesToViewList.setSelectedIndex(0);
            }
        }
    }

    private class MoveImageToBottomInListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selecteIndex = imagesToViewList.getSelectedIndex();

            if (selecteIndex > -1) {
                File image = imagesToViewListModel.remove(selecteIndex);
                imagesToViewListModel.addElement(image);
                imagesToViewList.setSelectedIndex(imagesToViewListModel.size() - 1);
            }
        }
    }

    private class OpenImageViewerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!imagesToViewListModel.isEmpty()) {

                ApplicationContext ac = ApplicationContext.getInstance();
                if (ac.isImageViewerDisplayed()) {
                    JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.onlyOneImageViewer"), lang.get("errormessage.maingui.informationMessageLabel"), JOptionPane.INFORMATION_MESSAGE);
                } else {
                    List<File> imagesToView = new ArrayList<File>();

                    for (int i = 0; i < imagesToViewListModel.size(); i++) {
                        imagesToView.add(imagesToViewListModel.get(i));
                    }

                    imageViewer = new ImageViewer(imagesToView);
                    imageViewer.setVisible(true);

                    // This can only be done when the image viewer is visible
                    // and with an image loaded, otherwise does nothing happen
                    // since the image is of size 0 pixels before the window is
                    // displayed.
                    if (configuration.getImageViewerState().isAutomaticallyResizeImages()) {
                        imageViewer.resizeImage();
                    }

                    ac.setImageViewerDisplayed(true);
                }
            }
        }
    }

    private class OpenImageResizerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!imagesToViewListModel.isEmpty()) {

                List<File> imagesToResize = new ArrayList<File>();

                for (int i = 0; i < imagesToViewListModel.size(); i++) {
                    imagesToResize.add(imagesToViewListModel.get(i));
                }

                ImageResizer imageresizer = new ImageResizer(imagesToResize);
                imageresizer.setVisible(true);
            }
        }
    }

    private class CopyImageListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!imagesToViewListModel.isEmpty()) {
                List<File> imagesToSetToSystemClipBoard = new ArrayList<File>();

                for (int i = 0; i < imagesToViewListModel.size(); i++) {
                    imagesToSetToSystemClipBoard.add(imagesToViewListModel.get(i));
                }
                FileSelection fileSelection = new FileSelection(imagesToSetToSystemClipBoard);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
            }
        }
    }

    private class SearchImagesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Prepare and perform the image search...
            Set<File> foundImages = ImageMetaDataContextUtil.performImageSearch(collectSearchParameters());

            if (foundImages.size() > 0) {
                // ... and create file and timestamp pairs...
                ImageMetaDataContext imdc = ImageMetaDataContext.getInstance();

                List<FileAndTimeStampPair> fileAndTimeStampPairs = new ArrayList<>(foundImages.size());
                for (File foundImage : foundImages) {
                    fileAndTimeStampPairs.add(new FileAndTimeStampPair(foundImage, imdc.getDateTime(foundImage)));
                }

                // ... and sort the file and timestamp pairs...
                Collections.sort(fileAndTimeStampPairs);

                // ... and create a new list with the file object from the
                // sorted file and timestamp pairs.
                List<File> foundImagesAsSortedList = new ArrayList<File>(foundImages.size());
                for (FileAndTimeStampPair fileAndTimeStampPair : fileAndTimeStampPairs) {
                    foundImagesAsSortedList.add(fileAndTimeStampPair.getFile());
                }

                ImageSearchResultViewer imagesearchResultViewer = new ImageSearchResultViewer(foundImagesAsSortedList);
                imagesearchResultViewer.setVisible(true);
            } else {
                displayInformationMessage(lang.get("findimage.searchImages.result"));
            }
        }
    }

    private class ClearCategoriesSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            javaPegIdToCheckTreeManager.get(configuration.getJavapegClientId()).getSelectionModel().clearSelection();
        }
    }

    private class ImportedClearCategoriesSelectionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CheckTreeManager checkTreeManager = javaPegIdToCheckTreeManager.get(e.getActionCommand());

            if (checkTreeManager != null) {
                checkTreeManager.getSelectionModel().clearSelection();
            }
        }
    }

    private class ClearAllMetaDataParametersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (javaPegIdToCheckTreeManager != null) {
                for (CheckTreeManager checkTreeManager : javaPegIdToCheckTreeManager.values()) {
                    checkTreeManager.getSelectionModel().clearSelection();
                }
            }

            yearMetaDataValue.clearValue();
            monthMetaDataValue.clearValue();
            dayMetaDataValue.clearValue();
            hourMetaDataValue.clearValue();
            minuteMetaDataValue.clearValue();
            secondMetaDataValue.clearValue();
            imagesSizeMetaDataValue.clearValue();
            isoMetaDataValue.clearValue();
            shutterSpeedMetaDataValue.clearValue();
            apertureValueMetaDataValue.clearValue();
            cameraModelMetaDataValue.clearValue();

            for (JCheckBox ratingCheckBox : ratingCheckBoxes) {
                ratingCheckBox.setSelected(false);
            }

            commentTextArea.setText("");
        }
    }

    private class ExportImageListListener implements ActionListener {
        @Override
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
        @Override
        public void actionPerformed(ActionEvent e) {
            File image = new File(e.getActionCommand());
            handleAddImageToImageList(image, true);
        }
    }

    /**
     * This listener class adds all currently selected images in the thumbnail
     * overview to the list of images to display in the {@link ImageViewer} GUI.
     *
     * @author Fredrik
     *
     */
    private class AddSelectedImagesToViewList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<File> selectedAsFileObjects = loadedThumbnailButtons.getSelectedAsFileObjects();
            int size = selectedAsFileObjects.size();

            for (int index = 0; index < size; index++) {
                if (index == size - 1) {
                    handleAddImageToImageList(selectedAsFileObjects.get(index), true);
                } else {
                    handleAddImageToImageList(selectedAsFileObjects.get(index), false);
                }
            }
        }
    }

    private class CopyImageToSystemClipBoard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<File> selectedFiles = new ArrayList<File>();
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
               FileSelection fileSelection = new FileSelection(new ArrayList<File>(jPEGFiles));
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

            List<File> allDisplayedThumbnailsAsFileObjects = loadedThumbnailButtons.getAllAsFileObjects();
            int size = allDisplayedThumbnailsAsFileObjects.size();

            for (int index = 0; index < size; index++) {
                if (index == size -1) {
                    handleAddImageToImageList(allDisplayedThumbnailsAsFileObjects.get(index), true);
                } else {
                    handleAddImageToImageList(allDisplayedThumbnailsAsFileObjects.get(index), false);
                }
            }
        }
    }

    private class AddCategory implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            TreePath selectedPath = ApplicationContext.getInstance().getSelectedCategoryPath();
            String categoryName = null;
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
        }
    }

    private class RenameCategory implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            TreePath selectedPath = ApplicationContext.getInstance().getSelectedCategoryPath();

            DefaultTreeModel model = (DefaultTreeModel)checkTreeManagerForAssignCategoriesCategoryTree.getTreeModel();

            DefaultMutableTreeNode nodeToRename = ((DefaultMutableTreeNode)selectedPath.getLastPathComponent());
            String value = ((CategoryUserObject)nodeToRename.getUserObject()).getName();

            String newName = displayInputDialog(lang.get("categrory.rename"), lang.get("category.enterNewNameForCategory")+ " " + value, value);

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
            List<File> selectedThmbnailButtonsAsFiles = loadedThumbnailButtons.getSelectedAsFileObjects();
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
            List<File> allThmbnailButtonsAsFiles = loadedThumbnailButtons.getAllAsFileObjects();
            setSelectedCategoriesToImages(allThmbnailButtonsAsFiles);
        }
    }

    private void setSelectedCategoriesToImages(List<File> imageFiles) {
        // Only do this if any image is selected...
        if (imageFiles != null && !imageFiles.isEmpty()) {

            Categories selectedCategoriesFromTreeModel = getSelectedCategoriesFromTreeModel(checkTreeManagerForAssignCategoriesCategoryTree);

            // ... and there are any categories selected.
            if (selectedCategoriesFromTreeModel.size() > 0) {
                ImageMetaDataDataBaseItemsToUpdateContext imddbituc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();

                for (File imageFile : imageFiles) {
                    ImageMetaDataItem imageMetaDataBaseItem = imddbituc.getImageMetaDataBaseItem(imageFile);

                    if (imageMetaDataBaseItem != null) {
                        Categories currentlyStoredCategories = imageMetaDataBaseItem.getCategories();

                        if (currentlyStoredCategories.addCategories(selectedCategoriesFromTreeModel.getCategories())) {
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
                TreeUtil.collapseEntireTree(tree, (DefaultMutableTreeNode)selectedPath.getLastPathComponent(), true);
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
                TreeUtil.expandEntireTree(tree, (DefaultMutableTreeNode)selectedPath.getLastPathComponent(), true);
            }
        }
    }

    private class ImagesToViewListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int selectedIndex = imagesToViewList.getSelectedIndex();

            if (selectedIndex > -1) {
                JPEGThumbNail thumbNail = JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(imagesToViewListModel.get(selectedIndex));
                imagePreviewLabel.setIcon(new ImageIcon(thumbNail.getThumbNailData()));
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

    private ImageMetaDataContextSearchParameters collectSearchParameters() {

        ImageMetaDataContextSearchParameters imdcsp = new ImageMetaDataContextSearchParameters();
        imdcsp.setJavaPegIdToCategoriesMap(getSelectedJavaPegIdToCategoriesMapFromTreeModels(javaPegIdToCheckTreeManager));
        imdcsp.setJavaPegToAndCategoriesSearch(getImportedAndCategoriesSearch(importedButtonGroups));

        imdcsp.setYear(yearMetaDataValue.getValue());
        imdcsp.setMonth(monthMetaDataValue.getValue());
        imdcsp.setDay(dayMetaDataValue.getValue());
        imdcsp.setHour(hourMetaDataValue.getValue());
        imdcsp.setMinute(minuteMetaDataValue.getValue());
        imdcsp.setSecond(secondMetaDataValue.getValue());
        imdcsp.setFNumber(apertureValueMetaDataValue.getValue());
        imdcsp.setCameraModel(cameraModelMetaDataValue.getValue());
        imdcsp.setComment(commentTextArea.getText());
        imdcsp.setImageSize(imagesSizeMetaDataValue.getValue());
        imdcsp.setIso(isoMetaDataValue.getValue());
        imdcsp.setRating(getSelectedRatings());
        imdcsp.setShutterSpeed(shutterSpeedMetaDataValue.getValue());

        return imdcsp;
    }

    private Map<String, Boolean> getImportedAndCategoriesSearch(List<ButtonGroup> importedButtonGroups) {
        Map<String, Boolean> javaPegIdToImportedAndCategories = null;

        // Set the state for this client "AND" button...
        javaPegIdToImportedAndCategories = new HashMap<String, Boolean>();
        javaPegIdToImportedAndCategories.put(configuration.getJavapegClientId(), andRadioButton.isSelected());

        // And set the state of the "AND" button for any imported categories.
        if (importedButtonGroups != null && importedButtonGroups.size() > 0) {

            for (ButtonGroup buttonGroup : importedButtonGroups) {

                Enumeration<AbstractButton> abstractButtons = buttonGroup.getElements();

                while (abstractButtons.hasMoreElements()) {
                    AbstractButton abstractButton = abstractButtons.nextElement();

                    if (abstractButton.isSelected()) {
                        javaPegIdToImportedAndCategories.put(abstractButton.getName(), abstractButton.getActionCommand().equals("AND") ? true : false);
                    }
                }
            }
        }
        return javaPegIdToImportedAndCategories;
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
        checkTreeManagerForAssignCategoriesCategoryTree.getSelectionModel().clearSelection();
    }

    /**
     * This method adds an image to the image view list {@link ListModel} and
     * set this image is also set as the selected image. It also updates the
     * label that displays the amount of images in the list. It also adds the
     * image to the {@link ImageViewer} if that one is displayed.
     *
     * @param image
     *            is the image to add.
     * @param addMetaData
     *            specifies if the meta data that is associated to the images to
     *            view list should be updated or not.
     */
    private void handleAddImageToImageList(File image, boolean addMetaData) {
        imagesToViewListModel.addElement(image);

        if (addMetaData) {
            imagesToViewList.setSelectedIndex(imagesToViewListModel.getSize() - 1);
            imagesToViewList.ensureIndexIsVisible(imagesToViewList.getSelectedIndex());

            setNrOfImagesLabels();
        }

        if (ApplicationContext.getInstance().isImageViewerDisplayed()) {
            imageViewer.addImage(image);
        }
    }

    private class MouseButtonListener extends MouseAdapter{
        @Override
        public void mouseReleased(MouseEvent e){
            if(e.isPopupTrigger() && (mainTabbedPane.getSelectedIndex() == 0)) {
                if (imageRepositoryListModel.contains(new ImageRepositoryItem(ApplicationContext.getInstance().getSourcePath(), Status.EXISTS))) {
                    popupMenuAddImagePathToImageRepositoryMerge.setVisible(false);
                    popupMenuRemoveImagePathFromImageRepositoryMerge.setVisible(true);
                } else {
                    popupMenuAddImagePathToImageRepositoryMerge.setVisible(true);
                    popupMenuRemoveImagePathFromImageRepositoryMerge.setVisible(false);
                }
                popupMenuCopyImageToClipBoardMerge.setActionCommand(((JToggleButton)e.getComponent()).getActionCommand());
                rightClickMenuMerge.show(e.getComponent(),e.getX(), e.getY());
            } else if(e.isPopupTrigger() && (mainTabbedPane.getSelectedIndex() == 1)) {
                if (imageRepositoryListModel.contains(new ImageRepositoryItem(ApplicationContext.getInstance().getSourcePath(), Status.EXISTS))) {
                    popupMenuAddImagePathToImageRepositoryRename.setVisible(false);
                    popupMenuRemoveImagePathFromImageRepositoryRename.setVisible(true);
                } else {
                    popupMenuAddImagePathToImageRepositoryRename.setVisible(true);
                    popupMenuRemoveImagePathFromImageRepositoryRename.setVisible(false);
                }
                popupMenuCopyImageToClipBoardRename.setActionCommand(((JToggleButton)e.getComponent()).getActionCommand());
                rightClickMenuRename.show(e.getComponent(),e.getX(), e.getY());
            } else if(e.isPopupTrigger() && (mainTabbedPane.getSelectedIndex() == 2)) {
                if (imageRepositoryListModel.contains(new ImageRepositoryItem(ApplicationContext.getInstance().getSourcePath(), Status.EXISTS))) {
                    popupMenuAddImagePathToImageRepositoryTag.setVisible(false);
                    popupMenuRemoveImagePathFromImageRepositoryTag.setVisible(true);
                } else {
                    popupMenuAddImagePathToImageRepositoryTag.setVisible(true);
                    popupMenuRemoveImagePathFromImageRepositoryTag.setVisible(false);
                }
                popupMenuCopyImageToClipBoardTag.setActionCommand(((JToggleButton)e.getComponent()).getActionCommand());
                rightClickMenuTag.show(e.getComponent(), e.getX(), e.getY());
            } else if(e.isPopupTrigger() && (mainTabbedPane.getSelectedIndex() == 3)) {
                if (imageRepositoryListModel.contains(new ImageRepositoryItem(ApplicationContext.getInstance().getSourcePath(), Status.EXISTS))) {
                    popupMenuAddImagePathToImageRepositoryView.setVisible(false);
                    popupMenuRemoveImagePathFromImageRepositoryView.setVisible(true);
                } else {
                    popupMenuAddImagePathToImageRepositoryView.setVisible(true);
                    popupMenuRemoveImagePathFromImageRepositoryView.setVisible(false);
                }
                popupMenuAddImageToViewList.setActionCommand(((JToggleButton)e.getComponent()).getActionCommand());
                popupMenuCopyImageToClipBoardView.setActionCommand(((JToggleButton)e.getComponent()).getActionCommand());
                rightClickMenuView.show(e.getComponent(),e.getX(), e.getY());
            } else if ((!e.isPopupTrigger()) && (e.getClickCount() == 2) && (mainTabbedPane.getSelectedIndex() == 3)) {
                File image = new File(((JToggleButton)e.getComponent()).getActionCommand());
                handleAddImageToImageList(image, true);
            }
        }
    }

    private class MainTabbedPaneListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
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
                /**
                 * Since the only thing to do is to update the
                 * ImageMetaDataContext there is nothing to do here, except
                 * from updating the ApplicationContext with the currently
                 * selected tab since the selected tab is the only one that can
                 * have unsaved meta data.
                 */
                ac.setMainTabbedPaneComponent(MainTabbedPaneComponent.CATEGORIZE);
                break;
            }
        }
    }

    private class AddSelecetedPathToImageRepository implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            ApplicationContext ac = ApplicationContext.getInstance();

            File repositoryPath = ac.getSourcePath();

            File imageMetaDataDataBaseFile = new File(repositoryPath, C.JAVAPEG_IMAGE_META_NAME);

            try {
                ImageMetaDataDataBase imageMetaDataDataBase;

                /**
                 * If an image meta data base file already exists in the
                 * selected directory, then deserialize that file and make some
                 * validity testing.
                 */
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
                /**
                 * Create the image meta data base file if not already
                 * existing.
                 */
                else {
                    if (!ImageMetaDataDataBaseHandler.createImageMetaDataDataBaseFileIn(repositoryPath)) {
                        return;
                    }
                    ac.setImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance(true);
                    imageMetaDataDataBase = ImageMetaDataDataBaseHandler.deserializeImageMetaDataDataBaseFile(imageMetaDataDataBaseFile);
                }

                /**
                 * Populate the image meta data context with content form the
                 * deserialized XML file.
                 */
                ImageMetaDataDataBaseHandler.populateImageMetaDataContext(imageMetaDataDataBase.getJavaPEGId(), imageMetaDataDataBase.getImageMetaDataItems());

                /**
                 * If the meta data base file is created by this JavaPEG
                 * instance then shall it be possible to make changes to it, if
                 * the file is writable for the current user.
                 */
                ac.setImageMetaDataDataBaseFileWritable(imageMetaDataDataBaseFile.canWrite());

                if (ac.isImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance() && ac.isImageMetaDataDataBaseFileWritable()) {
                    ImageMetaDataDataBaseItemsToUpdateContext imddbituc = ImageMetaDataDataBaseItemsToUpdateContext.getInstance();

                    imddbituc.setRepositoryPath(repositoryPath);
                    imddbituc.setImageMetaDataItems(imageMetaDataDataBase.getImageMetaDataItems());

                    ac.setImageMetaDataDataBaseFileLoaded(true);
                }

                /**
                 * Populate the image repository model with the currently
                 * selected path.
                 */
                ImageRepositoryItem iri = new ImageRepositoryItem(repositoryPath, Status.EXISTS);
                imageRepositoryListModel.add(iri);

                /**
                 * Add the path to the configuration, so the entry will be
                 * persisted upon application exit.
                 */
                configuration.getRepository().getPaths().getPaths().add(repositoryPath);

                if (ac.isImageMetaDataDataBaseFileWritable()) {
                    thumbNailsPanelHeading.setIcon("resources/images/db.png", lang.get("imagerepository.directory.added"));
                }
                else {
                    thumbNailsPanelHeading.setIcon("resources/images/lock.png", lang.get("imagerepository.directory.added.writeprotected"));
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

    private class RemoveSelecetedPathFromImageRepository implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ImageRepositoryItem iri = new ImageRepositoryItem(ApplicationContext.getInstance().getSourcePath(), Status.EXISTS);
            imageRepositoryListModel.removeElement(iri);
        }
    }

    private class AddDirectoryToAllwaysAutomaticallyAddToImageRepositoryList implements ActionListener {
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
     *            contains the new temlate.
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
                    String collapseCategory = "";
                    String expandCategory = "";
                    String addCategory = "";
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

                    /**
                     * If no category has been selected.
                     */
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
                                this.createMenu(CategoryMenueType.NO_RENAME_REMOVE);
                            } else if (someChildCanBeExpanded && !someChildIsExpanded) {
                                this.createMenu(CategoryMenueType.NO_RENAME_REMOVE_COLLAPSE);
                            } else if (!someChildCanBeExpanded && !someChildIsExpanded) {
                                this.createMenu(CategoryMenueType.NO_RENAME_REMOVE_EXPAND_COLLAPSE);
                            } else if (!someChildCanBeExpanded && someChildIsExpanded) {
                                this.createMenu(CategoryMenueType.NO_RENAME_REMOVE_EXPAND);
                            }
                        } else {
                            this.createMenu(CategoryMenueType.NO_RENAME_REMOVE_EXPAND_COLLAPSE);
                        }
                    }
                    /**
                     * If a category has been selected
                     */
                    else {
                        if (treeNode.getChildCount() > 0) {
                            if (categoryTree.isExpanded(selectedPath)) {
                                this.createMenu(CategoryMenueType.NO_EXPAND);
                            } else {
                                this.createMenu(CategoryMenueType.NO_COLLAPSE);
                            }
                        } else {
                            this.createMenu(CategoryMenueType.NO_EXPAND_COLLAPSE);
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

                @SuppressWarnings("unchecked")
                Enumeration<DefaultMutableTreeNode> preorderEnumeration = lastPathComponent.preorderEnumeration();
                while(preorderEnumeration.hasMoreElements()){
                    categoriesTree.getSelectionModel().addSelectionPath(getPath(preorderEnumeration.nextElement()));
                }
            }
        }

        public TreePath getPath(TreeNode treeNode) {
            List<Object> nodes = new ArrayList<Object>();
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

        private void createMenu(CategoryMenueType categoryMenyType) {

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

            rightClickMenuCategories.addSeparator();
            rightClickMenuCategories.add(popupMenuSaveSelectedCategoriesToAllImages);

            // Add this menu item if there is more than one selected image.
            if (loadedThumbnailButtons.size() > 1) {
                rightClickMenuCategories.add(popupMenuSaveSelectedCategoriesToSelectedImages);
            }
        }
    }

    /**
     * This class extends the {@link SwingWorker} and performs the long running
     * task of loading all the configured image meta data repository files in a
     * separate thread. When the loading is done, then the "search image" button
     * is set to enabled, and thereby it is possible to perform searches in the
     * image meta data repository.
     *
     * @author Fredrik
     *
     */
    private class ImageMetaDataContextLoader extends SwingWorker<ResultObject<String[]>, String> {
        @Override
        protected ResultObject<String[]> doInBackground() throws Exception {
            return ImageMetaDataContextUtil.initiateImageMetaDataContext(configuration.getRepository().getPaths(), imageRepositoryListModel, logger);
        }

        @Override
        protected void done() {
            searchImagesButton.setEnabled(true);
            searchImagesButton.setToolTipText(lang.get("findimage.searchImages.tooltip"));

            MetaDataTextfieldListener mdtl = new MetaDataTextfieldListener();

            yearMetaDataValue.setEnabled(true);
            yearMetaDataValue.setMouseListener(mdtl);
            monthMetaDataValue.setEnabled(true);
            monthMetaDataValue.setMouseListener(mdtl);
            dayMetaDataValue.setEnabled(true);
            dayMetaDataValue.setMouseListener(mdtl);
            hourMetaDataValue.setEnabled(true);
            hourMetaDataValue.setMouseListener(mdtl);
            minuteMetaDataValue.setEnabled(true);
            minuteMetaDataValue.setMouseListener(mdtl);
            secondMetaDataValue.setEnabled(true);
            secondMetaDataValue.setMouseListener(mdtl);
            imagesSizeMetaDataValue.setEnabled(true);
            imagesSizeMetaDataValue.setMouseListener(mdtl);
            isoMetaDataValue.setEnabled(true);
            isoMetaDataValue.setMouseListener(mdtl);
            shutterSpeedMetaDataValue.setEnabled(true);
            shutterSpeedMetaDataValue.setMouseListener(mdtl);
            apertureValueMetaDataValue.setEnabled(true);
            apertureValueMetaDataValue.setMouseListener(mdtl);
            cameraModelMetaDataValue.setEnabled(true);
            cameraModelMetaDataValue.setMouseListener(mdtl);

            try {
                ResultObject<String[]> result = get();

                String[] errorMessages = result.getObject();

                if (errorMessages[0] != null || errorMessages[1] != null) {
                    StringBuilder errorMessage = new StringBuilder();

                    if (errorMessages[0] != null) {
                        errorMessage.append(lang.get("imagerepository.repositories.inconsistent"));
                        errorMessage.append(C.LS);
                        errorMessage.append(C.LS);
                        errorMessage.append(errorMessages[0]);
                        errorMessage.append(C.LS);
                        errorMessage.append(lang.get("imagerepository.repositories.inconsistent.repair"));
                        errorMessage.append(C.LS);
                        errorMessage.append(C.LS);
                    }

                    if (errorMessages[1] != null) {
                        errorMessage.append(lang.get("imagerepository.repositories.corrupt"));
                        errorMessage.append(C.LS);
                        errorMessage.append(C.LS);
                        errorMessage.append(errorMessages[1]);
                        errorMessage.append(C.LS);
                        errorMessage.append(lang.get("imagerepository.repositories.corrupt.repair"));
                    }
                    displayErrorMessage(errorMessage.toString());
                }

                if (ApplicationContext.getInstance().isRestartNeeded()) {
                    displayInformationMessage(lang.get("common.application.restart.needed"));
                }
            } catch (InterruptedException e) {
                logger.logERROR(e);
            } catch (ExecutionException e) {
                logger.logERROR(e);
            }
            logger.logDEBUG("Image Meta Data Context initialization Finished");
        }
    }
}
