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

/**
 * This class is used in the Image Merge functionality of JavaPEG.
 * <p>
 * This class constructs a modal, which makes the current thread execution to
 * get paused (the application execution is paused and waits for the result from
 * this class), {@link JDialog} window that displays a matrix with conflicting
 * JPEG images.
 * <p>
 * Each row in the matrix displays thumbnails for the files that have the same
 * MD5 sum. Along with the thumbnails is also a checkbox for each thumbnail.
 * <p>
 * These checkboxes makes it possible to chose which images, based on the
 * thumbnails, that shall be copied to the destination directory.
 * <p>
 * For instance, if three directories are selected to be merged and a file with
 * the same MD5 sum exists in all three directories, or in the same directory
 * but with another file name, then will these three files be presented on one
 * row and in three columns in the matrix.
 * <p>
 * The component that creates an instance of this class may also ask for the
 * selection result via an public method of this class.
 *
 *
 * @author Fredrik
 *
 */
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

    /**
     * Constructor, creates an initiates this GUI component.
     *
     * @param md5ToFileListMap
     *            is the container that contains JPEG images grouped by their
     *            MD5 sum. All keys in the {@link Map} that has more than one
     *            entry in the value {@link List}:s will be displayed by this
     *            GUI.
     */
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
     * Helper method that puts all conflicting JPEG images as a thumbnail and a
     * checkbox in a matrix.
     *
     * @param md5ToFileListMap
     *            is the container that contains JPEG images grouped by their
     *            MD5 sum. All keys in the {@link Map} that has more than one
     *            entry in the value {@link List}:s will be displayed by this
     *            GUI.
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

    /**
     * Creates the main window and configures it according to persisted
     * configuration.
     */
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

    /**
     * Helper method to create the thumbnails background area, with a
     * {@link JScrollPane}.
     *
     * @return the created and configured {@link JScrollPane} component.
     */
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

    /**
     * Put the properties of this component that shall be persisted at
     * application exit to the application {@link Config}
     */
    private void saveSettings() {
        GUI gUI = configuration.getgUI();

        Rectangle sizeAndLocation = gUI.getImageSearchResultViewer().getSizeAndLocation();

        sizeAndLocation.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);
        sizeAndLocation.setSize(this.getSize().width, this.getSize().height);
    }

    private void addListeners() {
        this.addWindowListener(new WindowDestroyer());
    }

    /**
     * Creates one thumbnail checkbox combination as a component surrounded by a
     * {@link JPanel}
     *
     * @param setSelected
     *            decides whether or not the {@link JCheckBoxWithUserObject}
     *            shall be selected or not.
     * @param jpegFile
     *            is the file that shall be shown as thumbnail in this
     *            component.
     * @return a {@link JPanel} that contains a {@link JLabel} with an icon (the
     *         image thumbnail) and a {@link JCheckBoxWithUserObject}.
     */
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

    /**
     * Helper method that executes the tasks that has to be performed when the
     * window is to be disposed, call the saveSettings method for instance.
     */
    private void disposeFrame() {
        this.saveSettings();
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Listener that is activated when the window is closing.
     *
     * @author Fredrik
     *
     */
    private class WindowDestroyer extends WindowAdapter {
        @Override
        public void windowClosing (WindowEvent e) {
            disposeFrame();
        }
    }

    /**
     * Returns a {@link List} of {@link File} objects for the images that have
     * been selected in this window by the user.
     *
     * @return a {@link List} with {@link File} objects which contains the
     *         images that have been selected by the user in this window.
     */
    public List<File> getSelectedImageFiles() {
        List<File> selectedFiles = new ArrayList<File>();

        for (JCheckBoxWithUserObject<File> checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                selectedFiles.add(checkBox.getUserObject());
            }
        }
        return selectedFiles;
    }
}
