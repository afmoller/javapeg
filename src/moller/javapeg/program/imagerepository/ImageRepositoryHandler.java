package moller.javapeg.program.imagerepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import moller.javapeg.program.C;
import moller.javapeg.program.logger.Logger;
import moller.util.io.FileUtil;

public class ImageRepositoryHandler {

	/**
	 * The static singleton instance of this class.
	 */
	private static ImageRepositoryHandler instance;

	private final File repositoryFile;

	private TreeSet<String> imageRepositoryPaths;

	/**
	 * Private constructor.
	 */
	private ImageRepositoryHandler() {
		repositoryFile = new File(C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION + C.FS + "repository" + C.FS + "images.rep");
	}

	/**
	 * Accessor method for this Singleton class.
	 *
	 * @return the singleton instance of this class.
	 */
	public static ImageRepositoryHandler getInstance() {
		if (instance != null)
			return instance;
		synchronized (ImageRepositoryHandler.class) {
			if (instance == null) {
				instance = new ImageRepositoryHandler();
			}
			return instance;
		}
	}

	public Object [] load() {
		Logger logger = Logger.getInstance();
		if(repositoryFile.exists()) {
			try {
				imageRepositoryPaths = new TreeSet<String>(FileUtil.readFromFile(repositoryFile));
				return imageRepositoryPaths.toArray();
			} catch (IOException iox) {
				logger.logERROR("Can not read from file: " + repositoryFile.getAbsolutePath());
				logger.logERROR(iox);
				return null;
			}
		} else{
			return null;
		}
	}

	public void store(Iterator<Object> imageRepositoryItems) {
		Logger logger = Logger.getInstance();
		try {
			List<String> directoryPaths = new ArrayList<String>();

			while(imageRepositoryItems.hasNext()) {
				directoryPaths.add(((ImageRepositoryItem)imageRepositoryItems.next()).getPath());
			}

			if (contentChanged(directoryPaths)) {
				FileUtil.writeToFile(repositoryFile, directoryPaths, false);
				logger.logDEBUG("Image Repository file: " + repositoryFile.getAbsolutePath() + " updated");

				for (String addedPath : addedImageRepositoryPaths(directoryPaths)) {
					logger.logDEBUG("Image Repository Path Added: " + addedPath);
				}

				for (String removedPath : removedImageRepositoryPaths(directoryPaths)) {
					logger.logDEBUG("Image Repository Path Removed: " + removedPath);
				}
			} else {
				logger.logDEBUG("Image Repository file: " + repositoryFile.getAbsolutePath() + " not updated since there was no changes found");
			}
		} catch (IOException iox) {
			logger.logERROR("Can not write to file: " + repositoryFile.getAbsolutePath());
			logger.logERROR(iox);
		}
	}

	/**
	 * This method decides whether the image repository has changed while
	 * JavaPEG has been running. If a change is found then the image repository
	 * file needs to be updated by flushing the current content to persistent
	 * storage.
	 *
	 * @param directoryPaths contains the current image repository paths.
	 *
	 * @return a boolean value indicating whether the image repository file
	 *         need to be updated or not.
	 */
	private boolean contentChanged(List<String> directoryPaths) {

		if (directoryPaths.size() != imageRepositoryPaths.size()) {
			return true;
		}

		for (String imageRepositoryPath : imageRepositoryPaths) {
			if (!directoryPaths.contains(imageRepositoryPath)) {
				return true;
			}
		}
		return false;
	}

	private List<String> addedImageRepositoryPaths(List<String> directoryPaths) {
		List<String> addedPaths = new ArrayList<String>();

		for (String directoryPath : directoryPaths) {
			if (!imageRepositoryPaths.contains(directoryPath)) {
				addedPaths.add(directoryPath);
			}
		}
		return addedPaths;
	}

	private List<String> removedImageRepositoryPaths(List<String> directoryPaths) {
		List<String> removedPaths = new ArrayList<String>();

		for (String imageRepositoryPath : imageRepositoryPaths) {
			if (!directoryPaths.contains(imageRepositoryPath)) {
				removedPaths.add(imageRepositoryPath);
			}
		}
		return removedPaths;
	}
}
