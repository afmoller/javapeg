package moller.javapeg.program.rename;
/**
 * This class was created : 2009-02-26 by Fredrik M�ller
 * Latest changed         : 
 */

public class ValidatorStatus {
	
	private boolean isValid;
	private String statusMessage;
		
	public ValidatorStatus(boolean isValid, String statusMessage) {
		this.isValid = isValid;
		this.statusMessage = statusMessage;
	}

	public boolean isValid() {
		return isValid;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
}