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

import java.net.URL;

public class UpdatesChecker {

    private Boolean enabled;
    private Boolean attachVersionInformation;
    private Integer timeOut;
    private URL urlVersion;
    private URL urlVersionInformation;

    public Boolean isEnabled() {
        return enabled;
    }
    public Boolean getAttachVersionInformation() {
        return attachVersionInformation;
    }
    public Integer getTimeOut() {
        return timeOut;
    }
    public URL getUrlVersion() {
        return urlVersion;
    }
    public URL getUrlVersionInformation() {
        return urlVersionInformation;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public void setAttachVersionInformation(Boolean attachVersionInformation) {
        this.attachVersionInformation = attachVersionInformation;
    }
    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }
    public void setUrlVersion(URL urlVersion) {
        this.urlVersion = urlVersion;
    }
    public void setUrlVersionInformation(URL urlVersionInformation) {
        this.urlVersionInformation = urlVersionInformation;
    }
}
