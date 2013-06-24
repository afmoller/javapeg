package moller.javapeg.program.config;

public class VersionToSchema {

    public static String getConfigurationSchema(String version) {

        switch (version) {
        case "3.0":
            return "config-001.xsd";

        default:
            throw new IllegalArgumentException("Unsupported version: " + version);
        }
    }
}
