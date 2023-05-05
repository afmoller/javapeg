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
package moller.javapeg.program.rename.validator;

import moller.javapeg.program.FileRetriever;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.jpeg.JPEGThumbNailCache;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.rename.ValidatorStatus;
import moller.util.io.DirectoryUtil;
import moller.util.io.FileUtil;

import java.io.File;

public class AvailableDiskSpace {

    /**
     * The static singleton instance of this class.
     */
    private static AvailableDiskSpace instance;

    /**
     * Private constructor.
     */
    private AvailableDiskSpace() {
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static AvailableDiskSpace getInstance() {
        if (instance != null)
            return instance;
        synchronized (AvailableDiskSpace.class) {
            if (instance == null) {
                instance = new AvailableDiskSpace();
            }
            return instance;
        }
    }

    public ValidatorStatus test() {

        ValidatorStatus vs = new ValidatorStatus(true, "");

        ApplicationContext ac = ApplicationContext.getInstance();

        long totalDiskSpaceNeeded = 0;

        /**
         * Get needed disk space for all files in the source directory
         */
        try {
            totalDiskSpaceNeeded = DirectoryUtil.getDirectorySizeOnDisk(ac.getSourcePath());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * If there should be an thumb nail overview created the total size of
         * the thumb nails is calculated here.
         */
        if (ac.isCreateThumbNailsCheckBoxSelected()) {
            int totalThumbNailSize = 0;

            JPEGThumbNailCache jtc = JPEGThumbNailCache.getInstance();

            for (File jpegFile : FileRetriever.getInstance().getJPEGFiles()) {
                if (jtc.exists(jpegFile)) {
                    totalThumbNailSize += FileUtil.getFileSizeOnDisk(jtc.get(jpegFile).getThumbNailSize(), 4096);
                }
            }
            totalDiskSpaceNeeded += totalThumbNailSize;
        }

        /**
         * The check to see if there is enough space available in the destination
         * directory.
         */
        if (availableDiskSpaceAt(ac.getDestinationPath()) > totalDiskSpaceNeeded) {
            return vs;
        } else {
            vs.setValid(false);
            vs.setStatusMessage(Language.getInstance().get("validator.availablediskspace.notEnoughDiskSpace"));
            return vs;
        }
    }

    /**
     * This method returns the usable disk space in the specified directory.
     *
     * @param directory the directory to get available disk space at.
     *
     * @return the usable disk space at the directory specified by the directory
     *         parameter.
     */
    private long availableDiskSpaceAt (String directory) {
        return new File(directory).getUsableSpace();
    }
}