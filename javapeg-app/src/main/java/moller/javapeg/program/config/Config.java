/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program.config;

import moller.javapeg.program.C;
import moller.javapeg.program.config.controller.ConfigHandler;
import moller.javapeg.program.config.model.Configuration;

import java.io.File;

public class Config {

    /**
     * The static singleton instance of this class.
     */
    private static Config instance;

    public final static File PATH_TO_CONF_FILE =  new File(C.PATH_TO_CONFIGURATION_FILE);

    private Configuration configuration;

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

    public void set(Configuration configuration) {
        this.configuration = configuration;
    }

    public void save() {
        ConfigHandler.store(configuration, PATH_TO_CONF_FILE);
    }
}
