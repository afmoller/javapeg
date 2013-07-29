package moller.javapeg.program.config;

import java.io.File;

import moller.javapeg.program.C;
import moller.javapeg.program.config.controller.ConfigHandler;
import moller.javapeg.program.config.model.Configuration;

public class Config {

    /**
     * The static singleton instance of this class.
     */
    private static Config instance;

    public final static File PATH_TO_CONF_FILE =  new File(C.PATH_TO_CONFIGURATION_FILE);

    private final Configuration configuration;

    /**
     * Private constructor.
     */
    private Config() {
        configuration = ConfigHandler.load(PATH_TO_CONF_FILE);
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static Config getInstance() {
        if (instance != null)
            return instance;
        synchronized (Config.class) {
            if (instance == null) {
                instance = new Config();
            }
            return instance;
        }
    }

    public Configuration get() {
        return configuration;
    }

    public void save() {
        ConfigHandler.store(configuration, PATH_TO_CONF_FILE);
    }
}
