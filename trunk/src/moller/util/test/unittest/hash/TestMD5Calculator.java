package moller.util.test.unittest.hash;

import java.io.File;

import moller.util.hash.MD5Calculator;

import org.junit.Assert;
import org.junit.Test;

public class TestMD5Calculator {

	private static final String FS = File.separator;
	private static final String PATH   = FS + "src" + FS + "moller" + FS + "util" + FS + "test"+ FS + "unittest" + FS + "hash" + FS + "input" + FS;

	@Test
	public void testCalculate() {

		String calculatedHashValue = MD5Calculator.calculate(new File(System.getProperty("user.dir") + PATH + "2003-09-01 191650.jpg"));
		Assert.assertEquals("70b696c04b633869e193c6a456de6797", calculatedHashValue);
	}
}
