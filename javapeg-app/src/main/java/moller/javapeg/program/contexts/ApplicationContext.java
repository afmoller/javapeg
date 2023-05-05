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
package moller.javapeg.program.contexts;

import moller.javapeg.program.enumerations.FileLoadingAction;
import moller.javapeg.program.enumerations.MainTabbedPaneComponent;
import moller.javapeg.program.metadata.MetaData;

import javax.swing.tree.TreePath;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This is a class that holds non persistent runtime specific
 * information which different components of the application
 * will need to be able to do it´s tasks.
 *
 * @author Fredrik
 *
 */
public class ApplicationContext {

    /**
     * The static singleton instance of this class.
     */
    private static ApplicationContext instance;

    /**
     * This variable contains the path to the selected folder
     *  containing JPEG images.
     */
    private File sourcePath;

    private int nrOfFilesInSourcePath;

    /**
     * This variable contains the path to the selected destination
     * folder, the folder to where the renamed copies of the source
     *  folder will end up.
     */
    private String destinationPath;

    /**
     * This variable will contain the value that is entered in the
     * template JTextField for file in the GUI part of this application.
     */
    private String templateFileName;

    /**
     * This variable will contain the value that is entered in the
     * template JTextField for sub folder in the GUI part of this application.
     */
    private String templateSubFolderName;

    /**
     * This variable indicates whether the check box for creating thumb nails has
     * been clicked in the GUI.
     */
    private boolean createThumbNailsCheckBoxSelected;

    /**
     * This List contains MetaData objects of all JPEG files in the folder
     * that has been selected in the file chooser GUI.
     */
    private final List <MetaData>metaDataObjects;

    /**
     * This List contains the name of each language file that is embedded into
     * the application JAR file.
     */
    private Set<String> jarFileEmbeddedLanguageFiles;

    /**
     * This list contains File objects and is populated when a directory is
     * selected in the tree structure. When a jpeg image is found that is added
     * to this buffer list. At the same time there is another task populating
     * the grid with image thumbnails and that task get it´s images from this
     * buffer list.
     */
    private final List<File> jpegFileLoadBuffer;

    /**
     * This flag indicates whether the imageviewer is displayed or not
     */
    private boolean imageViewerDisplayed;

    /**
     * Indicates which image that has been selected in the grid of thumbnails
     * that has been populated when a directory has been selected in the tree
     * object.
     */
    private File selectedThumbNail;

    /**
     * This contains the currently selected TreePath in the category tree
     * structure. It can contain a null value, if no path is currently
     * selected.
     */
    private TreePath selectedCategoryPath;

    /**
     * This integer holds the value of the highest used category ID.
     */
    private int highestUsedCategoryID;

    /**
     * This {@link MainTabbedPaneComponent} variable keeps track of the
     * currently selected tab in the "Main tabbed pane", the one with the
     * Rename, Tag and Search / View.
     */
    private MainTabbedPaneComponent mainTabbedPaneComponent;

    /**
     * This boolean keeps track on whether an image meta data data base file
     * is loaded or not. This flag is set when a directory with images is
     * selected.
     */
    private boolean imageMetaDataDataBaseFileLoaded;

    /**
     * This boolean keeps track of whether any existing image meta data base
     * file in the currently selected directory is writable for the JavaPEG
     * application.
     */
    private boolean imageMetaDataDataBaseFileWritable;

    /**
     * This boolean indicates whether an image meta data data base file is
     * created by the running JavaPEG instance or not.
     */
    private boolean imageMetaDataDataBaseFileCreatedByThisJavaPEGInstance;


    /**
     * This boolean indicates whether there is a change in the application that
     * need a restart of the application to fully make use of the change.
     */
    private boolean restartNeeded;

