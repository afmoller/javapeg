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
package moller.util.version;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import moller.util.version.containers.ChangeLog;
import moller.util.version.containers.VersionInformation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UpdateChecker {

	public static long getLatestVersion(URL url, String applicationVersion, boolean addHeader, int timeOut) throws IOException, ParserConfigurationException, SAXException {
		Document xmlDoc = getDocument(url, applicationVersion, addHeader, timeOut);
		Element root = xmlDoc.getDocumentElement();

		return Long.parseLong(root.getElementsByTagName("latestVersion").item(0).getTextContent());
	}

	public static Map<Long, VersionInformation> getVersionInformationMap(URL url, String applicationVersion, boolean addHeader, int timeOut) throws SocketTimeoutException, IOException, SAXException, ParserConfigurationException{

		Map<Long, VersionInformation> vim = new HashMap<Long, VersionInformation>();

		Document xmlDoc = getDocument(url, applicationVersion, addHeader, timeOut);
		Element root = xmlDoc.getDocumentElement();

		NodeList versions = root.getElementsByTagName("version");

		int numberOfVersions = versions.getLength();

		int fileSize = 0;
		long timeStamp = 0;

		String changeLog = "";
		String downloadURL = "";
		String fileName = "";
		String versionNumber = "";

		ChangeLog c;
		VersionInformation vi;

		for (int i = 0; i < numberOfVersions; i++) {
			versionNumber = ((Element)versions.item(i)).getAttribute("number");
			downloadURL = ((Element)versions.item(i)).getElementsByTagName("downloadURL").item(0).getTextContent();
			fileName = ((Element)versions.item(i)).getElementsByTagName("fileName").item(0).getTextContent();
			timeStamp = Long.parseLong(((Element)versions.item(i)).getElementsByTagName("timeStamp").item(0).getTextContent());
			fileSize = Integer.parseInt(((Element)versions.item(i)).getElementsByTagName("fileSize").item(0).getTextContent());
			changeLog = ((Element)versions.item(i)).getElementsByTagName("changeLog").item(0).getTextContent();

			c  = new ChangeLog();
			vi = new VersionInformation();

			c.setChangeLog(changeLog);

			vi.setFileSize(fileSize);
			vi.setVersionNumber(versionNumber);
			vi.setDownloadURL(downloadURL);
			vi.setFileName(fileName);
			vi.addChangeLog(timeStamp, c);

			vim.put(timeStamp, vi);
		}
		return vim;
	}

	private static Document getDocument(URL url, String applicationVersion, boolean addHeader, int timeOut) throws IOException, ParserConfigurationException, SAXException {

		HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		huc.setConnectTimeout(1000 * timeOut);
		huc.setRequestMethod("GET");
		if (addHeader) {
			huc.setRequestProperty("JavaPEG-Version", applicationVersion);
		}
		huc.connect();

		int code = huc.getResponseCode();

		if (code != HttpURLConnection.HTTP_OK) {
			throw new IOException("Invaild HTTP response: " + code);
		}
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		return db.parse(huc.getInputStream());
	}
}