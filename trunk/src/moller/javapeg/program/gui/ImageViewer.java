package moller.javapeg.program.gui;

import java.awt.BorderLayout;
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
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.C;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.contexts.ApplicationContext;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaDataUtil;
import moller.util.gui.Screen;
import moller.util.gui.Update;
import moller.util.image.ImageUtil;
import moller.util.mnemonic.MnemonicConverter;
import moller.util.string.StringUtil;

public class ImageViewer extends JFrame {

	private static final long serialVersionUID = 1L;

	private static Config config;
	private static Logger logger;
	private static Language lang;

	private JLabel picture;

	private JToolBar toolBar;
	private JPopupMenu rightClickMenu;
	private StatusPanel statuspanel;

	private JPanel imageBackground;
	private JPanel overViewBackgroundPanel;
	private JPanel imageOverViewPanel;

	private JMenuItem popupMenuPrevious;
	private JMenuItem popupMenuNext;
	private JMenuItem popupMenuAdjustToWindowSize;

	private JButton previousJButton;
	private JButton nextJButton;
	private JButton adjustToWindowSizeJButton;
	private JButton rotateLeftButton;
	private JButton rotateRightButton;

	private JButton maximizeButton;
	private JButton minimizeButton;

	private JSplitPane imageMetaDataSplitPane;

	private JScrollPane overViewScrollpane;
	private JScrollPane scrollpane;

	private JScrollBar hSB;
	private JScrollBar vSB;

	private MetaDataPanel metaDataPanel;

	private JToggleButton automaticAdjustToWindowSizeJToggleButton;
	private JToggleButton automaticRotateToggleButton;
	private File thePicture;

	private final List<File> imagesToView;

	private int imageToViewListIndex;
	private int imagesToViewListSize;
	private int currentGUIImageRotation;

	private CustomKeyEventDispatcher customKeyEventDispatcher;

	private OverviewButtonListener overviewButtonListener;

	public ImageViewer(List<File> imagesToView) {

		config = Config.getInstance();
		logger = Logger.getInstance();
		lang   = Language.getInstance();

		this.imagesToView = imagesToView;

		imageToViewListIndex = 0;
		imagesToViewListSize = imagesToView.size();

		this.createMainFrame();
		this.createToolBar();
		this.createRightClickMenu();
		this.createStatusPanel();
		this.addListeners();
		this.createImage(imagesToView.get(0).getAbsolutePath());
	}

