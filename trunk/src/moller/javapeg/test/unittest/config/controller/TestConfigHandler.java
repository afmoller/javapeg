package moller.javapeg.test.unittest.config.controller;

import java.io.File;

import moller.javapeg.program.C;
import moller.javapeg.program.config.controller.ConfigHandler;
import moller.javapeg.program.config.model.Config;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.util.java.SystemProperties;

import org.junit.Test;

public class TestConfigHandler {

    private static String BASE_PATH = SystemProperties.getUserDir() + C.FS + "src" + C.FS + "moller" + C.FS + "javapeg" + C.FS + "test" + C.FS + "unittest" + C.FS + "resources" + C.FS;

    private static String PATH_TO_TEST_CONF_FILE =  BASE_PATH + "testconf.xml";
    private static String PATH_TO_TEST_WRITE_CONF_FILE = BASE_PATH + "writetestconf.xml";

    @Test
    public void testLoad() {

        Config config = ConfigHandler.load(new File(PATH_TO_TEST_CONF_FILE));

        ApplicationContext.getInstance().setHighestUsedCategoryID(10);

        ConfigHandler.store(config, new File(PATH_TO_TEST_WRITE_CONF_FILE));

    }

}
