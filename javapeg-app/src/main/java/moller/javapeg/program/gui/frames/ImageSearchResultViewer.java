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
package moller.javapeg.program.gui.frames;

import moller.javapeg.program.FileSelection;
import moller.javapeg.program.config.model.GUI.GUIWindow;
import moller.javapeg.program.config.model.ImageSearchResultViewerState;
import moller.javapeg.program.config.model.thumbnail.ThumbNailGrayFilter;
import moller.javapeg.program.enumerations.Direction;
import moller.javapeg.program.gui.ButtonIconUtil;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.gui.LoadedThumbnails;
import moller.javapeg.program.gui.components.StatusPanel;
import moller.javapeg.program.gui.components.ThumbNailsPanel;
import moller.javapeg.program.gui.frames.base.JavaPEGBaseFrame;
import moller.javapeg.program.gui.icons.IconLoader;
import moller.javapeg.program.gui.icons.Icons;
import moller.javapeg.program.gui.workers.SelectedImageIconGenerator;
import moller.javapeg.program.jpeg.JPEGThumbNail;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.metadata.MetaDataUtil;
import moller.javapeg.program.model.ImagesToViewModel;
import moller.javapeg.program.model.ModelInstanceLibrary;
import moller.javapeg.program.image.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.*;
import java.util.List;

public class ImageSearchResultViewer extends JavaPEGBaseFrame {

    private static final long serialVersionUID = 1L;

    private JToolBar toolBar;
    private JPopupMenu rightClickMenu;
    private StatusPanel statuspanel;

    private JMenuItem popupMenuSetSelectedToViewList;
    private JMenuItem popupMenuSelectAll;
    private JMenuItem popupMenuDeSelectAll;
    private JMenuItem popupMenuCopyImageToSystemClipBoard;
    private JMenuItem popupMenuCopySelectedImagesToSystemClipBoard;
    private JMenuItem popupMenuCopyAllImagesToSystemClipBoard;

    private int columnMargin;
    private int iconWidth;

    private final int nrOfImagesInResultSet;

    // Holds the list of all images which are part of the search result set.
    // If this set is larger then the current amount of images to display, then
    // there will be a paged display with several pages with images.
    private final List<File> imagesInSearchResult;

    private GridLayout thumbNailGridLayout;

    private ThumbNailsPanel thumbNailsPanel;
    private JScrollPane scrollpane;
    private JProgressBar thumbNailLoadingProgressBar;

    // This Set keeps track on which thumbnails that are selected in the
    //result set. This is necessary to keep track of because of the paged
    // result views.
    private final Set<FileIndex> selectedImageFileIndexObjects;

    private final Map<File, ImageIcon> imageFileToSelectedImageMapping;
    private final LoadedThumbnails loadedThumbnails;

    private SelectedImageIconGenerator selectedImageIconGenerator;

    private JComboBox<Integer> numberOfImagesToDisplaySelectionBox;
    private JButton loadPreviousImages;
    private JButton loadNextImages;

    private CustomKeyEventDispatcher customKeyEventDispatcher;

    private ImageSearchResultLoader imageSearchResultLoader = null;

    /**
     * Stores which index in the search result set List<File> that is the first
     * image that is displayed in the GUI.
     */
    private int currentStartIndexForDisplayedImages;

    public ImageSearchResultViewer(List<File> imagesInSearchResult) {

        // Must be set before the executeLoadThumbnailsProcess method is called
        this.imagesInSearchResult = imagesInSearchResult;
        nrOfImagesInResultSet = imagesInSearchResult.size();

        currentStartIndexForDisplayedImages = 0;

        loadedThumbnails = new LoadedThumbnails();
        imageFileToSelectedImageMapping = Collections.synchronizedMap(new HashMap<File, ImageIcon>());
        selectedImageFileIndexObjects = new TreeSet<>(new FileIndexComparator());

        this.createMainFrame();
        this.createToolBar();
        this.createRightClickMenu();
        this.createStatusPanel();
        this.initiateNumberOfImagesToDisplay();
        this.addListeners();

        List<File> initialimagesToLoad = getInitialimagesToLoad(imagesInSearchResult);

        this.loadThumbnailImages(initialimagesToLoad);
        this.setInitialStateOfNextAndPreviousButtons(initialimagesToLoad.size());
    }

