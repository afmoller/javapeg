package moller.javapeg.program;
/**
 * This class was created : 2009-03-27 by Fredrik Möller
 * Latest changed         : 2009-04-19 by Fredrik Möller
 */

import java.awt.Component;
import javax.swing.JScrollPane;

public class CustomizedJScrollPane extends JScrollPane {

	private static final long serialVersionUID = 1L;

	public CustomizedJScrollPane(Component view) {
		
		this.setViewportView(view);
				
		this.horizontalScrollBar.setUnitIncrement(10);
		this.verticalScrollBar.setUnitIncrement(10);
		
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);	
	}
}