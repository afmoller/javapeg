/*******************************************************************************
 * Copyright (c) JavaPEG developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package moller.javapeg.program.gui.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import moller.javapeg.program.C;
import moller.javapeg.program.FileSelection;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.gui.frames.ImageResizer;
import moller.javapeg.program.gui.frames.ImageViewer;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.imagelistformat.ImageList;
import moller.javapeg.program.jpeg.JPEGThumbNail;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.model.ModelInstanceLibrary;
import moller.util.image.ImageUtil;
import moller.util.io.FileUtil;

/**
 * This class constructs a GUI which displays two main GUI elements:
 *
 * <ul>
 *     <li>
 *         A list which can contain paths to images.
 *     </li>
 *     <li>
 *         A thumbnail which represents a selected image path in the list of
 *         images.
 *     </li>
 * </ul>
 *
 * The possible actions are the following:
 *
 * <ul>
 *     <li>
 *         Copy all images to the system clipboard.
 *     </li>
 *     <li>
 *         Copy the selected images to the system clipboard.
 *     </li>
 *     <li>
 *         Display the images in the list in the {@link ImageViewer}
 *     </li>
 *     <li>
 *         Resize the images in the list in an separate resizing GUI.
 *     </li>
 *     <li>
 *         Saved as an list for later use.
 *     </li>
 * </ul>
 *
 * Created by Fredrik on 2015-04-17.
 */
public class ViewPanelListSection extends JPanel {

    private static final long serialVersionUID = 1L;

    private JButton removeSelectedImagesButton;
    private JButton removeAllImagesButton;
    private JButton openImageListButton;
    private JButton saveImageListButton;
    private JButton exportImageListButton;
    private JButton moveUpButton;
    private JButton moveDownButton;
    private JButton openImageViewerButton;
    private JButton moveToTopButton;
    private JButton moveToBottomButton;
    private JButton copyImageListButton;
    private JButton openImageResizerButton;

    private JLabel amountOfImagesInImageListLabel;
    private JLabel imagePreviewLabel;

    private static Configuration configuration;
    private static Language lang;
    private static Logger logger;

    private JList<File> imagesToViewList;
    private final DefaultListModel<File> imagesToViewListModel;

    private ImageViewer imageViewer;

    public ViewPanelListSection() {

        configuration = Config.getInstance().get();
        lang = Language.getInstance();
        logger = Logger.getInstance();

        imagesToViewListModel = ModelInstanceLibrary.getInstance().getImagesToViewModel();

        this.createPanel();
        this.addListeners();
    }

