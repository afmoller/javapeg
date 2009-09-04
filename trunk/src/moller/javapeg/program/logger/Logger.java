package moller.javapeg.program.logger;
/**
 * This class was created : 2009-02-27 by Fredrik Möller
 * Latest changed         : 2009-03-01 by Fredrik Möller
 *                        : 2009-03-02 by Fredrik Möller
 *                        : 2009-03-03 by Fredrik Möller
 *                        : 2009-03-13 by Fredrik Möller
 *                        : 2009-04-04 by Fredrik Möller
 *                        : 2009-04-15 by Fredrik Möller
 *                        : 2009-05-10 by Fredrik Möller
 *                        : 2009-05-13 by Fredrik Möller
 *                        : 2009-08-21 by Fredrik Möller
 *                        : 2009-08-23 by Fredrik Möller
 *                        : 2009-09-04 by Fredrik Möller
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import moller.javapeg.program.config.Config;

public class Logger {

    private static Logger instance;
        
    private SimpleDateFormat sdf;
    
    private Date date;
    
    private File logFile;
    
    private long currentLogSize;
    
    private BufferedWriter logWriter;
    
    private Level logLevel;
    
    private boolean developerMode;
    private boolean rotateLog;
    
    private int	rotateSize;
    private String logName;
        	    
    private final static String FS        = File.separator;
    private final static String BASE_PATH = System.getProperty("user.dir") + FS + "logs";

    private Logger() {
    	
    	Config config = Config.getInstance();
    	    	
    	developerMode = config.getBooleanProperty("logger.developerMode");
    	rotateLog     = config.getBooleanProperty("logger.log.rotate");
    	logName       = config.getStringProperty("logger.log.name");
    	logLevel      = parseConfValue(config.getStringProperty("logger.log.level"));
    	rotateSize    = config.getIntProperty("logger.log.rotate.size");
    	    	
    	date     = new Date();
    	sdf      = new SimpleDateFormat(config.getStringProperty("logger.log.entry.timestamp.format"));
    	    	
    	logFile = new File(BASE_PATH + FS + logName);
    	
    	if(logFile.exists()) {
        	currentLogSize = logFile.length();
        } else {
        	try {
				if(!logFile.createNewFile()) {
//    			throw new IOException("Could  not create file: " + logFile.getAbsolutePath());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	currentLogSize = 0;
        }
        try {
			logWriter = new BufferedWriter(new FileWriter(logFile, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		} else if (logMessage instanceof Exception) {
			this.logException((Exception)logMessage, Level.DEBUG);
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
		} else if (logMessage instanceof Exception) {
			this.logException((Exception)logMessage, Level.INFO);
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
		} else if (logMessage instanceof Exception) {
			this.logException((Exception)logMessage, Level.WARN);
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
		} else if (logMessage instanceof Exception) {
			this.logException((Exception)logMessage, Level.ERROR);
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
		} else if (logMessage instanceof Exception) {
			this.logException((Exception)logMessage, Level.FATAL);
		}
    }
        
    /**
     * This method logs an Exception to the log if current log level allows
     * that.
     * 
     * @param e is the Exception object that contains the message to log
     * @param l is the severity of the log entry, see {@link Level} for
     *        details.
     * @throws IOException 
     */
    private void logException(Exception e, Level l) {
       	this.log("An Exception Has Occurred", l);
       	this.log("START ==========================================================================", l);
       	this.log("EXCEPTION MESSAGE:", l);
    	this.log(e.getMessage() == null ? "" : e.getMessage(), l);
    	this.log("STACKTRACE:", l);
    	for(StackTraceElement element : e.getStackTrace()) {
			this.log(element.toString(), l);	
		}
    	this.log("STOP ==========================================================================", l);
    }
	
    private void log(String logMessage, Level level) {
        	        
        if ((level.ordinal() >= logLevel.ordinal()) || developerMode) {
    	
        	date.setTime(System.currentTimeMillis());
        	
        	String padding = (level == Level.INFO || level == Level.WARN) ? " " : ""; 
        		    	
	    	String logEntry = sdf.format(date) + " " + level + padding +  " : " + logMessage;
	    		                
	        try {
		    	if (rotateLog && ((currentLogSize + logEntry.length()) > rotateSize)) {
		        	logWriter.close();
					
		        	logFile.renameTo(new File(BASE_PATH + FS + logName + System.currentTimeMillis()));
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
	        	
	        }
        }
    }
     
    private Level parseConfValue(String level) {
    	
    	if(level.equals("DEBUG")) {
    		return Level.DEBUG;
    	}
    	if(level.equals("INFO")) {
    		return Level.INFO;
    	}
    	if(level.equals("WARN")) {
    		return Level.WARN;
    	}
    	if(level.equals("ERROR")) {
    		return Level.ERROR;
    	}
    	if(level.equals("FATAL")) {
    		return Level.FATAL;
    	} else {
    		return Level.DEBUG;
    	}
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    public void flush() {
    	try {
			logWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
    }
}