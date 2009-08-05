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
*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;

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
import javax.swing.RepaintManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.GBHelper;
import moller.javapeg.program.config.Config;
import moller.javapeg.program.helpviewer.HelpViewerGUI;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaDataUtil;
import moller.util.gui.Screen;
import moller.util.gui.Update;
import moller.util.mnemonic.MnemonicConverter;

public class ImageViewer extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static Config config;
	private static Logger logger;
	private static Language lang;
	
	private JLabel picture;
		
	private JToolBar toolBar;
	private JPopupMenu rightClickMenu;
	private StatusPanel statuspanel;
	private ImageIcon pictureImageIcon;
	private String pathToPicture;

	private JPanel imageBackground;
	private JPanel overViewBackgroundPanel;
	
	private JMenuItem popupMenuPrevious;
	private JMenuItem popupMenuNext;
	private JMenuItem popupMenuAdjustToWindowSize;

	private JButton previousJButton;
	private JButton nextJButton;
	private JButton adjustToWindowSizeJButton;
	private JButton helpJButton;
	
	private JButton maximizeButton;
	private JButton minimizeButton;

	private JSplitPane imageMetaDataSplitPane;
	
	private JScrollPane overViewScrollpane;
	private JScrollPane scrollpane;
		
	private MetaDataPanel metaDataPanel;
	
	private JToggleButton automaticAdjustToWindowSizeJToggleButton;
	private File thePicture;

	private String ICONFILEPATH = "resources/images/imageviewer/";
	
	private List<File> imagesToView;
	
	private int imageToViewListIndex;
	private int imagesToViewListSize;
	
	private boolean firstrun = true;
	private Image two = null;
	
	public ImageViewer(List<File> imagesToView) {
		
		config =  Config.getInstance();
		logger =  Logger.getInstance();
		lang   = Language.getInstance();
		
		this.imagesToView = imagesToView;
		pathToPicture = imagesToView.get(0).getAbsolutePath();
		imageToViewListIndex = 0;
		imagesToViewListSize = imagesToView.size();
		
		this.createMainFrame(pathToPicture);
		this.createToolBar();
		this.createRightClickMenu();
		this.createStatusPanel(pathToPicture);
		this.addListeners();
	}

	// Create Main Window
	public void createMainFrame(String pathToPicture) {
		
		this.setSize(new Dimension(config.getIntProperty("imageViewerGUI.window.width"), config.getIntProperty("imageViewerGUI.window.height")));

		Point xyFromConfig = new Point(config.getIntProperty("imageViewerGUI.window.location.x"),config.getIntProperty("imageViewerGUI.window.location.y"));
				
		if(Screen.isOnScreen(xyFromConfig)) {
			this.setLocation(xyFromConfig);
		} else {
			this.setLocation(0,0);
			JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			logger.logERROR("Could not set location of Image Viewer GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");
		}

		ImageIcon image = new ImageIcon(StartJavaPEG.class.getResource(ICONFILEPATH + "Open16.gif"));
		this.setIconImage(image.getImage());
		
		imageBackground = new JPanel(new BorderLayout());
		
		JScrollBar hSB = new JScrollBar(JScrollBar.HORIZONTAL);
		JScrollBar vSB = new JScrollBar(JScrollBar.VERTICAL);
		
		hSB.setUnitIncrement(40);
		vSB.setUnitIncrement(40);
		
		scrollpane = new JScrollPane(imageBackground, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setHorizontalScrollBar(hSB);
		scrollpane.setVerticalScrollBar(vSB);
		
		thePicture = new File(pathToPicture);

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
		
		pictureImageIcon = null;
		System.gc();
		
		pictureImageIcon = new ImageIcon(pathToPicture);
	
		picture = new JLabel(pictureImageIcon);
		
		imageBackground.removeAll();
		imageBackground.updateUI();
		imageBackground.add(picture, BorderLayout.CENTER);
	}
	
	private JPanel createOverviewPanel() {
		
		JPanel backgroundPanel = new JPanel(new GridBagLayout());
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		JPanel buttonBackgroundPanel = new JPanel(new GridBagLayout());
		
		GBHelper posButtonPanel = new GBHelper();
		GBHelper posButtonBackgroundPanel = new GBHelper();
		
		Dimension buttonSize = new Dimension(16, 16);
		
		maximizeButton = new JButton(new ImageIcon(StartJavaPEG.class.getResource(ICONFILEPATH + "Forward16.gif")));
		maximizeButton.setPreferredSize(buttonSize);
		maximizeButton.setMinimumSize(buttonSize);
		
		minimizeButton = new JButton(new ImageIcon(StartJavaPEG.class.getResource(ICONFILEPATH + "Back16.gif")));
		minimizeButton.setPreferredSize(buttonSize);
		minimizeButton.setMinimumSize(buttonSize);
		
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

		previousJButton = new JButton(new ImageIcon(StartJavaPEG.class.getResource(ICONFILEPATH + "Back16.gif")));
		previousJButton.setToolTipText(lang.get("imageviewer.button.back.toolTip"));
		previousJButton.setActionCommand("Back");
		previousJButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.back.mnemonic").charAt(0)));

		nextJButton = new JButton(new ImageIcon(StartJavaPEG.class.getResource(ICONFILEPATH + "Forward16.gif")));
		nextJButton.setToolTipText(lang.get("imageviewer.button.forward.toolTip"));
		nextJButton.setActionCommand("Forward");
		nextJButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.forward.mnemonic").charAt(0)));
		
		automaticAdjustToWindowSizeJToggleButton = new JToggleButton(new ImageIcon(StartJavaPEG.class.getResource(ICONFILEPATH + "AutoAdjustToWindowSize16.gif")));
		automaticAdjustToWindowSizeJToggleButton.setToolTipText(lang.get("imageviewer.button.automaticAdjustToWindowSize.toolTip"));
		automaticAdjustToWindowSizeJToggleButton.setActionCommand("automaticAdjustToWindowSize");
		automaticAdjustToWindowSizeJToggleButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.automaticAdjustToWindowSize.mnemonic").charAt(0)));

		adjustToWindowSizeJButton = new JButton(new ImageIcon(StartJavaPEG.class.getResource(ICONFILEPATH + "Zoom16.gif")));
		adjustToWindowSizeJButton.setToolTipText(lang.get("imageviewer.button.adjustToWindowSize.toolTip"));
		adjustToWindowSizeJButton.setActionCommand("adjustToWindowSize");
		adjustToWindowSizeJButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.adjustToWindowSize.mnemonic").charAt(0)));
		
		helpJButton = new JButton(new ImageIcon(StartJavaPEG.class.getResource(ICONFILEPATH + "Help16.gif")));
		helpJButton.setToolTipText(lang.get("imageviewer.button.help.toolTip"));
		helpJButton.setActionCommand("help");
		helpJButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.help.mnemonic").charAt(0)));

		toolBar.add(previousJButton);
		toolBar.add(nextJButton);
		toolBar.addSeparator();
		toolBar.add(automaticAdjustToWindowSizeJToggleButton);
		toolBar.add(adjustToWindowSizeJButton);
		toolBar.addSeparator();
		toolBar.add(helpJButton);

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
	public void createStatusPanel(String pathToPicture)
	{
		boolean [] timerStatus = {false,false,false,false};
		statuspanel = new StatusPanel(timerStatus);
		this.getContentPane().add(statuspanel, BorderLayout.SOUTH);

		double fileSize = (double)thePicture.length();
		String fileSizeString = "";
		String prefix = "";
		String toolTipPrefix = "";
		
		if(fileSize > 10000){
			fileSizeString = Double.toString(fileSize/1000);
			prefix = "K";
			toolTipPrefix = "Kilo";
		}
		setStatusMessages(fileSizeString, prefix, toolTipPrefix);
	}

	private void addListeners() {
		this.addWindowListener(new WindowDestroyer());
		previousJButton.addActionListener(new ToolBarButtonPrevious());
		nextJButton.addActionListener(new ToolBarButtonNext());
		adjustToWindowSizeJButton.addActionListener(new ToolBarButtonAdjustToWindowSize());
		automaticAdjustToWindowSizeJToggleButton.addActionListener(new ToolBarButtonAutomaticAdjustToWindowSize());
		helpJButton.addActionListener(new ToolBarButtonHelp());
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
	
	private void setStatusMessages (String fileSizeString, String prefix, String toolTipPrefix) {
		statuspanel.setStatusMessage(" " + pathToPicture, lang.get("imageviewer.statusbar.pathToPicture"), 0);
		statuspanel.setStatusMessage(" " + lang.get("imageviewer.statusbar.sizeLabel") + " " + fileSizeString +  " " + prefix + "byte ", lang.get("imageviewer.statusbar.sizeLabelImage") + " " + toolTipPrefix + "bytes", 1);
//		statuspanel.setStatusMessage(" " + lang.get("imageviewer.statusbar.widthLabel") + " " + Integer.toString(pictureImageIcon.getIconWidth())  + " px ", lang.get("imageviewer.statusbar.widthLabelImage"), 2);
//		statuspanel.setStatusMessage(" " + lang.get("imageviewer.statusbar.heightLabel") + " "  + Integer.toString(pictureImageIcon.getIconHeight()) + " px", lang.get("imageviewer.statusbar.heightLabelImage"), 3);
		statuspanel.setStatusMessage(" " + lang.get("imageviewer.statusbar.widthLabel") + " " + "100"  + " px ", lang.get("imageviewer.statusbar.widthLabelImage"), 2);
		statuspanel.setStatusMessage(" " + lang.get("imageviewer.statusbar.heightLabel") + " "  + "100" + " px", lang.get("imageviewer.statusbar.heightLabelImage"), 3);
	}
	
	private void loadImageAndSetStatusBar(Image img) {
			
			
			ImageIcon icon = new ImageIcon();
			icon.setImage(img);
		
			pictureImageIcon = null;

			picture.setIcon(null);
			picture.setIcon(icon);

			imageBackground.removeAll();
			imageBackground.updateUI();
		
			imageBackground.add(picture, BorderLayout.CENTER);

			File tempFile = new File(pathToPicture);

			double fileSize = (double)tempFile.length();
			String fileSizeString = "";
			String prefix = "";
			String toolTipPrefix = "";

			if(fileSize > 10000){
				fileSizeString = Double.toString(fileSize/1000);
				prefix = "K";
				toolTipPrefix = "Kilo";
			}

			setStatusMessages(fileSizeString, prefix, toolTipPrefix);

			// Om knappen justera storlek automatiskt är intryckt
			if(automaticAdjustToWindowSizeJToggleButton.isSelected()) {
				double iconWidth  = Double.parseDouble(Integer.toString(pictureImageIcon.getIconWidth()));
				double iconHeight = Double.parseDouble(Integer.toString(pictureImageIcon.getIconHeight()));

				Rectangle tempRect = imageBackground.getVisibleRect();

				double backgroundWidth = tempRect.getWidth();
				double backgroundHeight = tempRect.getHeight();

				if((iconWidth>backgroundWidth)||(iconHeight>backgroundHeight)){
					adjustToWindowSizeJButton.doClick();
					adjustToWindowSizeJButton.doClick();
				}
			}	
			metaDataPanel.setMetaData(tempFile);			
	}
	
	private void disposeFrame() {
		this.saveSettings();
		this.setVisible(false);
		this.dispose();
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
			System.gc();

			if (imageToViewListIndex == 0) {
				imageToViewListIndex = imagesToViewListSize - 1;
			} else {
				imageToViewListIndex -= 1;
			}
			
			String imagePath = imagesToView.get(imageToViewListIndex).getAbsolutePath();
			
			// Uppdatera värden
			pathToPicture = imagePath;
			
			Image img = Toolkit.getDefaultToolkit().createImage(imagePath);
			while (img.getWidth(null) < 0 || img.getHeight(null) < 0) {
			    try {
			    	Thread.sleep(200);
			    } catch(InterruptedException ie) {
			    }
			}
			
			loadImageAndSetStatusBar(img);		
		}	
	}
	
	private class ToolBarButtonNext implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Total memory: " + Runtime.getRuntime().totalMemory());
			
			if (imageToViewListIndex == imagesToViewListSize - 1) {
				imageToViewListIndex = 0;
			} else {
				imageToViewListIndex += 1;
			}
			
			
			
			String imagePath = imagesToView.get(imageToViewListIndex).getAbsolutePath();
			
			// Uppdatera värden
			pathToPicture = imagePath;
			
			Image img = Toolkit.getDefaultToolkit().createImage(imagePath);
			while (img.getWidth(null) < 0 || img.getHeight(null) < 0) {
			    try {
			    	Thread.sleep(200);
			    } catch(InterruptedException ie) {
			    }
			}
			
			
				loadImageAndSetStatusBar(img);
				System.out.println("Free memory : " + Runtime.getRuntime().freeMemory());
		}
	}
	
	private class ToolBarButtonAutomaticAdjustToWindowSize implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(automaticAdjustToWindowSizeJToggleButton.isSelected()) { 
				double iconWidth  = Double.parseDouble(Integer.toString(pictureImageIcon.getIconWidth()));
				double iconHeight = Double.parseDouble(Integer.toString(pictureImageIcon.getIconHeight()));

				Rectangle tempRect = imageBackground.getVisibleRect();

				double backgroundWidth = tempRect.getWidth();
				double backgroundHeight = tempRect.getHeight();

				if((iconWidth>backgroundWidth)||(iconHeight>backgroundHeight)){
					System.gc();
					adjustToWindowSizeJButton.doClick();
				}
			}
		}
	}
	
	private class ToolBarButtonAdjustToWindowSize implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.gc();
			double iconWidth  = Double.parseDouble(Integer.toString(pictureImageIcon.getIconWidth()));
			double iconHeight = Double.parseDouble(Integer.toString(pictureImageIcon.getIconHeight()));

			Rectangle tempRect = imageBackground.getVisibleRect();

			double backgroundWidth = tempRect.getWidth();
			double backgroundHeight = tempRect.getHeight();

			if((iconWidth>backgroundWidth)||(iconHeight>backgroundHeight)) {
				Image tempImage;
				if((iconWidth>backgroundWidth)&&(iconHeight<=backgroundHeight)) {
					tempImage = pictureImageIcon.getImage();
					tempImage = tempImage.getScaledInstance((int)backgroundWidth, (int)(iconHeight/(iconWidth/backgroundWidth)), Image.SCALE_FAST);
					pictureImageIcon.setImage(tempImage);
					imageBackground.updateUI();
				} else if((iconWidth<=backgroundWidth)&&(iconHeight>backgroundHeight)) {
					tempImage = pictureImageIcon.getImage();
					tempImage = tempImage.getScaledInstance((int)(iconWidth/(iconHeight/backgroundHeight)), (int)backgroundHeight, Image.SCALE_FAST);
					pictureImageIcon.setImage(tempImage);
					imageBackground.updateUI();
				} else if((iconWidth>backgroundWidth)&&(iconHeight>backgroundHeight)) {
					tempImage = pictureImageIcon.getImage();

					if((iconWidth/backgroundWidth)>(iconHeight/backgroundHeight)) {
						tempImage = tempImage.getScaledInstance((int)backgroundWidth + 14, (int)(iconHeight/(iconWidth/backgroundWidth)) + 14, Image.SCALE_FAST);
					} else {
						tempImage = tempImage.getScaledInstance((int)(iconWidth/(iconHeight/backgroundHeight)) + 14, (int)backgroundHeight + 14, Image.SCALE_FAST);
					}
					pictureImageIcon.setImage(tempImage);

					imageBackground.updateUI();
				}
			}
		}
	}
	
	private class ToolBarButtonHelp implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			new HelpViewerGUI().setVisible(true);
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
			
			String imagePath = imagesToView.get(imageToViewListIndex).getAbsolutePath();
			
			// Uppdatera värden
			pathToPicture = imagePath;
			
			Image img = Toolkit.getDefaultToolkit().createImage(imagePath);
			while (img.getWidth(null) < 0 || img.getHeight(null) < 0) {
			    try {
			    	Thread.sleep(200);
			    } catch(InterruptedException ie) {
			    }
			}
			
			loadImageAndSetStatusBar(img);
		}
	}
}