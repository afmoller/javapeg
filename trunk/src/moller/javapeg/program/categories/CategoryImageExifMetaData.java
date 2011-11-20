package moller.javapeg.program.categories;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import moller.javapeg.program.datatype.ExposureTime;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.metadata.MetaDataRetriever;

public class CategoryImageExifMetaData {

    private double fNumber;
    private String cameraModel;
    private Date dateTime;
    private int isoValue;
    private int pictureHeight;
    private int pictureWidth;
    private ExposureTime exposureTime;

    public CategoryImageExifMetaData() {
        fNumber = -1;;
        cameraModel = "";
        dateTime = null;
        isoValue = -1;
        pictureHeight = -1;
        pictureWidth = -1;
        exposureTime = null;
    }

    public CategoryImageExifMetaData(File jpegFile) {
        MetaData md = MetaDataRetriever.getMetaData(jpegFile);

    	fNumber       = md.getExifFNumber();
        cameraModel   = md.getExifCameraModel();
        dateTime      = md.getExifDateTime();
        isoValue      = md.getExifISOValue();
        pictureHeight = md.getExifPictureHeight();
        pictureWidth  = md.getExifPictureWidth();
        exposureTime  = md.getExifExposureTime();
    }

    public double getFNumber() {
        return fNumber;
    }
    public String getCameraModel() {
        return cameraModel;
    }
    public Date getDateTime() {
        return dateTime;
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
    public ExposureTime getExposureTime() {
        return exposureTime;
    }

    public void setFNumber(double fNumber) {
        this.fNumber = fNumber;
    }
    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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

    public void setExposureTime(ExposureTime exposureTime) {
        this.exposureTime = exposureTime;
    }

    public String getDateTimeAsString() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
		return sdf.format(dateTime);
    }
}