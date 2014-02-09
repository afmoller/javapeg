/****************************import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import moller.util.os.OsUtil;
import moller.util.string.StringUtil;
*************/
package moller.util.io;

import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import moller.util.os.OsUtil;
import moller.util.string.StringUtil;

public class FileUtil {

	private static final String LS = System.getProperty("line.separator");

    /**
     * This method copies a file to a specified destination directory.
     *
     * @param sourceFile
     *            is the file to copy
     * @param destinationDirectory
     *            is the directory to copy the file specified by the sourceFile
     *            parameter.
     * @param ensureUniqueName
     *            if true, the destination file is ensured to be unique in the
     *            directory. That means. If there already exists a file in the
     *            destination directory, then there will be a unique suffix
     *            added to the name of the copied file. The suffix starts with 0
     *            (zero) and goes on with 1, 2 ,3, ... until a unique suffix is
     *            found.
     * @return a {@link File} object for the target file.
     * @throws IOException
     */
	public static File copyFileTodirectory(File sourceFile, File destinationDirectory, boolean ensureUniqueName) throws IOException {
	    if (destinationDirectory.isFile()) {
	        throw new IllegalArgumentException("The destination directory may not specify a file: " + destinationDirectory.getAbsolutePath());
	    }

	    if (sourceFile.isDirectory()) {
	        throw new IllegalArgumentException("The source file may not specify a directory: " + sourceFile.getAbsolutePath());
	    }

	    File target = new File(destinationDirectory, sourceFile.getName());

	    if (ensureUniqueName) {
	        String absolutePath = target.getAbsolutePath();

	        int suffix = 0;
	        while(target.exists()) {
	            String firstPartOfAbsolutePath = absolutePath.substring(0, absolutePath.lastIndexOf("."));
	            String lastPartOfAbsolutePath = absolutePath.substring(absolutePath.lastIndexOf("."));

	            StringBuilder absolutePathWithSuffix = new StringBuilder();
	            absolutePathWithSuffix.append(firstPartOfAbsolutePath);
	            absolutePathWithSuffix.append("_");
	            absolutePathWithSuffix.append(suffix);
	            absolutePathWithSuffix.append(lastPartOfAbsolutePath);

	            target = new File(absolutePathWithSuffix.toString());
	            suffix++;
	        }
	    }
	    Files.copy(sourceFile.toPath(), target.toPath());

	    return target;
	}

