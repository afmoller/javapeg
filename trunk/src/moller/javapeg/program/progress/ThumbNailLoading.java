package moller.javapeg.program.progress;
/**
 * This class was created : 2003-10-16 by Fredrik M�ller
 * Latest changed         : 2003-10-21 by Fredrik M�ller
 *                        : 2003-10-24 by Fredrik M�ller
 *                        : 2003-10-25 by Fredrik M�ller
 *                        : 2004-03-04 by Fredrik M�ller
 *                        : 2004-03-08 by Fredrik M�ller
 *                        : 2009-02-19 by Fredrik M�ller
 *                        : 2009-03-06 by Fredrik M�ller
 *                        : 2009-03-10 by Fredrik M�ller
 *                        : 2009-03-11 by Fredrik M�ller 
 */

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import moller.javapeg.program.language.Language;

public class ThumbNailLoading extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7597859635355677729L;
	
	private JProgressBar progress;
		
	public ThumbNailLoading(int theMinimumValue, int theMaximumValue){
		
		Language lang = Language.getInstance();
		
		// H�mta in sk�rmstorlek f�r att kunna positionera progressbarf�nstret h�r nedan
		Dimension	dScreen		= Toolkit.getDefaultToolkit().getScreenSize();
		Dimension	dContent 	= new Dimension(630,52);

		// S�tter storlek och position p� progressbarf�nstret. F�nstret hamnar centrerat p�
		// sk�rmen.
		this.setLocation((dScreen.width-dContent.width)/2,(dScreen.height-dContent.height)/2);
		this.setSize(dContent);

		// Skapar JPanel att l�gga alla nedanst�ende grafikobjekt p�.
		JPanel background = new JPanel(null);
		background.setBorder(BorderFactory.createRaisedBevelBorder());

		// Skapar JLabel f�r titel
		JLabel title = new JLabel(lang.get("progress.ThumbNailLoading.title"));
		title.setBounds(10,5,110,15);

		// Skapar progressbar
		progress = new JProgressBar(theMinimumValue,theMaximumValue);
		progress.setBounds(10,27,610,20);

		// L�gger alla grafikobjekt p� bakgrunden
		background.add(progress);
		background.add(title);

		// L�gger bakgrunden p� ContentPane
		getContentPane().add(background);
		
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
	}

	// Metod f�r att uppdatera alla f�lt och v�rden p� progressbaren.
	public void updateProgressBar() {
		progress.invalidate();
		progress.setValue(progress.getValue() + 1);
	}
}