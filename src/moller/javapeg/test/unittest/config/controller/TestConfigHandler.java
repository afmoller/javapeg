package moller.javapeg.test.unittest.config.controller;

import java.io.File;

import moller.javapeg.program.C;
import moller.javapeg.program.config.controller.ConfigHandler;
import moller.util.java.SystemProperties;

import org.junit.Test;

public class TestConfigHandler {

    @Test
    public void testLoad() {

        String PATH_TO_TEST_CONF_FILE = SystemProperties.getUserDir() + C.FS + "src" + C.FS + "moller" + C.FS + "javapeg" + C.FS + "test" + C.FS + "unittest" + C.FS + "resources" + C.FS + "testconf.xml";

        ConfigHandler.load(new File(PATH_TO_TEST_CONF_FILE));

    }

}
