package moller.javapeg.program.enumerations;

public enum MetaDataValueFieldName {
	
//	TODO: Fix hard coded string
	APERTURE_VALUE("APERTURE_VALUE"),
//	TODO: Fix hard coded string
	CAMERA_MODEL("CAMERA_MODEL"),
//	TODO: Fix hard coded string
	IMAGE_SIZE("IMAGE_SIZE"),
//	TODO: Fix hard coded string
	ISO("ISO"),
//	TODO: Fix hard coded string
	SHUTTER_SPEED("SHUTTER_SPEED"),
//	TODO: Fix hard coded string
	YEAR("YEAR"),
//	TODO: Fix hard coded string
	MONTH("MONTH"),
//	TODO: Fix hard coded string
	DAY("DAY"),
//	TODO: Fix hard coded string
	HOUR("HOUR"),
//	TODO: Fix hard coded string
	MINUTE("MINUTE"),
//	TODO: Fix hard coded string
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
