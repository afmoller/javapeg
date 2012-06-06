package moller.javapeg.program.config.model.applicationmode.tag;

public class TagImagesCategories {

    private Boolean warnWhenRemove;
    private Boolean warnWhenRemoveWithSubCategories;
    private Boolean orRadioButtonIsSelected;

    public Boolean getWarnWhenRemove() {
        return warnWhenRemove;
    }
    public Boolean getWarnWhenRemoveWithSubCategories() {
        return warnWhenRemoveWithSubCategories;
    }
    public Boolean getOrRadioButtonIsSelected() {
        return orRadioButtonIsSelected;
    }
    public void setWarnWhenRemove(Boolean warnWhenRemove) {
        this.warnWhenRemove = warnWhenRemove;
    }
    public void setWarnWhenRemoveWithSubCategories(
            Boolean warnWhenRemoveWithSubCategories) {
        this.warnWhenRemoveWithSubCategories = warnWhenRemoveWithSubCategories;
    }
    public void setOrRadioButtonIsSelected(Boolean orRadioButtonIsSelected) {
        this.orRadioButtonIsSelected = orRadioButtonIsSelected;
    }
}
