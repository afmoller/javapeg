package moller.javapeg.program.metadata;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import moller.javapeg.program.datatype.ShutterSpeed;
import moller.javapeg.program.datatype.ShutterSpeed.ShutterSpeedException;
import moller.javapeg.program.enumerations.FieldName;
import moller.javapeg.program.logger.Logger;
import moller.util.string.StringUtil;

// Klassdefinition
public class MetaDataRetriever {
	
	public static MetaData getMetaData(File imageFile) {
		MetaData md = new MetaData();
		md.setFileName(imageFile.getName());
		md.setFileObject(imageFile);
		
		Map<String, String> tagAndValueMappings = MetaDataUtil.parseImageFile(imageFile);
		
		populateMetaData(md, tagAndValueMappings);
		
		return md;
	}

	private static void populateMetaData(MetaData metaData, Map<String, String> tagAndValueMappings) {

		String cameraMake = tagAndValueMappings.get("0x010f");
		String cameraModel = tagAndValueMappings.get("0x0110");
		
		MetaDataCameraAndTagMapping mdcatm = MetaDataCameraAndTagMapping.getInstance();
		
		String apertureValueTag = mdcatm.getTag(cameraMake, cameraModel, FieldName.APERTURE_VALUE);
		String dateTimeOriginalTag = mdcatm.getTag(cameraMake, cameraModel, FieldName.DATE_TIME_ORIGINAL);
		String isoSpeedRatingsTag = mdcatm.getTag(cameraMake, cameraModel, FieldName.ISO_SPEED_RATINGS);
		String pixelXDimensionTag = mdcatm.getTag(cameraMake, cameraModel, FieldName.PIXEL_X_DIMENSION);
		String pixelYDimensionTag = mdcatm.getTag(cameraMake, cameraModel, FieldName.PIXEL_Y_DIMENSION);
		String shutterSpeedValueTag = mdcatm.getTag(cameraMake, cameraModel, FieldName.SHUTTER_SPEED_VALUE);
		
		String jpegInterchangeFormatTag = mdcatm.getTag(cameraMake, cameraModel, FieldName.JPEG_INTERCHANGE_FORMAT);
		String jpegInterchangeFormatLengthTag = mdcatm.getTag(cameraMake, cameraModel, FieldName.JPEG_INTERCHANGE_FORMAT_LENGTH);
		
		metaData.setExifApertureValue(getDoubleTagValue(tagAndValueMappings, apertureValueTag));
		metaData.setExifCameraModel(cameraModel);
		metaData.setExifDateTime(getDateTimeOriginalTagValue(tagAndValueMappings, dateTimeOriginalTag));
		metaData.setExifISOValue(getIntegerTagValue(tagAndValueMappings,isoSpeedRatingsTag));
		metaData.setExifPictureHeight(getIntegerTagValue(tagAndValueMappings, pixelYDimensionTag));
		metaData.setExifPictureWidth(getIntegerTagValue(tagAndValueMappings, pixelXDimensionTag));
		metaData.setExifShutterSpeed(getShutterSpeedTagValue(tagAndValueMappings, shutterSpeedValueTag));
		metaData.setThumbNailOffset(getIntegerTagValue(tagAndValueMappings, jpegInterchangeFormatTag));
		metaData.setThumbNailLength(getIntegerTagValue(tagAndValueMappings, jpegInterchangeFormatLengthTag));
	}
	
	private static int getIntegerTagValue(Map<String, String> tagAndValueMappings, String tag) {
		if (tagAndValueMappings.get(tag) == null) {
			return -1;
		} else {
			return Integer.parseInt(removeNonIntegerCharacters(tagAndValueMappings.get(tag)));
		}
	}
	
	private static double getDoubleTagValue(Map<String, String> tagAndValueMappings, String tag) {
		if (tagAndValueMappings.get(tag) == null) {
			return -1;
		} else {
			NumberFormat fmt = NumberFormat.getInstance();
			Number number;
			try {
				number = fmt.parse(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters(tagAndValueMappings.get(tag)));
			} catch (ParseException e) {
				Logger logger = Logger.getInstance();
				logger.logERROR("Could not parse String: " + StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters(tagAndValueMappings.get(tag)) + " to a double value");
				logger.logERROR(e);
				
				return -1;
			}
			return number.doubleValue();
		}
	}
	
	private static ShutterSpeed getShutterSpeedTagValue(Map<String, String> tagAndValueMappings, String tag) {
		try {
			 return new ShutterSpeed(tagAndValueMappings.get(tag));
		} catch (ShutterSpeedException e) {
			return null;
		}
	}
	
	private static Date getDateTimeOriginalTagValue(Map<String, String> tagAndValueMappings, String tag) {
		String dateString = tagAndValueMappings.get(tag);
		if (dateString == null) {
			return null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			try {
				return sdf.parse(dateString);
			} catch (ParseException e) {
//				TODO: Add logging
				return null;
			}
		}
	}

	private static String removeNonIntegerCharacters(String stringValue) {
		stringValue = stringValue.trim();
		
		StringBuilder allIntegerCharacters = new StringBuilder();
		String subString = "";
		
		for (int i = 0; i < stringValue.length(); i++) {
			subString = stringValue.substring(i, i + 1);
			try {
				Integer.parseInt(subString);
				allIntegerCharacters.append(subString);
			} catch (Exception e) {
				if (allIntegerCharacters.length() > 0) {
					break;
				}
			}
		}
		return allIntegerCharacters.toString();
	}
}
