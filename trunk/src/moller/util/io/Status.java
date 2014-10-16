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
package moller.util.io;

import java.awt.Color;

public enum Status {
    CORRUPT("configviewer.tag.imageRepositories.label.corruptTooltip", "configviewer.tag.imageRepositories.label.corrupt", new Color(255,0,51)),
    DOES_NOT_EXIST("configviewer.tag.imageRepositories.label.doesNotExistTooltip", "configviewer.tag.imageRepositories.label.doesNotExist", new Color(255,127,127)),
    EXISTS("configviewer.tag.imageRepositories.label.existsTooltip", "configviewer.tag.imageRepositories.label.exists", new Color(127,255,127)),
    INCONSISTENT("configviewer.tag.imageRepositories.label.inconsistentTooltip", "configviewer.tag.imageRepositories.label.inconsistent", new Color(255,0,51)),
	NOT_AVAILABLE("configviewer.tag.imageRepositories.label.notAvailableTooltip", "configviewer.tag.imageRepositories.label.notAvailable", new Color(251,231,128));

	private String toolTipTextKey;
    private String textKey;
	private Color bakgroundColor;

	Status(String toolTipTextKey, String textKey, Color bakgroundColor) {
	    this.toolTipTextKey = toolTipTextKey;
	    this.textKey = textKey;
	    this.bakgroundColor = bakgroundColor;
	}

	public String getToolTipTextKey() {
        return toolTipTextKey;
    }

    public String getTextKey() {
        return textKey;
    }

    public Color getBakgroundColor() {
        return bakgroundColor;
    }
}
