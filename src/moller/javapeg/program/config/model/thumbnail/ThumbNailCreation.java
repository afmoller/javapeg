package moller.javapeg.program.config.model.thumbnail;

import moller.util.jpeg.JPEGScaleAlgorithm;

public class ThumbNailCreation {

    private Boolean ifMissingOrCorrupt;
    private JPEGScaleAlgorithm algorithm;

    private Integer width;
    private Integer height;

    public Boolean getIfMissingOrCorrupt() {
        return ifMissingOrCorrupt;
    }
    public JPEGScaleAlgorithm getAlgorithm() {
        return algorithm;
    }
    public Integer getWidth() {
        return width;
    }
    public Integer getHeight() {
        return height;
    }
    public void setIfMissingOrCorrupt(Boolean ifMissingOrCorrupt) {
        this.ifMissingOrCorrupt = ifMissingOrCorrupt;
    }
    public void setAlgorithm(JPEGScaleAlgorithm algorithm) {
        this.algorithm = algorithm;
    }
    public void setWidth(Integer width) {
        this.width = width;
    }
    public void setHeight(Integer height) {
        this.height = height;
    }
}
