package moller.javapeg.program.updates;
/**
 * This class was created : 2009-05-16 by Fredrik Möller
 * Latest changed         : 2009-05-17 by Fredrik Möller
 *                        : 2009-05-20 by Fredrik Möller
 *                        : 2009-07-20 by Fredrik Möller
 *                        : 2009-08-09 by Fredrik Möller
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

import moller.javapeg.program.config.Config;
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
	
	public long newVersionExists(long applicationVersion) {
		
		long latestVersion = 0;
		String errorMessage = "";	
		URL versionURL = null;
		
		try {
			versionURL = new URL(urlVersion);
			latestVersion=  UpdateChecker.getLatestVersion(versionURL, Long.toString(applicationVersion), config.getBooleanProperty("updatechecker.attachVersionInformation"), timeOut);
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
			errorMessage = lang.get("updatechecker.errormessage.parseException") + "\n(" + versionURL.toString() + ")";
			logger.logERROR(e);
		} catch (ParserConfigurationException e) {
			errorMessage = lang.get("updatechecker.errormessage.parseConfigurationException") + "\n(" + versionURL.toString() + ")";
			logger.logERROR(e);
		}
		if (errorMessage.length() > 0) {
			JOptionPane.showMessageDialog(null, errorMessage, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
		}
		return latestVersion;	
	}
	
	public Map<Long, VersionInformation> getVersionInformation (long applicationVersion) {
		
		String errorMessage = "";
		URL changeLogURL = null;
				
		Map<Long, VersionInformation> vim = null;
		
		try {
			changeLogURL = new URL(urlChangeLog);
			vim = UpdateChecker.getVersionInformationMap(changeLogURL, Long.toString(applicationVersion), config.getBooleanProperty("updatechecker.attachVersionInformation"), timeOut);	
		} catch (MalformedURLException e) {
			errorMessage = lang.get("updatechecker.errormessage.uRLInvalid") + "\n(" + urlChangeLog + ")";
			logger.logERROR(e);
		} catch (SocketTimeoutException e) {
			errorMessage = lang.get("updatechecker.errormessage.networkTimeOut") + "\n(" + changeLogURL.toString() + ")";
			logger.logERROR(e);
		} catch (IOException e) {
			errorMessage = lang.get("updatechecker.errormessage.uRLWrong") + "\n(" + changeLogURL.toString() + ")";
			logger.logERROR(e);
		} catch (SAXException e) {
			errorMessage = lang.get("updatechecker.errormessage.parseException") + "\n(" + changeLogURL.toString() + ")";
			logger.logERROR(e);
		} catch (ParserConfigurationException e) {
			errorMessage = lang.get("updatechecker.errormessage.parseConfigurationException") + "\n(" + changeLogURL.toString() + ")";
			logger.logERROR(e);
		}
		if (errorMessage.length() > 0) {
			JOptionPane.showMessageDialog(null, errorMessage, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
		}
		return vim;
	}
	
	public String getChangeLog(Map<Long, VersionInformation> versionInformationMap, long applicationVersion) {
				
		StringBuilder changeLogsText = null;
		changeLogsText = new StringBuilder(512);
		
		Set<Long> reversedOrderSortedTimeStamps = new TreeSet<Long>(Collections.reverseOrder());
		reversedOrderSortedTimeStamps.addAll(versionInformationMap.keySet());

		String lS = System.getProperty("line.separator");
		
		String prolog =                                          lS +
		                "+ = Additions since previous version" + lS +
		                "! = Bug fixes since previous version" + lS +
		                "~ = Changes   since previous version" + lS +
		                                                         lS;
		
		if (reversedOrderSortedTimeStamps.size() > 0) {
			changeLogsText.append(prolog);
		}
				
		for (Long  timeStamp : reversedOrderSortedTimeStamps) {
			if(timeStamp > applicationVersion) {
				changeLogsText.append("Version: ");
				changeLogsText.append(versionInformationMap.get(timeStamp).getVersionNumber());
				changeLogsText.append(lS);
				changeLogsText.append(lS);
				changeLogsText.append(versionInformationMap.get(timeStamp).getChangeLogs().get(timeStamp).getChangeLog());
				changeLogsText.append(lS);
				changeLogsText.append(lS);
			}	
		}
		return changeLogsText.toString();
	}
}