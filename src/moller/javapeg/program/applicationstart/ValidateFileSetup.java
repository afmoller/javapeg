package moller.javapeg.program.applicationstart;

/**
 * This class was created : 2009-04-09 by Fredrik M�ller
 * Latest changed         : 2009-04-12 by Fredrik M�ller
 *                        : 2009-04-21 by Fredrik M�ller
 *                        : 2009-08-21 by Fredrik M�ller
 *                        : 2009-11-11 by Fredrik M�ller
 *                        : 2009-11-13 by Fredrik M�ller
 *                        : 2009-11-14 by Fredrik M�ller
 *                        : 2009-12-05 by Fredrik M�ller
 */

import java.io.File;
import java.io.IOException;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.logger.Logger;
import moller.util.io.FileUtil;

public class ValidateFileSetup {
	
	public static void check() {
		
		File javaPEGuserHome = new File(C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION);

		File configFile          = new File(javaPEGuserHome, "config" + C.FS + "conf.xml");
		File logDirectory        = new File(javaPEGuserHome, "logs");
		File repositoryDirectory = new File(javaPEGuserHome, "repository");
		File helpInfo            = new File(javaPEGuserHome, "resources" + C.FS + "help" + C.FS + "help.info");
		File languageInfo        = new File(javaPEGuserHome, "resources" + C.FS + "lang" + C.FS + "language.info");
		File layoutInfo          = new File(javaPEGuserHome, "resources" + C.FS + "thumb" + C.FS + "layout.info");
		File styleInfo           = new File(javaPEGuserHome, "resources" + C.FS + "thumb" + C.FS + "style.info");
		
		checkFileObject(configFile);
		checkFileObject(logDirectory);
		checkFileObject(repositoryDirectory);
		checkFileObject(helpInfo);
		checkFileObject(languageInfo);
		checkFileObject(layoutInfo);
		checkFileObject(styleInfo);
	}
	
	private static void checkFileObject (File fileToCheck) {
		
		Logger logger = null;
		String fileNameToCheck = fileToCheck.getName();
		
		if (log(fileNameToCheck)) {
			logger = Logger.getInstance();
		}
		
		File parent = fileToCheck.getParentFile();
		String parentPath = parent.getAbsolutePath();
		
		if (!fileToCheck.exists()) {
			if (!parent.exists()) {
				if (log(fileNameToCheck)) {
					logger.logDEBUG("The necessary directory: " + parentPath + " does not exist");
					logger.logDEBUG("Try to create direcotry: " + parentPath);
				}
				if (!parent.mkdirs()) {
					if (log(fileNameToCheck)) {
						logger.logFATAL("Could not create directory: " + parent.getAbsolutePath());
						logger.flush();
					}
					System.exit(1);
				} else {
					if (log(fileNameToCheck)) {
						logger.logDEBUG("Directory: " + parent.getAbsolutePath() + " has been created");
					}
				}
				if(!createFile(fileToCheck)) {
					if (log(fileNameToCheck)) {
						logger.logFATAL("Could not create file: " + fileToCheck.getAbsolutePath());
						logger.flush();
					}
					System.exit(1);
				} else {
					if (log(fileNameToCheck)) {
						logger.logDEBUG("File: " + fileToCheck.getAbsolutePath() + " has been created");
					}
				}
			} else {
				if (fileNameToCheck.equals("logs") || fileNameToCheck.equals("repository")) {
					if(!fileToCheck.mkdir()) {
						System.exit(1);
					} else {
						if (log(fileNameToCheck)) {
							logger.logDEBUG("Directory: " + fileToCheck.getAbsolutePath() + " has been created");
						}
					}
				} else {
					if(!createFile(fileToCheck)) {
						if (log(fileNameToCheck)) {
							logger.logFATAL("Could not create file: " + fileToCheck.getAbsolutePath());
							logger.flush();
						}
						System.exit(1);
					} else {
						if (log(fileNameToCheck)) {
							logger.logDEBUG("File: " + fileToCheck.getAbsolutePath() + " has been created");
						}
					}
				}
			}
		}
	}
	
	private static boolean createFile(File fileToCreate) {
				
		if(!FileUtil.createFile(fileToCreate)) {
			return false;
		}
		try {
			FileUtil.copy(StartJavaPEG.class.getResourceAsStream("resources/startup/" + fileToCreate.getName()), fileToCreate);
		} catch (IOException e) {
			if (log(fileToCreate.getName())) {
				Logger logger = Logger.getInstance();
				logger.logFATAL("Could not write to file: " + fileToCreate);
				for(StackTraceElement element : e.getStackTrace()) {
					logger.logFATAL(element.toString());	
				}
			}
			return false;
		}			
		return true;
	}
	
	private static boolean log(String fileName) {
		return fileName.equals("conf.xml") || fileName.equals("logs") ? false : true;
	}
}