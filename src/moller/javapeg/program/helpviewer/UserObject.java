package moller.javapeg.program.helpviewer;
/**
 * This class was created : 2009-09-19 by Fredrik Möller
 * Latest changed         : 2009-09-20 by Fredrik Möller
 */

public class UserObject {
	
	private String displayString;
	private String identityString;
	
	public UserObject(String displayString, String identityString) {
		this.displayString = displayString;
		this.identityString = identityString;
	}
	
	public String getDisplayString() {
		return displayString;
	}

	public String getIdentityString() {
		return identityString;
	}

	public void setDisplayString(String displayString) {
		this.displayString = displayString;
	}

	public void setIdentityString(String identityString) {
		this.identityString = identityString;
	}
	
	@Override
	public String toString() {
		return displayString;
	}
}