package moller.javapeg.program.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.FileSelection;
import moller.javapeg.program.config.Config;
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

	private static Config config;
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

	private String ICONFILEPATH = "resources/images/imageviewer/";

	private int columnMargin;
	private int iconWidth;
	private int nrOfImagesToView;
	
	private GridLayout thumbNailGridLayout;

	private JPanel thumbNailsPanel;

	public ImageSearchResultViewer(Set<File> imagesToView) {

		config = Config.getInstance();
		logger = Logger.getInstance();
		lang   = Language.getInstance();

		this.createMainFrame();
		this.createToolBar();
		this.createRightClickMenu();
		this.createStatusPanel();
		this.addListeners();
		this.executeLoadThumbnailsProcess(imagesToView);
		
		nrOfImagesToView = imagesToView.size();
	}

	// Create Main Window
	public void createMainFrame() {

		this.setSize(new Dimension(config.getIntProperty("imageSearchResultViewerGUI.window.width"), config.getIntProperty("imageSearchResultViewerGUI.window.height")));

		Point xyFromConfig = new Point(config.getIntProperty("imageSearchResultViewerGUI.window.location.x"),config.getIntProperty("imageSearchResultViewerGUI.window.location.y"));

		if(Screen.isOnScreen(xyFromConfig)) {
			this.setLocation(xyFromConfig);
		} else {
			this.setLocation(0,0);
			JOptionPane.showMessageDialog(null, lang.get("errormessage.maingui.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			logger.logERROR("Could not set location of Image Search Result Viewer GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");
		}

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e){
			logger.logERROR("Could not set desired Look And Feel for Image Search Result Viewer GUI");
			logger.logERROR(e);
		}

//		InputStream imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Open16.gif");
//
//		ImageIcon image = new ImageIcon();
//		try {
//			image.setImage(ImageIO.read(imageStream));
//			this.setIconImage(image.getImage());
//		} catch (IOException e) {
//			logger.logERROR("Could not load icon: Open16.gif");
//			logger.logERROR(e);
//		}

//		TODO: Fix hard coded string
		this.setTitle("Search Result");
		this.getContentPane().add(this.createThumbNailsBackgroundPanel(), BorderLayout.CENTER);
	}
	
	private JScrollPane createThumbNailsBackgroundPanel(){
		
		thumbNailGridLayout = new GridLayout(0, 6);
		thumbNailsPanel = new JPanel(thumbNailGridLayout);

		JScrollBar hSB = new JScrollBar(JScrollBar.HORIZONTAL);
		JScrollBar vSB = new JScrollBar(JScrollBar.VERTICAL);

		hSB.setUnitIncrement(40);
		vSB.setUnitIncrement(40);

		JScrollPane scrollpane = new JScrollPane(thumbNailsPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollpane.setHorizontalScrollBar(hSB);
		scrollpane.setVerticalScrollBar(vSB);
		
		return scrollpane;
	}

	// Create ToolBar
	public void createToolBar()	{
		
		toolBar = new JToolBar();
		toolBar.setRollover(true);

//		previousJButton = new JButton();
//		nextJButton = new JButton();
//		automaticAdjustToWindowSizeJToggleButton = new JToggleButton();
//		adjustToWindowSizeJButton = new JButton();

		InputStream imageStream = null;

		ImageIcon previousImageIcon = new ImageIcon();
		ImageIcon nextImageIcon = new ImageIcon();
		ImageIcon automaticAdjustToWindowSizeImageIcon = new ImageIcon();
		ImageIcon adjustToWindowSizeImageIcon = new ImageIcon();

		try {
			imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Back16.gif");
			previousImageIcon.setImage(ImageIO.read(imageStream));
//			previousJButton.setIcon(previousImageIcon);
//			previousJButton.setToolTipText(lang.get("imageviewer.button.back.toolTip"));

			imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Forward16.gif");
			nextImageIcon.setImage(ImageIO.read(imageStream));
//			nextJButton.setIcon(nextImageIcon);
//			nextJButton.setToolTipText(lang.get("imageviewer.button.forward.toolTip"));

			imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "AutoAdjustToWindowSize16.gif");
			automaticAdjustToWindowSizeImageIcon.setImage(ImageIO.read(imageStream));
//			automaticAdjustToWindowSizeJToggleButton.setIcon(automaticAdjustToWindowSizeImageIcon);
//			automaticAdjustToWindowSizeJToggleButton.setToolTipText(lang.get("imageviewer.button.automaticAdjustToWindowSize.toolTip"));
//			automaticAdjustToWindowSizeJToggleButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.automaticAdjustToWindowSize.mnemonic").charAt(0)));

			imageStream = StartJavaPEG.class.getResourceAsStream(ICONFILEPATH + "Zoom16.gif");
			adjustToWindowSizeImageIcon.setImage(ImageIO.read(imageStream));
//			adjustToWindowSizeJButton.setIcon(adjustToWindowSizeImageIcon);
//			adjustToWindowSizeJButton.setToolTipText(lang.get("imageviewer.button.adjustToWindowSize.toolTip"));
//			adjustToWindowSizeJButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("imageviewer.button.adjustToWindowSize.mnemonic").charAt(0)));
		} catch (IOException e) {
			logger.logERROR("Could not load image. See Stack Trace below for details");
			logger.logERROR(e);
		}

//		toolBar.add(previousJButton);
//		toolBar.add(nextJButton);
		toolBar.addSeparator();
//		toolBar.add(automaticAdjustToWindowSizeJToggleButton);
//		toolBar.add(adjustToWindowSizeJButton);

		this.getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	public void createRightClickMenu(){

		rightClickMenu = new JPopupMenu();

//		TODO: Fix hard coded string
		popupMenuSelectAll = new JMenuItem("Select all images");
//		TODO: Fix hard coded string
		popupMenuDeSelectAll = new JMenuItem("Deselect all selected images");
//		TODO: Fix hard coded string
		popupMenuSetSelectedToViewList = new JMenuItem("Add selected image(s) to view list");
//		TODO: Fix hard coded string		
		popupMenuCopyImageToSystemClipBoard = new JMenuItem("Copy selected image to system clip board"); 
//		TODO: Fix hard coded string
		popupMenuCopyAllImagesToSystemClipBoard = new JMenuItem("Copy all images to system clip board");
		
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
		config.setIntProperty("imageSearchResultViewerGUI.window.location.x", this.getLocationOnScreen().x);
		config.setIntProperty("imageSearchResultViewerGUI.window.location.y", this.getLocationOnScreen().y);
		config.setIntProperty("imageSearchResultViewerGUI.window.width", this.getSize().width);
		config.setIntProperty("imageSearchResultViewerGUI.window.height", this.getSize().height);
	}

	private void setStatusMessages () {
			int nrOfColumns = thumbNailGridLayout.getColumns();
			
			statuspanel.setStatusMessage(Integer.toString(nrOfColumns), lang.get("statusbar.message.amountOfColumns"), 1);
			
			int extraRow = nrOfImagesToView % nrOfColumns == 0 ? 0 : 1;
			int rowsInGridLayout = (nrOfImagesToView / nrOfColumns) + extraRow; 

			statuspanel.setStatusMessage(Integer.toString(rowsInGridLayout), lang.get("statusbar.message.amountOfRows"), 2);
//			TODO: Fix hard coded string
			statuspanel.setStatusMessage(Integer.toString(nrOfImagesToView), "Amount of images in search result", 3);
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
				popupMenuCopyImageToSystemClipBoard.setActionCommand(((JToggleButton)e.getComponent()).getActionCommand());
			}
		}
	}

	private class RightClickMenuListenerSetSelectedToViewList implements ActionListener {
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
		public void actionPerformed(ActionEvent e) {
			for (JToggleButton jToggleButton : getJToggleButtons()) {
				if (jToggleButton.getName().equals("deselected")) {
					setSelectedThumbNailImage(jToggleButton);
					jToggleButton.setSelected(true);
				}	
			}
		}
	}
	
	private class RightClickMenuListenerDeSelectAll implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (JToggleButton jToggleButton : getJToggleButtons()) {
				if (jToggleButton.getName().equals("selected")) {
					setDeSelectedThumbNailImage(jToggleButton);
					jToggleButton.setSelected(false);
				}
			}
		}
	}
	
	private class RightClickMenuListenerCopyImageToSystemClipBoard implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			List<File> selectedFiles = new ArrayList<File>();
			selectedFiles.add(new File(e.getActionCommand()));

			FileSelection fileSelection = new FileSelection(selectedFiles);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(fileSelection, null);
		}
	}
	
	private class RightClickMenuListenerCopyAllImagesToSystemClipBoard implements ActionListener {
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
	
	private void setSelectedThumbNailImage(JToggleButton toggleButton) {
		GrayFilter filter = new GrayFilter(true, 35);
		ImageProducer prod = new FilteredImageSource(((ImageIcon)toggleButton.getIcon()).getImage().getSource(), filter);
		Image disabledImage = Toolkit.getDefaultToolkit().createImage(prod);
		
		toggleButton.setIcon(new ImageIcon(disabledImage));
		toggleButton.setName("selected");
	}
	
	private void setDeSelectedThumbNailImage(JToggleButton toggleButton) {
		toggleButton.setIcon(new ImageIcon(JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(new File(toggleButton.getActionCommand())).getThumbNailData()));	
		toggleButton.setName("deselected");
	}

	private void executeLoadThumbnailsProcess(Set<File> images) {
		
		ThumbNailListener thumbNailListener = new ThumbNailListener();
		MouseButtonListener mouseRightClickButtonListener = new MouseButtonListener();
		
		for (File image : images) {
			JPEGThumbNail tn =	JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(image);

			JToggleButton thumbContainer = new JToggleButton();
			thumbContainer.setIcon(new ImageIcon(tn.getThumbNailData()));
			thumbContainer.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
			if (!config.getStringProperty("thumbnails.tooltip.state").equals("0")) {
				thumbContainer.setToolTipText(MetaDataUtil.getToolTipText(image));	
			}
			thumbContainer.setActionCommand(image.getAbsolutePath());
			thumbContainer.setName("deselected");
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
		}
		setStatusMessages();
	}
	
	private class ComponentListener extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			if (((thumbNailsPanel.getVisibleRect().width - (columnMargin * thumbNailGridLayout.getColumns())) / iconWidth) != thumbNailGridLayout.getColumns()) {
				
				int columns = (thumbNailsPanel.getVisibleRect().width - ((thumbNailGridLayout.getHgap() * thumbNailGridLayout.getColumns()) + columnMargin * thumbNailGridLayout.getColumns())) / iconWidth;
				
				thumbNailGridLayout.setColumns(columns > 0 ? columns : 1);
				thumbNailsPanel.invalidate();
				thumbNailsPanel.repaint();
				thumbNailsPanel.updateUI();
				setStatusMessages();
			}				
		}
	}
	
	private class ThumbNailListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JToggleButton toggleButton = (JToggleButton)e.getSource();
			
			if (toggleButton.getName().equals("deselected")) {
				setSelectedThumbNailImage(toggleButton);
			} else {
				setDeSelectedThumbNailImage(toggleButton);
			}
		}
	}
}
