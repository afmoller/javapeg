package moller.javapeg.program.rename.process;
/**
 * This class was created : 2009-02-25 by Fredrik Möller
 * Latest changed         : 2009-02-26 by Fredrik Möller
 *                        : 2009-03-07 by Fredrik Möller
 *                        : 2009-03-09 by Fredrik Möller
 *                        : 2009-03-10 by Fredrik Möller
 *                        : 2009-03-11 by Fredrik Möller
 *                        : 2009-04-14 by Fredrik Möller
 *                        : 2009-05-09 by Fredrik Möller
 *                        : 2009-05-10 by Fredrik Möller
 */

import moller.javapeg.program.ApplicationContext;
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