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
package moller.util.color;

import java.awt.Color;

/**
 * @author Fredrik
 *
 */
public class ColorUtil {

    /**
     * Converts a {@link Color} object into a white space separated string of
     * red green and blue integer values.
     *
     * @param color
     *            is the {@link Color} object to transform into a string.
     * @return a string in the form
     *         "<RED_INTEGER_VALUE> <GREEN_INTEGER_VALUE> <BLUE_INTEGER_VALUE>".
     *         The values are ranging from 0 to 255.
     */
    public static String getColorAsRGBString(Color color) {
        StringBuilder builder = new StringBuilder();
        builder.append(color.getRed());
        builder.append(" ");
        builder.append(color.getGreen());
        builder.append(" ");
        builder.append(color.getBlue());

        return builder.toString();
    }

    /**
     * Converts a string consisting of three integer values, separated with
     * white spaces, with values ranging from 0 to 255 into an {@link Color}
     * object. The order is: Red, Green and Blue
     *
     * @param rgbString
     *            is the {@link String} which will be converted into an
     *            {@link Color} object.
     * @return a {@link Color}
     */
    public static Color getColorFromRGBString(String rgbString) {
        String[] rgbArray = rgbString.split(" ");

        return new Color(Integer.parseInt(rgbArray[0]), Integer.parseInt(rgbArray[1]), Integer.parseInt(rgbArray[2]));
    }
}
