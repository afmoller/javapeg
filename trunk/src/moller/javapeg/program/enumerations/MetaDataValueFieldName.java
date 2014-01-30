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

public enum MetaDataValueFieldName {

    APERTURE_VALUE("APERTURE_VALUE"),
    CAMERA_MODEL("CAMERA_MODEL"),
    IMAGE_SIZE("IMAGE_SIZE"),
    ISO("ISO"),
    EXPOSURE_TIME("EXPOSURE_TIME"),
    YEAR("YEAR"),
    MONTH("MONTH"),
    DAY("DAY"),
    HOUR("HOUR"),
    MINUTE("MINUTE"),
    SECOND("SECOND");

    private String value;

    private MetaDataValueFieldName(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
