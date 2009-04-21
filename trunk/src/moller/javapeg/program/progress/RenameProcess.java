package moller.javapeg.program.progress;
/**
 * This class was created : 2003-10-16 by Fredrik M�ller
 * Latest changed         : 2003-10-21 by Fredrik M�ller
 *                        : 2003-10-24 by Fredrik M�ller
 *                        : 2003-10-25 by Fredrik M�ller
 *                        : 2004-03-04 by Fredrik M�ller
 *                        : 2004-03-08 by Fredrik M�ller
 *                        : 2007-05-01 by Fredrik M�ller
 *                        : 2007-07-22 by Fredrik M�ller
 *                        : 2007-07-28 by Fredrik M�ller
 *                        : 2009-03-07 by Fredrik M�ller
 *                        : 2009-03-09 by Fredrik M�ller
 *                        : 2009-03-10 by Fredrik M�ller
 *                        : 2009-03-14 by Fredrik M�ller
 *                        : 2009-04-03 by Fredrik M�ller
 *                        : 2009-04-13 by Fredrik M�ller
 *                        : 2009-04-14 by Fredrik M�ller
 */

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import moller.javapeg.program.ApplicationContext;
import moller.javapeg.program.Config;
import moller.javapeg.program.GBC;
import moller.javapeg.program.language.Language;
import moller.util.mnemonic.MnemonicConverter;

public class RenameProcess extends JFrame implements ActionListener {

	private static final long serialVersionUID = 3159146939361532653L;

	private static final Timer TIMER = new Timer(true);

	private JProgressBar processProgressBar;
	private CustomizedJTextArea log;
	private String processMessage = "";
	private SimpleDateFormat sdf;
		
	private JPanel background;

	private int processProgress = 0;
	
	private JLabel title;

	private JButton dismissWindowButton;
	private JButton openDestinationDirectoryButton;

	private UpdateTask updateTask = null;
	private GUIUpdater guiUpdater = null;
	
	private Language lang;

