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

import moller.javapeg.program.enumerations.SplitPaneDividerSize;
import moller.javapeg.program.language.Language;

import javax.swing.*;

/**
 * Created by Fredrik on 2015-05-13.
 */
public class SplitPaneDividerThicknessComboBox extends JComboBox<SplitPaneDividerThicknessWrapper> {

    public SplitPaneDividerThicknessComboBox(Language lang) {
        SplitPaneDividerThicknessWrapper thin = new SplitPaneDividerThicknessWrapper(lang.get(SplitPaneDividerSize.THIN.getLocalizationKey()), SplitPaneDividerSize.THIN);
        SplitPaneDividerThicknessWrapper thick = new SplitPaneDividerThicknessWrapper(lang.get(SplitPaneDividerSize.THICK.getLocalizationKey()), SplitPaneDividerSize.THICK);

        addItem(thin);
        addItem(thick);
    }

    public void setSelectedThickness(SplitPaneDividerSize selectedThickness) {
        switch (selectedThickness) {
            case THIN:
                setSelectedIndex(0);
                break;
            case THICK:
                setSelectedIndex(1);
                break;
            default:
                setSelectedIndex(0);
                break;
        }
    }
}
