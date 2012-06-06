package moller.javapeg.program.config.model.applicationmode.tag;

public class TagImagesPaths {

    private Boolean automaticallyRemoveNonExistingImagePath;
    private Integer addToRepositoryPolicy;

    public Boolean getAutomaticallyRemoveNonExistingImagePath() {
        return automaticallyRemoveNonExistingImagePath;
    }
    public Integer getAddToRepositoryPolicy() {
        return addToRepositoryPolicy;
    }
    public void setAutomaticallyRemoveNonExistingImagePath(
            Boolean automaticallyRemoveNonExistingImagePath) {
        this.automaticallyRemoveNonExistingImagePath = automaticallyRemoveNonExistingImagePath;
    }
    public void setAddToRepositoryPolicy(Integer addToRepositoryPolicy) {
        this.addToRepositoryPolicy = addToRepositoryPolicy;
    }
}
