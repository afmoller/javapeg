package moller.javapeg.program;
/**
 * This class was created : 2009-03-14 by Fredrik Möller
 * Latest changed         : 2009-03-23 by Fredrik Möller
 */

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
	 * This variable indicates if the "Ok" button in the file chooser GUI has 
	 * been clicked.
	 */
	private boolean fileChooserOkButtonClicked;
	
	/**
	 * This variable indicates if the "Cancel" button in the file chooser GUI has 
	 * been clicked.
	 */
	private boolean fileChooserCancelButtonClicked;
	
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
	 * Private constructor.
	 */
	private ApplicationContext() {
		sourcePath = "";
		destinationPath = "";
		fileChooserOkButtonClicked = false;
		fileChooserCancelButtonClicked = false;
		createThumbNailsCheckBoxSelected = false;
		templateFileName = "";
		templateSubFolderName = "";
		metaDataObjects = new ArrayList<MetaData>();
		jarFileEmbeddedLanguageFiles = new HashSet<String>(); 
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

	public boolean isFileChooserOkButtonClicked() {
		return fileChooserOkButtonClicked;
	}

	public boolean isFileChooserCancelButtonClicked() {
		return fileChooserCancelButtonClicked;
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

	public void setFileChooserOkButtonClicked(boolean fileChooserOkButtonClicked) {
		this.fileChooserOkButtonClicked = fileChooserOkButtonClicked;
	}

	public void setFileChooserCancelButtonClicked(boolean fileChooserCancelButtonClicked) {
		this.fileChooserCancelButtonClicked = fileChooserCancelButtonClicked;
	}
	
	public void setMetaDataObjects(List<MetaData> metaDataObjects) {
		if (!this.metaDataObjects.isEmpty()) {
			this.metaDataObjects.clear();
		}
		this.metaDataObjects = metaDataObjects;
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
}