package moller.javapeg.program.updates;
/**
 * This class was created : 2009-05-16 by Fredrik Möller
 * Latest changed         : 2009-05-17 by Fredrik Möller
 *                        : 2009-05-20 by Fredrik Möller
 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;

import moller.javapeg.program.Config;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.version.UpdateChecker;
import moller.util.version.containers.VersionInformation;

import org.xml.sax.SAXException;

public class NewVersionChecker {
	
	/**
	 * The static singleton instance of this class.
	 */
	private static NewVersionChecker instance;
		
	private Logger logger;
	private Config config;
	private Language lang;
	
	private String urlVersion;
	private String urlChangeLog;
	private int timeOut; 
		
	/**
	 * Private constructor.
	 */
	private NewVersionChecker() {
		logger = Logger.getInstance();
		config = Config.getInstance();
		lang = Language.getInstance();
		
		urlVersion = config.getStringProperty("updatechecker.url.version");
		urlChangeLog = config.getStringProperty("updatechecker.url.changelog");
		timeOut = config.getIntProperty("updatechecker.timeout");
	}

	/**
	 * Accessor method for this Singleton class.
	 * 
	 * @return the singleton instance of this class.
	 */
	public static NewVersionChecker getInstance() {
		if (instance != null)
			return instance;
		synchronized (NewVersionChecker.class) {
			if (instance == null) {
				instance = new NewVersionChecker();
			}
			return instance;
		}
	}
	
	public long newVersionExists() {
		
		long latestVersion = 0;
		String errorMessage = "";	
		URL versionURL = null;
		
		try {
			versionURL = new URL(urlVersion);
			latestVersion=  UpdateChecker.getLatestVersion(versionURL, timeOut);
		} catch (MalformedURLException e) {
			errorMessage = lang.get("updatechecker.errormessage.uRLInvalid") + "\n(" + urlVersion + ")";
			logger.logERROR(e);
		} catch (SocketTimeoutException e) {
			errorMessage = lang.get("updatechecker.errormessage.networkTimeOut") + "\n(" + versionURL.toString() + ")";
			logger.logERROR(e);
		} catch (IOException e) {
			errorMessage = lang.get("updatechecker.errormessage.uRLWrong") + "\n(" + versionURL.toString() + ")";
			logger.logERROR(e);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (errorMessage.length() > 0) {
			JOptionPane.showMessageDialog(null, errorMessage, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
		}
		return latestVersion;	
	}
	
	public Map<Long, VersionInformation> getVersionInformation (long applicationVersion) {
			
		URL changeLogURL = null;
				
		Map<Long, VersionInformation> vim = null;
		
		try {
			changeLogURL = new URL(urlChangeLog);
			vim = UpdateChecker.getVersionInformationMap(changeLogURL, timeOut);	
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return vim;
	}
	
	public String getChangeLog(Map<Long, VersionInformation> versionInformationMap, long applicationVersion) {
				
		StringBuilder changeLogsText = null;
		changeLogsText = new StringBuilder();
		
		Set<Long> reversedOrderSortedTimeStamps = new TreeSet<Long>(Collections.reverseOrder());
		reversedOrderSortedTimeStamps.addAll(versionInformationMap.keySet());

		String lineSeparator = System.getProperty("line.separator");
		
		if (reversedOrderSortedTimeStamps.size() > 0) {
			changeLogsText.append(lineSeparator);
			changeLogsText.append("+ = Additions since previous version");
			changeLogsText.append(lineSeparator);
			changeLogsText.append("! = Bug fixes since previous version");
			changeLogsText.append(lineSeparator);
			changeLogsText.append("~ = Changes   since previous version");
			changeLogsText.append(lineSeparator);
			changeLogsText.append(lineSeparator);
		}
				
		for (Long  timeStamp : reversedOrderSortedTimeStamps) {
			if(timeStamp > applicationVersion) {
				changeLogsText.append("Version: ");
				changeLogsText.append(versionInformationMap.get(timeStamp).getVersionNumber());
				changeLogsText.append(lineSeparator);
				changeLogsText.append(lineSeparator);
				changeLogsText.append(versionInformationMap.get(timeStamp).getChangeLogs().get(timeStamp).getChangeLog());
				changeLogsText.append(lineSeparator);
				changeLogsText.append(lineSeparator);
			}	
		}
		return changeLogsText.toString();
	}
}