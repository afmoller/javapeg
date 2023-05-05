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
package moller.util.io;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestPathUtil {

    @Test
    public void testIsChild() {
        File parent = new File(System.getProperty("user.dir"));
        File child = new File(parent, "child");

        Assertions.assertTrue(PathUtil.isChild(child, parent));
        Assertions.assertFalse(PathUtil.isChild(parent, child));
        Assertions.assertFalse(PathUtil.isChild(parent, parent));
        Assertions.assertFalse(PathUtil.isChild(child, child));
    }

    @Test
    public void testIsParent() {
        File parent = new File(System.getProperty("user.dir"));
        File child = new File(parent, "child");

        Assertions.assertTrue(PathUtil.isParent(parent, child));
        Assertions.assertFalse(PathUtil.isParent(child, parent));
        Assertions.assertFalse(PathUtil.isParent(parent, parent));
        Assertions.assertFalse(PathUtil.isParent(child, child));
    }
}
