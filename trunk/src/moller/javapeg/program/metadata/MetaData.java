package moller.javapeg.program.metadata;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import moller.javapeg.program.datatype.ExposureTime;

public class MetaData {

    private String fileName;
    private Date exifDateTime;
    private String exifCameraModel;
    private ExposureTime exifExposureTime;
    private int exifISOValue;
    private int exifPictureWidth;
    private int exifPictureHeight;
    private double exifFNumber;
    private int thumbNailOffset;
    private int thumbNailLength;
    private File fileObject;

    public MetaData() {
        fileName = null;
        exifDateTime = null;
        exifCameraModel = null;
        exifExposureTime = null;
        exifISOValue = -1;
        exifPictureWidth = -1;
        exifPictureHeight = -1;
        exifFNumber = -1;
        thumbNailOffset = -1;
        thumbNailLength = -1;
        fileObject = null;
    }

    public String getFileName() {
        return fileName;
    }

    public Date getExifTime() {
        return exifDateTime;
    }

    public Date getExifDate() {
        return exifDateTime;
    }

    public Date getExifDateTime() {
        return exifDateTime;
    }

    public String getExifDateAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
        return exifDateTime != null ? sdf.format(exifDateTime) : null;
    }

    public String getExifTimeAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return exifDateTime != null ? sdf.format(exifDateTime) : null;
    }

    public String getExifCameraModel() {
        return exifCameraModel;
    }

    public ExposureTime getExifExposureTime() {
        return exifExposureTime;
    }

    public int getExifISOValue() {
        return exifISOValue;
    }

    public int getExifPictureWidth() {
        return exifPictureWidth;
    }

    public int getExifPictureHeight() {
        return exifPictureHeight;
    }

    public double getExifFNumber() {
        return exifFNumber;
    }

    public int getThumbNailOffset() {
        return thumbNailOffset;
    }

    public int getThumbNailLength() {
        return thumbNailLength;
    }

    public File getFileObject() {
        return fileObject;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setExifDateTime(Date exifDateTime) {
        this.exifDateTime = exifDateTime;
    }

    public void setExifCameraModel(String exifCameraModel) {
        this.exifCameraModel = exifCameraModel;
    }

    public void setExifExposureTime(ExposureTime exifExposureTime) {
        this.exifExposureTime = exifExposureTime;
    }

    public void setExifISOValue(int exifISOValue) {
        this.exifISOValue = exifISOValue;
    }

    public void setExifPictureWidth(int exifPictureWidth) {
        this.exifPictureWidth = exifPictureWidth;
    }

    public void setExifPictureHeight(int exifPictureHeight) {
        this.exifPictureHeight = exifPictureHeight;
    }

    public void setExifFNumber(double exifFNumber) {
        this.exifFNumber = exifFNumber;
    }

    public void setThumbNailOffset(int thumbNailOffset) {
        this.thumbNailOffset = thumbNailOffset;
    }

    public void setThumbNailLength(int thumbNailLength) {
        this.thumbNailLength = thumbNailLength;
    }

    public void setFileObject(File fileObject) {
        this.fileObject = fileObject;
    }
}
