package moller.javapeg.program.helpviewer;
/**
 * This class was created : 2009-04-16 by Fredrik Möller
 * Latest changed         : 2009-04-19 by Fredrik Möller
 *                        : 2009-04-20 by Fredrik Möller
 *                        : 2009-04-21 by Fredrik Möller
 *                        : 2009-04-24 by Fredrik Möller
 *                        : 2009-04-27 by Fredrik Möller
 *                        : 2009-07-20 by Fredrik Möller
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import moller.javapeg.StartJavaPEG;
import moller.javapeg.program.Config;
import moller.javapeg.program.CustomizedJScrollPane;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.gui.Screen;
import moller.util.io.StreamUtil;

public class HelpViewerGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;

	private JTree tree;
	
	private JPanel contentJPanel;
	private JPanel backgroundsPanel;
	
	private JSplitPane splitPane;
			
	private Config   conf;
	private Logger   logger;
	private Language lang;
		
	private String content = "";
		
	public HelpViewerGUI() {
		conf   = Config.getInstance();
		logger = Logger.getInstance();
		lang   = Language.getInstance();
				
		this.initiateWindow();
		this.addListeners();
	}
	
	private void initiateWindow() {
								
		this.setSize(new Dimension(conf.getIntProperty("helpViewerGUI.window.width"),conf.getIntProperty("helpViewerGUI.window.height")));
				
		Point xyFromConfig = new Point(conf.getIntProperty("helpViewerGUI.window.location.x"),conf.getIntProperty("helpViewerGUI.window.location.y"));
				
		if(Screen.isOnScreen(xyFromConfig)) {
			this.setLocation(xyFromConfig);
		} else {
			this.setLocation(0,0);
			JOptionPane.showMessageDialog(null, lang.get("helpViewerGUI.window.locationError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			logger.logERROR("Could not set location of Help Viewer GUI to: x = " + xyFromConfig.x + " and y = " + xyFromConfig.y + " since that is outside of available screen size.");
		}
		this.setLayout(new BorderLayout());
		
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){
			logger.logERROR("Could not set desired Look And Feel for Help Viewer GUI");
			logger.logERROR("Below is the generated StackTrace");
			
			for(StackTraceElement element : e.getStackTrace()) {
				logger.logERROR(element.toString());	
			}
		}
		this.getContentPane().add(this.initiateSplitPane(), BorderLayout.CENTER);	
		
		InputStream imageStream = null;
		try {
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/Help16.gif");
			this.setIconImage(ImageIO.read(imageStream));
		} catch (Exception e) {
			Logger.getInstance().logERROR("Could not open the image Help16.gif");
		} finally {
			StreamUtil.closeStream(imageStream);
		}
		
		this.setTitle(lang.get("helpViewerGUI.window.title"));
	}
	
	private JSplitPane initiateSplitPane() {	
		backgroundsPanel = new JPanel(new BorderLayout());
		backgroundsPanel.add(this.initiateContentPanel(), BorderLayout.CENTER);
		
		splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(200);
		splitPane.add(this.initiateJTree(), JSplitPane.LEFT);
		splitPane.add(backgroundsPanel, JSplitPane.RIGHT);
				
		return splitPane;
	}
	
	private void addListeners(){
		this.addWindowListener(new WindowEventHandler());
		backgroundsPanel.addComponentListener(new WindowComponentListener());
	}
	
	private class WindowEventHandler extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			updateWindowLocationAndSize();
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
	}
	
	private void updateWindowLocationAndSize() {
		conf.setIntProperty("helpViewerGUI.window.location.x", this.getLocation().x);
		conf.setIntProperty("helpViewerGUI.window.location.y", this.getLocation().y);
		conf.setIntProperty("helpViewerGUI.window.width", this.getSize().width);
		conf.setIntProperty("helpViewerGUI.window.height", this.getSize().height);	
	}
		
	private JScrollPane initiateJTree() {		
		tree = new JTree(HelpViewerGUIUtil.createNodes());
		tree.setShowsRootHandles (true);
		tree.addMouseListener(new Mouselistener());
		
		return new JScrollPane(tree);
	}
	
	private CustomizedJScrollPane initiateContentPanel() {
		contentJPanel = new JPanel(new BorderLayout());
		return new CustomizedJScrollPane(contentJPanel);
	}
		
	private JTextArea getContent(int selectedRow) {
		if (content.equals("") && selectedRow > -1) {
			String confLang = conf.getStringProperty("gUILanguageISO6391");
							
			try {
				content = StreamUtil.getString(StartJavaPEG.class.getResourceAsStream("resources/help/" + confLang + HelpViewerGUIUtil.getFile(selectedRow)));
			} catch (IOException e) {
				
				JOptionPane.showMessageDialog(null, lang.get("helpViewerGUI.errorMessage"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
				logger.logERROR("Could not load embedded help file:");
				logger.logERROR(e);
			}
			content = content.replaceAll("\r", "");
			content = content.replaceAll("\n", "");
			content = content.replaceAll("<LF>", System.getProperty("line.separator"));
		}
		
		JTextArea textarea = new JTextArea();
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		textarea.setEditable(false);
		textarea.setText(content);
		textarea.setCaretPosition(0);
		
		return textarea;
	}
			
	/**
	 * Mouse listener
	 */
	private class Mouselistener extends MouseAdapter{
		public void mousePressed(MouseEvent e){
			int selRow = tree.getRowForLocation(e.getX(), e.getY());
			if(selRow > 0) {
				content = "";
				contentJPanel.removeAll();
				contentJPanel.updateUI();
				contentJPanel.add(getContent(selRow), BorderLayout.CENTER);
			}
		}
	}
		
	private class WindowComponentListener extends ComponentAdapter {
		@Override
		public void componentResized(ComponentEvent e) {
			contentJPanel.removeAll();
			contentJPanel.updateUI();
			contentJPanel.add(getContent(-1), BorderLayout.CENTER);
		}		
	}
}