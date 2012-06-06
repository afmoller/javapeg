package moller.javapeg.program.rename.validator;

import java.io.File;
import java.util.Map;

import moller.javapeg.program.C;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.enumerations.Type;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.rename.FileAndType;
import moller.javapeg.program.rename.RenameProcessContext;
import moller.javapeg.program.rename.ValidatorStatus;
import moller.util.io.DirectoryUtil;
import moller.util.io.FileUtil;

public class FileCreationAtDestinationDirectory {

	/**
	 * The static singleton instance of this class.
	 */
	private static FileCreationAtDestinationDirectory instance;

	/**
	 * The sub directory to which the renamed JPEG files and all other non
	 * JPEG files shall be copied.
	 */
	private File subDirectory;

	private String destinationPath;
	private String subDirectoryName;
	private String thumbnailDirectoryName;

	private Logger logger;

	/**
	 * Private constructor
	 */
	private FileCreationAtDestinationDirectory() {
	}

	/**
	 * Accessor method for this Singleton class.
	 *
	 * @return the singleton instance of this class.
	 */
	public static FileCreationAtDestinationDirectory getInstance() {
		if (instance != null)
			return instance;
		synchronized (FileCreationAtDestinationDirectory.class) {
			if (instance == null) {
				instance = new FileCreationAtDestinationDirectory();
			}
			return instance;
		}
	}

	public ValidatorStatus test() {

		init();

		Language lang = Language.getInstance();

		ValidatorStatus vs = new ValidatorStatus(true, "");

		if(!createSubDirectory()) {
			vs.setValid(false);
			vs.setStatusMessage(lang.get("validator.filecreationatdestinationdirectory.couldNotCreateSubDirectory"));
			logger.logERROR(lang.get("validator.filecreationatdestinationdirectory.couldNotCreateSubDirectory"));
			return vs;
		}
		if(!createJPEGFiles()) {
			vs.setValid(false);
			vs.setStatusMessage(lang.get("validator.filecreationatdestinationdirectory.couldNotCreateAllJPEGFiles"));
			logger.logERROR(lang.get("validator.filecreationatdestinationdirectory.couldNotCreateAllJPEGFiles"));
			return vs;
		}
		if (ApplicationContext.getInstance().isCreateThumbNailsCheckBoxSelected()) {
			if (!createJPEGThumbnailsDirectory()) {
				vs.setValid(false);
				vs.setStatusMessage(lang.get("validator.filecreationatdestinationdirectory.couldNotCreateThumbNailDirectory"));
				logger.logERROR(lang.get("validator.filecreationatdestinationdirectory.couldNotCreateThumbNailDirectory"));
				return vs;
			}
			if (!createJPEGThumbnails()) {
				vs.setValid(false);
				vs.setStatusMessage(lang.get("validator.filecreationatdestinationdirectory.couldNotCreateAllThumbNails"));
				logger.logERROR(lang.get("validator.filecreationatdestinationdirectory.couldNotCreateAllThumbNails"));
				return vs;
			}
		}
		if(!createNonJPEGFiles()) {
			vs.setValid(false);
			vs.setStatusMessage(lang.get("validator.filecreationatdestinationdirectory.couldNotCreateAllNonJPEGFiles"));
			logger.logERROR(lang.get("validator.filecreationatdestinationdirectory.couldNotCreateAllNonJPEGFiles"));
			return vs;
		}
		if(!deleteSubDirectory()) {
			vs.setValid(false);
			vs.setStatusMessage(lang.get("validator.filecreationatdestinationdirectory.couldNotDeleateSubDirectory"));
			logger.logERROR(lang.get("validator.filecreationatdestinationdirectory.couldNotDeleateSubDirectory"));
			return vs;
		}
		return vs;
	}

	private void init () {
		ApplicationContext ac = ApplicationContext.getInstance();
		destinationPath  = ac.getDestinationPath();

		RenameProcessContext     rpc = RenameProcessContext.getInstance();
		subDirectoryName       = rpc.getSubDirectoryName();
		thumbnailDirectoryName = rpc.getTHUMBNAIL_DIRECTORY_NAME();

		logger = Logger.getInstance();
	}

	private boolean createSubDirectory() {
		subDirectory = new File(destinationPath + C.FS + subDirectoryName);
		return subDirectory.mkdir();
	}

	/**
	 * This method creates empty files for all JPEG files in the selected
	 * source folder.
	 *
	 * @return a boolean value indicating whether the creation of all JPEG
	 *         files was successful or not. true means success and false means
	 *         failure.
	 */
	private boolean createJPEGFiles() {
		Map<File, File> allJPEGFiles = RenameProcessContext.getInstance().getAllJPEGFileNameMappings();

		if(!FileUtil.createFiles(allJPEGFiles.values())) {
			return false;
		}
		return true;
	}

	private boolean createJPEGThumbnailsDirectory() {
		File thumbnailDirectory = new File(destinationPath + C.FS + subDirectoryName + C.FS + thumbnailDirectoryName);
		return thumbnailDirectory.mkdir();
	}

	/**
	 * This method creates empty thumb nail files for all JPEG files in the
	 * selected source folder if the JPEG file has an embedded thumb nail.
	 *
	 * @return a boolean value indicating whether the creation of all thumb nail
	 *         files was successful or not. true means success and false means
	 *         failure.
	 */
	private boolean createJPEGThumbnails () {
		Map<File, File> allThumbNailFiles = RenameProcessContext.getInstance().getAllThumbNailFileNameMappings();

		if(!FileUtil.createFiles(allThumbNailFiles.values())) {
			return false;
		}
		return true;
	}

	/**
	 * This method creates empty files for the non JPEG files that might exist
	 * in the selected source folder.
	 *
	 * @return a boolean value indicating whether the creation of all non JPEG
	 *         files was successful or not. true means success and false means
	 *         failure.
	 */
	private boolean createNonJPEGFiles() {
		Map<File, FileAndType> allNonJPEGFiles = RenameProcessContext.getInstance().getAllNonJPEGFileNameMappings();

		for (FileAndType fileAndType : allNonJPEGFiles.values()) {

			Type type = fileAndType.getType();
			File file = fileAndType.getFile();

			if (!file.exists()) {
				if (type.equals(Type.FILE)) {
					if (!file.getParentFile().exists()) {
						if(!file.getParentFile().mkdirs()) {
							logger.logERROR("Could not create directory: " + file.getParentFile().getAbsolutePath());
							return false;
						}
					}
					if(!FileUtil.createFile(file)) {
						logger.logERROR("Could not create file: " + file.getAbsolutePath());
						return false;
					}

				} else {
					if (!file.mkdirs()) {
						logger.logERROR("Could not create directory: " + file.getAbsolutePath());
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * This method deletes the created sub directory
	 *
	 * @return whether the deletion was successful or not. true means success
	 *         and false means failure.
	 */
	private boolean deleteSubDirectory() {
		return DirectoryUtil.deleteDirectory(subDirectory);
	}
}