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
package moller.util.test.unittest.string;

import moller.util.string.Tab;
import org.junit.Assert;
import org.junit.Test;

public class TestTab {

    @Test
    public void testValue() {
        Assert.assertEquals(" ", Tab.ONE.value());
        Assert.assertEquals("  ", Tab.TWO.value());
        Assert.assertEquals("   ", Tab.THREE.value());
        Assert.assertEquals("    ", Tab.FOUR.value());
        Assert.assertEquals("     ", Tab.FIVE.value());
        Assert.assertEquals("      ", Tab.SIX.value());
        Assert.assertEquals("       ", Tab.SEVEN.value());
        Assert.assertEquals("        ", Tab.EIGHT.value());
        Assert.assertEquals("         ", Tab.NINE.value());
        Assert.assertEquals("          ", Tab.TEN.value());
    }
}
