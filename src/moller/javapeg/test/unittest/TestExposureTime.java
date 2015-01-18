package moller.javapeg.test.unittest;

import moller.javapeg.program.datatype.ExposureTime;
import moller.javapeg.program.datatype.ExposureTime.ExposureTimeException;

import org.junit.Assert;
import org.junit.Test;

public class TestExposureTime {

	@Test
	public void testToString() {

		ExposureTime spOne = null;
		try {
			spOne = new ExposureTime("1/200 sec");
		} catch (ExposureTimeException e) {
			Assert.fail();
		}

		ExposureTime spTwo = null;
		try {
			spTwo = new ExposureTime("1 1/200 sec");
		} catch (ExposureTimeException e) {
			Assert.fail();
		}

		ExposureTime spThree = null;
		try {
			spThree = new ExposureTime("1 sec");
		} catch (ExposureTimeException e) {
			Assert.fail();
		}

		ExposureTime spFour = null;
		try {
			spFour = new ExposureTime("0.5 sec");
		} catch (ExposureTimeException e) {
			Assert.fail();
		}

		Assert.assertEquals("1/200", spOne.toString());
		Assert.assertEquals("1 1/200", spTwo.toString());
		Assert.assertEquals("1", spThree.toString());
		Assert.assertEquals("0.5", spFour.toString());
	}
}
