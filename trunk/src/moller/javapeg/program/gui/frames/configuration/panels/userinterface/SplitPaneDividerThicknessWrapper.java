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
package moller.javapeg.program.gui.frames.configuration.panels.userinterface;

import moller.javapeg.program.enumerations.SplitPaneDividerThickness;

/**
 * This class wraps the {@link SplitPaneDividerThickness} {@link Enum} with an
 * display string. This class is to be used as an object in a
 * {@link javax.swing.JComboBox} and the display string property is the
 * {@link String} which will be displayed in the GUI
 *
 * Created by Fredrik on 2015-05-13.
 */
public class SplitPaneDividerThicknessWrapper {

    private String displayString;
    private SplitPaneDividerThickness splitPaneDividerThickness;

    public SplitPaneDividerThicknessWrapper(String displayString, SplitPaneDividerThickness splitPaneDividerThickness) {
        this.displayString = displayString;
        this.splitPaneDividerThickness = splitPaneDividerThickness;
    }

    public SplitPaneDividerThickness getSplitPaneDividerThickness() {
        return splitPaneDividerThickness;
    }

    @Override
    public String toString() {
        return displayString;
    }
}
