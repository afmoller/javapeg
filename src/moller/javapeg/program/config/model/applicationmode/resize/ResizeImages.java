package moller.javapeg.program.config.model.applicationmode.resize;

import java.io.File;

public class ResizeImages {

    private File pathDestination;
    private Integer width;
    private Integer height;
    private Integer selectedQualityIndex;

    public File getPathDestination() {
        return pathDestination;
    }
    public Integer getWidth() {
        return width;
    }
    public Integer getHeight() {
        return height;
    }
    public Integer getSelectedQualityIndex() {
        return selectedQualityIndex;
    }
    public void setPathDestination(File pathDestination) {
        this.pathDestination = pathDestination;
    }
    public void setWidth(Integer width) {
        this.width = width;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
    public void setSelectedQualityIndex(Integer selectedQualityIndex) {
        this.selectedQualityIndex = selectedQualityIndex;
    }
}
