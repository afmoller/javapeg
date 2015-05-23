package moller.javapeg.program.gui.frames.configuration.panels.userinterface;

import moller.javapeg.program.enumerations.SplitPaneDividerSize;
import moller.javapeg.program.enumerations.TabPosition;
import moller.javapeg.program.enumerations.wrappers.SplitPaneDividerThicknessWrapper;
import moller.javapeg.program.enumerations.wrappers.TabPositionWrapper;
import moller.javapeg.program.language.Language;

import javax.swing.*;

/**
 * Created by Fredrik on 2015-05-23.
 */
public class TabPositionComboBox extends JComboBox<TabPositionWrapper> {

    public TabPositionComboBox(Language lang) {
        TabPositionWrapper thin = new TabPositionWrapper(lang.get(TabPosition.TOP.getLocalizationKey()), TabPosition.TOP);
        TabPositionWrapper thick = new TabPositionWrapper(lang.get(TabPosition.BOTTOM.getLocalizationKey()), TabPosition.BOTTOM);

        addItem(thin);
        addItem(thick);
    }

    public void setSelectedThickness(TabPosition tabPosition) {
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
