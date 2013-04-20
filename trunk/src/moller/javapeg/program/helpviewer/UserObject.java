package moller.javapeg.program.helpviewer;

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