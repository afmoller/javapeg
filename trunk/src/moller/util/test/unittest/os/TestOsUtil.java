package moller.util.test.unittest.os;

import static org.junit.Assert.*;

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
