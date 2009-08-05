package moller.util.html;
/**
 * This class was created : 2009-03-13 by Fredrik Möller
 * Latest changed         : 2009-03-16 by Fredrik Möller
 *                        : 2009-03-21 by Fredrik Möller
 *                        : 2009-03-22 by Fredrik Möller
 */

public enum Tag {
	
	HTMLOpen  ("<html@>" , true),
	HTMLClose ("</html>" , true),
	HEADOpen  ("<head@>" , true),
	HEADClose ("</head>" , true),
	META      ("<meta@/>", true),
	TITLEOpen ("<title@>", true),
	TITLEOpenNoLF ("<title@>", false),
	TITLEClose("</title>", true),
	LINK      ("<link@/>", true),
	BODYOpen  ("<body@>" , true),
	BODYClose ("</body>" , true),
	DIVOpen   ("<div@>"  , true),
	DIVClose  ("</div>"  , true),
	SPANOpen  ("<span@>" , true),
	SPANOpenNoLF ("<span@>", false),
	SPANClose ("</span>" , true),
	SPANCloseNoLF ("</span>" , false),
	AOpen     ("<a@>"    , true),
	AClose    ("</a>"    , true),
	IMG       ("<img@/>" , true),
	BR        ("<br@/>"  , true);
	
	private String value;
	private boolean lineFeed;
	private static final String LINEFEED = System.getProperty("line.separator");
	
	private Tag(String value, boolean lineFeed) {
		this.value    = value;
		this.lineFeed = lineFeed;
	}
	
	public String attributes(String attr) {
		return value.replace("@", " " + attr) + (lineFeed ? LINEFEED : "");
	}
	
	public String classAttribute(String className) {
		return value.replace("@", " class=\"" + className + "\"") + (lineFeed ? LINEFEED : "");
	}
	
	@Override
	public String toString() {
		return value.replace("@", "") + (lineFeed ? LINEFEED : "");
	}
}