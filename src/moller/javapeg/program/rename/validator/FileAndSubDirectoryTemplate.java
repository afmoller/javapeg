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
import moller.javapeg.program.rename.ValidatorStatus;
import moller.util.io.PathUtil;

public class FileAndSubDirectoryTemplate {

private static FileAndSubDirectoryTemplate instance;

    private FileAndSubDirectoryTemplate() {
    }

    public static FileAndSubDirectoryTemplate getInstance() {
        if (instance != null)
            return instance;
        synchronized (FileAndSubDirectoryTemplate.class) {
            if (instance == null) {
                instance = new FileAndSubDirectoryTemplate();
            }
            return instance;
        }
    }

    public ValidatorStatus test() {

        Language lang = Language.getInstance();

        ApplicationContext ac = ApplicationContext.getInstance();
        String subFolderName = ac.getTemplateSubFolderName();
        String fileNameTemplate = ac.getTemplateFileName();

        String errorMessage ="";

        /***
         * Kontrollera om undermappsnamnet är tomt
         **/
        if(subFolderName.equals("") ){
            errorMessage += lang.get("validator.fileandsubdirectorytemplate.noSubFolderNameError") + "\n";
        }
        /***
         * Om det inte är tomt...
         **/
        else {
            /***
             * ...kontrollera så att undermappsnamnet inte innehåller några otillåtna tecken: (\/:*?"<>|)
             **/
            int result = PathUtil.validateString(subFolderName, false);

            if(result > -1) {
                errorMessage += lang.get("validator.fileandsubdirectorytemplate.invalidCharactersInSubFolderNameError") + " (" + (char)result + ")\n";
            }
            /***
             * ...kontrollera så att undermappsnamnet inte innehåller felaktiga variabler
             **/
            if(!TemplateUtil.subFolderNameIsValid(subFolderName)) {
                errorMessage += lang.get("validator.fileandsubdirectorytemplate.invalidVariablesInSubFolderNameError") + " " + "%" + lang.get("variable.pictureDateVariable") + "%, " + "%" + lang.get("variable.cameraModelVariable") + "%, " + "%" + lang.get("variable.dateOftodayVariable") + "%" + "\n";
            }
            /***
             * ...kontrollera så att undermappsnamnet inte innehåller en punkt som första tecken
             **/
            if(subFolderName.charAt(0) == '.') {
                errorMessage += lang.get("validator.fileandsubdirectorytemplate.dotFirstInSubFolderNameTemplate") + "\n";
            }
            /***
             * ...kontrollera så att undermappsnamnet inte innehåller en punkt som sista tecken
             **/
            if(subFolderName.charAt(subFolderName.length()-1) == '.') {
                errorMessage += lang.get("validator.fileandsubdirectorytemplate.dotLastInSubFolderNameTemplate") + "\n";
            }
        }

        /***
         * Kontrollera om filnamnsmallen är tom
         **/
        if(fileNameTemplate.equals("")){
            errorMessage += lang.get("validator.fileandsubdirectorytemplate.noFileNameError") + "\n";
        }
        /***
         * Om den inte är tom...
         **/
        else {
            /***
             * ...kontrollera så att filnamnsmallen inte innehåller några otillåtna tecken: (\/:*?"<>|)
             **/
            int result = PathUtil.validateString(fileNameTemplate, false);

            if(result > -1) {
                errorMessage += lang.get("validator.fileandsubdirectorytemplate.invalidCharactersInFileNameError") + " (" + (char)result + ")\n";
            }
            /***
             * ...kontrollera så att filnamnsmallen inte innehåller en punkt som första tecken
             **/
            if(fileNameTemplate.charAt(0) == '.') {
                errorMessage += lang.get("validator.fileandsubdirectorytemplate.dotFirstInFileNameTemplate") + "\n";
            }
            /***
             * ...kontrollera så att filnamnsmallen inte innehåller en punkt som sista tecken
             **/
            if(fileNameTemplate.charAt(fileNameTemplate.length()-1) == '.') {
                errorMessage += lang.get("validator.fileandsubdirectorytemplate.dotLastInFileNameTemplate") + "\n";
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
