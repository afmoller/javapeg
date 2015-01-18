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
package moller.util.test.unittest.image;

import java.awt.Dimension;

import moller.util.image.ImageUtil;

import org.junit.Assert;
import org.junit.Test;

public class TestImageUtil {

	@Test
	public void testCalculateScaledImageWidthAndHeight() {
		Dimension dimension = ImageUtil.calculateScaledImageWidthAndHeight(400, 500, 800, 600);

		Assert.assertEquals(400, dimension.width);
		Assert.assertEquals(300, dimension.height);

		dimension = ImageUtil.calculateScaledImageWidthAndHeight(200, 200, 800, 600);

		Assert.assertEquals(200, dimension.width);
		Assert.assertEquals(150, dimension.height);

		dimension = ImageUtil.calculateScaledImageWidthAndHeight(200, 200, 600, 800);

		Assert.assertEquals(150, dimension.width);
		Assert.assertEquals(200, dimension.height);
	}
}
