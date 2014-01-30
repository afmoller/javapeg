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
package moller.javapeg.program.rename.process;

import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.progress.RenameProcess;
import moller.javapeg.program.rename.ValidatorStatus;
import moller.javapeg.program.rename.validator.AvailableDiskSpace;
import moller.javapeg.program.rename.validator.DestinationDirectoryDoesNotExist;
import moller.javapeg.program.rename.validator.ExternalOverviewLayout;
import moller.javapeg.program.rename.validator.FileAndSubDirectoryTemplate;
import moller.javapeg.program.rename.validator.FileCreationAtDestinationDirectory;
import moller.javapeg.program.rename.validator.JPEGTotalPathLength;
import moller.javapeg.program.rename.validator.NonJPEGTotalPathLength;
import moller.javapeg.program.rename.validator.SourceAndDestinationPath;

public class PreFileProcessor {

    /**
     * The singleton object of this class.
     */
    private static PreFileProcessor instance;

    /**
     * Private constructor
     */
    private PreFileProcessor() {
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static PreFileProcessor getInstance() {
        if (instance != null)
            return instance;
        synchronized (PreFileProcessor.class) {
            if (instance == null) {
                instance = new PreFileProcessor();
            }
            return instance;
        }
    }

    public ValidatorStatus startTest(RenameProcess rp) {

        Language lang = Language.getInstance();

        ValidatorStatus vs;

        if(ApplicationContext.getInstance().isCreateThumbNailsCheckBoxSelected()) {
            rp.setRenameProgressMessages(lang.get("rename.PreFileProcessor.externalOverviewLayout"));
            vs = ExternalOverviewLayout.getInstance().test();
            if(!vs.isValid()) {
                return vs;
            }
            rp.incProcessProgress();
        }

        rp.setRenameProgressMessages(lang.get("rename.PreFileProcessor.sourceAndDestinationPath"));
        vs = SourceAndDestinationPath.getInstance().test();
        if(!vs.isValid()) {
            return vs;
        }
        rp.incProcessProgress();

        rp.setRenameProgressMessages(lang.get("rename.PreFileProcessor.fileAndSubDirectoryTemplate"));
        vs = FileAndSubDirectoryTemplate.getInstance().test();
        if(!vs.isValid()) {
            return vs;
        }
        rp.incProcessProgress();

        rp.setRenameProgressMessages(lang.get("rename.PreFileProcessor.destinationDirectoryDoesNotExist"));
        vs = DestinationDirectoryDoesNotExist.getInstance().test();
        if(!vs.isValid()) {
            return vs;
        }
        rp.incProcessProgress();

        rp.setRenameProgressMessages(lang.get("rename.PreFileProcessor.jPEGTotalPathLength"));
        vs = JPEGTotalPathLength.getInstance().test();
        if(!vs.isValid()) {
            return vs;
        }
        rp.incProcessProgress();

        rp.setRenameProgressMessages(lang.get("rename.PreFileProcessor.nonJPEGTotalPathLength"));
        vs = NonJPEGTotalPathLength.getInstance().test();
        if(!vs.isValid()) {
            return vs;
        }
        rp.incProcessProgress();

        rp.setRenameProgressMessages(lang.get("rename.PreFileProcessor.availableDiskSpace"));
        vs = AvailableDiskSpace.getInstance().test();
        if(!vs.isValid()) {
            return vs;
        }
        rp.incProcessProgress();

        rp.setRenameProgressMessages(lang.get("rename.PreFileProcessor.fileCreationAtDestinationDirectory"));
        vs = FileCreationAtDestinationDirectory.getInstance().test();
        if(!vs.isValid()) {
            return vs;
        }
        rp.incProcessProgress();
        return vs;
    }
}