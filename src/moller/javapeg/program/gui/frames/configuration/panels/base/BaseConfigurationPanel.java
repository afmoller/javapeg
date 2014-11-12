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
package moller.javapeg.program.gui.frames.configuration.panels.base;

import javax.swing.JPanel;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.language.Language;

public abstract class BaseConfigurationPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final Configuration configuration;
    private final Language language;

    public BaseConfigurationPanel() {
        configuration = Config.getInstance().get();
        language = Language.getInstance();

        createPanel();
        setStartUpConfig();
        addListeners();
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Language getLang() {
        return language;
    }

    /**
     * Returns whether the configuration is valid or not. If valid, then
     * <code>true</code> is returned, otherwise <code>false</code>.
     *
     * @return
     */
    public abstract boolean isValidConfiguration();

    /**
     * This method is responsible for adding listeners to those graphical
     * components which has a need of reacting of input.
     */
    protected abstract void addListeners();

    /**
     * This method is responsible for creating the graphical content and loading
     * the initial configuration state.
     */
    protected abstract void createPanel();

    /**
     * This method returns a string which contains information about which
     * properties that has a changed value, if any, compared with was the value
     * was when the configuration was loaded.
     *
     * @return
     */
    public abstract String getChangedConfigurationMessage();

    /**
     * This method is responsible of exporting any change of the configuration,
     * made on the graphical elements into the {@link Configuration} data model.
     */
    public abstract void updateConfiguration();

    /**
     * This method is responsible of remembering the initial configuration
     * state.
     */
    protected abstract void setStartUpConfig();
}
