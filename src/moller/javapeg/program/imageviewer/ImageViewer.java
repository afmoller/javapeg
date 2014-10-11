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
package moller.javapeg.program.imageviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.FileSelection;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.config.controller.ConfigElement;
import moller.javapeg.program.config.model.Configuration;
import moller.javapeg.program.config.model.ImageViewerState;
import moller.javapeg.program.config.model.GUI.GUI;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPane;
import moller.javapeg.program.config.model.GUI.GUIWindowSplitPaneUtil;
import moller.javapeg.program.config.model.thumbnail.ThumbNailGrayFilter;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.datatype.ResizeQualityAndDisplayString;
import moller.javapeg.program.enumerations.Direction;
import moller.javapeg.program.gui.ButtonIconUtil;
import moller.javapeg.program.gui.GUIDefaults;
import moller.javapeg.program.gui.MetaDataPanel;
import moller.javapeg.program.gui.StatusPanel;
import moller.javapeg.program.gui.components.NavigableImagePanel;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaDataUtil;
import moller.util.gui.Screen;
import moller.util.gui.Update;
import moller.util.io.StreamUtil;
import moller.util.mnemonic.MnemonicConverter;
import moller.util.string.StringUtil;

import org.imgscalr.Scalr.Method;

public class ImageViewer extends JFrame {

    private static final long serialVersionUID = 1L;

    private static Configuration configuration;
    private static Logger logger;
    private static Language lang;

    private JToolBar toolBar;
    private JPopupMenu rightClickMenu;
    private StatusPanel statuspanel;

    private NavigableImagePanel imageBackground;
    private JPanel overViewBackgroundPanel;
    private JPanel imageOverViewPanel;

    private JMenuItem popupMenuPrevious;
    private JMenuItem popupMenuNext;
    private JMenuItem popupMenuAdjustToWindowSize;
    private JMenuItem popupMenuCopyImageToSystemClipboard;
    private JMenuItem popupMenuFullScreenView;
    private JMenuItem popupMenuExitFullScreenView;

    private JButton previousJButton;
    private JButton nextJButton;
    private JButton adjustToWindowSizeJButton;
    private JButton rotateLeftButton;
    private JButton rotateRightButton;
    private JButton centerButton;
    private JButton zoomInButton;
    private JButton zoomOutButton;
    private JButton startSlideShowButton;
    private JButton stopSlideShowButton;

    private JButton maximizeButton;
    private JButton minimizeButton;

    private JComboBox<ResizeQualityAndDisplayString> resizeQuality;
    private JComboBox<Integer> slideShowDelay;

    private JSplitPane imageMetaDataSplitPane;

    private JScrollPane overViewScrollpane;

    private MetaDataPanel metaDataPanel;

    private JToggleButton automaticAdjustToWindowSizeJToggleButton;
    private JToggleButton automaticRotateToggleButton;
    private JToggleButton toggleNavigationImageButton;

    private File thePicture;

    private final List<File> imagesToView;
    private final Map<File, JButton> imageToJButtonMapping;

    private int imageToViewListIndex;
    private int imagesToViewListSize;

    private final LoadImageAction loadNextImageAction = new LoadImageAction(Direction.NEXT);
    private final LoadImageAction loadPreviousImageAction = new LoadImageAction(Direction.PREVIOUS);

    private CustomKeyEventDispatcher customKeyEventDispatcher;

    private OverviewButtonListener overviewButtonListener;

    private Dimension widthAndHeight;

    private Point location;

    private JPanel imageOverviewPanel;

    private int imageMetaDataSplitPaneDividerLocation;

    private boolean fullScreenEnabled;

    private final Timer slideShowTimer;

    public ImageViewer(List<File> imagesToView) {

        configuration = Config.getInstance().get();
        logger = Logger.getInstance();
        lang   = Language.getInstance();

        this.imagesToView = imagesToView;

        imageToViewListIndex = 0;
        imagesToViewListSize = imagesToView.size();

        imageToJButtonMapping = new HashMap<File, JButton>();
        slideShowTimer = new Timer(3000, new ToolBarButtonNext());
        slideShowTimer.setInitialDelay(0);

        this.createMainFrame();
        this.createToolBar();
        this.createRightClickMenu();
        this.createStatusPanel();
        this.addListeners();
        this.initiateButtonStates();
        this.initiateResizeQuality();
        this.initiateSlideShowDelay();
        this.createImageDisplayImageAndScrollThumbnailToVisibleRect(imageToViewListIndex);
    }

    /**
     * This method finds the correct resize Method (from the persisted
     * configuration) and selects the appropriate element in the
     * {@link JComboBox} or the first element if no matching element is found.
     */
    private void initiateResizeQuality() {
        Method resizeQualityMethod = configuration.getImageViewerState().getResizeQuality();

        for (int index = 0; index < resizeQuality.getItemCount(); index++) {
            if (resizeQuality.getModel().getElementAt(index).getMethod() == resizeQualityMethod) {
                resizeQuality.setSelectedIndex(index);
                return;
            }
        }

        // If no match found, then set the first item as default.
        resizeQuality.setSelectedIndex(0);
    }

