package moller.javapeg.test.unittest;

import junit.framework.Assert;
import moller.javapeg.program.datatype.ShutterSpeed;
import moller.javapeg.program.datatype.ShutterSpeed.ShutterSpeedException;

import org.junit.Test;

public class TestShutterSpeed {

	@Test
	public void testToString() {

		ShutterSpeed spOne = null;
		try {
			spOne = new ShutterSpeed("1/200 sec");
		} catch (ShutterSpeedException e) {
			Assert.fail();
		}

		ShutterSpeed spTwo = null;
		try {
			spTwo = new ShutterSpeed("1 1/200 sec");
		} catch (ShutterSpeedException e) {
			Assert.fail();
		}

		ShutterSpeed spThree = null;
		try {
			spThree = new ShutterSpeed("1 sec");
		} catch (ShutterSpeedException e) {
			Assert.fail();
		}

		ShutterSpeed spFour = null;
		try {
			spFour = new ShutterSpeed("0.5 sec");
		} catch (ShutterSpeedException e) {
			Assert.fail();
		}

		Assert.assertEquals("1/200", spOne.toString());
		Assert.assertEquals("1 1/200", spTwo.toString());
		Assert.assertEquals("1", spThree.toString());
		Assert.assertEquals("0.5", spFour.toString());
	}
}
