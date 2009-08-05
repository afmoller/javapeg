package moller.util.test.unittest.html;

import org.junit.Assert;
//import junit.framework.Assert;

import moller.util.html.Tag;

import org.junit.Test;

public class TestTag {

	private static final String LINEFEED = System.getProperty("line.separator");
	
	@Test
	public void testAttributes() {
		System.out.println(Tag.HTMLOpen.attributes("\"Content-Type\" content=\"text/html;charset=iso-8859-1\""));
		Assert.assertEquals("<html \"Content-Type\" content=\"text/html;charset=iso-8859-1\">" + LINEFEED, Tag.HTMLOpen.attributes("\"Content-Type\" content=\"text/html;charset=iso-8859-1\""));
		
		
		
	}

	@Test
	public void testToString() {
		Assert.assertEquals("<html>" + LINEFEED, Tag.HTMLOpen.toString());
	}

}
