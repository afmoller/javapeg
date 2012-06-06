package moller.javapeg.program.config.model;

import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImages;
import moller.javapeg.program.config.model.applicationmode.tag.TagImagesCategories;
import moller.javapeg.program.config.model.repository.Repository;
import moller.javapeg.program.config.model.thumbnail.ThumbNail;

public class Config {

    private Logging logging;
    private String javapegClientId;
    private GUI gUI;
    private UpdatesChecker updatesChecker;
    private Language language;
    private RenameImages renameImages;
    private TagImages tagImages;
    private ThumbNail thumbNail;
    private ToolTips toolTips;
    private TagImagesCategories categories;
    private Repository repository;

    public Logging getLogging() {
        return logging;
    }
    public String getJavapegClientId() {
        return javapegClientId;
    }
    public GUI getgUI() {
        return gUI;
    }
    public UpdatesChecker getUpdatesChecker() {
        return updatesChecker;
    }
    public Language getLanguage() {
        return language;
    }
    public RenameImages getRenameImages() {
        return renameImages;
    }
    public TagImages getTagImages() {
        return tagImages;
    }
    public ThumbNail getThumbNail() {
        return thumbNail;
    }
    public ToolTips getToolTips() {
        return toolTips;
    }
    public TagImagesCategories getCategories() {
        return categories;
    }
    public Repository getRepository() {
        return repository;
    }
    public void setLogging(Logging logging) {
        this.logging = logging;
    }
    public void setJavapegClientId(String javapegClientId) {
        this.javapegClientId = javapegClientId;
    }
    public void setgUI(GUI gUI) {
        this.gUI = gUI;
    }
    public void setUpdatesChecker(UpdatesChecker updatesChecker) {
        this.updatesChecker = updatesChecker;
    }
    public void setLanguage(Language language) {
        this.language = language;
    }
    public void setRenameImages(RenameImages renameImages) {
        this.renameImages = renameImages;
    }
    public void setTagImages(TagImages tagImages) {
        this.tagImages = tagImages;
    }
    public void setThumbNail(ThumbNail thumbNail) {
        this.thumbNail = thumbNail;
    }
    public void setToolTips(ToolTips toolTips) {
        this.toolTips = toolTips;
    }
    public void setCategories(TagImagesCategories categories) {
        this.categories = categories;
    }
    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}
