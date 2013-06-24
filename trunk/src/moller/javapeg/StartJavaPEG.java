package moller.javapeg;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import moller.javapeg.program.ApplicationUncaughtExceptionHandler;
import moller.javapeg.program.MainGUI;
import moller.javapeg.program.applicationstart.ApplicationBootUtil;
import moller.javapeg.program.firstlaunch.InitialConfigGUI;
import moller.util.os.OsUtil;

public class StartJavaPEG {

    public static void main (String [] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                ApplicationUncaughtExceptionHandler.registerExceptionHandler();

                boolean supportedOS = false;

                String osName = OsUtil.getOsName();

                if (osName.toLowerCase().contains("windows")) {
                    supportedOS = true;
                }

                if (supportedOS) {

                    boolean startApplication = false;

                    if (ApplicationBootUtil.isFirstApplicationLaunch()) {
                        InitialConfigGUI initialConfigGUI = new InitialConfigGUI();
                        initialConfigGUI.setVisible(true);

                        Object[] options = {"Continue", "Cancel"};
                        Object initialValue = options[0];

                        int result = JOptionPane.showOptionDialog(null, initialConfigGUI, "Initial Configuration", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, initialValue);

                        if (result == JOptionPane.YES_OPTION) {
                            startApplication = true;
                        }
                    } else {
                        startApplication = true;
                    }

                    if (startApplication) {
                        MainGUI mainGUI = new MainGUI();
                        mainGUI.setVisible(true);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Unsupported operating system" + OsUtil.getOsName() + ".\n\nThe supported operating systems are:\nWindows", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
