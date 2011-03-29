package moller.javapeg.program.datatype;

import moller.util.datatype.Rational;
import moller.util.string.StringUtil;

public class ShutterSpeed implements Comparable<ShutterSpeed> {

	private int seconds;
	private Rational partsOfSecond;
	private float shutterSpeedAsFloat;

	public ShutterSpeed(String shutterSpeed) throws ShutterSpeedException {
		shutterSpeed = StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters(shutterSpeed);

		if (shutterSpeed.contains(Rational.DELIMITER)) {
			shutterSpeed = shutterSpeed.trim();
			int delimiterIndex = -1;

			if (shutterSpeed.contains(" ")) {
				String[] parts = shutterSpeed.split(" ");
				if (parts.length != 2) {
					throw new ShutterSpeedException("Invalid format of ShutterSpeed: " + shutterSpeed);
				}

				String seconds        = parts[0];
				String partsOfSeconds = parts[1];

				delimiterIndex = partsOfSeconds.indexOf(Rational.DELIMITER);
				this.seconds = Integer.parseInt(seconds);
				this.partsOfSecond = new Rational(partsOfSeconds.substring(0, delimiterIndex), partsOfSeconds.substring(delimiterIndex + 1, partsOfSeconds.length()));
				this.shutterSpeedAsFloat = Float.MIN_VALUE;

			} else {
				delimiterIndex = shutterSpeed.indexOf(Rational.DELIMITER);
				this.seconds = -1;
				this.partsOfSecond = new Rational(shutterSpeed.substring(0, delimiterIndex), shutterSpeed.substring(delimiterIndex + 1, shutterSpeed.length()));
				this.shutterSpeedAsFloat = Float.MIN_VALUE;
			}
		} else if (shutterSpeed.contains(".") || shutterSpeed.contains(",")) {
			if (shutterSpeed.contains(",")) {
				shutterSpeed = shutterSpeed.replace(",", ".");
			}
			try {
				this.seconds = -1;
				this.partsOfSecond = null;
				this.shutterSpeedAsFloat  = Float.parseFloat(shutterSpeed);
			} catch (NumberFormatException nfex) {
				throw new ShutterSpeedException("Invalid format of ShutterSpeed: " + shutterSpeed);
			}
		} else {
			try {
				this.seconds = Integer.parseInt(shutterSpeed.trim());
				this.partsOfSecond = null;
				this.shutterSpeedAsFloat = Float.MIN_VALUE;
			} catch (NumberFormatException nfex) {
				throw new ShutterSpeedException("Invalid format of ShutterSpeed: " + shutterSpeed);
			}
		}
	}

	@Override
	public String toString() {
		if (partsOfSecond == null && seconds == -1 && shutterSpeedAsFloat == Float.MIN_VALUE) {
			return "";
		} else if (shutterSpeedAsFloat == Float.MIN_VALUE && partsOfSecond == null) {
			return Integer.toString(seconds);
		} else if (shutterSpeedAsFloat == Float.MIN_VALUE && seconds == -1){
			return partsOfSecond.toString();
		} else if (partsOfSecond == null && seconds == -1){
			return Float.toString(shutterSpeedAsFloat);
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

		if (shutterSpeedAsFloat != Float.MIN_NORMAL) {
			result += shutterSpeedAsFloat;
		}
		return result;
	}

	public class ShutterSpeedException extends Exception {
		private static final long serialVersionUID = 1L;

		ShutterSpeedException(String message) {
			super(message);
		}
	}

	@Override
	public int compareTo(ShutterSpeed o) {
		if (this.comparableValue() < o.comparableValue()) {
			return -1;
		} else if (this.comparableValue() == o.comparableValue()) {
			return 0;
		} else {
			return 1;
		}
	}
}
