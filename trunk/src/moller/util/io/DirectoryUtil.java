package moller.util.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class was created : 2009-04-05 by Fredrik Möller
 * Latest changed         : 
 */

public class DirectoryUtil {

	/**
	 * Utility method for deleting a folder. If the appointed folder contains
	 * other files they are deleted first, since a folder must be empty to be
	 * deletable in Java.
	 * 
	 * @param directoryPath the folder that shall be deleted.
	 * @return a boolean value indication whether the deletion of the folder
	 *         was successful or not. True indicates that the folder and all 
	 *         potential sub folders and files was successfully deleted.
	 *     
	 */
	public static boolean deleteDirectory(File directoryPath) {
		boolean deleated = true;
		
		if (directoryPath.exists() && directoryPath.isDirectory()) {
			if (directoryPath.list().length > 0) {
				for (File f : directoryPath.listFiles()) {
					if (f.isFile()) {
						if (!f.delete()) {
							deleated = false;
						}
					} else {
						if(!deleteDirectory(f)) {
							deleated = false;
						}
					}
				}
				if (!directoryPath.delete()) {
					deleated = false;
				}
			} else {
				if (!directoryPath.delete()) {
					deleated = false;
				}
			}
		}
		return deleated;
	}
	
	
	
	/**
	 * This method returns the amount of bytes, that all files inside the 
	 * directory given as argument to this method occupy when stored to disk.
	 * This include the files in all potential sub folders as well. 
	 *  
	 * @param directory is the folder that contains files for which the 
	 *        total size, in bytes, shall be calculated.
	 *               
	 * @return a long value of the summarized amount of bytes for all files
	 *         stored in the folder given in the parameter directory.
	 *         
	 * @throws Exception if the parameter directory is not an directory 
	 */
	public static long getDirectorySizeOnDisk(File directory) throws Exception {
		
		long size = 0;
		
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				if (file.isDirectory()) {
					size += getDirectorySizeOnDisk(file);
				} else {
					size += FileUtil.getFileSizeOnDisk(file, 4096);
				}
			}	
		} else {
			throw new Exception(directory.getName() + " is not a directory");
		}
		return size;
	}
	
	/**
	 * Wrapper method for the getDirectorySize(File directory) method.
	 * 
	 * @param directory is the folder that contains files for which the 
	 *        total size, in bytes, shall be calculated.
	 *        
	 * @return a long value of the summarized amount of bytes for all files
	 *         stored in the folder given in the parameter directory.
	 *         
	 * @throws Exception if the parameter directory is not an Directory.
	 */
	public static long getDirectorySizeOnDisk(String directory) throws Exception {
		return getDirectorySizeOnDisk(new File(directory));
	}
		
	/**
	 * This method returns the length of the longest path in a directory, 
	 * including all potential sub directories and files in them.
	 * 
	 * @param directory is the directory in which the longest path shall be found
	 * @param basePath is the base from where the path length is calculated
	 *  
	 * @return An integer with the value set to the amount of characters (including
	 *         File.separator characters, not a leading, but all other) found in the
	 *         longest path.
	 *         
	 * @throws Exception is thrown if the parameter directory is not an directory.
	 */
	public static int getLengthOfLongestSubDirectoryPath(File directory, File basePath) throws Exception {
		
		int length = 0;
		int basePathLength = basePath.getPath().length();
		
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				
				int relativeFileNameLength = file.getPath().length() - basePathLength;
												
				if (relativeFileNameLength > length) {
					length = relativeFileNameLength;
				}
				
				if (file.isDirectory()) {
					relativeFileNameLength = getLengthOfLongestSubDirectoryPath(file, basePath);
					if (relativeFileNameLength > length) {
						length = relativeFileNameLength;
					}
				}
			}
		} else  {
			throw new Exception(directory.getName() + " is not a directory");
		}
		return length;
	}
	
	
	
	public static String getLongestSubDirectoryPath(File directory, File basePath) throws Exception {
		
		String longestPath = "";
		
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				
		String longestRelativePath = file.getPath().substring(basePath.getPath().length());
												
				if (longestRelativePath.length() > longestPath.length()) {
					longestPath = longestRelativePath;
				}
				
				if (file.isDirectory()) {
					longestRelativePath = getLongestSubDirectoryPath(file, basePath);
					if (longestRelativePath.length() > longestPath.length()) {
						longestPath = longestRelativePath;
					}
				}
			}
		} else  {
			throw new Exception(directory.getName() + " is not a directory");
		}
		return longestPath;
	}
	
	/**
	 * This method returns a List<File> with all files and directories that exist
	 * in a directory.
	 *  
	 * @param directory is the directory that contain all files and directories
	 *        that shall be returned.
	 *        
	 * @return a list containing all the files and directories as java.io.File
	 *         objects that exist in the directory specified by the directory 
	 *         parameter. There is one list entry created per leaf that exist
	 *         in the tree.
	 *           
	 * @throws Exception is thrown if the parameter directory is not an directory.  
	 */
	public static List<File> getDirectoryContent (File directory) throws Exception {
		
		List<File> files = new ArrayList<File>();
		
		if (directory.isDirectory()) {
			File [] filesInDirectory = directory.listFiles();
			
			if (filesInDirectory.length > 0) {
				for (File file : directory.listFiles()) {
					if (file.isDirectory()) {
						files.addAll(getDirectoryContent(file));
					} else {
						files.add(file);
					}
				}
			} else {
				files.add(directory);
			}
		} else  {
			throw new Exception(directory.getName() + " is not a directory");
		}		
		return files;
	}
}
