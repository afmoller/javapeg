package moller.javapeg.program.rename.process;
/**
* This class was created : 2009-04-13 by Fredrik Möller
* Latest changed         : 2009-04-14 by Fredrik Möller
*                        : 2009-05-20 by Fredrik Möller
*/

import java.io.File;
import java.util.Map;

import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.progress.RenameProcess;
import moller.javapeg.program.rename.FileAndType;
import moller.javapeg.program.rename.RenameProcessContext;
import moller.util.hash.MD5;

public class PostFileProcessor {

	/**
	 * The static singleton instance of this class.
	 */
	private static PostFileProcessor instance;
	
	/**
	 * Private constructor.
	 */
	private PostFileProcessor() {	
	}

	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static PostFileProcessor getInstance() {
		if (instance != null)
			return instance;
		synchronized (PostFileProcessor.class) {
			if (instance == null) {
				instance = new PostFileProcessor();
			}
			return instance;
		}
	}
	
	public boolean process(RenameProcess rp) {
		
		Language lang = Language.getInstance();

		rp.setRenameProgressMessages(lang.get("rename.PostFileProcessor.integrityCheck.checking"));
		
		boolean copySucess = true;
		
		RenameProcessContext rpc = RenameProcessContext.getInstance();
		
		Map<File, FileAndType> allNonJPEGFileNameMappings = rpc.getAllNonJPEGFileNameMappings();
		
		for (File sourceFile : allNonJPEGFileNameMappings.keySet()) {
			FileAndType fat = allNonJPEGFileNameMappings.get(sourceFile);
			File destFile = fat.getFile();
						
			if(notEqual(sourceFile, destFile)) {
				copySucess = false;
				rp.setLogMessage(lang.get("rename.PostFileProcessor.renameFromLabel") + " " + sourceFile.getName() + " " + lang.get("rename.PostFileProcessor.copiedWithError") + " " + destFile.getAbsolutePath());
			}
			rp.setLogMessage(lang.get("rename.PostFileProcessor.renameFromLabel") + " " + sourceFile.getName() + " " + lang.get("rename.PostFileProcessor.copiedOK") + " " + destFile.getAbsolutePath());
		}
		
		Map<File, File> allJPEGFileNameMappings =  rpc.getAllJPEGFileNameMappings();
		
		for (File sourceFile : allJPEGFileNameMappings.keySet()) {
			File destFile = allJPEGFileNameMappings.get(sourceFile);
									
			if(notEqual(sourceFile, destFile)) {
				copySucess = false;
				rp.setLogMessage(lang.get("rename.PostFileProcessor.renameFromLabel") + " " + sourceFile.getName() + " " + lang.get("rename.PostFileProcessor.renamedWithError") + " " + destFile.getName());
			}
			rp.setLogMessage(lang.get("rename.PostFileProcessor.renameFromLabel") + " " + sourceFile.getName() + " " + lang.get("rename.PostFileProcessor.renamedOK") + " " + destFile.getName());
		}
		return copySucess;
	}
	
	private boolean notEqual(File sourceFile, File destFile) {
		
		Logger logger = Logger.getInstance();
		
		String sourceHash = MD5.calculate(sourceFile);
		String destHash   = MD5.calculate(destFile);
		
		logger.logDEBUG(sourceHash + " = Hash value for file: " + sourceFile.getAbsolutePath());
		logger.logDEBUG(destHash + " = Hash value for file: " + destFile.getAbsolutePath());
		
		
		if(!destHash.equals(sourceHash)) {
			Logger.getInstance().logERROR("File: " + sourceFile.getAbsolutePath() + " was not correctly copied to: " + destFile.getAbsolutePath());				
			return true;
		}
		return false;
	}
}