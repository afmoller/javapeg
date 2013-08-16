package moller.javapeg.program.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.gui.Screen;

public class ImageResizer extends JFrame {

    private static final long serialVersionUID = 1L;

    private static Configuration configuration;
    private static Logger logger;
    private static Language lang;

    private JTextField resizeDestinationPathTextField;

    private JButton resizeDestinationPathButton;

    private final List<File> imagesToResize;

    public ImageResizer(List<File> imagesToResize) {

        this.imagesToResize = imagesToResize;

        configuration = Config.getInstance().get();
        logger = Logger.getInstance();
        lang   = Language.getInstance();

        this.createMainFrame();
        this.addListeners();
    }

    private void addListeners() {
        this.addWindowListener(new WindowDestroyer());

    }

    private void createMainFrame() {
        GUI gUI = configuration.getgUI();

        Rectangle sizeAndLocation = gUI.getImageViewer().getSizeAndLocation();

        this.setSize(sizeAndLocation.getSize());

        Point xyFromConfig = new Point(sizeAndLocation.getLocation());

        if(Screen.isOnScreen(xyFromConfig)) {
            this.setLocation(xyFromConfig);
        } else {
            this.setLocation(0,0);
            JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            logger.logERROR("Could not set location of Image Viewer GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");
        }

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            logger.logERROR("Could not set desired Look And Feel for Main GUI");
            logger.logERROR(e);
        }



        InputStream imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "Open16.gif");

        try {
            this.setIconImage(ImageIO.read(imageStream));
        } catch (IOException e) {
            logger.logERROR("Could not load icon: Open16.gif");
            logger.logERROR(e);
        }

        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new GridBagLayout());

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(this.createImageListPanel(), posBackgroundPanel);
        backgroundPanel.add(this.createInputPanel(), posBackgroundPanel.nextCol());
        backgroundPanel.add(this.createOutputPanel());

        this.getContentPane().add(backgroundPanel, BorderLayout.CENTER);

    }

    private JPanel createOutputPanel() {
        // TODO Auto-generated method stub
        return new JPanel();
    }

    private JPanel createImageListPanel() {
        // TODO Auto-generated method stub
        return new JPanel();
    }

    private JPanel createInputPanel() {

        JLabel resizeDestinationPathLabel = new JLabel(lang.get("labels.destinationPath"));
        resizeDestinationPathLabel.setForeground(Color.GRAY);

        ImageIcon openPictureImageIcon = new ImageIcon();
        try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/open.gif")){
            openPictureImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException iox) {
            logger.logERROR("Could not open the image open.gif");
            logger.logERROR(iox);
        }

        resizeDestinationPathTextField = new JTextField();
        resizeDestinationPathTextField.setEditable(false);
        resizeDestinationPathTextField.setBackground(Color.WHITE);

        resizeDestinationPathButton = new JButton(openPictureImageIcon);
        resizeDestinationPathButton.setActionCommand("destinationPathButton");
        resizeDestinationPathButton.addActionListener(new ResizeDestinationPathButtonListener());

        resizeDestinationPathButton.setToolTipText(lang.get("tooltip.destinationPathButton"));
        resizeDestinationPathButton.setPreferredSize(new Dimension(30, 20));
        resizeDestinationPathButton.setMinimumSize(new Dimension(30, 20));
        resizeDestinationPathButton.setEnabled(false);

        GBHelper posBackground = new GBHelper();
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.add(resizeDestinationPathLabel, posBackground);
        backgroundPanel.add(resizeDestinationPathTextField, posBackground.nextRow());
        backgroundPanel.add(resizeDestinationPathButton, posBackground.nextCol());

        return backgroundPanel;
    }


    private class WindowDestroyer extends WindowAdapter {
        @Override
        public void windowClosing (WindowEvent e) {
            disposeFrame();
        }
    }

    private void disposeFrame() {
        this.saveSettings();
        this.setVisible(false);
        this.dispose();
    }

    private void saveSettings() {
        // TODO Auto-generated method stub

    }

    private class ResizeDestinationPathButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle(lang.get("fileSelectionDialog.destinationPathFileChooser"));

            if(chooser.showOpenDialog(ImageResizer.this) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();

//                char [] tempArray = temp.toCharArray();
//
//                for(int i = 0; i < tempArray.length; i++) {
//                    if(tempArray[i] == 92) {
//                        tempArray[i] = '/';
//                    }
//                }

                if (!ApplicationContext.getInstance().getSourcePath().equals(selectedFile)) {

//                    ApplicationContext.getInstance().setDestinationPath(String.valueOf(tempArray));
                    resizeDestinationPathTextField.setText(selectedFile.getAbsolutePath());

                } else {
                    JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.sameSourceAndDestination"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

}
