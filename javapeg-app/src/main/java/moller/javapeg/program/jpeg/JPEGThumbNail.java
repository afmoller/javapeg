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
package moller.javapeg.program.jpeg;

import moller.javapeg.program.metadata.MetaData;

/**
 * This class is an container for the byte content of an thumbnail in JPEG
 * format. It can also hold meta data in the form of an {@link MetaData} object
 * from the original JPEG image.
 *
 * @author Fredrik
 *
 */
public class JPEGThumbNail {

    private final MetaData metaData;
    private byte [] thumbNailData;

    /**
     * Default constructor
     *
     * @param metaData contains the meta data which is associated with the
     *        original image.
     */
    public JPEGThumbNail (MetaData metaData) {
        thumbNailData = null;
        this.metaData = metaData;
    }

    /**
     * Constructor
     *
     * @param thumbNailData is an array of bytes containing the actual
     *        content of the JPEG thumb nail.
     */
    public JPEGThumbNail (byte [] thumbNailData) {
        this.thumbNailData = thumbNailData;
        metaData = null;
    }

    /**
     * Retrieve the Thumbnail as a byte array
     *
     * @return the thumbnail in form of an byte array.
     */
    public byte[] getThumbNailData() {
        return thumbNailData;
    }

    /**
     * Get the thumbnail siz in bytes.
     *
     * @return the size in bytes for this thumbnail as in int.
     */
    public int getThumbNailSize() {
        return thumbNailData.length;
    }

    /**
     * Set the thumbanil as an byte array.
     *
     * @param thumbNailData
     *            contains the bytes of the thumbnail to set to this object.
     */
    public void setThumbNailData(byte[] thumbNailData) {
        this.thumbNailData = thumbNailData;
    }

    /**
     * Retrieves a {@link MetaData} object which contains meta information about the original image.
     * @return
     */
    public MetaData getMetaData() {
        return metaData;
    }

    /**
     * Prints the thumbnail byte data to System.out, as a matrix of the
     * hexadecimal value of each byte. Each row is 32 bytes wide.
     */
    public void debug () {

        int rows = thumbNailData.length / 32;
        int reminder = thumbNailData.length % 32;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < 32; j++) {
                System.out.print(byteToHex(thumbNailData[i * 32 + j]));
            }
            System.out.println();
        }

        for (int i = 0; i < reminder; i++) {
            System.out.print(byteToHex(thumbNailData[rows * 32 + i]));
        }
    }

    private String byteToHex(byte b){
        int i = b & 0xFF;
        return Integer.toHexString(i);
    }
}