    private void loadThumbnailImages(List<File> imagesToLoad) {
        if (imageSearchResultLoader == null || imageSearchResultLoader.isDone()) {
            // Display the progress bar, if it is hidden.
            thumbNailLoadingProgressBar.setVisible(true);

            imageSearchResultLoader = new ImageSearchResultLoader(imagesToLoad);
            imageSearchResultLoader.addPropertyChangeListener(new ImageSearhResultLoaderPropertyListener());
            imageSearchResultLoader.execute();
        }
    }

    private void setInitialStateOfNextAndPreviousButtons(int nrOfInitialImagesToLoad) {
        loadPreviousImages.setEnabled(false);
        loadNextImages.setEnabled(nrOfInitialImagesToLoad < imagesInSearchResult.size());
    }

    private List<File> getInitialimagesToLoad(List<File> imagesInResultSet) {
        int toIndex;

        if (getCurrentValueOfNumberOfImagesToDisplay() > imagesInResultSet.size() - 1) {
            toIndex = imagesInResultSet.size();
        } else {
            toIndex = getCurrentValueOfNumberOfImagesToDisplay();
        }

        setWindowTitle(toIndex);

        return imagesInResultSet.subList(0, toIndex);
    }

    private class ImageSearhResultLoaderPropertyListener implements PropertyChangeListener {
        /**
         * Invoked when task's progress property changes.
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if ("progress" == evt.getPropertyName()) {
                int progress = (Integer) evt.getNewValue();
                thumbNailLoadingProgressBar.setValue(progress);
            }
        }
    }

    // Create Main Window
    public void createMainFrame() {
        loadAndApplyGUISettings();

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        customKeyEventDispatcher = new CustomKeyEventDispatcher();
        manager.addKeyEventDispatcher(customKeyEventDispatcher);

        this.setIconImage(IconLoader.getIcon(Icons.FIND).getImage());
        this.setTitle(getLang().get("imagesearchresultviewer.title"));

        thumbNailLoadingProgressBar = new JProgressBar();
        thumbNailLoadingProgressBar.setStringPainted(true);
        thumbNailLoadingProgressBar.setVisible(true);

        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.add(this.createThumbNailsBackgroundPanel(), BorderLayout.CENTER);
        backgroundPanel.add(thumbNailLoadingProgressBar, BorderLayout.SOUTH);

        this.getContentPane().add(backgroundPanel, BorderLayout.CENTER);
    }

    /**
     * This class listens for keyboard input.
     *
     * @author Fredrik
     *
     */
    private class CustomKeyEventDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {

            if (ImageSearchResultViewer.this.isFocused()) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getModifiersEx() != KeyEvent.ALT_DOWN_MASK) {
                    if (KeyEvent.VK_LEFT == e.getKeyCode()) {
                        loadPreviousImages.doClick(0);
                        return true;
                    }
                    if (KeyEvent.VK_RIGHT == e.getKeyCode()) {
                        loadNextImages.doClick(0);
                        return true;
                    }
                }
            }

