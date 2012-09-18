package moller.javapeg.program.rename.validator;

import java.io.File;

import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.rename.ValidatorStatus;
import moller.util.io.PathUtil;

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
		if(sourcePath.equals("")){
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
