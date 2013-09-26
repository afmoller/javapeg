package moller.javapeg.program.gui.tab;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.Gap;
import moller.javapeg.program.MainGUI;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.enumerations.MainTabbedPaneComponent;
import moller.javapeg.program.gui.ImageMergeConflictViewer;
import moller.javapeg.program.gui.components.DestinationDirectorySelector;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.model.SortedListModel;
import moller.javapeg.program.progress.CustomizedJTextArea;
import moller.util.hash.MD5Calculator;
import moller.util.io.DirectoryUtil;
import moller.util.io.FileUtil;
import moller.util.jpeg.JPEGUtil;

/**
 * This class constructs a GUI that is to be added to the {@link JTabbedPane}
 * component in the {@link MainGUI} class.
 * <p>
 * The GUI provides a merge functionality for directories containing images. It
 * is possible to merge an unlimited amount of directories into a specified
 * destination directory.
 * <p>
 * The mechanism to detect duplicates are to calculated MD5 sums for each image
 * in the specified directories to merge and all images which have a unique MD5
 * sum will be copied into the destination directory.
 * <p>
 * If a conflict (the same MD5 sum) is detected, then the user is asked what to
 * do: either copy one of the conflicting images, both images (or all if more
 * than one conflict is detected).
 * <p>
 * The user will guided in the selection process by displayed thumbnails for
 * all images that have a conflicting MD5 sum.
 *
 * @author Fredrik
 *
 */
public class ImageMergeTab extends JPanel {

    private static final long serialVersionUID = 1L;

    private static Configuration configuration;
    private static Language lang;
    private static Logger logger;

    private final SimpleDateFormat sdf;

    private JButton addDirectoryButton;
    private JButton cancelMergeButton;
    private JButton mergeDirectoryButton;
    private JButton removeSelectedDirectoryButton;

    private CustomizedJTextArea outputTextArea;

    private JList<File> directoriesToMergeList;

    private DestinationDirectorySelector destinationDirectorySelector;

    private ImageMergeWorker imw;

    /**
     * Constructor
     */
    public ImageMergeTab() {
        super();

        configuration = Config.getInstance().get();
        lang = Language.getInstance();
        logger = Logger.getInstance();

        sdf = configuration.getRenameImages().getProgressLogTimestampFormat();

        this.createPanel();
        this.addListeners();
    }

    /**
     * Adds the listeners to the objects that executes actions in this
     * component.
     */
    private void addListeners() {
        removeSelectedDirectoryButton.addActionListener(new RemoveSelectedDirectoryButtonListener());
        addDirectoryButton.addActionListener(new AddDirectoryButtonListener());
        mergeDirectoryButton.addActionListener(new MergeDirectoryButtonListener());
        cancelMergeButton.addActionListener(new CancelMergeButtonListener());
    }

    private void createPanel() {
        this.setName(MainTabbedPaneComponent.MERGE.toString());

        this.setLayout(new GridBagLayout());
        this.setBorder(new EmptyBorder(2, 2, 2, 2));

        GBHelper posBackground = new GBHelper();

        this.add(this.createDirectoriesPanel(), posBackground.expandH());
        this.add(new Gap(2), posBackground.nextCol());
        this.add(this.createProcessLogPanel(), posBackground.nextCol().expandH().expandW());

    }

    private JPanel createProcessLogPanel() {

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        GBHelper posBackgroundPanel = new GBHelper();

        JLabel outputLabel = new JLabel(lang.get("imageresizer.processlog.title"));
        outputLabel.setForeground(Color.GRAY);

        outputTextArea = new CustomizedJTextArea();
        outputTextArea.setEditable(false);

        JScrollPane sp = new JScrollPane(outputTextArea);

        backgroundPanel.add(outputLabel,posBackgroundPanel);
        backgroundPanel.add(sp, posBackgroundPanel.nextRow().expandH().expandW());

        return backgroundPanel;
    }

