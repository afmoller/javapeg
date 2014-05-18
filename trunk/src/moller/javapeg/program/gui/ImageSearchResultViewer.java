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
package moller.javapeg.program.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.FileSelection;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.thumbnail.ThumbNailGrayFilter;
import moller.javapeg.program.gui.main.LoadedThumbnails;
import moller.javapeg.program.jpeg.JPEGThumbNail;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaDataUtil;
import moller.javapeg.program.model.ImagesToViewModel;
import moller.javapeg.program.model.ModelInstanceLibrary;
import moller.util.gui.Screen;

public class ImageSearchResultViewer extends JFrame {

    private static final long serialVersionUID = 1L;

    private static Configuration configuration;
    private static Logger logger;
    private static Language lang;

    private JToolBar toolBar;
    private JPopupMenu rightClickMenu;
    private StatusPanel statuspanel;

    private JMenuItem popupMenuSetSelectedToViewList;
    private JMenuItem popupMenuSelectAll;
    private JMenuItem popupMenuDeSelectAll;
    private JMenuItem popupMenuCopyImageToSystemClipBoard;
    private JMenuItem popupMenuCopyAllImagesToSystemClipBoard;

    private final String ICONFILEPATH = "resources/images/imageviewer/";

    private int columnMargin;
    private int iconWidth;
    private final int nrOfImagesToView;

    private GridLayout thumbNailGridLayout;

    private JPanel thumbNailsPanel;
    private JScrollPane scrollpane;
    private JProgressBar thumbNailLoadingProgressBar;

    private final Map<File, ImageIcon> imageFileToSelectedImaigeMapping;
    private final LoadedThumbnails loadedThumbnails;

    SelectedImageIconGenerator selectedImageIconGenerator;

    public ImageSearchResultViewer(List<File> imagesToView) {

        configuration = Config.getInstance().get();
        logger = Logger.getInstance();
        lang   = Language.getInstance();

        // Must be set before the executeLoadThumbnailsProcess method is called
        nrOfImagesToView = imagesToView.size();

        this.createMainFrame();
        this.createToolBar();
        this.createRightClickMenu();
        this.createStatusPanel();
        this.addListeners();

        loadedThumbnails = new LoadedThumbnails();

        imageFileToSelectedImaigeMapping = Collections.synchronizedMap(new HashMap<File, ImageIcon>());

        ImageSearhResultLoader imageSearhResultLoader = new ImageSearhResultLoader(imagesToView);
        imageSearhResultLoader.addPropertyChangeListener(new ImageSearhResultLoaderPropertyListener());
        imageSearhResultLoader.execute();
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

        thumbNailLoadingProgressBar = new JProgressBar();
        thumbNailLoadingProgressBar.setStringPainted(true);
        thumbNailLoadingProgressBar.setVisible(true);

        JPanel backgroundPanel = new JPanel(new BorderLayout());
        backgroundPanel.add(this.createThumbNailsBackgroundPanel(), BorderLayout.CENTER);
        backgroundPanel.add(thumbNailLoadingProgressBar, BorderLayout.SOUTH);

        this.getContentPane().add(backgroundPanel, BorderLayout.CENTER);
    }

    /**
     * @return the instance of this Class.
     */
    private Component getThis() {
        return this;
    }

    private JScrollPane createThumbNailsBackgroundPanel(){

        thumbNailGridLayout = new GridLayout(0, 6);
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

    // Create ToolBar
    public void createToolBar()    {

        toolBar = new JToolBar();
        toolBar.setRollover(true);

        InputStream imageStream = null;

        ImageIcon previousImageIcon = new ImageIcon();
        ImageIcon nextImageIcon = new ImageIcon();
        ImageIcon automaticAdjustToWindowSizeImageIcon = new ImageIcon();
        ImageIcon adjustToWindowSizeImageIcon = new ImageIcon();

        try {
            imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Back16.gif");
            previousImageIcon.setImage(ImageIO.read(imageStream));

            imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Forward16.gif");
            nextImageIcon.setImage(ImageIO.read(imageStream));

            imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "AutoAdjustToWindowSize16.gif");
            automaticAdjustToWindowSizeImageIcon.setImage(ImageIO.read(imageStream));

            imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Zoom16.gif");
            adjustToWindowSizeImageIcon.setImage(ImageIO.read(imageStream));
        } catch (IOException e) {
            logger.logERROR("Could not load image. See Stack Trace below for details");
            logger.logERROR(e);
        }

        toolBar.addSeparator();

        this.getContentPane().add(toolBar, BorderLayout.NORTH);
    }

