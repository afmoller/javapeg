package moller.util.test.unittest.io;

import java.io.File;

import junit.framework.Assert;
import moller.util.io.PathUtil;

import org.junit.Test;

public class TestPathUtil {

    @Test
    public void testIsChild() {
        File parent = new File(System.getProperty("user.dir"));
        File child = new File(parent, "child");

        Assert.assertTrue(PathUtil.isChild(child, parent));
        Assert.assertFalse(PathUtil.isChild(parent, child));
        Assert.assertFalse(PathUtil.isChild(parent, parent));
        Assert.assertFalse(PathUtil.isChild(child, child));
    }

    @Test
    public void testIsParent() {
        File parent = new File(System.getProperty("user.dir"));
        File child = new File(parent, "child");

        Assert.assertTrue(PathUtil.isParent(parent, child));
        Assert.assertFalse(PathUtil.isParent(child, parent));
        Assert.assertFalse(PathUtil.isParent(parent, parent));
        Assert.assertFalse(PathUtil.isParent(child, child));
    }
}
