package moller.javapeg.program.metadata;

import java.io.File;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.language.Language;

public class MetaDataUtil {

	public static String getToolTipText(File jpgFile) {
		
		Language lang = Language.getInstance();
		
		MetaData md = MetaDataRetriever.getMetaData(jpgFile);
		
		String noValue = "no value";
		
		if (Config.getInstance().getStringProperty("thumbnails.tooltip.state").equals("2")) {
			return "<html>" +
	         "<table>" +
//	         TODO: Fix this bug
	           createTableRow("Filnamn", jpgFile.getName()) +
	           createTableRow(lang.get("variable.pictureDate"), MetaDataUtil.hasValue(md.getExifDateAsString()) ? md.getExifDateAsString() : noValue) +
	           createTableRow(lang.get("variable.pictureTime"), MetaDataUtil.hasValue(md.getExifTimeAsString()) ? md.getExifTimeAsString() : noValue) +
	           createTableRow(lang.get("variable.cameraModel"), MetaDataUtil.hasValue(md.getExifCameraModel()) ? md.getExifCameraModel() : noValue) +
	           createTableRow(lang.get("variable.shutterSpeed"), MetaDataUtil.hasValue(md.getExifShutterSpeed().toString()) ? md.getExifShutterSpeed().toString() : noValue) +
	           createTableRow(lang.get("variable.isoValue"), MetaDataUtil.hasValue(md.getExifISOValue()) ? Integer.toString(md.getExifISOValue()) : noValue) +
	           createTableRow(lang.get("variable.pictureWidth"), MetaDataUtil.hasValue(md.getExifPictureWidth()) ? Integer.toString(md.getExifPictureWidth()) : noValue) +
	           createTableRow(lang.get("variable.pictureHeight"), MetaDataUtil.hasValue(md.getExifPictureHeight()) ? Integer.toString(md.getExifPictureHeight()) : noValue) +
	           createTableRow(lang.get("variable.apertureValue"), MetaDataUtil.hasValue(md.getExifApertureValue()) ? md.getExifApertureValue() : noValue) +
	         "</table>" +
		   "</html>";
		} else {
//			TODO: Fix hard coded string
			return "File name: " + jpgFile.getName(); 
		}
	}
	
	private static String createTableRow(String metaDataKey, String metaDataValue) {
		return "<tr>" +
		         "<td>" + metaDataKey + "</td>" +
		         "<td>" + ": " + metaDataValue + "</td>" +
	           "</tr>";
	}
	
	public static boolean hasValue(Object object) {
		if (object instanceof Integer) {
			return (Integer)object > -1;
		} else {
			return object != null;
		}
	}
}
