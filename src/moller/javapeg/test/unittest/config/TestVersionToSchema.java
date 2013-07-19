package moller.javapeg.test.unittest.config;

import moller.javapeg.program.config.schema.SchemaUtil;

import org.junit.Assert;
import org.junit.Test;

public class TestVersionToSchema {

    @Test
    public void testGetConfigurationSchema() {
        Assert.assertTrue(SchemaUtil.getConfigurationSchemaForVersion("3.0").equals("config-001.xsd"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetConfigurationSchemaWithUnsupportedVersion() {
        SchemaUtil.getConfigurationSchemaForVersion("Illegal version");
    }

}
