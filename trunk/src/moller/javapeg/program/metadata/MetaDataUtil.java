package moller.javapeg.program.metadata;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;

public class MetaDataUtil {

	public static String getToolTipText(File jpgFile) {

		Language lang = Language.getInstance();

		MetaData md = MetaDataRetriever.getMetaData(jpgFile);

		String noValue = lang.get("common.missing.value");

		if (Config.getInstance().get().getToolTips().getState().equals("2")) {
			return "<html>" +
	         "<table>" +
	           createTableRow(lang.get("variable.comment.fileName"), jpgFile.getName()) +
	           createTableRow(lang.get("variable.pictureDate"), MetaDataUtil.hasValue(md.getExifDateAsString()) ? md.getExifDateAsString() : noValue) +
	           createTableRow(lang.get("variable.pictureTime"), MetaDataUtil.hasValue(md.getExifTimeAsString()) ? md.getExifTimeAsString() : noValue) +
	           createTableRow(lang.get("variable.cameraModel"), MetaDataUtil.hasValue(md.getExifCameraModel()) ? md.getExifCameraModel() : noValue) +
	           createTableRow(lang.get("variable.shutterSpeed"), MetaDataUtil.hasValue(md.getExifExposureTime()) ? md.getExifExposureTime().toString() : noValue) +
	           createTableRow(lang.get("variable.isoValue"), MetaDataUtil.hasValue(md.getExifISOValue()) ? Integer.toString(md.getExifISOValue()) : noValue) +
	           createTableRow(lang.get("variable.pictureWidth"), MetaDataUtil.hasValue(md.getExifPictureWidth()) ? Integer.toString(md.getExifPictureWidth()) : noValue) +
	           createTableRow(lang.get("variable.pictureHeight"), MetaDataUtil.hasValue(md.getExifPictureHeight()) ? Integer.toString(md.getExifPictureHeight()) : noValue) +
	           createTableRow(lang.get("variable.apertureValue"), MetaDataUtil.hasValue(md.getExifFNumber()) ? Double.toString(md.getExifFNumber()) : noValue) +
	         "</table>" +
		   "</html>";
		} else {
			return lang.get("variable.comment.fileName") + ": " + jpgFile.getName();
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
		} else if (object instanceof Double) {
		    return (Double)object > -1;
		} else {
			return object != null;
		}
	}

    public static Map<String, String> parseImageFile(File imageFile) {
		Logger logger = Logger.getInstance();

		Map<String, String> tagsMap = new HashMap<String, String>();

		try{
			Metadata metadata = JpegMetadataReader.readMetadata(imageFile);

			for (Directory directory : metadata.getDirectories()) {
			    for (Tag tag : directory.getTags()) {
					if(tag.toString().indexOf("Unknown tag") == -1){
						tagsMap.put(tag.getTagTypeHex(), tag.getDescription());
					}
				}
				if (directory.hasErrors()) {
					for (String error : directory.getErrors()) {
						logger.logERROR("File:" + imageFile.getAbsolutePath() + " contain meta data directory error for directory: " + directory.getName() + "(" + error + ")");
					}
				}
			}
		} catch (JpegProcessingException jpex) {
			logger.logERROR("Could not read meata data from file: " + imageFile.getAbsolutePath());
			logger.logERROR(jpex);
		} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return tagsMap;
	}

    public static int getOrientationTag(File imageFile) {

		Logger logger = Logger.getInstance();

		try{
			Metadata metadata = JpegMetadataReader.readMetadata(imageFile);

			for (Directory directory : metadata.getDirectories()) {

				if(directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
					return directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
				}
			}
		} catch (JpegProcessingException jpex) {
			logger.logERROR("Could not read meata data from file: " + imageFile.getAbsolutePath());
			logger.logERROR(jpex);
		} catch (MetadataException mdex) {
			logger.logERROR("Could not get value for orientation tag (" + ExifIFD0Directory.TAG_ORIENTATION + ") in file: " + imageFile.getAbsolutePath());
			logger.logERROR(mdex);
		} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return -1;
	}
}
