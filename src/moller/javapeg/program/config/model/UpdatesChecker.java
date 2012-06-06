package moller.javapeg.program.config.model;

import java.net.URL;

public class UpdatesChecker {

    private Boolean enabled;
    private Boolean attachVersionInformation;
    private Integer timeOut;
    private URL urlVersion;
    private URL urlVersionInformation;

    public Boolean getEnabled() {
        return enabled;
    }
    public Boolean getAttachVersionInformation() {
        return attachVersionInformation;
    }
    public Integer getTimeOut() {
        return timeOut;
    }
    public URL getUrlVersion() {
        return urlVersion;
    }
    public URL getUrlVersionInformation() {
        return urlVersionInformation;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public void setAttachVersionInformation(Boolean attachVersionInformation) {
        this.attachVersionInformation = attachVersionInformation;
    }
    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }
    public void setUrlVersion(URL urlVersion) {
        this.urlVersion = urlVersion;
    }
    public void setUrlVersionInformation(URL urlVersionInformation) {
        this.urlVersionInformation = urlVersionInformation;
    }
}
