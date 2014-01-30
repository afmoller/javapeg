/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.util.test.unittest.string;

import junit.framework.Assert;
import moller.util.string.StringUtil;

import org.junit.Test;

public class TestStringUtil {

	@Test
	public void testFormatBytes() {
//		fail("Not yet implemented");
	}

	@Test
	public void testFormatString() {
		Assert.assertEquals("TestTestTest" + System.getProperty("line.separator"), StringUtil.formatString("Test\nTest\rTest<LF>"));
	}

	@Test
	public void testReverse() {
		Assert.assertEquals("tset", StringUtil.reverse("test"));
	}

	@Test
	public void testIsInt() {
		Assert.assertTrue(StringUtil.isInt("12345"));
		Assert.assertTrue(StringUtil.isInt("012345"));

		Assert.assertFalse(StringUtil.isInt("12345a"));
		Assert.assertFalse(StringUtil.isInt("a12345"));
	}

	@Test
	public void testGetIntValue() {
	    Assert.assertEquals(1, StringUtil.getIntValue("1", -1));
	    Assert.assertEquals(1, StringUtil.getIntValue("a", 1));
	    Assert.assertEquals(11, StringUtil.getIntValue("11", -1));
	}

	@Test
	public void testRemoveFirstCharacter() {
		Assert.assertTrue(StringUtil.removeFirstCharacter("String").equals("tring"));
	}

	@Test
	public void testRemoveLastCharacter() {
		Assert.assertTrue(StringUtil.removeLastCharacter("String").equals("Strin"));
	}

	@Test
	public void testGetFirstCharacter() {
		Assert.assertTrue(StringUtil.getFirstCharacter("String").equals("S"));
	}

	@Test
	public void testRemoveStringFromEnd() {
		Assert.assertTrue(StringUtil.removeStringFromEnd("String", "ng").equals("Stri"));
		Assert.assertTrue(StringUtil.removeStringFromEnd("String||", "||").equals("String"));
		Assert.assertTrue(StringUtil.removeStringFromEnd("String||", "**").equals("String||"));
	}

	@Test
	public void testRemoveAnyPreceedingNonIntegerCharacters() {
		Assert.assertTrue(StringUtil.removeAnyPrecedingNonIntegerCharacters("a123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingNonIntegerCharacters("a 123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingNonIntegerCharacters("a-123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingNonIntegerCharacters("a- 123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingNonIntegerCharacters("123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingNonIntegerCharacters(" 123").equals("123"));
	}

	@Test
	public void testRemoveAnyTrailingNonIntegerCharacters() {
		Assert.assertTrue(StringUtil.removeAnyTrailingNonIntegerCharacters("123a").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyTrailingNonIntegerCharacters("123 a").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyTrailingNonIntegerCharacters("123-a").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyTrailingNonIntegerCharacters("123 -a").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyTrailingNonIntegerCharacters("123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyTrailingNonIntegerCharacters("123 ").equals("123"));
	}

	@Test
	public void testRemoveAnyPreceedingAndTrailingNonIntegerCharacters() {
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a 123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a-123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a- 123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters(" 123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123a").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123 a").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123-a").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123 -a").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123 ").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a123a").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a 123 a").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a-123-a").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a- 123 -a").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123").equals("123"));
		Assert.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters(" 123 ").equals("123"));
	}

	@Test
	public void testConvertToHexString() {
	    Assert.assertEquals("74657374537472696e67", StringUtil.convertToHexString("testString".getBytes()));
	}


	@Test
    public void testIsNotBlank() {
	    Assert.assertTrue(StringUtil.isNotBlank("noteEmpty"));
	    Assert.assertFalse(StringUtil.isNotBlank(""));
	    Assert.assertFalse(StringUtil.isNotBlank(null));
	}

	@Test
	public void testRemoveAllNonDigits() {
	    Assert.assertEquals("123", StringUtil.removeAllNonDigits("123a"));
	    Assert.assertEquals("1234", StringUtil.removeAllNonDigits("123.4a"));
	    Assert.assertEquals("123", StringUtil.removeAllNonDigits("a123a"));
	    Assert.assertEquals(null, StringUtil.removeAllNonDigits(null));
	    Assert.assertEquals("", StringUtil.removeAllNonDigits(""));

	}
}
