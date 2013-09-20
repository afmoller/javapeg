package moller.javapeg.program.gui.tab;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.Gap;
import moller.javapeg.program.MainGUI;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.enumerations.MainTabbedPaneComponent;
import moller.javapeg.program.gui.components.DestinationDirectorySelector;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.model.SortedListModel;
import moller.javapeg.program.progress.CustomizedJTextArea;

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

    private static Language lang;
    private static Logger logger;

    private JButton removeSelectedDirectoryButton;
    private JButton addDirectoryButton;
    private JButton mergeDirectoryButton;

    private CustomizedJTextArea outputTextArea;

    private JList<File> directoriesToMergeList;

    /**
     * Constructor
     */
    public ImageMergeTab() {
        super();

        lang = Language.getInstance();
        logger = Logger.getInstance();

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

        DestinationDirectorySelector destinationDirectorySelector = new DestinationDirectorySelector(true);

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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        buttonPanel.add(addDirectoryButton);
        buttonPanel.add(mergeDirectoryButton);

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

    private class MergeDirectoryButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (directoriesToMergeList.getModel().getSize() > 1) {
                System.out.println("MERGE");
//                doMerge();
            }
            // TODO Auto-generated method stub

        }

    }

}
