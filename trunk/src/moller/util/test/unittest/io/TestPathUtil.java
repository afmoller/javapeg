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
package moller.util.test.unittest.io;

import java.io.File;

import moller.util.io.PathUtil;

import org.junit.Assert;
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
