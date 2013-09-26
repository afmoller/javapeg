package moller.javapeg.program.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.jpeg.JPEGThumbNail;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.gui.Screen;

public class ImageMergeConflictViewer extends JDialog {

    private static final long serialVersionUID = 1L;

    private static Configuration configuration;
    private static Logger logger;
    private static Language lang;


    private GridLayout thumbNailGridLayout;
    private JPanel thumbNailsPanel;
    private JScrollPane scrollpane;
    private List<JCheckBoxWithUserObject<File>> checkBoxes;

    private final int maxNrOfConflicts;

    public ImageMergeConflictViewer(Map<String, List<File>> md5ToFileListMap) {
        configuration = Config.getInstance().get();
        logger = Logger.getInstance();
        lang   = Language.getInstance();

        maxNrOfConflicts = getMaxNrOfConflicts(md5ToFileListMap);

        this.createMainFrame();
        this.addListeners();
        this.initiateGUI(md5ToFileListMap);
    }

    /**
     *
     * @param md5ToFileListMap
     */
    private void initiateGUI(Map<String, List<File>> md5ToFileListMap) {

        checkBoxes = new ArrayList<JCheckBoxWithUserObject<File>>();

        for (String hash : md5ToFileListMap.keySet()) {
            List<File> jpegFiles = md5ToFileListMap.get(hash);

            if (jpegFiles.size() > 1) {
                for (int i = 0; i < jpegFiles.size(); i++) {
                    thumbNailsPanel.add(createThumbnailAndCheckBoxContainer(i == 0, jpegFiles.get(i)));
                    thumbNailsPanel.updateUI();
                }

                for (int i = jpegFiles.size(); i < maxNrOfConflicts; i++) {
                    thumbNailsPanel.add(new JPanel());
                    thumbNailsPanel.updateUI();
                }
            }
        }
    }

    /**
     * Calculates the maximum amount of conflicts, how many times the same image
     * was found among the selected directories.
     *
     * @param md5ToFileListMap
     *            contains all images grouped by their MD5 sum.
     * @return the highest number, most conflicting image, that means how many
     *         times the same occurs among the selected directories.
     */
    private int getMaxNrOfConflicts(Map<String, List<File>> md5ToFileListMap) {
        int maxNrOfConflicts = 0;
        for (String hash : md5ToFileListMap.keySet()) {
            if (md5ToFileListMap.get(hash).size() > maxNrOfConflicts) {
                maxNrOfConflicts = md5ToFileListMap.get(hash).size();
            }
        }

        return maxNrOfConflicts;
    }

    // Create Main Window
    public void createMainFrame() {

        GUI gUI = configuration.getgUI();

        Rectangle sizeAndLocation = gUI.getImageSearchResultViewer().getSizeAndLocation();

        this.setSize(sizeAndLocation.getSize());

        Point xyFromConfig = new Point(sizeAndLocation.getLocation());

        if (Screen.isVisibleOnScreen(sizeAndLocation)) {
            this.setLocation(xyFromConfig);
            this.setSize(sizeAndLocation.getSize());
        } else {
            JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            logger.logERROR("Could not set location of Image Search Result Viewer GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");

            this.setLocation(0,0);
            this.setSize(GUIDefaults.IMAGE_SEARCH_RESULT_VIEWER_WIDTH, GUIDefaults.IMAGE_SEARCH_RESULT_VIEWER_HEIGHT);
        }

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            logger.logERROR("Could not set desired Look And Feel for Image Search Result Viewer GUI");
            logger.logERROR(e);
        }

        this.setTitle(lang.get("imagesearchresultviewer.title"));
        this.setModalityType(ModalityType.APPLICATION_MODAL);
        this.getContentPane().add(this.createThumbNailsBackgroundPanel(), BorderLayout.CENTER);
    }

    private JScrollPane createThumbNailsBackgroundPanel(){

        thumbNailGridLayout = new GridLayout(0, maxNrOfConflicts);
        thumbNailsPanel = new JPanel(thumbNailGridLayout);

        JScrollBar hSB = new JScrollBar(JScrollBar.HORIZONTAL);
        JScrollBar vSB = new JScrollBar(JScrollBar.VERTICAL);

        hSB.setUnitIncrement(40);
        vSB.setUnitIncrement(40);

        scrollpane = new JScrollPane(thumbNailsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollpane.setHorizontalScrollBar(hSB);
        scrollpane.setVerticalScrollBar(vSB);

        return scrollpane;
    }

    private void saveSettings() {
        GUI gUI = configuration.getgUI();

        Rectangle sizeAndLocation = gUI.getImageSearchResultViewer().getSizeAndLocation();

        sizeAndLocation.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);
        sizeAndLocation.setSize(this.getSize().width, this.getSize().height);
    }

    private void addListeners() {
        this.addWindowListener(new WindowDestroyer());
    }

    private JPanel createThumbnailAndCheckBoxContainer(boolean setSelected, File jpegFile) {
        JPanel thumbnailAndCheckBoxPanel = new JPanel(new BorderLayout());
        thumbnailAndCheckBoxPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        JPEGThumbNail tn = JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(jpegFile);
        JLabel thumbNail = new JLabel(new ImageIcon(tn.getThumbNailData()));

        JCheckBoxWithUserObject<File> checkBox = new JCheckBoxWithUserObject<File>(jpegFile);
        checkBox.setSelected(setSelected);

        thumbnailAndCheckBoxPanel.add(thumbNail, BorderLayout.CENTER);
        thumbnailAndCheckBoxPanel.add(checkBox, BorderLayout.SOUTH);
        checkBoxes.add(checkBox);

        return thumbnailAndCheckBoxPanel;
    }

    private void disposeFrame() {
//        this.collectSelectedImages();
        this.saveSettings();
        this.setVisible(false);
        this.dispose();
    }

    private class WindowDestroyer extends WindowAdapter {
        @Override
        public void windowClosing (WindowEvent e) {
            disposeFrame();
        }
    }

    private List<File> collectSelectedImages() {
        List<File> selectedFiles = new ArrayList<File>();

        for (JCheckBoxWithUserObject<File> checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                selectedFiles.add(checkBox.getUserObject());
            }
        }
        return selectedFiles;
    }

    public List<File> getSelectedImageFiles() {
        return collectSelectedImages();
    }
}
