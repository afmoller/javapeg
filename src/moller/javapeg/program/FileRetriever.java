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
package moller.javapeg.program;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.enumerations.FileLoadingAction;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.jpeg.JPEGUtil;

public class FileRetriever {

    /**
     * The static singleton instance of this class.
     */
    private static FileRetriever instance;

    /**
     * This Map contains a File name and File object mapping. In other words:
     * the key in the Map is a file name and the value is the actual file
     * object which have the file name denoted by the key
     */
    private final Map<String, File> jpegFileNameFileObjectMap;

    /**
     * This Map contains a File name and File object mapping. In other words:
     * the key in the Map is a file name and the value is the actual file
     * object which have the file name denoted by the key
     */
    private final Map<String, File> nonJpegFileNameFileObjectMap;

    private int nrOfJpegImages;

    private static Language lang;
    private static Logger   logger;

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static FileRetriever getInstance() {
        if (instance != null)
            return instance;
        synchronized (FileRetriever.class) {
            if (instance == null) {
                instance = new FileRetriever();
            }
            return instance;
        }
    }

    private FileRetriever() {
        lang   = Language.getInstance();
        logger = Logger.getInstance();

        nrOfJpegImages = 0;

        jpegFileNameFileObjectMap    = new HashMap<String, File>(128);
        nonJpegFileNameFileObjectMap = new HashMap<String, File>(128);
    }

    /**
     * This method will list all files in a directory on disk and put
     * them into two different Map structures, one with JPEG files and
     * the second one with all potentially existing non JPEG files and
     * directories.
     *
     * @param files is a List with all the files that shall be separated into
     *        the two different file categories.
     */
    public void loadFilesFromDisk(List<File> files) {

        nrOfJpegImages = 0;
        jpegFileNameFileObjectMap.clear();
        nonJpegFileNameFileObjectMap.clear();

        ApplicationContext ac = ApplicationContext.getInstance();
        ac.clearJpegFileLoadBuffer();
        for (File file : files) {
            try {
                if(JPEGUtil.isJPEG(file)) {
                    logger.logDEBUG("File: " + file.getAbsolutePath() + " added to list of JPEG Files");
                    jpegFileNameFileObjectMap.put(file.getName(), file);
                    handleNrOfJpegImages(FileLoadingAction.SET);
                    ac.handleJpegFileLoadBuffer(file, FileLoadingAction.ADD);
                } else {
                    logger.logDEBUG("File: " + file.getAbsolutePath() + " added to list of non JPEG Files");
                    nonJpegFileNameFileObjectMap.put(file.getName(), file);
                }
            } catch (FileNotFoundException fnfex) {
                JOptionPane.showMessageDialog(null, lang.get("fileretriever.canNotFindFile") + "\n(" + file.getAbsolutePath() + ")", lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                logger.logERROR("Could not find file:");
                logger.logERROR(fnfex);
            } catch (IOException iox) {
                JOptionPane.showMessageDialog(null, lang.get("fileretriever.canNotReadFromFile") + "\n(" + file.getAbsolutePath() + ")", lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                logger.logERROR("Can not read from file:");
                logger.logERROR(iox);
            }
        }
    }

    public synchronized int handleNrOfJpegImages(FileLoadingAction action) {
        switch (action) {
        case RETRIEVE:
            return nrOfJpegImages;
        case SET:
            nrOfJpegImages++;
            return -1;
        default:
            throw new RuntimeException("Unsupported Action");
        }
    }

    /**
     * Get a collection of JPEG File objects.
     *
     * @return A collection of File objects.
     */
    public Collection <File> getJPEGFiles() {
        return new TreeSet<File>(jpegFileNameFileObjectMap.values());
    }

    /**
     * Get a Map consisting of a key - value pair of String - File
     * objects, where the String object represents the name of the
     * file and the File object the actual file.
     *
     * @return A Map with file name and file pairs.
     */
    public Map<String, File> getJPEGFileNameFileObjectMap() {
        return jpegFileNameFileObjectMap;
    }

    /**
     * Get a collection of non JPEG File objects.
     *
     * @return A collection of File objects.
     */
    public Collection <File> getNonJPEGFiles() {
        return nonJpegFileNameFileObjectMap.values();
    }

    /**
     * Get a Map consisting of a key - value pair of String - File
     * objects, where the String object represents the name of the
     * file and the File object the actual file.
     *
     * @return A Map with file name and file pairs.
     */
    public Map<String, File> getNonJPEGFileNameFileObjectMap() {
        return nonJpegFileNameFileObjectMap;
    }
}