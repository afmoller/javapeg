package moller.util.test.unittest.string;

import junit.framework.Assert;
import moller.util.string.Tab;

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
