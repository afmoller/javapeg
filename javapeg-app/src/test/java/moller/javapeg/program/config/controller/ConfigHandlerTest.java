package moller.javapeg.program.config.controller;

import moller.javapeg.program.C;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.util.io.FileUtil;
import moller.util.java.SystemProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

class ConfigHandlerTest {

    private static final String BASE_PATH  = "src" + C.FS + "test" + C.FS + "resources" + C.FS;
    private static String PATH_TO_TEST_CONF_FILE =  BASE_PATH + "testconf.xml";
    private static String PATH_TO_TEST_WRITE_CONF_FILE = BASE_PATH + "writetestconf.xml";

    @Test
    public void testLoad() throws IOException {

//        Configuration config = ConfigHandler.load(new File(PATH_TO_TEST_CONF_FILE));
//
//        ApplicationContext.getInstance().setHighestUsedCategoryID(10);
//
//        ConfigHandler.store(config, new File(PATH_TO_TEST_WRITE_CONF_FILE));
//
//        List<String> expected = FileUtil.readFromFile(new File(PATH_TO_TEST_CONF_FILE));
//        List<String> actual = FileUtil.readFromFile(new File(PATH_TO_TEST_WRITE_CONF_FILE));
//
//        Assertions.assertTrue(isEqual(expected, actual));

    }


    private boolean isEqual(List<String> a, List<String> b) {
        if (a.size() != b.size()) {
            return false;
        }

        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i))) {
                return false;
            }
        }

        return true;
    }

}