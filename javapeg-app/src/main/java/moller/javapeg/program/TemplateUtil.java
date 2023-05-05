/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.applicationmode.rename.RenameImages;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.metadata.MetaDataUtil;
import moller.util.io.FileUtil;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TemplateUtil {

    public static String convertTemplateToString(String stringToConvert, MetaData theMetaData){

        Language lang = Language.getInstance();
        Configuration configuration = Config.getInstance().get();

        RenameImages renameImages = configuration.getRenameImages();

        if(stringToConvert.indexOf("%" + lang.get("variable.pictureDateVariable") + "%") > -1){
            String date = "";
            if (MetaDataUtil.hasValue(theMetaData.getExifDateAsString())) {
                date = theMetaData.getExifDateAsString();
                date = date.replaceAll(":", "-");
            } else {
                if(renameImages.getUseLastModifiedDate()) {
                    date = FileUtil.getLatestModifiedDate(theMetaData.getFileObject());
                } else {
                    date = "no value";
                }
            }
            stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.pictureDateVariable") + "%", date);
        }
        if(stringToConvert.indexOf("%" + lang.get("variable.pictureTimeVariable") + "%") > -1){
            String time = "";
            if (MetaDataUtil.hasValue(theMetaData.getExifTimeAsString())) {
                time = theMetaData.getExifTimeAsString();
                time = time.replaceAll(":", "-");
            } else {
                if(renameImages.getUseLastModifiedTime()) {
                    time = FileUtil.getLatestModifiedTime(theMetaData.getFileObject());
                } else {
                    time = "no value";
                }
            }
            stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.pictureTimeVariable") + "%", time);
        }
        if(stringToConvert.indexOf("%" + lang.get("variable.cameraModelVariable") + "%") > -1){
            String cameraModel = theMetaData.getExifCameraModel();
            if (!MetaDataUtil.hasValue(cameraModel)) {
                cameraModel = "no value";
            }

            // Limit the length of the Camera Model String according to a
            // configurable parameter.
            int maxLength = renameImages.getCameraModelNameMaximumLength();

            if( maxLength > 0) {
                if(cameraModel.length() > maxLength)
                    cameraModel = cameraModel.substring(0,maxLength);
            }
            stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.cameraModelVariable") + "%", cameraModel);
        }
        if(stringToConvert.indexOf("%" + lang.get("variable.shutterSpeedVariable") + "%") > -1){
            String exposureTime = "";
            if (MetaDataUtil.hasValue(theMetaData.getExifExposureTime().toString())) {
                exposureTime = theMetaData.getExifExposureTime().toString();
                exposureTime = exposureTime.replaceAll("/", "_");
            } else {
                exposureTime = "no value";
            }
            stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.shutterSpeedVariable") + "%", exposureTime);
        }
        if(stringToConvert.indexOf("%" + lang.get("variable.isoValueVariable") + "%") > -1){
            String isoString = "";
            int iso = theMetaData.getExifISOValue();
            if (!MetaDataUtil.hasValue(iso)) {
                isoString = "no value";
            } else {
                isoString = Integer.toString(iso);
            }
            stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.isoValueVariable") + "%", isoString);
        }
        if(stringToConvert.indexOf("%" + lang.get("variable.pictureWidthVariable") + "%") > -1){
            String pictureWidthString = "";
            int pictureWidth = theMetaData.getExifPictureWidth();
            if (!MetaDataUtil.hasValue(pictureWidth)) {
                pictureWidthString = "no value";
            } else {
                pictureWidthString = Integer.toString(pictureWidth);
            }
            stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.pictureWidthVariable") + "%", pictureWidthString);
        }
        if(stringToConvert.indexOf("%" + lang.get("variable.pictureHeightVariable") + "%") > -1){
            String pictureHeightString = "";
            int pictureHeight = theMetaData.getExifPictureHeight();
            if (!MetaDataUtil.hasValue(pictureHeight)) {
                pictureHeightString = "no value";
            } else {
                pictureHeightString = Integer.toString(pictureHeight);
            }
            stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.pictureHeightVariable") + "%", pictureHeightString);
        }
        if(stringToConvert.indexOf("%" + lang.get("variable.apertureValueVariable") + "%") > -1){
            String apertureString = "";
            double fNumber = theMetaData.getExifFNumber();
            if (!MetaDataUtil.hasValue(fNumber)) {
                apertureString = "no value";
            } else {
                apertureString = Double.toString(fNumber);
            }
            stringToConvert = stringToConvert.replaceAll("%" + lang.get("variable.apertureValueVariable") + "%", apertureString);
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

        return filterString(stringToConvert);
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

    /****
     * Metod för att rensa en sträng från otillåtna tecken.
     * De tecken som rensas bort är de som inte är tillåtna
     * i en sökväg till en fil..
     **/
    private static String filterString(String theStringToFilter){

        char [] stringToChar = theStringToFilter.toCharArray();

        for(int i = 0; i < stringToChar.length; i++) {
            switch (stringToChar[i]) {
                case 47: // "/"
                    stringToChar[i] = ' ';
                    break;
                case 92: // "\"
                    stringToChar[i] = ' ';
                    break;
                case 58: // ":"
                    stringToChar[i] = ' ';
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
}
