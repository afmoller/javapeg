package moller.javapeg.program.applicationstart;

import moller.javapeg.program.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

class ValidateFileSetupTest {

    @Test
    public void testCheck() {

        File javaPEGuserHome = new File(C.JAVAPEG_HOME);

        File configFile   = new File(javaPEGuserHome, "config" + C.FS + "conf.xml");
        File logDirectory = new File(javaPEGuserHome, "logs");
        File layoutInfo   = new File(javaPEGuserHome, "resources" + C.FS + "thumb" + C.FS + "layout.info");
        File styleInfo    = new File(javaPEGuserHome, "resources" + C.FS + "thumb" + C.FS + "style.info");

        ValidateFileSetup.check();

        Assertions.assertTrue(configFile.exists());
        Assertions.assertTrue(logDirectory.exists());
        Assertions.assertTrue(layoutInfo.exists());
        Assertions.assertTrue(styleInfo.exists());
    }

}