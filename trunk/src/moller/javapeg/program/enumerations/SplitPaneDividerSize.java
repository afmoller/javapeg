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
 * This enumeration lists predefined SplitPane divider sizes and har the
 * ability to return the size in pixels.
 *
 * Created by Fredrik on 2015-05-13.
 */
public enum SplitPaneDividerSize {
    THIN(5, "configviewer.userinterface.splitpanedividerthickness.thin"),
    THICK(10, "configviewer.userinterface.splitpanedividerthickness.thick");

    private int dividerSizeInPixels;
    private String localizationKey;

    private SplitPaneDividerSize(int dividerSizeInPixels, String localizationKey) {
        this.dividerSizeInPixels = dividerSizeInPixels;
        this.localizationKey = localizationKey;
    }

    public int getDividerSizeInPixels() {
        return dividerSizeInPixels;
    }

    public String getLocalizationKey() {
        return localizationKey;
    }
}
