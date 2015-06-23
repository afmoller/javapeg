package moller.javapeg.program.config.model.GUI.tab;

import moller.javapeg.program.enumerations.TabPosition;

/**
 * Created by Fredrik on 2015-06-21.
 */
public class GUITab {

    private String textColor;
    private TabPosition position;
    private String id;

    public TabPosition getPosition() {
        return position;
    }

    public void setPosition(TabPosition position) {
        this.position = position;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