    /**
     * This method retrieves the slide show delay (from the persisted
     * configuration) and selects the appropriate element in the
     * {@link JComboBox} or the first element if no matching element is found.
     */
    private void initiateSlideShowDelay() {
        int slideShowDelayInSeconds = configuration.getImageViewerState().getSlideShowDelayInSeconds();

        for (int index = 0; index < slideShowDelay.getItemCount(); index++) {
            if (slideShowDelay.getModel().getElementAt(index) == slideShowDelayInSeconds) {
                slideShowDelay.setSelectedIndex(index);
                return;
            }
        }

        // If no match found, then set the third item as default. (3 seconds)
        slideShowDelay.setSelectedIndex(2);
    }

    /**
     * This method sets the persisted states of the toggle buttons found in the
     * ImageViewer.
     */
    private void initiateButtonStates() {
        automaticAdjustToWindowSizeJToggleButton.setSelected(configuration.getImageViewerState().isAutomaticallyResizeImages());
        automaticRotateToggleButton.setSelected(configuration.getImageViewerState().isAutomaticallyRotateImages());
        toggleNavigationImageButton.setSelected(configuration.getImageViewerState().isShowNavigationImage());
    }

    // Create Main Window
    public void createMainFrame() {

        GUI gUI = configuration.getgUI();

        Rectangle sizeAndLocation = gUI.getImageViewer().getSizeAndLocation();

        this.setSize(sizeAndLocation.getSize());

        Point xyFromConfig = new Point(sizeAndLocation.getLocation());

        if (Screen.isVisibleOnScreen(sizeAndLocation)) {
            this.setLocation(xyFromConfig);
            this.setSize(sizeAndLocation.getSize());
        } else {
            JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
            logger.logERROR("Could not set location of Image Viewer GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");

            this.setLocation(0,0);
            this.setSize(GUIDefaults.IMAGE_VIEWER_WIDTH, GUIDefaults.IMAGE_VIEWER_HEIGHT);
        }

        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e){
            logger.logERROR("Could not set desired Look And Feel for Main GUI");
            logger.logERROR(e);
        }

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        customKeyEventDispatcher = new CustomKeyEventDispatcher();
        manager.addKeyEventDispatcher(customKeyEventDispatcher);

