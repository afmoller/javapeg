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
package moller.javapeg.program.contexts.imagemetadata;

import moller.javapeg.program.C;
import moller.javapeg.program.datatype.ExposureTime;
import moller.javapeg.program.datatype.ExposureTime.ExposureTimeException;
import moller.javapeg.program.datatype.ImageSize;
import moller.javapeg.program.enumerations.MetaDataValueFieldName;
import moller.javapeg.program.enumerations.Operator;
import moller.javapeg.program.logger.Logger;
import moller.util.string.StringUtil;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FindBy {

    public static void year(String year, Iterator<Integer> iterator, List<Integer> yearValuesToGet) {
        integerType(MetaDataValueFieldName.YEAR, year, iterator, yearValuesToGet);
    }

    public static void month(String month, Iterator<Integer> iterator, List<Integer> monthValuesToGet) {
        integerType(MetaDataValueFieldName.MONTH, month, iterator, monthValuesToGet);
    }

    public static void day(String day, Iterator<Integer> iterator, List<Integer> dateValuesToGet) {
        integerType(MetaDataValueFieldName.DAY, day, iterator, dateValuesToGet);
    }

    public static void hour(String hour, Iterator<Integer> iterator, List<Integer> hourValuesToGet) {
        integerType(MetaDataValueFieldName.HOUR, hour, iterator, hourValuesToGet);
    }

    public static void minute(String minute, Iterator<Integer> iterator, List<Integer> minuteValuesToGet) {
        integerType(MetaDataValueFieldName.MINUTE, minute, iterator, minuteValuesToGet);
    }

    public static void second(String second, Iterator<Integer> iterator, List<Integer> secondValuesToGet) {
        integerType(MetaDataValueFieldName.SECOND, second, iterator, secondValuesToGet);
    }

    public static void iso(String iso, Iterator<Integer> iterator, List<Integer> isoValuesToGet) {
        integerType(MetaDataValueFieldName.ISO, iso, iterator, isoValuesToGet);
    }

    public static void fNumber(String fNumber, Iterator<Double> iterator, List<Double> fNumberValuesToGet) {

        Operator operator = getOperator(fNumber);

        String[] fNumberAsStrings = getValues(fNumber);

        switch (operator) {
        case LESS:
            while (iterator.hasNext()) {
                Double doubleValue = iterator.next();

                if (doubleValue < Double.parseDouble(fNumberAsStrings[0])) {
                    fNumberValuesToGet.add(doubleValue);
                }
            }
            break;
        case EQUAL:
            for (String fNumberAsString : fNumberAsStrings) {
                fNumberValuesToGet.add(Double.parseDouble(fNumberAsString));
            }
            break;
        case GREATER:
            while (iterator.hasNext()) {
                Double integer = iterator.next();

                if (integer > Double.parseDouble(fNumberAsStrings[0])) {
                    fNumberValuesToGet.add(integer);
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

    public static void exposureTime(String exposureTimeString, Iterator<String> iterator, List<String> exposureTimeValuesToGet) {
        stringType(MetaDataValueFieldName.EXPOSURE_TIME, exposureTimeString, iterator, exposureTimeValuesToGet);
    }

    private static void integerType(MetaDataValueFieldName metaDataValueFieldName, String valueString, Iterator<Integer> iterator, List<Integer> valuesToGet) {

        Operator operator = getOperator(valueString);

        String[] valuesAsStrings = getValues(valueString);

        switch (operator) {
        case LESS:
            while (iterator.hasNext()) {
                switch (metaDataValueFieldName) {
                case YEAR:
                case MONTH:
                case DAY:
                case HOUR:
                case MINUTE:
                case SECOND:
                case ISO:
                    Integer integer = iterator.next();

                    if (integer < Integer.parseInt(valuesAsStrings[0])) {
                        valuesToGet.add(integer);
                    }
                default:
                    break;
                }
            }
            break;
        case EQUAL:
            for (String isoValueAsString : valuesAsStrings) {
                valuesToGet.add(Integer.parseInt(isoValueAsString));
            }
            break;
        case GREATER:
            while (iterator.hasNext()) {
                switch (metaDataValueFieldName) {
                case YEAR:
                case MONTH:
                case DAY:
                case HOUR:
                case MINUTE:
                case SECOND:
                case ISO:
                    Integer integer = iterator.next();

                    if (integer > Integer.parseInt(valuesAsStrings[0])) {
                        valuesToGet.add(integer);
                    }
                default:
                    break;
                }
            }
            break;
        }
    }

    private static void stringType(MetaDataValueFieldName metaDataValueFieldName, String valueString, Iterator<String> iterator, List<String> valuesToGet) {
        Logger logger = Logger.getInstance();

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
                case EXPOSURE_TIME:
                    try {
                        ExposureTime exposureTime = new ExposureTime(iterator.next());

                        if (exposureTime.compareTo(new ExposureTime(valuesAsStrings[0])) < 0) {
                            valuesToGet.add(exposureTime.toString());
                        }
                    } catch (ExposureTimeException spex) {
                        // This can happen if there is a missing exposure time
                        // or an exposure time in an incorrect format in the
                        // image meta data information.
                        logger.logDEBUG("Could not create a ExposureTime object from string value:");
                        logger.logDEBUG(spex);
                    }
                    break;
                default:
                    break;
                }
            }
            break;
        case EQUAL:
            valuesToGet.addAll(Arrays.asList(valuesAsStrings));
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
                case EXPOSURE_TIME:
                    try {
                        ExposureTime exposureTime = new ExposureTime(iterator.next());

                        if (exposureTime.compareTo(new ExposureTime(valuesAsStrings[0])) > 0) {
                            valuesToGet.add(exposureTime.toString());
                        }
                    } catch (ExposureTimeException spex) {
                        // This can happen if there is a missing exposure time
                        // or an exposure time in an incorrect format in the
                        // image meta data information.
                        logger.logDEBUG("Could not create a ExposureTime object from string value:");
                        logger.logDEBUG(spex);
                    }
                    break;
                default:
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
