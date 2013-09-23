package moller.javapeg.program.jpeg;

/**
 * This class is an container for the byte content of an thumbnail in JPEG
 * format.
 *
 * @author Fredrik
 *
 */
public class JPEGThumbNail {

    /**
     * Instance variables
     */
    private byte [] thumbNailData;

    /**
     * Default constructor
     */
    public JPEGThumbNail () {
        thumbNailData = null;
    }

    /**
     * Constructor
     *
     * @param thumbNailData is an array of bytes containing the actual
     *        content of the JPEG thumb nail.
     */
    public JPEGThumbNail (byte [] thumbNailData) {
        this.thumbNailData = thumbNailData;
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