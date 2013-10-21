package moller.javapeg.program;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;

/**
 * This class receives all exceptions that have not earlier been caught by the
 * application. All exceptions that arrives here will be shown to the user in an
 * {@link JOptionPane} and logged by the logging system. After these two actions
 * the application will be exited with an error status.
 *
 * @author Fredrik
 *
 */
public class ApplicationUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        this.handle(throwable);
    }

    /**
     * If the currently running {@link Thread} is the event dispatcher thread
     * then the Throwable is handled immediately, otherwise the
     * {@link SwingUtilities} invokeLater mechanism is used.
     *
     * @param throwable
     *            is the {@link Throwable} to handle.
     */
    private void handle(final Throwable throwable) {
        try {
            if (SwingUtilities.isEventDispatchThread()) {
                showException(throwable);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        showException(throwable);
                    }
                });
            }
        } catch (Throwable t) {
            System.exit(1);
        }
    }

    /**
     * Convenience method that logs the uncaught exception, displays it for the
     * user and quits the application.
     *
     * @param t
     *            is the {@link Throwable} that not yet have been caught by the
     *            application.
     */
    private void showException(Throwable t) {
        logException(t);
        Language lang = Language.getInstance();

        String errorMessage = "";

        if("Java heap space".equals(t.getMessage())) {
            errorMessage = lang.get("errormessage.uncaughtexceptionhandler.outOfMemoryError");
        } else {
            errorMessage = lang.get("errormessage.uncaughtexceptionhandler.unexpectedErrorPartOne") + " " + t.getMessage() + "." + lang.get("errormessage.uncaughtexceptionhandler.unexpectedErrorPartTwo");
        }
        JOptionPane.showMessageDialog(null, errorMessage, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

    /**
     * Convenience method to make a log statement of the occurred exception.
     *
     * @param throwable
     *            is the uncaught exception to log.
     */
    private void logException(Throwable throwable) {
        Logger.getInstance().logFATAL(throwable);
        Logger.getInstance().flush();
    }

    /**
     * Static convenience method that makes it possible to register this class
     * as the uncaught exception handler for JavaPEG
     */
    public static void registerExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new ApplicationUncaughtExceptionHandler());
        System.setProperty("sun.awt.exception.handler", ApplicationUncaughtExceptionHandler.class.getName());
    }
}