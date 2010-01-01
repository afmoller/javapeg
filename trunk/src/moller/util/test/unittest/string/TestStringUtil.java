package moller.util.test.unittest.string;
/**
 * This class was created : 2009-12-28 by Fredrik Möller
 * Latest changed         : 
 */

import junit.framework.Assert;
import moller.util.string.StringUtil;

import org.junit.Test;

public class TestStringUtil {

	@Test
	public void testFormatBytes() {
//		fail("Not yet implemented");
	}

	@Test
	public void testFormatString() {
		Assert.assertEquals("TestTestTest" + System.getProperty("line.separator"), StringUtil.formatString("Test\nTest\rTest<LF>"));		
	}
	
	@Test
	public void testReverse() {
		Assert.assertEquals("tset", StringUtil.reverse("test"));		
	}
}
