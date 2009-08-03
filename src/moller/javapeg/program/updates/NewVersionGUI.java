package moller.javapeg.program.updates;
/**
 * This class was created : 2009-05-16 by Fredrik Möller
 * Latest changed         : 2009-05-19 by Fredrik Möller
 *                        : 2009-05-20 by Fredrik Möller
 *                        : 2009-08-02 by Fredrik Möller
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import moller.javapeg.program.Config;
import moller.javapeg.program.language.Language;
import moller.javapeg.program.logger.Logger;
import moller.util.io.StreamUtil;

public class NewVersionGUI extends JFrame{
		
	private static final long serialVersionUID = 1L;
	
	private JPanel mainPanel;
	private JPanel buttonPanel;

	private JLabel newVersionJLabel;

	private JButton downloadButton;
	private JButton closeButton;

	private Config conf;
	private Logger logger;
	private Language lang;
	
	private String downloadURL;
	private String fileName;
		
	private JProgressBar progress;
	
	private int progressValue;
	
	private UpdateTask updateTask = null;
	private GUIUpdater guiUpdater = null;
	
	private static final Timer TIMER = new Timer(true);
	
	private File destination;

	public NewVersionGUI(String changeLogText, String downloadURL, String fileName, String versionNumber, int fileSize) {

		conf = Config.getInstance();
		lang = Language.getInstance();
		logger = Logger.getInstance();
		
		updateTask = new UpdateTask();
		guiUpdater = new GUIUpdater();

		this.downloadURL = downloadURL;
		this.fileName    = fileName;
				
		this.setTitle(lang.get("updatechecker.gui.title") + ": " + versionNumber);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));

		newVersionJLabel = new JLabel(lang.get("updatechecker.gui.newVersion"));
		newVersionJLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
		newVersionJLabel.setForeground(Color.GRAY);
		
		JTextArea changeLog = new JTextArea(changeLogText, 20, 80);
		changeLog.setEditable(false);
		
		JScrollPane sp = new JScrollPane(changeLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		progress = new JProgressBar(0, fileSize);
		progress.setStringPainted(true);
		
		mainPanel.add(newVersionJLabel, BorderLayout.NORTH);
		mainPanel.add(sp, BorderLayout.CENTER);
		mainPanel.add(progress, BorderLayout.SOUTH);

		buttonPanel = new JPanel();

		downloadButton = new JButton(lang.get("updatechecker.gui.downloadButton"));
		closeButton = new JButton(lang.get("updatechecker.gui.closeButton"));

		buttonPanel.add(downloadButton);
		buttonPanel.add(closeButton);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){
			logger.logERROR("Could not set desired Look And Feel for New Version GUI");
			logger.logERROR(e);
		}

		addListeners();
		this.pack();
		
		this.setLocationCenteredToMainGUI();
		this.setAlwaysOnTop(true);
	}

	private void setLocationCenteredToMainGUI () {
		int mainGUILocationX = 0;
		int mainGUILocationY = 0;
		
		int mainGUIWidth = 1024;
		int mainGUIHeight = 768;
		
		if (this.getParent() != null) {
			mainGUILocationX = this.getParent().getLocation().x;
			mainGUILocationY = this.getParent().getLocation().y;
		} else {
			mainGUILocationX = conf.getIntProperty("mainGUI.window.location.x");
			mainGUILocationY = conf.getIntProperty("mainGUI.window.location.y");		
		}
		
		int mainGUICenterX = mainGUILocationX + (mainGUIWidth / 2);
		int mainGUICenterY = mainGUILocationY + (mainGUIHeight / 2);
		
		int thisGUILocationX = mainGUICenterX - (this.getSize().width / 2);
		int thisGUILocationY = mainGUICenterY - (this.getSize().height / 2);
		
		this.setLocation(thisGUILocationX, thisGUILocationY);	
	}
	
	private void addListeners(){
		addWindowListener(new WindowDestroyer());
		downloadButton.addActionListener(new DownloadButtonListener());
		closeButton.addActionListener(new CancelButtonListener());
	}
	
	private void alwaysOnTop(boolean onTop) {
		this.setAlwaysOnTop(onTop);
	}
				
	private void downloadAndSaveFile() {
		InputStream fileStream = null;
		OutputStream destinationFile = null;
				
		try {
			URL source = new URL(downloadURL);
			HttpURLConnection huc = (HttpURLConnection)source.openConnection();
			
			huc.connect();
			
			if ("DEBUG".equals(conf.getStringProperty("logger.log.level"))) {
				Map<String, List<String>> headers = huc.getHeaderFields();
				
				logger.logDEBUG("Response headers and their values for the request to: " + downloadURL);	
				for (String key : headers.keySet()) {
					logger.logDEBUG("Header: " + key + " Headervalue: " + headers.get(key));
				}
			}
							
			if (HttpURLConnection.HTTP_MOVED_TEMP == huc.getResponseCode()) {
				logger.logDEBUG("Following redirect to: " + huc.getHeaderField("location"));
				source = new URL(huc.getHeaderField("location"));	
			}
			
			fileStream = source.openStream();
						
			destinationFile = new FileOutputStream(destination);

			byte[] buf = new byte[32 * 1024];
			
			int bytesRead = 0;
			int bytesReadTotal = 0;
			
			while ((bytesRead = fileStream.read(buf)) != -1) {
				destinationFile.write(buf, 0, bytesRead);
				bytesReadTotal += bytesRead;
				this.setProgressValue(bytesReadTotal);
			}
			huc.disconnect();
			JOptionPane.showMessageDialog(this, lang.get("updatechecker.informationmessage.downloadFinished"), lang.get("errormessage.maingui.informationMessageLabel"), JOptionPane.INFORMATION_MESSAGE);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(this, lang.get("updatechecker.errormessage.downloadError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);			
			logger.logERROR(e);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, lang.get("updatechecker.errormessage.downloadError"), lang.get("errormessage.maingui.errorMessageLabel"), JOptionPane.ERROR_MESSAGE);
			logger.logERROR(e);
		} finally {
			StreamUtil.closeStream(fileStream);
			StreamUtil.closeStream(destinationFile);
		}			
	}

	private class DownloadButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
					
			alwaysOnTop(false);
			JFileChooser fc = new JFileChooser();
			
			fc.setFileHidingEnabled(true);
			fc.setSelectedFile(new File(fileName));	
								
			if (fc.showSaveDialog(NewVersionGUI.this) == JFileChooser.APPROVE_OPTION) {
				destination = fc.getSelectedFile();
								
				Thread download = new Thread() {
					public void run() {
						downloadAndSaveFile();	
					}
				};
				download.start();
			}
			alwaysOnTop(true);
		}	
	}

	private class CancelButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			closeWindow();
		}	
	}

	private class WindowDestroyer extends WindowAdapter	{
		public void windowClosing(WindowEvent e) {
			closeWindow();
		}
	}
	
	public void closeWindow() {
		try {
			stopUpdateTask();
			setVisible(false);
		} finally {
			dispose();
		}
	}
	
	public void init() {
		updateGUI();
		TIMER.schedule(updateTask, 0, 500);	
	}
	
	public void setProgressValue(int value) {
		progressValue = value;
		updateGUI();
	}
	
	private class GUIUpdater implements Runnable {
		public void run() {
			progress.setValue(progressValue);
		}
	}
	
	private synchronized void stopUpdateTask() {
		try {
			updateTask.cancel();
			updateTask = null;
		} catch (Exception e) {
		}
	}

	private void updateGUI() {
		SwingUtilities.invokeLater(guiUpdater);
	}

	private class UpdateTask extends TimerTask {
		@Override
		public void run() {
			updateGUI();
		}
	}
}