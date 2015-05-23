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
package moller.javapeg.program.enumerations.wrappers;

/**
 * Base class for the enum wrapper classes, classes which are intended to be
 * used in an Graphical component, where each enumeration also do have an
 * localized string value.
 *
 * Created by Fredrik on 2015-05-23.
 */
public class BaseEnumWrapper {

    // The field is protected due to be able to be accessed in subclass
    // constructors.
    protected String displayString;

    @Override
    public String toString() {
        return displayString;
    }
}
