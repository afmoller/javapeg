package moller.util.datatype;

public class ShutterSpeed implements Comparable<ShutterSpeed> {

	private int seconds;
	private Rational partsOfSecond;
	
	public ShutterSpeed(int seconds) {
		this.seconds = seconds;
		this.partsOfSecond = null;
	}
	
	public ShutterSpeed(Rational partsOfSecond) {
		this.seconds = -1;
		this.partsOfSecond = partsOfSecond;
	}
	
	public ShutterSpeed(int seconds, Rational partsOfSecond) {
		this.seconds = seconds;
		this.partsOfSecond = partsOfSecond;
	}
	
	public ShutterSpeed(String shutterSpeed) throws ShutterSpeedException {
		shutterSpeed = removeAnyTrailingNonIntegerCharacters(shutterSpeed);
		
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
				
			} else {
				delimiterIndex = shutterSpeed.indexOf(Rational.DELIMITER);
				this.seconds = -1;
				this.partsOfSecond = new Rational(shutterSpeed.substring(0, delimiterIndex), shutterSpeed.substring(delimiterIndex + 1, shutterSpeed.length()));
			}
		} else {
			this.seconds = Integer.parseInt(shutterSpeed.trim());
		}
	}

	public int getSeconds() {
		return seconds;
	}

	public Rational getPartsOfSecond() {
		return partsOfSecond;
	}

	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	public void setPartsOfSecond(Rational partsOfSecond) {
		this.partsOfSecond = partsOfSecond;
	}
	
	public String toString() {
		if (partsOfSecond == null && seconds == -1) {
			return "";
		} else if (partsOfSecond == null) {
			return Integer.toString(seconds);
		} else if (seconds == -1){
			return partsOfSecond.toString();
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
		return result;
	}
	
	private String removeAnyTrailingNonIntegerCharacters(String stringValue) {
		stringValue = stringValue.trim();
		
		String subString = "";
		
		int index = -1;
		
		for (int i = stringValue.length(); i >= 0 ; i--) {
			subString = stringValue.substring(i - 1, i);
			try {
				Integer.parseInt(subString);
				index = i;
				break;
			} catch (Exception e) {
			}
		}
		return stringValue.substring(0, index);
	}
	
	public class ShutterSpeedException extends Exception {
		private static final long serialVersionUID = 1L;

		ShutterSpeedException(String message) {
			super(message);
		}
	}

	@Override
	public int compareTo(ShutterSpeed o) {
		// TODO Auto-generated method stub
		if (this.comparableValue() < o.comparableValue()) {
			return -1;
		} else if (this.comparableValue() == o.comparableValue()) {
			return 0;
		} else {
			return 1;
		}
	}
}
