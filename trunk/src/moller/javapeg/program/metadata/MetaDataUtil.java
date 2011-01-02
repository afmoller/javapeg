package moller.javapeg.program.metadata;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectory;

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
	
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseImageFile(File imageFile) {
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
	
	@SuppressWarnings("unchecked")
	public static int getOrientationTag(File imageFile) {
		
		try{
			Metadata metadata = JpegMetadataReader.readMetadata(imageFile);
			Iterator directories = metadata.getDirectoryIterator();

			while (directories.hasNext()) {
				Directory directory = (Directory)directories.next();
		
				if(directory.containsTag(ExifDirectory.TAG_ORIENTATION)) {
					return directory.getInt(ExifDirectory.TAG_ORIENTATION);
				}
			}
		} catch (JpegProcessingException jpe) {
			System.err.println("error 1a");
			jpe.printStackTrace();
		} catch (MetadataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
}
