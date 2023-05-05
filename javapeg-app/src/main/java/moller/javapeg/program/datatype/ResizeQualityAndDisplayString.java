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
package moller.javapeg.program.datatype;

import org.imgscalr.Scalr.Method;

import javax.swing.*;

/**
 * This class maps a {@link Method} object with a {@link String}. Typical use
 * case is to add this kind of object to an {@link ComboBoxModel} and the add
 * that {@link ComboBoxModel} to a {@link JComboBox}. In the {@link JComboBox}
 * will the property {@link #displayString} be displayed.
 *
 * @author Fredrik
 *
 */
public class ResizeQualityAndDisplayString {

    private String displayString;
    private Method method;

    public ResizeQualityAndDisplayString(String displayString, Method method) {
        super();
        this.displayString = displayString;
        this.method = method;
    }
    public String getDisplayString() {
        return displayString;
    }
    public void setDisplayString(String displayString) {
        this.displayString = displayString;
    }
    public Method getMethod() {
        return method;
    }
    public void setMethod(Method method) {
        this.method = method;
    }
    @Override
    public String toString() {
        return displayString;
    }
}
