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
package moller.util.mnemonic;
/**
 * Latest changed         :
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Fredrik
 *
 */
public class TestMnemonicConverter {

	/**
	 * Test method for {@link MnemonicConverter#convertAtoZCharToKeyEvent(char)}.
	 */
	@Test
	public void testConvertAtoZCharToKeyEventWith_AtoZ_and_aToz() {

		char [] upperCase = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		char [] lowerCase = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

		for (int i = 65; i <= 90; i++) {
			Assertions.assertEquals(i, MnemonicConverter.convertAtoZCharToKeyEvent(upperCase[i - 65]));
			Assertions.assertEquals(i, MnemonicConverter.convertAtoZCharToKeyEvent(lowerCase[i - 65]));
		}
	}

	@Test
	public void testConvertAtoZCharToKeyEventWithIllegalInput() {

		boolean thrownException = false;

		try {
			MnemonicConverter.convertAtoZCharToKeyEvent('1');
		} catch (RuntimeException re) {
			thrownException = true;
		}
		Assertions.assertEquals(true, thrownException);
	}
}