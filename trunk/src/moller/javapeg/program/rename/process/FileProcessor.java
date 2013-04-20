package moller.javapeg.program.rename.process;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import moller.javapeg.program.C;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.enumerations.Type;
import moller.javapeg.program.jpeg.JPEGThumbNailCache;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.progress.RenameProcess;
import moller.javapeg.program.rename.FileAndType;
import moller.javapeg.program.rename.RenameProcessContext;
import moller.util.io.FileUtil;

public class FileProcessor {

    /**
     * The static singleton instance of this class.
     */
    private static FileProcessor instance;

    /**
     * Private constructor.
     */
    private FileProcessor() {
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static FileProcessor getInstance() {
        if (instance != null)
            return instance;
        synchronized (FileProcessor.class) {
            if (instance == null) {
                instance = new FileProcessor();
            }
            return instance;
        }
    }

    public boolean process(RenameProcess rp) {

        Logger logger = Logger.getInstance();

        ApplicationContext ac = ApplicationContext.getInstance();

        RenameProcessContext rpc = RenameProcessContext.getInstance();

        Language lang = Language.getInstance();

        String destinationPath = ac.getDestinationPath();
        String subDirectoryName = rpc.getSubDirectoryName();

        /**
         * Create sub directory.
         */
        rp.setRenameProgressMessages(lang.get("rename.FileProcessor.createSubDirectory"));

        File subDirectory = new File(destinationPath + C.FS + subDirectoryName);
        subDirectory.mkdir();

        rp.incProcessProgress();


        if (ac.isCreateThumbNailsCheckBoxSelected()) {
            /**
             * Create thumb nails directory.
             */
            rp.setRenameProgressMessages(lang.get("rename.FileProcessor.createThumbNailsDirectory"));
            File thumbNailDirectory = new File(destinationPath + C.FS + subDirectoryName + C.FS + rpc.getTHUMBNAIL_DIRECTORY_NAME());
            thumbNailDirectory.mkdir();
            rp.incProcessProgress();

            /**
             * Create thumb nails
             */
            rp.setRenameProgressMessages(lang.get("rename.FileProcessor.createThumbNails"));

            Map<File, File> allThumbNailFileNameMappings = rpc.getAllThumbNailFileNameMappings();

            JPEGThumbNailCache jtc = JPEGThumbNailCache.getInstance();

            if (FileUtil.createFiles(allThumbNailFileNameMappings.values())) {
                for (File source : allThumbNailFileNameMappings.keySet()) {
                    FileUtil.copyFile(jtc.get(source).getThumbNailData(), allThumbNailFileNameMappings.get(source));
                }
            }
            rp.incProcessProgress();
        }

        /**
         * Create and transfer content of JPEG files.
         */
        rp.setRenameProgressMessages(lang.get("rename.FileProcessor.createAndTransferContentOfJPEGFiles"));

        Map<File, File> allJPEGFileNameMappings = rpc.getAllJPEGFileNameMappings();

        if (FileUtil.createFiles(allJPEGFileNameMappings.values())) {
            for (File source : allJPEGFileNameMappings.keySet()) {
                FileUtil.copyFile(source, allJPEGFileNameMappings.get(source));
                rp.setLogMessage(lang.get("rename.FileProcessor.renameFromLabel") + " " + source.getName() + " " + lang.get("rename.FileProcessor.renameToLabel") + " " + allJPEGFileNameMappings.get(source).getName());
                logger.logDEBUG("File: " + source.getName() + " Renamed To: " + allJPEGFileNameMappings.get(source).getName());
            }
        } else {
            logger.logERROR("All JPEG files could not be created");
            return false;
        }
        rp.incProcessProgress();

        /**
         * Create non JPEG files.
         */
        rp.setRenameProgressMessages(lang.get("rename.FileProcessor.createAndTransferContentOfNonJPEGFiles"));

        Map<File, FileAndType> allNonJPEGFileNameMappings = rpc.getAllNonJPEGFileNameMappings();

        for (FileAndType fileAndType : allNonJPEGFileNameMappings.values()) {

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

        /**
         * Transfer content of non JPEG files.
         */
        for (File sourceFile : allNonJPEGFileNameMappings.keySet()) {

            FileAndType fat = allNonJPEGFileNameMappings.get(sourceFile);

            Type type = fat.getType();
            File destinationFile = fat.getFile();

            if (type.equals(Type.FILE)) {
                if (destinationFile.exists()) {
                    if (sourceFile.getName().equals(C.JAVAPEG_IMAGE_META_NAME)) {
                        try {
                            /**
                             * Get content of the meta data file
                             */
                            List<String> fileRows = FileUtil.readFromFile(sourceFile);
                            /**
                             * Modify the content of the meta data file (change
                             * file names so they will match the new names of
                             * the JPEG image files).
                             */
                            for (File originalName : allJPEGFileNameMappings.keySet()) {
                                String originalNameString = originalName.getName();

                                for (int i = 0; i < fileRows.size(); i++) {
                                    if (fileRows.get(i).contains(originalNameString)) {
                                        fileRows.set(i, fileRows.get(i).replace(originalNameString, allJPEGFileNameMappings.get(originalName).getName()));
                                        break;
                                    }
                                }
                            }
                            rpc.setJavaPegImageMetaFileContent(fileRows);

                            /**
                             * Store the modified content to new destination
                             * file.
                             */
                            FileUtil.writeToFile(destinationFile, fileRows, false);

                            rp.setLogMessage(sourceFile.getAbsolutePath());
                            logger.logDEBUG("Copy: " + sourceFile.getAbsolutePath() + " to: " + destinationFile.getAbsolutePath() + " with file names changed");
                        } catch (IOException iox) {
                            logger.logERROR("Could not change the file names in the file: " + sourceFile.getAbsolutePath() + " the file is copied unchanged. See stacktrace below for details");
                            logger.logERROR(iox);
                            this.copyFileAndSetLogMessage(sourceFile, destinationFile, rp, logger);
                        }
                    } else {
                        this.copyFileAndSetLogMessage(sourceFile, destinationFile, rp, logger);
                    }
                } else {
                    logger.logERROR("Could not copy content of source file: " + sourceFile.getAbsolutePath() + " to destination file : " + destinationFile.getAbsolutePath() + " since destination file does not exist.");
                    return false;
                }
            }
        }
        rp.incProcessProgress();
        return true;
    }

    private void copyFileAndSetLogMessage (File sourceFile, File destinationFile, RenameProcess rp, Logger logger) {
        FileUtil.copyFile(sourceFile, destinationFile);
        rp.setLogMessage(sourceFile.getAbsolutePath());
        logger.logDEBUG("Copy: " + sourceFile.getAbsolutePath() + " to: " + destinationFile.getAbsolutePath());
    }
}