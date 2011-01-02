package moller.util.test.unittest.image;

import java.awt.Dimension;

import junit.framework.Assert;
import moller.util.image.ImageUtil;
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
