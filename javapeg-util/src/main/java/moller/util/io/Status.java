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

import java.awt.*;

/**
 * This enumeration specifies the possible statuses an image repository can have:
 *
 *  CORRUPT:
 *  The content of an image repository file is not valid against the specified schema.
 *
 *  DOES_NOT_EXIST:
 *  The image repository references an repository file which does not exist.
 *
 *  EXISTS
 *  The image repository references an repository file which does exist and is
 *  "healthy", which means not corrupt and not inconsistent.
 *
 *  INCONSISTENT:
 *  The content on the path which the image repository file references is not
 *  the same as is referenced in the file: more, less or other entries.
 *
 *  NOT_AVAILABLE:
 *  The image repository references an repository file which at the moment is
 *  not available, it could be a repository which is stored on a network
 *  resource or an removable storage, such as an USB disk.
 *
 *  UNKNOWN:
 *  The status is not yet determined, which means that the status is being
 *  calculated.
 *
 *
 * @author Fredrik
 *
 */
public enum Status {
    CORRUPT("configviewer.tag.imageRepositories.label.corruptTooltip", "configviewer.tag.imageRepositories.label.corrupt", new Color(255,0,51)),
    DOES_NOT_EXIST("configviewer.tag.imageRepositories.label.doesNotExistTooltip", "configviewer.tag.imageRepositories.label.doesNotExist", new Color(255,127,127)),
    EXISTS("configviewer.tag.imageRepositories.label.existsTooltip", "configviewer.tag.imageRepositories.label.exists", new Color(127,255,127)),
    INCONSISTENT("configviewer.tag.imageRepositories.label.inconsistentTooltip", "configviewer.tag.imageRepositories.label.inconsistent", new Color(255,0,51)),
	NOT_AVAILABLE("configviewer.tag.imageRepositories.label.notAvailableTooltip", "configviewer.tag.imageRepositories.label.notAvailable", new Color(251,231,128)),
	UNKNOWN("configviewer.tag.imageRepositories.label.unknownTooltip", "configviewer.tag.imageRepositories.label.unknown", new Color(192,192,192));

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
