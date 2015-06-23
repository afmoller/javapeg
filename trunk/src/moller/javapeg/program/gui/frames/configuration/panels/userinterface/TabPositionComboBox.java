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
package moller.javapeg.program.gui.frames.configuration.panels.userinterface;

import moller.javapeg.program.enumerations.TabPosition;
import moller.javapeg.program.enumerations.wrappers.TabPositionWrapper;
import moller.javapeg.program.language.Language;

import javax.swing.*;

/**
 * Created by Fredrik on 2015-05-23.
 */
public class TabPositionComboBox extends JComboBox<TabPositionWrapper> {

    public TabPositionComboBox(Language lang) {
        TabPositionWrapper top = new TabPositionWrapper(lang.get(TabPosition.TOP.getLocalizationKey()), TabPosition.TOP);
        TabPositionWrapper bottom = new TabPositionWrapper(lang.get(TabPosition.BOTTOM.getLocalizationKey()), TabPosition.BOTTOM);

        addItem(top);
        addItem(bottom);
    }

    public void setSelectedPosition(TabPosition tabPosition) {
        switch (tabPosition) {
            case TOP:
                setSelectedIndex(0);
                break;
            case BOTTOM:
                setSelectedIndex(1);
                break;
            default:
                setSelectedIndex(0);
                break;
        }
    }
}
