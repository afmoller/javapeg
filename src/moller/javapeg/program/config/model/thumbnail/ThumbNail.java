package moller.javapeg.program.config.model.thumbnail;

public class ThumbNail {

    private ThumbNailCache cache;
    private ThumbNailCreation creation;

    public ThumbNailCache getCache() {
        return cache;
    }
    public ThumbNailCreation getCreation() {
        return creation;
    }
    public void setCache(ThumbNailCache cache) {
        this.cache = cache;
    }
    public void setCreation(ThumbNailCreation creation) {
        this.creation = creation;
    }
}
