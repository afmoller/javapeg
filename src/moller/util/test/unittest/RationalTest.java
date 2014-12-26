package moller.util.test.unittest;

import static org.junit.Assert.assertEquals;
import moller.util.datatype.Rational;

import org.junit.Test;

public class RationalTest {

    @Test
    public void testNormalize_1_10() {
        Rational r = new Rational(1, 10);

        r.normalize();

        assertEquals(1, r.getNumerator());
        assertEquals(10, r.getDenominator());
    }

    @Test
    public void testNormalize_2_10() {
        Rational r = new Rational(2, 10);

        r.normalize();

        assertEquals(1, r.getNumerator());
        assertEquals(5, r.getDenominator());
    }

    @Test
    public void testNormalize_49_100() {
        Rational r = new Rational(49, 100);

        r.normalize();

        assertEquals(1, r.getNumerator());
        assertEquals(2, r.getDenominator());
    }

    @Test
    public void testNormalize_2_1() {
        Rational r = new Rational(2, 1);

        r.normalize();

        assertEquals(2, r.getNumerator());
        assertEquals(1, r.getDenominator());
    }

}
