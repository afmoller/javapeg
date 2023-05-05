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
package moller.util.datatype;


import moller.util.enumerations.ExposureTimeType;
import moller.util.string.StringUtil;

/**
 * This class supports the following Exposure time formats as input strings:
 *
 * "2"     = two seconds
 * "1/100" = one hundredth of a second.
 * "1 1/2" = one and a half second.
 * "1.5"   = one and a half second.
 * "1,5"   = one and a half second.
 *
 * @author Fredrik
 *
 */
public class ExposureTime implements Comparable<ExposureTime> {

    private static final int DEFAULT_SECONDS_VALUE = -1;
    private static final float DEFAULT_EXPOSURETIME_AS_FLOAT = Float.MIN_VALUE;

    private int seconds = DEFAULT_SECONDS_VALUE;
    private Rational partsOfSecond = null;
    private float exposureTimeAsFloat = DEFAULT_EXPOSURETIME_AS_FLOAT;

    public ExposureTime(String exposureTime) throws ExposureTimeException {

        if (exposureTime == null) {
            return;
        }

        exposureTime = StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters(exposureTime);

        if (exposureTime.contains(Rational.DELIMITER)) {
            exposureTime = exposureTime.trim();
            int delimiterIndex = -1;

            if (exposureTime.contains(" ")) {
                String[] parts = exposureTime.split(" ");
                if (parts.length != 2) {
                    throw new ExposureTimeException("Invalid format of ExposureTime: " + exposureTime);
                }

                String seconds        = parts[0];
                String partsOfSeconds = parts[1];

                delimiterIndex = partsOfSeconds.indexOf(Rational.DELIMITER);
                this.seconds = Integer.parseInt(seconds);
                this.partsOfSecond = new Rational(partsOfSeconds.substring(0, delimiterIndex), partsOfSeconds.substring(delimiterIndex + 1, partsOfSeconds.length()));
            } else {
                delimiterIndex = exposureTime.indexOf(Rational.DELIMITER);
                this.partsOfSecond = new Rational(exposureTime.substring(0, delimiterIndex), exposureTime.substring(delimiterIndex + 1, exposureTime.length()));
            }
        } else if (exposureTime.contains(".") || exposureTime.contains(",")) {
            if (exposureTime.contains(",")) {
                exposureTime = exposureTime.replace(",", ".");
            }
            try {
                this.partsOfSecond = null;
                this.exposureTimeAsFloat  = Float.parseFloat(exposureTime);
            } catch (NumberFormatException nfex) {
                throw new ExposureTimeException("Invalid format of ExposureTime: " + exposureTime);
            }
        } else {
            try {
                this.seconds = Integer.parseInt(exposureTime.trim());
                this.partsOfSecond = null;
            } catch (NumberFormatException nfex) {
                throw new ExposureTimeException("Invalid format of ExposureTime: " + exposureTime);
            }
        }
    }

    @Override
    public String toString() {
        if (partsOfSecond == null && seconds == DEFAULT_SECONDS_VALUE && exposureTimeAsFloat == DEFAULT_EXPOSURETIME_AS_FLOAT) {
            return "";
        } else if (exposureTimeAsFloat == DEFAULT_EXPOSURETIME_AS_FLOAT && partsOfSecond == null) {
            return Integer.toString(seconds);
        } else if (exposureTimeAsFloat == DEFAULT_EXPOSURETIME_AS_FLOAT && seconds == DEFAULT_SECONDS_VALUE){
            return partsOfSecond.toString();
        } else if (partsOfSecond == null && seconds == DEFAULT_SECONDS_VALUE){
            return Float.toString(exposureTimeAsFloat);
        } else {
            return Integer.toString(seconds) + " " + partsOfSecond.toString();
        }
    }

    private float comparableValue() {
        float result = 0;

        if (seconds > 0) {
            result += seconds;
        }

        if (partsOfSecond != null) {
            result += (float)partsOfSecond.getNumerator() / (float)partsOfSecond.getDenominator();
        }

        if (exposureTimeAsFloat != DEFAULT_EXPOSURETIME_AS_FLOAT) {
            result += exposureTimeAsFloat;
        }
        return result;
    }

    public class ExposureTimeException extends Exception {
        private static final long serialVersionUID = 1L;

        ExposureTimeException(String message) {
            super(message);
        }
    }

    @Override
    public int compareTo(ExposureTime o) {
        if (this.comparableValue() < o.comparableValue()) {
            return -1;
        } else if (this.comparableValue() == o.comparableValue()) {
            return 0;
        } else {
            return 1;
        }
    }

    public float getExposureTimeAsFloat() {
        return exposureTimeAsFloat;
    }

    public int getSeconds() {
        return seconds;
    }

    public Rational getPartsOfSecond() {
        return partsOfSecond;
    }

    public ExposureTimeType getExposureTimeType() {

        if (exposureTimeAsFloat != DEFAULT_EXPOSURETIME_AS_FLOAT) {
            return ExposureTimeType.DECIMAL;
        } else if (partsOfSecond != null) {
            return ExposureTimeType.RATIONAL;
        } else {
            return ExposureTimeType.INTEGER;
        }
    }
}
