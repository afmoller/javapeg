/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
