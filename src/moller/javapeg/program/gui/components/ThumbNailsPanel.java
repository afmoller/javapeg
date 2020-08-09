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
package moller.javapeg.program.gui.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThumbNailsPanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = -2453472350357718200L;

    public ThumbNailsPanel(GridLayout thumbNailGridLayout) {
        super(thumbNailGridLayout);
    }

    public List<JToggleButton> getJToggleButtons() {
        List<JToggleButton> jToggleButtons = new ArrayList<>();

        Component[] components = getComponents();

        for (Component component : components) {
            if (component instanceof JToggleButton) {
                jToggleButtons.add((JToggleButton)component);
            }
        }
        return jToggleButtons;
    }

}
