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
package moller.javapeg.program.enumerations.xml;

import java.util.HashMap;
import java.util.Map;

public enum ImageMetaDataDataBaseItemElement implements IXmlElement {

    JAVAPEG_IMAGE_META_DATA_DATA_BASE("javapeg-image-meta-data-data-base"),
    FILE("file"),
    IMAGE("image"),
    JAVAPEG_ID("javapeg-id"),
    CATEGORIES("categories"),
    RATING("rating"),
    COMMENT("comment"),
    EXPOSURE_TIME("exposure-time"),
    PICTURE_WIDTH("picture-width"),
    PICTURE_HEIGHT("picture-height"),
    ISO_VALUE("iso-value"),
    DATE_TIME("date-time"),
    CAMERA_MODEL("camera-model"),
    F_NUMBER("f-number"),
    EXIF_META_DATA("exif-meta-data"),
    NO_OPERATION("");

    private String elementValue;

    ImageMetaDataDataBaseItemElement(String elementValue) {
        this.elementValue = elementValue;
    }

    @Override
    public String getElementValue() {
        return elementValue;
    }

    public static ImageMetaDataDataBaseItemElement getEnum(String strVal) {
        if(!strValMap.containsKey(strVal)) {
            return NO_OPERATION;
        }
        return strValMap.get(strVal);
    }

    private static final Map<String, ImageMetaDataDataBaseItemElement> strValMap = new HashMap<>();

    static {
        for(final ImageMetaDataDataBaseItemElement en : ImageMetaDataDataBaseItemElement.values()) {
            strValMap.put(en.elementValue, en);
        }
    }
}
