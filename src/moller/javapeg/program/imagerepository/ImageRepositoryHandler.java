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
				return new TreeSet<String>(FileUtil.readFromFile(repositoryFile)).toArray();
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

			FileUtil.writeToFile(repositoryFile, directoryPaths, false);
		} catch (IOException iox) {
			logger.logERROR("Can not write to file: " + repositoryFile.getAbsolutePath());
			logger.logERROR(iox);
		}
	}
}
