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
package moller.javapeg.program.thumbnailoverview;

public class LayoutMetaItem {

    private String classString;
    private String labelString;
    private String metaString;

    public LayoutMetaItem(String classString, String labelString, String metaString) {
        super();
        this.classString = classString;
        this.labelString = labelString;
        this.metaString = metaString;
    }

    public String getClassString() {
        return classString;
    }

    public String getLabelString() {
        return labelString;
    }

    public String getMetaString() {
        return metaString;
    }

    public void setClassString(String classString) {
        this.classString = classString;
    }

    public void setLabelString(String labelString) {
        this.labelString = labelString;
    }

    public void setMetaString(String metaString) {
        this.metaString = metaString;
    }
}