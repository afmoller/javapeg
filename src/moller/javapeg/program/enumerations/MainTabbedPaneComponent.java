package moller.javapeg.program.enumerations;

/**
 * This enumeration is used to set a name to the components in the tabs in the
 * main GUI window and to be able to know which tab that is currently selected
 * by asking the component about it´s name.
 *
 * @author Fredrik
 *
 */
public enum MainTabbedPaneComponent {

    MERGE("MERGE"),
    RENAME("RENAME"),
    VIEW("VIEW"),
    CATEGORIZE("CATEGORIZE");

    private String value;

    private MainTabbedPaneComponent(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
