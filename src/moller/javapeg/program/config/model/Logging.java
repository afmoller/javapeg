package moller.javapeg.program.config.model;

import java.text.SimpleDateFormat;

import moller.javapeg.program.enumerations.Level;

public class Logging {

    private Boolean developerMode;
    private Boolean rotate;
    private Boolean rotateZip;

    private Level level;
    private Long rotateSize;
    private String fileName;
    private SimpleDateFormat timeStampFormat;

    public Boolean getDeveloperMode() {
        return developerMode;
    }
    public Boolean getRotate() {
        return rotate;
    }
    public Boolean getRotateZip() {
        return rotateZip;
    }
    public Level getLevel() {
        return level;
    }
    public Long getRotateSize() {
        return rotateSize;
    }
    public String getFileName() {
        return fileName;
    }
    public SimpleDateFormat getTimeStampFormat() {
        return timeStampFormat;
    }
    public void setDeveloperMode(Boolean developerMode) {
        this.developerMode = developerMode;
    }
    public void setRotate(Boolean rotate) {
        this.rotate = rotate;
    }
    public void setRotateZip(Boolean rotateZip) {
        this.rotateZip = rotateZip;
    }
    public void setLevel(Level level) {
        this.level = level;
    }
    public void setRotateSize(Long rotateSize) {
        this.rotateSize = rotateSize;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void setTimeStampFormat(SimpleDateFormat timeStampFormat) {
        this.timeStampFormat = timeStampFormat;
    }
}
