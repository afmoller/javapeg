package moller.javapeg.program.config.model.applicationmode.rename;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Set;

public class RenameImages {

    private File pathSource;
    private File pathDestination;
    private String templateSubDirectoryName;
    private String templateFileName;
    private Boolean createThumbNails;
    private Boolean useLastModifiedDate;
    private Boolean useLastModifiedTime;
    private Integer cameraModelNameMaximumLength;
    private SimpleDateFormat progressLogTimestampFormat;
    private Set<String> templateSubDirectoryNames;
    private Set<String> templateFileNameNames;

    public File getPathSource() {
        return pathSource;
    }
    public File getPathDestination() {
        return pathDestination;
    }
    public String getTemplateSubDirectoryName() {
        return templateSubDirectoryName;
    }
    public String getTemplateFileName() {
        return templateFileName;
    }
    public Boolean getCreateThumbNails() {
        return createThumbNails;
    }
    public Boolean getUseLastModifiedDate() {
        return useLastModifiedDate;
    }
    public Boolean getUseLastModifiedTime() {
        return useLastModifiedTime;
    }
    public Integer getCameraModelNameMaximumLength() {
        return cameraModelNameMaximumLength;
    }
    public SimpleDateFormat getProgressLogTimestampFormat() {
        return progressLogTimestampFormat;
    }
    public Set<String> getTemplateSubDirectoryNames() {
        return templateSubDirectoryNames;
    }
    public Set<String> getTemplateFileNameNames() {
        return templateFileNameNames;
    }
    public void setPathSource(File pathSource) {
        this.pathSource = pathSource;
    }
    public void setPathDestination(File pathDestination) {
        this.pathDestination = pathDestination;
    }
    public void setTemplateSubDirectoryName(String templateSubDirectoryName) {
        this.templateSubDirectoryName = templateSubDirectoryName;
    }
    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }
    public void setCreateThumbNails(Boolean createThumbNails) {
        this.createThumbNails = createThumbNails;
    }
    public void setUseLastModifiedDate(Boolean useLastModifiedDate) {
        this.useLastModifiedDate = useLastModifiedDate;
    }
    public void setUseLastModifiedTime(Boolean useLastModifiedTime) {
        this.useLastModifiedTime = useLastModifiedTime;
    }
    public void setCameraModelNameMaximumLength(Integer cameraModelNameMaximumLength) {
        this.cameraModelNameMaximumLength = cameraModelNameMaximumLength;
    }
    public void setProgressLogTimestampFormat(
            SimpleDateFormat progressLogTimestampFormat) {
        this.progressLogTimestampFormat = progressLogTimestampFormat;
    }
    public void setTemplateSubDirectoryNames(Set<String> templateSubDirectoryNames) {
        this.templateSubDirectoryNames = templateSubDirectoryNames;
    }
    public void setTemplateFileNameNames(Set<String> templateFileNameNames) {
        this.templateFileNameNames = templateFileNameNames;
    }
}
