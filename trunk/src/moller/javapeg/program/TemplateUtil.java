package moller.javapeg.program;
/**
 * This class was created : 2007-02-28 by Fredrik Möller
 * Latest changed         : 2007-03-05 by Fredrik Möller
 *                        : 2007-03-07 by Fredrik Möller
 *                        : 2009-02-22 by Fredrik Möller
 *                        : 2009-03-21 by Fredrik Möller
 */

import java.util.Calendar;
import java.util.GregorianCalendar;

import moller.javapeg.program.language.Language;
import moller.javapeg.program.metadata.MetaData;

public class TemplateUtil {

	public static String convertTemplateToString(String stringToConvert, MetaData theMetaData){

		Language lang = Language.getInstance();
		
		if(stringToConvert.indexOf("%" + lang.get("variable.pictureDateVariable") + "%") > -1){
			String date = theMetaData.getExifDate();
			date = date.replaceAll(":", "-");
			stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.pictureDateVariable") + "%", date);
		}
		if(stringToConvert.indexOf("%" + lang.get("variable.pictureTimeVariable") + "%") > -1){
			String time = theMetaData.getExifTime();
			time = time.replaceAll(":", "-");
			stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.pictureTimeVariable") + "%", time);
		}
		if(stringToConvert.indexOf("%" + lang.get("variable.cameraModelVariable") + "%") > -1){
			stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.cameraModelVariable") + "%", theMetaData.getExifCameraModel());
		}
		if(stringToConvert.indexOf("%" + lang.get("variable.shutterSpeedVariable") + "%") > -1){
			String shutterSpeed = "";
			shutterSpeed = theMetaData.getExifShutterSpeed().replaceAll("/", "_");
			stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.shutterSpeedVariable") + "%", shutterSpeed);
		}
		if(stringToConvert.indexOf("%" + lang.get("variable.isoValueVariable") + "%") > -1){
			stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.isoValueVariable") + "%", theMetaData.getExifISOValue());
		}
		if(stringToConvert.indexOf("%" + lang.get("variable.pictureWidthVariable") + "%") > -1){
			stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.pictureWidthVariable") + "%", theMetaData.getExifPictureWidth());
		}
		if(stringToConvert.indexOf("%" + lang.get("variable.pictureHeightVariable") + "%") > -1){
			stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.pictureHeightVariable") + "%", theMetaData.getExifPictureHeight());
		}
		if(stringToConvert.indexOf("%" + lang.get("variable.apertureValueVariable") + "%") > -1){
			stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.apertureValueVariable") + "%", theMetaData.getExifApertureValue());
		}
		if(stringToConvert.indexOf("%" + lang.get("variable.dateOftodayVariable") + "%") > -1){
			Calendar cal = new GregorianCalendar();

			String date = "";

			String month = Integer.toString((cal.get(Calendar.MONTH) + 1));
			if(month.length() == 1) {
				month = "0" + month;
			}

			String dayOfMonth = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
			if(dayOfMonth.length() == 1) {
				dayOfMonth = "0" + dayOfMonth;
			}

			date = cal.get(Calendar.YEAR) + "-" + month + "-" + dayOfMonth;
			stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.dateOftodayVariable") + "%", date);
		}
		/**
		 * The if statement below was added by Angel Bueno. It gives the 
		 * possibility to use a variable that inserts the original filename 
		 * into the resulting filename when the filename template is parsed.
		 */
		if(stringToConvert.indexOf("%" + lang.get("variable.sourceNameVariable") + "%") > -1){
			String fileName = theMetaData.getFileName();
			if (fileName.toLowerCase().endsWith(".jpeg")) {
				fileName = fileName.substring(0, fileName.length()-5);				
			} else {
				fileName = fileName.substring(0, fileName.length()-4);
			}
			stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.sourceNameVariable") + "%", fileName);
		}

		return stringToConvert;
	}

	/***
	 * Metod för att kontrollera så att undermappsnamner endast innehåller tillåtna variabler.
	 **/
	public static boolean subFolderNameIsValid(String subFolderName) {
		Language lang = Language.getInstance();
		
		if(subFolderName.indexOf("%" + lang.get("variable.pictureTimeVariable") + "%") > -1)
			return false;
		if(subFolderName.indexOf("%" + lang.get("variable.shutterSpeedVariable") + "%") > -1)
			return false;
		if(subFolderName.indexOf("%" + lang.get("variable.isoValueVariable") + "%") > -1)
			return false;
		if(subFolderName.indexOf("%" + lang.get("variable.pictureWidthVariable") + "%") > -1)
			return false;
		if(subFolderName.indexOf("%" + lang.get("variable.pictureHeightVariable") + "%") > -1)
			return false;
		if(subFolderName.indexOf("%" + lang.get("variable.apertureValueVariable") + "%") > -1)
			return false;
		if(subFolderName.indexOf("%" + lang.get("variable.sourceNameVariable") + "%") > -1)
			return false;

		return true;
	}
}