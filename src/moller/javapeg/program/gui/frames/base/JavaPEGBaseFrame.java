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
package moller.javapeg.program.gui.frames.base;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.gui.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Base class for GUI classes which are {@link JFrame} objects and which have a
 * persisted state for their size and location on the screen.
 *
 * @author Fredrik
 *
 */
public abstract class JavaPEGBaseFrame extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 8862923274612854190L;

    /**
     * Returns the {@link GUIWindow} configuration object which is to be used by
     * the concrete implementation of this class.
     *
     * @return
     */
    public abstract GUIWindow getGUIWindowConfig();

    /**
     * Returns the default size, width and height as an {@link Dimension} object
     * for the GUI of the concrete class instance.
     *
     * @return
     */
    public abstract Dimension getDefaultSize();

    private final Configuration configuration = Config.getInstance().get();
    private final Logger logger = Logger.getInstance();
    private final Language lang = Language.getInstance();

    /**
     * Sets the, from the configuration settings, loaded size and location or
     * the default location and size, if the configuration from the settings are
     * not part (outside) of the available coordinate system.
     */
    protected void loadAndApplyGUISettings() {
        GUIWindow guiWindowConfig = getGUIWindowConfig();
        Rectangle sizeAndLocation = guiWindowConfig.getSizeAndLocation();

        this.setSize(sizeAndLocation.getSize());

        Point xyFromConfig = new Point(sizeAndLocation.getLocation());

        if (Screen.isVisibleOnScreen(sizeAndLocation)) {
            this.setLocation(xyFromConfig);
            this.setSize(sizeAndLocation.getSize());
        } else {
            JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            logger.logERROR("Could not set location of " + getClass().getSimpleName() + " GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");

            this.setLocation(0,0);
            this.setSize(getDefaultSize());
        }

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            logger.logERROR("Could not set desired Look And Feel for " + getClass().getSimpleName() + " GUI");
            logger.logERROR(e);
        }
    }

    /**
     * Returns the {@link Language} object which is kept by this class
     *
     * @return
     */
    public Language getLang() {
        return lang;
    }

    /**
     * Returns the {@link Configuration} object which is kept by this class
     *
     * @return
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Returns the {@link Logger} object which is kept by this class
     *
     * @return
     */
    public Logger getLogger() {
        return logger;
    }

    protected void addListeners() {
        this.addWindowListener(new WindowDestroyer());
    }

    protected class WindowDestroyer extends WindowAdapter {

        @Override
        public void windowClosing (WindowEvent e) {
            disposeFrame();
        }
    }

    protected void disposeFrame() {
        saveSettings();
        setVisible(false);
        dispose();
    }

    /**
     * Stores the current size and location of the GUI into the configuration
     * object.
     */
    protected void saveSettings() {
        if (this.isVisible()) {
            Rectangle sizeAndLocation = getGUIWindowConfig().getSizeAndLocation();

            sizeAndLocation.setSize(this.getSize().width, this.getSize().height);
            sizeAndLocation.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);
        }
    }
}
