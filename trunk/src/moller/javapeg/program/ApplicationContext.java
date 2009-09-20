package moller.javapeg.program;
/**
 * This class was created : 2009-03-14 by Fredrik Möller
 * Latest changed         : 2009-03-23 by Fredrik Möller
 *                        : 2009-06-02 by Fredrik Möller
 *                        : 2009-09-14 by Fredrik Möller
 *                        : 2009-09-19 by Fredrik Möller
 */

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import moller.javapeg.program.metadata.MetaData;

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
	private String sourcePath;
	
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
	private List <MetaData>metaDataObjects;
	
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
	private List<File> jpegFileLoadBuffer;
	
	/**
	 * This flag indicates whether the iamgeviewer is displayed or not 
	 */
	private boolean imageViewerDisplayed;
	
	/**
	 * Private constructor.
	 */
	private ApplicationContext() {
		sourcePath = "";
		destinationPath = "";
		createThumbNailsCheckBoxSelected = false;
		templateFileName = "";
		templateSubFolderName = "";
		metaDataObjects = new ArrayList<MetaData>();
		jarFileEmbeddedLanguageFiles = new HashSet<String>();
		jpegFileLoadBuffer = new ArrayList<File>();
		imageViewerDisplayed = false;
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
	public String getSourcePath() {
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
	
	/**
	 * SET  - methods
	 */
	public void setSourcePath(String sourcePath) {
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
		
	public synchronized File handleJpegFileLoadBuffer(File image, Action action) {
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
}