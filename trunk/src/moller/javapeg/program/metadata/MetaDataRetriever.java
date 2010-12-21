package moller.javapeg.program.metadata;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import moller.javapeg.program.datatype.ShutterSpeed;
import moller.javapeg.program.datatype.ShutterSpeed.ShutterSpeedException;
import moller.javapeg.program.enumerations.FieldName;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;

// Klassdefinition
public class MetaDataRetriever {
	
	public static MetaData getMetaData(File imageFile) {
		MetaData md = new MetaData();
		md.setFileName(imageFile.getName());
		md.setFileObject(imageFile);
		
		Map<String, String> tagAndValueMappings = parseImageFile(imageFile);
		
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
		
		metaData.setExifApertureValue(tagAndValueMappings.get(apertureValueTag));
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

	@SuppressWarnings("unchecked")
	private static Map<String, String> parseImageFile(File imageFile) {
		Map<String, String> tagsMap = new HashMap<String, String>();
		
		try{
			Metadata metadata = JpegMetadataReader.readMetadata(imageFile);
			Iterator directories = metadata.getDirectoryIterator();

			while (directories.hasNext()) {
				Directory directory = (Directory)directories.next();
				Iterator tags = directory.getTagIterator();

				while (tags.hasNext()) {
					Tag tag = (Tag)tags.next();
					
					if(tag.toString().indexOf("Unknown tag") == -1){
						try {
							tagsMap.put(tag.getTagTypeHex(), tag.getDescription());
						} catch (MetadataException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				if (directory.hasErrors()) {
					Iterator errors = directory.getErrors();
					while (errors.hasNext()) {
//						TODO: Add logging
						System.out.println("ERROR: " + errors.next());
					}
				}
			}
		} catch (JpegProcessingException jpe) {
			System.err.println("error 1a");
			jpe.printStackTrace();
		}
		return tagsMap;
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
