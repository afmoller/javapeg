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
 * This enumeration lists predefined tab positions
 *
 * Created by Fredrik on 2015-04-26.
 */
public enum TabPosition {

    TOP(5, "configviewer.userinterface.tabPosition.top"),
    BOTTOM(10, "configviewer.userinterface.tabPosition.bottom");

    private String localizationKey;

    private TabPosition(int dividerSizeInPixels, String localizationKey) {
        this.localizationKey = localizationKey;
    }

    public String getLocalizationKey() {
        return localizationKey;
    }
}
