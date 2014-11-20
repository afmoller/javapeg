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

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import moller.javapeg.StartJavaPEG;

public class IconLoader {

    private static ImageIcon addIcon = null;
    private static ImageIcon removeIcon = null;


    public static ImageIcon getAddIcon() {
        if (addIcon == null) {
            addIcon = loadIcon(Icons.ADDICON);
        }
        return addIcon;
    }

    public static ImageIcon getRemoveIcon() {
        if (removeIcon  == null) {
            removeIcon = loadIcon(Icons.REMOVEICON);
        }
        return removeIcon;
    }

    private static ImageIcon loadIcon(Icons icon) {
        ImageIcon imageIcon = new ImageIcon();
        try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream(icon.getResource())) {
            imageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException e) {

        }
        return imageIcon;
    }

}
