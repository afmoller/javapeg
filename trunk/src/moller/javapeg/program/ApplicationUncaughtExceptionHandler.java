package moller.javapeg.program;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;

public class ApplicationUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        this.handle(throwable);
    }

    public void handle(final Throwable throwable) {
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
            // don't let the exception get thrown out, will cause infinite looping!
        }
    }

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

    private void logException(Throwable throwable) {
        Logger.getInstance().logFATAL(throwable);
        Logger.getInstance().flush();
    }

    public static void registerExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new ApplicationUncaughtExceptionHandler());
        System.setProperty("sun.awt.exception.handler", ApplicationUncaughtExceptionHandler.class.getName());
    }
}