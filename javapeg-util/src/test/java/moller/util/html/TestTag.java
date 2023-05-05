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
package moller.util.html;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTag {

	private static final String LINEFEED = System.getProperty("line.separator");

	@Test
	public void testAttributes() {
		System.out.println(Tag.HTMLOpen.attributes("\"Content-Type\" content=\"text/html;charset=iso-8859-1\""));
		Assertions.assertEquals("<html \"Content-Type\" content=\"text/html;charset=iso-8859-1\">" + LINEFEED, Tag.HTMLOpen.attributes("\"Content-Type\" content=\"text/html;charset=iso-8859-1\""));
	}

	@Test
	public void testToString() {
		Assertions.assertEquals("<html>" + LINEFEED, Tag.HTMLOpen.toString());
	}

}