	public RenameProcess() {
		lang = Language.getInstance();
		setTitle(lang.get("progress.RenameProcess.title.processStarting"));
		
		setLayout(new GridBagLayout());
		updateTask = new UpdateTask();
		guiUpdater = new GUIUpdater();
		
		background = new JPanel(new GridBagLayout());
		
		title = new JLabel("_");

		if(ApplicationContext.getInstance().isCreateThumbNailsCheckBoxSelected()) {
			processProgressBar = new JProgressBar(0, 14);	
		} else {
			processProgressBar = new JProgressBar(0, 11);
		}
				
		log = new CustomizedJTextArea();
		log.setEditable(false);
		
		JScrollPane jsp = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setPreferredSize(new Dimension(600,300));
			
		dismissWindowButton = new JButton(lang.get("progress.RenameProcess.dismissWindowButton.processStarting"));
		dismissWindowButton.setToolTipText(lang.get("progress.RenameProcess.dismissWindowButton.processStarting.toolTip"));
		dismissWindowButton.setEnabled(false);
		dismissWindowButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("progress.RenameProcess.dismissWindowButton.mnemonic").charAt(0)));
		
		openDestinationDirectoryButton = new JButton(lang.get("progress.RenameProcess.openDestinationDirectoryButton.processStarting"));
		openDestinationDirectoryButton.setToolTipText(lang.get("progress.RenameProcess.openDestinationDirectoryButton.processStarting.toolTip"));
		openDestinationDirectoryButton.setEnabled(false);
		openDestinationDirectoryButton.setMnemonic(MnemonicConverter.convertAtoZCharToKeyEvent(lang.get("progress.RenameProcess.openDestinationDirectoryButton.mnemonic").charAt(0)));
		
		GBC gbcRIF = GBC.std().insets(0, 0, 20, 0).fill(GBC.HORIZONTAL);
		GBC gbcEol = GBC.eol();
		GBC gbcEolFillI = GBC.eol().fill(GBC.HORIZONTAL).insets(0, 5, 0, 0);

		background.add(title, gbcRIF);
		background.add(Box.createVerticalStrut(20), gbcEol);

		background.add(processProgressBar, gbcEolFillI);
		background.add(Box.createVerticalStrut(10), gbcEol);
		background.add(jsp, gbcEolFillI);
		background.add(Box.createVerticalStrut(20), gbcEol);
		
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		
		GBC gbcRight = GBC.std().anchor(GBC.SOUTHEAST).insets(5, 0, 0, 0);
		bottomPanel.add(Box.createHorizontalGlue(), GBC.std().fill(GBC.HORIZONTAL));
		bottomPanel.add(dismissWindowButton, gbcRight);
		bottomPanel.add(openDestinationDirectoryButton, gbcRight);

		background.add(bottomPanel, gbcEolFillI);

		JPanel borderPanel = new JPanel(new GridBagLayout());
		borderPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		borderPanel.add(background, GBC.std().insets(10, 10, 10, 10).fill());

		add(borderPanel, GBC.std().fill());
		setUndecorated(true);

		dismissWindowButton.addActionListener(this);
		openDestinationDirectoryButton.addActionListener(this);

		// Initialize the layout in respect to the layout (font size ...)
		pack();

		// The layout is now initialized - we disable it because we don't want
		// want to the labels to jump around if the content changes.
		background.setLayout(null);

		Dimension dScreen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dContent = getSize();
		setLocation((dScreen.width - dContent.width) / 2, (dScreen.height - dContent.height) / 2);

		processProgressBar.setMinimum(0);
		processProgressBar.setValue(0);
		
		this.setAlwaysOnTop(true);
	}

	public void init() {
		updateGUI();
		TIMER.schedule(updateTask, 0, 500);
		sdf = new SimpleDateFormat(Config.getInstance().getStringProperty("rename.progress.log.timestamp.format"));
	}
	
	public void incProcessProgress() {
		processProgress++;
		updateGUI();
	}
	
	public void setProcessMessage(String message) {
		processMessage = message;
	}
	
	public void setLogMessage(String logMessage) {
		String formattedTimeStamp = sdf.format(new Date(System.currentTimeMillis()));
		log.appendAndScroll(formattedTimeStamp + ": " + logMessage + "\n");
	}
		
	public void renameProcessFinished() {
		stopUpdateTask();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				setTitle(lang.get("progress.RenameProcess.title.processFinished"));

				dismissWindowButton.setText(lang.get("progress.RenameProcess.dismissWindowButton.processFinished"));
				dismissWindowButton.setToolTipText(lang.get("progress.RenameProcess.dismissWindowButton.processFinished.toolTip"));
				dismissWindowButton.setEnabled(true);

				openDestinationDirectoryButton.setText(lang.get("progress.RenameProcess.openDestinationDirectoryButton.processFinished"));
				openDestinationDirectoryButton.setToolTipText(lang.get("progress.RenameProcess.openDestinationDirectoryButton.processFinished.toolTip"));
				openDestinationDirectoryButton.setEnabled(true);
			}
		});
	}

	private synchronized void stopUpdateTask() {
		try {
			updateTask.cancel();
			updateTask = null;
		} catch (Exception e) {
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
	
	public void setRenameProgressMessages(String message) {
		setProcessMessage(message);
		setLogMessage(message);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (openDestinationDirectoryButton.equals(source)) {
			try {
				String strCmd = "rundll32 url.dll,FileProtocolHandler" + " " + ApplicationContext.getInstance().getDestinationPath();
				Runtime.getRuntime().exec(strCmd);
			} catch (Exception ex) {
			}
		} else if (dismissWindowButton.equals(source)) {
			closeWindow();
		} 
	}

	private class GUIUpdater implements Runnable {

		public void run() {
			processProgressBar.setValue(processProgress);
			title.setText(processMessage);
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