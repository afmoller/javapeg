package moller.javapeg.program.rename.validator;
/**
 * This class was created : 2009-02-25 by Fredrik Möller
 * Latest changed         : 2009-02-26 by Fredrik Möller
 *                        : 2009-02-27 by Fredrik Möller
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import moller.javapeg.program.ApplicationContext;
import moller.javapeg.program.TemplateUtil;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.metadata.MetaData;
import moller.javapeg.program.rename.ValidatorStatus;

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