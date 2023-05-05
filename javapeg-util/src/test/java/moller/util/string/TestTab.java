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
package moller.util.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTab {

    @Test
    public void testValue() {
        Assertions.assertEquals(" ", Tab.ONE.value());
        Assertions.assertEquals("  ", Tab.TWO.value());
        Assertions.assertEquals("   ", Tab.THREE.value());
        Assertions.assertEquals("    ", Tab.FOUR.value());
        Assertions.assertEquals("     ", Tab.FIVE.value());
        Assertions.assertEquals("      ", Tab.SIX.value());
        Assertions.assertEquals("       ", Tab.SEVEN.value());
        Assertions.assertEquals("        ", Tab.EIGHT.value());
        Assertions.assertEquals("         ", Tab.NINE.value());
        Assertions.assertEquals("          ", Tab.TEN.value());
    }
}
