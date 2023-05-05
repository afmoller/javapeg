package moller.javapeg.program.datatype;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExposureTimeTest {

    @Test
    public void testToString() {

        ExposureTime spOne = null;
        try {
            spOne = new ExposureTime("1/200 sec");
        } catch (ExposureTime.ExposureTimeException e) {
            Assertions.fail();
        }

        ExposureTime spTwo = null;
        try {
            spTwo = new ExposureTime("1 1/200 sec");
        } catch (ExposureTime.ExposureTimeException e) {
            Assertions.fail();
        }

        ExposureTime spThree = null;
        try {
            spThree = new ExposureTime("1 sec");
        } catch (ExposureTime.ExposureTimeException e) {
            Assertions.fail();
        }

        ExposureTime spFour = null;
        try {
            spFour = new ExposureTime("0.5 sec");
        } catch (ExposureTime.ExposureTimeException e) {
            Assertions.fail();
        }

        Assertions.assertEquals("1/200", spOne.toString());
        Assertions.assertEquals("1 1/200", spTwo.toString());
        Assertions.assertEquals("1", spThree.toString());
        Assertions.assertEquals("0.5", spFour.toString());
    }

}