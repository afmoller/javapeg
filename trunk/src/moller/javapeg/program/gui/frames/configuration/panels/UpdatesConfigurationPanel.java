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
import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.model.UpdatesChecker;
import moller.javapeg.program.gui.frames.configuration.panels.base.BaseConfigurationPanel;

public class UpdatesConfigurationPanel extends BaseConfigurationPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private JCheckBox updatesEnabled;
    private JCheckBox sendVersionInformationEnabled;

    private boolean UPDATE_CHECK_ENABLED;
    private boolean UPDATE_CHECK_ATTACH_VERSION;

    @Override
    public boolean isValidConfiguration() {
        return true;
    }

    @Override
    protected void addListeners() {
        updatesEnabled.addChangeListener(new UpdatesEnabledCheckBoxListener());
    }

    @Override
    protected void createPanel() {
        UpdatesChecker updatesChecker = getConfiguration().getUpdatesChecker();

        updatesEnabled = new JCheckBox(getLang().get("configviewer.update.label.updateEnabled.text"));
        updatesEnabled.setSelected(updatesChecker.isEnabled());

        sendVersionInformationEnabled = new JCheckBox(getLang().get("configviewer.update.label.attachVersionInformation.text"));
        sendVersionInformationEnabled.setSelected(updatesChecker.getAttachVersionInformation());
        sendVersionInformationEnabled.setEnabled(updatesEnabled.isSelected());


        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), BorderFactory.createTitledBorder(getLang().get("configviewer.tree.node.updates"))));

        GBHelper posPanel = new GBHelper();
        add(updatesEnabled, posPanel.expandW());
        add(sendVersionInformationEnabled, posPanel.nextRow().expandW());
        add(Box.createVerticalGlue(), posPanel.nextRow().expandH().expandW());
    }

    @Override
    public String getChangedConfigurationMessage() {
        StringBuilder displayMessage = new StringBuilder();

        if(UPDATE_CHECK_ENABLED != updatesEnabled.isSelected()){
            displayMessage .append(getLang().get("configviewer.update.label.updateEnabled.text") + ": " + updatesEnabled.isSelected() + " (" + UPDATE_CHECK_ENABLED + ")\n");
        }

        if(UPDATE_CHECK_ATTACH_VERSION != sendVersionInformationEnabled.isSelected()){
            displayMessage.append(getLang().get("configviewer.update.label.attachVersionInformation.text") + ": " + sendVersionInformationEnabled.isSelected() + " (" + UPDATE_CHECK_ATTACH_VERSION + ")\n");
        }

        return displayMessage.toString();
    }

    @Override
    public void updateConfiguration() {
        UpdatesChecker updatesChecker = getConfiguration().getUpdatesChecker();

        updatesChecker.setEnabled(updatesEnabled.isSelected());
        updatesChecker.setAttachVersionInformation(sendVersionInformationEnabled.isSelected());
    }

    @Override
    protected void setStartUpConfig() {
        UPDATE_CHECK_ENABLED = getConfiguration().getUpdatesChecker().isEnabled();
        UPDATE_CHECK_ATTACH_VERSION = getConfiguration().getUpdatesChecker().getAttachVersionInformation();
    }

    private class UpdatesEnabledCheckBoxListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            sendVersionInformationEnabled.setEnabled(updatesEnabled.isSelected());
        }
    }
}
