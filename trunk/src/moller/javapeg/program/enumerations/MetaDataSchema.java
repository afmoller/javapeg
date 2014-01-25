package moller.javapeg.program.enumerations;

/**
 * This {@link Enum} lists all the meta data base schemas used by the JavaPEG
 * application for all their version. It basically maps, for each entry in this
 * {@link Enum}, a name as an {@link String} to an XSD schema file.
 *
 * @author Fredrik
 *
 */
public enum MetaDataSchema {

    META_DATA_001_XSD("meta-data-001.xsd");

    private String schemaName;

    MetaDataSchema(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSchemaName() {
        return schemaName;
    }
}
