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
package moller.util.java;

public class SystemProperties {

    /**
     * @return The character encoding for the default locale
     */
    public static String getFileEncoding() {
        return System.getProperty("file.encoding");
    }

    /**
     * @return The package that contains the converters that handle converting between local encodings and Unicode
     */
    public static String getFileEncodingPkg() {
        return System.getProperty("file.encoding.pkg");
    }

    /**
     * @return The platform-dependent file separator (e.g., "/" on UNIX, "\" for Windows)
     */
    public static String getFileSeparator() {
        return System.getProperty("file.separator");
    }

    /**
     * @return The value of the CLASSPATH environment variable
     */
    public static String getJavaClassPath() {
        return System.getProperty("java.class.path");
    }

    /**
     * @return The version of the Java API
     */
    public static String getJavaClassVersion() {
        return System.getProperty("java.class.version");
    }

    /**
     * @return The just-in-time compiler to use, if any. The java interpreter provided with the JDK initializes this property from the environment variable JAVA_COMPILER.
     */
    public static String getJavaCompiler() {
        return System.getProperty("java.compiler");
    }

    /**
     * @return The directory in which Java is installed
     */
    public static String getJavaHome() {
        return System.getProperty("java.home");
    }

    /**
     * @return The directory in which java should create temporary files
     */
    public static String getJavaIoTmpdir() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * @return The version of the Java interpreter
     */
    public static String getJavaVersion() {
        return System.getProperty("java.version");
    }

    /**
     * @return A vendor-specific string
     */
    public static String getJavaVendor() {
        return System.getProperty("java.vendor");
    }

    /**
     * @return A vendor URL
     */
    public static String getJavaVendorUrl() {
        return System.getProperty("java.vendor.url");
    }

    /**
     * @return The platform-dependent line separator (e.g., "\n" on UNIX, "\r\n" for Windows)
     */
    public static String getLineSeparator() {
        return System.getProperty("line.separator");
    }

    /**
     * @return The name of the operating system
     */
    public static String getOsName() {
        return System.getProperty("os.name");
    }

    /**
     * @return The system architecture
     */
    public static String getOsArch() {
        return System.getProperty("os.arch");
    }

    /**
     * @return The operating system version
     */
    public static String getOsVersion() {
        return System.getProperty("os.version");
    }

    /**
     * @return The platform-dependent path separator (e.g., ":" on UNIX, "," for Windows)
     */
    public static String getPathSeparator() {
        return System.getProperty("path.separator");
    }

    /**
     * @return The current working directory when the properties were initialized
     */
    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    /**
     * @return The home directory of the current user
     */
    public static String getUserHome() {
        return System.getProperty("user.home");
    }

    /**
     * @return The two-letter language code of the default locale
     */
    public static String getUserLanguage() {
        return System.getProperty("user.language");
    }

    /**
     * @return The username of the current user
     */
    public static String getUserName() {
        return System.getProperty("user.name");
    }

    /**
     * @return The two-letter country code of the default locale
     */
    public static String getUserRegion() {
        return System.getProperty("user.region");
    }

    /**
     * @return The default time zone
     */
    public static String getUserTimezone() {
        return System.getProperty("user.timezone");
    }
}
