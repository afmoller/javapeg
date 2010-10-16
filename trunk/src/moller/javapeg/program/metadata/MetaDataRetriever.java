package moller.javapeg.program.metadata;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import moller.util.datatype.ShutterSpeed;
import moller.util.datatype.ShutterSpeed.ShutterSpeedException;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;

// Klassdefinition
public class MetaDataRetriever {

//	// Konstruktor
//	public MetaDataRetriever (File imageFile) {
//		
//		Config conf = Config.getInstance(); 
//
//		try {
//			// Läsa in filens metadata (Exif)
//			Metadata metadata = JpegMetadataReader.readMetadata(imageFile);
//
//			md = new MetaData();
//			md.setFileName(imageFile.getName());
//
//			Iterator<?> directories = metadata.getDirectoryIterator();
//
//			while (directories.hasNext()) {
//				Directory directoryExif = (Directory)directories.next();
//				Iterator<?> tags = directoryExif.getTagIterator();
//
//				/***
//				 * Iterera igenom de Exif-taggar som finns lagrade i bilden och
//				 * spara de värden i vilka taggar som matchar med vilkoren i
//				 * nedanstående if-satser.
//				 **/
//				while (tags.hasNext()) {
//					Tag tag = (Tag)tags.next();
//					if(tag.toString().indexOf("Unknown tag") == -1){
//
//						// DateAndTime
//						// 0x0132 = Exif.Image.DateTime          (Canon)
//						// 0x9003 = Exif.Photo.DateTimeOriginal  (Fujifilm)
//						if(tag.getTagTypeHex().equals("0x0132") || tag.getTagTypeHex().equals("0x9003")){
//							try {
//
//								String tagValue = "";
//
//								// Fix för bugg i kameran Fujifilm SP-2500 vad det gäller taggen 0x9003"
//								if(md.getExifCameraModel().equals("SP-2500")) {
//									if(tag.getTagTypeHex().equals("0x0132")) {
//
//										tagValue = tag.getDescription();
//
//										md.setExifDate(tagValue.substring(0, tagValue.indexOf(" ")));
//										md.setExifTime(tagValue.substring(tagValue.indexOf(" "), tagValue.length()));
//									}
//								}
//								// För alla andra kameror
//								else {
//
//									tagValue = tag.getDescription();
//
//									md.setExifDate(tagValue.substring(0, tagValue.indexOf(" ")));
//									md.setExifTime(tagValue.substring(tagValue.indexOf(" "), tagValue.length()));
//								}
//							} catch (Exception ex) {
//							}
//						}
//
//						if(tag.getTagName().equals("Model")){
//							try {
//								md.setExifCameraModel(tag.getDescription());
//
//								int maxLength = conf.getIntProperty("rename.maximum.length.camera-model"); 
//								
//								if( maxLength > 0) {
//									if(md.getExifCameraModel().length() > maxLength)
//										md.setExifCameraModel(md.getExifCameraModel().substring(0,maxLength));
//								}
//							} catch (Exception ex) {
//							}
//						}
//
//						// Shutter speed value / Exposure time
//						// 0x829a = Exif.Photo.ExposureTime      (HP)
//						// 0x9201 = Exif.Photo.ShutterSpeedValue (Canon)
//						if(tag.getTagTypeHex().equals("0x9201") || tag.getTagTypeHex().equals("0x829a")){
//							try {
//								md.setExifShutterSpeed(tag.getDescription());
//							} catch (Exception ex) {
//							}
//						}
//
//						// "ISO Speed Ratings" = Fujifilm FinePix S5000
//						// "Iso"			   = Canon PowerShot A95
//						// "ISO"               = Nikon
//						// 0xa215 = Exif.Photo.ExposureIndex (Kodak)
//						if(tag.getTagName().equals("ISO Speed Ratings")
//						|| tag.getTagTypeHex().equals("0xa215") || tag.getTagName().equals("ISO") || tag.getTagTypeHex().equals("0x8827")){
//							try {
//								md.setExifISOValue(tag.getDescription());
//							} catch (Exception ex) {
//							}
//						}
//
//						if(tag.getTagName().equals("Exif Image Width")){
//							try {
//								md.setExifPictureWidth(removeNonIntegerCharacters(tag.getDescription()));
//							} catch (Exception ex) {
//							}
//						}
//
//						if(tag.getTagName().equals("Exif Image Height")){
//							try {
//								md.setExifPictureHeight(removeNonIntegerCharacters(tag.getDescription()));
//							} catch (Exception ex) {
//							}
//						}
//
//						// Aperture Value / F Number
//						// 0x829d = Exif.Photo.FNumber       (HP)
//						// 0x9202 = Exif.Photo.ApertureValue (Minolta)
//						if(tag.getTagTypeHex().equals("0x829d") || tag.getTagTypeHex().equals("0x9202")){
//							try {
//								md.setExifApertureValue(tag.getDescription());
//							} catch (Exception ex) {
//							}
//						}
//
//						if(tag.getTagName().equals("Thumbnail Offset")){
//							try {
//								String temp = tag.getDescription();
//								temp = temp.substring(0, temp.indexOf(" "));
//								md.setThumbNailOffset(Integer.parseInt(temp));
//							} catch (Exception ex) {
//							}
//						}
//
//						if(tag.getTagName().equals("Thumbnail Length")){
//							try {
//								String temp = tag.getDescription();
//								temp = temp.substring(0, temp.indexOf(" "));
//								md.setThumbNailLength(Integer.parseInt(temp));
//							} catch (Exception ex) {
//							}
//						}
//					}
//				}
//				if (directoryExif.hasErrors()) {
//					Iterator<?> errors = directoryExif.getErrors();
//
//					while (errors.hasNext()) {
//						System.out.println("ERROR: " + errors.next());
//					}
//				}
//			}
//		} catch (JpegProcessingException jpex) {
//			// Inte nödvändigt att göra något här då koden precis här nedanför tar hand om det
//			// faktum att varibalerna inte fått något värde.
//		}
//
//		/****
//		 * Om det av någon anledning saknas värden för någon Exiftagg så sätts värdet till "na"
//		 * Om taggarna inte saknar värde så kontrolleras värdet så att det inte innehåller några
//		 * otillåtna tecken.
//		 **/
//		if(md.getExifDate().equals("")) {
//			if(conf.getBooleanProperty("rename.use.lastmodified.date")) {
//				FileUtil.getLatestModifiedDate(imageFile);
//			} else {
//				md.setExifDate("na");	
//			}
//		} else {
//			md.setExifDate(filterString(md.getExifDate(), "exifDateAndTime"));
//			md.setExifDate(md.getExifDate().trim());
//		}
//
//		if(md.getExifTime().equals("")) {
//			if(conf.getBooleanProperty("rename.use.lastmodified.time")) {
//				FileUtil.getLatestModifiedTime(imageFile);
//			} else {
//				md.setExifTime("na");
//			}
//		} else {
//			md.setExifTime(filterString(md.getExifTime(), "exifDateAndTime"));
//			md.setExifTime(md.getExifTime().trim());
//		}
//
//		if(md.getExifCameraModel().equals("")) {
//			md.setExifCameraModel("na");
//		} else {
//			md.setExifCameraModel(filterString(md.getExifCameraModel(), "exifCameraModel"));
//			md.setExifCameraModel(md.getExifCameraModel().trim());
//		}
//
//		if(md.getExifShutterSpeed().equals("")) {
//			md.setExifShutterSpeed("na");
//		} else {
//			md.setExifShutterSpeed(filterString(md.getExifShutterSpeed(), "exifShutterSpeed"));
//			md.setExifShutterSpeed(md.getExifShutterSpeed().trim());
//		}
//
//		if(md.getExifISOValue().equals("")) {
//			md.setExifISOValue("na");
//		} else {
//			md.setExifISOValue(filterString(md.getExifISOValue(), "exifISOValue"));
//			md.setExifISOValue(md.getExifISOValue().trim());
//		}
//
//		if(md.getExifPictureWidth().equals("")) {
//			md.setExifPictureWidth("na");
//		} else {
//			md.setExifPictureWidth(filterString(md.getExifPictureWidth(), "exifPictureWidth"));
//			md.setExifPictureWidth(md.getExifPictureWidth().trim());
//		}
//
//		if(md.getExifPictureHeight().equals("")) {
//			md.setExifPictureHeight("na");
//		} else {
//			md.setExifPictureHeight(filterString(md.getExifPictureHeight(), "exifPictureHeight"));
//			md.setExifPictureHeight(md.getExifPictureHeight().trim());
//		}
//
//		if(md.getExifApertureValue().equals("")) {
//			md.setExifApertureValue("na");
//		} else {
//			md.setExifApertureValue(filterString(md.getExifApertureValue(), "exifApertureValue"));
//			md.setExifApertureValue(md.getExifApertureValue().trim());
//		}
//		md.setFileObject(imageFile);
//	}
	
	
	public static MetaData getMetaData(File imageFile) {
		
		MetaData md = new MetaData();
		md.setFileName(imageFile.getName());
		
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

	/****
	 * Metod för att rensa en sträng från otillåtna tecken.
	 * De tecken som rensas bort är de som inte är tillåtna
	 * i en sökväg till en fil..
	 **/
	private static String filterString(String theStringToFilter, String whichStringToFilter){

		char [] stringToChar = theStringToFilter.toCharArray();

		for(int i = 0; i < stringToChar.length; i++) {
			switch (stringToChar[i]) {
				case 47: // "/"
					if(!whichStringToFilter.equals("exifShutterSpeed")) {
						stringToChar[i] = ' ';
					}
					break;
				case 92: // "\"
					stringToChar[i] = ' ';
					break;
				case 58: // ":"
					if(!whichStringToFilter.equals("exifDateAndTime")) {
						stringToChar[i] = ' ';
					}
					break;
				case 60: // "<"
					stringToChar[i] = ' ';
					break;
				case 62: // ">"
					stringToChar[i] = ' ';
					break;
				case 124: // "|"
					stringToChar[i] = ' ';
					break;
				case 42: // "*"
					stringToChar[i] = ' ';
					break;
				case 63: // "?"
					stringToChar[i] = ' ';
					break;
				case 34: // """
					stringToChar[i] = ' ';
					break;
			}
		}
		return String.valueOf(stringToChar);
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