    private JPanel createDirectoriesPanel() {

//        TODO: Remove hard coded string
        JLabel directoryListLabel = new JLabel("DIRECTORIES TO MERGE");
        directoryListLabel.setForeground(Color.GRAY);

        SortedListModel<File> directoriesModel = new SortedListModel<File>();
        directoriesToMergeList = new JList<File>(directoriesModel);

        directoriesToMergeList.setModel(directoriesModel);

        JScrollPane scrollPane = new JScrollPane(directoriesToMergeList);

        destinationDirectorySelector = new DestinationDirectorySelector(true);

        ImageIcon removePictureImageIcon = new ImageIcon();
        try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/viewtab/remove.gif");){
            removePictureImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException iox) {
            logger.logERROR("Could not open the image remove.gif");
            logger.logERROR(iox);
        }
        removeSelectedDirectoryButton = new JButton();
        removeSelectedDirectoryButton.setIcon(removePictureImageIcon);
//      TODO: Remove hard coded string
        removeSelectedDirectoryButton.setToolTipText("Remove selected directory");

        JPanel removeSelectedDirectoryButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        removeSelectedDirectoryButtonPanel.add(removeSelectedDirectoryButton);


        ImageIcon addPictureImageIcon = new ImageIcon();
        try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/plus_16.png")) {
            addPictureImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException iox) {
            logger.logERROR("Could not open the image plus_16.png");
            logger.logERROR(iox);
        }

        addDirectoryButton = new JButton();
        addDirectoryButton.setIcon(addPictureImageIcon);
//      TODO: Remove hard coded string
        addDirectoryButton.setToolTipText("Add currently displayed  directory");

        ImageIcon mergePictureImageIcon = new ImageIcon();
        try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/arrow_join.png")) {
            mergePictureImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException iox) {
            logger.logERROR("Could not open the image arrow_join.png");
            logger.logERROR(iox);
        }

        mergeDirectoryButton = new JButton();
        mergeDirectoryButton.setIcon(mergePictureImageIcon);
//      TODO: Remove hard coded string
        mergeDirectoryButton.setToolTipText("Merge the images in selected directories");




        ImageIcon cancelMergePictureImageIcon = new ImageIcon();
        try (InputStream imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/cancel.png")) {
            cancelMergePictureImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException iox) {
            logger.logERROR("Could not open the image arrow_join.png");
            logger.logERROR(iox);
        }

        cancelMergeButton = new JButton();
        cancelMergeButton.setIcon(cancelMergePictureImageIcon);
