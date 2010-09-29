package moller.javapeg.program.gui;
/**
 * This class was created : 2009-12-25 by Fredrik Möller
 * Latest changed         : 2009-12-28 by Fredrik Möller
 *                        : 2010-01-28 by Fredrik Möller
 */

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import moller.javapeg.program.imagerepository.ImageRepositoryItem;
import moller.util.io.Status;

public class CustomCellRenderer	extends	JLabel implements ListCellRenderer {
	
	private static final long serialVersionUID = 1L;
	
	public CustomCellRenderer() {
		this.setOpaque(true);
	}
     
	public Component getListCellRendererComponent(JList list, Object value, int index, 
			                                      boolean isSelected, boolean cellHasFocus) {
		
		Status directoryStatus = ((ImageRepositoryItem)value).getPathStatus();
		String directoryPathValue = ((ImageRepositoryItem)value).getPath();
		
		this.setText(directoryPathValue);
		
		switch (directoryStatus) {
		case DOES_NOT_EXIST:
			this.setBackground(new Color(255,127,127));
//			TODO: Fix hard coded string
			this.setToolTipText("Image Repository path does not exist.");
			break;
		case EXISTS:
			this.setBackground(new Color(127,255,127));
//			TODO: Fix hard coded string
			this.setToolTipText("Image Repository path exists.");
			break;
		case NOT_AVAILABLE:
			this.setBackground(new Color(251,231,128));
//			TODO: Fix hard coded string
			this.setToolTipText("Image Repository is not available.");
			break;
		}
		
		if(isSelected) {
			this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			
		} else {
			this.setBorder(null);
			
		}
		return this;
	}
}