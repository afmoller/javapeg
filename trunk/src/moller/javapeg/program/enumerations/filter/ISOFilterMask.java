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
package moller.javapeg.program.enumerations.filter;

public enum ISOFilterMask implements IFilterMask {

    NO_MASK(                  "n",       1),
    MASK_UP_TO_POSITON_FIRST( "n0",      10),
    MASK_UP_TO_POSITON_SECOND("n00",     100),
    MASK_UP_TO_POSITON_THIRD( "n000",    1000),
    MASK_UP_TO_POSITON_FOURTH("n0000",   10000),
    MASK_UP_TO_POSITON_FIFTH( "n00000",  100000),
    MASK_UP_TO_POSITON_SIXTH( "n000000", 1000000);

    private String mask;
    private int triggerValue;

    private ISOFilterMask(String mask, int triggerValue) {
        this.mask = mask;
        this.triggerValue = triggerValue;
    }

    @Override
    public String getMask() {
        return mask;
    }

    @Override
    public String toString() {
        return mask;
    }

    @Override
    public int getTriggerValue() {
        return triggerValue;
    }

}
