package moller.util.test.unittest.string;

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
}
