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
package moller.javapeg.program.config.model.GUI;

import java.util.List;

public class GUIWindowSplitPaneUtil {

    public static Integer getGUIWindowSplitPaneDividerLocation(List<GUIWindowSplitPane> gUIWindowSplitPanes, String name)  {
        for (GUIWindowSplitPane guiWindowSplitPane : gUIWindowSplitPanes) {
            if (guiWindowSplitPane.getName().equals(name)) {
                return guiWindowSplitPane.getLocation();
            }
        }
        return 0;
    }

    public static Integer getGUIWindowSplitPaneDividerSize(List<GUIWindowSplitPane> gUIWindowSplitPanes, String name)  {
        for (GUIWindowSplitPane guiWindowSplitPane : gUIWindowSplitPanes) {
            if (guiWindowSplitPane.getName().equals(name)) {
                return guiWindowSplitPane.getWidth();
            }
        }
        return 10;
    }

    public static void setGUIWindowSplitPaneDividerLocation(List<GUIWindowSplitPane> gUIWindowSplitPanes, String name, int splitPaneLocation)  {
        for (GUIWindowSplitPane guiWindowSplitPane : gUIWindowSplitPanes) {
            if (guiWindowSplitPane.getName().equals(name)) {
                guiWindowSplitPane.setLocation(splitPaneLocation);
            }
        }
    }

    public static void setGUIWindowSplitPaneDividerWidth(List<GUIWindowSplitPane> gUIWindowSplitPanes, String name, int dividerSize) {
        for (GUIWindowSplitPane guiWindowSplitPane : gUIWindowSplitPanes) {
            if (guiWindowSplitPane.getName().equals(name)) {
                guiWindowSplitPane.setWidth(dividerSize);
            }
        }
    }
}
