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

import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.rename.ValidatorStatus;
import moller.util.io.PathUtil;

import java.io.File;

public class SourceAndDestinationPath {

    private static SourceAndDestinationPath instance;

    private SourceAndDestinationPath() {
    }

    public static SourceAndDestinationPath getInstance() {
        if (instance != null)
            return instance;
        synchronized (SourceAndDestinationPath.class) {
            if (instance == null) {
                instance = new SourceAndDestinationPath();
            }
            return instance;
        }
    }

    public ValidatorStatus test() {

        Language lang = Language.getInstance();

        String errorMessage = "";

        ApplicationContext ac = ApplicationContext.getInstance();
        File sourcePath = ac.getSourcePath();
        String destinationPath = ac.getDestinationPath();

        /***
         * Kontrollera så att källsökvägen inte är tom
         **/
        if(sourcePath == null){
            errorMessage = lang.get("validator.sourceanddestinationpath.noSourcePathError") + "\n";
        }
        /***
         * Om den inte är tom kontrollera så att källsökvägen inte innehåller några otillåtna tecken: (*?"<>|)
         **/
        else {
            int result = PathUtil.validateString(sourcePath.getAbsolutePath(), true);

            if(result > -1) {
                errorMessage += lang.get("validator.sourceanddestinationpath.invalidCharactersInSourcePathError") + " (" + (char)result + ")\n";
            }
        }

        /***
         * Kontrollera så att destinationssökvägen inte är tom
         **/
        if(destinationPath.equals("")){
            errorMessage += lang.get("validator.sourceanddestinationpath.noDestinationPathError") + "\n";
        }
        /***
         * Om den inte är tom kontrollera så att destinationssökvägen inte innehåller några otillåtna tecken: (*?"<>|)
         **/
        else {
            int result = PathUtil.validateString(destinationPath, true);

            if(result > -1) {
                errorMessage += lang.get("validator.sourceanddestinationpath.invalidCharactersInDestinationPathError") + " (" + (char)result + ")\n";
            }
        }

        ValidatorStatus vs = new ValidatorStatus(true, "");

        if (errorMessage.length() == 0) {
            return vs;
        } else {
            vs.setValid(false);
            vs.setStatusMessage(errorMessage);
            return vs;
        }
    }
}
