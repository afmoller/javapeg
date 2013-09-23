package moller.javapeg.program.applicationstart;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import moller.javapeg.program.C;
import moller.util.io.DirectoryUtil;

/**
 * This class contains methods which are used when JavaPEG is booting up.
 *
 * @author Fredrik
 *
 */
public class ApplicationBootUtil {

    private static String FIRST_LAUNCH = "firstLaunch";

    private static File javaPEGHome = new File(C.JAVAPEG_HOME);

    /**
     * This method returns whether or not it is the first time this JavaPEG
     * installation is launched. This is determined by searching for a file
     * named "firstLaunch" in the JavaPEG configuration directory.
     *
     * @return a boolean value indication whether or not it is the first time
     *         JavaPEG is started after installation.
     */
    public static boolean isFirstApplicationLaunch() {
        return DirectoryUtil.containsFile(javaPEGHome, FIRST_LAUNCH);
    }

    /**
     * This method deletes the first application launch marker file.
     *
     * @throws IOException
     */
    public static void removeFirstApplicationLaunchMarkerFile() throws IOException {
        File firstLaunchFile = new File(javaPEGHome, FIRST_LAUNCH);

        Files.delete(firstLaunchFile.toPath());
    }
}
