package moller.javapeg.program.enumerations;

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
