package moller.util.string;
/**
 * This class was created : 2009-04-06 by Fredrik Möller
 * Latest changed         :
 */

public enum Tab {

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