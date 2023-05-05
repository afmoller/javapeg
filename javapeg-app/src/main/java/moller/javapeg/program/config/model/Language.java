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

public class Language {

    private Boolean automaticSelection;
    private String gUILanguageISO6391;

    public Boolean getAutomaticSelection() {
        return automaticSelection;
    }
    public String getgUILanguageISO6391() {
        return gUILanguageISO6391;
    }
    public void setAutomaticSelection(Boolean automaticSelection) {
        this.automaticSelection = automaticSelection;
    }
    public void setgUILanguageISO6391(String gUILanguageISO6391) {
        this.gUILanguageISO6391 = gUILanguageISO6391;
    }
}
