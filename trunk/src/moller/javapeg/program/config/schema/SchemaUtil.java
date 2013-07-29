package moller.javapeg.program.config.schema;

import java.util.ArrayList;
import java.util.List;

import moller.javapeg.program.enumerations.ConfigurationSchema;

public class SchemaUtil {

//    private static String CONFIG_001_XSD = "config-001.xsd";

//    private static String[] schemas = new String[]{CONFIG_001_XSD};

    public static ConfigurationSchema getConfigurationSchemaForVersion(String version) {

        switch (version) {
        case "3.0":
            return ConfigurationSchema.CONFIG_001_XSD;

        default:
            throw new IllegalArgumentException("Unsupported version: " + version);
        }
    }

//    public static String[] getSchemas() {
//        return schemas;
//    }

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
}
