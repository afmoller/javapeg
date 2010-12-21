package moller.javapeg.test.unittest;

import junit.framework.Assert;
import moller.javapeg.program.datatype.ShutterSpeed;
import moller.javapeg.program.datatype.ShutterSpeed.ShutterSpeedException;
import moller.util.datatype.Rational;

import org.junit.Test;

public class TestShutterSpeed {

	@Test
	public void testToString() {
		ShutterSpeed spOne = new ShutterSpeed(1);
		ShutterSpeed spTwo = new ShutterSpeed(1, new Rational(1, 60));
		ShutterSpeed spThree = new ShutterSpeed(new Rational(1, 60));
		ShutterSpeed spFour = new ShutterSpeed(-1, null);
		
		ShutterSpeed spFive = null;
		try {
			spFive = new ShutterSpeed("1/200 sec");
		} catch (ShutterSpeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ShutterSpeed spSix = null;
		try {
			spSix = new ShutterSpeed("1 1/200 sec");
		} catch (ShutterSpeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ShutterSpeed spSeven = null;
		try {
			spSeven = new ShutterSpeed("1 sec");
		} catch (ShutterSpeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Assert.assertEquals("1", spOne.toString());
		Assert.assertEquals("1 1/60", spTwo.toString());
		Assert.assertEquals("1/60", spThree.toString());
		Assert.assertEquals("", spFour.toString());
		
		Assert.assertEquals("1/200", spFive.toString());
		Assert.assertEquals(1, spFive.getPartsOfSecond().getNumerator());
		Assert.assertEquals(200, spFive.getPartsOfSecond().getDenominator());
		
		Assert.assertEquals("1 1/200", spSix.toString());
		Assert.assertEquals(1, spSix.getSeconds());
		Assert.assertEquals(1, spSix.getPartsOfSecond().getNumerator());
		Assert.assertEquals(200, spSix.getPartsOfSecond().getDenominator());
		
		Assert.assertEquals("1", spSeven.toString());
		Assert.assertEquals(1, spSeven.getSeconds());
		Assert.assertEquals(null, spSeven.getPartsOfSecond());
		
	}
}