    /**
     * Private constructor.
     */
    private ApplicationContext() {
        sourcePath = null;
        destinationPath = "";
        createThumbNailsCheckBoxSelected = false;
        templateFileName = "";
        templateSubFolderName = "";
        metaDataObjects = new ArrayList<MetaData>();
        jarFileEmbeddedLanguageFiles = new HashSet<String>();
        jpegFileLoadBuffer = new ArrayList<File>();
        imageViewerDisplayed = false;
        selectedThumbNail = null;
        selectedCategoryPath = null;
        highestUsedCategoryID = -1;
        mainTabbedPaneComponent = null;
        imageMetaDataDataBaseFileLoaded = false;
        imageMetaDataDataBaseFileWritable = false;
        imageMetaDataDataBaseFileCreatedByThisJavaPEGInstance = false;
        restartNeeded = false;
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static ApplicationContext getInstance() {
        if (instance != null)
            return instance;
        synchronized (ApplicationContext.class) {
            if (instance == null) {
                instance = new ApplicationContext();
            }
            return instance;
        }
    }

    /**
     * GET  - methods
     */
    public File getSourcePath() {
        return sourcePath;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public List<MetaData> getMetaDataObjects() {
        return metaDataObjects;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public String getTemplateSubFolderName() {
        return templateSubFolderName;
    }

    public boolean isCreateThumbNailsCheckBoxSelected() {
        return createThumbNailsCheckBoxSelected;
    }

    public boolean isImageViewerDisplayed() {
        return imageViewerDisplayed;
    }

    public Set<String> getJarFileEmbeddedLanguageFiles() {
        return jarFileEmbeddedLanguageFiles;
    }

    public File getSelectedThumbNail() {
        return selectedThumbNail;
    }

    public TreePath getSelectedCategoryPath() {
        return selectedCategoryPath;
    }

    public int getHighestUsedCategoryID() {
        return highestUsedCategoryID;
    }

    public int getNextIDToUse() {
        setHighestUsedCategoryID(highestUsedCategoryID + 1);
        return highestUsedCategoryID;
    }

    public MainTabbedPaneComponent getMainTabbedPaneComponent() {
        return mainTabbedPaneComponent;
    }

    public int getNrOfFilesInSourcePath() {
        return nrOfFilesInSourcePath;
    }

    /**
     * SET  - methods
     */
    public void setSourcePath(File sourcePath) {
        this.sourcePath = sourcePath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public void clearMetaDataObjects () {
        this.metaDataObjects.clear();
    }

    public void addMetaDataObject(MetaData metaData) {
        this.metaDataObjects.add(metaData);
    }

    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }

    public void setTemplateSubFolderName(String templateSubFolderName) {
        this.templateSubFolderName = templateSubFolderName;
    }

    public void setCreateThumbNailsCheckBoxSelected(boolean createThumbNailsCheckBoxSelected) {
        this.createThumbNailsCheckBoxSelected = createThumbNailsCheckBoxSelected;
    }

    public void setJarFileEmbeddedLanguageFiles(Set<String> jarFileEmbeddedLanguageFiles) {
        if (!this.jarFileEmbeddedLanguageFiles.isEmpty()) {
            this.jarFileEmbeddedLanguageFiles.clear();
        }
        this.jarFileEmbeddedLanguageFiles = jarFileEmbeddedLanguageFiles;
    }

    public void setImageViewerDisplayed(boolean imageViewerDisplayed) {
        this.imageViewerDisplayed = imageViewerDisplayed;
    }

    public void setSelectedThumbNail(File selectedThumbNail) {
        this.selectedThumbNail = selectedThumbNail;
    }

    public void setSelectedCategoryPath(TreePath selectedCategoryPath) {
        this.selectedCategoryPath = selectedCategoryPath;
    }

    public void setHighestUsedCategoryID(int highestUsedCategoryID) {
        this.highestUsedCategoryID = highestUsedCategoryID;
    }

    public void setMainTabbedPaneComponent(MainTabbedPaneComponent mainTabbedPaneComponent) {
        this.mainTabbedPaneComponent = mainTabbedPaneComponent;
    }

    public void setNrOfFilesInSourcePath(int nrOfFilesInSourcePath) {
        this.nrOfFilesInSourcePath = nrOfFilesInSourcePath;
    }

    public void clearJpegFileLoadBuffer() {
        jpegFileLoadBuffer.clear();
    }

    public synchronized File handleJpegFileLoadBuffer(File image, FileLoadingAction action) {
        switch (action) {
        case ADD:
            jpegFileLoadBuffer.add(image);
            return image;
        case RETRIEVE:
            if (jpegFileLoadBuffer.size() > 0) {
                return jpegFileLoadBuffer.remove(0);
            } else {
                return image;
            }
        default:
            return image;
        }
    }

    public void setImageMetaDataDataBaseFileLoaded(boolean imageMetaDataDataBaseFileLoaded) {
        this.imageMetaDataDataBaseFileLoaded = imageMetaDataDataBaseFileLoaded;
    }

    public boolean isImageMetaDataDataBaseFileLoaded() {
        return imageMetaDataDataBaseFileLoaded;
    }

    public void setImageMetaDataDataBaseFileWritable(boolean imageMetaDataDataBaseFileWritable) {
        this.imageMetaDataDataBaseFileWritable = imageMetaDataDataBaseFileWritable;
    }

    public boolean isImageMetaDataDataBaseFileWritable() {
        return imageMetaDataDataBaseFileWritable;
    }

    public boolean isImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance() {
        return imageMetaDataDataBaseFileCreatedByThisJavaPEGInstance;
    }

    public void setImageMetaDataDataBaseFileCreatedByThisJavaPEGInstance(
            boolean imageMetaDataDataBaseFileCreatedByThisJavaPEGInstance) {
        this.imageMetaDataDataBaseFileCreatedByThisJavaPEGInstance = imageMetaDataDataBaseFileCreatedByThisJavaPEGInstance;
    }

    public void setRestartNeeded() {
        restartNeeded = true;
    }

    public boolean isRestartNeeded() {
        return restartNeeded;
    }
}
