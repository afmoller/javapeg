package moller.javapeg.program;
/**
 * This class was created : 2009-09-02 by Fredrik Möller
 * Latest changed         : 2009-09-03 by Fredrik Möller
 *                        : 2009-09-06 by Fredrik Möller  
 */

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import moller.javapeg.program.logger.Logger;

public class ApplicationUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

	public void uncaughtException(Thread thread, Throwable throwable) {
		this.handle(throwable);
	}

	public void handle(final Throwable throwable) {
		try {
			if (SwingUtilities.isEventDispatchThread()) {
				showException(throwable);
			} else {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						showException(throwable);
					}
				});
			}
		} catch (Throwable t) {
			System.exit(1);
			// don't let the exception get thrown out, will cause infinite looping!
		} 
	}

    private void showException(Throwable t) {
    	logException(t);
    	
    	String errorMessage = "";
    	
//    	TODO: Remove hard coded strings
    	if("Java heap space".equals(t.getMessage())) {
    		errorMessage = "JavaPEG has run out of memory.\n\nSee logfile for more details and consult the README file\nin installationdirectory to find a solution\n\nJavaPEG will be shut down";
    	} else {
    		errorMessage = "An unexpected error has occured: " + t.getMessage() + ".\n\nSee log file for details and consult the README file\nin installationdirectory to find a solution\n\nJavaPEG will be shut down";
    	}
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    private void logException(Throwable throwable) {
    	Logger.getInstance().logFATAL(throwable);
    	Logger.getInstance().flush();
    }
    
    public static void registerExceptionHandler() {
    	Thread.setDefaultUncaughtExceptionHandler(new ApplicationUncaughtExceptionHandler());
    	System.setProperty("sun.awt.exception.handler", ApplicationUncaughtExceptionHandler.class.getName());
    }
}