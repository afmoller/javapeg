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

import moller.javapeg.program.enumerations.Level;

import java.text.SimpleDateFormat;

public class Logging {

    private Boolean developerMode;
    private Boolean rotate;
    private Boolean rotateZip;

    private Level level;
    private Long rotateSize;
    private String fileName;
    private SimpleDateFormat timeStampFormat;

    public Boolean getDeveloperMode() {
        return developerMode;
    }
    public Boolean getRotate() {
        return rotate;
    }
    public Boolean getRotateZip() {
        return rotateZip;
    }
    public Level getLevel() {
        return level;
    }
    public Long getRotateSize() {
        return rotateSize;
    }
    public String getFileName() {
        return fileName;
    }
    public SimpleDateFormat getTimeStampFormat() {
        return timeStampFormat;
    }
    public void setDeveloperMode(Boolean developerMode) {
        this.developerMode = developerMode;
    }
    public void setRotate(Boolean rotate) {
        this.rotate = rotate;
    }
    public void setRotateZip(Boolean rotateZip) {
        this.rotateZip = rotateZip;
    }
    public void setLevel(Level level) {
        this.level = level;
    }
    public void setRotateSize(Long rotateSize) {
        this.rotateSize = rotateSize;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void setTimeStampFormat(SimpleDateFormat timeStampFormat) {
        this.timeStampFormat = timeStampFormat;
    }
}
