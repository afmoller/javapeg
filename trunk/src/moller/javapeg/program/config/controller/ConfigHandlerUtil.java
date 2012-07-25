package moller.javapeg.program.config.controller;



import java.awt.Rectangle;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import moller.javapeg.program.categories.CategoryUserObject;
import moller.javapeg.program.config.model.Language;
import moller.javapeg.program.config.model.Logging;
import moller.javapeg.program.config.model.ToolTips;
import moller.javapeg.program.config.model.UpdatesChecker;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPane;
import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesCategories;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPaths;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesPreview;
import moller.javapeg.program.config.model.categories.ImportedCategories;
import moller.javapeg.program.config.model.repository.Repository;
import moller.javapeg.program.config.model.repository.RepositoryExceptions;
import moller.javapeg.program.config.model.repository.RepositoryPaths;
import moller.javapeg.program.config.model.thumbnail.ThumbNail;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCache;
import moller.javapeg.program.config.model.thumbnail.ThumbNailCreation;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.enumerations.Level;
import moller.javapeg.program.logger.Logger;
import moller.util.jpeg.JPEGScaleAlgorithm;
import moller.util.string.StringUtil;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ConfigHandlerUtil {

    public static Logging getLoggingConfig(Node loggingNode, XPath xPath) {
        Logging logging = new Logging();

        try {
            logging.setFileName((String)xPath.evaluate("fileName", loggingNode, XPathConstants.STRING));
            logging.setDeveloperMode(Boolean.valueOf((String)xPath.evaluate("developerMode", loggingNode, XPathConstants.STRING)));
            logging.setLevel(Level.valueOf((String)xPath.evaluate("level", loggingNode, XPathConstants.STRING)));
            logging.setRotate(Boolean.valueOf((String)xPath.evaluate("rotate", loggingNode, XPathConstants.STRING)));
            logging.setRotateSize(new Long((String)xPath.evaluate("rotateSize", loggingNode, XPathConstants.STRING)));
            logging.setRotateZip(Boolean.valueOf((String)xPath.evaluate("rotateZip", loggingNode, XPathConstants.STRING)));
            logging.setTimeStampFormat(new SimpleDateFormat((String)xPath.evaluate("timestampFormat", loggingNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return logging;
    }

    public static TreeNode getCategoriesConfig(Node categoriesNode, XPath xPath) {

        DefaultMutableTreeNode root = null;
        boolean createCategoriesModelSuccess = false;

        Logger logger = Logger.getInstance();

        try {
            String id = (String)xPath.evaluate("@highest-used-id", categoriesNode, XPathConstants.STRING);
            ApplicationContext.getInstance().setHighestUsedCategoryID(Integer.parseInt(id));

            root = new DefaultMutableTreeNode(new CategoryUserObject("root", "-1"));
            populateTreeModelFromNode(categoriesNode, root, xPath);
            createCategoriesModelSuccess = true;
        } catch (XPathExpressionException xpee) {
            logger.logFATAL("Invalid XPathExpression. See stacktrace below for details.");
            logger.logFATAL(xpee);
        }

        if (!createCategoriesModelSuccess) {
            moller.javapeg.program.language.Language lang = moller.javapeg.program.language.Language.getInstance();
            displayErrorMessage(lang.get("category.categoriesModel.create.error"), lang.get("errormessage.maingui.errorMessageLabel"));
            System.exit(1);
        }

        return root;
    }

    public static void populateTreeModelFromNode(Node categoriesElement, DefaultMutableTreeNode root, XPath xPath) {

        try {
            NodeList categoryElements = (NodeList)xPath.evaluate("category", categoriesElement, XPathConstants.NODESET);
            if (categoryElements.getLength() > 0) {
                for (int i = 0; i < categoryElements.getLength(); i++) {
                    populateNodeBranch(root, categoryElements.item(i), xPath);
                }
            }
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private static void populateNodeBranch(DefaultMutableTreeNode node, Node category, XPath xPath ) {

        try {
            String displayString = (String)xPath.evaluate("@name", category, XPathConstants.STRING);
            String identityString = (String)xPath.evaluate("@id", category, XPathConstants.STRING);

            CategoryUserObject cuo = new CategoryUserObject(displayString, identityString);

            DefaultMutableTreeNode mtn = new DefaultMutableTreeNode(cuo);

            node.add(mtn);

            NodeList categoryChildren = (NodeList)xPath.evaluate("category", category, XPathConstants.NODESET);

            if (categoryChildren.getLength() > 0) {
                for (int i = 0; i < categoryChildren.getLength(); i++) {
                    populateNodeBranch(mtn, categoryChildren.item(i), xPath);
                }
            }
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static GUI getGUIConfig(Node gUINode, XPath xPath) {
        GUI gui = new GUI();

        try {
            gui.setConfigViewer(createGUIWindow((Node)xPath.evaluate("configViewer", gUINode, XPathConstants.NODE), xPath));
            gui.setHelpViewer(createGUIWindow((Node)xPath.evaluate("helpViewer", gUINode, XPathConstants.NODE), xPath));
            gui.setImageSearchResultViewer(createGUIWindow((Node)xPath.evaluate("imageSearchResultViewer", gUINode, XPathConstants.NODE), xPath));
            gui.setImageViewer(createGUIWindow((Node)xPath.evaluate("imageViewer", gUINode, XPathConstants.NODE), xPath));
            gui.setMain(createGUIWindow((Node)xPath.evaluate("main", gUINode, XPathConstants.NODE), xPath));

        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return gui;
    }

    private static GUIWindow createGUIWindow(Node windowNode, XPath xPath) {
        GUIWindow guiWindow = new GUIWindow();

        Rectangle sizeAndLocation = new Rectangle();

        try {
            sizeAndLocation.x = StringUtil.getIntValue((String)xPath.evaluate("xLocation", windowNode, XPathConstants.STRING), 1);
            sizeAndLocation.y = StringUtil.getIntValue((String)xPath.evaluate("yLocation", windowNode, XPathConstants.STRING), 1);
            sizeAndLocation.width = StringUtil.getIntValue((String)xPath.evaluate("width", windowNode, XPathConstants.STRING), 100);
            sizeAndLocation.height = StringUtil.getIntValue((String)xPath.evaluate("height", windowNode, XPathConstants.STRING), 100);

            guiWindow.setSizeAndLocation(sizeAndLocation);
            guiWindow.setGuiWindowSplitPane(createGUIWindowSplitPane((NodeList)xPath.evaluate("splitPane", windowNode, XPathConstants.NODESET), xPath));
        } catch (XPathExpressionException e) {

            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return guiWindow;
    }

    private static List<GUIWindowSplitPane> createGUIWindowSplitPane(NodeList splitPaneNodeList, XPath xPath) {
        List<GUIWindowSplitPane> guiWindowSplitPanes = new ArrayList<GUIWindowSplitPane>();

        for (int index = 0; index < splitPaneNodeList.getLength(); index++) {
            try {
                GUIWindowSplitPane guiWindowSplitPane = new GUIWindowSplitPane();

                Node splitPaneNode = splitPaneNodeList.item(index);

                guiWindowSplitPane.setLocation(StringUtil.getIntValue(splitPaneNode.getTextContent(), 1));

                String id = (String)xPath.evaluate("@id", splitPaneNode, XPathConstants.STRING);
                String width = (String)xPath.evaluate("@width", splitPaneNode, XPathConstants.STRING);

                guiWindowSplitPane.setId(id);
                guiWindowSplitPane.setWidth(width.equals("") ? null : Integer.parseInt(width));

                guiWindowSplitPanes.add(guiWindowSplitPane);
            } catch (XPathExpressionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return guiWindowSplitPanes;
    }

    public static String getJavapegClientIdConfig(NodeList javapegClientIdNodeList) {
        Node javapegClientIdNode = javapegClientIdNodeList.item(0);

        NodeList children = javapegClientIdNode.getChildNodes();

        for (int index = 0; index < children.getLength(); index++) {

            Node child = children.item(index);

            if ("javapegClientId".equals(child.getNodeName())) {
                return child.getTextContent();
            }
        }
        return "NOT-DEFINED";
    }

    public static Language getLanguageConfig(Node languageNode, XPath xPath) {
        Language language = new Language();

        try {
            language.setAutomaticSelection(Boolean.valueOf((String)xPath.evaluate("automaticSelection", languageNode, XPathConstants.STRING)));
            language.setgUILanguageISO6391((String)xPath.evaluate("gUILanguageISO6391", languageNode, XPathConstants.STRING));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return language;
    }

    public static RenameImages getRenameImagesConfig(Node renameImagesNode, XPath xPath) {
        RenameImages renameImages = new RenameImages();

        try {
            renameImages.setCameraModelNameMaximumLength(StringUtil.getIntValue((String)xPath.evaluate("cameraModelNameMaximumLength", renameImagesNode, XPathConstants.STRING), 0));
            renameImages.setCreateThumbNails(Boolean.valueOf((String)xPath.evaluate("createThumbNails", renameImagesNode, XPathConstants.STRING)));
            renameImages.setPathDestination(new File((String)xPath.evaluate("pathDestination", renameImagesNode, XPathConstants.STRING)));
            renameImages.setPathSource(new File((String)xPath.evaluate("pathSource", renameImagesNode, XPathConstants.STRING)));
            renameImages.setProgressLogTimestampFormat(new SimpleDateFormat((String)xPath.evaluate("progressLogTimestampFormat", renameImagesNode, XPathConstants.STRING)));
            renameImages.setTemplateFileName((String)xPath.evaluate("templateFileName", renameImagesNode, XPathConstants.STRING));
            renameImages.setTemplateSubDirectoryName((String)xPath.evaluate("templateSubDirectoryName", renameImagesNode, XPathConstants.STRING));
            renameImages.setUseLastModifiedDate(Boolean.valueOf((String)xPath.evaluate("useLastModifiedDate", renameImagesNode, XPathConstants.STRING)));
            renameImages.setUseLastModifiedTime(Boolean.valueOf((String)xPath.evaluate("useLastModifiedTime", renameImagesNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return renameImages;
    }

    public static Repository getRepositoryConfig(Node repositoryNode, XPath xPath) {
        Repository repository = new Repository();

        try {
            repository.setExceptions(getRepositoryExceptions((Node)xPath.evaluate("exceptions", repositoryNode, XPathConstants.NODE), xPath));
            repository.setPaths(getRepositoryPaths((Node)xPath.evaluate("paths", repositoryNode, XPathConstants.NODE), xPath));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return repository;
    }

    private static RepositoryPaths getRepositoryPaths(Node repositoryNode, XPath xPath) {
        RepositoryPaths repositoryPaths = new RepositoryPaths();

        List<File> paths = new ArrayList<File>();

        try {
            NodeList pathNodeList = (NodeList)xPath.evaluate("path", repositoryNode, XPathConstants.NODESET);

            for (int index = 0; index < pathNodeList.getLength(); index++) {
                paths.add(new File(pathNodeList.item(index).getTextContent()));
            }
            repositoryPaths.setPaths(paths);
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return repositoryPaths;
    }

    private static RepositoryExceptions getRepositoryExceptions(Node exceptionsNode, XPath xPath) {
        RepositoryExceptions repositoryExceptions = new RepositoryExceptions();

        List<File> allwaysAdd = new ArrayList<File>();
        List<File> neverAdd = new ArrayList<File>();

        try {
            NodeList allwaysAddNodeList = (NodeList)xPath.evaluate("allwaysAdd", exceptionsNode, XPathConstants.NODESET);

            for (int index = 0; index < allwaysAddNodeList.getLength(); index++) {
                allwaysAdd.add(new File(allwaysAddNodeList.item(index).getTextContent()));
            }
            repositoryExceptions.setAllwaysAdd(allwaysAdd);

        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            NodeList neverAddNodeList = (NodeList)xPath.evaluate("neverAdd", exceptionsNode, XPathConstants.NODESET);

            for (int index = 0; index < neverAddNodeList.getLength(); index++) {
                neverAdd.add(new File(neverAddNodeList.item(index).getTextContent()));
            }
            repositoryExceptions.setNeverAdd(neverAdd);

        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return repositoryExceptions;
    }

    public static TagImages getTagImagesConfig(Node tagImagesNode, XPath xPath) {
        TagImages tagImages = new TagImages();

        try {
            tagImages.setCategories(getCategories((Node)xPath.evaluate("categories", tagImagesNode, XPathConstants.NODE), xPath));
            tagImages.setImagesPaths(getImagesPaths((Node)xPath.evaluate("paths", tagImagesNode, XPathConstants.NODE), xPath));
            tagImages.setPreview(getPreview((Node)xPath.evaluate("preview", tagImagesNode, XPathConstants.NODE), xPath));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tagImages;
    }

    private static TagImagesPreview getPreview(Node previewNode, XPath xPath) {
        TagImagesPreview tagImagesPreview = new TagImagesPreview();

        try {
            tagImagesPreview.setUseEmbeddedThumbnail(Boolean.valueOf((String)xPath.evaluate("useEmbeddedThumbnail", previewNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tagImagesPreview;
    }

    private static TagImagesPaths getImagesPaths(Node pathsNode, XPath xPath) {
        TagImagesPaths tagImagesPaths = new TagImagesPaths();

        try {
            tagImagesPaths.setAddToRepositoryPolicy(StringUtil.getIntValue((String)xPath.evaluate("addToRepositoryPolicy", pathsNode, XPathConstants.STRING), 1));
            tagImagesPaths.setAutomaticallyRemoveNonExistingImagePath(Boolean.valueOf((String)xPath.evaluate("automaticallyRemoveNonExistingImagePath", pathsNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tagImagesPaths;
    }

    private static TagImagesCategories getCategories(Node categoriesNode, XPath xPath) {
        TagImagesCategories tagImagesCategories = new TagImagesCategories();

        try {
            tagImagesCategories.setOrRadioButtonIsSelected(Boolean.valueOf((String)xPath.evaluate("orRadioButtonIsSelected", categoriesNode, XPathConstants.STRING)));
            tagImagesCategories.setWarnWhenRemove(Boolean.valueOf((String)xPath.evaluate("warnWhenRemove", categoriesNode, XPathConstants.STRING)));
            tagImagesCategories.setWarnWhenRemoveWithSubCategories(Boolean.valueOf((String)xPath.evaluate("warnWhenRemoveWithSubCategories", categoriesNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tagImagesCategories;
    }

    public static ThumbNail getThumbNailConfig(Node thumbNailNode, XPath xPath) {
        ThumbNail thumbNail = new ThumbNail();

        try {
            thumbNail.setCache(getCache((Node)xPath.evaluate("cache", thumbNailNode, XPathConstants.NODE), xPath));
            thumbNail.setCreation(getCreation((Node)xPath.evaluate("creation", thumbNailNode, XPathConstants.NODE), xPath));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return thumbNail;
    }

    private static ThumbNailCreation getCreation(Node creationNode, XPath xPath) {
        ThumbNailCreation thumbNailCreation = new ThumbNailCreation();

        try {
            thumbNailCreation.setAlgorithm(JPEGScaleAlgorithm.valueOf((String)xPath.evaluate("algorithm", creationNode, XPathConstants.STRING)));
            thumbNailCreation.setHeight(StringUtil.getIntValue((String)xPath.evaluate("height", creationNode, XPathConstants.STRING), 120));
            thumbNailCreation.setIfMissingOrCorrupt(Boolean.valueOf((String)xPath.evaluate("ifMissingOrCorrupt", creationNode, XPathConstants.STRING)));
            thumbNailCreation.setWidth(StringUtil.getIntValue((String)xPath.evaluate("witdth", creationNode, XPathConstants.STRING), 160));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return thumbNailCreation;
    }

    private static ThumbNailCache getCache(Node cacheNode, XPath xPath) {
        ThumbNailCache thumbNailCache = new ThumbNailCache();

        try {
            thumbNailCache.setEnabled(Boolean.valueOf((String)xPath.evaluate("enabled", cacheNode, XPathConstants.STRING)));
            thumbNailCache.setMaxSize(StringUtil.getIntValue((String)xPath.evaluate("maxSize", cacheNode, XPathConstants.STRING), 1000));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return thumbNailCache;
    }

    public static ToolTips getToolTipsConfig(Node toolTipsNode, XPath xPath) {
        ToolTips toolTips = new ToolTips();

        try {
            toolTips.setState((String)xPath.evaluate("maxSize", toolTipsNode, XPathConstants.STRING));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return toolTips;
    }

    public static UpdatesChecker getUpdatesCheckerConfig(Node updatesCheckerNode, XPath xPath) {
        UpdatesChecker updatesChecker = new UpdatesChecker();

        try {
            updatesChecker.setAttachVersionInformation(Boolean.valueOf((String)xPath.evaluate("attachVersionInformation", updatesCheckerNode, XPathConstants.STRING)));
            updatesChecker.setEnabled(Boolean.valueOf((String)xPath.evaluate("enabled", updatesCheckerNode, XPathConstants.STRING)));
            updatesChecker.setTimeOut(StringUtil.getIntValue((String)xPath.evaluate("timeout", updatesCheckerNode, XPathConstants.STRING), 60));
            updatesChecker.setUrlVersion(new URL((String)xPath.evaluate("urlVersion", updatesCheckerNode, XPathConstants.STRING)));
            updatesChecker.setUrlVersionInformation(new URL((String)xPath.evaluate("urlVersionInformation", updatesCheckerNode, XPathConstants.STRING)));
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return updatesChecker;
    }

    private static void displayErrorMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static Map<String, ImportedCategories> getImportedCategoriesConfig(Node importedCategoriesNode, XPath xPath) {

        Map<String, ImportedCategories> importedCategoriesConfig = null;

        try {
            NodeList instancesNodeList = (NodeList)xPath.evaluate("instance", importedCategoriesNode, XPathConstants.NODESET);

            importedCategoriesConfig = new HashMap<String, ImportedCategories>();

            for (int index = 0; index < instancesNodeList.getLength(); index++) {
                Node instanceNode = instancesNodeList.item(index);
                String javapegClientId = (String)xPath.evaluate("@javapegclientid", instanceNode, XPathConstants.STRING);
                String displayName = (String)xPath.evaluate("@displayname", instanceNode, XPathConstants.STRING);

                DefaultMutableTreeNode root = new DefaultMutableTreeNode(new CategoryUserObject("root", "-1"));

                populateTreeModelFromNode(instanceNode, root, xPath);

                ImportedCategories importedCategories = new ImportedCategories();
                importedCategories.setDisplayName(displayName);
                importedCategories.setRoot(root);

                importedCategoriesConfig.put(javapegClientId, importedCategories);
            }
        } catch (XPathExpressionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return importedCategoriesConfig;
    }
}
