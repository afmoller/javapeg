/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.util.version.containers;

import java.util.HashMap;
import java.util.Map;

public class VersionInformation {

	private int fileSize;
	private String downnloadURL;
	private String fileName;
	private String versionNumber;
	private final Map<Long, ChangeLog> changeLogs;

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
