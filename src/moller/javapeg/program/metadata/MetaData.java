package moller.javapeg.program.metadata;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import moller.javapeg.program.datatype.ShutterSpeed;

public class MetaData {

	// Instansvariabler
	private String fileName;
	private Date exifDateTime;
	private String exifCameraModel;
	private ShutterSpeed exifShutterSpeed;
	private int exifISOValue;
	private int exifPictureWidth;
	private int exifPictureHeight;
	private double exifApertureValue;
	private int thumbNailOffset;
	private int thumbNailLength;
	private File fileObject;

	// Konstruktor
	public MetaData() {
		fileName = null;
		exifDateTime = null;
		exifCameraModel = null;
		exifShutterSpeed = null;
		exifISOValue = -1;
		exifPictureWidth = -1;
		exifPictureHeight = -1;
		exifApertureValue = -1;
		thumbNailOffset = -1;
		thumbNailLength = -1;
		fileObject = null;
	}

	// GET-metoder
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
		return sdf.format(exifDateTime);
	}

	public String getExifTimeAsString() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(exifDateTime);
	}

	public String getExifCameraModel() {
		return exifCameraModel;
	}

	public ShutterSpeed getExifShutterSpeed() {
		return exifShutterSpeed;
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

	public double getExifApertureValue() {
		return exifApertureValue;
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
	
	// SET-metoder
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setExifDateTime(Date exifDateTime) {
		this.exifDateTime = exifDateTime;
	}

	public void setExifCameraModel(String exifCameraModel) {
		this.exifCameraModel = exifCameraModel;
	}

	public void setExifShutterSpeed(ShutterSpeed exifShutterSpeed) {
		this.exifShutterSpeed = exifShutterSpeed;
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

	public void setExifApertureValue(double exifApertureValue) {
		this.exifApertureValue = exifApertureValue;
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