package moller.javapeg.program.config.model.thumbnail;

public class ThumbNailCache {

    private Boolean enabled;
    private Integer maxSize;

    public Boolean getEnabled() {
        return enabled;
    }
    public Integer getMaxSize() {
        return maxSize;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }
}
