package moller.javapeg.program;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import moller.javapeg.StartJavaPEG;
import moller.util.io.StreamUtil;

public class HTMLViewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6469304907830539198L;
	private String documentPath;
	private String documentTitle;
	private JEditorPane jep;
	private JPanel mainPanel;

	public HTMLViewer(String theDocumentPath, String theDocumentTitle) {

		documentPath = theDocumentPath;
		documentTitle = theDocumentTitle;
		this.initiateFrame();
		this.loadDocument();
	}

	private void initiateFrame() {

		Dimension	dScreen		= Toolkit.getDefaultToolkit().getScreenSize();
		Dimension	dContent 	= new Dimension(600,400);

		this.setLocation((dScreen.width-dContent.width)/2,(dScreen.height-dContent.height)/2);
		this.setSize(dContent);
		this.setResizable(false);
		this.setTitle(documentTitle);
		
		InputStream imageStream = null;
		ImageIcon titleImageIcon = new ImageIcon();
		try {
			imageStream = StartJavaPEG.class.getResourceAsStream("resources/images/javapeg.gif");
			titleImageIcon.setImage(ImageIO.read(imageStream));
		} catch (Exception e) {
			
		} finally {
			StreamUtil.closeStream(imageStream);
		}

        this.setIconImage(titleImageIcon.getImage());

		mainPanel = new JPanel();
		this.getContentPane().add(mainPanel);

		this.setVisible(true);
		this.addWindowListener(new WindowDestroyer());
	}

	private void loadDocument() {

		try {
			jep = new JEditorPane();
			jep.setContentType("text/html");
			jep.setEditable( false );
			jep.setPage(documentPath);
		}
		catch(IOException ioe){
		}

		JScrollPane jsp = new JScrollPane(jep);
		jsp.setBounds(0,0,594,368);
		mainPanel.add(jsp);

	}

	// WindowDestroyer
	private class WindowDestroyer extends WindowAdapter{
		public void windowClosing (WindowEvent e){
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		}
	}
}