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

import moller.util.java.SystemProperties;

import java.io.File;

/**
 * This class contains environment or instance information constants that might
 * be useful in the entire JavaPEG application.
 *
 * @author Fredrik
 *
 */
public class C {

    /**
     * The system dependent file separator char
     */
    public final static String FS  = File.separator;

    /**
     * The system dependent line separator
     */
    public final static String LS = SystemProperties.getLineSeparator();

    /**
     * The home directory for the current user.
     */
    public final static String USER_HOME = SystemProperties.getUserHome();

    /**
     * JavaPEG version number
     */
    public final static String JAVAPEG_VERSION = "3.0";

    /**
     * In which directory the user specific data of JavaPEG is installed;
     * configuration, logs and so on.
     */
    public final static String JAVAPEG_HOME = C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION;

    /**
     * Path to the configuration file.
     */
    public final static String PATH_TO_CONFIGURATION_FILE = JAVAPEG_HOME + C.FS + "config" + C.FS + "conf.xml";

    /**
     * This variable  contains a time stamp which is set to when the latest
     * release is done. The value is the amount of milliseconds since
     * 1970-01-01 with
     */
    public final static long VERSION_TIMESTAMP = 1258486103;

    public final static String DIRECTORY_STATUS_DELIMITER = "-";

    /**
     * The name of the file containing meta data of images in the same
     * directory as this file is stored in.
     */
    public final static String JAVAPEG_IMAGE_META_NAME = "javapeg-image-meta.xml";

    public final static String IMAGE_META_DATA_DATA_BASE_VERSION = "1";

    public static final String META_DATA_PARAMETER_VALUES_DELIMITER = " || ";

    public final static String META_DATA_PARAMETER_VALUES_DELIMITER_REGEXP = "\\s\\|\\|\\s";

    public final static String ICONFILEPATH = "resources/images/";
    public final static String ICONFILEPATH_IMAGEVIEWER = ICONFILEPATH + "imageviewer/";

    /**
     * The file encoding used by JavaPEG when a text based file is created.
     */
    public final static String UTF8 = "UTF-8";

    public final static String PATH_SCHEMAS = "resources/schema/";
    public final static String PATH_SCHEMA_META_DATA = "resources/schema/metadata/";
}