	// Create Main Window
	public void createMainFrame() {

		this.setSize(new Dimension(config.getIntProperty("imageViewerGUI.window.width"), config.getIntProperty("imageViewerGUI.window.height")));

		Point xyFromConfig = new Point(config.getIntProperty("imageViewerGUI.window.location.x"),config.getIntProperty("imageViewerGUI.window.location.y"));

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

		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		customKeyEventDispatcher = new CustomKeyEventDispatcher();
		manager.addKeyEventDispatcher(customKeyEventDispatcher);

		InputStream imageStream = StartJavaPEG.class.getResourceAsStream(C.ICONFILEPATH_IMAGEVIEWER + "Open16.gif");

		ImageIcon image = new ImageIcon();
		try {
			image.setImage(ImageIO.read(imageStream));
			this.setIconImage(image.getImage());
		} catch (IOException e) {
			logger.logERROR("Could not load icon: Open16.gif");
			logger.logERROR(e);
		}

		imageBackground = new JPanel(new BorderLayout());

		hSB = new JScrollBar(JScrollBar.HORIZONTAL);
		vSB = new JScrollBar(JScrollBar.VERTICAL);

		hSB.setUnitIncrement(40);
		vSB.setUnitIncrement(40);

		scrollpane = new JScrollPane(imageBackground, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setHorizontalScrollBar(hSB);
		scrollpane.setVerticalScrollBar(vSB);

		thePicture = new File(imagesToView.get(0).getAbsolutePath());

		this.setTitle(thePicture.getParent());

		metaDataPanel = new MetaDataPanel();
		metaDataPanel.setMetaData(thePicture);

		imageMetaDataSplitPane = new JSplitPane();
		imageMetaDataSplitPane.setOneTouchExpandable(true);
		imageMetaDataSplitPane.setDividerSize(config.getIntProperty("imageViewerGUI.splitpane.width.image-metadata"));
		imageMetaDataSplitPane.setDividerLocation(config.getIntProperty("imageViewerGUI.splitpane.location.image-metadata"));
		imageMetaDataSplitPane.setLeftComponent(scrollpane);
		imageMetaDataSplitPane.setRightComponent(metaDataPanel);

		this.getContentPane().add(imageMetaDataSplitPane, BorderLayout.CENTER);

		overViewBackgroundPanel = this.createOverviewPanel();

		this.getContentPane().add(overViewBackgroundPanel, BorderLayout.WEST);

		picture = new JLabel();
	}

	private JPanel createOverviewPanel() {

		JPanel backgroundPanel = new JPanel(new GridBagLayout());

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

		for(int i = 0; i < imagesToView.size(); i++) {
			this.addImageToOverViewPanel(imagesToView.get(i), overviewButtonListener, i);
		}

		overViewScrollpane.getViewport().add(imageOverViewPanel);

		GBHelper posBackgroundPanel = new GBHelper();

		backgroundPanel.add(overViewScrollpane, posBackgroundPanel.expandW());
		backgroundPanel.add(buttonBackgroundPanel, posBackgroundPanel.nextCol().align(GridBagConstraints.NORTH).expandH());

		return backgroundPanel;
	}

	private void addImageToOverViewPanel(File jpegImage, OverviewButtonListener overviewButtonListener, int index) {
		JButton imageButton = new JButton(new ImageIcon(JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(jpegImage).getThumbNailData()));

		imageButton.setActionCommand(Integer.toString(index));
		if (!config.getStringProperty("thumbnails.tooltip.state").equals("0")) {
			imageButton.setToolTipText(MetaDataUtil.getToolTipText(jpegImage));
		}
		imageButton.addActionListener(overviewButtonListener);

		imageOverViewPanel.add(imageButton);
		Update.updateAllUIs();

	}

	// Create ToolBar
	public void createToolBar()	{
		toolBar = new JToolBar();
		toolBar.setRollover(true);

		previousJButton = new JButton();
		nextJButton = new JButton();
		automaticAdjustToWindowSizeJToggleButton = new JToggleButton();
		automaticRotateToggleButton = new JToggleButton();
		adjustToWindowSizeJButton = new JButton();
		rotateLeftButton = new JButton();
		rotateRightButton = new JButton();

		InputStream imageStream = null;

		ImageIcon previousImageIcon = new ImageIcon();
		ImageIcon nextImageIcon = new ImageIcon();
		ImageIcon automaticAdjustToWindowSizeImageIcon = new ImageIcon();
		ImageIcon adjustToWindowSizeImageIcon = new ImageIcon();
		ImageIcon rotateLeftImageIcon = new ImageIcon();
		ImageIcon rotateRightImageIcon = new ImageIcon();
		ImageIcon automaticRotateImageIcon = new ImageIcon();

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
		} catch (IOException e) {
			logger.logERROR("Could not load image. See Stack Trace below for details");
			logger.logERROR(e);
		}

		toolBar.add(previousJButton);
		toolBar.add(nextJButton);
		toolBar.addSeparator();
		toolBar.add(automaticAdjustToWindowSizeJToggleButton);
		toolBar.add(adjustToWindowSizeJButton);
		toolBar.addSeparator();
		toolBar.add(rotateLeftButton);
		toolBar.add(rotateRightButton);
		toolBar.add(automaticRotateToggleButton);

		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	public void createRightClickMenu(){

		rightClickMenu = new JPopupMenu();

		popupMenuPrevious = new JMenuItem(lang.get("imageviewer.popupmenu.back.text"));
		popupMenuNext = new JMenuItem(lang.get("imageviewer.popupmenu.forward.text"));
		popupMenuAdjustToWindowSize = new JMenuItem(lang.get("imageviewer.popupmenu.adjustToWindowSize.text"));

		rightClickMenu.add(popupMenuPrevious);
		rightClickMenu.add(popupMenuNext);
		rightClickMenu.add(popupMenuAdjustToWindowSize);
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
		this.addImageToOverViewPanel(image, overviewButtonListener, imagesToView.size() - 1);
	}

	private void addListeners() {
		this.addWindowListener(new WindowDestroyer());
		previousJButton.addActionListener(new ToolBarButtonPrevious());
		nextJButton.addActionListener(new ToolBarButtonNext());
		adjustToWindowSizeJButton.addActionListener(new ToolBarButtonAdjustToWindowSize());
		automaticAdjustToWindowSizeJToggleButton.addActionListener(new ToolBarButtonAutomaticAdjustToWindowSize());
		imageBackground.addMouseListener(new MouseButtonListener());
		popupMenuPrevious.addActionListener(new RightClickMenuListenerPrevious());
		popupMenuNext.addActionListener(new RightClickMenuListenerNext());
		popupMenuAdjustToWindowSize.addActionListener(new RightClickMenuListenerAdjustToWindowSize());
		maximizeButton.addActionListener(new OverviewMaximizeButton());
		minimizeButton.addActionListener(new OverviewMinimizeButton());
		rotateLeftButton.addActionListener(new ToolBarButtonRotateLeft());
		rotateRightButton.addActionListener(new ToolBarButtonRotateRight());
		automaticRotateToggleButton.addActionListener(new ToolBarButtonAutomaticRotate());
	}

	private void saveSettings() {
		config.setIntProperty("imageViewerGUI.window.location.x", this.getLocationOnScreen().x);
		config.setIntProperty("imageViewerGUI.window.location.y", this.getLocationOnScreen().y);
		config.setIntProperty("imageViewerGUI.window.width", this.getSize().width);
		config.setIntProperty("imageViewerGUI.window.height", this.getSize().height);
		config.setIntProperty("imageViewerGUI.splitpane.location.image-metadata", imageMetaDataSplitPane.getDividerLocation());
		config.setIntProperty("imageViewerGUI.splitpane.width.image-metadata", imageMetaDataSplitPane.getDividerSize());
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

		// Load image from disk
		Image img = Toolkit.getDefaultToolkit().createImage(imagePath);

		while (img.getWidth(null) < 0 || img.getHeight(null) < 0) {
		    try {
		    	Thread.sleep(200);
		    } catch(InterruptedException ie) {
		    }
		}

		thePicture = new File(imagePath);


		// Rotate image if necessary and automatic rotation is selected.
		if(automaticRotateToggleButton.isSelected()) {
			img = rotateAccordingToExif(thePicture, img);
		}


		int imageWidth  = img.getWidth(null);
		int imageHeight = img.getHeight(null);

		setStatusMessages(imagePath, thePicture.length(), imageWidth, imageHeight);

		metaDataPanel.setMetaData(thePicture);

		// Resize the image if necessary and automatic resizing is selected
		if(automaticAdjustToWindowSizeJToggleButton.isSelected()) {
			Rectangle visibleImageBackgroundRectangle = imageBackground.getVisibleRect();

			double backgroundWidth = visibleImageBackgroundRectangle.getWidth();
			double backgroundHeight = visibleImageBackgroundRectangle.getHeight();

			if((imageWidth > visibleImageBackgroundRectangle.getWidth())||(imageHeight > visibleImageBackgroundRectangle.getHeight())){
				img = this.resizeImage(img, backgroundWidth, backgroundHeight, imageWidth, imageHeight);
			}
		}

		// Display the loaded and possibly rotated and resized image.
		ImageIcon icon = new ImageIcon();
		icon.setImage(img);

		picture.setIcon(null);
		picture.setIcon(icon);

		imageBackground.removeAll();
		imageBackground.updateUI();
		imageBackground.add(picture, BorderLayout.CENTER);
	}

	/**
	 * According to the Exif Version 2.2 standard:
	 *
	 * 1 = The 0th row is at the visual top of the image, and the 0th column is the visual left-hand side.
     * 2 = The 0th row is at the visual top of the image, and the 0th column is the visual right-hand side.
     * 3 = The 0th row is at the visual bottom of the image, and the 0th column is the visual right-hand side.
     * 4 = The 0th row is at the visual bottom of the image, and the 0th column is the visual left-hand side.
     * 5 = The 0th row is the visual left-hand side of the image, and the 0th column is the visual top.
     * 6 = The 0th row is the visual right-hand side of the image, and the 0th column is the visual top.
     * 7 = The 0th row is the visual right-hand side of the image, and the 0th column is the visual bottom.
     * 8 = The 0th row is the visual left-hand side of the image, and the 0th column is the visual bottom.
     * Other = reserved
     *
	 * @param imageFile
	 * @param img
	 * @return
	 */
	private Image rotateAccordingToExif(File imageFile, Image img) {
		int rotationAccordingToExif;

		switch (MetaDataUtil.getOrientationTag(imageFile)) {
		case 1:
			rotationAccordingToExif = 0;
			break;
		case 2:
			return img;
		case 3:
			rotationAccordingToExif = 180;
			break;
		case 4:
			return img;
		case 5:
			return img;
		case 6:
			rotationAccordingToExif = 90;
			break;
		case 7:
			return img;
		case 8:
			rotationAccordingToExif = 270;
			break;
		default:
			return img;
		}

		return rotateImageAccordingToExifAndCurrentGUIRotation(rotationAccordingToExif, img);
	}

	private void loadAndViewPreviousImage() {
		if (imageToViewListIndex == 0) {
			imageToViewListIndex = imagesToViewListSize - 1;
		} else {
			imageToViewListIndex -= 1;
		}
		currentGUIImageRotation = 0;
		createImage(imagesToView.get(imageToViewListIndex).getAbsolutePath());
		logger.logDEBUG("Image: " + imagesToView.get(imageToViewListIndex).getAbsolutePath() + " has been loaded");
	}

	private void loadAndViewNextImage() {
		if (imageToViewListIndex == imagesToViewListSize - 1) {
			imageToViewListIndex = 0;
		} else {
			imageToViewListIndex += 1;
		}
		currentGUIImageRotation = 0;
		createImage(imagesToView.get(imageToViewListIndex).getAbsolutePath());
		logger.logDEBUG("Image: " + imagesToView.get(imageToViewListIndex).getAbsolutePath() + " has been loaded");
	}

	private Image resizeImage(Image image, double backgroundWidth, double backgroundHeight, int imageWidth, int imageHeight) {

		if((imageWidth > backgroundWidth) && (imageHeight <= backgroundHeight)) {
			image = image.getScaledInstance((int)backgroundWidth, (int)(imageHeight/(imageWidth/backgroundWidth)), Image.SCALE_FAST);
		} else if((imageWidth <= backgroundWidth)&&(imageHeight>backgroundHeight)) {
			image = image.getScaledInstance((int)(imageWidth/(imageHeight/backgroundHeight)), (int)backgroundHeight, Image.SCALE_FAST);
		} else if((imageWidth>backgroundWidth)&&(imageHeight>backgroundHeight)) {

			int scrollBarPaddingHeight = 0;
			int scrollBarPaddingWidth = 0;

			if(hSB.isVisible()) {
				scrollBarPaddingHeight = hSB.getHeight();
			}
			if(vSB.isVisible()) {
				scrollBarPaddingWidth= vSB.getWidth();
			}

			if((imageWidth/backgroundWidth)>(imageHeight/backgroundHeight)) {
				image = image.getScaledInstance((int)backgroundWidth + scrollBarPaddingWidth, (int)(imageHeight/(imageWidth/backgroundWidth)) + scrollBarPaddingWidth, Image.SCALE_FAST);
			} else {
				image = image.getScaledInstance((int)(imageWidth/(imageHeight/backgroundHeight)) + scrollBarPaddingHeight, (int)backgroundHeight + scrollBarPaddingHeight, Image.SCALE_FAST);
			}
		}
		return image;
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
	        if (e.getID() == KeyEvent.KEY_PRESSED && e.getModifiersEx() != KeyEvent.ALT_DOWN_MASK) {
	        	if (KeyEvent.VK_LEFT == e.getKeyCode()) {
	        		loadAndViewPreviousImage();
	        		return true;
	        	}
				if (KeyEvent.VK_RIGHT == e.getKeyCode()) {
					loadAndViewNextImage();
					return true;
				}
	        }
	        return false;
	    }
	}

