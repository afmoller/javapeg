package moller.javapeg.program.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageUtilTest {

    @Test
    public void testCalculateScaledImageWidthAndHeight() {
        Dimension dimension = ImageUtil.calculateScaledImageWidthAndHeight(400, 500, 800, 600);

        Assertions.assertEquals(400, dimension.width);
        Assertions.assertEquals(300, dimension.height);

        dimension = ImageUtil.calculateScaledImageWidthAndHeight(200, 200, 800, 600);

        Assertions.assertEquals(200, dimension.width);
        Assertions.assertEquals(150, dimension.height);

        dimension = ImageUtil.calculateScaledImageWidthAndHeight(200, 200, 600, 800);

        Assertions.assertEquals(150, dimension.width);
        Assertions.assertEquals(200, dimension.height);
    }

}