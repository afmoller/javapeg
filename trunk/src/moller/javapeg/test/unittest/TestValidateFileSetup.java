package moller.javapeg.test.unittest;

import java.io.File;

import junit.framework.Assert;
import moller.javapeg.program.C;
import moller.javapeg.program.applicationstart.ValidateFileSetup;

import org.junit.Test;

public class TestValidateFileSetup {

	@Test
	public void testCheck() {

		File javaPEGuserHome = new File(C.USER_HOME + C.FS + "javapeg-" + C.JAVAPEG_VERSION);

		File configFile   = new File(javaPEGuserHome, "config" + C.FS + "conf.xml");
		File logDirectory = new File(javaPEGuserHome, "logs");
		File layoutInfo   = new File(javaPEGuserHome, "resources" + C.FS + "thumb" + C.FS + "layout.info");
		File styleInfo    = new File(javaPEGuserHome, "resources" + C.FS + "thumb" + C.FS + "style.info");

		ValidateFileSetup.check();

		Assert.assertTrue(configFile.exists());
		Assert.assertTrue(logDirectory.exists());
		Assert.assertTrue(layoutInfo.exists());
		Assert.assertTrue(styleInfo.exists());
	}
}
