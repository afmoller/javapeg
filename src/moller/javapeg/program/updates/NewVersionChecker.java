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
package moller.javapeg.program.updates;

import moller.javapeg.program.C;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.UpdatesChecker;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.version.UpdateChecker;
import moller.util.version.containers.VersionInformation;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class NewVersionChecker {

    /**
     * The static singleton instance of this class.
     */
    private static NewVersionChecker instance;

    private final Logger logger;
    private final Configuration configuration;
    private final Language lang;

    private final URL urlVersion;
    private final URL urlChangeLog;
    private final int timeOut;

    /**
     * Private constructor.
     */
    private NewVersionChecker() {
        logger = Logger.getInstance();
        configuration = Config.getInstance().get();
        lang = Language.getInstance();

        UpdatesChecker updatesChecker = configuration.getUpdatesChecker();

        urlVersion = updatesChecker.getUrlVersion();
        urlChangeLog = updatesChecker.getUrlVersionInformation();
        timeOut = updatesChecker.getTimeOut();
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

        try {
            UpdatesChecker updatesChecker = configuration.getUpdatesChecker();
            latestVersion=  UpdateChecker.getLatestVersion(urlVersion, Long.toString(applicationVersion), updatesChecker.getAttachVersionInformation(), timeOut);
        } catch (MalformedURLException e) {
            errorMessage = lang.get("updatechecker.errormessage.uRLInvalid") + "\n(" + urlVersion + ")";
            logger.logERROR(e);
        } catch (SocketTimeoutException e) {
            errorMessage = lang.get("updatechecker.errormessage.networkTimeOut") + "\n(" + urlVersion.toString() + ")";
            logger.logERROR(e);
        } catch (IOException e) {
            if (e.getMessage().equals(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND))) {
                errorMessage = lang.get("updatechecker.errormessage.uRLWrong") + "\n(" + urlVersion.toString() + ")";
            }
            logger.logERROR(e);
        } catch (SAXException e) {
            errorMessage = lang.get("updatechecker.errormessage.parseException") + "\n(" + urlVersion.toString() + ")";
            logger.logERROR(e);
        } catch (ParserConfigurationException e) {
            errorMessage = lang.get("updatechecker.errormessage.parseConfigurationException") + "\n(" + urlVersion.toString() + ")";
            logger.logERROR(e);
        }
        if (errorMessage.length() > 0) {
            JOptionPane.showMessageDialog(null, errorMessage, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
        }
        return latestVersion;
    }

    public Map<Long, VersionInformation> getVersionInformation (long applicationVersion) {

        String errorMessage = "";

        Map<Long, VersionInformation> vim = null;

        try {
            UpdatesChecker updatesChecker = configuration.getUpdatesChecker();
            vim = UpdateChecker.getVersionInformationMap(urlChangeLog, Long.toString(applicationVersion), updatesChecker.getAttachVersionInformation(), timeOut);
        } catch (MalformedURLException e) {
            errorMessage = lang.get("updatechecker.errormessage.uRLInvalid") + "\n(" + urlChangeLog + ")";
            logger.logERROR(e);
        } catch (SocketTimeoutException e) {
            errorMessage = lang.get("updatechecker.errormessage.networkTimeOut") + "\n(" + urlChangeLog.toString() + ")";
            logger.logERROR(e);
        } catch (IOException e) {
            if (e.getMessage().equals(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND))) {
                errorMessage = lang.get("updatechecker.errormessage.uRLWrong") + "\n(" + urlChangeLog.toString() + ")";
            }
            logger.logERROR(e);
        } catch (SAXException e) {
            errorMessage = lang.get("updatechecker.errormessage.parseException") + "\n(" + urlChangeLog.toString() + ")";
            logger.logERROR(e);
        } catch (ParserConfigurationException e) {
            errorMessage = lang.get("updatechecker.errormessage.parseConfigurationException") + "\n(" + urlChangeLog.toString() + ")";
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

        String prolog =                                          C.LS +
                        "+ = Additions since previous version" + C.LS +
                        "! = Bug fixes since previous version" + C.LS +
                        "~ = Changes   since previous version" + C.LS +
                                                                 C.LS;

        if (reversedOrderSortedTimeStamps.size() > 0) {
            changeLogsText.append(prolog);
        }

        for (Long  timeStamp : reversedOrderSortedTimeStamps) {
            if(timeStamp > applicationVersion) {
                changeLogsText.append("Version: ");
                changeLogsText.append(versionInformationMap.get(timeStamp).getVersionNumber());
                changeLogsText.append(C.LS);
                changeLogsText.append(C.LS);
                changeLogsText.append(versionInformationMap.get(timeStamp).getChangeLogs().get(timeStamp).getChangeLog());
                changeLogsText.append(C.LS);
                changeLogsText.append(C.LS);
            }
        }
        return changeLogsText.toString();
    }
}