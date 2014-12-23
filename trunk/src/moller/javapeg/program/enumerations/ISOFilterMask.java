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

public enum ISOFilterMask implements IFilterMask {

    NO_MASK("n"),
    MASK_UP_TO_POSITON_FIRST("n0"),
    MASK_UP_TO_POSITON_SECOND("n00"),
    MASK_UP_TO_POSITON_THIRD("n000"),
    MASK_UP_TO_POSITON_FOURTH("n0000"),
    MASK_UP_TO_POSITON_FIFTH("n00000"),
    MASK_UP_TO_POSITON_SIXTH("n000000");

    private String mask;

    private ISOFilterMask(String mask) {
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
