package moller.javapeg.program.rename.validator;
/**
 * This class was created : 2009-02-25 by Fredrik M�ller
 * Latest changed         : 2009-02-26 by Fredrik M�ller
 *                        : 2009-02-27 by Fredrik M�ller
 *                        : 2009-03-04 by Fredrik M�ller
 */

import moller.javapeg.program.ApplicationContext;
import moller.javapeg.program.TemplateUtil;
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
		 * Kontrollera om undermappsnamnet �r tomt
		 **/
		if(subFolderName.equals("") ){
			errorMessage += lang.get("validator.fileandsubdirectorytemplate.noSubFolderNameError") + "\n";
		}
		/***
		 * Om det inte �r tomt...
		 **/
		else {
			/***
			 * ...kontrollera s� att undermappsnamnet inte inneh�ller n�gra otill�tna tecken: (\/:*?"<>|)
			 **/
			int result = PathUtil.validateString(subFolderName, false);

			if(result > -1) {
				errorMessage += lang.get("validator.fileandsubdirectorytemplate.invalidCharactersInSubFolderNameError") + " (" + (char)result + ")\n";
			}

//			/***
//			 * ...kontrollera s� att undermappsnamnet inte �r l�ngre �n 20 tecken
//			 **/
//			if(subFolderName.length() > 20) {
//				errorMessage += lang.get("validator.fileandsubdirectorytemplate.toLongSubFolderNameTemplate") + "\n";
//			}
			/***
			 * ...kontrollera s� att undermappsnamnet inte inneh�ller felaktiga variabler
			 **/
			if(!TemplateUtil.subFolderNameIsValid(subFolderName)) {
				errorMessage += lang.get("validator.fileandsubdirectorytemplate.invalidVariablesInSubFolderNameError") + " " + "%" + lang.get("variable.pictureDateVariable") + "%, " + "%" + lang.get("variable.cameraModelVariable") + "%, " + "%" + lang.get("variable.dateOftodayVariable") + "%" + "\n";
			}
			/***
			 * ...kontrollera s� att undermappsnamnet inte inneh�ller en punkt som f�rsta tecken
			 **/
			if(subFolderName.charAt(0) == '.') {
				errorMessage += lang.get("validator.fileandsubdirectorytemplate.dotFirstInSubFolderNameTemplate") + "\n";
			}
			/***
			 * ...kontrollera s� att undermappsnamnet inte inneh�ller en punkt som sista tecken
			 **/
			if(subFolderName.charAt(subFolderName.length()-1) == '.') {
				errorMessage += lang.get("validator.fileandsubdirectorytemplate.dotLastInSubFolderNameTemplate") + "\n";
			}
		}

		/***
		 * Kontrollera om filnamnsmallen �r tom
		 **/
		if(fileNameTemplate.equals("")){
			errorMessage += lang.get("validator.fileandsubdirectorytemplate.noFileNameError") + "\n";
		}
		/***
		 * Om den inte �r tom...
		 **/
		else {
			/***
			 * ...kontrollera s� att filnamnsmallen inte inneh�ller n�gra otill�tna tecken: (\/:*?"<>|)
			 **/
			int result = PathUtil.validateString(fileNameTemplate, false);

			if(result > -1) {
				errorMessage += lang.get("validator.fileandsubdirectorytemplate.invalidCharactersInFileNameError") + " (" + (char)result + ")\n";
			}
//			/***
//			 * ...kontrollera s� att filnamnsmallen inte �r l�ngre �n 100 tecken
//			 **/
//			if(fileNameTemplate.length() > 90) {
//				errorMessage += lang.get("validator.fileandsubdirectorytemplate.toLongFileNameTemplate") + "\n";
//			}
			/***
			 * ...kontrollera s� att filnamnsmallen inte inneh�ller en punkt som f�rsta tecken
			 **/
			if(fileNameTemplate.charAt(0) == '.') {
				errorMessage += lang.get("validator.fileandsubdirectorytemplate.dotFirstInFileNameTemplate") + "\n";
			}
			/***
			 * ...kontrollera s� att filnamnsmallen inte inneh�ller en punkt som sista tecken
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