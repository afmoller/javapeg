package moller.util.gui;
/**
 * This class was created : 2009-04-04 by Fredrik Möller
 * Latest changed         : 2009-04-05 by Fredrik Möller
 */

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
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
		
		for (Rectangle screen : screens) {
			if (screen.width + screen.x >= coordinate.x && screen.height + screen.y >= coordinate.y) {
				return true;
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
				
		for (Rectangle screen : getScreens()) {
			if (screen.width + screen.x >= coordinate.x && screen.height + screen.y >= coordinate.y) {
				return true;
			}
		}
		return false;
	}
}