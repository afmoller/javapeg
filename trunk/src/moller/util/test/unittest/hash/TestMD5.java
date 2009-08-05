package moller.util.test.unittest.hash;
/**
 * This class was created : 2009-04-05 by Fredrik Möller
 * Latest changed         : 
 */

import java.io.File;

import moller.util.hash.MD5;

import org.junit.Test;
import org.junit.Assert;


public class TestMD5 {

	private static final String FS = File.separator;
	private static final String PATH   = FS + "src" + FS + "moller" + FS + "util" + FS + "test"+ FS + "unittest" + FS + "hash" + FS + "input" + FS;
		
	@Test
	public void testCalculate() {
		
		
		String calculatedHashValue = MD5.calculate(new File(System.getProperty("user.dir") + PATH + "2003-09-01 191650.jpg"));
		Assert.assertEquals("70b696c04b633869e193c6a456de6797", calculatedHashValue);
		
//		Assert.assertEquals("098f6bcd4621d373cade4e832627b4f6", md5.calculate("test"));
		
	}
}
