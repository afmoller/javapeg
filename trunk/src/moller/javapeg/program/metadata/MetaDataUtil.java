package moller.javapeg.program.metadata;
/**
 * This class was created : 2009-03-14 by Fredrik Möller
 * Latest changed         : 2009-09-13 by Fredrik Möller
 *                        : 2009-09-14 by Fredrik Möller
 */

import java.io.File;
import java.util.List;
import java.util.Vector;

import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.Language;

public class MetaDataUtil {

	public static String getToolTipText(File jpgFile) {
		
		Language lang = Language.getInstance();
		
		MetaData md = MetaDataRetriever.getMetaData(jpgFile);

		return "<html>" +
		         "<table>" +
		           createTableRow("Filnamn", jpgFile.getName()) +
		           createTableRow(lang.get("variable.pictureDate"), md.getExifDateAsString()) +
		           createTableRow(lang.get("variable.pictureTime"), md.getExifTimeAsString()) +
		           createTableRow(lang.get("variable.cameraModel"), md.getExifCameraModel()) +
		           createTableRow(lang.get("variable.shutterSpeed"), md.getExifShutterSpeed().toString()) +
		           createTableRow(lang.get("variable.isoValue"), Integer.toString(md.getExifISOValue())) +
		           createTableRow(lang.get("variable.pictureWidth"), Integer.toString(md.getExifPictureWidth())) +
		           createTableRow(lang.get("variable.pictureHeight"), Integer.toString(md.getExifPictureHeight())) +
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
			temp.addElement(metaData.getExifDateAsString());
			temp.addElement(metaData.getExifTimeAsString());
			temp.addElement(metaData.getExifCameraModel());
			temp.addElement(metaData.getExifShutterSpeed().toString());
			temp.addElement(Integer.toString(metaData.getExifISOValue()));
			temp.addElement(Integer.toString(metaData.getExifPictureWidth()));
			temp.addElement(Integer.toString(metaData.getExifPictureHeight()));
			temp.addElement(metaData.getExifApertureValue());

			metaDataVector.addElement(temp);
		}
		return metaDataVector;
	}
}