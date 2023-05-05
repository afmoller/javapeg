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
package moller.javapeg.program.config.model;

public class ToolTips {

    private String overviewState;
    private String imageSearchResultState;
    private String overviewImageViewerState;

    public String getOverviewState() {
        return overviewState;
    }

    public void setOverviewState(String overviewState) {
        this.overviewState = overviewState;
    }

    public String getImageSearchResultState() {
        return imageSearchResultState;
    }

    public void setImageSearchResultState(String imageSearchResultState) {
        this.imageSearchResultState = imageSearchResultState;
    }

    public String getOverviewImageViewerState() {
        return overviewImageViewerState;
    }

    public void setOverviewImageViewerState(String overviewImageViewerState) {
        this.overviewImageViewerState = overviewImageViewerState;
    }
}
