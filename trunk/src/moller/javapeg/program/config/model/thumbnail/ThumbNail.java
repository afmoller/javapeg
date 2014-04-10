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
package moller.javapeg.program.config.model.thumbnail;

public class ThumbNail {

    private ThumbNailCache cache;
    private ThumbNailCreation creation;
    private ThumbNailGrayFilter grayFilter;

    public ThumbNailCache getCache() {
        return cache;
    }
    public ThumbNailCreation getCreation() {
        return creation;
    }
    public ThumbNailGrayFilter getGrayFilter() {
        return grayFilter;
    }
    public void setCache(ThumbNailCache cache) {
        this.cache = cache;
    }
    public void setCreation(ThumbNailCreation creation) {
        this.creation = creation;
    }
    public void setGrayFilter(ThumbNailGrayFilter grayFilter) {
        this.grayFilter = grayFilter;
    }
}