    public void createRightClickMenu(){

        rightClickMenu = new JPopupMenu();

        popupMenuSelectAll = new JMenuItem(lang.get("imagesearchresultviewer.menuitem.selectAll"));
        popupMenuDeSelectAll = new JMenuItem(lang.get("imagesearchresultviewer.menuitem.deSelectAll"));
        popupMenuSetSelectedToViewList = new JMenuItem(lang.get("imagesearchresultviewer.menuitem.addSelectedImagesToViewList"));
        popupMenuCopyImageToSystemClipBoard = new JMenuItem(lang.get("imagesearchresultviewer.menuitem.copySelectedImagesToSystemClipboard"));
        popupMenuCopyAllImagesToSystemClipBoard = new JMenuItem(lang.get("imagesearchresultviewer.menuitem.copyAllImagesToSystemClipboard"));

        rightClickMenu.add(popupMenuSelectAll);
        rightClickMenu.add(popupMenuDeSelectAll);
        rightClickMenu.addSeparator();
        rightClickMenu.add(popupMenuSetSelectedToViewList);
        rightClickMenu.addSeparator();
        rightClickMenu.add(popupMenuCopyImageToSystemClipBoard);
        rightClickMenu.add(popupMenuCopyAllImagesToSystemClipBoard);
    }

    // Create Status Bar
    public void createStatusPanel() {
        boolean [] timerStatus = {false,false,false,false};
        statuspanel = new StatusPanel(timerStatus);
        this.getContentPane().add(statuspanel, BorderLayout.SOUTH);
    }

    private void addListeners() {
        this.addWindowListener(new WindowDestroyer());
        this.addComponentListener(new ComponentListener());
        popupMenuSetSelectedToViewList.addActionListener(new RightClickMenuListenerSetSelectedToViewList());
        popupMenuSelectAll.addActionListener(new RightClickMenuListenerSelectAll());
        popupMenuDeSelectAll.addActionListener(new RightClickMenuListenerDeSelectAll());
        popupMenuCopyImageToSystemClipBoard.addActionListener(new RightClickMenuListenerCopyImageToSystemClipBoard());
        popupMenuCopyAllImagesToSystemClipBoard.addActionListener(new RightClickMenuListenerCopyAllImagesToSystemClipBoard());
    }

