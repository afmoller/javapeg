package moller.util.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

	public static Status getStatus(String path) {
		return getStatus(new File(path));
	}

	/**
	 * @param path
	 * @return
	 */
	public static Status getStatus(File path) {

		if(path.exists()) {
			return Status.EXISTS;
		} else {
			File parent = path.getParentFile();
			while(parent != null) {
				if (parent.exists()) {
					return Status.DOES_NOT_EXIST;
				}
				parent = parent.getParentFile();
			}
			return Status.NOT_AVAILABLE;
		}
	}

	/**
	 * This method will answer whether a file exists in a directory or not.
	 *
	 * @param directory is the directory to search in.
	 *
	 * @param fileName is the name of the file to search for in the directory
	 *        specified by the directory parameter.
	 *
	 * @return a boolean value indicating whether or not a file with a
	 *         specified filename exists in a specified directory.
	 */
	public static boolean containsFile(File directory, String fileName) {
	    File[] directoryContent = directory.listFiles();

	    for (File file : directoryContent) {
	        if (file.isFile()) {
	            if (file.getName().equals(fileName)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}

    /**
     * This method return a {@link List} of {@link File} objects that was found
     * in the directory specified by the parameter directory (or in any sub
     * directory) and which matches the file suffix specified by the parameter
     * fileSuffix
     *
     * @param directory
     *            specifies the directory in which to search for files
     * @param fileSuffix
     *            specifies the file suffix to search for.
     * @return A {@link List} of {@link File} objects found in the directory
     *         specified by the parameter directory and which matches the file
     *         suffix specified by the parameter fileSuffix. If the parameter is
     *         null or empty, all files found will be returned
     */
	public static List<File> findFiles(File directory, String fileSuffix) {
	    List<File> foundFiles = new ArrayList<>();

	    if (directory.canRead() && directory.isDirectory()) {
	        File[] directoryContent = directory.listFiles();

	        if (directoryContent != null) {
	            for (File file : directoryContent) {
	                if (file.isFile()) {
	                    if (fileSuffix == null || fileSuffix.isEmpty() || FileUtil.isOfType(fileSuffix, file)) {
	                        foundFiles.add(file);
	                    }
	                } else {
	                    foundFiles.addAll(findFiles(file, fileSuffix));
	                }
	            }
	        }
	    }

	    return foundFiles;
	}

	public static List<File> findDirectories(File directory, String namePattern) {
	    List<File> foundDirectories = new ArrayList<>();

        if (directory.canRead() && directory.isDirectory()) {
            File[] directoryContent = directory.listFiles();

            if (directoryContent != null) {
                for (File file : directoryContent) {
                    if (file.isDirectory()) {
                        if (file.getName().contains(namePattern)) {
                            foundDirectories.add(file);
                        } else {
                            foundDirectories.addAll(findDirectories(file, namePattern));
                        }
                    }
                }
            }
        }

        return foundDirectories;
	}

	/**
	 * This method will return a directory name that is unique within a given
	 * parent directory.
	 *
	 * @param parentDirectory is the directory directory which shall contain
	 *        the directory that must be unique.
	 * @param directoryThatMustBeUnique is the directory that must be unique.
	 *
	 * @return a unique directory in the context of the directory specified by
	 *         the parameter parentDirectory. The directory that must be unique
	 *         is specified by the parameter directoryThatMustBeUnique. If the
	 *         specified directory is already unique in the parent directory,
	 *         then the directory is returned unmodified, otherwise a suffix
	 *         will be added until the directory is unique. The suffix starts
	 *         at 0 (zero) and is then incremented with 1 until a unique
	 *         directory name is found.
	 */
	public static File getUniqueDirectory(File parentDirectory, File directoryThatMustBeUnique) {
	    File[] directoryContent = parentDirectory.listFiles();

	    String absolutePath = directoryThatMustBeUnique.getAbsolutePath();

	    boolean notUnique = true;
	    int suffix = 0;

	    while (notUnique) {
	        boolean matchFound = false;

	        for (File file : directoryContent) {
	            if (file.equals(directoryThatMustBeUnique)) {
	                matchFound = true;
	                directoryThatMustBeUnique = new File(absolutePath + suffix);
	                suffix++;
	                break;
	            }
	        }
	        if (!matchFound) {
	            notUnique = false;
	        }
	    }
	    return directoryThatMustBeUnique;
	}
}
