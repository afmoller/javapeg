package moller.javapeg.program.config.schema;

public class SchemaUtil {

    private static String CONFIG_001_XSD = "config-001.xsd";

    private static String[] schemas = new String[]{CONFIG_001_XSD};

    public static String getConfigurationSchemaForVersion(String version) {

        switch (version) {
        case "3.0":
            return CONFIG_001_XSD;

        default:
            throw new IllegalArgumentException("Unsupported version: " + version);
        }
    }

    public static String[] getSchemas() {
        return schemas;
    }
}
