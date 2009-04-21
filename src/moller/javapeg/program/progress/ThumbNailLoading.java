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
		
		// Hämta in skärmstorlek för att kunna positionera progressbarfönstret här nedan
		Dimension	dScreen		= Toolkit.getDefaultToolkit().getScreenSize();
		Dimension	dContent 	= new Dimension(630,52);

		// Sätter storlek och position på progressbarfönstret. Fönstret hamnar centrerat på
		// skärmen.
		this.setLocation((dScreen.width-dContent.width)/2,(dScreen.height-dContent.height)/2);
		this.setSize(dContent);

		// Skapar JPanel att lägga alla nedanstående grafikobjekt på.
		JPanel background = new JPanel(null);
		background.setBorder(BorderFactory.createRaisedBevelBorder());

		// Skapar JLabel för titel
		JLabel title = new JLabel(lang.get("progress.ThumbNailLoading.title"));
		title.setBounds(10,5,110,15);

		// Skapar progressbar
		progress = new JProgressBar(theMinimumValue,theMaximumValue);
		progress.setBounds(10,27,610,20);

		// Lägger alla grafikobjekt på bakgrunden
		background.add(progress);
		background.add(title);

		// Lägger bakgrunden på ContentPane
		getContentPane().add(background);
		
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);
	}

	// Metod för att uppdatera alla fält och värden på progressbaren.
	public void updateProgressBar() {
		progress.invalidate();
		progress.setValue(progress.getValue() + 1);
	}
}