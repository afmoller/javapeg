package moller.javapeg.program.rename.process;
/**
* This class was created : 2009-02-19 by Fredrik Möller
* Latest changed         : 2009-03-03 by Fredrik Möller
*                        : 2009-03-10 by Fredrik Möller
*                        : 2009-03-11 by Fredrik Möller
*                        : 2009-03-14 by Fredrik Möller
*                        : 2009-04-04 by Fredrik Möller
*                        : 2009-04-05 by Fredrik Möller
*                        : 2009-04-14 by Fredrik Möller
*                        : 2009-05-20 by Fredrik Möller
*                        : 2009-08-21 by Fredrik Möller
*/

import java.io.File;
import java.util.Map;

import moller.javapeg.program.ApplicationContext;
import moller.javapeg.program.jpeg.JPEGThumbNailCache;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.progress.RenameProcess;
import moller.javapeg.program.rename.FileAndType;
import moller.javapeg.program.rename.RenameProcessContext;
import moller.javapeg.program.rename.Type;
import moller.util.io.FileUtil;

public class FileProcessor {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static FileProcessor instance;
	
	/**
	 * The system dependent file separator char
	 */
	private final static String FS = File.separator;

	/**
	 * Private constructor.
	 */
	private FileProcessor() {	
	}

	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static FileProcessor getInstance() {
		if (instance != null)
			return instance;
		synchronized (FileProcessor.class) {
			if (instance == null) {
				instance = new FileProcessor();
			}
			return instance;
		}
	}
	
	public boolean process(RenameProcess rp) {
		
		Logger logger = Logger.getInstance();
				
		ApplicationContext ac = ApplicationContext.getInstance();
		
		RenameProcessContext rpc = RenameProcessContext.getInstance();
		
		Language lang = Language.getInstance();
		
		String destinationPath = ac.getDestinationPath();
		String subDirectoryName = rpc.getSubDirectoryName();
		
		/**
		 * Create sub directory.
		 */
		rp.setRenameProgressMessages(lang.get("rename.FileProcessor.createSubDirectory"));
				
		File subDirectory = new File(destinationPath + FS + subDirectoryName);
		subDirectory.mkdir();
		
		rp.incProcessProgress();
				
		
		if (ac.isCreateThumbNailsCheckBoxSelected()) {
			/**
			 * Create thumb nails directory.
			 */
			rp.setRenameProgressMessages(lang.get("rename.FileProcessor.createThumbNailsDirectory"));
			File thumbNailDirectory = new File(destinationPath + FS + subDirectoryName + FS + rpc.getTHUMBNAIL_DIRECTORY_NAME());
			thumbNailDirectory.mkdir();
			rp.incProcessProgress();
			
			/**
			 * Create thumb nails
			 */
			rp.setRenameProgressMessages(lang.get("rename.FileProcessor.createThumbNails"));

			Map<File, File> allThumbNailFileNameMappings = rpc.getAllThumbNailFileNameMappings();

			JPEGThumbNailCache jtc = JPEGThumbNailCache.getInstance();

			if (FileUtil.createFiles(allThumbNailFileNameMappings.values())) {
				for (File source : allThumbNailFileNameMappings.keySet()) {
					FileUtil.copyFile(jtc.get(source).getThumbNailData(), allThumbNailFileNameMappings.get(source));
				}
			}
			rp.incProcessProgress();
		}
				
		/**
		 * Create and transfer content of JPEG files.
		 */
		rp.setRenameProgressMessages(lang.get("rename.FileProcessor.createAndTransferContentOfJPEGFiles"));
		
		Map<File, File> allJPEGFileNameMappings = rpc.getAllJPEGFileNameMappings();
		
		if (FileUtil.createFiles(allJPEGFileNameMappings.values())) {
			for (File source : allJPEGFileNameMappings.keySet()) {
				FileUtil.copyFile(source, allJPEGFileNameMappings.get(source));
				rp.setLogMessage(lang.get("rename.FileProcessor.renameFromLabel") + " " + source.getName() + " " + lang.get("rename.FileProcessor.renameToLabel") + " " + allJPEGFileNameMappings.get(source).getName());
				logger.logDEBUG("File: " + source.getName() + " Renamed To: " + allJPEGFileNameMappings.get(source).getName());
			}	
		} else {
			logger.logERROR("All JPEG files could not be created");		
			return false;
		}
		rp.incProcessProgress();
				
		/**
		 * Create non JPEG files.
		 */
		rp.setRenameProgressMessages(lang.get("rename.FileProcessor.createAndTransferContentOfNonJPEGFiles"));
		
		Map<File, FileAndType> allNonJPEGFileNameMappings = RenameProcessContext.getInstance().getAllNonJPEGFileNameMappings();
		
		for (FileAndType fileAndType : allNonJPEGFileNameMappings.values()) {
			
			Type type = fileAndType.getType();
			File file = fileAndType.getFile();
			
			if (!file.exists()) {
				if (type.equals(Type.FILE)) {
					if (!file.getParentFile().exists()) {
						if(!file.getParentFile().mkdirs()) {
							logger.logERROR("Could not create directory: " + file.getParentFile().getAbsolutePath());
							return false;
						}
					}
					if(!FileUtil.createFile(file)) {
						logger.logERROR("Could not create file: " + file.getAbsolutePath());
						return false;
					}
					
				} else {
					if (!file.mkdirs()) {
						logger.logERROR("Could not create directory: " + file.getAbsolutePath());
						return false;
					}
				} 	
			}
		}
					
		/**
		 * Transfer content of non JPEG files.
		 */
		for (File sourceFile : allNonJPEGFileNameMappings.keySet()) {
			
			FileAndType fat = allNonJPEGFileNameMappings.get(sourceFile);
			
			Type type = fat.getType();
			File destinationFile = fat.getFile();
			
			if (type.equals(Type.FILE)) {
				if (destinationFile.exists()) {
					FileUtil.copyFile(sourceFile, destinationFile);
					rp.setLogMessage(sourceFile.getAbsolutePath());
					logger.logDEBUG("Copy: " + sourceFile.getAbsolutePath() + " to: " + destinationFile.getAbsolutePath());
				} else {
					logger.logERROR("Could not copy content of source file: " + sourceFile.getAbsolutePath() + " to destination file : " + destinationFile.getAbsolutePath() + " since destination file does not exist.");					
					return false;
				}
			}
		}
		rp.incProcessProgress();
		return true;
	}
}