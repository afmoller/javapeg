package moller.util.version.containers;
/**
 * This class was created : 2009-05-14 by Fredrik Möller
 * Latest changed         : 2009-05-15 by Fredrik Möller
 *                        : 2009-05-17 by Fredrik Möller
 *                        : 2009-05-19 by Fredrik Möller
 *                        : 2009-08-02 by Fredrik Möller
 */

import java.util.HashMap;
import java.util.Map;

public class VersionInformation {
	
	private int fileSize;
	private String downnloadURL;
	private String fileName;
	private String versionNumber;
	private Map<Long, ChangeLog> changeLogs;
	
	public VersionInformation() {
		changeLogs = new HashMap<Long, ChangeLog>();
	}
	
	public VersionInformation(long latestVersion, Map<Long, ChangeLog> versions) {
		super();
		this.changeLogs = versions;
	}

	public Map<Long, ChangeLog> getChangeLogs() {
		return changeLogs;
	}

	public String getDownnloadURL() {
		return downnloadURL;
	}
	
	public String getFileName() {
		return fileName;
	}

	public String getVersionNumber() {
		return versionNumber;
	}
	
	public int getFileSize() {
		return fileSize;
	}

	public void addChangeLog(Long timeStamp, ChangeLog changeLog) {
		changeLogs.put(timeStamp, changeLog);
	}
	
	public void setDownloadURL(String downloadURL) {
		this.downnloadURL = downloadURL;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
}