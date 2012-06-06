package moller.javapeg.program.config.model.applicationmode.tag;

public class TagImages {

    private TagImagesCategories categories;
    private TagImagesPaths imagesPaths;
    private TagImagesPreview preview;

    public TagImagesCategories getCategories() {
        return categories;
    }
    public TagImagesPaths getImagesPaths() {
        return imagesPaths;
    }
    public TagImagesPreview getPreview() {
        return preview;
    }
    public void setCategories(TagImagesCategories categories) {
        this.categories = categories;
    }
    public void setImagesPaths(TagImagesPaths imagesPaths) {
        this.imagesPaths = imagesPaths;
    }
    public void setPreview(TagImagesPreview preview) {
        this.preview = preview;
    }
}
