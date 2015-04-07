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
package moller.javapeg.program.gui;

import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.javapeg.program.language.Language;
import moller.util.io.Status;

import javax.swing.*;
import java.awt.*;

/**
 * This Rendered class is used in the Configurations GUI and specifically by the
 * {@link JList} that contains all the configured {@link ImageRepositoryItem}
 * objects.
 *
 * @author Fredrik
 *
 */
public class CustomCellRenderer    extends    JLabel implements ListCellRenderer<Object> {

    private static final long serialVersionUID = 1L;

    public CustomCellRenderer() {
        this.setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Object> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {

        Status directoryStatus = ((ImageRepositoryItem)value).getPathStatus();
        String directoryPathValue = ((ImageRepositoryItem)value).getPath().getAbsolutePath();

        this.setText(directoryPathValue);

        Language lang = Language.getInstance();

        switch (directoryStatus) {
        case DOES_NOT_EXIST:
            this.setBackground(new Color(255,127,127));
            this.setToolTipText(lang.get("category.categoriesModel.repositoryNotExists"));
            break;
        case EXISTS:
            this.setBackground(new Color(127,255,127));
            this.setToolTipText(lang.get("category.categoriesModel.repositoryExists"));
            break;
        case NOT_AVAILABLE:
            this.setBackground(new Color(251,231,128));
            this.setToolTipText(lang.get("category.categoriesModel.repositoryNotAvailable"));
            break;
        case INCONSISTENT:
            this.setBackground(new Color(255,0,51));
            this.setToolTipText(lang.get("category.categoriesModel.repositoryInconsistent"));
            break;
        case CORRUPT:
            this.setBackground(new Color(255,0,51));
            this.setToolTipText(lang.get("category.categoriesModel.repositoryCorrupt"));
            break;

        default:
            break;
        }

        if(isSelected) {
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.setBackground(new Color(127,127,127));
        }
        return this;
    }
}
