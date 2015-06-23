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
package moller.javapeg.program.config.model.GUI.splitpane;

import moller.javapeg.program.enumerations.SplitPaneDividerSize;

public class GUIWindowSplitPane {

    private Integer location;
    private SplitPaneDividerSize dividerSize;
    private String name;

    public Integer getLocation() {
        return location;
    }
    public SplitPaneDividerSize getDividerSize() {
        return dividerSize;
    }
    public String getName() {
        return name;
    }
    public void setLocation(Integer location) {
        this.location = location;
    }
    public void setDividerSize(SplitPaneDividerSize dividerSize) {
        this.dividerSize = dividerSize;
    }
    public void setId(String name) {
        this.name = name;
    }
}
