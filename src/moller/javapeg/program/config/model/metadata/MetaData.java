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
package moller.javapeg.program.config.model.metadata;

import java.util.List;

import moller.javapeg.program.enumerations.ExposureTimeFilterMask;
import moller.javapeg.program.enumerations.ISOFilterMask;

public class MetaData {

    private List<MetaDataFilter<ISOFilterMask>> isoFilters;
    private List<MetaDataFilter<ExposureTimeFilterMask>> exposureTimeFilters;


    public List<MetaDataFilter<ISOFilterMask>> getIsoFilters() {
        return isoFilters;
    }

    public void setIsoFilters(List<MetaDataFilter<ISOFilterMask>> isoFilters) {
        this.isoFilters = isoFilters;
    }

    public List<MetaDataFilter<ExposureTimeFilterMask>> getExposureTimeFilters() {
        return exposureTimeFilters;
    }

    public void setExposureTimeFilters(List<MetaDataFilter<ExposureTimeFilterMask>> exposureTimeFilters) {
        this.exposureTimeFilters = exposureTimeFilters;
    }
}
