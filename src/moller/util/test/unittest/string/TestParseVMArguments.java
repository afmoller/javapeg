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

import junit.framework.Assert;
import moller.util.string.ParseVMArguments;

import org.junit.Test;

public class TestParseVMArguments {

    @Test
    public void testParseXmxToInt() {
        Assert.assertEquals(402653184, ParseVMArguments.parseXmxToLong("-Xmx384m"));
        Assert.assertEquals(402653184, ParseVMArguments.parseXmxToLong("Xmx384m"));
        Assert.assertEquals(16777216, ParseVMArguments.parseXmxToLong("Xmx16m"));

        Assert.assertEquals(402653184, ParseVMArguments.parseXmxToLong("-Xmx384M"));
        Assert.assertEquals(402653184, ParseVMArguments.parseXmxToLong("Xmx384M"));
        Assert.assertEquals(16777216, ParseVMArguments.parseXmxToLong("Xmx16M"));

        Assert.assertEquals(393216, ParseVMArguments.parseXmxToLong("Xmx384k"));
        Assert.assertEquals(393216, ParseVMArguments.parseXmxToLong("Xmx384k"));
        Assert.assertEquals(16384, ParseVMArguments.parseXmxToLong("Xmx16k"));

        Assert.assertEquals(393216, ParseVMArguments.parseXmxToLong("Xmx384K"));
        Assert.assertEquals(393216, ParseVMArguments.parseXmxToLong("Xmx384K"));
        Assert.assertEquals(16384, ParseVMArguments.parseXmxToLong("Xmx16K"));
    }
}
