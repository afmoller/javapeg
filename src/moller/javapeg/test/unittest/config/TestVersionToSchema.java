package moller.javapeg.test.unittest.config;

import moller.javapeg.program.config.schema.SchemaUtil;
import moller.javapeg.program.enumerations.ConfigurationSchema;

import org.junit.Assert;
import org.junit.Test;

public class TestVersionToSchema {

    @Test
    public void testGetConfigurationSchema() {
        Assert.assertTrue(SchemaUtil.getConfigurationSchemaForVersion("3.0") == ConfigurationSchema.CONFIG_001_XSD);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testGetConfigurationSchemaWithUnsupportedVersion() {
        SchemaUtil.getConfigurationSchemaForVersion("Illegal version");
    }

}
