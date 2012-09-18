package moller.javapeg.program.config.model.GUI;

import java.util.List;

public class GUIWindowSplitPaneUtil {

    public static Integer getGUIWindowSplitPaneDividerLocation(List<GUIWindowSplitPane> gUIWindowSplitPanes, String name)  {
        for (GUIWindowSplitPane guiWindowSplitPane : gUIWindowSplitPanes) {
            if (guiWindowSplitPane.getName().equals(name)) {
                return guiWindowSplitPane.getLocation();
            }
        }
        return 0;
    }

    public static Integer getGUIWindowSplitPaneDividerSize(List<GUIWindowSplitPane> gUIWindowSplitPanes, String name)  {
        for (GUIWindowSplitPane guiWindowSplitPane : gUIWindowSplitPanes) {
            if (guiWindowSplitPane.getName().equals(name)) {
                return guiWindowSplitPane.getWidth();
            }
        }
        return 10;
    }

    public static void setGUIWindowSplitPaneDividerLocation(List<GUIWindowSplitPane> gUIWindowSplitPanes, String name, int splitPaneLocation)  {
        for (GUIWindowSplitPane guiWindowSplitPane : gUIWindowSplitPanes) {
            if (guiWindowSplitPane.getName().equals(name)) {
                guiWindowSplitPane.setLocation(splitPaneLocation);
            }
        }
    }

    public static void setGUIWindowSplitPaneDividerWidth(List<GUIWindowSplitPane> gUIWindowSplitPanes, String name, int dividerSize) {
        for (GUIWindowSplitPane guiWindowSplitPane : gUIWindowSplitPanes) {
            if (guiWindowSplitPane.getName().equals(name)) {
                guiWindowSplitPane.setWidth(dividerSize);
            }
        }
    }
}
