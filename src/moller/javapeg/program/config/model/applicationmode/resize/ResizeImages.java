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
package moller.javapeg.program.config.model.applicationmode.resize;

import java.io.File;

public class ResizeImages {

    private File pathDestination;
    private Integer width;
    private Integer height;
    private Integer selectedQualityIndex;

    public File getPathDestination() {
        return pathDestination;
    }
    public Integer getWidth() {
        return width;
    }
    public Integer getHeight() {
        return height;
    }
    public Integer getSelectedQualityIndex() {
        return selectedQualityIndex;
    }
    public void setPathDestination(File pathDestination) {
        this.pathDestination = pathDestination;
    }
    public void setWidth(Integer width) {
        this.width = width;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    public void setSelectedQualityIndex(Integer selectedQualityIndex) {
        this.selectedQualityIndex = selectedQualityIndex;
    }
}
