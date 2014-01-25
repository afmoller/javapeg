package moller.javapeg.program.enumerations;

/**
 * This {@link Enum} lists all the configuration file schemas used by the
 * JavaPEG application for all their version. It basically maps, for each entry
 * in this {@link Enum}, a name as an {@link String} to an XSD schema file.
 *
 * @author Fredrik
 *
 */
public enum ConfigurationSchema {

    CONFIG_001_XSD("config-001.xsd");

    private String schemaName;

    ConfigurationSchema(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSchemaName() {
        return schemaName;
    }
}
