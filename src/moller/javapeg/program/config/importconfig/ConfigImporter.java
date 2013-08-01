package moller.javapeg.program.config.importconfig;

import java.io.File;
import java.util.List;

import moller.javapeg.program.C;
import moller.javapeg.program.config.ConfigUtil;
import moller.javapeg.program.config.controller.ConfigHandler;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.schema.SchemaUtil;
import moller.javapeg.program.enumerations.ConfigurationSchema;

public class ConfigImporter {

    public static Configuration doConfigurationImport(File importPath, Configuration config) {

        ConfigurationSchema configurationSchema = getConfigurationSchema(importPath);

        List<String> transformers = SchemaUtil.getTransformersForSchema(configurationSchema);

        if (transformers.isEmpty()) {
            return ConfigHandler.load(importPath);
        } else {
            return doTransformation();
        }
    }

    private static ConfigurationSchema getConfigurationSchema(File configurationFileToImport) {

        for (ConfigurationSchema schema : ConfigurationSchema.values()) {
            String configSchemaLocation = C.PATH_SCHEMAS + schema.getSchemaName();
            if (ConfigUtil.isConfigValid(configurationFileToImport, configSchemaLocation).getResult()) {
                return schema;
            }
        }

        return null;
    }

    private static Configuration doTransformation() {
        // Must be implemented when there are more than one schema available
        return null;
    }
}