            return false;
        }
    }

    /**
     * @return the instance of this Class.
     */
    private Component getThis() {
        return this;
    }

    private JScrollPane createThumbNailsBackgroundPanel(){
        thumbNailGridLayout = new GridLayout(0, 6);
        thumbNailsPanel = new ThumbNailsPanel(thumbNailGridLayout);

        JScrollBar hSB = new JScrollBar(JScrollBar.HORIZONTAL);
        JScrollBar vSB = new JScrollBar(JScrollBar.VERTICAL);

        hSB.setUnitIncrement(40);
        vSB.setUnitIncrement(40);

        scrollpane = new JScrollPane(thumbNailsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollpane.setHorizontalScrollBar(hSB);
        scrollpane.setVerticalScrollBar(vSB);

        return scrollpane;
    }

    // Create ToolBar
    public void createToolBar()    {
        Integer[] imagesPerTabInteger = new Integer[]{50, 100, 200, 400, 800, 1000};

        ComboBoxModel<Integer> delaysModel = new DefaultComboBoxModel<Integer>(imagesPerTabInteger);

        numberOfImagesToDisplaySelectionBox = new JComboBox<Integer>(delaysModel);
        numberOfImagesToDisplaySelectionBox.setMaximumSize(numberOfImagesToDisplaySelectionBox.getPreferredSize());
        numberOfImagesToDisplaySelectionBox.setToolTipText(getLang().get("imagesearchresultviewer.button.numberOfImagesToDisplayPerTab.tooltip"));

        loadPreviousImages = new JButton();
        loadPreviousImages.setEnabled(false);

        loadNextImages = new JButton();
        loadNextImages.setEnabled(false);

        loadPreviousImages.setIcon(IconLoader.getIcon(Icons.BACK));
        loadPreviousImages.setToolTipText(getLang().get("imagesearchresultviewer.button.loadPreviousImage.tooltip"));

        loadNextImages.setIcon(IconLoader.getIcon(Icons.FORWARD));
        loadNextImages.setToolTipText(getLang().get("imagesearchresultviewer.button.loadNextImage.tooltip"));

        toolBar = new JToolBar();
        toolBar.setRollover(true);
        toolBar.add(numberOfImagesToDisplaySelectionBox);
        toolBar.add(loadPreviousImages);
        toolBar.add(loadNextImages);

        this.getContentPane().add(toolBar, BorderLayout.NORTH);
    }

    /**
     * This method retrieves the number of images to display in the GUI (from
     * the persisted configuration) and selects the appropriate element in the
     * {@link JComboBox} or the third element if no matching element is found.
     */
    private void initiateNumberOfImagesToDisplay() {
        int numberOfImagesToDisplay = getConfiguration().getImageSearchResultViewerState().getNumberOfImagesToDisplay();

        for (int index = 0; index < numberOfImagesToDisplaySelectionBox.getItemCount(); index++) {
            if (numberOfImagesToDisplaySelectionBox.getModel().getElementAt(index) == numberOfImagesToDisplay) {
                numberOfImagesToDisplaySelectionBox.setSelectedIndex(index);
                return;
            }
        }

        // If no match found, then set the third item as default. (200 images)
        numberOfImagesToDisplaySelectionBox.setSelectedIndex(2);
    }

    public void createRightClickMenu(){
        rightClickMenu = new JPopupMenu();

        popupMenuSelectAll = new JMenuItem(getLang().get("imagesearchresultviewer.menuitem.selectAll"));
        popupMenuDeSelectAll = new JMenuItem(getLang().get("imagesearchresultviewer.menuitem.deSelectAll"));
        popupMenuSetSelectedToViewList = new JMenuItem(getLang().get("imagesearchresultviewer.menuitem.addSelectedImagesToViewList"));
        popupMenuCopyImageToSystemClipBoard = new JMenuItem(getLang().get("imagesearchresultviewer.menuitem.copyImageToSystemClipboard"));
        popupMenuCopySelectedImagesToSystemClipBoard = new JMenuItem(getLang().get("imagesearchresultviewer.menuitem.copySelectedImagesToSystemClipboard"));
        popupMenuCopyAllImagesToSystemClipBoard = new JMenuItem(getLang().get("imagesearchresultviewer.menuitem.copyAllImagesToSystemClipboard"));

        rightClickMenu.add(popupMenuSelectAll);
        rightClickMenu.add(popupMenuDeSelectAll);
        rightClickMenu.addSeparator();
        rightClickMenu.add(popupMenuSetSelectedToViewList);
        rightClickMenu.addSeparator();
        rightClickMenu.add(popupMenuCopyImageToSystemClipBoard);
        rightClickMenu.add(popupMenuCopySelectedImagesToSystemClipBoard);
        rightClickMenu.add(popupMenuCopyAllImagesToSystemClipBoard);
    }

    // Create Status Bar
    public void createStatusPanel() {
        boolean [] timerStatus = {false,false,false,false};
        statuspanel = new StatusPanel(timerStatus);
        this.getContentPane().add(statuspanel, BorderLayout.SOUTH);
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        this.addComponentListener(new ComponentListener());
        popupMenuSetSelectedToViewList.addActionListener(new RightClickMenuListenerSetSelectedToViewList());
        popupMenuSelectAll.addActionListener(new RightClickMenuListenerSelectAll());
        popupMenuDeSelectAll.addActionListener(new RightClickMenuListenerDeSelectAll());
        popupMenuCopyImageToSystemClipBoard.addActionListener(new RightClickMenuListenerCopyImageToSystemClipBoard());
        popupMenuCopySelectedImagesToSystemClipBoard.addActionListener(new RightClickMenuListenerCopySelectedImagesToSystemClipBoard());
        popupMenuCopyAllImagesToSystemClipBoard.addActionListener(new RightClickMenuListenerCopyAllImagesToSystemClipBoard());
        numberOfImagesToDisplaySelectionBox.addItemListener(new NumberOfImagesToDisplayChangeListener());
        loadPreviousImages.addActionListener(new LoadPreviousImagesListener());
        loadNextImages.addActionListener(new LoadNextImagesListener());
    }

    private void removeCustomKeyEventDispatcher() {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.removeKeyEventDispatcher(customKeyEventDispatcher);
    }

    @Override
    protected void saveSettings() {
        super.saveSettings();

        ImageSearchResultViewerState imageSearchResultViewerState = getConfiguration().getImageSearchResultViewerState();

        imageSearchResultViewerState.setNumberOfImagesToDisplay(getCurrentValueOfNumberOfImagesToDisplay());
    }

    private void setStatusMessages () {
        int nrOfColumns = thumbNailGridLayout.getColumns();

        statuspanel.setStatusMessage(Integer.toString(nrOfColumns), getLang().get("statusbar.message.amountOfColumns"), 1);

        int nrOfImagesToDisplay;

        if (nrOfImagesInResultSet > getCurrentValueOfNumberOfImagesToDisplay()) {
            nrOfImagesToDisplay = getCurrentValueOfNumberOfImagesToDisplay();
        } else {
            nrOfImagesToDisplay = nrOfImagesInResultSet;
        }

        int extraRow = nrOfImagesToDisplay % nrOfColumns == 0 ? 0 : 1;
        int rowsInGridLayout = (nrOfImagesToDisplay / nrOfColumns) + extraRow;

        statuspanel.setStatusMessage(Integer.toString(rowsInGridLayout), getLang().get("statusbar.message.amountOfRows"), 2);
        statuspanel.setStatusMessage(Integer.toString(nrOfImagesToDisplay), getLang().get("imagesearchresultviewer.statusMessage.amountOfImagesInSearchResult"), 3);
    }

    @Override
    protected void disposeFrame() {
        this.removeCustomKeyEventDispatcher();
        super.disposeFrame();
    }

    /**
     * @return the currently selected value for the {@link JComboBox} which
     *         defines how many images that shall be selected. If there is no
     *         selection, then the value 200 is returned.
     */
    private int getCurrentValueOfNumberOfImagesToDisplay() {
        if (numberOfImagesToDisplaySelectionBox.getSelectedIndex() == -1) {
            return 200;
        }
        return numberOfImagesToDisplaySelectionBox.getModel().getElementAt(numberOfImagesToDisplaySelectionBox.getSelectedIndex());
    }

    private class MouseButtonListener extends MouseAdapter{
        @Override
        public void mouseReleased(MouseEvent e){
            if(e.isPopupTrigger() && !thumbNailLoadingProgressBar.isVisible()) {
                rightClickMenu.show(e.getComponent(),e.getX(), e.getY());
                popupMenuCopyImageToSystemClipBoard.setActionCommand(((JToggleButton)e.getComponent()).getActionCommand());

                // Only set these as enabled if there are any selected images.
                popupMenuCopySelectedImagesToSystemClipBoard.setEnabled(selectedImageFileIndexObjects.size() > 0);
                popupMenuSetSelectedToViewList.setEnabled(selectedImageFileIndexObjects.size() > 0);
            }
        }
    }

    private class RightClickMenuListenerSetSelectedToViewList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ImagesToViewModel imagesToViewModel = ModelInstanceLibrary.getInstance().getImagesToViewModel();

            for (FileIndex selectedImageFile : selectedImageFileIndexObjects) {
                imagesToViewModel.addElement(selectedImageFile.getFile());
            }
        }
    }

    private class RightClickMenuListenerSelectAll implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            while (!selectedImageIconGenerator.isDone()) {
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e1) {
                }
            }

            for (JToggleButton jToggleButton : thumbNailsPanel.getJToggleButtons()) {
                if (!jToggleButton.isSelected()) {
                    jToggleButton.setSelected(true);
                    File imageFile = new File(jToggleButton.getActionCommand());
                    jToggleButton.setIcon(imageFileToSelectedImageMapping.get(imageFile));
                }
            }
            for (int i = 0; i < imagesInSearchResult.size(); i++) {
                selectedImageFileIndexObjects.add(new FileIndex(imagesInSearchResult.get(i), i));

            }
        }
    }

    private class RightClickMenuListenerDeSelectAll implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (JToggleButton jToggleButton : thumbNailsPanel.getJToggleButtons()) {
                if (jToggleButton.isSelected()) {
                    ButtonIconUtil.setDeSelectedThumbNailImage(jToggleButton);
                    jToggleButton.setSelected(false);
                }
            }
            selectedImageFileIndexObjects.clear();
        }
    }

    private class RightClickMenuListenerCopyImageToSystemClipBoard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<File> selectedFiles = new ArrayList<File>();
            selectedFiles.add(new File(e.getActionCommand()));

            FileSelection fileSelection = new FileSelection(selectedFiles);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
        }
    }

    private class RightClickMenuListenerCopySelectedImagesToSystemClipBoard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<File> selectedFiles = new ArrayList<>();
            for (FileIndex selectedImageFileIndexObject : selectedImageFileIndexObjects) {
                selectedFiles.add(selectedImageFileIndexObject.getFile());
            }

            FileSelection fileSelection = new FileSelection(selectedFiles);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
        }
    }

    private class RightClickMenuListenerCopyAllImagesToSystemClipBoard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<File> selectedFiles = new ArrayList<File>();

            for (JToggleButton jToggleButton : thumbNailsPanel.getJToggleButtons()) {
                selectedFiles.add(new File(jToggleButton.getActionCommand()));
            }
            FileSelection fileSelection = new FileSelection(selectedFiles);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
        }
    }

    private class LoadPreviousImagesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imageSearchResultLoader == null || imageSearchResultLoader.isDone()) {
                List<File> imagesToLoad = getImagesToLoad(Direction.PREVIOUS);
                loadThumbnailImages(imagesToLoad);
            }
        }
    }

    private class LoadNextImagesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (imageSearchResultLoader == null || imageSearchResultLoader.isDone()) {
                List<File> imagesToLoad = getImagesToLoad(Direction.NEXT);
                loadThumbnailImages(imagesToLoad);
            }
        }
    }

    /**
     * This listener class reacts of changes of the {@link JCheckBox} which
     * defines how many images that shall be displayed in the GUI. This allows
     * the GUI to present an image result set in a paged mode
     *
     * @author Fredrik
     *
     */
    private class NumberOfImagesToDisplayChangeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent ie) {
            switch (ie.getStateChange()) {
            case ItemEvent.SELECTED:
                // Reset the index to zero, when a number of displayed images
                // is performed.
                currentStartIndexForDisplayedImages = 0;

                List<File> initialimagesToLoad = getInitialimagesToLoad(imagesInSearchResult);

                loadPreviousImages.setEnabled(false);
                loadNextImages.setEnabled(initialimagesToLoad.size() < imagesInSearchResult.size());

                loadThumbnailImages(initialimagesToLoad);

                break;
            default:
                break;
            }
        }
    }

    /**
     * This method returns which images to load depending on the currently
     * selected {@link Direction} to load.
     *
     * @param direction
     *            specifies in which direction the images shall be loaded.
     *            Either {@link Direction#NEXT} or {@link Direction#PREVIOUS}
     * @return a {@link List} of {@link File} objects which is to load in the
     *         GUI.
     */
    private List<File> getImagesToLoad(Direction direction) {

        int toIndex = -1;
        List<File> imagesToLoad = new ArrayList<File>();

        switch (direction) {
        case NEXT:
            // Set the new start index.
            currentStartIndexForDisplayedImages += getCurrentValueOfNumberOfImagesToDisplay();

            if (currentStartIndexForDisplayedImages + getCurrentValueOfNumberOfImagesToDisplay() > imagesInSearchResult.size() - 1) {
                toIndex = imagesInSearchResult.size();
                loadNextImages.setEnabled(false);
                loadPreviousImages.setEnabled(true);
            } else {
                toIndex = currentStartIndexForDisplayedImages + getCurrentValueOfNumberOfImagesToDisplay();
                loadNextImages.setEnabled(true);
                loadPreviousImages.setEnabled(true);
            }
            imagesToLoad = imagesInSearchResult.subList(currentStartIndexForDisplayedImages, toIndex);
            break;
        case PREVIOUS:
            // First get the to index as the current start index...
            toIndex = currentStartIndexForDisplayedImages;

            // ... and then get the new start index.
            currentStartIndexForDisplayedImages -= getCurrentValueOfNumberOfImagesToDisplay();

            loadPreviousImages.setEnabled(currentStartIndexForDisplayedImages != 0);
            loadNextImages.setEnabled(true);

            imagesToLoad = imagesInSearchResult.subList(currentStartIndexForDisplayedImages, toIndex);
            break;
        default:
            break;
        }

        setWindowTitle(toIndex);

        return imagesToLoad;
    }

    private void setWindowTitle(int toIndex) {
        setTitle(String.format(getLang().get("imagesearchresultviewer.title"), currentStartIndexForDisplayedImages + 1, toIndex, imagesInSearchResult.size()));
    }

    /**
     * This class listens for size changes of the Search result main GUI. When a
     * resize is performed, the number of columns in the GridLayout used to lay
     * out the thumnails, is adapted to have as many columns as possible,
     * without any horizontal scrollbar visible.
     *
     * @author Fredrik
     *
     */
    private class ComponentListener extends ComponentAdapter {
        @Override
        public void componentResized(ComponentEvent e) {
            int scrollbarWidth = 0;

            if (scrollpane.getHorizontalScrollBar().isVisible()) {
                scrollbarWidth = scrollpane.getVerticalScrollBar().getWidth();
            }

            if (thumbNailGridLayout.getColumns() > 0 && iconWidth > 0) {
                if (((scrollpane.getViewport().getSize().width - scrollbarWidth - (columnMargin * thumbNailGridLayout.getColumns())) / iconWidth) != thumbNailGridLayout.getColumns()) {
                    int columns = (scrollpane.getViewport().getSize().width - scrollbarWidth - ((thumbNailGridLayout.getHgap() * thumbNailGridLayout.getColumns()) + columnMargin * thumbNailGridLayout.getColumns())) / iconWidth;

                    thumbNailGridLayout.setColumns(columns > 0 ? columns : 1);
                    thumbNailsPanel.invalidate();
                    thumbNailsPanel.repaint();
                    thumbNailsPanel.updateUI();
                    setStatusMessages();
                }
            }
        }
    }

    private class ThumbNailListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JToggleButton toggleButton = (JToggleButton)e.getSource();

            FileIndex fileIndex = new FileIndex(loadedThumbnails.getFileObject(toggleButton), (int) toggleButton.getClientProperty("ListIndex"));

            if (toggleButton.isSelected()) {
                ThumbNailGrayFilter grayFilter = getConfiguration().getThumbNail().getGrayFilter();
                ButtonIconUtil.setSelectedThumbNailImage(toggleButton, grayFilter.isPixelsBrightened(), grayFilter.getPercentage());
                selectedImageFileIndexObjects.add(fileIndex);
            } else {
                ButtonIconUtil.setDeSelectedThumbNailImage(toggleButton);
                selectedImageFileIndexObjects.remove(fileIndex);
            }
        }
    }

    /**
     * This class extends the {@link SwingWorker} and performs the long running
     * task of loading all thumbnails and showing them.
     *
     * @author Fredrik
     *
     */
    private class ImageSearchResultLoader extends SwingWorker<Void, String> {

        List<File> images;

        public ImageSearchResultLoader(List<File> images) {
            this.images = images;
        }

        @Override
        protected Void doInBackground() throws Exception {

            getThis().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // Clear any previous state.
            thumbNailsPanel.removeAll();
            thumbNailsPanel.updateUI();
            loadedThumbnails.clear();

            ThumbNailListener thumbNailListener = new ThumbNailListener();
            MouseButtonListener mouseRightClickButtonListener = new MouseButtonListener();

            setStatusMessages();

            double progress = 0;
            double nrOfImages = images.size();
            int listIndex = 0;
            int numberOfColumns = 0;

            for (File image : images) {
                if (image.exists()) {
                    JPEGThumbNail tn = JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(image);

                    JToggleButton thumbContainer = new JToggleButton();
                    ImageIcon imageIcon = new ImageIcon(tn.getThumbNailData());
                    imageIcon = ImageUtil.rotateIfNeeded(imageIcon, tn.getMetaData());

                    thumbContainer.setIcon(imageIcon);
                    thumbContainer.putClientProperty("ListIndex", listIndex);
                    listIndex++;

                    if (selectedImageFileIndexObjects.contains(image)) {
                        ThumbNailGrayFilter grayFilter = getConfiguration().getThumbNail().getGrayFilter();
                        final int percentage = grayFilter.getPercentage();
                        final boolean pixelsBrightened = grayFilter.isPixelsBrightened();
                        ButtonIconUtil.setSelectedThumbNailImage(thumbContainer, pixelsBrightened, percentage);
                        thumbContainer.setSelected(true);
                    }

                    thumbContainer.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
                    if (!getConfiguration().getToolTips().getImageSearchResultState().equals("0")) {
                        thumbContainer.setToolTipText(MetaDataUtil.getToolTipText(image, getConfiguration().getToolTips().getImageSearchResultState()));
                    }
                    String absolutePath = image.getAbsolutePath();
                    thumbContainer.setActionCommand(absolutePath);
                    thumbContainer.addActionListener(thumbNailListener);
                    thumbContainer.addMouseListener(mouseRightClickButtonListener);

                    columnMargin = thumbContainer.getBorder().getBorderInsets(thumbContainer).left;
                    columnMargin += thumbContainer.getBorder().getBorderInsets(thumbContainer).right;

                    int width = thumbContainer.getIcon().getIconWidth();

                    if (width > iconWidth) {
                        iconWidth = width;
                    }

                    thumbNailsPanel.add(thumbContainer);
                    thumbNailsPanel.updateUI();
                    loadedThumbnails.add(thumbContainer);
                }

                setProgress((int)((progress++ / nrOfImages) * 100));

                int currentNumberOfColumns = thumbNailGridLayout.getColumns();
                if (currentNumberOfColumns > numberOfColumns) {
                    triggerOptimizeColumnLayout();
                    numberOfColumns = currentNumberOfColumns;
                }
            }
            return null;
        }

        @Override
        protected void done() {
            // hide the progress bar...
            thumbNailLoadingProgressBar.setVisible(false);

            getThis().setCursor(Cursor.getDefaultCursor());

            // ... and start the background task of "creating selected" images
            //for the JToggleButtons.
            selectedImageIconGenerator = new SelectedImageIconGenerator(loadedThumbnails, imageFileToSelectedImageMapping);
            selectedImageIconGenerator.execute();

            // re calculate / optimize thumbnail grid size
            triggerOptimizeColumnLayout();
        }
    }

    private void triggerOptimizeColumnLayout() {
        Arrays.asList(getThis().getComponentListeners()).forEach(componentListener -> {
            componentListener.componentResized(new ComponentEvent(getThis(), ComponentEvent.COMPONENT_RESIZED));
        });
    }

    @Override
    public GUIWindow getGUIWindowConfig() {
        return getConfiguration().getgUI().getImageSearchResultViewer();
    }

    @Override
    public Dimension getDefaultSize() {
        return new Dimension(GUIDefaults.IMAGE_SEARCH_RESULT_VIEWER_WIDTH, GUIDefaults.IMAGE_SEARCH_RESULT_VIEWER_HEIGHT);
    }

    private class FileIndex {
        private int index;
        private File file;

        public FileIndex(File file, int index) {
            this.file = file;
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }
    }

    private class FileIndexComparator implements Comparator<FileIndex> {

        @Override
        public int compare(FileIndex o1, FileIndex o2) {
            return Integer.compare(o1.getIndex(), o2.getIndex());
        }
    }
}
