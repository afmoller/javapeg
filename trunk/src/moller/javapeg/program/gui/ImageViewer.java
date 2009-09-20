package moller.javapeg.program.gui;
/**
* This class was created : 2003-10-17 by Fredrik Möller
* Latest changed         : 2003-10-19 by Fredrik Möller
*                        : 2003-10-21 by Fredrik Möller
*                        : 2003-11-04 by Fredrik Möller
*                        : 2003-11-05 by Fredrik Möller
*                        : 2003-11-07 by Fredrik Möller
*                        : 2003-11-08 by Fredrik Möller
*                        : 2003-11-15 by Fredrik Möller
*                        : 2003-12-27 by Fredrik Möller
*                        : 2003-12-28 by Fredrik Möller
*                        : 2004-03-13 by Fredrik Möller
*                        : 2004-04-23 by Fredrik Möller
*                        : 2009-06-08 by Fredrik Möller
*                        : 2009-06-16 by Fredrik Möller
*                        : 2009-06-20 by Fredrik Möller
*                        : 2009-07-03 by Fredrik Möller
*                        : 2009-07-04 by Fredrik Möller
*                        : 2009-07-05 by Fredrik Möller
*                        : 2009-07-06 by Fredrik Möller
*                        : 2009-07-07 by Fredrik Möller
*                        : 2009-08-16 by Fredrik Möller
*                        : 2009-08-17 by Fredrik Möller
*                        : 2009-08-18 by Fredrik Möller
*                        : 2009-08-19 by Fredrik Möller
*                        : 2009-08-20 by Fredrik Möller
*                        : 2009-08-21 by Fredrik Möller
*                        : 2009-08-30 by Fredrik Möller
*                        : 2009-09-19 by Fredrik Möller
*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import moller.javapeg.program.ApplicationContext;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaDataUtil;
import moller.util.gui.Screen;
import moller.util.gui.Update;
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
	
	private JMenuItem popupMenuPrevious;
	private JMenuItem popupMenuNext;
	private JMenuItem popupMenuAdjustToWindowSize;

	private JButton previousJButton;
	private JButton nextJButton;
	private JButton adjustToWindowSizeJButton;
	
	private JButton maximizeButton;
	private JButton minimizeButton;

	private JSplitPane imageMetaDataSplitPane;
	
	private JScrollPane overViewScrollpane;
	private JScrollPane scrollpane;
	
	private JScrollBar hSB;
	private JScrollBar vSB;
	
	private MetaDataPanel metaDataPanel;
	
	private JToggleButton automaticAdjustToWindowSizeJToggleButton;
	private File thePicture;

	private String ICONFILEPATH = "resources/images/imageviewer/";
	
	private List<File> imagesToView;
	
	private int imageToViewListIndex;
	private int imagesToViewListSize;
		
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

		InputStream imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Open16.gif");
				
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
			imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Forward16.gif");
			maximizeImageIcon.setImage(ImageIO.read(imageStream));
			maximizeButton.setIcon(maximizeImageIcon);
			maximizeButton.setPreferredSize(buttonSize);
			maximizeButton.setMinimumSize(buttonSize);
			
			imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Back16.gif");
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
				
		JPanel imageOverViewPanel = new JPanel(new GridLayout(0, 1));	
		imageOverViewPanel.setBorder(BorderFactory.createCompoundBorder(new EtchedBorder(EtchedBorder.LOWERED), new EmptyBorder(2, 2, 2, 2)));
		
		OverviewButtonListener overviewButtonListener = new OverviewButtonListener();
		
		for(int i = 0; i < imagesToView.size(); i++) {	
			File jpegImage = imagesToView.get(i);
			
			JButton imageButton = new JButton(new ImageIcon(JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(jpegImage).getThumbNailData())); 
			
			imageButton.setActionCommand(Integer.toString(i));
			imageButton.setToolTipText(MetaDataUtil.getToolTipText(jpegImage));
			imageButton.addActionListener(overviewButtonListener);
			
			imageOverViewPanel.add(imageButton);
		}
		
		overViewScrollpane.getViewport().add(imageOverViewPanel);
		
		GBHelper posBackgroundPanel = new GBHelper();
						
		backgroundPanel.add(overViewScrollpane, posBackgroundPanel.expandW());
		backgroundPanel.add(buttonBackgroundPanel, posBackgroundPanel.nextCol().align(GridBagConstraints.NORTH).expandH());
				
		return backgroundPanel;
	}
	
	// Create ToolBar
	public void createToolBar()
	{
		toolBar = new JToolBar();
		toolBar.setRollover(true);
		
		previousJButton = new JButton();
		nextJButton = new JButton();
		automaticAdjustToWindowSizeJToggleButton = new JToggleButton();
		adjustToWindowSizeJButton = new JButton();
		
		InputStream imageStream = null;
		
		ImageIcon previousImageIcon = new ImageIcon();
		ImageIcon nextImageIcon = new ImageIcon();
		ImageIcon automaticAdjustToWindowSizeImageIcon = new ImageIcon();
		ImageIcon adjustToWindowSizeImageIcon = new ImageIcon();
				
		try {
			imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Back16.gif");
			previousImageIcon.setImage(ImageIO.read(imageStream));
			previousJButton.setIcon(previousImageIcon);
			previousJButton.setToolTipText(lang.get("imageviewer.button.back.toolTip"));
			previousJButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.back.mnemonic").charAt(0)));
						
			imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Forward16.gif");
			nextImageIcon.setImage(ImageIO.read(imageStream));
			nextJButton.setIcon(nextImageIcon);
			nextJButton.setToolTipText(lang.get("imageviewer.button.forward.toolTip"));
			nextJButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.forward.mnemonic").charAt(0)));
			
			imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "AutoAdjustToWindowSize16.gif");
			automaticAdjustToWindowSizeImageIcon.setImage(ImageIO.read(imageStream));
			automaticAdjustToWindowSizeJToggleButton.setIcon(automaticAdjustToWindowSizeImageIcon);
			automaticAdjustToWindowSizeJToggleButton.setToolTipText(lang.get("imageviewer.button.automaticAdjustToWindowSize.toolTip"));
			automaticAdjustToWindowSizeJToggleButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.automaticAdjustToWindowSize.mnemonic").charAt(0)));
	
			imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Zoom16.gif");
			adjustToWindowSizeImageIcon.setImage(ImageIO.read(imageStream));
			adjustToWindowSizeJButton.setIcon(adjustToWindowSizeImageIcon);
			adjustToWindowSizeJButton.setToolTipText(lang.get("imageviewer.button.adjustToWindowSize.toolTip"));
			adjustToWindowSizeJButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.adjustToWindowSize.mnemonic").charAt(0)));
		} catch (IOException e) {
			logger.logERROR("Could not load image. See Stack Trace below for details");
			logger.logERROR(e);
		}
		
		toolBar.add(previousJButton);
		toolBar.add(nextJButton);
		toolBar.addSeparator();
		toolBar.add(automaticAdjustToWindowSizeJToggleButton);
		toolBar.add(adjustToWindowSizeJButton);

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
	}
	
	private void saveSettings() {
		config.setIntProperty("imageViewerGUI.window.location.x", this.getLocationOnScreen().x);
		config.setIntProperty("imageViewerGUI.window.location.y", this.getLocationOnScreen().y);
		config.setIntProperty("imageViewerGUI.window.width", this.getSize().width);
		config.setIntProperty("imageViewerGUI.window.height", this.getSize().height);
		config.setIntProperty("imageViewerGUI.splitpane.location.image-metadata", imageMetaDataSplitPane.getDividerLocation());
		config.setIntProperty("imageViewerGUI.splitpane.width.image-metadata", imageMetaDataSplitPane.getDividerSize());	
	}
	
	private void setStatusMessages (String imagePath, long fileSize,  int imageWidht, int imageHeight) {
		statuspanel.setStatusMessage(" " + imagePath, lang.get("imageviewer.statusbar.pathToPicture"), 0);
		statuspanel.setStatusMessage(" " + lang.get("imageviewer.statusbar.sizeLabel") + " " + StringUtil.formatBytes(fileSize, "0.00") + " ", lang.get("imageviewer.statusbar.sizeLabelImage") + " byte", 1);
		statuspanel.setStatusMessage(" " + lang.get("imageviewer.statusbar.widthLabel") + " " + Integer.toString(imageWidht)  + " px ", lang.get("imageviewer.statusbar.widthLabelImage"), 2);
		statuspanel.setStatusMessage(" " + lang.get("imageviewer.statusbar.heightLabel") + " "  + Integer.toString(imageHeight) + " px", lang.get("imageviewer.statusbar.heightLabelImage"), 3);
	}
		
	private void disposeFrame() {
		this.saveSettings();
		this.setVisible(false);
		this.dispose();
		ApplicationContext.getInstance().setImageViewerDisplayed(false);
	}
	
	private void createImage(String imagePath) {
		
		logger.logDEBUG("Total mem " + Runtime.getRuntime().totalMemory());
		logger.logDEBUG("Max mem   " + Runtime.getRuntime().maxMemory());
				
		Image img = Toolkit.getDefaultToolkit().createImage(imagePath);
			
		while (img.getWidth(null) < 0 || img.getHeight(null) < 0) {
		    try {
		    	Thread.sleep(200);
		    } catch(InterruptedException ie) {
		    }
		}
			
		int imageWidth  = img.getWidth(null);
		int imageHeight = img.getHeight(null);
		
		// Om knappen justera storlek automatiskt är intryckt
		if(automaticAdjustToWindowSizeJToggleButton.isSelected()) {	
			Rectangle visibleImageBackgroundRectangle = imageBackground.getVisibleRect();

			double backgroundWidth = visibleImageBackgroundRectangle.getWidth();
			double backgroundHeight = visibleImageBackgroundRectangle.getHeight();
			
			if((imageWidth > visibleImageBackgroundRectangle.getWidth())||(imageHeight > visibleImageBackgroundRectangle.getHeight())){
				img = this.resizeImage(img, backgroundWidth, backgroundHeight, imageWidth, imageHeight);
			}
		}
		
		ImageIcon icon = new ImageIcon();
		icon.setImage(img);
		
		picture.setIcon(null);
		System.out.println("Before " + Runtime.getRuntime().freeMemory());
		picture.setIcon(icon);
		System.out.println("After " + Runtime.getRuntime().freeMemory());

		imageBackground.removeAll();
		imageBackground.updateUI();
		imageBackground.add(picture, BorderLayout.CENTER);
		
		File imageFile = new File(imagePath);
		
		setStatusMessages(imagePath, imageFile.length(), imageWidth, imageHeight);
		
		metaDataPanel.setMetaData(imageFile);
		
		
	}

	private class WindowDestroyer extends WindowAdapter {
		public void windowClosing (WindowEvent e) {
			disposeFrame();
		}
	}

	private class MouseButtonListener extends MouseAdapter{
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
			if (imageToViewListIndex == 0) {
				imageToViewListIndex = imagesToViewListSize - 1;
			} else {
				imageToViewListIndex -= 1;
			}			
			createImage(imagesToView.get(imageToViewListIndex).getAbsolutePath());
			logger.logDEBUG("Image: " + imagesToView.get(imageToViewListIndex).getAbsolutePath() + " has been loaded");
		}	
	}
	
	private class ToolBarButtonNext implements ActionListener {
		public void actionPerformed(ActionEvent e) {				
			if (imageToViewListIndex == imagesToViewListSize - 1) {
				imageToViewListIndex = 0;
			} else {
				imageToViewListIndex += 1;
			}			
			createImage(imagesToView.get(imageToViewListIndex).getAbsolutePath());
			logger.logDEBUG("Image: " + imagesToView.get(imageToViewListIndex).getAbsolutePath() + " has been loaded");
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
			createImage(imagesToView.get(imageToViewListIndex).getAbsolutePath());	
		}
	}
}