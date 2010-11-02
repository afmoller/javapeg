package moller.javapeg.program.enumerations;

public enum MainTabbedPaneComponent {
	
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
