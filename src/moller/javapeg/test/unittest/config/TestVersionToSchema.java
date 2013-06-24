package moller.javapeg.test.unittest.config;

import moller.javapeg.program.config.VersionToSchema;

import org.junit.Assert;
import org.junit.Test;

public class TestVersionToSchema {

    @Test
    public void testGetConfigurationSchema() {
        Assert.assertTrue(VersionToSchema.getConfigurationSchema("3.0").equals("config-001.xsd"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetConfigurationSchemaWithUnsupportedVersion() {
        VersionToSchema.getConfigurationSchema("Illegal version");
    }

}
