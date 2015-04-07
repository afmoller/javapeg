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

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.ConfigUtil;
import moller.javapeg.program.config.model.Language;
import moller.javapeg.program.gui.frames.configuration.panels.base.BaseConfigurationPanel;
import moller.javapeg.program.language.ISO639;
import moller.util.java.SystemProperties;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LanguageConfigurationPanel extends BaseConfigurationPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private JRadioButton manualRadioButton;
    private JRadioButton automaticRadioButton;
    private JList<String> languageList;
    private JLabel currentLanguage;

    @Override
    public boolean isValidConfiguration() {
        return true;
    }

    @Override
    protected void addListeners() {
        manualRadioButton.addActionListener(new ManualRadioButtonListener());
        automaticRadioButton.addActionListener(new AutomaticRadioButtonListener());
        languageList.addListSelectionListener(new LanguageListListener());
    }

    @Override
    protected void createPanel() {
        setLayout(new GridBagLayout());

        manualRadioButton = new JRadioButton(getLang().get("configviewer.language.radiobutton.manual"));
        automaticRadioButton = new JRadioButton(getLang().get("configviewer.language.radiobutton.automatic"));

        ButtonGroup languageSelectionMode = new ButtonGroup();
        languageSelectionMode.add(manualRadioButton);
        languageSelectionMode.add(automaticRadioButton);

        Language language = getConfiguration().getLanguage();

        if (language.getAutomaticSelection()) {
            if (!language.getgUILanguageISO6391().equals(SystemProperties.getUserLanguage())) {
                language.setgUILanguageISO6391(SystemProperties.getUserLanguage());
            }
        }

        JPanel selectionModePanel = new JPanel(new GridBagLayout());
        selectionModePanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.language.label.selectionMode")));

        GBHelper posSelectionMode = new GBHelper();

        selectionModePanel.add(manualRadioButton, posSelectionMode.expandW());
        selectionModePanel.add(automaticRadioButton, posSelectionMode.nextRow().expandW());
        selectionModePanel.add(Box.createVerticalGlue(), posSelectionMode.nextRow().expandH());

        languageList = new JList<String>(ConfigUtil.listLanguagesFiles());

        if (language.getAutomaticSelection()) {
            languageList.setEnabled(false);
        }

        JScrollPane languageListScrollPane = new JScrollPane(languageList);

        JPanel availableLanguagesPanel = new JPanel(new GridBagLayout());
        availableLanguagesPanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.language.label.availableLanguages")));

        GBHelper posAvailableLanguages = new GBHelper();

        availableLanguagesPanel.add(languageListScrollPane, posAvailableLanguages.expandW().expandH());

        currentLanguage = new JLabel(ConfigUtil.resolveCodeToLanguageName(language.getgUILanguageISO6391()));

        JPanel currentLanguagePanel = new JPanel(new GridBagLayout());
        currentLanguagePanel.setBorder(BorderFactory.createTitledBorder(getLang().get("configviewer.language.label.currentLanguage")));

        GBHelper posCurrentLanguage = new GBHelper();

        currentLanguagePanel.add(currentLanguage, posCurrentLanguage.expandW());
        currentLanguagePanel.add(Box.createVerticalGlue(), posCurrentLanguage.nextRow().expandH());

        GBHelper posPanel = new GBHelper();

        add(currentLanguagePanel, posPanel.expandW().expandH());
        add(selectionModePanel, posPanel.nextRow().expandW().expandH());
        add(availableLanguagesPanel, posPanel.nextRow().expandW().expandH());

        if (language.getAutomaticSelection()) {
            automaticRadioButton.setSelected(true);
        } else {
            manualRadioButton.setSelected(true);
        }
    }

    @Override
    public String getChangedConfigurationMessage() {
        StringBuilder displayMessage = new StringBuilder();

        Language language = getConfiguration().getLanguage();

        if(language.getAutomaticSelection() != automaticRadioButton.isSelected()){
            displayMessage.append(getLang().get("configviewer.language.radiobutton.automatic") + ": " + automaticRadioButton.isSelected() + " (" + language.getAutomaticSelection() + ")\n");
        }

        if(!language.getgUILanguageISO6391().equals(ISO639.getInstance().getCode(currentLanguage.getText()))){
            displayMessage.append(getLang().get("configviewer.language.label.currentLanguage") + ": " + currentLanguage.getText() + " (" + ISO639.getInstance().getLanguage(language.getgUILanguageISO6391()) + ")\n");
        }

        return displayMessage.toString();
    }

    @Override
    public void updateConfiguration() {
        Language language = getConfiguration().getLanguage();

        language.setAutomaticSelection(automaticRadioButton.isSelected());
        language.setgUILanguageISO6391(ISO639.getInstance().getCode(currentLanguage.getText()));

    }

    private class ManualRadioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            languageList.setEnabled(true);
        }
    }

    private class AutomaticRadioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            languageList.setEnabled(false);

            String userLanguage = System.getProperty("user.language");

            if(!getConfiguration().getLanguage().getgUILanguageISO6391().equals(userLanguage)) {
                currentLanguage.setText(ConfigUtil.resolveCodeToLanguageName(userLanguage));
            }
        }
    }

    private class LanguageListListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            if(languageList.getSelectedIndex() > -1) {
                currentLanguage.setText(languageList.getSelectedValue());
            }
        }
    }

}
