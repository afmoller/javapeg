/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program.config.importconfig;

import java.io.File;
import java.util.List;

import javax.xml.transform.stream.StreamSource;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.config.controller.ConfigHandler;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.schema.SchemaUtil;
import moller.javapeg.program.enumerations.ConfigurationSchema;
import moller.util.xml.XMLUtil;

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
            StreamSource configSchema = new StreamSource(StartJavaPEG.class.getResourceAsStream(configSchemaLocation));

            if (XMLUtil.validate(configurationFileToImport, configSchema).getResult()) {
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
