package moller.javapeg.program.progress;
/**
 * This class was created : 2003-10-16 by Fredrik Möller
 * Latest changed         : 2003-10-21 by Fredrik Möller
 *                        : 2003-10-24 by Fredrik Möller
 *                        : 2003-10-25 by Fredrik Möller
 *                        : 2004-03-04 by Fredrik Möller
 *                        : 2004-03-08 by Fredrik Möller
 *                        : 2009-02-19 by Fredrik Möller
 *                        : 2009-03-06 by Fredrik Möller
 *                        : 2009-03-10 by Fredrik Möller
 *                        : 2009-03-11 by Fredrik Möller
 *                        : 2009-07-16 by Fredrik Möller 
 */

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import moller.javapeg.program.GBHelper;
import moller.javapeg.program.language.Language;

public class ThumbNailLoading extends JDialog{
	
	private static final long serialVersionUID = 7597859635355677729L;
	
	private JProgressBar progress;
		
	public ThumbNailLoading(int theMinimumValue, int theMaximumValue, Container parent){
		
		Language lang = Language.getInstance();
		
		int centerXCoordinate = parent.getX() + (parent.getWidth() / 2);
		int centerYCoordinate = parent.getY() + (parent.getHeight() / 2);

		// Skapar JPanel att lägga alla nedanstående grafikobjekt på.
		JPanel background = new JPanel(new GridBagLayout());
		background.setBorder(BorderFactory.createRaisedBevelBorder());

		// Skapar JLabel för titel
		JLabel title = new JLabel(lang.get("progress.ThumbNailLoading.title"));

		// Skapar progressbar
		progress = new JProgressBar(theMinimumValue,theMaximumValue);
		progress.setPreferredSize(new Dimension(610, 20));
		progress.setMinimumSize(new Dimension(610, 20));
		progress.setStringPainted(true);

		GBHelper posBackground = new GBHelper();
		
		// Lägger alla grafikobjekt på bakgrunden
		background.add(title, posBackground);
		background.add(progress, posBackground.nextRow().expandW());
		
		// Lägger bakgrunden på ContentPane
		getContentPane().add(background);
				
		this.setBounds(centerXCoordinate - (630 / 2), centerYCoordinate - (52 / 2), 630, 42);
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
	}

	// Metod för att uppdatera alla fält och värden på progressbaren.
	public void updateProgressBar() {
		progress.invalidate();
		progress.setValue(progress.getValue() + 1);
	}
}