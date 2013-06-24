package moller.javapeg.program.applicationstart;

import java.io.File;

import moller.javapeg.program.C;
import moller.util.io.DirectoryUtil;
import moller.util.java.SystemProperties;

public class ApplicationBootUtil {

    /**
     * This method returns whether or not it is the first time this JavaPEG
     * installation is launched. This is determined by searching for a file
     * named "firstLaunch" in the JavaPEG configuration directory.
     *
     * @return a boolean value indication whether or not it is the first time
     *         JavaPEG is started after installation.
     */
    public static boolean isFirstApplicationLaunch() {

        File javaPEGHome = new File(SystemProperties.getUserHome(), "javapeg-" + C.JAVAPEG_VERSION);

        return DirectoryUtil.containsFile(javaPEGHome, "firstLaunch");
    }

}
