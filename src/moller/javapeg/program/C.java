package moller.javapeg.program;

import java.io.File;

import moller.util.java.SystemProperties;

public class C {

    /**
     * The system dependent file separator char
     */
    public final static String FS              = File.separator;

    /**
     * The system dependent line separator
     */
    public final static String LS              = SystemProperties.getLineSeparator();

    public final static String USER_HOME       = SystemProperties.getUserHome();
    public final static String JAVAPEG_VERSION = "3.0";
    public final static String JAVAPEG_HOME = C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION;
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

    public final static String ICONFILEPATH_IMAGEVIEWER = "resources/images/imageviewer/";

    public final static String UTF8 = "UTF-8";

    public final static String PATH_SCHEMAS = "resources/schema/";
}
