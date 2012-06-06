package moller.util.html;

public enum DocType {
	HTML_4_01_Strict      ("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">"                               , true),
	HTML_4_01_Transitional("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"                   , true),
	HTML_4_01_Frameset    ("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Frameset//EN\" \"http://www.w3.org/TR/html4/frameset.dtd\">"                    , true),
	XHTML_1_0_Strict      ("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">"            , true),
	XHTML_1_0_Transitional("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">", true),
	XHTML_1_0_Frameset    ("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">"        , true),
	XHTML_1_1             ("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">"                        , true);

	private String value;
	private boolean lineFeed;

	private DocType(String value, boolean lineFeed) {
		this.value    = value;
		this.lineFeed = lineFeed;
	}

	@Override
	public String toString() {
		return value + (lineFeed ? System.getProperty("line.separator") : "");
	}
}