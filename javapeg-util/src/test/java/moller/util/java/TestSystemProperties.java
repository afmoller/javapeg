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


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSystemProperties {

    @Test
    public void testGetFileEncoding() {
        Assertions.assertEquals(System.getProperty("file.encoding"), SystemProperties.getFileEncoding());
    }

    @Test
    public void testGetFileEncodingPkg() {
        Assertions.assertEquals(System.getProperty("file.encoding.pkg"), SystemProperties.getFileEncodingPkg());
    }

    @Test
    public void testGetFileSeparator() {
        Assertions.assertEquals(System.getProperty("file.separator"), SystemProperties.getFileSeparator());
    }

    @Test
    public void testGetJavaClassPath() {
        Assertions.assertEquals(System.getProperty("java.class.path"), SystemProperties.getJavaClassPath());
    }

    @Test
    public void testGetJavaClassVersion() {
        Assertions.assertEquals(System.getProperty("java.class.version"), SystemProperties.getJavaClassVersion());
    }

    @Test
    public void testGetJavaCompiler() {
        Assertions.assertEquals(System.getProperty("java.compiler"), SystemProperties.getJavaCompiler());
    }

    @Test
    public void testGetJavaHome() {
        Assertions.assertEquals(System.getProperty("java.home"), SystemProperties.getJavaHome());
    }

    @Test
    public void testGetJavaIoTmpdir() {
        Assertions.assertEquals(System.getProperty("java.io.tmpdir"), SystemProperties.getJavaIoTmpdir());
    }

    @Test
    public void testGetJavaVersion() {
        Assertions.assertEquals(System.getProperty("java.version"), SystemProperties.getJavaVersion());
    }

    @Test
    public void testGetJavaVendor() {
        Assertions.assertEquals(System.getProperty("java.vendor"), SystemProperties.getJavaVendor());
    }

    @Test
    public void testGetJavaVendorUrl() {
        Assertions.assertEquals(System.getProperty("java.vendor.url"), SystemProperties.getJavaVendorUrl());
    }

    @Test
    public void testGetLineSeparator() {
        Assertions.assertEquals(System.getProperty("line.separator"), SystemProperties.getLineSeparator());
    }

    @Test
    public void testGetOsName() {
        Assertions.assertEquals(System.getProperty("os.name"), SystemProperties.getOsName());
    }

    @Test
    public void testGetOsArch() {
        Assertions.assertEquals(System.getProperty("os.arch"), SystemProperties.getOsArch());
    }

    @Test
    public void testGetOsVersion() {
        Assertions.assertEquals(System.getProperty("os.version"), SystemProperties.getOsVersion());
    }

    @Test
    public void testGetPathSeparator() {
        Assertions.assertEquals(System.getProperty("path.separator"), SystemProperties.getPathSeparator());
    }

    @Test
    public void testGetUserDir() {
        Assertions.assertEquals(System.getProperty("user.dir"), SystemProperties.getUserDir());
    }

    @Test
    public void testGetUserHome() {
        Assertions.assertEquals(System.getProperty("user.home"), SystemProperties.getUserHome());
    }

    @Test
    public void testGetUserLanguage() {
        Assertions.assertEquals(System.getProperty("user.language"), SystemProperties.getUserLanguage());
    }

    @Test
    public void testGetUserName() {
        Assertions.assertEquals(System.getProperty("user.name"), SystemProperties.getUserName());
    }

    @Test
    public void testGetUserRegion() {
        Assertions.assertEquals(System.getProperty("user.region"), SystemProperties.getUserRegion());
    }

    @Test
    public void testGetUserTimezone() {
        Assertions.assertEquals(System.getProperty("user.timezone"), SystemProperties.getUserTimezone());
    }
}
