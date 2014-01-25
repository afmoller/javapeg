package moller.javapeg.program.config.schema;

import java.util.ArrayList;
import java.util.List;

import moller.javapeg.program.enumerations.ConfigurationSchema;
import moller.javapeg.program.enumerations.MetaDataSchema;

public class SchemaUtil {

    public static ConfigurationSchema getConfigurationSchemaForVersion(String javapegVersion) {

        switch (javapegVersion) {
        case "3.0":
            return ConfigurationSchema.CONFIG_001_XSD;

        default:
            throw new IllegalArgumentException("Unsupported version: " + javapegVersion);
        }
    }

    public static List<String> getTransformersForSchema(ConfigurationSchema configurationSchema) {

        List<String> transformers = new ArrayList<String>();

        switch (configurationSchema) {
        case CONFIG_001_XSD:

            break;

        default:
            break;
        }

        return transformers;
    }

    public static MetaDataSchema getMetaDataSchemaForVersion(String javapegVersion) {
        switch (javapegVersion) {
        case "3.0":
            return MetaDataSchema.META_DATA_001_XSD;

        default:
            throw new IllegalArgumentException("Unsupported version: " + javapegVersion);
        }
    }
}
