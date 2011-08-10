package moller.util.test.unittest.string;

import junit.framework.Assert;
import moller.util.string.ParseVMArguments;

import org.junit.Test;

public class TestParseVMArguments {

    @Test
    public void testParseXmxToInt() {
        Assert.assertEquals(402653184, ParseVMArguments.parseXmxToLong("-Xmx384m"));
        Assert.assertEquals(402653184, ParseVMArguments.parseXmxToLong("Xmx384m"));
        Assert.assertEquals(16777216, ParseVMArguments.parseXmxToLong("Xmx16m"));

        Assert.assertEquals(402653184, ParseVMArguments.parseXmxToLong("-Xmx384M"));
        Assert.assertEquals(402653184, ParseVMArguments.parseXmxToLong("Xmx384M"));
        Assert.assertEquals(16777216, ParseVMArguments.parseXmxToLong("Xmx16M"));

        Assert.assertEquals(393216, ParseVMArguments.parseXmxToLong("Xmx384k"));
        Assert.assertEquals(393216, ParseVMArguments.parseXmxToLong("Xmx384k"));
        Assert.assertEquals(16384, ParseVMArguments.parseXmxToLong("Xmx16k"));

        Assert.assertEquals(393216, ParseVMArguments.parseXmxToLong("Xmx384K"));
        Assert.assertEquals(393216, ParseVMArguments.parseXmxToLong("Xmx384K"));
        Assert.assertEquals(16384, ParseVMArguments.parseXmxToLong("Xmx16K"));
    }
}
