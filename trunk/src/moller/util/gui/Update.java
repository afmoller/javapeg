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

import javax.swing.*;
import java.awt.*;

public class Update{

	/**
		 * Method to attempt a dynamic update for any GUI accessible by this JVM. It will
		 * filter through all frames and sub-components of the frames.
		 */
		public static void updateAllUIs() {
			Frame frames[];
			frames = Frame.getFrames();

			for (int i = 0; i < frames.length; i++) {
				updateWindowUI(frames[i]);
			}
		}

		/**
		 * Method to attempt a dynamic update for all components of the given <code>Window</code>.
		 * @param window The <code>Window</code> for which the look and feel update has to be performed against.
		 */
		public static void updateWindowUI(Window window){
			try	{
				updateComponentTreeUI(window);
			}
			catch(Exception exception) { }

			Window windows[] = window.getOwnedWindows();

			for (int i = 0; i < windows.length; i++)
				updateWindowUI(windows[i]);
		}

		/**
		 * A simple minded look and feel change: ask each node in the tree
		 * to <code>updateUI()</code> -- that is, to initialize its UI property
		 * with the current look and feel.
		 *
		 * Based on the Sun SwingUtilities.updateComponentTreeUI, but ensures that
		 * the update happens on the components of a JToolbar before the JToolbar
		 * itself.
		 */
		public static void updateComponentTreeUI(Component c) {
			updateComponentTreeUI0(c);
			c.invalidate();
			c.validate();
			c.repaint();
		}

		private static void updateComponentTreeUI0(Component c) {

			Component[] children = null;

			if (c instanceof JToolBar) {
				children = ((JToolBar)c).getComponents();

				if (children != null) {
					for(int i = 0; i < children.length; i++) {
						updateComponentTreeUI0(children[i]);
					}
				}
				((JComponent) c).updateUI();
			}
			else {
				if (c instanceof JComponent) {
					((JComponent) c).updateUI();
				}

				if (c instanceof JMenu) {
					children = ((JMenu)c).getMenuComponents();
				}
				else if (c instanceof Container) {
					children = ((Container)c).getComponents();
				}

				if (children != null) {
					for(int i = 0; i < children.length; i++) {
						updateComponentTreeUI0(children[i]);
					}
				}
			}
		}
	}