        InputStream imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "Open16.gif");

        try {
            this.setIconImage(ImageIO.read(imageStream));
        } catch (IOException e) {
            logger.logERROR("Could not load icon: Open16.gif");
            logger.logERROR(e);
        }

        imageBackground = new NavigableImagePanel();
        imageBackground.setHighQualityRenderingEnabled(true);
        imageBackground.setNavigationImageEnabled(configuration.getImageViewerState().isShowNavigationImage());

        thePicture = new File(imagesToView.get(0).getAbsolutePath());

        this.setTitle(thePicture.getParent());

        metaDataPanel = new MetaDataPanel();
        metaDataPanel.setMetaData(thePicture);

        imageMetaDataSplitPane = new JSplitPane();
        imageMetaDataSplitPane.setOneTouchExpandable(true);

        List<GUIWindowSplitPane> gUIWindowSplitPanes = gUI.getImageViewer().getGuiWindowSplitPane();

        imageMetaDataSplitPane.setDividerSize(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerSize(gUIWindowSplitPanes, ConfigElement.IMAGE_META_DATA));
        imageMetaDataSplitPane.setDividerLocation(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerLocation(gUIWindowSplitPanes, ConfigElement.IMAGE_META_DATA));
        imageMetaDataSplitPane.setLeftComponent(imageBackground);
        imageMetaDataSplitPane.setRightComponent(metaDataPanel);

        this.getContentPane().add(imageMetaDataSplitPane, BorderLayout.CENTER);

        overViewBackgroundPanel = this.createOverviewPanel();

        this.getContentPane().add(overViewBackgroundPanel, BorderLayout.WEST);
    }

    private JPanel createOverviewPanel() {

        imageOverviewPanel = new JPanel(new GridBagLayout());

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JPanel buttonBackgroundPanel = new JPanel(new GridBagLayout());

        GBHelper posButtonPanel = new GBHelper();
        GBHelper posButtonBackgroundPanel = new GBHelper();

        Dimension buttonSize = new Dimension(16, 16);

        maximizeButton = new JButton();
        minimizeButton = new JButton();

        InputStream imageStream = null;

        ImageIcon maximizeImageIcon = new ImageIcon();
        ImageIcon minimizeIcon = new ImageIcon();

        try {
            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "Forward16.gif");
            maximizeImageIcon.setImage(ImageIO.read(imageStream));
            maximizeButton.setIcon(maximizeImageIcon);
            maximizeButton.setPreferredSize(buttonSize);
            maximizeButton.setMinimumSize(buttonSize);

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "Back16.gif");
            minimizeIcon.setImage(ImageIO.read(imageStream));
            minimizeButton.setIcon(minimizeIcon);
            minimizeButton.setPreferredSize(buttonSize);
            minimizeButton.setMinimumSize(buttonSize);

        } catch (IOException e) {
            logger.logERROR("Could not load image. See Stack Trace below for details");
            logger.logERROR(e);
        }

        buttonPanel.add(maximizeButton, posButtonPanel);
        buttonPanel.add(minimizeButton, posButtonPanel.nextRow());

        buttonBackgroundPanel.add(buttonPanel, posButtonBackgroundPanel.align(GridBagConstraints.NORTH));

        overViewScrollpane = new JScrollPane();

        JScrollBar vScrollBar = new JScrollBar();
        vScrollBar.setUnitIncrement(40);

        overViewScrollpane.setVerticalScrollBar(vScrollBar);
        overViewScrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        overViewScrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        imageOverViewPanel = new JPanel(new GridLayout(0, 1));
        imageOverViewPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));

        overviewButtonListener = new OverviewButtonListener();

        Thread addImagesToOverviewPanel = new Thread() {

            @Override
            public void run(){
                for(int i = 0; i < imagesToView.size(); i++) {
                    addImageToOverViewPanel(imagesToView.get(i), overviewButtonListener, i, false);
                }
            }
        };
        addImagesToOverviewPanel.start();

        overViewScrollpane.getViewport().add(imageOverViewPanel);

        GBHelper posBackgroundPanel = new GBHelper();

        imageOverviewPanel.add(overViewScrollpane, posBackgroundPanel.expandW());
        imageOverviewPanel.add(buttonBackgroundPanel, posBackgroundPanel.nextCol().align(GridBagConstraints.NORTH).expandH());

        return imageOverviewPanel;
    }

    private void addImageToOverViewPanel(File jpegImage, OverviewButtonListener overviewButtonListener, int index, boolean updateGUI) {
        JButton imageButton = new JButton(new ImageIcon(JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(jpegImage).getThumbNailData()));

        imageButton.setActionCommand(Integer.toString(index));
        if (!configuration.getToolTips().getOverviewImageViewerState().equals("0")) {
            imageButton.setToolTipText(MetaDataUtil.getToolTipText(jpegImage, configuration.getToolTips().getOverviewImageViewerState()));
        }
        imageButton.addActionListener(overviewButtonListener);

        imageOverViewPanel.add(imageButton);

        // Populate the helper variable, which makes it possible to
        // automatically let the GUI scroll the list of thumbnails to the
        // thumbnail for the currently selected image to display.
        imageToJButtonMapping.put(jpegImage, imageButton);

        if (updateGUI) {
            Update.updateWindowUI(this);
        }
    }

    // Create ToolBar
    public void createToolBar()    {
        toolBar = new JToolBar();
        toolBar.setRollover(true);

        previousJButton = new JButton();
        nextJButton = new JButton();
        automaticAdjustToWindowSizeJToggleButton = new JToggleButton();
        automaticRotateToggleButton = new JToggleButton();
        adjustToWindowSizeJButton = new JButton();
        rotateLeftButton = new JButton();
        rotateRightButton = new JButton();
        centerButton = new JButton();
        toggleNavigationImageButton = new JToggleButton();
        zoomInButton = new JButton();
        zoomOutButton = new JButton();
        startSlideShowButton = new JButton();
        stopSlideShowButton = new JButton();

        ResizeQualityAndDisplayString one = new ResizeQualityAndDisplayString(lang.get("imageviewer.combobox.resize.quality.automatic"), Method.AUTOMATIC);
        ResizeQualityAndDisplayString two = new ResizeQualityAndDisplayString(lang.get("imageviewer.combobox.resize.quality.speed"), Method.SPEED);
        ResizeQualityAndDisplayString three = new ResizeQualityAndDisplayString(lang.get("imageviewer.combobox.resize.quality.balanced"), Method.BALANCED);
        ResizeQualityAndDisplayString four = new ResizeQualityAndDisplayString(lang.get("imageviewer.combobox.resize.quality.quality"), Method.QUALITY);
        ResizeQualityAndDisplayString five = new ResizeQualityAndDisplayString(lang.get("imageviewer.combobox.resize.quality.ultraquality"), Method.ULTRA_QUALITY);

        ResizeQualityAndDisplayString[] qualityAndDisplayStrings = new ResizeQualityAndDisplayString[5];
        qualityAndDisplayStrings[0] = one;
        qualityAndDisplayStrings[1] = two;
        qualityAndDisplayStrings[2] = three;
        qualityAndDisplayStrings[3] = four;
        qualityAndDisplayStrings[4] = five;

        ComboBoxModel<ResizeQualityAndDisplayString> model = new DefaultComboBoxModel<ResizeQualityAndDisplayString>(qualityAndDisplayStrings);

        resizeQuality = new JComboBox<ResizeQualityAndDisplayString>(model);
        resizeQuality.setMaximumSize(resizeQuality.getPreferredSize());

        Integer[] delayInteger = new Integer[]{1, 2, 3, 4, 5, 7, 10, 15, 20, 25, 30, 45, 60, 120};

        ComboBoxModel<Integer> delaysModel = new DefaultComboBoxModel<Integer>(delayInteger);

        slideShowDelay = new JComboBox<Integer>(delaysModel);
        slideShowDelay.setMaximumSize(slideShowDelay.getPreferredSize());
        slideShowDelay.setToolTipText(lang.get("imageviewer.button.slideShowDelay.toolTip"));

        InputStream imageStream = null;

        ImageIcon previousImageIcon = new ImageIcon();
        ImageIcon nextImageIcon = new ImageIcon();
        ImageIcon automaticAdjustToWindowSizeImageIcon = new ImageIcon();
        ImageIcon adjustToWindowSizeImageIcon = new ImageIcon();
        ImageIcon rotateLeftImageIcon = new ImageIcon();
        ImageIcon rotateRightImageIcon = new ImageIcon();
        ImageIcon automaticRotateImageIcon = new ImageIcon();
        ImageIcon centerImageIcon = new ImageIcon();
        ImageIcon zoomInImageIcon = new ImageIcon();
        ImageIcon zoomOutImageIcon = new ImageIcon();
        ImageIcon navigationImageEnabledIcon = new ImageIcon();
        ImageIcon navigationImageDisabledIcon = new ImageIcon();
        ImageIcon startSlideshowImageIcon = new ImageIcon();
        ImageIcon stopSlideshowImageIcon = new ImageIcon();

        try {
            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "Back16.gif");
            previousImageIcon.setImage(ImageIO.read(imageStream));
            previousJButton.setIcon(previousImageIcon);
            previousJButton.setToolTipText(lang.get("imageviewer.button.back.toolTip"));

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER+ "Forward16.gif");
            nextImageIcon.setImage(ImageIO.read(imageStream));
            nextJButton.setIcon(nextImageIcon);
            nextJButton.setToolTipText(lang.get("imageviewer.button.forward.toolTip"));

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "AutoAdjustToWindowSize16.gif");
            automaticAdjustToWindowSizeImageIcon.setImage(ImageIO.read(imageStream));
            automaticAdjustToWindowSizeJToggleButton.setIcon(automaticAdjustToWindowSizeImageIcon);
            automaticAdjustToWindowSizeJToggleButton.setToolTipText(lang.get("imageviewer.button.automaticAdjustToWindowSize.toolTip"));
            automaticAdjustToWindowSizeJToggleButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.automaticAdjustToWindowSize.mnemonic").charAt(0)));

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "Zoom16.gif");
            adjustToWindowSizeImageIcon.setImage(ImageIO.read(imageStream));
            adjustToWindowSizeJButton.setIcon(adjustToWindowSizeImageIcon);
            adjustToWindowSizeJButton.setToolTipText(lang.get("imageviewer.button.adjustToWindowSize.toolTip"));
            adjustToWindowSizeJButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.adjustToWindowSize.mnemonic").charAt(0)));

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "RotateLeft16.gif");
            rotateLeftImageIcon.setImage(ImageIO.read(imageStream));
            rotateLeftButton.setIcon(rotateLeftImageIcon);
            rotateLeftButton.setToolTipText(lang.get("imageviewer.button.rotateLeft.toolTip"));
            rotateLeftButton.setMnemonic(KeyEvent.VK_LEFT);

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "RotateRight16.gif");
            rotateRightImageIcon.setImage(ImageIO.read(imageStream));
            rotateRightButton.setIcon(rotateRightImageIcon);
            rotateRightButton.setToolTipText(lang.get("imageviewer.button.rotateRight.toolTip"));
            rotateRightButton.setMnemonic(KeyEvent.VK_RIGHT);

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "RotateAutomatic16.gif");
            automaticRotateImageIcon.setImage(ImageIO.read(imageStream));
            automaticRotateToggleButton.setIcon(automaticRotateImageIcon);
            automaticRotateToggleButton.setToolTipText(lang.get("imageviewer.button.rotateAutomatic"));
            automaticRotateToggleButton.setMnemonic(KeyEvent.VK_UP);

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "Center16.png");
            centerImageIcon.setImage(ImageIO.read(imageStream));
            centerButton.setIcon(centerImageIcon);
            centerButton.setToolTipText(lang.get("imageviewer.button.center.toolTip"));

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "ZoomIn16.gif");
            zoomInImageIcon.setImage(ImageIO.read(imageStream));
            zoomInButton.setIcon(zoomInImageIcon);
            zoomInButton.setToolTipText(lang.get("imageviewer.button.zoomIn.toolTip"));

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "ZoomOut16.gif");
            zoomOutImageIcon.setImage(ImageIO.read(imageStream));
            zoomOutButton.setIcon(zoomOutImageIcon);
            zoomOutButton.setToolTipText(lang.get("imageviewer.button.zoomOut.toolTip"));

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "NavigationImageEnabled16.png");
            navigationImageEnabledIcon.setImage(ImageIO.read(imageStream));

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "NavigationImageDisabled16.png");
            navigationImageDisabledIcon.setImage(ImageIO.read(imageStream));

            toggleNavigationImageButton.setIcon(navigationImageEnabledIcon);
            toggleNavigationImageButton.setSelectedIcon(navigationImageDisabledIcon);
            toggleNavigationImageButton.setToolTipText(lang.get("imageviewer.button.toggleNavigationImage.toolTip"));

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH + "play.gif");
            startSlideshowImageIcon.setImage(ImageIO.read(imageStream));
            startSlideShowButton.setIcon(startSlideshowImageIcon);
            startSlideShowButton.setToolTipText(lang.get("imageviewer.button.startSlideShow.toolTip"));

            imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "Stop16.gif");
            stopSlideshowImageIcon.setImage(ImageIO.read(imageStream));
            stopSlideShowButton.setIcon(stopSlideshowImageIcon);
            stopSlideShowButton.setToolTipText(lang.get("imageviewer.button.stopSlideShow.toolTip"));
        } catch (IOException e) {
            logger.logERROR("Could not load image. See Stack Trace below for details");
            logger.logERROR(e);
        } finally {
            if (imageStream != null) {
                StreamUtil.close(imageStream, true);
            }
        }

        toolBar.add(previousJButton);
        toolBar.add(nextJButton);
        toolBar.addSeparator();
        toolBar.add(rotateLeftButton);
        toolBar.add(rotateRightButton);
        toolBar.add(automaticRotateToggleButton);
        toolBar.addSeparator();
        toolBar.add(automaticAdjustToWindowSizeJToggleButton);
        toolBar.addSeparator();
        toolBar.add(adjustToWindowSizeJButton);
        toolBar.add(zoomInButton);
        toolBar.add(zoomOutButton);
        toolBar.addSeparator();
        toolBar.add(resizeQuality);
        toolBar.addSeparator();
        toolBar.add(centerButton);
        toolBar.add(toggleNavigationImageButton);
        toolBar.addSeparator();
        toolBar.add(startSlideShowButton);
        toolBar.add(stopSlideShowButton);
        toolBar.add(slideShowDelay);

        this.getContentPane().add(toolBar, BorderLayout.NORTH);
    }

    public void createRightClickMenu(){

        rightClickMenu = new JPopupMenu();

        popupMenuPrevious = new JMenuItem(lang.get("imageviewer.popupmenu.back.text"));
        popupMenuNext = new JMenuItem(lang.get("imageviewer.popupmenu.forward.text"));
        popupMenuAdjustToWindowSize = new JMenuItem(lang.get("imageviewer.popupmenu.adjustToWindowSize.text"));
        popupMenuCopyImageToSystemClipboard = new JMenuItem(lang.get("imageviewer.popupmenu.copyImageToSystemClipboard.text"));
        popupMenuFullScreenView = new JMenuItem(lang.get("imageviewer.popupmenu.fullScreenView.text"));
        popupMenuExitFullScreenView = new JMenuItem(lang.get("imageviewer.popupmenu.exitFullScreenView.text"));

        rightClickMenu.add(popupMenuPrevious);
        rightClickMenu.add(popupMenuNext);
        rightClickMenu.add(popupMenuAdjustToWindowSize);
        rightClickMenu.add(popupMenuCopyImageToSystemClipboard);
        rightClickMenu.add(popupMenuFullScreenView);
        rightClickMenu.add(popupMenuExitFullScreenView);
    }

    // Create Status Bar
    public void createStatusPanel() {
        boolean [] timerStatus = {false,false,false,false};
        statuspanel = new StatusPanel(timerStatus);
        this.getContentPane().add(statuspanel, BorderLayout.SOUTH);
    }

    public void addImage(File image) {
        imagesToView.add(image);
        imagesToViewListSize++;
        this.addImageToOverViewPanel(image, overviewButtonListener, imagesToView.size() - 1, true);
    }

    private void addListeners() {
        this.addWindowListener(new WindowDestroyer());
        previousJButton.addActionListener(new ToolBarButtonPrevious());
        nextJButton.addActionListener(new ToolBarButtonNext());
        adjustToWindowSizeJButton.addActionListener(new ToolBarButtonAdjustToWindowSize());
        zoomInButton.addActionListener(new ToolBarButtonZoomIn());
        zoomOutButton.addActionListener(new ToolBarButtonZoomOut());
        startSlideShowButton.addActionListener(new ToolBarButtonStartSlideshow());
        stopSlideShowButton.addActionListener(new ToolBarButtonStopSlideshow());
        automaticAdjustToWindowSizeJToggleButton.addActionListener(new ToolBarButtonAutomaticAdjustToWindowSize());
        imageBackground.addMouseListener(new MouseButtonListener());
        popupMenuPrevious.addActionListener(new RightClickMenuListenerPrevious());
        popupMenuNext.addActionListener(new RightClickMenuListenerNext());
        popupMenuAdjustToWindowSize.addActionListener(new RightClickMenuListenerAdjustToWindowSize());
        popupMenuCopyImageToSystemClipboard.addActionListener(new RightClickMenuListenerCopyImageToSystemClipboard());
        popupMenuFullScreenView.addActionListener(new RightClickMenuListenerFullScreenView());
        popupMenuExitFullScreenView.addActionListener(new RightClickMenuListenerExitFullScreenView());
        maximizeButton.addActionListener(new OverviewMaximizeButton());
        minimizeButton.addActionListener(new OverviewMinimizeButton());
        rotateLeftButton.addActionListener(new ToolBarButtonRotateLeft());
        rotateRightButton.addActionListener(new ToolBarButtonRotateRight());
        automaticRotateToggleButton.addActionListener(new ToolBarButtonAutomaticRotate());
        resizeQuality.addItemListener(new ResizeQualityChangeListener());
        slideShowDelay.addItemListener(new SlideShowDelayChangeListener());
        centerButton.addActionListener(new CenterButton());
        toggleNavigationImageButton.addActionListener(new ToggleNavigationImageButton());
    }

    public void saveSettings() {
        GUI gUI = configuration.getgUI();

        Rectangle sizeAndLocation = gUI.getImageViewer().getSizeAndLocation();

        sizeAndLocation.setSize(this.getSize().width, this.getSize().height);
        sizeAndLocation.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y);

        List<GUIWindowSplitPane> guiWindowSplitPanes = gUI.getImageViewer().getGuiWindowSplitPane();

        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerLocation(guiWindowSplitPanes, ConfigElement.IMAGE_META_DATA, imageMetaDataSplitPane.getDividerLocation());
        GUIWindowSplitPaneUtil.setGUIWindowSplitPaneDividerWidth(guiWindowSplitPanes, ConfigElement.IMAGE_META_DATA, imageMetaDataSplitPane.getDividerSize());

        ImageViewerState imageViewerState = configuration.getImageViewerState();

        imageViewerState.setAutomaticallyResizeImages(automaticAdjustToWindowSizeJToggleButton.isSelected());
        imageViewerState.setAutomaticallyRotateImages(automaticRotateToggleButton.isSelected());
        imageViewerState.setResizeQuality(resizeQuality.getModel().getElementAt(resizeQuality.getSelectedIndex()).getMethod());
        imageViewerState.setShowNavigationImage(toggleNavigationImageButton.isSelected());
        imageViewerState.setSlideShowDelay(slideShowDelay.getModel().getElementAt(slideShowDelay.getSelectedIndex()));
    }

    private void removeCustomKeyEventDispatcher() {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.removeKeyEventDispatcher(customKeyEventDispatcher);
    }

    private void setStatusMessages (String imagePath, long fileSize,  int imageWidht, int imageHeight) {
        statuspanel.setStatusMessage(" " + imagePath, lang.get("imageviewer.statusbar.pathToPicture"), 0);
        statuspanel.setStatusMessage(" " + lang.get("imageviewer.statusbar.sizeLabel") + " " + StringUtil.formatBytes(fileSize, "0.00") + " ", lang.get("imageviewer.statusbar.sizeLabelImage") + " byte", 1);
        statuspanel.setStatusMessage(" " + lang.get("imageviewer.statusbar.widthLabel") + " " + Integer.toString(imageWidht)  + " px ", lang.get("imageviewer.statusbar.widthLabelImage"), 2);
        statuspanel.setStatusMessage(" " + lang.get("imageviewer.statusbar.heightLabel") + " "  + Integer.toString(imageHeight) + " px", lang.get("imageviewer.statusbar.heightLabelImage"), 3);
    }

    private void disposeFrame() {
        this.saveSettings();
        this.removeCustomKeyEventDispatcher();
        this.setVisible(false);
        this.dispose();
        ApplicationContext.getInstance().setImageViewerDisplayed(false);
    }

    private void createImage(String imagePath) {
        logger.logDEBUG("Total mem " + Runtime.getRuntime().totalMemory());
        logger.logDEBUG("Max mem   " + Runtime.getRuntime().maxMemory());

        try {
            // Load image from disk
            thePicture = new File(imagePath);
            BufferedImage img = ImageIO.read(thePicture);

            setStatusMessages(imagePath, thePicture.length(), img.getWidth(null), img.getHeight(null));

            metaDataPanel.setMetaData(thePicture);

            imageBackground.setImage(img, thePicture, !automaticAdjustToWindowSizeJToggleButton.isSelected(), automaticRotateToggleButton.isSelected(), resizeQuality.getModel().getElementAt(resizeQuality.getSelectedIndex()).getMethod());
        } catch (IOException iox) {
            logger.logERROR("Could not read the image: " + thePicture.getAbsolutePath());
            logger.logERROR(iox);
            JOptionPane.showMessageDialog(this, String.format(lang.get("imageviewer.could.nor.read.image"), thePicture.getAbsolutePath()), lang.get("common.error"), JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAndViewPreviousImage() {
        int indexForCurrentlyDisplayedImage = imageToViewListIndex;

        if (imageToViewListIndex == 0) {
            imageToViewListIndex = imagesToViewListSize - 1;
        } else {
            imageToViewListIndex -= 1;
        }

        createImageDisplayImageAndScrollThumbnailToVisibleRect(imageToViewListIndex);
        clearSelectionForCurrentlyDisplayedButton(indexForCurrentlyDisplayedImage);

        logger.logDEBUG("Image: " + imagesToView.get(imageToViewListIndex).getAbsolutePath() + " has been loaded");
    }

    private void loadAndViewNextImage() {
        int indexForCurrentlyDisplayedImage = imageToViewListIndex;

        if (imageToViewListIndex == imagesToViewListSize - 1) {
            imageToViewListIndex = 0;
        } else {
            imageToViewListIndex += 1;
        }

        createImageDisplayImageAndScrollThumbnailToVisibleRect(imageToViewListIndex);
        clearSelectionForCurrentlyDisplayedButton(indexForCurrentlyDisplayedImage);

        logger.logDEBUG("Image: " + imagesToView.get(imageToViewListIndex).getAbsolutePath() + " has been loaded");
    }

    private void createImageDisplayImageAndScrollThumbnailToVisibleRect(int imageToViewListIndex) {
        File imageToDisplay = imagesToView.get(imageToViewListIndex);

        createImage(imageToDisplay.getAbsolutePath());

        JButton jButtonForThumbnailForCurrentImageToDisplay = imageToJButtonMapping.get(imageToDisplay);

        ThumbNailGrayFilter grayFilter = configuration.getThumbNail().getGrayFilter();

        Image selectedIcon = ButtonIconUtil.getSelectedIcon(jButtonForThumbnailForCurrentImageToDisplay, grayFilter.isPixelsBrightened(), grayFilter.getPercentage());

        jButtonForThumbnailForCurrentImageToDisplay.setIcon(new ImageIcon(selectedIcon));

        if (jButtonForThumbnailForCurrentImageToDisplay != null) {
            imageOverViewPanel.scrollRectToVisible(jButtonForThumbnailForCurrentImageToDisplay.getBounds());
        }
    }

    private void clearSelectionForCurrentlyDisplayedButton(int indexForCurrentlyDisplayedImage) {
        File image = imagesToView.get(indexForCurrentlyDisplayedImage);
        JButton button = imageToJButtonMapping.get(image);
        ButtonIconUtil.setDeSelectedThumbNailImage(button, image);
    }

    public void resizeImage() {
        if (this.isVisible()) {
            adjustToWindowSizeJButton.doClick();
        }
    }

    private class WindowDestroyer extends WindowAdapter {
        @Override
        public void windowClosing (WindowEvent e) {
            disposeFrame();
        }
    }

    private class CustomKeyEventDispatcher implements KeyEventDispatcher {
        @Override
        public boolean dispatchKeyEvent(KeyEvent e) {

            if (e.getID() == KeyEvent.KEY_PRESSED) {
                if (KeyEvent.VK_ESCAPE == e.getKeyCode()) {
                    if (fullScreenEnabled) {
                        escapeFullScreenMode();
                    }
                    return true;
                }

                else if (KeyEvent.VK_F11 == e.getKeyCode()) {
                    if (fullScreenEnabled) {
                        escapeFullScreenMode();
                    } else {
                        enterFullScreenMode();
                    }
                    return true;
                }
            }

            if (e.getID() == KeyEvent.KEY_PRESSED && e.getModifiersEx() != KeyEvent.ALT_DOWN_MASK) {
                if (KeyEvent.VK_LEFT == e.getKeyCode()) {
                    if (!loadPreviousImageAction.isRunning()) {
                        Thread thread = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                loadPreviousImageAction.run();
                            }
                        });
                        loadPreviousImageAction.start();
                        thread.start();
                    }
                    return true;
                }
                if (KeyEvent.VK_RIGHT == e.getKeyCode()) {
                    if (!loadNextImageAction.isRunning()) {
                        Thread thread = new Thread(new Runnable() {

                            @Override
                            public void run() {
                                loadNextImageAction.run();
                            }
                        });
                        loadNextImageAction.start();
                        thread.start();
                    }
                    return true;
                }
            }

            if (e.getID() == KeyEvent.KEY_PRESSED && e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK) {
                if (KeyEvent.VK_C == e.getKeyCode()) {
                    copyDisplayedImageToSystemClipboard();
                }
                return true;
            }
            return false;
        }
    }

    private void enterFullScreenMode() {
        widthAndHeight = new Dimension(this.getSize().width, this.getSize().height);
        location = new Point(this.getLocationOnScreen().x, this.getLocationOnScreen().y);

        imageMetaDataSplitPaneDividerLocation = imageMetaDataSplitPane.getDividerLocation();

        this.dispose();
        this.setUndecorated(true);
        this.setBounds(getGraphicsConfiguration().getBounds());
        this.getGraphicsConfiguration().getDevice().setFullScreenWindow(this);

        imageMetaDataSplitPane.setDividerSize(0);
        imageMetaDataSplitPane.setRightComponent(null);

        imageBackground.setBackground(Color.BLACK);
        imageBackground.setOpaque(true);

        displaySidePanels(false);

        fullScreenEnabled = true;
        this.setVisible(true);
    }

    private void escapeFullScreenMode() {
        this.dispose();
        this.setUndecorated(false);

        this.setLocation(location);
        this.setSize(widthAndHeight);

        GUI gUI = configuration.getgUI();
        List<GUIWindowSplitPane> gUIWindowSplitPanes = gUI.getImageViewer().getGuiWindowSplitPane();

        imageMetaDataSplitPane.setDividerSize(GUIWindowSplitPaneUtil.getGUIWindowSplitPaneDividerSize(gUIWindowSplitPanes, ConfigElement.IMAGE_META_DATA));
        imageMetaDataSplitPane.setDividerLocation(imageMetaDataSplitPaneDividerLocation);
        imageMetaDataSplitPane.setRightComponent(metaDataPanel);

        imageBackground.setBackground(null);
        imageBackground.setOpaque(false);

        displaySidePanels(true);

        fullScreenEnabled = false;
        this.setVisible(true);
    }

    private void displaySidePanels(boolean visible) {
        statuspanel.setVisible(visible);
        imageOverviewPanel.setVisible(visible);
        toolBar.setVisible(visible);
    }

    private void copyDisplayedImageToSystemClipboard() {
        List<File> displayedImageFile = new ArrayList<File>();
        displayedImageFile.add(imagesToView.get(imageToViewListIndex));

        FileSelection fileSelection = new FileSelection(displayedImageFile);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
    }

    private class MouseButtonListener extends MouseAdapter{
        @Override
        public void mouseReleased(MouseEvent e){
            if(e.isPopupTrigger()) {
                if (fullScreenEnabled) {
                    popupMenuFullScreenView.setVisible(false);
                    popupMenuExitFullScreenView.setVisible(true);
                } else {
                    popupMenuFullScreenView.setVisible(true);
                    popupMenuExitFullScreenView.setVisible(false);
                }
                rightClickMenu.show(e.getComponent(),e.getX(), e.getY());
            }
        }
    }

    private class RightClickMenuListenerPrevious implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadAndViewPreviousImage();
        }
    }

    private class RightClickMenuListenerNext implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadAndViewNextImage();
        }
    }

    private class RightClickMenuListenerAdjustToWindowSize implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            adjustToWindowSizeJButton.doClick();
        }
    }

    private class RightClickMenuListenerCopyImageToSystemClipboard implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            copyDisplayedImageToSystemClipboard();
        }
    }

    private class RightClickMenuListenerFullScreenView implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            enterFullScreenMode();
        }
    }

    private class RightClickMenuListenerExitFullScreenView implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            escapeFullScreenMode();
        }
    }

    private class ToolBarButtonPrevious implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadAndViewPreviousImage();
        }
    }

    private class ToolBarButtonNext implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            loadAndViewNextImage();
        }
    }

    private class ToolBarButtonRotateLeft implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            imageBackground.rotateLeft();
        }
    }

    private class ToolBarButtonRotateRight implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            imageBackground.rotateRight();
        }
    }

    private class ToolBarButtonAutomaticRotate implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            imageBackground.setRotateImageAccordingToExif(automaticRotateToggleButton.isSelected());
        }
    }

    private class ToolBarButtonAutomaticAdjustToWindowSize implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            imageBackground.setShowNonScaled(!automaticAdjustToWindowSizeJToggleButton.isSelected());
        }
    }

    private class ToolBarButtonAdjustToWindowSize implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            imageBackground.setShowNonScaled(false);
        }
    }

    private class ToolBarButtonZoomIn implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            imageBackground.zoomIn();
        }
    }

    private class ToolBarButtonZoomOut implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            imageBackground.zoomOut();
        }
    }

    private class ToolBarButtonStartSlideshow implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!slideShowTimer.isRunning()) {
                slideShowTimer.start();
            }
        }
    }

    private class ToolBarButtonStopSlideshow implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (slideShowTimer.isRunning()) {
                slideShowTimer.stop();
            }
        }
    }

    private class OverviewMaximizeButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            overViewScrollpane.setVisible(true);
            Update.updateComponentTreeUI(overViewBackgroundPanel);
        }
    }

    private class OverviewMinimizeButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            overViewScrollpane.setVisible(false);
            Update.updateComponentTreeUI(overViewBackgroundPanel);
        }
    }

    private class OverviewButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int indexForCurrentlyDisplayedImage = imageToViewListIndex;

            imageToViewListIndex = Integer.parseInt(e.getActionCommand());

            if (indexForCurrentlyDisplayedImage != imageToViewListIndex) {
                createImageDisplayImageAndScrollThumbnailToVisibleRect(imageToViewListIndex);
                clearSelectionForCurrentlyDisplayedButton(indexForCurrentlyDisplayedImage);
            }
        }
    }

    private class LoadImageAction {

        public LoadImageAction(Direction direction) {
            this.direction = direction;
        }

        private final Direction direction;
        private boolean isRunning = false;

        public void run() {
            switch (direction) {
            case NEXT:
                loadAndViewNextImage();
                break;
            case PREVIOUS:
                loadAndViewPreviousImage();
                break;
            default:
                break;
            }
            completed();
        }

        public synchronized void start() {
            isRunning = true;
        }

        public synchronized void completed() {
            isRunning = false;
        }

        public synchronized boolean isRunning() {
            return isRunning;
        }
    }

    /**
     * Listens for selection changes of the scale quality {@link Method}
     * {@link JComboBox} and if a change is detected, this change is propagated
     * to the {@link NavigableImagePanel} and the displayed image is newly
     * rendered with the selected quality method.
     *
     * @author Fredrik
     *
     */
    private class ResizeQualityChangeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent ie) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                ResizeQualityAndDisplayString resizeQualityAndDisplayString = (ResizeQualityAndDisplayString)ie.getItem();
                imageBackground.setHighQualityScalingMethodToUse(resizeQualityAndDisplayString.getMethod());
            }
        }
    }

    private class SlideShowDelayChangeListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent ie) {
            if (ie.getStateChange() == ItemEvent.SELECTED) {
                Integer delayInSeconds = (Integer)ie.getItem();
                slideShowTimer.setDelay(delayInSeconds * 1000);
            }
        }
    }

    /**
     * Listens for clicks of the center image button. When this button is
     * clicked then must the displayed image being centered.
     *
     * @author Fredrik
     *
     */
    private class CenterButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            imageBackground.centerImage();
            imageBackground.repaint();
        }
    }

    /**
     * Listens for changes in state of the {@link JToggleButton} that specifies
     * whether or not the navigation image shall be visible or not in the
     * displayed image.
     *
     * @author Fredrik
     *
     */
    private class ToggleNavigationImageButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            imageBackground.setNavigationImageEnabled(toggleNavigationImageButton.isSelected());
        }
    }
}
