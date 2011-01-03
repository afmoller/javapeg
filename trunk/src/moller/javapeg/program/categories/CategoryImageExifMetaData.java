package moller.javapeg.program.categories;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import moller.javapeg.program.datatype.ShutterSpeed;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.metadata.MetaDataRetriever;

public class CategoryImageExifMetaData {
    
    private double apertureValue;
    private String cameraModel;
    private Date date;
    private int isoValue;
    private int pictureHeight;
    private int pictureWidth;
    private ShutterSpeed shutterSpeed;
    private Date time;
    
    public CategoryImageExifMetaData() {
        apertureValue = -1;;
        cameraModel = "";
        date = null;
        isoValue = -1;
        pictureHeight = -1;
        pictureWidth = -1;
        shutterSpeed = null;
        time = null;
    }
    
    public CategoryImageExifMetaData(File jpegFile) {
        MetaData md = MetaDataRetriever.getMetaData(jpegFile);
    	
    	apertureValue = md.getExifApertureValue();
        cameraModel   = md.getExifCameraModel();
        date          = md.getExifDate();
        isoValue      = md.getExifISOValue();
        pictureHeight = md.getExifPictureHeight();
        pictureWidth  = md.getExifPictureWidth();
        shutterSpeed  = md.getExifShutterSpeed();
        time          = md.getExifTime();
    }
    
    public double getApertureValue() {
        return apertureValue;
    }
    public String getCameraModel() {
        return cameraModel;
    }
    public Date getDate() {
        return date;
    }
    public int getIsoValue() {
        return isoValue;
    }
    public int getPictureHeight() {
        return pictureHeight;
    }
    public int getPictureWidth() {
        return pictureWidth;
    }
    public ShutterSpeed getShutterSpeed() {
        return shutterSpeed;
    }
    public Date getTime() {
        return time;
    }
    public void setApertureValue(double apertureValue) {
        this.apertureValue = apertureValue;
    }
    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setIsoValue(int isoValue) {
        this.isoValue = isoValue;
    }
    public void setPictureHeight(int pictureHeight) {
        this.pictureHeight = pictureHeight;
    }
    public void setPictureWidth(int pictureWidth) {
        this.pictureWidth = pictureWidth;
    }
    public void setShutterSpeed(ShutterSpeed shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    
    public String getDateAsString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd");
		return sdf.format(date);
    }
    
    public String getTimeAsString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(time);
    }
}
