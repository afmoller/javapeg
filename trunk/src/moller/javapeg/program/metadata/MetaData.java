package moller.javapeg.program.metadata;

import java.io.File;

public class MetaData{

	// Instansvariabler
	private String fileName;
	private String exifDate;
	private String exifTime;
	private String exifCameraModel;
	private String exifShutterSpeed;
	private String exifISOValue;
	private String exifPictureWidth;
	private String exifPictureHeight;
	private String exifApertureValue;
	private int thumbNailOffset;
	private int thumbNailLength;
	private File fileObject;

	// Konstruktor
	public MetaData() {

		fileName = "";
		exifDate = "";
		exifTime = "";
		exifCameraModel = "";
		exifShutterSpeed = "";
		exifISOValue = "";
		exifPictureWidth = "";
		exifPictureHeight = "";
		exifApertureValue = "";
		thumbNailOffset = 0;
		thumbNailLength = 0;
	}

	// GET-metoder
	public String getFileName() {
		return fileName;
	}

	public String getExifDate() {
		return exifDate;
	}

	public String getExifTime() {
		return exifTime;
	}

	public String getExifCameraModel() {
		return exifCameraModel;
	}

	public String getExifShutterSpeed() {
		return exifShutterSpeed;
	}

	public String getExifISOValue() {
		return exifISOValue;
	}

	public String getExifPictureWidth() {
		return exifPictureWidth;
	}

	public String getExifPictureHeight() {
		return exifPictureHeight;
	}

	public String getExifApertureValue() {
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

	public void setExifDate(String exifDate) {
		this.exifDate = exifDate;
	}

	public void setExifTime(String exifTime) {
		this.exifTime = exifTime;
	}

	public void setExifCameraModel(String exifCameraModel) {
		this.exifCameraModel = exifCameraModel;
	}

	public void setExifShutterSpeed(String exifShutterSpeed) {
		this.exifShutterSpeed = exifShutterSpeed;
	}

	public void setExifISOValue(String exifISOValue) {
		this.exifISOValue = exifISOValue;
	}

	public void setExifPictureWidth(String exifPictureWidth) {
		this.exifPictureWidth = exifPictureWidth;
	}

	public void setExifPictureHeight(String exifPictureHeight) {
		this.exifPictureHeight = exifPictureHeight;
	}

	public void setExifApertureValue(String exifApertureValue) {
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