//      TODO: Remove hard coded string
        cancelMergeButton.setToolTipText("Cancel the merge process");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        buttonPanel.add(addDirectoryButton);
        buttonPanel.add(mergeDirectoryButton);
        buttonPanel.add(cancelMergeButton);

        JPanel backgroundPanel = new  JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        GBHelper posBackgroundPanel = new GBHelper();

        backgroundPanel.add(directoryListLabel, posBackgroundPanel);
        backgroundPanel.add(scrollPane, posBackgroundPanel.nextRow().expandH().expandW());
        backgroundPanel.add(removeSelectedDirectoryButtonPanel, posBackgroundPanel.nextRow().expandW());
        backgroundPanel.add(destinationDirectorySelector, posBackgroundPanel.nextRow().expandH().expandW());
        backgroundPanel.add(buttonPanel, posBackgroundPanel.nextRow().expandW());

        return backgroundPanel;
    }

    /**
     * Listener class for the Button used to remove an already selected
     * directory to merge. It removes a selected entry in the directory to merge
     * {@link JList} list.
     *
     * @author Fredrik
     *
     */
    private class RemoveSelectedDirectoryButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (File selectedValue : directoriesToMergeList.getSelectedValuesList()) {
                ((SortedListModel<File>)directoriesToMergeList.getModel()).removeElement(selectedValue);
            }
        }
    }

    /**
     * Listener class for the Button that adds a directory to the {@link JList}
     * that contains the directories in which the content shall be merged.
     *
     * @author Fredrik
     *
     */
    private class AddDirectoryButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            File sourcePath = ApplicationContext.getInstance().getSourcePath();
            if (sourcePath != null) {
                ((SortedListModel<File>)directoriesToMergeList.getModel()).add(sourcePath);
            }
        }
    }

    /**
     * Listener class for the Button that starts the merge process.
     *
     * @author Fredrik
     *
     */
    private class MergeDirectoryButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (directoriesToMergeList.getModel().getSize() > 1 && !destinationDirectorySelector.getText().isEmpty()) {
                mergeDirectoryButton.setEnabled(false);
                imw = new ImageMergeWorker();
                imw.addPropertyChangeListener(new PropertyChangeListener() {
                    @Override
                    public void propertyChange(final PropertyChangeEvent event) {
                        switch (event.getPropertyName()) {
                        case "progress":
//                            progressBar.setIndeterminate(false);
//                            progressBar.setValue((Integer) event.getNewValue());
                            break;
                        }
                    }
                });
                imw.execute();
            }
        }
    }

    private class CancelMergeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imw != null && !imw.isCancelled()) {
                imw.cancel(true);
            }
        }
    }

    private class ImageMergeWorker extends SwingWorker<String, String> {
        @Override
        protected String doInBackground() throws Exception {
            long startTime = System.currentTimeMillis();

            File destinationDirectory = new File(destinationDirectorySelector.getText(), "merged");

            destinationDirectory = DirectoryUtil.getUniqueDirectory(destinationDirectory.getParentFile(), destinationDirectory);

//            TODO: Fix hard coded string
            publish("Merge Process started");

            if (!destinationDirectory.mkdirs()) {
                publish("Could not create directory" + ": " + destinationDirectory.getAbsolutePath());
                logger.logERROR("Could not create directory:" + destinationDirectory.getAbsolutePath());

//                TODO: Fix hard coded string
                return "Merge process aborted";
            } else {
                publish("Destination directory created" + " " + destinationDirectory.getAbsolutePath());
                publish("Searching selected directories for JPEG files");

                List<File> jpegFiles = findJpegFilesFromSelectedDirectories();

                Map<String, List<File>> md5ToFileListMap = calculateMD5SumsForFoundJpegFiles(jpegFiles);

                List<File> nonConflictingFiles = getNonConflictingFiles(md5ToFileListMap);

                List<File> selectedFilesFromMergeConflictViwer = getFilesFromConflictViewer(md5ToFileListMap);

                for (File nonConflictingFile : nonConflictingFiles) {
                    FileUtil.copyFileTodirectory(nonConflictingFile, destinationDirectory, true);
                }

                for (File selectedFileFromMergeConflictViwer : selectedFilesFromMergeConflictViwer) {
                    FileUtil.copyFileTodirectory(selectedFileFromMergeConflictViwer, destinationDirectory, true);
                }


                if (!imw.isCancelled()) {
                    publish(String.format(lang.get("imageresizer.processlog.image.resize.done"), (System.currentTimeMillis() - startTime) / 1000));
                    return lang.get("imageresizer.resize.process.done");
                } else {
                    publish(String.format(lang.get("imageresizer.processlog.image.resize.done.cancelled"), (System.currentTimeMillis() - startTime) / 1000));

                    mergeDirectoryButton.setEnabled(true);
                    return lang.get("imageresizer.resize.process.cancelled");
                }
            }
        }

        /**
         * Find all files that have a unique MD5 sum.
         *
         * @param md5ToFileListMap
         *            is the {@link Map} that contains all JPEG files grouped by
         *            their MD5 sum.
         * @return a {@link List} of {@link File} object for all JPEG files that
         *         have no conflict.
         */
        private List<File> getNonConflictingFiles(Map<String, List<File>> md5ToFileListMap) {
            List<File> nonConflictingFiles = new ArrayList<File>();

            for (String hash : md5ToFileListMap.keySet()) {
                if (md5ToFileListMap.get(hash).size() == 1) {
                    nonConflictingFiles.addAll(md5ToFileListMap.get(hash));
                }
            }

            return nonConflictingFiles;
        }

        /**
         * Calculates the MD5 sum for JPEG files and puts the result in a
         * {@link Map} with the MD5 sum as key and the file as part of a
         * {@link List} of type {@link File} as value in the {@link Map}.
         *
         * @param jpegFiles
         *            is the list of JPEG files to calculate the MD5 sum for.
         * @return a {@link Map} containing {@link List}:s of {@link File}
         *         objects (Value) grouped by their MD5 sum (Key).
         */
        private Map<String, List<File>> calculateMD5SumsForFoundJpegFiles(List<File> jpegFiles) {
            Map<String, List<File>> md5ToFileListMap = new HashMap<String, List<File>>();
            for (File file : jpegFiles) {
                long startHashCalc = System.currentTimeMillis();
                String hash = MD5Calculator.calculate(file);
//                TODO Fix hard coded string
                publish("MD5 sum calculated (" + hash + ") for file: " + file.getAbsolutePath() + " (" + (System.currentTimeMillis() - startHashCalc) + " milliseconds");
                if (!md5ToFileListMap.containsKey(hash)) {
                    List<File> files = new ArrayList<File>();
                    files.add(file);
                    md5ToFileListMap.put(hash, files);
                } else {
                    md5ToFileListMap.get(hash).add(file);
                }
            }
            return md5ToFileListMap;
        }

        /**
         * Finds all JPEG files in the selected directories that shall be
         * merged.
         *
         * @return a {@link List} of {@link File} object for all found JPEG
         *         files in the specified directories
         * @throws FileNotFoundException
         * @throws IOException
         */
        private List<File> findJpegFilesFromSelectedDirectories() throws FileNotFoundException, IOException {

            List<File> jpegFiles = new ArrayList<File>();

            int nrOfFiles = 0;
            for (File directory : ((SortedListModel<File>)directoriesToMergeList.getModel()).getModel()) {
                jpegFiles.addAll(JPEGUtil.getJPEGFiles(directory));
//                TODO: Fix hard coded string
                publish(jpegFiles.size() - nrOfFiles + " " + "JPEG files found in directory: " + directory.getAbsolutePath());
                nrOfFiles = jpegFiles.size();
            }

            return jpegFiles;
        }

        /**
         * Display an image conflicts viewer in a JDialog window, and let the
         * user select which images(s) to use when a conflict has been detected.
         * It is possible to select all conflicting images if wanted.
         *
         * @param md5ToFileListMap
         *            is the {@link Map} that contains all the images from the
         *            selected directories grouped by their MD5 hash sum.
         * @return a list of {@link File} objects that the user selected in the
         *         viewer.
         */
        private List<File> getFilesFromConflictViewer(Map<String, List<File>> md5ToFileListMap) {
            ImageMergeConflictViewer imcv = new ImageMergeConflictViewer(md5ToFileListMap);
            imcv.setVisible(true);

            List<File> selectedImages = imcv.getSelectedImageFiles();

            for (File selectedImage : selectedImages) {
                publish(selectedImage.getAbsolutePath());
            }

            return selectedImages;
        }

        @Override
        protected void process(List<String> chunks) {
            for (String chunk : chunks) {
                setLogMessage(chunk);
            }
        }

        @Override
        protected void done() {
            try {
                JOptionPane.showMessageDialog(ImageMergeTab.this, get());
                mergeDirectoryButton.setEnabled(true);
            } catch (ExecutionException e) {
                throw new RuntimeException(e.getCause());
            } catch (InterruptedException e) {
                mergeDirectoryButton.setEnabled(true);
            } catch (Exception e) {
            }
        }
    }

    /**
     * Convenience method for adding a log statement to the process log. After
     * each log statement there is automatically a newline added, and the log
     * is automatically scrolled to bottom.
     *
     * @param logMessage is the string that shall be added to the log.
     */
    public void setLogMessage(String logMessage) {
        String formattedTimeStamp = sdf.format(new Date(System.currentTimeMillis()));
        outputTextArea.appendAndScroll(formattedTimeStamp + ": " + logMessage + "\n");
        logger.logDEBUG(logMessage);
    }
}
