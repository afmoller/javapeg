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
package moller.javapeg.program.logger;

import moller.javapeg.program.C;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Logging;
import moller.javapeg.program.enumerations.Level;
import moller.util.io.ZipUtil;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

    private static Logger instance;

    private final SimpleDateFormat sdf;

    private final Date date;

    private final File logFile;

    private long currentLogSize;

    private BufferedWriter logWriter;

    private final Level logLevel;

    private final boolean developerMode;
    private final boolean rotateLog;
    private final boolean zipLog;

    private final Long   rotateSize;
    private final String logName;

    private final static String BASE_PATH = C.JAVAPEG_HOME + C.FS + "logs";

    private Logger() {

        Logging logging = Config.getInstance().get().getLogging();

        developerMode = logging.getDeveloperMode();
        rotateLog     = logging.getRotate();
        logName       = logging.getFileName();
        logLevel      = logging.getLevel();
        rotateSize    = logging.getRotateSize();
        zipLog        = logging.getRotateZip();

        date = new Date();
        sdf  = logging.getTimeStampFormat();

        logFile = new File(BASE_PATH + C.FS + logName);

        if(logFile.exists()) {
            currentLogSize = logFile.length();
        } else {
            try {
                if(!logFile.createNewFile()) {
                    JOptionPane.showMessageDialog(null, "Could not create file: " + logFile, "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Could not create file: " + logFile + "\nException: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            currentLogSize = 0;
        }
        try {
            logWriter = new BufferedWriter(new FileWriter(logFile, true));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not create FileWriter: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Accessor method for this Singleton class.
     *
     * @return the singleton instance of this class.
     */
    public static Logger getInstance() {
        if (instance != null)
            return instance;
        synchronized (Logger.class) {
            if (instance == null) {
                instance = new Logger();
            }
            return instance;
        }
    }

    /**
     * This method logs a debug entry to the log file if current log level
     * allows that.
     *
     * The meaning of the DEBUG level is: The DEBUG Level designates
     * fine-grained informational events that are most useful to debug an
     * application.
     *
     * @param logMessage is the message to log.
     */
    public void logDEBUG(Object logMessage) {
        if (logMessage instanceof String) {
            this.log(logMessage.toString(), Level.DEBUG);
        } else if (logMessage instanceof Throwable) {
            this.logThrowable((Throwable)logMessage, Level.DEBUG);
        }
    }

    /**
     * This method logs an info entry to the log file if current log level
     * allows that.
     *
     * The meaning of the INFO level is: The INFO level designates
     * informational messages that highlight the progress of the application
     * at coarse-grained level.
     *
     * @param logMessage is the message to log.
     */
    public void logINFO(Object logMessage) {
        if (logMessage instanceof String) {
            this.log(logMessage.toString(), Level.INFO);
        } else if (logMessage instanceof Throwable) {
            this.logThrowable((Throwable)logMessage, Level.INFO);
        }
    }

    /**
     * This method logs a warn entry to the log file if current log level
     * allows that.
     *
     * The meaning of the WARN level is: The WARN level designates potentially
     * harmful situations.
     *
     * @param logMessage is the message to log.
     */
    public void logWARN(Object logMessage) {
        if (logMessage instanceof String) {
            this.log(logMessage.toString(), Level.WARN);
        } else if (logMessage instanceof Throwable) {
            this.logThrowable((Throwable)logMessage, Level.WARN);
        }
    }

    /**
     * This method logs an error entry to the log file if current log level
     * allows that.
     *
     * The meaning of the ERROR level is: The ERROR level designates error
     * events that might still allow the application to continue running.
     *
     * @param logMessage is the message to log.
     */
    public void logERROR(Object logMessage) {
        if (logMessage instanceof String) {
            this.log(logMessage.toString(), Level.ERROR);
        } else if (logMessage instanceof Throwable) {
            this.logThrowable((Throwable)logMessage, Level.ERROR);
        }
    }

    /**
     * This method logs a fatal entry to the log file if current log level
     * allows that.
     *
     * The meaning of the FATAL level is: The FATAL level designates very
     * severe error events that will presumably lead the application to abort.
     *
     * @param logMessage is the message to log.
     */
    public void logFATAL(Object logMessage) {
        if (logMessage instanceof String) {
            this.log(logMessage.toString(), Level.FATAL);
        } else if (logMessage instanceof Throwable) {
            this.logThrowable((Throwable)logMessage, Level.FATAL);
        }
    }

    /**
     * This method logs an Exception to the log if current log level allows
     * that.
     *
     * @param t is the Exception object that contains the message to log
     * @param l is the severity of the log entry, see {@link Level} for
     *        details.
     * @throws IOException
     */
    private void logThrowable(Throwable t, Level l) {
        if(t instanceof Throwable) {
            this.log("A Throwable has been thrown", l);
        } else {
            this.log("An Exception Has Occurred", l);
        }
        this.log("START ==========================================================================", l);
        this.log("CAUSE", l);
        this.log(t.getCause() == null ? "No cause" : t.getCause().getMessage() == null ? "No cause message" : t.getCause().getMessage() , l);
        this.log("MESSAGE:", l);
        this.log(t.getMessage() == null ? "No exception message" : t.getMessage(), l);
        this.log("STACKTRACE:", l);
        for(StackTraceElement element : t.getStackTrace()) {
            this.log(element.toString(), l);
        }
        this.log("STOP ==========================================================================", l);
    }

    private void log(String logMessage, Level level) {

        if ((level.ordinal() >= logLevel.ordinal())) {

            String padding = (level == Level.INFO || level == Level.WARN) ? " " : "";

            date.setTime(System.currentTimeMillis());
            String logEntry = sdf.format(date) + " " + level + padding +  " : " + logMessage;

            try {
                if (rotateLog && ((currentLogSize + logEntry.length()) > rotateSize)) {
                    logWriter.close();

                    File renamedFile = new File(BASE_PATH + C.FS + logName + System.currentTimeMillis());

                    logFile.renameTo(renamedFile);

                    if(zipLog) {
                        ZipUtil.zip(renamedFile);
                        renamedFile.delete();
                    }

                    if(!logFile.exists()) {
                        if(!logFile.createNewFile()) {
                            throw new IOException("Could  not create file: " + logFile.getAbsolutePath());
                        }

                        logWriter = new BufferedWriter(new FileWriter(logFile, true));
                        logWriter.write(logEntry);
                        logWriter.newLine();
                        currentLogSize = logEntry.length();
                    }
                } else {
                    logWriter.write(logEntry);
                    logWriter.newLine();
                    currentLogSize += logEntry.length();
                }
                if (developerMode) {
                    flush();
                }
            } catch (IOException iox) {
                throw new RuntimeException(iox);
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Flush the log.
     */
    public void flush() {
        try {
            logWriter.flush();
        } catch (IOException e) {
            /**
             * Not much to do. Logging is done at best effort, and if it fails
             * to write the log to disk then there is nothing to do.
             */
        }
    }
}
