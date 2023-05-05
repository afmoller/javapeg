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
package moller.javapeg.program.rename.validator;

import moller.javapeg.program.TemplateUtil;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.rename.ValidatorStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DestinationDirectoryDoesNotExist {

    private static DestinationDirectoryDoesNotExist instance;
    private List <MetaData> metaDataObjects;

    private DestinationDirectoryDoesNotExist() {
        metaDataObjects = new ArrayList<MetaData>();
    }

    public static DestinationDirectoryDoesNotExist getInstance() {
        if (instance != null)
            return instance;
        synchronized (DestinationDirectoryDoesNotExist.class) {
            if (instance == null) {
                instance = new DestinationDirectoryDoesNotExist();
            }
            return instance;
        }
    }

    public ValidatorStatus test() {
        ApplicationContext ac = ApplicationContext.getInstance();
        metaDataObjects = ac.getMetaDataObjects();

        String destinationPath   = ac.getDestinationPath();
        String subFolderTemplate = ac.getTemplateSubFolderName();
        String subFolderName = TemplateUtil.convertTemplateToString(subFolderTemplate, metaDataObjects.get(0));

        File destinationFolder = new File(destinationPath + File.separator + subFolderName);

        if(destinationFolder.exists()) {
            return new ValidatorStatus(false, Language.getInstance().get("validator.destinationdirectorydoesnotexist.existingSubDirectory"));
        }
        return new ValidatorStatus(true, "");
    }
}