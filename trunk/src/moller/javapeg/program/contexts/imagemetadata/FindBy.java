package moller.javapeg.program.contexts.imagemetadata;

import java.util.Iterator;
import java.util.List;

import moller.javapeg.program.C;
import moller.javapeg.program.datatype.ImageSize;
import moller.javapeg.program.datatype.ShutterSpeed;
import moller.javapeg.program.datatype.ShutterSpeed.ShutterSpeedException;
import moller.javapeg.program.enumerations.MetaDataValueFieldName;
import moller.javapeg.program.enumerations.Operator;
import moller.util.string.StringUtil;

public class FindBy {
	
	public static void iso(String iso, Iterator<Integer> iterator, List<Integer> isoValuesToGet) {
		
		Operator operator = getOperator(iso);
		
		String[] isoValuesAsStrings = getValues(iso);
		
		switch (operator) {
		case LESS:
			while (iterator.hasNext()) {
				Integer integer = iterator.next();
				
				if (integer < Integer.parseInt(isoValuesAsStrings[0])) {
					isoValuesToGet.add(integer);
				}
			}
			break;
		case EQUAL:
			for (String isoValueAsString : isoValuesAsStrings) {
				isoValuesToGet.add(Integer.parseInt(isoValueAsString));
			}
			break;
		case GREATER:
			while (iterator.hasNext()) {
				Integer integer = iterator.next();
				
				if (integer > Integer.parseInt(isoValuesAsStrings[0])) {
					isoValuesToGet.add(integer);
				}
			}
			break;
		}
	}
	
	public static void apertureValue(String apertureValue, Iterator<Double> iterator, List<Double> apertureValueValuesToGet) {
		
		Operator operator = getOperator(apertureValue);
		
		String[] apertureValueAsStrings = getValues(apertureValue);
		
		switch (operator) {
		case LESS:
			while (iterator.hasNext()) {
				Double doubleValue = iterator.next();
				
				if (doubleValue < Double.parseDouble(apertureValueAsStrings[0])) {
					apertureValueValuesToGet.add(doubleValue);
				}
			}
			break;
		case EQUAL:
			for (String apertureValueAsString : apertureValueAsStrings) {
				apertureValueValuesToGet.add(Double.parseDouble(apertureValueAsString));
			}
			break;
		case GREATER:
			while (iterator.hasNext()) {
				Double integer = iterator.next();
				
				if (integer > Double.parseDouble(apertureValueAsStrings[0])) {
					apertureValueValuesToGet.add(integer);
				}
			}
			break;
		}
	}
	
	public static void cameraModel(String cameraModelString, Iterator<String> iterator, List<String> cameraModelsToGet) {
		stringType(MetaDataValueFieldName.CAMERA_MODEL, cameraModelString, iterator, cameraModelsToGet);
	}	
	
	public static void imageSize(String imageSizeString, Iterator<String> iterator, List<String> imageSizeValuesToGet) {
		stringType(MetaDataValueFieldName.IMAGE_SIZE, imageSizeString, iterator, imageSizeValuesToGet);
	}
	
	public static void shutterSpeed(String shutterSpeedString, Iterator<String> iterator, List<String> shutterSpeedValuesToGet) {
		stringType(MetaDataValueFieldName.SHUTTER_SPEED, shutterSpeedString, iterator, shutterSpeedValuesToGet);
	}
	
	private static void stringType(MetaDataValueFieldName metaDataValueFieldName, String valueString, Iterator<String> iterator, List<String> valuesToGet) {
		Operator operator = getOperator(valueString);
		
		String[] valuesAsStrings = getValues(valueString);
		
		switch (operator) {
		case LESS:
			while (iterator.hasNext()) {
				switch (metaDataValueFieldName) {
				case IMAGE_SIZE:
					ImageSize imageSize = new ImageSize(iterator.next());

					if (imageSize.compareTo(new ImageSize(valuesAsStrings[0])) < 0) {
						valuesToGet.add(imageSize.toString());
					}
					break;
				case SHUTTER_SPEED:
					try {
						ShutterSpeed shutterSpeed = new ShutterSpeed(iterator.next());
						
						if (shutterSpeed.compareTo(new ShutterSpeed(valuesAsStrings[0])) < 0) {
							valuesToGet.add(shutterSpeed.toString());
						}
					} catch (ShutterSpeedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}			
			}
			break;
		case EQUAL:
			for (String valueAsString : valuesAsStrings) {
				valuesToGet.add(valueAsString);
			}
			break;
		case GREATER:
			while (iterator.hasNext()) {
				switch (metaDataValueFieldName) {
				case IMAGE_SIZE:
					ImageSize imageSize = new ImageSize(iterator.next());
					
					if (imageSize.compareTo(new ImageSize(valuesAsStrings[0])) > 0) {
						valuesToGet.add(imageSize.toString());
					}
					break;
				case SHUTTER_SPEED:
					try {
						ShutterSpeed shutterSpeed = new ShutterSpeed(iterator.next());
						
						if (shutterSpeed.compareTo(new ShutterSpeed(valuesAsStrings[0])) > 0) {
							valuesToGet.add(shutterSpeed.toString());
						}
					} catch (ShutterSpeedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
			break;
		}
	}
	
	private static Operator getOperator(String iso) {
		String operator = StringUtil.getFirstCharacter(iso);
		if(operator.equals("<")) {
			return Operator.LESS;
		} else if (operator.equals("=")) {
			return Operator.EQUAL;
		} else {
			return Operator.GREATER;
		}
	}
	
	private static String[] getValues(String parameterString) {
		return StringUtil.removeFirstCharacter(parameterString).split(C.META_DATA_PARAMETER_VALUES_DELIMITER_REGEXP);
	}	
}
