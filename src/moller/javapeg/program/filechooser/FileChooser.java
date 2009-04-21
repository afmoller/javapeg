package moller.javapeg.program.filechooser;
/**
 * This class was created : 2009-03-05 by Fredrik Möller
 * Latest changed         : 2009-03-27 by Fredrik Möller
 *                        : 2009-03-28 by Fredrik Möller
 *                        : 2009-03-30 by Fredrik Möller
 *                        : 2009-04-04 by Fredrik Möller
 *                        : 2009-04-05 by Fredrik Möller
 *                        : 2009-04-14 by Fredrik Möller
 *                        : 2009-04-15 by Fredrik Möller
 *                        : 2009-04-16 by Fredrik Möller
 *                        : 2009-04-19 by Fredrik Möller
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import moller.javapeg.program.ApplicationContext;
import moller.javapeg.program.Config;
import moller.javapeg.program.CustomizedJScrollPane;
import moller.javapeg.program.FileRetriever;
import moller.javapeg.program.jpeg.JPEGThumbNail;
import moller.javapeg.program.jpeg.JPEGThumbNailRetriever;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.javapeg.program.metadata.MetaDataUtil;
import moller.javapeg.program.progress.ThumbNailLoading;
import moller.util.gui.Screen;
import moller.util.mnemonic.MnemonicConverter;

public class FileChooser extends JFrame {
		
	private static final long serialVersionUID = -335394666325766197L;
	
	private JTree tree;
	private Mouselistener mouseListener;
	private JPanel thumbnailsJPanel;
	private ThumbNailLoading pb;
	private Collection <File> jpgFilesAsFiles;
	private GridLayout thumbNailGridLayout;
	private int iconWidth = 1;
	
	private JButton okButton;
	private JButton cancelButton;
	
	private Config conf = Config.getInstance();
	private Language lang = Language.getInstance();
			
	// Constructor
	public FileChooser() {
		this.initiateWindow();
		this.addListeners();
	}
	
	private void initiateWindow() {
				
		Logger logger = Logger.getInstance();
						
		this.setSize(new Dimension(conf.getIntProperty("fileChooser.window.width"),conf.getIntProperty("fileChooser.window.height")));
				
		Point xyFromConfig = new Point(conf.getIntProperty("fileChooser.window.location.x"),conf.getIntProperty("fileChooser.window.location.y"));
				
		if(Screen.isOnScreen(xyFromConfig)) {
			this.setLocation(xyFromConfig);
		} else {
			this.setLocation(0,0);
			JOptionPane.showMessageDialog(null, lang.get("filechooser.window.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			logger.logERROR("Could not set location of File Chooser GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");
		}
		
		this.setLayout(new BorderLayout());
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){
			logger.logERROR("Could not set desired Look And Feel for File Chooser GUI");
			logger.logERROR("Below is the generated StackTrace");
			
			for(StackTraceElement element : e.getStackTrace()) {
				logger.logERROR(element.toString());	
			}
		}
		
		this.getContentPane().add(this.initiateSplitPane(), BorderLayout.CENTER);
		this.getContentPane().add(this.initiateButtonPanel(), BorderLayout.SOUTH);
		
		// Restore the button and path state 
		ApplicationContext ac = ApplicationContext.getInstance();
		ac.setFileChooserOkButtonClicked(false);
		ac.setFileChooserCancelButtonClicked(false);
		ac.setSourcePath("");
	}
	
	private JSplitPane initiateSplitPane() {
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(200);
		splitPane.add(this.initiateJTree(), JSplitPane.LEFT);
		splitPane.add(this.initiateThumbnailsPanel(), JSplitPane.RIGHT);
				
		return splitPane;
	}
	
	private void addListeners(){
		this.addWindowListener(new WindowEventHandler());
		thumbnailsJPanel.addComponentListener(new ComponentListener());
		okButton.addActionListener(new ButtonListener());
		cancelButton.addActionListener(new ButtonListener());
	}

	private class WindowEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			ApplicationContext ac = ApplicationContext.getInstance();
			ac.setFileChooserCancelButtonClicked(true);
			ac.setSourcePath("");
			updateWindowLocationAndSize();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
	}
	
	private void updateWindowLocationAndSize() {
		conf.setIntProperty("fileChooser.window.location.x", this.getLocation().x);
		conf.setIntProperty("fileChooser.window.location.y", this.getLocation().y);
		conf.setIntProperty("fileChooser.window.width", this.getSize().width);
		conf.setIntProperty("fileChooser.window.height", this.getSize().height);	
	}
		
	private JScrollPane initiateJTree() {
		
		tree = new JTree (new FileModel (new Comparator<Object> (){
			public int compare (Object fileAsObjectA, Object fileAsObjectB){
				File fileA = (File) fileAsObjectA;
				File fileB = (File) fileAsObjectB;
				return (fileA.isDirectory () && ! fileB.isDirectory ()) ? -1 : (! fileA.isDirectory () && fileB.isDirectory ()) ? 1 : fileA.getAbsolutePath ().compareToIgnoreCase (fileB.getAbsolutePath ());
			}
		}));
	
		DefaultTreeCellRenderer renderer = 	new DefaultTreeCellRenderer();
    	renderer.setLeafIcon(renderer.getDefaultClosedIcon());
		
		tree.setCellRenderer(renderer);
		tree.setRootVisible (false);
		tree.setShowsRootHandles (true);
		tree.addMouseListener(mouseListener = new Mouselistener());
		
		return new JScrollPane(tree);
	}
	
	private CustomizedJScrollPane initiateThumbnailsPanel() {
		thumbNailGridLayout = new GridLayout(0,4);
		thumbnailsJPanel = new JPanel(thumbNailGridLayout);
		return new CustomizedJScrollPane(thumbnailsJPanel);
	}
	
	private JPanel initiateButtonPanel() {
		JPanel panel = new JPanel();
		
		okButton = new JButton(lang.get("filechooser.button.ok"));
		okButton.setActionCommand("okButton");
		okButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("filechooser.button.ok.mnemonic").charAt(0)));
		okButton.setEnabled(false);
		
		cancelButton = new JButton(lang.get("filechooser.button.cancel"));
		cancelButton.setActionCommand("cancelButton");
		cancelButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("filechooser.button.cancel.mnemonic").charAt(0)));
		
		panel.add(okButton);
		panel.add(cancelButton);
		
		return panel;	
	}
	
	private void addThumbnails(String sourcePath) {
				
		// Skapa en länkad lista som skall innehålla jpgfiler
		jpgFilesAsFiles = new ArrayList<File>();

		// Hämta alla jpgfiler ifrån utpekad källkatalog
		FileRetriever fr = FileRetriever.getInstance();
		
		File sourceFile = new File(sourcePath);
		if (sourceFile.exists()) {
			fr.loadFilesFromDisk(new File(sourcePath));
			
			jpgFilesAsFiles = fr.getJPEGFiles();
			
			if(jpgFilesAsFiles.size() > 0){
	
				removeMouseListener();
				pb = new ThumbNailLoading(0, jpgFilesAsFiles.size());
				pb.setVisible(true);
							
				Thread thumbNailsFetcher = new Thread() {
					
					public void run(){
											
						// Iterera igenom alla filer och leta upp och ta ut tumnageln ur varje bild.
						for (File jpegFile : jpgFilesAsFiles) {	
							
							// Hämta ur tumnageln ur angiven fil
							JPEGThumbNail tn =	JPEGThumbNailRetriever.getInstance().retrieveThumbNailFrom(jpegFile);
											
							JLabel thumbContainer = new JLabel();
				
							thumbContainer.setIcon(new ImageIcon(tn.getThumbNailData()));
							thumbContainer.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
							thumbContainer.setToolTipText(MetaDataUtil.getToolTipText(jpegFile));
							thumbContainer.setHorizontalAlignment(JLabel.CENTER);
							
							int width = thumbContainer.getIcon().getIconWidth();
							
							if (width > iconWidth) {
								iconWidth = width;
							}
				
							addThumbnail(thumbContainer);
							updateGUI();
							
							pb.updateProgressBar();
						}
						pb.dispose();
						addMouseListener();
						okButton.setEnabled(true);
					}
				};
				thumbNailsFetcher.start();
			}
		}
	}
	
	private void addThumbnail(JLabel thumbNail) {
		thumbnailsJPanel.add(thumbNail);
	}
	
	private void updateGUI() {
		thumbnailsJPanel.updateUI();
	}
	
	private void removeMouseListener(){
		tree.removeMouseListener(mouseListener);
	}
	
	private void addMouseListener(){
		tree.addMouseListener(mouseListener);
	}
	
	private void disposeFileChooser() {
		updateWindowLocationAndSize();
		tree = null;
		this.dispose();
	}
	
	/**
	 * Mouse listener
	 */
	private class Mouselistener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			int selRow = tree.getRowForLocation(e.getX(), e.getY());
			TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
			if(selRow != -1) {
				Object [] path = selPath.getPath();
				String totalPath = "";
				for(int i=0; i<path.length; i++){
					if(i==0 || i==1 || i==path.length-1){
						totalPath = totalPath + path[i].toString();
					}
					else{
						totalPath = totalPath + path[i].toString() + "\\";
					}
				}
				ApplicationContext.getInstance().setSourcePath(totalPath);
				
				okButton.setEnabled(false);
				thumbnailsJPanel.removeAll();
				thumbnailsJPanel.updateUI();
				addThumbnails(totalPath);
			}
		}
	}
		
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ApplicationContext ac = ApplicationContext.getInstance();
			
			if("okButton".equals(e.getActionCommand())) {
				ac.setFileChooserOkButtonClicked(true);
				disposeFileChooser();
			} else if ("cancelButton".equals(e.getActionCommand())) {
				ac.setFileChooserCancelButtonClicked(true);
				ac.setSourcePath("");
				disposeFileChooser();
			}	
		}
	}
	
	private class ComponentListener extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			if ((thumbnailsJPanel.getVisibleRect().width / iconWidth) != thumbNailGridLayout.getColumns()) {
				thumbnailsJPanel.setSize(thumbnailsJPanel.getVisibleRect().width, thumbnailsJPanel.getVisibleRect().height);
				
				int columns = thumbnailsJPanel.getWidth() / iconWidth;
				
				thumbNailGridLayout.setColumns(columns > 0 ? columns : 1);
				thumbnailsJPanel.invalidate();
				thumbnailsJPanel.repaint();
				thumbnailsJPanel.updateUI();
			}				
		}
	}
}