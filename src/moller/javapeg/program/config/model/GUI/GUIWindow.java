package moller.javapeg.program.config.model.GUI;

import java.awt.Rectangle;
import java.util.List;

public class GUIWindow {

    private Rectangle sizeAndLocation;
    private List<GUIWindowSplitPane> guiWindowSplitPane;

    public Rectangle getSizeAndLocation() {
        return sizeAndLocation;
    }
    public List<GUIWindowSplitPane> getGuiWindowSplitPane() {
        return guiWindowSplitPane;
    }
    public void setSizeAndLocation(Rectangle sizeAndLocation) {
        this.sizeAndLocation = sizeAndLocation;
    }
    public void setGuiWindowSplitPane(List<GUIWindowSplitPane> guiWindowSplitPane) {
        this.guiWindowSplitPane = guiWindowSplitPane;
    }
}