	private class MouseButtonListener extends MouseAdapter{
		@Override
		public void mouseReleased(MouseEvent e){
			if(e.isPopupTrigger()) {
				rightClickMenu.show(e.getComponent(),e.getX(), e.getY());
			}
		}
	}

	private class RightClickMenuListenerPrevious implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			previousJButton.doClick();
		}
	}

	private class RightClickMenuListenerNext implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			nextJButton.doClick();
		}
	}

	private class RightClickMenuListenerAdjustToWindowSize implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			adjustToWindowSizeJButton.doClick();
		}
	}

	private class ToolBarButtonPrevious implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			loadAndViewPreviousImage();
		}
	}

	private class ToolBarButtonNext implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			loadAndViewNextImage();
		}
	}

	private Image rotateImageAccordingToExifAndCurrentGUIRotation(int rotationAccordingToExif, Image img) {
		if (currentGUIImageRotation == rotationAccordingToExif) {
			return img;
		}

		int angleToRotate = rotationAccordingToExif - currentGUIImageRotation;

		if (angleToRotate < 0) {
			angleToRotate += 360;
		}
		return rotateImage(img, angleToRotate);
	}

	private Image rotateImage(Image image, int angle) {
		updateCurrentGUIImageRotation(angle);
		return ImageUtil.rotateImage(image, angle);
	}

	private void updateCurrentGUIImageRotation(int angle) {
		if (angle < 0) {
			angle += 360;
		}

		currentGUIImageRotation += angle;

		if (currentGUIImageRotation >= 360) {
			currentGUIImageRotation -= 360;
		}
	}

	private class ToolBarButtonRotateLeft implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ImageIcon icon = (ImageIcon)picture.getIcon();

			icon.setImage(rotateImage(icon.getImage(), -90));

			picture.setIcon(null);
			picture.setIcon(icon);

			imageBackground.removeAll();
			imageBackground.updateUI();
			imageBackground.add(picture, BorderLayout.CENTER);
		}
	}

	private class ToolBarButtonRotateRight implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ImageIcon icon = (ImageIcon)picture.getIcon();

			icon.setImage(rotateImage(icon.getImage(), 90));

			picture.setIcon(null);
			picture.setIcon(icon);

			imageBackground.removeAll();
			imageBackground.updateUI();
			imageBackground.add(picture, BorderLayout.CENTER);
		}
	}

	private class ToolBarButtonAutomaticRotate implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(automaticRotateToggleButton.isSelected()) {
				ImageIcon icon = (ImageIcon)picture.getIcon();

				Image img = rotateAccordingToExif(thePicture, icon.getImage());

				icon.setImage(img);

				picture.setIcon(null);
				picture.setIcon(icon);

				imageBackground.removeAll();
				imageBackground.updateUI();
				imageBackground.add(picture, BorderLayout.CENTER);
			}
		}
	}

	private class ToolBarButtonAutomaticAdjustToWindowSize implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(automaticAdjustToWindowSizeJToggleButton.isSelected()) {
				Dimension pictureDimensions = picture.getSize();

				int pictureWidth  = pictureDimensions.width;
				int pictureHeight = pictureDimensions.height;

				Rectangle tempRect = imageBackground.getVisibleRect();

				double backgroundWidth  = tempRect.getWidth();
				double backgroundHeight = tempRect.getHeight();

				if((pictureWidth > backgroundWidth) || (pictureHeight > backgroundHeight)){
					adjustToWindowSizeJButton.doClick();
				}
			}
		}
	}

	private class ToolBarButtonAdjustToWindowSize implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Dimension pictureDimensions = picture.getSize();

			int pictureWidth  = pictureDimensions.width;
			int pictureHeight = pictureDimensions.height;

			Rectangle imageBackgroundRectangle = imageBackground.getVisibleRect();

			double backgroundWidth = imageBackgroundRectangle.getWidth();
			double backgroundHeight = imageBackgroundRectangle.getHeight();

			if((pictureWidth > backgroundWidth)||(pictureHeight > backgroundHeight)) {
				Image tempImage = ((ImageIcon)picture.getIcon()).getImage();

				tempImage = resizeImage(tempImage, backgroundWidth, backgroundHeight, pictureWidth, pictureHeight);

				ImageIcon icon = new ImageIcon();
				icon.setImage(tempImage);

				picture.setIcon(null);
				picture.setIcon(icon);

				imageBackground.removeAll();
				imageBackground.updateUI();
				imageBackground.add(picture, BorderLayout.CENTER);
			}
		}
	}

	private class OverviewMaximizeButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			overViewScrollpane.setVisible(true);
			Update.updateComponentTreeUI(overViewBackgroundPanel);
		}
	}

	private class OverviewMinimizeButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			overViewScrollpane.setVisible(false);
			Update.updateComponentTreeUI(overViewBackgroundPanel);
		}
	}

	private class OverviewButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			imageToViewListIndex = Integer.parseInt(e.getActionCommand());
			currentGUIImageRotation = 0;
			createImage(imagesToView.get(imageToViewListIndex).getAbsolutePath());
		}
	}
}