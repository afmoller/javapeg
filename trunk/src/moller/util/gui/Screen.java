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
package moller.util.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Screen {

	/**
	 * This method returns all available screens (monitors) for the system.
	 *
	 * @return a list of Rectangle objects describing the existing screens
	 *         available for the system.
	 */
	public static List<Rectangle> getScreens() {

		List<Rectangle> screens = new ArrayList<Rectangle>();

		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

		for (GraphicsDevice graphicsDevice : graphicsEnvironment.getScreenDevices()) {
			GraphicsConfiguration gc = graphicsDevice.getDefaultConfiguration();
			screens.add(gc.getBounds());
		}
		return screens;
	}

	/**
	 * This method validates whether a coordinate is inside of the available
	 * screen coordinate system. This is mainly an important thing in multi
	 * monitor systems where it is possible that an GUI window might be opened,
	 * by an application, outside of the available screen coordinate system, if
	 * a monitor has been removed since last application session.
	 *
	 * @param coordinate is the upper left corner of the application GUI.
	 * @param screens are the, for the system, available screens (monitors).
	 *
	 * @return a boolean value indication whether the coordinate parameter is
	 *         inside any of the available screens.
	 */
	public static boolean isOnScreen(Point coordinate, List<Rectangle> screens) {

	    if (coordinate.x > -1 && coordinate.y > -1) {
	        for (Rectangle screen : screens) {
	            if (screen.width + screen.x >= coordinate.x && screen.height + screen.y >= coordinate.y) {
	                return true;
	            }
	        }
	    }
		return false;
	}

	/**
	 * This method validates whether a coordinate is inside of the available
	 * screen coordinate system. This is mainly an important thing in multi
	 * monitor systems where it is possible that an GUI window might be opened,
	 * by an application, outside of the available screen coordinate system, if
	 * a monitor has been removed since last application session.
	 *
	 * @param coordinate is the upper left corner of the application GUI.
	 *
	 * @return a boolean value indication whether the coordinate parameter is
	 *         inside any of the available screens.
	 */
	public static boolean isOnScreen(Point coordinate) {
	    return isOnScreen(coordinate, getScreens());
	}

	public static boolean isVisibleOnScreen(Rectangle sizeAndLocation) {
	    for (Rectangle screen : getScreens()) {
            if (sizeAndLocation.intersects(screen)) {
                return true;
            }
        }
	    return false;
	}

    /**
     * This method returns the upper left coordinate that gives an window with
     * the width and height given as arguments to this method a centered
     * position in the main monitor (if a multi monitor setup is used)
     *
     * @param width
     *            is the width of the window to be centered.
     * @param height
     *            is the height of the window to be centered.
     * @return an {@link Point} object which specifies the upper left coordinate
     *         that gives the window with the width and height values as those
     *         given to this method by the values of the width and height
     *         parameters a centered position.
     */
	public static Point getLeftUpperLocationForCenteredPosition(int width, int height) {
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	    int screenWidth = (int)screenSize.getWidth();
	    int screeHeight = (int)screenSize.getHeight();

	    Point location = new Point();
	    location.x = (screenWidth - width) / 2;
	    location.y = (screeHeight - height) / 2;

	    return location;

	}
}