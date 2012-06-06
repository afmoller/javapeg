package moller.javapeg.program.config.model.applicationmode.rename;

import java.net.URI;
import java.text.SimpleDateFormat;

public class RenameImages {

    private URI pathSource;
    private URI pathDestination;
    private String templateSubDirectoryName;
    private String templateFileName;
    private Boolean createThumbNails;
    private Boolean useLastModifiedDate;
    private Boolean useLastModifiedTime;
    private Integer cameraModelNameMaximumLength;
    private SimpleDateFormat progressLogTimestampFormat;

    public URI getPathSource() {
        return pathSource;
    }
    public URI getPathDestination() {
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
    public void setPathSource(URI pathSource) {
        this.pathSource = pathSource;
    }
    public void setPathDestination(URI pathDestination) {
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
}
