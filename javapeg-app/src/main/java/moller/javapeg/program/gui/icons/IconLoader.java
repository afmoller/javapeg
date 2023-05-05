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
package moller.javapeg.program.gui.icons;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.logger.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Helper class which acts as an repository of all icon images which are used in
 * this application. It is responsible for loading, if not yet loaded, and the
 * delivery of an specific image. Any error handling is also handled by this
 * class.
 *
 * @author Fredrik
 *
 */
public class IconLoader {

    /**
     * A map which contains all loaded and created {@link ImageIcon} objects.
     */
    private static Map<Icons, ImageIcon> icons = new HashMap<Icons, ImageIcon>();

    /**
     * Gets the icon described by the parameter icon of type {@link Icons}.
     *
     * @param icon
     *            specifies which {@link ImageIcon} object to return.
     * @return
     */
    public static ImageIcon getIcon(Icons icon) {
        return getOrCreateAndGetIcon(icon);
    }

    private static ImageIcon getOrCreateAndGetIcon(Icons icon) {
        if (icons.containsKey(icon)) {
            return icons.get(icon);
        } else {
            ImageIcon imageIcon = new ImageIcon();
            try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream(icon.getResource())) {
                imageIcon.setImage(ImageIO.read(imageStream));
                icons.put(icon, imageIcon);
            } catch (IOException iox) {
                Logger logger = Logger.getInstance();
                logger.logERROR("The icon: " + icon + " could not be loaded, see stack trace for details");
                logger.logERROR(iox);
            }
            return imageIcon;
        }
    }
}
