package moller.javapeg.program.enumerations;
/**
 * This class was created : 2009-03-20 by Fredrik Möller
 * Latest changed         : 2009-03-22 by Fredrik Möller
 */

public enum LayoutMetaDataVariable {
	FILENAME       ("fileName"),
	DATE           ("date"),
	TIME           ("time"),
	CAMERAMODEL    ("cameraModel"),
	SHUTTERSPEED   ("shutterSpeed"),
	ISOVALUE       ("isoValue"),
	PICTUREWIDTH   ("pictureWidth"),
	PICTUREHEIGHT  ("pictureHeight"),
	APERTUREVALUE  ("apertureValue"),
	FILESIZE       ("fileSize");

	private String value;
	
	private LayoutMetaDataVariable(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return value;
	}
}