    private void createPanel() {

        removeSelectedImagesButton = new JButton(IconLoader.getIcon(Icons.REMOVE));
        removeSelectedImagesButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.removeSelectedImages"));

        removeAllImagesButton = new JButton(IconLoader.getIcon(Icons.REMOVE_ALL));
        removeAllImagesButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.removeAllImages"));

        openImageListButton = new JButton(IconLoader.getIcon(Icons.OPEN));
        openImageListButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.openImageList"));

        saveImageListButton = new JButton(IconLoader.getIcon(Icons.SAVE));
        saveImageListButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.saveImageList"));

        exportImageListButton = new JButton(IconLoader.getIcon(Icons.EXPORT_IMAGE_LIST));
        exportImageListButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.exportImageList"));

        moveUpButton = new JButton(IconLoader.getIcon(Icons.MOVE_UP));
        moveUpButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.moveUp"));

        moveDownButton = new JButton(IconLoader.getIcon(Icons.MOVE_DOWN));
        moveDownButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.moveDown"));

        openImageViewerButton = new JButton(IconLoader.getIcon(Icons.VIEW_IMAGES));
        openImageViewerButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.viewImages"));

        moveToTopButton = new JButton(IconLoader.getIcon(Icons.MOVE_TO_TOP));
        moveToTopButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.moveToTop"));

        moveToBottomButton = new JButton(IconLoader.getIcon(Icons.MOVE_TO_BOTTOM));
        moveToBottomButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.moveToBottom"));

        copyImageListButton = new JButton(IconLoader.getIcon(Icons.COPY));
        copyImageListButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.copyImageListToClipboard"));

        openImageResizerButton = new JButton(IconLoader.getIcon(Icons.IMAGE_RESIZER));
        openImageResizerButton.setToolTipText(lang.get("maingui.tabbedpane.imagelist.button.openImageResizer"));

        imagesToViewList = new JList<>(imagesToViewListModel);
        imagesToViewList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBorder(BorderFactory.createCompoundBorder(new TitledBorder(""), new EmptyBorder(2, 2, 2, 2)));

        GBHelper posVerticalButtonPanel = new GBHelper();

        JPanel verticalButtonPanel = new JPanel(new GridBagLayout());
        verticalButtonPanel.add(removeSelectedImagesButton, posVerticalButtonPanel);
        verticalButtonPanel.add(removeAllImagesButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(Box.createVerticalStrut(15), posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(moveToTopButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(moveUpButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(moveDownButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(moveToBottomButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(Box.createVerticalStrut(15), posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(openImageResizerButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(openImageListButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(saveImageListButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(exportImageListButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(openImageViewerButton, posVerticalButtonPanel.nextRow());
        verticalButtonPanel.add(copyImageListButton, posVerticalButtonPanel.nextRow());

        GBHelper posBackgroundPanel = new GBHelper();

        JScrollPane spImageList = new JScrollPane(imagesToViewList);

        amountOfImagesInImageListLabel = new JLabel();
        amountOfImagesInImageListLabel.setForeground(Color.GRAY);
        this.setNrOfImagesLabels();

        JLabel previewLabel = new JLabel(lang.get("maingui.tabbedpane.imagelist.label.preview"));
        previewLabel.setForeground(Color.GRAY);

        JPanel previewBackgroundPanel = new JPanel(new GridBagLayout());

        GBHelper posPreviewPanel = new GBHelper();

        imagePreviewLabel = new JLabel();

        JPanel previewPanel = new JPanel();
        previewPanel.setBorder(BorderFactory.createTitledBorder(""));

        previewPanel.add(imagePreviewLabel);

        previewBackgroundPanel.add(previewPanel, posPreviewPanel);

        backgroundPanel.add(amountOfImagesInImageListLabel, posBackgroundPanel.width(2));
        backgroundPanel.add(spImageList, posBackgroundPanel.nextRow().expandH().expandW());
        backgroundPanel.add(verticalButtonPanel, posBackgroundPanel.nextCol().align(GridBagConstraints.NORTH));
        backgroundPanel.add(previewLabel, posBackgroundPanel.nextRow());
        backgroundPanel.add(previewBackgroundPanel, posBackgroundPanel.nextRow().width(2).align(GridBagConstraints.NORTH));

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(5, 2, 0, 2));
        this.add(backgroundPanel, BorderLayout.CENTER);
    }

    private void addListeners() {
        removeSelectedImagesButton.addActionListener(new RemoveSelectedImagesListener());
        removeAllImagesButton.addActionListener(new RemoveAllImagesListener());
        openImageListButton.addActionListener(new OpenImageListListener());
        saveImageListButton.addActionListener(new SaveImageListListener());
        exportImageListButton.addActionListener(new ExportImageListListener());
        moveUpButton.addActionListener(new MoveImageUpInListListener());
        moveDownButton.addActionListener(new MoveImageDownInListListener());
        moveToTopButton.addActionListener(new MoveImageToTopInListListener());
        moveToBottomButton.addActionListener(new MoveImageToBottomInListListener());
        openImageViewerButton.addActionListener(new OpenImageViewerListener());
        openImageResizerButton.addActionListener(new OpenImageResizerListener());
        copyImageListButton.addActionListener(new CopyImageListListener());

        imagesToViewList.addListSelectionListener(new ImagesToViewListListener());
        imagesToViewListModel.addListDataListener(new ImagesToViewListModelListener());
    }

    private void setNrOfImagesLabels () {
        String nrOfImages = Integer.toString(imagesToViewListModel.size());

        amountOfImagesInImageListLabel.setText(lang.get("maingui.tabbedpane.imagelist.label.list") + " (" + nrOfImages + ")");
        amountOfImagesInImageListLabel.setToolTipText(lang.get("maingui.tabbedpane.imagelist.label.numberOfImagesInList") + " " + nrOfImages);
    }

    /**
     * Listener which listens for changes of the images to view list model.
     * Whenever the model is changed, then the {@link JLabel} which displays the
     * amount of images which are in the image view list is updated with the
     * correct amount of images.
     *
     * @author Fredrik
     *
     */
    private class ImagesToViewListModelListener implements ListDataListener {

        @Override
        public void intervalAdded(ListDataEvent e) {
            setNrOfImagesLabel();
        }

        @Override
        public void intervalRemoved(ListDataEvent e) {
            setNrOfImagesLabel();
        }

        @Override
        public void contentsChanged(ListDataEvent e) {
            setNrOfImagesLabel();
        }

        private void setNrOfImagesLabel() {
            setNrOfImagesLabels();
        }
    }

    /**
     * This class implements the actions that is taking place when the
     * "Remove image button from image view list" is clicked.
     *
     * The selected images are removed and the image after in the list is set
     * as the selected image and the preview image is set to this newly selected
     * image.
     *
     * If there is only one image left after the removal then this image is set
     * to the selected image independent of it was before or after the removed
     * image in image view list.
     *
     * If the last image is removed then the preview image is cleared.
     *
     * @author Fredrik
     *
     */
    private class RemoveSelectedImagesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!imagesToViewList.isSelectionEmpty()) {

                int firstSelectedIndex = imagesToViewList.getSelectedIndex();
                int [] selectedIndices = imagesToViewList.getSelectedIndices();

                // Remove all the selected indices
                while(selectedIndices.length > 0) {
                    imagesToViewListModel.remove(selectedIndices[0]);
                    selectedIndices = imagesToViewList.getSelectedIndices();
                }

                if (imagesToViewListModel.isEmpty()) {
                    // All images have been removed, clear the preview image
                    imagePreviewLabel.setIcon(null);
                } else {
                    if (firstSelectedIndex == 0) {
                        // If the first image was removed
                        imagesToViewList.setSelectedIndex(firstSelectedIndex);
                    } else if (firstSelectedIndex >= imagesToViewListModel.getSize()) {
                        // The last image was removed
                        imagesToViewList.setSelectedIndex(imagesToViewListModel.getSize() - 1);
                    } else  {
                        // An image in between the first and last was removed
                        imagesToViewList.setSelectedIndex(firstSelectedIndex);
                    }
                }
            }
        }
    }

    private class RemoveAllImagesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imagesToViewListModel.size() > 0) {
                imagesToViewListModel.clear();
                imagePreviewLabel.setIcon(null);
            }
        }
    }

    private class MoveImageUpInListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selecteIndex = imagesToViewList.getSelectedIndex();

            if(selecteIndex > -1) {
                if(selecteIndex > 0) {
                    File image = imagesToViewListModel.remove(selecteIndex);
                    imagesToViewListModel.add(selecteIndex - 1, image);
                    imagesToViewList.setSelectedIndex(selecteIndex - 1);
                }
            }
        }
    }

    private class MoveImageDownInListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selecteIndex = imagesToViewList.getSelectedIndex();

            if(selecteIndex > -1) {
                if(selecteIndex < (imagesToViewListModel.size() - 1)) {
                    File image = imagesToViewListModel.remove(selecteIndex);
                    imagesToViewListModel.add(selecteIndex + 1, image);
                    imagesToViewList.setSelectedIndex(selecteIndex + 1);
                }
            }
        }
    }

