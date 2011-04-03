package moller.javapeg.program.enumerations;

public enum MetaDataValueFieldName {

	APERTURE_VALUE("APERTURE_VALUE"),
	CAMERA_MODEL("CAMERA_MODEL"),
	IMAGE_SIZE("IMAGE_SIZE"),
	ISO("ISO"),
	EXPOSURE_TIME("EXPOSURE_TIME"),
	YEAR("YEAR"),
	MONTH("MONTH"),
	DAY("DAY"),
	HOUR("HOUR"),
	MINUTE("MINUTE"),
	SECOND("SECOND");

	private String value;

	private MetaDataValueFieldName(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
