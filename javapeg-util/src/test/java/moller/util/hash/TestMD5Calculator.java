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
package moller.util.hash;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class TestMD5Calculator {

	private static final String FS = File.separator;
	private static final String PATH  = "src" + FS + "test" + FS + "resources" + FS + "hash" + FS + "input" + FS;

	@Test
	public void testCalculate() {

		String calculatedHashValue = MD5Calculator.calculate(new File( PATH + "2003-09-01 191650.jpg"));
		Assertions.assertEquals("70b696c04b633869e193c6a456de6797", calculatedHashValue);
	}
}
