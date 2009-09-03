package moller.javapeg.program;

/**
 * This class was created : 2009-04-09 by Fredrik Möller
 * Latest changed         : 2009-04-12 by Fredrik Möller
 *                        : 2009-04-21 by Fredrik Möller
 *                        : 2009-08-21 by Fredrik Möller
 */

import java.io.File;
import java.io.IOException;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.logger.Logger;
import moller.util.io.FileUtil;
import moller.util.io.StreamUtil;

public class FileSetup {
		
	public static void check() {
							
		File userDir = new File(System.getProperty("user.dir"));
		
		String fs = File.separator;
						
		File configFile = new File(userDir, "config" + fs +  "conf.xml");
		File logDirectory = new File(userDir, "logs");
		File languageInfo = new File(userDir, "resources" + fs + "lang" + fs + "language.info");
		File layoutInfo = new File(userDir, "resources" + fs + "thumb" + fs + "layout.info");
		File styleInfo = new File(userDir, "resources" + fs + "thumb" + fs + "style.info");
		
		checkFileObject(configFile);
		checkFileObject(logDirectory);
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
					}
					System.exit(1);
				} else {
					if (log(fileNameToCheck)) {
						logger.logDEBUG("File: " + fileToCheck.getAbsolutePath() + " has been created");
					}
				}
			} else {
				if (fileNameToCheck.equals("logs")) {
					if(!fileToCheck.mkdir()) {
						if (log(fileNameToCheck)) {
							logger.logFATAL("Could not create directory: " + fileToCheck.getAbsolutePath());
							System.exit(1);
						}
					} else {
						if (log(fileNameToCheck)) {
							logger.logDEBUG("Directory: " + fileToCheck.getAbsolutePath() + " has been created");
						}
					}
				} else {
					if(!createFile(fileToCheck)) {
						if (log(fileNameToCheck)) {
							logger.logFATAL("Could not create file: " + fileToCheck.getAbsolutePath());
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
			String fileContent = StreamUtil.getString(StartJavaPEG.class.getResourceAsStream("resources/startup/" + fileToCreate.getName()));
			FileUtil.writeToFile(fileToCreate, fileContent, false);
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