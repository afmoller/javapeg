package moller.javapeg.program;
/**
 * This class was created : 2009-09-02 by Fredrik Möller
 * Latest changed         : 2009-09-03 by Fredrik Möller
 */

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
			// don't let the exception get thrown out, will cause infinite looping!
		}
	}

    private void showException(Throwable throwable) {
    	
        String msg = String.format("Unexpected problem on thread %s", throwable.getMessage());

        logException(throwable);
        JOptionPane.showMessageDialog(null, msg);
    }

    private void logException(Throwable throwable) {
//        TODO: add logging
    }
    
    public static void registerExceptionHandler() {
    	Thread.setDefaultUncaughtExceptionHandler(new ApplicationUncaughtExceptionHandler());
    	System.setProperty("sun.awt.exception.handler", ApplicationUncaughtExceptionHandler.class.getName());
    }
}