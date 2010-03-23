package moller.javapeg.program.categories;

import java.io.File;

import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.metadata.MetaDataRetriever;

public class ImageExifMetaData {
    
    private String apertureValue;
    private String cameraModel;
    private String date;
    private String isoValue;
    private String pictureHeight;
    private String pictureWidth;
    private String shutterSpeed;
    private String time;
    
    public ImageExifMetaData() {
        apertureValue = "";
        cameraModel = "";
        date = "";
        isoValue = "";
        pictureHeight = "";
        pictureWidth = "";
        shutterSpeed = "";
        time = "";
    }
    
    public ImageExifMetaData(File jpegFile) {
        MetaDataRetriever mdr = new MetaDataRetriever(jpegFile);
        MetaData md = mdr.getMetaData();
    	
    	apertureValue = md.getExifApertureValue();
        cameraModel   = md.getExifCameraModel();
        date          = md.getExifDate();
        isoValue      = md.getExifISOValue();
        pictureHeight = md.getExifPictureHeight();
        pictureWidth  = md.getExifPictureWidth();
        shutterSpeed  = md.getExifShutterSpeed();
        time          = md.getExifTime();
    }
    
    public String getApertureValue() {
        return apertureValue;
    }
    public String getCameraModel() {
        return cameraModel;
    }
    public String getDate() {
        return date;
    }
    public String getIsoValue() {
        return isoValue;
    }
    public String getPictureHeight() {
        return pictureHeight;
    }
    public String getPictureWidth() {
        return pictureWidth;
    }
    public String getShutterSpeed() {
        return shutterSpeed;
    }
    public String getTime() {
        return time;
    }
    public void setApertureValue(String apertureValue) {
        this.apertureValue = apertureValue;
    }
    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setIsoValue(String isoValue) {
        this.isoValue = isoValue;
    }
    public void setPictureHeight(String pictureHeight) {
        this.pictureHeight = pictureHeight;
    }
    public void setPictureWidth(String pictureWidth) {
        this.pictureWidth = pictureWidth;
    }
    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
