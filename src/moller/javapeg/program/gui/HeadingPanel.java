package moller.javapeg.program.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.logger.Logger;
import moller.util.image.ImageUtil;

public class HeadingPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JLabel label;
    private final JButton icon;

    public HeadingPanel(String title, Color titleColor, String iconPath) {
        this.setLayout(new GridBagLayout());

        label = new JLabel(title);
        label.setForeground(titleColor);

        icon = new JButton();
        icon.setContentAreaFilled(false);
        icon.setBorderPainted(false);
        icon.setMargin(new Insets(0, 0, 0, 0));

        if (null != iconPath) {
            this.setIcon(iconPath, null);
        }

        GBHelper position = new GBHelper();

        this.add(label, position);
        this.add(icon, position.nextCol().align(GridBagConstraints.EAST).expandW());
    }

    public void setIcon(String iconPath, String iconToolTip) {
        try {
            icon.setIcon(ImageUtil.getIcon(StartJavaPEG.class.getResourceAsStream(iconPath), true));
            if (null != iconToolTip) {
                icon.setToolTipText(iconToolTip);
            }
        } catch (IOException iox) {
            Logger logger = Logger.getInstance();
            logger.logERROR("Could not set image: " + iconPath + " as icon. See stacktrace below for details");
            logger.logERROR(iox);
        }
    }

    public void removeIcon() {
        icon.setIcon(null);
    }

    public void setListener(ActionListener actionListener) {
        icon.addActionListener(actionListener);
    }

    /**
     * Removes any listener which are associated with the icon {@link JButton}
     * member of this class.
     */
    public void removeListeners() {
        ActionListener[] actionListeners = icon.getActionListeners();

        for (ActionListener actionListener : actionListeners) {
            icon.removeActionListener(actionListener);
        }
    }
}
