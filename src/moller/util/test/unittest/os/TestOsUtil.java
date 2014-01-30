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
package moller.util.test.unittest.os;

import moller.util.os.OsUtil;

import org.junit.Test;

public class TestOsUtil {

	@Test
	public void testGetOsVersion() {
		System.out.println(OsUtil.getOsVersion());

	}

	@Test
	public void testGetOsArchitecture() {
		System.out.println(OsUtil.getOsArchitecture());
	}

	@Test
	public void testGetOsName() {
		System.out.println(OsUtil.getOsName());
	}

	@Test
	public void testGetuserhome() {
		System.out.println(System.getProperty("user.home"));
	}

}
