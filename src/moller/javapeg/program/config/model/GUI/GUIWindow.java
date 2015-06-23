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

import moller.javapeg.program.config.model.GUI.splitpane.GUIWindowSplitPane;

import java.awt.*;
import java.util.List;

public class GUIWindow {

    private Rectangle sizeAndLocation;
    private List<GUIWindowSplitPane> guiWindowSplitPane;

    public Rectangle getSizeAndLocation() {
        return sizeAndLocation;
    }
    public List<GUIWindowSplitPane> getGuiWindowSplitPane() {
        return guiWindowSplitPane;
    }
    public void setSizeAndLocation(Rectangle sizeAndLocation) {
        this.sizeAndLocation = sizeAndLocation;
    }
    public void setGuiWindowSplitPane(List<GUIWindowSplitPane> guiWindowSplitPane) {
        this.guiWindowSplitPane = guiWindowSplitPane;
    }

    /**
     * This method returns the {@link GUIWindowSplitPane} with a name which
     * matches the name given in the name parameter or null if there is no
     * match.
     *
     * @param name
     * @return
     */
    public GUIWindowSplitPane getGUIWindowSplitPane(String name) {
        if (guiWindowSplitPane == null) {
            return null;
        }
        for (GUIWindowSplitPane windowSplitPane : guiWindowSplitPane) {
            if (windowSplitPane.getName().equals(name)) {
                return windowSplitPane;
            }
        }
        return null;
    }
}
