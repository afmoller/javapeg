package moller.javapeg.program.rename.validator;

import java.io.File;
import java.util.Collection;

import moller.javapeg.program.FileRetriever;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.enumerations.Type;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.rename.FileAndType;
import moller.javapeg.program.rename.RenameProcessContext;
import moller.javapeg.program.rename.ValidatorStatus;
import moller.util.io.DirectoryUtil;

/**
 * This class validates the length of the paths that all non
 * JPEG files and directories in the source directory will get
 * when they are copied to the destination directory.
 *
 * @author Fredrik
 *
 */
public class NonJPEGTotalPathLength {

    /**
     * The static singleton instance of this class.
     */
    private static NonJPEGTotalPathLength instance;

    /**
     * The system dependent file separator char
     */
    private final static String FS = File.separator;

    /**
     * The length of the system dependent file separator char
     */
    private static final int FSL = File.separator.length();

    /**
     * Private constructor
     */
    private NonJPEGTotalPathLength() {
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static NonJPEGTotalPathLength getInstance() {
        if (instance != null)
            return instance;
        synchronized (NonJPEGTotalPathLength.class) {
            if (instance == null) {
                instance = new NonJPEGTotalPathLength();
            }
            return instance;
        }
    }

    /**
     * Validate the path lengths for all potential non JPEG files and sub
     * directories that might exist in the source directory. For files the
     * maximum allowed path length is 260 characters and for folders it is 249
     * characters.
     *
     * For files, the paths are constructed as follows:
     *
     * Selected destination directory + converted sub directory name + file name
     *
     * For sub directories, the paths are constructed as follows:
     *
     * Selected destination directory + converted sub directory name + longest
     * path in sub directory
     *
     * @return a boolean value indicating whether all paths are valid regarding
     *         path lengths. True means all paths are valid.
     */
    public ValidatorStatus test() {

        Language lang = Language.getInstance();

        Logger logger = Logger.getInstance();

        ApplicationContext ac = ApplicationContext.getInstance();

        String destinationPath = ac.getDestinationPath();
        File sourcePath      = ac.getSourcePath();

        Collection<File> nonJPEGFiles = FileRetriever.getInstance().getNonJPEGFiles();

        RenameProcessContext rpc = RenameProcessContext.getInstance();
        rpc.clearAllNonJPEGFileNameMappings();

        String  convertedSubFolderNameTemplate = rpc.getSubDirectoryName();

        for (File file : nonJPEGFiles) {

            ValidatorStatus vs = new ValidatorStatus(true, "");

            int destinationPathLength = destinationPath.length() + FSL + convertedSubFolderNameTemplate.length() + FSL +  file.getName().length();

            if (file.isFile()) {
                if (destinationPathLength > 260) {
                    vs.setValid(false);
                    vs.setStatusMessage(lang.get("validator.nonjpegtotalpathlength.toLongFileName") + " " + file.getName());
                    logger.logINFO(lang.get("validator.nonjpegtotalpathlength.toLongFileName") + " " + file.getName());
                    return vs;
                }
                rpc.addNonJPEGFileNameMapping(file, new FileAndType(new File(destinationPath + FS + convertedSubFolderNameTemplate + FS + file.getName()), Type.FILE));

            } else {
                try {
                    String subDirectoryPath = DirectoryUtil.getLongestSubDirectoryPath(file, file);

                    int subDestinationPathLength = (destinationPathLength + subDirectoryPath.length());

                    if (subDestinationPathLength > 249) {

                        File type = new File(sourcePath + FS + file.getName() + subDirectoryPath);

                        if (type.isDirectory()) {
                            vs.setValid(false);
                            vs.setStatusMessage(lang.get("validator.nonjpegtotalpathlength.toLongDirectoryPath") + " " + file.getName());
                            logger.logINFO(lang.get("validator.nonjpegtotalpathlength.toLongDirectoryPath") + " " + file.getName());
                            return vs;
                        }
                        if (type.isFile() && subDestinationPathLength > 260) {
                            vs.setValid(false);
                            vs.setStatusMessage(lang.get("validator.nonjpegtotalpathlength.toLongFileName") + " " + file.getName());
                            logger.logINFO(lang.get("validator.nonjpegtotalpathlength.toLongFileName") + " " + file.getName());
                            return vs;
                        }
                    }

                    for (File f : DirectoryUtil.getDirectoryContent(file)) {

                        FileAndType fat = new FileAndType(new File(destinationPath + FS + convertedSubFolderNameTemplate + f.getAbsolutePath().substring(sourcePath.getAbsolutePath().length())));
                        fat.setType(f.isFile() ? (Type.FILE) : Type.DIRECTORY);

                        rpc.addNonJPEGFileNameMapping(f, fat);
                    }
                } catch (Exception e) {
                    logger.logINFO("File: " + file.getAbsolutePath() + " is not a directory");
                }
            }
        }
        return new ValidatorStatus(true, "");
    }
}