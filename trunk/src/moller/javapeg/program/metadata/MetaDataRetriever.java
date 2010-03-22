package moller.javapeg.program.metadata;
/**
 * This class was created : 2007-01-17 by Fredrik Möller
 * Latest changed         : 2007-02-27 by Fredrik Möller
 *                        : 2007-02-28 by Fredrik Möller
 *                        : 2009-08-10 by Fredrik Möller
 *                        : 2010-01-16 by Fredrik Möller
 */

import java.io.File;
import java.util.Iterator;

import moller.javapeg.program.config.Config;
import moller.util.io.FileUtil;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

// Klassdefinition
public class MetaDataRetriever {

	// Instansvariabler
	private MetaData md;

	// Konstruktor
	public MetaDataRetriever (File imageFile) {
		
		Config conf = Config.getInstance(); 

		try {
			// Läsa in filens metadata (Exif)
			Metadata metadata = JpegMetadataReader.readMetadata(imageFile);

			md = new MetaData();
			md.setFileName(imageFile.getName());

			Iterator<?> directories = metadata.getDirectoryIterator();

			while (directories.hasNext()) {
				Directory directoryExif = (Directory)directories.next();
				Iterator<?> tags = directoryExif.getTagIterator();

				/***
				 * Iterera igenom de Exif-taggar som finns lagrade i bilden och
				 * spara de värden i vilka taggar som matchar med vilkoren i
				 * nedanstående if-satser.
				 **/
				while (tags.hasNext()) {
					Tag tag = (Tag)tags.next();
					if(tag.toString().indexOf("Unknown tag") == -1){

						// DateAndTime
						// 0x0132 = Exif.Image.DateTime          (Canon)
						// 0x9003 = Exif.Photo.DateTimeOriginal  (Fujifilm)
						if(tag.getTagTypeHex().equals("0x0132") || tag.getTagTypeHex().equals("0x9003")){
							try {

								String tagValue = "";

								// Fix för bugg i kameran Fujifilm SP-2500 vad det gäller taggen 0x9003"
								if(md.getExifCameraModel().equals("SP-2500")) {
									if(tag.getTagTypeHex().equals("0x0132")) {

										tagValue = tag.getDescription();

										md.setExifDate(tagValue.substring(0, tagValue.indexOf(" ")));
										md.setExifTime(tagValue.substring(tagValue.indexOf(" "), tagValue.length()));
									}
								}
								// För alla andra kameror
								else {

									tagValue = tag.getDescription();

									md.setExifDate(tagValue.substring(0, tagValue.indexOf(" ")));
									md.setExifTime(tagValue.substring(tagValue.indexOf(" "), tagValue.length()));
								}
							} catch (Exception ex) {
							}
						}

						if(tag.getTagName().equals("Model")){
							try {
								md.setExifCameraModel(tag.getDescription());

								int maxLength = conf.getIntProperty("rename.maximum.length.camera-model"); 
								
								if( maxLength > 0) {
									if(md.getExifCameraModel().length() > maxLength)
										md.setExifCameraModel(md.getExifCameraModel().substring(0,maxLength));
								}
							} catch (Exception ex) {
							}
						}

						// Shutter speed value / Exposure time
						// 0x829a = Exif.Photo.ExposureTime      (HP)
						// 0x9201 = Exif.Photo.ShutterSpeedValue (Canon)
						if(tag.getTagTypeHex().equals("0x9201") || tag.getTagTypeHex().equals("0x829a")){
							try {
								md.setExifShutterSpeed(tag.getDescription());
							} catch (Exception ex) {
							}
						}

						// "ISO Speed Ratings" = Fujifilm FinePix S5000
						// "Iso"			   = Canon PowerShot A95
						// "ISO"               = Nikon
						// 0xa215 = Exif.Photo.ExposureIndex (Kodak)
						if(tag.getTagName().equals("Iso") || tag.getTagName().equals("ISO Speed Ratings")
						|| tag.getTagTypeHex().equals("0xa215") || tag.getTagName().equals("ISO")){
							try {
								md.setExifISOValue(tag.getDescription());
							} catch (Exception ex) {
							}
						}

						if(tag.getTagName().equals("Exif Image Width")){
							try {
								md.setExifPictureWidth(removeNonIntegerCharacters(tag.getDescription()));
							} catch (Exception ex) {
							}
						}

						if(tag.getTagName().equals("Exif Image Height")){
							try {
								md.setExifPictureHeight(removeNonIntegerCharacters(tag.getDescription()));
							} catch (Exception ex) {
							}
						}

						// Aperture Value / F Number
						// 0x829d = Exif.Photo.FNumber       (HP)
						// 0x9202 = Exif.Photo.ApertureValue (Minolta)
						if(tag.getTagTypeHex().equals("0x829d") || tag.getTagTypeHex().equals("0x9202")){
							try {
								md.setExifApertureValue(tag.getDescription());
							} catch (Exception ex) {
							}
						}

						if(tag.getTagName().equals("Thumbnail Offset")){
							try {
								String temp = tag.getDescription();
								temp = temp.substring(0, temp.indexOf(" "));
								md.setThumbNailOffset(Integer.parseInt(temp));
							} catch (Exception ex) {
							}
						}

						if(tag.getTagName().equals("Thumbnail Length")){
							try {
								String temp = tag.getDescription();
								temp = temp.substring(0, temp.indexOf(" "));
								md.setThumbNailLength(Integer.parseInt(temp));
							} catch (Exception ex) {
							}
						}
					}
				}
				if (directoryExif.hasErrors()) {
					Iterator<?> errors = directoryExif.getErrors();

					while (errors.hasNext()) {
						System.out.println("ERROR: " + errors.next());
					}
				}
			}
		} catch (JpegProcessingException jpex) {
			// Inte nödvändigt att göra något här då koden precis här nedanför tar hand om det
			// faktum att varibalerna inte fått något värde.
		}

		/****
		 * Om det av någon anledning saknas värden för någon Exiftagg så sätts värdet till "na"
		 * Om taggarna inte saknar värde så kontrolleras värdet så att det inte innehåller några
		 * otillåtna tecken.
		 **/
		if(md.getExifDate().equals("")) {
			if(conf.getBooleanProperty("rename.use.lastmodified.date")) {
				FileUtil.getLatestModifiedDate(imageFile);
			} else {
				md.setExifDate("na");	
			}
		} else {
			md.setExifDate(filterString(md.getExifDate(), "exifDateAndTime"));
			md.setExifDate(md.getExifDate().trim());
		}

		if(md.getExifTime().equals("")) {
			if(conf.getBooleanProperty("rename.use.lastmodified.time")) {
				FileUtil.getLatestModifiedTime(imageFile);
			} else {
				md.setExifTime("na");
			}
		} else {
			md.setExifTime(filterString(md.getExifTime(), "exifDateAndTime"));
			md.setExifTime(md.getExifTime().trim());
		}

		if(md.getExifCameraModel().equals("")) {
			md.setExifCameraModel("na");
		} else {
			md.setExifCameraModel(filterString(md.getExifCameraModel(), "exifCameraModel"));
			md.setExifCameraModel(md.getExifCameraModel().trim());
		}

		if(md.getExifShutterSpeed().equals("")) {
			md.setExifShutterSpeed("na");
		} else {
			md.setExifShutterSpeed(filterString(md.getExifShutterSpeed(), "exifShutterSpeed"));
			md.setExifShutterSpeed(md.getExifShutterSpeed().trim());
		}

		if(md.getExifISOValue().equals("")) {
			md.setExifISOValue("na");
		} else {
			md.setExifISOValue(filterString(md.getExifISOValue(), "exifISOValue"));
			md.setExifISOValue(md.getExifISOValue().trim());
		}

		if(md.getExifPictureWidth().equals("")) {
			md.setExifPictureWidth("na");
		} else {
			md.setExifPictureWidth(filterString(md.getExifPictureWidth(), "exifPictureWidth"));
			md.setExifPictureWidth(md.getExifPictureWidth().trim());
		}

		if(md.getExifPictureHeight().equals("")) {
			md.setExifPictureHeight("na");
		} else {
			md.setExifPictureHeight(filterString(md.getExifPictureHeight(), "exifPictureHeight"));
			md.setExifPictureHeight(md.getExifPictureHeight().trim());
		}

		if(md.getExifApertureValue().equals("")) {
			md.setExifApertureValue("na");
		} else {
			md.setExifApertureValue(filterString(md.getExifApertureValue(), "exifApertureValue"));
			md.setExifApertureValue(md.getExifApertureValue().trim());
		}
		md.setFileObject(imageFile);
	}

	// Metod för att returnera MetaData-objektet.
	public MetaData getMetaData() {
		return md;
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
	
	private String removeNonIntegerCharacters(String stringValue) {
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

	public void debug() {

		System.out.println("exifDate          : " + md.getExifDate());
		System.out.println("exifTime          : " + md.getExifTime());
		System.out.println("exifCameraModel   : " + md.getExifCameraModel());
		System.out.println("exifShutterSpeed  : " + md.getExifShutterSpeed());
		System.out.println("exifISOValue      : " + md.getExifISOValue());
		System.out.println("exifPictureWidth  : " + md.getExifPictureWidth());
		System.out.println("exifPictureHeight : " + md.getExifPictureHeight());
		System.out.println("exifApertureValue : " + md.getExifApertureValue());
		System.out.println("thumbNailOffset   : " + md.getThumbNailOffset());
		System.out.println("thumbNailLength   : " + md.getThumbNailLength());
	}
}