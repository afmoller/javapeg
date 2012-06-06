package moller.javapeg.program.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class StatusPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final Vector<JLabel> statusVector;
	private final Vector<Timer> timerVector;
	private final int [] indexArray;
	private final int [] statusNrSetArray;

	// Konstruktor
	public StatusPanel(boolean [] timerStatus) {
		this.setLayout(new BorderLayout());

		JPanel leftStatusPanel = new JPanel(new GridLayout(1,1));
		JPanel rightStatusPanel = new JPanel(new GridLayout(1,(timerStatus.length - 1)));

		this.add(leftStatusPanel, BorderLayout.CENTER);
		this.add(rightStatusPanel, BorderLayout.EAST);

		statusVector 	 = new Vector<JLabel>();
		timerVector 	 = new Vector<Timer>();
		indexArray   	 = new int [timerStatus.length];
		statusNrSetArray = new int [timerStatus.length];

		// För att motverka nullpointerexception i setStatusMessage-metoden
		for(int i=0; i<indexArray.length; i++){indexArray[i] = 9999;}

		for(int i=0; i<timerStatus.length; i++) {
			JLabel status;
			Timer myTimer;

			// Skapa statusfälten.
			if(i==0) {
				status = new JLabel(" ", JLabel.LEFT);
			} else {
				status = new JLabel("       ");
			}
			status.setBorder(BorderFactory.createLoweredBevelBorder());
			status.setToolTipText("");
			statusVector.addElement(status);

			// Lägga till statusfälten till statusraden
			if(i==0) {
				leftStatusPanel.add(statusVector.elementAt(i));
			} else {
				rightStatusPanel.add(statusVector.elementAt(i));
			}

			if(timerStatus[i] == true) {
				myTimer = new Timer(10000, new TimerListener());
				timerVector.addElement(myTimer);
				indexArray[i] = i;
			}
		}
	}

	public void setStatusMessage(String message, String toolTipText, int statusNr) {
		int nrOfChars = message.length();

		if(nrOfChars == 4){message = " "   + message + " "; }
		if(nrOfChars == 3){message = "  "  + message + " "; }
		if(nrOfChars == 2){message = "  "  + message + "  ";}
		if(nrOfChars == 1){message = "   " + message + "  ";}

		statusVector.elementAt(statusNr).setText(message);
		statusVector.elementAt(statusNr).setToolTipText(toolTipText);

		if(indexArray[statusNr] == statusNr) {
			timerVector.elementAt(statusNr).stop();
			timerVector.elementAt(statusNr).start();
		}

		statusNrSetArray[statusNr] = statusNr;
	}

	public void clear () {
		for (int i=0; i < this.getNrOfStatusMessages(); i++) {
			this.setStatusMessage(" ", "", i);
		}
	}

	public int getNrOfStatusMessages() {
		return statusVector.size();
	}

	// Lyssnare till ovan skapade timer.
	// Den rensar fälten
	private class TimerListener implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			for(int i=0; i<indexArray.length; i++) {
				if(indexArray[i] == i) {
					setStatusMessage(" ", "", i);
					timerVector.elementAt(i).stop();
				}
			}
		}
	}
}