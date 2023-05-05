package moller.javapeg.program.config.model.GUI.tab;

/**
 * This enumeration lists all the tabs which are present in the main GUI. And
 * it is used to store and load the last selected tab in the main GUI to the
 * application settings.
 *
 * Created by Fredrik on 2015-03-29.
 */
public enum SelectedMainGUITab {

    MERGE(0),
    RENAME(1),
    TAG(2),
    SEARCH_VIEW(3);

    /**
     * This field specifies the position of the tab in the tabbed pane in the
     * Main GUI.
     */
    private int guiOrder;

    SelectedMainGUITab(int guiOrder) {
        this.guiOrder = guiOrder;
    }

    public int getGuiOrder() {
        return guiOrder;
    }

    public static SelectedMainGUITab getEnumeration(int guiOrder) {
        SelectedMainGUITab[] values = values();

        for (SelectedMainGUITab value : values) {
            if (value.getGuiOrder() == guiOrder) {
                return value;
            }
        }
        throw new IllegalArgumentException("GUI order: " + guiOrder + " is undefined");
    }
}
