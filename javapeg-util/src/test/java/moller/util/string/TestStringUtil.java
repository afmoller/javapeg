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
package moller.util.string;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestStringUtil {

	@Test
	public void testFormatBytes() {
//		fail("Not yet implemented");
	}

	@Test
	public void testFormatString() {
		Assertions.assertEquals("TestTestTest" + System.getProperty("line.separator"), StringUtil.formatString("Test\nTest\rTest<LF>"));
	}

	@Test
	public void testReverse() {
		Assertions.assertEquals("tset", StringUtil.reverse("test"));
	}

	@Test
	public void testIsInt() {
		Assertions.assertTrue(StringUtil.isInt("12345"));
		Assertions.assertTrue(StringUtil.isInt("012345"));

		Assertions.assertFalse(StringUtil.isInt("12345a"));
		Assertions.assertFalse(StringUtil.isInt("a12345"));
	}

	@Test
	public void testGetIntValue() {
	    Assertions.assertEquals(1, StringUtil.getIntValue("1", -1));
	    Assertions.assertEquals(1, StringUtil.getIntValue("a", 1));
	    Assertions.assertEquals(11, StringUtil.getIntValue("11", -1));
	}

	@Test
	public void testRemoveFirstCharacter() {
		Assertions.assertTrue(StringUtil.removeFirstCharacter("String").equals("tring"));
	}

	@Test
	public void testRemoveLastCharacter() {
		Assertions.assertTrue(StringUtil.removeLastCharacter("String").equals("Strin"));
	}

	@Test
	public void testGetFirstCharacter() {
		Assertions.assertTrue(StringUtil.getFirstCharacter("String").equals("S"));
	}

	@Test
	public void testRemoveStringFromEnd() {
		Assertions.assertTrue(StringUtil.removeStringFromEnd("String", "ng").equals("Stri"));
		Assertions.assertTrue(StringUtil.removeStringFromEnd("String||", "||").equals("String"));
		Assertions.assertTrue(StringUtil.removeStringFromEnd("String||", "**").equals("String||"));
	}

	@Test
	public void testRemoveAnyPreceedingNonIntegerCharacters() {
		Assertions.assertTrue(StringUtil.removeAnyPrecedingNonIntegerCharacters("a123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingNonIntegerCharacters("a 123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingNonIntegerCharacters("a-123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingNonIntegerCharacters("a- 123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingNonIntegerCharacters("123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingNonIntegerCharacters(" 123").equals("123"));
	}

	@Test
	public void testRemoveAnyTrailingNonIntegerCharacters() {
		Assertions.assertTrue(StringUtil.removeAnyTrailingNonIntegerCharacters("123a").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyTrailingNonIntegerCharacters("123 a").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyTrailingNonIntegerCharacters("123-a").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyTrailingNonIntegerCharacters("123 -a").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyTrailingNonIntegerCharacters("123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyTrailingNonIntegerCharacters("123 ").equals("123"));
	}

	@Test
	public void testRemoveAnyPreceedingAndTrailingNonIntegerCharacters() {
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a 123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a-123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a- 123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters(" 123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123a").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123 a").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123-a").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123 -a").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123 ").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a123a").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a 123 a").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a-123-a").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("a- 123 -a").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters("123").equals("123"));
		Assertions.assertTrue(StringUtil.removeAnyPrecedingAndTrailingNonIntegerCharacters(" 123 ").equals("123"));
	}

	@Test
	public void testConvertToHexString() {
	    Assertions.assertEquals("74657374537472696e67", StringUtil.convertToHexString("testString".getBytes()));
	}


	@Test
    public void testIsNotBlank() {
	    Assertions.assertTrue(StringUtil.isNotBlank("noteEmpty"));
	    Assertions.assertFalse(StringUtil.isNotBlank(""));
	    Assertions.assertFalse(StringUtil.isNotBlank(null));
	}

	@Test
	public void testRemoveAllNonDigits() {
	    Assertions.assertEquals("123", StringUtil.removeAllNonDigits("123a"));
	    Assertions.assertEquals("1234", StringUtil.removeAllNonDigits("123.4a"));
	    Assertions.assertEquals("123", StringUtil.removeAllNonDigits("a123a"));
	    Assertions.assertEquals(null, StringUtil.removeAllNonDigits(null));
	    Assertions.assertEquals("", StringUtil.removeAllNonDigits(""));

	}
}
