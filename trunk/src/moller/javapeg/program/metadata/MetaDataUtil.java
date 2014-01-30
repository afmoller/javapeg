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
package moller.javapeg.program.metadata;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * This method creates a string which is supposed to be displayed as an tool
     * tip when a thumbnail is hovered. This method produces two different tool
     * tips: a standard and an extended. The standard displays the name of the
     * file and the extended version displays an HTML table with various meta
     * data about the image file.
     *
     * @param jpgFile
     *            is the file to display information about in the tool tip.
     * @param state
     *            defines the type of tool tip: standard or extended.
     * @return a string representing a tool tip for the file specified in the
     *         jpgFile parameter to this method.
     */
    public static String getToolTipText(File jpgFile, String state) {

        Language lang = Language.getInstance();

        MetaData md = MetaDataRetriever.getMetaData(jpgFile);

        String noValue = lang.get("common.missing.value");

        if (state.equals("2")) {
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
        } catch (JpegProcessingException | IOException ex) {
            logger.logERROR("Could not read meata data from file: " + imageFile.getAbsolutePath());
            logger.logERROR(ex);
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
        } catch (JpegProcessingException | IOException ex) {
            logger.logERROR("Could not read meata data from file: " + imageFile.getAbsolutePath());
            logger.logERROR(ex);
        } catch (MetadataException mdex) {
            logger.logERROR("Could not get value for orientation tag (" + ExifIFD0Directory.TAG_ORIENTATION + ") in file: " + imageFile.getAbsolutePath());
            logger.logERROR(mdex);
        }
        return -1;
    }
}
