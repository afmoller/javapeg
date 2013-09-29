package moller.javapeg.program.config.model.GUI;

public class GUI {

    private GUIWindow main;
    private GUIWindow imageViewer;
    private GUIWindow imageSearchResultViewer;
    private GUIWindow configViewer;
    private GUIWindow helpViewer;
    private GUIWindow imageResizer;
    private GUIWindow imageConflictViewer;

    public GUIWindow getMain() {
        return main;
    }
    public GUIWindow getImageViewer() {
        return imageViewer;
    }
    public GUIWindow getImageSearchResultViewer() {
        return imageSearchResultViewer;
    }
    public GUIWindow getConfigViewer() {
        return configViewer;
    }
    public GUIWindow getHelpViewer() {
        return helpViewer;
    }
    public GUIWindow getImageResizer() {
        return imageResizer;
    }
    public GUIWindow getImageConflictViewer() {
        return imageConflictViewer;
    }
    public void setMain(GUIWindow main) {
        this.main = main;
    }
    public void setImageViewer(GUIWindow imageViewer) {
        this.imageViewer = imageViewer;
    }
    public void setImageSearchResultViewer(GUIWindow imageSearchResultViewer) {
        this.imageSearchResultViewer = imageSearchResultViewer;
    }
    public void setConfigViewer(GUIWindow configViewer) {
        this.configViewer = configViewer;
    }
    public void setHelpViewer(GUIWindow helpViewer) {
        this.helpViewer = helpViewer;
    }
    public void setImageResizer(GUIWindow imageResizer) {
        this.imageResizer = imageResizer;
    }
    public void setImageConflictViewer(GUIWindow imageConflictViewer) {
        this.imageConflictViewer = imageConflictViewer;
    }
}
