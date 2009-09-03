package moller.javapeg.program.rename.validator;
/**
 * This class was created : 2009-02-10 by Fredrik Möller
 * Latest changed         : 2009-02-19 by Fredrik Möller
 *                        : 2009-02-25 by Fredrik Möller
 *                        : 2009-02-26 by Fredrik Möller
 *                        : 2009-02-27 by Fredrik Möller
 *                        : 2009-03-03 by Fredrik Möller
 *                        : 2009-03-04 by Fredrik Möller
 *                        : 2009-08-21 by Fredrik Möller
 */

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import moller.javapeg.program.ApplicationContext;
import moller.javapeg.program.TemplateUtil;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.rename.RenameProcessContext;
import moller.javapeg.program.rename.ValidatorStatus;

/**
 * This class validates the lengths of all converted paths that 
 * has been generated according the templates for files and sub
 * directory that exists in the GUI part of this application. This
 * class validates the paths for all JPEG files found in the source 
 * directory.
 * 
 * @author Fredrik
 */
public class JPEGTotalPathLength {

	/**
	 * The static singleton instance of this class.
	 */
	private static JPEGTotalPathLength instance;
	
	/**
	 * The system dependent file separator char
	 */
	private final static String FS = File.separator;
	
	/**
	 * The length of the system dependent file separator char
	 */
	private final static int FSL = File.separator.length();
		
	/**
	 * The suffix to add to the JPEG files.
	 */
	private final static String JPEG_SUFFIX = "jpg";
	
	private final static String THUMBNAIL_SUFFIX = "thm";
		
	/**
	 * Private constructor
	 */
	private JPEGTotalPathLength() {
	}

	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static JPEGTotalPathLength getInstance() {
		if (instance != null)
			return instance;
		synchronized (JPEGTotalPathLength.class) {
			if (instance == null) {
				instance = new JPEGTotalPathLength();
			}
			return instance;
		}
	}

	/**
	 * Validates the path lengths for all JPEG files and in the source
	 * directory. The maximum allowed path length is 260 characters.
	 * 
	 * The paths are constructed as follows:
	 * 
	 * "Selected destination directory" + File.separator + "converted sub
	 * directory name" + File.separator + "converted file name"
	 * 
	 * The converted values are constructed using the templates provided by the
	 * user in the GUI part of this application.
	 * 
	 * @return a boolean value indicating whether all paths are valid regarding
	 *         path lengths. True means all paths are valid.
	 */
	public ValidatorStatus test() {
		
		Logger logger = Logger.getInstance();

		ApplicationContext ac = ApplicationContext.getInstance();

		String templateFileName      = ac.getTemplateFileName();
		String templateSubFolderName = ac.getTemplateSubFolderName();
		String destinationPath       = ac.getDestinationPath();

		List<MetaData> metaDataObjects = ac.getMetaDataObjects();

		String convertedFilenNameTemplate        = "";
		String convertedSubDirectoryNameTemplate = "";

		Set<String> convertedJPEGFileNames = new HashSet<String>();
		
		RenameProcessContext rpc = RenameProcessContext.getInstance();
		rpc.clearAllJPEGFileNameMappings();
		rpc.clearAllThumbNailFileNameMappings();

		ValidatorStatus vs = new ValidatorStatus(true, "");
		
		Language lang = Language.getInstance();
		
		if (metaDataObjects.size() > 0) {
			convertedSubDirectoryNameTemplate = TemplateUtil.convertTemplateToString(templateSubFolderName, metaDataObjects.get(0));
			rpc.setSubDirectoryName(convertedSubDirectoryNameTemplate);
		
			StringBuilder sb = new StringBuilder(512);
			
			for (MetaData metaDataObject : metaDataObjects) {
	
				convertedFilenNameTemplate = TemplateUtil.convertTemplateToString(templateFileName, metaDataObject);
				
				if (convertedJPEGFileNames.contains(convertedFilenNameTemplate)) {
					int unique = 1;
	
					while (convertedJPEGFileNames.contains(convertedFilenNameTemplate + "_"	+ Integer.toString(unique))) {
						unique++;
					}
					convertedFilenNameTemplate += "_" + Integer.toString(unique);
				}
				convertedJPEGFileNames.add(convertedFilenNameTemplate);
	
				if (ac.isCreateThumbNailsCheckBoxSelected()) {
					if ((destinationPath.length() + FSL	+ convertedSubDirectoryNameTemplate.length() + FSL	+ rpc.getTHUMBNAIL_DIRECTORY_NAME().length() + FSL + convertedFilenNameTemplate.length() + ".".length() + THUMBNAIL_SUFFIX.length()) > 260) {
						sb.append(metaDataObject.getFileName() + " (" + destinationPath + FS + convertedSubDirectoryNameTemplate + FS + rpc.getTHUMBNAIL_DIRECTORY_NAME() + FS + convertedFilenNameTemplate + "." + THUMBNAIL_SUFFIX + ")");
						sb.append(System.getProperty("line.separator"));
					}
					rpc.addThumbNailFileNameMapping(metaDataObject.getFileObject(), new File(destinationPath + FS + convertedSubDirectoryNameTemplate + FS + rpc.getTHUMBNAIL_DIRECTORY_NAME() + FS + convertedFilenNameTemplate + "." + THUMBNAIL_SUFFIX));
				} else {
					if ((destinationPath.length() + FSL	+ convertedSubDirectoryNameTemplate.length() + FSL	+ convertedFilenNameTemplate.length() + ".".length() + JPEG_SUFFIX.length()) > 260) {
						sb.append(metaDataObject.getFileName() + " (" + destinationPath + FS + convertedSubDirectoryNameTemplate + FS + convertedFilenNameTemplate + "." + JPEG_SUFFIX + ")");
						sb.append(System.getProperty("line.separator"));
					}
				}
				rpc.addJPEGFileNameMapping(metaDataObject.getFileObject(), new File(destinationPath + FS + convertedSubDirectoryNameTemplate + FS + convertedFilenNameTemplate + "." + JPEG_SUFFIX));
			}
			if (sb.length() > 0){
				vs.setValid(false);
				vs.setStatusMessage(lang.get("validator.jpegtotalpathlength.toLongFileName") );
				logger.logINFO(lang.get("validator.jpegtotalpathlength.toLongFileName") + System.getProperty("line.separator")+ sb.toString());
				rpc.clearAllJPEGFileNameMappings();
			}
		} else {
			vs.setValid(false);
			vs.setStatusMessage(lang.get("validator.jpegtotalpathlength.noJPEGFIlesInSourceDirectory"));
			logger.logERROR(lang.get("validator.jpegtotalpathlength.noJPEGFIlesInSourceDirectory"));
			return vs;	
		}
		return vs;
	}
}