	public static boolean copyFile(byte [] data, File destinationFile) {

		boolean copySuccessfull = false;

		try (FileChannel destination = new FileOutputStream(destinationFile).getChannel()) {

			long transferedBytes = destination.write(ByteBuffer.wrap(data));

			copySuccessfull = transferedBytes == data.length ? true : false;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return copySuccessfull;
	}

	/**
	 * This method copies the content of a InputStream to a file.
	 *
	 * @param source is the InputStream that has the content that shall be
	 *        copied.
	 * @param destinationFile is to which folder and name
	 *        the the source InputStream will be copied.
	 *
	 * @return a boolean value indicating whether the file
	 *         copy was successful or not (true == success,
	 *         false == failure).
	 *
	 * @throws IOException is thrown if it is not possible to read from the
	 *         BufferedInputStream.
	 */
	public static boolean copy(InputStream source, File destinationFile) throws IOException {

		BufferedInputStream bis = new BufferedInputStream(source);
		ByteArrayOutputStream buf = new ByteArrayOutputStream();

		byte [] buffer = new byte [8096];

		int result = bis.read(buffer);
		while(result != -1) {
			buf.write(buffer, 0, result);
			result = bis.read(buffer);
		}
		return copyFile(buf.toByteArray(), destinationFile);
	}

	/**
	 * This method calculates the size a file will occupy on disk when stored.
	 *
	 * @param file is the object that will have it´s actual size on disk
	 *        calculated.

	 * @param clusterSize is the smallest size used to store data by the file
	 *        system for the executing machine.
	 *
	 * @return the size in bytes that a file will actually occupy on disk when
	 *         stored.
	 */
	public static long getFileSizeOnDisk (File file, long clusterSize) {
		return getFileSizeOnDisk(file.length(), clusterSize);
	}

	/**
	 * This method calculates the size a file will occupy on disk when stored.
	 *
	 * @param fileSize is the size in bytes for a file.
	 *
	 * @param clusterSize is the smallest size used to store data by the file
	 *        system for the executing machine.
	 *
	 * @return the size in bytes that a file will actually occupy on disk when
	 *         stored.
	 */
	public static long getFileSizeOnDisk (long fileSize, long clusterSize) {

		long clustersNeeded = fileSize / clusterSize;

		/**
		 * If the file size is not evenly dividable with the cluster size one
		 * extra cluster is needed to store the file
		 */
		if (fileSize % clusterSize != 0) {
			clustersNeeded += 1;
		}
		return clustersNeeded * clusterSize;
	}


	/**
	 * This method creates empty files on the file system, according the paths
	 * in each file object in the java.io.File Collection sent to this method.
	 *
	 * @param files is a Collection of java.io.File objects.
	 *
	 * @return whether the creation of all files was successful. True means
	 *         success, false means failure.
	 */
	public static boolean createFiles (Collection<File> files) {
		for (File file : files) {
			if(!createFile(file)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * This method create an empty file on the file system according to the
	 * path in the input File object.
	 *
	 * @param file is the java.io.File object that shall be created on the
	 *        file system according to the path in the File object.
	 *
	 * @return whether the creation of the file was successful. True means
	 *         success, false means failure.
	 */
	public static boolean createFile (File file) {
		try {
			if (!file.createNewFile()) {
				return false;
			}
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * This method writes a string to a file either by appending the file
	 * content or by writing the string from the beginning of the file.
	 *
	 * @param f is the file object to write to.
	 * @param text contains the string that will be written to the file f
	 *        object
	 * @param append decides whether the string in the text object will be
	 *        appended to the file object f or put to the start of the file.
	 *
	 * @throws IOException if the file does not exist or is a directory or by
	 *         some other reason is impossible to write to.
	 */
	public static void writeToFile(File f, String text, boolean append) throws IOException {
		try (BufferedWriter bw = new  BufferedWriter(new FileWriter(f, append))) {
		    bw.write(text);
		}
	}

	/**
	 * This method writes a set of strings which are transformed into a string
	 * with line separator characters added between each string to a file,
	 * either by appending the file content or by writing the string from the
	 * beginning of the file.
	 *
	 * @param f is the file object to write to.
	 * @param texts contains the strings that will be written to the file f
	 *        object
	 * @param append decides whether the strings in the texts object will be
	 *        appended to the file object f or put to the start of the file.
	 *
	 * @throws IOException if the file does not exist or is a directory or by
	 *         some other reason is impossible to write to.
	 */
	public static void writeToFile(File f, Set<String> texts, boolean append) throws IOException {
		StringBuilder sb = new StringBuilder();

		for(String text : texts) {
			sb.append(text + LS);
		}
		writeToFile(f, sb.toString(), append);
	}

	/**
	 * This method writes a string to a file either by appending the file
	 * content or by writing the string from the beginning of the file.
	 *
	 * @param f is the file object to write to.
	 * @param texts contains the strings that will be written to the file f
	 *        object
	 * @param append decides whether the string in the text object will be
	 *        appended to the file object f or put to the start of the file.
	 *
	 * @throws IOException if the file does not exist or is a directory or by
	 *         some other reason is impossible to write to.
	 */
	public static void writeToFile(File f, List<String> texts, boolean append) throws IOException {
		StringBuilder sb = new StringBuilder();

		for(String text : texts) {
			sb.append(text + LS);
		}
		writeToFile(f, sb.toString(), append);
	}

	/**
	 * This method writes a set of strings which are transformed into a string
	 * with line separator characters added between each string to a file,
	 * either by appending the file content or by writing the string from the
	 * beginning of the file.
	 *
	 * @param f is the file object to write to.
	 * @param texts contains the strings that will be written to the file f
	 *        object
	 * @param append decides whether the strings in the texts object will be
	 *        appended to the file object f or put to the start of the file.
	 *
	 * @throws IOException if the file does not exist or is a directory or by
	 *         some other reason is impossible to write to.
	 */
	public static void writeToFile(File f, Iterator<Object> texts, boolean append) throws IOException {
		StringBuilder sb = new StringBuilder();

		while(texts.hasNext()) {
			sb.append(texts.next() + LS);
		}
		writeToFile(f, sb.toString(), append);
	}

	/**
	 * This method writes a set of strings which are transformed into a string
	 * with line separator characters added between each string to a file,
	 * either by appending the file content or by writing the string from the
	 * beginning of the file.
	 *
	 * @param f is the file object to write to.
	 *
	 * @param texts contains the strings that will be written to the file f
	 *        object
	 *
	 * @param append decides whether the strings in the texts object will be
	 *        appended to the file object f or put to the start of the file.
	 *
	 * @param splitTextToken defines a token that will be used to split the
	 *        strings contained in the texts object.
	 *
	 * @param useFirstPart defines whether the first or second part of the
	 *        strings in the texts object will be used.
	 *
	 * @throws IOException if the file does not exist or is a directory or by
	 *         some other reason is impossible to write to.
	 */
	public static void writeToFile(File f, Iterator<Object> texts, boolean append, String splitTextToken, boolean useFirstPart) throws IOException {
		StringBuilder sb = new StringBuilder();

		while(texts.hasNext()) {

			String text = texts.next().toString();
			text = StringUtil.reverse(text);

			sb.append(StringUtil.reverse(text.split(splitTextToken, 2)[useFirstPart ? 1 : 0]));

			if(texts.hasNext()) {
				sb.append(LS);
			}
		}
		writeToFile(f, sb.toString(), append);
	}

	/**
	 * This method downloads a file from an URL and saves it to a File object
	 *
	 * @param source is the URL from which the file shall be downloaded.
	 * @param destination is to which end point the file in the source
	 *        parameter shall be downloaded.
	 * @param fileSize is the size in bytes for the file that shall be
	 *        downloaded. This size will be used to set the max value of the
	 *        progressbar.
	 * @param bufferSize is the size in kilobytes that will be used as buffer
	 *        when the file is downloaded.
	 * @param centerPoint defines the center point that the progressbar shall
	 *        have.
	 *
	 * @throws IOException is thrown when the destination file is not possible
	 *         to create, when it is not possible to open a stream to the
	 *         source, when it is not possible to read from the source stream
	 *         or when it is not possible to write to the destination file.
	 */
	public static void downloadAndSaveFile(URL source, File destination, int fileSize, int bufferSize, Point centerPoint) throws IOException {

		OutputStream destinationFile = null;
		InputStream fileStream = null;
		try {
			destinationFile = new FileOutputStream(destination);
			fileStream = source.openStream();

			byte[] buf = new byte[bufferSize * 1024];
			int bytesRead = 0;
			while ((bytesRead = fileStream.read(buf)) != -1) {
				destinationFile.write(buf, 0, bytesRead);
			}
		} catch (FileNotFoundException e) {
			throw new IOException(e);
		} catch (IOException e) {
			throw new IOException(e);
		} finally {
			StreamUtil.closeStream(fileStream);
			StreamUtil.closeStream(destinationFile);
		}
	}

	/**
	 * This method tests whether a file is of the of a specific type.
	 *
	 * @param extension holds a String representation of a file type, for
	 *        instance: "jpg", "jpeg" and so on.
	 *
	 * @param file is the object that shall be tested regarding the file type.
	 *
	 * @return a boolean value indicating whether the parameter "file" has the
	 *         same extension as the parameter "extension" has. If that is
	 *         true, then true is returned, otherwise false is returned.
	 */
	public static boolean isOfType(String extension, File file) {

		String fileName = file.getName();
		String fileSuffix = "";

		int index = fileName.lastIndexOf(".");

		if(index == -1) {
			return false;
		} else {
			fileSuffix = fileName.substring(index + 1, fileName.length());
		}

		return fileSuffix.equalsIgnoreCase(extension);
	}

	/**
	 * This method reads the content of a text file and returns the content as
	 * a List of Strings
	 *
	 * @param file is the source to read from.
	 * @return a List consisting of the contents found in the file specified by
	 *         the file parameter.
	 * @throws IOException
	 */
	public static List<String> readFromFile(File file) throws IOException {
		List<String> content = new ArrayList<String>(32);
		String line = null;

		try (BufferedReader input =  new BufferedReader(new FileReader(file))) {
			while((line = input.readLine()) != null) {
				content.add(line);
			}
		}
		return content;
	}

	/**
	 * @param file
	 * @param simpleDateFormatString
	 * @return
	 */
	public static String getLatestModified(File file, String simpleDateFormatString) {

		SimpleDateFormat sdf = new SimpleDateFormat(simpleDateFormatString);
		Date date = new Date(file.lastModified());

		return sdf.format(date);
	}

	/**
	 * @param file
	 * @return
	 */
	public static String getLatestModifiedDate(File file) {
		return getLatestModified(file,"yyyy-MM-dd");
	}

	/**
	 * @param file
	 * @return
	 */
	public static String getLatestModifiedTime(File file) {
		return getLatestModified(file,"HH-mm-ss");
	}

	public static boolean testWriteAccess(File directory) {
		File test = new File(directory, System.currentTimeMillis() + "javapeg-test.txt");

		if(FileUtil.createFile(test)) {
			return test.delete();
		} else {
			return false;
		}
	}

	public static byte[] getBytesFromFile(File file) throws IOException {

		byte[] bytes;

		try (InputStream is = new FileInputStream(file)) {

			bytes = new byte[(int)file.length()];

			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
				offset += numRead;
			}

			if (offset < bytes.length) {
				throw new IOException("Could not completely read file " + file.getName());
			}
        }
        return bytes;
    }

    /**
     * Removes the file suffix from a file name.
     *
     * @param file
     *            is the {@link File} object to remove the file suffix from.
     * @return the name, as a {@link String} of the file denoted by the
     *         parameter file, but without the suffix of the file. If the file
     *         name does not have a suffix, then the name is returned as it is.
     */
	public static String removeFileSuffix(File file) {
	    String fileName = file.getName();

	    if (fileName.indexOf(".") > 0) {
	        return fileName.substring(0, fileName.lastIndexOf("."));
	    } else {
	        return fileName;
	    }
	}

	/**
	 * @param file
	 * @return
	 */
	public static String getFileSuffix(File file) {
	    String fileName = file.getName();

        if (fileName.indexOf(".") > 0) {
            return fileName.substring(fileName.lastIndexOf("."));
        } else {
            return fileName;
        }
	}

	public static boolean validFileName(String fileName) {
	    if (OsUtil.getOsName().toLowerCase().contains("windows")) {
	        return validWindowsFileName(fileName);
	    }
	    return true;
	}

	private static boolean validWindowsFileName(String fileName) {
	    return fileName.indexOf('/')  == -1 &&
	           fileName.indexOf('\\') == -1 &&
	           fileName.indexOf(':')  == -1 &&
	           fileName.indexOf('<')  == -1 &&
	           fileName.indexOf('>')  == -1 &&
	           fileName.indexOf('|')  == -1 &&
	           fileName.indexOf('*')  == -1 &&
	           fileName.indexOf('?')  == -1 &&
	           fileName.indexOf('"')  == -1;
	}
}
