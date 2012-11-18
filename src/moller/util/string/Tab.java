package moller.util.string;

public enum Tab {

    ZERO  (""),
	ONE   (" "),
	TWO   ("  "),
	THREE ("   "),
	FOUR  ("    "),
	FIVE  ("     "),
	SIX   ("      "),
	SEVEN ("       "),
	EIGHT ("        "),
	NINE  ("         "),
	TEN   ("          ");

	private String value;

	private Tab(String value) {
		this.value    = value;
	}

	public String value() {
		return value;
	}
}
