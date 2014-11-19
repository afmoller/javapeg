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
package moller.javapeg.program.model.iso;

import moller.javapeg.program.enumerations.ISOFilterMask;

public class CameraAndISOFilterPair {

    private String cameraModel;
    private ISOFilterMask iSOFilterMask;

    public String getCameraModel() {
        return cameraModel;
    }
    public ISOFilterMask getiSOFilter() {
        return iSOFilterMask;
    }
    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }
    public void setiSOFilter(ISOFilterMask isoFilterMask) {
        this.iSOFilterMask = isoFilterMask;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cameraModel == null) ? 0 : cameraModel.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CameraAndISOFilterPair other = (CameraAndISOFilterPair) obj;
           if (cameraModel == null) {
            if (other.cameraModel != null) {
                return false;
            }
        } else if (!cameraModel.equals(other.cameraModel)) {
            return false;
        }
        return true;
    }
}
