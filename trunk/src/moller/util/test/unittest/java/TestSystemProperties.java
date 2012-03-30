package moller.util.test.unittest.java;

import junit.framework.Assert;
import moller.util.java.SystemProperties;

import org.junit.Test;

public class TestSystemProperties {

    @Test
    public void testGetFileEncoding() {
        Assert.assertEquals(System.getProperty("file.encoding"), SystemProperties.getFileEncoding());
    }

    @Test
    public void testGetFileEncodingPkg() {
        Assert.assertEquals(System.getProperty("file.encoding.pkg"), SystemProperties.getFileEncodingPkg());
    }

    @Test
    public void testGetFileSeparator() {
        Assert.assertEquals(System.getProperty("file.separator"), SystemProperties.getFileSeparator());
    }

    @Test
    public void testGetJavaClassPath() {
        Assert.assertEquals(System.getProperty("java.class.path"), SystemProperties.getJavaClassPath());
    }

    @Test
    public void testGetJavaClassVersion() {
        Assert.assertEquals(System.getProperty("java.class.version"), SystemProperties.getJavaClassVersion());
    }

    @Test
    public void testGetJavaCompiler() {
        Assert.assertEquals(System.getProperty("java.compiler"), SystemProperties.getJavaCompiler());
    }

    @Test
    public void testGetJavaHome() {
        Assert.assertEquals(System.getProperty("java.home"), SystemProperties.getJavaHome());
    }

    @Test
    public void testGetJavaIoTmpdir() {
        Assert.assertEquals(System.getProperty("java.io.tmpdir"), SystemProperties.getJavaIoTmpdir());
    }

    @Test
    public void testGetJavaVersion() {
        Assert.assertEquals(System.getProperty("java.version"), SystemProperties.getJavaVersion());
    }

    @Test
    public void testGetJavaVendor() {
        Assert.assertEquals(System.getProperty("java.vendor"), SystemProperties.getJavaVendor());
    }

    @Test
    public void testGetJavaVendorUrl() {
        Assert.assertEquals(System.getProperty("java.vendor.url"), SystemProperties.getJavaVendorUrl());
    }

    @Test
    public void testGetLineSeparator() {
        Assert.assertEquals(System.getProperty("line.separator"), SystemProperties.getLineSeparator());
    }

    @Test
    public void testGetOsName() {
        Assert.assertEquals(System.getProperty("os.name"), SystemProperties.getOsName());
    }

    @Test
    public void testGetOsArch() {
        Assert.assertEquals(System.getProperty("os.arch"), SystemProperties.getOsArch());
    }

    @Test
    public void testGetOsVersion() {
        Assert.assertEquals(System.getProperty("os.version"), SystemProperties.getOsVersion());
    }

    @Test
    public void testGetPathSeparator() {
        Assert.assertEquals(System.getProperty("path.separator"), SystemProperties.getPathSeparator());
    }

    @Test
    public void testGetUserDir() {
        Assert.assertEquals(System.getProperty("user.dir"), SystemProperties.getUserDir());
    }

    @Test
    public void testGetUserHome() {
        Assert.assertEquals(System.getProperty("user.home"), SystemProperties.getUserHome());
    }

    @Test
    public void testGetUserLanguage() {
        Assert.assertEquals(System.getProperty("user.language"), SystemProperties.getUserLanguage());
    }

    @Test
    public void testGetUserName() {
        Assert.assertEquals(System.getProperty("user.name"), SystemProperties.getUserName());
    }

    @Test
    public void testGetUserRegion() {
        Assert.assertEquals(System.getProperty("user.region"), SystemProperties.getUserRegion());
    }

    @Test
    public void testGetUserTimezone() {
        Assert.assertEquals(System.getProperty("user.timezone"), SystemProperties.getUserTimezone());
    }
}
