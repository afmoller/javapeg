package moller.util.test.unittest.mnemonic;
/**
 * This class was created : 2009-03-24 by Fredrik Möller
 * Latest changed         : 
 */

import junit.framework.Assert;
import moller.util.mnemonic.MnemonicConverter;

import org.junit.Test;

/**
 * @author Fredrik
 *
 */
public class TestMnemonicConverter {

	/**
	 * Test method for {@link moller.util.mnemonic.MnemonicConverter#convertAtoZCharToKeyEvent(char)}.
	 */
	@Test
	public void testConvertAtoZCharToKeyEventWith_AtoZ_and_aToz() {
		
		char [] upperCase = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		char [] lowerCase = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
				
		for (int i = 65; i <= 90; i++) {
			Assert.assertEquals(i, MnemonicConverter.convertAtoZCharToKeyEvent(upperCase[i - 65]));
			Assert.assertEquals(i, MnemonicConverter.convertAtoZCharToKeyEvent(lowerCase[i - 65]));
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
		Assert.assertEquals(true, thrownException);
	}
}