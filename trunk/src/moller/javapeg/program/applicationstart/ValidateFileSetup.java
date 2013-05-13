package moller.javapeg.program.applicationstart;

import java.io.File;
import java.io.IOException;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.logger.Logger;
import moller.util.io.FileUtil;

public class ValidateFileSetup {

    public static void check() {

        File javaPEGuserHome = new File(C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION);

        File logDirectory = new File(javaPEGuserHome, "logs");
        File configFile   = new File(javaPEGuserHome, "config" + C.FS + "conf.xml");
        File layoutInfo   = new File(javaPEGuserHome, "resources" + C.FS + "thumb" + C.FS + "layout.info");
        File styleInfo    = new File(javaPEGuserHome, "resources" + C.FS + "thumb" + C.FS + "style.info");

        checkFileObject(logDirectory);
        checkFileObject(configFile);
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
                if (fileNameToCheck.equals("logs")) {
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
            } else {
                if (fileNameToCheck.equals("logs")) {
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
