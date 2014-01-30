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
package moller.javapeg.program.config.model.applicationmode.tag;

public class TagImagesCategories {

    private Boolean warnWhenRemove;
    private Boolean warnWhenRemoveWithSubCategories;
    private Boolean orRadioButtonIsSelected;

    public Boolean getWarnWhenRemove() {
        return warnWhenRemove;
    }
    public Boolean getWarnWhenRemoveWithSubCategories() {
        return warnWhenRemoveWithSubCategories;
    }
    public Boolean getOrRadioButtonIsSelected() {
        return orRadioButtonIsSelected;
    }
    public void setWarnWhenRemove(Boolean warnWhenRemove) {
        this.warnWhenRemove = warnWhenRemove;
    }
    public void setWarnWhenRemoveWithSubCategories(
            Boolean warnWhenRemoveWithSubCategories) {
        this.warnWhenRemoveWithSubCategories = warnWhenRemoveWithSubCategories;
    }
    public void setOrRadioButtonIsSelected(Boolean orRadioButtonIsSelected) {
        this.orRadioButtonIsSelected = orRadioButtonIsSelected;
    }
}
