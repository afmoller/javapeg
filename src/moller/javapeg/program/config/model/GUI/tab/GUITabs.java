package moller.javapeg.program.config.model.GUI.tab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fredrik on 2015-06-21.
 */
public class GUITabs {

    private List<GUITab> guiTabs;

    public GUITabs() {
        guiTabs = new ArrayList<>();
    }

    public void addTab(GUITab guiTab) {
        guiTabs.add(guiTab);
    }

    public List<GUITab> getGuiTabs() {
        return guiTabs;
    }
}
