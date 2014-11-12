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
package moller.javapeg.program.gui.frames.configuration.panels;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.border.EtchedBorder;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.javapeg.program.gui.frames.configuration.panels.base.BaseConfigurationPanel;

public class MetadataConfigurationPanel extends BaseConfigurationPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MetadataConfigurationPanel() {
        setLayout(new GridBagLayout());

        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), BorderFactory.createTitledBorder(getLang().get("configviewer.tree.node.rename"))));

        GBHelper posPanel = new GBHelper();

        RenameImages renameImages = getConfiguration().getRenameImages();

        DefaultComboBoxModel<String> isoPatternModel = new DefaultComboBoxModel<String>();
        isoPatternModel.addElement("*");
        isoPatternModel.addElement("*0");
        isoPatternModel.addElement("*00");
        isoPatternModel.addElement("*000");

        JComboBox<String> isoPatterns = new JComboBox<String>(isoPatternModel);

        add(isoPatterns, posPanel);

    }

    @Override
    public boolean isValidConfiguration() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected void addListeners() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void createPanel() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getChangedConfigurationMessage() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateConfiguration() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void setStartUpConfig() {
        // TODO Auto-generated method stub

    }

}