    private class MoveImageToTopInListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = imagesToViewList.getSelectedIndex();

            if (selectedIndex > 0) {
                File image = imagesToViewListModel.remove(selectedIndex);
                imagesToViewListModel.add(0, image);
                imagesToViewList.setSelectedIndex(0);
            }
        }
    }

    private class MoveImageToBottomInListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = imagesToViewList.getSelectedIndex();

            if (selectedIndex > -1) {
                File image = imagesToViewListModel.remove(selectedIndex);
                imagesToViewListModel.addElement(image);
                imagesToViewList.setSelectedIndex(imagesToViewListModel.size() - 1);
            }
        }
    }

    private class ImagesToViewListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int selectedIndex = imagesToViewList.getSelectedIndex();

            if (selectedIndex > -1) {
                JPEGThumbNail thumbNail = JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(imagesToViewListModel.get(selectedIndex));
                ImageIcon imageIcon = ImageUtil.rotateIfNeeded(new ImageIcon(thumbNail.getThumbNailData()), thumbNail.getMetaData());
                imagePreviewLabel.setIcon(imageIcon);
            }
        }
    }

    private class OpenImageViewerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!imagesToViewListModel.isEmpty()) {

                ApplicationContext ac = ApplicationContext.getInstance();
                if (ac.isImageViewerDisplayed()) {
                    JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.onlyOneImageViewer"), lang.get("errormessage.maingui.informationMessageLabel"), JOptionPane.INFORMATION_MESSAGE);
                } else {
                    java.util.List<File> imagesToView = new ArrayList<>();

                    for (int i = 0; i < imagesToViewListModel.size(); i++) {
                        imagesToView.add(imagesToViewListModel.get(i));
                    }

                    imageViewer = new ImageViewer(imagesToView);
                    imageViewer.setVisible(true);

                    // This can only be done when the image viewer is visible
                    // and with an image loaded, otherwise does nothing happen
                    // since the image is of size 0 pixels before the window is
                    // displayed.
                    if (configuration.getImageViewerState().isAutomaticallyResizeImages()) {
                        imageViewer.resizeImage();
                    }

                    ac.setImageViewerDisplayed(true);
                }
            }
        }
    }

    private class OpenImageListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            FileNameExtensionFilter fileFilterPolyView = new FileNameExtensionFilter("JavaPEG Image List", "jil");

            JFileChooser chooser = new JFileChooser();

            chooser.setAcceptAllFileFilterUsed(false);
            chooser.setDialogTitle(lang.get("maingui.tabbedpane.imagelist.filechooser.openImageList.title"));
            chooser.addChoosableFileFilter(fileFilterPolyView);


            int returnVal = chooser.showOpenDialog(null);
            if(returnVal == JFileChooser.APPROVE_OPTION) {

                if(!imagesToViewListModel.isEmpty()) {
                    int returnValue = JOptionPane.showConfirmDialog(null, lang.get("maingui.tabbedpane.imagelist.filechooser.openImageList.nonSavedImageListMessage"));

                    /**
                     * 0 indicates a yes answer, and then the current list
                     * shall be overwritten, otherwise just return.
                     */
                    if(returnValue != 0) {
                        return;
                    }
                }

                File source = chooser.getSelectedFile();

                try {
                    List<String> fileContent = FileUtil.readFromFile(source);

                    imagesToViewListModel.clear();

                    List<String> notExistingFiles = new ArrayList<>();

                    for(String filePath : fileContent) {
                        File file = new File(filePath);

                        if(file.exists()) {
                            imagesToViewListModel.addElement(file);
                        } else {
                            notExistingFiles.add(filePath);
                        }
                    }

                    if(!notExistingFiles.isEmpty()) {
                        StringBuilder missingFilesErrorMessage = new StringBuilder();

                        missingFilesErrorMessage.append(lang.get("maingui.tabbedpane.imagelist.filechooser.openImageList.missingFilesErrorMessage"));
                        missingFilesErrorMessage.append(C.LS);
                        missingFilesErrorMessage.append(C.LS);

                        for(String path : notExistingFiles) {
                            missingFilesErrorMessage.append(path);
                            missingFilesErrorMessage.append(C.LS);
                        }

                        JOptionPane.showMessageDialog(null, missingFilesErrorMessage, lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(null, lang.get("maingui.tabbedpane.imagelist.filechooser.openImageList.couldNotReadFile") + "\n(" + source.getAbsolutePath() + ")", lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
                    logger.logERROR("Could not read from file:");
                    logger.logERROR(ioe);
                }
            }
        }
    }

    private class SaveImageListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (imagesToViewListModel.size() > 0) {

                FileNameExtensionFilter fileFilterPolyView = new FileNameExtensionFilter("JavaPEG Image List", "jil");

                JFileChooser chooser = new JFileChooser();

                chooser.setAcceptAllFileFilterUsed(false);
                chooser.setDialogTitle(lang.get("maingui.tabbedpane.imagelist.filechooser.saveImageList.title"));
                chooser.addChoosableFileFilter(fileFilterPolyView);

                int returnVal = chooser.showSaveDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File destination = chooser.getSelectedFile();
                    ImageList.getInstance().createList(imagesToViewListModel, destination, "jil", "JavaPEG Image List");
                }
            }
        }
    }

    private class OpenImageResizerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!imagesToViewListModel.isEmpty()) {

                List<File> imagesToResize = new ArrayList<>();

                for (int i = 0; i < imagesToViewListModel.size(); i++) {
                    imagesToResize.add(imagesToViewListModel.get(i));
                }

                ImageResizer imageresizer = new ImageResizer(imagesToResize);
                imageresizer.setVisible(true);
            }
        }
    }

    private class CopyImageListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!imagesToViewListModel.isEmpty()) {
                List<File> imagesToSetToSystemClipBoard = new ArrayList<>();

                for (int i = 0; i < imagesToViewListModel.size(); i++) {
                    imagesToSetToSystemClipBoard.add(imagesToViewListModel.get(i));
                }
                FileSelection fileSelection = new FileSelection(imagesToSetToSystemClipBoard);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
            }
        }
    }

    private class ExportImageListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (imagesToViewListModel.size() > 0) {

                FileNameExtensionFilter fileFilterIrfanView = new FileNameExtensionFilter("IrfanView", "txt");
                FileNameExtensionFilter fileFilterPolyView  = new FileNameExtensionFilter("PolyView" , "pvs");
                FileNameExtensionFilter fileFilterXnView    = new FileNameExtensionFilter("XnView"   , "sld");

                JFileChooser chooser = new JFileChooser();

                chooser.setAcceptAllFileFilterUsed(false);
                chooser.setDialogTitle(lang.get("maingui.tabbedpane.imagelist.filechooser.exportImageList.title"));
                chooser.addChoosableFileFilter(fileFilterIrfanView);
                chooser.addChoosableFileFilter(fileFilterPolyView);
                chooser.addChoosableFileFilter(fileFilterXnView);

                int returnVal = chooser.showSaveDialog(null);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    File destination = chooser.getSelectedFile();

                    String listFormat      = ((FileNameExtensionFilter)chooser.getFileFilter()).getExtensions()[0];
                    String listDescription = chooser.getFileFilter().getDescription();

                    ImageList.getInstance().createList(imagesToViewListModel, destination, listFormat, listDescription);
                }
            }
        }
    }

    /**
     * This method adds an image to the image view list {@link JList} and
     * set this image is also set as the selected image. It also updates the
     * label that displays the amount of images in the list. It also adds the
     * image to the {@link ImageViewer} if that one is displayed.
     *
     * @param image
     *            is the image to add.
     * @param addMetaData
     *            specifies if the meta data that is associated to the images to
     *            view list should be updated or not.
     */
    public void handleAddImageToImageList(File image, boolean addMetaData) {
        imagesToViewListModel.addElement(image);

        if (addMetaData) {
            imagesToViewList.setSelectedIndex(imagesToViewListModel.getSize() - 1);
            imagesToViewList.ensureIndexIsVisible(imagesToViewList.getSelectedIndex());
        }

        if (ApplicationContext.getInstance().isImageViewerDisplayed()) {
            imageViewer.addImage(image);
        }
    }
}
