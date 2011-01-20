package moller.javapeg.program.rename;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenameProcessContext {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static RenameProcessContext instance;
	
	/**
	 * This Map holds the information about all JPEG files found in the source
	 * directory. The information is the absolute path of the files which is
	 * used as the key in the Map. The values mapped to each key is the converted
	 * path as the JPEG file will get in the destination directory. That path is 
	 * used to create a java.io.File object which is stored in the value field. 
	 */
	private Map<File, File> jPEGOriginalFilePathConvertedFileObjectMapping;
	
	
	private Map<File, File> jPEGOriginalFilePathThumbNailFileObjectMapping;
	
	/**
	 * This Map holds the information about all non JPEG files and directories
	 * found in the source directory. The information in the key field is the 
	 * absolute path of the file. The values mapped to each key is the destination
	 * path that each file in the key filed will get when copied to the destination
	 * directory. This path is used to create a java.io.File object which is stored
	 * in the value field. 
	 */
	private Map<File, FileAndType> nonJPEGOriginalFilePathDestinationFileObjectMapping;
	
	
	private String subDirectoryName;
	
	/**
	 * The name of the directory that will contain thumbnails
	 * of the JPEG files that exists in the source directory
	 * if the check box in the GUI for the creation of that is
	 *  selected.
	 */
	private static final String THUMBNAIL_DIRECTORY_NAME = "thm"; 
	
	/**
	 * This list contains the content of the image meta data XML file, if there
	 * exist such a file in the directory with images that shall be renamed.
	 * The list contains the content that it will have when it is copied to the
	 * destination (all file attributes in the image tag has values which 
	 * matches the new image names).
	 */
	private List<String> javaPegImageMetaFileContent;
		
	/**
	 * Private constructor.
	 */
	private RenameProcessContext() {
		jPEGOriginalFilePathConvertedFileObjectMapping      = new HashMap<File, File>(128);
		jPEGOriginalFilePathThumbNailFileObjectMapping      = new HashMap<File, File>(128);
		nonJPEGOriginalFilePathDestinationFileObjectMapping = new HashMap<File, FileAndType>(128);
		javaPegImageMetaFileContent = new ArrayList<String>();
	}

	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static RenameProcessContext getInstance() {
		if (instance != null)
			return instance;
		synchronized (RenameProcessContext.class) {
			if (instance == null) {
				instance = new RenameProcessContext();
			}
			return instance;
		}
	}
		
	public Map<File, File> getAllJPEGFileNameMappings() {
		return jPEGOriginalFilePathConvertedFileObjectMapping;
	}
	
	public void clearAllJPEGFileNameMappings () {
		jPEGOriginalFilePathConvertedFileObjectMapping.clear();
	}
	
	public void addJPEGFileNameMapping (File originalName, File convertedName) {
		jPEGOriginalFilePathConvertedFileObjectMapping.put(originalName, convertedName);
	}
	
	public Map<File, File> getAllThumbNailFileNameMappings() {
		return jPEGOriginalFilePathThumbNailFileObjectMapping;
	}
	
	public void clearAllThumbNailFileNameMappings () {
		jPEGOriginalFilePathThumbNailFileObjectMapping.clear();
	}
	
	public void addThumbNailFileNameMapping (File originalName, File convertedThumbNailName) {
		jPEGOriginalFilePathThumbNailFileObjectMapping.put(originalName, convertedThumbNailName);
	}
		
	public Map<File, FileAndType> getAllNonJPEGFileNameMappings() {
		return nonJPEGOriginalFilePathDestinationFileObjectMapping;
	}
	
	public void clearAllNonJPEGFileNameMappings () {
		nonJPEGOriginalFilePathDestinationFileObjectMapping.clear();
	}
	
	/**
	 * @param sPath is the path to the source file.
	 * @param dPath is the path to the destination file and the type of the file:
	 *        "directory" or "file"
	 */
	public void addNonJPEGFileNameMapping (File sPath, FileAndType dPath) {
		nonJPEGOriginalFilePathDestinationFileObjectMapping.put(sPath, dPath);
	}
	
	public String getTHUMBNAIL_DIRECTORY_NAME() {
		return THUMBNAIL_DIRECTORY_NAME;
	}
	
	public String getSubDirectoryName() {
		return subDirectoryName;
	}

	public void setSubDirectoryName(String subDirectoryName) {
		this.subDirectoryName = subDirectoryName;
	}
	
	public List<String> getJavaPegImageMetaFileContent() {
		return javaPegImageMetaFileContent;
	}

	public void setJavaPegImageMetaFileContent(List<String> javaPegImageMetaFileContent) {
		this.javaPegImageMetaFileContent = javaPegImageMetaFileContent;
	}
		
	public void clearAllMappingMaps() {
		jPEGOriginalFilePathConvertedFileObjectMapping.clear();
		jPEGOriginalFilePathThumbNailFileObjectMapping.clear();
		nonJPEGOriginalFilePathDestinationFileObjectMapping.clear();
	}
}