    private void saveSettings() {
        GUI gUI = configuration.getgUI();

        Rectangle sizeAndLocation = gUI.getImageSearchResultViewer().getSizeAndLocation();

        sizeAndLocation.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);
        sizeAndLocation.setSize(this.getSize().width, this.getSize().height);
    }

    private void setStatusMessages () {
        int nrOfColumns = thumbNailGridLayout.getColumns();

        statuspanel.setStatusMessage(Integer.toString(nrOfColumns), lang.get("statusbar.message.amountOfColumns"), 1);

        int extraRow = nrOfImagesToView % nrOfColumns == 0 ? 0 : 1;
        int rowsInGridLayout = (nrOfImagesToView / nrOfColumns) + extraRow;

        statuspanel.setStatusMessage(Integer.toString(rowsInGridLayout), lang.get("statusbar.message.amountOfRows"), 2);
        statuspanel.setStatusMessage(Integer.toString(nrOfImagesToView), lang.get("imagesearchresultviewer.statusMessage.amountOfImagesInSearchResult"), 3);
    }

    private void disposeFrame() {
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

    private class MouseButtonListener extends MouseAdapter{
        @Override
        public void mouseReleased(MouseEvent e){
            if(e.isPopupTrigger() && !thumbNailLoadingProgressBar.isVisible()) {
                rightClickMenu.show(e.getComponent(),e.getX(), e.getY());
                popupMenuCopyImageToSystemClipBoard.setActionCommand(((JToggleButton)e.getComponent()).getActionCommand());
            }
        }
    }

    private class RightClickMenuListenerSetSelectedToViewList implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ImagesToViewModel imagesToViewModel = ModelInstanceLibrary.getInstance().getImagesToViewModel();

            for (JToggleButton jToggleButton : getJToggleButtons()) {
                if (jToggleButton.isSelected()) {
                    imagesToViewModel.addElement(new File(jToggleButton.getActionCommand()));
                }
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

            for (JToggleButton jToggleButton : getJToggleButtons()) {
                if (!jToggleButton.isSelected()) {
                    jToggleButton.setSelected(true);
                    jToggleButton.setIcon(imageFileToSelectedImaigeMapping.get(new File(jToggleButton.getActionCommand())));
                }
            }
        }
    }

    private class RightClickMenuListenerDeSelectAll implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (JToggleButton jToggleButton : getJToggleButtons()) {
                if (jToggleButton.isSelected()) {
                    ButtonIconUtil.setDeSelectedThumbNailImage(jToggleButton);
                    jToggleButton.setSelected(false);
                }
            }
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

    private class RightClickMenuListenerCopyAllImagesToSystemClipBoard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<File> selectedFiles = new ArrayList<File>();

            for (JToggleButton jToggleButton : getJToggleButtons()) {
                selectedFiles.add(new File(jToggleButton.getActionCommand()));
            }
            FileSelection fileSelection = new FileSelection(selectedFiles);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
        }
    }

    private List<JToggleButton> getJToggleButtons() {
        List<JToggleButton> jToggleButtons = new ArrayList<JToggleButton>();

        Component[] components = thumbNailsPanel.getComponents();

        for (Component component : components) {
            if (component instanceof JToggleButton) {
                jToggleButtons.add((JToggleButton)component);
            }
        }
        return jToggleButtons;
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

            if (toggleButton.isSelected()) {
                ThumbNailGrayFilter grayFilter = configuration.getThumbNail().getGrayFilter();
                ButtonIconUtil.setSelectedThumbNailImage(toggleButton, grayFilter.isPixelsBrightened(), grayFilter.getPercentage());
            } else {
                ButtonIconUtil.setDeSelectedThumbNailImage(toggleButton);
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
    private class ImageSearhResultLoader extends SwingWorker<Void, String> {

        List<File> images;

        public ImageSearhResultLoader(List<File> images) {
            this.images = images;
        }

        @Override
        protected Void doInBackground() throws Exception {

            getThis().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            ThumbNailListener thumbNailListener = new ThumbNailListener();
            MouseButtonListener mouseRightClickButtonListener = new MouseButtonListener();

            setStatusMessages();

            double progress = 0;
            double nrOfImages = images.size();

            for (File image : images) {
                if (image.exists()) {
                    JPEGThumbNail tn = JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(image);

                    JToggleButton thumbContainer = new JToggleButton();
                    thumbContainer.setIcon(new ImageIcon(tn.getThumbNailData()));
                    thumbContainer.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
                    if (!configuration.getToolTips().getImageSearchResultState().equals("0")) {
                        thumbContainer.setToolTipText(MetaDataUtil.getToolTipText(image, configuration.getToolTips().getImageSearchResultState()));
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
            }
            return null;
        }

        @Override
        protected void done() {
            // hide the progress bar...
            thumbNailLoadingProgressBar.setVisible(false);

            getThis().setCursor(Cursor.getDefaultCursor());

            // ... and start the background task or "creating selected" images
            //for the JToggleButtons.
            selectedImageIconGenerator = new SelectedImageIconGenerator();
            selectedImageIconGenerator.execute();
        }
    }

    private class SelectedImageIconGenerator extends SwingWorker<Void, String> {

        @Override
        protected Void doInBackground() throws Exception {
            logger.logDEBUG("Starting to create selected images for the " + loadedThumbnails.size() + " loaded thumbnails in the search result window");

            ThumbNailGrayFilter grayFilter = configuration.getThumbNail().getGrayFilter();
            final int percentage = grayFilter.getPercentage();
            final boolean pixelsBrightened = grayFilter.isPixelsBrightened();

            ExecutorService newCachedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

            for (final JToggleButton loadedThumbnail : loadedThumbnails) {
                final String actionCommand = loadedThumbnail.getActionCommand();
                final File actionCommandAsFile = new File(actionCommand);

                newCachedThreadPool.submit(new Runnable() {

                    @Override
                    public void run() {
                        if (!imageFileToSelectedImaigeMapping.containsKey(actionCommandAsFile)) {
                            Image selectedIcon = ButtonIconUtil.getSelectedIcon(loadedThumbnail, pixelsBrightened, percentage);
                            imageFileToSelectedImaigeMapping.put(actionCommandAsFile, new ImageIcon(selectedIcon));
                        }
                    }
                });
            }

            logger.logDEBUG("Waiting for treads to finnish");
            newCachedThreadPool.shutdown();
            newCachedThreadPool.awaitTermination(1, TimeUnit.MINUTES);
            logger.logDEBUG("Finished waiting for treads to finnish");

            logger.logDEBUG("Finished to create selected images for thumbnails in search result window");
            return null;
        }
    }
}
