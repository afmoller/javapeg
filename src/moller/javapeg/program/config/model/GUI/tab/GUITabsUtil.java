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
package moller.javapeg.program.config.model.GUI.tab;

import java.util.List;

/**
 * This class has utility methods which are working with the {@link GUITab} class.
 * Created by Fredrik on 2015-06-23.
 */
public class GUITabsUtil {

    /**
     * Returns the {@link GUITab} which have the id which is specified by the parameter id
     *
     * @param guiTabs is the total list of {@link GUITab} objects.
     * @param id      specifies the id to search for.
     * @return
     */
    public static GUITab getGUITab(List<GUITab> guiTabs, String id) {
        for (GUITab guiTab : guiTabs) {
            if (guiTab.getId().equals(id)) {
                return guiTab;
            }
        }
        throw new IllegalArgumentException("Unexpected id value: " + id);
    }
}
