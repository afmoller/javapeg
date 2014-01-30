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
package moller.javapeg.program.enumerations;

/**
 * This enumeration is used to set a name to the components in the tabs in the
 * main GUI window and to be able to know which tab that is currently selected
 * by asking the component about it´s name.
 *
 * @author Fredrik
 *
 */
public enum MainTabbedPaneComponent {

    MERGE("MERGE"),
    RENAME("RENAME"),
    VIEW("VIEW"),
    CATEGORIZE("CATEGORIZE");

    private String value;

    private MainTabbedPaneComponent(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
