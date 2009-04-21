package moller.javapeg.program.jpeg;

public class JPEGThumbNail {
		
	/**
	 * Instance variables
	 */
	private byte [] thumbNailData;
	private int thumbNailSize;
		
	/**
	 * Default constructor
	 */
	public JPEGThumbNail () {
		thumbNailData = null;
		thumbNailSize = 0;
	}
		
	/**
	 * Constructor
	 * 
	 * @param thumbNailData is an array of bytes containing the actual
	 *        content of the JPEG thumb nail.
	 */
	public JPEGThumbNail (byte [] thumbNailData) {
		this.thumbNailData = thumbNailData;
		this.thumbNailSize = thumbNailData.length;
	}

	public byte[] getThumbNailData() {
		return thumbNailData;
	}

	public int getThumbNailSize() {
		return thumbNailSize;
	}

	public void setThumbNailData(byte[] thumbNailData) {
		this.thumbNailData = thumbNailData;
	}

	public void setThumbNailSize(int thumbNailSize) {
		this.thumbNailSize = thumbNailSize;
	}
	
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