package moller.javapeg.program.metadata;
/**
 * This class was created : 2009-03-14 by Fredrik Möller
 * Latest changed         : 2009-09-13 by Fredrik Möller
 *                        : 2009-09-14 by Fredrik Möller
 */

import java.io.File;
import java.util.List;
import java.util.Vector;

import moller.javapeg.program.ApplicationContext;
import moller.javapeg.program.language.Language;

public class MetaDataUtil {

	public static String getToolTipText(File jpgFile) {
		
		Language lang = Language.getInstance();
		
		MetaDataRetriever mdr = new MetaDataRetriever(jpgFile);
		MetaData md = mdr.getMetaData();

		return "<html>" +
		         "<table>" +
		           createTableRow("Filnamn", jpgFile.getName()) +
		           createTableRow(lang.get("variable.pictureDate"), md.getExifDate()) +
		           createTableRow(lang.get("variable.pictureTime"), md.getExifTime()) +
		           createTableRow(lang.get("variable.cameraModel"), md.getExifCameraModel()) +
		           createTableRow(lang.get("variable.shutterSpeed"), md.getExifShutterSpeed()) +
		           createTableRow(lang.get("variable.isoValue"), md.getExifISOValue()) +
		           createTableRow(lang.get("variable.pictureWidth"), md.getExifPictureWidth()) +
		           createTableRow(lang.get("variable.pictureHeight"), md.getExifPictureHeight()) +
		           createTableRow(lang.get("variable.apertureValue"), md.getExifApertureValue()) +
		         "</table>" +
			   "</html>";
	}
	
	private static String createTableRow(String metaDataKey, String metaDataValue) {
		return "<tr>" +
		         "<td>" + metaDataKey + "</td>" +
		         "<td>" + ": " + metaDataValue + "</td>" +
	           "</tr>";
	}
	
	public static Vector<Vector<String>> getMetaData() {

		Vector<Vector<String>> metaDataVector = new Vector<Vector<String>>();

		List<MetaData> metaDataObjects = ApplicationContext.getInstance().getMetaDataObjects();			
				
		for (MetaData metaData : metaDataObjects) {
			Vector<String> temp= new Vector<String>();
			
			temp.addElement(metaData.getFileName());
			temp.addElement(metaData.getExifDate());
			temp.addElement(metaData.getExifTime());
			temp.addElement(metaData.getExifCameraModel());
			temp.addElement(metaData.getExifShutterSpeed());
			temp.addElement(metaData.getExifISOValue());
			temp.addElement(metaData.getExifPictureWidth());
			temp.addElement(metaData.getExifPictureHeight());
			temp.addElement(metaData.getExifApertureValue());

			metaDataVector.addElement(temp);
		}
		return metaDataVector;
	}
	
	public static MetaData getMetaData(File jpegFile) {
		MetaDataRetriever mdr = new MetaDataRetriever(jpegFile);
		return mdr.getMetaData();
	}
}