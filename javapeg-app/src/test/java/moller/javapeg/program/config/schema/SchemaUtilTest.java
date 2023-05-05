package moller.javapeg.program.config.schema;

import moller.javapeg.program.enumerations.ConfigurationSchema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SchemaUtilTest {

    @Test
    public void testGetConfigurationSchema() {
        Assertions.assertTrue(SchemaUtil.getConfigurationSchemaForVersion("3.0") == ConfigurationSchema.CONFIG_001_XSD);
    }

    @Test
    public void testGetConfigurationSchemaWithUnsupportedVersion() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            SchemaUtil.getConfigurationSchemaForVersion("Illegal version");
        });
    }

}