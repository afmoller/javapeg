package moller.javapeg.program.metadata;

import java.util.HashMap;
import java.util.Map;

public class MetaDataCameraAndTagMapping {
	
	private Map<String, Map<FieldName, String>> makeModelAndTagMappings;
 	
	/**
	 * The static singleton instance of this class.
	 */
	private static MetaDataCameraAndTagMapping instance;
	
	/**
	 * Private constructor.
	 */
	private MetaDataCameraAndTagMapping() {
		makeModelAndTagMappings = new HashMap<String, Map<FieldName, String>>();
		this.initMakeModelAndTagMappingsMap();
	}

	private void initMakeModelAndTagMappingsMap() {
		makeModelAndTagMappings.put("ExifDefaultTags", getMappingsForExifDefault());
		makeModelAndTagMappings.put("CanonCanon PowerShot A95", getMappingsForCanonPowerShotA95());
		
		
	}

	private Map<FieldName, String> getMappingsForExifDefault() {
		Map<FieldName, String> fieldNameAndTagIdMappings = new HashMap<FieldName, String>();
		
		fieldNameAndTagIdMappings.put(FieldName.APERTURE_VALUE, "0x9202");
		fieldNameAndTagIdMappings.put(FieldName.DATE_TIME_ORIGINAL, "0x9003");
		fieldNameAndTagIdMappings.put(FieldName.ISO_SPEED_RATINGS, "0x8827");
		fieldNameAndTagIdMappings.put(FieldName.JPEG_INTERCHANGE_FORMAT, "0x0201");
		fieldNameAndTagIdMappings.put(FieldName.JPEG_INTERCHANGE_FORMAT_LENGTH, "0x0202");
		fieldNameAndTagIdMappings.put(FieldName.PIXEL_X_DIMENSION, "0xa002");
		fieldNameAndTagIdMappings.put(FieldName.PIXEL_Y_DIMENSION, "0xa003");
		fieldNameAndTagIdMappings.put(FieldName.SHUTTER_SPEED_VALUE, "0x9201");
		
		return fieldNameAndTagIdMappings;
	}
	
	private Map<FieldName, String> getMappingsForCanonPowerShotA95() {
		Map<FieldName, String> fieldNameAndTagIdMappings = new HashMap<FieldName, String>();
		
		fieldNameAndTagIdMappings.put(FieldName.ISO_SPEED_RATINGS, "0xc110");
		
		return fieldNameAndTagIdMappings;
	}
	
	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static MetaDataCameraAndTagMapping getInstance() {
		if (instance != null)
			return instance;
		synchronized (MetaDataCameraAndTagMapping.class) {
			if (instance == null) {
				instance = new MetaDataCameraAndTagMapping();
			}
			return instance;
		}
	}
	
	public String getTag(String make, String model, FieldName fieldName) {
		if (makeModelAndTagMappings.containsKey(make+model)) {
			String value = makeModelAndTagMappings.get(make+model).get(fieldName);
			if (value != null) {
				return value;
			} else {
				return makeModelAndTagMappings.get("ExifDefaultTags").get(fieldName);
			}
		} else {
			return makeModelAndTagMappings.get("ExifDefaultTags").get(fieldName);
		}
	}
}
