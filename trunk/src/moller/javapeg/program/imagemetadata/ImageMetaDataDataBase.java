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
package moller.javapeg.program.imagemetadata;

import java.util.ArrayList;
import java.util.List;

/**
 * This class encapsulates an unmarshalled version of an image meta data base
 * file: an XML file that contains user entered meta data and meta data
 * automatically retrieved from the Exif part of the image.
 *
 * @author Fredrik
 *
 */
public class ImageMetaDataDataBase {

    /**
     * Which JavaPEG client created this image meta data base file.
     */
    private String javaPEGId;

    /**
     * This list contains {@link ImageMetaDataItem} objects that contains the
     * meta data about all JPEG images that are found in the directory where the
     * meta data base XML file is stored.
     */
    private List<ImageMetaDataItem> imageMetaDataItems;

    public ImageMetaDataDataBase() {
        imageMetaDataItems = new ArrayList<ImageMetaDataItem>();
    }

    public String getJavaPEGId() {
        return javaPEGId;
    }
    public List<ImageMetaDataItem> getImageMetaDataItems() {
        return imageMetaDataItems;
    }
    public void setJavaPEGId(String javaPEGId) {
        this.javaPEGId = javaPEGId;
    }
    public void setImageMetaDataItems(
            List<ImageMetaDataItem> imageMetaDataItems) {
        this.imageMetaDataItems = imageMetaDataItems;
    }

    /**
     * Adds an {@link ImageMetaDataItem} to the list of
     * {@link ImageMetaDataItem} objects that is contained by this data
     * structure.
     *
     * @param imageMetaDataItem
     *            is the {@link ImageMetaDataItem} to add.
     */
    public void addImageMetaDataItem(ImageMetaDataItem imageMetaDataItem) {
        if (imageMetaDataItem != null) {
            imageMetaDataItems.add(imageMetaDataItem);
        }
    }
}
