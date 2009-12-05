package moller.javapeg.program.categories;
/**
 * This class was created : 2009-12-03 by Fredrik Möller
 * Latest changed         : 2009-12-04 by Fredrik Möller
 *                        : 2009-12-05 by Fredrik Möller
 */

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import moller.javapeg.program.C;
import moller.util.io.FileUtil;

public class CategoriesRepositoryHandler {

	/**
	 * The static singleton instance of this class.
	 */
	private static CategoriesRepositoryHandler instance;
	
	private File repositoryFile;
	
	/**
	 * Private constructor.
	 */
	private CategoriesRepositoryHandler() {
		repositoryFile = new File(C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION + C.FS + "repository" + C.FS + "categorize.rep");
	}

	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static CategoriesRepositoryHandler getInstance() {
		if (instance != null)
			return instance;
		synchronized (CategoriesRepositoryHandler.class) {
			if (instance == null) {
				instance = new CategoriesRepositoryHandler();
			}
			return instance;
		}
	}
	
	public Set<String> load() {
		if(repositoryFile.exists()) {
			try {
				return new TreeSet<String>(FileUtil.readFromFile(repositoryFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}	
		} else{
			return null;
		}	 
	}
	
	public  void store(Set<String> repositoriesPaths) {
		try {
			FileUtil.writeToFile(repositoryFile, repositoriesPaths, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}