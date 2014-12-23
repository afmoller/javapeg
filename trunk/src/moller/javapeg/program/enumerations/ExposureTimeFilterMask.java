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

public enum ExposureTimeFilterMask implements IFilterMask {

    NO_MASK("1/n"),
    MASK_UP_TO_POSITON_FIRST("1/n0"),
    MASK_UP_TO_POSITON_SECOND("1/n00"),
    MASK_UP_TO_POSITON_THIRD("1/n000"),
    MASK_UP_TO_POSITON_FOURTH("1/n0000"),
    MASK_UP_TO_POSITON_FIFTH("1/n00000"),
    MASK_UP_TO_POSITON_SIXTH("1/n000000");

    private String mask;

    private ExposureTimeFilterMask(String mask) {
        this.mask = mask;
    }

    @Override
    public String getMask() {
        return mask;
    }

    @Override
    public String toString() {
        return mask;
    }
}
